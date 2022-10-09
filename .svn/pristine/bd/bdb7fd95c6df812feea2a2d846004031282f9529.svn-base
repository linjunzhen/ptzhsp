<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >经办人和财务负责人和办税人员信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经办人（被委托人）姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="OPERATOR_NAME" maxlength="8" value="${busRecord.OPERATOR_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="OPERATOR_MOBILE" value="${busRecord.OPERATOR_MOBILE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 固定电话：</td>
		<td colspan="3"><input type="text" class="tab_text"
			name="OPERATOR_FIXEDLINE" value="${busRecord.OPERATOR_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${busRecord.OPERATOR_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="OPERATOR_IDNO" maxlength="32" value="${busRecord.OPERATOR_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>财务负责人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="FINANCE_NAME" maxlength="8" value="${busRecord.FINANCE_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>财务负责人联系电话：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="FINANCE_MOBILE"  value="${busRecord.FINANCE_MOBILE }"/></td>
	</tr>
	<tr><td class="tab_width"> 财务负责人固定电话：</td>
		<td><input type="text" class="tab_text"
			name="FINANCE_FIXEDLINE"  value="${busRecord.FINANCE_FIXEDLINE }"/></td>
		<td class="tab_width"> 财务负责人电子邮箱：</td>
		<td><input type="text" class="tab_text validate[[],custom[email]]"
			name="FINANCE_EMAIL"  value="${busRecord.FINANCE_EMAIL }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="FINANCE_IDTYPE" value="${busRecord.FINANCE_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]" onblur="checkDifferent(this.name);"
			name="FINANCE_IDNO" maxlength="32" value="${busRecord.FINANCE_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>核算方式：</td>
		<td colspan="3">
			<input type="radio" name="ACCOUNTING_METHOD" value="1" <c:if test="${busRecord.ACCOUNTING_METHOD!=2 }">checked="checked"</c:if> >独立核算
			<input type="radio" name="ACCOUNTING_METHOD" value="2" <c:if test="${busRecord.ACCOUNTING_METHOD==2 }">checked="checked"</c:if> >非独立核算
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>办税人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="TAXMAN_NAME" maxlength="8" value="${busRecord.TAXMAN_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="TAXMAN_PHONE"  value="${busRecord.TAXMAN_PHONE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="TAXMAN_IDTYPE" value="${busRecord.TAXMAN_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="TAXMAN_IDNO" maxlength="32" value="${busRecord.TAXMAN_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定或委托的有效期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="vstart" readonly="readonly"
			name="VALIDITY_START_DATE"  value="${busRecord.VALIDITY_START_DATE }"/>至
			<input type="text" class="laydate-icon validate[required]" id="vend" readonly="readonly"
			name="VALIDITY_END_DATE"  value="${busRecord.VALIDITY_END_DATE }"/></td>
	</tr>
</table>
