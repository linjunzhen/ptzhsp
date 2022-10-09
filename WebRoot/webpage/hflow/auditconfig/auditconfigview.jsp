<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除审批控件列表记录
	 */
	function deleteAuditConfigGridRecord() {
		AppUtil.deleteDataGridRecord("auditConfigController.do?multiDel",
				"AuditConfigGrid");
	};
	/**
	 * 编辑审批控件列表记录
	 */
	function editAuditConfigGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("AuditConfigGrid");
		if (entityId) {
			showAuditConfigWindow(entityId);
		}
	}

	/**
	 * 显示审批控件信息对话框
	 */
	function showAuditConfigWindow(entityId) {
		$.dialog.open("auditConfigController.do?info&entityId=" + entityId, {
			title : "审批控件信息",
			width : "600px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};
	
	function formatAuditType(val,row){
		if(val=="1"){
			return "<font color='green'><b>接口类型</b></font>";
		}else if(val=="3"){
			return "<font color='black'><b>任务决策类型</b></font>";
		}else{
			return "<font color='blue'><b>选择器类型</b></font>";
		}
	}

	$(document).ready(function() {

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="AuditConfigToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_AuditConfig"
								iconcls="eicon-plus" plain="true"
								onclick="showAuditConfigWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_AuditConfig"
								iconcls="eicon-pencil" plain="true"
								onclick="editAuditConfigGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_AuditConfig"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteAuditConfigGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="AuditConfigForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">配置名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.CONFIG_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">组件类型</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.AUDITER_TYPE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowAuditCompType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('AuditConfigToolbar','AuditConfigGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AuditConfigForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="AuditConfigGrid" fitcolumns="true" toolbar="#AuditConfigToolbar"
			method="post" idfield="CONFIG_ID" checkonselect="true" nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="auditConfigController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONFIG_ID',hidden:true">CONFIG_ID</th>
					<th data-options="field:'CONFIG_NAME',align:'left'" width="20%">配置名称</th>
					<th data-options="field:'CONFIG_CODE',align:'left'" width="26%">配置代码</th>
					<th data-options="field:'AUDITER_TYPE',align:'left'" width="10%" formatter="formatAuditType">组件类型</th>
					<th data-options="field:'SELECTOR_URL',align:'left'" width="23%">选择器URL</th>
					<th data-options="field:'SELECTOR_HEIGHT',align:'left'" width="8%">选择器高度</th>
					<th data-options="field:'SELECTOR_WIDTH',align:'left'" width="8%">选择器宽度</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
