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
    		var addId = "";
    		var userId = "";
			var recName = "";
			var mobile = "";
			var fixedTel = "";
			var detailAdd = "";
			var postcode = "";
			var province = "";
			var city = "";
			var county = "";
			for(var i = 0;i<rows.length;i++){
				addId+=rows[i].ADD_ID;
				userId+=rows[i].USER_ID;
				recName+=rows[i].REC_NAME;
				mobile+=rows[i].MOBILE;
				fixedTel+=rows[i].FIXED_TEL;
				detailAdd+=rows[i].DETAIL_ADD;
				postcode+=rows[i].POSTCODE;
				province+=rows[i].PROVINCE;
				city+=rows[i].CITY;
				county+=rows[i].COUNTY;
			}
    		art.dialog.data("selectAddrInfo", {
    			addId:addId,
    			userId:userId,
    			recName:recName,
    			mobile:mobile,
    			fixedTel:fixedTel,
    			detailAdd:detailAdd,
    			postcode:postcode,
    			province:province,
    			city:city,
    			county:county
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
				method="post" idField="ADD_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="userInfoController.do?findAddrByUserId">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'ADD_ID',hidden:true" width="80">ADD_ID</th>
	                    <th data-options="field:'USER_ID',hidden:true" width="80">USER_ID</th>
	                    <th data-options="field:'REC_NAME',align:'left'" width="80">姓名</th>
						<th data-options="field:'MOBILE',align:'left'" width="150">手机号</th>
						<th data-options="field:'FIXED_TEL',align:'left'" width="150">固定电话</th>
						<th data-options="field:'DETAIL_ADD',align:'left'" width="300">详细地址</th>
						<th data-options="field:'POSTCODE',align:'left'" width="96">邮政编码</th>
						<th data-options="field:'PROVINCE',align:'left'" width="96">所属省份</th>
						<th data-options="field:'CITY',align:'left'" width="96">所属城市</th>
						<th data-options="field:'COUNTY',align:'left'" width="96">所属区/县</th>
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
