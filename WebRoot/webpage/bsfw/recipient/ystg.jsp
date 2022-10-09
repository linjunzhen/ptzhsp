<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

	
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
		var start1 = {
			elem : "#Ystg.CREATE_TIME_BEGIN",
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
			elem : "#Ystg.CREATE_TIME_END",
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

		AppUtil.initAuthorityRes("YstgToolbar");

	});

	//空数据，横向滚动
	$('#YstgGrid').datagrid({
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
		<div id="YstgToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="YstgForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.EXE_ID_LIKE" /></td>
							<td style="width:68px;text-align:right;">申报名称 </td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">提交日期从</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Ystg.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">提交日期至</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Ystg.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('YstgToolbar','YstgGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('YstgForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table rownumbers="true" pagination="true" striped="true"
			id="YstgGrid" fitcolumns="true" toolbar="#YstgToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" 
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="executionController.do?handledByMe&Q_T.APPLY_STATUS_IN=2">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">申报名称</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">提交日期</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">申报状态</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">当前环节审核人</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
					<th data-options="field:'WORK_HOURS',align:'left'" width="15%">运行时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
