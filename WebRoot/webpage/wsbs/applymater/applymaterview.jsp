<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addBusTypeTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr  = "";
	    operateStr += "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showBusTypeWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showBusTypeWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "busTypeTree",
				url : "busTypeController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeBusTypeTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onBusTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType,
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
					"tableName" : "T_WSBS_BUSTYPE"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("busTypeTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("busTypeTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onBusTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#ApplyMaterToolbar input[name='TYPE_ID']").val(treeNode.id);
			AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
		}
	}

	/**
	 * 弹出主题服务信息窗口
	 */
	function showBusTypeWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "busTypeController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + encodeURIComponent(treeNode.name);
		} else {
			var parentNode = treeNode.getParentNode();
			url = "busTypeController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + (parentNode.name) + "&entityId="
					+ treeNode.id;
		}
		$.dialog.open(url, {
			title : "主题服务信息",
			width : "600px",
			height : "300px",
			lock : true,
			resize : false
		}, false);

	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		var TYPE_ID = $("#ApplyMaterToolbar input[name='TYPE_ID']").val();
		AppUtil.gridSearchReset("ApplyMaterForm");
		$("#ApplyMaterToolbar input[name='TYPE_ID']").val(TYPE_ID);
	}

	/**
	 * 删除申请材料列表记录
	 */
	function deleteApplyMaterDataGridRecord() {
		AppUtil.deleteDataGridRecord("applyMaterController.do?multiDel",
				"ApplyMaterGrid");
	};
	/**
	 * 编辑申请材料列表记录
	 */
	function editApplyMaterGridRecord() {
		var MATER_ID = AppUtil.getEditDataGridRecord("ApplyMaterGrid");
		if (MATER_ID) {
			showApplyMaterWindow(MATER_ID);
		}
	}

	/**
	 * 显示申请材料信息对话框
	 */
	function showApplyMaterWindow(entityId) {
		$.dialog.open("applyMaterController.do?info&entityId=" + entityId,
				{
					title : "申请材料信息",
					width : "700px",
					height : "450px",
					lock : true,
					resize : false
				}, false);
	};
	
	function formatReceiveTypes(val,row){
		if(val=="1"){
			return "纸质收取";
		}else if(val=="2"){
			return "上传";
		}
	};
	
	

	$(document).ready(function() {
		var busTypeTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addBusTypeTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeBusTypeTreeHoverDom
			},
			callback : {
				onClick : onBusTypeTreeClick,
				onDrop : onBusTypeTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_WSBS_BUSTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "服务分类树"
				}
			}
		};
		$.fn.zTree.init($("#busTypeTree"), busTypeTreeSetting);
		AppUtil.initAuthorityRes("ApplyMaterToolbar");
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
								style="position: relative; top:7px; float:left;" />&nbsp;服务分类管理
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="busTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="ApplyMaterToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="TYPE_ID">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_ApplyMater"
								iconCls="eicon-plus" plain="true"
								onclick="showApplyMaterWindow();">新建</a> 
							<a href="#" class="easyui-linkbutton" resKey="EDIT_ApplyMater"
								iconCls="eicon-pencil" plain="true"
								onclick="editApplyMaterGridRecord();">编辑</a> 
							<a href="#" class="easyui-linkbutton" resKey="DEL_ApplyMater"
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteApplyMaterDataGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ApplyMaterForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">材料编码</td>
						<td style="width:235px;"><input class="eve-input" type="text"
							maxlength="35" style="width:228px;" name="Q_T.MATER_CODE_LIKE" /></td>
						<td style="width:68px;text-align:right;">材料名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.MATER_NAME_LIKE" /></td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('ApplyMaterToolbar','ApplyMaterGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('ApplyMaterForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ApplyMaterGrid" fitColumns="true" toolbar="#ApplyMaterToolbar"
			method="post" idField="MATER_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true" fit="true" border="false"
			url="applyMaterController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'MATER_ID',hidden:true">MATER_ID</th>
					<th data-options="field:'MATER_CODE',align:'left'" width="20%">材料编码</th>
					<th data-options="field:'MATER_NAME',align:'left'" width="58%">材料名称</th>
					<th data-options="field:'RECEIVE_TYPES',align:'left'" width="15%" formatter="formatReceiveTypes">收取方式</th>
					
				</tr>
			</thead>
		</table>

	</div>
</div>



