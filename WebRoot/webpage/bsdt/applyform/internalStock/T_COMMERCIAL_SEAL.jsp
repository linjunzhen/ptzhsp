<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >印章信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>印章类型（企业公章）：</td>
		<td colspan="3"><input type="checkbox"  class="checkbox" 
			name="OFFICIAL_TYPE" value="1" <c:if test="${busRecord.OFFICIAL_TYPE==1 }">checked="checked"</c:if> />企业公章</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>印章规格：</td>
		<td colspan="3">
			<input type="checkbox"  class="checkbox" name="OFFICIAL_SPEC" value="1" <c:if test="${busRecord.OFFICIAL_SPEC==1 }">checked="checked"</c:if> />40mm单位专用章
			<input type="checkbox"  class="checkbox" name="OFFICIAL_SPEC" value="0" <c:if test="${busRecord.OFFICIAL_SPEC==0 }">checked="checked"</c:if>  id="officalOther" onclick="specCheck('officalOther');"/>其他
			<input type="text" class="tab_text validate[required]" maxlength="16" disabled="disabled" id="officalOtherDetail"
				   name="OFFICIAL_SPECOTHER" value="${busRecord.OFFICIAL_SPECOTHER }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>印章材质：</td>
		<td colspan="3">
			<input type="radio" name="OFFICIAL_MATERIAL" value="01" <c:if test="${busRecord.OFFICIAL_MATERIAL=='01' }">checked="checked"</c:if> />金属铜质
			<input type="radio" name="OFFICIAL_MATERIAL" value="02" <c:if test="${busRecord.OFFICIAL_MATERIAL=='02' }">checked="checked"</c:if> />橡胶
			<input type="radio" name="OFFICIAL_MATERIAL" value="03" <c:if test="${busRecord.OFFICIAL_MATERIAL=='03' }">checked="checked"</c:if> />光敏
			<input type="radio" name="OFFICIAL_MATERIAL" value="04" <c:if test="${busRecord.OFFICIAL_MATERIAL=='04' }">checked="checked"</c:if> />牛角
			<input type="radio" name="OFFICIAL_MATERIAL" value="05" <c:if test="${busRecord.OFFICIAL_MATERIAL=='05' }">checked="checked"</c:if> />回墨
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章价格(元)：</td>
		<td colspan="3">
			<input type="text" class="tab_text" maxlength="4"
				   name="OFFICIAL_PRICE" value="${busRecord.OFFICIAL_PRICE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章类型（财务专用章）：</td>
		<td colspan="3">
			<input type="radio" name="FINANCE_TYPE" value="1" <c:if test="${busRecord.FINANCE_TYPE==1 }">checked="checked"</c:if> />是
			<input type="radio" name="FINANCE_TYPE" value="0" <c:if test="${busRecord.FINANCE_TYPE==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章规格：</td>
		<td colspan="3">
			<input type="checkbox" class="checkbox" disabled="disabled"
				name="FINANCE_SPEC" value="1" <c:if test="${busRecord.FINANCE_SPEC==1 }">checked="checked"</c:if> />38mm财务专用章
			<input type="checkbox"  class="checkbox" disabled="disabled" name="FINANCE_SPEC" value="0" <c:if test="${busRecord.FINANCE_SPEC==0 }">checked="checked"</c:if> id="financeOther" onclick="specCheck('financeOther');"/>其他
			<input type="text" class="tab_text validate[required]" maxlength="16" disabled="disabled" id="financeOtherDetail"
				   name="FINANCE_SPECOTHER" value="${busRecord.FINANCE_SPECOTHER }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章材质：</td>
		<td colspan="3">
			<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="01" <c:if test="${busRecord.FINANCE_MATERIAL=='01' }">checked="checked"</c:if> />金属铜质
			<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="02" <c:if test="${busRecord.FINANCE_MATERIAL=='02' }">checked="checked"</c:if> />橡胶
			<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="03" <c:if test="${busRecord.FINANCE_MATERIAL=='03' }">checked="checked"</c:if> />光敏
			<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="04" <c:if test="${busRecord.FINANCE_MATERIAL=='04' }">checked="checked"</c:if> />牛角
			<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="05" <c:if test="${busRecord.FINANCE_MATERIAL=='05' }">checked="checked"</c:if> />回墨
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章价格(元)：</td>
		<td colspan="3">
			<input type="text" class="tab_text" maxlength="4"
				   name="FINANCE_PRICE" value="${busRecord.FINANCE_PRICE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章类型（发票专用章）：</td>
		<td colspan="3">
			<input type="radio" class="radio" name="BILL_TYPE" value="1" <c:if test="${busRecord.BILL_TYPE==1 }">checked="checked"</c:if> />是
			<input type="radio" class="radio" name="BILL_TYPE" value="0" <c:if test="${busRecord.BILL_TYPE==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章规格：</td>
		<td colspan="3">
			<input type="checkbox" class="checkbox" disabled="disabled"
				name="BILL_SPEC" value="1"  <c:if test="${busRecord.BILL_SPEC==1 }">checked="checked"</c:if> />40x30mm发票专用章
			<input type="checkbox"  class="checkbox" disabled="disabled" name="BILL_SPEC" value="0"<c:if test="${busRecord.BILL_SPEC==0 }">checked="checked"</c:if>  id="billOther" onclick="specCheck('billOther');"/>其他
			<input type="text" class="tab_text validate[required]" maxlength="16" disabled="disabled" id="billOtherDetail"
				   name="BILL_SPECOTHER" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章材质：</td>
		<td colspan="3">
			<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="01" <c:if test="${busRecord.BILL_MATERIAL=='01' }">checked="checked"</c:if> />金属铜质
			<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="02" <c:if test="${busRecord.BILL_MATERIAL=='02' }">checked="checked"</c:if> />橡胶
			<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="03" <c:if test="${busRecord.BILL_MATERIAL=='03' }">checked="checked"</c:if> />光敏
			<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="04" <c:if test="${busRecord.BILL_MATERIAL=='04' }">checked="checked"</c:if> />牛角
			<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="05" <c:if test="${busRecord.BILL_MATERIAL=='05' }">checked="checked"</c:if> />回墨
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章价格(元)：</td>
		<td colspan="3">
			<input type="text" class="tab_text" maxlength="4"
				   name="BILL_PRICE"  value="${busRecord.BILL_PRICE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章类型（合同公章）：</td>
		<td colspan="3">
			<input type="radio" name="CONTRACT_TYPE" value="1" <c:if test="${busRecord.CONTRACT_TYPE==1 }">checked="checked"</c:if> />是
			<input type="radio" name="CONTRACT_TYPE" value="0" <c:if test="${busRecord.CONTRACT_TYPE==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章规格：</td>
		<td colspan="3">
			<input type="checkbox" class="checkbox" disabled="disabled"
				name="CONTRACT_SPEC" value="1" <c:if test="${busRecord.CONTRACT_SPEC==1 }">checked="checked"</c:if> />38mm合同公章
			<input type="checkbox"  class="checkbox" disabled="disabled" name="CONTRACT_SPEC" value="0" <c:if test="${busRecord.CONTRACT_SPEC==0 }">checked="checked"</c:if> id="contractOther" onclick="specCheck('contractOther');"/>其他
			<input type="text" class="tab_text validate[required]" maxlength="16" disabled="disabled" id="contractOtherDetail"
				   name="CONTRACT_SPECOTHER" value="${busRecord.CONTRACT_SPECOTHER }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章材质：</td>
		<td colspan="3">
			<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="01" <c:if test="${busRecord.CONTRACT_MATERIAL=='01' }">checked="checked"</c:if> />金属铜质
			<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="02" <c:if test="${busRecord.CONTRACT_MATERIAL=='02' }">checked="checked"</c:if> />橡胶
			<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="03" <c:if test="${busRecord.CONTRACT_MATERIAL=='03' }">checked="checked"</c:if> />光敏
			<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="04" <c:if test="${busRecord.CONTRACT_MATERIAL=='04' }">checked="checked"</c:if> />牛角
			<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="05" <c:if test="${busRecord.CONTRACT_MATERIAL=='05' }">checked="checked"</c:if> />回墨
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章价格(元)：</td>
		<td colspan="3">
			<input type="text" class="tab_text" maxlength="4" 
				   name="CONTRACT_PRICE"  value="${busRecord.CONTRACT_PRICE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章类型（法人印章）：</td>
		<td colspan="3">
			<input type="radio" name="LEGALSEAL_TYPE" value="1" <c:if test="${busRecord.LEGALSEAL_TYPE==1 }">checked="checked"</c:if> />是
			<input type="radio" name="LEGALSEAL_TYPE" value="0" <c:if test="${busRecord.LEGALSEAL_TYPE==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章规格：</td>
		<td colspan="3">
			<input type="checkbox" class="checkbox" disabled="disabled"
				name="LEGALSEAL_SPEC" value="1" <c:if test="${busRecord.LEGALSEAL_SPEC==1 }">checked="checked"</c:if> />38mm法人印章
			<input type="checkbox"  class="checkbox" disabled="disabled" name="LEGALSEAL_SPEC" value="0" <c:if test="${busRecord.LEGALSEAL_SPEC==0 }">checked="checked"</c:if> id="leaglSealOther" onclick="specCheck('leaglSealOther');"/>其他
			<input type="text" class="tab_text validate[required]" maxlength="16" disabled="disabled" id="leaglSealOtherDetail"
				   name="LEGALSEAL_SPECOTHER"  value="${busRecord.LEGALSEAL_SPECOTHER }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章材质：</td>
		<td colspan="3">
			<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="01" <c:if test="${busRecord.LEGALSEAL_MATERIAL=='01' }">checked="checked"</c:if> />金属铜质
			<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="02" <c:if test="${busRecord.LEGALSEAL_MATERIAL=='02' }">checked="checked"</c:if> />橡胶
			<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="03" <c:if test="${busRecord.LEGALSEAL_MATERIAL=='03' }">checked="checked"</c:if> />光敏
			<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="04" <c:if test="${busRecord.LEGALSEAL_MATERIAL=='04' }">checked="checked"</c:if> />牛角
			<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="05" <c:if test="${busRecord.LEGALSEAL_MATERIAL=='05' }">checked="checked"</c:if> />回墨
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 印章价格(元)：</td>
		<td colspan="3">
			<input type="text" class="tab_text" maxlength="4" 
				   name="LEGALSEAL_PRICE"  value="${busRecord.LEGALSEAL_PRICE }"/>
		</td>
	</tr>
</table>
