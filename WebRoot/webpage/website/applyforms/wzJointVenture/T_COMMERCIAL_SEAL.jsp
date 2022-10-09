<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="SEAL_FORM"  >
	<div class="syj-title1 tmargin20">
		<span>企业公章</span>
	</div>
	<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>印章类型：</th>
			<td><input type="checkbox"  class="checkbox  validate[required]" 
				name="OFFICIAL_TYPE" value="1" <c:if test="${busRecord.OFFICIAL_TYPE==1}"> checked="checked"</c:if>/>企业公章</td>				
			<th><font class="syj-color2">*</font>印章规格：</th>
			<td>
				<input type="radio" class="validate[required]" name="OFFICIAL_SPEC" value="1" 
				<c:if test="${busRecord.OFFICIAL_SPEC==1}"> checked="checked"</c:if>/>40mm单位专用章
				<input type="radio" name="OFFICIAL_SPEC" value="0"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_SPEC==0}"> checked="checked"</c:if>/>其他
			</td>
		</tr>
		<tr style="display: none;" id="OFFICIAL_SPECOTHER">
			<th><font class="syj-color2">*</font>规格大小（mm）：</th>
			<td colspan="3">
				<input type="text" class="syj-input1" name="OFFICIAL_SPECOTHER" maxlength="8" 
				style="width: 240px;" value="${busRecord.OFFICIAL_SPECOTHER}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>印章材质：</th>
			<td>
				<input type="radio" name="OFFICIAL_MATERIAL" value="01"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_MATERIAL=='01'}"> checked="checked"</c:if>/>金属铜质
				<input type="radio" name="OFFICIAL_MATERIAL" value="02"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_MATERIAL=='02'}"> checked="checked"</c:if>/>橡胶
				<input type="radio" name="OFFICIAL_MATERIAL" value="03"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_MATERIAL=='03'}"> checked="checked"</c:if>/>光敏
				<input type="radio" name="OFFICIAL_MATERIAL" value="04"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_MATERIAL=='04'}"> checked="checked"</c:if>/>牛角
				<input type="radio" name="OFFICIAL_MATERIAL" value="05"  class="validate[required]"
				<c:if test="${busRecord.OFFICIAL_MATERIAL=='05'}"> checked="checked"</c:if>/>回墨
			</td>
			<th>印章价格(元)：</th>
			<td>
				<input type="text"  class="syj-input1 inputBackgroundCcc" readonly="readonly" name="OFFICIAL_PRICE"
				maxlength="4" style="width: 240px;" value="${busRecord.OFFICIAL_PRICE}"/>
			</td>
		</tr>
	</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>财务专用章</span>
	</div>
	<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th> 印章类型：</th>
			<td>
				<input type="radio" name="FINANCE_TYPE" value="1" <c:if test="${busRecord.FINANCE_TYPE==1}"> checked="checked"</c:if>/>是
				<input type="radio" name="FINANCE_TYPE" value="0" <c:if test="${busRecord.FINANCE_TYPE!=1}"> checked="checked"</c:if>/>否
			</td>
			<th> 印章规格：</th>
			<td>
				<input type="radio" name="FINANCE_SPEC"  disabled="disabled" value="1" 
				<c:if test="${busRecord.FINANCE_SPEC==1}"> checked="checked"</c:if>/>38mm财务专用章									
				<input type="radio" name="FINANCE_SPEC"  disabled="disabled" value="0" 
				<c:if test="${busRecord.FINANCE_SPEC==0}"> checked="checked"</c:if>/>其他
			</td>
		</tr>
		<tr style="display: none;" id="FINANCE_SPECOTHER">
			<th><font class="syj-color2">*</font>印章规格大小（mm）：</th>
			<td colspan="3">
				<input type="text" class="syj-input1" name="FINANCE_SPECOTHER" maxlength="8"  style="width: 240px;" 
				value="${busRecord.FINANCE_SPECOTHER}"/>
			</td>
		</tr>
		<tr>
			<th> 印章材质：</th>
			<td>
				<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="01"  
				<c:if test="${busRecord.FINANCE_MATERIAL=='01'}"> checked="checked"</c:if>/>金属铜质
				<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="02"  
				<c:if test="${busRecord.FINANCE_MATERIAL=='02'}"> checked="checked"</c:if>/>橡胶
				<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="03"  
				<c:if test="${busRecord.FINANCE_MATERIAL=='03'}"> checked="checked"</c:if>/>光敏
				<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="04"  
				<c:if test="${busRecord.FINANCE_MATERIAL=='04'}"> checked="checked"</c:if>/>牛角
				<input type="radio" disabled="disabled" name="FINANCE_MATERIAL" value="05"  
				<c:if test="${busRecord.FINANCE_MATERIAL=='05'}"> checked="checked"</c:if>/>回墨
			</td>
			<th>印章价格(元)：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="FINANCE_PRICE" maxlength="4" style="width: 240px;"  value="${busRecord.FINANCE_PRICE}"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>发票专用章</span>
	</div>
	<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th> 印章类型：</th>
			<td>
				<input type="radio" name="BILL_TYPE" value="1" <c:if test="${busRecord.BILL_TYPE==1}"> checked="checked"</c:if>/>是
				<input type="radio" name="BILL_TYPE" value="0" <c:if test="${busRecord.BILL_TYPE!=1}"> checked="checked"</c:if>/>否
			</td>
			<th> 印章规格：</th>
			<td>
				<input type="radio" name="BILL_SPEC" disabled="disabled" value="1"   
				<c:if test="${busRecord.BILL_SPEC=='1'}"> checked="checked"</c:if>/>40x30mm发票专用章				
				<input type="radio" name="BILL_SPEC" disabled="disabled" value="0"   
				<c:if test="${busRecord.BILL_SPEC=='0'}"> checked="checked"</c:if>/>其他
			</td>
		</tr>
		<tr style="display: none;" id="BILL_SPECOTHER">
			<th><font class="syj-color2">*</font>印章规格大小（mm）：</th>
			<td colspan="3">
				<input type="text" class="syj-input1" name="BILL_SPECOTHER" maxlength="8"  style="width: 240px;"
				value="${busRecord.BILL_SPECOTHER}"/>
			</td>
		</tr>
		<tr>
			<th> 印章材质：</th>
			<td>
				<!--<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="01" />金属铜质-->
				<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="02"    
				<c:if test="${busRecord.BILL_MATERIAL=='02'}"> checked="checked"</c:if>/>橡胶
				<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="03"    
				<c:if test="${busRecord.BILL_MATERIAL=='03'}"> checked="checked"</c:if>/>光敏
				<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="04"    
				<c:if test="${busRecord.BILL_MATERIAL=='04'}"> checked="checked"</c:if>/>牛角
				<input type="radio" disabled="disabled" name="BILL_MATERIAL" value="05"    
				<c:if test="${busRecord.BILL_MATERIAL=='05'}"> checked="checked"</c:if>/>回墨
			</td>
			<th>印章价格(元)：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="BILL_PRICE" maxlength="4" style="width: 240px;"  value="${busRecord.BILL_PRICE}"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>合同公章</span>
	</div>
	<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th> 印章类型：</th>
			<td>
				<input type="radio" name="CONTRACT_TYPE" value="1" <c:if test="${busRecord.CONTRACT_TYPE==1}"> checked="checked"</c:if>/>是
				<input type="radio" name="CONTRACT_TYPE" value="0" <c:if test="${busRecord.CONTRACT_TYPE!=1}"> checked="checked"</c:if>/>否
			</td>
			<th> 印章规格：</th>
			<td>
				<input type="radio" name="CONTRACT_SPEC" disabled="disabled" value="1"     
				<c:if test="${busRecord.CONTRACT_SPEC=='1'}"> checked="checked"</c:if>/>38mm合同公章						
				<input type="radio" name="CONTRACT_SPEC" disabled="disabled" value="0"     
				<c:if test="${busRecord.CONTRACT_SPEC=='0'}"> checked="checked"</c:if>/>其他
			</td>
		</tr>
		<tr style="display: none;" id="CONTRACT_SPECOTHER">
			<th><font class="syj-color2">*</font>印章规格大小（mm）：</th>
			<td colspan="3">
				<input type="text" class="syj-input1" name="CONTRACT_SPECOTHER" maxlength="8"  style="width: 240px;"  value="${busRecord.CONTRACT_SPECOTHER}"/>
			</td>
		</tr>
		<tr>
			<th> 印章材质：</th>
			<td>
				<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="01"      
				<c:if test="${busRecord.CONTRACT_MATERIAL=='01'}"> checked="checked"</c:if>/>金属铜质
				<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="02"      
				<c:if test="${busRecord.CONTRACT_MATERIAL=='02'}"> checked="checked"</c:if>/>橡胶
				<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="03"      
				<c:if test="${busRecord.CONTRACT_MATERIAL=='03'}"> checked="checked"</c:if>/>光敏
				<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="04"      
				<c:if test="${busRecord.CONTRACT_MATERIAL=='04'}"> checked="checked"</c:if>/>牛角
				<input type="radio" disabled="disabled" name="CONTRACT_MATERIAL" value="05"      
				<c:if test="${busRecord.CONTRACT_MATERIAL=='05'}"> checked="checked"</c:if>/>回墨
			</td>
			<th>印章价格(元)：</th>
			<td>
				<input type="text"  class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="CONTRACT_PRICE" maxlength="4" style="width: 240px;"  value="${busRecord.CONTRACT_PRICE}"/>
			</td>
		</tr>
	</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>法人印章</span>
	</div>
	<div style="position:relative;">	
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th> 印章类型：</th>
			<td>
				<input type="radio" name="LEGALSEAL_TYPE" value="1"
				<c:if test="${busRecord.LEGALSEAL_TYPE==1}"> checked="checked"</c:if>/>是
				<input type="radio" name="LEGALSEAL_TYPE" value="0"
				<c:if test="${busRecord.LEGALSEAL_TYPE!=1}"> checked="checked"</c:if>/>否
			</td>
			<th> 印章规格：</th>
			<td>
				<input type="radio" name="LEGALSEAL_SPEC" disabled="disabled" value="1"       
				<c:if test="${busRecord.LEGALSEAL_SPEC=='1'}"> checked="checked"</c:if>/>18*18mm法人公章						
				<input type="radio" name="LEGALSEAL_SPEC" disabled="disabled" value="0"       
				<c:if test="${busRecord.LEGALSEAL_SPEC=='0'}"> checked="checked"</c:if>/>其他
			</td>
		</tr>
		<tr style="display: none;" id="LEGALSEAL_SPECOTHER">
			<th><font class="syj-color2">*</font>印章规格大小（mm）：</th>
			<td colspan="3">
				<input type="text" class="syj-input1" name="LEGALSEAL_SPECOTHER" maxlength="8"  style="width: 240px;"  value="${busRecord.LEGALSEAL_SPECOTHER}"/>
			</td>
		</tr>
		<tr>
			<th> 印章材质：</th>
			<td>
				<!--<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="01" />金属铜质-->
				<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="02"        
				<c:if test="${busRecord.LEGALSEAL_MATERIAL=='02'}"> checked="checked"</c:if>/>橡胶
				<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="03"        
				<c:if test="${busRecord.LEGALSEAL_MATERIAL=='03'}"> checked="checked"</c:if>/>光敏
				<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="04"        
				<c:if test="${busRecord.LEGALSEAL_MATERIAL=='04'}"> checked="checked"</c:if>/>牛角
				<!--<input type="radio" disabled="disabled" name="LEGALSEAL_MATERIAL" value="05" />回墨-->
			</td>
			<th>印章价格(元)：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="LEGALSEAL_PRICE" maxlength="4" style="width: 240px;"  value="${busRecord.LEGALSEAL_PRICE}"/>
			</td>
		</tr>
	</table>
	</div>
	
</form>
