<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
  
	function setSelectUsers(userIds, userNames) {
		var rowsData = $("#SysRoleGrid").datagrid("getChecked");
		var row = rowsData[0];
		AppUtil.ajaxProgress({
			url : "sysRoleController.do?grantUsers",
			params : {
				"roleId" : row.ROLE_ID,
				"userIds" : userIds
			},
			callback : function(resultJson) {
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				AppUtil.gridDoSearch("SysRoleToolbar", "SysRoleGrid");
			}
		});
	}

	/**
	 * 删除系统角色列表记录
	 */
	function deleteSysRoleGridRecord() {
		AppUtil.deleteDataGridRecord("sysRoleController.do?multiDel",
				"SysRoleGrid");
	};

	/**
	 * 编辑系统角色列表记录
	 */
	function editSysRoleGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("SysRoleGrid");
		if (entityId) {
			showSysRoleWindow(entityId);
		}
	}

	function grantSysRoleGridRecord() {
		var curUserRoleCodes= $("input[name='curUserRoleCodes']").val();
		var entityId = AppUtil.getEditDataGridRecord("SysRoleGrid");
		if (entityId) {
			var rowsData = $("#SysRoleGrid").datagrid("getChecked");
			var row = rowsData[0];
			if (row.ROLE_CODE == "Admin") {
				art.dialog({
					content: "无法对超级管理员进行授权!",
					icon:"warning",
				    ok: true
				});
			}else if(curUserRoleCodes.indexOf(row.ROLE_CODE)!="-1"){
				art.dialog({
					content: "无法对自身拥有角色进行授权!",
					icon:"warning",
				    ok: true
				});				
			} else {
				showResGrantWindow(entityId);
			}
		}
	}

	function grantUsers() {
		var entityId = AppUtil.getEditDataGridRecord("SysRoleGrid");
		if (entityId) {
			var rowsData = $("#SysRoleGrid").datagrid("getChecked");
			var row = rowsData[0];
			var userIds = row.USER_IDS;
			var userNames = row.USER_NAMES;
	        $.dialog.open("sysUserController.do?selector&allowCount=0&userIds="
					+ userIds, {
				title : "人员选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close: function () {
	    			var selectUserInfo = art.dialog.data("selectUserInfo");
	    			if(selectUserInfo&&selectUserInfo.userIds){
	    				setSelectUsers(selectUserInfo.userIds,selectUserInfo.userNames);
	    				art.dialog.removeData("selectUserInfo");
	    			}
	    		}
			}, false);
		}
	}

	function showResGrantWindow(entityId) {
		$.dialog.open("sysRoleController.do?grant&roleId=" + entityId, {
			title : "角色授权",
			width : "500px",
			lock : true,
			resize : false,
			height : "500px",
		}, false);
	};

	/**
	 * 显示系统角色信息对话框
	 */
	function showSysRoleWindow(entityId) {
		$.dialog.open("sysRoleController.do?info&entityId=" + entityId, {
			title : "系统角色信息",
			width : "450px",
			lock : true,
			resize : false,
			height : "200px",
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("SysRoleToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="SysRoleToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_T.ROLE_GROUPCODE_=" />
			<input type="hidden" name="curUserRoleCodes" value="${sessionScope.curLoginUser.roleCodes}" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SysRole"
								iconcls="eicon-plus" plain="true"
								onclick="showSysRoleWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysRole"
								iconcls="eicon-pencil" plain="true"
								onclick="editSysRoleGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysRole"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteSysRoleGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" reskey="Grant_SysRole"
								iconcls="eicon-bookmark" plain="true"
								onclick="grantSysRoleGridRecord();">角色授权</a>
							<a href="#" class="easyui-linkbutton" reskey="AddUsers_SysRole"
								iconcls="icon-adduser" plain="true"
								onclick="grantUsers();">分配用户</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SysRoleForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">角色名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ROLE_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">角色编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ROLE_CODE_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SysRoleToolbar','SysRoleGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysRoleForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="SysRoleGrid" fitcolumns="true" toolbar="#SysRoleToolbar"
			method="post" idfield="ROLE_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="sysRoleController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'USER_IDS',hidden:true">USER_IDS</th>
					<th data-options="field:'ROLE_ID',hidden:true">ROLE_ID</th>
					<th data-options="field:'ROLE_NAME',align:'left'" width="20%">角色名称</th>
					<th data-options="field:'ROLE_CODE',align:'left'" width="20%">角色编码</th>
					<th data-options="field:'USER_NAMES',align:'left'" width="55%">授权用户</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>