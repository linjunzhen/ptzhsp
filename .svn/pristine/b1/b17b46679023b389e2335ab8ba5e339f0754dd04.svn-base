<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("commentForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#commentForm").serialize();
				var url = $("#commentForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.reloadcommentList(resultJson.msg);
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
		}, "T_HD_COMMENT");
	});
	
</script>

</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="commentForm" method="post"
		action="commentController.do?saveOrUpdate">
		<div region="center" style="min-height:500px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="COMPLAIN_ID" value="${comment.COMPLAIN_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">评议人：</span>
					</td>
					<td style="width: 197px;">
						<p style="width: 197px;margin-left: 5px; margin-right: 5px;">${comment.COMMENT_NAME}</p>
					</td>
					<td style="width:80px;">
						<span style="width: 120px;float:left;text-align:right;">年龄：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;">${comment.COMMENT_AGE}</p>
					</td>
				</tr>
				
				<tr style="height:29px;" id="tr_dept">
					<td>
						<span style="width: 120px;float:left;text-align:right;">学历：</span>
					</td>
					<td style="width: 197px;">
						<p style="width: 197px;margin-left: 5px; margin-right: 5px;">${comment.COMMENT_DEGREE}</p>
					</td>
					<td style="width:80px;">
						<span style="width: 120px;float:left;text-align:right;">职业：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;">${comment.COMMENT_JOB}</p>
					</td>
				</tr>
				<tr style="height:29px;" id="tr_dept">
					<td>
						<span style="width: 120px;float:left;text-align:right;">身份证：</span>
					</td>
					<td style="width: 197px;">
						<p style="width: 197px;margin-left: 5px; margin-right: 5px;">${comment.COMMENT_IDCARD}</p>
					</td>
					<td style="width:80px;">
						<span style="width: 120px;float:left;text-align:right;">电子邮箱：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;">${comment.COMMENT_EMAIL}</p>
					</td>
				</tr>
				<tr style="height:29px;" id="tr_dept">
					<td>
						<span style="width: 120px;float:left;text-align:right;">手机号码：</span>
					</td>
					<td style="width: 197px;">
						<p style="width: 197px;margin-left: 5px; margin-right: 5px;">${comment.COMMENT_PHONE}</p>
					</td>
					<td style="width:80px;">
						<span style="width: 120px;float:left;text-align:right;">评议时间：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;">${comment.CREATE_TIME}</p>
					</td>
				</tr>
				</table>
				<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>评议信息</a></div></td>
				</tr>	
				
				<tr>
					<td style="width: 80px;">
						<span style="width: 120px;float:left;text-align:right;">评议部门：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_DEPT}</p>
					</td>
				</tr>
				
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">贯彻落实中央和市委市政府,重大决策部署和方针政策：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_FZZC}</p>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">办事效率：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_BSXL}</p>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">廉洁勤政：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_LJQZ}</p>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">依法行政：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_YFXZ}</p>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">服务质量：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_FWZL}</p>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 120px;float:left;text-align:right;">补充说明：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${comment.COMMENT_REMARK}</p>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<!--<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> -->
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

