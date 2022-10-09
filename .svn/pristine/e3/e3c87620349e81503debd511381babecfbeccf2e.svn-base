<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addFlowTypeTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "";
		if(treeNode.id=="0"){
			operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		}
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showFlowTypeWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showFlowTypeWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "flowFormTypeTree",
				url : "flowTypeController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeFlowTypeTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onFlowTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType,
			isCopy) {
		if (moveType != null) {
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"moveType" : moveType,
					"targetNodeId" : targetNode.id,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "JBPM6_FLOWTYPE"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("flowFormTypeTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("flowFormTypeTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onFlowTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if(event.target.tagName=="SPAN"){
			if(treeNode.id=="0"){
				$("#flowFormToolbar input[name='TYPE_ID']").val("");
				$("#flowFormToolbar input[name='Q_P.PATH_LIKE']").val("");
			}else{
				$("#flowFormToolbar input[name='TYPE_ID']").val(treeNode.id);
				$("#flowFormToolbar input[name='Q_P.PATH_LIKE']").val(treeNode.PATH);
			}
			
			AppUtil.gridDoSearch("flowFormToolbar","flowFormGrid");
		}
	}

	/**
	 * 弹出流程类别信息窗口
	 */
	function showFlowTypeWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "flowTypeController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name;
		} else {
			var parentNode = treeNode.getParentNode();
			url = "flowTypeController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id;
		}
		$.dialog.open(url, {
			title : "流程类别信息",
			width : "400px",
			height : "200px",
			lock : true,
			resize : false
		}, false);

	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		var TYPE_ID = $("#flowFormToolbar input[name='TYPE_ID']").val();
		var PATH = $("#flowFormToolbar input[name='Q_P.PATH_LIKE']").val();
		AppUtil.gridSearchReset("FlowFormForm");
		$("#flowFormToolbar input[name='TYPE_ID']").val(TYPE_ID);
		$("#flowFormToolbar input[name='Q_P.PATH_LIKE']").val(PATH);
	}

	/**
	 * 删除流程表单列表记录
	 */
	function deleteFlowFormDataGridRecord() {
		AppUtil.deleteDataGridRecord("flowFormController.do?multiDel",
				"flowFormGrid");
	};
	/**
	 * 编辑流程表单列表记录
	 */
	function editflowFormGridRecord() {
		var FORM_ID = AppUtil.getEditDataGridRecord("flowFormGrid");
		if (FORM_ID) {
			showFlowFormWindow(FORM_ID);
		}
	}
	
	function formatFormType(val,row){
		if(val=="1"){
			return "链接类型";
		}else{
			return "代码类型";
		}
	}

	/**
	 * 显示流程表单信息对话框
	 */
	function showFlowFormWindow(entityId) {
		if (entityId) {
			$.dialog.open("flowFormController.do?info&FORM_ID=" + entityId, {
				title : "流程表单信息",
				width : "650px",
				height : "350px",
				lock : true,
				resize : false
			}, false);

		} else {
			var TYPE_ID = $("#flowFormToolbar input[name='TYPE_ID']").val();
			if (TYPE_ID && TYPE_ID != "0") {
				var treeObj = $.fn.zTree.getZTreeObj("flowFormTypeTree");
				var treeNode = treeObj.getNodesByParam("id", TYPE_ID, null)[0];
				$.dialog.open("flowFormController.do?info&TYPE_ID="
						+ treeNode.id + "&TYPE_NAME=" + treeNode.name, {
					title : "流程表单信息",
					width : "650px",
					height : "350px",
					lock : true,
					resize : false
				}, false);
			} else {
				art.dialog({
					content : "请先选择流程类别!",
					icon : "warning",
					ok : true
				});
			}
		}

	};
	
	function previewflowFormGridRecord() {
		var FORM_ID = AppUtil.getEditDataGridRecord("flowFormGrid");
		if (FORM_ID) {
			var rowsData = $("#flowFormGrid").datagrid("getChecked")[0];
			var FORM_ID = rowsData.FORM_ID;
			window.open("flowFormController.do?preview&formId="+FORM_ID, "_blank");
		}
	}
	
	function queryFieldGridRecord() {
		var FORM_ID = AppUtil.getEditDataGridRecord("flowFormGrid");
		if (FORM_ID) {
			$.dialog.open("formFieldController.do?info&formId=" + FORM_ID, {
				title : "表单字段明细",
				width : "500px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	}

	$(document).ready(function() {
		var flowFormTypeTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addFlowTypeTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeFlowTypeTreeHoverDom
			},
			callback : {
				onClick : onFlowTypeTreeClick,
				onDrop : onFlowTypeTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "JBPM6_FLOWTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "流程类别树"
				}
			}
		};
		$.fn.zTree.init($("#flowFormTypeTree"), flowFormTypeTreeSetting);
		//AppUtil.initAuthorityRes("flowFormToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;流程类别树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="flowFormTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="flowFormToolbar">
			<!--====================开始编写隐藏域============== -->
			 <input type="hidden" name="TYPE_ID"  />
		     <input type="hidden" name="Q_P.PATH_LIKE"  />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_FlowForm"
								iconCls="eicon-plus" plain="true"
								onclick="showFlowFormWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" resKey="EDIT_FlowForm"
								iconCls="eicon-pencil" plain="true"
								onclick="editflowFormGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" resKey="DEL_FlowForm"
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteFlowFormDataGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" resKey="PREVIEW_FlowForm"
								iconCls="eicon-eye" plain="true"
								onclick="previewflowFormGridRecord();">预览</a>
							<a href="#"
								class="easyui-linkbutton" resKey="QUERY_FlowForm"
								iconCls="eicon-columns" plain="true"
								onclick="queryFieldGridRecord();">查看表单字段</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="FlowFormForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">表单名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.FORM_NAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">表单KEY</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.FORM_KEY_LIKE" /></td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('flowFormToolbar','flowFormGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('FlowFormForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="flowFormGrid" fitColumns="true" toolbar="#flowFormToolbar"
			method="post" idField="FORM_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true" fit="true" border="false"
			url="flowFormController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
    
					<th data-options="field:'FORM_ID',hidden:true">FORM_ID</th>
					<th data-options="field:'FORM_URL',hidden:true">FORM_ID</th>
					<th data-options="field:'FORM_NAME',align:'left'" width="30%">表单名称</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="10%">类别名称</th>
					<th data-options="field:'FORM_KEY',align:'left'" width="26%">表单KEY</th>
					<th data-options="field:'FORM_TYPE',align:'left'" width="11%" formatter="formatFormType">表单的类型</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="17%">创建时间</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



