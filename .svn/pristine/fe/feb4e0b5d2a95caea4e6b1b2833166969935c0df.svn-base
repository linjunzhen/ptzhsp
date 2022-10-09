<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >	
	<div class="syj-title1 ">
		<span>企业资金信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
			<tr>
				<th><font class="syj-color2">*</font>投资总额(万元)：</th>
				<td  title="系统会根据用户填写的认缴出资额信息自动计算">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]" 
					name="INVESTMENT"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" onchange="alertInvesment(this.value)"
					readonly="readonly""  value="${busRecord.INVESTMENT}"/></td>
				<th><font class="syj-color2">*</font>币种：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY" value="人民币" >
					</eve:eveselect>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<c:if test="${requestParams.COMPANY_TYPECODE!='zrrdz'&&busRecord.COMPANY_TYPECODE!='zrrdz'&&requestParams.COMPANY_TYPECODE!='frdz'&&busRecord.COMPANY_TYPECODE!='frdz'}">
		<a href="javascript:void(0);" onclick="javascript:addGdxxDiv();" class="syj-addbtn" id="addGdxx">
			添 加
		</a>
		</c:if>
		<span>股东信息</span>
	</div>
	<div id="gdxxDiv">	
		<c:if test="${empty holderList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			
				<tr>
					<th><font class="syj-color2">*</font>股东姓名/名称：</th>
					<td><input type="text" class="syj-input1 validate[required,custom[checkShareHolderName]]"
						name="SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>股东类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE"  onchange="setGdlxValidate(this.value,'LEGAL_PERSON');
						setGdlxValidate(this.value,'LEGAL_IDNO_PERSON');setGdType(this.value,'SHAREHOLDER_TYPE','LICENCE_TYPE','LICENCE_NO');">
						</eve:eveselect>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>证照类型：</th>
					<td>
						<c:if test="${requestParams.COMPANY_TYPECODE=='zrrkg'||busRecord.COMPANY_TYPECODE=='zrrkg'||requestParams.COMPANY_TYPECODE=='zrrdz'||busRecord.COMPANY_TYPECODE=='zrrdz'}">
						<eve:eveselect clazz="input-dropdown w280 validate[required,custom[IdTypeOlnySF]]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LICENCE_NO');"
						defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
						</eve:eveselect>
						</c:if>

						<c:if test="${requestParams.COMPANY_TYPECODE=='frdz'||busRecord.COMPANY_TYPECODE=='frdz'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required,custom[IdTypeNotSF]]" dataParams="DocumentType"
										   dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LICENCE_NO');"
										   defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
							</eve:eveselect>
						</c:if>
						<c:if test="${requestParams.COMPANY_TYPECODE=='qtyxzrgs'||busRecord.COMPANY_TYPECODE=='qtyxzrgs'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
										   dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LICENCE_NO');"
										   defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
							</eve:eveselect>
						</c:if>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="LICENCE_NO"  placeholder="请输入证件号码" maxlength="32"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>身份证件地址：</th>
					<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
						name="ID_ADDRESS" placeholder="请输入身份证件地址，请按身份证上的地址申报"  maxlength="64"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>联系方式：</th>
					<td ><input type="text" class="syj-input1 validate[required,custom[mobilePhone]]"
						name="CONTACT_WAY"  placeholder="请输入联系方式"  maxlength="16"/></td>
					<th><font class="syj-color2">*</font>国别（地区）：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb" defaultEmptyValue="中国"
									   dataInterface="dictionaryService.findDatasForCountrySelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> <font class="syj-color2 LEGAL_PERSON_CLASS" style="display: none" >*</font>投资企业或其他组织机构法定代表人姓名：</th>
					<td ><input type="text" class="syj-input1"
						name="LEGAL_PERSON"  placeholder="请输入法定代表人姓名"  maxlength="32"/></td>
					<th><font class="syj-color2 LEGAL_PERSON_CLASS" style="display: none">*</font> 法定代表人身份证号码：</th>
					<td ><input type="text" class="syj-input1  validate[custom[vidcard],custom[isEighteen]]"
								name="LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码"  maxlength="32"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B; text-align:center">股东出资方式（至少包含以下一项）</th>
				</tr>
				<tr>
					<th> 出资方式</th>
					<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
				</tr>
				<tr>
					<th> 现金</th>
					<td colspan="3">
						<input type="text" class="syj-input1 validate[required,custom[numMustBiggerZero]]"
						name="INVESTMENT_CASH"  placeholder="请输入现金(万元)"  maxlength="14"  onchange="alertInvesment(this.value)"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 实物</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
						name="INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14" disabled="disabled"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 技术出资</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
						name="INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14"  disabled="disabled"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 土地使用权</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
						name="INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14"  disabled="disabled"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 股权</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
						name="INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14"  disabled="disabled"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th> 其他</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
						name="INVESTMENT_OTHER"  placeholder="请输入其他(万元)"  maxlength="14"  disabled="disabled"
						onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;text-align: center;">出资额合计</th>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
					<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
						name="CONTRIBUTIONS"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" readonly="readonly" /></td>
					<th><font class="syj-color2">*</font>占注册资本比例：</th>
					<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
						name="PROPORTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" readonly="readonly" /></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
					<td><input type="text" class="syj-input1 validate[required,custom[JustNumber]]"  onblur="onlyNumber(this)" onkeyup="onlyNumber(this);" onchange="alertInvesment(this.value)"
							   name="PAIDCAPITAL"  placeholder="请输入实缴出资额" maxlength="14"/></td>
				</tr>
			</table>
			
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
		</c:if>
		<jsp:include page="./initGdxxDiv_MP.jsp"></jsp:include>
	</div>
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">		
		<tr>
			<th><font class="syj-color2">*</font>出资全部缴付</br>到位期限：</th>
			<td ><input type="text" class="Wdate validate[required,custom[yearPaymentperiod]]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
			onchange="isGtCurDate(this.value)"	id="period" readonly="readonly" name="PAYMENT_PERIOD"  placeholder="请选择期限" /></td>
		</tr>

	</table>
	
</form>
