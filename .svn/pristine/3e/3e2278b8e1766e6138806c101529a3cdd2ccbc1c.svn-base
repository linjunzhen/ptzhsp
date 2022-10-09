<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除系统用户列表记录
	 */
	function deleteSysUserGridRecord() {
		AppUtil.deleteDataGridRecord("sysUserController.do?multiDel",
				"SysUserGrid");
	};
	/**
	 * 编辑系统用户列表记录
	 */
	function editSysUserGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("SysUserGrid");
		if (entityId) {
			showSysUserWindow(entityId);
		}
	}

	/**
	 * 显示系统用户信息对话框
	 */
	function showSysUserWindow(entityId) {
		//获取组织机构的ID
		var departId = $("#SysUserToolbar input[name='DEPART_ID']").val();
		var url = "sysUserController.do?info&entityId=" + entityId;
		if(departId&&departId!="0"){
			url+="&departId="+departId;
		}
		$.dialog.open(url, {
    		title : "系统用户信息",
            width:"700px",
            lock: true,
            resize:false,
            height:"350px",
    	}, false);
	};
	
	function formatStatus(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>激活</b></font>";
		}else if(val=="2"){
			return "<font color='#ff4b4b'><b>停用</b></font>";
		}else{
			return "<font color='#ff4b4b'><b>禁用</b></font>";
		}
	}
	function formatIsDisable(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>是</b></font>";
		}else{
			return "<font color='#ff4b4b'><b>否</b></font>";
		}
	}
	
	function updateStatus(status){
    	AppUtil.multiOperateDataGridRecord("sysUserController.do?updateStatus&status="+status,
		"SysUserGrid");
    };
    function updateAutoStatus(status){
    	AppUtil.multiOperateDataGridRecord("sysUserController.do?updateAutoStatus&status="+status,
		"SysUserGrid");
    };
    function resetPassword(){
    	AppUtil.multiOperateDataGridRecord("sysUserController.do?resetPass",
		"SysUserGrid");
    }
    
    /**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#SysUserToolbar input[name='DEPART_ID']").val(treeNode.id);
			$("#SysUserToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			AppUtil.gridDoSearch("SysUserToolbar","SysUserGrid");
		}
	}

	$(document).ready(function() {
		var curUserResKeys = $("input[name='curUserResKeys']").val();
		var userDepartTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartmentTreeClick,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
					if (curUserResKeys != "__ALL") {
						var loginUserInfoJson = $(
								"input[name='loginUserInfoJson']").val();
						var loginUser = JSON2.parse(loginUserInfoJson);
						//获取授权的区划代码数组
						var authDepCodeArray = loginUser.authDepCodes
								.split(",");
						var nodes = treeObj.transformToArray(treeObj
								.getNodes());
						for (var i = 0; i < nodes.length; i++) {
							var child = nodes[i];
							if (!AppUtil.isContain(authDepCodeArray,
									child.DEPART_CODE)) {
								if(child.DEPART_CODE){
									treeObj.hideNode(child);
								}
							}
						}
					}
				}
			},
			async : {
				enable : true,
				url : "departmentController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"Q_STATUS_!=":0,
					"isShowRoot" : "true",
					"rootName" : "组织机构树"
				}
			}
		};
		$.fn.zTree.init($("#userDepartTree"), userDepartTreeSetting);
		//AppUtil.initAuthorityRes("SysUserToolbar");

	});
	function showExcelExportUserInfo(){
        AppUtil.showExcelExportWin({
            dataGridId:"SysUserGrid",
            tplKey:"UserInfoReportTpl",
            excelName:"用户数据",
            queryParams:{
                "T.USERNAME":$("input[name='Q_T.USERNAME_LIKE']").val(),
                "T.FULLNAME":$("input[name='Q_T.FULLNAME_LIKE']").val(),
                "T.STATUS":$("input[name='Q_T.STATUS_=']").val()
            }
        });
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">

    <div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;组织机构树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="userDepartTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="SysUserToolbar">
		     <!--====================开始编写隐藏域============== -->
			 <input type="hidden" name="Q_D.PATH_LIKE" />
			 <input type="hidden" name="DEPART_ID"  />
			 <input type="hidden" value="${sessionScope.curLoginUser.loginUserInfoJson}" name="loginUserInfoJson"> 
			<input type="hidden" value="${sessionScope.curLoginUser.resKeys}" name="curUserResKeys">
			 <input type="hidden" name="curUserName" value="${sessionScope.curLoginUser.username}" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<!-- <a href="#" class="easyui-linkbutton" reskey="Add_SysUser"
								iconcls="eicon-plus" plain="true"
								onclick="showSysUserWindow();">新建</a> --> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysUser"
								iconcls="eicon-pencil" plain="true"
								onclick="editSysUserGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysUser"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteSysUserGridRecord();">删除</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-check" plain="true" onclick="updateStatus(1);">激活</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-ban" plain="true" onclick="updateStatus(-1);">禁用</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-ban" plain="true" onclick="updateStatus(2);">停用</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_Schedule"
								iconcls="eicon-key" plain="true"
								onclick="resetPassword();">密码重置</a> <a href="#"
								class="easyui-linkbutton" reskey="JH_Schedule"
								iconcls="eicon-check" plain="true"
								onclick="updateAutoStatus(1);">激活（自动禁用）</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
								iconcls="eicon-file-excel-o" plain="true"
								onclick="showExcelExportUserInfo();">导出数据</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SysUserForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">帐号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.USERNAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">用户姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.FULLNAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">手机号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.MOBILE_=" /></td>
							<td style="width:68px;text-align:right;">身份证</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.USERCARD_=" /></td>
						</tr>
						<tr style="height:28px;">	
							<td style="width:68px;text-align:right;">用户状态</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:128px;" name="Q_T.STATUS_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '1', value: '激活'},{label: '-1',value: '禁用'},],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SysUserToolbar','SysUserGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysUserForm');" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="SysUserGrid" fitcolumns="true" toolbar="#SysUserToolbar"
			method="post" idfield="USER_ID" checkonselect="true" nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="sysUserController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'USER_ID',hidden:true">USER_ID</th>
					<th data-options="field:'USERNAME',align:'left'" width="12%">帐号</th>
					<th data-options="field:'FULLNAME',align:'left'" width="12%">用户姓名</th>
					<th data-options="field:'MOBILE',align:'left'" width="12%">手机号码</th>
					<th data-options="field:'STATUS',align:'left'" width="8%" formatter="formatStatus">状态</th>
					<th data-options="field:'IS_DISABLE',align:'left'" width="12%" formatter="formatIsDisable">是否自动禁用</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="20%">机构名称</th>
					<th data-options="field:'ROLE_NAMES',align:'left'" width="18%">授权角色</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>