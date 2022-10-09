<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >法定代表人基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME }"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="ssdjzw:02,03,10,11,12,21"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择职务" name="LEGAL_JOB"  value="${busRecord.LEGAL_JOB}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
		<td class="tab_width"> 固定电话：</td>
		<td><input type="text" class="tab_text "
			name="LEGAL_FIXEDLINE" value="${busRecord.LEGAL_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 邮箱：</td>
		<td colspan="3"><input type="text" class="tab_text validate[[],custom[email]]"
			name="LEGAL_EMAIL" maxlength="32" value="${busRecord.LEGAL_EMAIL }"/></td>
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
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
						   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
						   name="LEGAL_COMPANYCOUNTRY" value="${busRecord.LEGAL_COMPANYCOUNTRY}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>产生方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjcsfs"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择产生方式" name="LEGAL_PRODUCEMODE"  value="${busRecord.LEGAL_PRODUCEMODE}" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>住所</td>
		<td colspan="3"><input type="text" class="tab_text"
							   name="LEGAL_ADDRESS" placeholder="请输入住所" maxlength="256" value="${busRecord.LEGAL_ADDRESS}" /></td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >经办人信息</th>
	</tr>
	<!--<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定或委托的有效期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="vstart" readonly="readonly"
			name="VALIDITY_START_DATE"  value="${busRecord.VALIDITY_START_DATE }"/>至
			<input type="text" class="laydate-icon validate[required]" id="vend" readonly="readonly"
			name="VALIDITY_END_DATE"  value="${busRecord.VALIDITY_END_DATE }"/></td>
	</tr>-->
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
</table>
<c:if test="${busRecord.ISEMAIL==1}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >邮寄营业执照信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 收件人姓名：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_RECEIVER"  value="${busRecord.EMS_RECEIVER }"/>
		</td>
		<td class="tab_width"> 收件人电话：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_PHONE"  value="${busRecord.EMS_PHONE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送地址：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"  style="width: 700px;"
				   name="EMS_ADDRESS"  value="${busRecord.EMS_ADDRESS }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送城市：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"
				   name="EMS_CITY"  value="${busRecord.EMS_CITY }"/>
		</td>
	</tr>
</table>
</c:if>