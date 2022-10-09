<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业资金信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font><span id="investmentTab">投资总额：</span></td>
		<td><input type="text" class="tab_text " disabled="disabled" 
			name="INVESTMENT" value="${busRecord.INVESTMENT }"/>万元</td>
		<td class="tab_width"><font class="tab_color">*</font>币种：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjbz"
			dataInterface="dictionaryService.findDatasForSelect" onchange="currencyChange(this.value);"
			defaultEmptyText="请选择币种" name="CURRENCY" id="selectcurrency" value="${busRecord.CURRENCY }">
			</eve:eveselect>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2 ybzx" id="holder">
	<tr>
		<th colspan="4">股东信息</th>
	</tr>
	<tr id="holder_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>股东姓名/名称：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="SHAREHOLDER_NAME"  maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 股东类型：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjgdlx"
						dataInterface="dictionaryService.findDatasForSelect" id="holderType"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证照类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="LICENCE_NO" maxlength="32"/></td>
					<td class="tab_width"><font class="tab_color">*</font>国家（地区）：</td>
					<td><eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
									   defaultEmptyText="请选择国别"	 dataInterface="dictionaryService.findDatasForCountrySelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY">
					</eve:eveselect></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>身份证件地址：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="ID_ADDRESS" maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>联系方式：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="CONTACT_WAY"  maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 投资企业或其他组织机构法定代表人姓名：</td>
					<td ><input type="text" class="tab_text"
						name="LEGAL_PERSON" maxlength="32"/></td>
					<td class="tab_width"> 法定代表人身份证号码：</td>
					<td ><input type="text" class="tab_text validate[]"
								name="LEGAL_IDNO_PERSON" maxlength="32"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">股东出资方式（至少包含以下一项）</th>
				</tr>
				<tr>
					<td  style="width: 170px;background: #fbfbfb;"> 出资方式</td>
					<td colspan="3" style="text-align: center;">折合人民币</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_CASH" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 实物：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_MATERIAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 技术出资：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_TECHNOLOGY" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 土地使用权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_LAND" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 股权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_STOCK" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 其他：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_OTHER" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">出资额合计</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>认缴出资额（万元）：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="CONTRIBUTIONS" maxlength="16"/>万元</td>
					<td class="tab_width"><font class="tab_color">*</font>占注册资本比例：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PROPORTION" maxlength="5"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>实缴出资额（万元）：</td>
					<td colspan="3"><input type="text" class="tab_text "
							   name="PAIDCAPITAL" maxlength="16"/>万元</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" name="deleteCurrentHolder" onclick="deleteHolder('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneHolder" value="增加一行" onclick="addHolder();"> -->
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>出资全部缴付到位期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="period" readonly="readonly"
			name="PAYMENT_PERIOD" value="${busRecord.PAYMENT_PERIOD }"/></td>
	</tr>
</table>