<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callnew/takeNo/css/index.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callnew/takeNo/css/style2.css">
<link rel="stylesheet" href="<%=path%>/webpage/callnew/takeNo/css/default.css" type="text/css">
<script>
	$(function(){
        setInterval('reloadView()',20000);
	});
	function reloadView(){
        $.post("exeDataController.do?getResultOfSign",{
            EXEID:'26206508',
            NAME:'任宏英',
            IDNO:'350128197603080024'
			},
           function(responseText, status, xhr) {
               var resultJson = $.parseJSON(responseText);
               if(resultJson.code == '001'){//成功
                   console.info('token为'+resultJson.msg);
               } else {//失败
                   console.info('错误信息为'+resultJson.msg);
               }
           }
        );
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <base href="<%=basePath%>">
		<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>平潭落地屏</title>

	</head>
	<body>



	</body>
</html>
