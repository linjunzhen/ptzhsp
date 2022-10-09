<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="LEGAL_FORM"  >
	<div class="syj-title1 tmargin20">
		<span>法定代表人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>法定代表人姓名：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"   readonly="readonly"
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"  value="${busRecord.LEGAL_NAME}"/></td>
				<th> 职务：</th>
				<td>
				
					<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:02,03,10,11,12,20,30"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
						</eve:eveselect>
					</c:if>
					
					<c:if test="${requestParams.COMPANY_SETTYPE!='zwhz'&&busRecord.COMPANY_SETTYPE!='zwhz'}">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,02,03,10,11,12,20"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择职务" name="LEGAL_JOB" onchange="setLegalValue(this.value);"   value="${busRecord.LEGAL_JOB}">
						</eve:eveselect>
					</c:if>
				</td>
			</tr>			
			<tr>
				<th><font class="syj-color2">*</font>国别：</th>
				<td colspan="3">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
					dataInterface="dictionaryService.findDatasForCountrySelect"
					defaultEmptyText="请选择国别" name="LEGAL_COUNTRY" id="LEGAL_COUNTRY"   value="${busRecord.LEGAL_COUNTRY}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LEGAL_MOBILE"  placeholder="请输入移动电话"  value="${busRecord.LEGAL_MOBILE}"  maxlength="16"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="LEGAL_FIXEDLINE"  placeholder="请输入固定电话" value="${busRecord.LEGAL_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th> 电子邮箱：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[],custom[email]"
					name="LEGAL_EMAIL"  placeholder="请输入电子邮箱" maxlength="32" value="${busRecord.LEGAL_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEGAL_IDNO');"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"  value="${busRecord.LEGAL_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"  value="${busRecord.LEGAL_IDNO}"/></td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 tmargin20">
		<span>经办人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>指定或委托的有效期限：</th>
				<td colspan="3">
					<input type="text" class="Wdate validate[required]" id="VALIDITY_START_DATE" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'VALIDITY_END_DATE\')}'})"
					readonly="readonly" name="VALIDITY_START_DATE"  placeholder="请输入开始时间" value="${busRecord.VALIDITY_START_DATE}"  maxlength="16"/>至
					<input type="text" class="Wdate validate[required]" id="VALIDITY_END_DATE" readonly="readonly"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{M:1})||\'%y-{%M+1}-%d\'}'
					,maxDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{y:1})||\'{%y+1}-%M-%d\'}'})"
					name="VALIDITY_END_DATE"  placeholder="请输入结束时间" value="${busRecord.VALIDITY_END_DATE}"  maxlength="16"/></td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>经办人（被委托人）</br>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_NAME"  placeholder="请输入经办人（被委托人）姓名"  maxlength="8"  value="${busRecord.OPERATOR_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_MOBILE"  placeholder="请输入移动电话" value="${busRecord.OPERATOR_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th> 固定电话：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[]"
					name="OPERATOR_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.OPERATOR_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'OPERATOR_IDNO');"
					defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${busRecord.OPERATOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="OPERATOR_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.OPERATOR_IDNO}" /></td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>财务负责人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>财务负责人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_NAME"  placeholder="请输入财务负责人姓名" maxlength="8" value="${busRecord.FINANCE_NAME}"/></td>
				<th><font class="syj-color2">*</font>财务负责人移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="FINANCE_MOBILE"  placeholder="请输入财务负责人移动电话" value="${busRecord.FINANCE_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr><th> 财务负责人固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="FINANCE_FIXEDLINE"  placeholder="请输入财务负责人固定电话" value="${busRecord.FINANCE_FIXEDLINE}"  maxlength="16"/></td>
				<th> 财务负责人电子邮箱：</th>
				<td><input type="text" class="syj-input1  validate[],custom[email]"
					name="FINANCE_EMAIL"  placeholder="请输入财务负责人电子邮箱" maxlength="32" value="${busRecord.FINANCE_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'FINANCE_IDNO');" 
					defaultEmptyText="请选择证件类型" name="FINANCE_IDTYPE" value="${busRecord.FINANCE_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[equalsLegalIdno]"
					name="FINANCE_IDNO" placeholder="请输入证件号码"  maxlength="32" value="${busRecord.FINANCE_IDNO}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>核算方式：</th>
				<td colspan="3">
					<input type="radio" class="validate[required]" name="ACCOUNTING_METHOD" value="1" 
					<c:if test="${busRecord.ACCOUNTING_METHOD==1 || busRecord.ACCOUNTING_METHOD==null}"> checked="checked"</c:if>>独立核算
					<input type="radio" class="validate[required]" name="ACCOUNTING_METHOD" value="2" 
					<c:if test="${busRecord.ACCOUNTING_METHOD==2}"> checked="checked"</c:if>>非独立核算
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>适用会计制度：</th>
				<td colspan="3">
			<input type="radio" class="validate[required]" name="ACCOUNTING_SYSTEM" value="1" 
					<c:if test="${busRecord.ACCOUNTING_SYSTEM==1}"> checked="checked"</c:if>>企业会计准则
			<input type="radio" class="validate[required]" name="ACCOUNTING_SYSTEM" value="2" 
					<c:if test="${busRecord.ACCOUNTING_SYSTEM==2}"> checked="checked"</c:if>>小企业会计制度
				</td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>办税人员信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>办税人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="TAXMAN_NAME"  placeholder="请输入办税人姓名"  maxlength="8" value="${busRecord.TAXMAN_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="TAXMAN_PHONE"   placeholder="请输入移动电话" value="${busRecord.TAXMAN_PHONE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'TAXMAN_IDNO');" 
					defaultEmptyText="请选择证件类型" name="TAXMAN_IDTYPE" value="${busRecord.TAXMAN_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TAXMAN_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${busRecord.TAXMAN_IDNO}"/></td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 tmargin20">
		<span>联络员信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LIAISON_NAME"  placeholder="请输入姓名" maxlength="8" value="${busRecord.LIAISON_NAME}"/></td>
				<th> 固定电话：</th>
				<td><input type="text" class="syj-input1 validate[]"
					name="LIAISON_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.LIAISON_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LIAISON_MOBILE"  placeholder="请输入移动电话" value="${busRecord.LIAISON_MOBILE}"  maxlength="16"/></td>
				<th> 电子邮箱：</th>
				<td><input type="text" class="syj-input1 validate[],custom[email]"
					name="LIAISON_EMAIL" placeholder="请输入电子邮箱" maxlength="32"  value="${busRecord.LIAISON_EMAIL}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LIAISON_IDNO');" 
					defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="LIAISON_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.LIAISON_IDNO}"/></td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addAttorneyDiv();" class="syj-addbtn" id="addAttorney">添 加</a>
		<span>外商投资企业法律文件送达授权委托书基本信息</span>
	</div>
	<div id="attorneyDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th ><font class="syj-color2">*</font>授权人/委托方：</th>
					<td>	
						<ul class="AUTHORIZER_UL">
							
						</ul>						
					</td>		
					<th ><font class="syj-color2">*</font>被授权人：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="DELEGATEE" maxlength="62" placeholder="请输入被授权人"/></td>		
				</tr>
				<tr>
					<th ><font class="syj-color2">*</font>被授权人联系人名称/姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="DELEGATEE_CONTRACTNAME" maxlength="16" placeholder="请输入被授权人联系人名称/姓名"/></td>
					<th > 电子邮箱：</th>
					<td><input type="text" class="syj-input1 validate[[],custom[email]]"
						name="DELEGATEE_EMAIL" maxlength="32" placeholder="请输入电子邮箱"/></td>
				</tr>
				<tr>					
					<th ><font class="syj-color2">*</font>移动电话：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DELEGATEE_MOBILE" maxlength="11" placeholder="请输入移动电话"/></td>
					<th > 固定电话：</th>
					<td><input type="text" class="syj-input1 validate[]" 
						name="DELEGATEE_FIXEDLINE" maxlength="16" placeholder="请输入固定电话"/></td>
				</tr>
				<tr>					
					<th ><font class="syj-color2">*</font>被授权人地址：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DELEGATEE_ADDR" maxlength="62" placeholder="请输入被授权人地址"/></td>
					<th ><font class="syj-color2">*</font>邮政编码：</th>
					<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]" 
						name="DELEGATEE_POSTCODE" maxlength="6" placeholder="请输入邮政编码"/></td>
				</tr>
			</table>
		</div>
	</div>
</form>
