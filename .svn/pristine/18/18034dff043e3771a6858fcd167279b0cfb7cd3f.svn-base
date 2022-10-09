<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusTacheForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusTacheForm").serialize();
				var url = $("#BusTacheForm").attr("action");
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
							parent.$("#TacheGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_TACHE");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusTacheForm" method="post"
		action="busSpecialController.do?saveEditTache">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="TACHE_ID" value="${busTache.TACHE_ID}">
		<input type="hidden" name="BUS_CODE" value="${busTache.BUS_CODE}">
		<input type="hidden" name="UNIT_CODE" value="${busTache.UNIT_CODE}">
		<input type="hidden" name="FLOW_INFO" value="${busTache.FLOW_INFO}">
		<input type="hidden" name="CREATE_TIME" value="${busTache.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busTache.CREATE_USER}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">环节编码：</span>
					<c:if test="${busTache.TACHE_ID!=null}">
					   ${busTache.TACHE_CODE}
					</c:if> <c:if test="${busTache.TACHE_ID==null}">
						<input type="text" style="width:250px;float:left;" maxlength="18"
							class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
							name="TACHE_CODE" id="TACHE_CODE" value="${busTache.TACHE_CODE}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">环节名称：</span>
					<input type="text" style="width:250px;float:left;" maxlength="128"
					class="eve-input validate[required]" name="TACHE_NAME" value="${busTache.TACHE_NAME}"/><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">排序：</span>
					<input type="text" style="width:250px;float:left;" maxlength="2"
					class="easyui-numberbox validate[required]"
					value="${busTache.TREE_SN}" precision="0" name="TREE_SN" /><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

