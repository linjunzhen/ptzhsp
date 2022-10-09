<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.ExecutionService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	ExecutionService executionService = (ExecutionService)AppUtil.getBean("executionService");
    List<Map<String,Object>> bjsxList = executionService.getEndExecution();
    request.setAttribute("bjsxList", bjsxList);
%>
<table cellpadding="0" cellspacing="0" class="indtable tmargin16">
	<tr>
		<th width="140px">受理部门</th>
		<th>受理事项</th>
		<th width="110px">申报日期</th>
		<th width="120px">应办结日期</th>
		<th width="120px">办件状态</th>
	</tr>
	<c:forEach items="${bjsxList}" var="bjsxInfo">
		<tr>
			<td width="140px">${bjsxInfo.DEPART_NAME}</td>
			<td><a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=${bjsxInfo.ITEM_CODE}" title="${bjsxInfo.ITEM_NAME}">${bjsxInfo.ITEM_NAME}</a></td>
			<td width="110px">${bjsxInfo.CREATE_TIME}</td>
			<td width="120px">${bjsxInfo.END_TIME}</td>
			<td width="120px">${bjsxInfo.STATUS}</td>
		</tr>
	</c:forEach>
</table>

