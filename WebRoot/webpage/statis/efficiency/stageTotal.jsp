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
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>				
<script type="text/javascript">

    function showDataRefresh(){
    	$.dialog.open("statisticsController.do?dataRefresh", {
    		title : "同步数据",
            width:"400px",
            lock: true,
            resize:false,
            height:"300px",
    	}, false);
    }
    
    function exportExcel(){
    	var beginDate = $("input[name='BEGIN_DATE']").val();
    	var endDate = $("input[name='END_DATE']").val();
    	//var departId=$("input[name='DEPART_ID']").val();
    	window.location.href = "deptStaticsController.do?exportStageExcel&Q_T.STATIST_DATE_GE="+beginDate+"&Q_T.STATIST_DATE_LE="+endDate;
    }
    
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="stagetotalToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
					         onclick="exportExcel();">导出数据</a> 
							 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SysLogForm">
				<input type="hidden" name="DEPART_ID" value="${sysUser.depId}">
				
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;">
							<input id="beginDate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})"  
							class="tab_text validate[required] Wdate" name="BEGIN_DATE" style="vertical-align:middle; line-height:20px;"/>
							
							</td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input id="endDate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})"  
							class="tab_text validate[required] Wdate" name="END_DATE" style="vertical-align:middle; line-height:20px;"/>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('stagetotalToolBar','stagetotalGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="stagetotalGrid" fitcolumns="false" toolbar="#stagetotalToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="deptStaticsController.do?stageTotalData">
			<thead>
				<tr>
					<th data-options="field:'END_DATE',align:'left'" width="20%" >时间范围</th>
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