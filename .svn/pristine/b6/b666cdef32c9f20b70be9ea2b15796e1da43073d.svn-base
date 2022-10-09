<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!doctype html>
<html>
 <head>
<title>流程图示例</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=path%>/webpage/flowchart/gojs.js"></script>
<link href="<%=path%>/webpage/flowchart/css/goSamples.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchart/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/jquery-ui.min.js"></script>
<link href="<%=path%>/webpage/test/css/jquery-1.11.4-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
<script id="code">
function initflow(){
	init("myDiagram",false,1,"myPalette","myToolbar","0,1,3",false,"","",2);
	var old=document.getElementById("diagramHeight").value;
	myDiagram.div.style.height=parseInt(old)+"px";
}

</script>
</head>
<body onload="initflow()" sroll="no">
    <div id="sample" style="height: 100%;">
  		<div style="width:100%; white-space:nowrap;height: 100%; margin-bottom: 0px;padding-bottom: 0px;">
    		<span style="display: inline-block; vertical-align: top; padding: 5px; 
    				width:126px;position:fixed; top: 66px; left: 10px;">
      			<div id="myPalette" style="border: solid 1px gray; height: 520px;"></div>
    		</span>
			<span style="display: inline-block; vertical-align: top; padding: 5px; 
					width:70%;overflow: hidden;height: 100%;">
      			<input type="hidden" id="diagramHeight" name="diagramHeight" value="1000">
      			<div id="myDiagram" style="border: solid 1px gray; height: 1000px;overflow: hidden;" onmouseout="diagramOut();"></div>
    		</span>
     		<span id="toolbarSpan" style="display: inline-block; vertical-align: top;">
    			<b>工具栏:</b><br />
    			<div id="myToolbar" style="width:20%; height: 620px" ></div>
  			</span>  
    </div>
    
    
  <textarea id="mySavedModel" style="width:100%;height:200px;visibility: visible;" >
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
  <button onclick="makeSVG('svgArea')">Render as SVG</button>
  <button onclick="makeImage('imageArea')">Render as IMAGE</button>
  <div id="svgArea"></div>
  <div id="imageArea"></div>
</div>
  </body>
</html>
