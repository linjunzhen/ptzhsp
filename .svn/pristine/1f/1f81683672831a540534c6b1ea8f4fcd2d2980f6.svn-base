<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<!-- 原法定代表人信息 -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="fddbrxx">
	<tr>
		<th colspan="4" >法定代表人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME}"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,20,30"
			dataInterface="dictionaryService.findDatasForSelectIn"
			defaultEmptyText="请选择职务" name="LEGAL_JOB" value="${busRecord.LEGAL_JOB}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
			name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]" onblur="checkDifferent(this.name);"
			name="LEGAL_IDNO" maxlength="32" value="${busRecord.LEGAL_IDNO }"/></td>
	</tr>
</table>

<!-- 新法定代表人信息 -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="newfddbrxx" style="display:none">
	<tr>
		<th colspan="4" >新法定代表人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LEGAL_NAME_CHANGE" maxlength="8" value="${busRecord.LEGAL_NAME_CHANGE}"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,20,30"
			dataInterface="dictionaryService.findDatasForSelectIn"
			defaultEmptyText="请选择职务" name="LEGAL_JOB_CHANGE" value="${busRecord.LEGAL_JOB_CHANGE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LEGAL_MOBILE_CHANGE" value="${busRecord.LEGAL_MOBILE_CHANGE }"/></td>
		<td class="tab_width"> 单位电话：</td>
		<td><input type="text" class="tab_text "
			name="LEGAL_FIXEDLINE_CHANGE" value="${busRecord.LEGAL_FIXEDLINE_CHANGE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 邮箱：</td>
		<td colspan="3"><input type="text" class="tab_text validate[[],custom[email]]"
			name="LEGAL_EMAIL_CHANGE" maxlength="32" value="${busRecord.LEGAL_EMAIL_CHANGE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE_CHANGE" value="${busRecord.LEGAL_IDTYPE_CHANGE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]" onblur="checkDifferent(this.name);"
			name="LEGAL_IDNO_CHANGE" maxlength="32" value="${busRecord.LEGAL_IDNO_CHANGE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="state"
						   dataInterface="extraDictionaryService.findDatasForSelect" defaultEmptyValue="156"
						   name="LEGAL_COUNTRY_CHANGE" value="${busRecord.LEGAL_COUNTRY_CHANGE}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择产生方式" name="LEGAL_PRODUCEMODE_CHANGE"  value="${busRecord.LEGAL_PRODUCEMODE_CHANGE}" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>住所</td>
		<td colspan="3"><input type="text" class="tab_text"
							   name="LEGAL_ADDRESS_CHANGE" placeholder="请输入住所" maxlength="256" value="${busRecord.LEGAL_ADDRESS_CHANGE}" /></td>
	</tr>
</table>