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
<script type="text/javascript">
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
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_JBJINFO_FORM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_JBJINFO_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
				}
    		}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}
    		
    	}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_JBJINFO"
		//});
		
	});
	
	
	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_JBJINFO_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_JBJINFO_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 //console.dir(flowSubmitObj);
				 return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
	
	function FLOW_TempSaveFun(flowSubmitObj){
		 //先判断表单是否验证通过
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		 //先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_BSFW_JBJINFO_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 return flowSubmitObj;
			 
	}
	
	function FLOW_BackFun(flowSubmitObj){
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
   <form id="T_BSFW_JBJINFO_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
	<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
	<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
	<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
    <%--===================重要的隐藏域内容=========== --%>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">基本信息</th>
		</tr>
		<tr>
			<td > 审批事项：</td>
			<td >${serviceItem.ITEM_NAME}</td>     
            <td class="tab_width"> 审批类型：</td>
            <td >${serviceItem.SXLX}</td>    
        </tr>
		<tr>
			<td> 所属部门：</td>
			<td >${serviceItem.SSBMMC}</td>	
			<td class="tab_width"> 承诺时间(工作日)：</td>
			<td >${serviceItem.CNQXGZR}</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
			<td colspan="3"><input type="text" class="tab_text validate[required]"
				style="width: 70%" maxlength="220"
				name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
		</tr>
		<tr>
			<td class="tab_width"> 申报号：</td>
			<td id = "EXEID"></td>
			<td></td><td></td>
		</tr>
	</table>
	
	
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./applyMaterList.jsp" />
	<%--结束引入公用上传材料界面 --%>
	<div class="tab_height" ></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="4">办理结果领取方式</th>
        </tr>
        <tr>
            <td colspan="2">
                <input type="radio" name="FINISH_GETTYPE" value="01" <c:if test="${execution.FINISH_GETTYPE=='01'||execution.FINISH_GETTYPE==null }">checked="checked"</c:if> />窗口领取
            </td>
            <td colspan="2">
                <input type="radio" name="FINISH_GETTYPE" value="02" <c:if test="${execution.FINISH_GETTYPE=='02' }">checked="checked"</c:if> />快递送达
            </td>
        </tr>
    </table>
    <div class="tab_height" ></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="2">其他信息</th>
        </tr>
        <tr>
            <td class="tab_width"> 备注：</td>
            <td><textarea name="REMARK" cols="140" rows="10" class="validate[[],maxSize[500]]"></textarea></td>
        </tr>
    </table>
	</form>
</body>
</html>
