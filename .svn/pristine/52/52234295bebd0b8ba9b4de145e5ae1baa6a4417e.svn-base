<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >
	
	<div class="syj-title1 ">
		<span>联络员信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LIAISON_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.LIAISON_NAME}"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]"
					name="LIAISON_FIXEDLINE" placeholder="请输入固定电话" maxlength="16" value="${busRecord.LIAISON_FIXEDLINE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]"
					name="LIAISON_MOBILE"  placeholder="请输入移动电话" maxlength="16" value="${busRecord.LIAISON_MOBILE}"/></td>
				<th> 电子邮箱：</th>
				<td><input type="text" class="syj-input1 validate[],custom[email]"
					name="LIAISON_EMAIL" placeholder="请输入电子邮箱" maxlength="32" value="${busRecord.LIAISON_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
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
	
	<div class="syj-title1 ">
		<span>财务负责人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.FINANCE_NAME}"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="FINANCE_FIXEDLINE"  placeholder="请输入固定电话" maxlength="16" value="${busRecord.FINANCE_FIXEDLINE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_MOBILE"  placeholder="请输入移动电话" maxlength="16" value="${busRecord.FINANCE_MOBILE}"/></td>
				<th> 电子邮箱：</th>
				<td><input type="text" class="syj-input1  validate[],custom[email]"
					name="FINANCE_EMAIL"  placeholder="请输入电子邮箱" maxlength="32" value="${busRecord.FINANCE_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'FINANCE_IDNO');" 
					defaultEmptyText="请选择证件类型" name="FINANCE_IDTYPE" value="${busRecord.FINANCE_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="FINANCE_IDNO" placeholder="请输入证件号码"  maxlength="32"  value="${busRecord.FINANCE_IDNO}"/></td>
			</tr>
		</table>
	</div>
	
	
	<div class="syj-title1 ">
		<span>办税人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>办税人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="TAXMAN_NAME"  placeholder="请输入办税人姓名"  maxlength="8" value="${busRecord.TAXMAN_NAME}"/></td>
				<th > 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="TAXMAN_MOBILE"  maxlength="16" placeholder="请输入固定电话" value="${busRecord.TAXMAN_MOBILE}"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TAXMAN_FIXEDLINE"  maxlength="16" placeholder="请输入移动电话" value="${busRecord.TAXMAN_FIXEDLINE}"/></td>
				<th > 电子邮箱：</th>
				<td><input type="text" class="syj-input1 validate[[],custom[email]]"
					name="TAXMAN_EMAIL" maxlength="32" placeholder="请输入电子邮箱" value="${busRecord.TAXMAN_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
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
	
</form>
