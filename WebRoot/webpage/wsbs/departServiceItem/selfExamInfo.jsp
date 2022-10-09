<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("SelfEaxmForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#SelfEaxmForm").serialize();
				var url = $("#SelfEaxmForm").attr("action");
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
		},null);
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SelfEaxmForm" method="post"
		action="departServiceItemController.do?saveOrUpdateSelfEaxm">
		<div id="SelfEaxmFormDiv" data-options="region:'center',split:true,border:false">
			<%--==========隐藏域部分开始 ===========--%>
			<input type="hidden" name="RECORD_ID" value="${examinationInfo.RECORD_ID}">
			<input type="hidden" name="ITEM_ID" value="${examinationInfo.ITEM_ID}">
			<input type="hidden" name="BUS_HANDLE_TYPE" value="${examinationInfo.BUS_HANDLE_TYPE}">
			<%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">标题：</span>						
						<input type="text" style="width:150px;float:left;" maxlength="32"
								class="eve-input validate[required]"
								value="${examinationInfo.EXAM_TITLE}" name="EXAM_TITLE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">类型：</span>
						<eve:eveselect clazz="eve-input validate[required]"
							dataParams="examType" value="${examinationInfo.EXAM_TYPE}"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择类型" name="EXAM_TYPE">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">内容：</span>
						<textarea class="eve-textarea validate[required]" maxlength="512"
							style="width: 400px;height:80px;" name="EXAM_CONTENT">${examinationInfo.EXAM_CONTENT}</textarea>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">是否启用：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="1"
							value="${examinationInfo.EXAM_STATUS}" fieldname="EXAM_STATUS">
						</eve:radiogroup>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>
