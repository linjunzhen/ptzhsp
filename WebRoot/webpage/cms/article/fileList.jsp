<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,json2,artdialog"></eve:resources>
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">

	/**
	 * 删除调查主题列表记录
	 */
	function deletearticleFileGridRecord() {
		AppUtil.deleteDataGridRecord("contentController.do?multiFileDel",
				"articleFileGrid");
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("articleFileToolbar");
	});
	
	function reloadarticleFileList(){
		$("#articleFileGrid").datagrid("reload");
	}
	
	
	$(document).ready(function() {
		AppUtil.gridDoSearch('articleFileToolbar','articleFileGrid');
		rootPath = $("#rootPath").val();
	});
	
	function openAttachUploadDialog(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attach', {
			title: "上传",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						saveFile(uploadedFileInfo.fileName
						,uploadedFileInfo.filePath
						,"download/fileDownload?attachId=" 
						+ uploadedFileInfo.fileId + "&attachType=attach");
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function openImageUploadDialog(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						saveFile(uploadedFileInfo.fileName
						,uploadedFileInfo.filePath
						,"download/fileDownload?attachId=" 
						+ uploadedFileInfo.fileId + "&attachType=image");
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function saveFile(fileName,fileUrl,filePath){
		$.post("contentController.do?saveFile", {fileName:fileName, 
		fileUrl:fileUrl,filePath:filePath}, function(r) {			
			if (r.success) {
				var ids = $("input[name='attachFileIds']").val();
				if (null != ids && ids.length>0) {
					$("input[name='attachFileIds']").val($("input[name='attachFileIds']").val()+","+r.msg);
				}else{
					$("input[name='attachFileIds']").val(r.msg);
				}
				art.dialog.data("uploadedFileIds", {
					attachFileIds:$("input[name='attachFileIds']").val()
				});
				AppUtil.gridDoSearch('articleFileToolbar','articleFileGrid');
			} else {
				$.messager.alert("提示", r.msg);
			}
		}, "json");
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
        <!-- 隐藏域 -->
        <input type="hidden" id="rootPath" value="<%=path%>" />
	    <div  id="CodeProjectFormDiv">
		
		<div class="easyui-layout" fit="true"  style="width: 100%;height:380px;">
			<div region="center">
				<!-- =========================查询面板开始========================= -->
				<div id="articleFileToolbar">
					<!--====================开始编写隐藏域============== -->
					<input type="hidden" name="attachFileIds" value="${attachFileIds}">
					<!--====================结束编写隐藏域============== -->
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#"
										class="easyui-linkbutton" reskey="ADD_ARTICLEFILE"
										iconcls="icon-note-add" plain="true"
										onclick="openAttachUploadDialog();">上传文件</a>
									<a href="#"
										class="easyui-linkbutton" reskey="ADD_ARTICLEFILE"
										iconcls="icon-note-add" plain="true"
										onclick="openImageUploadDialog();">上传图片</a>  
									<a href="#"
										class="easyui-linkbutton" reskey="DEL_ARTICLEFILE"
										iconcls="icon-note-delete" plain="true"
										onclick="deletearticleFileGridRecord();">删除</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- =========================查询面板结束========================= -->
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="true" pagination="true"
					id="articleFileGrid" fitcolumns="true" toolbar="#articleFileToolbar"
					method="post" idfield="FILE_ID" checkonselect="false" 
					selectoncheck="false" fit="true" border="false" 
					nowrap="false" singleSelect="false" 
					url="contentController.do?fileDatagrid" >
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>	
							<th data-options="field:'FILE_NAME',align:'left'" width="150">文件名称</th>
							<th data-options="field:'FILE_URL',align:'left'" width="200">文件路径</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="80">上传时间</th>
						</tr>
					</thead>
				</table>
				<!-- =========================表格结束==========================-->
			</div>
		</div>
		</div>
		<div class="eve_buttons">
		    <input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
</body>
