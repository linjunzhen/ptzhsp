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
<eve:resources loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    
	$(function() {
		//初始化验证引擎的配置
		AppUtil.initWindowForm("SubmitFlowForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = FlowUtil.getFormEleData("SHCZDIV");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	flowVars = AppUtil.mergeObject(formData,flowVars);
		    	//判断是否是多分支流程
		    	var isMultiTransFlow = $("#isMultiTransFlow").val();
		    	if(isMultiTransFlow=="true"){
		    		//获取下拉框中的选择节点名称
		    		var selectNodeName = $("select[name='flowNodeName']").val();
		    		var NEW_EFLOW_NEXTSTEPS = {};
		    		for(var index in flowVars.EFLOW_NEXTSTEPS){
		    			if(index==selectNodeName){
		    				NEW_EFLOW_NEXTSTEPS[index] = flowVars.EFLOW_NEXTSTEPS[index];
		    			}
		    		}
		    		flowVars.EFLOW_NEXTSTEPS = NEW_EFLOW_NEXTSTEPS;
		    	}
		    	//将审核人转换成JSON
		    	var EFLOW_NEXTSTEPSJSON =  JSON2.stringify(flowVars.EFLOW_NEXTSTEPS);
		    	flowVars.EFLOW_NEXTSTEPS= null;
		    	if(EFLOW_NEXTSTEPSJSON!=null&&EFLOW_NEXTSTEPSJSON!="undefined"){
		    		flowVars.EFLOW_NEXTSTEPSJSON = EFLOW_NEXTSTEPSJSON; 
		    	}else{
		    		//说明是办结
		    		flowVars.EFLOW_DESTTOEND = "true";
		    		flowVars.EFLOW_NEXTSTEPSJSON = "";
		    	}
		    	var EFLOW_APPMATERFILEJSON = getSubmitFlowMatersJson();
		    	if(EFLOW_APPMATERFILEJSON&&EFLOW_APPMATERFILEJSON!=""){
		    		flowVars.EFLOW_APPMATERFILEJSON = EFLOW_APPMATERFILEJSON;
		    	}
		    	if("${nodeConfig.SECOND_AUDIT}"==1){
		    		var EFLOW_AGAINMATERFILEJSON = getAgainbjcllbJson();
			    	if(EFLOW_AGAINMATERFILEJSON&&EFLOW_AGAINMATERFILEJSON!=""){
			    		flowVars.EFLOW_AGAINMATERFILEJSON = EFLOW_AGAINMATERFILEJSON;
			    	}
		    	}
		    	//获取传阅的人员IDS
		    	var EFLOW_PERULA_IDS = $("input[name='EFLOW_PERULA_IDS']").val();
		    	flowVars.EFLOW_PERULA_IDS = EFLOW_PERULA_IDS;
				//获取绑定审核材料ID
		    	var FLOW_USER_MATER_ID = $("input[name='FLOW_USER_MATER_ID']").val();
				flowVars.FLOW_USER_MATER_ID = FLOW_USER_MATER_ID;
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
										if(top.opener.$("#QueuingUndoGrid").length>0){
											top.opener.$("#QueuingUndoGrid").datagrid('reload');
											top.opener.parent.reloadTabGrid("已受理记录","accept");
										}
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

<body >
	<form id="SubmitFlowForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div region="center">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="ISPARALLEL" value="${ISPARALLEL}" />
				<input type="hidden" name="SELECTOR_NAMES" />
			    <input type="hidden" name="isMultiTransFlow" id="isMultiTransFlow" value="${isMultiTransFlow}" />
				<input type="hidden" name="SELECTEDTRANSNODE" />
				<input type="hidden" id="flowSubmitInfoJson" name="flowSubmitInfoJson" value="${flowSubmitInfoJson}"/>
				<!--==========隐藏域部分结束 ===========-->
		        
		        <!-- ===================审核材料代码开始========================= -->
		        <jsp:include page="./submitAddBindMatters.jsp" >
		           <jsp:param value="${flowSubmitInfoJson}" name="flowSubmitInfoJson"/>
		           <jsp:param value="false" name="isBackBj"/>
		           <jsp:param value="${nodeConfig.PUBDOC_RULE}" name="publicDocRule"/>
		        </jsp:include>
		        <!-- ===================审核材料代码结束========================= -->
		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

