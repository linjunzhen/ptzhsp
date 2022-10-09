<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ReceivingTimeInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ReceivingTimeInfoForm").serialize();
				var url = $("#ReceivingTimeInfoForm").attr("action");
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
	<form id="ReceivingTimeInfoForm" method="post"
		action="departServiceItemController.do?updateReceivingTimeInfo">
		<div id="ReceivingTimeInfoFormDiv">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="ITEM_ID" value="${itemInfo.ITEM_ID}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">收件时限</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">收件天数：</span>
						<input type="text"
						maxlength="2" class="eve-input validate[required,custom[integerplus]]" value="${itemInfo.RECEIVEDAY}"
						name="RECEIVEDAY" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;"><font style="color: red">点击确定后将设置所选事项的收件天数为当前值</font>
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
