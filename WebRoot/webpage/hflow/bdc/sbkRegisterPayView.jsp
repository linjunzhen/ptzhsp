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
    <title>社保卡缴费</title>
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
    .display_box{
        display: flex;
        justify-content: center;
        align-items: center;
        flex-wrap: nowrap;
    }
    .current-ziti{
        font-size: 16px;
    }
    .box-margin{
        margin-top: 5px;
    }
    .box-padding{
        padding:0px 5px;
    }
</style>

<script>

</script>

<body class="eui-bodyBg" style="background: #fff;margin:0px;padding: 0px;">
<!--页面主体-->
<div class="mui-content">
    <div class="eui-wrap" style="padding-left: 10px;padding-right: 10px;">
        <div class="title"><span>社保卡缴费</span></div>
        <div>
            <div class="display_box box-margin current-ziti">请长按二维码图片进行识别缴费登记</div>
            <div class="display_box box-margin current-ziti">支付前请确认收款账号为:</div>
            <div class="display_box box-margin current-ziti" style="color: #990000;">【平潭综合实验区行政服务中心】</div>
            <div class="display_box"><img style="width: 250px;height: auto;" src="<%=path%>/webpage/site/bdc/info/images/payImg.png"></div>
            <div class="current-ziti box-padding" style="margin-top: 10px;">付款须知：</div>
            <div class="current-ziti box-padding">①社保卡缴费标准：首次办理免费，补、换卡25元/张</div>
            <div class="current-ziti box-padding">②支付时请务必添加备注：您的缴费类型及个人信息(不限姓名、身份证等)</div>
            <div class="current-ziti box-padding">③业务咨询：12345</div>
            <div class="current-ziti box-padding">④退款及申诉：15659416227，联系人：池女士</div>
        </div>
    </div>

</div>
<script>

</script>
</body>
</html>
