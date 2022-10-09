<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ page import="net.evecom.platform.wsbs.service.TabletMenuService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//BusTypeService busTypeService = (BusTypeService)AppUtil.getBean("busTypeService");
//List<Map<String,Object>> bmfwList = busTypeService.findByTypeCodeForWebSite("BMFW");
//request.setAttribute("bmfwList", bmfwList);
TabletMenuService tabletMenuService = (TabletMenuService)AppUtil.getBean("tabletMenuService");
List<Map<String,Object>> bmfwList = tabletMenuService.findBSDeptForWebSite();
request.setAttribute("bmfwList", bmfwList);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>平潭综合实验区行政服务中心-权责清单</title>
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.bslogo img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
<script type="text/javascript">
	function TabHeads(c,a,b){
		$(c).hover(function(){
			$(c).each(function(){
				$(this).removeClass(a)
			});
			$(b).each(function(){
				$(this).hide()
			});
			$(this).addClass(a);
			var d=$(c).index(this);
			$(b).eq(d).each(function(){
				$(this).show();
			});
		})
	}
	$(document).ready(function(){
		TabHeads("#listContainer li","bslistOn","#listContainer h3");
	})
</script>
</head>

<body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp?currentNav=qlqd" />
    <%--结束编写头部文件 --%>
    <div class="container">
    	<div class="bsMain clearfix">
        	
          
			
            <%--开始映入列表页 --%>
            <jsp:include page="../common/qzqdlist.jsp" />
            <%--开始映入列表页 --%>
             <%--开始引入分页JSP --%>
            <jsp:include page="../common/page.jsp" >
            <jsp:param value="billRightController/qzqdlist.do" name="pageURL"/>
            <jsp:param value="callpage" name="callpage"/>
            <jsp:param value="10" name="limitNum"/>
            <jsp:param value="bsForm" name="pageFormId"/>
            </jsp:include>
            <%--结束引入分页JSP--%>           
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
