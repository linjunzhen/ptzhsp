<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}MANAGER_NAME"  placeholder="请输入姓名"  maxlength="8"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:03"
				dataInterface="dictionaryService.findTwoDatasForSelect"
				defaultEmptyText="请选择职务" name="${currentTime}MANAGER_JOB">
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<c:if test="${isClose!='1'}">
	<a href="javascript:void(0);"
			onclick="delDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>