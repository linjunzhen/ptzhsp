<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${oldMangerList}" var="oldManger" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${s.index}MANAGER_NAME"  placeholder="请输入姓名"  maxlength="8" value="${oldManger.MANAGER_NAME}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:03"
				dataInterface="dictionaryService.findTwoDatasForSelect" value="${oldManger.MANAGER_JOB}"
				defaultEmptyText="请选择职务" name="${s.index}MANAGER_JOB">
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0 && isClose!='1'}">
	<a href="javascript:void(0);"
			onclick="delDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>