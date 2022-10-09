<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ValidateFormForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ValidateFormForm").serialize();
				var url = $("#ValidateFormForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$.fn.zTree.getZTreeObj("validteFormTree").reAsyncChildNodes(null, "refresh");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, "T_MSJW_SYSTEM_VALIDATE_FORM");
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ValidateFormForm" method="post" action="formValidateController.do?saveOrUpdateTree">
	    <div  id="ValidateFormFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="FORM_ID" value="${validateForm.FORM_ID}">
	    <input type="hidden" name="PARENT_ID" value="${validateForm.PARENT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${validateForm.PARENT_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">配置名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${validateForm.FORM_NAME}" maxlength="30"
					 class="eve-input validate[required]" name="FORM_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">配置编码：</span>
				<c:if test="${validateForm.FORM_ID!=null}">
				   ${validateForm.FORM_CODE}
				</c:if>
				<c:if test="${validateForm.FORM_ID==null}">
				<input
					type="text" style="width:150px;float:left;" 
					value="${validateForm.FORM_CODE}" id="FORM_CODE" maxlength="32"
					class="eve-input validate[required,ajax[ajaxVerifyValueExist]]" name="FORM_CODE" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</c:if>
				</td>
			</tr>
			
			
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
