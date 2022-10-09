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
    <link rel="stylesheet" type="text/css" href="webpage/callnew/takeNo/css/style.css">
    <link rel="stylesheet" type="text/css" href="webpage/callnew/takeNo/css/idangerous.swiper.css">
</head>

<body class="bg-none" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title"><a href="javascript:void(0);" onclick="backToParent();"><img src="webpage/call/takeNo/imagesnew/back.png">目前仅支持试点业务！</a></div>
<!-- 	<div class="eui-title"><a href="javascript:void(0);" >目前仅支持试点业务！</a></div> -->

	<div class="slideTxtBox eui-tab">
		<div class="hd">
			<ul>
				<li><i></i>不动产</li>
				<li><i></i>公积金</li>
				<li><i></i>医保</li>
				<li><i></i>社保</li>
				<li><i></i>批量</li>
			</ul>
		</div>
		<div class="bd">
			<div>
				<div class="eui-tit">不动产</div>
				<div class="multipleColumn mul1">
					<div class="eui-sx">
					    <c:forEach items="${businessItemList1}" var="businessItemListInfo" varStatus="infoStatus">
					    	<li><a href="javascript:void(0);" onclick="chooseMaterType('${businessItemListInfo.ITEM_CODE}','${businessItemListInfo.ITEM_NAME}','${businessItemListInfo.DEF_KEY}','${businessItemListInfo.BUSINESS_CODE}','${businessItemListInfo.BUSINESS_NAME}')" >${businessItemListInfo.ITEM_NAME}</a></li>
						</c:forEach>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
			</div>
			
			<div>
				<div class="eui-tit">公积金（失业保险、婚姻登记）</div>
				<div class="multipleColumn mul2">
					<div class="eui-sx">
					    <c:forEach items="${businessItemList2}" var="businessItemListInfo" varStatus="infoStatus">
					    	<li><a href="javascript:void(0);" onclick="chooseMaterType('${businessItemListInfo.ITEM_CODE}','${businessItemListInfo.ITEM_NAME}','${businessItemListInfo.DEF_KEY}','${businessItemListInfo.BUSINESS_CODE}','${businessItemListInfo.BUSINESS_NAME}')" >${businessItemListInfo.ITEM_NAME}</a></li>
						</c:forEach>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
			</div>
			<div>
				<div class="eui-tit">医保</div>
				<div class="multipleColumn mul3">
					<div class="eui-sx">
					    <c:forEach items="${businessItemList3}" var="businessItemListInfo" varStatus="infoStatus">
					    	<li><a href="javascript:void(0);" onclick="chooseMaterType('${businessItemListInfo.ITEM_CODE}','${businessItemListInfo.ITEM_NAME}','${businessItemListInfo.DEF_KEY}','${businessItemListInfo.BUSINESS_CODE}','${businessItemListInfo.BUSINESS_NAME}')" >${businessItemListInfo.ITEM_NAME}</a></li>
						</c:forEach>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
			</div>
			<div>
				<div class="eui-tit">社保</div>
				<div class="multipleColumn mul4">
					<div class="eui-sx">
					    <c:forEach items="${businessItemList4}" var="businessItemListInfo" varStatus="infoStatus">
					    	<li><a href="javascript:void(0);" onclick="chooseMaterType('${businessItemListInfo.ITEM_CODE}','${businessItemListInfo.ITEM_NAME}','${businessItemListInfo.DEF_KEY}','${businessItemListInfo.BUSINESS_CODE}','${businessItemListInfo.BUSINESS_NAME}')" >${businessItemListInfo.ITEM_NAME}</a></li>
						</c:forEach>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
			</div>
			<div>
				<div class="eui-tit">批量件</div>
				<div class="multipleColumn mul5">
					<div class="eui-sx">
					    <li><a href="javascript:void(0);" onclick="readCard('P','社保批量件','1','2c90b38a67a6266d0167ab958b94619b','A','社保批量件')" >批量件（五件以上）</a></li>
					    <li><a href="javascript:void(0);" onclick="readCard('L','“五险一金”关联件','1','2c90b38a67a6266d0167ab958b94619b','A','“五险一金”关联件')" >“五险一金”关联件</a></li>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="webpage/callnew/takeNo/js/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/callnew/takeNo/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="webpage/callnew/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="webpage/callnew/takeNo/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript">
        function readCard(businessCode,businessName,status,departId,belongRoom,lc){
            if(belongRoom!='${roomNo}'){//不动产登记中心
                parent.art.dialog({
                    content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往"+belongRoom+"厅取号！</font>",
                    icon:"warning",
                    time:3,
                    width:"400px",
                    height:"150px",
                    ok: false
                });
            }else{
                if(status=='0'){
                    parent.art.dialog({
                        content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往人工导引台咨询取号！</font>",
                        icon:"warning",
                        time:3,
                        width:"400px",
                        height:"150px",
                        ok: false
                    });
                }else{
					if('1'=='${macType}'){//白色机器	
						window.parent.document.getElementById('takeFrame').src="webpage/callnew/takeNo/readCardWhite.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo=${roomNo}";
					}else{
					    window.parent.document.getElementById('takeFrame').src="webpage/callnew/takeNo/readCard.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo=${roomNo}";
					}
                }
            }
        }

        /* 使用js分组，每6个li放到一个ul里面 */
		jQuery(".mul1 .eui-sx li").each(function(i){ jQuery(".mul1 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
		jQuery(".mul2 .eui-sx li").each(function(i){ jQuery(".mul2 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
		jQuery(".mul3 .eui-sx li").each(function(i){ jQuery(".mul3 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
		jQuery(".mul4 .eui-sx li").each(function(i){ jQuery(".mul4 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
		jQuery(".mul5 .eui-sx li").each(function(i){ jQuery(".mul5 .eui-sx li").slice(i*14,i*14+14).wrapAll("<ul></ul>");});
		/* 调用SuperSlide，每次滚动一个ul，相当于每次滚动6个li */
		jQuery(".multipleColumn").slide({titCell:".ft ul",mainCell:".eui-sx",prevCell:".prev1",nextCell:".next1",autoPage:true,effect:"leftLoop"});
		  
		jQuery(".slideTxtBox").slide({effect:"fade"});
	    
		function backToParent(){		
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChoose&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
		}
		function chooseMaterType(itemCode,itemName,defKey,businessCode,businessName){					
	    	location.href="<%=path%>/newCallController.do?toMaterChoose&itemCode="+itemCode+"&itemName="
	    	+encodeURIComponent(itemName)+"&defKey="+defKey+"&businessName="+encodeURIComponent(businessName)+"&businessCode="+businessCode+
	    	"&roomNo=${roomNo}&departId=${departId}&ifMaterPrint=${ifMaterPrint}&macType=${macType}";				
		}
		function chooseItem(businessCode,businessName,status,departId,belongRoom,lc){
			if(belongRoom!='${roomNo}'){//不动产登记中心
				parent.art.dialog({
					content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往"+belongRoom+"厅取号！</font>",
					icon:"warning",
					time:3,
					width:"400px",
					height:"150px",
					ok: false
				});
			}else{
				if(status=='0'){
		    		parent.art.dialog({
							content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">请前往人工导引台咨询取号！</font>",
							icon:"warning",
							time:3,
							width:"400px",
							height:"150px",
							ok: false
						});
		    	}else{
// 					window.parent.document.getElementById('takeFrame').src="webpage/callnew/takeNo/readCard.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo=${roomNo}";
	    			window.parent.document.getElementById('takeFrame').src="newCallController/toYctbItemChoose.do?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo=${roomNo}";
		    	}
			}
		}
	    
// 		window.onload=function(){
// 	        window.setTimeout("backToTop()", 50 * 1000);
// 	    }
	    
// 		function backToTop(){
// 			window.parent.document.getElementById('takeFrame').src="callController.do?toTypeChoose&roomNo=${roomNo}";
// 		}
    </script> 
</body>
</html>
