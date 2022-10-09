<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,ztree,json2"></eve:resources>
<script type="text/javascript">
	/**
	 * 显示对接配置信息对话框
	 */
	 function showSelectDeparts(){
		parent.$.dialog.open("departmentController/selector.do?allowCount=1&checkCascadeY=&checkCascadeN=", {
			title : "组织机构选择器",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					saveSelectDept(selectDepInfo.departIds,selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
 	function saveSelectDept(departIds,departNames){
		$("#DEPART_NAME").val(departNames);
		$("#DEPART_ID").val(departIds);
	}
	$(function() {
		AppUtil.initWindowForm("DepartmentForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#DepartmentForm").serialize();
				var url = $("#DepartmentForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("departmentTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_BESPEAK_ONLINE_CONFIG");
		$.fn.zTree.init($("#DICTYPE_IDTree"), DICTYPE_IDSetting);
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="DepartmentForm" method="post"
		action="bespeakOnlineController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="ID" id="ID" value="${department.ID}">
		<input type="hidden" name="DEPART_ID" id="DEPART_ID" value="${department.DEPART_ID}">
		<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${department.PARENT_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">上级机构：</span>
					${department.PARENT_NAME}</td>
				<td><span style="width: 100px;float:left;text-align:right;">机构名称：</span>
					<input type="text" style="width:150px;float:left;" maxlength="50"
					class="eve-input validate[required]"
					value="${department.DEPART_NAME}" name="DEPART_NAME" id="DEPART_NAME" readonly="readonly"/><a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showSelectDeparts();">选择</a>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>

		</table>

		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
	
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DICTYPE_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
			id="DICTYPE_IDTree"></ul>
	</div>

</body>

