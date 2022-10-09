<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
  	
	/**
	 * 删除主行业列表记录
	 */
	function deleteIndustryGridRecord() {
		AppUtil.deleteDataGridRecord("industryController.do?multiDel",
				"IndustryGrid");
	};

	/**
	 * 编辑主行业列表记录
	 */
	function editIndustryGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("IndustryGrid");
		if (entityId) {
			showIndustryWindow(entityId);
		}
	}
	
	/**
	 * 显示主行业信息对话框
	 */
	function showIndustryWindow(entityId) {
		$.dialog.open("industryController.do?info&entityId=" + entityId, {
			title : "主行业信息",
			width : "600px",
			lock : true,
			resize : false,
			height : "200px",
		}, false);
	};
	
	/**
	 * 显示子行业信息编辑对话框
	 */
	function childIndustryView() {
		var entityId = AppUtil.getEditDataGridRecord("IndustryGrid");
		if (entityId) {
			$.dialog.open("industryController.do?childIndustryView&entityId=" + entityId, {
				title : "子行业信息编辑",
				width : "80%",
				lock : true,
				resize : false,
				height : "80%",
			}, false);
		}
		
	};

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="IndustryToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SysRole"
								iconcls="eicon-plus" plain="true"
								onclick="showIndustryWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysRole"
								iconcls="eicon-pencil" plain="true"
								onclick="editIndustryGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysRole"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteIndustryGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" reskey="Grant_SysRole"
								iconcls="eicon-bookmark" plain="true"
								onclick="childIndustryView();">子行业编辑</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="IndustryForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">主行业名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.INDUSTRY_NAME_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('IndustryToolbar','IndustryGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('IndustryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="IndustryGrid" fitcolumns="true" toolbar="#IndustryToolbar"
			method="post" idfield="INDUSTRY_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="industryController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'INDUSTRY_ID',hidden:true">INDUSTRY_ID</th>
					<th data-options="field:'COMTYPE_ID',hidden:true">COMTYPE_ID</th>
					<th data-options="field:'INDUSTRY_NAME',align:'left'" width="40%">主行业名称</th>
					<th data-options="field:'COMTYPE_NAME',align:'left'" width="40%">所属企业类型名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>