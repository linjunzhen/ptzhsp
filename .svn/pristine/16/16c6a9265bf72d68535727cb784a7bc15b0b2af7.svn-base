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
<title>监察节点及要素变更</title>
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
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/changeflowUtilsNode.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/monitorNode/flowFactor.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/monitorNode/changeFactorView.js"></script>
<style type="text/css">
.tdOver{
	border-right:solid 1px lightgray;
	border-bottom:solid 1px lightgray;
    line-height:20px;
    /* word-break:keep-all; 不换行 */
   /* white-space:nowrap; 不换行 */
    overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
    text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
    font-family:"Microsoft YaHei",sans-serif;
    font-weight: normal;
}
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
</style>
<style type="text/css">
<![CDATA[
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
]]>
</style>
<script id="code">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var svgdoc;
var busCode='${busInfo.busCode}';

$(function(){
	init("myDiagram",true,1,"","","",false,nodeClickC,"",2);
	var height=document.getElementById("diagramHeight").value;
	//document.getElementById("sample").style.height=parseFloat(height)+106+"px";
	myDiagram.div.style.height=parseFloat(height)+100+"px";
	/**var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	} **/
});
function loadSvgFactor(evt) {
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	//var polygons = node.getElementsByTagName("polygon");
	//polygons[0].click();
	var first=document.getElementById("firstTache").value;
	loadFlow(first);
	var p=svgdoc.getElementById("poly_"+first);
	//var pl=document.getElementById("poly_"+first);
	p.setAttribute("fill", "blue");
	//设置当前选中节点
	/**
	var curProcess=document.getElementById("presentCode").value;
	var nodeName=document.getElementById("presentProcessName").value;
	if(curProcess!=null&&curProcess!=undefined){
		document.getElementById("nodeName").value=nodeName;
		document.getElementById("processCode").value=curProcess;
		findNodeForId(curProcess);
		showMonitor(curProcess);
	} **/
}  

function clkPloy(evt,code){
	loadFlow(code);
	var curid="poly_"+code;
	//var imageTarget = evt.target;
	//var theFill = imageTarget.getAttribute("fill");
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	var polygons = node.getElementsByTagName("polygon");
	for(var i=0;i<polygons.length;i++){
		var id= polygons[i].getAttribute("id");
		if(id==curid){
			polygons[i].setAttribute("fill", "blue");
		}else{
			polygons[i].setAttribute("fill", "gray");
		}
	}
} 
function nodeClickC(data){
	if(data.category=='start'||data.category=='end'){
		refreshBase();
	}else{
		var ndata=findJsonDataForKey(data.key);
	document.getElementById("nodeName").value=data.text;
	document.getElementById("processCode").value=ndata.id;
	//document.getElementById("factorIsTr").style.display='';
	showMonitor(ndata.id);
	//loadFactorList(ndata.id);
	}
}

</script>
</head>
<body class="easyui-layout bodySelectNone">
	<!-- 开始编写头部工具栏 -->
	<div data-options="region:'north',collapsible:false," title="${busInfo.busName}" split="true" border="false"
		 class="design_titletool" style="height: 88px;margin-left: 139px;">
		 <input type="hidden" id="applyId" name="applyId" value="${busInfo.applyId}">
		 <input type="hidden" id="firstTache" name="firstTache" value="${firstTache}">
		 <input type="hidden" id="presentCode" name="presentCode" value="${presentCode}">
		 <input type="hidden" id="presentProcessName" name="presentProcessName" value="${presentProcessName}">
		 <!--  
		 <style>
            	ul{list-style: none;}
				li,ul{margin:0px; padding:0px;}
				.left_float{float:left; display:inline-block; width:135px; height:50px; margin-left:10px;}
				.left_float ul li{float:left; width:42px; height:46px; text-align:center; font-size:12px; margin:3px 10px;}
				.left_float ul li p{display:block; width:42px; height:36px; margin:0px; padding:0px; text-align:center; vertical-align:middle;}
				.left_float ul li p img{vertical-align:middle; margin-top:3px;}
				.left_float ul li a{display:block; width:42px; height:46px; color:#333; text-decoration:none; line-height:16px;}
				.left_float ul li a:hover{background:url(images/btn_bg.png) no-repeat;}
            </style>
		 <div class="left_float">
            	<ul>
                	<li><a href="javascript:void(0);" onclick="tempSaveFlow();" id="tempSave" style="display: none;"><p><img src="<%=path%>/webpage/flowchart/images/tempSave.png"></p>保存</a></li>
                    <li><a href="javascript:void(0);" onclick="submitAudit();" id="submitAudit" style="display: none;"><p><img src="<%=path%>/webpage/flowchart/images/submitAudit.png"></p>确认</a></li>
                </ul>
            </div> -->
		<c:if test="${empty tacheInfoList}">暂无数据</c:if>
            	<svg id="flowElement" width="860px" height="56px" onload="loadSvgFactor(evt);">
				<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0}">
							<polygon id="poly_${tacheInfo.tacheCode}" points="0,5 0,55 130,55 150,30 130,5 " fill="gray" onclick="clkPloy(evt,'${tacheInfo.tacheCode}')"
								style="stroke:#000000;stroke-width:1">
							</polygon>
							<text id="txt" x="10" y="20" width="20px" onclick="clkPloy(evt,'${tacheInfo.tacheCode}')">
								${tacheInfo.nameHtml}
							</text>
						</c:when>
						<c:otherwise>
							<polygon id="poly_${tacheInfo.tacheCode}" 
							points="${status.index*135},5 ${status.index*135+20},30 ${status.index*135},55 ${status.index*135+130},55 ${status.index*135+150},30 ${status.index*135+130},5 "
							 fill="gray" onclick="clkPloy(evt,'${tacheInfo.tacheCode}')"
								style="stroke:#000000;stroke-width:1">
							</polygon>
							<text id="txt" x="${status.index*125+20}" y="20" width="20px" onclick="clkPloy(evt,'${tacheInfo.tacheCode}')">
								${tacheInfo.nameHtml}
							</text>
						</c:otherwise>
					</c:choose>	
				</c:forEach>
				</svg>	
	</div>
	<!-- 结束编写头部工具栏 -->
	
	<!-- 开始编写基本信息 -->
	<div data-options="region:'east',collapsible:false" title="基本信息"  style="width:470px;background-color: #d8e4fe;" >
	    <p class="fix_height clearfix" style="min-height: 50px;white-space:nowrap;word-break:keep-all;">
		    <label class="text-Lh" style="width: 70px;">节点名称:</label>
		    <textarea rows="3" cols="20" name="nodeName" id="nodeName" class="checkout-input" readonly="readonly" style="width:370px;min-height: 50px;max-height: 80px;"></textarea>
		  </p>
		 <span class="fix_height clearfix" style="margin-top: 10px;padding-top: 20px;">
		    <label class="text-Lh" style="width: 70px;">过程编码:</label>
		    <input type="text" name="processCode" id="processCode"  class="checkout-input" readonly="readonly" style="width:370px;">
		 </span>
		 <p class="fix_height clearfix" style="text-align: left;">
		 	<label style="width:100px;float: left;line-height: 32px;font-size:14px;font-family:'Microsoft YaHei',sans-serif;">
		 	是否监察点：</label>
		    <input type="radio" id="monitorNodeYes" name="isAutoCreate" value="1"style="text-align: l" disabled="disabled">
		    <label for="monitorNodeYes" style="margin-left: 0px;">是&nbsp;&nbsp;&nbsp;</label>
            <input type="radio" id="noMonitor" name="isAutoCreate" checked="checked" value="2" disabled="disabled">
            <label for="noMonitor">否</label>
		 </p>
		 <table style="width: 98%;" cellpadding="0" cellspacing="0" class="dddl-contentBorder dddl_table">
		 	<!-- 监察节点start
		<tr>
			<td id="factorIsTr" style="padding-top: 3px;padding-left: 2px;height: 36px;border-width:2px;border-color: red;display: none;">
					是否监察节点：<span id="monitorNodeYes" style="height:50px;width: 50px;background: lightgray;cursor: pointer;">
					&nbsp;&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;</span>
					 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 			 <span id="noMonitor" style="width: 50px;height:50px;cursor: pointer;background:white;" >
					 			 &nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>  -->
		<tr>
			<td style="padding-top: 16px;padding-left: 1px;">
				<div id="factorInfoDiv" class="easyui-layout" style="width:100%;height:360px;overflow-y:auto;display: none;">  
				     <div region="north" title="要素信息" split="true" style="height:300px;width: 100%;">
				     	<table class="dddl-contentBorder dddl_table" cellpadding="0" cellspacing="0" id="factorTable" 
				     			style="padding-top: 0px;table-layout:fixed;width:445px;"> 
						</table>
				     </div>
				</div>    
			</td>
		</tr>
		 </table>
	</div> 
	<!-- 结束编写基本信息 -->

    
    <!-- 开始编写中间绘图区域 -->
	<div data-options="region:'center',title:'绘图区'" >
		<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
        <div id="myDiagram" style="height: 720px"></div>
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
</body>
</html>
