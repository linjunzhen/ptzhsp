<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="ChineseInvest" style="display: none;">
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">中方投资者信息</th>
	</tr>
	<tr id="ChineseInvest_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>中方投资者名称：</td>
					<td colspan="3">
						<input type="hidden" name="INVESTOR_TYPE" value="1"/>
						<input type="text" class="tab_text validate[required]" style="width:600px;"
							name="INVESTOR_NAME"  maxlength="32" onblur="addAppointSelect(this.value,'zf');"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>中方投资者联系电话：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="INVESTOR_PHONE"  maxlength="16"/></td>
					<td class="tab_width"><font class="tab_color">*</font>委派董事人数：</td>
					<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
						name="DIRECTOR_NUM" onblur="checkDirectorNum();"/>人</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="INVESTOR_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="INVESTOR_IDNO" maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>地址：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="INVESTOR_ADDR" maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别（地区）" name="INVESTOR_COUNTRY">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>中方投资者省/市信息：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="province"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择省/市" name="INVESTOR_PROVINCE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>资金来源地：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="FUNDS_SOURCE" maxlength="64"/>
					</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">中方投资者出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
				</tr>
				<tr>
					<td style="width: 170px;background: #fbfbfb;"> 出资方式</td>
					<td colspan="3" style="text-align: center;">折注册资本币种</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金_境内人民币：</td>
					<td colspan="3" style="text-align: center;">
						<input type="text" class="tab_text validate[[],custom[numberp6plus]]"
							name="INVESTMENT_DOMESTIC_RMB" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 实物：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_MATERIAL" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 技术出资：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_TECHNOLOGY" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 土地使用权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_LAND" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 股权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_STOCK" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 其他：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_OTHER" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">出资额合计</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>出资额：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="CONTRIBUTION" maxlength="16"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>占注册资本比例：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PROPORTION" maxlength="5"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>折人民币：</td>
					<td colspan="3"><input type="text" class="tab_text validate[[],custom[numberp6plus]]" disabled="disabled"
						name="FOLDING_RMB" maxlength="16"/>万元</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" name="deleteCurrentChineseInvest" onclick="deleteChineseInvest('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneChineseInvest" value="增加一行" onclick="addChineseInvest();"> -->
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
