<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,layer,artdialog,swfupload"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/fjfda/util/FdaAppUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/fjfda/website/css/syj-style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/fjfda/css/style.css" />
	
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />

<script type="text/javascript">
	$(function() {
		FdaAppUtil.initDefaultValidateConfig();
		var flowSubmitObj = FlowUtil.getFlowObj();
    	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
    	dealItems = "," + dealItems + ",";
    	if(flowSubmitObj){
			//获取表单字段权限控制信息
    		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
    		var exeid = flowSubmitObj.EFLOW_EXEID;
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_FJFDA_SPJYXK_JBXX_FROM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    		    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_FJFDA_SPJYXK_JBXX_FROM");    		    
    		    var EFLOW_VARS = getFlowVarsObj();
    		    if(EFLOW_VARS){
    		    	var EFLOW_BUSRECORD = EFLOW_VARS.EFLOW_BUSRECORD;
    		    	var legalRepresentative = EFLOW_VARS.legalRepresentative;
					var clinetMap = EFLOW_VARS.clinetMap;
					if (EFLOW_BUSRECORD) {
						//初始化基本信息表单字段值
						FlowUtil.initFormFieldValue(EFLOW_BUSRECORD, "BASEINFO_FORM");
					}
					if(legalRepresentative){
						//初始化人员信息表单字段值
						FlowUtil.initFormFieldValue(legalRepresentative, "PERSONNEL_FORM");
					} 
					if(clinetMap){
						//初始化委托人信息表单字段值
						FlowUtil.initFormFieldValue(clinetMap, "CLIENT_FORM");
					} 
    		    }    		    
    		    if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					if($("input[name='SBMC_LABEL']").val()){
					}else{
						$("input[name='SBMC_LABEL']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}
    		}else{
    			$("input[name='SBMC_LABEL']").val("-"+'${serviceItem.ITEM_NAME}');
    		}
		}
		//初始化材料列表
		AppUtil.initNetUploadMaters({
			busTableName:"T_FJFDA_SPJYXK_JBXX"
		});		
	});
	//提交流程按钮
	function FLOW_SubmitFun(EFLOW_VARS) {
		//验证表单是否合法
		var valiateTabForm = FdaAppUtil.validateTabForm();
		if (valiateTabForm) {
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
		    if(submitMaterFileJson||submitMaterFileJson==""){
		    	EFLOW_VARS.EFLOW_SUBMITMATERFILEJSON = submitMaterFileJson;
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		    }else{
		    	return null;
		    }
			//获取流程变量对象
			//先获取表单上的所有值
			var forms = $("form[id]");
			forms.each(function() {
				var formId = $(this).attr("id");
				var formData = FlowUtil.getFormEleData(formId);
				for ( var index in formData) {
					EFLOW_VARS[eval("index")] = formData[index];
				}
			});
			//法人
			var FRXM = $("input[name='FRXM']").val();
			var FRYDDH = $("input[name='FRYDDH']").val();
			if(FRXM&&FRXM!=""){
				EFLOW_VARS.EFLOW_MSGRECEIVER = FRXM;
			}
			if(FRYDDH&&FRYDDH!=""){
				EFLOW_VARS.SQRSJH = FRYDDH;
			}
			//委托人
			var WTDLR = $("input[name='WTDLR']").val();
			var WTRYDDH = $("input[name='WTRYDDH']").val();
			if(WTDLR&&WTDLR!=""){
				EFLOW_VARS.EFLOW_MSGRECEIVER = WTDLR;
			}
			if(WTRYDDH&&WTRYDDH!=""){
				EFLOW_VARS.SQRSJH = WTRYDDH;
			}
			EFLOW_VARS.SQRMC = $("input[name='JYZMC']").val();
			EFLOW_VARS.FacilityEquipmentJson =  getFacilityEquipmentJson();
			EFLOW_VARS.TechnicalPersonnelJson = getTechnicalPersonnelJson();
			EFLOW_VARS.PractitionersJson = getPractitionersJson();
			EFLOW_VARS.SBMC = $("input[name='SBMC_LABEL']").val();
			//获取申报的材料信息
			/* var submitMaterResult = FdaAppUtil.getWindowSubmitMaterFileJson();
		    if(submitMaterResult.submitMaterFileJson||submitMaterResult.submitMaterFileJson==""){
		    	EFLOW_VARS.EFLOW_SUBMITMATERFILEJSON = submitMaterResult.submitMaterFileJson;
		    }
		    if(!submitMaterResult.validate){
		    	return;
		    } */
		    var SHXYDMSFZHM = $("input[name='SHXYDMSFZHM']").val();
			SHXYDMSFZHM = FdaAppUtil.trim(SHXYDMSFZHM);
			var bool = SHXYDMSFZHM.indexOf("*");
			if(bool>-1){
				parent.art.dialog({
					content : "请输入完整信用代码,信用代码不允许有'*'符号! ",
					lock: true,
					cancel:false,
					icon : "error",
					ok : function(){
						//$("input[name='SHXYDMSFZHM']").focus();
					}
				});
				return ; 
			}
			addOther(EFLOW_VARS);
		    return EFLOW_VARS;
		}
	}
	//暂存流程按钮
	function FLOW_TempSaveFun(EFLOW_VARS) {
		var EFLOW_VARS = FlowUtil.getFlowObj();
		//先获取表单上的所有值
		var forms = $("form[id]");
		forms.each(function() {
			var formId = $(this).attr("id");
			var formData = FlowUtil.getFormEleData(formId);
			for ( var index in formData) {
				EFLOW_VARS[eval("index")] = formData[index];
			}
		});
		EFLOW_VARS.EFLOW_ISTEMPSAVE = "1";
		//法人
		var FRXM = $("input[name='FRXM']").val();
		var FRYDDH = $("input[name='FRYDDH']").val();
		if(FRXM&&FRXM!=""){
			EFLOW_VARS.EFLOW_MSGRECEIVER = FRXM;
		}
		if(FRYDDH&&FRYDDH!=""){
			EFLOW_VARS.SQRSJH = FRYDDH;
		}
		//委托人
		var WTDLR = $("input[name='WTDLR']").val();
		var WTRYDDH = $("input[name='WTRYDDH']").val();
		if(WTDLR&&WTDLR!=""){
			EFLOW_VARS.EFLOW_MSGRECEIVER = WTDLR;
		}
		if(WTRYDDH&&WTRYDDH!=""){
			EFLOW_VARS.SQRSJH = WTRYDDH;
		}
		EFLOW_VARS.SQRMC = $("input[name='JYZMC']").val();
		EFLOW_VARS.FacilityEquipmentJson = getFacilityEquipmentJson();
		EFLOW_VARS.TechnicalPersonnelJson = getTechnicalPersonnelJson();
		EFLOW_VARS.PractitionersJson = getPractitionersJson();
		EFLOW_VARS.SBMC = $("input[name='SBMC_LABEL']").val();
	    addOther(EFLOW_VARS);
		//获取申报的材料信息		
	    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	    if(submitMaterFileJson||submitMaterFileJson==""){
	    	EFLOW_VARS.EFLOW_SUBMITMATERFILEJSON = submitMaterFileJson;
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	    }
	    return EFLOW_VARS;
	}
	
	function addOther(EFLOW_VARS){
		EFLOW_VARS.BSYHLX = "2";
		EFLOW_VARS.JBR_MOBILE = EFLOW_VARS.SQRSJH;
		EFLOW_VARS.JBR_NAME = EFLOW_VARS.SQRMC;
	}
	
	function getFlowVarsObj(){
		var EFLOW_VARS_JSON = $("input[name='EFLOW_VARS_JSON']").val();
		//将其转换成JSON对象
		var EFLOW_VARS = $.parseJSON(EFLOW_VARS_JSON);
		//var json = JSON.stringify(EFLOW_VARS);
		return EFLOW_VARS;
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
</script>
</head>

<body style="background: unset;">
	<input type="hidden" name="EFLOW_VARS_JSON" value="${EFLOW_VARS_JSON}" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
   <form id="T_FJFDA_SPJYXK_JBXX_FROM" method="post">	
   <%--===================重要的隐藏域内容=========== --%>
	<input type="hidden" name="uploadUserId"
		value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
		name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
	<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
	<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
	<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
		type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
		type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
	<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
		type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
		type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="BELONG_DEPTNAME"
		value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
		value="${serviceItem.SXLX}" /> 
	<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
	<!-- 对接对应省平台的事项代码 -->
	<input type="hidden" name="FJFDA_ITEM_CODE" value="001XK00101" />
	<%--===================重要的隐藏域内容=========== --%> 
	<div class="eui-slidebox" style="width:90%">
	    <div>
	    	<div class="syj-title1"><span>事项信息</span></div>
			<table cellpadding="0" cellspacing="1" class="syj-table2">
				<tr>
					<th>审批事项：</th>
					<td>${serviceItem.ITEM_NAME}</td>
					<th class="tab_width">承诺时间(工作日)：</th>
					<td>${serviceItem.CNQXGZR}</td>
				</tr>
				<tr>
					<th>所属部门：</th>
					<td>${serviceItem.SSBMMC}</td>
					<th class="tab_width">审批类型：</th>
					<td>${serviceItem.SXLX}</td>
				</tr>
	
				<tr>
					<th class="tab_width"><font class="tab_color">*</font> 申报名称：</th>
					<td colspan="3"><input type="text"
						class="tab_text validate[required]" style="width: 70%"
						maxlength="220" name="SBMC_LABEL" value="${busRecord.SBMC_LABEL}"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
					</span>
					</td>
				</tr>
				<tr>
					<th class="tab_width">申报号：</th>
					<td id="EXEID" colspan="3"></td> 
				</tr>
			</table>
		</div>
		<div>
			<!-- 开始引入基本信息界面 -->
			<jsp:include page="./baseInfoForm.jsp"></jsp:include>
			<!-- 结束引入基本信息界面 -->
		</div>
		<div>
			<!-- 开始引入人员信息界面 -->
			<jsp:include page="./personnelForm.jsp"></jsp:include>
			<!-- 结束引入人员信息界面 -->
		</div>
		<div>
			<!-- 开始引入食品安全管理人员信息界面 -->
			<jsp:include page="../common/securityPersonnelForm.jsp"></jsp:include>
			<!-- 结束引入食品安全管理人员信息界面 -->
		</div>
		<div>
			<!-- 开始引入从业人员信息界面 -->
			<jsp:include page="../common/employeeForm.jsp"></jsp:include>
			<!-- 结束引入从业人员信息界面 -->
		</div>
	    <div>
			<!-- 开始引入食品安全设施设备信息界面 -->
			<jsp:include page="../common/facilityEquipment.jsp"></jsp:include>
			<!-- 结束引入食品安全设施设备信息界面 -->
		</div>
		<div>
			<!-- 开始引入委托人信息界面 -->
			<jsp:include page="../common/clientForm.jsp"></jsp:include>
			<!-- 结束引入委托人信息界面 -->
		</div>
		<div>
			<%--开始引入公用上传材料界面 --%>
			<jsp:include page="../common/applyMaterList.jsp">
				<jsp:param value="2" name="isWebsite" />
			</jsp:include>
			<%--结束引入公用上传材料界面 --%>
		</div>
	</div>
  </form>
</body>
</html>