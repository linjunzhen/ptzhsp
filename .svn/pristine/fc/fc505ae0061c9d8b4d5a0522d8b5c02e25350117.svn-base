<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="LEGAL_FORM"  >
						
	<input type="hidden" name="YFDBRMC"  value="${busRecord.YFDBRMC }">
	<input type="hidden" name="YFDBRZW"  value="${busRecord.YFDBRZW }">		
	<div class="syj-title1 tmargin20">
		<span>法定代表人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>法定代表人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME}"/></td>
				<th> 职务：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02,03,10,11,12,21"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setLegalValue(this.value);" 
					defaultEmptyText="请选择职务" name="LEGAL_JOB"  value="${busRecord.LEGAL_JOB}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1  validate[required,custom[mobilePhone]]"
					name="LEGAL_MOBILE"  placeholder="请输入移动电话"  value="${busRecord.LEGAL_MOBILE}"  maxlength="16"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="LEGAL_FIXEDLINE"  placeholder="请输入固定电话" value="${busRecord.LEGAL_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th> 电子邮箱：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[],custom[email]"
					name="LEGAL_EMAIL"  placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.LEGAL_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEGAL_IDNO');"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${busRecord.LEGAL_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO}"/></td>
			</tr>

			<tr>
				<th><font class="syj-color2">*</font>国别（地区）：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
								   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
								   name="LEGAL_COMPANYCOUNTRY" value="${busRecord.LEGAL_COMPANYCOUNTRY}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>产生方式：</th>
				<td>
					<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="ssdjcsfs"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择产生方式" name="LEGAL_PRODUCEMODE"  value="${busRecord.LEGAL_PRODUCEMODE}" >
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>住所</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
									   name="LEGAL_ADDRESS" placeholder="请输入住所" maxlength="256" value="${busRecord.LEGAL_ADDRESS}" /></td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 tmargin20">
		<span>经办人信息</span>
	</div>
	<div style="position:relative;">
		<!--<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>指定或委托的有效期限：</th>
				<td colspan="3">
					<input type="text" class="Wdate validate[required]" id="VALIDITY_START_DATE" style="height: 28px;  line-height: 28px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'VALIDITY_END_DATE\')}'})"
					readonly="readonly" name="VALIDITY_START_DATE"  placeholder="请输入开始时间" value="${busRecord.VALIDITY_START_DATE}"  maxlength="16"/>至
					<input type="text" class="Wdate validate[required]" id="VALIDITY_END_DATE" readonly="readonly" style="height: 28px;  line-height: 28px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{M:1})||\'%y-{%M+1}-%d\'}'
					,maxDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{y:1})||\'{%y+1}-%M-%d\'}'})"
					name="VALIDITY_END_DATE"  placeholder="请输入结束时间" value="${busRecord.VALIDITY_END_DATE}"  maxlength="16"/></td>
			</tr>
		</table>-->
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>经办人（被委托人）</br>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_NAME"  placeholder="请输入经办人（被委托人）姓名"  maxlength="8"  value="${busRecord.OPERATOR_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1  validate[required,custom[mobilePhone]]"
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
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
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
	
	<c:if test="${busRecord.ISEMAIL==1||requestParams.ISEMAIL==1}">
	<div class="syj-title1 tmargin20">
		<span>邮寄营业执照</span>
	</div>
	<div style="position:relative;display: none;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th> 是否邮寄营业执照：</th>
				<td>
					<input type="radio" name="ISEMAIL" value="1" <c:if test="${busRecord.ISEMAIL==1}"> checked="checked"</c:if>/>是
					<input type="radio" name="ISEMAIL" value="0" <c:if test="${busRecord.ISEMAIL!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
		</table>
	</div>
	<div style="position:relative;display: none;" id="emailInfo">

		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">

			<tr>
				<th><font class="syj-color2">*</font>收件人姓名：</th>
				<td>
					<input type="text" class="syj-input1 " maxlength="16" placeholder="请输入收件人姓名"
						   name="EMS_RECEIVER" value="${busRecord.EMS_RECEIVER}"/>
				</td>
				<th><font class="syj-color2">*</font>收件人电话：</th>
				<td>
					<input type="text" class="syj-input1 validate[custom[mobilePhone]]" maxlength="16"
						   name="EMS_PHONE" value="${busRecord.EMS_PHONE}"  placeholder="请输入收件人电话"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>寄送地址：</th>
				<td colspan="3" >
					<input type="text" class="syj-input1 w878 " maxlength="100"
						   name="EMS_ADDRESS" value="${busRecord.EMS_ADDRESS}" placeholder="请输入寄送地址"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>寄送城市：</th>
				<td colspan="3" >
					<input type="text" class="syj-input1 w878" maxlength="64" placeholder="请输入寄送城市"
						   name="EMS_CITY" value="${busRecord.EMS_CITY}"/>
				</td>
			</tr>
		</table>
	</div>
	</c:if>
</form>
