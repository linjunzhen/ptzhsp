<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,ztree,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("SysResForm", function(form, valid) {
			if (valid) {
				var rows = $("#urlConfigGrid").datagrid("getRows");
				if (rows.length > 0) {
					var datas = [];
					for (var i = 0; i < rows.length; i++) {
						datas.push({
							"url" : rows[i].url
						});
					}
					$("input[type='hidden'][name='RES_OPERURLJSON']").val(
							JSON.stringify(datas));
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#SysResForm").serialize();
				var url = $("#SysResForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("sysResTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_MSJW_SYSTEM_RES");
		
	});
	
	var editIndex = undefined;
	/**
	 * 结束编辑模式
	 */
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($("#urlConfigGrid").datagrid("validateRow", editIndex)) {
			$("#urlConfigGrid").datagrid("endEdit", editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 添加表信息
	 */
	function addUrl() {
		if (endEditing()) {
			$("#urlConfigGrid").datagrid("appendRow", {});
			editIndex = $("#urlConfigGrid").datagrid("getRows").length - 1;
			$("#urlConfigGrid").datagrid("selectRow", editIndex).datagrid(
					"beginEdit", editIndex);
		}
	}

	function removeUrl() {
		if (editIndex == undefined) {
			return
		}
		$("#urlConfigGrid").datagrid("cancelEdit", editIndex).datagrid(
				"deleteRow", editIndex);
		editIndex = undefined;
	}

	function confirmUrl() {
		if (endEditing()) {
			$("#urlConfigGrid").datagrid("acceptChanges");
		}
	}

	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$("#urlConfigGrid").datagrid("selectRow", index).datagrid(
						"beginEdit", index);
				editIndex = index;
			} else {
				$("#urlConfigGrid").datagrid("selectRow", editIndex);
			}
		}
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SysResForm" method="post"
		action="sysResController.do?saveOrUpdate">
		<div id="SysResFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="RES_ID" value="${sysres.RES_ID}">
            <input type="hidden" name="PARENT_ID" value="${sysres.PARENT_ID}">
            <input type="hidden" name="RES_OPERURLJSON"> 
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td>
					<span style="width: 100px;float:left;text-align:right;">上级资源：</span>
					${sysres.PARENT_NAME}
					</td>
				</tr>
				
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">资源名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="124"
						class="eve-input validate[required]" value="${sysres.RES_NAME}"
						name="RES_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">资源KEY：</span>					
 					    <c:if test="${sysres.RES_ID!=null}">
						   ${sysres.RES_KEY}
						</c:if>
						<c:if test="${sysres.RES_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="28"
						class="eve-input validate[required],custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]"
						value="${sysres.RES_KEY}" id="RES_KEY" name="RES_KEY" />
						<font class="dddl_platform_html_requiredFlag">*</font>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">资源类型：</span>
						<eve:radiogroup typecode="SysResType" width="150"
							fieldname="RES_TYPE" defaultvalue="1" value="${sysres.RES_TYPE}"></eve:radiogroup>
							<font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">资源URL：</span>
						<input type="text" style="width:150px;float:left;" maxlength="252"
						class="eve-input" value="${sysres.RES_URL}" name="RES_URL" /></td>
				</tr>
				
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">资源图片路径：</span>
						<input type="text" style="width:150px;float:left;" maxlength="124"
						class="eve-input" value="${sysres.ICON_PATH}"
						name="ICON_PATH" />
					</td>
					<td><span style="width: 100px;float:left;text-align:right;">公共菜单：</span>
						<eve:radiogroup typecode="YesOrNo" width="150"
							fieldname="IS_PUBLIC" defaultvalue="0" value="${sysres.IS_PUBLIC}"></eve:radiogroup>
							<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
			<div id="urlConfigToolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-note-add"
					plain="true" onclick="addUrl();">新建</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-note-delete" plain="true"
					onclick="removeUrl();">删除</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-save" plain="true"
					onclick="confirmUrl();">确定</a>
			</div>
			<table toolbar="#urlConfigToolbar" id="urlConfigGrid"
				class="easyui-datagrid" title="URL权限配置"
				style="width:100%;height:340px;"
				data-options="singleSelect:true,url:'sysResController.do?querys&resId=${sysres.RES_ID}',method:'post',onClickRow: onClickRow">
				<thead>
					<tr>
						<th data-options="field:'url',width:'85%',align:'left',editor:'textbox'">访问控制URL</th>
					</tr>
				</thead>
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

