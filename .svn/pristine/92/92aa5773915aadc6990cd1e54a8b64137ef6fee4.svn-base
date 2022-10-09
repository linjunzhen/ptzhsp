<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
var editIndex = undefined;
/**
 * 结束编辑模式
 */
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#buttonrightConfGrid").datagrid("validateRow", editIndex)){
		var ed = $("#buttonrightConfGrid").datagrid("getEditor", {index:editIndex,field:'IS_AUTH'});
		var DIC_NAME = $(ed.target).combobox("getText");
		$("#buttonrightConfGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
		$("#buttonrightConfGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#buttonrightConfGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#buttonrightConfGrid").datagrid("selectRow", editIndex);
		}
	}
}

function addFlowEvent(){
	if (endEditing()){
		$("#buttonrightConfGrid").datagrid("appendRow",{});
		editIndex = $("#buttonrightConfGrid").datagrid("getRows").length-1;
		$("#buttonrightConfGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}

function delFlowEvent() {
	if (editIndex == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#buttonrightConfGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function confirmButtonRight(){
	if (endEditing()){
		$("#buttonrightConfGrid").datagrid("acceptChanges");
		var rows = $("#buttonrightConfGrid").datagrid("getRows");
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"BUTTON_KEY":rows[i].BUTTON_KEY,
				"IS_AUTH":rows[i].IS_AUTH,
				"AUTH_INTERFACECODE":rows[i].AUTH_INTERFACECODE
			});
		}
		var buttonRightJson = JSON.stringify(datas);
	    var subObj = {};
	    subObj.BUTTONRIGHT_JSON = buttonRightJson;
	    subObj.DEFID = $("#BUTTON_DEFID").val();
	    subObj.FLOWVERSION = $("#BUTTON_FLOWVERSION").val();
	    subObj.NODENAME = $("#BUTTON_NODENAME").val();
	    var formData = $.param(subObj);
	    AppUtil.ajaxProgress({
			url : "buttonRightController.do?saveOrUpdate",
			params : formData,
			callback : function(resultJson) {
				if(resultJson.success){
				    parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
	}else{
		alert("数据无效,请重新输入!");
	}
}

$(function(){
	var nodeName = $("input[name='NODE_NAME']").val();
	$("#buttonrightConfGrid").datagrid({
		url:"buttonRightController.do?datagrid",
		queryParams: {
			"nodeName": nodeName,
			"defId": '${nodeData.defId}',
			"flowVersion": '${nodeData.flowVersion}'
		}
	});
});
</script>

<div class="easyui-layout" fit="true">
    <input type="hidden" id="BUTTON_DEFID" value="${nodeData.defId}"/>
    <input type="hidden" id="BUTTON_FLOWVERSION" value="${nodeData.flowVersion}"/>
    <input type="hidden" id="BUTTON_NODENAME" value="${nodeData.nodeName}"/>
	<div region="center">
		<!-- =========================查询面板开始=========================-->
		<div id="buttonrightConfToolBar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
								onclick="confirmButtonRight();">保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table  rownumbers="true" pagination="false"
			id="buttonrightConfGrid" fitcolumns="true" toolbar="#buttonrightConfToolBar"
			idfield="BUTTON_KEY" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			onClickRow="onClickRow"
			data-options="autoSave:true,onClickRow: onClickRow"
			>
			<thead>
				<tr>
					<th data-options="field:'BUTTON_KEY',hidden:true" width="80">BUTTON_KEY</th>
					<th data-options="field:'BUTTON_NAME',align:'left'" width="100" >按钮名称</th>
					<th data-options="field:'IS_AUTH',width:'30',align:'left',
					    formatter:function(value,row){
					        if(value=='1'){
					            return '是';
					        }else{
					            return '否';	
					        }
						},
					    editor:{
							type:'combobox',
							options:{
							    editable:false,
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								url:'dictionaryController.do?load&typeCode=FlowButtonAuth',
								required:true
							}
						}
					"
					>是否授权</th>
					<th data-options="field:'AUTH_INTERFACECODE',align:'left',editor:{type:'validatebox',options:{required:false,validType:'length[1,200]'}}" width="100" >权限干预接口</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
