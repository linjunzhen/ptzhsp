<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
</head>
<body>
<form name="serForm" action="<%=path%>/wxUploadVideoController.do?fileUpload" method="post"  enctype="multipart/form-data">
	<h1>采用流的方式上传文件</h1>
	<input type="file" name="file">
	<input type="text" name="TOKEN">
	<input type="submit" value="upload"/>
</form>

<form name="Form2" action="/SpringMVC006/fileUpload2" method="post"  enctype="multipart/form-data">
	<h1>采用multipart提供的file.transfer方法上传文件</h1>
	<input type="file" name="file">
	<input type="submit" value="upload"/>
</form>

<form name="Form2" action="/SpringMVC006/springUpload" method="post"  enctype="multipart/form-data">
	<h1>使用spring mvc提供的类的方法上传文件</h1>
	<input type="file" name="file">
	<input type="submit" value="upload"/>
</form>
</body>
</html>