<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable{width:100%;height:100%}
.gridtable .datagrid-htable{border-top:1px solid #8DB2E3;border-right:1px solid #8DB2E3}
.gridtable .datagrid-btable{border-right:1px solid #8DB2E3;border-bottom:1px solid #8DB2E3}
.gridtable .datagrid-header-row td{border-width:0;border-left:1px solid #8DB2E3;border-bottom: 1px solid #8DB2E3;}
.gridtable .datagrid-body td{border-width:0;border-left:1px solid #8DB2E3;border-top: 1px solid #8DB2E3} 
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
    	var departId=$("input[name='DEPART_ID']").val();
    	window.location.href = "deptStaticsController.do?exportExcel&Q_T.STATIST_DATE_GE="+beginDate+"&Q_T.STATIST_DATE_LE="+endDate+"&departId="+departId;
    }
 
	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="depthorizonToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="SysLogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;"> 
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;">
							<input id="beginDate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  
							class="tab_text validate[required] Wdate" name="BEGIN_DATE" style="vertical-align:middle; line-height:20px;"/>
							</td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input id="endDate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  
							class="tab_text validate[required] Wdate" name="END_DATE" style="vertical-align:middle; line-height:20px;"/>
							</td>  
							
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('depthorizonToolBar','depthorizonGrid')" />
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
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="depthorizonGrid" fitcolumns="false" toolbar="#depthorizonToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="deptStaticsController.do?deptHorizonData">
			<thead>
				<tr>
					<th data-options="field:'DEPART_NAME',align:'left'" width="200" >部门</th>
					<th data-options="field:'BJ_PTJ',align:'center'" width="100" >办结总数</th>
					<th data-options="field:'TIQIAN',align:'center'" width="100" >提前</th>
					<th data-options="field:'ANSHI',align:'center'" width="100" >按时</th>
					<th data-options="field:'CHAOQI',align:'center'" width="100" >超期</th>
					<th data-options="field:'EFFI_DESC_4',align:'center'" width="80" >即办</th>
					<th data-options="field:'TIQL',align:'center'" width="160" >提前办结率</th>
					<th data-options="field:'CHAOQL',align:'center'" width="160" >超期办结率</th>
				</tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>