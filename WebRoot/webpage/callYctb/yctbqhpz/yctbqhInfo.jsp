<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,ztree,json2"></eve:resources>
<script type="text/javascript">
	

	$(function() {
		AppUtil.initWindowForm("DepartmentForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DepartmentForm").serialize();
				var url = $("#DepartmentForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("yctbqhTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_CKBS_YCTBQH");
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="DepartmentForm" method="post"
		action="callYctbController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="YCTBQH_ID" value="${callYctb.YCTBQH_ID}">
		<input type="hidden" name="PARENT_ID" value="${callYctb.PARENT_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">上级类别：</span>
					${callYctb.PARENT_NAME}</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">类别名称：</span>
					<input type="text" style="width:150px;float:left;" maxlength="50"
					class="eve-input validate[required]"
					value="${callYctb.TYPE_NAME}" name="TYPE_NAME" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">类别别名：</span>
					<input type="text" style="width:150px;float:left;" maxlength="50"
					class="eve-input validate[required]"
					value="${callYctb.TYPE_ALIAS}" name="TYPE_ALIAS" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">类别编码：</span>
					<c:if test="${callYctb.YCTBQH_ID!=null}">
					   ${callYctb.TYPE_CODE}
					</c:if> <c:if test="${callYctb.YCTBQH_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="18"
							class="eve-input validate[required],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
							value="${callYctb.TYPE_CODE}" id="TYPE_CODE"
							name="TYPE_CODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
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

