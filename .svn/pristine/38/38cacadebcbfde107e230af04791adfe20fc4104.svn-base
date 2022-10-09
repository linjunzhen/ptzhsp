<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="holder">
	<tr>
		<th colspan="4">合伙人信息</th>
	</tr>
	<tr id="holder_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>合伙人姓名/名称：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="SHAREHOLDER_NAME"  maxlength="32"/></td>
					<td class="tab_width"> 合伙人类型：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjgdlx"
						dataInterface="dictionaryService.findDatasForSelect" id="holderType"
						defaultEmptyText="请选择合伙人类型" name="SHAREHOLDER_TYPE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证照类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="LICENCE_NO" maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>住所：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="ID_ADDRESS" maxlength="64"/></td>
					<td class="tab_width"><font class="tab_color">*</font>承担责任方式：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="cdzrfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择承担责任方式" name="DUTY_MODE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="CONTACT_WAY"  maxlength="64"/></td>
					<td class="tab_width"><font class="tab_color">*</font>执行事务合伙人：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择执行事务合伙人" name="IS_PARTNERSHIP">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">固定电话：</td>
					<td><input type="text" class="tab_text "
							   name="SHAREHOLDER_FIXPHONE"    maxlength="64"/></td>
					</td>
					<td class="tab_width">电子邮箱：</td>
					<td><input type="text" class="tab_text "
							   name="SHAREHOLDER_EMAIL"    maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
					<td >
						<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
									   dataInterface="dictionaryService.findDatasForCountrySelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY" >
						</eve:eveselect>
					</td>
					<td class="tab_width">评估方式：</td>
					<td><input type="text" class="tab_text "
							   name="EVALUATE_WAY"    maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 合伙企业或其他组织机构法定代表人姓名：</td>
					<td colspan="3"><input type="text" class="tab_text" style="width:600px;"
						name="LEGAL_PERSON" maxlength="32"/></td>
				</tr>
				<tr class="licenceAppointTr" style="display:none;">
					<th colspan="4" style="background-color: #FFD39B;">委派人信息</th>
				</tr>
				<tr class="licenceAppointTr" style="display:none;">
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td colspan="3"><input type="text" class="tab_text validate[]" style="width:600px;"
						name="LICENCE_APPOINT_NAME" maxlength="64"/></td>
				</tr>
				<tr class="licenceAppointTr" style="display:none;">
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="LICENCE_APPOINT_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[]"
						name="LICENCE_APPOINT_IDNO" maxlength="32"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">合伙人出资方式（至少包含以下一项）</th>
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
					<th colspan="4" style="background-color: #FFD39B;">实缴额详情</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>实缴出资额（万元）：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PAIDIN_QUOTA" maxlength="16"/>万元</td>
					<td class="tab_width"><font class="tab_color">*</font>缴款日期：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PAIDIN_DATE" maxlength="5"/></td>
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
