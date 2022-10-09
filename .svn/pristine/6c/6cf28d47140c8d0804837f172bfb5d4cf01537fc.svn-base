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
	loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
    
	$(function() {
		//初始化验证引擎的配置
		AppUtil.initWindowForm("HandleOverForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				//获取提交的变量对象
		    	var flowSubmitInfoJson  = $("#flowSubmitInfoJson").val();
		    	//转换成对象
		    	var flowVars = JSON2.parse(flowSubmitInfoJson);
		    	var EFLOW_HANDLE_OPINION = $("textarea[name='EFLOW_HANDLE_OPINION']").val();
		    	flowVars.EFLOW_HANDLE_OPINION = EFLOW_HANDLE_OPINION;
		    	var submitFlowVars = $.param(flowVars);
				AppUtil.ajaxProgress({
					url : "executionController.do?handleOver",
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

<body>
	<form id="HandleOverForm" method="post" >
		<div region="center">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="SELECTOR_NAMES" /> <input type="hidden"
				name="SELECTEDTRANSNODE" /> <input type="hidden"
				id="flowSubmitInfoJson" name="flowSubmitInfoJson"
				value="${flowSubmitInfoJson}" />
			<!--==========隐藏域部分结束 ===========-->

			<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
				<tr>
					<th colspan="1">审核意见</th>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">意见内容：
					</span> 
					<textarea rows="4" cols="5"
							class="eve-textarea validate[maxSize[1500]]"
							maxlength="1200" style="width: 400px" name="EFLOW_HANDLE_OPINION"></textarea>
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