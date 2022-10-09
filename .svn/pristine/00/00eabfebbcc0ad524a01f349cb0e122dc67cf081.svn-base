<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteFdaDwspGridRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"FdaDwspGrid");
	};
	function restartFlowTaskExplain(){
		var $dataGrid = $("#FdaDwspGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var isAllowStart = true;
			var selectTaskIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				var TASK_STATUS = rowsData[i].TASK_STATUS;
				var TASK_NODENAME = rowsData[i].TASK_NODENAME;
				var TASK_ID  = rowsData[i].TASK_ID;
				if(TASK_STATUS!="-1"&&TASK_STATUS!="3"){
					isAllowStart = false;
					break;
				}
				if(i>0){
					selectTaskIds+=",";
				}
				selectTaskIds+=TASK_ID;
			}
			if(!isAllowStart){
				art.dialog({
					content: "只能选择被挂起或完成补件的流程任务进行重启!",
					icon:"warning",
				    ok: true
				});
			}else if(TASK_STATUS == -1 && TASK_NODENAME == "开始"){
				AppUtil.operateDataGridRecord("flowTaskController.do?reStart",{selectTaskIds:selectTaskIds},"FdaDwspGrid");
			}else{
				$.dialog.open("flowTaskController.do?restartFlowTaskExplain&selectTaskIds="+selectTaskIds, {
					title : "挂起说明",
					width : "450px",
					height : "300px",
					lock : true,
					resize : false
				}, false);
			}
		}
	}
	function restartFlowTask(){
		var $dataGrid = $("#FdaDwspGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var isAllowStart = true;
			var selectTaskIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				var TASK_STATUS = rowsData[i].TASK_STATUS;
				var TASK_ID  = rowsData[i].TASK_ID;
				if(TASK_STATUS!="-1"){
					isAllowStart = false;
					break;
				}
				if(i>0){
					selectTaskIds+=",";
				}
				selectTaskIds+=TASK_ID;
			}
			if(!isAllowStart){
				art.dialog({
					content: "只能选择被挂起的流程任务进行重启!",
					icon:"warning",
				    ok: true
				});
			}else{
				AppUtil.operateDataGridRecord("flowTaskController.do?reStart",{selectTaskIds:selectTaskIds},"FdaDwspGrid");
			}
		}
	}
	/**
	 * 编辑流程实例列表记录
	 */
	function editFdaDwspGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FdaDwspGrid");
		if (entityId) {
			showExecutionWindow(entityId);
		}
	}
	
	function formatTaskStatus(val,row){
		  if(val=="1"){
			  return "<font color='#0368ff'><b>正在审核</b></font>";
		  }else if(val=="2"){
			  return "<font color='blue'><b>已审核</b></font>";
		  }else if(val=="3"){
			  return "<font color='#ff4b4b'><b>退回</b></font>";
		  }else if(row.TASK_NODENAME == "开始" && val == "-1" ){
			  return "<font color='#8c97cb'><b>退回补件</b></font>";
		  }else if(val=="4"){
			  return "<font color='#8c97cb'><b>转发</b></font>";
		  }else if(val=="5"){
			  return "<font color='#8c97cb'><b>委托</b></font>";
		  }else if(val=="6"){
			  return "<font color='black'><b>结束流程</b></font>";
		  }else if(val=="-1"){
			  return "<font color='#8c97cb'><b>挂起</b></font>";
		  }
	}
	
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
		if(taskStatus!="1"){
			href += "executionController.do?goDetail&exeId="+exeId;
		}else{
			href += "executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	
	function formatLeftDay(val,row){
		if(row.TASK_NODENAME == "开始" && row.TASK_STATUS == "-1"){
			return "<font color='#ff4b4b'><b>补件中</b></font>";
		}else if(row.TASK_NODENAME != "开始" && row.TASK_STATUS == "-1"){
			return "<font color='#8c97cb'><b>挂起中</b></font>";
		}else if(row.isYs==true){
			if(val=="-1"){
				return "<font color='#ff4b4b'><b>已超期</b></font>";
			}else if(row.IS_IMMEDIATE==1){
				return "<font color='#fa5800'><b>即办</b></font>";
			}else if(val=="0"){
				if(row.IS_IMMEDIATE==2){
					return "<font color='#fa5800'><b>[A]今天截止</b></font>";
				}else{
					return "<font color='#fa5800'><b>今天截止</b></font>";
				}
			}else if(val!="-2"){
				if(row.IS_IMMEDIATE==2){	
					return "<font color='#0368ff'><b>[A]剩余"+val+"个工作日</font>";
				}else{
					return "<font color='#0368ff'><b>剩余"+val+"个工作日</font>";
				}
			}else{
				return "无限制";
			}
		}else if(val>180){
			return "<font color='#ff4b4b'><b>已超期</b></font>";
		}else if(val<=180){
			var time = Number(180)-Number(val);
			return "<font color='#0368ff'><b>剩余"+time+"分钟</font>";
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
			elem : "#FdaDwspT.CREATE_TIME_BEGIN",
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
			elem : "#FdaDwspT.CREATE_TIME_END",
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
		var rows = $("#FdaDwspGrid").datagrid("getChecked");
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
	$('#FdaDwspGrid').datagrid({
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
		<div id="FdaDwspToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;待我审批列表
								<a href="#"
                                class="easyui-linkbutton" reskey="SHOW_Bjxx"
                                iconcls="eicon-eye" plain="true"
                                onclick="showBjxxGridRecord();">查看补件要求</a>
<%--                                <a href="#"--%>
<%--                                class="easyui-linkbutton" --%>
<%--                                iconcls="icon-tick" plain="true"--%>
<%--                                onclick="restartFlowTask();">重启流程任务</a>--%>
                                <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-play" plain="true"
                                onclick="restartFlowTaskExplain();">重启流程任务</a>
							</div>
							 
						</div>
					</div>
				</div>
			</div>
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
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="FdaDwspT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="FdaDwspT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FdaDwspToolbar','FdaDwspGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ExecutionForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FdaDwspGrid" fitcolumns="false" toolbar="#FdaDwspToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="flowTaskController.do?fdaNeedMeHandle&Q_T.TASK_NODENAME_NEQ=受理&Q_E.APPLY_STATUS_NEQ=1&isHaveHandup=true">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'isYs',hidden:true">isYs</th>
					<th data-options="field:'TASK_STATUS',align:'left'" width="8%" formatter="formatTaskStatus">任务状态</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="10%">环节名称</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="8%">发起人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th>
					<th data-options="field:'WORK_HOURS',align:'left'" width="15%">工时统计</th>
					<th data-options="field:'LEFT_WORKDAY',align:'left'" width="10%" formatter="formatLeftDay">办理时限</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
