<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>
<c:forEach items="${holderList}" var="holder" varStatus="s">
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
	
		<tr>
			<th><font class="syj-color2">*</font>合伙人姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="setPartnershipName();isEqualShahareholderName('${s.index}');"
				name="${s.index}SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32" value="${holder.SHAREHOLDER_NAME}"/></td>
			<th><font class="syj-color2">*</font>合伙人类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setGdlxValidate(this.value,'${s.index}LEGAL_PERSON');showLicenceAppoint('${s.index}');"
				defaultEmptyText="请选择股东类型" name="${s.index}SHAREHOLDER_TYPE" value="${holder.SHAREHOLDER_TYPE}">
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>证照类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}LICENCE_NO');" 
				defaultEmptyText="请选择证照类型" name="${s.index}LICENCE_TYPE" value="${holder.LICENCE_TYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${s.index}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32" value="${holder.LICENCE_NO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>住所：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${s.index}ID_ADDRESS" placeholder="请输入住所" maxlength="64" value="${holder.ID_ADDRESS}"/></td>
			<th><font class="syj-color2">*</font>承担责任方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="cdzrfs"
				dataInterface="dictionaryService.findDatasForSelect"   onchange="setsfzxswhhr(this.value,'${s.index}IS_PARTNERSHIP');" 
				defaultEmptyText="请选择承担责任方式" name="${s.index}DUTY_MODE" value="${holder.DUTY_MODE}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th>固定电话：</th>
			<td >
				<input type="text" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" value="${holder.SHAREHOLDER_FIXPHONE}"
					   name="${s.index}SHAREHOLDER_FIXPHONE"  placeholder="请输入固定电话"  maxlength="16"/>
			</td>
			<th>电子邮箱：</th>
			<td>
				<input type="text" class="syj-input1 validate[[],custom[email]]"  value="${holder.SHAREHOLDER_EMAIL}"
					   name="${s.index}SHAREHOLDER_EMAIL"  placeholder="请输入电子邮箱"  maxlength="16"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
							   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
							   name="${s.index}SHAREHOLDER_COMPANYCOUNTRY" value="${holder.SHAREHOLDER_COMPANYCOUNTRY}" >
				</eve:eveselect>
			</td>
			<th>评估方式：</th>
			<td><input type="text" class="syj-input1"
					   name="${s.index}EVALUATE_WAY"  placeholder="请输入评估方式"  maxlength="16" value="${holder.EVALUATE_WAY}"/>
					   </td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>移动电话：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[mobilePhone]"
				name="${s.index}CONTACT_WAY"  placeholder="请输入移动电话" maxlength="16" value="${holder.CONTACT_WAY}"/></td>
			<th><font class="syj-color2">*</font>执行事务合伙人：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="YesOrNo"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setPartnershipName();showLicenceAppoint('${s.index}');" 
				defaultEmptyText="请选择是否执行事务合伙人" name="${s.index}IS_PARTNERSHIP" value="${holder.IS_PARTNERSHIP}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 合伙企业或其他组织机构法定代表人姓名：</th>
			<td colspan="3"><input type="text" 
				<c:if test="${holder.SHAREHOLDER_TYPE=='zrr'}"> class="syj-input1" disabled="disabled" </c:if>
				<c:if test="${holder.SHAREHOLDER_TYPE!='zrr'}"> class="syj-input1 validate[required]"  </c:if>
				name="${s.index}LEGAL_PERSON"  placeholder="请输入法定代表人姓名" maxlength="32" value="${holder.LEGAL_PERSON}"/></td>
		</tr>
		<tr class="${s.index}licenceAppointTr" style="display:none;">
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">委派人信息</th>
		</tr>
		<tr class="${s.index}licenceAppointTr" style="display:none;">
			<th><font class="syj-color2">*</font>姓名：</th>
			<td colspan="3"><input type="text" class="syj-input1" value="${holder.LICENCE_APPOINT_NAME}" onblur="isEqualShahareholderName('${s.index}');" 
				name="${s.index}LICENCE_APPOINT_NAME"  maxlength="32"  placeholder="请输入姓名"/></td>		
		
		</tr>
		<tr class="${s.index}licenceAppointTr" style="display:none;">
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280" dataParams="DocumentType" value="${holder.LICENCE_APPOINT_IDTYPE}"
				dataInterface="dictionaryService.findDatasForSelect"   onchange="setZjValidate(this.value,'${s.index}LICENCE_APPOINT_IDNO');"
				defaultEmptyText="请选择证件类型" name="${s.index}LICENCE_APPOINT_IDTYPE" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1" value="${holder.LICENCE_APPOINT_IDNO}"
				name="${s.index}LICENCE_APPOINT_IDNO" maxlength="32"  placeholder="请输入证件号码"/></td>
		</tr>
		<script type="text/javascript">
			$(function(){
				showLicenceAppoint('${s.index}');
				setRealCapital();
			});
		</script>
		<tr>
			<th colspan="4" style="background-color: #FFD39B;text-align: center;">合伙人出资方式（至少包含以下一项）</th>
		</tr>
		<tr>
			<th> 出资方式</th>
			<td colspan="3" style="text-align: center;">折合人民币（<font color="red">万元</font>）</td>
		</tr>
		<tr>
			<th> 现金（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_CASH"  placeholder="请输入现金(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment();setEvaluateWayNeed('${s.index}EVALUATE_WAY','${s.index}INVESTMENT_CASH');isEqualToCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"  value="${holder.INVESTMENT_CASH}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 实物（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="16" 
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"  value="${holder.INVESTMENT_MATERIAL}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 技术出资（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_TECHNOLOGY}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 土地使用权（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="16" 
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_LAND}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 股权（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment();isEqualToStock(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_STOCK}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 其他（<font color="red">万元</font>）</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${s.index}INVESTMENT_OTHER"  placeholder="请输入其他(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_OTHER}"/>
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
			<th><font class="syj-color2">*</font>认缴出资额（<font color="red">万元</font>）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${s.index}CONTRIBUTIONS"  placeholder="请填写股东出资方式" readonly="readonly"  value="${holder.CONTRIBUTIONS}"/></td>
			<th><font class="syj-color2">*</font>出资比例：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${s.index}PROPORTION"  placeholder="请填写股东出资方式" readonly="readonly"  value="${holder.PROPORTION}"/></td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">实缴额详情</th>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>实缴出资额（<font color="red">万元</font>）：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="16"  onblur="setRealCapital();isEqualToSjcze(this.value)"
				name="${s.index}PAIDIN_QUOTA"  placeholder="请输入实缴出资额（万元）"   value="${holder.PAIDIN_QUOTA}"/></td>
			<th>缴款日期：</th>
			<td><input type="text" class="syj-input1 Wdate"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})" 
				 readonly="readonly" name="${s.index}PAIDIN_DATE"  placeholder="请选择缴款日期"  value="${holder.PAIDIN_DATE}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>
	

