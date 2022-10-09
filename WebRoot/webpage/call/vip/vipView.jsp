<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteVipGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelVip",
				"VipGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editVipGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("VipGrid");
		if (entityId) {
			showVipGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showVipGridRecord(entityId) {
		$.dialog.open("callController.do?vipInfo&entityId=" + entityId, {
			title : "VIP信息",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false
		}, false);
	};

	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("VipToolbar");
	}); */
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="VipToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Vip"
								iconcls="icon-note-add" plain="true"
								onclick="showVipGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Vip"
								iconcls="icon-note-edit" plain="true"
								onclick="editVipGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Vip"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteVipGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="VipGrid" fitcolumns="true" nowrap="false"
			toolbar="#VipToolbar" method="post" idfield="VIP_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?vipDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'VIP_ID',hidden:true" width="80">VIP_ID</th>
					<th data-options="field:'VIP_NAME',align:'left'" width="10%">姓名</th>
					<th data-options="field:'VIP_CARDNO',align:'left'" width="20%">身份证号</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
