<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DictionaryForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#DictionaryForm").serialize();
				var url = $("#DictionaryForm").attr("action");
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
							parent.$("#dictionaryGrid").datagrid("reload");
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
		},null);
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DictionaryForm" method="post" action="dictionaryController.do?saveOrUpdate">
	    <div  id="DictionaryFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="DIC_ID" value="${dictionary.DIC_ID}">
	    <input type="hidden" name="TYPE_ID" value="${dictionary.TYPE_ID}">
	    <input type="hidden" name="TYPE_NAME" value="${dictionary.TYPE_NAME}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">所属类别：</span>
				${dictionary.TYPE_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">字典名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${dictionary.DIC_NAME}"
					 class="eve-input validate[required]" name="DIC_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
				<span style="width:100px;float:left;text-align:right;">字典编码：</span>
				<input
					type="text" style="width:150px;float:left;" 
					value="${dictionary.DIC_CODE}"
					class="eve-input validate[required]" name="DIC_CODE" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">描述：</span>
				<input
					type="text" style="width:450px;float:left;" 
					value="${dictionary.DIC_DESC}" maxlength="256"
					class="eve-input" name="DIC_DESC" />
				</td>
			</tr>
			
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
