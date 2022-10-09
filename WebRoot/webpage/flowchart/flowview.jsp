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
<style type="text/css">
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
</style>
<style type="text/css">
<![CDATA[
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
]]>
</style>
<script type="text/javascript">
var everoot='<%=path%>';
var showflg=true;
var tacheCode;
var tacheId;
var busiCode;
var svgdoc;
</script>
<script type="text/javascript">
    $(function(){
	init("myDiagram",true,1,"","","",false,"","",2);
	myDiagram.div.style.height="500px";
	myDiagram.div.style.width=parseFloat(document.body.clientWidth)-220+"px";
	/**var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}  **/
	});
function loadSvg(evt) {
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	var first=document.getElementById("firstTache").value;
	loadFlow(first);
	var p=svgdoc.getElementById("poly_"+first);
	//var pl=document.getElementById("poly_"+first);
	p.setAttribute("fill", "blue");
}
function addClick(){
	var node=svgdoc.documentElement;
	var texts = node.getElementsByTagName("text");alert(texts.length);
	for(var i=0;i<texts.length;i++){
		texts[i].setAttribute("fill", "red");
		//texts[i].setAttributeNS(null, "onclick", "clkPloytest(evt)");
	}
}
function clkPloytest(evt){
	alert(111);
}
</script>
</head>

<body class="easyui-layout bodySelectNone" >
	
	<!-- 开始编写头部工具栏 -->
	<div data-options="region:'north',collapsible:false," title="${busInfo.busName}" split="true" border="false"
		 class="design_titletool" style="height: 88px;margin-left: 139px;">
		  <input type="hidden" id="firstTache" name="firstTache" value="${firstTache}">
		 
		 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
            	<svg id="flowElement" width="860px" height="50px" onload="loadSvg(evt);">
            	<script><![CDATA[ function initSvg(evt) { 
					svgDoc = evt.target.ownerDocument; svgRoot = svgDoc.getElementById("poly1"); alert(svgRoot);
 					}
				]]></script>
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
		 <!-- 
		 <c:if test="${empty tacheInfoList}">暂无数据</c:if>
  				 		<table style="margin-top: 10px;">
  				 			<tr>
						<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
								<td style="word-break: break-all; word-wrap:break-word;">
									<c:choose>
										<c:when test="${status.index==0}">
										<div id="span_${status.index}" onclick="loadFlow('${tacheInfo.tacheCode}');"  
										 style="top:30px;left:136px;z-index:${status.index*10}; height: 46px;position:absolute;cursor:pointer;" >
									<div  
										style="color:white;position:absolute;cursor: pointer;text-align: center;
										vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;
										font: bold 10pt Helvetica, Arial, sans-serif;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
									</div>
									</c:when>
									<c:otherwise>
										<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}');"  
										style="top:30px;left:${status.index*146+136}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
										<div
										style="color:white;position:absolute;cursor: pointer;text-align: center;
										vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;
										word-break: break-all; word-wrap:break-word;font: bold 10pt Helvetica, Arial, sans-serif;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
									</div>
									</c:otherwise>
									</c:choose>
								</td>
						</c:forEach> 
						</tr>
  				 		</table>  -->
		 <!--  
		<div id="courseDiv" style="margin-top:9px;text-align: center;" >
			<c:if test="${empty tacheInfoList}">暂无数据</c:if>
			<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer;width:auto; font-size: 13px;font-weight: bold;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='#d8e4fe';"
			 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">>&nbsp;</span>  <sapn>&nbsp;</span>
			</c:forEach>
		</div>	 -->
	</div>
	<!-- 结束编写头部工具栏 -->
	
	<!-- 开始编写基本信息
	<div data-options="region:'east',split:true" title=""  style="width:200px;background-color: #d8e4fe;" >
	    
	</div>  -->
	<!-- 结束编写基本信息 -->
	
	<!-- 开始编写左侧图元区工具栏
	<div data-options="region:'west',split:true,collapsible:false" title="图元区"
		class="design_leftcontent" >
		<div id="myPalette" style="height: 720px"></div>
    </div>  -->
    <!-- 结束编写左侧图元区工具栏 -->
    
    <!-- 开始编写中间绘图区域 -->
	<div data-options="region:'center',title:'绘图区'" >
		<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
        <div id="myDiagram" style="height: 720px"></div>
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
