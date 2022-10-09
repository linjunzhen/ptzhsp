<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,json2,artdialog"></eve:resources>
<script type="text/javascript">
var editIndex = undefined;
/**
 * 结束编辑模式
 */
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#validateRuleGrid").datagrid("validateRow", editIndex)){
		var ed = $("#validateRuleGrid").datagrid("getEditor", {index:editIndex,field:'rule'});
		var DIC_NAME = $(ed.target).combobox("getText");
		$("#validateRuleGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
		$("#validateRuleGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加表信息
 */
function addValidateRule(){
	if (endEditing()){
		$("#validateRuleGrid").datagrid("appendRow",{});
		editIndex = $("#validateRuleGrid").datagrid("getRows").length-1;
		$("#validateRuleGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}

function removeValidateRule() {
	if (editIndex == undefined){return}
	$("#validateRuleGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function confirmValidateRule(){
	if (endEditing()){
		$("#validateRuleGrid").datagrid("acceptChanges");
	}
}

function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#validateRuleGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#validateRuleGrid").datagrid("selectRow", editIndex);
		}
	}
}

$(function() {
	AppUtil.initWindowForm("ValidateForm", function(form, valid) {
		if (valid) {
			var url = $("#ValidateForm").attr("action");
			var rows = $("#validateRuleGrid").datagrid("getRows");
			if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"rule":rows[i].rule,
						"msg" :rows[i].msg,
						"DIC_NAME":rows[i].DIC_NAME
					});
				}
				$("input[type='hidden'][name='RULES']").val(JSON.stringify(datas));
			}
			var formData = $("#ValidateForm").serialize();
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
						parent.$("#validateGrid").datagrid("reload");
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
	}, null)
});

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ValidateForm" method="post" action="formValidateController.do?saveOrUpdate">
	    <div  id="ValidateFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="RULE_ID" value="${validateRule.RULE_ID}">
	    <input type="hidden" name="FORM_ID" value="${validateRule.FORM_ID}">
	    <input type="hidden" name="RULES" >
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">验证规则信息</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">表单名称：</span>
					${validateRule.FORM_NAME}
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">验证名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${validateRule.COLUMN_NAME}"
					 class="eve-input validate[required]" name="COLUMN_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">验证编码：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${validateRule.COLUMN_CODE}"
					 class="eve-input validate[required]" name="COLUMN_CODE" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">
				<input
					type="checkbox" <c:if test="${validateRule.NOTNULL==1}">checked="checked"</c:if>
					value="1" name="NOTNULL" />必填项
					</span>
				</td>
			</tr>
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">必填依赖字段：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${validateRule.DEPENDENCE}"
					 class="eve-input" name="DEPENDENCE" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">依赖字段值：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${validateRule.DEPENDENCE_VALUE}"
					 class="eve-input" name="DEPENDENCE_VALUE" /> 
				</td>
				
			</tr>
			
		</table>
		<div id="validateRuleToolbar" style="width: 100%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-note-add"
				plain="true" onclick="addValidateRule();">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-note-delete" plain="true"
				onclick="removeValidateRule();">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
				onclick="confirmValidateRule();">确定</a>
		</div>
		<table toolbar="#validateRuleToolbar" id="validateRuleGrid"  style="width: 100%;height: 350px;overflow: auto;"
		class="easyui-datagrid" title="验证规则配置" 
			data-options="singleSelect:true,url:'formValidateController.do?ruleInfo&RULE_ID=${validateRule.RULE_ID}',method:'post',onClickRow: onClickRow">
			<thead>
				<tr>
					<th data-options="field:'rule',width:'50%',align:'left',
					    formatter:function(value,row){
					        return row.DIC_NAME;
						},
					    editor:{
							type:'combobox',
							options:{
							    editable:false,
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								url:'dictionaryController.do?load&typeCode=ValidateRegex',
								required:true,
								onSelect:function(rec){
									$('#validateRuleGrid').datagrid('getEditor', {index:editIndex,field:'msg'}).target.textbox('setValue',rec.DIC_DESC);
									$('#validateRuleGrid').datagrid('getEditor', {index:editIndex,field:'DIC_NAME'}).target.textbox('setValue',rec.DIC_NAME);
								}
							}
						}
					"
					>验证规则</th>
					<th data-options="field:'DIC_NAME',width:'25%',align:'left',editor:'textbox',hidden:true">规则名称</th>
					<th data-options="field:'msg',width:'50%',align:'left',editor:'textbox'">提示信息</th>
				</tr>
			</thead>
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
