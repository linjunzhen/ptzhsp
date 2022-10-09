
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ChildIndustryForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ChildIndustryForm").serialize();
				var url = $("#ChildIndustryForm").attr("action");
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
							//parent.$("#ChildIndustryGrid").datagrid("reload");
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
		}, "T_COMMERCIAL_INDUSTRY");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ChildIndustryForm" method="post"
		action="industryController.do?saveOrUpdateChildIndus">
		<div id="ChildIndustryFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="INDUSTRY_ID" value="${childIndustry.INDUSTRY_ID}">
			<input type="hidden" name="PARENT_ID" value="${parentId}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">子行业名称：</span>
						<input type="text" style="width:180px;float:left;" maxlength="128"
						class="eve-input validate[required]" value="${childIndustry.INDUSTRY_NAME}"
						name="INDUSTRY_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

