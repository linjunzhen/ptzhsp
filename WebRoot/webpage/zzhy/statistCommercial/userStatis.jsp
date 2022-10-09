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
	border-width: 0;
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
	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.USERSTATIS_TIME_BEGIN",
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
					elem : "#SysLogL.USERSTATIS_TIME_END",
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
	function resetSpsxtjbForm(){
		AppUtil.gridSearchReset('userStatisForm');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="userStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="userStatisForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">						
								
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.USERSTATIS_TIME_BEGIN" name="Q_T.END_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.USERSTATIS_TIME_END" name="Q_T.END_TIME_<=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('userStatisToolBar','userStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSpsxtjbForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="userStatisGrid" fitcolumns="false" toolbar="#userStatisToolBar"
			method="post" idfield="ASSIGNER_NAME" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="statistCommercialController.do?userStatisData">
			<thead>
				<tr>
					<th data-options="field:'ASSIGNER_NAME',align:'left'" width="400" >注册官名称</th>
					<th data-options="field:'COUNTS',align:'center'" width="200" >办件量总数</th>
				</tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>