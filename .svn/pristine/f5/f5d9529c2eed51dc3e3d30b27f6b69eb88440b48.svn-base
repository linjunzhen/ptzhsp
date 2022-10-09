<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusSysForm", function(form, valid) {
			if (valid) {
				var platCode = $("#bussys_developID").combobox('getValue');
				if(platCode=='' || platCode=='undefined'){
					parent.art.dialog({
						content: '请选择系统开发商',
						icon:"error",
					    ok: true
					});
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusSysForm").serialize();
				var url = $("#BusSysForm").attr("action");
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
							parent.$("#busSysGrid").datagrid("reload");
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
		}, "T_LCJC_SYSTEM_BUSSYS");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusSysForm" method="post"
		action="busSysController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="SYSTEM_ID" value="${busSys.SYSTEM_ID}">
		<input type="hidden" name="UNIT_ID" value="${busSys.UNIT_ID}">
		<input type="hidden" name="CREATE_TIME" value="${busSys.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busSys.CREATE_USER}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">系统编码：</span>
					<c:if test="${busSys.SYSTEM_ID!=null}">
					   ${busSys.SYSTEM_CODE}
					</c:if> 
					<c:if test="${busSys.SYSTEM_ID==null}">
					   <input type="text" style="width:200px;float:left;" maxlength="18"
							class="eve-input validate[required],custom[onlyLetterNumber]"
							name="SYSTEM_CODE" id="SYSTEM_CODE" value="${busSys.SYSTEM_CODE}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if> 					
					</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">系统名称：</span>
					<input type="text" style="width:200px;float:left;" maxlength="128"
					class="eve-input validate[required]" name="SYSTEM_NAME" value="${busSys.SYSTEM_NAME}"/><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">系统开发商：</span>
					<input class="easyui-combobox" style="width:200px;" name="DEVELOP_ID" id="bussys_developID" value="${busSys.DEVELOP_ID}" 
						data-options="url:'departmentController.do?loadDevelop&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_ID',textField:'UNIT_NAME',panelWidth: 200,panelHeight: 'auto' " /> 
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

