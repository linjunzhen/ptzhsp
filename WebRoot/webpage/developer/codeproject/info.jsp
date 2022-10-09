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
	if ($("#tableConfigGrid").datagrid("validateRow", editIndex)){
		var ed = $("#tableConfigGrid").datagrid("getEditor", {index:editIndex,field:'isMainTable'});
		var DIC_NAME = $(ed.target).combobox("getText");
		$("#tableConfigGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
		$("#tableConfigGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加表信息
 */
function addTableInfo(){
	if (endEditing()){
		$("#tableConfigGrid").datagrid("appendRow",{});
		editIndex = $("#tableConfigGrid").datagrid("getRows").length-1;
		$("#tableConfigGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}

function removeTableInfo() {
	if (editIndex == undefined){return}
	$("#tableConfigGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function confirmTableInfo(){
	if (endEditing()){
		$("#tableConfigGrid").datagrid("acceptChanges");
	}
}

function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#tableConfigGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#tableConfigGrid").datagrid("selectRow", editIndex);
		}
	}
}

$(function() {
	AppUtil.initWindowForm("CodeProjectForm", function(form, valid) {
		if (valid) {
			var url = $("#CodeProjectForm").attr("action");
			var rows = $("#tableConfigGrid").datagrid("getRows");
			var tableInfoJson = "";
			if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"tableName":rows[i].tableName,
						"className":rows[i].className,
						"chineseName":rows[i].chineseName,
						"isMainTable":rows[i].isMainTable
					});
				}
				$("input[type='hidden'][name='TABLEINFO_JSON']").val(JSON.stringify(datas));
			} else {
				alert("请至少输入一条表信息记录!");
				return;
			}
			var formData = $("#CodeProjectForm").serialize();
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
						parent.$.fn.zTree.getZTreeObj("projectCodeTree").reAsyncChildNodes(null, "refresh");
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
	<form id="CodeProjectForm" method="post" action="codeProjectController.do?saveOrUpdate">
	    <div  id="CodeProjectFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="PROJECT_ID" value="${codeProject.PROJECT_ID}">
	    <input type="hidden" name="TABLEINFO_JSON" >
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">代码建模信息</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">建模项目名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${codeProject.PROJECT_NAME}"
					 class="eve-input validate[required]" name="PROJECT_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">大模块包名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${codeProject.PACKAGE_NAME}"
					 class="eve-input validate[required]" name="PACKAGE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">生成框架代码：</span>
				<div class="eve-chcekbox" style="width: 160px;">
				     <ul>
						<li>生成<input type="radio" name="ISGEN_CODE" checked="checked" value="1" ></li>
						<li>不生成<input type="radio" name="ISGEN_CODE" value="0"></li>
				     </ul>	
				</div>
				</td>
			</tr>
			
		</table>
		<div id="tableConfigToolbar" style="width: 100%;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-note-add"
				plain="true" onclick="addTableInfo();">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-note-delete" plain="true"
				onclick="removeTableInfo();">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
				onclick="confirmTableInfo();">确定</a>
		</div>
		<table toolbar="#tableConfigToolbar" id="tableConfigGrid"  style="width: 100%;"
		class="easyui-datagrid" title="库表配置" style="width:auto;height:auto;"
			data-options="singleSelect:true,url:'codeProjectController.do?tableInfo&PROJECT_ID=${codeProject.PROJECT_ID}',method:'post',onClickRow: onClickRow">
			<thead>
				<tr>
					<th data-options="field:'tableName',width:'25%',align:'left',editor:'textbox'">表名称</th>
					<th data-options="field:'className',width:'25%',align:'left',editor:'textbox'">类名称</th>
					<th data-options="field:'chineseName',width:'25%',align:'left',editor:'textbox'">中文名称</th>
					<th data-options="field:'isMainTable',width:'25%',align:'left',
					    formatter:function(value,row){
					        if(value=='0'){
					           return '否';
					        }else if(value=='1'){
					           return '是';
					        }else{
					           return row.DIC_NAME;
					        }
						},
					    editor:{
							type:'combobox',
							options:{
							    editable:false,
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								url:'dictionaryController.do?load&typeCode=YesOrNo',
								required:true
							}
						}
					"
					>是否主表</th>
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
