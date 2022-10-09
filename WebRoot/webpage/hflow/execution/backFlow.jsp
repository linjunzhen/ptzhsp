<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
    
	$(function() {
		
		AppUtil.initWindowForm("BackFlowForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	var formData = FlowUtil.getFormEleData("SHYJTABLE");
		    	if(formData.EFLOW_HANDLE_OPINION){
		    		flowVars.EFLOW_HANDLE_OPINION = formData.EFLOW_HANDLE_OPINION;
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
		    			var nextStepAssignerTypes = nextStepAssignerType.split(",");
		    			for(var i = 0;i<handlerAccounts.length;i++){
		    				var handler = {};
		    				handler.nextStepAssignerCode = handlerAccounts[i];
		    				handler.nextStepAssignerName = handlerNames[i];
		    				if(nextStepAssignerTypes[i]){
		    					handler.nextStepAssignerType = nextStepAssignerTypes[i];
		    				}else{
		    					handler.nextStepAssignerType = "1";
		    				}
		    				handlersArray.push(handler);
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
		    	var submitFlowVars = $.param(flowVars);
				AppUtil.ajaxProgress({
					url : "executionController.do?exeFlow",
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
									lock: true,
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
		}, "JBPM6_EXECUTION");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BackFlowForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div region="center">
			<div id="BackFlowFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" id="backNodeNames" name="backNodeNames"
				value="${backNodeNames}" />
			<input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson"
				value="${flowSubmitInfoJson}" />
				<!--==========隐藏域部分结束 ===========-->
				<table style="width:100%;border-collapse:collapse;"
					class="dddl-contentBorder dddl_table">
					<tr style="height:29px;">
						<td colspan="2" class="dddl-legend" style="font-weight:bold;">退回环节信息</td>
					</tr>
				
					<c:forEach items="${nextTrans}" var="nextTran">
						<tr>
							<td><span style="width: 100px;float:left;text-align:right;">环节名称：</span>
								<b>${nextTran.taskName}</b></td>
							<td><span style="width: 100px;float:left;text-align:right;">环节审核人：</span>
								<div style="width:250px;" class="eve-chcekbox">
									<ul>
										<c:forEach items="${nextTran.handlers}" var="handler">
											<li>${handler.nextStepAssignerName}<input type="checkbox"
												nextassignertype="${handler.nextStepAssignerType}"
												text="${handler.nextStepAssignerName}"
												value="${handler.nextStepAssignerCode}"
												name="${nextTran.taskName}" checked="checked">
											</li>
										</c:forEach>
									</ul>
								</div></td>
						</tr>
					</c:forEach>
				</table>
				<table style="width:100%;border-collapse:collapse;" id="SHYJTABLE"
					class="dddl-contentBorder dddl_table">
					<tr style="height:29px;">
						<td colspan="1" class="dddl-legend" style="font-weight:bold;">退回意见</td>
					</tr>
					<tr>
						<td><span style="width: 100px;float:left;text-align:right;">意见内容<font class="dddl_platform_html_requiredFlag">*</font>：</span>
							<textarea rows="5" cols="5"
								class="eve-textarea validate[required,maxSize[1200]]" maxlength="1200"
								style="width: 400px" name="EFLOW_HANDLE_OPINION"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<div data-options="region:'south'" style="height:50px;"   >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

