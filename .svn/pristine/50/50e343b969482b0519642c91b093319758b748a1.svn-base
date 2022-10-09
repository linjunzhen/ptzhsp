<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusRuleSeeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusRuleSeeForm").serialize();
				var url = $("#BusRuleSeeForm").attr("action");
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
							parent.$("#BusRuleGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_RULECONFIG");
	});
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusRuleSeeForm" method="post"
		action="busRuleController.do?saveEditRule">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="RULE_ID" value="${busRule.RULE_ID}">
		<input type="hidden" name="BUSSYS_UNITCODE" value="${busRule.BUSSYS_UNITCODE}">
		<input type="hidden" name="PROCESS_CODE" value="${busRule.PROCESS_CODE}">
		<input type="hidden" name="JUDGE_CONDITIONS" value="${busRule.JUDGE_CONDITIONS}">
		<input type="hidden" name="CREATE_TIME" value="${busRule.CREATE_TIME}">
		<input type="hidden" name="CREATE_USER" value="${busRule.CREATE_USER}">
		<input type="hidden" name="STATUS" value="${busRule.STATUS}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td width="50%"><span style="width: 90px;float:left;text-align:right;">所属单位：</span>
					${busRule.UNIT_NAME}
				</td>
				<td width="*"><span style="width: 90px;float:left;text-align:right;">所属系统：</span>
					<input class="easyui-combobox" style="width:150px;" name="BUSSYS_PLATCODE" value="${busRule.BUSSYS_PLATCODE}" 
						data-options="url:'busSysController.do?load&defaultEmpty=true&unitCode=${busRule.BUSSYS_UNITCODE}',editable:false,
						method: 'post',valueField:'SYSTEM_CODE',textField:'SYSTEM_NAME',panelWidth: 250,panelHeight: 'auto' " disabled/> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">业务专项：</span>
					${busRule.BUS_NAME}
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">业务环节：</span>
					${busRule.TACHE_NAME}
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 90px;float:left;text-align:right;">监察节点：</span>
					${busRule.PROCESS_NAME}
				</td>
			</tr>
			<tr>
				<td ><span style="width: 90px;float:left;text-align:right;">监察要素：</span>
					${busRule.SUPER_ELEMENTS}
				</td>
				<td ><span style="width: 90px;float:left;text-align:right;">监察类型：</span>
					<c:if test="${busRule.ANALYSIS_TYPE==1}">时限监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==2}">内容监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==3}">流程监察</c:if>
					<c:if test="${busRule.ANALYSIS_TYPE==4}">收费监察</c:if>
					<!-- <input class="easyui-combobox" style="width:150px;" name="ANALYSIS_TYPE" value="${busRule.ANALYSIS_TYPE}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " readonly="readonly"/>
					 -->
				</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">监察方式：</span>
					<input class="easyui-combobox" style="width:150px;" name="IS_ARTIFICIAL" value="${busRule.IS_ARTIFICIAL}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=EsuperWay',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " disabled/> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">预警类型：</span>
					<input class="easyui-combobox" style="width:150px;" name="WARN_STATUS" value="${busRule.WARN_STATUS}" 
						data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=WarnType',editable:false,
						method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " disabled/>
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">规则信息</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则表达式：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width: 505px;height:35px;resize: none;" name="JUDGE_DESC" disabled>${busRule.JUDGE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font> 
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">规则描述：</span>
					<textarea rows="2" cols="5" class="eve-textarea validate[required]"
						style="width: 505px;height:35px;resize: none;" name="RULE_DESC" disabled>${busRule.RULE_DESC}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4"><span style="width: 90px;float:left;text-align:right;">备注：</span>
					<textarea rows="1" cols="5" class="eve-textarea"
						style="width: 505px;height:35px;resize: none;" name="REMARK" disabled>${busRule.REMARK}</textarea>
				</td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

