<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("faqProblemForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#faqProblemForm").serialize();
				var url = $("#faqProblemForm").attr("action");
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
							parent.reloadfaqProblemList(resultJson.msg);
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
		}, "T_ZZHY_FAQ");
	});
	
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="faqProblemForm" method="post"
		action="faqProblemController.do?saveOrUpdate">
		<div region="center" style="min-height:210px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="PROBLEM_ID" value="${faqProblem.PROBLEM_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:35px;">
					<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">问题标题：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="30" type="text" name="PROBLEM_TITLE" value="${faqProblem.PROBLEM_TITLE}" style="width:480px;"/>
					</td>
				</tr>
				
				<tr style="height:100px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;height:100px;">问题答案：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="PROBLEM_CONTENT">${faqProblem.PROBLEM_CONTENT}</textarea>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 80px;float:left;text-align:right;">状态：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择状态" class="eve-input validate[required]" name="STATUS"  onChange="onSelectClass(this.value)">
						<option value="">请选择状态</option>						
						<option value="0" <c:if test="${faqProblem.STATUS==0}">selected="selected"</c:if>>未发布</option>
						<option value="1" <c:if test="${faqProblem.STATUS==1}">selected="selected"</c:if>>已发布</option>
					</select>	
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

