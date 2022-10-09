
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除比赛列表记录
	 */
	function deleteFootBallGridRecord() {
		AppUtil.deleteDataGridRecord("footBallController.do?multiDel",
				"FootBallGrid");
	};
	/**
	 * 编辑比赛列表记录
	 */
	function editFootBallGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FootBallGrid");
		if (entityId) {
			showFootBallWindow(entityId);
		}
	}

	/**
	 * 显示比赛信息对话框
	 */
	function showFootBallWindow(entityId) {
		$.dialog.open("footBallController.do?info&entityId=" + entityId, {
			title : "比赛信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		var start1 = {
			elem : "#FootBallT.BSSJ_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.BSSJ_>=']").val();
				var endTime = $("input[name='Q_T.BSSJ_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.BSSJ_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#FootBallT.BSSJ_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.BSSJ_>=']").val();
				var endTime = $("input[name='Q_T.BSSJ_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.BSSJ_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);

		AppUtil.initAuthorityRes("FootBallToolbar");

	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FootBallToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FootBall"
								iconcls="icon-note-add" plain="true"
								onclick="showFootBallWindow();">新建比赛</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_FootBall"
								iconcls="icon-note-edit" plain="true"
								onclick="editFootBallGridRecord();">编辑比赛</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_FootBall"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteFootBallGridRecord();">删除比赛</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="FootBallForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">比赛球队</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BSQD_LIKE" /></td>
							<td style="width:68px;text-align:right;">比赛时间从</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="FootBallT.BSSJ_BEGIN" name="Q_T.BSSJ_&gt;=" /></td>
							<td style="width:68px;text-align:right;">比赛时间至</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="FootBallT.BSSJ_END" name="Q_T.BSSJ_&lt;=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FootBallToolbar','FootBallGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('FootBallForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="FootBallGrid" fitcolumns="true" toolbar="#FootBallToolbar"
			method="post" idfield="BALL_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="footBallController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'BALL_ID',hidden:true" width="80">BALL_ID</th>
					<th data-options="field:'BSQD',align:'left'" width="100">比赛球队</th>
					<th data-options="field:'BSSJ',align:'left'" width="100">比赛时间</th>
					<th data-options="field:'PL',align:'left'" width="100">赔率</th>
					<th data-options="field:'QCSG',align:'left'" width="100">全场赛果</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
