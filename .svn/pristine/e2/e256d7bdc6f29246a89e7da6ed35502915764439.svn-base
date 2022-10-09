<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addSceneInfoTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showSceneInfoWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showSceneInfoWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "sceneInfoTree",
				url : "sceneInfoController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeSceneInfoTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onSceneInfoTreeDrop(event, treeId, treeNodes, targetNode,
			moveType, isCopy) {
		if (moveType != null) {
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"moveType" : moveType,
					"targetNodeId" : targetNode.id,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "T_WSBS_SCENEINFO"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("sceneInfoTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("sceneInfoTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onSceneInfoTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id=="0"){
				$("#SceneGuideToolbar input[name='SCENE_ID']").val("");
			}else{
				$("#SceneGuideToolbar input[name='SCENE_ID']").val(treeNode.id);
			}
			AppUtil.gridDoSearch("SceneGuideToolbar", "SceneGuideGrid");
		}
	}

	/**
	 * 弹出场景导航信息窗口
	 */
	function showSceneInfoWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "sceneInfoController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name;
		} else {
			var parentNode = treeNode.getParentNode();
			url = "sceneInfoController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id;
		}
		$.dialog.open(url, {
			title : "场景导航信息",
			width : "400px",
			height : "250px",
			lock : true,
			resize : false
		}, false);

	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id=="0"){
				$("#SceneGuideToolbar input[name='TYPE_ID']").val("");
				$("#SceneGuideToolbar input[name='Q_BUS.PATH_LIKE']").val("");
			}else{
				$("#SceneGuideToolbar input[name='TYPE_ID']").val(treeNode.id);
				$("#SceneGuideToolbar input[name='Q_BUS.PATH_LIKE']").val(treeNode.PATH);
			}
			AppUtil.gridDoSearch("SceneGuideToolbar", "SceneGuideGrid");
		}
	}

	/**
	 * 删除办事指南列表记录
	 */
	function removeGuideFromScene() {
		var sceneId = $("#SceneGuideToolbar input[name='SCENE_ID']").val();
		if (sceneId && sceneId != "0") {
			AppUtil.deleteDataGridRecord("sceneInfoController.do?removeGuides&sceneId="+sceneId,
					"SceneGuideGrid");
		} else {
			art.dialog({
				content : "请先选择场景!",
				icon : "warning",
				ok : true
			});
		}
		
	};
	
	/**
	**新增办事指南到场景
	**/
	function saveOrUpdateGuideToScene(guideIds,SCENE_ID) {
		AppUtil.ajaxProgress({
			url : "sceneInfoController.do?grantguides",
			params : {
				"guideIds" :guideIds,
				"sceneId":SCENE_ID
			},
			callback : function(resultJson) {
			    art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
			    AppUtil.gridDoSearch("SceneGuideToolbar", "SceneGuideGrid");
			}
		});
	}

	/**
	 * 显示办事指南信息对话框
	 */
	function addGuideToScene(entityId) {
		var SCENE_ID = $("#SceneGuideToolbar input[name='SCENE_ID']").val();
		if (SCENE_ID && SCENE_ID != "0") {
			$.dialog.open("serviceGuideController.do?selector&allowCount=0", {
				title : "办事项目选择器",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"500px",
	            close: function () {
	    			var selectGuideInfo = art.dialog.data("selectGuideInfo");
	    			if(selectGuideInfo){
	    				saveOrUpdateGuideToScene(selectGuideInfo.guideIds,SCENE_ID);
	    				art.dialog.removeData("selectGuideInfo");
	    			}
	    		}
	    	}, false);
		} else {
			art.dialog({
				content : "请先选择场景!",
				icon : "warning",
				ok : true
			});
		}

	};

	$(document).ready(function() {
		var sceneInfoTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addSceneInfoTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeSceneInfoTreeHoverDom
			},
			callback : {
				onClick : onSceneInfoTreeClick,
				onDrop : onSceneInfoTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_WSBS_SCENEINFO",
					"idAndNameCol" : "SCENE_ID,SCENE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "场景导航树"
				}
			}
		};
		$.fn.zTree.init($("#sceneInfoTree"), sceneInfoTreeSetting);
		
	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;场景导航树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="sceneInfoTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="SceneGuideToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="SCENE_ID" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_ServiceGuide"
								iconCls="icon-note-add" plain="true"
								onclick="addGuideToScene();">加入办事项目</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_ServiceGuide"
								iconCls="icon-note-delete" plain="true"
								onclick="removeGuideFromScene();">移除办事项目</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SceneGuideForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">项目名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.PROJECT_NAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">项目编码</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.PROJECT_CODE_LIKE" /></td>
						<td colspan="6"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('SceneGuideToolbar','SceneGuideGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('SceneGuideForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="SceneGuideGrid" fitColumns="true"
			toolbar="#SceneGuideToolbar" method="post" idField="GUIDE_ID"
			checkOnSelect="false" selectOnCheck="false" fit="true" border="false"
			url="serviceGuideController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'GUIDE_ID',hidden:true" width="80">GUIDE_ID</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="100">项目名称</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="100">所属业务类别</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="100">项目编码</th>
					<th data-options="field:'SERVER_OBJECTS',align:'left'" width="100">服务对象</th>
					<th data-options="field:'HANDLE_DEPT',align:'left'" width="100">办理机构(科室)</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



