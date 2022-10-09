<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${busRecord.DIRECTOR_NAME}"
				name="${currentTime}DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:10,11,12,20,21"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DIRECTOR_JOB}"
				defaultEmptyText="请选择职务" name="${currentTime}DIRECTOR_JOB">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280  validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}DIRECTOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${currentTime}DIRECTOR_IDTYPE" value="${director.DIRECTOR_IDTYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${director.DIRECTOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]"
				name="${currentTime}DIRECTOR_PHONE"  placeholder="请输入手机号码"  maxlength="32" value="${director.DIRECTOR_PHONE}"/>
			</td>
		</tr>
	</table>
	<c:if test="${isClose!='1'}">
	<a href="javascript:void(0);"
			onclick="delDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>