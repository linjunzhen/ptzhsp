<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FlowButtonForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FlowButtonForm").serialize();
				var url = $("#FlowButtonForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							parent.$("#FlowButtonGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "JBPM6_BUTTON");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="FlowButtonForm" method="post"
		action="flowButtonController.do?saveOrUpdate">
		<div id="FlowButtonFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="BUTTON_ID" value="${flowButton.BUTTON_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">按钮名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${flowButton.BUTTON_NAME}" name="BUTTON_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">按钮的KEY：</span>
					    <c:if test="${flowButton.BUTTON_ID!=null}">
						   ${flowButton.BUTTON_KEY}
						</c:if>
						<c:if test="${flowButton.BUTTON_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
						value="${flowButton.BUTTON_KEY}" id="BUTTON_KEY" name="BUTTON_KEY" />
						</c:if>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">按钮图标CSS：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${flowButton.BUTTON_ICON}" name="BUTTON_ICON" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">按钮的操作函数：</span>
						<input type="text" style="width:150px;float:left;" maxlength="62"
						class="eve-input validate[required]"
						value="${flowButton.BUTTON_FUN}" name="BUTTON_FUN" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

