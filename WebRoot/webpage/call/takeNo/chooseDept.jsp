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

	<c:if test="${isAppoint==0}">
<%--		<div class="eui-title">请选择办事部门！</div>		--%>
		<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请选择办事部门！</a></div>
		
	</c:if>
	<c:if test="${isAppoint==1}">
		<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请选择办事部门！</a></div>
    </c:if>	
    <div class="scroll-left">
        <div class="bd">           
        
           	<c:forEach items="${departList}" var="departInfo" varStatus="infoStatus">
	           	<c:choose>
	           		<c:when test="${infoStatus.index==0}">
	           			<ul class="scroll-list">
	           				<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" onclick="toChildDepart('${departInfo.DEPART_ID}','${departInfo.DEPART_NAME}','${departInfo.BELONG_NO}','${departInfo.DIC_DESC}','${departInfo.IS_TAKE}')" ><img src="webpage/call/takeNo/imagesnew/icon${departInfo.ICON_NO}.png">${departInfo.DEPART_NAME}</a></li>
	           		</c:when>
	           		<c:when test="${infoStatus.index%12==0}">
	           			</ul>
	           			<ul class="scroll-list">
	           				<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" onclick="toChildDepart('${departInfo.DEPART_ID}','${departInfo.DEPART_NAME}','${departInfo.BELONG_NO}','${departInfo.DIC_DESC}','${departInfo.IS_TAKE}')" ><img src="webpage/call/takeNo/imagesnew/icon${departInfo.ICON_NO}.png">${departInfo.DEPART_NAME}</a></li>
	           		</c:when>
	           		<c:otherwise>
							<li class="color${infoStatus.index+1}"><a href="javascript:void(0);" onclick="toChildDepart('${departInfo.DEPART_ID}','${departInfo.DEPART_NAME}','${departInfo.BELONG_NO}','${departInfo.DIC_DESC}','${departInfo.IS_TAKE}')" ><img src="webpage/call/takeNo/imagesnew/icon${departInfo.ICON_NO}.png">${departInfo.DEPART_NAME}</a></li>
					</c:otherwise>
	           	</c:choose>
			</c:forEach>
            </ul>
        </div>
        <div class="hd">
            <a class="next"></a>
            <ul></ul>
            <a class="prev"></a>
        </div>        
    </div>
    
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript">
		jQuery(".scroll-left").slide({titCell:".hd ul",mainCell:".bd",autoPage:true,effect:"left",pnLoop:false,scroll:1,vis:1,delayTime:200,trigger:"click"});

		function backToParent(){
	    	location.href="callController.do?toTypeChoose&roomNo=${roomNo}";
			//history.go(-1);
		}
		window.onload=function(){
	        window.setTimeout("backToTop()", 50 * 1000);
	    }
		function backToTop(){
			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
		}  
	    function toChildDepart(departId,departName,roomNo,lc,isTake){
	    	var now=new Date();            //创建Date对象
	        var hour=now.getHours();    //获取小时
	        var minute=now.getMinutes();    //获取分钟
            if(hour<7||(hour==7&&minute<30)){
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
// 	        if(hour>=12&&(hour<13||(hour==13&&minute<50))){
// 	        	parent.art.dialog({
// 						content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">该时间段暂不提供取号服务！</font>",
// 						icon:"warning",
// 						time:3,
// 						width:"400px",
// 						height:"150px",
// 						ok: false
// 					});
// 				return false;
// 	        }
	        if(hour>=29){
	        	if(minute>30){
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
	    	location.href="callController.do?toChildDepart&departId="+departId+"&departName="+departName+"&roomNo="+roomNo;
	    	}
	    }
    </script>
</body>
</html>
