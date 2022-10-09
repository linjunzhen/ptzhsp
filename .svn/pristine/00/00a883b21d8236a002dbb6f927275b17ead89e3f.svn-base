<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!doctype html>
<html>
 <head>
<title>权力运行流程图</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<link href="<%=path%>/webpage/flowchart/css/goSamples.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/flowdesign.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/jquery-ui.min.js"></script>
<link href="<%=path%>/webpage/test/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/main/css/style.css"/>

<script id="code">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var busiCode;

$(function(){
	//setDivCenter("toolbarDiv"); 
}); 
function initflow(){
	//显示工具栏时，显示工具栏面板头toolbarDivShow
	document.getElementById('toolbarDivShow').style.visibility='visible';
	init("myDiagram",false,1,"myPalette","toolbarDiv","",false,nodeClickC,"",2);
}
function nodeClickC(node){
	window.parent.nodeClickP(node);
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
<body onload="initflow()" style="width: 98%;">
    <div id="sample" style="height: 100%;">
  		<div id="drawdiv" style="width:100%; white-space:nowrap;height: 100%; margin-bottom: 0px;padding-bottom: 0px;">
    		<span style="display: inline-block; vertical-align: top; padding: 5px; 
    				width:126px;position:fixed; top: 0px; left: 10px;">
      			<div id="myPalette" style="border: solid 1px gray; height: 420px;"></div>
    		</span>
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
  <textarea id="mySavedModelOld" style="width:100%;height:0px;visibility: hidden;" >
{ "class": "go.GraphLinksModel",
  "linkFromPortIdProperty": "fromPort",
  "linkToPortIdProperty": "toPort",
  "nodeDataArray": [
 ],
  "linkDataArray": [
 ]}
  </textarea>
</div>
  
<div id="toolbarDiv" style="position:fixed;z-index:9999;display: block;top: 0px;left: 300px;background-color: silver;
					height: 66px;width: 460px;padding-top: 10px;visibility: hidden;" align="center">
						
</div> 
<div id="toolbarDivShow" style="position:fixed;z-index:9999;display: block;top: 0px;left: 300px;background-color: silver;
	height: 12px;width: 460px;visibility: hidden;" align="center" 
	onclick="toolbarShow();">工具栏
</div>
  </body>
</html>
