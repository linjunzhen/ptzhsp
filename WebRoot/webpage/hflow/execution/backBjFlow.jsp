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

<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
    href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    
	$(function() {
		AppUtil.initWindowForm("BackBjFlowForm", function(form, valid) {
			if (valid) {
				var bjcllbJson = getbjcllbJson();
				var isNeedBj = isRequiredBjclxx();
				//判断需补件材料信息，补件要求是否填写
				var isPass = true;
				if(isNeedBj){
				    var bjcllbObj = JSON2.parse(bjcllbJson);
					for(var index in bjcllbObj){
			            //获取材料补件意见
			            var BJYJ = bjcllbObj[index].BJYJ;
			            var BJMC =  bjcllbObj[index].MATER_NAME;
			            var SFBJ = bjcllbObj[index].SFBJ;
			            if( SFBJ=="1" &&(BJYJ==null || BJYJ ==undefined || BJYJ=="")){
		                	isPass = false;
		                	break;
						}
			        }
				}
				if(!isPass){
					parent.art.dialog({
						content : "需要补件的材料请填写补件要求！",
						icon : "error",
						ok : true
					});
					return false;
				}
		        
	            $("input[name='BJCLLB']").val(bjcllbJson);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	var formData = FlowUtil.getFormEleData("SHYJTABLE");
		    	if(formData.EFLOW_HANDLE_OPINION){
		    		flowVars.EFLOW_HANDLE_OPINION = formData.EFLOW_HANDLE_OPINION;
		    	}
		    	if(formData.BJCLLB){
		    		flowVars.BJCLLB = formData.BJCLLB;
		    	}
		    	var backNodeNames = $("#backNodeNames").val().split(",");
		    	flowVars.EFLOW_NEXTSTEPS = null;
		    	for(var index in backNodeNames){
		    		var taskNodeName = backNodeNames[index];
		    		//获取勾选的审核人
		    		var selectedUserAccounts = FlowUtil.getCheckBoxValues(taskNodeName);
		    		if(selectedUserAccounts){
		    			if(!flowVars.EFLOW_NEXTSTEPS){
		    				flowVars.EFLOW_NEXTSTEPS = {};
		    			}
		    			var handlersArray = new Array();
		    			var selectedUserNames = FlowUtil.getCheckBoxTagValues(taskNodeName,"text");
		    			var nextStepAssignerType = FlowUtil.getCheckBoxTagValues(taskNodeName,"nextassignertype");
		    			var handlerAccounts = selectedUserAccounts.split(",");
		    			var handlerNames = selectedUserNames.split(",");
		    			for(var i = 0;i<handlerAccounts.length;i++){
		    				var handler = {};
		    				handler.nextStepAssignerCode = handlerAccounts[i];
		    				handler.nextStepAssignerName = handlerNames[i];
		    				if(nextStepAssignerType){
		    					handler.nextStepAssignerType = nextStepAssignerType;
		    					flowVars.APPLY_STATUS = nextStepAssignerType;
		    				}else{
		    					handler.nextStepAssignerType = "1";
		    					flowVars.APPLY_STATUS = "1";
		    				}
		    				if(handler.nextStepAssignerCode){
		    					handlersArray.push(handler);
		    				}
		    			}
		    			flowVars.EFLOW_NEXTSTEPS[taskNodeName] = handlersArray;
		    		}
		    	}
		    	if(!flowVars.EFLOW_NEXTSTEPS){
		    		alert("请选择退回环节审核人!");
		    		return;
		    	}
		    	//将审核人转换成JSON
		    	var EFLOW_NEXTSTEPSJSON =  JSON2.stringify(flowVars.EFLOW_NEXTSTEPS);
		    	flowVars.EFLOW_NEXTSTEPS= null;
		    	if(EFLOW_NEXTSTEPSJSON!=null&&EFLOW_NEXTSTEPSJSON!="undefined"){
		    		flowVars.EFLOW_NEXTSTEPSJSON = EFLOW_NEXTSTEPSJSON; 
		    	}
		    	/* var EFLOW_APPMATERFILEJSON = getSubmitFlowMatersJson();
		    	if(EFLOW_APPMATERFILEJSON&&EFLOW_APPMATERFILEJSON!=""){
		    		flowVars.EFLOW_APPMATERFILEJSON = EFLOW_APPMATERFILEJSON;
		    	} */
		    	var submitFlowVars = $.param(flowVars);
				AppUtil.ajaxProgress({
					url : "executionController.do?exeBjFlow",
					params : submitFlowVars,
					callback : function(resultJson) {
						if(resultJson.OPER_SUCCESS){
							if(resultJson.EFLOW_CALLBACKFN){
								var flowVarsJson = JSON2.stringify(resultJson);
								var fnName = resultJson.EFLOW_CALLBACKFN;
								top.$("#EFLOWFORM_IFRAME")[0].contentWindow.eval(fnName+"('"+flowVarsJson+"')");
								AppUtil.closeLayer();
							}else{
								parent.art.dialog({
									content : resultJson.OPER_MSG,
									icon : "succeed",
									cancel:false,
									ok: function(){
										top.opener.$("#MyApplyExecGrid").datagrid('reload');
										top.opener.$("#NeedMeHandleGrid").datagrid('reload');
										top.opener.loadByMeHandledTaskDatas();
										top.window.opener = null;   
										top.window.open('','_self'); 
										top.window.close(); 
									}
								});
							}
						} else {
							parent.art.dialog({
								content : resultJson.OPER_MSG,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "JBPM6_EXECUTION","bottomLeft");

	});
	   function getbjcllbJson(){
	        var bjcllbList = [];
	        var applyMatersJson = $("input[name='applyMatersJson']").val();
	        var applyMatersObj = JSON2.parse(applyMatersJson);
	        for(var index in applyMatersObj){
	            //获取材料编码
	            var MATER_CODE = applyMatersObj[index].MATER_CODE;
	            //var selectValue = $("#SFBJ_"+MATER_CODE).val();
	            var selectValue = $("input[name='SFBJ_"+MATER_CODE+"']:checked ").val();
	            if((selectValue&&selectValue=="1") || (selectValue&&selectValue=="-1")){
	                var bjcllb = {};
	                bjcllb.MATER_CODE = $("#mc_"+MATER_CODE).val();
	                bjcllb.MATER_NAME = $("#mn_"+MATER_CODE).text();
	                bjcllb.BJYJ = $("#bjyq_"+MATER_CODE).val();
	                bjcllb.SFBJ = selectValue;
	               	bjcllbList.push(bjcllb);
	            }
	         }
	        return JSON2.stringify(bjcllbList);
	    }
	    
	    //补件材料信息（是否需要补件判断）
	    function isRequiredBjclxx(){
	    	var isNeedBj = false;
	    	var applyMatersJson = $("input[name='applyMatersJson']").val();
	        var applyMatersObj = JSON2.parse(applyMatersJson);
	        for(var index in applyMatersObj){
	            //获取材料编码
	            var MATER_CODE = applyMatersObj[index].MATER_CODE;
	            //var selectValue = $("#SFBJ_"+MATER_CODE).val();
	            var selectValue = $("input[name='SFBJ_"+MATER_CODE+"']:checked ").val();//是否补件
	            if(selectValue&&selectValue=="1"){
	                isNeedBj = true;
	                break;
	            }
	         }
	        return isNeedBj;
	    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
	<form id="BackBjFlowForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div id="BackBjFlowFormDiv" data-options="region:'center',split:true,border:false" >
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" id="backNodeNames" name="backNodeNames"
				value="${backNodeNames}" />
			<input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson"
				value="${flowSubmitInfoJson}" />
			<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
			
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;" id="SHYJTABLE"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">补件意见信息</td>
					<!-- =============隐藏补件信息JSON====== -->
					<input type="hidden" name="BJCLLB" val="" />
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">补件意见：</span>
						<textarea rows="5" cols="5"
							class="eve-textarea validate[required,maxSize[1024]]"
							style="width: 650px" name="EFLOW_HANDLE_OPINION"></textarea></td>
				</tr>
			</table>
			 <!-- ===================审核材料代码开始========================= -->
             <%-- <jsp:include page="./auditMaters.jsp" >
                <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
                <jsp:param value="true" name="isBackBj"/>
             </jsp:include> --%>
             <!-- ===================审核材料代码结束========================= -->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">补件材料信息</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr>
								<td style="text-align: center;font-weight:bold;">材料名称</td>
								<td style="text-align: center;font-weight:bold;width: 100px;">是否补件</td>
								<td style="text-align: center;font-weight:bold;width: 300px;">补件要求</td>
							</tr>
							<c:forEach var="mater" items="${materList}">
								<tr>
									<td style="text-align: center;"><input type="hidden"
										id="mc_${mater.MATER_CODE}" value="${mater.MATER_CODE}" /> <span
										id="mn_${mater.MATER_CODE}">${mater.MATER_NAME}</span></td>
									<td>
									<%-- <select type="select" class="tab_text"
										style="width: 90px;" id="SFBJ_${mater.MATER_CODE}">
											<option value="-1">否</option>
											<option value="1">是</option>
									</select> --%>
									<input type="radio" name="SFBJ_${mater.MATER_CODE}" value="1"
									 <c:if test="${mater.NOPASS=='1'}">checked="checked"</c:if> />是
									<input type="radio" name="SFBJ_${mater.MATER_CODE}" value="-1"  
									<c:if test="${mater.NOPASS!='1'}">checked="checked"</c:if> />否
									</td>
									<td><input type="text" id="bjyq_${mater.MATER_CODE}" value="${mater.materYj}"
										class="eve-input" maxlength="1000" style="width:95%;" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			<div style="display: none;">
				<c:forEach items="${nextTrans}" var="nextTran">
					<ul>
						<c:forEach items="${nextTran.handlers}" var="handler">
							<li>${handler.nextStepAssignerName}<input type="checkbox"
								nextassignertype="${handler.nextStepAssignerType}"
								text="${handler.nextStepAssignerName}"
								value="${handler.nextStepAssignerCode}" name="${nextTran.taskName}"
								checked="checked">
							</li>
						</c:forEach>
					</ul>
				</c:forEach>
			</div>

		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

