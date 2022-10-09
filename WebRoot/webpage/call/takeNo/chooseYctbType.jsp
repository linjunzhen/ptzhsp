<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="expires" content="0" />
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>自助取号系统</title>
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/cssnew/style.css">
</head>

<body class="bg-none" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title">请选择取号类型！</div>
    	
    <div class="scrollt-left">
			<ul class="scrollt-list" >
				<li >
					<a href="callController.do?toYctbReadCardAppoint&roomNo=${roomNo}" >
						<img src="webpage/call/takeNo/images/toReadCardAppoint.png">
						<span style="font-size:22px;font-weight:bold;">预约取号</span>
					</a>
				</li>
				<li >
					<%-- <a href="callController.do?toDeptChoose&roomNo=${roomNo}&isAppoint=1" > --%>
<!-- 					<a href="newCallController/toYctbBusinessItemChoose.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&macType=${macType}" > -->
					<a href="callYctbController/toYctbBusinessItemChoose.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&macType=${macType}&cleanItem=1" >
						<img src="webpage/call/takeNo/images/toDeptChoose.png">
						<span style="font-size:22px;font-weight:bold;">现场取号</span>
					</a>
				</li>
				<li >
					<a href="callController.do?toYctbReadCardAgain&roomNo=${roomNo}" >
						<img src="webpage/call/takeNo/images/toReadCardAppoint.png">
						<span style="font-size:22px;font-weight:bold;">查询取号</span>
					</a>
				</li>
            </ul>
    </div>
    
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript">
		jQuery(".scroll-left").slide({titCell:".hd ul",mainCell:".bd",autoPage:true,effect:"left",pnLoop:false,scroll:1,vis:1,delayTime:200,trigger:"click"});
		
	    function toChildDepart(departId,roomNo,lc,isTake){
	    	var now=new Date();            //创建Date对象
	        var hour=now.getHours();    //获取小时
	        var minute=now.getMinutes();    //获取分钟
	        if(hour>=29){
	        	if(minute>15){
		        	parent.art.dialog({
							content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">该时间段暂不提供取号服务！</font>",
							icon:"warning",
							time:3,
							width:"400px",
							height:"150px",
							ok: false
						});
					return false;
	        	}
	        }
	    	if(isTake=='0'){
	    		parent.art.dialog({
						content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往人工导引台咨询取号！</font>",
						icon:"warning",
						time:3,
						width:"400px",
						height:"150px",
						ok: false
					});
	    	}else if(lc!='${lc}'){
	    		parent.art.dialog({
						content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往"+roomNo+"区取号！</font>",
						icon:"warning",
						time:3,
						width:"400px",
						height:"150px",
						ok: false
					});
	    	}else{
	    	//window.parent.document.getElementById('takeFrame').src="callController.do?toChildDepart&departId="+departId;
	    	window.parent.document.getElementById('takeFrame').src="callController.do?toChildDepart&departId="+departId+"&roomNo="+roomNo;
	    	}
	    }
    </script>
</body>
</html>
