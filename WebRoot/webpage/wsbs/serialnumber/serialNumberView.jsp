<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteSerialNumberGridRecord() {
		AppUtil.deleteDataGridRecord("serialNumberController.do?multiDel",
				"SerialNumberGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editSerialNumberGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("SerialNumberGrid");
		if (entityId) {
			showSerialNumberWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showSerialNumberWindow(entityId) {
		$.dialog.open("serialNumberController.do?info&entityId=" + entityId, {
			title : "编号配置信息",
			width : "750px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("SerialNumberToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="SerialNumberToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SerialNumber"
								iconcls="eicon-plus" plain="true"
								onclick="showSerialNumberWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_SerialNumber"
								iconcls="eicon-pencil" plain="true"
								onclick="editSerialNumberGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_SerialNumber"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteSerialNumberGridRecord();">删除</a>
								<a href="#"
								class="easyui-linkbutton" iconcls="eicon-refresh" plain="true"
								onclick="AppUtil.gridDoSearch('SerialNumberToolbar','SerialNumberGrid');">刷新</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="SerialNumberGrid" fitcolumns="true" toolbar="#SerialNumberToolbar"
			method="post" idfield="SERIAL_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="serialNumberController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SERIAL_ID',hidden:true">SERIAL_ID</th>
					<th data-options="field:'SERIAL_NAME',align:'left'" width="20%">编号配置名称</th>
					<th data-options="field:'SERIAL_TYPENAME',align:'left'" width="15%">编号配置类型</th>
					<th data-options="field:'SERIAL_RULE',align:'left'" width="60%">编号配置规则</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
