<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("WinScreenForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#WinScreenForm").serialize();
				var url = $("#WinScreenForm").attr("action");
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
							parent.$("#WinScreenGrid").datagrid("reload");
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
		},null);
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="WinScreenForm" method="post" action="callController.do?saveOrUpdateWinScreen">
	    <div  id="WinScreenFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${winScreenInfo.RECORD_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>					
					<td><span style="width: 100px;float:left;text-align:right;">窗口编号：</span>
						<input type="text" style="width:150px;float:left;"
						value="${winScreenInfo.WIN_NO}" maxlength="4"
						class="eve-input validate[required,custom[onlyLetterNumber]]"
						name="WIN_NO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">屏编号：</span>
						<input type="text" style="width:150px;float:left;"
						value="${winScreenInfo.SCREEN_NO}" maxlength="4"
						class="eve-input validate[required,custom[onlyLetterNumber]]"
						name="SCREEN_NO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">显示单位：</span>
						<input type="text" style="width:150px;float:left;"
						value="${winScreenInfo.DEPARTINFO}"
						class="eve-input validate[required]"
						name="DEPARTINFO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">显示字符数：</span>
						<input type="text" style="width:150px;float:left;"
						value="${winScreenInfo.WORD_NUM}"
						class="eve-input validate[required,custom[onlyLetterNumber]]"
						name="WORD_NUM" /> <font class="dddl_platform_html_requiredFlag">*</font>
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
