<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("encryptionForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#encryptionForm").serialize();
				var url = $("#encryptionForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("EncryptionConfigTree").reAsyncChildNodes(null, "refresh");
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
		}, "ENCRYPTION_CONFIG_TABLE");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="encryptionForm" method="post" action="encryptionController.do?saveOrUpdateTree">
	    <div  id="encryptionFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="CONFIG_ID" value="${configInfo.CONFIG_ID}">
	    <input type="hidden" name="PARENT_ID" value="${configInfo.PARENT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			
			
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${configInfo.PARENT_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">业务表名注释：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${configInfo.CONFIG_NAME}" maxlength="30"
					 class="eve-input validate[required]" name="CONFIG_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">业务表名称：</span>
				<c:if test="${configInfo.CONFIG_ID!=null}">
				   ${configInfo.BUSTABLE_NAME}
				</c:if>
				<c:if test="${configInfo.CONFIG_ID==null}">
				<input
					type="text" style="width:150px;float:left;" 
					value="${configInfo.BUSTABLE_NAME}" id="BUSTABLE_NAME" maxlength="32"
					class="eve-input validate[required,custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]]" name="BUSTABLE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</c:if>
				</td>
			</tr>
			<tr>
				<td>
				<span style="width:100px;float:left;text-align:right;">主键：</span>
				<input
					type="text" style="width:150px;float:left;" 
					value="${configInfo.BUSTABLE_PRIMARYKEY}" maxlength="32"
					class="eve-input validate[required,custom[onlyLetterNumberUnderLine]]" name="BUSTABLE_PRIMARYKEY" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				
				</td>
				<td>
				<span style="width:100px;float:left;text-align:right;">排序字段：</span>
				<input
					type="text" style="width:150px;float:left;" 
					value="${configInfo.ORDER_COLUMN}" maxlength="32"
					class="eve-input validate[required,custom[onlyLetterNumberUnderLine]]" name="ORDER_COLUMN" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<span style="width:100px;float:left;text-align:right;">排序最大值：</span>
				<input
					type="text" style="width:150px;float:left;" 
					value="${configInfo.MAX_ORDER_VAL}" maxlength="32"
					class="eve-input validate[required]" name="MAX_ORDER_VAL" /> 
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
