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
<title>可视化流程定义</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
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
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/design.js"></script>
<script type="text/javascript">
    $(function(){
    	initFlowDesign(false,true);
    });
    
</script>
</head>

<body class="easyui-layout bodySelectNone" >
	<!-- 开始编写头部工具栏 -->
	<div data-options="region:'north',collapsible:false" split="true" border="false"
		title="工具栏" class="design_titletool">
		<img alt="预览设计代码" title="预览设计代码" onclick="showDesignCode('1');"
			src="plug-in/eveflowdesign-1.0/images/zoom.png"
			class="design_titlebuttonStyle" />
		<img alt="导入设计代码" title="导入设计代码" onclick="showDesignCode('2');"
			src="plug-in/eveflowdesign-1.0/images/copy.png"
			class="design_titlebuttonStyle" />
		<img alt="垂直对齐" title="垂直对齐" onclick="nodeAlign('v');"
			src="plug-in/eveflowdesign-1.0/images/czdq.png"
			class="design_titlebuttonStyle" />
		<img alt="水平对齐" title="水平对齐" onclick="nodeAlign('h');"
			src="plug-in/eveflowdesign-1.0/images/smdq.png"
			class="design_titlebuttonStyle" />
	</div>
	<!-- 结束编写头部工具栏 -->
	
	<!-- 开始编写基本信息 -->
	<div data-options="region:'east',split:true" title="基本信息"  style="width:250px;background-color: #d8e4fe;" >
	     <form name="FLowDesignForm" action="flowDefController.do?saveOrUpdate">
	     <input type="hidden" name="DEF_JSON" id="DEF_JSON" value="${flowDef.DEF_JSON}">
	     <input type="hidden" name="DEF_XML" id="DEF_XML" >
	     <input type="hidden" name="DEF_ID" value="${flowDef.DEF_ID}">
	     <input type="hidden" name="VERSION" value="${flowDef.VERSION}" >
	     <input type="hidden" name="BIND_FORMID" value="${flowDef.BIND_FORMID}" id="BIND_FORMID" >
	     <p class="fix_height clearfix">
		    <label class="text-Lh">流程名称:</label>
		    <input type="text" name="DEF_NAME" value="${flowDef.DEF_NAME}" class="checkout-input" >
		  </p>
		 <p class="fix_height clearfix">
		    <label class="text-Lh">流程KEY:</label>
		    <input type="text" name="DEF_KEY" value="${flowDef.DEF_KEY}" 
		     <c:if test="${flowDef.DEF_ID!=null}">readonly="readonly"</c:if> class="checkout-input" >
		 </p>
		 <p class="fix_height clearfix">
		    <label class="text-Lh">所属类别:</label>
		    <eve:eveselect clazz="checkout-input" dataParams=" "
					         dataInterface="flowTypeService.findForSelect" defaultEmptyText="请选择表单类别"
					         value="${flowDef.TYPE_ID}" name="TYPE_ID">
		    </eve:eveselect>
		    <!--  
		    <input type="text" class="checkout-input" readonly="readonly" value="${flowDef.TYPE_NAME}" >
		    -->
		 </p>
		 <p class="fix_height clearfix">
		    <label class="text-Lh">绑定表单:</label>
		    <input type="text" name="BIND_FORMNAME" id="BIND_FORMNAME" value="${flowDef.BIND_FORMNAME}" <c:if test="${flowDef.BIND_FORMID==null}">onclick="showSelectForms();"</c:if>  readonly="readonly"  class="checkout-input" >
		  </p>
		 <p class="fix_height clearfix">
		    <input type="button" onclick="submitDesign();" value="部署流程" class="checkout-btn">
		 </p>
		</form>
	</div>
	<!-- 结束编写基本信息 -->
	
	<!-- 开始编写左侧图元区工具栏 -->
	<div data-options="region:'west',split:true,collapsible:false" title="图元区"
		class="design_leftcontent" >
		<div id="myPalette" style="height: 720px"></div>
    </div>
    <!-- 结束编写左侧图元区工具栏 -->
    
    <!-- 开始编写中间绘图区域 -->
	<div
		data-options="region:'center',title:'绘图区'">
        <div id="myDiagram" style="height: 1000px;"></div>
	</div>
    <!-- 结束编写中间绘图区域 -->

</body>
</html>
