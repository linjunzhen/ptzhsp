<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    function showVideoFileWindow(entityId){
    	if(entityId){
    		$.dialog.open("videoFileController.do?info&entityId="+entityId, {
        		title: "多媒体中心视频类编辑",
                width: "500px",
                lock: true,
                resize: false,
                height: "320px"
        	});
    	}else{
    		var typeId = $("#VideoFileViewToolbar input[name='TYPE_ID']").val();
    		if(typeId && typeId != "0"){
    			var treeObj = $.fn.zTree.getZTreeObj("videoFileTypeTree");
    			var treeNode = treeObj.getNodesByParam("id", typeId, null)[0];
    			$.dialog.open("videoFileController.do?info&TYPE_ID="+treeNode.id+"&TYPE_NAME="+treeNode.name, {
            		title: "多媒体中心视频类新增",
                    width: "500px",
                    lock: true,
                    resize: false,
                    height: "320px"
            	});
    		}else{
    			art.dialog({
    				content: "请选择多媒体类别~",
    				icon: "warning",
    			    ok: true
    			});
    		}
    	}
    }
    
    function editVideoFileGridRecord(){
    	var entityId = AppUtil.getEditDataGridRecord("VideoFileViewGrid");
    	if(entityId){
    		showVideoFileWindow(entityId);
    	}
    }
    
    function deleteVideoFileGridRecord(){
    	AppUtil.deleteDataGridRecord("videoFileController.do?multiDel", "VideoFileViewGrid");
    }
    
    function moveVideoFileGridRecord(){
    	var $dataGrid = $("#VideoFileViewGrid");
    	var rowsData = $dataGrid.datagrid('getChecked');
    	if(!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)){
    		art.dialog({
    			content: "请选择需要被移动的多媒体资源记录~",
    			icon: "warning",
    			ok: true
    		});
    	}else{
    		var colName = $dataGrid.datagrid('options').idField;  
    		var selectColNames = "";
			for(var i = 0; i < rowsData.length; i++){
				if(i > 0){
					selectColNames += ",";
				}
				selectColNames += eval('rowsData[i].'+colName);
			}
			$.dialog.open("videoFileController.do?move&selectColNames=" + selectColNames, {
				title: "多媒体资源移动",
				width: "450px",
				height: "180px",
				lock: true,
				resize: false
			});
    	}
    }
    
    function showMp4TranscodeConfWindow(){
    	$.dialog.open("transcodeController.do?confInfo&mediaType=Mp4", {
    		title: "mp4视频转码配置",
            width: "640px",
            lock: true,
            resize: false,
            height: "300px"
    	});
    }
    
    function showMp4TranscodeWindow(){
    	var selVideoId = AppUtil.getEditDataGridRecord('VideoFileViewGrid');
    	if(selVideoId){
    		$.dialog.open("transcodeController.do?transcodeInfo&mediaType=Mp4&videoId="+selVideoId, {
        		title: "mp4视频转码",
                width: "640px",
                lock: true,
                resize: false,
                height: "300px"
        	});
    	}
    }
    
    function formatVideoTranscodeStatus(val, row){
    	if(val == 0){
			if (row.VIDEO_SUFFIX == "mp4") {
				return "<font color='orange'><b>无须转码</b></font>";
			} else {
    			return "<font color='blue'><b>待转码</b></font>";
			}
    	}else if(val == 1){
    		return "<font color='green'><b>转码成功</b></font>";
    	}else if(val == 2){
    		return "<font color='red'><b>转码失败</b></font>";
    	}
    }
    
    function formatVideoFileOpr(val, row){
    	var playATag = "<a title='播放' href='javascript:void(0);' onclick='openVideoPlayDialog(\""+val+"\");'>播放</a>";
    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=video';
    	var downloadATag = "<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
    	return playATag + '&nbsp;&nbsp;' + downloadATag;
    }
    
    function openVideoPlayDialog(videoId){
    	$.dialog.open("videoFileController.do?ckplayer&videoId="+videoId, {
    		title: "多媒体中心-视频播放",
            width: "660px",
            lock: true,
            resize: false,
            height: "500px"
    	});
    }
    
    /**
     * 添加树形hover工具按钮
     */
    function addVideoTypeTreeHoverDom(treeId, treeNode){
    	if(treeNode && treeNode.id == "1") return;
    	var aObj = $("#" + treeNode.tId + "_a");
    	if($("#addItemHref_" + treeNode.id).length > 0) return;
    	if($("#editItemHref_" + treeNode.id).length > 0) return;
    	if($("#delItemHref_" + treeNode.id).length > 0) return;
    	var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png'/></a>";
    	if(treeNode.id != "0"){
    		operateStr += "<a id='editItemHref_" + treeNode.id + "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png'/></a>";
    		operateStr += "<a id='delItemHref_" + treeNode.id + "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png'/></a>";
    	}
    	aObj.append(operateStr);
    	$("#addItemHref_" + treeNode.id).bind("click", function(){
    		showVideoTypeWindow(treeNode, true);
    	});
    	$("#editItemHref_" + treeNode.id).bind("click", function(){
    		showVideoTypeWindow(treeNode, false);
    	});
    	$("#delItemHref_" + treeNode.id).bind("click", function(){
    		AppUtil.deleteTreeNode({
    			treeId: "videoFileTypeTree",
    			url: "fileTypeController.do?multiDel",
    			noAllowDeleteIfHasChild: true,
    			treeNode: treeNode,
    			callback: function(){
    				if(parent.$.fn.zTree.getZTreeObj("videoFileTypeTree")){
			        	parent.$.fn.zTree.getZTreeObj("videoFileTypeTree").reAsyncChildNodes(null, "refresh");
			        }
    				if(parent.$.fn.zTree.getZTreeObj("audioFileTypeTree")){
			        	parent.$.fn.zTree.getZTreeObj("audioFileTypeTree").reAsyncChildNodes(null, "refresh");
			        }
			        if(parent.$.fn.zTree.getZTreeObj("attachFileTypeTree")){
			        	parent.$.fn.zTree.getZTreeObj("attachFileTypeTree").reAsyncChildNodes(null, "refresh");
			        }
			        if(parent.$.fn.zTree.getZTreeObj("imageFileTypeTree")){
			        	parent.$.fn.zTree.getZTreeObj("imageFileTypeTree").reAsyncChildNodes(null, "refresh");
			        }
			        if(parent.$.fn.zTree.getZTreeObj("swfFileTypeTree")){
			        	parent.$.fn.zTree.getZTreeObj("swfFileTypeTree").reAsyncChildNodes(null, "refresh");
			        }
    			}
    		});
    	});
    }
    
    /**
     * 移除树形hover工具按钮
     */
    function removeVideoTypeTreeHoverDom(treeId, treeNode){
    	$("#addItemHref_" + treeNode.id).unbind().remove();
    	$("#editItemHref_" + treeNode.id).unbind().remove();
    	$("#delItemHref_" + treeNode.id).unbind().remove();
    }
    
    /**
     * 点击树形节点触发的事件
     */
    function onVideoTypeTreeClick(event, treeId, treeNode, clickFlag){
    	if(event.target.tagName == "SPAN"){
    		if(treeNode.id == "0"){
    			$("#VideoFileViewToolbar input[name='TYPE_ID']").val("");
    			$("#VideoFileViewToolbar input[name='Q_T.TYPE_ID_=']").val("");
    		}else{
    			$("#VideoFileViewToolbar input[name='TYPE_ID']").val(treeNode.id);
    			$("#VideoFileViewToolbar input[name='Q_T.TYPE_ID_=']").val(treeNode.id);
    		}
    		AppUtil.gridDoSearch("VideoFileViewToolbar", "VideoFileViewGrid");
    	}
    }
    
    /**
     * 树形节点拖放排序
     */
    function onVideoTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy){
    	if(moveType != null){
    		AppUtil.ajaxProgress({
    		   url: "baseController.do?updateTreeSn",
    		   params: {
    			   "dragTreeNodeId": treeNodes[0].id,
    			   "dragTreeNodeNewLevel": treeNodes[0].level,
    			   "targetNodeId": targetNode.id,
    			   "moveType": moveType,
    			   "targetNodeLevel": targetNode.level,
    			   "tableName": "T_FILES_TYPE"
    		   },
    		   callback: function(resultJson){
    				if(resultJson.success){
    	    	   	    alert("成功完成拖拽排序!");
    				}else{
    					alert(resultJson.msg);
    					$.fn.zTree.getZTreeObj("videoFileTypeTree").reAsyncChildNodes(null, "refresh");
    				}
    		   	    
    		   }
    		});
    	}
    }
    
    /**
     * 弹出多媒体类别窗口
     */
    function showVideoTypeWindow(treeNode, isAdd){
    	var url = "";
    	if(isAdd){
    		url = "fileTypeController.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
    	}else{
    		var parentNode = treeNode.getParentNode();
    		url = "fileTypeController.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
    	}
    	$.dialog.open(url, {
    		title: "多媒体类别信息",
            width: "350px",
            lock: true,
            resize: false,
            height: "180px"
    	});
    }
    
    $(document).ready(function(){
    	var videoTypeTreeSetting = {
   			edit: {
   			    enable: true,
   			    showRemoveBtn: false,
   				showRenameBtn: false
   			},
   			view: {
   				addHoverDom: addVideoTypeTreeHoverDom,
   				selectedMulti: false,
   				removeHoverDom: removeVideoTypeTreeHoverDom
   			},
   			callback: {
   				onClick: onVideoTypeTreeClick,
   				onDrop: onVideoTypeTreeDrop
   			},
   			async: {
   				enable: true,
   				url: "fileTypeController/tree.do",
   				otherParam: {
   					"tableName": "T_FILES_TYPE",
   					"idAndNameCol": "TYPE_ID,TYPE_NAME",
   					"targetCols": "TREE_PATH,PARENT_ID",
   					"rootParentId": "0",
   					"isShowRoot": "true",
   					"rootName": "多媒体类别树"
   				}
   			}
    	};
    	$.fn.zTree.init($("#videoFileTypeTree"), videoTypeTreeSetting);
    	
    	var uploadStartTimeInput = {
       	    elem: "#VideoFile.CREATE_TIME_BEGIN",
       		format: "YYYY-MM-DD hh:mm:ss",
       		istime: true,
       		choose: function(data){
       			var beginTime = data;
		    	var endTime = $("#VideoFileViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val();
		    	if(beginTime != "" && endTime != ""){
		    		if(beginTime > endTime){
		    			alert("起始时间必须小于等于截止时间，请重新输入！");
		    			$("#VideoFileViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val("");
		    		}
		    	}
       		}	
        };
    	var uploadEndTimeInput = {
       		elem: "#VideoFile.CREATE_TIME_END",
       		format: "YYYY-MM-DD hh:mm:ss",
       		istime: true,
       		choose: function(data){
       			var beginTime = $("#VideoFileViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val();
		    	var endTime = data;
		    	if(beginTime != "" && endTime != ""){
		    		if(beginTime > endTime){
		    			alert("截止时间必须大于等于起始时间，请重新输入！");
		    			$("#VideoFileViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val("");
		    		}
		    	}
       		}
        };
    	laydate(uploadStartTimeInput);
       	laydate(uploadEndTimeInput);
       	AppUtil.initAuthorityRes("VideoFileViewToolbar");
    });
</script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',split:false" style="width:250px;background:#f5f5f5;">
        <div class="right-con">
		    <div class="e-toolbar" style="z-index:1111;top:0px;left:0px;">
			    <div class="e-toolbar-ct">
				    <div class="e-toolbar-overflow">
				        <div style="color:#005588;">
				            <img src="plug-in/easyui-1.4/themes/icons/script.png" 
				                 style="position: relative;top:7px;float:left;"/>&nbsp;多媒体类别树
				        </div>
					</div>
				</div>
			</div>
		</div>
		<ul id="videoFileTypeTree" class="ztree"></ul>
    </div>
    <div data-options="region:'center',split:false">
        <div id="VideoFileViewToolbar">
            <!--====================开始编写隐藏域============== -->
            <input type="hidden" name="Q_T.TYPE_ID_="/>
		    <input type="hidden" name="TYPE_ID"/>
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
			    <div class="e-toolbar" style="z-index:1111;top:0px;left:0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <a href="#" class="easyui-linkbutton" reskey="ADD_VideoFile"
							   iconcls="icon-note-add" plain="true" onclick="showVideoFileWindow();">新建</a>
						    <a href="#" class="easyui-linkbutton" reskey="EDIT_VideoFile"
							   iconcls="icon-note-edit" plain="true" onclick="editVideoFileGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_VideoFile"
							   iconcls="icon-note-delete" plain="true" onclick="deleteVideoFileGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" reskey="MOVE_VideoFile"
							   iconcls="icon-cut" plain="true" onclick="moveVideoFileGridRecord();">移动</a>
							<a href="#" class="easyui-linkbutton" reskey="TRANSCODE_MP4"
							   iconcls="icon-monitor" plain="true" onclick="showMp4TranscodeWindow();">转码</a>
						    <a href="#" class="easyui-linkbutton" style="float:right;" reskey="TRANSCODECONF_MP4"
							   iconcls="icon-cog" plain="true" onclick="showMp4TranscodeConfWindow();">转码配置</a>
						</div>
					</div>
				</div>
			</div>
			<form name="VideoFileViewForm" action="#">
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
			        <tbody>
			            <tr style="height:28px;">
			                <td style="width:70px;text-align:right;">视频名</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" maxlength="32" 
			                           name="Q_T.VIDEO_NAME_LIKE" style="width:145px;"/>
			                </td>
			                <td style="width:70px;text-align:right;">上传人</td>
			                <td style="width:140px;" colspan="3">
			                    <input class="eve-input" type="text" maxlength="32" 
			                           name="Q_T.UPLOADER_NAME_LIKE" style="width:145px;"/>
			                </td>
			                
			            </tr>
			            <tr style="height:28px;">
			                <td style="width:70px;text-align:right;">上传时间从</td>
			                <td style="width:140px;">
			                    <input type="text" maxlength="32" class="laydate-icon" 
			                           id="VideoFile.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
			                </td>
			                <td style="width:70px;text-align:right;">至</td>
			                <td style="width:140px;">
			                    <input type="text" maxlength="32" class="laydate-icon" 
			                           id="VideoFile.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
			                </td>
			                <td colspan="2">
			                    <input type="button" value="查询" class="eve-button"
								       onclick="AppUtil.gridDoSearch('VideoFileViewToolbar','VideoFileViewGrid');"/>
								<input type="button" value="重置" class="eve-button"
								       onclick="AppUtil.gridSearchReset('VideoFileViewForm');" />
			                </td>
			            </tr>
			        </tbody>
			    </table>
			</form>
        </div>
        <table class="easyui-datagrid" rownumbers="true" pagination="true" id="VideoFileViewGrid"
               fitcolumns="false" toolbar="#VideoFileViewToolbar" method="post" idfield="VIDEO_ID"
               checkOnSelect="true" selectOnCheck="true" ctrlSelect="true" fit="true"
			   pageSize="15" pageList="[15,30,50]"
               border="false" url="videoFileController.do?datagrid">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'VIDEO_PATH',hidden:'true'">视频路径</th>
                    <th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
                    <th data-options="field:'VIDEO_NAME',align:'left'" width="20%">视频名</th>
                    <!-- <th data-options="field:'VIDEO_PATH',align:'left'" width="20%">视频路径</th> -->
                    <%--<th data-options="field:'VIDEO_SUFFIX',align:'left'" width="7%">视频类型</th>--%>
                    <%--<th data-options="field:'VIDEO_SIZE',align:'left'" width="10%">视频大小</th>--%>
                    <th data-options="field:'UPLOADER_NAME',align:'left'" width="10%">上传人</th>
                    <th data-options="field:'CREATE_TIME',align:'left'" width="15%">上传时间</th>
                    <th data-options="field:'TRANSCODE_STATUS',align:'left'" width="10%" formatter="formatVideoTranscodeStatus">转码状态</th>
                    <th data-options="field:'TRANSCODE_TIME',align:'left'" width="15%">转码时间</th>
                    <th data-options="field:'TRANSCODE_REMARK',align:'left'" width="15%">转码失败备注</th>
                    <th data-options="field:'VIDEO_ID',align:'center'" width="10%" formatter="formatVideoFileOpr">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>