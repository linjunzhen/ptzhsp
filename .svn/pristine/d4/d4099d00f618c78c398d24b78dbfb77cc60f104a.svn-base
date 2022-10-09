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
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/changeflowUtils.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/flowchange/js/flowNewOldView.js"></script>
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
var showflg=true;

$(function(){
	$('#tt').tabs({  
      border:false,  
      onSelect:function(title){  
          //alert(title+' is selected');  
           //var pp = $('#tt').tabs('getSelected');//;$(this).attr("id"); 
           //var tab = pp.panel('options').tab; 
           var id=$('#tt').tabs('getSelected').attr("id");
           if(id=="oldPanel"){
           	  var span0=document.getElementById("span_old_0");
				if(typeof(span0)!="undefined"){
					span0.click();
				}
           }else if(id=="newPanel"){
           	  var span0=document.getElementById("span_0");
				if(typeof(span0)!="undefined"){
					span0.click();
				}
           } 
      }  
  });  
});
/**
 $('#tt').tabs('add',{  
     title:'New Tab',  
     content:'Tab Body',  
      closable:true 
  });  
  **/
function initflow(){
	//显示工具栏时，显示工具栏面板头toolbarDivShow
	init("myDiagram",true,1,"","","",false,"","",2);
	myDiagram.div.style.height="500px";
	document.getElementById("sample").style.width=parseFloat(screen.availWidth)-200+"px";
	document.getElementById("newPanel").style.height=parseFloat(screen.availHeight)-160+"px";
	myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
	var span0=document.getElementById("span_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
}  
function initflowOld(){
	//显示工具栏时，显示工具栏面板头toolbarDivShow
	init("myDiagramOld",true,1,"","","",false,"","",2);
	myDiagram.div.style.height="500px";
	document.getElementById("sampleOld").style.width=parseFloat(screen.availWidth)-200+"px";
	document.getElementById("oldPanel").style.height=parseFloat(screen.availHeight)-160+"px";
	myDiagram.div.style.width=parseFloat(screen.availWidth)-220+"px";
	var span0=document.getElementById("span_old_0");
	if(typeof(span0)!="undefined"){
		span0.click();
	}
}

</script>
</head>
<body class="easyui-layout" onload="initflow()">
	<input type="hidden" id="busCode" name="busCode" value="${busInfo.busCode}">
	<div id="tt" class="easyui-tabs" style="width:100%;height:60px;"> 
	<!-- #############################tab1 start######################## --> 
     <div id="newPanel" title="新的流程图" style="padding:3px;display:block;width:100%;height: 50px;"> 
          <div region="north" style="height:36px;background:white;font-size: medium;">
		<div id="courseDiv" style="padding-top: 3px;padding-left: 160px;" >
			<c:if test="${empty newTacheList}">暂无数据</c:if>
			<c:forEach items="${newTacheList}" var="tacheInfo" varStatus="status">
			 <span style="cursor: pointer;background:silver;width:auto;" id="span_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='silver';"
			 onclick="loadFlow('${tacheInfo.tacheCode}');">${tacheInfo.tacheName}
			 <span style="">&clubs;></span> <sapn></span>
			</c:forEach>
		</div>
	</div>
      </div> 
 <!-- #############################tab1结束######################## -->   
  <!-- #############################tab2 start######################## -->  
      <div id="oldPanel" title="旧的流程图" style="overflow:auto;padding:3px;"> 
          <div region="north" style="height:36px;background:white;font-size: medium;">
          <input type="hidden" id="applyId" name="applyId" value="${busInfo.applyId}">
			<div style="padding-top: 3px;padding-left: 160px;" >
			<c:if test="${empty oldTacheList}">暂无数据</c:if>
			<c:forEach items="${oldTacheList}" var="oldtacheInfo" varStatus="status">
			 <span style="cursor: pointer;background:silver;width:auto;" id="span_old_${status.index}" 
			 onmouseover="this.style.background='#00A9C9';" onmouseout="this.style.background='silver';"
			 onclick="loadFlowOld('${oldtacheInfo.tacheCode}');">${oldtacheInfo.tacheName}
			 <span style="">&clubs;></span> <sapn></span>
			</c:forEach>
			</div>
			</div>
     </div> 
     <!-- ##########################newpanel end -->
     <!-- 
      <div title="Tab3" iconCls="icon-reload" closable="true" style="padding:20px;display:none;"> 
          tab3  wq
     </div>  -->
 </div> 
 <!-- 图面板 -->
 <div id="pictureDiv">
 	<!--1.1 egion="north"，指明高度，可以自适应-->
	
	<!--1.2region="center",这里的宽度和高度都是由周边决定，不用设置-->
	<div region="center">
 		<!--对<div>标签引用'easyui-layout'类,fit="true"自动适应父窗口,这里我们指定了宽度和高度-->
 		<div id="sample" style="height: 100%;top: 20px;width:86%;margin-left: 136px;">
			<span style="display: inline-block; vertical-align: top; padding: 5px; 
					width:99%;overflow: hidden;height: 100%;">
      			<input type="hidden" id="diagramHeight" name="diagramHeight" value="1600">
      			<div id="myDiagram" style="border: solid 1px gray;" onmouseout="diagramOut();"></div>
    		</span>
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
  "nodeDataArray": [{"key":-1, "category":"start", "loc":"520 50", "text":"开始"}
 ],
  "linkDataArray": [
 ]}
  </textarea>
</div>
   
 	</div>
 </div> 
</body>
</html>
