<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript" src="plug-in/ztree-3.5/js/jquery.ztree.exhide-3.5.min.js"></script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#SelectedSysUserGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
			var handlers = new Array();
			for(var i = 0;i<rows.length;i++){
				var handler = {};
				handler.nextStepAssignerCode = rows[i].USERNAME;
				handler.nextStepAssignerName = rows[i].FULLNAME;
				handler.nextStepAssignerDepName = rows[i].DEPART_NAME;
				handlers.push(handler);
			}
			art.dialog.data("EFLOW_SELECTHANDLERS", {
				handlers:handlers
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

	$(function() {
		var curUserResKeys = $("input[name='curUserResKeys']").val();
		var allowCount = $("input[name='allowCount']").val();
		var noAuth = $("input[name='noAuth']").val();
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
				onClick : onDepartmentTreeClick,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
					if (curUserResKeys != "__ALL"&&noAuth=="false") {
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
    <input type="hidden" name="noAuth" value="${noAuth}">
    <input type="hidden" value="${sessionScope.curLoginUser.loginUserInfoJson}" name="loginUserInfoJson"> 
	<input type="hidden" value="${sessionScope.curLoginUser.resKeys}" name="curUserResKeys">
	<div class="easyui-layout" fit="true" >
		<div data-options="region:'west',split:false"
			style="width:250px;">
			<div class="easyui-tabs eve-tabs" fit="true" >
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
							<td style="width:68px;text-align:right;">用户姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.FULLNAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('SysUserToolbar','SysUserGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('SysUserForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="SysUserGrid" fitColumns="true" toolbar="#SysUserToolbar"
				method="post" idField="USER_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="sysUserController.do?datagrid&noAuth=${noAuth}&Q_T.STATUS_EQ=1">
				<thead>
					<tr>
						<th data-options="field:'USER_ID',hidden:true" width="80">USER_ID</th>
						<th data-options="field:'DEPART_NAME'" width="80">所属部门</th>
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
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选用户列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedSysUserGrid" 
			    fitColumns="true" toolbar="#SelectedSysUserToolbar" nowrap="false"
				method="post" idField="USER_ID" checkOnSelect="false" url="sysUserController.do?selected&userAccounts=${userAccounts}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'USER_ID',hidden:true" width="80">USER_ID</th>
						<th data-options="field:'DEPART_NAME'" width="80">所属部门</th>
						<th data-options="field:'USERNAME',align:'left'" width="100">帐号</th>
						<th data-options="field:'FULLNAME',align:'left'" width="100">用户姓名</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
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

