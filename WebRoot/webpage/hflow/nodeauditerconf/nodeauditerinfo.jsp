<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("NodeAuditerForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#NodeAuditerForm").serialize();
				var url = $("#NodeAuditerForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							art.dialog.data("saveNodeAuditResult", {
								operSuccess:true
							});
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "JBPM6_NODEAUDITERCONF");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="NodeAuditerForm" method="post"
		action="nodeAuditerController.do?saveOrUpdate">
		<div id="NodeAuditerFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="AUDITER_ID" value="${nodeAuditer.AUDITER_ID}">
			<input type="hidden" name="DEF_ID" value="${nodeAuditer.DEF_ID}"> 
			<input type="hidden" name="DEF_VERSION" value="${nodeAuditer.DEF_VERSION}"> 
			<input type="hidden" name="NODE_NAME" value="${nodeAuditer.NODE_NAME}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">下一环节：</span>
					    <eve:eveselect clazz="eve-input validate[required]" dataParams="${nodeAuditer.DEF_ID},${nodeAuditer.NODE_NAME}"
					         dataInterface="flowNodeService.findNextTaskNodesForSelect" defaultEmptyText="请选择下一环节"
					         value="${nodeAuditer.NEXT_NODE_NAME}" name="NEXT_NODE_NAME">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">审核人控件：</span>
						<eve:eveselect clazz="eve-input validate[required]" dataParams="1"
					         dataInterface="auditConfigService.findForSelect" defaultEmptyText="请选择审核人控件"
					         value="${nodeAuditer.CONFIG_ID}" name="CONFIG_ID">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">审核人规则：</span>
					    <eve:eveselect clazz="eve-input" dataParams="FlowAuditerRule"
					         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择审核人规则"
					         value="${nodeAuditer.AUDITER_RULE}" name="AUDITER_RULE">
					    </eve:eveselect>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">变量值：</span>
						<textarea rows="5" cols="5" class="eve-textarea"
							style="width: 400px" name="VARS_VALUE">${nodeAuditer.VARS_VALUE}</textarea></td>
				</tr>
			</table>


		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

