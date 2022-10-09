<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    function startScheduleGridRecord(){
    	AppUtil.multiOperateDataGridRecord("scheduleController.do?startJob",
		"ScheduleGrid");
    };
    function stopScheduleGridRecord(){
    	AppUtil.multiOperateDataGridRecord("scheduleController.do?stopJob",
		"ScheduleGrid");
    };
	/**
	 * 删除定时器列表记录
	 */
	function deleteScheduleGridRecord() {
		AppUtil.deleteDataGridRecord("scheduleController.do?multiDel",
				"ScheduleGrid");
	};
	/**
	 * 编辑定时器列表记录
	 */
	function editScheduleGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ScheduleGrid");
		if (entityId) {
			showScheduleWindow(entityId);
		}
	}

	/**
	 * 显示定时器信息对话框
	 */
	function showScheduleWindow(entityId) {
		$.dialog.open("scheduleController.do?info&entityId=" + entityId, {
    		title : "定时器信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"400px",
    	}, false);
	};
	
	function formatStatus(val,row){
		if(row.JOB_STATUS=="0"){
			return "<font color='#0368ff'><b>激活</b></font>";
		}else if(row.JOB_STATUS=="1"){
			return "<font color='#ff4b4b'><b>禁用</b></font>";
		}
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("ScheduleToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ScheduleToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Schedule"
								iconcls="eicon-plus" plain="true"
								onclick="showScheduleWindow();">新建</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="EDIT_Schedule"
								iconcls="eicon-pencil" plain="true"
								onclick="editScheduleGridRecord();">编辑</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteScheduleGridRecord();">删除</a>
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-check" plain="true"
								onclick="startScheduleGridRecord();">启用</a>
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-ban" plain="true"
								onclick="stopScheduleGridRecord();">禁用</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ScheduleForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">任务名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.JOB_NAME_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('ScheduleToolbar','ScheduleGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ScheduleForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ScheduleGrid" fitcolumns="false" toolbar="#ScheduleToolbar"
			method="post" idfield="JOB_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false"
			url="scheduleController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'JOB_ID',hidden:true" >JOB_ID</th>
					<th data-options="field:'JOB_NAME',align:'left'" width="25%">任务名称</th>
					<th data-options="field:'JOB_CRON',align:'left'" width="15%">任务CRON表达式</th>
					<th data-options="field:'JOB_STATUS',align:'left'" width="10%" formatter="formatStatus">任务状态</th>
					<th data-options="field:'CLASS_NAME',align:'left'" width="45%">对应CLASS名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>