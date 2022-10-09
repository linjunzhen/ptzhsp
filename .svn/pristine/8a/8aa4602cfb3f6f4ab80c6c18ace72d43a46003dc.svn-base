<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusinessScopeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusinessScopeForm").serialize();
				var url = $("#BusinessScopeForm").attr("action");
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
							parent.$("#BusinessScopeGrid").datagrid("reload");
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
		}, "T_WSBS_BUSSCOPE");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BusinessScopeForm" method="post"
		action="domesticControllerController.do?saveOrUpdateBusinessScope">
		<div id="BusinessScopeFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ID" value="${businessScope.ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">经营范围表述条目：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.BUSSCOPE_NAME}" name="BUSSCOPE_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">条目代码：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.BUSSCOPE_CODE}" name="BUSSCOPE_CODE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">经营范围规范条目说明：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.REMARK}" name="REMARK" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">对应《国民经济行业分类》：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.INDU_CODE}" name="INDU_CODE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">使用经营范围规范条目办理登记的相关活动：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.GFTMXGHD}" name="GFTMXGHD" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">经营范围规范条目不包含的相关活动内容：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.FGFTMXGHD}" name="FGFTMXGHD" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">相关信息共享推送部门：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[]"
						value="${businessScope.DEPARTMENT_NAME}" name="DEPARTMENT_NAME" />
						</td>
					<td><span style="width: 120px;float:left;text-align:right;">经营范围规范条目对应涉企行政许可事项：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[]"
						value="${businessScope.ITEM_NAME}" name="ITEM_NAME" />
						</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">事项类型：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.ITEM_TYPE}" name="ITEM_TYPE" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 120px;float:left;text-align:right;">是否可用：</span>
						<input type="text" style="width:150px;float:left;" maxlength="14"
						class="eve-input validate[required]"
						value="${businessScope.IS_USABLE}" name="IS_USABLE" /><font
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

