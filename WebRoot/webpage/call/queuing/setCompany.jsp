<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("CompanyForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#CompanyForm").serialize();
				var url = $("#CompanyForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){		
							parent.toUrl("executionController.do?goHandle&taskId="+resultJson.jsonString+"&exeId="+$("#EXE_ID").val()+"&lineId=${record.RECORD_ID}","");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},null);
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;" id="companyBody">
	<form id="CompanyForm" method="post" action="callController.do?queuingCompany">
	    <div  id="CompanyFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${record.RECORD_ID}">
		    <input type="hidden" name="Q_T.TASK_NODENAME_EQ" value="窗口办理">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">处理信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">申报号：</span>
						<input type="text" class="eve-input validate[required]" maxLength="30" name="EXE_ID" id="EXE_ID">
						</select> <font class="dddl_platform_html_requiredFlag">*流水号</font>
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
