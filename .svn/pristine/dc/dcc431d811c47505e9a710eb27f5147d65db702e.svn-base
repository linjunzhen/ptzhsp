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
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowview.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/monitorNode/factorMng.js"></script>

<script id="code">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var busCode='${busInfo.busCode}';

$(function(){
	init("myDiagram",true,1,"","","",false,nodeClickC,"",2);
	var height=document.getElementById("diagramHeight").value;
	//document.getElementById("sample").style.height=parseFloat(height)+106+"px";
	myDiagram.div.style.height=parseFloat(height)+100+"px";
	var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
});

function nodeClickC(data){
	var ndata=findJsonDataForKey(data.key);
	document.getElementById("nodeName").innerHTML=data.text;
	document.getElementById("processCode").innerHTML=ndata.id;
	document.getElementById("factorIsTr").style.display='';
	showMonitor(ndata.id);
	//loadFactorList(ndata.id);
}

</script>
</head>
<body class="easyui-layout" fit="true" onload="initflow()">
	<!--1.1 egion="north"，指明高度，可以自适应-->
	<div region="north" style="height:80px;background:white;font-size: medium;">
		<div id="titlediv" style="top: 20px;padding-top: 10px;padding-left: 10px;">${busInfo.busName}&nbsp;&nbsp;
			<button style="cursor: pointer;" onclick="submitFactor('first');" id="submitAudit">确认</button>
			<button style="cursor: pointer;" onclick="backDraw();" id="submitAudit">退回绘图</button>
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
 		<div id="sample" style="height: 100%;top: 80px;width:860px;margin-left: 0px;">
  		<div id="drawdiv" style="width:100%; white-space:nowrap;height: 100%; margin-bottom: 0px;padding-bottom: 0px;">
			<span style="display: inline-block; vertical-align: top; padding: 5px; 
					width:99%;overflow: hidden;height: 100%;">
      			<input type="hidden" id="diagramHeight" name="diagramHeight" value="1600">
      			<div id="myDiagram" style="border: solid 1px gray;" onmouseout="diagramOut();"></div>
    		</span>
    </div>
    <input type="hidden" id="tacheCode" name="tacheCode">
    <button id="SaveButton" style="visibility: hidden;">Save</button>
  <textarea id="mySavedModel" style="width:100%;height:0px;visibility: hidden;" >
		${flowInfo.flowInfo}
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
 
 	<!--1.3 region="east",必须指明宽度-->
 	<div region="east"  style="width:386px;font-size: 12px;" title="属性面板" split="true">
 		<table style="width: 98%;" cellpadding="0" cellspacing="0">
 		<!-- 节点信息start -->
		<tr>
			<td style="padding-top: 5px;padding-left: 1px;">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height: 36px;width: 66px;padding: 0px;margin: 0px;background: lightblue;text-align: right;">节点名称：</td>
						<td style="width: 1px;"></td>
						<td style="background: lightgray;"><span id="nodeName" name="nodeName" style="width: 50px;"></span> </td>
					</tr>
					<tr><td style="height:2px;"></td> <td></td></tr>
					<tr>
						<td style="height: 36px;width: 66px;padding: 0px;margin: 0px;background: lightblue;text-align: right;">过程编号：</td>
						<td style="width: 1px;"></td>
						<td style="background: lightgray;"><p id="processCode" name="processCode" style="width: 50px;"></p> </td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- 监察节点start -->
		<tr>
			<td id="factorIsTr" style="padding-top: 3px;padding-left: 2px;height: 36px;border-width:2px;border-color: red;display: none;">
					是否监察节点：<span id="monitorNodeYes" onclick="monitorClk('yes');" style="height:50px;width: 50px;background: lightgray;cursor: pointer;">
					&nbsp;&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;</span>
					 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 			 <span id="noMonitor" style="width: 50px;height:50px;cursor: pointer;background:white;" onclick="monitorClk('no');">
					 			 &nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 5px;padding-left: 1px;height: 300px;">
				<div id="factorInfoDiv" class="easyui-layout" style="width:100%;height:300px;display: none;">  
				     <div region="north" title="要素信息" split="true" style="height:300px;width: 100%;">
									<a href="#" class="easyui-linkbutton" 
										onclick="showFactorWindow();">添加</a> 
				     	<table class="dddl-contentBorder dddl_table" cellpadding="0" cellspacing="0" border="1" id="factorTable" 
				     			style="padding-top: 3px;"> 
						</table>
				     </div>
				</div>    
			</td>
		</tr>
	</table>
 	</div>
 
  </body>
</html>
