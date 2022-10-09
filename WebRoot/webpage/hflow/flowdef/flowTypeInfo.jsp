<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FlowTypeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#FlowTypeForm").serialize();
				var url = $("#FlowTypeForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("flowTypeTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_MSJW_SYSTEM_DICTYPE");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="FlowTypeForm" method="post" action="flowTypeController.do?saveOrUpdate">
	    <div  id="FlowTypeFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TYPE_ID" value="${flowType.TYPE_ID}">
	    <input type="hidden" name="PARENT_ID" value="${flowType.PARENT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>

		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${flowType.PARENT_NAME}
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">类别名称：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]" value="${flowType.TYPE_NAME}"
					name="TYPE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
		</table>
		
		
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
