
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FootBallForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FootBallForm").serialize();
				var url = $("#FootBallForm").attr("action");
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
							parent.$("#FootBallGrid").datagrid("reload");
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
		}, "T_MSJW_SYSTEM_FOOTBALL");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="FootBallForm" method="post"
		action="footBallController.do?saveOrUpdate">
		<div id="FootBallFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="BALL_ID" value="${footBall.BALL_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">比赛球队：</span>
						<input type="text" style="width:350px;float:left;" 
						class="eve-input validate[required]" value="${footBall.BSQD}"
						name="BSQD" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">比赛时间：</span>
						<input type="text" style="width:130px;float:left;"
						class="laydate-icon validate[required]" value="${footBall.BSSJ}"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm'})"
						name="BSSJ" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">赔率：</span>
						<input type="text" style="width:350px;float:left;" 
						class="eve-input validate[required]" value="${footBall.PL}"
						name="PL" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">全场赛果：</span>
						<input type="text" style="width:350px;float:left;" 
						class="eve-input" value="${footBall.QCSG}" name="QCSG" /></td>
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

