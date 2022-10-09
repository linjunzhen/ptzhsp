<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
    request.setAttribute("zcfgModuleId","627");
    request.setAttribute("downFileModuleId","628");
    request.setAttribute("projectListModuleId","629");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta name="renderer" content="webkit">
    <title>平潭综合实验区工程建设项目管理平台</title>
<%--    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/eui.css">
	<!-- layui CSS -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/layui-v2.4.5/layui/css/layui.css">
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript"
            src="<%=basePath%>/webpage/website/project/variableConstant.jsp"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/layui-v2.4.5/layui/layui.js"></script>
   <script type="text/javascript">
            function  toUrl(url) {
                window.location.href=__ctxPath+url;
            }
    </script>
</head>

<body>
<!-- 头部 -->
<div class="eui-con eui-flex vc eui-header">
    <%-- <div class="eui-logo">
        <a class="eui-flex vc" href="<%=basePath%>projectWebsiteController.do?index"><img src="<%=basePath%>/webpage/website/project/images/logo.png" ></a>
    </div> --%>
    <div class="eui-nav flex1">
        <ul class="eui-flex vc">
            <li <c:if test="${nav==1}">class="on" </c:if>><a href="<%=basePath%>projectWebsiteController.do?index">首　页</a></li>
            <li  <c:if test="${nav==2}">class="on" </c:if>><a href="<%=basePath%>projectWebsiteController.do?declareInfo">申报指引</a></li>
            <li  <c:if test="${nav==3}">class="on" </c:if>>
                <a href="<%=basePath%>projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638b5690001c">办事指南</a></li>
            <li  <c:if test="${nav==4||currentNav=='4'}">class="on" </c:if>><a href="<%=basePath%>contentController/list.do?moduleId=${projectListModuleId}&currentNav=4">办件公示</a></li>
            <li  <c:if test="${nav==5||currentNav=='5'}">class="on" </c:if>><a href="<%=basePath%>contentController/list.do?moduleId=${zcfgModuleId}&currentNav=5">政策法规</a></li>
            <li  <c:if test="${nav==6||currentNav=='6'}">class="on" </c:if>><a href="<%=basePath%>contentController/list.do?moduleId=${downFileModuleId}&currentNav=6">下载专区</a></li>
            <li  <c:if test="${nav==7||currentNav=='7'}">class="on" </c:if>><a href="<%=basePath%>projectWebsiteController.do?myProjectView&currentNav=7">我的项目</a></li>
        </ul>
    </div>
    <div class="eui-header-login">
        <!-- 未登录 -->
        <c:if test="${sessionScope.curLoginMember==null}">
            <a class="unlogin" href="<%=path%>/userInfoController/mztLogin.do?returnUrl=projectWebsiteController.do?index">登录/注册</a>
        </c:if>
        <!-- 已登录 -->
        <c:if test="${sessionScope.curLoginMember!=null}">
            <div class="eui-user" >
                <span>
                    <c:choose>
                        <c:when test="${fn:length(sessionScope.curLoginMember.YHMC) > 40}">
                            <c:out value="${fn:substring(sessionScope.curLoginMember.YHMC, 0, 36)}..." />
                        </c:when>
                        <c:otherwise>
                            <c:out value="${sessionScope.curLoginMember.YHMC}" />
                        </c:otherwise>
                    </c:choose>
                </span>
                <div class="sub">
                    <a  href="<%=basePath%>projectWebsiteController.do?myProjectView&currentNav=7">个人中心</a>
                    <a href="userInfoController/logout.do?type=project2020">退出登录</a>
                </div>
            </div>
        </c:if>
    </div>
</div>
<!-- 头部 end -->

</body>
</html>