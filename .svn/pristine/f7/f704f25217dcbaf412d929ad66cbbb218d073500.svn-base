<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("BusClassForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusClassForm").serialize();
				var url = $("#BusClassForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							art.dialog.opener.addBusunessClass(resultJson.recordId,resultJson.busclassName);
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
							$("input[type='submit']").attr("disabled", false);
						}
					}
				});
			}
		}, "T_WSBS_SERVICEITEM_BUSCLASS");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="BusClassForm" method="post"
		action="applyMaterController.do?saveOrUpdateBusclass">
		<div id="BusClassFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ITEM_ID" value="${itemId}">
			<input type="hidden" name="RECORD_ID" value="${busRecord.RECORD_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td class="dddl-legend"><div class="eui-dddltit"><a>下一级事项信息</a></div></td>
				</tr>
				<tr>
					<td ><span style="width: 115px;float:left;text-align:right;">名称：</span>
						<textarea rows="1" cols="1" class="eve-textarea validate[required,maxSize[98]]"
                            style="width: 250px;height:95px;margin-top: 4px;margin-bottom: 7px;"  name="BUSCLASS_NAME">${busRecord.BUSCLASS_NAME}</textarea>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td>
						<span style="width: 115px;float:left;text-align:right;">是否前台展示：</span>
						<eve:eveselect clazz="tab_text" dataParams="YesOrNo"
									   dataInterface="dictionaryService.findDatasForSelect"
									   defaultEmptyValue="1" name="IS_SHOWNET" id="IS_SHOWNET" value="${busRecord.IS_SHOWNET}">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons" >
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

