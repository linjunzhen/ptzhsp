<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${invoiceapplyList}" var="invoiceapply" varStatus="s">
<tbody>
	<tr style="height:10px;"></tr>	
	<tr>								
		<th><font class="syj-color2">*</font>发票种类：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="invoiceType"
			dataInterface="dictionaryService.findDatasForSelect" value="${invoiceapply.INVOICE_TYPE }"
			defaultEmptyText="请选择发票种类" name="INVOICE_TYPE" >
			</eve:eveselect>
			<script type="text/javascript">
				console.log('${invoiceapply.INVOICE_TYPE }');
			</script>
		</td>
		<th><font class="syj-color2">*</font>单份发票最高开票限额：</th>
		<td>
			<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="billingLimit"
			dataInterface="dictionaryService.findDatasForSelect" value="${invoiceapply.BILLING_LIMIT }"
			defaultEmptyText="请选择开票限额" name="BILLING_LIMIT" >
			</eve:eveselect>
			<c:if test="${s.index>0 }">
			<div style="position: relative;top:-30px;"><a href="javascript:void(0);" onclick="javascript:delInvoiceApplyDiv(this);" class="deletebtn"></a></div>
			</c:if>
		</td>
	</tr>
	<tr>
		<th><font class="syj-color2">*</font>申请数量：</th>
		<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[integerplus]" value="${invoiceapply.APPLY_NUM }"
			name="APPLY_NUM" placeholder="请输入申请数量" maxlength="3"/></td>
	</tr>
</tbody>
</c:forEach>
