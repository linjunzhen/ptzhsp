<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${chartreviewnumList}" var="chartreviewnum" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" 
	<c:if test="${s.index>0}">style="margin-top: -1px;"</c:if>>			
		<tr>
			<th><font class="syj-color2">*</font>证书编号</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]" value="${chartreviewnum.CHARTREVIEWNUM}" 
				name="${s.index}CHARTREVIEWNUM" maxlength="32" placeholder="请输入施工图审查合格证书编号" /></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delChartreviewnumDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>