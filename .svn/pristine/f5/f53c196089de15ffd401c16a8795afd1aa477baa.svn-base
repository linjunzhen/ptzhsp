<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<script type="text/javascript">
    $(function(){
    	//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "3000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	if(flowSubmitObj){
    		//获取表单字段权限控制信息
    		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"LeaveInfoForm");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"LeaveInfoForm");
    		}
    	}
    });

	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#LeaveInfoForm").validationEngine("validate");
		 if(validateResult){
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("LeaveInfoForm");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 //定义目标合并任务节点
			 flowSubmitObj.EFLOW_JOINPRENODENAMES = "人力审批,上级审批";
			 //定义定制开发回调函数
			 //flowSubmitObj.EFLOW_CALLBACKFN = "myFun";
			 return flowSubmitObj;
		 }else{
			 return null;
		 }
	}
	
	function FLOW_TempSaveFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#LeaveInfoForm").validationEngine("validate");
		 if(validateResult){
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("LeaveInfoForm");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 //定义定制开发回调函数
			 //flowSubmitObj.EFLOW_CALLBACKFN = "myFun";
			 return flowSubmitObj;
		 }else{
			 return null;
		 }
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	function myFun(flowVarsJson){
		alert("hello");
	    console.log(flowVarsJson);
		//var flowVars = JSON2.parse(flowVarsJson);
		
	}
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="LeaveInfoForm" method="post"
		action="leaveInfoController.do?saveOrUpdate">
		<div id="LeaveInfoFormDiv">
			<!--==========隐藏域部分开始 ===========-->

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">请假人：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"  name="PERSON_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">请假类型：</span>
						 <eve:eveselect clazz="eve-input validate[required]" dataParams="ExpressWay"
					         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择请假类型"
					         name="LEAVE_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
						
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">请假日期：</span>
						<input type="text" style="width:150px;float:left;"
						class="laydate-icon validate[required]"
						onclick="laydate({istime: false, format: 'YYYY-MM-DD'})"
						name="LEAVE_DATE" /><font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">开始时间：</span>
						<input type="text" style="width:150px;float:left;"
						class="laydate-icon validate[required]"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
						name="BEGIN_TIME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">结束时间：</span>
						<input type="text" style="width:150px;float:left;"
						class="laydate-icon validate[required]"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
						name="END_TIME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">性别：</span>
						<eve:radiogroup typecode="sex" width="130px" fieldname="SEX"
						defaultvalue="1"></eve:radiogroup>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">原因说明</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">快递方式：</span>
					    <eve:checkboxgroup typecode="ExpressWay"  width="370" fieldname="KDFS">
					    </eve:checkboxgroup>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">请假原因：</span>
						<textarea rows="5" cols="5"
							class="eve-textarea validate[required]" style="width: 400px"
							name="LEAVE_REASON"></textarea><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>


		</div>
	</form>

</body>

