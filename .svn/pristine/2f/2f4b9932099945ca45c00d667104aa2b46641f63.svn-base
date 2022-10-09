<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
	.box{
		width:2000px;
		height:1000px;
		border:2px;
		text-align:center;
	}
	.img{
		vertical-align: middle;
	}
</style >
</head>
<body >
   <div class="box">
     <img class="img" src="${_file_Server}${fileAllPath}" />
    </div>
</body>
</html>