<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%

%>
<tr>
	<th width="8%">序号</th>
	<th>材料名称</th>
	<th width="10%">材料类型</th>
	<th width="10%">是否需<br>电子材料</th>
	<th width="10%">纸质材料<br>份数</th>
	<th width="15%">填报须知</th>
	<th width="10%">材料说明</th>
</tr>
<c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
<tr>
	<td align="center">${varStatus.index+1}</td>
	<td> ${applyMater.MATER_NAME}</td>
	<td align="center">${applyMater.MATER_TYPE}</td>
	<td align="center"><c:if test="${fn:indexOf(applyMater.MATER_CLSMLX, '电子文档') != -1}">是</c:if>
		<c:if test="${fn:indexOf(applyMater.MATER_CLSMLX, '电子文档') == -1}">否</c:if></td>
	<td align="center">
		<c:if test="${applyMater.PAGECOPY_NUM==null&&applyMater.PAGE_NUM==null}">
			<c:if test="${applyMater.MATER_CLSMLX==null||applyMater.MATER_CLSMLX==''}">无</c:if>
			<c:if test="${applyMater.MATER_CLSMLX=='复印件'}">原件1份&nbsp;</c:if>
		</c:if>
		<c:if test="${applyMater.PAGECOPY_NUM!=null||applyMater.PAGE_NUM!=null}">
			<c:if test="${applyMater.PAGECOPY_NUM!=null}">复印件${applyMater.PAGECOPY_NUM}份</c:if>
			<c:if test="${applyMater.PAGE_NUM!=null}">原件${applyMater.PAGE_NUM}份（${applyMater.GATHERORVER}）</c:if>
		</c:if>

	</td>
	<td align="center">${applyMater.SQGZ}</td>
	<td align="center">${applyMater.MATER_DESC}</td>
</tr>
</c:forEach>