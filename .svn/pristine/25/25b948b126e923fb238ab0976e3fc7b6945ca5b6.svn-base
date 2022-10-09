<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除前台用户管理列表记录
	 */
	function deleteUserInfoGridRecord() {
		AppUtil.deleteDataGridRecord("userInfoController.do?multiDel",
				"UserInfoGrid");
	};
	/**
	 * 编辑前台用户管理列表记录
	 */
	function editUserInfoGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("UserInfoGrid");
		if (entityId) {
			showUserInfoWindow(entityId);
		}
	}

	/**
	 * 显示前台用户管理信息对话框
	 */
	function showUserInfoWindow(entityId) {
		$.dialog.open("userInfoController.do?info&entityId=" + entityId, {
			title : "前台用户信息",
			width : "650px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {

		AppUtil.initAuthorityRes("UserInfoToolbar");

	});
	function formatStatus(val,row){
        if(val=="1"){
            return "<font color='#0368ff'><b>正常</b></font>";
        }else if(val=="-1"){
            return "<font color='#ff4b4b'><b>禁用</b></font>";
        }else{
            return "<font color='#fa5800'><b>锁定</b></font>";
        }
    }
	function formatType(val,row){
        if(val=="1"){
            return "<font ><b>个人用户</b></font>";
        }else{
            return "<font ><b>企业用户</b></font>";
        }
    }
	function updateStatus(status){
        AppUtil.multiOperateDataGridRecord("userInfoController.do?updateYHZT&YHZT="+status,
        "UserInfoGrid");
    };
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="UserInfoToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_UserInfo"
								iconcls="eicon-eye" plain="true"
								onclick="editUserInfoGridRecord();">查看</a> <a href="#"
								class="easyui-linkbutton" reskey="QY_UserInfo"
								iconcls="eicon-check" plain="true"
								onclick="updateStatus(1);">启用</a> <a href="#"
                                class="easyui-linkbutton" reskey="JY_UserInfo"
                                iconcls="eicon-ban" plain="true"
                                onclick="updateStatus(-1);">禁用</a> 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="UserInfoForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
						    <td style="width:68px;text-align:right;">用户账号</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.YHZH_LIKE" />
                            </td>
							<td style="width:68px;text-align:right;">用户名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.YHMC_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('UserInfoToolbar','UserInfoGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('UserInfoForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="UserInfoGrid" fitcolumns="false" toolbar="#UserInfoToolbar"
			method="post" idfield="USER_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false"
			url="userInfoController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'USER_ID',hidden:true">USER_ID</th>
					<th data-options="field:'YHZH',align:'left'" width="25%">用户账号</th>
					<th data-options="field:'YHMC',align:'left'" width="25%">名称</th>
					<th data-options="field:'SJHM',align:'left'" width="20%">手机号码</th>
					<th data-options="field:'USER_TYPE',align:'left'" width="10%" formatter="formatType">用户类型</th>
					<th data-options="field:'YHZT',align:'left'" width="15%" formatter="formatStatus">用户状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
