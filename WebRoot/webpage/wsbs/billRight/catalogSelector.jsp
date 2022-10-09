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
    	var rows = $("#CatalogGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
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
    		var billId = "";
    		var catalogName = "";
			var departId = "";
			var departName = "";
			var sxxz = "";
			var sxxzc = "";
			var fta = "";
			for(var i = 0;i<rows.length;i++){
				billId+=rows[i].BILL_ID;
				catalogName+=rows[i].BILL_NAME;
				departId+=rows[i].DEPART_ID;
				departName+=rows[i].DEPART_NAME;
				sxxz+=rows[i].SXXZ;
				sxxzc+=rows[i].ITEM_KIND;
				fta+=rows[i].FTA_FLAG;
			}
    		art.dialog.data("selectCatalogInfo", {
    			billId:billId,
    			catalogName:catalogName,
    			departId:departId,
    			departName:departName,
    			sxxz:sxxz,
    			sxxzc:sxxzc,
    			fta:fta
			}); 
    		AppUtil.closeLayer();
    	}
    	
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="CatalogToolbar">
				
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="CatalogGrid" fitColumns="true" toolbar="#CatalogToolbar"
				method="post" idField="BILL_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="billRightController.do?catalogForBind">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'BILL_ID',hidden:true" width="80">BILL_ID</th>
	                    <th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
	                    <th data-options="field:'SXXZ',hidden:true" width="80">SXXZ</th>
	                    <th data-options="field:'FTA_FLAG',hidden:true" width="80">FTA_FLAG</th>
	                    <th data-options="field:'BILL_NAME',align:'left'" width="250">目录名称</th>
						<th data-options="field:'ITEM_KIND',align:'left'" width="80">清单性质</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="96">所属部门</th>
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
