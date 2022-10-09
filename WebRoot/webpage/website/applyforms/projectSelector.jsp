<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#projectSelectorGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
    		var ProjectIds = "";
			var ProjectNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					ProjectIds+=",";
					ProjectNames+=",";
				}
				ProjectIds+=rows[i].PROJECT_ID;
				ProjectNames+=rows[i].PROJECT_NAME;
			}
    		art.dialog.data("selectProjectInfo", {
    			ProjectIds:ProjectIds,
    			ProjectNames:ProjectNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }


	$(function() {
	});
	function projectSearch(){
		$("#projectSelectorGrid").datagrid("clearSelections");
		AppUtil.gridDoSearch('projectSelectorToolbar','projectSelectorGrid')
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="projectSelectorToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--<input type="hidden" name="TYPE_ID">-->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="ProjectForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">							
							<td style="width:68px;text-align:right;">事项名称：</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="Q_T.PROJECT_NAME_LIKE" /></td>
								
							<td><input type="button" value="查询"
								class="eve-button"
								onclick="projectSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ProjectForm')" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="projectSelectorGrid" fitColumns="true" toolbar="#projectSelectorToolbar"
				method="post" idField="PROJECT_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false" 
				url="projectController/loadDatagrid.do">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'PROJECT_ID',hidden:true" width="80">TIEM_ID</th>
						<th data-options="field:'PROJECT_NAME',align:'left'" width="300">项目名称</th>						
						<th data-options="field:'PROJECT_TIME',align:'left'" width="100">立项时间</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
