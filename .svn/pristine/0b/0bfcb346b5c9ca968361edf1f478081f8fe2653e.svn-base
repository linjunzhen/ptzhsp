<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
		/**
		if(taskStatus!="1"){
			href += "executionController.do?goDetail&exeId="+exeId;
		}else{
			href += "executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId;
		}**/
		href += "executionController.do?goDetail&exeId="+exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	
	function formatLeftDay(val,row){
		if(val=="0"){
			return "<font color='#fa5800'><b>今天截止</b></font>";
		}else if(val=="-1"){
			return "<font color='#ff4b4b'><b>已超期</b></font>";
		}else if(val=="1"){
			return "<font color='#fa5800'><b>剩余"+val+"个工作日</b></font>";
		}else if(val!="-2"){
			return "<font color='#0368ff'><b>剩余"+val+"个工作日</b></font>";
		}else{
			return "无限制";
		}
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

	$(document).ready(function() {
		var start1 = {
			elem : "#NeedMeHandleT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#NeedMeHandleT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);
	});
	
	function showBjxxGridRecord() {
		var rows = $("#deptItemViewGrid").datagrid("getChecked");
        if(rows.length==0){
            art.dialog({
                content: "请至少选择一条记录!",
                icon:"warning",
                ok: true
            });         
            return null;
        }else if(rows.length>1){
            art.dialog({
                content: "只能选择一条被操作的记录!",
                icon:"warning",
                ok: true
            });
            return null;
        }else{
        	//alert("BJXX_ID:"+rows[0].BJXX_ID+",BACKOPINION:"+rows[0].BACKOPINION+",APPLY_STATUS:"+rows[0].APPLY_STATUS)
        	thsm(rows[0].BJXX_ID,rows[0].BACKOPINION,rows[0].APPLY_STATUS);
        }
        
    }
	function thsm(bjid,backinfo,applyStatus){
        if(bjid!=''&&applyStatus=='7'){
            $.dialog.open("webSiteController/thbjxx.do?&BJID="+bjid, {
                title : "退回补件信息",
                width : "900px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
        }else{
        	alert('该待办无补件要求');
        }
    }
	
	
	//空数据，横向滚动
	$('#deptItemViewGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="deptItemViewToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->

			<form action="#" name="ExecutionForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">发起人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.CREATOR_NAME_LIKE" /></td>
						    <td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.SUBJECT_LIKE" />
							
							</td><td style="width:68px;text-align:right;">剩余时间</td>
							<td style="width:135px;padding-left:4px;">
								<select class="easyui-combobox" name="LEFTDAYRANGE" id="leftDayRange" >
								    <option value="">----请选择----</option>
								    <option value="1">剩余一天以上</option>
									<option value="0">即将截止</option>
									<option value="-1">已超期</option>
						    	</select>
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandleT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandleT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td style="width:68px;text-align:right;">部门</td>
							<td style="width:135px;"><input class="eve-input"
															type="text" maxlength="20" style="width:128px;"
															name="Q_D.DEPART_NAME_LIKE" /></td>
							<td colspan="3"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('deptItemViewToolbar','deptItemViewGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ExecutionForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="deptItemViewGrid" fitcolumns="true" toolbar="#deptItemViewToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="deptItemViewController.do?datagrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'LEFT_WORKDAY',align:'left'" width="10%" formatter="formatLeftDay">办理时限</th>
					<th data-options="field:'TASK_STATUS',align:'left'" width="8%" formatter="FlowUtil.formatRunStatus">任务状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="10%">环节名称</th>
					<th data-options="field:'ASSIGNER_NAME',align:'left'" width="10%">审核人</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="10%">事项部门</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="10%">经办人</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
