<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="msthemecompatible" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
<eve:resources loadres="jquery,easyui,apputil,layer,laydate,ztree,artdialog"></eve:resources>

<title>可视化代码建模工具</title>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style.css"/>
<script type="text/javascript" src="webpage/developer/codeMission/js/design.js"></script>
</head>

<body class="easyui-layout">   
    <input type="hidden" id="missionId" value="${missionId}">
    
    <%--引入头部工具栏 --%>
	<div region="north" style="height:22px;">
			<ul>
				<li style="float:left;"><img alt="生成代码" title="生成代码"
					style="cursor: pointer; float:left;"
					onclick="genCode('${missionId}')"
					src="plug-in/easyui-1.4/themes/icons/filesave.png" />生成代码</li>
				<li style="float:left;"><img alt="预览代码" title="预览代码"
					style="cursor: pointer; float:left;"
					src="plug-in/easyui-1.4/themes/icons/search.png" onclick="previewCode('${missionId}')"/> 预览代码</li>
			</ul>
	</div>

	<!-- 引入左边菜单界面 -->
    <div region="west"  split="false" border="false" width="200px;" title="控件树" collapsible="true">
        <ul id="controlTree" class="ztree"></ul>
    </div>   
    <!-- 引入中央面板 -->
    <div region="center">
        <jsp:include page="./genCode.jsp"></jsp:include>
    </div>   
    <%--引入东部界面 --%>
    <div region="east" width="250px;" title="已配置控件树" split="true" collapsible="true">
       <ul id="controlConfigTree" class="ztree"></ul>
    </div>
    
</body>

</html>

