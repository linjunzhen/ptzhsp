<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>

<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#SelectedSysUserGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var userIds = "";
			var userNames = "";
			var userAccounts = "";
			var mobiles = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					userIds+=",";
					userNames+=",";
					userAccounts+=",";
					mobiles+=",";
				}
				userIds+=rows[i].USER_ID;
				userNames+=rows[i].FULLNAME;
				userAccounts+=rows[i].USERNAME;
				mobiles+=rows[i].MOBILE;
			}
    		art.dialog.data("selectUserInfo", {
    			userIds:userIds,
    			userNames:userNames,
    			userAccounts:userAccounts,
    			mobiles:mobiles
			});
    		AppUtil.closeLayer();
    	}
    	
    }

    /**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#SysUserToolbar input[name='DEPART_ID']").val(treeNode.id);
			$("#SysUserToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			$("#SysUserToolbar input[name='ROLE_ID']").val("");
			AppUtil.gridDoSearch("SysUserToolbar","SysUserGrid");
		}
	}
    
	/**
	 * 点击树形节点触发的事件
	 */
	function onRoleTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id!="0"){
				$("#SysUserToolbar input[name='DEPART_ID']").val("");
				$("#SysUserToolbar input[name='Q_D.PATH_LIKE']").val("");
				$("#SysUserToolbar input[name='ROLE_ID']").val(treeNode.id);
			}else{
				$("#SysUserToolbar input[name='ROLE_ID']").val("");
			}
			AppUtil.gridDoSearch("SysUserToolbar","SysUserGrid");
		}
	}
	/**
	 * easyUI grid做勾选API
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	function doMultiCheck(){
		var $dataGrid = $("#SysUserGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if(rowsData.length>0){
			for (var i = 0; i < rowsData.length; i++) {
				var row=rowsData[i];
				var rowIndex = $("#SelectedSysUserGrid").datagrid("getRowIndex",row.USER_ID);
				if(rowIndex==-1){
					$("#SelectedSysUserGrid").datagrid("appendRow",row);
				}
			}
			$('#SysUserGrid').datagrid('clearChecked')=='none';
		}else{
			alert("至少选择一条记录");
		}
	}
    function doRemoveCheck(){
		var $dataGrid = $("#SelectedSysUserGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if(rowsData.length>0){
			removeSelectedSysUserGrid();
		}else{
			alert("至少选择一条记录");
		}
	}
    function removeSelectedSysUserGrid(){
		var $dataGrid = $("#SelectedSysUserGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if(rowsData.length>0){
			for (var i = 0; i < rowsData.length; i++) {
				var row=rowsData[i];
				var rowIndex = $("#SelectedSysUserGrid").datagrid("getRowIndex",row);
				$("#SelectedSysUserGrid").datagrid("deleteRow",rowIndex);
			}
			removeSelectedSysUserGrid();
		}else{
			$('#SelectedSysUserGrid').datagrid('clearChecked')=='none';
			return;
		}
	}
	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		var userDepartTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartmentTreeClick
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
		var userRoleTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onRoleTreeClick
			},
			async : {
				enable : true,
				url : "sysRoleController.do?roleTree",
				otherParam : {
					"start" : "0",
					"limit" : "1000"
				}
			}
		};
		$.fn.zTree.init($("#userRoleTree"), userRoleTreeSetting);
		$("#SysUserGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedSysUserGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedSysUserGrid").datagrid("getRowIndex",row.USER_ID);
				if(rowIndex==-1){
					$("#SelectedSysUserGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedSysUserGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedSysUserGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout eui-jh-box" fit="true" >
		<div data-options="region:'west',split:false"
			style="width:250px;">
			<div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" >
			    <div title="按部门" >
					<ul id="userDepartTree" class="ztree"></ul>
				</div>
				<div title="按角色" >
				    <ul id="userRoleTree" class="ztree"></ul>
				</div>
				
			</div>
		</div>
		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="SysUserToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_D.PATH_LIKE" />
			 	<input type="hidden" name="DEPART_ID"  />	
			 	<input type="hidden" name="ROLE_ID"  />	
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选用户列表</img>
									<font style="color:#FF0033;float:right;" >如有重名，请注意人员归属部门!&nbsp;</font>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="SysUserForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">帐号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.USERNAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.FULLNAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SysUserToolbar','SysUserGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysUserForm')" /></td>
							<td colspan="2"><input type="button" value="选中" class="eve-button"
								   onclick="doMultiCheck()" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="SysUserGrid" fitColumns="true" toolbar="#SysUserToolbar"
				method="post" idField="USER_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="sysUserController.do?datagrid&noAuth=${noAuth}&Q_T.STATUS_EQ=1">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'USER_ID',hidden:true" width="80">USER_ID</th>
						<th data-options="field:'MOBILE',hidden:true" width="80">MOBILE</th>
						<th data-options="field:'USERNAME',align:'left'" width="100">帐号</th>
						<th data-options="field:'FULLNAME',align:'left'" width="100">用户姓名</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedSysUserToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="top: 0px;left: 0px; height: 70px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选用户列表
								</div>
							</div>
							<div class="e-toolbar-overflow">
								<div style="color:#005588;" >
									<input type="button"  style="margin-top: 2px;" value="移除" class="eve-button"
										   onclick="doRemoveCheck()" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" resize="true" id="SelectedSysUserGrid" striped="true"
			    fitColumns="true" toolbar="#SelectedSysUserToolbar" nowrap="false"
				method="post" idField="USER_ID" checkOnSelect="false" url="sysUserController.do?selected&userIds=${userIds}"
				selectOnCheck="false" fit="false" height="420px" style="margin-top: 300px;" border="false" >
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'USER_ID',hidden:true" width="80">USER_ID</th>
						<th data-options="field:'MOBILE',hidden:true" width="80">MOBILE</th>
						<th data-options="field:'USERNAME',align:'left'" width="100">帐号</th>
						<th data-options="field:'FULLNAME',align:'left'" width="100">用户姓名</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>

