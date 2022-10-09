<%@page import="net.evecom.platform.hflow.service.FlowMappedService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   String defId = request.getParameter("defId");
   String nodeName = request.getParameter("nodeName");
   FlowMappedService flowMappedService = (FlowMappedService) AppUtil.getBean("flowMappedService");
   Map<String,Object> mapped = flowMappedService.getByDefidAndNodeName(defId, nodeName);
   request.setAttribute("mapped", mapped);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
    <script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
</head>
<body class="bsbody">
<div class="bsbox clearfix" >
	<div class="bsboxT">
    	<ul>
        	<li class="on" style="background:none"><span>审批记录</span></li>
        </ul>
    </div>
   	<div class="flow_container" style="height: 65px;">
           <ul>
               <c:forEach var="m" items="${mapped.mappedList}">
               <li class="${m.nodeClass}" style="width:${m.nodeWidth}"><div class="pro_base"></div><a href="javascript:void(0);">${m.YS_NAME}</a></li>
               </c:forEach>
           </ul>
           <div class="flow_progress" style="margin:0 ${mapped.flow_progress};">
               <div class="flow_progress_bar">
                   <span class="flow_progress_highlight" style="width:${mapped.allHighLight};"></span>
               </div>
           </div>
     </div>
            
</div>
</body>