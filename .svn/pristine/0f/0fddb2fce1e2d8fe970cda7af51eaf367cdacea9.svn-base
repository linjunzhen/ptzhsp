<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
  

	function deleteFlowTypeGridRecord() {
		AppUtil.deleteDataGridRecord("flowConfigTypeController.do?multiDel",
				"FlowTypeGrid");
	};

	function editFlowTypeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FlowTypeGrid");
		
		if (entityId) {
			showFlowTypeWindow(entityId);
		}
	}

	function showFlowTypeWindow(entityId) {
		$.dialog.open("flowConfigTypeController.do?info&entityId=" + entityId, {
			title : "流程类型信息",
			width : "550px",
			lock : true,
			resize : false,
			height : "350px",
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("FlowTypeToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowTypeToolbar">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FlowType"
								iconcls="eicon-plus" plain="true"
								onclick="showFlowTypeWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_FlowType"
								iconcls="eicon-pencil" plain="true"
								onclick="editFlowTypeGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_FlowType"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteFlowTypeGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FlowTypeGrid" fitcolumns="true" toolbar="#FlowTypeToolbar"
			method="post" idfield="TYPE_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="flowConfigTypeController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="25%">审批流程名称</th>
					<th data-options="field:'SPLCLX',align:'left'" width="15%">流程字典编码</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="55%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>