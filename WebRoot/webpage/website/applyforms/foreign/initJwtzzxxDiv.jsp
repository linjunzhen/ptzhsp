<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${foreigninvestorList}" var="foreign" varStatus="s">
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>境外投资者名称：</th>
				<td colspan="3">
					<input type="hidden" name="INVESTOR_TYPE" value="0"/>
					<input type="text" class="syj-input1 validate[required]" style="width:600px;"
						name="${foreign.INVESTOR_ID}INVESTOR_NAME"  maxlength="32" placeholder="请输入境外投资者名称"
						onblur="setInvestorName();setAuthorizer();" value="${foreign.INVESTOR_NAME}"/>
				</td>
			</tr>
			<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'||companySettype=='zwhz'}">
			<tr>				
				<th><font class="syj-color2">*</font>委派董事人数：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required,custom[numberplus]]" style="width:600px;"
					name="${foreign.INVESTOR_ID}DIRECTOR_NUM"  placeholder="请输入委派董事人数" maxlength="4"  value="${foreign.DIRECTOR_NUM}"/></td>
			</tr>
			</c:if>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>境外投资者移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${foreign.INVESTOR_ID}INVESTOR_PHONE"  maxlength="16"  placeholder="请输入移动电话"  value="${foreign.INVESTOR_PHONE}"/></td>
				<th><font class="syj-color2">*</font>地址：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="${foreign.INVESTOR_ID}INVESTOR_ADDR" maxlength="128" placeholder="请输入地址"
					value="${foreign.INVESTOR_ADDR}"/></td>
			
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>主体资格证明/护照：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setWzqyZjValidate(this.value,'jwtzzxxDiv','${foreign.INVESTOR_ID}INVESTOR_IDNO');"
					defaultEmptyText="请选择证照类型" name="${foreign.INVESTOR_ID}INVESTOR_IDTYPE" value="${foreign.INVESTOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码/主体资格证明号码/护照号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="${foreign.INVESTOR_ID}INVESTOR_IDNO" maxlength="32"  placeholder="请输入号码"
					value="${foreign.INVESTOR_IDNO}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>国别（地区）：</th>
				<td>
					<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb:中国"
					dataInterface="dictionaryService.findDatasForCountrySelect"
					defaultEmptyText="请选择国别（地区）" name="${foreign.INVESTOR_ID}INVESTOR_COUNTRY" 
					value="${foreign.INVESTOR_COUNTRY}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>资金来源地：</th>
				<td>
					<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择资金来源地" name="${foreign.INVESTOR_ID}FUNDS_SOURCE" 
					value="${foreign.FUNDS_SOURCE}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th colspan="4" style="background-color: #FFD39B;">境外投资者出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
			</tr>
			<tr>
				<th> 出资方式</th>
				<th colspan="3" style="text-align: center;">折注册资本币种(万元)</th>
			</tr>
			<tr>
				<th> 现金_境外现汇</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_ABROAD_EXCHANGE" maxlength="16"  placeholder="请输入现金_境外现汇(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"  
					value="${foreign.INVESTMENT_ABROAD_EXCHANGE}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
			</tr>
			<tr>
				<th> 现金_境外人民币</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_ABROAD_RMB" maxlength="16"  placeholder="请输入现金_境外人民币(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"  
					value="${foreign.INVESTMENT_ABROAD_RMB}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 现金_境内人民币</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_DOMESTIC_RMB" maxlength="16" placeholder="请输入现金_境内人民币(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;" 
					value="${foreign.INVESTMENT_DOMESTIC_RMB}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 实物</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_MATERIAL" maxlength="16"  placeholder="请输入实物(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;" 
					value="${foreign.INVESTMENT_MATERIAL}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 技术出资</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_TECHNOLOGY" maxlength="16"  placeholder="请输入技术出资(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;" 
					value="${foreign.INVESTMENT_TECHNOLOGY}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 土地使用权</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_LAND" maxlength="16"  placeholder="请输入土地使用权(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
					value="${foreign.INVESTMENT_LAND}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 股权</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_STOCK" maxlength="16"  placeholder="请输入股权(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
					value="${foreign.INVESTMENT_STOCK}"/>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th> 其他</th>
				<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="${foreign.INVESTOR_ID}INVESTMENT_OTHER" maxlength="16"  placeholder="请输入其他(万元)" 
					onblur="onlyNumber(this);setJwtzzInvestment()" onkeyup="onlyNumber(this);" style="width: 520px;"
					value="${foreign.INVESTMENT_OTHER}"/>
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
					name="${foreign.INVESTOR_ID}CONTRIBUTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" 
					value="${foreign.CONTRIBUTION}"/>
				</td>
				<th><font class="syj-color2">*</font>占注册资本比例：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="${foreign.INVESTOR_ID}PROPORTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算"  
					value="${foreign.PROPORTION}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>折人民币：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]" readonly="readonly"
					name="${foreign.INVESTOR_ID}FOLDING_RMB"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" style="width: 278px;"  
					value="${foreign.FOLDING_RMB}"/></td>
			</tr>
		</table>
		<c:if test="${s.index>0}">
		<a  href="javascript:void(0);" onclick="javascript:delJwtzzxxDiv(this);" class="syj-closebtn"></a>
		</c:if>
	</div>
</c:forEach>