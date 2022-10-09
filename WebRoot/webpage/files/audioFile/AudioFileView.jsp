<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 播放音频
	 */
	function openAudioPlayDialog(audioId){
		$.dialog.open("audioFileController.do?playAudio&audioId="+audioId, {
			title: "播放音频",
			lock: true,
			resize: false
		});
	}

    function showAudioFileWindow(entityId){
    	if(entityId){
    		$.dialog.open("audioFileController.do?info&entityId="+entityId, {
        		title: "多媒体中心音频类编辑",
                width: "500px",
                lock: true,
                resize: false,
                height: "320px"
        	});
    	}else{
    		var typeId = $("#AudioFileViewToolbar input[name='TYPE_ID']").val();
    		if(typeId && typeId != "0"){
    			var treeObj = $.fn.zTree.getZTreeObj("audioFileTypeTree");
    			var treeNode = treeObj.getNodesByParam("id", typeId, null)[0];
    			$.dialog.open("audioFileController.do?info&TYPE_ID="+treeNode.id+"&TYPE_NAME="+treeNode.name, {
            		title: "多媒体中心音频类新增",
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
    
    function editAudioFileGridRecord(){
    	var entityId = AppUtil.getEditDataGridRecord("AudioFileViewGrid");
    	if(entityId){
    		showAudioFileWindow(entityId);
    	}
    }
    
    function deleteAudioFileGridRecord(){
    	AppUtil.deleteDataGridRecord("audioFileController.do?multiDel", "AudioFileViewGrid");
    }
    
    function formatAudioFileOpr(val, row){
    	var downloadUrl = row.FILESERVER_URL + 'download/fileDownload?attachId='+val+'&attachType=audio';
    	var downloadATag = "<a href='javascript:void(0)' onclick='openAudioPlayDialog(\""+val+"\")'>播放</a>"+
				"&nbsp;<a title='下载' target='_blank' href='"+downloadUrl+"'>下载</a>";
    	return downloadATag;
    }
    
    function moveAudioFileGridRecord(){
    	var $dataGrid = $("#AudioFileViewGrid");
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
			$.dialog.open("audioFileController.do?move&selectColNames=" + selectColNames, {
				title: "多媒体资源移动",
				width: "450px",
				height: "180px",
				lock: true,
				resize: false
			});
    	}
    }
    
    /**
     * 添加树形hover工具按钮
     */
    function addAudioTypeTreeHoverDom(treeId, treeNode){
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
    		showAudioTypeWindow(treeNode, true);
    	});
    	$("#editItemHref_" + treeNode.id).bind("click", function(){
    		showAudioTypeWindow(treeNode, false);
    	});
    	$("#delItemHref_" + treeNode.id).bind("click", function(){
    		AppUtil.deleteTreeNode({
    			treeId: "audioFileTypeTree",
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
    function removeAudioTypeTreeHoverDom(treeId, treeNode){
    	$("#addItemHref_" + treeNode.id).unbind().remove();
    	$("#editItemHref_" + treeNode.id).unbind().remove();
    	$("#delItemHref_" + treeNode.id).unbind().remove();
    }
    
    /**
     * 点击树形节点触发的事件
     */
    function onAudioTypeTreeClick(event, treeId, treeNode, clickFlag){
    	if(event.target.tagName == "SPAN"){
    		if(treeNode.id == "0"){
    			$("#AudioFileViewToolbar input[name='TYPE_ID']").val("");
    			$("#AudioFileViewToolbar input[name='Q_T.TYPE_ID_=']").val("");
    		}else{
    			$("#AudioFileViewToolbar input[name='TYPE_ID']").val(treeNode.id);
    			$("#AudioFileViewToolbar input[name='Q_T.TYPE_ID_=']").val(treeNode.id);
    		}
    		AppUtil.gridDoSearch("AudioFileViewToolbar", "AudioFileViewGrid");
    	}
    }
    
    /**
     * 弹出多媒体类别窗口
     */
    function showAudioTypeWindow(treeNode, isAdd){
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
    
    /**
     * 树形节点拖放排序
     */
    function onAudioTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy){
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
    					$.fn.zTree.getZTreeObj("audioFileTypeTree").reAsyncChildNodes(null, "refresh");
    				}
    		   	    
    		   }
    		});
    	}
    }
    
    $(document).ready(function(){
    	var audioTypeTreeSetting = {
         	edit: {
         		enable: true,
         		showRemoveBtn: false,
         		showRenameBtn: false
         	},
         	view: {
         		addHoverDom: addAudioTypeTreeHoverDom,
         		selectedMulti: false,
       			removeHoverDom: removeAudioTypeTreeHoverDom
       		},
         	callback: {
         		onClick: onAudioTypeTreeClick,
         		onDrop: onAudioTypeTreeDrop
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
        $.fn.zTree.init($("#audioFileTypeTree"), audioTypeTreeSetting);
    	
    	var uploadStartTimeInput = {
       	    elem: "#AudioFile.CREATE_TIME_BEGIN",
       		format: "YYYY-MM-DD hh:mm:ss",
       		istime: true,
       		choose: function(data){
       			var beginTime = data;
		    	var endTime = $("#AudioFileViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val();
		    	if(beginTime != "" && endTime != ""){
		    		if(beginTime > endTime){
		    			alert("起始时间必须小于等于截止时间，请重新输入！");
		    			$("#AudioFileViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val("");
		    		}
		    	}
       		}	
        };
    	var uploadEndTimeInput = {
       		elem: "#AudioFile.CREATE_TIME_END",
       		format: "YYYY-MM-DD hh:mm:ss",
       		istime: true,
       		choose: function(data){
       			var beginTime = $("#AudioFileViewToolbar").find("input[name='Q_T.CREATE_TIME_>=']").val();
		    	var endTime = data;
		    	if(beginTime != "" && endTime != ""){
		    		if(beginTime > endTime){
		    			alert("截止时间必须大于等于起始时间，请重新输入！");
		    			$("#AudioFileViewToolbar").find("input[name='Q_T.CREATE_TIME_<=']").val("");
		    		}
		    	}
       		}
        };
    	laydate(uploadStartTimeInput);
       	laydate(uploadEndTimeInput);
       	AppUtil.initAuthorityRes("AudioFileViewToolbar");
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
		<ul id="audioFileTypeTree" class="ztree"></ul>
    </div>
    <div data-options="region:'center',split:false">
        <div id="AudioFileViewToolbar">
            <!--====================开始编写隐藏域============== -->
            <input type="hidden" name="Q_T.TYPE_ID_="/>
		    <input type="hidden" name="TYPE_ID"/>
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
			    <div class="e-toolbar" style="z-index:1111;top:0px;left:0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <a href="#" class="easyui-linkbutton" reskey="ADD_AudioFile"
							   iconcls="icon-note-add" plain="true" onclick="showAudioFileWindow();">新建</a>
						    <a href="#" class="easyui-linkbutton" reskey="EDIT_AudioFile"
							   iconcls="icon-note-edit" plain="true" onclick="editAudioFileGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_AudioFile"
							   iconcls="icon-note-delete" plain="true" onclick="deleteAudioFileGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" reskey="MOVE_AudioFile"
							   iconcls="icon-cut" plain="true" onclick="moveAudioFileGridRecord();">移动</a>
						</div>
					</div>
				</div>
			</div>
			<form name="AudioFileViewForm" action="#">
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
			        <tbody>
			            <tr style="height:28px;">
			                <td style="width:70px;text-align:right;">音频名</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" maxlength="32" 
			                           name="Q_T.AUDIO_NAME_LIKE" style="width:145px;"/>
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
			                           id="AudioFile.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" style="width:128px;"/>
			                </td>
			                <td style="width:70px;text-align:right;">至</td>
			                <td style="width:140px;">
			                    <input type="text" maxlength="32" class="laydate-icon" 
			                           id="AudioFile.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" style="width:128px;"/>
			                </td>
			                <td colspan="2">
			                    <input type="button" value="查询" class="eve-button"
								       onclick="AppUtil.gridDoSearch('AudioFileViewToolbar','AudioFileViewGrid');"/>
								<input type="button" value="重置" class="eve-button"
								       onclick="AppUtil.gridSearchReset('AudioFileViewForm');" />
			                </td>
			            </tr>
			        </tbody>
			    </table>
			</form>
        </div>
        <table class="easyui-datagrid" rownumbers="true" pagination="true" id="AudioFileViewGrid"
               fitcolumns="false" toolbar="#AudioFileViewToolbar" method="post" idfield="AUDIO_ID"
               checkOnSelect="true" selectOnCheck="true" ctrlSelect="true" fit="true"
			   pageSize="15" pageList="[15,30,50]"
               border="false" url="audioFileController.do?datagrid">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'AUDIO_PATH',hidden:'true'">音频路径</th>
                    <th data-options="field:'FILESERVER_URL',hidden:'true'">文件服务器url</th>
                    <th data-options="field:'AUDIO_NAME',align:'left'" width="25%">音频名</th>
                    <!-- <th data-options="field:'AUDIO_PATH',align:'left'" width="25%">音频路径</th> -->
                    <th data-options="field:'AUDIO_SUFFIX',align:'left'" width="15%">音频类型</th>
                    <th data-options="field:'AUDIO_SIZE',align:'left'" width="15%">音频大小</th>
                    <th data-options="field:'UPLOADER_NAME',align:'left'" width="15%">上传人</th>
                    <th data-options="field:'CREATE_TIME',align:'left'" width="15%">上传时间</th>
                    <th data-options="field:'AUDIO_ID',align:'center'" width="10%" formatter="formatAudioFileOpr">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>