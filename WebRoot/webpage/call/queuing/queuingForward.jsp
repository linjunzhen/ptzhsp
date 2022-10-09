<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ForwardForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ForwardForm").serialize();
				var url = $("#ForwardForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
							});
							parent.$("#callingGrid").datagrid("reload");
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

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ForwardForm" method="post" action="callController.do?queuingForward">
	    <div  id="ForwardFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${recordId}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">转发信息</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">需要转发窗口：</span>
							<eve:eveselect clazz="tab_text validate[required]" 
							dataInterface="callService.findDatasForSelect" dataParams="${winNo}"
							defaultEmptyText="选择窗口" name="CUR_WIN" id="WIN_NO">
							</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
