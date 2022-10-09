<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${directorList}" var="director" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"  readonly="readonly" onchange="setSelectOldDsName(this.value)"
				name="DIRECTOR_NAME_OLD"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="32" value="${director.DIRECTOR_NAME_OLD}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>	
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:01,04"
					dataInterface="dictionaryService.findTwoDatasForSelect"
					defaultEmptyText="请选择职务" name="DIRECTOR_JOB_OLD"
					value="${director.DIRECTOR_JOB_OLD}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function(){						
						$("[name='DIRECTOR_JOB_OLD']").attr("disabled",true);
					});
				</script>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'DIRECTOR_IDNO_OLD');" 
				defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE_OLD" value="${director.DIRECTOR_IDTYPE_OLD}" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="DIRECTOR_IDNO_OLD"  placeholder="请输入证件号码"  maxlength="32" value="${director.DIRECTOR_IDNO_OLD}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]"
				name="DIRECTOR_PHONE_OLD"  placeholder="请输入手机号码"  maxlength="32" value="${director.DIRECTOR_PHONE_OLD}"/>
			</td>
		</tr>
	</table>
</div>
</c:forEach>