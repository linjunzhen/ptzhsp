<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<style type="text/css">

	.addbtn{
	    width: 50px;
	    height: 30px;
	    text-align: center;
	    background: #6496c8;
	    color:#fff;
	    border:none;
	    border-radius:2px;
	    
	}
	
	
</style>

<div class="addBox">
    <input type="hidden" name="status" value="1" />
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th>原股东</th>
			<td><input type="text" class="syj-input1"
					name="${currentTime}SHAREHOLDER_NAME"/>
			   <input type="button" value="收起"  onclick="displayOrShowOldGdxx(this);" class="addbtn"/>
			</td>
			
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2"  >
		<tr>
			<th><font class="syj-color2">*</font>股东姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32" 
				onchange="setSelectStockFromName(this,this.value)" /></td>
			<th><font class="syj-color2">*</font>股东类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="investorType"
				dataInterface="extraDictionaryService.findDatasForSelect" onchange="setGdlxValidate(this.value,'${currentTime}LEGAL_PERSON');setGdlxValidate(this.value,'${currentTime}LEGAL_IDNO_PERSON');"
				defaultEmptyText="请选择股东类型" name="${currentTime}SHAREHOLDER_TYPE">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${currentTime}LICENCE_NO');"
				defaultEmptyText="请选择证件类型" name="${currentTime}LICENCE_TYPE">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td ><input type="text" class="syj-input1 validate[required]"
				name="${currentTime}LICENCE_NO"  placeholder="请输入证件号码" maxlength="32"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required] w878"
				name="${currentTime}ID_ADDRESS" placeholder="请输入证件地址，请按身份证上的地址申报" maxlength="64"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>联系方式：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[mobilePhone]]"
				name="${currentTime}CONTACT_WAY"  placeholder="请输入联系方式" maxlength="16"/></td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="state" defaultEmptyValue="156"
							   dataInterface="extraDictionaryService.findDatasForSelect"
							   name="${currentTime}SHAREHOLDER_COMPANYCOUNTRY" >
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2 ${currentTime}LEGAL_PERSON_CLASS" style="display: none" >*</font> 投资企业或其他组织机构法定代表人姓名：</th>
			<td ><input type="text" class="syj-input1 "
				name="${currentTime}LEGAL_PERSON"  placeholder="请输入法定代表人姓名"  maxlength="32"/></td>
			<th><font class="syj-color2 ${currentTime}LEGAL_PERSON_CLASS" style="display: none" >*</font> 法定代表人身份证号码：</th>
			<td ><input type="text" class="syj-input1  validate[custom[vidcard]]"
						name="${currentTime}LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码"  maxlength="32"/></td>
		</tr>
		<tr>
			<th colspan="4" style="background-color: #FFD39B;text-align: center;">原股东出资方式（至少包含以下一项）</th>
		</tr>
		<tr>
			<th> 出资方式</th>
			<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
		</tr>
		<tr>
			<th> 现金</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_CASH"  placeholder="请输入现金(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment();isEqualToCash(this.value);" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 实物</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14" 
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 技术出资</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 土地使用权</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14" 
				onblur="onlyNumber(this);setInvestment();isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 股权</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment();isEqualToStock(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 其他</th>
			<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
				name="INVESTMENT_OTHER"  placeholder="请输入其他(万元)" maxlength="14"
				onblur="onlyNumber(this);setInvestment()" onkeyup="onlyNumber(this);" style="width: 638px;"/>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY_1" value="002" onchange="setCurrency(this.value)">
				</eve:eveselect>
			</td>
		</tr>		

		<tr>
			<th colspan="4" style="background-color: #FFD39B; text-align: center;">出资额合计</th>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="CONTRIBUTIONS"  placeholder="请填写股东出资方式" readonly="readonly" />
				<input type="hidden" name="CONTRIBUTIONS_REMAIN" />
				<input type="hidden" name="CONTRIBUTIONS_PRO" />
			</td>
			<th><font class="syj-color2">*</font>占注册资本比例：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="PROPORTION"  placeholder="请填写股东出资方式" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>实缴出资额（万元）：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[JustNumber]]"  onblur="onlyNumber(this);isEqualToSjcze(this.value);setPayTime(this);" onkeyup="onlyNumber(this);"
					   name="PAIDCAPITAL"  placeholder="请输入实缴出资额" maxlength="14"/></td>
		   <th ><font class="syj-color2" id="jfrq" style="display:none">*</font>实缴时间：</th>
		   <td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-{%d-1}'})"
		        name="PAYMENT_TIME"  placeholder="请选择实缴时间" /></td>
		</tr>
	</table>
	<a href="javascript:void(0);"
			onclick="delOldGdxxDiv(this);" class="syj-closebtn"></a>
</div>
	

