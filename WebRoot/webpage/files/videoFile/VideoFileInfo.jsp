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
    <eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine"></eve:resources>
    <link rel="stylesheet" type="text/css" href="plug-in/webuploader/webuploader.css"/>
    <script type="text/javascript" src="plug-in/webuploader/webuploader.min.js"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	//兼容性判断
	    	if(!WebUploader.Uploader.support()){
	            alert('Web Uploader不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器~');
	            throw new Error('WebUploader does not support the browser you are using.');
	        }
	    	//初始化进度条
	    	$('#videoFileProcessBar').progressbar({value: 0});
	    	//上传遮罩
    		var shadeLayer = parent.art.dialog({content: '已完成文件数据上传，正在进行视频文件转码...'}).hide();
	    	//加入WebUploader上传队列的文件
	    	var fileInQueque = null;
	    	//初始化WebUploader
	    	var uploader = WebUploader.create({
	    	    swf: 'plug-in/webuploader/Uploader.swf',
	    	    server: __file_server + '/upload/fileUpload',
	    	    pick: '#videoFilePickBtn',
	    	    timeout: 0,
	    	    accept: {
	    	        title: 'VideoFiles',
	    	        extensions: '${requestScope.acceptFileType}',
	    	        mimeTypes: '*/*'
	    	    },
	    	    formData: {
	                uploaderId: '${sessionScope.curLoginUser.userId}',
	                uploaderName: '${sessionScope.curLoginUser.fullname}',
	                uploadToken: '<%=uploadToken%>',
	                isAdmin: 1, //0前台用户1后台
	                typeId: '${videoFile.TYPE_ID}'
	            },
	    	    chunked: true,
	    	    threads: 3,
	    	    fileSingleSizeLimit: '${requestScope.fileSizeLimit}'
	    	});
	    	//文件添加进队列触发
	    	uploader.on('fileQueued', function(file){
	    		if(fileInQueque){
	    			uploader.removeFile(fileInQueque);
	    			fileInQueque = file;
	    		}else{
	    			fileInQueque = file;
	    		}
	    		$('#videoFileProcessBar').progressbar({value: 0});
	    		$("#VideoFileForm").find("input[name='VIDEO_NAME']").val(file.name);
	    		$("#VideoFileForm").find("input[name='VIDEO_SUFFIX']").val(file.ext);
	    		//格式化文件大小
	    		var fileSize = WebUploader.Base.formatSize(file.size);
	    		$("#VideoFileForm").find("input[name='VIDEO_SIZE']").val(fileSize);
	    	});
	    	//某个文件开始上传前触发，一个文件只会触发一次
	    	uploader.on('uploadStart', function(file){
	    		$('#videoFilePickBtn').hide();
				$("input[type='submit']").attr("disabled", "disabled");
				//编辑
	    		if('${videoFile.VIDEO_ID}'){
	    			uploader.options.formData.uid = '${videoFile.VIDEO_ID}';
	    		//新增
	    		}else{
	    			uploader.options.formData.uid = WebUploader.Base.guid("");
	    		}
	    	});
	    	//上传过程中触发，携带上传进度
	    	uploader.on('uploadProgress', function(file, percentage){
	    		var progress = percentage*100;
	    		if(progress < 100){
	    			progress = progress.toFixed(2);
	    			$('#videoFileProcessBar').progressbar({value: progress});
	    		}else if(progress == 100){
	    			$('#videoFileProcessBar').progressbar({value: progress});
	    			shadeLayer.show().lock();
	    		}
	    	});
	    	//当文件上传成功时触发
	    	uploader.on('uploadSuccess', function(file, response){
	    		shadeLayer.close();
	    		if(response.success){
	    			parent.art.dialog({
						content: response.msg,
						icon: "succeed",
					    ok: true
					});
	    			parent.$("#VideoFileViewGrid").datagrid("reload");
					AppUtil.closeLayer();
	    		}else{
	    			$('#videoFileProcessBar').progressbar({value: 0});
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
	    		$('#videoFileProcessBar').progressbar({value: 0});
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
            
	    	AppUtil.initWindowForm("VideoFileForm", function(form, valid){
        		if(valid){
        			if(fileInQueque){
        				uploader.upload();
        			}else{
        				parent.art.dialog({
    						content: '请选择待上传的文件~',
    						icon: "warning",
    					    ok: true
    					});
        			}
        		}else{
        			parent.art.dialog({
						content: '请选择待上传的文件~',
						icon: "warning",
					    ok: true
					});
        		}
	    	});
	    });
    </script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
    <form id="VideoFileForm" method="post">
        <!--==========隐藏域部分开始 ===========-->
        <input type="hidden" name="TYPE_ID" value="${videoFile.TYPE_ID}"/>
        <!--==========隐藏域部分结束 ===========-->
        <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
				<td class="dddl-legend" style="font-weight:bold;">注意事项</td>
			</tr>
			<tr>
			    <td>
			        <div style="padding:0 25px;color:red;">
			                        文件类型限制：${requestScope.acceptFileType}；文件大小限制：${requestScope.formatFileSizeLimit}。
			        </div>
			    </td>
			</tr>
            <tr style="height:29px;">
				<td class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
			    <td>
			        <span style="width:110px;float:left;text-align:right;">所属类别：</span>
					${videoFile.TYPE_NAME}
			    </td>
			</tr>
			<tr style="line-height:9px;">
			    <td>
			        <span style="width:110px;text-align:right;float:left;line-height:28px;">
			            <font class="dddl_platform_html_requiredFlag">*</font>视频名：
			        </span>
			        <input class="eve-input validate[required]" type="text" name="VIDEO_NAME" style="width:240px;float:left;" value="${videoFile.VIDEO_NAME}" readonly/>
			        <div id="videoFilePickBtn" style="float:left;margin-left:3px;">选择</div>
			    </td>
			</tr>
			<tr>
			    <td>
			        <span style="width:110px;text-align:right;float:left;line-height:28px;">
			            <font class="dddl_platform_html_requiredFlag">*</font>视频类型：
			        </span>
			        <input class="eve-input validate[required]" type="text" name="VIDEO_SUFFIX" style="width:240px;float:left;" value="${videoFile.VIDEO_SUFFIX}" readonly/>
			    </td>
			</tr>
			<tr>
			    <td>
			        <span style="width:110px;text-align:right;float:left;line-height:28px;">
			            <font class="dddl_platform_html_requiredFlag">*</font>视频大小：
			        </span>
			        <input class="eve-input validate[required]" type="text" name="VIDEO_SIZE" style="width:240px;float:left;" value="${videoFile.VIDEO_SIZE}" readonly/>
			    </td>
			</tr>
			<tr>
			    <td>
			        <span style="width:110px;text-align:right;float:left;">上传进度：</span>
			        <div id="videoFileProcessBar" style="width:240px;"></div>
			    </td>
			</tr>
        </table>
        <div class="eve_buttons">
			<input value="上传" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();" />
		</div>
    </form>
</body>