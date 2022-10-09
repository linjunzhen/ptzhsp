<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">合伙出资情况</th>
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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="foreignCapital" style="display: none;">
	<tr id="folding" style="display: none;">
		<td class="tab_width"><font class="tab_color">*</font>折合人民币：</td>
		<td colspan="3"><input type="text" class="tab_text"
			name="FOLDINGRMB" maxlength="16" value="${busRecord.FOLDINGRMB }"/>万元</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册资本：</td>
		<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
			name="REGISTERED_CAPITAL" maxlength="16" value="${busRecord.REGISTERED_CAPITAL }"/>万元</td>
	</tr>
	<tr id="chineseCapital" style="display: none;">
		<td class="tab_width"><font class="tab_color">*</font>中方资本：</td>
		<td><input type="text" class="tab_text validate[required,custom[numberplus]]" disabled="disabled"
			name="CHINA_CAPITAL"  maxlength="16" value="${busRecord.CHINA_CAPITAL }"/>万元</td>
		<td class="tab_width"><font class="tab_color">*</font>中方股比：</td>
		<td><input type="text" class="tab_text validate[required,custom[numberplus]]" disabled="disabled"
			name="CHINA_RATIO"  maxlength="5" value="${busRecord.CHINA_RATIO }"/></td>
	</tr>
	<tr id="foreignCapital">
		<td class="tab_width"><font class="tab_color">*</font>外方资本：</td>
		<td><input type="text" class="tab_text validate[required,custom[numberplus]]" disabled="disabled"
			name="FOREIGN_CAPITAL"  maxlength="16" value="${busRecord.FOREIGN_CAPITAL }"/>万元</td>
		<td class="tab_width"><font class="tab_color">*</font>外方股比：</td>
		<td><input type="text" class="tab_text validate[required,custom[numberplus]]" disabled="disabled"
			name="FOREIGN_RATIO"  maxlength="5" value="${busRecord.FOREIGN_RATIO }"/></td>
	</tr>
</table>
