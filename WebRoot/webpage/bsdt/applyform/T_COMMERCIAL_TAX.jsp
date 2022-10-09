<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script>
	$(function(){
		$("#accountAndAgreementInfo").find("[id='DIVISION']").each(function(){		
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
<c:if test="${busRecord.ISGETBILL == '1'}">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th colspan="4" >新办企业税务套餐登记信息</th>
        </tr>        
		<tr>
			<th colspan="4"  style="background-color: #FFD39B;">纳税人资格类型登记</th>
		</tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>增值税纳税人资格类型：</td>
            <td colspan="3">
                <eve:radiogroup typecode="TAXPAYERQTYPE" width="130px" value="${busRecord.TAXPAYER_QTYPE}" fieldname="TAXPAYER_QTYPE"></eve:radiogroup>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>纳税人类别：</td>
            <td>
                <input type="text" class="tab_text validate[required]" maxlength="32"
                       name="TAXPAYER_CATEGORY"  placeholder="请输入纳税人类别" value="${busRecord.TAXPAYER_CATEGORY}"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>主营业务类别：</td>
            <td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="MainBusinessCategory"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择主营业务类别" name="BUSINESS_CATEGORY" value="${busRecord.BUSINESS_CATEGORY}">
				</eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>一般纳税人资格生效之日：</td>
            <td>
                <eve:eveselect clazz="tab_text validate[required]" dataParams="EffectiveDate"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择生效之日" name="EFFECTIVE_DATE" value="${busRecord.EFFECTIVE_DATE}">
				</eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>会计核算是否健全：</td>
            <td>
                <input type="radio" class="radio validate[required]" name="IS_SOUNDACCOUNT" value="是" <c:if test="${busRecord.IS_SOUNDACCOUNT!='否'}">checked="checked"</c:if> />是
				<input type="radio" class="radio validate[required]" name="IS_SOUNDACCOUNT" value="否" <c:if test="${busRecord.IS_SOUNDACCOUNT=='否'}">checked="checked"</c:if> />否
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>是否办理银行账户信息：</td>
            <td colspan="3">
                <input type="radio" class="radio validate[required]" name="IS_TAX_BANKACCOUNT" value="1" <c:if test="${busRecord.IS_TAX_BANKACCOUNT=='1'}">checked="checked"</c:if> />是
				<input type="radio" class="radio validate[required]" name="IS_TAX_BANKACCOUNT" value="0" <c:if test="${busRecord.IS_TAX_BANKACCOUNT!='1'}">checked="checked"</c:if> />否
            </td>
        </tr>
        <c:if test="${busRecord.IS_TAX_BANKACCOUNT=='1'}">
			<tr>
				<th colspan="4"  style="background-color: #FFD39B;">银行账号信息及委托扣款协议</th>
			</tr>
        </c:if>
	</table>
	<c:if test="${busRecord.IS_TAX_BANKACCOUNT=='1'}">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="accountAndAgreementInfo">
		<c:forEach items="${accountAndAgreementList}" var="accountAndAgreement" varStatus="s">
		<tr id="accountAndAgreement_${s.index+1 }">
			<td colspan="4">
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>账户性质：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="accountnature"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择账户性质" name="ACCOUNT_NATURE" value="${accountAndAgreement.ACCOUNT_NATURE }" >
							</eve:eveselect>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>银行开户登记证号：</td>
						<td>
							<input type="text" class="tab_text validate[required]" value="${accountAndAgreement.ACCOUNT_CERTNO }"
								name="ACCOUNT_CERTNO" maxlength="30"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>发放日期：</td>
						<td>
							<input type="text" class="laydate-icon validate[required]" readonly="readonly" οnclick="laydate({istime: false, format: 'YYYY-MM-DD'})"
								name="RELEASE_DATE" value="${accountAndAgreement.RELEASE_DATE }"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>行政区划：</td>
						<td>
							<input name="DIVISION" id="DIVISION" value="${accountAndAgreement.DIVISION }" class="easyui-combotree" style="width: 186px;height: 26px;"/>
						</td>
					</tr>			
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>银行行别：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="BankCategory"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择银行行别" name="BANK_CATEGORY" value="${accountAndAgreement.BANK_CATEGORY }">
							</eve:eveselect>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>开户银行：</td>
						<td>
							<input type="text" class="tab_text validate[required]" value="${accountAndAgreement.DEPOSIT_BANK }"
								name="DEPOSIT_BANK" maxlength="30"/>
						</td>
					</tr>			
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>账户名称：</td>
						<td>
							<input type="text" class="tab_text validate[required]" value="${accountAndAgreement.ACCOUNT_NAME }"
								name="ACCOUNT_NAME" maxlength="30"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>账号：</td>
						<td>
							<input type="text" class="tab_text validate[required]" value="${accountAndAgreement.ACCOUNT_NO }"
								name="ACCOUNT_NO" maxlength="30"/>
						</td>
					</tr>			
					<tr>
						<td class="tab_width"> 币种：</td>
						<td>
							<input type="text" class="tab_text validate[required]" value="人民币"
								name="ACCOUNT_NAME" maxlength="30"/>
						</td>
						<td class="tab_width"> 账户标识：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="ACCOUNTIDENTIFY" value="${accountAndAgreement.ACCOUNT_IDENTIFY }" 
							dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="首选缴税账号"
							name="ACCOUNT_IDENTIFY" >
							</eve:eveselect>
						</td>
					</tr>		
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>开户日期：</td>
						<td>
							<input type="text" class="laydate-icon validate[required]" readonly="readonly" οnclick="laydate({istime: false, format: 'YYYY-MM-DD'})"
								name="ACCOUNTOPEN_DATE" value="${accountAndAgreement.ACCOUNTOPEN_DATE }"/>
						</td>
						<td class="tab_width"> 委托扣款协议账户：</td>
						<td>
							<eve:eveselect clazz="tab_text" dataParams="YesOrNo" value="${accountAndAgreement.IS_ENTRUST }" 
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
								name="IS_ENTRUST" >
							</eve:eveselect>
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
			</td>
		</tr>
		</c:forEach>
		
    </table>
    </c:if>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>是否办理发票和税控设备申领：</td>
            <td colspan="3">
                <input type="radio" class="radio validate[required]" name="IS_APPLY_INVOICE" value="1" <c:if test="${busRecord.IS_APPLY_INVOICE=='1'}">checked="checked"</c:if> />是
				<input type="radio" class="radio validate[required]" name="IS_APPLY_INVOICE" value="0" <c:if test="${busRecord.IS_APPLY_INVOICE!='1'}">checked="checked"</c:if> />否
            </td>
        </tr>
    </table>
    <c:if test="${busRecord.IS_APPLY_INVOICE=='1'}">
	    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="invoiceapplyTable">
			<tr>
				<th colspan="4"  style="background-color: #FFD39B;">发票核定及申领</th>
			</tr>
	    	<c:forEach items="${invoiceapplyList}" var="invoiceapply" varStatus="s">
				<tr id="invoiceapply_${s.index+1 }">
					<td colspan="4">
						<table class="tab_tk_pro2">
							<tr>
								<td class="tab_width"><font class="tab_color">*</font>发票种类：</td>
								<td>
									<eve:eveselect clazz="tab_text validate[required]" dataParams="invoiceType"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择账户性质" name="INVOICE_TYPE" value="${invoiceapply.INVOICE_TYPE }" >
									</eve:eveselect>
								</td>
								<td class="tab_width"><font class="tab_color">*</font>单份发票最高开票限额：</td>
								<td>
									<eve:eveselect clazz="tab_text validate[required]" dataParams="billingLimit"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择单份发票最高开票限额" name="BILLING_LIMIT" value="${invoiceapply.BILLING_LIMIT }" >
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<td class="tab_width"><font class="tab_color">*</font>申请数量：</td>
								<td colspan="3">
									<input type="text" class="tab_text validate[required]" name="APPLY_NUM" value="${invoiceapply.APPLY_NUM }"/>
								</td>
							</tr>
						</table>
						<div class="tab_height2"></div>
					</td>
				</tr>
			</c:forEach>
					
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>定额发票累计领票金额：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" value="${busRecord.TOTAL_AMOUNT}"
						name="TOTAL_AMOUNT" maxlength="16"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width">税控开票设备服务厂商：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="serviceProvider" value="${busRecord.SERVICE_PROVIDER }" 
					dataInterface="dictionaryService.findDatasForSelect" 
					name="SERVICE_PROVIDER" >
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>领取方式：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="invoiceReciveway" value="${busRecord.INVOICE_RECIVEWAY }" 
					dataInterface="dictionaryService.findDatasForSelect"
					name="INVOICE_RECIVEWAY" >
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>购票人：</td>
				<td>
					<input type="text" class="tab_text validate[required]" value="${busRecord.INVOICE_BUYER}"
						name="INVOICE_BUYER" maxlength="16"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
				<td>
					<input type="text" class="tab_text validate[required]" value="${busRecord.INVOICE_BUYER_PHONE}"
						name="INVOICE_BUYER_PHONE" maxlength="16"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>购票人身份证件种类：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType" value="${busRecord.INVOICE_BUYER_IDTYPE }" 
					dataInterface="dictionaryService.findDatasForSelect" 
					name="INVOICE_BUYER_IDTYPE" >
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>购票人身份证件号码：</td>
				<td>
					<input type="text" class="tab_text validate[required]" value="${busRecord.INVOICE_BUYER_NO}"
						name="INVOICE_BUYER_NO" maxlength="18"/>
				</td>
			</tr>
	    </table>
    </c:if>
</c:if>