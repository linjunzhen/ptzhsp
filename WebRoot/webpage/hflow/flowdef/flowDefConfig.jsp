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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>【${flowDef.DEF_NAME}】流程配置</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
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
	src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/go.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/LinkLabelDraggingTool.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/design.js"></script>

<script type="text/javascript">
    $(function(){
    	var defId = $("#DEF_ID").val();
    	var flowVersion = $("#FLOW_DEFVERSION").val();
    	initFlowDesign(true,false,function(data){
    		var nodeName = data.text;
    		var nodeType = data.nodeType;
    		var key = data.key;
    		var url = "flowNodeController.do?goConfig&nodeName="+encodeURIComponent(nodeName)+"&nodeType="+nodeType+"&key="+key+"&defId="+defId+"&flowVersion="+flowVersion;
    		$.dialog.open(url, {
    			title : "【"+nodeName+"】配置",
    			width : "700px",
    			height : "500px",
    			lock : true,
    			resize : false
    		}, false);
    	});
    });
</script>
</head>

<body class="easyui-layout bodySelectNone" fit="true" >
    <input type="hidden" name="DEF_ID" id="DEF_ID" value="${flowDef.DEF_ID}" >
    <input type="hidden" name="DEF_JSON" id="DEF_JSON" value="${flowDef.DEF_JSON}">
    <input type="hidden" name="FLOW_DEFVERSION" id="FLOW_DEFVERSION" value="${flowDef.VERSION}">
    <input type="hidden" name="DEF_XML" id="DEF_XML" >
    
    <div data-options="region:'center'" fit="true">
       
		<div class="easyui-tabs eve-tabs" fit="true" >
		    <div title="流程图配置" style="padding:10px">
			    <div id="myDiagram" style="height: 720px"></div>
			</div>
			<div title="流程全局配置" >
			    <iframe scrolling="auto" width="100%" height="100%" frameborder="0" 
			        src="flowDefController.do?configview&defId=${flowDef.DEF_ID}&flowVersion=${flowDef.VERSION}">
			    </iframe>
			</div>
			<div title="绑定表单" >
			    <iframe scrolling="auto" width="100%" height="100%" frameborder="0" src="${flowDef.FORM_URL}">
			    </iframe>
			</div>
			
		</div>
	</div>
</body>
</html>
