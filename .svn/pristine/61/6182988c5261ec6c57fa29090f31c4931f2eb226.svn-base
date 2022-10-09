<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${domesticinvestorList}" var="domestic" varStatus="s">
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>中方投资者名称：</th>
				<td colspan="3">
					<input type="hidden" name="${domestic.INVESTOR_ID}INVESTOR_TYPE" value="1"/>
					<input type="text" class="syj-input1 validate[required]" style="width:600px;"
						name="${domestic.INVESTOR_ID}INVESTOR_NAME"  maxlength="32" placeholder="请输入中方投资者名称"  onblur="setInvestorName()"
						 value="${domestic.INVESTOR_NAME}"/>
				</td>
			</tr>
			<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'||companySettype=='zwhz'}">
			<tr>
				<th><font class="syj-color2">*</font>委派董事人数：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required,custom[numberplus]]" style="width:600px;"
					name="${domestic.INVESTOR_ID}DIRECTOR_NUM" placeholder="请输入委派董事人数" maxlength="4"
						 value="${domestic.DIRECTOR_NUM}"/></td>
			</tr>
			</c:if>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>中方投资者移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${domestic.INVESTOR_ID}INVESTOR_PHONE"  maxlength="16" placeholder="请输入移动电话"
						 value="${domestic.INVESTOR_PHONE}"/></td>
				<th><font class="syj-color2">*</font>地址：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${domestic.INVESTOR_ID}INVESTOR_ADDR" maxlength="64" placeholder="请输入地址"
						 value="${domestic.INVESTOR_ADDR}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'zftzzxxDiv','${domestic.INVESTOR_ID}INVESTOR_IDNO');"
					defaultEmptyText="请选择证照类型" name="${domestic.INVESTOR_ID}INVESTOR_IDTYPE" 
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
				<td class="tab_width"><font class="syj-color2">*</font>中方投资者省/市：</td>
				<td>
					<eve:eveselect clazz="input-dropdown   validate[required]" dataParams="province"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择省/市" name="${domestic.INVESTOR_ID}INVESTOR_PROVINCE"
						 value="${domestic.INVESTOR_PROVINCE}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>资金来源地：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[required]"
							name="FUNDS_SOURCE" maxlength="64" placeholder="请输入资金来源地"
						 value="${domestic.FUNDS_SOURCE}"/>
				</td>
			</tr>
			<tr>
				<th colspan="4" style="background-color: #FFD39B;">中方投资者出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
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
				<th><font class="syj-color2">*</font>出资额(万元)：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="${domestic.INVESTOR_ID}CONTRIBUTION" placeholder="系统会根据用户填写的认缴出资额信息自动计算" 
						 value="${domestic.CONTRIBUTION}"/>
				</td>
				<th><font class="syj-color2">*</font>占注册资本比例：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="${domestic.INVESTOR_ID}PROPORTION" placeholder="系统会根据用户填写的认缴出资额信息自动计算"  
						 value="${domestic.PROPORTION}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>折人民币：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[[required],custom[JustNumber]]" readonly="readonly"
					name="${domestic.INVESTOR_ID}FOLDING_RMB"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" style="width: 278px;"  
						 value="${domestic.FOLDING_RMB}"/></td>
			</tr>
		</table>
		<c:if test="${s.index>0}">
			<a  href="javascript:void(0);" onclick="javascript:delZftzzxxDiv(this);" class="syj-closebtn"></a>
		</c:if>
	</div>
</c:forEach>