<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
    <eve:resources loadres="jquery,easyui,apputil,artdialog,laydate,json2,ztree"></eve:resources>
    <style>
		.layout-split-south{border-top-width:0px !important;}
		.eve_buttons{position: relative !important;}
	</style>
    <script type="text/javascript">
        
        /**
         * 附件类操作列格式化
         */
	    function formatAttachFileOpr(val, row){
	    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=attach';
	    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
	    	return downloadATag;
	    }
	    
	    /**
         * 音频类操作列格式化
         */
	    function formatAudioFileOpr(val, row){
	    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=audio';
	    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
	    	return downloadATag;
	    }
	    
	    /**
         * 图片类操作列格式化
         */
	    function formatImageFileOpr(val, row){
	    	var previewATag = "<a title='查看' target='_blank' href='"+row.FILESERVER_URL+row.IMAGE_PATH+"'>查看</a>";
	    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=image';
	    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
	    	return previewATag + '&nbsp;&nbsp;' + downloadATag;
	    }
	    
	    /**
	     * flash类操作列格式化
	     */
	    function formatSwfFileOpr(val, row){
	    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=flash';
	    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
	    	return downloadATag;
	    }
	    
	    /**
	     * 视频类操作列格式化
	     */
	    function formatVideoFileOpr(val, row){
	    	var playATag = "<a title='播放' href='javascript:void(0);' onclick='openVideoPlayDialog(\""+val+"\");'>播放</a>";
	    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=video';
	    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
	    	return playATag + '&nbsp;&nbsp;' + downloadATag;
	    }
	    
	    function openVideoPlayDialog(videoId){
	    	$.dialog.open('${webRoot}/videoFileController.do?ckplayer&videoId='+videoId, {
	    		title: "多媒体中心-视频播放",
	            width: "660px",
	            lock: true,
	            resize: false,
	            height: "500px"
	    	});
	    }
	    
	    /**
		 * 点击多媒体类型树节点
		 */
	    function onFileTypeTreeClick(event, treeId, treeNode, clickFlag){
			if(event.target.tagName == "SPAN"){
				if(treeNode.id == "0"){
	    			$("#FilesViewToolbar input[name='TYPE_ID']").val("");
	    			$("#FilesViewToolbar input[name='Q_T.TYPE_ID_=']").val("");
	    		}else{
	    			$("#FilesViewToolbar input[name='TYPE_ID']").val(treeNode.id);
	    			$("#FilesViewToolbar input[name='Q_T.TYPE_ID_=']").val(treeNode.id);
	    		}
	    		AppUtil.gridDoSearch("FilesViewToolbar", "FilesViewGrid");
			}
	    }
        
	    $(document).ready(function(){
	    	//允许选择最大数
	    	var allowCount = '${requestScope.allowCount}';
	    	/**
	    	 * 多媒体资源类型树实例化
	    	 */
	    	var fileTypeTreeSetting = {
				edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				view: {
					selectedMulti: false
				},
				callback: {
					onClick: onFileTypeTreeClick
				},
				async: {
					enable: true,
					url: "fileTypeController/tree.do",
					otherParam : {
						"tableName": "T_FILES_TYPE",
	   					"idAndNameCol": "TYPE_ID,TYPE_NAME",
	   					"targetCols": "TREE_PATH,PARENT_ID",
	   					"rootParentId": "0",
	   					"isShowRoot": "true",
	   					"rootName": "多媒体类别树"
					}
				}
			};
			$.fn.zTree.init($("#FileTypeSelectTree"), fileTypeTreeSetting);
			
			/**
			 * 多媒体资源列表双击行事件
			 */
			$("#FilesViewGrid").datagrid({
			    onDblClickRow: function(index, row){
			        var rows = $("#SelectedFilesViewGrid").datagrid("getRows");
					if(allowCount != 0 && (rows.length >= allowCount)){
						alert("最多只能选择" + allowCount + "条记录!");
						return;
					}
					var attachType = '${requestScope.attachType}';
					if(attachType == 'attach'){
						var rowIndex = $("#SelectedFilesViewGrid").datagrid("getRowIndex", row.ATTACHMENT_ID);
						if(rowIndex == -1){
							var newRow = {
							    FILE_ID: row.ATTACHMENT_ID,
							    FILE_NAME: row.ATTACHMENT_NAME,
							    FILE_PATH: row.ATTACHMENT_PATH,
							    FILE_SUFFIX: row.ATTACHMENT_SUFFIX,
							    FILESERVER_URL: row.FILESERVER_URL
							};
							$("#SelectedFilesViewGrid").datagrid("appendRow", newRow);
						}
					}
					if(attachType == 'audio'){
						var rowIndex = $("#SelectedFilesViewGrid").datagrid("getRowIndex", row.AUDIO_ID);
						if(rowIndex == -1){
							var newRow = {
							    FILE_ID: row.AUDIO_ID,
							    FILE_NAME: row.AUDIO_NAME,
							    FILE_PATH: row.AUDIO_PATH,
							    FILE_SUFFIX: row.AUDIO_SUFFIX,
							    FILESERVER_URL: row.FILESERVER_URL
							};
							$("#SelectedFilesViewGrid").datagrid("appendRow", newRow);
						}
					}
					if(attachType == 'image'){
						var rowIndex = $("#SelectedFilesViewGrid").datagrid("getRowIndex", row.IMAGE_ID);
						if(rowIndex == -1){
							var newRow = {
							    FILE_ID: row.IMAGE_ID,
							    FILE_NAME: row.IMAGE_NAME,
							    FILE_PATH: row.IMAGE_PATH,
							    FILE_SUFFIX: row.IMAGE_SUFFIX,
							    FILESERVER_URL: row.FILESERVER_URL
							};
							$("#SelectedFilesViewGrid").datagrid("appendRow", newRow);
						}
					}
					if(attachType == 'flash'){
						var rowIndex = $("#SelectedFilesViewGrid").datagrid("getRowIndex", row.SWF_ID);
						if(rowIndex == -1){
							var newRow = {
							    FILE_ID: row.SWF_ID,
							    FILE_NAME: row.SWF_NAME,
							    FILE_PATH: row.SWF_PATH,
							    FILE_SUFFIX: row.SWF_SUFFIX,
							    FILESERVER_URL: row.FILESERVER_URL
							};
							$("#SelectedFilesViewGrid").datagrid("appendRow", newRow);
						}
					}
					if(attachType == 'video'){
						var rowIndex = $("#SelectedFilesViewGrid").datagrid("getRowIndex", row.VIDEO_ID);
						if(rowIndex == -1){
							var newRow = {
							    FILE_ID: row.VIDEO_ID,
							    FILE_NAME: row.VIDEO_NAME,
							    FILE_PATH: row.VIDEO_PATH,
							    FILE_SUFFIX: row.VIDEO_SUFFIX,
							    FILESERVER_URL: row.FILESERVER_URL
							};
							$("#SelectedFilesViewGrid").datagrid("appendRow", newRow);
						}
					}
				}
			});
			
			/**
			 * 已选多媒体资源列表双击行事件
			 */
			$("#SelectedFilesViewGrid").datagrid({
				onDblClickRow: function(index, row){
					$("#SelectedFilesViewGrid").datagrid("deleteRow", index);
				},
				onLoadSuccess:function() {
					$(this).datagrid('cancelCellTip');
				}
			});
			
			/**
			 * laydate实例化
			 */
			var uploadStartTimeInput = {
	       	    elem: "#FileSelector.CREATE_TIME_BEGIN",
	       		format: "YYYY-MM-DD hh:mm:ss",
	       		istime: true,
	       		choose: function(data){
	       			var beginTime = data;
			    	var endTime = $("#FilesViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val();
			    	if(beginTime != "" && endTime != ""){
			    		if(beginTime > endTime){
			    			alert("起始时间必须小于等于截止时间，请重新输入！");
			    			$("#FilesViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val("");
			    		}
			    	}
	       		}	
	        };
	    	var uploadEndTimeInput = {
	       		elem: "#FileSelector.CREATE_TIME_END",
	       		format: "YYYY-MM-DD hh:mm:ss",
	       		istime: true,
	       		choose: function(data){
	       			var beginTime = $("#FilesViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val();
			    	var endTime = data;
			    	if(beginTime != "" && endTime != ""){
			    		if(beginTime > endTime){
			    			alert("截止时间必须大于等于起始时间，请重新输入！");
			    			$("#FilesViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val("");
			    		}
			    	}
	       		}
	        };
	    	laydate(uploadStartTimeInput);
	       	laydate(uploadEndTimeInput);
	    });
	    
	    //easyui datagrid扩展
	    AppUtil.extendsEasyDataGrid();
	    
	    /**
	     * 对话框确定按钮点击事件
	     */
	    function doDialogConfirm(){
	    	var rows = $("#SelectedFilesViewGrid").datagrid("getRows");
	    	if(rows.length == 0){
	    		alert("请至少选择一条记录!");
	    	}else{
	    		var fileIds = "";
				var fileNames = "";
				var filePaths = "";
				var fileSuffixs = "";
				var fileServerUrls = "";
				for(var i = 0; i < rows.length; i++){
					if(i > 0){
						fileIds += ",";
						fileNames += ",";
						filePaths += ",";
						fileSuffixs += ",";
						fileServerUrls += ",";
					}
					fileIds += rows[i].FILE_ID;
					fileNames += rows[i].FILE_NAME;
					filePaths += rows[i].FILE_PATH;
					fileSuffixs += rows[i].FILE_SUFFIX;
					fileServerUrls += rows[i].FILESERVER_URL;
				}
	    		art.dialog.data("selectedFileInfo", {
	    			fileIds: fileIds,
	    			fileNames: fileNames,
	    			filePaths: filePaths,
	    			fileSuffixs: fileSuffixs,
	    			fileServerUrls: fileServerUrls
				});
	    		AppUtil.closeLayer();
	    	}
	    }
    </script>
</head>
<body style="margin:0px;background-color:#f7f7f7;">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'west',split:false" style="width:180px;background:#f5f5f5;">
			<ul id="FileTypeSelectTree" class="ztree"></ul>
		</div>
		<div data-options="region:'center',split:false">
		    <div id="FilesViewToolbar">
		        <!--====================开始编写隐藏域============== -->
		        <input type="hidden" name="Q_T.TYPE_ID_="/>
		    	<input type="hidden" name="TYPE_ID"/>
		        <!--====================结束编写隐藏域============== -->
		        <div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" />
									&nbsp;多媒体资源列表(双击行选择)
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="FilesViewForm">
				    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				        <c:if test="${requestScope.attachType == 'attach' }">
				            <tr style="height:28px;">
								<td style="width:68px;text-align:right;">附件名</td>
								<td style="width:135px;">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.ATTACHMENT_NAME_LIKE" />
							    </td>
								<td style="width:68px;text-align:right;">上传人</td>
								<td colspan="2">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.UPLOADER_NAME_LIKE" />
							    </td>
							</tr>
							<tr style="height:28px;">
							    <td style="width:68px;text-align:right;">上传时间从</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
							    </td>
							    <td style="width:68px;text-align:right;">至</td>
							    <td>
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
							    </td>
							    <td>
								    <input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('FilesViewToolbar','FilesViewGrid')"/>
									<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('FilesViewForm')"/>
							    </td>
							</tr>
				        </c:if>
				        <c:if test="${requestScope.attachType == 'audio' }">
				            <tr style="height:28px;">
								<td style="width:68px;text-align:right;">音频名</td>
								<td style="width:135px;">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.AUDIO_NAME_LIKE" />
							    </td>
								<td style="width:68px;text-align:right;">上传人</td>
								<td colspan="2">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.UPLOADER_NAME_LIKE" />
							    </td>
							</tr>
							<tr style="height:28px;">
							    <td style="width:68px;text-align:right;">上传时间从</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
							    </td>
							    <td style="width:68px;text-align:right;">至</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
							    </td>
							    <td>
								    <input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('FilesViewToolbar','FilesViewGrid')"/>
									<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('FilesViewForm')"/>
							    </td>
							</tr>
				        </c:if>
				        <c:if test="${requestScope.attachType == 'image' }">
				            <tr style="height:28px;">
								<td style="width:68px;text-align:right;">图片名</td>
								<td style="width:135px;">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.IMAGE_NAME_LIKE" />
							    </td>
								<td style="width:68px;text-align:right;">上传人</td>
								<td colspan="2">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.UPLOADER_NAME_LIKE" />
							    </td>
							</tr>
							<tr style="height:28px;">
							    <td style="width:68px;text-align:right;">上传时间从</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
							    </td>
							    <td style="width:68px;text-align:right;">至</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
							    </td>
							    <td>
								    <input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('FilesViewToolbar','FilesViewGrid')"/>
									<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('FilesViewForm')"/>
							    </td>
							</tr>
				        </c:if>
				        <c:if test="${requestScope.attachType == 'flash' }">
				            <tr style="height:28px;">
								<td style="width:68px;text-align:right;">flash名</td>
								<td style="width:135px;">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.SWF_NAME_LIKE" />
							    </td>
								<td style="width:68px;text-align:right;">上传人</td>
								<td colspan="2">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.UPLOADER_NAME_LIKE" />
								</td>
							</tr>
							<tr style="height:28px;">
							    <td style="width:68px;text-align:right;">上传时间从</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
							    </td>
							    <td style="width:68px;text-align:right;">至</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
							    </td>
							    <td>
								    <input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('FilesViewToolbar','FilesViewGrid')"/>
									<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('FilesViewForm')"/>
							    </td>
							</tr>
				        </c:if>
				        <c:if test="${requestScope.attachType == 'video' }">
				            <tr style="height:28px;">
								<td style="width:68px;text-align:right;">视频名</td>
								<td style="width:135px;">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.VIDEO_NAME_LIKE" />
							    </td>
								<td style="width:68px;text-align:right;">上传人</td>
								<td colspan="2">
								    <input class="eve-input" type="text" maxlength="32" style="width:148px;"
									       name="Q_T.UPLOADER_NAME_LIKE" />
							    </td>
							</tr>
							<tr style="height:28px;">
							    <td style="width:68px;text-align:right;">上传时间从</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
							    </td>
							    <td style="width:68px;text-align:right;">至</td>
							    <td style="width:135px;">
							        <input type="text" maxlength="32" class="laydate-icon" 
			                           id="FileSelector.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
							    </td>
							    <td>
								    <input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('FilesViewToolbar','FilesViewGrid')"/>
									<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('FilesViewForm')"/>
							    </td>
							</tr>
				        </c:if>
				    </table>
				</form>
		    </div>
		    <!-- =========================表格开始==========================-->
		    <c:if test="${requestScope.attachType == 'attach' }">
			    <table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="FilesViewGrid" fitColumns="true" toolbar="#FilesViewToolbar"
					   method="post" idField="ATTACHMENT_ID" checkOnSelect="false"
					   selectOnCheck="false" fit="true" border="false" nowrap="true"
					   url="attachFileController.do?datagrid">
					<thead>
						<tr>
						    <th data-options="field:'ATTACHMENT_SUFFIX',hidden:true">ATTACHMENT_SUFFIX</th>
						    <th data-options="field:'ATTACHMENT_PATH',hidden:'true'">附件路径</th>
                    		<th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
							<th data-options="field:'ATTACHMENT_NAME',align:'left'" width="40%">附件名</th>
							<th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="20%">上传时间</th>
							<th data-options="field:'ATTACHMENT_ID',align:'center'" width="10%" formatter="formatAttachFileOpr">操作</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'audio' }">
			    <table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="FilesViewGrid" fitColumns="true" toolbar="#FilesViewToolbar"
					   method="post" idField="AUDIO_ID" checkOnSelect="false"
					   selectOnCheck="false" fit="true" border="false" nowrap="true"
					   url="audioFileController.do?datagrid">
					<thead>
						<tr>
						    <th data-options="field:'AUDIO_SUFFIX',hidden:true">AUDIO_SUFFIX</th>
						    <th data-options="field:'AUDIO_PATH',hidden:'true'">音频路径</th>
                    		<th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
							<th data-options="field:'AUDIO_NAME',align:'left'" width="40%">音频名</th>
							<th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="20%">上传时间</th>
							<th data-options="field:'AUDIO_ID',align:'center'" width="10%" formatter="formatAudioFileOpr">操作</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'image' }">
			    <table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="FilesViewGrid" fitColumns="true" toolbar="#FilesViewToolbar"
					   method="post" idField="IMAGE_ID" checkOnSelect="false"
					   selectOnCheck="false" fit="true" border="false" nowrap="true"
					   url="imageFileController.do?datagrid">
					<thead>
						<tr>
						    <th data-options="field:'IMAGE_SUFFIX',hidden:true">IMAGE_SUFFIX</th>
						    <th data-options="field:'IMAGE_PATH',hidden:'true'">图片路径</th>
                    		<th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
							<th data-options="field:'IMAGE_NAME',align:'left'" width="40%">图片名</th>
							<th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="20%">上传时间</th>
							<th data-options="field:'IMAGE_ID',align:'center'" width="10%" formatter="formatImageFileOpr">操作</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'flash' }">
			    <table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="FilesViewGrid" fitColumns="true" toolbar="#FilesViewToolbar"
					   method="post" idField="SWF_ID" checkOnSelect="false"
					   selectOnCheck="false" fit="true" border="false" nowrap="true"
					   url="swfFileController.do?datagrid">
					<thead>
						<tr>
						    <th data-options="field:'SWF_SUFFIX',hidden:true">SWF_SUFFIX</th>
						    <th data-options="field:'SWF_PATH',hidden:'true'">flash路径</th>
						    <th data-options="field:'SWF_PATH',hidden:'true'">flash路径</th>
                    		<th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
							<th data-options="field:'SWF_NAME',align:'left'" width="40%">flash名</th>
							<th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="20%">上传时间</th>
							<th data-options="field:'SWF_ID',align:'center'" width="10%" formatter="formatSwfFileOpr">操作</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'video' }">
			    <table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="FilesViewGrid" fitColumns="true" toolbar="#FilesViewToolbar"
					   method="post" idField="VIDEO_ID" checkOnSelect="false"
					   selectOnCheck="false" fit="true" border="false" nowrap="true"
					   url="videoFileController.do?datagrid">
					<thead>
						<tr>
						    <th data-options="field:'VIDEO_SUFFIX',hidden:true">VIDEO_SUFFIX</th>
						    <th data-options="field:'VIDEO_PATH',hidden:'true'">视频路径</th>
                    		<th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
							<th data-options="field:'VIDEO_NAME',align:'left'" width="40%">视频名</th>
							<th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
							<th data-options="field:'CREATE_TIME',align:'left'" width="20%">上传时间</th>
							<th data-options="field:'VIDEO_ID',align:'center'" width="10%" formatter="formatVideoFileOpr">操作</th>
						</tr>
					</thead>
				</table>
			</c:if>
		    <!-- =========================表格开始==========================-->
		</div>
		<div data-options="region:'east',split:false" style="width:275px;">
		    <div id="SelectedFilesViewToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />
									&nbsp;已选多媒体资源列表(双击行取消选择)
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<c:if test="${requestScope.attachType == 'attach' }">
                <table class="easyui-datagrid" rownumbers="true" id="SelectedFilesViewGrid" 
			       fitColumns="true" toolbar="#SelectedFilesViewToolbar" nowrap="true"
				   method="post" idField="FILE_ID" checkOnSelect="false" 
				   url="attachFileController.do?selectedDatagrid&needSelectRowIds=${requestScope.needSelectRowIds}"
				   selectOnCheck="false" fit="true" border="false" >
					<thead>
						<tr>
							<th data-options="field:'FILE_ID',hidden:true">FILE_ID</th>
							<th data-options="field:'FILE_PATH',hidden:true">FILE_PATH</th>
							<th data-options="field:'FILESERVER_URL',hidden:true">FILESERVER_URL</th>
							<th data-options="field:'FILE_SUFFIX',hidden:true">FILE_SUFFIX</th>
							<th data-options="field:'FILE_NAME',align:'left'" width="100">附件名</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'audio' }">
			    <table class="easyui-datagrid" rownumbers="true" id="SelectedFilesViewGrid" 
			       fitColumns="true" toolbar="#SelectedFilesViewToolbar" nowrap="true"
				   method="post" idField="FILE_ID" checkOnSelect="false" 
				   url="audioFileController.do?selectedDatagrid&needSelectRowIds=${requestScope.needSelectRowIds}"
				   selectOnCheck="false" fit="true" border="false" >
					<thead>
						<tr>
							<th data-options="field:'FILE_ID',hidden:true">FILE_ID</th>
							<th data-options="field:'FILE_PATH',hidden:true">FILE_PATH</th>
							<th data-options="field:'FILESERVER_URL',hidden:true">FILESERVER_URL</th>
							<th data-options="field:'FILE_SUFFIX',hidden:true">FILE_SUFFIX</th>
							<th data-options="field:'FILE_NAME',align:'left'" width="100">音频名</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'image' }">
			    <table class="easyui-datagrid" rownumbers="true" id="SelectedFilesViewGrid" 
			       fitColumns="true" toolbar="#SelectedFilesViewToolbar" nowrap="true"
				   method="post" idField="FILE_ID" checkOnSelect="false" 
				   url="imageFileController.do?selectedDatagrid&needSelectRowIds=${requestScope.needSelectRowIds}"
				   selectOnCheck="false" fit="true" border="false" >
					<thead>
						<tr>
							<th data-options="field:'FILE_ID',hidden:true">FILE_ID</th>
							<th data-options="field:'FILE_PATH',hidden:true">FILE_PATH</th>
							<th data-options="field:'FILESERVER_URL',hidden:true">FILESERVER_URL</th>
							<th data-options="field:'FILE_SUFFIX',hidden:true">FILE_SUFFIX</th>
							<th data-options="field:'FILE_NAME',align:'left'" width="100">图片名</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'flash' }">
			    <table class="easyui-datagrid" rownumbers="true" id="SelectedFilesViewGrid" 
			       fitColumns="true" toolbar="#SelectedFilesViewToolbar" nowrap="true"
				   method="post" idField="FILE_ID" checkOnSelect="false" 
				   url="swfFileController.do?selectedDatagrid&needSelectRowIds=${requestScope.needSelectRowIds}"
				   selectOnCheck="false" fit="true" border="false" >
					<thead>
						<tr>
							<th data-options="field:'FILE_ID',hidden:true">FILE_ID</th>
							<th data-options="field:'FILE_PATH',hidden:true">FILE_PATH</th>
							<th data-options="field:'FILESERVER_URL',hidden:true">FILESERVER_URL</th>
							<th data-options="field:'FILE_SUFFIX',hidden:true">FILE_SUFFIX</th>
							<th data-options="field:'FILE_NAME',align:'left'" width="100">flash名</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<c:if test="${requestScope.attachType == 'video' }">
			    <table class="easyui-datagrid" rownumbers="true" id="SelectedFilesViewGrid" 
			       fitColumns="true" toolbar="#SelectedFilesViewToolbar" nowrap="true"
				   method="post" idField="FILE_ID" checkOnSelect="false" 
				   url="videoFileController.do?selectedDatagrid&needSelectRowIds=${requestScope.needSelectRowIds}"
				   selectOnCheck="false" fit="true" border="false" >
					<thead>
						<tr>
							<th data-options="field:'FILE_ID',hidden:true">FILE_ID</th>
							<th data-options="field:'FILE_PATH',hidden:true">FILE_PATH</th>
							<th data-options="field:'FILESERVER_URL',hidden:true">FILESERVER_URL</th>
							<th data-options="field:'FILE_SUFFIX',hidden:true">FILE_SUFFIX</th>
							<th data-options="field:'FILE_NAME',align:'left'" width="100">视频名</th>
						</tr>
					</thead>
				</table>
			</c:if>
			<!-- =========================表格结束==========================-->
		</div>
		<div data-options="region:'south',split:true,border:false">
		    <div class="eve_buttons">
				<input value="确定" type="button" onclick="doDialogConfirm();" class="z-dlg-button z-dialog-okbutton aui_state_highlight"/>
				<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
    </div>
</body>