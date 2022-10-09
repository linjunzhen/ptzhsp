<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

function deleteWeiboAccountGridRecord() {
	AppUtil.deleteDataGridRecord("weiboAccountController.do?multiDel",
			"ContactGrid");
};
function editWeiboAccountGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("ContactGrid");
	if (entityId) {
		showWeiboAccountWindow(entityId);
	}
}

function showWeiboAccountWindow(entityId) {
	$.dialog.open("weiboAccountController.do?info&entityId=" + entityId, {
		title : "微博备案管理信息",
		width : "600px",
		height : "400px",
		lock : true,
		resize : false
	}, false);
};

$(document).ready(function() {

	AppUtil.initAuthorityRes("ContactToolbar");

});
function formatStatus(val,row){
	if(val=="1"){
		return "<font color='red'><b><a href=\"javascript:createWeibo('"+row.ID+"','"+row.USER_NAME+"')\">发布微博</a></b></font>";
	}else{
		return "<font color='red'><b><a href=\"javascript:jihuo('"+row.ID+"')\">未激活</a></b></font>";
	}
}

function addContactWin(){
	$.dialog.open("busContactController.do?info", {
		title : "联系人信息",
		width : "600px",
		height : "280px",
		lock : true,
		resize : false,
		close: function () {
			$("#ContactGrid").datagrid("reload");
		}
	}, false);
}

function refresh(){
	$("#ContactGrid").datagrid("reload");
	$("#ContactGrid2").datagrid("reload");
	$("#ContactGrid2").datagrid("reload");
	$("#ContactGrid3").datagrid("reload");
}

function closeAllDialog(){
	var list = art.dialog.list;
	for (var i in list) {
		list[i].close();
	}
	refresh();
}
</script>
<div id="ContactToolbar">
	<form action="#" name="ContactSearchFrom">
		<input type="hidden" name="Q_L.CONTACT_TYPE_EQ" value="0"/>
		<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;" >
			<tbody>
				<tr style="height:28px;">
				<td style="width:80px;text-align:right;">所属单位&nbsp;&nbsp;</td>
				<td style="width:180px;"><input class="eve-input" 
					type="text" maxlength="60" style="width:170px;" 
					name="Q_L.CONTACT_DEPT_LIKE"/></td>
			      	<td style="width:80px;text-align:right;">联系人姓名&nbsp;&nbsp;</td>
				<td style="width:135px;"><input class="eve-input"
					type="text" maxlength="20" style="width:128px;"
					name="Q_L.CONTACT_NAME_LIKE" /></td>							
				<td style="width:350px;"><input type="button" value="查询"
					class="eve-button"
					onclick="AppUtil.gridsDoSearch('ContactToolbar','ContactGrid')" />
					<input type="button" value="重置" class="eve-button"
					onclick="AppUtil.gridSearchReset('ContactSearchFrom')" /></td>
				<td ><a href="#" class="easyui-linkbutton" reskey="ADD_WeiboAccount"
					iconcls="icon-note-add" plain="true"
					onclick="addContactWin();">新增联系人</a></td>
						</tr>
				</tbody>
			</table>
		</form>
</div>
<div class="easyui-tabs eve-tabs" fit="true" style="width:100%;height:100%">
	<div title="业务处室" >
 		<div region="center" style="height:100%" >
			
			 <table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ContactGrid" fitcolumns="false" toolbar="#ContactToolbar"
				 method="post" idfield="ID"
				checkonselect="false" selectoncheck="false" fit="true" border="false"
				url="busContactController.do?datagrid&Q_L.CONTACT_TYPE_EQ=0">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'CONTACT_DEPT',align:'left'" width="200">所属单位</th>
						<th data-options="field:'CONTACT_NAME',align:'left'" width="150">姓名</th>
						<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
						<th data-options="field:'CONTACT_MOBILE',align:'left'" width="180">手机号码</th>
						<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>						
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div title="信息科室" >
		<div region="center" style="height:100%" >
 		 <div id="ContactToolbar1">
			<form action="#" name="ContactSearchFrom">
				<input type="hidden" name="Q_L.CONTACT_TYPE_EQ" value="1"/>
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;" >
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">所属单位&nbsp;&nbsp;</td>
							<td style="width:180px;"><input class="eve-input" 
								type="text" maxlength="60" style="width:170px;" 
								name="Q_L.CONTACT_DEPT_LIKE"/></td>
					       	<td style="width:80px;text-align:right;">联系人姓名&nbsp;&nbsp;</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_L.CONTACT_NAME_LIKE" /></td>							
							<td style="width:350px;"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridsDoSearch('ContactToolbar1','ContactGrid1')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ContactSearchFrom')" /></td>
							<td ><a href="#" class="easyui-linkbutton" reskey="ADD_WeiboAccount"
								iconcls="icon-note-add" plain="true"
								onclick="addContactWin();">新增联系人</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		 <table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ContactGrid1" fitcolumns="false" toolbar="#ContactToolbar1"
			 method="post" idfield="ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="busContactController.do?datagrid&Q_L.CONTACT_TYPE_EQ=1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true" width="80">ID</th>
					<th data-options="field:'CONTACT_DEPT',align:'left'" width="200">所属单位</th>
					<th data-options="field:'CONTACT_NAME',align:'left'" width="150">姓名</th>
					<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
					<th data-options="field:'CONTACT_MOBILE',align:'left'" width="180">手机号码</th>
					<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>
				</tr>
			</thead>
		</table>
		</div>
	</div>
	<div title="系统开发商等各方负责人" >
		<div region="center" style="height:100%" >
 		 <div id="ContactToolbar2">
			<form action="#" name="ContactSearchFrom">
			    <input type="hidden" name="Q_L.CONTACT_TYPE_EQ" value="2"/>
				<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;" >
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">所属单位&nbsp;&nbsp;</td>
							<td style="width:180px;"><input class="eve-input" 
								type="text" maxlength="60" style="width:170px;" 
								name="Q_L.CONTACT_DEPT_LIKE"/></td>
					       	<td style="width:80px;text-align:right;">联系人姓名&nbsp;&nbsp;</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SCREEN_NAME_LIKE" /></td>
							<td style="width:350px;"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridsDoSearch('ContactToolbar2','ContactGrid2')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ContactSearchFrom')" /></td>
							<td ><a href="#" class="easyui-linkbutton" reskey="ADD_WeiboAccount"
								iconcls="icon-note-add" plain="true"
								onclick="addContactWin();">新增联系人</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
			 <table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ContactGrid2" fitcolumns="false" toolbar="#ContactToolbar2"
				 method="post" idfield="ID"
				checkonselect="false" selectoncheck="false" fit="true" border="false"
				url="busContactController.do?datagrid&Q_L.CONTACT_TYPE_EQ=2">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'CONTACT_DEPT',align:'left'" width="200">所属单位</th>
						<th data-options="field:'CONTACT_NAME',align:'left'" width="150">姓名</th>
						<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
						<th data-options="field:'CONTACT_MOBILE',align:'left'" width="180">手机号码</th>
						<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div title="经办" >
		<div region="center" style="height:100%" >
 			  <div id="ContactToolBar3">
			<form action="#" name="ContactSearchFrom">
			    <input type="hidden" name="Q_L.CONTACT_TYPE_EQ" value="3"/>
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;" >
					<tbody>
						<tr style="height:28px;">
							
							<td style="width:80px;text-align:right;">所属单位&nbsp;&nbsp;</td>
							<td style="width:180px;"><input class="eve-input" 
								type="text" maxlength="60" style="width:170px;" 
								name="Q_L.CONTACT_DEPT_LIKE"/></td>
					       	<td style="width:80px;text-align:right;">联系人姓名&nbsp;&nbsp;</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SCREEN_NAME_LIKE" /></td>
							<td style="width:350px;"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridsDoSearch('ContactToolBar3','ContactGrid3')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ContactSearchFrom')" /></td>
							<td ><a href="#" class="easyui-linkbutton" reskey="ADD_WeiboAccount"
								iconcls="icon-note-add" plain="true"
								onclick="addContactWin();">新增联系人</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
			 <table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ContactGrid3" fitcolumns="false" toolbar="#ContactToolBar3"
				 method="post" idfield="ID"
				checkonselect="false" selectoncheck="false" fit="true" border="false"
				url="busContactController.do?datagrid&Q_L.CONTACT_TYPE_EQ=3">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'CONTACT_DEPT',align:'left'" width="200">所属单位</th>
						<th data-options="field:'CONTACT_NAME',align:'left'" width="150">姓名</th>
						<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
						<th data-options="field:'CONTACT_MOBILE',align:'left'" width="180">手机号码</th>
						<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div title="对接技术人员" >
		<div region="center" style="height:100%" >
 		  <div id="ContactToolBar4">
			<form action="#" name="ContactSearchFrom">
			    <input type="hidden" name="Q_L.CONTACT_TYPE_EQ" value="4"/>
				<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;" >
					<tbody>
						<tr style="height:28px;">
							
							<td style="width:80px;text-align:right;">所属单位&nbsp;&nbsp;</td>
							<td style="width:180px;"><input class="eve-input" 
								type="text" maxlength="60" style="width:170px;" 
								name="Q_L.CONTACT_DEPT_LIKE"/></td>
					       	<td style="width:80px;text-align:right;">联系人姓名&nbsp;&nbsp;</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SCREEN_NAME_LIKE" /></td>
							<td style="width:350px;"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridsDoSearch('ContactToolBar4','ContactGrid4')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ContactSearchFrom')" /></td>
							<td ><a href="#" class="easyui-linkbutton" reskey="ADD_WeiboAccount"
								iconcls="icon-note-add" plain="true"
								onclick="addContactWin();">新增联系人</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
			 <table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ContactGrid4" fitcolumns="false" toolbar="#ContactToolBar4"
				 method="post" idfield="ID"
				checkonselect="false" selectoncheck="false" fit="true" border="false"
				url="busContactController.do?datagrid&Q_L.CONTACT_TYPE_EQ=4">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ID',hidden:true" width="80">ID</th>
						<th data-options="field:'CONTACT_DEPT',align:'left'" width="200">所属单位</th>
						<th data-options="field:'CONTACT_NAME',align:'left'" width="150">姓名</th>
						<th data-options="field:'CONTACT_PHONE',align:'left'" width="150">固定电话</th>
						<th data-options="field:'CONTACT_MOBILE',align:'left'" width="180">手机号码</th>
						<th data-options="field:'CONTACT_EMAIL',align:'left'" width="150">电子邮箱</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>