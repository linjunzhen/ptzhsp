<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("changeSelectForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#changeSelectForm").serialize();
				var url = $("#changeSelectForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							$("#processChangeGrid").datagrid("reload");
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
		}, "t_lcjc_bus_special");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="changeSelectForm" method="post"
		action="processChange.do?startChange">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" id="BUS_ID" name="BUS_ID">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr style="height:36px;">
				<td><span style="width: 100px;float:left;text-align:right;">专项名称：</span>
					<input style="width:350px;height:25px;float:left;" id="BUS_NAMECombx"
							class="easyui-combobox validate[required]" value="${deploy.BUSSYS_PLATCODE}"
							name="BUS_CODE"
							data-options="url:'processChange.do?comboxSpecial',
											editable:false,method: 'post',valueField:'BUS_CODE',
											textField:'BUS_NAME',panelWidth: 350,panelHeight: 'auto',
											onSelect:function(rec){
											   if(rec.BUS_ID){
											   	  $('#BUS_ID').val(rec.BUS_ID);
											   }
											}
										" />
						<font class="dddl_platform_html_requiredFlag">*</font>      
					</td>
			</tr>
			<!-- 
			<tr style="height:60px;">
				<td><span style="width: 100px;float:left;text-align:right;height:60px;">专项编号：</span>
					<input type="text" style="width:300px;float:left;" maxlength="18"
							class="eve-input validate[required]"
							name="BUS_CODE" id="BUS_CODE"/>
				</td>
			</tr>  -->
		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

