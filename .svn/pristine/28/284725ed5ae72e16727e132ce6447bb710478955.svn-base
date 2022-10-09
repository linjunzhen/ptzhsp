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
<div>
    <a class="back" href="javascript:void(0);" onclick="backToParent();" style="position: absolute;cursor: pointer;z-index: 999;"><img src="<%=path%>/webpage/callYctb/takeNo/images/back.png"></a>
    <div class="eui-title">请选择取号类型！</div>
</div>
<div class="scrollt-left">
    <ul class="scrollt-list" >
        <li >
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=yyzz" style="width: 176px;">
            <img src="webpage/callYqyz/takeNo/images/yyzz.png">
                <span style="font-size:22px;font-weight:bold;">营业执照</span>
            </a>
        </li>
        <li >
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=xkzj" style="width: 176px;">
                <img src="webpage/callYqyz/takeNo/images/xkzj.png">
                <span style="font-size:22px;font-weight:bold;">许可证件</span>
            </a>
        </li>
        <%--<li >
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=zzhb" style="width: 176px;">
            <img src="webpage/callYqyz/takeNo/images/zzhb.png">
                <span style="font-size:22px;font-weight:bold;">证照合办</span>
            </a>
        </li>--%>
        <li>
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=yjstc" style="width: 176px;">
                <img src="webpage/call/takeNo/images/toReadCardAppoint.png">
                <span style="font-size:22px;font-weight:bold;">一件事套餐</span><br>
            </a>
        </li>
        <li>
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=kstb" style="width: 176px;">
                <img src="webpage/call/takeNo/images/toReadCardAppoint.png">
                <span style="font-size:22px;font-weight:bold;">跨省通办</span><br>
            </a>
        </li>
        <li>
            <a href="callYqyzController/toYqyzBusinessItemChooseMacW.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&cleanItem=1&zzType=Sntb" style="width: 176px;">
                <img src="webpage/call/takeNo/images/toReadCardAppoint.png">
                <span style="font-size:22px;font-weight:bold;">省内通办</span><br>
            </a>
        </li>
    </ul>
</div>

<script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.min.js"></script>
<script type="text/javascript" src="webpage/call/takeNo/jsnew/jquery.SuperSlide.2.1.2.js"></script>
<script type="text/javascript">
    jQuery(".scroll-left").slide({titCell:".hd ul",mainCell:".bd",autoPage:true,effect:"left",pnLoop:false,scroll:1,vis:1,delayTime:200,trigger:"click"});
    function backToParent() {
        window.parent.document.getElementById('takeFrame').src="callController.do?toYqyzTypeChooseMacW&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}";
    }

    function toQykbUrl() {

        var url = encodeURI("webpage/callYctb/takeNo/readCardWhite.jsp?businessCode=100&businessName=企业开办&departId=2c93f48251a4554f0151a46923c8000f&roomNo=D&takeRoomNo=D&zzType=qykb");
        window.parent.document.getElementById('takeFrame').src=url;
    }

</script>
</body>
</html>
