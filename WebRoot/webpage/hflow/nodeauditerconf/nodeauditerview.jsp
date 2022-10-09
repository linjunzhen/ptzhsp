<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
/**
 * 删除流程表单列表记录
 */
function deleteNodeAuditerDataGridRecord() {
	AppUtil.deleteDataGridRecord("nodeAuditerController.do?multiDel",
			"nodeAuditerGrid");
};
/**
 * 编辑流程表单列表记录
 */
function editNodeAuditerGridRecord() {
	var AUDITER_ID = AppUtil.getEditDataGridRecord("nodeAuditerGrid");
	if (AUDITER_ID) {
		showNodeAuditerWindow(AUDITER_ID);
	}
}

/**
 * 显示流程表单信息对话框
 */
function showNodeAuditerWindow(entityId) {
	var defId = $("input[name='defId']").val();
	var nodeName = $("input[name='nodeName']").val();
	var flowVersion = $("input[name='flowVersion']").val();
	$.dialog.open("nodeAuditerController.do?info&entityId="+entityId+"&defId="+defId+"&nodeName="+encodeURIComponent(nodeName)+"&flowVersion="+flowVersion, {
		title : "审核人信息",
		width : "550px",
		height : "300px",
		lock : true,
		resize : false,
		close: function () {
			var saveNodeAuditResult = art.dialog.data("saveNodeAuditResult");
			if(saveNodeAuditResult){
				if(saveNodeAuditResult.operSuccess){
					$("#nodeAuditerGrid").datagrid("reload");
				}
			}
		}
	}, false);

};

$(function(){
	var nodeName = $("input[name='NODE_NAME']").val();
	$("#nodeAuditerGrid").datagrid({
		url:"nodeAuditerController.do?datagrid",
		queryParams: {
			"Q_T.NODE_NAME_EQ": nodeName,
			"Q_T.DEF_ID_EQ": '${nodeData.defId}',
			"Q_T.DEF_VERSION_EQ": '${nodeData.flowVersion}'
		}
	});
});
</script>

<div class="easyui-layout" fit="true">
	<div region="center">
	    <input type="hidden" name="defId" value="${nodeData.defId}">
	    <input type="hidden" name="flowVersion" value="${nodeData.flowVersion}">
	    <input type="hidden" name="nodeName" value="${nodeData.nodeName}">
		<!-- =========================查询面板开始=========================-->
		<div id="nodeAuditerToolBar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_FlowDef"
								iconCls="icon-note-add" plain="true"
								onclick="showNodeAuditerWindow();">新建审核人</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_FlowDef"
								iconCls="icon-note-edit" plain="true"
								onclick="editNodeAuditerGridRecord();">编辑审核人</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_FlowDef"
								iconCls="icon-note-delete" plain="true"
								onclick="deleteNodeAuditerDataGridRecord();">删除审核人</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="nodeAuditerGrid" fitcolumns="true" toolbar="#nodeAuditerToolBar"
			method="post" idfield="AUDITER_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'AUDITER_ID',hidden:true" width="80">AUDITER_ID</th>
					<th data-options="field:'NEXT_NODE_NAME',align:'left'" width="100">下一环节</th>
					<th data-options="field:'CONFIG_NAME',align:'left'" width="100" >审核人控件</th>
					<th data-options="field:'DIC_NAME',align:'left'" width="100" >审核人规则</th>
					<th data-options="field:'VARS_VALUE',align:'left'" width="100" >变量值</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
