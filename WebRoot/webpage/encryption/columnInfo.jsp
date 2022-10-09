<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ColumnInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ColumnInfoForm").serialize();
				var url = $("#ColumnInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$("#EncryptionConfigGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, "T_SYSTEM_LOGCONFIG_COLUMN");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ColumnInfoForm" method="post" action="encryptionController.do?saveOrUpdateColumn">
	    <div  id="ColumnInfoFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="COLUMN_ID" value="${columnInfo.COLUMN_ID}">
	    <input type="hidden" name="CONFIG_ID" value="${columnInfo.CONFIG_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			
			
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">业务表：</span>
				${columnInfo.CONFIG_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">字段注释：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${columnInfo.COLUMN_COMMENT}" maxlength="30"
					 class="eve-input validate[required]" name="COLUMN_COMMENT" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">字段名称：</span>
				
				<input
					type="text" style="width:150px;float:left;" 
					value="${columnInfo.COLUMN_NAME}" id="COLUMN_NAME" maxlength="32"
					class="eve-input validate[required,custom[onlyLetterNumberUnderLine]]" name="COLUMN_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
