<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
<style type="text/css">
ul,ol {
	list-style: none;
}

.eve-layout ul li {
	float: left;
	margin: 0 10px 10px 0;
	width: 150px;
	text-align: center;
}
</style>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("LayoutControlForm", function(form, valid) {
			if (valid) {
				var formData = $("#LayoutControlForm").serialize();
				var url = $("#LayoutControlForm").attr("action");
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
	<form id="LayoutControlForm" method="post"
		action="controlConfigController.do?saveOrUpdate">
		<input type="hidden" name="CONFIG_ID" value="${controlConfig.CONFIG_ID}" />
		<input type="hidden" name="MISSION_ID" value="${controlConfig.MISSION_ID}" />
		<input type="hidden" name="PARENT_ID" value="${controlConfig.PARENT_ID}" />
		<input type="hidden" name="CONTROL_NAME" value="${controlConfig.CONTROL_NAME}" />
		<input type="hidden" name="CONTROL_TYPE" value="21" />
		<div id="LayoutControlFormDiv">
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td>
					<span style="width: 125px;float:left;text-align:right;">标签值：</span>
					<input
					type="text" style="width:350px;float:left;"
					 class="eve-input" name="TAB_VALUES" /> 
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
