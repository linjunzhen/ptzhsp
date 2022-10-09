<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function statusformater(value,row,index){
		if(value=='1'){
			return '已受理';
		}else if(value=='2'){
			return '过号';
		}else if(value=='3'){
			return '处理完毕（领照）';
		}else if(value=='4'){
			return '处理完毕（咨询）';
		}else if(value=='5'){
			return '处理完毕（其他）';
		}else if(value=='6'){
			return '正在办理';
		}else if(value=='0'){
			return '等待中……';
		}else if(value=='7'){
			return '取错事项';
		}
	}
	function showQueueGridRecord(){
		$.dialog.open("newCallController.do?assistTakeInfo", {
			title : "取号信息",
			width : "500px",
			height : "320px",
			lock : true,
			resize : false
		}, false);
	}
	function showToTopReason(){
		var entityId = AppUtil.getEditDataGridRecord("QueueGrid");
		if (entityId) {
			$.dialog.open("newCallController.do?toTopReason&entityId="+entityId, {
				title : "置顶原因",
				width : "430px",
				height : "180px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function cancelQueueGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("QueueGrid");
		if (entityId) {
			AppUtil.ajaxProgress({
				url : "newCallController.do?cancelTop&entityId="+entityId,
				callback : function(resultJson) {
					if(resultJson.success){
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
						$("#QueueGrid").datagrid("reload");
					}else{
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				}
			});
		}
	}
	
	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("QueueToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="QueueToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-sticky-note-o" plain="true"
								onclick="showQueueGridRecord();">取号</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_TakeNo"
								iconcls="eicon-arrow-up" plain="true"
								onclick="showToTopReason();">置顶</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="cancal_TakeNo"
								iconcls="eicon-arrow-down" plain="true"
								onclick="cancelQueueGridRecord();">取消置顶</a>
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="AssistForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">窗口业务类</td>
							<td style="width:235px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:235px;" name="Q_t.BUSINESS_CODE_="
								data-options="url:'callSetController.do?loadBusiness&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'BUSINESS_CODE',textField:'BUSINESS_NAME',panelWidth: 235,panelHeight: '300' " />
								
							</td>
							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.line_name_LIKE" /></td>
							<td style="width:68px;text-align:right;">办理状态</td>
							<td style="width:135px;padding-left:4px;">
								<input class="easyui-combobox"
								style="width:128px;" name="Q_T.CALL_STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '0',value: '等待中……',selected: true},
									            {label: '1', value: '已受理'},{label: '2',value: '过号'},
									            {label: '3', value: '处理完毕（领照）'},{label: '4',value: '处理完毕（咨询）'},
									            {label: '5', value: '处理完毕（其他）'},{label: '6',value: '正在办理'},{label: '7',value: '取错事项'}],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('QueueToolbar','QueueGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AssistForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="QueueGrid" fitcolumns="true" nowrap="false"
			toolbar="#QueueToolbar" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="newCallController.do?queueRecordGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'CALL_STATUS',align:'left',formatter:statusformater" width="10%">办理状态</th>
					<th data-options="field:'LINE_NO',align:'left'" width="6%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="6%">姓名</th>
					<th data-options="field:'LINE_CARDNO',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">取号时间</th>
					<th data-options="field:'CUR_WIN',align:'left'" width="10%">叫号窗口</th>
					<th data-options="field:'IS_VIP',align:'left',formatter:rowformater" width="8%">是否VIP</th>
					<th data-options="field:'BUSINESS_NAME',align:'left'" width="15%">办理业务类型</th>
					<th data-options="field:'TOTOPREASON',align:'left'" width="11%">备注</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
