<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteTicketsGridRecord() {
		AppUtil.deleteDataGridRecord("ticketsController.do?multiDel",
				"TicketsGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editTicketsGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("TicketsGrid");
		if (entityId) {
			showTicketsWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showTicketsWindow(entityId) {
		$.dialog.open("ticketsController.do?info&entityId=" + entityId, {
			title : "单据配置信息",
			width : "700px",
			height : "300px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("TicketsToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="TicketsToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Tickets"
								iconcls="eicon-plus" plain="true"
								onclick="showTicketsWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Tickets"
								iconcls="eicon-pencil" plain="true"
								onclick="editTicketsGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Tickets"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteTicketsGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TicketsForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">单据名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_TICKETS_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('TicketsToolbar','TicketsGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('TicketsForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="TicketsGrid" fitcolumns="true" toolbar="#TicketsToolbar"
			method="post" idfield="TICKETS_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="ticketsController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TICKETS_ID',hidden:true">TICKETS_ID</th>
					<th data-options="field:'TICKETS_NAME',align:'left'" width="97">单据名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
