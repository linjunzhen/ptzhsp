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
	loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    
	$(function() {
		//初始化验证引擎的配置
		AppUtil.initWindowForm("PreAuditNoPassForm", function(form, valid) {
			if (valid) {
				var bjcllbJson = getbjcllbJson();
	            $("input[name='BJCLLB']").val(bjcllbJson);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	var EFLOW_HANDLE_OPINION = $("textarea[name='EFLOW_HANDLE_OPINION']").val();
		    	flowVars.EFLOW_HANDLE_OPINION = EFLOW_HANDLE_OPINION;
		    	flowVars.BJCLLB = $("input[name='BJCLLB']").val();
		    	/* var EFLOW_APPMATERFILEJSON = getSubmitFlowMatersJson();
		    	if(EFLOW_APPMATERFILEJSON&&EFLOW_APPMATERFILEJSON!=""){
		    		flowVars.EFLOW_APPMATERFILEJSON = EFLOW_APPMATERFILEJSON;
		    	} */
		    	var submitFlowVars = $.param(flowVars);
				AppUtil.ajaxProgress({
					url : "executionController.do?preNoPass",
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
	function getbjcllbJson(){
        var bjcllbList = [];
        var applyMatersJson = $("input[name='applyMatersJson']").val();
        var applyMatersObj = JSON2.parse(applyMatersJson);
        for(var index in applyMatersObj){
            //获取材料编码
            var MATER_CODE = applyMatersObj[index].MATER_CODE;
            //var selectValue = $("#SFBJ_"+MATER_CODE).val();
            var selectValue = $("input[name='SFBJ_"+MATER_CODE+"']:checked ").val();
            if(selectValue&&selectValue=="-1"){
                var bjcllb = {};
                bjcllb.MATER_CODE = $("#mc_"+MATER_CODE).val();
                bjcllb.MATER_NAME = $("#mn_"+MATER_CODE).text();
                bjcllb.BJYJ = $("#bjyq_"+MATER_CODE).val();
                bjcllbList.push(bjcllb);
            }
            
         }
        return JSON2.stringify(bjcllbList);
    }
</script>
</head>

<body>
	<form id="PreAuditNoPassForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div region="center">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="SELECTOR_NAMES" /> <input type="hidden"
				name="SELECTEDTRANSNODE" /> <input type="hidden"
				id="flowSubmitInfoJson" name="flowSubmitInfoJson"
				value="${flowSubmitInfoJson}" />
			<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
			<input type="hidden" name="BJCLLB" val="" />
			<!--==========隐藏域部分结束 ===========-->

			<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				<tr>
					<th colspan="1">不通过理由</th>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">理由内容<font
							class="dddl_platform_html_requiredFlag">*</font>：
					</span> 
					<textarea rows="4" cols="5"
							class="eve-textarea validate[required,maxSize[500]]"
							maxlength="500" style="width: 400px" name="EFLOW_HANDLE_OPINION"></textarea>
					</td>
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
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">材料审核信息</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr>
								<td style="text-align: center;font-weight:bold;">材料名称</td>
								<td style="text-align: center;font-weight:bold;width: 100px;">是否通过</td>
								<td style="text-align: center;font-weight:bold;width: 300px;">不通过意见</td>
							</tr>
							<c:forEach var="mater" items="${materList}">
								<tr>
									<td style="text-align: center;"><input type="hidden"
										id="mc_${mater.MATER_CODE}" value="${mater.MATER_CODE}" /> <span
										id="mn_${mater.MATER_CODE}">${mater.MATER_NAME}</span></td>
									<td>
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
		</div>
		<div data-options="region:'south'" style="height:50px;">
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>