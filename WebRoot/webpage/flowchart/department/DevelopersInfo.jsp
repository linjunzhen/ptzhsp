<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DevelopersForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DevelopersForm").serialize();
				var url = $("#DevelopersForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.$("#DevelopersGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_LCJC_SYSTEM_DEVELOPER");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="DevelopersForm" method="post"
		action="departmentController.do?saveDeveloper">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="UNIT_ID" value="${developers.UNIT_ID}">
		<input type="hidden" name="PARENT_ID" value="0">
		<input type="hidden" name="UNIT_TYPE" value="3">
		<input type="hidden" name="STATUS" value="${developers.STATUS}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">开发商编码：</span>
					<c:if test="${developers.UNIT_ID!=null}">
					   ${developers.UNIT_CODE}
					</c:if> 
					<c:if test="${developers.UNIT_ID==null}">
						<input type="text" style="width:200px;float:left;" maxlength="18"
							class="eve-input validate[required],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
							name="UNIT_CODE" id="UNIT_CODE" value="${developers.UNIT_CODE}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
					</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">开发商名称：</span>
					<input type="text" style="width:200px;float:left;" maxlength="16"
					class="eve-input validate[required]" name="UNIT_NAME" value="${developers.UNIT_NAME}"/><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">开发商地址：</span>
					<input type="text" style="width:200px;float:left;" maxlength="64"
					class="eve-input" name="UNIT_ADDRESS" value="${developers.UNIT_ADDRESS}"/>					
				</td>	
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">联系人：</span>
					<input type="text" style="width:200px;float:left;" maxlength="32"
					class="eve-input" name="CONTACT_PERSON" value="${developers.CONTACT_PERSON}"/>					
				</td>	
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">联系电话：</span>
					<input type="text" style="width:200px;float:left;" maxlength="32"
					class="eve-input" name="CONTACT_PHONE" value="${developers.CONTACT_PHONE}"/>					
				</td>	
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">邮件：</span>
					<input type="text" style="width:200px;float:left;" maxlength="32"
					class="eve-input" name="CONTACT_EMAIL" value="${developers.CONTACT_EMAIL}"/>					
				</td>	
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">其它：</span>
					<input type="text" style="width:200px;float:left;" maxlength="32"
					class="eve-input" name="CONTACT_OTHER" value="${developers.CONTACT_OTHER}"/>					
				</td>	
			</tr>
		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

