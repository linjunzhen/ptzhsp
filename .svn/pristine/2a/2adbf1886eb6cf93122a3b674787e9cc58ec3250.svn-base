<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript">
	function downloadApply(){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApply.do?exeId="+exeId
			+"&itemCode="+itemCode;
	}
</script>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >银行开户申请信息
		<a href="javascript:void(0);" onclick="javascript:downloadApply();" class="syj-addbtn" style="float: right;">下载申请书</a>
		</th>
	</tr>
	<tr>
		<td class="tab_width"> 存款人名称：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			name="DEPOSITOR"  value="${busRecord.DEPOSITOR }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 地址：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			name="DEPOSITOR_ADDR"  value="${busRecord.DEPOSITOR_ADDR }"/></td>
	</tr>
	<tr>		
		<td class="tab_width"> 邮编：</td>
		<td><input type="text" class="tab_text"
			name="DEPOSITOR_POSTCODE"  value="${busRecord.DEPOSITOR_POSTCODE }"/></td>
		<td class="tab_width"> 地区代码：</td>
		<td ><input type="text" class="tab_text"
			name="AREA_CODE"  value="${busRecord.AREA_CODE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 法定代表人：</td>
		<td ><input type="text" class="tab_text"
			name="LEGAL_NAME"  value="${busRecord.LEGAL_NAME }"/>
		</td>
		<td class="tab_width"> 注册资金(万元)：</td>
		<td ><input type="text" class="tab_text"
			name="INVESTMENT"  value="${busRecord.REGISTERED_CAPITAL }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 证件类型：</td>
		<td >
			<eve:eveselect clazz="tab_text" dataParams="DocumentType" 
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name=""  id="legalPersonIDType" value="${busRecord.LEGAL_IDTYPE}">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 证件号码：</td>
		<td ><input type="text" class="tab_text"
			name="LEGAL_IDNO"  value="${busRecord.LEGAL_IDNO }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" id="businessScope"
				class="eve-textarea" readonly="readonly"
				style="width:740px;height:100px;"  >${busRecord.BUSSINESS_SCOPE}</textarea>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 行业分类：</th>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			id="INDUSTRY_CATEGORY_CDOE"  value="${busRecord.INDUSTRY_CATEGORY}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 存款人类型：</td>
		<td ><input type="text" class="tab_text"
			name="DEPOSITOR_TYPE"  value="${busRecord.DEPOSITOR_TYPE }"/>
		</td>
		<td class="tab_width"> 证明文件类型：</td>
		<td ><input type="text" class="tab_text"
			name="PROOFDOC"  value="${busRecord.PROOFDOC }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">控股股东或实际控制人名称：</th>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			id="CONTROLLER"  value="${busRecord.CONTROLLER}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 账户性质：</td>
		<td ><input type="text" class="tab_text"
			name="ACCOUNT_NATURE"  value="${busRecord.ACCOUNT_NATURE }"/>
		</td>
		<td class="tab_width"> 开户银行代码：</td>
		<td ><input type="text" class="tab_text"
			name="BANK_CODE"  value="${busRecord.BANK_CODE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 开户银行名称：</th>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			id="BANK_NAME"  value="${busRecord.BANK_NAME}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 账户名称：</th>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			id="ACCOUNT_NAME"  value="${busRecord.ACCOUNT_NAME}"/>
		</td>
	</tr>
</table>
