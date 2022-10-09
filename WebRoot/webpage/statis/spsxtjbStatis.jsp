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

    function exportSpsxtjbExcel(){
    	var beginDate = $("input[name='Q_T.END_TIME_>=']").val();
    	var endDate = $("input[name='Q_T.END_TIME_<=']").val();
		var DEPTNAME = $("input[name='DEPT_NAME']").val();
		var DEPART_ID = $("input[name='Q_T.DEPART_ID_=']").val();
		var ITEM_NAME = $("input[name='Q_T.ITEM_NAME_LIKE']").val(); 
    	window.location.href = "statisticsController.do?exportSpsxtjbExcel&Q_T.END_TIME_GE="+beginDate+" 00:00:00&Q_T.END_TIME_LE="+endDate+" 23:59:59&beginDate="+beginDate+"&endDate="+endDate+"&DEPTNAME="+DEPTNAME+"&Q_T.DEPART_ID_EQ="+DEPART_ID+"&Q_T.ITEM_NAME_LIKE="+ITEM_NAME;
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.SPSXTJB_TIME_BEGIN",
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
					elem : "#SysLogL.SPSXTJB_TIME_END",
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
			
	function showSelectDeparts(){
		var departId = $("input[name='Q_T.DEPART_ID_=']").val();
		$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "组织机构选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					$("input[name='Q_T.DEPART_ID_=']").val(selectDepInfo.departIds);
					$("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
	function resetSpsxtjbForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SpsxtjbForm');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="spsxtjbStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
					         onclick="exportSpsxtjbExcel();">导出数据</a> 
							 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SpsxtjbForm">
				<input type="hidden" id="DEPARTID" name="Q_T.DEPART_ID_=" value="">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">查询部门：</td>							
							<td style="width:135px;">							
							<input type="text"  placeholder="请选择部门"
								style="width:128px;float:left;" class="eve-input" name="DEPT_NAME" onclick="showSelectDeparts();"/>
							</td>
							<td style="width:68px;text-align:right;">事项名称：</td>							
							<td style="width:135px;">							
							<input type="text" 
								style="width:128px;float:left;" class="eve-input" name="Q_T.ITEM_NAME_LIKE" />
							</td>
							
								
							<td style="width:68px;text-align:right;">开始日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SPSXTJB_TIME_BEGIN" name="Q_T.END_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期：</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.SPSXTJB_TIME_END" name="Q_T.END_TIME_<=" /></td>
							<td style="width:80px;text-align:right;">办件渠道：</td>
							<td>
								<select class="easyui-combobox" name="Q_T.SQFS_EQ">
									<option value="">请选择</option>
									<option value="1">网上申请</option>
									<option value="2">窗口收件</option>
									<option value="3">省网办发起</option>
									<option value="4">省市联动</option>
								</select>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('spsxtjbStatisToolBar','spsxtjbStatisGrid')" />
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
			id="spsxtjbStatisGrid" fitcolumns="false" toolbar="#spsxtjbStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="statisticsController.do?spsxtjbData">
			<thead>
				<tr>
					<th data-options="field:'ITEM_NAME',align:'left'" width="40%" >审批事项</th>
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
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>