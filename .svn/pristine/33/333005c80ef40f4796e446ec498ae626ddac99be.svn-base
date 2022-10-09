<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable {
	width: 100%;
	height: 100%
}

.gridtable .datagrid-htable {
	border-top: 1px solid #ccc;
	border-left: 1px solid #ccc
}

.gridtable .datagrid-btable {
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc
}

.gridtable .datagrid-header-row td {
	border-width: 0;
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.gridtable .datagrid-header-row td:last-child {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}

.gridtable .datagrid-body td {
	border-width: 0;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
</style>
<script type="text/javascript">

    function exportSjdzxlExcel(){
    	var beginDate = $("input[name='Q_T.END_TIME_>=']").val();
    	var endDate = $("input[name='Q_T.END_TIME_<=']").val();
    	window.location.href = "statisticsController.do?exportSjdzxlExcel&Q_T.END_TIME_GE="+beginDate+" 00:00:00&Q_T.END_TIME_LE="+endDate+" 23:59:59&beginDate="+beginDate+"&endDate="+endDate;
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.SJDZXL_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.END_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.SJDZXL_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.END_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.END_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.END_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
			

	function resetSjdzxlForm(){
		AppUtil.gridSearchReset('SjdzxlForm');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="sjdzxlStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
						         onclick="exportSjdzxlExcel();">导出数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SjdzxlForm">
				<input type="hidden" id="DEPARTID" name="Q_T.DEPART_ID_=" value="">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SJDZXL_TIME_BEGIN" name="Q_T.END_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SJDZXL_TIME_END" name="Q_T.END_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('sjdzxlStatisToolBar','sjdzxlStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSjdzxlForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="sjdzxlStatisGrid" fitcolumns="false" toolbar="#sjdzxlStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?sjdzxlData">
			<thead>
				<tr>
					<th data-options="field:'TIMES',align:'left'" width="30%" >时间范围</th>
					<th data-options="field:'COUNTS',align:'center'" width="10%" >办结总数</th>
					<th data-options="field:'EFFI_DESC_1',align:'center'" width="10%" >提前</th>
					<th data-options="field:'EFFI_DESC_2',align:'center'" width="10%" >按时</th>
					<th data-options="field:'EFFI_DESC_3',align:'center'" width="10%" >超期</th>
					<th data-options="field:'EFFI_DESC_4',align:'center'" width="10%" >即办</th>
					<th data-options="field:'TQBJL',align:'center'" width="10%" >提前办结率</th>
					<th data-options="field:'CQBJL',align:'center'" width="10%" >超期办结率</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>