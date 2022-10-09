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
<script type="text/javascript" src="plug-in/easyui-1.4/extension/jquery-easyui-datagridview/datagrid-groupview.js"></script>
<script type="text/javascript">

    function exportBjtstjExcel(){
    	var beginDate = $("input[name='T.STATIST_DATE']").val();
    	var endDate = $("input[name='T.STATIST_DATE']").val();
    	window.location.href = "statisticsController.do?exportExcel&beginDate="+beginDate+"&endDate="+endDate;
    }

	$(document).ready(
			function() {
				var start1 = {
					elem : "#Bjtstj.OPERATE_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='T.BJTSTJSTATIST_SDATE']")
								.val();
						var endTime = $("input[name='T.BJTSTJSTATIST_EDATE']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='T.BJTSTJSTATIST_SDATE']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#Bjtstj.OPERATE_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='T.BJTSTJSTATIST_SDATE']")
								.val();
						var endTime = $("input[name='T.BJTSTJSTATIST_EDATE']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='T.BJTSTJSTATIST_EDATE']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);

			});
	function formatItemSpeed(val,row){
       return val+"%";
    };
    function searchBjtstjData(){
    	var today = new Date();
    	var endTime = $("input[name='T.BJTSTJSTATIST_EDATE']").val();
        if (endTime != "") {
    	
        	var end = new Date(endTime.replace("-", "/")
                .replace("-", "/"));
	    	   if (today < end) {
	    		    alert("结束时间必须小于等于当前时间,请重新输入!");
	    		    $("input[name='T.BJTSTJSTATIST_EDATE']").val("");
	            }
        }
    	AppUtil.gridDoSearch('bjtstjStatisToolBar','bjtstjStatisGrid');
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="bjtstjStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
						         <!-- <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" 
						         onclick="exportBjtstjExcel();">导出数据</a>  -->
						         提速统计列表
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BjtstjForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Bjtstj.OPERATE_TIME_BEGIN" name="T.BJTSTJSTATIST_SDATE" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Bjtstj.OPERATE_TIME_END" name="T.BJTSTJSTATIST_EDATE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="searchBjtstjData();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BjtstjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="bjtstjStatisGrid" fitcolumns="true" toolbar="#bjtstjStatisToolBar"
			method="post"  checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false" 
			url="statisticalReportController.do?bjtstjData" data-options="
                view:groupview,
                groupField:'DEPART_CODE',
                groupFormatter:function(value,rows){
                    return rows[0].DEPART_NAME + '  部门提速率 : '+rows[0].DEPART_SPEED+'%';
                } ">
			<thead>
				<tr>
                    <th data-options="field:'ITEM_NAME',align:'left'" width="81%">项目名称</th>
                    <th data-options="field:'ITEM_SPEED',align:'center'" width="15%" formatter="formatItemSpeed">项目提速率</th>
                </tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>