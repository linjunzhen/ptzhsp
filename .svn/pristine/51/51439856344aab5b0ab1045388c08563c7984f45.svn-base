<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<script language="javascript" src="webpage/wsbs/twqualification/js/qrcode.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery3.min.js"></script>	
<script type="text/javascript">
    $(function() {
	    var id = '${id}';
		var qrtext = "http://xzfwzx.pingtan.gov.cn:8888/twQualificationController/QRcodeSearch.do?id="+id;
		new QRCode(document.getElementById("qrcode"), {width : 100,height : 100,text: qrtext});	
	});
</script>
</head>

<div id="qrcode" style="width:50%;height:80%;margin-left:90px;margin-top:30px;">	
   
</div>