<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>

<div class="tab_height"></div>
<!--原股东信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="holder">
	<tr>
		<th colspan="4">股东信息</th>
	</tr>
	<tr id="holder_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>股东姓名/名称：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="SHAREHOLDER_NAME"  maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>股东类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="investorType"
						dataInterface="extraDictionaryService.findDatasForSelect" id="holderType"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="LICENCE_NO" maxlength="32"/></td>
					<td class="tab_width"><font class="tab_color">*</font>国家（地区）：</td>
					<td><eve:eveselect clazz="tab_text validate[required]" dataParams="state"
									   defaultEmptyText="请选择国别"	 dataInterface="extraDictionaryService.findDatasForSelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY">
					</eve:eveselect></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件地址：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="ID_ADDRESS" maxlength="128"/></td>
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
					<td ><input type="text" class="tab_text validate[custom[vidcard]]"
								name="LEGAL_IDNO_PERSON" maxlength="32"/></td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">原股东出资方式（至少包含以下一项）</th>
				</tr>
				<tr>
					<td  style="width: 170px;background: #fbfbfb;"> 出资方式</td>
					<td colspan="3" style="text-align: center;">折合人民币</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_CASH" maxlength="16" />万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 实物：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_MATERIAL" maxlength="16" />万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 技术出资：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_TECHNOLOGY" maxlength="16" />万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 土地使用权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_LAND" maxlength="16"  />万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 股权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_STOCK" maxlength="16" />万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 其他：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_OTHER" maxlength="16" />万元</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">出资额合计</th>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>认缴出资额（万元）：</td>
					<td><input type="text" class="tab_text validate[required] " disabled="disabled"
						name="CONTRIBUTIONS" maxlength="16"/>万元
						<input type="hidden" name="CONTRIBUTIONS_REMAIN" />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>占注册资本比例：</td>
					<td><input type="text" class="tab_text validate[required]" disabled="disabled"
						name="PROPORTION" maxlength="5"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>认缴出资额转让后剩余金额（万元）：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required] " 
						name="CONTRIBUTIONS_REMAIN" maxlength="16"/>万元
					</td>
					<!-- <td class="tab_width"><font class="tab_color">*</font>原出资额转让后剩余占原注册资本比例：</td>
					<td><input type="text" class="tab_text validate[required]" 
						name="CONTRIBUTIONS_PRO" maxlength="10"/></td> -->
				</tr>
				
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>实缴出资额（万元）：</td>
					<td ><input type="text" class="tab_text validate[required]"
							   name="PAIDCAPITAL" maxlength="16"/>万元</td>
				    <td class="tab_width"><font class="tab_color" id="jfrq" style="display:none">*</font>实缴时间：</td>
					<td ><input type="text"  class="laydate-icon "
					  name="PAYMENT_TIME"  placeholder="请选择实缴时间" /></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!--  <input type="button" class="eflowbutton" name="deleteCurrentHolder" onclick="deleteHolder('1');" value="删除行">
			<br>
			<br>
			<input type="button" class="eflowbutton"  name="addOneHolder" value="增加一行" onclick="addHolder();">  -->
		</td>
	</tr>
</table>
 <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>出资全部缴付到位期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="periodOld" readonly="readonly"
			name="PAYMENT_PERIOD_OLD" value="${busRecord.PAYMENT_PERIOD_OLD}"/></td>
	</tr>
</table> 

<!--变更股东信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="bgholder" style="display:none">
	<tr>
		<th colspan="4">拟变更后新股东信息</th>
	</tr>
	<tr id="bgholder_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>股东姓名/名称：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="SHAREHOLDER_NAME"  maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tab_width"> 股东类型：</td>
					<td>
						<eve:eveselect clazz="tab_text " dataParams="investorType"
						dataInterface="extraDictionaryService.findDatasForSelect" id="holderType"
						defaultEmptyText="请选择股东类型" name="SHAREHOLDER_TYPE">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证件类型" name="LICENCE_TYPE">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td><input type="text" class="tab_text validate[required]"
						name="LICENCE_NO" maxlength="32"/></td>
					<td class="tab_width"><font class="tab_color">*</font>国家（地区）：</td>
					<td><eve:eveselect clazz="tab_text validate[required]" dataParams="state"
									   defaultEmptyText="请选择国别"	 dataInterface="extraDictionaryService.findDatasForSelect"
									   name="SHAREHOLDER_COMPANYCOUNTRY">
					</eve:eveselect></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件地址：</td>
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
					<td ><input type="text" class="tab_text validate[custom[vidcard]]"
								name="LEGAL_IDNO_PERSON" maxlength="32"/></td>
				</tr>
				<tr>
					<td colspan="4">
						<table class="tab_tk_pro2" id="bgholder_1_waytabble">
							<tr id="bgholder_1_waytabble_1">
								<td colspan="3">
									<table class="tab_tk_pro2">
										<tr>
											<th colspan="4" style="background-color: #FFD39B;">新股东出资方式（至少包含以下一项）</th>
										</tr>
										<tr>
											<td class="tab_width"><font class="tab_color">*</font>股权来源：</td>
											<td colspan="3">
												<input type="text" class="tab_text validate[required]"
												name="stockFrom" maxlength="32" value="新增"/>
											</td>
										</tr>
										<tr>
											<td  style="width: 170px;background: #fbfbfb;"> 出资方式</td>
											<td colspan="3" style="text-align: center;">折合人民币（万元）</td>
										</tr>
										<tr>
											<td class="tab_width"> 现金：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_CASH" maxlength="16" />万元</td>
										</tr>
										<tr>
											<td class="tab_width"> 实物：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_MATERIAL" maxlength="16" />万元</td>
										</tr>
										<tr>
											<td class="tab_width"> 技术出资：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_TECHNOLOGY" maxlength="16" />万元</td>
										</tr>
										<tr>
											<td class="tab_width"> 土地使用权：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_LAND" maxlength="16" />万元</td>
										</tr>
										<tr>
											<td class="tab_width"> 股权：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_STOCK" maxlength="16" />万元</td>
										</tr>
										<tr>
											<td class="tab_width"> 其他：</td>
											<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
												name="INVESTMENT_OTHER" maxlength="16" />万元</td>
										</tr>
										<tbody id="bgholder_1_total_1">
											<tr>
												<td class="tab_width"><font class="tab_color">*</font>总转让认缴出资额（万元）：</td>
												<td><input type="text" class="tab_text " disabled="disabled"
													name="TRANSFER_CONTRIBUTIONS" maxlength="16"/>万元</td>
												<td class="tab_width"><font class="tab_color">*</font>占原注册资本比例：</td>
												<td><input type="text" class="tab_text " disabled="disabled"
													name="OLD_PROPORTION" maxlength="5"/></td>
											</tr>
											<tr>
												<td class="tab_width"><font class="tab_color">*</font>转让价格：</td>
												<td><input type="text" class="tab_text " 
													name="TRANSFER_PRICE" maxlength="16"/>万元</td>
												<td class="tab_width">付款方式：</td>
												<td>
													<input type="text" class="tab_text " 
													name="PAYMETHOD" maxlength="16"/>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
								<td style="width:100px;">
									<input type="button" class="eflowbutton" name="deleteWayHolder" onclick="deleteWayHolder('1','1');" value="删除行">
									<br>
									<br>
									<input type="button" class="eflowbutton"  name="addOneWayHolder" value="增加一行" onclick="addWayHolder();"> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th colspan="4" style="background-color: #FFD39B;">总计</th>
				</tr>
				<tr>
					<td  style="width: 170px;background: #fbfbfb;"> 出资方式</td>
					<td colspan="3" style="text-align: center;">折合人民币（万元）</td>
				</tr>
				<tr>
					<td class="tab_width"> 现金：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_CASH_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 实物：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_MATERIAL_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 技术出资：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_TECHNOLOGY_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 土地使用权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_LAND_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 股权：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_STOCK_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
				</tr>
				<tr>
					<td class="tab_width"> 其他：</td>
					<td colspan="3" style="text-align: center;"><input type="text" class="tab_text validate[[],custom[numberp6plus]]"
						name="INVESTMENT_OTHER_TOTAL" maxlength="16" onblur="calculation();"/>万元</td>
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
					<td ><input type="text" class="tab_text "
							   name="PAIDCAPITAL" maxlength="16"/>万元</td>
					<td class="tab_width"><font class="tab_color" id="jfrq" style="display:none">*</font>缴付日期：</td>
					<td ><input type="text"  class="laydate-icon "
					  name="PAYMENT_TIME"  placeholder="请选择缴款日期" /></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteBgHolder" onclick="deleteBgHolder('1');" value="删除行">
			<br>
			<br>
			<input type="button" class="eflowbutton"  name="addBgHolder" value="增加一行" onclick="addBgHolder();"> 
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="bgholdertime" style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>出资全部缴付到位期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="period" 
			name="PAYMENT_PERIOD" value="${busRecord.PAYMENT_PERIOD }"/></td>
	</tr>
</table>

