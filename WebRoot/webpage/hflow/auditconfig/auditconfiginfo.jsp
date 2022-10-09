<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("AuditConfigForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#AuditConfigForm").serialize();
				var url = $("#AuditConfigForm").attr("action");
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
							parent.$("#AuditConfigGrid").datagrid("reload");
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
		}, "JBPM6_AUDITCONFIG");
		//获取类型
		var AUDITER_TYPE = $("select[name='AUDITER_TYPE']").val();
		if(AUDITER_TYPE=="1"||AUDITER_TYPE=="3"){
			$("#CONFIG_CODE_TR").attr("style","");
			$("#SELECTOR_TBODY").attr("style","display:none;");
		}else{
			$("#CONFIG_CODE_TR").attr("style","display:none;");
			$("#SELECTOR_TBODY").attr("style","");
		}
	});
	
	function changeAuditCompType(){
		var AUDITER_TYPE = $("select[name='AUDITER_TYPE']").val();
		if(AUDITER_TYPE=="1"||AUDITER_TYPE=="3"){
			$("#CONFIG_CODE_TR").attr("style","");
			$("#SELECTOR_TBODY").attr("style","display:none;");
		}else{
			$("#CONFIG_CODE_TR").attr("style","display:none;");
			$("#SELECTOR_TBODY").attr("style","");
		}
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="AuditConfigForm" method="post"
		action="auditConfigController.do?saveOrUpdate">
		<div id="AuditConfigFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="CONFIG_ID"
				value="${auditConfig.CONFIG_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">配置名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="30"
						class="eve-input validate[required]"
						value="${auditConfig.CONFIG_NAME}" name="CONFIG_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">组件类型：</span> 
					    <eve:eveselect clazz="eve-input validate[required]" dataParams="FlowAuditCompType"
					         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择组件类型"
					         onchange="changeAuditCompType();"
					         value="${auditConfig.AUDITER_TYPE}" name="AUDITER_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>附属信息</a></div></td>
				</tr>
				<tr style="display: none;" id="CONFIG_CODE_TR">
					<td><span style="width: 100px;float:left;text-align:right;">配置代码：</span>
						<input type="text" style="width:300px;float:left;" maxlength="126"
						class="eve-input" value="${auditConfig.CONFIG_CODE}"
						name="CONFIG_CODE" /></td>
				</tr>
				<tbody style="display: none;" id="SELECTOR_TBODY">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">选择器URL：</span>
						<input type="text" style="width:350px;float:left;" maxlength="126"
						class="eve-input" value="${auditConfig.SELECTOR_URL}"
						name="SELECTOR_URL" /></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">选择器高度：</span>
						<input type="text" style="width:150px;float:left;" maxlength="18"
						class="easyui-numberbox" value="${auditConfig.SELECTOR_HEIGHT}"
						precision="0" name="SELECTOR_HEIGHT" /></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">选择器宽度：</span>
						<input type="text" style="width:150px;float:left;" maxlength="18"
						class="easyui-numberbox" value="${auditConfig.SELECTOR_WIDTH}"
						precision="0" name="SELECTOR_WIDTH" /></td>
				</tr>
				</tbody>
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

