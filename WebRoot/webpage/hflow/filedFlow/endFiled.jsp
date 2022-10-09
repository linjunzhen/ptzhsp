<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	function formatSubject(val, row) {
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		// 追加赋值isFiled=1用于detail页面查询时区分是否未归档流程查询
		// 若isFiled=1，则查询归档表数据
		href += "executionController.do?goDetail&isFiled=1&exeId=" + exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>" + val + "</span></a>";
		return href;
	}

	$(document).ready(function() {
		var startReg = {
			elem : "#MyApplyExecT.CREATE_TIME_BEGIN_REG",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						art.dialog({
							content : "开始时间必须小于等于结束时间,请重新输入!",
							icon : "warning",
							ok : true
						});
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var endReg = {
			elem : "#MyApplyExecT.CREATE_TIME_END_REG",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						art.dialog({
							content : "结束时间必须大于等于开始时间,请重新输入!",
							icon : "warning",
							ok : true
						});
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(startReg);
		laydate(endReg);

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="endFiledToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="endFiledForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="Q_T.SQRMC_LIKE" /></td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_BEGIN_REG" name="Q_T.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_END_REG" name="Q_T.CREATE_TIME_&lt;=" /></td>
							<td colspan="2"><input type="button" value="查询" class="eve-button"
								onclick="AppUtil.gridDoSearch('endFiledToolbar','endFiled')" /> <input
								type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('endFiledForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="endFiled" fitcolumns="true" toolbar="#endFiledToolbar" method="post"
			idfield="EXE_ID" checkonselect="false" nowrap="false" selectoncheck="false"
			fit="true" border="false" url="serviceHandleController.do?hisFiledFlowList">
			<thead>
				<tr>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%"
						formatter="formatSubject">流程标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="13%">申请人</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="13%"
						formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
