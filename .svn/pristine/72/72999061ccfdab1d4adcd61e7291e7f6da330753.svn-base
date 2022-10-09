<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<eve:resources loadres="jquery,easyui,layer,artdialog,apputil,validationegine"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ToTopReasonForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ToTopReasonForm").serialize();
				var url = $("#ToTopReasonForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : "置顶成功",
								icon : "succeed",
								time : 3,
								ok : true
							});
							parent.$("#QueueGrid").datagrid("reload");
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
		}, "T_CKBS_QUEUERECORD");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ToTopReasonForm" method="post" action="newCallController.do?toTop">
		<div id="ToTopReasonFormDiv"  data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="entityId" value="${entityId}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<!-- <tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;"></td>
			</tr> -->
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 置顶原因：</td>
				<td  colspan="3">
					<textarea id="toTopReason" class="eve-textarea validate[required],maxSize[50]" rows="3" cols="200"  
					  name="TOTOPREASON" style="width:300px;height:100px;"  ></textarea>
				</td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>
