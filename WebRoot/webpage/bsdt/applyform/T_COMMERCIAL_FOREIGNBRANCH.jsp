<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/js/foreignbranch.js"></script>	

<script type="text/javascript">
	function downloadApply(){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApply.do?exeId="+exeId
			+"&itemCode="+itemCode;
	}
	
	function setPreapprovalPass(value){
		if (value === "1") {			
			$("input[name='PREAPPROVAL_NUM']").attr("disabled",false);
			$("input[name='PREAPPROVAL_NUM']").addClass(" validate[required]");
		} else {			
			$("input[name='PREAPPROVAL_NUM']").attr("disabled",true); 
			$("input[name='PREAPPROVAL_NUM']").removeClass(" validate[required]");
			$("input[name='PREAPPROVAL_NUM']").val('');
		}
	}
	
	$(function(){
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			if(flowSubmitObj.busRecord){
				//初始化是否已通过名称自主选用
				setPreapprovalPass(flowSubmitObj.busRecord.IS_PREAPPROVAL_PASS);
			}
		}
		
		//点击是否已通过名称自主选用触发事件
		$("input[name='IS_PREAPPROVAL_PASS']").on("click", function(event) {
			setPreapprovalPass(this.value);
		});
		
	});
	
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_COMMERCIAL_BRANCH" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="REGISTER_TYPE" value="0"/>
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td > 牵头部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
			<tr id="jjgl" style="display:none;">
				<td style="width: 170px;background: #fbfbfb;"> 1+N证合一事项：</td>
				<td colspan="3" id="relatedItem"></td>
			</tr>
		</table>

		<div id="jbxx">
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">企业基本信息</th>
			</tr>			
			<tr id="unicode" <c:if test="${busRecord.SOCIAL_CREDITUNICODE==null || busRecord.SOCIAL_CREDITUNICODE==''}">style="display: none;"</c:if>>
				<td class="tab_width"><font class="tab_color">*</font>社会信用代码：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" 
						name="SOCIAL_CREDITUNICODE" maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>分支机构名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="BRANCH_NAME" value="${busRecord.BRANCH_NAME }" style="width:854px;"/>
				</td>
			</tr>
<%--			<tr>--%>
<%--				<td class="tab_width"><font class="tab_color">*</font>是否已通过名称自主选用：</td>--%>
<%--				<td>--%>
<%--					<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--					<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--				</td>--%>
<%--				<td class="tab_width"> 名称自主选用文号：</td>--%>
<%--				<td><input type="text"  maxlength="32"--%>
<%--					name="PREAPPROVAL_NUM"  class="tab_text"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--			</tr>--%>
			<%-- <tr>
				<td class="tab_width"><font class="tab_color">*</font>公司类型：</td>
				<td>
					<eve:eveselect clazz="tab_text1 validate[required]" dataParams="branchType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择企业类型" name="COMPANY_TYPE" value="${busRecord.COMPANY_TYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>分公司名称预先核准文号/注册号/统一社会信用代码：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="PREAPPROVAL_NUM" value="${busRecord.PREAPPROVAL_NUM }"/>
				</td>
			</tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>营业场所：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="BUSINESS_PLACE" value="${busRecord.BUSINESS_PLACE }" style="width:854px;"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
				<td><input type="text" class="tab_text"
					name="PHONE" maxlength="16" placeholder="请输入联系电话"  value="${busRecord.PHONE}"/></td>
				
				<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
				<td><input type="text" class="tab_text validate[required,custom[chinaZip]]" 
					name="POSTCODE" maxlength="6"  value="${busRecord.POSTCODE}"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>从业人数：</td>
				<td>
					<input type="text" class="tab_text validate[required,custom[numberplus]]"
						name="EMPLOYED_NUM" maxlength="4"  value="${busRecord.EMPLOYED_NUM}"/>
				</td>
				<td colspan="2"></td>
				<%-- <td class="tab_width"><font class="tab_color">*</font>副本数：</td>
				<td>
					<input type="text" class="tab_text validate[required,custom[numberplus]]"
						name="LICENSE_COPY_NUM" maxlength="4"  value="${busRecord.LICENSE_COPY_NUM}"/>
				</td> --%>
			</tr>
			<%-- <tr>
				<td class="tab_width"><font class="tab_color">*</font>电子营业执照数：</td>
				<td>
					<input type="text" class="tab_text validate[required,custom[numberplus]]"
						name="LICENSE_NUM" maxlength="4"  value="${busRecord.LICENSE_NUM}"/>
				</td>
				<td colspan="2"></td>
			</tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>生产经营地址：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="PREMISES" value="${busRecord.PREMISES }" style="width:854px;"/>
				</td>
			</tr>
			<%-- <tr>
				<td class="tab_width"><font class="tab_color">*</font>经营期限：</td>
				<td>
					<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE==1 }">checked="checked"</c:if> >年
					<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0 }">checked="checked"</c:if> >长期
				</td>
				<td class="tab_width"> 经营期限年数：</td>
				<td><input type="text" class="tab_text validate[[],custom[integerplus]]" 
					name="BUSSINESS_YEARS" value="${busRecord.BUSSINESS_YEARS }"/></td>
			</tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
						class="eve-textarea"
					 	style="width:854px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
					 	<input type="button" value="选择" class="eve-button" style="vertical-align:unset;" onclick="showSelectJyfwNew();">
					 	<!-- <a href="javascript:showSelectJyfw();" class="btn1">选 择</a> -->
				</td>
			</tr>
			<%-- <tr>
				<td class="tab_width"> 投资行业：</td>
				<td colspan="3">
					<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
						class="eve-textarea"
					 	style="width:854px;height:100px;" >${busRecord.INVEST_INDUSTRY }</textarea>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 行业编码：</td>
				<td colspan="3"><input type="text" class="tab_text " style="width:854px;" readonly="readonly"
					name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE }"/></td>
			</tr> --%>
			<%-- <tr>
				<td class="tab_width"> 备注：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:854px;" name="SCOPE_REMARK" value="${busRecord.SCOPE_REMARK }"/>
				</td>
			</tr> --%>
		</table>
		</div>
		
		<div id="subordinateInfo">
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >隶属企业</th>
			</tr>
			<%-- <tr>
				<td class="tab_width"><font class="tab_color">*</font>隶属企业所在地：</td>
				<td colspan="3" >
					<input type="radio" name="SUBORDINATE_LOCATION" value="1" <c:if test="${busRecord.SUBORDINATE_LOCATION==1 }">checked="checked"</c:if> >本省
					<input type="radio" name="SUBORDINATE_LOCATION" value="0" <c:if test="${busRecord.SUBORDINATE_LOCATION==0 }">checked="checked"</c:if> >外省市
				</td>
			</tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:854px;" name="SUBORDINATE_NAME" value="${busRecord.SUBORDINATE_NAME }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>注册号/统一社会信用代码：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					name="SUBORDINATE_CREDITUNICODE" value="${busRecord.SUBORDINATE_CREDITUNICODE }"/></td>
				<%-- <td class="tab_width"> 企业注册资金（万元）</td>
				<td><input type="text" class="tab_text validate[required]"
					name="SUBORDINATE_CAPITAL" value="${busRecord.SUBORDINATE_CAPITAL }"/></td> --%>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业名称（英文）：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="SUBORDINATE_NAME_EN" value="${busRecord.SUBORDINATE_NAME_EN }"/></td>
				<td class="tab_width"> 企业类型：</td>
				<td>
					<eve:eveselect clazz="input-dropdown sel validate[required]" onchange="setValidate(this.value);"
									dataParams="FOREIGNBRANCH" value="${busRecord.SUBORDINATE_TYPE}"
									dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
									defaultEmptyText="====选择企业类型====" name="SUBORDINATE_TYPE"></eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>隶属企业登记机关：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:854px;" name="SUBORDINATE_REGISTRATION" value="${busRecord.SUBORDINATE_REGISTRATION}"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>营业期限：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="SUBORDINATE_BUSSINESSTIME" value="${busRecord.SUBORDINATE_BUSSINESSTIME}"/>
				</td>
			</tr>
			<%-- <tr id="outprovince" style="display: none;">
				<td class="tab_width"><font class="tab_color">*</font>所属省、市、县：</td>
				<td colspan="3">
					<eve:eveselect clazz="tab_text validate[required]" dataParams="-1,1"
					dataInterface="commercialSetService.findDatasForSelectFromDevbase"
					defaultEmptyText="请选择省" name="SUBORDINATE_PROVINCE" value="${busRecord.SUBORDINATE_PROVINCE }">
					</eve:eveselect>
					
					<eve:eveselect clazz="tab_text validate[required]" dataParams="${busRecord.SUBORDINATE_PROVINCE },2"
					dataInterface="commercialSetService.findDatasForSelectFromDevbase"
					defaultEmptyText="请选择市" name="SUBORDINATE_CITY" value="${busRecord.SUBORDINATE_CITY }">
					</eve:eveselect>
					
					<eve:eveselect clazz="tab_text validate[required]" dataParams="${busRecord.SUBORDINATE_CITY },3"
					dataInterface="commercialSetService.findDatasForSelectFromDevbase"
					defaultEmptyText="请选择区（县）" name="SUBORDINATE_AREA" value="${busRecord.SUBORDINATE_AREA }">
					</eve:eveselect>
				</td>
			</tr> --%>
		</table>
		</div>
		<%--开始引入经办人和财务负责人和办税人信息界面 --%>
		<div id="ryxx" >
		<jsp:include page="./T_COMMERCIAL_OPERATORS.jsp" />
		</div>
		<%--结束引入经办人和财务负责人和办税人信息界面 --%>
		
		<%--开始引入联络员信息界面 --%>
		<div id="lxyxx" >
		<jsp:include page="./T_COMMERCIAL_LIAISON.jsp" />		
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>委托有效期限：</td>
				<td colspan="3"><input type="text" class="laydate-icon" readonly="readonly"
					name="LVALIDITY_START_DATE"  value="${busRecord.LVALIDITY_START_DATE }"/>至
					<input type="text" class="laydate-icon" readonly="readonly"
					name="LVALIDITY_END_DATE"  value="${busRecord.LVALIDITY_END_DATE }"/></td>
			</tr>
		</table>
		</div>
		<%--结束引入联络员信息界面 --%>
		
		<div id="fzrxx" >
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >负责人信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td><input type="text" class="tab_text" 
					name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME }"/></td>				
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text"
					name="LEGAL_EMAIL" maxlength="32" value="${busRecord.LEGAL_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
				<td class="tab_width"> 固定电话：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_FIXEDLINE" value="${busRecord.LEGAL_FIXEDLINE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_IDNO"  value="${busRecord.LEGAL_IDNO }"/></td>
			</tr>
		</table>
		</div>
		
		<%--开始引入印章信息界面 --%>
		<div id="yzxx" >
		<jsp:include page="./T_COMMERCIAL_SEAL.jsp" />
		</div>
		<%--结束引入印章信息界面 --%>

		<%--开始引入社会保险信息界面 --%>
		<div id="sbxx" >
		<jsp:include page="./T_COMMERCIAL_INSURANCE.jsp" />
		</div>
		<%--结束引入社会保险信息界面 --%>
		
		<%--开始引入银行开户信息界面 --%>
		<div id="bank" >
		<jsp:include page="./T_COMMERCIAL_BANK.jsp" />
		</div>
		<%--结束引入银行开户信息界面 --%>	
		
		<%--开始引入其他信息界面 --%>
		<div id="qtxx" >
		<jsp:include page="./T_COMMERCIAL_OTHER.jsp" />
		</div>
		<%--结束引入其他信息界面 --%>	
		
		<%--开始引入商事材料下载--%>
		<div id="clxx" >
		<jsp:include page="./commercialMaterList.jsp" />
		</div>
		<%--结束引入商事材料下载 --%>
		
		<%--开始引入关联事项材料 --%>
		<div id="relatedItemMater"></div>
		<%--结束引入关联事项材料 --%>
		
		<%--开始引入多证合一对象--%>
		<jsp:include page="./common/T_COMMERCIAL_MULTIPLE_BRANCH.jsp" />
		<%--结束引入多证合一对象 --%>
		
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
