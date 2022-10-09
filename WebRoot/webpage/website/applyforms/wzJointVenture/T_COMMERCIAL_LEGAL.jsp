<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="LEGAL_FORM"  >
	<div class="syj-title1 tmargin20">
		<span>经办人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>指定或委托的有效期限：</th>
				<td colspan="3">
					<input type="text" class="Wdate validate[required]" id="VALIDITY_START_DATE" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'VALIDITY_END_DATE\')}'})"
					readonly="readonly" name="VALIDITY_START_DATE"  placeholder="请输入开始时间" value="${busRecord.VALIDITY_START_DATE}"  maxlength="16"/>至
					<input type="text" class="Wdate validate[required]" id="VALIDITY_END_DATE" readonly="readonly"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{M:1})||\'%y-{%M+1}-%d\'}'
					,maxDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{y:1})||\'{%y+1}-%M-%d\'}'})"
					name="VALIDITY_END_DATE"  placeholder="请输入结束时间" value="${busRecord.VALIDITY_END_DATE}"  maxlength="16"/></td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>经办人（被委托人）</br>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_NAME"  placeholder="请输入经办人（被委托人）姓名"  maxlength="8"  value="${busRecord.OPERATOR_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_MOBILE"  placeholder="请输入移动电话" value="${busRecord.OPERATOR_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th> 固定电话：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[]"
					name="OPERATOR_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.OPERATOR_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'OPERATOR_IDNO');"
					defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${busRecord.OPERATOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="OPERATOR_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.OPERATOR_IDNO}" /></td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>财务负责人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>财务负责人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_NAME"  placeholder="请输入财务负责人姓名" maxlength="8" value="${busRecord.FINANCE_NAME}"/></td>
				<th><font class="syj-color2">*</font>财务负责人移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_MOBILE"  placeholder="请输入财务负责人移动电话" value="${busRecord.FINANCE_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr><th> 财务负责人固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="FINANCE_FIXEDLINE"  placeholder="请输入财务负责人固定电话" value="${busRecord.FINANCE_FIXEDLINE}"  maxlength="16"/></td>
				<th> 财务负责人电子邮箱：</th>
				<td><input type="text" class="syj-input1  validate[],custom[email]"
					name="FINANCE_EMAIL"  placeholder="请输入财务负责人电子邮箱" maxlength="32" value="${busRecord.FINANCE_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'FINANCE_IDNO');" 
					defaultEmptyText="请选择证件类型" name="FINANCE_IDTYPE" value="${busRecord.FINANCE_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="FINANCE_IDNO" placeholder="请输入证件号码"  maxlength="32" value="${busRecord.FINANCE_IDNO}"/></td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>办税人员信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>办税人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="TAXMAN_NAME"  placeholder="请输入办税人姓名"  maxlength="8" value="${busRecord.TAXMAN_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="TAXMAN_PHONE"   placeholder="请输入移动电话" value="${busRecord.TAXMAN_PHONE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'TAXMAN_IDNO');" 
					defaultEmptyText="请选择证件类型" name="TAXMAN_IDTYPE" value="${busRecord.TAXMAN_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TAXMAN_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${busRecord.TAXMAN_IDNO}"/></td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 tmargin20">
		<span>联络员信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LIAISON_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.LIAISON_NAME}"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="LIAISON_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.LIAISON_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LIAISON_MOBILE"  placeholder="请输入移动电话" value="${busRecord.LIAISON_MOBILE}"  maxlength="16"/></td>
				<th> 电子邮箱：</th>
				<td><input type="text" class="syj-input1 validate[],custom[email]"
					name="LIAISON_EMAIL" placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.LIAISON_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LIAISON_IDNO');" 
					defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LIAISON_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.LIAISON_IDNO}"/></td>
			</tr>	
		</table>
	</div>
	
</form>
