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

	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">请选择办事部门！</a></div>
    	
    <div class="scroll-left">
        <div class="bd">
            <!--<ul class="scroll-list nocolor">如需彩色背景块，去除nocolor-->
           	<c:forEach items="${childList}" var="childInfo" varStatus="infoStatus">
           		<c:choose>
	           		<c:when test="${infoStatus.index==0}">
           				<ul class="scroll-list nocolor">
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${childInfo.DEPART_ID}','${childInfo.DEPART_NAME}','${childInfo.IS_TAKE}','${childInfo.PARENT_ID}')" >${childInfo.DEPART_NAME}</a></li>
						</c:when>
	           		<c:when test="${infoStatus.index%12==0}">
	           			</ul>
           				<ul class="scroll-list nocolor">
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${childInfo.DEPART_ID}','${childInfo.DEPART_NAME}','${childInfo.IS_TAKE}','${childInfo.PARENT_ID}')" >${childInfo.DEPART_NAME}</a></li>
					</c:when>
	           		<c:otherwise>
							<li class="color1"><a href="javascript:void(0);" onclick="readCard('${childInfo.DEPART_ID}','${childInfo.DEPART_NAME}','${childInfo.IS_TAKE}','${childInfo.PARENT_ID}')" >${childInfo.DEPART_NAME}</a></li>
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
			//window.parent.document.getElementById('takeFrame').src="callController.do?toDeptChoose";
			//location.href="callController.do?toDeptChoose";
			//history.go(-1);			
			window.parent.document.getElementById('takeFrame').src="callController.do?toDeptChoose&roomNo=${roomNo}";
		}
		
		function readCard(departId,departName,isTake,parentId){
			if(parentId=='2c90b38a5779662501579f6a0b820e0d'){//不动产登记中心
				if(
						(departName=='抵押权登记、预告登记'
								||departName=='安置房登记'
								||departName=='维修资金管理'
								||departName=='查封解封'
								||departName=='预告批量')
							&&"${roomNo}"!='A'){
		    		parent.art.dialog({
						content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往一楼A厅取号！</font>",
						icon:"warning",
						time:3,
						width:"400px",
						height:"150px",
						ok: false
					});
		    	}else if(departName!='抵押权登记、预告登记'
		    			&&departName!='安置房登记'
		    			&&departName!='预告批量'
			    		&&departName!='维修资金管理'
				    	&&departName!='查封解封'
		    			&&"${roomNo}"!='D'){
		    		parent.art.dialog({
						content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往二楼D厅取号！</font>",
						icon:"warning",
						time:3,
						width:"400px",
						height:"150px",
						ok: false
					});
		    	}else {
					window.parent.document.getElementById('takeFrame').src="webpage/call/takeNo/readCard.jsp?departId="+departId+"&departName="+departName+"&parentId="+parentId+"&roomNo=${roomNo}";
		    	}
			}else{
				if(isTake=='0'){
		    		parent.art.dialog({
							content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往人工导引台咨询取号！</font>",
							icon:"warning",
							time:3,
							width:"400px",
							height:"150px",
							ok: false
						});
		    	}else{
					window.parent.document.getElementById('takeFrame').src="webpage/call/takeNo/readCard.jsp?departId="+departId+"&departName="+departName+"&parentId="+parentId+"&roomNo=${roomNo}";
		    	}
			}
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
