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
		action="esuperController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="SUPER_ID" value="${esuperInfo.SUPER_ID}">
		<input type="hidden" name="ESUPER_ID" value="${esuperInfo.ESUPER_ID}">
		<input type="hidden" name="FB_ID" value="${esuperInfo.FB_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">办件申报号：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${esuperInfo.BUSINESS_ID}"  name="BUSINESS_ID" /></td>
				<td><span style="width: 90px;float:left;text-align:right;">业务环节：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${esuperInfo.PROCESS_NAME}"  name="PROCESS_NAME" /></td>	       
			</tr>
			
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">预警信息</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">预警类型：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" <c:if test="${esuperInfo.WARN_TYPE=='1'}">value="黄灯" </c:if> 
					 <c:if test="${esuperInfo.WARN_TYPE=='2'}">value="红灯" </c:if> name="WARN_TYPE" /></td>
				<td><span style="width: 90px;float:left;text-align:right;">预警反馈时限：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${esuperInfo._REPLY_LIMIT}"  name="_REPLY_LIMIT" /></td>
			</tr>
			<tr>
				<td  colspan = "2"><span style="width: 90px;float:left;text-align:right;">预警原因：</span>
					<textarea type="text" style="width:580px;float:left;height:45px;"  COLS="50" ROWS="6"
					       readonly = "readonly"  name="WARN_REASON" >${esuperInfo.WARN_REASON}</textarea></td>
			</tr>
			<tr>
				<td  colspan = "2"><span style="width: 90px;float:left;text-align:right;">预警内容：</span>
					<textarea type="text" style="width:580px;float:left;height:80px;"  COLS="50" ROWS="10"
					       readonly = "readonly"  name="WARN_INFO" >${esuperInfo.WARN_INFO}</textarea></td>
			</tr>
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">反馈信息</td>
			</tr>
			<tr>
				<td><span style="width: 90px;float:left;text-align:right;">反馈人：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${esuperInfo.REPLY_USER}"  name="REPLY_USER" /></td>
				<td><span style="width: 90px;float:left;text-align:right;">反馈时间：</span>
					<input type="text" style="width:230px;float:left;" maxlength="15" class="eve-input"
					       readonly = "readonly" value="${esuperInfo.REPLY_TIME}"  name="REPLY_TIME" /></td>
			</tr>
			<tr>
				<td  colspan = "2"><span style="width: 90px;float:left;text-align:right;">反馈内容：</span>
					<textarea type="text" style="width:580px;float:left;height:90px;"  COLS="50" ROWS="10"
					       readonly = "readonly"  name="REPLY_INFO" >${esuperInfo.REPLY_INFO}</textarea></td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input
				value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>

