<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<eve:resources loadres="jquery,easyui,layer,artdialog,apputil,validationegine"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("mobileForm", function(form, valid) {
			if (valid) {
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#mobileForm").serialize();
				var url = $("#mobileForm").attr("action");
				var mobile=$("#mobile").val();
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {/**
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});  **/
							art.dialog.data("selectDepInfo", {
								mobile : mobile
							});// 存储数据
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
		}, "T_MSJW_SYSTEM_SYSUSER","bottomLeft","${sessionScope.curLoginUser.userId}");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="mobileForm" method="post"
		action="sysUserController.do?updateCardAndMobile">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="userId"
			value="${sessionScope.curLoginUser.userId}">

		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">

			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">根据省公务人员统一身份认证平潭要求，需要各账号补全以下信息</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
					<input type="mobile" style="width:150px;float:left;"
					maxlength="16" class="eve-input validate[required,custom[mobilePhoneLoose],ajax[ajaxVerifyValueExist]]"
					id="MOBILE" name="MOBILE" value="${sessionScope.curLoginUser.mobile}"/>
					<font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">身份证号：</span>
					<input style="width:150px;float:left;"
					maxlength="18" class="eve-input validate[required,custom[vidcard],ajax[ajaxVerifyValueExist]]"
					id="USERCARD" name="USERCARD" value="${sessionScope.curLoginUser.usercard}"/>
					<font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
			<c:if test="${isForce==null}">
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
			</c:if>
		</div>
	</form>

</body>
