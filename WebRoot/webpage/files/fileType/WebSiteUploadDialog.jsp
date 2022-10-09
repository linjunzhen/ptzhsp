<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.*" %>
<%@ page import="net.evecom.platform.system.model.*" %>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
	String uploaderId = request.getParameter("uploadUserId");
	String uploadToken = StringUtil.encryptMd5("evecom" + uploaderId);
%>
<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>
    <link rel="stylesheet" type="text/css" href="plug-in/webuploader/webuploader.css">
    <script type="text/javascript" src="plug-in/webuploader/webuploader.min.js"></script>
    <style type="text/css">
		.uploadBtn {
		    line-height: 32px;
		    background: #00b7ee;
		    border-color: transparent;
		    color: #fff;
		    padding: 0 15px;
		    display: inline-block;
		    border-radius: 3px;
		    margin:3px 0 3px 5px;
		    cursor: pointer;
		    font-size: 12px;
		    float: left;
		}
		.uploadBtn:hover {
		    background: #00a2d4;
		}
		.disabledBtn {
		    pointer-events: none;
		    opacity: 0.6;
		}
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
        	//兼容性判断
	    	if(!WebUploader.Uploader.support()){
	            alert('Web Uploader不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器~');
	            throw new Error('WebUploader does not support the browser you are using.');
	        }
	    	//WebUploader实例
        	var uploader;
        	//加入WebUploader上传队列的文件
	    	var fileInQueque = null;
        	
        	function initWebuploader(){
        		uploader = WebUploader.create({
    	    	    swf: 'plug-in/webuploader/Uploader.swf',
    	    	    server: __file_server + 'upload/fileUpload',
    	    	    pick: {
    	    	    	id: '#localFilePicker',
    	    	    	label: '浏览',
						multiple:false
    	    	    },
    	    	    timeout: 0,
    	    	    accept: {
    	                title: 'UploadDialog',
    	                extensions: '${requestScope.acceptFileType}',
    	                mimeTypes: '*/*'
    	            },
    	    	    formData: {
    	                uploaderId: '${uploadUserId}',
    	                uploaderName: '${uploadUserName}',
    	                uploadToken: '<%=uploadToken%>',
						busTableName:'${busTableName}',
						attachKey:'${attachKey}',
						SQFS:'${SQFS}',
						SFSQ:'${SFSQ}',
						SFHZD:'${SFHZD}',
    	                isAdmin: 0 //0前台用户1后台
    	            },
    	    	    chunked: true,
    	    	    threads: 3,
    	    	    fileSingleSizeLimit: '${requestScope.fileSizeLimit}'
    	    	});
        		//清空上传队列
        		fileInQueque = null;
        		//上传遮罩
        		var shadeLayer;
        		//文件添加进队列触发
    	    	uploader.on('fileQueued', function(file){
					shadeLayer = parent.art.dialog({content: '正在校验文件...'}).lock();
					 uploader.md5File( file ,0,10 * 1024 * 1024)
					// 及时显示进度
					.progress(function(percentage) {
						var progress = percentage*100;
						if(progress < 100){
							progress = progress.toFixed(2);
						}
						shadeLayer.content('正在校验文件...已完成'+progress+'%');
					})

					// 完成
					.then(function(val) {
						shadeLayer.close();
						uploader.options.formData.localFileMd5 = val;
						$("#localFileMd5").val(val);
					});
    	    		if(fileInQueque){
    	    			uploader.removeFile(fileInQueque);
    	    			fileInQueque = file;
    	    		}else{
    	    			fileInQueque = file;
    	    		}
    	    		recordUploadResult({
    	    			fileId: '',
    	    			filePath: '',
    	    			fileServerUrl: '',
    	    			fileName: file.name,
    	    			fileSuffix: file.ext
    	    		}, 'local');
    	    	});
    	    	//某个文件开始上传前触发，一个文件只会触发一次
    	    	uploader.on('uploadStart', function(file){
    	    		shadeLayer = parent.art.dialog({content: '正在上传文件数据...'}).lock();
    	    		uploader.options.formData.uid = WebUploader.Base.guid("");
    	    	});
    	    	
    	    	//上传过程中触发，携带上传进度
    	    	uploader.on('uploadProgress', function(file, percentage){
    	    		var progress = percentage*100;
    	    		if(progress < 100){
    	    			progress = progress.toFixed(2);
    	    			shadeLayer.content('正在上传文件数据...已完成'+progress+'%');
    	    		}else if(progress == 100){
    	    			shadeLayer.content('正在上传文件数据...已完成'+progress+'%');
    	    			if('${requestScope.attachType}' == 'video'){
    	    				shadeLayer.content('已完成文件数据上传，正在进行视频文件转码...');
    	    			}
    	    		}
    	    		
    	    	});
    	    	//当文件上传成功时触发
    	    	uploader.on('uploadSuccess', function(file, response){
    	    		shadeLayer.close();
    	    		if(response.success){
    	    			recordUploadResult(response.data, 'local');
    	    			art.dialog.data("uploadedFileInfo", {
        	    			fileId: $("#localFileId").val(),
        	    			fileName: $("#localFileInput").val(),
        	    			filePath: $("#localFilePath").val(),
        	    			fileSuffix: $("#localFileSuffix").val(),
        	    			fileServerUrl: $("#localFileServerUrl").val()
        				});
            			AppUtil.closeLayer();
    	    		}else{
    	    			recordUploadResult({
        	    			fileId: '',
        	    			filePath: '',
        	    			fileServerUrl: '',
        	    			fileName: '',
        	    			fileSuffix: ''
        	    		}, 'local');
    	    			parent.art.dialog({
    						content: response.msg,
    						icon: "error",
    					    ok: true
    					});
    	    		}
    	    	});
    	    	//文件上传错误时触发
    	    	uploader.on('uploadError', function(file, reason){
    	    		shadeLayer.close();
    	    		recordUploadResult({
    	    			fileId: '',
    	    			filePath: '',
    	    			fileServerUrl: '',
    	    			fileName: '',
    	    			fileSuffix: ''
    	    		}, 'local');
    	    		var errorMsg = '';
    	    		switch(reason){
	    	    		case 'http':
		    				errorMsg = '网络堵塞';
		    				break;
		    			case 'abort':
		    				errorMsg = '线程终止';
		    				break;
		    			case 'server':
		    				errorMsg = '服务器繁忙';
		    				break;
		    			default:
		    				errorMsg = reason;
		    				break;
    	    		}
    	    		parent.art.dialog({
    					content: '文件上传出错，异常原因【'+errorMsg+'】。',
    					icon: "error",
    				    ok: true
    				});
    	    	});
    	    	//当validate不通过时，会以派送错误事件的形式通知调用者
    	    	uploader.on('error', function(type){
    	    		var errorMsg = '';
    	    		switch(type){
	    	    		case 'Q_EXCEED_NUM_LIMIT':
		    				errorMsg = '文件数超过限定值';
		    				break;
		    			case 'Q_EXCEED_SIZE_LIMIT':
		    				errorMsg = '文件总大小超过限定值';
		    				break;
		    			case 'Q_TYPE_DENIED':
		    				errorMsg = '非法的文件类型';
		    				break;
		    			case 'F_EXCEED_SIZE':
		    				errorMsg = '单文件大小超过限定值';
		    				break;
		    			case 'F_DUPLICATE':
		    				errorMsg = '已添加至上传队列';
		    				break;
		    			default:
		    				errorMsg = '未知的错误';
		    				break;
    	    		}
    	    		parent.art.dialog({
    					content: '文件上传出错，异常原因【'+errorMsg+'】。',
    					icon: "error",
    				    ok: true
    				});
    	    	});
            }
        	
        	$("input[name='fileSource']").change(function(){
        		var fileSource = $("input[name='fileSource']:checked").val();
        		if(fileSource == 'local'){
        			//启用本地浏览按钮
        			$("#localFileInput").removeAttr("disabled");
        			$("#localFilePicker").removeClass("uploadBtn disabledBtn").css("line-height", "12px");
        			initWebuploader();
        			//禁用网络浏览按钮
        			$("#remoteFileInput").attr("disabled", "disabled");
        			$("#remoteFilePicker").addClass("disabledBtn");
        			//清空选择情况
        			recordUploadResult({
    	    			fileId: '',
    	    			filePath: '',
    	    			fileServerUrl: '',
    	    			fileName: '',
    	    			fileSuffix: ''
    	    		}, 'remote');
        		}else if(fileSource == 'remote'){
        			//禁用本地浏览按钮
        			uploader.destroy();
        			$("#localFilePicker").children().remove();
        			$("#localFileInput").attr("disabled", "disabled");
        			$("#localFilePicker").addClass("uploadBtn disabledBtn").css("line-height", "32px").text("浏览");
        			//启用网络浏览按钮
        			$("#remoteFileInput").removeAttr("disabled");
        			$("#remoteFilePicker").removeClass("disabledBtn");
        			//清空选择情况
        			recordUploadResult({
    	    			fileId: '',
    	    			filePath: '',
    	    			fileServerUrl: '',
    	    			fileName: '',
    	    			fileSuffix: ''
    	    		}, 'local');
        		}
        	});
        	
        	$("#remoteFilePicker").click(function(){
        		if(!$("#remoteFilePicker").hasClass("disabledBtn")){
        			$.dialog.open('${webRoot}/fileTypeController.do?fileWebStieSelector&allowCount=1&needSelectRowIds=' + $("#remoteFileId").val()+'&attachType=${requestScope.attachType}', {
        				title: "多媒体资源选择器",
        				width: "1000px",
        				lock: true,
        				resize: false,
        				height: "500px",
        				close: function(){
        	    			var selectedFileInfo = art.dialog.data("selectedFileInfo");
        	    			if(selectedFileInfo && selectedFileInfo.fileIds){
        	    				recordUploadResult({
        	    					fileId: selectedFileInfo.fileIds,
        	    					fileName: selectedFileInfo.fileNames,
        	    					filePath: selectedFileInfo.filePaths,
        	    					fileSuffix: selectedFileInfo.fileSuffixs,
        	    					fileServerUrl: selectedFileInfo.fileServerUrls
        	    				}, 'remote');
        	    				art.dialog.removeData("selectedFileInfo");
        	    			}
        	    		}
        			});
        		}
        	});
        	
        	/**
        	 * 赋值input
        	 */
        	function recordUploadResult(file, source){
        		if(source == 'local'){
        			if(file.fileId != undefined){
        				$("#localFileId").val(file.fileId);
        			}
        			if(file.fileServerUrl != undefined){
        				$("#localFileServerUrl").val(file.fileServerUrl);
        			}
        			if(file.filePath != undefined){
        				$("#localFilePath").val(file.filePath);
        			}
        			if(file.fileName != undefined){
        				$("#localFileInput").val(file.fileName);
        			}
        			if(file.fileSuffix != undefined){
        				$("#localFileSuffix").val(file.fileSuffix);
        			}
        		}
        		if(source == 'remote'){
        			if(file.fileId != undefined){
        				$("#remoteFileId").val(file.fileId);
        			}
        			if(file.fileServerUrl != undefined){
        				$("#remoteFileServerUrl").val(file.fileServerUrl);
        			}
        			if(file.filePath != undefined){
        				$("#remoteFilePath").val(file.filePath);
        			}
        			if(file.fileName != undefined){
        				$("#remoteFileInput").val(file.fileName);
        			}
        			if(file.fileSuffix != undefined){
        				$("#remoteFileSuffix").val(file.fileSuffix);
        			}
        		}
        	}
        	
        	initWebuploader();
        	
        	/**
        	 * 对话框确定按钮点击事件
        	 */
        	$("#dialogConfirmBtn").click(function(){
        		var fileSource = $("input[name='fileSource']:checked").val();
        		if(fileSource == 'local'){
        			if($("#localFileInput").val() != ''){  	
						//上传遮罩
						var checkLayer = parent.art.dialog({content: '正在上传文件...'}).lock();
						$.post(__file_server + 'upload/checkFileMd5',{
							localFileMd5:$("#localFileMd5").val(),
							uploaderId: '${uploadUserId}',
							uploaderName: '${uploadUserName}',
							fileType:$("#localFileSuffix").val(),
							fileName: $("#localFileInput").val(),
							busTableName:'${busTableName}',
							attachKey:'${attachKey}',
							SQFS:'${SQFS}',
							SFSQ:'${SFSQ}',
							SFHZD:'${SFHZD}'
						},
						function(responseText,status,xhr){
							checkLayer.close();
							if(responseText.success){									
								recordUploadResult(responseText.data, 'local');
								art.dialog.data("uploadedFileInfo", {
									fileId: $("#localFileId").val(),
									fileName: $("#localFileInput").val(),
									filePath: $("#localFilePath").val(),
									fileSuffix: $("#localFileSuffix").val(),
									fileServerUrl: $("#localFileServerUrl").val()
								});
								AppUtil.closeLayer();
							} else{										
								uploader.upload();
							}
						}
						);
        			}else{
        				art.dialog.data("uploadedFileInfo", {
        	    			fileId: '',
        	    			fileName: '',
        	    			filePath: '',
        	    			fileSuffix: '',
        	    			fileServerUrl: ''
        				});
            			AppUtil.closeLayer();
        			}
        		}else if(fileSource == 'remote'){
        			art.dialog.data("uploadedFileInfo", {
    	    			fileId: $("#remoteFileId").val(),
    	    			fileName: $("#remoteFileInput").val(),
    	    			filePath: $("#remoteFilePath").val(),
    	    			fileSuffix: $("#remoteFileSuffix").val(),
    	    			fileServerUrl: $("#remoteFileServerUrl").val()
    				});
        			AppUtil.closeLayer();
        		}
        	});
        });
    </script>
</head>
<body class="eui-diabody" style="margin:0px;">
    <form id="FileUploadForm">
        <!--==========隐藏域部分开始 ===========-->
        <!--==========隐藏域部分结束 ===========-->
        <div id="FileUploadFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>注意事项</a></div></td>
			</tr>
			<tr>
			    <td colspan="2">
			        <div style="padding:0 25px;color:red;">
			                        文件类型限制：${requestScope.acceptFileType}；</br>
									文件数量限制：1；文件大小限制：${requestScope.formatFileSizeLimit}。
			        </div>
			    </td>
			</tr>
            <tr style="height:29px;">
				<td class="dddl-legend" style="font-weight:bold;" colspan="2">文件来源</td>
			</tr>
			<tr>
			    <td style="width:15%;text-align:right;">
			        <input name="fileSource" type="radio" value="local" checked/>
			        <span>本地：</span>
			    </td>
			    <td style="width:85%;">
			        <!-- 本地文件结果隐藏域 -->
			        <input type="hidden" id="localFileId"/>
			        <input type="hidden" id="localFileServerUrl"/>
			        <input type="hidden" id="localFilePath"/>
			        <input type="hidden" id="localFileSuffix"/>
			        <input type="hidden" id="localFileMd5"/>
			        <!-- 本地文件结果隐藏域 -->
			        <input id="localFileInput" class="eve-input validate[required]" type="text" style="width:80%;margin-top:8px;float:left;" readonly="readonly"/>
			        <div id="localFilePicker" style="float:left;margin:3px 0 3px 5px;line-height:12px;"></div>
			    </td>
			</tr>
        </table>
        </div>
		<div data-options="region:'south'" style="height:46px;" >
	        <div class="eve_buttons">
				<input id="dialogConfirmBtn" value="确定" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight"/>
				<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
    </form>
</body>