<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deletedepartBusGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelDepartBus",
				"departBusGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editdepartBusGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("departBusGrid");
		if (entityId) {
			showdepartBusGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showdepartBusGridRecord(entityId) {
		$.dialog.open("callController.do?departBusInfo&entityId=" + entityId, {
			title : "部门业务绑定信息",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false
		}, false);
	};	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="departBusToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showdepartBusGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="icon-note-edit" plain="true"
								onclick="editdepartBusGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-note-delete" plain="true"
								onclick="deletedepartBusGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="departBusGrid" fitcolumns="false" nowrap="false"
			toolbar="#departBusToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?departBusDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="250">部门名称</th>
					<th data-options="field:'BUS_NAME',align:'left'" width="250" >业务类别</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
