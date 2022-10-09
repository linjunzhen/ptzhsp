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
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/flowdesign.js"></script>
<style type="text/css">
.toos_one{height:28px; margin:16px 0 0 4px;}
.toos_one a{float:left; margin:0 5px;}
.toos_tow{height:28px; margin:16px 0 0 1px;}
.toos_tow a{float:left; width:60px; height:28px; margin:0 2px;}	
.span_selected{fill:blue}	
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
</script>

<script type="text/javascript">
var everoot='<%=path%>';
var showflg=false;
var svgdoc;
var templateFlag=false;
$(function(){
	init("myDiagram",false,1,"myPalette","","",false,nodeClickC,"",2);
	//myDiagram.initialPosition.x=300;
	//myDiagram.div.style.height="500px";
	//myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
		/**var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	} 
	$("[id^=span_]").click(function(e) {
		$("[id^=span_]").removeClass('span_selected');    // 先删除所有元素的selected样式
		$(e.target).addClass('span_selected');  // 然后为被点击元素添加selected样式
	}); **/
});
    
function loadSvg(evt) {
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	//var polygons = node.getElementsByTagName("polygon");
	//polygons[0].click();
	var first=document.getElementById("firstTache").value;
	loadFlow(first);
	var p=svgdoc.getElementById("poly_"+first);
	//var pl=document.getElementById("poly_"+first);
	p.setAttribute("fill", "blue");
}
//递归遍历节点nodeType为3两个节点元素之间的空白区域也算是一个元素,4时是脚本
function findnode(node)
{
     var child;      
     for(var i=0;i<node.childNodes.length;i++){
       if(node.childNodes.item(i).nodeType!=3){           
              child=node.childNodes.item(i);
              if(child.getAttribute("id"))
              {
              alert(child.getAttribute("id")) 
              }          
        }      
    }
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
	//var imageTarget=node.getElementById("poly_1");
	//imageTarget.setAttribute("fill", "blue");
}    
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

function loadTemp(rec){
	if(rec.TEMPLATE_ID){
		$('#template_id').val(rec.TEMPLATE_ID);
		$('#mySavedModel').val(rec.TEMPLATE_CONTENT);
		myDiagram.model = go.Model.fromJson(rec.TEMPLATE_CONTENT);
		templateFlag=true;
	}
}

</script>
</head>

<body class="easyui-layout bodySelectNone" >
	
	<!-- 开始编写头部工具栏 -->
	<div data-options="region:'north'," title="${busInfo.busName}" split="false" border="false"
		 class="design_titletool" style="height: 86px;">
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
										style="color:white;position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 0px;width:126px;top:11px;word-wrap: break-word;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_start.png" style="vertical-align:middle;height: 46px;width:167px;top:10px;left:0px;"/>
									</div>
									</c:when>
									<c:otherwise>
										<div  id="span_${status.index}"  onclick="loadFlow('${tacheInfo.tacheCode}');"  
										style="top:30px;left:${status.index*146+136}px;z-index:${status.index*10}; height: 56px;position:absolute;cursor:pointer;" >
										<div
										style="color:white;position:absolute;cursor: pointer;text-align: center;vertical-align: middle;height: 46px;left:25px;width:126px;top:11px;white-space:normal;;word-break: break-all; word-wrap:break-word;">
										${tacheInfo.tacheName}
									</div>
									<img id="img_${tacheInfo.tacheCode}" src="<%=path%>/webpage/flowchart/images/arrow_go.png" style="vertical-align:middle;height: 46px;width:167px;top:0px;left:0px;"/>
									</div>
									</c:otherwise>
									</c:choose>
								</td>
						</c:forEach> 
						</tr>
  				 		</table>	-->
			<!-- 
		<div id="courseDiv" style="margin-top:-12px;text-align: center;" >
			<c:if test="${empty tacheInfoList}">暂无数据</c:if>
			<c:forEach items="${tacheInfoList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer; font-size: 13px;font-weight: bold;white-space:normal;;word-break: break-all; word-wrap:break-word;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='#d8e4fe';"
			 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">>&nbsp;</span>  <sapn>&nbsp;</span>
			</c:forEach>
			<input type="hidden" id="tacheCode" name="tacheCode">
		</div>	-->
	</div>
	<!-- 结束编写头部工具栏 -->
	
	<!-- 开始编写基本信息 -->
	<div data-options="region:'east',split:true" title="工具栏"  style="width:220px;background-color: #d8e4fe;" >
		
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
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="loadTemplate();">加载模板</a>  -->
		<style>
        	.R_select{margin:12px 10px;}
			.toos_one{height:28px; margin:16px 0 0 4px;}
			.toos_one a{float:left; margin:0 5px;}
			.toos_tow{height:28px; margin:16px 0 0 1px;}
			.toos_tow a{float:left; width:60px; height:28px; margin:0 2px;}
        </style>
    	    <hr/> 
    	    <input type="hidden" id="template_id" name="template_id">
        	<div class="R_select"><input style="width:156px;height:25px;float:left;" id="template_nameCombx"
							class="easyui-combobox" value="选择流程图模板" 
							name="template_code"
							data-options="url:'flowChartController.do?comboxTemplate',
											editable:false,method: 'post',valueField:'TEMPLATE_CODE',
											textField:'TEMPLATE_NAME',panelWidth: 156,panelHeight: 'auto',
											onSelect:function(rec){
											   loadTemp(rec);
											}
										" /></div>  
		 <p class="fix_height clearfix">
		 	<label class="text-Lh">编码方式</label>
		    <input type="radio" id="autoCreate" name="isAutoCreate" value="1" checked="checked" onclick="createSet(1);" style="margin-left: 0px;padding-left: 0px;">
		    <label for="autoCreate" style="margin-left: 0px;">自动</label>
            <input type="radio" id="handCreate" name="isAutoCreate" value="2" onclick="createSet(2);">
            <label for="handCreate">手动</label>
		 </p>
		 <p class="fix_height clearfix" id="processCodeP" style="display: none;">
		    <label class="text-Lh">过程编码</label>
		    <input type="hidden" name="processKey" id="processKey">
		    <input type="text" name="processCode" id="processCode"  class="checkout-input" style="width: 126px;">
		    <input type="button" name="saveProcessCode" id="saveProcessCode" onclick="setNodeId();" value="确定">
		 </p>
	</div> 
	<!-- 结束编写基本信息 -->
	
	<!-- 开始编写左侧图元区工具栏 -->
	<div data-options="region:'west',split:true,collapsible:false" title="图元区"
		class="design_leftcontent" style="width:136px;">
		<div id="myPalette" style="height: 720px"></div>
    </div>
    <!-- 结束编写左侧图元区工具栏 -->
    
    <!-- 开始编写中间绘图区域 -->
	<div data-options="region:'center'" >
		<input type="hidden" id="diagramHeight" name="diagramHeight" value="620">
        <div id="myDiagram" style="height: 720px;"></div>
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
    <!-- 结束编写中间绘图区域 
    <div id="toolbarDiv" class="design_titletool" style="position:fixed;z-index:9999;display: block;top: 0px;right: 3px;background-color:#d8e4fe;
					height: 50px;width: 490px;padding-top: 10px;visibility: visible;" align="center">
					<a href="#" class="easyui-linkbutton" style="margin-top: 5px;">垂直对齐</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('2');">水平对齐</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('3');">撤销</a></br>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('4');">重做</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('5');">全选</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('6');">放大</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('7');">缩小</a></br>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('8');">剪切</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('9');">黏贴</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('10');">复制</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('11');">删除</a></br>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="designTool('12');">清除</a></br>
		<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-top: 5px;" onclick="loadTemplate();">加载模板</a>
					</div> 
<div id="toolbarDivShow" style="position:fixed;z-index:9999;display: block;top: 0px;right: 3px;background-color:#d8e4fe;
	height: 12px;width: 460px;visibility: visible;font-size: 12px;font-weight: bold;color: #0E2D5F;height: 16px;" align="center" 
	onclick="toolbarShow();" class="design_titletool">工具栏 
</div>
  -->
</body>
</html>
