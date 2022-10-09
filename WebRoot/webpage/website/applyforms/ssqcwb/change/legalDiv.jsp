<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>法定代表人姓名：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc "   readonly="readonly"
				name="LEGAL_NAME_CHANGE"  placeholder="请输入法定代表人姓名，须填写自然人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME_CHANGE}"/></td>
			<th> 职务：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280" dataParams="ssdjzw:${ssdjzw}"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择职务" name="LEGAL_JOB_CHANGE" onchange="setLegalValue(this.value);setlegalProducemode(this.value);"   value="${busRecord.LEGAL_JOB_CHANGE}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1  validate[custom[mobilePhone]]"
				name="LEGAL_MOBILE_CHANGE"  placeholder="请输入移动电话"  value="${busRecord.LEGAL_MOBILE_CHANGE}"  maxlength="16"/></td>
			<th> 固定电话：</th>
			<td><input type="text" class="syj-input1 validate[]"
				name="LEGAL_FIXEDLINE_CHANGE"  placeholder="请输入固定电话" value="${busRecord.LEGAL_FIXEDLINE_CHANGE}"  maxlength="16"/></td>
		</tr>
		<tr>
			<th> 电子邮箱：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[email]"
				name="LEGAL_EMAIL_CHANGE"  placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.LEGAL_EMAIL_CHANGE}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEGAL_IDNO_CHANGE');"
				defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE_CHANGE" id="LEGAL_IDTYPE_CHANGE"   value="${busRecord.LEGAL_IDTYPE_CHANGE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc "  readonly="readonly"
				name="LEGAL_IDNO_CHANGE"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO_CHANGE}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280" dataParams="state"
							   dataInterface="extraDictionaryService.findDatasForSelect" defaultEmptyValue="156"
							   name="LEGAL_COUNTRY_CHANGE" value="${busRecord.LEGAL_COUNTRY_CHANGE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280" dataParams="ssdjcsfs" 
							   dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="02"
							   defaultEmptyText="请选择产生方式" name="LEGAL_PRODUCEMODE_CHANGE"  value="${busRecord.LEGAL_PRODUCEMODE_CHANGE}" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>住所</th>
			<td colspan="3"><input type="text" class="syj-input1 "
								   name="LEGAL_ADDRESS_CHANGE" placeholder="请输入住所" maxlength="256" value="${busRecord.LEGAL_ADDRESS_CHANGE}" /></td>
		</tr>

	</table>
</div>