<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>平潭综合实验区商事主体登记申报系统</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
	
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript">
		
	</script>

</head>

<body style="background: #f0f0f0;">

	<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />
	<div  class="eui-main">
		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
				<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
				<li style="font-size:16px">网上申报</li>
			</ul>
		</div>
		<div  class="eui-main">	
		    <div class="classimg" style="margin-top: 0;">
			    <a href="<%=path%>/webSiteController.do?zzhywssb"><img src="<%=path%>/webpage/website/zzhy/images/process5.png" style="width: 1300px;" /></a>
			    <a href="<%=path%>/webSiteController.do?wssbmp"><img src="<%=path%>/webpage/website/zzhy/images/2021041203.png" style="width: 1300px;" /></a>
			     <a href="<%=path%>/webSiteController.do?bgzx"><img src="<%=path%>/webpage/website/zzhy/images/2021041201.png" style="width: 1300px;" /></a>
			    <%-- <a href="<%=path%>/webSiteController.do?relateItemSelect"><img src="<%=path%>/webpage/website/zzhy/images/class2.png" /></a> --%>
		    </div>
	  	</div>
	</div>
	
	<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
