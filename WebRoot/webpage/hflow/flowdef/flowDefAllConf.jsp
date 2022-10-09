<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FlowAllConfForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				var formData = $("#FlowAllConfForm").serialize();
				var url = $("#FlowAllConfForm").attr("action");
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
		}, null);
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="FlowAllConfForm" method="post" action="queryButtonAuthController.do?saveOrUpdate">
	    <div  id="FlowAllConfFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="defId" value="${defId}"/>
	    <input type="hidden" name="flowVersion" value="${flowVersion}"/>
	    <%--==========隐藏域部分结束 ===========--%>

		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="1" class="dddl-legend" style="font-weight:bold;">查看流程信息-操作按钮授权</td>
			</tr>
			
			<c:forEach items="${queryButtonList}" var="queryButton">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">${queryButton.BUTTON_NAME}：</span>
					<eve:radiogroup typecode="CKANSQ" width="150px" value="${queryButton.IS_AUTH}" fieldname="${queryButton.BUTTON_KEY}" >
					</eve:radiogroup>
					</td>
				</tr>
			</c:forEach>
			
		</table>
		
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
