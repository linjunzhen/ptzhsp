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
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/zgjbylbx/js/ylbxcb.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css"/>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css"/>
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
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			 //初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_YBQLC_ZGXB");
			//初始化表单字段值
			if(flowSubmitObj.busRecord){
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='受理'){
					$('#insuredGrid').datagrid({
						url :''
					});
				}else{
					$("#insuredGrid").datagrid("hideColumn", "ck");
					var startDate = $('#insuredGrid').datagrid('getColumnOption', 'GZ');
					startDate.editor={};
					var endDate = $('#insuredGrid').datagrid('getColumnOption', 'CBKSRQ');
					endDate.editor={};
					$('#insuredGrid').datagrid({
						url :'zgjbylbxController.do?findXBInsuredInfo'
					});
				}
				/* initAutoTable(flowSubmitObj); */
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_YBQLC_ZGTB");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='审查'){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#baseinfo input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#zgxbinfo input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#zgxbinfo select").each(function(index){
						$(this).attr("disabled","disabled");
					});
				}
				if($("input[name='SBMC']").val()){
				}else{
					$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');					
				}
			}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    			$("#zgxbinfo").attr("style","display:none");
    		}
    	}
   	});
	
	function FLOW_SubmitFun(flowSubmitObj){
		//先判断表单是否验证通过
		var validateResult =$("#T_BSFW_PTJINFO_FORM").validationEngine("validate");
		if(validateResult){
		 	if(flowSubmitObj.busRecord){
				 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
				 if(submitMaterFileJson||submitMaterFileJson==""){
					 if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='审查'){
						 var insuredRows = $("#insuredGrid").datagrid("getSelections");
						 if(insuredRows.length==0){
				    		 alert("请至少选择一条险种信息记录!");
				    		 return null;
				    	 }else{
				    		 var insuredJson = JSON.stringify(insuredRows)
				    		 $("input[name='INSUREDINFO_JSON']").val(insuredJson);
				    		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
							 //先获取表单上的所有值
							 var formData = FlowUtil.getFormEleData("T_BSFW_PTJINFO_FORM");
							 for(var index in formData){
								 flowSubmitObj[eval("index")] = formData[index];
							 }
							 return flowSubmitObj;
				    	 }
					 }else{
						 var insuredRows = $("#insuredGrid").datagrid("getData");
			    		 var insuredJson = JSON.stringify(insuredRows);
			    		 $("input[name='INSUREDINFO_JSON']").val(insuredJson);
			    		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
						 //先获取表单上的所有值
						 var formData = FlowUtil.getFormEleData("T_BSFW_PTJINFO_FORM");
						 for(var index in formData){
							 flowSubmitObj[eval("index")] = formData[index];
						 }
						 return flowSubmitObj;
			    	 }
				 }else{
					 return null;
				 }
			 }else{
				 var insuredRows = $("#insuredGrid").datagrid("getSelections");
				 var insuredJson = JSON.stringify(insuredRows);
	    		 $("input[name='INSUREDINFO_JSON']").val(insuredJson);
	    		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_PTJINFO_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				return flowSubmitObj;
			 }
		 }else{
			return null;
		 }
	}
	
	/**
	 * 可编辑险种信息（easyui）操作方法
	 */
	var editIndex = undefined;
	//结束编辑模式
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($("#insuredGrid").datagrid("validateRow", editIndex)){
			$("#insuredGrid").datagrid("endEdit", editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	//点击行触发操作
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$("#insuredGrid").datagrid("selectRow", index).datagrid("beginEdit", index);
				editIndex = index;
			} else {
				$("#insuredGrid").datagrid("selectRow", editIndex);
			}
		}
	}
	//添加行操作
	function addParam(){
		if (endEditing()){
			$("#insuredGrid").datagrid("appendRow",{});
			editIndex = $("#insuredGrid").datagrid("getRows").length-1;
			$("#insuredGrid").datagrid("selectRow", editIndex)
					.datagrid("beginEdit", editIndex);
		}
	}
	//删除行操作
	function delParam() {
		if (editIndex == undefined){
			alert("进入行编辑状态的时候才可以删除!");
			return;
		}
		$("#insuredGrid").datagrid("cancelEdit", editIndex)
				.datagrid("deleteRow", editIndex);
		editIndex = undefined;
	}
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BSFW_PTJINFO_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/> 
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}"/>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}"/>
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}"/>
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}"/>
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}"/>
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}"/>
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON"/>
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}"/>
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}"/>
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}"/>
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}"/>
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}"/>
		<input type="hidden" name="INSUREDINFO_JSON"/>
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="baseinfo">
			<tr>
				<th colspan="4">基本信息</th>
			</tr>
			<tr>
				<td>审批事项：</td>
				<td>${serviceItem.ITEM_NAME}</td>
				<td class="tab_width">承诺时间(工作日)：</td>
				<td>${serviceItem.CNQXGZR}</td>
			</tr>
			<tr>
				<td>所属部门：</td>
				<td>${serviceItem.SSBMMC}</td>
				<td class="tab_width">审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>

			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3">
				<input type="text"
					class="tab_text validate[required]" style="width: 70%"
					maxlength="220" name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span>
				</td>
			</tr>
			<tr>
				<td class="tab_width">申报号：</td>
				<td id="EXEID"></td>
				<td></td>
				<td></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<div id="zgxbinfo">
			<%--职工续保对象--%>
			<jsp:include page="./ybqlc/zgjbylbx/zgxbinfo.jsp" />
			<%--职工续保对象 --%>
		</div>
	</form>
</body>
</html>