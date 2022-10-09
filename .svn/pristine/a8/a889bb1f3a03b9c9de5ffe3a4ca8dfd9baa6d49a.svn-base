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
		<input type="hidden" name="CONTROL_TYPE" value="2" />
		<div id="LayoutControlFormDiv">
			<div class="eve-layout">
				<ul>
					<li><img src="webpage/developer/codeMission/images/1.png" />
					<p>
							<input type="radio" name="LAYOUT_TYPE" value="1" checked="checked"/>中央布局
						</p></li>
					<li><img src="webpage/developer/codeMission/images/2.png" />
					<p>
							<input type="radio" name="LAYOUT_TYPE" value="2" />左右布局
						</p></li>
					<li><img src="webpage/developer/codeMission/images/3.png" />
					<p>
							<input type="radio" name="LAYOUT_TYPE" value="3" />左中右布局
						</p></li>
					<li><img src="webpage/developer/codeMission/images/4.png" />
					<p>
							<input type="radio" name="LAYOUT_TYPE" value="4" />上中下布局
						</p></li>
					<li><img src="webpage/developer/codeMission/images/5.png" />
					<p>
							<input type="radio" name="LAYOUT_TYPE" value="5"/>上下布局
						</p></li>
				</ul>
			</div>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>
