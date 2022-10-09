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
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_ZFKXYJBGZHPS_FORM");
    			
    			//获取表单字段权限控制信息
        		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        		 //初始化表单字段权限控制
        		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_ZFKXYJBGZHPS_FORM");
    		}
    	}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_ZFKXYJBGZHPS"
		//});
		
	});
	
	
	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_ZFKXYJBGZHPS_FORM").validationEngine("validate");
		 if(eWebEditor.getHTML()==null||eWebEditor.getHTML()==""){
			 layer.alert("请填写项目投资概况");
			 return null;
		 }
		 $("#NTZXMGK").val(eWebEditor.getHTML());
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFKXYJBGZHPS_FORM");
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
		 var validateResult =$("#T_BSFW_ZFKXYJBGZHPS_FORM").validationEngine("validate");
		 if(eWebEditor.getHTML()==null||eWebEditor.getHTML()==""){
			 parent.layer.alert("请填写项目投资概况");
			 return null;
		 }
		 $("#NTZXMGK").val(eWebEditor.getHTML());
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFKXYJBGZHPS_FORM");
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
   <form id="T_BSFW_ZFKXYJBGZHPS_FORM" method="post" >
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
    <%--===================重要的隐藏域内容=========== --%>
	<!-- <div class="tk_title_bar">
		<div class="ty_title_inner">
			<span class="ty_title_bg1"></span> <span class="ty_title_tabg">《项目申请报告+》综合(专家)评审申请表 </span>
			<span class="ty_title_bg2"></span>
		</div>
	</div> -->
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">基本信息</th>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 拟投资项目编号：</td>
			<td colspan="3">
			<input type="text" class="tab_text validate[required]" value="${busRecord.TZXMBH}" name="TZXMBH" />
			</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 拟投资项目名称：</td>
			<td><input type="text" maxlength="62"
				class="tab_text validate[required]" name="NTZXMC" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 投资人：</td>
			<td><input type="text" class="tab_text validate[required]" maxlength="14"
				name="TZR" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 项目负责人姓名：</td>
			<td><input type="text" maxlength="14"
				class="tab_text validate[required]" name="XMFZRXM" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 项目负责人电话：</td>
			<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]"
				name="XMFZRDH"  maxlength="14"/></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 项目联系人姓名：</td>
			<td><input type="text" maxlength="14"
				class="tab_text validate[required]" name="XMLXRXM" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 项目联系人电话：</td>
			<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]"
				name="XMLXRDH"  maxlength="14"/></td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">项目前期办理情况</th>
		</tr>
		<tr>			
            <td class="tab_width"><font class="tab_color">*</font> 规划选址及用地情况：</td>
			<td colspan="3"><textarea name="GHXZJYDQK" cols="140" rows="10" maxlength="1998" class="validate[required]"></textarea></td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">拟投资项目概况</th>
		</tr>
		<tr>			
             <td colspan="4" align="center">
                 <IFRAME ID="eWebEditor" NAME="eWebEditor" 
                 		SRC="plug-in/ewebeditor/ewebeditor.htm?id=NTZXMGK&style=mini500" 
				FRAMEBORDER="0" SCROLLING="no" WIDTH="95%" HEIGHT="400"></IFRAME>
                 <input type="hidden" id="NTZXMGK" name="NTZXMGK" >
             </td>
		</tr>
	</table>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./applyMaterList.jsp" />
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
