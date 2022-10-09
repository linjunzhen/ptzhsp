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

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>权力运行流程图</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/default/easyui.css" rel="stylesheet">
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
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/flowDetail.js"></script>
<script type="text/javascript">
var everoot='<%=path%>';
</script>
<script type="text/javascript">
   var tacheCode='<%=request.getParameter("tacheCode")%>';
    $(function(){
	init("myDiagram",true,1,"","","",false,"","",2);
	myDiagram.div.style.height="500px";
	myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
	var code=$("#tacheCode").val();
	loadFlow(code);
	});
</script>
</head>

<body class="easyui-layout bodySelectNone" >
    <input type="hidden" id="tacheCode" name="tacheCode" value="<%=request.getParameter("tacheCode")%>">
    <!-- 开始编写中间绘图区域 -->
	<div data-options="region:'center',title:'绘图区'">
		<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
        <div id="myDiagram" style="height: 720px;width: 90%;"></div>
        <textarea id="mySavedModel" style="width:100%;height:0px;visibility:hidden;" >
{ "class": "go.GraphLinksModel",
  "linkFromPortIdProperty": "fromPort",
  "linkToPortIdProperty": "toPort",
  "nodeDataArray": [ 
  ],
  "linkDataArray": [  ]}
  </textarea>
     <textarea id="mySavedModelBak" style="width:100%;height:0px;visibility: hidden;" >
{ "class": "go.GraphLinksModel",
  "linkFromPortIdProperty": "fromPort",
  "linkToPortIdProperty": "toPort",
  "nodeDataArray": [
  {"key":-1, "category":"start", "loc":"520 50", "text":"开始"}
 ],
  "linkDataArray": [
 ]}
  </textarea>
	</div>
</body>
</html>
