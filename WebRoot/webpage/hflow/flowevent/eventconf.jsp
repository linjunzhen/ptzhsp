<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
var editIndex = undefined;
/**
 * 结束编辑模式
 */
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#eventConfGrid").datagrid("validateRow", editIndex)){
		var ed = $("#eventConfGrid").datagrid("getEditor", {index:editIndex,field:'EVENT_TYPE'});
		var DIC_NAME = $(ed.target).combobox("getText");
		$("#eventConfGrid").datagrid("getRows")[editIndex]["DIC_NAME"] = DIC_NAME;
		$("#eventConfGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#eventConfGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#eventConfGrid").datagrid("selectRow", editIndex);
		}
	}
}

function addFlowEvent(){
	if (endEditing()){
		$("#eventConfGrid").datagrid("appendRow",{});
		editIndex = $("#eventConfGrid").datagrid("getRows").length-1;
		$("#eventConfGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}

function delFlowEvent() {
	if (editIndex == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#eventConfGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function confirmFlowEvent(){
	if (endEditing()){
		$("#eventConfGrid").datagrid("acceptChanges");
		var rows = $("#eventConfGrid").datagrid("getRows");
		var tableInfoJson = "";
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"EVENT_TYPE":rows[i].EVENT_TYPE,
				"EVENT_CODE":rows[i].EVENT_CODE
			});
		}
		var flowEventJson = JSON.stringify(datas);
	    var subObj = {};
	    subObj.FLOWEVENT_JSON = flowEventJson;
	    subObj.EVENT_DEFID = $("#EVENT_DEFID").val();
	    subObj.EVENT_FLOWVERSION = $("#EVENT_FLOWVERSION").val();
	    subObj.EVENT_NODENAME = $("#EVENT_NODENAME").val();
	    var formData = $.param(subObj);
	    AppUtil.ajaxProgress({
			url : "flowEventController.do?saveOrUpdate",
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
	$("#eventConfGrid").datagrid({
		url:"flowEventController.do?datagrid",
		queryParams: {
			"Q_T.NODE_NAME_EQ":nodeName,
			"Q_T.DEF_ID_EQ": '${nodeData.defId}',
			"Q_T.DEF_VERSION_EQ": '${nodeData.flowVersion}'
		}
	});
});
</script>

<div class="easyui-layout" fit="true">
    <input type="hidden" id="EVENT_DEFID" value="${nodeData.defId}"/>
    <input type="hidden" id="EVENT_FLOWVERSION" value="${nodeData.flowVersion}"/>
    <input type="hidden" id="EVENT_NODENAME" value="${nodeData.nodeName}"/>
	<div region="center">
		<!-- =========================查询面板开始=========================-->
		<div id="eventConfToolBar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								iconCls="icon-note-add" plain="true"
								onclick="addFlowEvent();">新增事件</a> 
							<a href="#"
								class="easyui-linkbutton"
								iconCls="icon-note-delete" plain="true"
								onclick="delFlowEvent();">删除事件</a> 
							<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
								onclick="confirmFlowEvent();">保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="eventConfGrid" fitcolumns="true" toolbar="#eventConfToolBar"
			idfield="EVENT_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			onClickRow="onClickRow"
			data-options="autoSave:true,method:'post',onClickRow: onClickRow"
			>
			<thead>
				<tr>
					<th data-options="field:'EVENT_ID',hidden:true" width="80">EVENT_ID</th>
					<th data-options="field:'EVENT_TYPE',width:'100',align:'left',
					    formatter:function(value,row){
					        if(value=='1'){
					            return '前置事件';
					        }else if(value=='2'){
					            return '缺省业务数据存储';					           
					        }else if(value=='3'){
					            return '后置事件';	
					        }else{
					            return '决策事件';	
					        }
						},
					    editor:{
							type:'combobox',
							options:{
							    editable:false,
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								url:'dictionaryController.do?load&typeCode=FlowEventType',
								required:true
							}
						}
					"
					>事件类型</th>
					<th data-options="field:'EVENT_CODE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,200]'}}" width="100" >事件代码</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
