<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${domesticinvestorList}" var="domestic" varStatus="s">
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>中方合伙人名称：</th>
				<td colspan="3">
					<input type="hidden" name="${domestic.INVESTOR_ID}INVESTOR_TYPE" value="1"/>
					<input type="text" class="syj-input1 validate[required]" style="width:600px;"
						name="${domestic.INVESTOR_ID}INVESTOR_NAME"  maxlength="32" placeholder="请输入中方合伙人名称"  onblur="setAuthorizer()"
						 value="${domestic.INVESTOR_NAME}"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>中方合伙人移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${domestic.INVESTOR_ID}INVESTOR_PHONE"  maxlength="16" placeholder="请输入移动电话"
						 value="${domestic.INVESTOR_PHONE}"/></td>
				<th><font class="syj-color2">*</font>住所：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${domestic.INVESTOR_ID}INVESTOR_ADDR" maxlength="64" placeholder="请输入住所"
						 value="${domestic.INVESTOR_ADDR}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'zftzzxxDiv','${domestic.INVESTOR_ID}INVESTOR_IDNO');"
					defaultEmptyText="请选择证件类型" name="${domestic.INVESTOR_ID}INVESTOR_IDTYPE" 
						 value="${domestic.INVESTOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${domestic.INVESTOR_ID}INVESTOR_IDNO" maxlength="32" placeholder="请输入证件号码"
						 value="${domestic.INVESTOR_IDNO}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>国别（地区）：</th>
				<td>
					<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb"
					dataInterface="dictionaryService.findDatasForCountrySelect"
					defaultEmptyText="请选择国别（地区）" name="${domestic.INVESTOR_ID}INVESTOR_COUNTRY" value="中国">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>合伙人类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgdlx"
					dataInterface="dictionaryService.findDatasForSelect" value="${domestic.INVESTOR_JOINT_TYPE}" 
					defaultEmptyText="请选择合伙人类型" name="${domestic.INVESTOR_ID}INVESTOR_JOINT_TYPE"  
					onchange="setGdlxValidate(this.value,'${domestic.INVESTOR_ID}LEGAL_PERSON');showZfInvestorAppoint('${s.index}');">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>承担责任方式：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="cdzrfs"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setsfzxswhhr(this.value,'zftzzxxDiv','${s.index}');"
					defaultEmptyText="请选择承担责任方式" name="${s.index}DUTY_MODE" 
					value="${domestic.DUTY_MODE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>执行事务合伙人：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="YesOrNo"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setPartnershipName();showZfInvestorAppoint('${s.index}');"  
					defaultEmptyText="请选择是否执行事务合伙人" name="${s.index}IS_PARTNERSHIP" 
					value="${domestic.IS_PARTNERSHIP}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 合伙企业或其他组织机构法定代表人姓名：</th>
				<td colspan="3"><input type="text" style="width:600px;"					
				<c:if test="${domestic.INVESTOR_JOINT_TYPE=='zrr'}"> class="syj-input1" disabled="disabled" </c:if>
				<c:if test="${domestic.INVESTOR_JOINT_TYPE!='zrr'}"> class="syj-input1 validate[required]"  </c:if>
					name="${domestic.INVESTOR_ID}LEGAL_PERSON" value="${domestic.LEGAL_PERSON}" placeholder="请输入法定代表人姓名"  maxlength="32"/></td>
			</tr>
			<tr class="${s.index}investorAppointTr" style="display:none;">
				<th colspan="4" style="background-color: #FFD39B;">委派人信息</th>
			</tr>
			<tr class="${s.index}investorAppointTr" style="display:none;">
				<th><font class="syj-color2">*</font>姓名：</th>
				<td colspan="3"><input type="text" class="syj-input1" value="${domestic.INVESTOR_APPOINT_NAME}" name="${s.index}INVESTOR_APPOINT_NAME"  maxlength="32"  placeholder="请输入姓名"/></td>		
			
			</tr>
			<tr class="${s.index}investorAppointTr" style="display:none;">
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown" dataParams="DocumentType" 
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'zftzzxxDiv','${s.index}INVESTOR_APPOINT_IDNO');"
					defaultEmptyText="请选择证件类型" name="${s.index}INVESTOR_APPOINT_IDTYPE" value="${domestic.INVESTOR_APPOINT_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1" value="${domestic.INVESTOR_APPOINT_IDNO}"
					name="${s.index}INVESTOR_APPOINT_IDNO" maxlength="32"  placeholder="请输入证件号码"/></td>
			</tr>
			<script type="text/javascript">
				$(function(){
					showZfInvestorAppoint('${s.index}');
				});
			</script>
			<tr>
				<th colspan="4" style="background-color: #FFD39B;">中方合伙人出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
			</tr>
			<tr>
				<th> 出资方式</th>
				<th colspan="3" style="text-align: center;">折注册资本币种(万元)</th>
			</tr>
			<tr>
				<th> 现金_境内人民币</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_DOMESTIC_RMB" maxlength="16"  placeholder="请输入现金_境内人民币(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_DOMESTIC_RMB}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 实物</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_MATERIAL" maxlength="16"  placeholder="请输入实物(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_MATERIAL}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 技术出资</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_TECHNOLOGY" maxlength="16"  placeholder="请输入技术出资(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_TECHNOLOGY}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 土地使用权</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_LAND" maxlength="16"  placeholder="请输入土地使用权(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_LAND}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 股权</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_STOCK" maxlength="16"  placeholder="请输入股权(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_STOCK}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 其他</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${domestic.INVESTOR_ID}INVESTMENT_OTHER" maxlength="16"  placeholder="请输入其他(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
						 value="${domestic.INVESTMENT_OTHER}"/>
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
					name="${domestic.INVESTOR_ID}CONTRIBUTION" placeholder="系统会根据用户填写的认缴出资额信息自动计算" 
						 value="${domestic.CONTRIBUTION}"/>
				</td>
				<th><font class="syj-color2">*</font>出资比例：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="${domestic.INVESTOR_ID}PROPORTION" placeholder="系统会根据用户填写的认缴出资额信息自动计算"  
						 value="${domestic.PROPORTION}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>折人民币：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[[required],custom[JustNumber]]" readonly="readonly"
					name="${s.index}FOLDING_RMB"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" value="${domestic.FOLDING_RMB}"/></td>
				<th><font class="syj-color2">*</font>出资时间：</th>
				<td><input type="text" class="Wdate validate[required]"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
					 readonly="readonly" name="${s.index}FOLDING_DATE"  placeholder="请选择出资时间" value="${domestic.FOLDING_DATE}"/></td>
			</tr>		
			<tr>
				<th colspan="4" style="background-color: #FFD39B;">实缴额详情</th>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[JustNumber],min[1]" maxlength="16" onblur="setRealCapital()"
					name="${s.index}PAIDIN_QUOTA"  placeholder="请输入实缴出资额（万元）" value="${domestic.PAIDIN_QUOTA}"/></td>
				<th><font class="syj-color2">*</font>缴款日期：</th>
				<td><input type="text" class="Wdate validate[required]"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})" 
					 readonly="readonly" name="${s.index}PAIDIN_DATE"  placeholder="请选择缴款日期" value="${domestic.PAIDIN_DATE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>折人民币（万元）：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[[required],custom[JustNumber]]"
					name="${s.index}PAIDIN_RMB"  placeholder="请输入折人民币（万元）" style="width: 278px;" value="${domestic.PAIDIN_RMB}"/></td>
			</tr>
		</table>
		<a  href="javascript:void(0);" onclick="javascript:delZftzzxxDiv(this);" class="syj-closebtn"></a>		
	</div>
</c:forEach>