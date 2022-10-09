<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除流程按钮列表记录
	 */
	function deleteBusinessScopeGridRecord() {
		AppUtil.deleteDataGridRecord("domesticControllerController.do?multiDelBusinessScope",
				"BusinessScopeGrid");
	};
	/**
	 * 编辑流程按钮列表记录
	 */
	function editBusinessScopeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BusinessScopeGrid");
		if (entityId) {
			showBusinessScopeWindow(entityId);
		}
	}

	/**
	 * 显示流程按钮信息对话框
	 */
	function showBusinessScopeWindow(entityId) {
		$.dialog.open("domesticControllerController.do?businessScopeInfo&entityId=" + entityId, {
			title : "经营范围信息",
			width : "950px",
			height : "800px",
			lock : true,
			resize : false
		}, false);
	};

	function isusableformater(value,row,index){
		if(value=='1'){
			return "<font color='blue'>可用</font>";
		}else if(value=='0'){
			return "<font color='red'>不可用</font>";
		}
	}
	$(document).ready(function() {

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BusinessScopeToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_BusinessScope"
								iconcls="eicon-plus" plain="true"
								onclick="showBusinessScopeWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_BusinessScope"
								iconcls="eicon-pencil" plain="true"
								onclick="editBusinessScopeGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_BusinessScope"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBusinessScopeGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BusinessScopeForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:135px;text-align:right;">经营范围表述条目</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_NAME_LIKE" /></td>
							<td style="width:135px;text-align:right;">条目代码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_CODE_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusinessScopeToolbar','BusinessScopeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusinessScopeForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BusinessScopeGrid" fitcolumns="true" toolbar="#BusinessScopeToolbar"
			method="post" idfield="ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false"
			url="domesticControllerController.do?businessScopeDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'BUSSCOPE_NAME',align:'left'" width="30%">经营范围表述条目</th>
					<th data-options="field:'BUSSCOPE_CODE',align:'left'" width="15%">条目代码</th>
					<th data-options="field:'REMARK',align:'left'" width="35%">经营范围规范条目说明</th>
					<th data-options="field:'IS_USABLE',align:'left',formatter:isusableformater" width="15%">是否可用</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
