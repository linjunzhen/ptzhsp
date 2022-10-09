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
		AppUtil.initWindowForm("BspjForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BspjForm").serialize();
				var url = $("#BspjForm").attr("action");
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
		}, "T_WSBS_BSPJ");

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BspjForm" method="post"
		action="bspjController.do?savePjxx">
		<div id="BspjFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="PJ_ID" value="${bspj.PJ_ID}"> <input
				type="hidden" name="EXE_ID" value="${bspj.EXE_ID}"> <input
				type="hidden" name="FWSXBM" value="${bspj.FWSXBM}"> 

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询标题：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_TITLE}</p>
					</td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询类型：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;"><c:if test="${busConsult.CONSULT_TYPE == 0}">办事咨询</c:if><c:if test="${busConsult.CONSULT_TYPE == 1}">其他咨询</c:if></p>
					</td>
				</tr>
				<c:if test="${busConsult.CONSULT_TYPE == 0}">
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询事项：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_ITEMS}</p>
					</td>
				</tr>
				</c:if>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询部门：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${busConsult.CONSULT_DEPT}</p>
					</td>
				</tr>
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询内容：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px; line-height: 20px;">${busConsult.CONSULT_CONTENT}</p>
					</td>
				</tr>				
				<tr>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">咨询者：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;">${busConsult.CREATE_USERNAME}</p>
					</td>
				</tr>
				<tr>					
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">手机号码：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;">${userInfo.SJHM}</p>
					</td>
				</tr>
				</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">回复信息</td>
				</tr>				
				<tr style="height:100px;" id="tr_reply">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;height:100px;">回复内容：</span>
					</td>
					<td>
						${busConsult.REPLY_CONTENT}						
					</td>
				</tr>
			</table>


		</div>
		<div class="eve_buttons">
		<input
				value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

