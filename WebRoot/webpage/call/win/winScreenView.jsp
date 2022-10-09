<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteWinScreenGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelWinScreen",
				"WinScreenGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editWinScreenGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("WinScreenGrid");
		if (entityId) {
			showWinScreenGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showWinScreenGridRecord(entityId) {
		$.dialog.open("callController.do?winScreenInfo&entityId=" + entityId, {
			title : "窗口屏绑定信息",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false
		}, false);
	};	

	function rowformater(value,row,index){
		if(value=='1'){
			return '是';
		}else if(value=='0'){
			return '否';
		}
	}
	
	
	function changeScreenGridRecord(statu){
    	var rows = $("#WinScreenGrid").datagrid("getChecked");
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		}
		AppUtil.ajaxProgress({
			url : "callController.do?winScreenUse",
			params : {
				"entityIds" : entityIds,
				"statu" : statu
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("WinScreenToolbar", "WinScreenGrid");
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
	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("WinScreenToolbar");

	}); */
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinScreenToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showWinScreenGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="icon-note-edit" plain="true"
								onclick="editWinScreenGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteWinScreenGridRecord();">删除</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-ok" plain="true"
								onclick="changeScreenGridRecord('1');">启用</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-no" plain="true"
								onclick="changeScreenGridRecord('0');">禁用</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="WinScreenGrid" fitcolumns="false" nowrap="false"
			toolbar="#WinScreenToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?winScreenDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'WIN_NO',align:'left'" width="150">窗口编号</th>
					<th data-options="field:'SCREEN_NO',align:'left'" width="150" >屏编号</th>
					<th data-options="field:'DEPARTINFO',align:'left'" width="150" >显示单位名称</th>
					<th data-options="field:'WORD_NUM',align:'left'" width="150" >显示字符数</th>
					<th data-options="field:'USE_STATUS',align:'left',formatter:rowformater" width="150" >启用状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
