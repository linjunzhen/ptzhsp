<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="CAPITAL_FORM"  >	
	<div class="syj-title1 ">
		<span>原企业资金信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>注册资本(万元)：</th>
				<td  title="系统会根据用户填写的认缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" 
					name="INVESTMENT" value="${busRecord.INVESTMENT}"/></td>
				<th><font class="syj-color2">*</font>币种：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="currency" onchange=""
					dataInterface="extraDictionaryService.findDatasForSelect"
					defaultEmptyText="请选择币种" name="CURRENCY" value="${busRecord.CURRENCY}" >
					</eve:eveselect>
					<script type="text/javascript">
						$(function(){
							$("[name$='CURRENCY']").attr("disabled",true);
							$("[name$='CURRENCY_1']").attr("disabled",true);
							$("[name$='CURRENCY_CHANGE']").attr("disabled",true);
							$("[name$='CURRENCY_3']").attr("disabled",true);
						});
					</script>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addOldGdxxDiv();" class="syj-addbtn" id="addOldGdxx" >原股东</a>
		<span>原股东信息</span>
	</div>
	<div id="gdxxDiv">
		<jsp:include page="./initGdxxDiv.jsp"></jsp:include>
	</div>
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">		
		<tr>
			<th><font class="syj-color2">*</font>出资全部缴付</br>到位期限：</th>
			<td ><input type="text" class="Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:handleDate})"
				id="periodOld" readonly="readonly" name="PAYMENT_PERIOD_OLD"  placeholder="请选择期限" value="${busRecord.PAYMENT_PERIOD_OLD}"/></td>
		</tr>
	</table> 
	
	<div id="C_CAPTITAL" style="display: none;">
		<div class="syj-title1 ">
			<span>拟变更后新企业资金信息</span>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
				<tr>
					<th><font class="syj-color2">*</font>注册资本(万元)：</th>
					<td  title="系统会根据用户填写的认缴出资额信息自动计算"><input type="text" class="syj-input1 inputBackgroundCcc " 
						name="INVESTMENT_CHANGE"  placeholder="系统会根据用户填写的认缴出资额信息自动计算"
						readonly="readonly" value="${busRecord.INVESTMENT_CHANGE}"/></td>
					<th><font class="syj-color2">*</font>币种：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280" dataParams="currency" onchange="setCurrency2(this.value)"
						dataInterface="extraDictionaryService.findDatasForSelect"
						defaultEmptyText="请选择币种" name="CURRENCY_CHANGE" value="${busRecord.CURRENCY_CHANGE}">
						</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addNewGdxxDiv();" class="syj-addbtn" id="addGdxx" style="display:none;">股东</a><span>拟变更后新股东信息</span>
		</div>
		<div id="newGdxxDiv">
			<c:if test="${empty holderChangeList}">	
			<div style="position:relative;">
				<input type="hidden" name="status" value="1" />
				<table cellpadding="0" cellspacing="0" class="syj-table2 ">
					<tr>
						<th>新股东</th>
						<td><input type="text" class="syj-input1" 
								name="SHAREHOLDER_NAME"  />
						   <input type="button" value="收起"  onclick="displayOrShowNewGdxx(this);" class="addbtn"/>
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2 ">
					<tr>
						<th><font class="syj-color2">*</font>股东姓名/名称：</th>
						<td><input type="text" class="syj-input1 " onchange="setSelectHolderName(this,this.value,'SHAREHOLDER')"
							name="SHAREHOLDER_NAME" placeholder="请输入股东姓名/名称" maxlength="32"/></td>
						<th><font class="syj-color2">*</font>股东类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="investorType"
							dataInterface="extraDictionaryService.findDatasForSelect" onchange="setGdlxValidate(this.value,'LEGAL_PERSON');setGdlxValidate(this.value,'LEGAL_IDNO_PERSON');"
							defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE" >
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LICENCE_NO');"
							defaultEmptyText="请选择证件类型" name="LICENCE_TYPE">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1 "
							name="LICENCE_NO"  placeholder="请输入证件号码" maxlength="32"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件地址：</th>
						<td colspan="3"><input type="text" class="syj-input1 w878"
							name="ID_ADDRESS" placeholder="请输入证件地址"  maxlength="64"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>联系方式：</th>
						<td ><input type="text" class="syj-input1 "
							name="CONTACT_WAY"  placeholder="请输入联系方式"  maxlength="16"/></td>
						<th><font class="syj-color2">*</font>国别（地区）：</th>
						<td>
							<eve:eveselect clazz="input-dropdown  w280" dataParams="state" defaultEmptyValue="156"
										   dataInterface="extraDictionaryService.findDatasForSelect"
										   name="SHAREHOLDER_COMPANYCOUNTRY" >
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2 LEGAL_PERSON_CLASS" style="display: none" >*</font>投资企业或其他组织机构法定代表人姓名：</th>
						<td ><input type="text" class="syj-input1"
							name="LEGAL_PERSON"  placeholder="请输入法定代表人姓名"  maxlength="32"/></td>
						<th><font class="syj-color2 LEGAL_PERSON_CLASS" style="display: none" >*</font>法定代表人身份证号码：</th>
						<td ><input type="text" class="syj-input1  validate[custom[vidcard]]"
									name="LEGAL_IDNO_PERSON"  placeholder="请输入法定代表人身份证号码"  maxlength="32"/></td>
					</tr>
					<tr>
						<th colspan="4" style="background-color: #FFD39B;text-align: center;">新股东出资方式（至少包含以下一项）</th>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2 gqlytable">
					<tbody>
						<tr style="background: #f2f2f2;">
							<th style="background: #f2f2f2;"><font class="syj-color2">*</font>股权来源：</th>
							<td style="border: none;">
								<select name="stockFrom" class="input-dropdown" onchange="setStockFrom(this);setNewInvestment(this);">
								</select>
							</td>
							<th style="border: none;background: #f2f2f2;"></th>
							<td style="border-left: none;">
								<a href="javascript:void(0);" onclick="javascript:addGqlyDiv(this);" class="syj-addbtn" id="addGqly">股权来源</a>
							</td>
						</tr>
						<tr>
							<th> 出资方式</th>
							<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
						</tr>
						<tr>
							<th> 现金</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_CASH"  placeholder="请输入现金(万元)"  maxlength="14" 
								onblur="onlyNumber(this);setNewInvestment(this);isEqualToCash(this.value);" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002" onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th> 实物</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14"
								onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th> 技术出资</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14"
								onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th> 土地使用权</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14"
								onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th> 股权</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14"
								onblur="onlyNumber(this);setNewInvestment(this);isEqualToStock(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th> 其他</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
								name="INVESTMENT_OTHER"  placeholder="请输入其他(万元)"  maxlength="14"
								onblur="onlyNumber(this);setNewInvestment(this)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
								<eve:eveselect clazz="input-dropdown " dataParams="currency"
									dataInterface="extraDictionaryService.findDatasForSelect"
									defaultEmptyText="请选择币种" name="CURRENCY_3" value="002" onchange="setCurrency2(this.value)">
								</eve:eveselect>
							</td>
						</tr>
						<tr id="transfer1" style="display:none;">
							<th>总转让认缴出资额（万元）：</th>
							<td><input type="text" class="syj-input1 inputBackgroundCcc"
								name="TRANSFER_CONTRIBUTIONS"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
							<th>占原注册资本比例：</th>
							<td><input type="text" class="syj-input1 inputBackgroundCcc"
								name="OLD_PROPORTION"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
						</tr>
						<tr id="transfer2" style="display:none;">
							<th><font class="syj-color2">*</font>转让价格：</th>
							<td><input type="text" class="syj-input1 " style="width: 45%;"
								name="TRANSFER_PRICE"  placeholder="请输入转让价格（万元）" onblur="setPayWay(this)" />
								<fda:fda-eveselect clazz="selectwidth input-dropdown " dataparams="currency"
									datainterface="extraDictionaryService.findDatasForSelect"
									defaultemptytext="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
								</fda:fda-eveselect>
							</td>
							<th><font class="syj-color2" id="fkfs" style="">*</font>付款方式：</th>
							<td>
								<select name="PAYMETHOD" class="input-dropdown " style="width:200px;">
									<option value="">请选择</option>
									<option value="现金">现金</option>
									<option value="转账">转账</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
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
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_CASH_TOTAL"  placeholder="请输入现金(万元)"  maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 实物</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_MATERIAL_TOTAL"  placeholder="请输入实物(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 技术出资</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_TECHNOLOGY_TOTAL"  placeholder="请输入技术出资(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 土地使用权</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_LAND_TOTAL" placeholder="请输入土地使用权(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 股权</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_STOCK_TOTAL"  placeholder="请输入股权(万元)" maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th> 其他</th>
						<td colspan="3"><input type="text" class="syj-input1 validate[],custom[JustNumber]"
							name="INVESTMENT_OTHER_TOTAL"  placeholder="请输入其他(万元)"  maxlength="14" style="width: 638px;" readonly="readonly"/>
							<eve:eveselect clazz="input-dropdown " dataParams="currency"
								dataInterface="extraDictionaryService.findDatasForSelect"
								defaultEmptyText="请选择币种" name="CURRENCY_3" value="002"  onchange="setCurrency2(this.value)">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th colspan="4" style="background-color: #FFD39B;">出资额合计</th>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>认缴出资额（万元）：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc "
							name="CONTRIBUTIONS"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" readonly="readonly" /></td>
						<th><font class="syj-color2">*</font>占注册资本比例：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc "
							name="PROPORTION"  placeholder="系统会根据用户填写的认缴出资额信息自动计算" readonly="readonly" /></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>实缴出资额（万元）：</th>
						<td><input type="text" class="syj-input1 "  onblur="onlyNumber(this);setPayTime(this);isEqualToSjcze(this.value)" onkeyup="onlyNumber(this);"
								   name="PAIDCAPITAL"  placeholder="请输入实缴出资额" maxlength="14"/></td>
					    <th ><font class="syj-color2" id="jfrq" style="display:none">*</font>缴付日期：</th>
						<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-{%d-1}'})"
					  name="PAYMENT_TIME"  placeholder="请选择缴款日期" /></td>
					</tr>
				</table>
				
			</div>
			</c:if>
			<jsp:include page="./initNewGdxxDiv.jsp"></jsp:include>
		</div>
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>出资全部缴付</br>到位期限：</th>
				<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:handleChangeDate})"
					id="period" readonly="readonly" name="PAYMENT_PERIOD"  placeholder="请选择期限" value="${busRecord.PAYMENT_PERIOD}" /></td>
			</tr>
		</table>
	</div>
</form>
