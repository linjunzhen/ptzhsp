<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">


	function changeTaskHandler() {
		var exeId = $("#EFLOW_EXEID").val();
		var taskId = AppUtil.getEditDataGridRecord("FlowTaskManagerGrid");
		if (taskId) {
			$.dialog.open("sysUserController.do?selector&allowCount=1", {
				title : "人员选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectUserInfo = art.dialog.data("selectUserInfo");
					if (selectUserInfo) {
						AppUtil.ajaxProgress({
							url : "flowTaskController.do?changeHandler&exeId="+exeId,
							params : {
								taskId :taskId,
								userId: selectUserInfo.userIds
							},
							callback : function(resultJson) {
								if (resultJson.success == true) {
									parent.art.dialog({
										content : resultJson.msg,
										icon : "succeed",
										time : 3,
										ok : true
									});
									$("#FlowTaskManagerGrid")
											.datagrid("reload");
									parent.$("#FlowMonitorGrid").datagrid(
											"reload");
								} else {
									art.dialog({
										content : resultJson.msg,
										icon : "error",
										ok : true
									});
								}
							}
						});
					}
				}
			}, false);
		}
	}

	/**
	 * 删除流程任务
	 */
	function deleteTask() {
		var exeId = $("#EFLOW_EXEID").val();
		AppUtil.deleteDataGridRecord("flowTaskController.do?multiDel&exeId="
				+ exeId, "FlowTaskManagerGrid", function() {
			$("#FlowTaskManagerGrid").datagrid("reload");
			parent.$("#FlowMonitorGrid").datagrid("reload");
		});
	};
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
<div class="easyui-layout" fit="true">
	<div region="center">
	    <input type="hidden" id="EFLOW_EXEID" name="EFLOW_EXEID" value="${exeId}">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowTaskManagerToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_FlowDef"
								iconCls="eicon-send-o" plain="true"
								onclick="changeTaskHandler();">转发任务</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_FlowDef"
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteTask();">删除任务</a> 
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="FlowTaskManagerGrid" fitcolumns="true" toolbar="#FlowTaskManagerToolbar"
			method="post" idfield="TASK_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="flowTaskController.do?managerData&exeId=${exeId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="100">环节名称</th>
					<th data-options="field:'ASSIGNER_NAME',align:'left'" width="100">任务审核人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="100">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>
