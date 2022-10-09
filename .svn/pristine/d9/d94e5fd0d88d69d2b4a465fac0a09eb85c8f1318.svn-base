<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteFlowMonitorRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"FlowMonitorGrid");
	};
	
	/**
	* 终结流程实例
	*/
	function endFlowMonitorRecord(){
		AppUtil.deleteDataGridRecord("executionController.do?multiEnd",
		"FlowMonitorGrid");
	};
	/**
	* 更改执行路径
	*/
	function changeFlowBranch(){
		var entityId = AppUtil.getEditDataGridRecord("FlowMonitorGrid");
		if (entityId) {
			$.dialog.open("flowTaskController.do?goChange&exeId=" + entityId, {
				title : "更改执行路径",
				width : "800px",
				height : "200px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	/**
	 * 流程任务管理
	 */
	function managerFlowTask() {
		var entityId = AppUtil.getEditDataGridRecord("FlowMonitorGrid");
		if (entityId) {
			$.dialog.open("flowTaskController.do?goManager&exeId=" + entityId, {
				title : "实例任务明细",
				width : "550px",
				height : "450px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		if(runStatus=="0"){
			href += "executionController.do?goStart&exeId="+exeId;
		}else{
			href += "executionController.do?goDetail&exeId="+exeId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	$(document).ready(function() {
		AppUtil.initAuthorityRes("FlowMonitorToolbar");
	});
	function updateFlowExe() {
		var exeId = AppUtil.getEditDataGridRecord("FlowMonitorGrid");
		var $dataGrid = $("#FlowMonitorGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		var run_status=rowsData[0].RUN_STATUS;
		if(run_status!='2'){
			art.dialog({
				content: "只能选择已办结的流程任务进行修改!",
				icon:"warning",
			    ok: true
			});
			return;
		}
		if (exeId) {
			parent.$.dialog.open("executionController.do?updateFlowResult&exeId="+exeId,{
				title : "办件结果修改",
	            width:"900px",
	            lock: true,
	            resize:false,
	            height:"500px",
	            close: function () {
	    			
	    		}
			},false);
		  //var href = "executionController.do?updateFlowResult&exeId="+exeId;
         // window.open(href,'_blank');
		}
	}
	$(document).ready(function() {
		var start1 = {
			elem : "#MyApplyExecT.CREATE_TIME_BEGIN",
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
			elem : "#MyApplyExecT.CREATE_TIME_END",
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
	
	//空数据，横向滚动
	$('#FlowMonitorGrid').datagrid({
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
		<div id="FlowMonitorToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						   <a href="#"
								class="easyui-linkbutton"  reskey="ZJLCSL"
								iconCls="eicon-power-off" plain="true"
								onclick="endFlowMonitorRecord();">终结流程实例</a> 
						   <a href="#"
								class="easyui-linkbutton"  reskey="LCRWGL"
								iconCls="eicon-tasks" plain="true"
								onclick="managerFlowTask();">流程任务管理</a> 
							<a href="#"
								class="easyui-linkbutton"  reskey="GGZXLJ"
								iconCls="eicon-arrows" plain="true"
								onclick="changeFlowBranch();">更改执行路径</a> 
						   <a href="#"
								class="easyui-linkbutton"  reskey="SCLCSL"
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteFlowMonitorRecord();">删除流程实例</a>
						   <a href="#" class="easyui-linkbutton"   reskey="XGBJJG"
								iconCls="eicon-edit" plain="true" 
								onclick="updateFlowExe();">修改办件结果</a> 
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
								name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.CREATOR_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">经办人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.JBR_NAME_LIKE" /></td>
						    <td style="width:68px;text-align:right;">证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SQRSFZ_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">流程状态</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">申请方式</td>
							<td style="width:135px;"><input class="easyui-combobox"
															style="width:128px;" name="Q_T.SQFS_="
															data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=bjsqfs',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="MyApplyExecT.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FlowMonitorToolbar','FlowMonitorGrid')" />
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
			id="FlowMonitorGrid" fitcolumns="true" toolbar="#FlowMonitorToolbar"
			method="post" idfield="EXE_ID" checkonselect="true" nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="executionController.do?datagridNew&Q_T.RUN_STATUS_NEQ=0">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">申请时间</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="12%" formatter="FlowUtil.formatRunStatus">流程状态</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="12%">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="20%">当前环节审核人</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
					<th data-options="field:'WORK_HOURS',align:'left'" width="15%">运行时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
