
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("SysRoleForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#SysRoleForm").serialize();
				var url = $("#SysRoleForm").attr("action");
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
							parent.$("#SysRoleGrid").datagrid("reload");
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
		}, "T_MSJW_SYSTEM_SYSROLE");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SysRoleForm" method="post"
		action="sysRoleController.do?saveOrUpdate">
		<div id="SysRoleFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ROLE_ID" value="${sysrole.ROLE_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">角色名称：</span>
						<input type="text" style="width:180px;float:left;" maxlength="20"
						class="eve-input validate[required]" value="${sysrole.ROLE_NAME}"
						name="ROLE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">角色编码：</span>
					 <c:if test="${sysrole.ROLE_ID!=null}">
						   ${sysrole.ROLE_CODE}
					 </c:if>
					 <c:if test="${sysrole.ROLE_ID==null}">
						  <input type="text" style="width:180px;float:left;" maxlength="14"
						class="eve-input validate[required],custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]"
						value="${sysrole.ROLE_CODE}" id="ROLE_CODE" name="ROLE_CODE" />
					 </c:if>
					<font class="dddl_platform_html_requiredFlag">*</font></td>
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

