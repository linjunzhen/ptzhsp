<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,ztree,swfupload,artdialog">
</eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ExcelImportForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				//var formData = $("#ExcelImportForm").serialize();
				var formData = new FormData($("#ExcelImportForm")[0]);
				var url = $("#ExcelImportForm").attr("action");
				var layload = layer.load('正在提交请求中…');
				$.ajax({
					url : url,     
					type: 'POST', 
				    data: formData, 
				    async: false, 
				    cache: false, 
				    contentType: false, 
				    processData: false, 
					success: function(resultJson) {
						layer.close(layload);
						var obj = JSON.parse(resultJson);
						if (obj.success) {
							parent.art.dialog({
								content: obj.msg,
								icon:"succeed",
							    ok: true
							});
							parent.$("#TwQualifyInfoGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: obj.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_TW_QUALIFICATION");
	});
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ExcelImportForm" method="post"
	action="twQualificationController.do?uploadexcel&startRowNum=3" enctype="multipart/form-data">
		<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan='3' style="text-align:center;">
					<a href="webpage/wsbs/twqualification/台湾地区职业资格采信证书模板.xls">下载excel导入模板</a>
				</td>
			</tr>
			<tr>
				<td colspan='3' style="text-align:center;">
					<input id='upfile' type='file' name='file' accept=".xls,.xlsx">
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

