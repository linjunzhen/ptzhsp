<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html>
 <head>
<title>监察规则管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree"></eve:resources>
<script src="<%=path%>/webpage/flowchart/js/gojs.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowutils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowview.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flow/busRule/BusRuleAddWin.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/flowchart/css/jquery-1.11.4-ui.css" ></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/default/easyui.css"></link>
<link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css"></link>

<script id="code">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var busiCode;

$(function(){
});
function initflow(){
	init("myDiagram",true,1,"","","",false,nodeClickC,"",2);
	var height=document.getElementById("diagramHeight").value;
	myDiagram.div.style.height=parseFloat(height)+100+"px";
	var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
}
function nodeClickC(data){
	var ndata=findJsonDataForKey(data.key);
	document.getElementById("nodeName").innerHTML=data.text;
	document.getElementById("processCode").innerHTML=ndata.id;
	loadFactorList(ndata.id);
}

</script>
</head>
<body class="easyui-layout" fit="true" onload="initflow()">
	<form id="BusRuleAddForm" method="post" action="busRuleController.do?saveEditRule">
	<!--1.1 egion="north"，指明高度，可以自适应-->
	<div region="north" style="height:80px;background:white;font-size: medium;">
		<div id="titlediv" style="top: 20px;padding-top: 10px;padding-left: 10px;">${busInfo.busName}&nbsp;&nbsp;
			<button style="cursor: pointer;" onclick="submitFactor();" id="submitAudit">提交审批</button>
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
		<tr style="width:350px;font-size: 12px;">
			<td style="padding-top: 5px;padding-left: 1px;height:300px;">
				<!-- =========================表格开始==========================-->
				<div id="BusRuleAddbar">
					<input type="hidden" name="Q_T.PROCESS_CODE_EQ" id="busrule_addprocess"/>
				</div>
				<table id="BusRuleAddGrid"></table>
				<!-- =========================表格结束==========================-->
			</td>
		</tr>
	</table>
 	</div>
 	</form>
  </body>
</html>
