<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<eve:resources loadres="jquery,easyui,layer,artdialog,apputil,validationegine"></eve:resources>
<script type="text/javascript" src="plug-in/passJs/des.js" charset="UTF-8"></script> 
<script type="text/javascript">

	$(function() {
		$("input[name='OLD_PASSWORD']").val("");
		AppUtil.initWindowForm("PasswordForm", function(form, valid) {
			if (valid) {
				var OLD_PASSWORD = $("input[name='OLD_PASSWORD']").val();
				var PASSWORD = $("input[name='PASSWORD']").val();
				var REPASSWORD = $("input[name='REPASSWORD']").val();
				var MOBILE = $("input[name='MOBILE']").val();
				var USERCARD = $("input[name='USERCARD']").val();				
				$("input[name='OLD_PASSWORD']").val(strEnc(OLD_PASSWORD,"1","2","3"));
				$("input[name='PASSWORD']").val(strEnc(PASSWORD,"1","2","3"));
				$("input[name='REPASSWORD']").val(strEnc(REPASSWORD,"1","2","3"));
				$("input[name='MOBILE']").val(strEnc(MOBILE,"1","2","3"));
				$("input[name='USERCARD']").val(strEnc(USERCARD,"1","2","3"));
				
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
		}, "T_MSJW_SYSTEM_SYSUSER","bottomLeft","${sessionScope.curLoginUser.userId}");
		
		$("input[name='IS_TBZ']").click(function(){
			var val = $(this).val();
			if(val==1){
				$("input[name='USERCARD']").removeClass("validate[required,custom[vidcard],ajax[ajaxVerifyValueExist]]");
				$("input[name='USERCARD']").addClass("validate[required,ajax[ajaxVerifyValueExist]]");
			}else{
				$("input[name='USERCARD']").removeClass("validate[required,ajax[ajaxVerifyValueExist]]");
				$("input[name='USERCARD']").addClass("validate[required,custom[vidcard],ajax[ajaxVerifyValueExist]]");
			}
		});

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="PasswordForm" method="post"
		action="sysUserController.do?updatePass">
		<div id="PasswordFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="USER_ID"
			value="${sessionScope.curLoginUser.userId}">

		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">

			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>修改密码信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">原始密码：</span>
					<input type="password" style="width:180px;float:left;"
					maxlength="60" class="eve-input validate[required]"
					name="OLD_PASSWORD" autocomplete="off" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">新密码：</span>
					<input type="password" style="width:180px;float:left;"
					maxlength="16" 
					class="eve-input validate[required,minSize[8]],custom[Enpassword],ajax[verifyCheckPwd]"
					id="password" name="PASSWORD" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">确认新密码：</span>
					<input type="password" style="width:180px;float:left;"
					maxlength="16" class="eve-input validate[required,equals[password]]"
					name="REPASSWORD" /><font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
					<input type="mobile" style="width:180px;float:left;"
					maxlength="16" class="eve-input validate[required,custom[mobilePhoneLoose],ajax[ajaxVerifyValueExist]]"
					id="MOBILE" name="MOBILE" value="${sessionScope.curLoginUser.mobile}"/>
					<font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">是否台胞：</span>
					<eve:radiogroup typecode="YesOrNo" width="130px"
						defaultvalue="0"
						fieldname="IS_TBZ">
					</eve:radiogroup></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">身份证号：</span>
					<input style="width:180px;float:left;"
					maxlength="18" class="eve-input validate[required,custom[vidcard],ajax[ajaxVerifyValueExist]]"
					id="USERCARD" name="USERCARD" value="${sessionScope.curLoginUser.usercard}"/>
					<font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<c:if test="${isForce==null}">
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
				</c:if>
			</div>
		</div>
	</form>

</body>
