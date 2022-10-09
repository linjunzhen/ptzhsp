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
	src="<%=basePath%>/webpage/bsdt/applyform/change/js/commercial.js"></script>

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
			<input type="hidden" name="CHANGEREGS" value="${busRecord.CHANGEREGS}" />
			<input type="hidden" name="RECORDS"  value="${busRecord.RECORDS}"/>
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

		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="gslxtable">
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" style="width:740px;"
						name="COMPANY_NAME" maxlength="64" value="${busRecord.COMPANY_NAME }"/>
				</td>
			</tr>
		</table>

		<%--开始引入企业基本信息界面 --%>
		<div id="jbxx" >
		<jsp:include page="./change/T_COMMERCIAL_COMPANY.jsp" />
		</div>
		<%--结束引入企业基本信息界面 --%>
			<%--开始变更项目信息界面 --%>
			<div id="ryxx" >
				<jsp:include page="./change/T_COMMERCIAL_ITEM.jsp" />
			</div>
			<%--结束变更项目信息界面 --%>
			<%--开始引入联络员信息界面 --%>
			<div id="lxyxx" >
				<jsp:include page="./change/T_COMMERCIAL_LIAISON.jsp" />
			</div>
		<%--开始引入指定或委托代理人信息信息界面 --%>
		<div id="ryxx" >
		<jsp:include page="./change/T_COMMERCIAL_OPERATORS.jsp" />
		</div>
		<%--结束引入指定或委托代理人信息信息界面 --%>

			<div>
				<jsp:include page="../../bsdt/applyform/change/applyMaterList.jsp">
					<jsp:param value="1" name="applyType" />
					<jsp:param value="0" name="isWebsite" />
				</jsp:include>
			</div>

		<%--开始引入商事材料下载--%>
		<div id="clxx" >
		<jsp:include page="./change/commercialMaterList.jsp" />
		</div>
		<%--结束引入商事材料下载 --%>
		
		<%--开始引入关联事项材料 --%>
		<div id="relatedItemMater"></div>
		<%--结束引入关联事项材料 --%>
		

		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
