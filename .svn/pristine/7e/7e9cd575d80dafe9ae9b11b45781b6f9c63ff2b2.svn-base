<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteReceiptTemplatesGridRecord() {
		AppUtil.deleteDataGridRecord("receiptTemplatesController.do?multiDel",
				"ReceiptTemplatesGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editReceiptTemplatesGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ReceiptTemplatesGrid");
		if (entityId) {
			showReceiptTemplatesWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showReceiptTemplatesWindow(entityId) {
		$.dialog.open("receiptTemplatesController.do?info&entityId=" + entityId, {
			title : "服务事项信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("ReceiptTemplatesToolbar");

	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ReceiptTemplatesToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_ReceiptTemplates"
								iconcls="icon-note-add" plain="true"
								onclick="showReceiptTemplatesWindow();">新增单据模板</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_ReceiptTemplates"
								iconcls="icon-note-edit" plain="true"
								onclick="editReceiptTemplatesGridRecord();">编辑单据模版</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_ReceiptTemplates"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteReceiptTemplatesGridRecord();">删除单据模板</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ReceiptTemplatesForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('ReceiptTemplatesToolbar','ReceiptTemplatesGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ReceiptTemplatesForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ReceiptTemplatesGrid" fitcolumns="true" toolbar="#ReceiptTemplatesToolbar"
			method="post" idfield="ITEM_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="receiptTemplatesController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
					<th data-options="field:'FWSXZT',align:'left'" width="100">事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="300">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="100">事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="100">办件类型</th>
					<th data-options="field:'FWDX',align:'left'" width="100">服务对象</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="100">承诺时限工作日</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
