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
				<li><i></i>跨省通办</li>
			</ul>
		</div>
		<div class="bd">
			<div>
				<div class="eui-tit">跨省通办</div>
				<div class="multipleColumn mul_8">
					<div class="eui-sx">
						<ul>
							<li><a href="javascript:void(0);" onclick="readCard('141','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >内资企业及分支机构设立登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('142','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >内资企业及分支机构变更登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('143','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >内资企业及分支机构注销登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('144','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >外资企业及分支机构设立登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('145','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >外资企业及分支机构变更登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('146','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >外资企业及分支机构注销登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('147','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >个体工商户设立登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('148','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >个体工商户变更登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('149','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >个体工商户注销登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('150','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >农民专业合作社设立登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('151','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >农民专业合作社变更登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('152','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >农民专业合作社注销登记</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('153','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >营业执照遗失补领、换发</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('154','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >计量器具型式批准</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('155','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >检验检测机构资质认定</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('156','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >工业产品生产许可证核发（含5个子项）</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('157','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >特种设备检验机构核准（含2个子项）</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('158','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >特种设备生产单位资格许可（含10个子项）</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('159','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >特种设备充装单位资格许可（含2个子项）</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('160','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >特种设备作业人员资格许可</a></li>
							<li><a href="javascript:void(0);" onclick="readCard('161','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >执业药师注册 </a></li>
							<li><a href="javascript:void(0);" onclick="readCard('162','跨省通办类业务','1','2c90b38a67a6266d0167ab958b94619b','D','跨省通办类业务')" >消毒产品卫生安全评价报告备案</a></li>
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