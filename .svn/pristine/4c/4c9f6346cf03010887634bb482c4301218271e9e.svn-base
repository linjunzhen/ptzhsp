<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteWinGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelWin",
				"WinGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editWinGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("WinGrid");
		if (entityId) {
			showWinGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showWinGridRecord(entityId) {
		$.dialog.open("callController.do?winInfo&entityId=" + entityId, {
			title : "叫号窗口信息",
			width : "650px",
			height : "250px",
			lock : true,
			resize : false
		}, false);
	};

	function isContinueGridRecord(statu){
    	var rows = $("#WinGrid").datagrid("getChecked");
    	var entityIds = "";
    	if(rows.length==0){
       	 parent.art.dialog({
					content: "请至少选择一条记录",
					icon:"succeed",
					time:3,
					ok: true
				});
			return;
		}
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].WIN_ID;
		}
		AppUtil.ajaxProgress({
			url : "callController.do?isContinue",
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
				AppUtil.gridDoSearch("WinToolbar", "WinGrid");
			}
		});
	}
	
	function isUseGridRecord(statu){
    	var rows = $("#WinGrid").datagrid("getChecked");
        if(rows.length==0){
        	 parent.art.dialog({
					content: "请至少选择一条记录",
					icon:"succeed",
					time:3,
					ok: true
				});
			return;
		}
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].WIN_ID;
		}
		AppUtil.ajaxProgress({
			url : "callController.do?isUse",
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
				AppUtil.gridDoSearch("WinToolbar", "WinGrid");
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
		AppUtil.initAuthorityRes("WinToolbar");

	}); */
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showWinGridRecord();">新增</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="icon-note-edit" plain="true"
								onclick="editWinGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteWinGridRecord();">删除</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-ok" plain="true"
								onclick="isContinueGridRecord('1');">可继续受理</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-no" plain="true"
								onclick="isContinueGridRecord('0');">不可继续受理</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-ok" plain="true"
								onclick="isUseGridRecord('1');">启用</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-no" plain="true"
								onclick="isUseGridRecord('0');">禁用</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="WinGrid" fitcolumns="true" nowrap="false"
			toolbar="#WinToolbar" method="post" idfield="WIN_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?winDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'WIN_ID',hidden:true" width="80">WIN_ID</th>
					<th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="200">部门名称</th>
					<th data-options="field:'BELONG_NO',align:'left'" width="80">大厅编号</th>
					<th data-options="field:'WIN_NO',align:'left'" width="100">窗口编号</th>
					<th data-options="field:'SCREEN_NO',align:'left'" width="100" >屏编号</th>
					<th data-options="field:'USERNAMES',align:'left'" width="150">用户名称</th>
					<th data-options="field:'WINDEPART_NAME',align:'left'" width="250">所属业务</th>
					<th data-options="field:'IS_CONTINUE',align:'left',formatter:rowformater" width="150">是否可继续受理</th>
					<th data-options="field:'IS_USE',align:'left',formatter:rowformater" width="150">启用状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
