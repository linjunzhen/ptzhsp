<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("uploadInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#uploadInfoForm").serialize();
				var url = $("#uploadInfoForm").attr("action");
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
							parent.$("#DownloadDataGrid").datagrid("reload");
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
		}, "");


	});
	
  
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="uploadInfoForm" method="post"
		action="bankDealController.do?saveOrUpdateBankLic">
		<div id="uploadInfoFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="recordId" value="${licInfo.recordId }">
			<input type="hidden" name="tableName" value="${licInfo.tableName }">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:13px;">
					<td style="border-bottom:0;"></td>
				</tr>
				<tr style="height:49px;">
					<td><span style="width: 120px;float:left;text-align:right;">开户许可证号：</span>
					    <input type="text" style="width:190px;float:left;"
							value="${licInfo.BANK_LICENSE }" maxlength="30"
							class="eve-input validate[required]"
							name="BANK_LICENSE" />
					</td>
				</tr>
			</table>


		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

