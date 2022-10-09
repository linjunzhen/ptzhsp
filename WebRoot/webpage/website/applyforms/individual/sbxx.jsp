<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
  String registerType = request.getParameter("registerType");
  request.setAttribute("registerType", registerType);
%>
<form action="" id="SBXX_FORM"  style="padding: 25px 30px;">	
	<%--开始引入公共隐藏域部分 --%>
	<jsp:include page="/webpage/website/applyforms/commonhidden.jsp" />	
	<input type="hidden" name="REGISTER_TYPE" value="${registerType}"/>
	<%--结束引入公共隐藏域部分 --%>	
	<table cellpadding="0" cellspacing="0" class="syj-table1">
		<tr>
			<th>审批事项：</th>
			<td>${serviceItem.ITEM_NAME}</td>
			<th>所属部门：</th>
			<td>${serviceItem.SSBMMC}</td>
		</tr>
		<tr>
			<th>审批类型：</th>
			<td>${serviceItem.SXLX}</td>
			<th>承诺时间：</th>
			<td>${serviceItem.CNQXGZR} (工作日)</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font> 申报名称：</th>
			<td colspan="3"><input name="SBMC" style="width: 55%;" type="text" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}"
			class="syj-input validate[required]" maxlength="256"  />
			<span class="bscolor1"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
		</tr>		
	</table>
</form>
