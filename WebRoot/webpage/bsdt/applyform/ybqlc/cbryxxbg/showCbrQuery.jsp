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
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,json2"></eve:resources>
	<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
	<script type="text/javascript">	
		$(function (){
			//空数据，横向滚动
			$('#cbrGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			});
		});
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">

	<div class="easyui-layout" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="cbrToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="cbrForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tbody>
							<tr style="height:28px;">
								<td style="width:68px;text-align:right;">证件号码</td>
								<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:180px;" name="" />
								</td>
					            <td style="width:68px;text-align:right;">姓名</td>
								<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:180px;" name="" />
								</td>
								<td colspan="4">
									<input type="button" value="查询"
									class="eve-button"
									onclick="AppUtil.gridDoSearch('cbrToolbar','cbrGrid')" />
									<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('cbrForm')" /></td>
								</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="cbrGrid" fitColumns="true" toolbar="#cbrToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="YbCbryxxbgController.do?cbrQueryForSelect">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'BUSINESS_NAME',align:'left'" width="10%">单位保险号</th>
	                    <th data-options="field:'ITEM_CODE',align:'left'" width="15%">单位名称</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="10%">姓名</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="20%">证件号码</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="20%">个人保险号</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="15%">参保身份</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="10%">险种</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="15%">参保开始日期</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="10%">参保状态</th>		                    
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确认" type="button" onclick=""
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
