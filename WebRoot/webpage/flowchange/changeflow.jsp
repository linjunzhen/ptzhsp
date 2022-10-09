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
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/changeflowUtils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/flowdraw.js"></script>
<style type="text/css">
.toos_one{height:28px; margin:16px 0 0 4px;}
.toos_one a{float:left; margin:0 5px;}
.toos_tow{height:28px; margin:16px 0 0 1px;}
.toos_tow a{float:left; width:60px; height:28px; margin:0 2px;}		
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
</style>
<style type="text/css">
<![CDATA[
.abbreviation {font: bold 10pt Helvetica, Arial, sans-serif;fill:white;}
]]>
</style>
<script type="text/javascript">
	var tacheCode;
	var tacheId;
	var busCode;
	var svgdoc;
</script>
<script type="text/javascript">
var everoot='<%=path%>';
var showflg=false;
    $(function(){
    	//document.getElementById('toolbarDivShow').style.visibility='visible';
	init("myDiagram",false,1,"myPalette","","",false,nodeClickC,"",2);
	myDiagram.div.style.height="500px";
	//document.getElementById("sample").style.width=parseFloat(screen.availWidth)-200+"px";
	myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
	/**var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	} **/
    });
function nodeClickC(data){
	var ndata=findJsonDataForKey(data.key);
	document.getElementById("processKey").value=data.key;
	if(ndata.id!=undefined){
		document.getElementById("processCode").value=ndata.id;	
	}else{
		document.getElementById("processCode").value="";
	}
}    
function setNodeId(){
	var key=document.getElementById("processKey").value;
	var code=document.getElementById("processCode").value;
	if(code!=""&&code!=undefined){
		var model = myDiagram.model;
		var node=myDiagram.findNodeForKey(key);
		model.startTransaction("setNodeId");
		model.setDataProperty(node.data, "id",code);
		model.commitTransaction("setNodeId");
	}else{
		alert("请输入正确的过程编码");
	}
}
function createSet(flag){
	if(flag=='1'){
		$("#processCodeP").css("display","none");
	}else{
		$("#processCodeP").css("display","");
	}
}    
    
</script>
</head>

<body class="easyui-layout bodySelectNone" >
	
	<!-- 开始编写头部工具栏 -->
	<div data-options="region:'north'" title="${busInfo.busName}" split="false" border="false"
		 class="design_titletool" style="height: 86px;">
		 	<input type="hidden" id="busCode" name="busCode" value="${busInfo.busCode}">
			<input type="hidden" id="applyId" name="applyId" value="${busInfo.applyId}">
			<input type="hidden" id="firstTache" name="firstTache" value="${firstTache}">
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
                	<li><a href="javascript:void(0);" onclick="tempSaveFlow();" id="tempSave" ><p><img src="<%=path%>/webpage/flowchart/images/tempSave.png"></p>暂存</a></li>
                    <li><a href="javascript:void(0);" onclick="submitAudit();" id="submitAudit"><p><img src="<%=path%>/webpage/flowchart/images/submitAudit.png"></p>确认</a></li>
                </ul>
            </div>
			<c:if test="${empty tacheInfoList}">暂无数据</c:if>
            	<svg id="flowElement" width="860px" height="56px" onload="loadSvg(evt);">
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
										style="font: bold 10pt Helvetica, Arial, sans-serif;color:white;position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
									</div>
									</c:when>
									<c:otherwise>
										<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}');"  
										style="top:30px;left:${status.index*146+136}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
										<div
										style="font: bold 10pt Helvetica, Arial, sans-serif;color:white;position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;;word-break: break-all; word-wrap:break-word;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
									</div>
									</c:otherwise>
									</c:choose>
								</td>
						</c:forEach> 
						</tr>
  				 		</table>
			
		<div id="courseDiv" style="margin-top:-10px;text-align: center;" >
			<c:if test="${empty tacheInfoList}">暂无数据</c:if>
			<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer;width:auto; font-size: 13px;font-weight: bold;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='#d8e4fe';"
			 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">>&nbsp;</span>  <sapn>&nbsp;</span>
			</c:forEach>
		</div>	-->
	</div>
	<!-- 结束编写头部工具栏 -->
	
	<!-- 开始编写基本信息 -->
	<div data-options="region:'east',split:true" title="工具栏"  style="width:260px;background-color: #d8e4fe;" >
	    <div class="toos_one">
            	<img src="<%=path%>/webpage/flowchart/images/toos_icon.png" onclick="designTool('1');" style="cursor: pointer;">
                <img src="<%=path%>/webpage/flowchart/images/toos_icon1.png" onclick="designTool('2');">
            </div>
            <div class="toos_tow">
            	<img src="<%=path%>/webpage/flowchart/images/toos_icon2.png" style="cursor: pointer;" onclick="designTool('5');">
                <img src="<%=path%>/webpage/flowchart/images/toos_icon3.png" style="cursor: pointer;" onclick="designTool('6');">
                <img src="<%=path%>/webpage/flowchart/images/toos_icon4.png" style="cursor: pointer;" onclick="designTool('7');">
            </div>
            <div class="toos_tow">
            	<img onclick="designTool('8');" src="<%=path%>/webpage/flowchart/images/toos_icon5.png" style="cursor: pointer;">
                <img onclick="designTool('10');" src="<%=path%>/webpage/flowchart/images/toos_icon6.png" style="cursor: pointer;">
                <img onclick="designTool('9');" src="<%=path%>/webpage/flowchart/images/toos_icon7.png" style="cursor: pointer;">
            </div>
            <div class="toos_tow">
            	<img onclick="designTool('4');" src="<%=path%>/webpage/flowchart/images/toos_icon8.png" style="cursor: pointer;">
                <img onclick="designTool('3');" src="<%=path%>/webpage/flowchart/images/toos_icon9.png" style="cursor: pointer;">
                <img onclick="designTool('11');" src="<%=path%>/webpage/flowchart/images/toos_icon10.png" style="cursor: pointer;">
            </div>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('12');">清除</a>
		<p class="fix_height clearfix">
		 	<label class="text-Lh">编码方式</label>
		    <input type="radio" id="autoCreate" name="isAutoCreate" value="1" checked="checked" onclick="createSet(1);">
		    <label for="autoCreate">自动编码</label>
            <input type="radio" id="handCreate" name="isAutoCreate" value="2" onclick="createSet(2);">
            <label for="handCreate">手动编码</label>
		 </p>
		 <p class="fix_height clearfix" id="processCodeP" style="display: none;">
		    <label class="text-Lh">过程编码</label>
		    <input type="hidden" name="processKey" id="processKey"  class="checkout-input">
		    <input type="text" name="processCode" id="processCode"  class="checkout-input">
		    <input type="button" name="saveProcessCode" id="saveProcessCode" onclick="setNodeId();" value="确定">
		 </p>
	</div> 
	<!-- 结束编写基本信息 -->
	
	<!-- 开始编写左侧图元区工具栏 -->
	<div data-options="region:'west',split:true,collapsible:false" title="图元区"
		class="design_leftcontent" >
		<div id="myPalette" style="height: 720px"></div>
    </div>
    <!-- 结束编写左侧图元区工具栏 -->
    
    <!-- 开始编写中间绘图区域 -->
	<div data-options="region:'center',title:'绘图区'" >
		<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
        <div id="myDiagram" style="height: 720px"></div>
        <div id="mySvg" style="height: 720px;display:none;"></div>
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
