<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

	function formatSubject(val, row) {
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId=" + exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>" + val + "</span></a>";
		return href;
	}

	$(document).ready(function() {
		var startReg = {
			elem : "#MyApplyExecT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.END_TIME_>=']").val();
				var endTime = $("input[name='Q_T.END_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						art.dialog({
							content : "开始时间必须小于等于结束时间,请重新输入!",
							icon : "warning",
							ok : true
						});
						$("input[name='Q_T.END_TIME_>=']").val("");
					}
				}
			}
		};
		var endReg = {
			elem : "#MyApplyExecT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.END_TIME_>=']").val();
				var endTime = $("input[name='Q_T.END_TIME_<=']").val();
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


	/**
	 * 手动归档
	 */
	function filedFlowByEndTime() {
		var beginTime = $("input[name='Q_T.END_TIME_>=']").val();
		var endTime = $("input[name='Q_T.END_TIME_<=']").val();
		if (beginTime == '' || endTime == '') {
			art.dialog({
				content : "请选择办理起止时间！",
				icon : "warning",
				ok : true
			});
			return null;
		}
		AppUtil.ajaxProgress({
			url : 'serviceHandleController.do?processArchive',
			params : {
				"START_DATE" : beginTime,
				"END_DATE" : endTime
			},
			callback : function(resultJson) {
				if (resultJson.success) {
					parent.art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
					parent.$("#handleMonitorGrid").datagrid("reload");
					AppUtil.closeLayer();
				} else {
					parent.art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			}
		});
	}

	/**
	 * 手动归档
	 */
	function filedFlow() {
		// 获取选中的流程
		var checkedItems = $('#endFlowGrid').datagrid('getChecked');
		if (checkedItems.length < 1) {
			art.dialog({
				content : "至少选择一条记录进行操作!",
				icon : "warning",
				ok : true
			});
			return null;
		}
		AppUtil.ajaxProgress({
			url : 'serviceHandleController.do?processArchive',
			params : {
				"CHECKED_ITEMS" : JSON.stringify(checkedItems)
			},
			callback : function(resultJson) {
				if (resultJson.success) {
					parent.art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
					parent.$("#endFlowGrid").datagrid("reload");
					AppUtil.closeLayer();
				} else {
					parent.art.dialog({
						content : '归档失败，请联系管理员！',
						icon : "error",
						ok : true
					});
				}
			}
		});
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="endFlowToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="endFlowForm">
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
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">办理日期</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_BEGIN" name="Q_T.END_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">办理日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_END" name="Q_T.END_TIME_&lt;=" /></td>
							<td><input type="button" value="查询" class="eve-button"
								onclick="AppUtil.gridDoSearch('endFlowToolbar','endFlowGrid')" /></td>
							<td><input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('endFlowForm')" /></td>
							<td style="width: 50px;"><input type="button" value="按申报号归档" class="eve-button"
								onclick="filedFlow();" /></td>
							<td><input type="button" value="按办理时间归档" class="eve-button"
								onclick="filedFlowByEndTime();" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="endFlowGrid" fitcolumns="true" toolbar="#endFlowToolbar" method="post"
			idfield="EXE_ID" checkonselect="false" nowrap="false" selectoncheck="false"
			fit="true" border="false" url="serviceHandleController.do?hisHandleMonitor">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%"
						formatter="formatSubject">流程标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="13%"
						formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
					<!--<th data-options="field:'WORK_HOURS',align:'left'" width="100">运行时间</th>  -->
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
