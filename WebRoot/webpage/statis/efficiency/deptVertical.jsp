<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    function showSelectDeparts(){
    	var departId = $("input[name='DEPART_ID']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");//ShowObjProperty(selectDepInfo);
    			if(selectDepInfo){
    				$("input[name='DEPART_ID']").val(selectDepInfo.departIds);
        			$("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
        			//$("input[name='DEPT_CODE']").val(selectDepInfo.departCode);
    			}
    		}
    	}, false);
    }
    function ShowObjProperty(Obj)
{
	var str;
	for(prop in Obj){
		str+="属性" + prop+";";
	}
	alert(str);
} 

	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="deptverticalToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
							     
						         <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" 
						         onclick="exportExcel();">导出数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
							</div>
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
							<td style="width:68px;text-align:right;">查询部门</td>
							<td style="width:135px;">
								<input type="text" style="width:150px;float:left;"
									readonly="readonly" value="${sysUser.depName}"
									class="eve-input validate[required]" name="DEPT_NAME" onclick="showSelectDeparts();"></td>
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
								onclick="AppUtil.gridDoSearch('deptverticalToolBar','deptverticalGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="deptverticalGrid" fitcolumns="false" toolbar="#deptverticalToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="deptStaticsController.do?deptVerticalData">
			<thead>
				<tr>
					<th data-options="field:'STADATE',align:'left'" width="200" >月份</th>
					<th data-options="field:'BJ_PTJ',align:'left'" width="100" >办结总数</th>
					<th data-options="field:'TIQIAN',align:'left'" width="100" >提前</th>
					<th data-options="field:'ANSHI',align:'left'" width="100" >按时</th>
					<th data-options="field:'CHAOQI',align:'left'" width="100" >超期</th>
					<th data-options="field:'EFFI_DESC_4',align:'left'" width="100" >即办</th>
					<th data-options="field:'TIQL',align:'left'" width="160" >提前办结率</th>
					<th data-options="field:'CHAOQL',align:'left'" width="160" >超期办结率</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>