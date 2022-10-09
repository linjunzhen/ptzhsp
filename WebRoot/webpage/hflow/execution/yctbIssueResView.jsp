<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	
	
	function updateCJZT() {
		var exeId = AppUtil.getEditDataGridRecord("YctbFlowMonitorGrid");
		var $dataGrid = $("#YctbFlowMonitorGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		var run_status=rowsData[0].RUN_STATUS;
		if(run_status!='2'&&run_status!='3'){
			art.dialog({
				content: "只能选择待取件流程任务!",
				icon:"warning",
			    ok: true
			});
			return;
		}else {
			art.dialog.confirm("您确定修改此办件取件状态吗？", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $dataGrid.datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post("executionController.do?multiUpdateCJZT",{
					   selectColNames:selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							$dataGrid.datagrid('reload');
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							if(callback!=null){
								callback.call(this,resultJson);
							}else{
								$dataGrid.datagrid('reload');
								$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
							}
						}else{
							$dataGrid.datagrid('reload');
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	}
	
	
	function formatRunStatus(val,row){
		if(val=="0"){
			return "<font color='#ff4b4b'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='#0368ff'><b>正在办理</b></font>";
		}else if(val=="2"||val=="3"){
			if(row.CJZT=="0"){
				return "<font color='#0368ff'><b>待取件</b></font>";
			}else if(row.CJZT=="1"){
				return "<font color='black'><b>已取件</b></font>";
			}
		}else if(val=="4"){
			return "<font ><b>已办结(审核不通过)</b></font>";
		}else if(val=="5"){
			return "<font color='#ff4b4b'><b>已办结(退回)</b></font>";
		}else if(val=="6"){
			return "<font color='black'><b>强制结束</b></font>";
		}else if(val=="7"){
			return "<font color='#ff4b4b'><b>预审不通过</b></font>";
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
		href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}
	$(document).ready(function() {
		AppUtil.initAuthorityRes("YctbFlowMonitorToolbar");
	});
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
	$('#YctbFlowMonitorGrid').datagrid({
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
		<div id="YctbFlowMonitorToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						   <a href="#" class="easyui-linkbutton"   reskey="XGQJZT"
								iconCls="eicon-edit" plain="true" 
								onclick="updateCJZT();">修改取件状态</a> 
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
								name="Q_T.SQRMC_LIKE" /></td>
						    <td style="width:68px;text-align:right;">申请人证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SQRSFZ_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">经办人</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.JBR_NAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
						    <td style="width:68px;text-align:right;">经办人证件号码</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.JBR_ZJHM_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">流程状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RUN_STATUS_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">流程标题</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">取件状态</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.CJZT_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=QJZT',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
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
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('YctbFlowMonitorToolbar','YctbFlowMonitorGrid')" />
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
			id="YctbFlowMonitorGrid" fitcolumns="true" toolbar="#YctbFlowMonitorToolbar"
			method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="executionController.do?YctbDatagrid&Q_T.RUN_STATUS_NEQ=0&Q_T.ACCEPTWAY_EQ=1&Q_T.SFCJ_EQ=1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CJZT',hidden:true">CJZT</th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="35%" formatter="formatSubject">流程标题</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="12%" formatter="formatRunStatus">流程状态</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'SQRSFZ',align:'left'" width="15%">申请人证件号码</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="10%">经办人</th>
					<th data-options="field:'JBR_ZJHM',align:'left'" width="15%">经办人证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">申请时间</th>
					<th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%">当前环节</th>
					<th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">当前环节审核人</th>
					<th data-options="field:'END_TIME',align:'left'" width="13%">办结时间</th>
					<th data-options="field:'WORK_HOURS',align:'left'" width="13%">运行时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
