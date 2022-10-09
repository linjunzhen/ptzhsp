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
		AppUtil.initWindowForm("osQuestionForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#osQuestionForm").serialize();
				var url = $("#osQuestionForm").attr("action");
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
							//parent.reloadosQuestionList(resultJson.msg);
							parent.$("#osQuestionGrid").datagrid("reload");
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
		}, "T_HD_OS_QUESTION");
	});
	
	
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="osQuestionForm" method="post"
		action="osQuestionController.do?saveOrUpdate">
		<div region="center" style="min-height:160px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="TOPICID" value="${osQuestion.TOPICID}">
			<input type="hidden" name="QUESTIONID" value="${osQuestion.QUESTIONID}">
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
						<input class="eve-input validate[required]" maxLength="30" type="text" name="TITLE" value="${osQuestion.TITLE}" style="width:480px;"/>
					</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">排序编号：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required],custom[onlyNumberSp]" maxLength="3" type="text" name="SEQNUM" value="${osQuestion.SEQNUM}" style="width:80px;"/>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 80px;float:left;text-align:right;">问题状态：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择问题类型" class="eve-input validate[required]" name="TYPE">
						<option value="">请选择问题类型</option>						
						<option value="select" <c:if test="${osQuestion.TYPE=='select'}">selected="selected"</c:if>>单选</option>
						<option value="checkbox" <c:if test="${osQuestion.TYPE=='checkbox'}">selected="selected"</c:if>>多选</option>					
						<option value="input" <c:if test="${osQuestion.TYPE=='input'}">selected="selected"</c:if>>文本框</option>
						<option value="textarea" <c:if test="${osQuestion.TYPE=='textarea'}">selected="selected"</c:if>>文本域</option>
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

