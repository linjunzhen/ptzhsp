<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${familyList}" var="family" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2" <c:if test="${s.index>0}">style="margin-top: -1px;"</c:if>>			
		<tr>
			<th ><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}FAMILY_NAME" maxlength="8" placeholder="请输入姓名" value="${family.FAMILY_NAME}"/></td>
			<th ><font class="syj-color2">*</font>身份证号码：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[vidcard]]"
				name="${currentTime}FAMILY_IDNO" maxlength="32" placeholder="请输入身份证号码" value="${family.FAMILY_IDNO}"/>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delJtcyxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>