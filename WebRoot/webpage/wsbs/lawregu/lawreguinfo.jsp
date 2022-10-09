
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("LawReguForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				$("#LAW_CONTENT").val(document.getElementById("eWebEditor").contentWindow.getHTML());
				var formData = $("#LawReguForm").serialize();
				var url = $("#LawReguForm").attr("action");
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
							parent.$("#LawReguGrid").datagrid("reload");
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
		}, "T_WSBS_LAWREGU");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="LawReguForm" method="post"
		action="lawReguController.do?saveOrUpdate">
		<div id="LawReguFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="LAW_ID" value="${lawRegu.LAW_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">法规标题：</span>
						<input type="text" style="width:350px;float:left;" maxlength="126"
						class="eve-input validate[required]" value="${lawRegu.LAW_TITLE}"
						name="LAW_TITLE" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">法规内容：</span>
						<input type="hidden" name="LAW_CONTENT" id="LAW_CONTENT"
						value="${lawRegu.LAW_CONTENT}"> <IFRAME ID="eWebEditor"
							SRC="plug-in/ewebeditor/ewebeditor.htm?id=LAW_CONTENT&style=mini500"
							FRAMEBORDER="0" SCROLLING="no" WIDTH="650" HEIGHT="350"></IFRAME>
					</td>
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

