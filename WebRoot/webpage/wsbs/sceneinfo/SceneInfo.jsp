<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,artdialog,validationegine,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("SceneInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#SceneInfoForm").serialize();
				var url = $("#SceneInfoForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("sceneInfoTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_MSJW_SYSTEM_DEPARTMENT");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="SceneInfoForm" method="post"
		action="sceneInfoController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="SCENE_ID" value="${sceneInfo.SCENE_ID}">
		<input type="hidden" name="PARENT_ID" value="${sceneInfo.PARENT_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">上级场景：</span>
					${sceneInfo.PARENT_NAME}</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">场景名称：</span>
					<input type="text" style="width:200px;float:left;" maxlength="128"
					class="eve-input validate[required]"
					value="${sceneInfo.SCENE_NAME}" name="SCENE_NAME" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">导航提示：</span>
					<input type="text" style="width:200px;float:left;" maxlength="100"
					class="eve-input" placeholder="请输入对下一场景导航提示"
					value="${sceneInfo.NAV_TIPS}" name="NAV_TIPS" />
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

