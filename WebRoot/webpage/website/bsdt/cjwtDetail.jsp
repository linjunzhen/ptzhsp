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
<html>
<head>
<title>常见问题</title>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete"></eve:resources>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>
	<!--[if lte IE 6]> 
	<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style type="text/css">
		.ind_body{background: rgba(0, 0, 0, 0) none repeat scroll 0 0;}
		.ftzbthcon {color: #333;padding: 8px 11px;}
		.ftzbthcon p {
		    line-height: 20px;
		    margin: 0 0 8px;
		    padding: 0 0 0 35px;
		}
		.ftzbicon {
		    background:url("webpage/website/common/images/ftIcon4.png") no-repeat scroll 0 0;
		}
		.ftzbicon1 {
		    background:url("webpage/website/common/images/ftIcon5.png") no-repeat scroll 0 0;
		}
	</style>
</head>
<body>
	
    <%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp" /> 
    <%--结束编写头部文件 --%>
    <div class="container lbpadding">
    	<div class="current">
		<span>您现在所在的位置：</span>
		<a href="webSiteController/view.do">首页</a> > <i>常见问题详细页</i></div>
        
        <div class="main clearfix" style="background: #fff;min-height: 424px;">
            <div class="detail">
                <!--startprint1-->
            	<h1 style="text-align:center;">${commonProblem.PROBLEM_TITLE}</h1>
            	<div id="detailTime" class="detailTime">${commonProblem.LASTER_UPDATETIME} </div> 
                <div id="cantianer" class="ftzbthcon" style="font-size:12pt;line-height: 1.8;">
			         <p id="fontzoom" class="ftzbicon">${commonProblem.PROBLEM_TITLE}</p>
					<div id="fontzoom1" class="ftzbicon1" style="margin:0 0 8px 0;padding: 0 0 0 35px;" class="clearfix">
					${commonProblem.ANSWER_CONTENT}
					</div>
				</div>       
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>