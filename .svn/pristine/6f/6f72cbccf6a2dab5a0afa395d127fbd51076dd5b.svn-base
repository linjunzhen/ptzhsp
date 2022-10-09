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
	$(function() {
		$("#LicenseFileGrid").datagrid({
		});
	});
	
	function rowformater(value,row,index){
		var downloadPath = row.FILE_DOWNLOAD_PATH;
		var href = "<a href='"+downloadPath+"' target='_blank'><span style='text-decoration:underline;color:green;'>"+value+"</span></a>";
	    return href;
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="LicenseFileToolbar">				
				
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================--> 
			<table rownumbers="true" pagination="false" striped="true"
				id="LicenseFileGrid" fitColumns="true" toolbar="#LicenseFileToolbar"
				method="post" idField="FILE_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="licenseCatalogController/licenseFilesData.do?SERIALNUMBER=${SERIALNUMBER}">
				<thead>
					<tr>
	                    <th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>
	                    <th data-options="field:'FILE_NAME',align:'left',formatter:rowformater" width="150">文件名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				 <input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
