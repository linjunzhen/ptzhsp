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
	src="<%=basePath%>/webpage/bsdt/applyform/internalStock/js/commercial.js"></script>

</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_COMMERCIAL_DOMESTIC_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="REGISTER_TYPE" value="0"/>
		<input type="hidden" name="HOLDER_JSON" />
		<input type="hidden" name="DIRECTOR_JSON" />
		<input type="hidden" name="SUPERVISOR_JSON" />
		<input type="hidden" name="MANAGER_JSON" />
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

		<%--开始引入企业基本信息界面 --%>
		<div id="jbxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_COMPANY.jsp" />
		</div>
		<%--结束引入企业基本信息界面 --%>

		<%--开始引入企业资金信息界面 --%>
		<div id="zjxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_CAPITAL.jsp" />
		</div>
		<%--结束引入企业资金信息界面 --%>

		<%--开始引入股东信息界面 --%>
		<div id="gdxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_SHAREHOLDER.jsp" />
		</div>
		<%--结束引入股东信息界面 --%>

		<%--开始引入董事信息界面 --%>
		<div id="dsxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_DIRECTOR.jsp" />
		</div>
		<%--结束引入董事信息界面 --%>
		
		<%--开始引入监事信息界面 --%>
		<div id="jsxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_SUPERVISOR.jsp" />
		</div>
		<%--结束引入监事信息界面 --%>
		
		<%--开始引入经理信息界面 --%>
		<div id="jlxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_MANAGER.jsp" />
		</div>
		<%--结束引入经理信息界面 --%>
		
		<%--开始引入法人信息界面 --%>
		<div id="frxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_LEGAL.jsp" />
		</div>
		<%--结束引入法人信息界面 --%>
		
		<%--开始引入经办人和财务负责人和办税人信息界面 --%>
		<div id="ryxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_OPERATORS.jsp" />
		</div>
		<%--结束引入经办人和财务负责人和办税人信息界面 --%>
		
		<%--开始引入联络员信息界面 --%>
		<div id="lxyxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_LIAISON.jsp" />
		</div>
		<%--结束引入联络员信息界面 --%>
		
		<%--开始引入印章信息界面 --%>
		<div id="yzxx" >		
			<c:if test="${empty execution.IS_FREE_ENGRAVE_SEAL}">
				<jsp:include page="./T_COMMERCIAL_SEAL.jsp" />
			</c:if>
			<c:if test="${!empty execution.IS_FREE_ENGRAVE_SEAL && execution.IS_FREE_ENGRAVE_SEAL==1}">
				<jsp:include page="./T_COMMERCIAL_NEW_SEAL.jsp" />
			</c:if>
		</div>
		<%--结束引入印章信息界面 --%>
		<%--开始引入邮寄信息界面 --%>
		<div  >
			<jsp:include page="./T_COMMERCIAL_EMAIL.jsp" />
		</div>
		<%--结束引入邮寄信息界面 --%>

		<%--开始引入社会保险信息界面 --%>
		<c:if test="${busRecord.ISSOCIALREGISTER == '1'}">
		<div id="sbxx" >
		<jsp:include page="./T_COMMERCIAL_INSURANCE.jsp" />
		</div>
		</c:if>
		<%--结束引入社会保险信息界面 --%>

		<%--开始引入医疗保险信息界面 --%>
		<div id="ybxx" >
			<jsp:include page="./T_COMMERCIAL_MEDICAL.jsp" />
		</div>
		<%--结束引入医疗保险信息界面 --%>

		<%--开始引入公积金登记信息界面 --%>
		<div id="gjjxx" >
			<jsp:include page="./T_COMMERCIAL_FUNDS.jsp" />
		</div>
		<%--结束引入公积金登记信息界面 --%>
		
		<%--开始引入新办企业税务套餐登记信息界面 --%>
		<div id="taxinfo" >
			<jsp:include page="./internalStock/T_COMMERCIAL_TAX.jsp" />
		</div>
		<%--结束引入新办企业税务套餐登记信息界面 --%>
		
		<%--开始引入银行开户信息界面 --%>
		<div id="bank" >
		<jsp:include page="./T_COMMERCIAL_BANK.jsp" />
		</div>
		<%--结束引入银行开户信息界面 --%>	
		
		<%--开始引入附件信息界面 --%>
		<div id="uploadFile" >
			<jsp:include page="./T_COMMERCIAL_NEW_FILE.jsp" />
		</div>
		<%--结束引入附件信息界面 --%>
		
		<%--开始引入其他信息界面 --%>
		<div id="qtxx" >
		<jsp:include page="./internalStock/T_COMMERCIAL_OTHER.jsp" />
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
		<jsp:include page="./common/T_COMMERCIAL_MULTIPLE.jsp" />
		<%--结束引入多证合一对象 --%>
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
