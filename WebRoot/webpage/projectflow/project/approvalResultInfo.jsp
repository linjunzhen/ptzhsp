<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/main/css/style1.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/syj-style.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/gcjsxm/xmjbxx/js/xmjbxx.js"></script>

<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>

</head>

<body class="eui-diabody" >
	<div class="eui-slidebox" style="width:90%">
	    <%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" id="STAGE_ID" name="STAGE_ID" value="${info.STAGE_ID}"/>
		<input type="hidden" id="TYPE_ID" name="TYPE_ID" value="${info.TYPE_ID}"/>
		
		<%--开始引入审批结果信息页面 --%>
		<jsp:include page="./xmjbxx/spjgxx.jsp" />
		<%--结束引入审批结果信息页面 --%>
	</div>
</body>
</html>
