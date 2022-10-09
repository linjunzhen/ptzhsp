
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">
	var editIndex = undefined;
	/**
	 * 结束编辑模式
	 */
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($("#paramConfGrid").datagrid("validateRow", editIndex)){
			$("#paramConfGrid").datagrid("endEdit", editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$("#paramConfGrid").datagrid("selectRow", index)
						.datagrid("beginEdit", index);
				editIndex = index;
			} else {
				$("#paramConfGrid").datagrid("selectRow", editIndex);
			}
		}
	}
	
	function addParam(){
		if (endEditing()){
			$("#paramConfGrid").datagrid("appendRow",{});
			editIndex = $("#paramConfGrid").datagrid("getRows").length-1;
			$("#paramConfGrid").datagrid("selectRow", editIndex)
					.datagrid("beginEdit", editIndex);
		}
	}
	
	function delParam() {
		if (editIndex == undefined){
			alert("进入行编辑状态的时候才可以删除!");
			return;
		}
		$("#paramConfGrid").datagrid("cancelEdit", editIndex)
				.datagrid("deleteRow", editIndex);
		editIndex = undefined;
	}

	$(function() {
		AppUtil.initWindowForm("FlowFormForm", function(form, valid) {
			if (valid) {
				if (endEditing()){
					var rows = $("#paramConfGrid").datagrid("getRows");
					var datas = [];
					for(var i = 0;i<rows.length;i++){
						datas.push({
							"PARAM_NAME":rows[i].PARAM_NAME,
							"PARAM_VALUE":rows[i].PARAM_VALUE
						});
					}
					var PARAM_JSON = JSON.stringify(datas);
					$("input[name='PARAM_JSON']").val(PARAM_JSON);
				}else{
					alert("数据无效,请重新输入!");
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#FlowFormForm").serialize();
				var url = $("#FlowFormForm").attr("action");
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
							parent.$("#flowFormGrid").datagrid("reload");
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
		}, "JBPM6_FLOWFORM");

	});
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="FlowFormForm" method="post"
		action="flowFormController.do?saveOrUpdate">
		<div id="FlowFormFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="FORM_ID" value="${flowForm.FORM_ID}">
			<input type="hidden" name="PARAM_JSON" >

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本配置</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">表单类别：</span>
					    <eve:eveselect clazz="eve-input validate[required]" dataParams=" "
					         dataInterface="flowTypeService.findForSelect" defaultEmptyText="请选择表单类别"
					         value="${flowForm.TYPE_ID}" name="TYPE_ID">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td><span style="width: 100px;float:left;text-align:right;">表单的类型：</span>
					    <eve:eveselect clazz="eve-input validate[required]" dataParams="FlowFormType"
					         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择表单类型"
					         value="${flowForm.FORM_TYPE}" name="FORM_TYPE">
					    </eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">表单名称：</span>
						<input type="text" style="width:150px;float:left;" maxlength="30"
						class="eve-input validate[required]" value="${flowForm.FORM_NAME}"
						name="FORM_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">表单KEY：</span>
					    <c:if test="${flowForm.FORM_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="60"
						class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
						value="${flowForm.FORM_KEY}" id="FORM_KEY" name="FORM_KEY" />
						</c:if>
						<c:if test="${flowForm.FORM_ID!=null}">
						    ${flowForm.FORM_KEY}
						 </c:if>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 100px;float:left;text-align:right;">表单的URL：</span>
						<input type="text" style="width:450px;float:left;" maxlength="126"
						class="eve-input validate[required]" value="${flowForm.FORM_URL}" name="FORM_URL" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
			
			<div id="paramConfToolBar" style="width: 100%;">
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton"
									iconCls="eicon-plus" plain="true"
									onclick="addParam();">新增参数</a> 
								<a href="#"
									class="easyui-linkbutton"
									iconCls="eicon-trash-o" plain="true"
									onclick="delParam();">删除参数</a> 
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="paramConfGrid" fitcolumns="true" toolbar="#paramConfToolBar"
			checkonselect="false" style="width:100%;height:auto;"
			selectoncheck="false" fit="true" border="false"
			data-options="autoSave:true,onClickRow:onClickRow,method:'post',url:'flowFormController.do?paramjson&formId=${flowForm.FORM_ID}'"
			>
			<thead>
				<tr>
					<th data-options="field:'PARAM_NAME',width:'100',align:'left',
					    editor:{
							type:'validatebox',options:{required:true,validType:'length[1,200]'}
						}
					"
					>参数名称</th>
					<th data-options="field:'PARAM_VALUE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,200]'}}" width="100" >参数缺省值</th>
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

