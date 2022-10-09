<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html>
 <head>
<title>权力运行流程图</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/changeflowUtils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/changeflowView.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-ui.min.js"></script>
<link href="<%=path%>/webpage/flowchart/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/webpage/test/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
<link rel="stylesheet" href="<%=path%>/plug-in/easyui-1.4/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js"></script>

<script id="code">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var busCode;

var stateflg='<%request.getAttribute("stateflg");%>';
$(function(){
	
});
function initflow(){
	//显示工具栏时，显示工具栏面板头toolbarDivShow
	init("myDiagram",true,1,"","","",false,nodeClickC,"",2);
	myDiagram.div.style.height="500px";
	document.getElementById("sample").style.width=parseFloat(screen.availWidth)-200+"px";
	myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
	var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
}
function nodeClickC(data){
	var ndata=findJsonDataForKey(data.key);
	document.getElementById("nodeName").value=data.text;
	document.getElementById("processCode").value=ndata.id;
}
function toolbarShow(){
	if(showflg){
		document.getElementById('toolbarDiv').style.visibility='visible';
		showflg=false;	
	}else{
		document.getElementById('toolbarDiv').style.visibility='hidden';
		showflg=true;
	}
}
</script>
</head>
<body class="easyui-layout" onload="initflow()">
	<!--1.1 egion="north"，指明高度，可以自适应-->
	<div region="north" style="height:80px;background:white;font-size: medium;">
		<div id="titlediv" style="top: 20px;padding-top: 10px;padding-left: 10px;">${busInfo.busName}&nbsp;&nbsp;
			<input type="hidden" id="applyId" name="applyId" value="${busInfo.applyId}">
			<!-- <button style="cursor: pointer;" onclick="endAudit();" id="endAudit">通过审批</button> -->
		</div>
		<div id="courseDiv" style="padding-top: 10px;padding-left: 160px;" >
			<c:if test="${empty tacheInfoList}">暂无数据</c:if>
			<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer;background:silver;width:auto;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='silver';"
			 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">&clubs;></span> <sapn></span>
			</c:forEach>
		</div>
		
	</div>
	<!--1.2region="center",这里的宽度和高度都是由周边决定，不用设置-->
	<div region="center">
 		<!--对<div>标签引用'easyui-layout'类,fit="true"自动适应父窗口,这里我们指定了宽度和高度-->
 		<div id="sample" style="height: 100%;top: 80px;width:860px;margin-left: 106px;">
  		<div id="drawdiv" style="width:100%; white-space:nowrap;height: 100%; margin-bottom: 0px;padding-bottom: 0px;">
    		
			<span style="display: inline-block; vertical-align: top; padding: 5px; 
					width:99%;overflow: hidden;height: 100%;">
      			<input type="hidden" id="diagramHeight" name="diagramHeight" value="1600">
      			<div id="myDiagram" style="border: solid 1px gray;" onmouseout="diagramOut();"></div>
    		</span>
    </div>
    <button id="SaveButton" style="visibility: hidden;">Save</button>
  <textarea id="mySavedModel" style="width:100%;height:0px;visibility: hidden;" >
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
 ],
  "linkDataArray": [
 ]}
  </textarea>
</div>
  
 	</div>
 
  </body>
</html>
