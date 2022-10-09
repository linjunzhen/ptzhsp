<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >	
	<div class="syj-title1 tmargin20">
		<span>合伙企业资金信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
			<tr>
				<th><font class="syj-color2">*</font>币种：</th>
				<td colspan="3">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="1" class="syj-table2 tmargin20" id="foreignCapital1">
			<tr id="folding" style="display: none;">
				<th><font class="syj-color2">*</font>折合人民币(万元)：</th>
				<td colspan="3"><input type="text" class="syj-input1"
					name="FOLDINGRMB" maxlength="16"  value="${busRecord.FOLDINGRMB}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
				<td title="系统会根据用户填写的认缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc validate[required,custom[JustNumber]]" readonly="readonly"
					name="REGISTERED_CAPITAL" style="width: 278px;" placeholder="系统会根据用户填写的认缴出资额信息自动计算"
					value="${busRecord.REGISTERED_CAPITAL}"/></td>
				<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
				<td title="系统会根据用户填写的实缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc validate[required,custom[JustNumber]]" readonly="readonly"
					name="REAL_CAPITAL" style="width: 278px;" placeholder="系统会根据用户填写的实缴出资额信息自动计算"
					value="${busRecord.REAL_CAPITAL}"/></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="1" class="syj-table2 tmargin20" id="foreignCapital2">	
			<tr id="chineseCapital" style="display:none;">
				<th><font class="syj-color2">*</font>中方资本(万元)：</th>
				<td  title="系统会根据用户填写的认缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc validate[],custom[JustNumber]" readonly="readonly"
					name="CHINA_CAPITAL" placeholder="系统会根据用户填写的认缴出资额信息自动计算"   value="${busRecord.CHINA_CAPITAL}"/></td>
				<th><font class="syj-color2">*</font>中方股比：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" readonly="readonly"
					name="CHINA_RATIO"   placeholder="系统会根据用户填写的认缴出资额信息自动计算" value="${busRecord.CHINA_RATIO}"/></td>
			</tr>
			<tr id="foreignCapital">
				<th><font class="syj-color2">*</font>外方资本(万元)：</th>
				<td  title="系统会根据用户填写的认缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc validate[required,custom[JustNumber]]" readonly="readonly"
					name="FOREIGN_CAPITAL"  placeholder="系统会根据用户填写的认缴出资额信息自动计算"  value="${busRecord.FOREIGN_CAPITAL}"/></td>
				<th><font class="syj-color2">*</font>外方股比：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="FOREIGN_RATIO"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" value="${busRecord.FOREIGN_RATIO}"/></td>
			</tr>
		</table>
	</div>
	
	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addJwtzzxxDiv();" class="syj-addbtn" id="addJwtzz" >添 加</a>
		<span>外方合伙人信息</span>
	</div>
	<div id="jwtzzxxDiv">
		<c:if test="${empty foreigninvestorList}">
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
					<tr>
						<th><font class="syj-color2">*</font>境外合伙人名称：</th>
						<td colspan="3">
							<input type="hidden" name="INVESTOR_TYPE" value="0"/>
							<input type="text" class="syj-input1 validate[required]" style="width:600px;"
								name="INVESTOR_NAME"  maxlength="32" placeholder="请输入境外合伙人名称" onblur="setAuthorizer();"/>
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>境外合伙人移动电话：</th>
						<td><input type="text" class="syj-input1 validate[required]"
							name="INVESTOR_PHONE"  maxlength="16"  placeholder="请输入移动电话"/></td>							
					
						<th><font class="syj-color2">*</font>住所：</th>
						<td ><input type="text" class="syj-input1 validate[required]"
							name="INVESTOR_ADDR" maxlength="128" placeholder="请输入住所"/></td>
					
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'jwtzzxxDiv','INVESTOR_IDNO');"
							defaultEmptyText="请选择证件类型" name="INVESTOR_IDTYPE" >
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1 validate[required]"
							name="INVESTOR_IDNO" maxlength="32"  placeholder="请输入证件号码"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>国别（地区）：</th>
						<td>
							<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb:中国"
							dataInterface="dictionaryService.findDatasForCountrySelect"
							defaultEmptyText="请选择国别（地区）" name="INVESTOR_COUNTRY">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>合伙人类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="wzhhrlx"
							dataInterface="dictionaryService.findDatasForSelect" onchange="showJwInvestorAppoint('1_');" 
							defaultEmptyText="请选择合伙人类型" name="1_INVESTOR_JOINT_TYPE">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>承担责任方式：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="cdzrfs"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="请选择承担责任方式" name="DUTY_MODE" onchange="setsfzxswhhr(this.value,'jwtzzxxDiv','1_');">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>执行事务合伙人：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="YesOrNo"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setPartnershipName();showJwInvestorAppoint('1_');" 
							defaultEmptyText="请选择是否执行事务合伙人" name="1_IS_PARTNERSHIP">
							</eve:eveselect>
						</td>
					</tr>
					<tr class="1_investorAppointTr" style="display:none;">
						<th colspan="4" style="background-color: #FFD39B;">委派人信息</th>
					</tr>
					<tr class="1_investorAppointTr" style="display:none;">
						<th><font class="syj-color2">*</font>姓名：</th>
						<td colspan="3"><input type="text" class="syj-input1"
							name="INVESTOR_APPOINT_NAME"  maxlength="32"  placeholder="请输入姓名"/></td>		
					
					</tr>
					<tr class="1_investorAppointTr" style="display:none;">
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'jwtzzxxDiv','INVESTOR_APPOINT_IDNO');"
							defaultEmptyText="请选择证件类型" name="INVESTOR_APPOINT_IDTYPE" >
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1"
							name="INVESTOR_APPOINT_IDNO" maxlength="32"  placeholder="请输入证件号码"/></td>
					</tr>
					<tr>
						<th colspan="4" style="background-color: #FFD39B;">境外合伙人出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
					</tr>
					<tr>
						<th> 出资方式</th>
						<th colspan="3" style="text-align: center;">折注册资本币种(万元)</th>
					</tr>
					<tr>
						<th> 现金_境外现汇</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_ABROAD_EXCHANGE" maxlength="14"  placeholder="请输入现金_境外现汇(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 现金_境外人民币</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_ABROAD_RMB" maxlength="14"  placeholder="请输入现金_境外人民币(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 现金_境内人民币</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_DOMESTIC_RMB" maxlength="14" placeholder="请输入现金_境内人民币(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 实物</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_MATERIAL" maxlength="14"  placeholder="请输入实物(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 技术出资</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_TECHNOLOGY" maxlength="14"  placeholder="请输入技术出资(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 土地使用权</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_LAND" maxlength="14"  placeholder="请输入土地使用权(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 股权</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_STOCK" maxlength="14"  placeholder="请输入股权(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 其他</th>
						<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
							name="INVESTMENT_OTHER" maxlength="14"  placeholder="请输入其他(万元)" 
							onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"/>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th colspan="4" style="background-color: #FFD39B;">出资额合计</th>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
							name="CONTRIBUTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" />
						</td>
						<th><font class="syj-color2">*</font>出资比例：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
							name="PROPORTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" /></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>折人民币：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]" readonly="readonly"
							name="FOLDING_RMB"  placeholder="系统会根据用户填写的认缴出资额信息自动计算"/></td>
						<th><font class="syj-color2">*</font>出资时间：</th>
						<td><input type="text" class="Wdate validate[required]"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
							 readonly="readonly" name="FOLDING_DATE"  placeholder="请选择出资时间"/></td>
					</tr>			
					<tr>
						<th colspan="4" style="background-color: #FFD39B;">实缴额详情</th>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
						<td><input type="text" class="syj-input1 validate[required],custom[JustNumber],min[0]" maxlength="16"
							name="PAIDIN_QUOTA"  placeholder="请输入实缴出资额（万元）"  onblur="setRealCapital()"/></td>
						<th><font class="syj-color2">*</font>缴款日期：</th>
						<td><input type="text" class="Wdate validate[required]"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})" 
							 readonly="readonly" name="PAIDIN_DATE"  placeholder="请选择缴款日期"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>折人民币（万元）：</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[[required],custom[JustNumber]]"
							name="PAIDIN_RMB"  placeholder="请输入折人民币（万元）" style="width: 278px;"/></td>
					</tr>
				</table>
			</div>
		</c:if>
		<jsp:include page="./initJwtzzxxDiv.jsp"></jsp:include>
	</div>

	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addZftzzxxDiv();" class="syj-addbtn" id="addZftzz">添 加</a><span>中方合伙人信息</span>
	</div>
	<div id="zftzzxxDiv">	
		
		<jsp:include page="./initZftzzxxDiv.jsp"></jsp:include>
	</div>
</form>
