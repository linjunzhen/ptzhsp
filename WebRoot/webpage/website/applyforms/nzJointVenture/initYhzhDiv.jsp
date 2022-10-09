<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){
		$("#YhzhTable tbody").find("[id='DIVISION']").each(function(){		
			$(this).combotree({
	           url: "dicTypeController/loadSelectTree.do?parentCode=XZQHDM35",
	           valueField: "text",
	           textField: "text",
	           lines: true,
	           required: true,
	           onChange: function (node) {					                                
	
	           }
	            
	    	});
	    	
    		$(this).parent().find("span input").attr("placeholder","请选择行政区划");
    	});
	})
</script>
<c:forEach items="${accountAndAgreementList}" var="accountAndAgreement" varStatus="s">
<tbody class="addBox">
	<tr>
		<th><font class="syj-color2">*</font>账户性质：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="accountnature"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择账户性质" name="ACCOUNT_NATURE" value="${accountAndAgreement.ACCOUNT_NATURE }" >
			</eve:eveselect>
		</td>
		<th><font class="syj-color2">*</font>银行开户登记证号：</th>
		<td><input type="text" class="syj-input1 validate[required]"  value="${accountAndAgreement.ACCOUNT_CERTNO }"
			name="ACCOUNT_CERTNO" placeholder="请输入银行开户登记证号" maxlength="32"/>
			<c:if test="${s.index>0 }">
			<div style="position: relative;top:-35px;"><a href="javascript:void(0);" onclick="javascript:delYhzhDiv(this);" class="deletebtn"></a></div>
			</c:if>
		</td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>发放日期：</th>
		<td>
			<input type="text" class="syj-input1 Wdate validate[required]"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
				readonly="readonly" name="RELEASE_DATE" value="${accountAndAgreement.RELEASE_DATE }" placeholder="请选择发放日期" />
		</td>
		<th><font class="syj-color2">*</font>行政区划：</th>
		<td>			
            <input name="DIVISION" id="DIVISION" value="${accountAndAgreement.DIVISION }" class="syj-input1 easyui-combotree"/>            
		</td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>银行行别：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="BankCategory"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择银行行别" name="BANK_CATEGORY" value="${accountAndAgreement.BANK_CATEGORY }">
			</eve:eveselect>
		</td>
		<th><font class="syj-color2">*</font>开户银行：</th>
		<td><input type="text" class="syj-input1 validate[required]" value="${accountAndAgreement.DEPOSIT_BANK }"
			name="DEPOSIT_BANK" placeholder="请输入开户银行" maxlength="32"/></td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>账户名称：</th>
		<td>
			<input type="text" class="syj-input1 validate[required]" value="${accountAndAgreement.ACCOUNT_NAME }"
			name="ACCOUNT_NAME" placeholder="请输入账户名称" maxlength="32"/>
		</td>
		<th><font class="syj-color2">*</font>账号：</th>
		<td><input type="text" class="syj-input1 validate[required]" value="${accountAndAgreement.ACCOUNT_NO }" 
			name="ACCOUNT_NO" placeholder="请输入账号" maxlength="32"/></td>
	</tr>
	<tr>
		<th> 币种：</th>
		<td>
			<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
			name="ACCOUNT_CURRENCY" value="人民币" maxlength="32"/>
		</td>
		<th> 账户标识：</th>
		<td><eve:eveselect clazz="input-dropdown w280" dataParams="ACCOUNTIDENTIFY" value="${accountAndAgreement.ACCOUNT_IDENTIFY }" 
			dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="首选缴税账号"
			name="ACCOUNT_IDENTIFY" >
			</eve:eveselect>
		</td>
	</tr>				
	<tr>
		<th><font class="syj-color2">*</font>开户日期：</th>
		<td>
			<input type="text" class="syj-input1 Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
				readonly="readonly" name="ACCOUNTOPEN_DATE" value="${accountAndAgreement.ACCOUNTOPEN_DATE }" placeholder="请选择开户日期" />
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
</c:forEach>
