
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FlowMappedForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FlowMappedForm").serialize();
				var url = $("#FlowMappedForm").attr("action");
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
		}, "T_WSBS_FLOWYS");

	});

	function bindFlowNodes() {
		$.dialog
				.open(
						"flowMappedController.do?selector&defId=${flowMapped.DEF_ID}&version=${flowMapped.version}",
						{
							title : "节点选择器",
							width : "600px",
							lock : true,
							resize : false,
							height : "500px",
							close : function() {
								var selectFlowNodesInfo = art.dialog
										.data("selectFlowNodesInfo");
								if (selectFlowNodesInfo) {
									$("textarea[name='DEF_NAMES']").val(
											selectFlowNodesInfo.nodeNames);
									art.dialog.removeData("selectFlowNodesInfo");
								}
							}
						}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="FlowMappedForm" method="post"
		action="flowMappedController.do?saveOrUpdate">
		<div id="FlowMappedFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="DEF_ID" value="${flowMapped.DEF_ID}">
			<input type="hidden" name="YS_ID" value="${flowMapped.YS_ID}">
			<input type="hidden" name="version" value="${flowMapped.version}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>映射配置管理</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">映射名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="200"
						class="eve-input validate[required]" value="${flowMapped.YS_NAME}"
						name="YS_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">映射流程节点名称：</span>
						<textarea rows="5" cols="5"
							class="eve-textarea validate[required]" style="width: 400px"
							name="DEF_NAMES" readonly="readonly" onclick="bindFlowNodes()">${flowMapped.DEF_NAMES}</textarea><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">排序值：</span>
						<input type="text" style="width:150px;float:left;" maxlength="4"
						class="eve-input validate[required]" value="${flowMapped.YS_CN}"
						name="YS_CN" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
                <td><span style="width: 120px;float:left;text-align:right;">映射类型：</span>
                    <eve:eveselect clazz="eve-input validate[required]" dataParams="lcyspz"
                        dataInterface="dictionaryService.findDatasForSelect" value="${flowMapped.YS_TYPE}" 
                        defaultEmptyText="==选择映射类型==" name="YS_TYPE"></eve:eveselect><font
                    class="dddl_platform_html_requiredFlag">*</font></td>
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

