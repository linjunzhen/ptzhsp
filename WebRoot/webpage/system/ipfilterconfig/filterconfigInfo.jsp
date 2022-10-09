
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FilterconfigForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FilterconfigForm").serialize();
				var url = $("#FilterconfigForm").attr("action");
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
							parent.$("#FilterConfigGrid").datagrid("reload");
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
		}, null);

	});
	
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="FilterconfigForm" method="post"
		action="ipFilterConfigController.do?saveOrUpdate">
		<div id="FilterconfigFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="CONFIG_ID" value="${config.CONFIG_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">IP地址段：</span>
						<input type="text" style="width:300px;float:left;" maxlength="16"
						class="eve-input validate[required]" value="${config.IP_SEGMENT}"
						name="IP_SEGMENT" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">起始：</span>
						<input type="text" style="width:200px;float:left;" maxlength="4"
						class="eve-input validate[required,custom[integerplus]]" value="${config.IP_BIT_START}"
						name="IP_BIT_START" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">结束：</span>
						<input type="text" style="width:200px;float:left;" maxlength="4"
						class="eve-input validate[required,custom[integerplus]]" value="${config.IP_BIT_END}"
						name="IP_BIT_END" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">备注：</span>
						<input type="text" style="width:300px;float:left;" maxlength="128"
							   class="eve-input validate[]" value="${config.IP_REMARK}"
							   name="IP_REMARK" /></td>
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

