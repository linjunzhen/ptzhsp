<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${oldDirectorList}" var="oldDirectorL" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" value="${oldDirectorL.DIRECTOR_NAME}" 
				name="${s.index}DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:10,11,12,20,21"
				dataInterface="dictionaryService.findDatasForSelect" value="${oldDirectorL.DIRECTOR_JOB}"
				defaultEmptyText="请选择职务" name="${s.index}DIRECTOR_JOB">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}_OLD_DIRECTOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${s.index}DIRECTOR_IDTYPE" value="${oldDirectorL.DIRECTOR_IDTYPE}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function() {
						setZjValidate('${oldDirectorL.DIRECTOR_IDTYPE}','${s.index}_OLD_DIRECTOR_IDNO');				
					});
				</script>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}_OLD_DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${oldDirectorL.DIRECTOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]" 
				name="${s.index}DIRECTOR_PHONE"  placeholder="请输入手机号码"  maxlength="32" value="${oldDirectorL.DIRECTOR_PHONE}"/>
			</td>
		</tr>
	</table>
	<c:if test="${s.index>0 && isClose!='1'}">
	<a href="javascript:void(0);"
			onclick="delDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>