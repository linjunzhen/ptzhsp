<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusSpecialForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusSpecialForm").serialize();
				var url = $("#BusSpecialForm").attr("action");
				
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
							parent.$("#SpecialGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_SPECIAL");
	});
		
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusSpecialForm" method="post"
		action="busSpecialController.do?saveEditSpecial">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="BUS_ID" value="${busSpecial.BUS_ID}">
		<input type="hidden" name="CREATE_TIME" value="${busSpecial.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busSpecial.CREATE_USER}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
					<c:if test="${busSpecial.BUS_ID!=null}">
					<input class="easyui-combobox" style="width:250px;" name="UNIT_CODE" value="${busSpecial.UNIT_CODE}"
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 250,panelHeight: 'auto' " disabled/>
					</c:if><c:if test="${busSpecial.BUS_ID==null}">
					<input class="easyui-combobox" style="width:250px;" name="UNIT_CODE" value="${busSpecial.UNIT_CODE}"
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 250,panelHeight: 'auto' " />
					<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>					
			</tr>
			<%--<tr>
				<td><span style="width: 150px;float:left;text-align:right;">专项编码：</span>
					<c:if test="${busSpecial.BUS_ID!=null}">
					   ${busSpecial.BUS_CODE}
					</c:if> <c:if test="${busSpecial.BUS_ID==null}">
						<input type="text" style="width:250px;float:left;" maxlength="18"
							class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
							name="BUS_CODE" id="BUS_CODE" value="${busSpecial.BUS_CODE}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">排序：</span>
					<input type="text" style="width:250px;float:left;" maxlength="2"
					class="easyui-numberbox validate[required]"
					value="${busSpecial.TREE_SN}" precision="0" name="TREE_SN" /><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
		--%>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">专项名称：</span>
					<input type="text" style="width:250px;float:left;" maxlength="128"
					class="eve-input validate[required]" name="BUS_NAME" value="${busSpecial.BUS_NAME}"/><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
		</table>
		
		<div class="eve_buttons">
			<input value="提交" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

