<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">
   

	$(function() {
		AppUtil.initWindowForm("EsuperInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#EsuperInfoForm").serialize();
				var url = $("#EsuperInfoForm").attr("action");
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
							parent.$("#EsuperGrid").datagrid("reload");
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
		}, "T_BSFW_ESUPER_INFO");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="EsuperInfoForm" method="post"
		action="esuperController.do?saveOrUpdateBatch">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="entityId" value="${entityId}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:35px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td colspan = "2"><span style="width: 90px;float:left;text-align:right;">办件申报号：</span>
					<textarea type="text" style="width:580px;float:left;height:55px;"  COLS="50" ROWS="6"
					       readonly = "readonly"  name="superIds" >${businessIds}</textarea></td>	       
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">反馈人：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${userName}"  name="REPLY_USER" /></td>
				<td></td>
			</tr>
			<tr>
				<td  colspan = "2"><span style="width: 90px;float:left;text-align:right;">反馈内容：</span>
					<textarea type="text" style="width:580px;float:left;height:120px;"  COLS="50" ROWS="10"
					  name="REPLY_INFO" ></textarea></td>
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

