<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${pratnerList}" var="pratner" varStatus="s">
<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>				
			<th><font class="syj-color2">*</font>执行事务合伙人：</th>
			<td colspan="3">
				<select name="${currentTime}PARTNER_PARTNERSHIP" class="input-dropdown validate[required]"  style="width: auto;">
					<option value="">请选择执行事务合伙人</option>
					<option value="${pratner.PARTNER_PARTNERSHIP}" selected>${pratner.PARTNER_PARTNERSHIP}</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="${currentTime}PARTNER_NAME"  placeholder="请输入姓名" maxlength="8" value="${pratner.PARTNER_NAME}"/></td>
			<th> 固定电话：</th>
			<td><input type="text" class="syj-input1 validate[]"
				name="${currentTime}PARTNER_FIXEDLINE" placeholder="请输入固定电话" value="${pratner.PARTNER_FIXEDLINE}"  maxlength="16"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}PARTNER_MOBILE"  placeholder="请输入移动电话" value="${pratner.PARTNER_MOBILE}"  maxlength="16"/></td>
			<th> 电子邮箱：</th>
			<td><input type="text" class="syj-input1 validate[],custom[email]"
				name="${currentTime}PARTNER_EMAIL" placeholder="请输入电子邮箱" maxlength="32"  value="${pratner.PARTNER_EMAIL}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'PARTNER_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${currentTime}PARTNER_IDTYPE" value="${pratner.PARTNER_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}PARTNER_IDNO" placeholder="请输入证件号码" maxlength="32" value="${pratner.PARTNER_IDNO}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a href="javascript:void(0);"
			onclick="delZxswhhrDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>