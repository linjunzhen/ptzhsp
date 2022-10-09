<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除流程按钮列表记录
	 */
	function deleteFlowButtonGridRecord() {
		AppUtil.deleteDataGridRecord("flowButtonController.do?multiDel",
				"FlowButtonGrid");
	};
	/**
	 * 编辑流程按钮列表记录
	 */
	function editFlowButtonGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FlowButtonGrid");
		if (entityId) {
			showFlowButtonWindow(entityId);
		}
	}

	/**
	 * 显示流程按钮信息对话框
	 */
	function showFlowButtonWindow(entityId) {
		$.dialog.open("flowButtonController.do?info&entityId=" + entityId, {
			title : "流程按钮信息",
			width : "650px",
			height : "200px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowButtonToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FlowButton"
								iconcls="eicon-plus" plain="true"
								onclick="showFlowButtonWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_FlowButton"
								iconcls="eicon-pencil" plain="true"
								onclick="editFlowButtonGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_FlowButton"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteFlowButtonGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="FlowButtonForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">按钮名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUTTON_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">按钮的KEY</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUTTON_KEY_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FlowButtonToolbar','FlowButtonGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('FlowButtonForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FlowButtonGrid" fitcolumns="true" toolbar="#FlowButtonToolbar"
			method="post" idfield="BUTTON_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false"
			url="flowButtonController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'BUTTON_ID',hidden:true">BUTTON_ID</th>
					<th data-options="field:'BUTTON_NAME',align:'left'" width="30%">按钮名称</th>
					<th data-options="field:'BUTTON_ICON',align:'left'" width="15%">按钮图标CSS</th>
					<th data-options="field:'BUTTON_KEY',align:'left'" width="15%">按钮的KEY</th>
					<th data-options="field:'BUTTON_FUN',align:'left'" width="20%">按钮的操作函数</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
