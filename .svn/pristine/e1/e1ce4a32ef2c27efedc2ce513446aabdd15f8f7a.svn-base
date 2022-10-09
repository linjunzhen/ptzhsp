<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.*" %>
<%@ page import="net.evecom.platform.system.model.*" %>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
	SysUser loginUser = AppUtil.getLoginUser();
	String uploaderId = loginUser.getUserId();
	String uploadToken = StringUtil.encryptMd5("evecom" + uploaderId);
%>
<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>
    <link rel="stylesheet" type="text/css" href="plug-in/webuploader/webuploader.css">
    <script type="text/javascript" src="plug-in/webuploader/webuploader.min.js"></script>
    <link rel="stylesheet" type="text/css" href="webpage/files/fileType/css/main.css">
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
        	
			var iconFile = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAABGCAMAAABG8BK2AAAAFVBMVEWZmZmZmZmZmZmZmZmZmZmZmZmZmZk9goCmAAAAB3RSTlMC/rYs1mCR8/Hv3QAAAYBJREFUWMPtmMsSwyAIRRWE///kGmMTH6CYtN0012knC+cU4Uq0zj169CkhqEI7hb2uYOZQnA2cx/GQHjeOFRP0qcHOQe9phLFyCAeBckwc3C1hxDgYchBJEZaYSIgcHpVHE1bRpAzxulUKDuzrCUoVcPOVLCo9t0fjUOGw7k4qaxxyduPPShzQkx8xcHDCOykyZ4zBwysHJnFwEXN4pZhHAmeGeXuFi6UI8Uwx2SuYNn9W6LbXHJM5rUtpFZM9hxCi/P7V7i4LpvYc1oVrMBDOQTVGyKmGwX7dJ6bnqNHwKaCuGbYcFSM3Topb1G0frjkrmLTQzSNpVMVZwaQSjbqYERNBZ0dlE0bpwYKNFgt+BeNKDF/HlF3YXcdYzgn/iklNSRZd9I3YL23RsKp8eHwKfhdDqhh/X/APYVDX11JMC0cBCYOcu7t3dzDUv2tuYGDh0CbmJr2Llw60Ixd/DWO9+3FbnfYobg1mNDFdRufyfrSmzLFodoFGYoPo+a9jVS+3Nw2jSs30KQAAAABJRU5ErkJggg=='; // 非图像文件预览的base64编码
             function matchProgressText(file,uploadPercentage) {
                if (uploadPercentage == 0) {
                    if (file.Status == 'queued') {
                        return '等待';
                    } else {
                        return '0%';
                    }
                } else {
                    if (file.Status == 'error') {
                        return '失败';
                    } else {
                        return (uploadPercentage + '%');
                    }
                }
            };
        	function initWebuploader(){
        		uploader = WebUploader.create({
    	    	    swf: 'plug-in/webuploader/Uploader.swf',
    	    	    server: __file_server + 'upload/fileUpload',
    	    	    pick: {
    	    	    	id: '#localFilePicker',
    	    	    	label: '浏览'
    	    	    },
    	    	    timeout: 0,
    	    	    accept: {
    	                title: 'UploadDialog',
    	                extensions: '${requestScope.acceptFileType}',
    	                mimeTypes: '*/*'
    	            },
    	    	    formData: {
    	                uploaderId: '${sessionScope.curLoginUser.userId}',
    	                uploaderName: '${sessionScope.curLoginUser.fullname}',
    	                uploadToken: '<%=uploadToken%>',
						busTableName:'${busTableName}',
						attachKey:'${attachKey}',
						SQFS:'${SQFS}',
						SFSQ:'${SFSQ}',
						SFHZD:'${SFHZD}',
    	                isAdmin: 1 //0前台用户1后台
    	            },
    	    	    chunked: true,
    	    	    threads: 1,
    	    	    fileSingleSizeLimit: '${requestScope.fileSizeLimit}',
					fileNumLimit:10
    	    	});
        		//上传遮罩
        		var shadeLayer;
        		//文件添加进队列触发
    	    	uploader.on('fileQueued', function(file){ 
					
					var  fileRet;
					uploader.makeThumb( file, function( error, ret ) {
						
						var localFileMd5 = "";
						uploader.md5File( file ,0,10 * 1024 * 1024 )
						// 及时显示进度
						.progress(function(percentage) {
							var progress = percentage*100;
							if(progress < 100){
								progress = progress.toFixed(2);
							}
						})

						// 完成
						.then(function(val) {	
							if ( error ) {
								fileRet = iconFile;
							} else {
								fileRet = ret;
							}
							var html = '<li id="' + file.id + '" class="list-item">'
							+  '<div class="preview">'
							+  '<img class="preview-img" src="' + fileRet +'" />'
							+  '</div>'
							+  '<div class="info">'
							+  '<p class="filename">'
							+  file.name
							+  '</p>'
							+  '<div class="progress-wrapper">'
							+  '<div class="progressbar" style="width:0%;'
							+  (file.Status == 'error' ? 'background-color:#f56c6c' : '') + '">'
							+  '</div>'
							+  '<div class="progress-text">'
							+  '<p class="state">等待上传</p>'
							+  '</div>'
							+  '</div>'
							+  '</div>'
							+  '<label class="btn-delete-item delete-label cursor-select" >'
							+  '<i class="delete-icon">X</i>'
							+  '</label>'
							+	'<input type="hidden" name="localFileId" />'
							+	'<input type="hidden" name="localFileServerUrl"/>'
							+	'<input type="hidden" name="localFilePath"/>'
							+	'<input type="hidden" name="localFileSuffix" value="'+file.ext+'"/>'
							+	'<input type="hidden" name="localFileInput" value="'+file.name+'"/>'
							+	'<input type="hidden" name="localFileMd5" value="'+val+'"/>'
							+  '</li>';
							$("#ulList").append(html);
							
							$.post(__file_server + 'upload/checkFileMd5',{
								localFileMd5:val,
								uploaderId: '${uploadUserId}',
								uploaderName: '${uploadUserName}',
								fileType:file.ext,
								fileName: file.name,
								busTableName:'${busTableName}',
								attachKey:'${attachKey}',
								SQFS:'${SQFS}',
								SFSQ:'${SFSQ}',
								SFHZD:'${SFHZD}'
							},
							function(response,status,xhr){;
								if(response.success){		
									var $li = $('#' + file.id); 
									$li.find('p.state').text(response.msg);
									$li.find('p.state').css('color', "green"); 
									$percent = $li.find('.progress-wrapper .progressbar');
									$li.find('p.state').text('上传完成(100%)');
									$percent.css('width', '100%');
									recordUploadResult(response.data, file.id);
									uploader.removeFile(file);//移除文件
								}
							});
							$(".btn-delete-item").off("click").on("click", function () {
								  var id = $(this).parent().attr('id');
								  uploader.removeFile(id,true);
								  $(this).parent().remove();
							});
						});
						
					});

    	    		
    	    	});
    	    	var fileNum = 0;
				var fileId ="";
    	    	//某个文件开始上传前触发，一个文件只会触发一次
    	    	uploader.on('uploadStart', function(file){
					var id = WebUploader.Base.guid(file.id);
					if(null==fileId || ''==fileId){
						fileId = id;
						uploader.options.formData.uid = fileId;
					} else if(fileId!=id){						
						fileId = id;
						uploader.options.formData.uid = fileId;
					}else if(fileId==id){						
						fileId = WebUploader.Base.guid(file.id)+fileNum;
						uploader.options.formData.uid = fileId;
					}
					var $li = $('#' + file.id); 
					var localFileMd5 = $li.find("[name$='localFileMd5']").val();
					uploader.options.formData.localFileMd5 = localFileMd5;
					fileNum++;
    	    	});
    	    	//上传过程中触发，携带上传进度
    	    	uploader.on('uploadProgress', function(file, percentage){
    	    		var progress = percentage*100;
					 var $li = $('#' + file.id),
						 $percent = $li.find('.progress-wrapper .progressbar');
					 $li.find('p.state').text('上传中('+progress.toFixed(2) + '%)');
					 $percent.css('width', progress.toFixed(2) + '%');
    	    		
    	    	});
    	    	//当文件上传成功时触发
    	    	uploader.on('uploadSuccess', function(file, response){
					var $li = $('#' + file.id); 
    	    		if(response.success && response.data){
						$li.find('p.state').text(response.msg);
						$li.find('p.state').css('color', "green");
    	    			recordUploadResult(response.data, file.id);
    	    		}else{ 
    	    			recordUploadResult({
        	    			fileId: '',
        	    			filePath: '',
        	    			fileServerUrl: '',
        	    			fileName: '',
        	    			fileSuffix: ''
        	    		}, file.id);
						$li.find('p.state').text("上传失败");
						$li.find('p.state').css('color', "red");
    	    		}
    	    	});
    	    	//当所有文件上传结束时触发。
    	    	uploader.on('uploadFinished', function(file, response){
					var fileArray = [];
					$("#ulList li").each(function(i){						
						var fileId = $(this).find("[name$='localFileId']").val();
						var filePath = $(this).find("[name$='localFilePath']").val();
						var fileServerUrl = $(this).find("[name$='localFileServerUrl']").val();
						var fileName = $(this).find("[name$='localFileInput']").val();
						var fileSuffix = $(this).find("[name$='localFileSuffix']").val();
						if(null!=fileId&&fileId!=""){
							var file = {};
							file.fileId = fileId;
							file.filePath = filePath;
							file.fileServerUrl = fileServerUrl;
							file.fileName = fileName;
							file.fileSuffix = fileSuffix;
							fileArray.push(file);		
						}
					});	
					art.dialog.data("uploadedFileInfo", fileArray);
            		AppUtil.closeLayer();
    	    	});
    	    	//文件上传错误时触发
    	    	uploader.on('uploadError', function(file, reason){
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
        	
        	
        	/**
        	 * 赋值input
        	 */
        	function recordUploadResult(file, id){
				if(file.fileId != undefined){
					$("#"+id+" [name='localFileId']").val(file.fileId);
				}
				if(file.fileServerUrl != undefined){
					$("#"+id+" [name='localFileServerUrl']").val(file.fileServerUrl);
				}
				if(file.filePath != undefined){
					$("#"+id+" [name='localFilePath']").val(file.filePath);
				}
				if(file.fileName != undefined){
					$("#"+id+" [name='localFileInput']").val(file.fileName);
				}
				if(file.fileSuffix != undefined){
					$("#"+id+" [name='localFileSuffix']").val(file.fileSuffix);
				}
        	}
        	
        	initWebuploader();
        	
        	/**
        	 * 对话框确定按钮点击事件
        	 */
        	$("#dialogConfirmBtn").click(function(){
        		uploader.upload();
        	});
        });
    </script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
</style>
</head>
<body style="margin:0px;background-color: #fff;">
    <form id="FileUploadForm">
		<div region="center" style="min-height:390px;">
			<!--==========隐藏域部分开始 ===========-->
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;" colspan="2">注意事项</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="padding:0 25px;color:red;">
										文件类型限制：${requestScope.acceptFileType}；</br>
										文件数量限制：10；文件大小限制：${requestScope.formatFileSizeLimit}。
						</div>
					</td>
				</tr>
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;" colspan="2">文件上传</td>
				</tr>
				<tr>
					<td style="width:85%;text-align:right;">
						
						<div id="uploader">
							<div class="easy-uploader">
								<ul class="list" id="ulList">
									
								</ul>
							</div>
						</div>
					</td>
					<td style="width:15%;">
						<!-- 本地文件结果隐藏域 -->
						<div id="localFilePicker" style="float:left;margin:3px 0 3px 5px;line-height:12px;"></div>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input id="dialogConfirmBtn" value="确定" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight"/>
				<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
    </form>
</body>