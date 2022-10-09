<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("RevokeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#RevokeForm").serialize();
				var url = $("#RevokeForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
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
		}, "JBPM6_REVOKE");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="RevokeForm" method="post"
		action="userInfoController.do?revoke">
		<div id="RevokeFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="REVOKE_ID" value="${revoke.REVOKE_ID}">
			<input type="hidden" name="EXE_ID" value="${revoke.EXE_ID}">  

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">撤办审核</td>
				</tr>
				<tr>
					<td valign="top"><span style="width: 100px;float:left;text-align:right;">撤办原因：</span>
						${revoke.REVOKE_CONTENT}
					</td>
				</tr>
				<tr>
					<td valign="top"><span style="width: 100px;float:left;text-align:right;"><font color="red">*</font>是否同意撤办：</span>
						同意<input type="radio" value="1" name="REVOKE_STATUS" class="validate[required]"/>
						不同意<input type="radio" value="-1" name="REVOKE_STATUS" class="validate[required]"/>
				</tr>
				<tr>
					<td valign="top"><span style="width: 100px;float:left;text-align:right;"><font color="red">*</font>审核说明：</span>
						<textarea rows="5" cols="5" class="eve-textarea validate[required],maxSize[500]"
							style="width: 400px" name="AUDIT_CONTENT" >${revoke.AUDIT_CONTENT}</textarea></td>
				</tr>
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

