<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% 
	List flist=(List)request.getAttribute("tacheList");
	request.setAttribute("tacheflowList",flist);

 %>
<!doctype html>
<html>
 <head>
<title>权力运行流程图</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="<%=path%>/webpage/flowchart/css/goSamples.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowmain.js"></script>
<link href="<%=path%>/webpage/test/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/main/css/style.css"/>

<script id="coded">
var stateflg='<%request.getAttribute("stateflg");%>';
$(function(){
	if(stateflg=='3'){
		window.frames["topFrame"].document.getElementById("tempSave").disabled=true;
		window.frames["topFrame"].document.getElementById("submitAudit").disabled=true;	
	}
});

function loadFlowInfo(tachecode){
	window.frames["centerFrame"].loadFlow(tachecode);
	if(stateflg!='3'){
		window.frames["topFrame"].document.getElementById("tempSave").disabled=false;	
	}
}
function tempSaveFlow(){
	window.frames["centerFrame"].tempSaveFlow();
}
function submitAudit(){
	window.frames["centerFrame"].submitAudit();
}
function nodeClickP(data){
	window.frames["rightFrame"].document.getElementById("nodeName").value=data.text;
	window.frames["rightFrame"].document.getElementById("processCode").value=data.key;
}
</script>
</head>
<frameset rows="80,*" cols="*" frameborder="yes" border="1" framespacing="0">
  <frame src="<%=path%>/webpage/flowchart/flowhead.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="76%,*" frameborder="yes" border="1" framespacing="0">
    <frame src="<%=path%>/webpage/flowchart/flowdesign.jsp" name="centerFrame" id="centerFrame" title="centerFrame" />
    <frame src="<%=path%>/webpage/flowchart/flowright.jsp" name="rightFrame" scrolling="No" noresize="noresize" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
<noframes>
<body>   
</body>
</html>