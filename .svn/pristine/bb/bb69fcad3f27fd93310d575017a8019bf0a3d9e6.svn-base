﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <title>平潭综合实验区不动产登记与交易</title>
    <!-- CSS -->
    <base href="<%=basePath%>">
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
    <!-- JS -->
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.min.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>
    <style>
        .display_box{
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>

<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>

	<div class="eui-main">	
		<div style="width:1200px;margin:auto;height:630px;background-color:white;" class="display_box">
			<div>
				<div class="display_box" style="margin-bottom: 10px"><span>请扫描以下二维码进行支付，支付前请确认收款账号为: 【<span style="color: #990000;">平潭综合实验区行政服务中心</span>】</span></div>
				<div class="display_box" style="font-size: 30px;font-weight: 700;">扫一扫付款</div>
				<div class="display_box"><img src="<%=path%>/webpage/site/bdc/info/images/payImg.png" style="width: 250px;height: auto;"></div>
				<div class="display_box" style="font-weight: 500;font-size: 25px;margin-bottom: 20px;">平潭综合实验区行政服务中心</div>
				<div class="display_box" style="justify-content: center;">
					<div>
						<img src="<%=path%>/webpage/site/bdc/info/images/weixin.png" style="width: 50px;height: auto;margin-right: 30px;">
						<div>微信支付</div>
					</div>
					<div>
						<img src="<%=path%>/webpage/site/bdc/info/images/zhifubao.png" style="width: 50px;height: auto;">
						<div>支付宝</div>
					</div>
				</div>
				<div>付款须知：</div>
				<div>①不动产登记费(住宅80元，非住宅550元)</div>
				<div>②不动产工本费(10元)</div>
				<div>③支付时务必添加备注：您的申报号及个人真实姓名、身份证</div>
				<div>④退款及申诉：15659416227，联系人：池女士</div>
			</div>
		</div>
	</div>

<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>

	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>

</body>
</html>