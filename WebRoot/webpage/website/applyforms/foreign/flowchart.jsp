<%@page import="net.evecom.platform.hflow.service.FlowMappedService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<%
   String defId = request.getParameter("defId");
   String nodeName = request.getParameter("nodeName");
   FlowMappedService flowMappedService = (FlowMappedService) AppUtil.getBean("flowMappedService");
   Map<String,Object> mapped = flowMappedService.getByDefidAndNodeName(defId, nodeName);
   request.setAttribute("mapped", mapped);
%>
<c:forEach var="m" items="${mapped.mappedList}" varStatus="vs">
	<div <c:if test="${m.nodeClass=='flow_active'}">class="current"</c:if> 
	<c:if test="${m.nodeClass!='flow_active'&&(vs.index+1)<fn:length(mapped.mappedList)}">class="todo"</c:if>
	<c:if test="${m.nodeClass!='flow_active'&&(vs.index+1)==fn:length(mapped.mappedList)}">class="todo eui-last"</c:if>>
		<div class="eui-wrap"><div class="eui-round">${vs.index+1}</div>
		<c:if test="${(vs.index+1)<fn:length(mapped.mappedList)}"><div class="eui-bar"></div></c:if>
		</div>
		<label>${m.YS_NAME}</label>
	</div>
</c:forEach>