<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>
<c:forEach items="${holderChangeList}" var="holder" varStatus="s">
<div class="addBox">
	<input type="hidden" name="status" value="0" />
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th>新股东</th>
			<td><input type="text" class="syj-input1"
					name="new${s.index}SHAREHOLDER_NAME" value="${holder.SHAREHOLDER_NAME}"  />
			   <input type="button" value="展开"  onclick="displayOrShowNewGdxx(this);" class="addbtn"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " style="display:none">	
		<tr>
			<th><font class="syj-color2">*</font>股东姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]"  onchange="setSelectHolderName(this,this.value,'new${s.index}SHAREHOLDER')"
				name="new${s.index}SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32" value="${holder.SHAREHOLDER_NAME}"/></td>
			<th><font class="syj-color2">*</font>股东类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="investorType" 
				dataInterface="extraDictionaryService.findDatasForSelect" onchange="setGdlxValidate(this.value,'new${s.index}LEGAL_PERSON');setGdlxValidate(this.value,'new${s.index}LEGAL_IDNO_PERSON');"
				defaultEmptyText="请选择股东类型" name="new${s.index}SHAREHOLDER_TYPE" value="${holder.SHAREHOLDER_TYPE}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function() {
						setGdlxValidate('${holder.SHAREHOLDER_TYPE}','new${s.index}LEGAL_PERSON');			
						setGdlxValidate('${holder.SHAREHOLDER_TYPE}','new${s.index}LEGAL_IDNO_PERSON');						
					});
				</script>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'new${s.index}LICENCE_NO');" 
				defaultEmptyText="请选择证件类型" name="new${s.index}LICENCE_TYPE" value="${holder.LICENCE_TYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]" 
				name="new${s.index}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32" value="${holder.LICENCE_NO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required] w878"
				name="new${s.index}ID_ADDRESS" placeholder="请输入证件地址，请按身份证上的地址申报" maxlength="64" value="${holder.ID_ADDRESS}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>联系方式：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="new${s.index}CONTACT_WAY"  placeholder="请输入联系方式" maxlength="16" value="${holder.CONTACT_WAY}"/></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="state"
							   dataInterface="extraDictionaryService.findDatasForSelect" defaultEmptyValue="156"
							   name="new${s.index}SHAREHOLDER_COMPANYCOUNTRY" value="${holder.SHAREHOLDER_COMPANYCOUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2 new${s.index}LEGAL_PERSON_CLASS" style="display: none" >*</font>投资企业或其他组织机构法定代表人姓名：</th>
			<td ><input type="text" class="syj-input1"
				name="new${s.index}LEGAL_PERSON"  placeholder="请输入法定代表人姓名" maxlength="32" value="${holder.LEGAL_PERSON}"/></td>
			<th><font class="syj-color2 new${s.index}LEGAL_PERSON_CLASS" style="display: none" >*</font>法定代表人身份证号码：</th>
			<td ><input type="text" class="syj-input1  validate[custom[vidcard]]"
						name="new${s.index}LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码" value="${holder.LEGAL_IDNO_PERSON}" maxlength="32"/></td>
		</tr>
		<tr>
			<th colspan="4"  style="background-color: #FFD39B; text-align: center;">新股东出资方式（至少包含以下一项）</th>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 gqlytable" style="display:none">
		<c:forEach items="${holder.gqlyList}" var="gqly" varStatus="g">
		<tbody>
			<tr style="background: #f2f2f2;">
				<th style="background: #f2f2f2;"><font class="syj-color2">*</font>股权来源：</th>
				<td style="border: none;">
					<select name="stockFrom" class="input-dropdown w280" onchange="setStockFrom(this);setNewInvestment(this);">
					</select>					
				</td>
				<th style="border: none;background: #f2f2f2;"></th>
				<td style="border-left: none;">
					<c:if test="${g.index==0}">
					<a href="javascript:void(0);" onclick="javascript:addGqlyDiv(this);" class="syj-addbtn" id="addGqly">添 加</a>
					</c:if>
					<c:if test="${g.index>0}">
					<a href="javascript:void(0);" onclick="javascript:delGqlyDiv(this);" class="deletebtn" id="delGqly"></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th> 出资方式</th>
				<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
			</tr>
			<tr>
				<th> 现金</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_CASH"  placeholder="请输入现金(万元)"  maxlength="14" value="${gqly.INVESTMENT_CASH}"
					onblur="onlyNumber(this);setNewInvestment(this);isEqualToCash(this.value);" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 实物</th>
				<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14" value="${gqly.INVESTMENT_MATERIAL}"
					onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 技术出资</th>
				<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14" value="${gqly.INVESTMENT_TECHNOLOGY}"
					onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 土地使用权</th>
				<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14" value="${gqly.INVESTMENT_LAND}"
					onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 股权</th>
				<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14" value="${gqly.INVESTMENT_STOCK}"
					onblur="onlyNumber(this);setNewInvestment(this);isEqualToStock(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 其他</th>
				<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
					name="INVESTMENT_OTHER"  placeholder="请输入其他(万元)"  maxlength="14" value="${gqly.INVESTMENT_OTHER}"
					onblur="onlyNumber(this);setNewInvestment(this)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr id="transfer1" style="display:none;">
				<th>总转让认缴出资额（万元）：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" value="${gqly.TRANSFER_CONTRIBUTIONS}"
					name="TRANSFER_CONTRIBUTIONS"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
				<th>占原注册资本比例：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" value="${gqly.OLD_PROPORTION}"
					name="OLD_PROPORTION"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
			</tr>
			<tr id="transfer2" style="display:none;">
				<th><font class="syj-color2">*</font>转让价格：</th>
				<td><input type="text" class="syj-input1 " style="width: 45%;" value="${gqly.TRANSFER_PRICE}"
					name="TRANSFER_PRICE"  placeholder="请输入转让价格（万元）" onblur="setPayWay(this)" />
					<fda:fda-eveselect clazz="selectwidth w280 input-dropdown validate[required]" dataparams="currency"
						datainterface="extraDictionaryService.findDatasForSelect"
						defaultemptytext="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
					</fda:fda-eveselect>
				</td>
				<th><font class="syj-color2" id="fkfs" style="">*</font>付款方式：</th>
				<td>
					<select name="PAYMETHOD" class="input-dropdown " value="${gqly.PAYMETHOD}" style="width:200px;">
						<option value="">请选择</option>
						<option value="现金" <c:if test="${gqly.PAYMETHOD=='现金'}">selected=true</c:if>>现金</option>
						<option value="转账" <c:if test="${gqly.PAYMETHOD=='转账'}">selected=true</c:if>>转账</option>
					</select>
				</td>
			</tr>
		</tbody>
		</c:forEach>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2" style="display:none">
		<tr style="background-color: #f2f2f2;">
			<th style="background-color: #f2f2f2;border-right: none;"></th>
			<td colspan="2" style="width:580px;text-align: center;border: none;">总计</td>
			<td style="border-left: none;"></td>
		</tr>
		<tr>
			<th> 出资方式</th>
			<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
		</tr>
		<tr>
			<th> 现金</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_CASH_TOTAL}"
				name="INVESTMENT_CASH_TOTAL"  placeholder="请输入现金(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 实物</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_MATERIAL_TOTAL}"
				name="INVESTMENT_MATERIAL_TOTAL"  placeholder="请输入实物(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 技术出资</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_TECHNOLOGY_TOTAL}"
				name="INVESTMENT_TECHNOLOGY_TOTAL"  placeholder="请输入技术出资(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 土地使用权</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_LAND_TOTAL}"
				name="INVESTMENT_LAND_TOTAL" placeholder="请输入土地使用权(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 股权</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_STOCK_TOTAL}"
				name="INVESTMENT_STOCK_TOTAL"  placeholder="请输入股权(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 其他</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]" value="${holder.INVESTMENT_OTHER_TOTAL}"
				name="INVESTMENT_OTHER_TOTAL"  placeholder="请输入其他(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
				</eve:eveselect>
			</td>
		</tr>		

		<tr>
			<th colspan="4"  style="background-color: #c9deef; text-align: left;">出资额合计</th>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" value="${holder.CONTRIBUTIONS}"
				name="CONTRIBUTIONS"  placeholder="请填写股东出资方式" readonly="readonly" /></td>
			<th><font class="syj-color2">*</font>占注册资本比例：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" value="${holder.PROPORTION}"
				name="PROPORTION"  placeholder="请填写股东出资方式" readonly="readonly" /></td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>实缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 validate[required]"  onblur="onlyNumber(this),setPayTime(this);isEqualToSjcze(this.value)" onkeyup="onlyNumber(this);" 
					   name="PAIDCAPITAL"  placeholder="请输入实缴出资额" maxlength="14" value="${holder.PAIDCAPITAL}"/></td>
			
			<th ><font class="syj-color2" id="jfrq" style="display:none">*</font>缴付日期：</th>
			<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-{%d-1}'})"
					  name="PAYMENT_TIME"  placeholder="请选择缴款日期" value="${holder.PAYMENT_TIME}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>
	

