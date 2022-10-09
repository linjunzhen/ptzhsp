<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
	
		<tr>
			<th><font class="syj-color2">*</font>合伙人姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setPartnershipName();isEqualShahareholderName('${currentTime}');"
				name="${currentTime}SHAREHOLDER_NAME" placeholder="请输入合伙人姓名/名称" maxlength="32"/></td>
			<th><font class="syj-color2">*</font>合伙人类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setGdlxValidate(this.value,'${currentTime}LEGAL_PERSON');showLicenceAppoint('${currentTime}');
				setGdType(this.value,'${currentTime}SHAREHOLDER_TYPE','${currentTime}LICENCE_TYPE','${currentTime}LICENCE_NO','${currentTime}SHAREHOLDER_NAME');
setGdlxValidate(this.value,'${currentTime}LEGAL_IDNO_PERSON');setGdlxValidate(this.value,'${currentTime}LEGAL_MOBILE_PERSON');"
				defaultEmptyText="请选择合伙人类型" name="${currentTime}SHAREHOLDER_TYPE">
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>证照类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}LICENCE_NO');" 
				defaultEmptyText="请选择证照类型" name="${currentTime}LICENCE_TYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>住所：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}ID_ADDRESS" placeholder="请输入住所" maxlength="64"/></td>
			<th><font class="syj-color2">*</font>承担责任方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="cdzrfs"
				dataInterface="dictionaryService.findDatasForSelect"   onchange="setsfzxswhhr(this.value,'${currentTime}IS_PARTNERSHIP');" 
				defaultEmptyText="请选择承担责任方式" name="${currentTime}DUTY_MODE">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${currentTime}SHAREHOLDER_COMPANYCOUNTRY">
				</eve:eveselect>
			</td>
			<th>评估方式：</th>
			<td>
				<input type="text" class="syj-input1"
					   name="${currentTime}EVALUATE_WAY"  placeholder="请输入评估方式"  maxlength="16"/>
					   </td>
		</tr>
		<tr>
			<th>固定电话：</th>
			<td >
				<input type="text" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]"
					   name="${currentTime}SHAREHOLDER_FIXPHONE"  placeholder="请输入固定电话"  maxlength="16"/>
			</td>
			<th>电子邮箱：</th>
			<td>
				<input type="text" class="syj-input1 validate[[],custom[email]]"
					   name="${currentTime}SHAREHOLDER_EMAIL"  placeholder="请输入电子邮箱"  maxlength="32"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[mobilePhone]"
				name="${currentTime}CONTACT_WAY"  placeholder="请输入移动电话" maxlength="16"/></td>
			<th><font class="syj-color2">*</font>执行事务合伙人：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="YesOrNo"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setPartnershipName();showLicenceAppoint('${currentTime}');" 
				defaultEmptyText="请选择是否执行事务合伙人" name="${currentTime}IS_PARTNERSHIP">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2 ${currentTime}LEGAL_PERSON_CLASS" style="display: none" >*</font> 合伙企业或其他组织机构法定代表人姓名：</th>
			<td ><input type="text" class="syj-input1"
						name="${currentTime}LEGAL_PERSON"  placeholder="请输入法定代表人姓名" maxlength="32"/></td>
			<th><font class="syj-color2 ${currentTime}LEGAL_PERSON_CLASS" style="display: none" >*</font> 法定代表人身份证号码：</th>
			<td ><input type="text" class="syj-input1 validate[custom[vidcard],custom[isEighteen]]"
						name="${currentTime}LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码" value="${holder.LEGAL_IDNO_PERSON}" maxlength="32"/></td>
		</tr>

		<tr>
			<th><font class="syj-color2 ${currentTime}LEGAL_PERSON_CLASS" style="display: none">*</font> 法定代表人移动号码：</th>
			<td colspan="3"><input type="text" class="syj-input1  validate[],custom[mobilePhone]" value="${holder.LEGAL_MOBILE_PERSON}"
								   name="${currentTime}LEGAL_MOBILE_PERSON"  placeholder="请输入法定代表人移动号码"  maxlength="32"/></td>

		</tr>
		<tr class="${currentTime}licenceAppointTr" style="display:none;">
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">委派人信息</th>
		</tr>
		<tr class="${currentTime}licenceAppointTr" style="display:none;">
			<th><font class="syj-color2">*</font>姓名：</th>
			<td colspan="3"><input type="text" class="syj-input1"  onblur="isEqualShahareholderName('${currentTime}');"
				name="${currentTime}LICENCE_APPOINT_NAME"  maxlength="32"  placeholder="请输入姓名"/></td>		
		
		</tr>
		<tr class="${currentTime}licenceAppointTr" style="display:none;">
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'jwtzzxxDiv','LICENCE_APPOINT_IDNO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}LICENCE_APPOINT_IDTYPE" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1"
				name="${currentTime}LICENCE_APPOINT_IDNO" maxlength="32"  placeholder="请输入证件号码"/></td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">合伙人出资方式（至少包含以下一项）</th>
		</tr>
		<tr>
			<th> 出资方式</th>
			<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
		</tr>
		<tr>
			<th> 现金</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required,custom[numMustBiggerZero]],custom[JustNumber]"
				name="${currentTime}INVESTMENT_CASH"  placeholder="请输入现金(万元)" maxlength="14"  onchange="alertInvesment(this.value)"
				onblur="onlyNumber(this);setInvestment();setEvaluateWayNeed('${currentTime}EVALUATE_WAY','${currentTime}INVESTMENT_CASH')" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 实物</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]" disabled="disabled"
				name="${currentTime}INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14" 
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 技术出资</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]" disabled="disabled"
				name="${currentTime}INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 土地使用权</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]" disabled="disabled"
				name="${currentTime}INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14" 
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 股权</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]" disabled="disabled"
				name="${currentTime}INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 其他</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]" disabled="disabled"
				name="${currentTime}INVESTMENT_OTHER"  placeholder="请输入其他(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">出资额合计</th>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${currentTime}CONTRIBUTIONS"  placeholder="请填写股东出资方式" readonly="readonly" /></td>
			<th><font class="syj-color2">*</font>出资比例：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${currentTime}PROPORTION"  placeholder="请填写股东出资方式" readonly="readonly" /></td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B;text-align: center;">实缴额详情</th>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="16"  onchange="alertInvesment(this.value)"
				name="${currentTime}PAIDIN_QUOTA"  placeholder="请输入实缴出资额（万元）"   onblur="setRealCapital()"/></td>
			<th>缴款日期：</th>
			<td><input type="text" class="syj-input1 Wdate"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})" 
				 readonly="readonly" name="${currentTime}PAIDIN_DATE"  placeholder="请选择缴款日期"/></td>
		</tr>
	</table>
<c:if test="${deleKey!='0'}">
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
</c:if>
</div>
	

