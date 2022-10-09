<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
 <head>
<title>权力运行流程图</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<c:set var="webRoot" value="<%=basePath%>" />
<!-- load the extjs libary -->
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>

<script src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowdesign.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-ui.min.js"></script>
<link href="<%=path%>/webpage/flowchart/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/webpage/test/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
<link rel="stylesheet" href="<%=path%>/plug-in/easyui-1.4/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var tacheCode;
	var tacheId;
	var busCode;
</script>

<script id="code">
var everoot='<%=path%>';
var showflg=false;

$(function(){
	
});
function initflow(){
	//显示工具栏时，显示工具栏面板头toolbarDivShow
	document.getElementById('toolbarDivShow').style.visibility='visible';
	init("myDiagram",false,1,"myPalette","toolbarDiv","",false,"","",2);
	myDiagram.div.style.height="500px";
	//document.getElementById("sample").style.width=parseFloat(screen.availWidth)-200+"px";
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
	<div region="north" style="height:66px;background:white;font-size: medium;">
		<div id="titlediv" style=";padding-left: 10px;">${busInfo.busName}&nbsp;&nbsp;
    		<button style="cursor: pointer;" onclick="tempSaveFlow();" 
    			id="tempSave">保存</button>
			<button style="cursor: pointer;" onclick="submitAudit();" id="submitAudit">确认</button>
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
 		<div id="sample" style="height: 100%;top: 66px;margin-left: 136px;">
    		<span style="display: inline-block; vertical-align: top; padding: 5px; 
    				width:126px;position:fixed; top: 80px; left: 10px;">
      			<div id="myPalette" style="border: solid 1px gray; height: 360px;"></div>
    		</span>
			<span style="display: inline-block; vertical-align: top; padding: 5px; 
					width:99%;overflow: hidden;height: 100%;">
      			<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
      			<div id="myDiagram" style="border: solid 1px gray;" onmouseout="diagramOut();"></div>
    		</span>
    <button id="SaveButton" style="visibility: hidden;">Save</button>
  <textarea id="mySavedModel" style="width:100%;height:300px;visibility: visible;" >
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
  
<div id="toolbarDiv" style="position:fixed;z-index:9999;display: block;top: 0px;right: 100px;background-color: silver;
					height: 66px;width: 460px;padding-top: 10px;visibility: visible;" align="center"></div> 
<div id="toolbarDivShow" style="position:fixed;z-index:9999;display: block;top: 0px;right: 100px;background-color: silver;
	height: 12px;width: 460px;visibility: visible;" align="center" 
	onclick="toolbarShow();">工具栏
</div>   
 	</div>
 
  </body>
</html>
