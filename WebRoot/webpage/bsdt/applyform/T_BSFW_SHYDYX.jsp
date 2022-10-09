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
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
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
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_SHYDYX_FORM");
    		}
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
    			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="汇总意见"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="协调结果"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXTYZ_TR").attr("style","");
    			}
    		}
    	}
		$("input[name='GCLX']").bind("click", function(){
			//获取当前勾选的值
			var currentValue =  AppUtil.getCheckRadioTagValue("GCLX","value");
			if(currentValue=="1"){
				$("#JZGCGK_TABLE").attr("style","");
				$("#TMGCGK_TABLE").attr("style","display:none;");
			}else if(currentValue=="2"){
				$("#TMGCGK_TABLE").attr("style","");
				$("#JZGCGK_TABLE").attr("style","display:none;");
			}
		});
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_SHYDYX"
		//});
		
		document.getElementById("fjbdDiv").style.display="none";
	});
	
	
	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_SHYDYX_FORM").validationEngine("validate");
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_SHYDYX_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
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
		 var validateResult =$("#T_BSFW_SHYDYX_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_SHYDYX_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	function FLOW_ViewSumOpinionFun(flowSubmitObj){
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
   <form id="T_BSFW_SHYDYX_FORM" method="post" >
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
    <input type="hidden" name="TZXMLX" value="1" />
    <%--===================重要的隐藏域内容=========== --%>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./tzjbxx.jsp" />
	<%--结束引入公用上传材料界面 --%>	
        
		
	<%--开始引入对接表信息部分:政府投资项目规划选址初步对接表 --%>
		<jsp:include page="./shtzAnnexUseland.jsp" />
	<%--结束引入对接表信息部分 --%>
	
	<%--开始引入公用上传材料界面 --%>
	
	<jsp:include page="./matterListZF.jsp" >
          <jsp:param value="T_BSFW_SHTZXMGH_FORM" name="formName"/>
    </jsp:include>
	
		
	<div class="tab_height" ></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" style="display: none;">
		<tr>
			<th colspan="4">业务操作</th>
		</tr>
		<tr id="SFXYXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>是否需要协调：</td>
			<td><eve:radiogroup typecode="SHSFXYXT" width="130px"
					fieldname="SFXYXT" defaultvalue="-1"></eve:radiogroup></td>
		</tr>
		<tr id="SFXTYZ_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>协调是否一致：</td>
			<td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
					fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
		</tr>
	</table>
	
    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
