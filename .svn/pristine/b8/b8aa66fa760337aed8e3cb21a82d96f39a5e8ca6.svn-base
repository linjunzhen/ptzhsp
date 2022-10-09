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

<title>平潭综合实验区商事主体登记申报系统 </title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

<script type="text/javascript"> 
	function contentCallpage(itemList){
		$("#contentList").html("");
		var newhtml = "<ul>";
		$.each( itemList, function(index, node){
			newhtml +='<li>';
			newhtml +='<span>'+node.ITEMRELDATE+'</span>';
			newhtml +='<a target="_blank" href="contentController/view.do?contentId='+node.TID+'">'+node.ITEMTITLE+'</a>';
			newhtml +='</li>';
		});
		newhtml += "</ul>";
		$("#contentList").html(newhtml);
	}
</script>
</head>

<body style="background: #f0f0f0;">

<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=${currentNav}" />
<div  class="eui-main">

<div class="container" >
	<div class="eui-crumbs">
		<ul>
			<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
			<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
			<li style="font-size:16px">${module.MODULE_NAME}</li>
		</ul>
	</div>
</div>
        
<div class="container"  style=" background:#fff;margin-bottom: 10px;">
	<div class="datalist"  id="contentList">
		 
	</div>
	<div class="syj-page">
		<%--开始引入分页JSP --%>
		<jsp:include page="../common/page.jsp" >
		<jsp:param value="contentController/pagelist.do?moduleId=${module.MODULE_ID}&endindex=42&timeout=2" name="pageURL"/>
		<jsp:param value="contentCallpage" name="callpage"/>
		<jsp:param value="15" name="limitNum"/>
		<jsp:param value="content" name="morename"/>
		</jsp:include>
		<%--结束引入分页JSP--%>
	</div>

</div>
</div>
<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
