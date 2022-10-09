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
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var row = $("#CreditCompanyGrid").datagrid("getChecked");
    	if(row.length==0){
    		art.dialog({
				content : "请选择一条记录",
				icon : "warning",
				ok : true
			});
    	}else if(row.length>1){
    		art.dialog({
				content : "只能选择一条记录",
				icon : "warning",
				ok : true
			});
    	}else{
			art.dialog.data("CREDITCOMPANY", {
				COMPANY_NAME:row[0].ENT_NAME,
				LEGAL_NAME:row[0].LEREP,
				OLD_REG_ADDR:row[0].DOM,
				BUSSINESS_ADDR:row[0].DOM,
				CONTACT_PHONE:row[0].TELEPHONE,
				OLD_BUS_YEARS:row[0].OPFROM+"至"+row[0].OPTO,
				OLD_BUS_SCOPE:row[0].OP_SCOPE,
				LIAISON_NAME:row[0].LIAISON_NAME,
				LIAISON_MOBILE:row[0].LIAISON_MOBILE,
				LIAISON_IDNO:row[0].LIAISON_IDNO
			});
			AppUtil.closeLayer();
    	}
    }

	$(function() {
		$("#CreditCompanyGrid").datagrid({
		});
	});
	
	function dosearchItem(){
		$("#CreditCompanyGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('CreditCompanyToolbar','CreditCompanyGrid');
	}

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="CreditCompanyToolbar">				
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<form name="LicenseCatalogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
                        <td style="width:68px;text-align:right;">公司名称</td>
                        <td style="width:135px;"><input class="eve-input"
                            type="text" maxlength="20" style="width:328px;"
                            name="ENT_NAME" value="${entName}"/></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="dosearchItem();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('LicenseCatalogForm')" /></td>
					</tr>
				</table>
			    </form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================--> 
			<table rownumbers="true" pagination="false" striped="true"
				id="CreditCompanyGrid" fitColumns="true" toolbar="#CreditCompanyToolbar"
				method="post" idField="COMPANY_NAME" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="exeDataController/findCreditCompanyInfos.do?sqrsfz=${sqrsfz}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
	                    <th data-options="field:'ENT_UUID',hidden:true" width="80">ENT_UUID</th>
	                    <th data-options="field:'ENT_NAME',align:'left'" width="150">企业名称</th>
	                    <th data-options="field:'SOCIAL_CREDIT_UNICODE',align:'left'" width="350">社会信用统一代码</th>
	                    <th data-options="field:'LEREP',align:'left'" width="150">法人</th>
	                    <th data-options="field:'EST_DATE',align:'left'" width="150">成立日期</th>
						<th data-options="field:'EST_DATE',align:'left',hidden:true" width="150">经营范围</th>
						<th data-options="field:'DOM',align:'left',hidden:true" width="150">经营场所</th>
						<th data-options="field:'TELEPHONE',align:'left',hidden:true" width="150">联系电话</th>
						<th data-options="field:'OPFROM',align:'left',hidden:true" width="150">营业期限起</th>
						<th data-options="field:'OPTO',align:'left',hidden:true" width="150">营业期限止</th>
						<th data-options="field:'OP_SCOPE',align:'left',hidden:true" width="150">经营范围</th>
						<th data-options="field:'LIAISON_NAME',align:'left',hidden:true" width="150">联络员姓名</th>
						<th data-options="field:'LIAISON_MOBILE',align:'left',hidden:true" width="150">联络员电话</th>
						<th data-options="field:'LIAISON_IDNO',align:'left',hidden:true" width="150">联络员身份证号</th>

					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="确定" type="button" onclick="doSelectRows()"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
