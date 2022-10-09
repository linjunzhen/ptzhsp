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
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callYctb/takeNo/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/callYctb/takeNo/css/idangerous.swiper.css">
    <script type="text/javascript" src="<%=path%>/webpage/callYctb/takeNo/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/callYctb/takeNo/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/callYctb/takeNo/js/jquery.SuperSlide.2.1.2.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/callYctb/takeNo/js/idangerous.swiper.min.js"></script>
</head>

<body class="bg-none macw" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

<div class="eui-title">
    <a class="back" href="javascript:void(0);" onclick="backToParent();"><img src="<%=path%>/webpage/callYctb/takeNo/images/back.png"></a>
    目前仅支持试点业务！
    <a href="javascript:void(0)" onclick="toItemCart();" class="qhlb">已选列表<i>&gt;</i><em id="qhlbNum">${itemNum}</em></a>
</div>
	<div class="slideTxtBox eui-tab">
		<div class="hd">
			<ul>
				<li><i></i>省内通办</li>
			</ul>
		</div>
		<div class="bd">
			<div>
				<div class="eui-tit">省内通办</div>
				<div class="multipleColumn mul_8">
					<div class="eui-sx">
						<ul>
							<li><a href="javascript:void(0);" onclick="readCard('163','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >企业申请迁出登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('164','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >企业申请准迁登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('165','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >有限责任公司申请准迁登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('166','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >有限责任公司申请迁出登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('167','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >股份有限公司申请准迁登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('168','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >股份有限公司申请迁出登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('169','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >经营性道路客货运输车辆道路运输证换发</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('170','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >经营性道路客货运输车辆道路运输证配发</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('171','省内通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','省内通办类业务')" >企业登记基本情况表查询</a></li>
						</ul>
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
                var url=encodeURI("webpage/callnew/takeNo/readCardWhite.jsp?businessCode="+businessCode+"&businessName="+businessName+"&departId="+departId+"&roomNo="+belongRoom+"&takeRoomNo=${roomNo}");
            	window.parent.document.getElementById('takeFrame').src=url;
            }
        }
	
	
    jQuery(".mul_5 .eui-sx li").each(function(i){ jQuery(".mul_5 .eui-sx li").slice(i*50,i*50+50).wrapAll("<ul></ul>");});
    /* 调用SuperSlide，每次滚动一个ul，相当于每次滚动6个li */
    jQuery(".multipleColumn").slide({titCell:".ft ul",mainCell:".eui-sx",prevCell:".prev1",nextCell:".next1",autoPage:true,effect:"leftLoop"});

    jQuery(".slideTxtBox").slide({effect:"fade"});

    function backToParent(){
        window.parent.document.getElementById('takeFrame').src="callYqyzController/toYqyzZzTypeMacW.do?roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}&cleanItem=1";
    }
    function chooseMaterType(itemCode,itemName,defKey,businessCode,businessName){
        var now=new Date();            //创建Date对象
        var hour=now.getHours();    //获取小时
        var minute=now.getMinutes();    //获取分钟
        // if(hour>20||(hour==20&&minute>15)){
        //     parent.art.dialog({
        //         content: "<font style=\"font-size:30px;font-family:\"汉仪综艺体简\";\">该时间段暂不提供取号服务！</font>",
        //         icon:"warning",
        //         time:3,
        //         width:"400px",
        //         height:"150px",
        //         ok: false
        //     });
        //     return false;
        // }
        var url="<%=path%>/callYqyzController/toMaterChooseMacW.do?itemCode="+itemCode+"&itemName="
            +encodeURIComponent(itemName)+"&defKey="+defKey+"&businessName="+encodeURIComponent(businessName)+"&businessCode="+businessCode+
            "&roomNo=${roomNo}&departId=${departId}&ifMaterPrint=${ifMaterPrint}&macType=${macType}&zzType=${zzType}";
        location.href=url;
    }
    function toItemCart(){
        var num = $("#qhlbNum").html();
        if(num>0){
            window.parent.document.getElementById('takeFrame').src="<%=path%>/callYqyzController/toYqyzItemCartMacW.do?roomNo=${roomNo}&departId=${departId}&zzType=${zzType}";
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