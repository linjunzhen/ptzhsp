<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.ServiceItemService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ServiceItemService serviceItemService = (ServiceItemService)AppUtil.getBean("serviceItemService");
    List<Map<String,Object>> shtzList = serviceItemService.findByCatalogCodeForWebSite("350128XK001");
    List<Map<String,Object>> zftzList = serviceItemService.findByCatalogCodeForWebSite("350128XK002");
    request.setAttribute("shtzList", shtzList);
    request.setAttribute("zftzList", zftzList);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp" /> 
    <%--结束编写头部文件 --%>
    
    <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>投资项目申报</i></div>
        <div class="bsMain1 clearfix">
            <div class="bsboxCon lfloat">
                <div class="bsboximg"><img src="webpage/website/common/images/bsboximg.png" /></div>
                <div class="list2">
                    <ul>
                        <c:forEach items="${shtzList}" var="info">
                        <li><a target="_blank" 
                        href="serviceItemController/bsznDetail.do?itemCode=${info.ITEM_CODE}">${info.ITEM_NAME}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="bsboxCon rfloat">
                <div class="bsboximg"><img src="webpage/website/common/images/bsboximg1.png" /></div>
                <div class="list2">
                    <ul>
                        <c:forEach items="${zftzList}" var="info">
                        <li><a target="_blank" 
                        href="serviceItemController/bsznDetail.do?itemCode=${info.ITEM_CODE}">${info.ITEM_NAME}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
