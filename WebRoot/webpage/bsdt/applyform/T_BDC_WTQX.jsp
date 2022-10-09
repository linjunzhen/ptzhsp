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
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<%-- <script	src="<%=basePath%>/webpage/bsdt/applyform/estate/js/t_bdc_wt.js"></script> --%>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData()
</SCRIPT>
    <style>
		.sel{
			border:solid 1px red;
		}
    </style>
<script type="text/javascript">
$(function(){
    $("input[name='JBR_NAME']").removeAttr('readonly');
    $("input[name='JBR_ZJHM']").removeAttr('readonly');
    var itemName=$("input[name='ITEM_NAME']").val();
    //初始化验证引擎的配置
    $.validationEngine.defaults.autoHidePrompt = true;
    $.validationEngine.defaults.promptPosition = "topRight";
    $.validationEngine.defaults.autoHideDelay = "2000";
    $.validationEngine.defaults.fadeDuration = "0.5";
    $.validationEngine.defaults.autoPositionUpdate =true;
    var flowSubmitObj = FlowUtil.getFlowObj();
    if(flowSubmitObj){
        //获取表单字段权限控制信息
        var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        var exeid = flowSubmitObj.EFLOW_EXEID;
        $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
        //初始化表单字段权限控制
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_WTQX_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_WTQX_FORM");
            if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始' ){
                $("#T_BDC_WTQX_FORM input").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                $("#T_BDC_WTQX_FORM select").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                
                if($("input[name='SBMC']").val()){
                }else{
                    $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+itemName);
                }
            }else if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
				$("#userinfo_div input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#userinfo_div select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#baseinfo input").each(function(index){
					$(this).attr("disabled","disabled");
				});
			}
        }else{
            $("input[name='SBMC']").val("-"+itemName);
        }

        var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
        if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ){
            if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
                $("input[name='JBR_NAME']").removeAttr('readonly');
            }
        }
    }

    //初始化材料列表
  	// AppUtil.initNetUploadMaters({
  		// busTableName:"T_BDC_WTQX"
  	// });
});

function FLOW_SubmitFun(flowSubmitObj){
    var flowSubmitObj = FlowUtil.getFlowObj();
    /*var runstatus1=$("input[name='runstatus1']").val();
    if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES="开始"&&runstatus1!="1"){
        
    }*/

    //先判断表单是否验证通过
    var validateResult =$("#T_BDC_WTQX_FORM").validationEngine("validate");
    if(validateResult){
    	setGrBsjbr();//个人不显示经办人设置经办人的值
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
        }else{
            return null;
        }   	
        //先获取表单上的所有值
        var formData = FlowUtil.getFormEleData("T_BDC_WTQX_FORM");
        for(var index in formData){
            flowSubmitObj[eval("index")] = formData[index];
        }
        flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
        return flowSubmitObj;
    }else{
        return null;
    }
}

function FLOW_TempSaveFun(flowSubmitObj){
    //先判断表单是否验证通过
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BDC_WTQX_FORM");
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;

}

function FLOW_BackFun(flowSubmitObj){
    return flowSubmitObj;
}

function setFileNumber(serialNum){
	/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
    var enterprise = '${execution.SQJG_CODE}';
    var idCard = '${execution.SQRSFZ}';
// 	alert(idCard + "," + enterprise);
    var fileNum;
    if (enterprise != ""){
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
    } else {
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
    }
    $("#fileNumber").val(fileNum);
}

</script>
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
	<form id="T_BDC_WTQX_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId"
			value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
			name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
			<input type="hidden" name="OBLIGEE_NAME" value="${busRecord.OBLIGEE_NAME}" />
			<input type="hidden" name="OBLIGEE_IDNUM" value="${busRecord.OBLIGEE_IDNUM}" />
			<input type="hidden" name="OB_ID" value="${busRecord.OB_ID}" />
			<input type="hidden" name="runstatus1" value="${execution.RUN_STATUS}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
			type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
			type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME"
			value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
			value="${serviceItem.SXLX}" /> <input type="hidden"
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
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
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		
		<%--开始引入不动产委托书--%>
		<jsp:include page="./estate/T_ESTATE_WTQX_ACCEPT.jsp" />
		<%--结束引入不动产委托书 --%>
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>	
		
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
