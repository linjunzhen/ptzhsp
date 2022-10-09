<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="msthemecompatible" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>

<title>平潭综合实验区综合审批平台</title>
<link rel="stylesheet" type="text/css" href="webpage/main/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/eveflow.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/ztree-3.5/css/demo.css"/>
<script type="text/javascript" src="plug-in/ztree-3.5/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//获取流程任务ID
		var taskId = row.TASK_ID;
		//获取流程任务状态
		var taskStatus = row.TASK_STATUS;
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}
	
	/**
	 * 显示流程实例信息对话框
	 */
	function showExecutionWindow(entityId) {
		$.dialog.open("executionController.do?info&entityId=" + entityId, {
			title : "流程实例信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	};
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="countDetailGrid" fitcolumns="true" method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="statistCommercialController.do?banJSDetailData&Q_T.OPERATOR_ID_EQ=${operatorId}
			&Q_T.RUN_STATUS_NEQ=0
			&Q_T.CREATE_TIME_GE=${beginTime}&Q_T.CREATE_TIME_LE=${endTime}
			&Q_T.END_TIME_GE=${beginTime2}&Q_T.END_TIME_LE=${endTime2}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TASK_ID',hidden:true" width="80">TASK_ID</th>
					<th data-options="field:'EXE_ID',align:'left'" width="100">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="250" formatter="formatSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',hidden:true" width="100">环节名称</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="60" formatter="FlowUtil.formatRunStatus">任务状态</th>
				<!-- 	<th data-options="field:'CREATOR_NAME',align:'left'" width="100">发起人</th>  -->
					<th data-options="field:'ASSIGNER_NAME',hidden:true" width="100">审核人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="150">申请时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
