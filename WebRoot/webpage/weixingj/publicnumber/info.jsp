<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("PublicNumberForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#PublicNumberForm").serialize();
				var url = $("#PublicNumberForm").attr("action");
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
							parent.$("#PublicNumberGrid").datagrid("reload");
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
		}, "T_WEIXINGJ_PUBLICNUMBER");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="PublicNumberForm" method="post"
		action="publicNumberController.do?saveOrUpdate">
		<div id="PublicNumberFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="NUMBER_ID"
				value="${publicNumber.NUMBER_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">APPID：</span>
						<input type="text" style="width:350px;float:left;" maxlength="30"
						class="eve-input validate[required]" value="${publicNumber.APPID}"
						name="APPID" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">APPSECRET：</span>
						<input type="text" style="width:350px;float:left;" maxlength="30"
						class="eve-input validate[required]"
						value="${publicNumber.APPSECRET}" name="APPSECRET" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">接口URL：</span>
						<input type="text" style="width:350px;float:left;" maxlength="254"
						class="eve-input validate[required]" value="${publicNumber.URL}"
						name="URL" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">接口Token：</span>
						<input type="text" style="width:350px;float:left;" maxlength="30"
						class="eve-input validate[required]" value="${publicNumber.TOKEN}"
						name="TOKEN" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">JS接口安全域名：</span>
						<input type="text" style="width:350px;float:left;" maxlength="30"
						class="eve-input" value="${publicNumber.JS_SEC_DOMAIN}"
						name="JS_SEC_DOMAIN" /></td>
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

