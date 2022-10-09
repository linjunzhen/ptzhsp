<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<eve:resources loadres="jquery,easyui,layer,artdialog,apputil,validationegine"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("FieldAuditForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FieldAuditForm").serialize();
				var url = $("#FieldAuditForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : "保存成功",
								icon : "succeed",
								time : 3,
								ok : true
							});
							var opinion = $("textarea[name='AUDIT_OPINION']").val();
							art.dialog.open.origin.showopinion('${fieldId}',opinion);
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
		}, "T_COMMERCIAL_FIELDAUDIT");
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="FieldAuditForm" method="post" action="commercialController.do?saveFieldOpinion">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="EXE_ID" value="${exeId}">
		<input type="hidden" name="AUDIT_NODE" value="${curNode}">
		<input type="hidden" name="BELONG_ID" value="${belongId}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td class="tab_width"> 字段名称：</td>
				<td >
					<input type="hidden" name="FIELD_NAME" value="${fieldName }" />
					<input type="text" class="tab_text" name="FIELD_TEXT" value="${fieldtext }" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 审核意见：</td>
				<td >
					<textarea class="eve-textarea validate[required],maxSize[1000]" rows="3" cols="200"  
					  name="AUDIT_OPINION" style="width:300px;height:100px;"  ></textarea>
				</td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input value="确定" type="submit"class="z-dlg-button z-dialog-cancelbutton" />
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="art.dialog.open.origin.cancelCheck('${fieldId}');AppUtil.closeLayer();" />
		</div>
	</form>

</body>
