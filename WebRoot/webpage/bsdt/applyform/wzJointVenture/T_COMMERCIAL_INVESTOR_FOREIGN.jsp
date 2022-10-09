<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="foreignInvest">
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">外方合伙人信息</th>
	</tr>
	<tr id="foreignInvest_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>境外合伙人名称：</td>
					<td colspan="3">
						<input type="hidden" name="INVESTOR_TYPE" value="0"/>
						<input type="text" class="tab_text validate[required]" style="width:600px;"
							name="INVESTOR_NAME"  maxlength="32" onblur="addAppointSelect(this.value,'wf');"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>境外合伙人移动电话：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]"
						name="INVESTOR_PHONE"  maxlength="16"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>住所：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="INVESTOR_ADDR" maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="INVESTOR_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="INVESTOR_IDNO" maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国别（地区）：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="ssdjgb" id="foreignCountry"
						dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
						defaultEmptyText="请选择国别（地区）" name="INVESTOR_COUNTRY">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>合伙人类型：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="wzhhrlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择合伙人类型" name="INVESTOR_JOINT_TYPE">
						</eve:eveselect>
					</td>
				</tr>				
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>承担责任方式：</td>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="cdzrfs"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择承担责任方式" name="DUTY_MODE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>执行事务合伙人：</td>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择是否执行事务合伙人" name="IS_PARTNERSHIP">
						</eve:eveselect>
					</td>
				</tr>
				<tr class="foreignAppointTr" style="display:none;">
					<th colspan="4" style="background-color: #FFD39B;">委派人信息</th>
				</tr>
				<tr class="foreignAppointTr" style="display:none;">
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td colspan="3"><input type="text" class="tab_text validate[]" style="width:600px;"
						name="INVESTOR_APPOINT_NAME" maxlength="64"/></td>
				</tr>
				<tr class="foreignAppointTr" style="display:none;">
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="INVESTOR_APPOINT_IDTYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[]"
						name="INVESTOR_APPOINT_IDNO" maxlength="32"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">境外合伙人出资方式(至少包含以下一项，折算汇率以当日汇率为准)</th>
				</tr>
				<tr>
					<td style="width: 170px;background: #fbfbfb;"> 出资方式</td>
					<td colspan="3" style="text-align: center;">折注册资本币种</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金_境外现汇：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_ABROAD_EXCHANGE" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金_境外人民币：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_ABROAD_RMB" maxlength="16" onblur="calculation();"/>万
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金_境内人民币：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
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
					<td class="tab_width"><font class="tab_color">*</font>认缴出资额：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="CONTRIBUTION" maxlength="16"/>万元
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>出资比例：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PROPORTION" maxlength="5"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>折人民币：</td>
					<td><input type="text" class="tab_text validate[[],custom[numberp6plus]]" disabled="disabled"
						name="FOLDING_RMB" maxlength="16"/>万元</td>
					<td class="tab_width"><font class="tab_color">*</font>出资时间：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="FOLDING_DATE" maxlength="5"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">实缴额详情</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>实缴出资额：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PAIDIN_QUOTA" maxlength="16"/>万元
						<input type="text" class="tab_text" disabled="disabled" id="currency"/>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>缴款日期：</td>
					<td><input type="text" class="tab_text " disabled="disabled"
						name="PAIDIN_DATE" maxlength="5"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>折人民币（万元）：</td>
					<td colspan="3"><input type="text" class="tab_text validate[[],custom[numberp6plus]]" disabled="disabled"
						name="PAIDIN_RMB" maxlength="16"/>万元</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" name="deleteCurrentForeignInvest" onclick="deleteForeignInvest('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneForeignInvest" value="增加一行" onclick="addForeignInvest();"> -->
		</td>
	</tr>
</table>
