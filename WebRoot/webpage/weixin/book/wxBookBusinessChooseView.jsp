<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
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
    <title>${param7}</title>
    <link rel="stylesheet" href="<%=basePath%>/webpage/declare/css/mui.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/webpage/declare/css/eui.css"/>
    <script type="text/javascript" src="<%=basePath%>/webpage/declare/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/declare/js/mui.min.js"></script>
    <script src="js/mobileUtil.js"></script>
</head>
<script>
    function toBookListView(departId,businessCode) {
        location.href = "<%=path%>/busInteractController.do?wxBookListView&departId=" + departId + "&businessCode=" + businessCode;
    }
</script>
<body class="eui-bodyBg">
<!--页面主体-->
<div class="mui-content">
    <ul class="mui-table-view">
        <c:forEach items="${list}" var="dataList">
            <li class="mui-table-view-cell" onclick="toBookListView('${dataList.DEPART_ID}','${dataList.BUSINESS_CODE}')">${dataList.BUSINESS_NAME}</li>
        </c:forEach>
    </ul>
</div>
</body>
</html>