<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2,swfupload"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("recallCheckForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#recallCheckForm").serialize();
				var url = $("#recallCheckForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#projectRecallGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_WSBS_PROJECT_RECALL");
	});
</script>
<style>
	.eve_buttons{position: relative !important;}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="recallCheckForm" method="post" action="projectDetailController.do?recallCheck">
		<div region="center" style="min-height:460px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="YW_ID" value="${recallInfo.YW_ID}">
				<input type="hidden" name="EXE_ID" value="${recallInfo.EXE_ID}">
				<!--==========隐藏域部分结束 ===========-->
				
				<div class="syj-title1" style="height:30px;">
					<span>撤件信息</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;">撤件人</th>
						<td>
							<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
							class="eve-input" value="${recallInfo.OPERATOR_NAME}" name="OPERATOR_NAME" readonly/>
						</td>
					</tr>
					<tr>
						<th style="border-bottom-width: 1px;width:180px;">撤件时间</th>
						<td>
							<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
							class="eve-input" value="${recallInfo.CREATE_TIME}" name="CREATE_TIME" readonly/>
						</td>
					</tr>
					<tr>
						<th style="border-bottom-width: 1px;width:180px;">撤件原因</th>
						<td>
							<textarea class="eve-textarea " rows="3" cols="200" name="RECALL_OPINION" 
							  style="width:98.6%;height:75px;" readonly>${recallInfo.RECALL_OPINION}</textarea>
						</td>
					</tr>
					<tr>
						<th style="border-bottom-width: 1px;width:180px;">附件</th>
						<td>
							<a style="color: blue;" title="${recallInfo.FILE_NAME}"
								href="${fileServer}download/fileDownload?attachId=${recallInfo.FILEATTACH_PATH}&attachType=${recallInfo.ATTACH_TYPE}" >
								${recallInfo.FILE_NAME}
							</a>
						</td>
					</tr>
				</table>
				<div class="syj-title1" style="height:30px;">
					<span>审核信息</span>
				</div>
				<c:if test="${recallInfo.CHECK_STATUS == '0' }">
					<table cellpadding="0" cellspacing="0" class="syj-table2">
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">是否审核通过</th>
							<td>
								<input type="radio" name="CHECK_STATUS" value="1" checked>通过
								<input type="radio" name="CHECK_STATUS" value="2">不通过
							</td>
						</tr>
						<tr>
							<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font>审核意见</th>
							<td>
								<textarea class="eve-textarea validate[required]" rows="3" cols="200" 
									name="CHECK_OPINION"  style="width:98.6%;height:75px;"></textarea>
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${recallInfo.CHECK_STATUS == '1' }">
					<table cellpadding="0" cellspacing="0" class="syj-table2">
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">审核人</th>
							<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
								class="eve-input" value="${recallInfo.CHECK_OPERATOR}" name="CHECK_OPERATOR" readonly/>
							</td>
						</tr>
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">审核时间</th>
							<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
								class="eve-input" value="${recallInfo.CHECK_TIME}" name="CHECK_TIME" readonly/>
							</td>
						</tr>
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">通过意见</th>
							<td>
								<textarea class="eve-textarea " rows="3" cols="200" name="CHECK_OPINION" 
								  style="width:98.6%;height:75px;" readonly>${recallInfo.CHECK_OPINION}</textarea>
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${recallInfo.CHECK_STATUS == '2' }">
					<table cellpadding="0" cellspacing="0" class="syj-table2">
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">审核人</th>
							<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
								class="eve-input" value="${recallInfo.CHECK_OPERATOR}" name="CHECK_OPERATOR" readonly/>
							</td>
						</tr>
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">审核时间</th>
							<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="64" 
								class="eve-input" value="${recallInfo.CHECK_TIME}" name="CHECK_TIME" readonly/>
							</td>
						</tr>
						<tr>
							<th style="border-bottom-width: 1px;width:180px;">不通过意见</th>
							<td>
								<textarea class="eve-textarea " rows="3" cols="200" name="CHECK_OPINION" 
								  style="width:98.6%;height:75px;" readonly>${recallInfo.CHECK_OPINION}</textarea>
							</td>
						</tr>
					</table>
				</c:if>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<c:if test="${recallInfo.CHECK_STATUS == '0' }">
					<input value="确定" type="submit"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				</c:if>
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

