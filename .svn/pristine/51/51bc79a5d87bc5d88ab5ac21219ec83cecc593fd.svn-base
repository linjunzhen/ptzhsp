<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除常见问题列表记录
	 */
	function deleteCommonProblemGridRecord() {
		AppUtil.deleteDataGridRecord("commonProblemController.do?multiDel",
				"CommonProblemGrid");
	};
	/**
	 * 编辑常见问题列表记录
	 */
	function editCommonProblemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("CommonProblemGrid");
		if (entityId) {
			showCommonProblemWindow(entityId);
		}
	}

	/**
	 * 显示常见问题信息对话框
	 */
	function showCommonProblemWindow(entityId) {
		$.dialog.open("commonProblemController.do?info&entityId=" + entityId, {
			title : "常见问题信息",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};
	
	function gridSearchReset(){
		AppUtil.gridSearchReset("CommonProblemForm");
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("CommonProblemToolbar");

	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="CommonProblemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_CommonProblem"
								iconcls="icon-note-add" plain="true"
								onclick="showCommonProblemWindow();">新建问题</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_CommonProblem"
								iconcls="icon-note-edit" plain="true"
								onclick="editCommonProblemGridRecord();">编辑问题</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CommonProblem"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteCommonProblemGridRecord();">删除问题</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="CommonProblemForm">
			    <input type="hidden" name="Q_D.PATH_LIKE" >
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">问题标题</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:228px;"
								name="Q_T.PROBLEM_TITLE_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('CommonProblemToolbar','CommonProblemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="gridSearchReset();" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="CommonProblemGrid" fitcolumns="true"
			toolbar="#CommonProblemToolbar" method="post" idfield="PROBLEM_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="commonProblemController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PROBLEM_ID',hidden:true" width="80">PROBLEM_ID</th>
					<th data-options="field:'PROBLEM_TITLE',align:'left'" width="300">问题标题</th>
					<th data-options="field:'LASTER_UPDATETIME',align:'left'" width="100">最后更新时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>

<div 
	style="display:none; position: absolute;" id="TDICTYPE_IDTreeContent">
	<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
		id="TDICTYPE_IDTree"></ul>
</div>
