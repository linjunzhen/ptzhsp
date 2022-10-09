<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("materSetForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#materSetForm").serialize();
				var url = $("#materSetForm").attr("action");
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
							parent.$("#materSetGrid").datagrid("reload");
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
	<form id="materSetForm" method="post" action="commercialSetController.do?saveOrUpdateMaterSet">
	    <div  id="materSetFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${mater.RECORD_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">材料应用信息</td>
				</tr>
				<tr>
					<td colspan="2">
						<span style="width: 100px;float:left;text-align:right;">是否表单化：</span> 
						<eve:eveselect clazz="eve-input validate[required]" dataParams="YesOrNo" 
					         value="${mater.IS_FORM}" 
					         dataInterface="dictionaryService.findDatasForSelect" 
					         name="IS_FORM">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">表单名称：</span>
						<input type="text" style="width:180px;float:left;"
						value="${mater.FORM_NAME}" maxlength="30"
						class="eve-input"
						name="FORM_NAME" />
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
