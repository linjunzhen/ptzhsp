<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<tbody class="addBox">
	<tr>
		<th><font class="syj-color2">*</font>账户性质：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="accountnature"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择账户性质" name="ACCOUNT_NATURE" >
			</eve:eveselect>
		</td>
		<th><font class="syj-color2">*</font>银行开户登记证号：</th>
		<td><input type="text" class="syj-input1 validate[required]" 
			name="ACCOUNT_CERTNO" placeholder="请输入银行开户登记证号" maxlength="32"/>
			<div style="position: relative;top:-35px;"><a href="javascript:void(0);" onclick="javascript:delYhzhDiv(this);" class="deletebtn"></a></div>
		</td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>发放日期：</th>
		<td>
			<input type="text" class="syj-input1 Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
				readonly="readonly" name="RELEASE_DATE"  placeholder="请选择发放日期" />
		</td>
		<th><font class="syj-color2">*</font>行政区划：</th>
		<td>			
            <input name="DIVISION" id="DIVISION" class="syj-input1 easyui-combotree"/>            
		</td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>银行行别：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="BankCategory"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择账户性质" name="BANK_CATEGORY" >
			</eve:eveselect>
		</td>
		<th><font class="syj-color2">*</font>开户银行：</th>
		<td><input type="text" class="syj-input1 validate[required]" 
			name="DEPOSIT_BANK" placeholder="请输入开户银行" maxlength="32"/></td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>账户名称：</th>
		<td>
			<input type="text" class="syj-input1 validate[required]" 
			name="ACCOUNT_NAME" placeholder="请输入账户名称" maxlength="32"/>
		</td>
		<th><font class="syj-color2">*</font>账号：</th>
		<td><input type="text" class="syj-input1 validate[required]" 
			name="ACCOUNT_NO" placeholder="请输入账号" maxlength="32"/></td>
	</tr>
	<tr>
		<th> 币种：</th>
		<td>
			<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
			name="ACCOUNT_CURRENCY" value="人民币" maxlength="32"/>
		</td>
		<th> 账户标识：</th>
		<td><eve:eveselect clazz="input-dropdown w280" dataParams="ACCOUNTIDENTIFY"
			dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="首选缴税账号"
			name="ACCOUNT_IDENTIFY" >
			</eve:eveselect>
		</td>
	</tr>				
	<tr>
		<th><font class="syj-color2">*</font>开户日期：</th>
		<td>
			<input type="text" class="Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
				readonly="readonly" name="ACCOUNTOPEN_DATE"  placeholder="请选择开户日期" />
		</td>
		<th> 委托扣款协议账户：</th>
		<td>			
			<eve:eveselect clazz="input-dropdown w280" dataParams="YesOrNo" value="${accountAndAgreement.IS_ENTRUST }" 
				dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
				name="IS_ENTRUST" >
			</eve:eveselect>
		</td>
	</tr>
</tbody>