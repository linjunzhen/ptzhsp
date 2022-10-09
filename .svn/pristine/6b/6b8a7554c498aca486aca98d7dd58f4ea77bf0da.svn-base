<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function childDepartManage(){
    	var rows = $("#DepartGrid").datagrid("getChecked");
    	if(rows.length>1){
    		alert("只能选择一条记录进行操作");
    		return ;
    	}else if(rows.length<1){
    		alert("请选择一条记录进行操作");
    		return ;
    	}
    	var departId = rows[0].DEPART_ID;
		if (departId) {
			$.dialog.open("callController.do?childDepartManage&departId=" + departId, {
				title : "单位处室取号管理",
				width : "600px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	}
	/**
	 * 删除对接配置列表记录
	 */
	function deleteDepartGridRecord() {
		AppUtil.deleteDataGridRecord("callController.do?multiDelWinDepart",
				"DepartGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editDepartGridRecord() {
    	/* var rows = $("#DepartGrid").datagrid("getChecked");
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		} */
		
		var entityId = AppUtil.getEditDataGridRecord("DepartGrid");
		if (entityId) {
			$.dialog.open("callController.do?winDepartInfo&entityId=" + entityId, {
				title : "窗口单位信息",
				width : "450px",
				height : "200px",
				lock : true,
				resize : false
			}, false);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	 function showSelectDeparts(){
    	var rows = $("#DepartGrid").datagrid("getRows");
    	var departIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				departIds+=",";
			}
			departIds+=rows[i].DEPART_ID;
		}
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departIds+"&allowCount=100&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				saveSelectDept(selectDepInfo.departIds,selectDepInfo.departNames);
    				art.dialog.removeData("selectDepInfo");
    			}
    		}
    	}, false);
    }

	function saveSelectDept(departIds,departNames){
		AppUtil.ajaxProgress({
			url : "callController.do?saveWinDepart",
			params : {
				"departIds" : departIds,
				"departNames" : departNames
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("DepartToolbar", "DepartGrid");
			}
		});
	}
	
	function changeDepartGridRecord(statu){
    	var rows = $("#DepartGrid").datagrid("getChecked");
    	var entityIds = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				entityIds+=",";
			}
			entityIds+=rows[i].RECORD_ID;
		}
		AppUtil.ajaxProgress({
			url : "callController.do?isTake",
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
				AppUtil.gridDoSearch("DepartToolbar", "DepartGrid");
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
		AppUtil.initAuthorityRes("DepartToolbar");

	}); */
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DepartToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="icon-note-add" plain="true"
								onclick="showSelectDeparts();">选择</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_Win"
								iconcls="icon-note-edit" plain="true"
								onclick="editDepartGridRecord();">编辑所属大厅</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteDepartGridRecord();">删除</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-ok" plain="true"
								onclick="changeDepartGridRecord('1');">可取号</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-no" plain="true"
								onclick="changeDepartGridRecord('0');">不可取号</a><a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="icon-page" plain="true"
								onclick="childDepartManage();">子部门管理</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="DepartGrid" fitcolumns="true" nowrap="false" pageSize="30"
			toolbar="#DepartToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?departDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="150">部门名称</th>
					<th data-options="field:'IS_TAKE',align:'left',formatter:rowformater" width="100" >是否可取号</th>
					<th data-options="field:'BELONG_NO',align:'left'" width="200">所属大厅</th>
					<th data-options="field:'ICON_NO',align:'left'" width="200">图标编号</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
