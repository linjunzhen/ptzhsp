<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>场景式导航_平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.bslogo img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
</head>

<body class="bsbody">
	
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp?currentNav=sceneNavi" />
    <%--结束编写头部文件 --%>
    <div class="cjscontainer">
    	<div class="cjsswf">
    	<object width="783" height="465" name="cjs2016" id="cjs2016" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
            <param name="movie" value="<%=path%>/webpage/website/common/swf/cjs2016.swf"> 
            <param name="quality" value="High"> 
            <param name="wmode" value="transparent"> 
            <param name="allowScriptAccess" value="sameDomain"> 
            <embed src="<%=path%>/webpage/website/common/swf/cjs2016.swf" quality="High" pluginspage="http://www.macromedia.com/go/getflashplayer" width="783" height="465" name="cjs2016" id="cjs2016" wmode="transparent" allowscriptaccess="sameDomain" type="application/x-shockwave-flash"></object>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
