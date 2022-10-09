<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >联络员信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LIAISON_NAME" maxlength="8" value="${busRecord.LIAISON_NAME }"/></td>
		<td class="tab_width"> 固定电话：</td>
		<td><input type="text" class="tab_text"
			name="LIAISON_FIXEDLINE"  value="${busRecord.LIAISON_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LIAISON_MOBILE" value="${busRecord.LIAISON_MOBILE }"/></td>
		<td class="tab_width"> 电子邮箱：</td>
		<td><input type="text" class="tab_text validate[[],custom[email]]"
			name="LIAISON_EMAIL" maxlength="32" value="${busRecord.LIAISON_EMAIL }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LIAISON_IDNO"  value="${busRecord.LIAISON_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定或委托的有效期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="vstart" readonly="readonly"
			name="L_VALIDITY_START_DATE"  value="${busRecord.L_VALIDITY_START_DATE }"/>至
			<input type="text" class="laydate-icon validate[required]" id="vend" readonly="readonly"
			name="L_VALIDITY_END_DATE"  value="${busRecord.L_VALIDITY_END_DATE }"/></td>
	</tr>
</table>
