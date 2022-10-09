<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("newWinGroupForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#newWinGroupForm").serialize();
				var url = $("#newWinGroupForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							art.dialog.opener.addWinGroup(resultJson.recordId,resultJson.winGroupName);
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
							$("input[type='submit']").attr("disabled", false);
						}
					}
				});
			}
		}, "T_CKBS_WIN_GROUP");
	});
</script>
</head>

<body style="margin:0px;" class="eui-diabody" fit="true">
	<form id="newWinGroupForm" method="post"
		action="callSetController.do?saveOrUpdateNewWinGroup">
		<div id="newWinGroupFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:36px;">
					<td  class="dddl-legend"><div class="eui-dddltit"><a>分组信息</a></div></td>
				</tr>
				<tr>
					<td ><span style="width: 85px;float:left;text-align:right;">分组名称：</span>
						<textarea rows="2" cols="1" class="eve-textarea validate[required,maxSize[16],ajax[ajaxVerifyValueExist]]"
                            style="width: 250px;height:95px;margin-top: 4px;margin-bottom: 7px;" id="GROUP_NAME"  name="GROUP_NAME"></textarea>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:50px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

