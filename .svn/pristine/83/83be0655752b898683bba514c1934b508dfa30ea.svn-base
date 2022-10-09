<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html lang="zh-cn">
<head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="expires" content="0" />
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>自助取号系统</title>
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/style.css">
	<link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/idangerous.swiper.css">
</head>

<body class="bg-none">

	
	<div class="eui-title">
		<a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png">目前仅支持试点业务！</a>
	</div>
    	
    	
    <div class="eui-sx-detail">
		<div class="eui-tit">取号列表</div>
		<div class="eui-con">
		    <div class="swiper-container" style="height: 410px;">
		    	<div class="swiper-wrapper">
		    		<div class="swiper-slide eui-qh-list" id="qhlbDiv">
						<c:forEach items="${itemCart}" var="itemcart" varStatus="s">
							<div class="item" onclick="delItemCart('${itemcart.value.itemCode}')"><span><i></i>${itemcart.value.itemName}</span></div>
						</c:forEach>
					</div>
				</div>
			</div>
			<c:if test="${itemNum>0}">
			<div class="eui-qh-btn"><a class="eui-btn" href="javascript:void(0)" onclick="readCard();">确认取号</a></div>
			</c:if>
		</div>
    </div>
	

	<script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.min.js"></script>
	<script type="text/javascript" src="webpage/callYctb/takeNo/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript">
		  var mySwiper = new Swiper('.swiper-container',{
		    paginationClickable: true,
		    mode: 'vertical',
		    slidesPerView: 'auto',
		    freeMode: true,
		    freeModeFluid: true
		  })
		  
		function backToParent(){		
			window.parent.document.getElementById('takeFrame').src="callYctbController/toYctbBusinessItemChooseMacW.do?roomNo=${itemMap.roomNo}&ifMaterPrint=${itemMap.ifMaterPrint}&departId=${itemMap.departId}";
		}
		
		function readCard(){					
			window.parent.document.getElementById('takeFrame').src="callYctbController/yctbReadCardWhite.do";
		}
		
		function delItemCart(code){			
			$.post("callYctbController/removeItemCart.do",{
				itemCode:code
			}, function(responseText, status, xhr) {
				if(responseText&&responseText!="websessiontimeout"){
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
						/* parent.art.dialog({
							content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
							icon:"succeed",
							time:10,
							width:"400px",
							height:"150px",
							ok: true,
							close: function(){		
								var num = $("#qhlbDiv").children().length;
								toItemCart(num-1);
							}
						});	 */	
						var num = $("#qhlbDiv").children().length;
						toItemCart(num-1);						
					}else{
						parent.art.dialog({
							content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
							icon:"warning",
							time:10,
							width:"400px",
							height:"150px",
							ok: true
						});
					}
				}
			});
		}
		
		function toItemCart(num){
			if(num>0){
				window.parent.document.getElementById('takeFrame').src="<%=path%>/callYctbController/toItemCartMacW.do";				
			} else{
				backToParent();
			}
		}
    </script> 
</body>
</html>