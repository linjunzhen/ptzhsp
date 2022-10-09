<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("tagTypeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#tagTypeForm").serialize();
				var url = $("#tagTypeForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("tagTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_USERCENTER_TAGTYPE");
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="tagTypeForm" method="post" action="tagController.do?saveOrUpdateTree">
	    <div  id="tagTypeFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TYPE_ID" value="${tagType.TYPE_ID}">
	    <input type="hidden" name="PARENT_ID" value="${tagType.PARENT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${tagType.PARENT_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">类别名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${tagType.TYPE_NAME}" maxlength="30"
					 class="eve-input validate[required]" name="TYPE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
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
