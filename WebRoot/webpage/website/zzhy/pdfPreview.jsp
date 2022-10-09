<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
<base href="<%=basePath%>">
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>预览附件</title>
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwplayer.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwpsrv.js"></script>
<style>
	.ind_body{background: rgba(0, 0, 0, 0) none repeat scroll 0 0;}
</style>
<script>  
	$(function () {
		
	});
</script>  
</head>

<body class="ind_body">
<div  style="float:left; width:100%;">

	<!--<div id="pdf1" style="text-align: center;">如果您还没有安装 <a style="color:red;" target="_blank" href="https://get.adobe.com/cn/reader/">Adobe Reader</a> 阅读器软件呢,为了方便预览PDF文档,请下载安装！ </div>	
	<div style="width:900px;margin: 0 auto;margin-top: 10px;margin-bottom: 10px;">
		<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="800" height="1050" border="0">    
		<param name="_Version" value="65539">    
		<param name="_ExtentX" value="20108">    
		<param name="_ExtentY" value="10866">    
		<param name="_StockProps" value="0">    
		<param name="SRC" value="<%=path%>/${pdfPath}">    
			<object data="<%=path%>/${pdfPath}" type="application/pdf" width="100%" height="650">    
			</object>    
		</object>
	</div>-->
	
	<div style="width:800px;margin: 0 auto;margin-top: 10px;margin-bottom: 10px;">
		<c:forEach items="${imgList}" var="img" varStatus="varStatus">
			<img src="<%=path%>/${img}"  style="width:800px;border:1px solid #000"></br></br>		
		</c:forEach>
	</div>

</div>
</body>
</html>
