<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${supervisorList}" var="supervisor" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]"  readonly="readonly"
				name="SUPERVISOR_NAME_OLD"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="32" value="${supervisor.SUPERVISOR_NAME_OLD}"/></td>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>	
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02"
					dataInterface="dictionaryService.findTwoDatasForSelect"
					defaultEmptyText="请选择职务" name="SUPERVISOR_JOB_OLD"
					value="${supervisor.SUPERVISOR_JOB_OLD}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function(){						
						$("[name='SUPERVISOR_JOB_OLD']").attr("disabled",true);
					});
				</script>
			</td>
		</tr>
	</table>
</div>
</c:forEach>