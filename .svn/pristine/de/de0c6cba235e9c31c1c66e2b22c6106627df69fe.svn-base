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
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
	
		<tr>
			<th><font class="syj-color2">*</font>股东姓名/名称1：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32" value="${holder.SHAREHOLDER_NAME}"/></td>
			<th><font class="syj-color2">*</font>股东类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgdlx"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setGdlxValidate(this.value,'${s.index}LEGAL_PERSON');"
				defaultEmptyText="请选择股东类型" name="${currentTime}SHAREHOLDER_TYPE" value="${holder.SHAREHOLDER_TYPE}">
				</eve:eveselect>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2" >
		<tr>
			<th><font class="syj-color2">*</font>证照类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}LICENCE_NO');" 
				defaultEmptyText="请选择证照类型" name="${currentTime}LICENCE_TYPE" value="${holder.LICENCE_TYPE}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32" value="${holder.LICENCE_NO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>身份证件地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required] w878"
				name="${currentTime}ID_ADDRESS" placeholder="请输入身份证件地址" maxlength="64" value="${holder.ID_ADDRESS}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>联系方式：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
						name="${currentTime}CONTACT_WAY"  placeholder="请输入联系方式"  maxlength="16" value="${holder.CONTACT_WAY}"/></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required] w280" dataParams="ssdjgb" defaultEmptyValue="中国"
							   dataInterface="dictionaryService.findDatasForCountrySelect"
							   name="${currentTime}SHAREHOLDER_COMPANYCOUNTRY" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 投资企业或其他组织机构法定代表人姓名：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878"
				name="${s.index}LEGAL_PERSON"  placeholder="请输入法定代表人姓名" maxlength="32" value="${holder.LEGAL_PERSON}"/></td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">股东出资方式（至少包含以下一项）</th>
		</tr>
		<tr>
			<th> 出资方式</th>
			<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
		</tr>
		<tr>
			<th> 现金</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_CASH"  placeholder="请输入现金(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"  value="${holder.INVESTMENT_CASH}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 实物</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="16" 
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"  value="${holder.INVESTMENT_MATERIAL}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 技术出资</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_TECHNOLOGY}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 土地使用权</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="16" 
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_LAND}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 股权</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="16"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;" value="${holder.INVESTMENT_STOCK}"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjbz"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="人民币" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 其他</th>
			<td colspan="3" style="text-align: center;"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="${currentTime}INVESTMENT_OTHER"  placeholder="请输入其他(万元)" maxlength="16"
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
			<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${currentTime}CONTRIBUTIONS"  placeholder="请填写股东出资方式" readonly="readonly"  value="${holder.CONTRIBUTIONS}"/></td>
			<th><font class="syj-color2">*</font>占注册资本比例：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${currentTime}PROPORTION"  placeholder="请填写股东出资方式" readonly="readonly"  value="${holder.PROPORTION}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
			<td><input type="text"  class="syj-input1 validate[required,custom[JustNumber]]"  onblur="onlyNumber(this)" onkeyup="onlyNumber(this);"
					   name="${currentTime}PAIDCAPITAL"  placeholder="请输入实缴出资额" maxlength="14" value="${holder.PAIDCAPITAL}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a href="javascript:void(0);"
			onclick="delGdxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>
	

