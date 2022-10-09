<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除开发商列表记录
	 */
	function deleteDevelopersGridRecord() {
		AppUtil.deleteDataGridRecord("departmentController.do?developersDel",
				"DevelopersGrid");
	};
	/**
	 * 编辑开发商列表记录
	 */
	function editDevelopersGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("DevelopersGrid");
		if (entityId) {
			showDevelopersWindow(entityId);
		}
	}

	/**
	 * 显示开发商信息对话框
	 */
	function showDevelopersWindow(entityId) {
		$.dialog.open("departmentController.do?developersInfo&entityId=" + entityId, {
    		title : "开发商信息",
            width:"500px",
            lock: true,
            resize:false,
            height:"300px",
    	}, false);
	}
	
	$(document).ready(function() {
		//AppUtil.initAuthorityRes("DevelopersToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DevelopersToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Developers"
								iconcls="icon-note-add" plain="true"
								onclick="showDevelopersWindow();">新建开发商</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="EDIT_Developers"
								iconcls="icon-note-edit" plain="true"
								onclick="editDevelopersGridRecord();">编辑开发商</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Developers"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteDevelopersGridRecord();">删除开发商</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DevelopersForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开发商名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.UNIT_NAME_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DevelopersToolbar','DevelopersGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('DevelopersForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="DevelopersGrid" fitcolumns="false" toolbar="#DevelopersToolbar"
			method="post" idfield="UNIT_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="departmentController.do?developersDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'UNIT_ID',hidden:true" width="80">UNIT_ID</th>
					<th data-options="field:'UNIT_CODE',align:'left'" width="100">开发商编码</th>
					<th data-options="field:'UNIT_NAME',align:'left'" width="300">开发商名称</th>
					<th data-options="field:'UNIT_ADDRESS',align:'left'" width="350">开发商地址</th>
					<th data-options="field:'CONTACT_PERSON',align:'left'" width="150">联系人</th>
					<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">联系电话</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>