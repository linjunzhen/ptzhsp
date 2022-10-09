<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">
    function doSubmitRows(){
    	var rows = $("#rightItemGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return;
    	}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return;
		}else{
    		var serviceId = "";
    		var agencyName = "";
			for(var i = 0;i<rows.length;i++){
				serviceId+=rows[i].SERVICE_ID;
				agencyName+=rows[i].AGENCY_ITEM_NAME;
			}
    		art.dialog.data("selectItemInfo", {
    			serviceId:serviceId,
    			agencyName:agencyName
			}); 
    		AppUtil.closeLayer();
    	}
    	
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true">
		<div data-options="region:'center',split:false">
			<div id="ItemToolbar"></div>
			<!-- =========================查询面板结束========================= -->

			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="rightItemGrid" fitColumns="true" toolbar="#ItemToolbar" method="post"
				idField="SERVICE_ID" checkOnSelect="true" selectOnCheck="true"
				fit="true" border="false" nowrap="false"
				url="agencyServiceController.do?datagrid&Q_T.STATUS_EQ=1">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'SERVICE_ID',hidden:true" width="80">SERVICE_ID</th>
						<th data-options="field:'IMPL_DEPART_ID',hidden:true" width="80">IMPL_DEPART_ID</th>
						<th data-options="field:'AGENCY_ITEM_NAME',align:'left'" width="250">中介事项名称</th>
						<th data-options="field:'AGENCY_ORG_TYPE_NAME',align:'left'" width="120">中介类型</th>
						<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="120">实施部门</th>
					</tr>
				</thead>
			</table>

		</div>

		<div data-options="region:'south',split:true,border:false">
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSubmitRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>


</body>
</html>
