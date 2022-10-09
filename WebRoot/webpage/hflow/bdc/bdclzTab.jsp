<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
    String itemName = request.getParameter("ITEM_NAME");
    request.setAttribute("itemName", itemName);
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>不动产领证</title>
    <link rel="stylesheet" href="<%=path%>/webpage/declare/css/mui.min.css"/>
    <link rel="stylesheet" href="<%=path%>/webpage/declare/css/eui.css"/>
    <script type="text/javascript" src="<%=path%>/webpage/declare/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/layer-1.8.5/layer.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/declare/js/mui.min.js"></script>
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/zepto.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <!-- 必引cordova.js，实现与原生的交互 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/cordova.js"></script>
    <!-- 含闽政通获取地市信息，网络状态等方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/bingotouch.js"></script>
    <!-- 含闽政通获取用户信息，分享，获取版本号，人脸识别方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jmtplugins.js"></script>
    <!-- js加解密 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jsencrypt.js"></script>
</head>

<style>
    .title{
        display: flex;
        justify-content: center;
        height: 40px;
        align-items: center;
        font-weight: 700;
        font-size: 20px;
        padding-top: 10px;
        border-bottom: 1px solid #ccc;
        padding-bottom: 10px;
    }
    .current-div{
        margin-top: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .current-button{
        padding: 10px 40px;
        width: 150px;
    }
</style>

<script>
    function tobdcqzAppointView() {
        window.location.href = "<%=path%>/bdcAppointController/bdcqzAppointView.do";
    }

    function tobdcRegisterPayView() {
        window.location.href = "<%=path%>/bdcAppointController/bdcRegisterPayView.do";
    }
</script>

<body class="eui-bodyBg" style="background: #fff;margin:0px;padding: 0px;">
<!--页面主体-->
<div class="mui-content">
    <div class="eui-wrap">
        <div class="title"><span>不动产领证</span></div>
        <div class="current-div">
            <button class="current-button" onclick="tobdcqzAppointView()">预约领证</button>
        </div>
<%--        <div class="current-div">--%>
<%--            <button class="current-button" onclick="tobdcRegisterPayView()">缴交登记费</button>--%>
<%--        </div>--%>
    </div>

</div>
<script>

</script>
</body>
</html>
