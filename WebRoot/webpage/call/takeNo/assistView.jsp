<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
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
			return '叫号中……';
		}else if(value=='0'){
			return '等待中……';
		}
	}
	function showAssistGridRecord(){
		$.dialog.open("callController.do?assistInfo", {
			title : "取号信息",
			width : "450px",
			height : "250px",
			lock : true,
			resize : false
		}, false);
	}
	function showToTopReason(){
		var entityId = AppUtil.getEditDataGridRecord("AssistGrid");
		if (entityId) {
			$.dialog.open("callController.do?toTopReason&entityId="+entityId, {
				title : "置顶原因",
				width : "450px",
				height : "200px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content: resultJson.msg,
				icon:"error",
				ok: true
			});
		}
	}
	
	function editAssistGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("AssistGrid");
		if (entityId) {
			AppUtil.ajaxProgress({
				url : "callController.do?toTop&entityId="+entityId,
				callback : function(resultJson) {
					if(resultJson.success){
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
						$("#AssistGrid").datagrid("reload");
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
	
	function cancelAssistGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("AssistGrid");
		if (entityId) {
			AppUtil.ajaxProgress({
				url : "callController.do?cancelTop&entityId="+entityId,
				callback : function(resultJson) {
					if(resultJson.success){
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
						$("#AssistGrid").datagrid("reload");
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
		AppUtil.initAuthorityRes("AssistToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="AssistToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="icon-note-add" plain="true"
								onclick="showAssistGridRecord();">取号</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_TakeNo"
								iconcls="icon-note-edit" plain="true"
								onclick="showToTopReason();">置顶</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="cancal_TakeNo"
								iconcls="icon-note-edit" plain="true"
								onclick="cancelAssistGridRecord();">取消置顶</a>
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
							<td style="width:195px;">
								<input class="easyui-combobox"
								style="width:195px;" name="Q_t.WIN_NO_="
								data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=winDepartNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 195,panelHeight: '300' " />
								
							</td>
							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.line_name_LIKE" /></td>
							<td style="width:68px;text-align:right;">办理状态</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:128px;" name="Q_T.CALL_STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '0',value: '等待中……',selected: true},
									            {label: '1', value: '已受理'},{label: '2',value: '过号'},
									            {label: '3', value: '处理完毕（领照）'},{label: '4',value: '处理完毕（咨询）'},
									            {label: '5', value: '处理完毕（其他）'},{label: '6',value: '叫号中……'},],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('AssistToolbar','AssistGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AssistForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="AssistGrid" fitcolumns="true" nowrap="false"
			toolbar="#AssistToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?assistDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'CALL_STATUS',align:'left',formatter:statusformater" width="10%">办理状态</th>
					<th data-options="field:'LINE_NO',align:'left'" width="10%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="10%">姓名</th>
					<th data-options="field:'LINE_CARDNO',align:'left'" width="20%">身份证号</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">取号时间</th>
					<th data-options="field:'IS_VIP',align:'left',formatter:rowformater" width="5%">是否VIP</th>
					<th data-options="field:'CKYWL',align:'left'" width="15%">办理业务类型</th>
					<th data-options="field:'TOTOPREASON',align:'left'" width="15%">备注</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
