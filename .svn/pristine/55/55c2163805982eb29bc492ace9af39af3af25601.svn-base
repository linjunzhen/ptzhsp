<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/style.css">
	<link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/idangerous.swiper.css">
</head>

<body class="bg-none" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title">
		<a class="back" href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png"></a>		
		目前仅支持试点业务！
		<a href="javascript:void(0)" onclick="toItemCart();" class="qhlb">已选列表<i>&gt;</i><em id="qhlbNum">${itemNum}</em></a>
	</div>
    	

    <div class="eui-sx-detail">
		<div class="eui-tit">${itemName}</div>
		<div class="eui-con">
		    <div class="swiper-container swiper6">
		    	<div class="swiper-wrapper">
		    		<div class="swiper-slide">
			           	<c:forEach items="${materTypeList}" var="materTypeInfo" varStatus="infoStatus">
							<div class="item">
							<c:if test="${ifMaterPrint!=1}">
							<a href="javascript:void(0);" ondblclick="readCard('${materTypeInfo.RECORD_ID}')" >
							</c:if>
								<div class="tit">${materTypeInfo.BUSCLASS_NAME}（双击进入下一步）</div>
								<div class="tit-sub">材料信息</div>
								<ul>
			           				<c:forEach items="${materList}" var="materInfo" varStatus="infoStatus">
				           				<c:choose>
							           		<c:when test="${materInfo.MATER_SSYW==''||materInfo.MATER_SSYW==null||materInfo.MATER_SSYW==undefined}">
												<li style="padding-right: 30px;">${materInfo.MATER_NAME}
							           			<c:if test="${ifMaterPrint==1}">
								           			<c:if test="${materInfo.FILE_ID1!=''&&materInfo.FILE_ID1!=null&&materInfo.FILE_ID1!=undefined}">
										             	<a href="DownLoadServlet?fileId=${materInfo.FILE_ID1}" target="_blank"><span style="text-decoration:underline;color:green;">材料模板下载</span></a>
													</c:if>
								           			<c:if test="${materInfo.FILE_ID2!=''&&materInfo.FILE_ID2!=null&&materInfo.FILE_ID2!=undefined}">
										             	<a href="DownLoadServlet?fileId=${materInfo.FILE_ID2}" target="_blank"><span style="text-decoration:underline;color:green;">示例模板下载</span></a>
													</c:if>
												</c:if>
												</li>
											</c:when>
				           					<c:when test="${fn:contains(materInfo.MATER_SSYW,materTypeInfo.RECORD_ID)}"> 
												<li style="padding-right: 30px;">${materInfo.MATER_NAME}
							           			<c:if test="${ifMaterPrint==1}">
								           			<c:if test="${materInfo.FILE_ID1!=''&&materInfo.FILE_ID1!=null&&materInfo.FILE_ID1!=undefined}">
										             	<a href="DownLoadServlet?fileId=${materInfo.FILE_ID1}" target="_blank"><span style="text-decoration:underline;color:green;">材料模板下载</span></a>
													</c:if>
								           			<c:if test="${materInfo.FILE_ID2!=''&&materInfo.FILE_ID2!=null&&materInfo.FILE_ID2!=undefined}">
										             	<a href="DownLoadServlet?fileId=${materInfo.FILE_ID2}" target="_blank"><span style="text-decoration:underline;color:green;">示例模板下载</span></a>
													</c:if>
												</c:if>
												</li>
											</c:when>
										</c:choose>
									</c:forEach>
								</ul>
							<c:if test="${ifMaterPrint!=1}">
							</a>
							</c:if>
							<c:forEach items="${itemCart}" var="item" varStatus="s">
								<c:if test="${item.key==itemCode}">	
									<c:if test="${item.value.materTypeId==materTypeInfo.RECORD_ID}">										
										<div class="eui-sx-btn">
											<a class="eui-btn" href="javascript:void(0)">已选择</a>
											<a class="eui-btn outline" href="javascript:void(0)" onclick="delItemCart('${itemCode}');">取消</a>
										</div>
									</c:if>
								</c:if>
							</c:forEach>
							</div>
			           	</c:forEach>
			    	</div>
				</div>
	    	</div>
		</div>
    </div>

	<script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="webpage/callYctb/takeNo/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript">
		jQuery(".scroll-left").slide({titCell:".hd ul",mainCell:".bd",autoPage:true,effect:"left",pnLoop:false,scroll:1,vis:1,delayTime:200,trigger:"click"});
		
		  var mySwiper = new Swiper('.swiper6',{
		    paginationClickable: true,
		    mode: 'vertical',
		    slidesPerView: 'auto',
		    freeMode: true,
		    freeModeFluid: true
		  })
// 		滚动条
// 	    $(".slimscroll").slimScroll({
// 	    	alwaysVisible: true, //是否 始终显示组件
// 	    	disableFadeOut: true, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
// 	        width: 'auto', //可滚动区域宽度
// 	        opacity: 1, //滚动条透明度
// 	        height: '460px', //可滚动区域高度
// 	        color: '#145aa2', //滚动条颜色
// 	    });
	    
		function backToParent(){		
			window.parent.document.getElementById('takeFrame').src="callYctbController/toYctbBusinessItemChoose.do?roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
		}
		
		function readCard(materTypeId){					
			//window.parent.document.getElementById('takeFrame').src="webpage/callnew/takeNo/yctbReadCard.jsp?materTypeId="+
			//materTypeId+"&roomNo=${roomNo}&businessCode=${businessCode}&businessName=${businessName}&departId=${departId}&itemCode=${itemCode}&itemName=${itemName}&defKey=${defKey}&ifMaterPrint=${ifMaterPrint}";
	
			$.post("callYctbController/addItemCart.do",{
				businessCode:'${businessCode}',
				businessName:'${businessName}',
				roomNo:'${roomNo}',
				itemCode:'${itemCode}',
				itemName:'${itemName}',
				defKey:'${defKey}',
				materTypeId:materTypeId,
				ifMaterPrint:'${ifMaterPrint}',
				departId : '${departId}'
			}, function(responseText, status, xhr) {
				if(responseText&&responseText!="websessiontimeout"){
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
// 						parent.art.dialog({
// 							content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
// 							icon:"succeed",
// 							time:10,
// 							width:"400px",
// 							height:"150px",
// 							ok: true,
// 							lock: true,
// 							close: function(){
// 								location.reload();
// 								backToTop();
// 							}
// 						});
								location.reload();
								backToTop();
					}else{
// 						parent.art.dialog({
// 							content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
// 							icon:"warning",
// 							time:10,
// 							width:"400px",
// 							height:"150px",
// 							ok: true,
// 							lock: true,
// 							close: function(){
// 								backToTop();
// 							}
// 						});
								backToTop();
					}
				}
			});
		}
	    
		window.onload=function(){
	        window.setTimeout("backToParent()", 50 * 1000);
	    }
		/* 
	    function backToParent(){		
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChoose&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
		}
		 */
		function backToTop(){
			window.parent.document.getElementById('takeFrame').src="callYctbController/toYctbBusinessItemChoose.do?roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
		}
		
		function toItemCart(){
			var num = $("#qhlbNum").html();
			if(num>0){				
				window.parent.document.getElementById('takeFrame').src="<%=path%>/callYctbController/toItemCart.do";
			} else{
				parent.art.dialog({
					content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">请先选择需要取号的事项！</font>",
					icon:"warning",
					time:10,
					width:"400px",
					height:"150px",
					ok: true
				});
			}
		}
		
		function delItemCart(code){			
			$.post("callYctbController/removeItemCart.do",{
				itemCode:code
			}, function(responseText, status, xhr) {
				if(responseText&&responseText!="websessiontimeout"){
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
// 						parent.art.dialog({
// 							content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">"+resultJson.msg+"</font>",
// 							icon:"succeed",
// 							time:10,
// 							width:"400px",
// 							height:"150px",
// 							ok: true,
// 							close: function(){								
// 								location.replace(location.href)
// 							}
// 						});						
								location.replace(location.href);			
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
    </script> 
</body>
</html>
