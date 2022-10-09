<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String roomNo = request.getParameter("roomNo");
request.setAttribute("roomNo", roomNo);
String businessCode = request.getParameter("businessCode");
request.setAttribute("businessCode", businessCode);
String businessName = request.getParameter("businessName");
request.setAttribute("businessName", businessName);
String departId=request.getParameter("departId");
request.setAttribute("departId",departId);
String itemCode=request.getParameter("itemCode");
request.setAttribute("itemCode",itemCode);
String itemName=request.getParameter("itemName");
request.setAttribute("itemName",itemName);
String defKey=request.getParameter("defKey");
request.setAttribute("defKey",defKey);
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

	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请选择办事部门！</a></div>
    	
    <div class="scroll-left">
        <div class="bd">
           	<c:forEach items="${materTypeList}" var="materTypeInfo" varStatus="infoStatus">
           		<c:choose>
	           		<c:when test="${infoStatus.index==0}">
           				<ul class="scroll-list nocolor">
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${materTypeInfo.RECORD_ID}')" >${materTypeInfo.BUSCLASS_NAME}</a></li>
						</c:when>
	           		<c:when test="${infoStatus.index%12==0}">
	           			</ul>
           				<ul class="scroll-list nocolor">
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${materTypeInfo.RECORD_ID}')" >${materTypeInfo.BUSCLASS_NAME}</a></li>
					</c:when>
	           		<c:otherwise>
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${materTypeInfo.RECORD_ID}')" >${materTypeInfo.BUSCLASS_NAME}</a></li>
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
			window.parent.document.getElementById('takeFrame').src="newCallController/toDeptChoose.do?roomNo=${roomNo}";
		}
		
		function readCard(materTypeId){					
			window.parent.document.getElementById('takeFrame').src="webpage/callnew/takeNo/readCard.jsp?materTypeId="+
			materTypeId+"&roomNo=${roomNo}&businessCode=${businessCode}&businessName=${businessName}&departId=${departId}&itemCode=${itemCode}&itemName=${itemName}&defKey=${defKey}";
		}
	    
		window.onload=function(){
	        window.setTimeout("backToTop()", 50 * 1000);
	    }
	    
		function backToTop(){
			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
		}
    </script> 
</body>
</html>
