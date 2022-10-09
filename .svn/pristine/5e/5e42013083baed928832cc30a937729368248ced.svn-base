<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("factorChangeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#factorChangeForm").serialize();
				var url = $("#factorChangeForm").attr("action");
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
							parent.loadFactorList(resultJson.msg);
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
		}, "T_LCJC_BUS_MONITOR_NODE");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="factorChangeForm" method="post"
		action="processChange.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="RULE_ID" value="${factorInfo.RULE_ID}">
		<input type="hidden" name="CHANGE_ID" value="${factorInfo.CHANGE_ID}">
		<input type="hidden" name="PROCESS_CODE" value="${factorInfo.PROCESS_CODE}">
		<input type="hidden" name="TACHE_CODE" value="${factorInfo.TACHE_CODE}">
		<input type="hidden" name="APPLY_ID" value="${factorInfo.APPLY_ID}">
		<input type="hidden" name="TREE_SN" value="${factorInfo.TREE_SN}">
		<input type="hidden" name="ELEMENT_STATUS" value="${factorInfo.ELEMENT_STATUS}">
		<input type="hidden" name="CREATE_TIME" value="${factorInfo.CREATE_TIME}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr style="height:36px;">
				<td><span style="width: 100px;float:left;text-align:right;">要素名称：</span>
					<input type="text" style="width:300px;float:left;" maxlength="30"
							class="eve-input validate[required]"
							name="SUPER_ELEMENTS" id="SUPER_ELEMENTS" value="${factorInfo.SUPER_ELEMENTS}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>      
					</td>
			</tr>
			<tr style="height:36px;">
				<td><span style="width: 100px;float:left;text-align:right;">监察类型：</span>
					<input style="width:160px;height:25px;float:left;" id="ANALYSIS_TYPECombx"
							class="easyui-combobox validate[required]" value="${factorInfo.ANALYSIS_TYPE}"
							name="ANALYSIS_TYPE"
							 data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 100,panelHeight: 'auto' "/>
						    
					</td>
			</tr>
			<tr style="height:60px;">
				<td><span style="width: 100px;float:left;text-align:right;height:60px;">要素描述：</span>
					<textarea class="eve-textarea" rows="3" cols="200" style="width:300px;height:60px;" 
					  name="RULE_DESC">${factorInfo.RULE_DESC}</textarea>
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

