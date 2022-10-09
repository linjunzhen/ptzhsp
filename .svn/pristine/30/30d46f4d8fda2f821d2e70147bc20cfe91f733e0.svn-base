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
						<span style="width: 80px;float:left;text-align:right;">投诉人：</span>
					</td>
					<td>
						${complain.COMPLAIN_NAME}
					</td>
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">手机号码：</span>
					</td>
					<td>
						${complain.COMPLAIN_PHONE}
					</td>
				</tr>
				
				<tr style="height:29px;" id="tr_dept">
					<td>
						<span style="width: 80px;float:left;text-align:right;">电子邮箱：</span>
					</td>
					<td>
						${complain.COMPLAIN_EMAIL}
					</td>
					<td>
						<span style="width: 80px;float:left;text-align:right;">投诉单位：</span>
					</td>
					<td>
						${complain.COMPLAIN_DEPT}
					</td>
				</tr>
				</table>
				<table style="width:100%;border-collapse:collapse;margin-top:-1px;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td style="width: 80px;">
						<span style="width: 80px;float:left;text-align:right;">投诉标题：</span>
					</td>
					<td colspan="3">
						${complain.COMPLAIN_TITLE}
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 80px;float:left;text-align:right;">咨询内容：</span>
					</td>
					<td colspan="3">
						${complain.COMPLAIN_CONTENT}
					</td>
				</tr>
				
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend" style="font-weight:bold;">回复信息</td>
				</tr>	
				
				<tr style="height:100px;" id="tr_reply">
					<td>
						<span style="width: 80px;float:left;text-align:right;height:100px;">回复内容：</span>
					</td>
					<td  colspan="3">
						${complain.REPLY_CONTENT}
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

