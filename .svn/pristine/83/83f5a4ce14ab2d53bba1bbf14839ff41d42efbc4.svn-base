<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("TableUploadConfigForm", function(form, valid) {
			if (valid) {
				var formData = $("#TableUploadConfigForm").serialize();
				var url = $("#TableUploadConfigForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							parent.window.location.reload();
							//AppUtil.closeLayer();
						}else{
							//layer.msg(resultJson.msg, 2, 1);
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, null)
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="TableUploadConfigForm" method="post"
		action="controlConfigController.do?saveOrUpdate">
		<%--==========隐藏域部分开始 ===========--%>
		<input type="hidden" name="CONFIG_ID"
			value="${controlConfig.CONFIG_ID}" /> <input type="hidden"
			name="MISSION_ID" value="${controlConfig.MISSION_ID}" /> <input
			type="hidden" name="PARENT_ID" value="${controlConfig.PARENT_ID}" />
		<input type="hidden" name="CONTROL_TYPE" value="14"> <input
			type="hidden" name="UPLOAD_TYPE" value="3"> <input
			type="hidden" name="CONTROL_NAME" value="表格上传控件">
		<div id="TableUploadConfigFormDiv">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">上传限制类型：</span>
						<input type="text" style="width:150px;float:left;"
						class="eve-input" name="UPLOAD_LIMITTYPES" value="*.doc;*.docx;" />

					</td>
					<td><span style="width: 125px;float:left;text-align:right;">限制文件大小：</span>
						<input type="text" style="width:150px;float:left;" value="10 MB"
						class="eve-input" name="UPLOAD_LIMITSIZE" /></td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">上传路径：</span>
						<input type="text" style="width:150px;float:left;"
						class="eve-input" name="UPLOAD_PATH" /></td>
					<td><span style="width: 125px;float:left;text-align:right;">上传表格ID：</span>
						<input type="text" style="width:150px;float:left;"
						class="eve-input" name="UPLOAD_TABLEID" /></td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">关系业务表名：</span>
						<input type="text" style="width:150px;float:left;"
						class="eve-input" name="BIND_TABLENAME" /></td>
					<td>
					<span style="width: 125px;float:left;text-align:right;">限制文件数量：</span>
						<input type="text" style="width:150px;float:left;" value="5"
						class="eve-input" name="UPLOAD_LIMITFILESIZE" />
					</td>
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
