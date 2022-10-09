<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function showWinAppointGridRecord(){
		$.dialog.open("appointmentController.do?winAppointmentApply", {
			title : "办事预约",
			width : "500px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	}
	
	function cancelWinAppointGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("NewAppointGrid");
		
		AppUtil.ajaxProgress({
			url : "appointmentController.do?cancelWinApply",
			params : {
				"entityId" : entityId
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("NewAppointToolbarBsyy", "NewAppointGrid");
			}
		});
	}
	
	
	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}
	
	function rowformaterstatus(value,row,index){
		if(value=='1'){
			return "<font color='#0368ff'>正常</font>";
		}else if(value=='2'){
			return "<font color='#ff4b4b'>作废</font>";
		}else if(value=='0'){
			return "<font color='#ff4b4b'>已取消</font>";
		}
	}
	
	$(document).ready(function() {
		
		var appointTime = {
	    	elem: "#NEWAPPOINT.APPOINT_TIME",
		    format: "YYYY-MM-DD",
		    istime: false,
		    min:laydate.now()
		};
		laydate(appointTime);
		
		//AppUtil.initAuthorityRes("NewAppointToolbarBsyy");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="NewAppointToolbarBsyy">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-sticky-note-o" plain="true"
								onclick="showWinAppointGridRecord();">预约</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-close" plain="true"
								onclick="cancelWinAppointGridRecord();">取消预约</a>
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="NewAppointForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">预约日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="NEWAPPOINT.APPOINT_TIME" name="Q_t.DATE_TIME_=" value="${currentDate }"/></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('NewAppointToolbarBsyy','NewAppointGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('NewAppointForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="NewAppointGrid" fitcolumns="true" nowrap="false"
			toolbar="#NewAppointToolbarBsyy" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="false" fit="true" border="false"
			url="appointmentController.do?winAppointmentGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'NAME',align:'left'" width="7%">姓名</th>
					<th data-options="field:'CARD',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'DATE_TIME',align:'left'" width="10%">预约日期</th>
					<th data-options="field:'BEGIN_TIME',align:'left'" width="10%">预约时段</th>
					<th data-options="field:'END_TIME',align:'left'" width="10%">预约时段</th>
					<th data-options="field:'BUSINESS_NAME',align:'left'" width="15%">预约业务</th>
					<th data-options="field:'IS_TAKE',align:'left',formatter:rowformater" width="8%">是否取号</th>
					<th data-options="field:'TAKE_TIME',align:'left'" width="15%">取号时间</th>
					<th data-options="field:'STATUS',align:'left',formatter:rowformaterstatus" width="5%">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
