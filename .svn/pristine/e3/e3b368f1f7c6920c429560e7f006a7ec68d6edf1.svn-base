<%@page import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
// 获取取号事项列表
Map<String, Object> itemCart = AppUtil.getItemCart();
request.setAttribute("itemCart", itemCart);
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
	<script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.min.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="webpage/callYctb/takeNo/js/idangerous.swiper.min.js"></script>
</head>

<body class="bg-none macw" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

	<div class="eui-title">
		<a class="back" href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png"></a>
		目前仅支持试点业务！
		<a href="javascript:void(0)" onclick="toItemCart();" class="qhlb">已选列表<i>&gt;</i><em id="qhlbNum">${itemNum}</em></a>
	</div>
<!-- 	<div class="eui-title"><a href="javascript:void(0);" >目前仅支持试点业务！</a></div> -->

	<div class="slideTxtBox eui-tab">
		<div class="hd">
			<ul>
				<e:for filterid="1" end="100" var="efor" dsid="175">
				<li><i></i>${efor.TYPE_NAME}</li>
				</e:for>
				<li><i></i>批量</li>
				<li><i></i>闽通卡</li>
				<li><i></i>公证处</li>
				<li><i></i>跨省通办</li>
				<li><i></i>银行公积金</li>
				<!-- <li><i></i>法律援助</li> -->
			</ul>
		</div>
		<div class="bd">
		
			<e:for filterid="1" end="100" var="efor" dsid="175">
			<div>
				<div class="eui-tit">${efor.TYPE_ALIAS}</div>
				<div class="multipleColumn mul${efor.ROWNUM_}">
					<div class="eui-sx">
					   
						<e:for filterid="${efor.YCTBQH_ID}" end="1000" var="efor1" dsid="176">
					    	<li>
							<a href="javascript:void(0);" onclick="chooseMaterType('${efor1.ITEM_CODE}'
							,'${efor1.ITEM_NAME}','${efor1.DEF_KEY}',
							'${efor1.BUSINESS_CODE}','${efor1.BUSINESS_NAME}')"  
							
								<c:forEach items="${itemCart}" var="item" varStatus="s">
									<c:if test="${item.key==efor1.ITEM_CODE}">
									style="color:red"
									</c:if>
								</c:forEach>
							>${efor1.ITEM_NAME}
							</a>
							</li>						
						</e:for>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
				    </div>
				</div>
				 <script type="text/javascript">
					jQuery(".mul${efor.ROWNUM_} .eui-sx li").each(function(i){ jQuery(".mul${efor.ROWNUM_} .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
				 </script>
			</div>
			</e:for>
			<div>
				<div class="eui-tit">批量件</div>
				<div class="multipleColumn mul_6">
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
			<div>
				<div class="eui-tit">闽通卡</div>
				<div class="multipleColumn mul_5">
					<div class="eui-sx">
						<li><a href="javascript:void(0);" onclick="readCard('093','闽通卡服务','1','2c90b38a67a6266d0167ab958b94619b','B','闽通卡服务')" >闽通卡服务</a></li>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
					</div>
				</div>
			</div>
			<div>
				<div class="eui-tit">公证业务</div>
				<div class="multipleColumn mul_7">
					<div class="eui-sx">
						<ul>
							<li><a href="javascript:void(0);" onclick="readCard('130','公证业务','1','2c90b38a67a6266d0167ab958b94619b','B','公证业务')" >公证业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('116','跨省通办业务','1','2c90b38a67a6266d0167ab958b94619b','B','跨省通办业务')" >跨省通办业务</a></li>
						</ul>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
					</div>
				</div>
			</div>
			
			<div>
				<div class="eui-tit">跨省通办</div>
				<div class="multipleColumn mul_8">
					<div class="eui-sx">
						<ul>
							<li><a href="javascript:void(0);" onclick="readCard('121','不动产类业务','1','2c90b38a67a6266d0167ab958b94619b','A','不动产类业务')" >不动产类业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('120','公积金类业务','1','2c90b38a67a6266d0167ab958b94619b','A','公积金类业务')" >公积金类业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('111','医保类业务','1','2c90b38a67a6266d0167ab958b94619b','A','医保类业务')" >医保类业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('115','社保类业务','1','2c90b38a67a6266d0167ab958b94619b','A','社保类业务')" >社保类业务</a></li>
						</ul>
					</div>
					<div class="ft">
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
					</div>
				</div>
			</div>
			<div>
				<div class="eui-tit">银行公积金</div>
				<div class="multipleColumn mul_9">
					<div class="eui-sx">
						<ul>
							<li><a href="javascript:void(0);" onclick="readCard('431','工商银行公积金业务','1','2c90b38a67a6266d0167ab958b94619b','A','工商银行公积金业务')" >工商银行公积金业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('445','中国银行公积金业务','1','2c90b38a67a6266d0167ab958b94619b','A','中国银行公积金业务')" >中国银行公积金业务</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('446','建设银行公积金业务','1','2c90b38a67a6266d0167ab958b94619b','A','建设银行公积金业务')" >建设银行公积金业务</a></li>
						</ul>
					</div>
					<div class="ft"> 
						<a class="prev1">上一页</a>
						<ul></ul>
						<a class="next1">下一页</a>
					</div>
				</div>
			</div>
			<div>
				<div class="eui-tit">法律援助业务</div>
				<div class="multipleColumn mul_10">
					<div class="eui-sx">
						<li><a href="javascript:void(0);" onclick="readCard('131','法律援助业务','1','2c90b38a67a6266d0167ab958b94619b','B','法律援助业务')" >法律援助业务</a></li>
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
	
    <script type="text/javascript">
        function readCard(businessCode,businessName,status,departId,belongRoom,lc){
            if(belongRoom!='${roomNo}'&&belongRoom!='B'){//不动产登记中心
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
                    var url=encodeURI("webpage/callYctb/takeNo/readCardWhite.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo="+belongRoom+"&takeRoomNo=${roomNo}");
                	window.parent.document.getElementById('takeFrame').src=url;
                }
            }
        }
        
        function readCardTwo(){
            var url=encodeURI("webpage/callYctb/takeNo/readCardWhite.jsp?businessCode="+'431'+"&businessName="+'工商银行公积金业务'+"&departId="+'2c90b38a67a6266d0167ab958b94619b'+"&roomNo="+'A'+"&takeRoomNo=${roomNo}");
        	window.parent.document.getElementById('takeFrame').src=url;
        }

        /* 使用js分组，每6个li放到一个ul里面 
		jQuery(".mul1 .eui-sx li").each(function(i){ jQuery(".mul1 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
		jQuery(".mul2 .eui-sx li").each(function(i){ jQuery(".mul2 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
		jQuery(".mul3 .eui-sx li").each(function(i){ jQuery(".mul3 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
		jQuery(".mul4 .eui-sx li").each(function(i){ jQuery(".mul4 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});*/
		jQuery(".mul_5 .eui-sx li").each(function(i){ jQuery(".mul_5 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
		/* 调用SuperSlide，每次滚动一个ul，相当于每次滚动6个li */
		jQuery(".multipleColumn").slide({titCell:".ft ul",mainCell:".eui-sx",prevCell:".prev1",nextCell:".next1",autoPage:true,effect:"leftLoop"});
		  
		jQuery(".slideTxtBox").slide({effect:"fade"});
	    
		function backToParent(){		
			window.parent.document.getElementById('takeFrame').src="callController.do?toYctbTypeChooseMacW&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
		}
		function chooseMaterType(itemCode,itemName,defKey,businessCode,businessName){	
	    	var now=new Date();            //创建Date对象
	        var hour=now.getHours();    //获取小时
	        var minute=now.getMinutes();    //获取分钟
	        if(hour>30||(hour==30&&minute>30)){
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
	        var url="<%=path%>/callYctbController/toMaterChooseMacW.do?itemCode="+itemCode+"&itemName="
                +encodeURIComponent(itemName)+"&defKey="+defKey+"&businessName="+encodeURIComponent(businessName)+"&businessCode="+businessCode+
                "&roomNo=${roomNo}&departId=${departId}&ifMaterPrint=${ifMaterPrint}&macType=${macType}";
	    	location.href=url;
		}
		function toItemCart(){
			var num = $("#qhlbNum").html();
			if(num>0){				
				window.parent.document.getElementById('takeFrame').src="<%=path%>/callYctbController/toItemCartMacW.do";
			} else{
				parent.art.dialog({
					content: "<font style=\"font-size:24px;font-family:\"汉仪综艺体简\";\">请先选择需要取号的事项！</font>",
					icon:"warning",
					time:3,
					width:"400px",
					height:"150px",
					ok: true
				});
			}
		}
    </script> 
</body>
</html>
