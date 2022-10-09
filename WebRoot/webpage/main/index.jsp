<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="msthemecompatible" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>

<title>平潭综合实验区综合审批平台</title>
<link rel="stylesheet" type="text/css" href="webpage/main/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/eveflow.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/ztree-3.5/css/demo.css"/>
<script type="text/javascript" src="plug-in/ztree-3.5/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript">
  $(function(){
	  __curUser =AppUtil.getLoginUserInfo();
	  AppUtil.disableFormAutoSubmit();
	  AppUtil.setSessionTimeOut();
	  AppUtil.extendsEasyDataGrid();  
	  $(".westScroll").slimscroll({
		    width: "224px", 
		    height: "auto"
		});
  });
</script>
</head>

<body class="easyui-layout">   
    <!-- 引入头部界面 -->
    <div data-options="region:'north'" style="height:72px;border:0px;">
       <jsp:include page="./header.jsp"></jsp:include>
    </div>   
    <!-- 引入左边菜单界面 -->
    <div data-options="region:'west'" title="导航菜单" class="westScroll"  style="width:224px;background:#fdfeff;border:0px; overflow:hidden;box-shadow: 0 0 15px rgba(62, 139, 255, .2); ">
       <jsp:include page="./left.jsp"></jsp:include>
    </div> 
    <!-- 引入中央面板 -->
    <div data-options="region:'center'" style="border:0;">
       <jsp:include page="./center.jsp"></jsp:include>
    </div>   
</body>

</html>

