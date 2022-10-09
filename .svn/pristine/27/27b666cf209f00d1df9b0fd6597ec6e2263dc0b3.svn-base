
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除公众号列表记录
	 */
	function deletePublicNumberGridRecord() {
		AppUtil.deleteDataGridRecord("publicNumberController.do?multiDel",
				"PublicNumberGrid");
	};
	/**
	 * 编辑公众号列表记录
	 */
	function editPublicNumberGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("PublicNumberGrid");
		if (entityId) {
			showPublicNumberWindow(entityId);
		}
	}

	/**
	 * 显示公众号信息对话框
	 */
	function showPublicNumberWindow(entityId) {
		$.dialog.open("publicNumberController.do?info&entityId=" + entityId, {
			title : "公众号信息",
			width : "600px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PublicNumberToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_PublicNumber"
								iconcls="icon-note-add" plain="true"
								onclick="showPublicNumberWindow();">新建公众号</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_PublicNumber"
								iconcls="icon-note-edit" plain="true"
								onclick="editPublicNumberGridRecord();">编辑公众号</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_PublicNumber"
								iconcls="icon-note-delete" plain="true"
								onclick="deletePublicNumberGridRecord();">删除公众号</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="PublicNumberForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">APPID</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.APPID_=" /></td>
							<td style="width:68px;text-align:right;">APPSECRET</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.APPSECRET_=" /></td>
							<td style="width:68px;text-align:right;">接口Token</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.TOKEN_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('PublicNumberToolbar','PublicNumberGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PublicNumberForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="PublicNumberGrid" fitcolumns="true"
			toolbar="#PublicNumberToolbar" method="post" idfield="NUMBER_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="publicNumberController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'NUMBER_ID',hidden:true" width="80">NUMBER_ID</th>
					<th data-options="field:'APPID',align:'left'" width="100">APPID</th>
					<th data-options="field:'APPSECRET',align:'left'" width="100">APPSECRET</th>
					<th data-options="field:'URL',align:'left'" width="100">接口URL</th>
					<th data-options="field:'TOKEN',align:'left'" width="100">接口Token</th>
					<th data-options="field:'JS_SEC_DOMAIN',align:'left'" width="100">JS接口安全域名</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="100">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
