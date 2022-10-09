<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,ztree,json2"></eve:resources>
<script type="text/javascript">
	function showSelectDICTYPE_IDTree() {
		var treeObj = $("#ADVI_NAME");
		var treeOffset = $("#ADVI_NAME").offset();
		$("#DICTYPE_IDTreeContent").css({
			left : (treeOffset.left) + "px",
			top : (treeOffset.top + treeObj.outerHeight()) + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onDICTYPE_IDTreeBodyDown);
	}
	
	function onSelectDICTYPE_IDTreeClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("DICTYPE_IDTree");
		var selectNode = zTree.getSelectedNodes()[0];
		$("#ADVI_NAME").attr("value", selectNode.name);
		$("input[name='ADVI_CODE']").attr("value", selectNode.TYPE_CODE);
		$("input[name='ADVI_PATH']").attr("value", selectNode.PATH);
		hideDICTYPE_IDSelectTree();
	}
	
	function onDICTYPE_IDTreeBodyDown(event) {
		if (!(event.target.id == "ADVI_NAME"
				|| event.target.id == "DICTYPE_IDTreeContent" || $(event.target)
				.parents("#DICTYPE_IDTreeContent").length > 0)) {
			hideDICTYPE_IDSelectTree();
		}
	}
	
	function hideDICTYPE_IDSelectTree() {
		$("#DICTYPE_IDTreeContent").fadeOut("fast");
		$("body").unbind("mousedown", onDICTYPE_IDTreeBodyDown);
	}
	
	var DICTYPE_IDSetting = {
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_MSJW_SYSTEM_DICTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PARENT_ID,PATH,TYPE_CODE",
				"rootParentId" : "0",
				"dicTypeCode" : "PTXZQH",
				"isShowRoot" : "false",
				"rootName" : "平潭市区划"
			}
		},
		callback : {
			onClick : onSelectDICTYPE_IDTreeClick
		}
	};


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
		}, "T_MSJW_SYSTEM_DEPARTMENT","","${department.DEPART_ID}");
		$.fn.zTree.init($("#DICTYPE_IDTree"), DICTYPE_IDSetting);
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DepartmentForm" method="post"
		action="departmentController.do?saveOrUpdate">
		<div id="DepartmentFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="DEPART_ID" value="${department.DEPART_ID}">
		<input type="hidden" name="PARENT_ID" value="${department.PARENT_ID}">
		<input type="hidden" name="ADVI_CODE" value="${department.ADVI_CODE}">
		<input type="hidden" name="ADVI_PATH" value="${department.ADVI_PATH}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">上级机构：</span>
					${department.PARENT_NAME}</td>
				<td><span style="width: 130px;float:left;text-align:right;">机构名称：</span>
					<input type="text" style="width:150px;float:left;" maxlength="50"
					class="eve-input validate[required]"
					value="${department.DEPART_NAME}" name="DEPART_NAME" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>

			<tr>
				<td><span style="width: 100px;float:left;text-align:right;">机构编码：</span>
					<c:if test="${department.DEPART_ID!=null}">
					   ${department.DEPART_CODE}
					</c:if> <c:if test="${department.DEPART_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="18"
							class="eve-input validate[required],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
							value="${department.DEPART_CODE}" id="DEPART_CODE"
							name="DEPART_CODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
				<td><span style="width: 130px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" style="width:150px;float:left;" maxlength="18"
						class="eve-input validate[],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
						value="${department.USC}" id="USC"
						name="USC" />
				</td>
			</tr>

			<tr>
				<td colspan="2"><span style="width: 130px;float:left;text-align:right;">统一编码（平潭）：</span>
					<input type="text" style="width:150px;float:left;" maxlength="18"
						class="eve-input validate[],custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]"
						value="${department.UNICODE}" id="UNICODE"
						name="UNICODE" />
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
	
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DICTYPE_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:160px;height: 150px"
			id="DICTYPE_IDTree"></ul>
	</div>

</body>

