<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<!--原企业资金信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业资金信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font><span id="investmentTab">注册资本(万元)：</span></td>
		<td><input type="text" class="tab_text validate[required]" disabled="disabled" 
			name="INVESTMENT" value="${busRecord.INVESTMENT}"/>万元</td>
		<td class="tab_width"><font class="tab_color">*</font>原币种：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="currency"
			dataInterface="extraDictionaryService.findDatasForSelect" onchange="currencyChange(this.value);"
			defaultEmptyText="请选择币种" name="CURRENCY" id="selectcurrency" value="${busRecord.CURRENCY }">
			</eve:eveselect>
		</td>
	</tr>
</table>

<!--变更企业资金信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="bgzjxx" style="display:none">
	<tr>
		<th colspan="4">拟变更后新企业资金信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font><span id="investmentTab">注册资本(万元)：(变更后)</span></td>
		<td><input type="text" class="tab_text validate[required]" 
			name="INVESTMENT_CHANGE" value="${busRecord.INVESTMENT_CHANGE}"/>万元</td>
		<td class="tab_width"><font class="tab_color">*</font>币种：(变更后)</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="currency"
			dataInterface="extraDictionaryService.findDatasForSelect" onchange="currencyChange(this.value);"
			defaultEmptyText="请选择币种" name="CURRENCY_CHANGE" id="selectcurrency" value="${busRecord.CURRENCY_CHANGE }">
			</eve:eveselect>
		</td>
	</tr>
</table>
