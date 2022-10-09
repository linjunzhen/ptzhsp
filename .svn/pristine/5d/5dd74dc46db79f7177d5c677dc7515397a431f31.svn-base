<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>发起申请</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
<link type="text/css" href="plug-in/eveflowdesign-1.0/css/design.css"
	rel="stylesheet">
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 开始引入artdialog -->
<link type="text/css" href="plug-in/artdialog-4.1.7/skins/default.css"
	rel="stylesheet">
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<!-- 结束引入artdialog -->
<script type="text/javascript"
	src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript"
	src="plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	
</head>

<body class="easyui-layout bodySelectNone" fit="true" >
     <input type="hidden" name="EFLOW_FLOWOBJ" id="EFLOW_FLOWOBJ" value="${EFLOW_FLOWOBJ}"/>


    <div data-options="region:'center'" >     
		<iframe id="EFLOWFORM_IFRAME" scrolling="auto" width="100%" height="98%" frameborder="0" src="${IFRAME_URL}" >
		</iframe>
	</div>
	    <!-- 开始编写头部工具栏 -->
	<div data-options="region:'south',collapsible:false" style="height: 42px;margin-top: 5px;" split="false" border="false" >	    
		<div style="text-align:center;">
			<button onclick="FlowUtil.FLOW_SubmitFun();" id="SubmitFlow" class="eflowbutton">
		    提交流程
		    </button>
		</div>
	</div>
	<!-- 结束编写头部工具栏 -->
</body>
</html>
