<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("OpinionForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#OpinionForm").serialize();
				var url = $("#OpinionForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							art.dialog.data("backinfo", {
								back : "1"
							});
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
		}, null);

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="OpinionForm" method="post"
		action="preApprovalController.do?updateSHYJ">
		<div id="OpinionFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="preIds" id="preIds" value="${preIds}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>审核信息</a></div></td>
				</tr>
				<tr style="height:50px;">
					<td><span style="width: 100px;float:left;text-align:right;">审核内容：</span>
	                  	<c:if test="${status==2}">
	                                                                 审核发布
	                  	</c:if> 
						<c:if test="${status==6}">
	                                                                 审核变更发布
	                  	</c:if> 
						<c:if test="${status==4}">
	                                                                 审核取消
	                  	</c:if> 
	                  	<c:if test="${status==null}">
	                  		批量审核
	                  	</c:if>
	                </td>
				</tr>
				<tr style="height:50px;">
					<td>
						<span style="width: 100px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>是否同意：</span>
						<input type="radio" value="1" name="SFTY" checked="checked">同意
						<input type="radio" value="2" name="SFTY">不同意
						
					</td>
				</tr>
				<tr style="height:50px;">
					<td><span style="width: 100px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>审核意见：</span>

						<textarea rows="7" cols="5"
							class="eve-textarea validate[required,maxSize[500]]" style="width: 450px"
							name="SHYJ" maxlength="500"></textarea> 
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 220px;color:red;float:left;text-align:right;">备注：请填写审核依据</span>
					</td>

				</tr>
				<tr/>
				<tr/>
			</table>

		</div>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>
