<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<eve:resources loadres="jquery,easyui,layer,artdialog,apputil,validationegine"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("PasswordForm", function(form, valid) {
			if (valid) {
				var formData = $("#PasswordForm").serialize();
				var url = $("#PasswordForm").attr("action");
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
		}, null,"bottomLeft");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="PasswordForm" method="post"
		action="winSignController.do?updateWinSign">
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td><div style="text-align:center;font-size:15pt;margin-top:10px;">当前窗口${sessionScope.winNo}号 </div>
				<div style="text-align:center;font-size:13pt;margin-top:10px;">该窗口上次签到时间为<span style="color:red;">${sessionScope.loginTime}</span> </div>
				<div style="text-align:center;margin-top:10px;margin-bottom:20px;"><input value="签到" type="submit" style="height:30px;width:80px;"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> </div>
			</td>
			</tr>
		</table>
	</form>

</body>
