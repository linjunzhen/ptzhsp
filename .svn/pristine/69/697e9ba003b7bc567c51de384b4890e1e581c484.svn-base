<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 预审操作
	 * flag  为预审操作类型    flag:1  通过   2：不通过   3.补件 
	 */
	function preAudit(flag) {
		var entityId="";
	
		//var entityId = AppUtil.getEditDataGridRecord("PreAuditGrid");
		//if (entityId) {
		var title = '';
		if(flag == 1){
			title="预审通过";
		}else if(flag==2){
			title="预审不通过";
		}else if(flag==3){
			title="预审补件";
		}else if(flag==4){
			title="预审转办";
		}
		$.dialog.open("preAuditController.do?proAudit&entityId=" + entityId+"&flag="+flag,
		{
			title : title,
			width : "60%",
			height : "70%",
			fixed : true,
			lock : true,
			resize : false,
			close : function() {
				AppUtil.gridDoSearch("PreAuditToolbar",
							"PreAuditGrid");
			}
		}, false);
		//}
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("PreAuditToolbar");
	});

	function formatFwdx(val, row) {
		if (val == "1") {
			return "公众";
		} else if (val == "2") {
			return "企业";
		} else if (val == "3") {
			return "部门";
		}
	}

	function formatFwsxzt(val, row) {
		if (val == "-1") {
			return "<font color='red'><b>草稿</b></font>";
		} else if (val == "1") {
			return "<font color='blue'><b>发布</b></font>";
		} else if (val == "2") {
			return "<font color='green'><b>审核中</b></font>";
		} else if (val == "3") {
			return "<font color='black'><b>取消</b></font>";
		}
	};
	function formatOper(val, row, index) {
		return '<a href="javascript:void(0);" style="color:blue;" onclick="editUser('
				+ index + ')">详细信息</a>';
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PreAuditToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_ServiceItem"
								iconcls="icon-note-add" plain="true" onclick="preAudit(4);">预审转办</a>
							<a href="#" class="easyui-linkbutton" reskey="EDIT_ServiceItem"
								iconcls="icon-note-edit" plain="true" onclick="preAudit(1);">预审通过</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_ServiceItem"
								iconcls="icon-note-delete" plain="true" onclick="preAudit(2);">预审不通过</a>
							<a href="#" class="easyui-linkbutton" reskey="DEL_ServiceItem"
								iconcls="icon-note-delete" plain="true" onclick="preAudit(3);">预审补件</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="PreAuditForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:200x;">查询内容<input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
									editable:false,
									valueField:'paramValue',
									textField:'paramName',
									panelWidth: 150,
									panelHeight: 'auto',
									value:'COL_NUMBER',
									data: [{paramName:'申报号',paramValue:'COL_NUMBER'},{paramName:'申请人',paramValue:'COL_USER'},{paramName:'事项名称',paramValue:'COL_NAME'}]" />
								<input class="eve-input" type="text" maxlength="20"
								style="width:200px;" name="Q_T.ITEM_CODE_LIKE" /> <span
								style="width:20px;display:inline-block"></span> <input
								type="button" value="查询" class="eve-button"
								onclick="AppUtil.gridDoSearch('PreAuditToolbar','PreAuditGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PreAuditForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="PreAuditGrid" fitcolumns="true" toolbar="#PreAuditToolbar"
			method="post" idfield="ITEM_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="serviceItemController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="200">申报名称</th>
					<th data-options="field:'FWSXZT',align:'left'" width="150"
						formatter="formatFwsxzt">事项名称</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="100">申请人</th>
					<th data-options="field:'SXXZ',align:'left'" width="100">申报号</th>
					<th data-options="field:'SXLX',align:'left'" width="100">申报时间</th>
					<th
						data-options="field:'_operate',width:80,align:'center',formatter:formatOper,align:'left'"
						width="100">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
