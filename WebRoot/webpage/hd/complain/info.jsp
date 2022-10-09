<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("complainForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#complainForm").serialize();
				var url = $("#complainForm").attr("action");
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
							parent.reloadcomplainList(resultJson.msg);
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
		}, "T_HD_COMPLAIN");
	});
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="complainForm" method="post"
		action="complainController.do?saveOrUpdate">
		<div region="center" style="min-height:356px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="COMPLAIN_ID" value="${complain.COMPLAIN_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">投诉人：</span>
					</td>
					<td>
						${complain.COMPLAIN_NAME}
					</td>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">手机号码：</span>
					</td>
					<td>
						${complain.COMPLAIN_PHONE}
					</td>
				</tr>
				
				<tr style="height:29px;" id="tr_dept">
					<td>
						<span style="width: 80px;float:left;text-align:right;">电子邮箱：</span>
					</td>
					<td>
						${complain.COMPLAIN_EMAIL}
					</td>
					<td>
						<span style="width: 80px;float:left;text-align:right;">投诉单位：</span>
					</td>
					<td>
						${complain.COMPLAIN_DEPT}
					</td>
				</tr>
				</table>
				<table style="width:100%;border-collapse:collapse;margin-top:-1px;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td style="width: 80px;">
						<span style="width: 80px;float:left;text-align:right;">投诉标题：</span>
					</td>
					<td colspan="3">
						${complain.COMPLAIN_TITLE}
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 80px;float:left;text-align:right;">投诉内容：</span>
					</td>
					<td colspan="3">
						${complain.COMPLAIN_CONTENT}
					</td>
				</tr>
				
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>回复信息</a></div></td>
				</tr>	
				
				<tr style="height:100px;" id="tr_reply">
					<td>
						<span style="width: 80px;float:left;text-align:right;height:100px;">回复内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" style="width:420px;height:100px;" 
						  name="REPLY_CONTENT">${complain.REPLY_CONTENT}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'"  style="height:46px;">
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

