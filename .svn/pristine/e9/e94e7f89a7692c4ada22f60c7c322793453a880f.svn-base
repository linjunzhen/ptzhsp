<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
			request.setAttribute("nowDate", nowDate);
			String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			request.setAttribute("nowTime", nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,autocomplete"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcDjxx.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/cqzxdj/js/cqzxdj.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/cqzxdj/js/dateTimeUtil.js"></script>
<SCRIPT type="text/javascript">
$(function() {
	//设置权利类型默认为'其他权利'
	$('#accept_QLLX').val('22');	
	//设置定着物类型默认为'层、套间',不可编辑
	$('#accept_DZWLX').prop("disabled", "disabled");
	$('#accept_DZWLX').val('DZWLX_1');	
	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate = true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
	dealItems = "," + dealItems + ",";

	if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
		initAutoTable(flowSubmitObj); //初始化注销明细（json格式数据）
		if($("#zxmxDetail input[name='FJ']").val()){
			$("#zxmxDetail input[name='FJ']").val('${busRecord.FJ}');
		}
	}
	if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '登簿'){
	    // 受理环节不展示登簿流程的操作按钮
		//隐藏确认登簿按钮
		$("#DBTR").hide();
		//隐藏登记审核
		$("#djshxx").hide();
		$("#SLRY").attr("disabled","disabled");
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始'){
			$("#zxjbxx").hide();
			$("#zxmx").hide();
		}
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理'){
			$("#SLRY").val('${sessionScope.curLoginUser.fullname}');
		}	
	}else{
	    //申请人信息、受理信息设置为不可修改
		setUnchangeable();
		// 登簿环节初始化公告开始结束时间
		setDjshxx();
	}
	
	if (flowSubmitObj) {
		//获取表单字段权限控制信息
		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		var exeid = flowSubmitObj.EFLOW_EXEID;
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		//初始化表单字段权限控制
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_DYQZXDJ_FORM");
		//初始化表单字段值
		if (flowSubmitObj.busRecord) {
			// 结束环节处理
			if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
				$("#djshxx").css("display", "block");
				$("#T_BDCQLC_DYQZXDJ_FORM input").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
				$("#T_BDCQLC_DYQZXDJ_FORM select").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
				$("#T_BDCQLC_DYQZXDJ_FORM textarea").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
			}
	
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_DYQZXDJ_FORM");
			if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') { //非草稿状态设置为不可编辑
				if ($("input[name='SBMC']").val()) {
				} else {
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
				}
			} else if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
				$("#userinfo_div input").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
				$("#userinfo_div select").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
				$("#baseinfo input").each(function(index) {
					$(this).attr("disabled", "disabled");
				});
			}
			var isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
			//设置文件是否合规
			if (isAuditPass == "-1") {
				$("input:radio[name='isAuditPass'][value='-1']").attr("checked", true);
			}
		} else {
			$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
			//持证类型默认分别持证
			$("select[name='TAKECARD_TYPE']").val("0");
			//申请人证件类型默认为'身份证'
			$("select[name='SQRZJLX']").val("SF");
			$("input[name='SQRSFZ']").addClass(",custom[vidcard]");
		}
	}
	//表单内按钮是否有权限操作
	isButtonAvailable();	
});

// 确认登簿后自动回填 注销人、注销时间
function doBooking() {
	if($("#ZXYY").val()==''){
		parent.art.dialog({
			content: "注销原因为空，请添加！",
			icon:"warning",
			ok: true
		});
		return;
	}
	$("#DBR").val('${sessionScope.curLoginUser.fullname}');
	$("#DJSJ").val('${nowTime}');
};

</SCRIPT>	
</head>
  <body>
   	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_CQZXDJ_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" /> 
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE"  value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="OBLIGEE_NAME" value="${busRecord.OBLIGEE_NAME}" />
		<input type="hidden" name="OBLIGEE_IDNUM" value="${busRecord.OBLIGEE_IDNUM}" />
		<input type="hidden" name="OB_ID" value="${busRecord.OB_ID}" />
		<input type="hidden" name="runstatus1" value="${execution.RUN_STATUS}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> 
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" /> 
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" /> 
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		
		<input type="hidden" name="ZXMX_JSON" />
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
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
				<td id ="EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入申报对象--%>
		<jsp:include page="./bdcqlc/cqzxdj/applyUserInfo.jsp" />
		<%--结束引入申报对象 --%>
		<%-- 开始引入受理信息 --%>
		<jsp:include page="./bdcqlc/cqzxdj/T_BDCQZXDJ_ACCEPTINFO.jsp" />
		<%-- 结束引入受理信息 --%>
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
   		
   		<%-- 开始引入注销基本信息 --%>
   		<jsp:include page="./bdcqlc/cqzxdj/T_BDCQZXDJ_ZXJBXX.jsp"/>
   		<%-- 结束引入注销基本信息 --%>
   		
   		<%-- 开始引入注销明细 --%>
   			<jsp:include page="./bdcqlc/cqzxdj/T_BDCQZXDJ_ZXMX.jsp"/>
   		<%-- 结束引入注销明细 --%>	
   	
   		<%--开始登簿审核信息--%>
		<jsp:include page="./bdcqlc/cqzxdj/bdcDjshxx.jsp" />
		<%--开始登簿审核信息--%>
   	</form>
   	
  </body>
  <OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
