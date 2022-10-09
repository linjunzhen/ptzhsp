
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除法律法规列表记录
	 */
	function deleteLawReguGridRecord() {
		AppUtil.deleteDataGridRecord("lawReguController.do?multiDel",
				"LawReguGrid");
	};
	/**
	 * 编辑法律法规列表记录
	 */
	function editLawReguGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("LawReguGrid");
		if (entityId) {
			showLawReguWindow(entityId);
		}
	}

	/**
	 * 显示法律法规信息对话框
	 */
	function showLawReguWindow(entityId) {
		$.dialog.open("lawReguController.do?info&entityId=" + entityId, {
			title : "法律法规信息",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("LawReguToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="LawReguToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_LawRegu"
								iconcls="icon-note-add" plain="true"
								onclick="showLawReguWindow();">新建法律法规</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_LawRegu"
								iconcls="icon-note-edit" plain="true"
								onclick="editLawReguGridRecord();">编辑法律法规</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_LawRegu"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteLawReguGridRecord();">删除法律法规</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="LawReguForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">法规标题</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.LAW_TITLE_LIKE" /></td>
							<td colspan="6"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('LawReguToolbar','LawReguGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('LawReguForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="LawReguGrid" fitcolumns="true" toolbar="#LawReguToolbar"
			method="post" idfield="LAW_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="lawReguController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'LAW_ID',hidden:true" width="80">LAW_ID</th>
					<th data-options="field:'LAW_TITLE',align:'left'" width="300">法规标题</th>
					<th data-options="field:'UPDATE_TIME',align:'left'" width="100">最后更新时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
