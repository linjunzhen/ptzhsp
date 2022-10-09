<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 删除流程实例列表记录
	 */
	function deleteNeedMeHandlePtjGridRecord() {
		AppUtil.deleteDataGridRecord("executionController.do?multiDel",
				"NeedMeHandlePtjGrid");
	};
	/**
	 * 编辑流程实例列表记录
	 */
	function editNeedMeHandlePtjGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("NeedMeHandlePtjGrid");
		if (entityId) {
			showExecutionWindow(entityId);
		}
	}
	
	function formatSubject(val,row){
		//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//获取流程任务ID
		var taskId = row.TASK_ID;
		var href = "<a href='";
		href += "executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
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

	$(document).ready(function() {
		var needHandlePtjStart = {
			elem : "#NeedMeHandlePtjT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_E.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_E.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_E.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var needHandlePtjEnd = {
			elem : "#NeedMeHandlePtjT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_E.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_E.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_E.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(needHandlePtjStart);
		laydate(needHandlePtjEnd);
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="NeedMeHandlePtjToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="NeedMeHandlePtjForm">
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
							<td style="width:68px;text-align:right;">证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.SQRSFZ_LIKE" />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandlePtjT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;"><input type="text"
								style="width:108px;float:left;" class="laydate-icon"
								id="NeedMeHandlePtjT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>								
						    
							<td style="width:68px;text-align:right;">申请人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.SQRMC_LIKE" /></td>
							<td style="width:68px;text-align:right;">经办人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_E.JBR_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('NeedMeHandlePtjToolbar','NeedMeHandlePtjGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('NeedMeHandlePtjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="NeedMeHandlePtjGrid" fitcolumns="true" toolbar="#NeedMeHandlePtjToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="serviceHandleController.do?needHandle&Q_TWS.SXLX_EQ=2">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="10%">经办人</th>
					<th data-options="field:'TASK_NODENAME',align:'left'" width="15%">环节名称</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="11%">发起人</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
