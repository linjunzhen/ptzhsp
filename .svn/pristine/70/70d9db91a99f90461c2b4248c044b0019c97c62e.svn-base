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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>

<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script  type="text/javascript">

$(function(){
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
        if(flowSubmitObj.EFLOW_DEFKEY=="jhmyjzxjfkdxssjsh"){
        
        }
        //初始化表单字段权限控制	
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_DZZZJBXX_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
        	//初始化信息
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_DZZZJBXX_FORM");
        }
    }
});

function FLOW_SubmitFun(flowSubmitObj){
    var flowSubmitObj = FlowUtil.getFlowObj();
    //先判断表单是否验证通过
    var validateResult =$("#T_BSFW_DZZZJBXX_FORM").validationEngine("validate");
    if(validateResult){
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
        }else{
            return null;
        }   	
        //先获取表单上的所有值
        var formData = FlowUtil.getFormEleData("T_BSFW_DZZZJBXX_FORM");
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
    var formData = FlowUtil.getFormEleData("T_BSFW_DZZZJBXX_FORM");
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;
}
</script>


</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
    
   <form id="T_BSFW_DZZZJBXX_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <%--===================重要的隐藏域内容=========== --%>
	<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
	<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
	<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
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
    <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" /> <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
	<input type="hidden" name="WT_JSON" />
	<input type="hidden" name="ST_JSON" />
	<input type="hidden" name="BD_JSON" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="YW_ID" value="${busRecord.YW_ID}"/>
    <input type="hidden" name="CERTIFICATEIDENTIFIERFILEID" value="${busRecord.CERTIFICATEIDENTIFIERFILEID}"/>
    <input type="hidden" name="CERTIFICATEIDENTIFIERFILEPATH" value="${busRecord.CERTIFICATEIDENTIFIERFILEPATH}"/>
    <input type="hidden" name="filePath" value="${busRecord.FILE_PATH}"/>
    <%--===================重要的隐藏域内容=========== --%>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
		<tr>
			<th colspan="4">证照信息</th>
		</tr>
		<tr>
			<td class="tab_width">持证人:</td>
			<td>
			  <input type="text" class="tab_text" name="CZR" value="${busRecord.CZR}"/>
			</td>
			<td class="tab_width">持证主体:</td>
			<td>
			  <input type="text" class="tab_text" name="CZZT" value="${busRecord.CZZT}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">证照编号:</td>
			<td>
			  <input type="text" class="tab_text" name="ZZBH" value="${busRecord.ZZBH}"/>
			</td>
			<td class="tab_width">标题:</td>
			<td>
			  <input type="text" class="tab_text" name="BT" value="${busRecord.BT}"/>
			</td>
		</tr>
		<tr>	
			<td class="tab_width">发证时间:</td>
			<td>
			  <input type="text" class="tab_text" name="ISSUEDDATE" value="${busRecord.ISSUEDDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
			</td>
 			<td class="tab_width">有效时间：</td>
			<td>
			  <input type="text" class="tab_text" name="EFFECTIVEDATE" value="${busRecord.EFFECTIVEDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">过期时间:</td>
			<td>
			  <input type="text" class="tab_text" name="EXPIRINGDATE" value="${busRecord.EXPIRINGDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
			</td>
		</tr>
		
	</table>
	
  	<%--开始引入公用上传材料界面 --%>
 	<jsp:include page="./matterListZF.jsp" >
        <jsp:param value="T_BSFW_DZZZJBXX_FORM" name="formName"/>
     </jsp:include>
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	
	<div class="tab_height"></div>
	</form>
</body>
</html>


