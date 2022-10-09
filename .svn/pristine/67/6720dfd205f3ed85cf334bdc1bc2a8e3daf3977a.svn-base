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
<html>
<head>
<title>手机门户_平潭综合实验区行政服务中心</title>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery2.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete"></eve:resources>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>


	<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');
	</script> 

	<style>
		.ind_body{background: rgba(0, 0, 0, 0) none repeat scroll 0 0;}
		

	</style>
<script>  
   
</script>  
</head>
<body>
	
    <jsp:include page="../index/head.jsp?currentNav=sy" />
    <div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span>
			<a target="_top" href="<%=path%>/webSiteController/view.do">首页</a> &gt;
        </div>
        <div class="listMain1 clearfix">
        	<div class="sjmh">
            	<h1>平潭移动办事平台</h1>
                <div class="mhL">
                	<div class="mhLo"><div class="mhLbg">
						<img src="<%=path%>/webpage/website/common/images/app/android.png" />
						<p><img src="<%=path%>/webpage/website/common/images/app/mhaand.png" />Android下载</p>
					</div>
					<div class="mhLbg tmargin10">
						<img src="<%=path%>/webpage/website/common/images/app/ios.png" />
						<p><img src="<%=path%>/webpage/website/common/images/app/mhaios.png" />iphone下载</p>
					</div>
					</div>
                    <div class="mhLt">
						<div class="mhLbg tmargin98"><img src="<%=path%>/webpage/website/common/images/app/mhrem.png" /><p>官方微信</p></div>
					</div>
                    <p style="width: 420px;">用手机扫描以上二维码</p>
                </div>
                <div class="mhJt"><img src="<%=path%>/webpage/website/common/images/app/mhjt.png" /></div>
                <div class="mhC"><img src="<%=path%>/webpage/website/common/images/app/mhrem.png" /></div>
                <div class="mhJt"><img src="<%=path%>/webpage/website/common/images/app/mhjt.png" /></div>
                <div class="mhR">
                	<div class="mhRtel"><img src="<%=path%>/webpage/website/common/images/app/mhsj1.png" /></div>
                    <p>访问平潭移动办事平台</p>
                </div>
            </div>
        </div>
    </div>
	
	<jsp:include page="../index/foot.jsp" />
</body>
</html>