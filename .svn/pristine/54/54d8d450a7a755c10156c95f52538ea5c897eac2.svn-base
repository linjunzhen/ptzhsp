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
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!--[if lte IE 6]> 
<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.bslogo img,.cjslistBg');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
<script type="text/javascript">
	
</script>
</head>

<body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp?currentNav=sceneNavi" />
    <%--结束编写头部文件 --%>
    <div class="cjscontainer">
    	<div class="cjslistBg">
    		<div class="current1"><span>您现在所在的位置：</span><a href="webSiteController/view.do?navTarget=website/bsdt/index">首页</a> > <a href="webSiteController/view.do?navTarget=website/bsdt/sceneNaviIndex">场景式导航</a> > <a href="busTypeController/sceneNavi.do?busCode=${busCode}">${navi}</a> > <i>${busType.TYPE_NAME}</i></div>
            <div class="scrollH">
            	<div class="cjscont">
                	<ul>
					<c:forEach items="${list}" var="list" varStatus="itemList">
                    	<li><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=${list.ITEM_CODE}">${itemList.index+1}、${list.ITEM_NAME}</a></li>
					</c:forEach>
					</ul>
                </div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
    <script type="text/javascript">
		$('.scrollH').slimscroll({
			height: '278px'
		});
	</script>
</body>
</html>
