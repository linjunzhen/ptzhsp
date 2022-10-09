<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function publishPreApprovalDraftGridRecord(){
		var $dataGrid = $("#PreApprovalDraftGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要直接发布的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var preIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					preIds+=",";
				}
				if(rowsData[i].STATUS!='-1'){
					art.dialog({
						content : "仅草稿状态的记录可直接发布",
						icon : "error",
						ok : true
					});
					return;
				}
				preIds+=rowsData[i].PRE_ID;
			}			
			art.dialog.confirm("您确定要将所选择的记录直接发布吗?", function() {
				$.post("preApprovalController.do?updateStatus", {
					preIds:preIds,
					status:'1'
				}, function(responseText, status, xhr) {
					var resultJson = $.parseJSON(responseText);
					if (resultJson.success == true) {					
						art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							ok : true
						});
						$dataGrid.datagrid('reload');
						$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
					} else {
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				});
			});
		}
	}
	/**
     * 申请审核服务事项列表记录
     */
	function applyPreApprovalDraftGridRecord() {
		var $dataGrid = $("#PreApprovalDraftGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要提交审核的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var preIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					preIds+=",";
				}
				preIds+=rowsData[i].PRE_ID;
			}
			$.post("preApprovalController.do?updateStatus", {
				preIds:preIds,
				status:'2'
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {					
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						ok : true
					});
					$dataGrid.datagrid('reload');
					$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
				} else {
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			});
		}
	}
	/**
	 * 编辑服务事项列表记录
	 */
	function editPreApprovalDraftGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("PreApprovalDraftGrid");
		if (entityId) {
			showPreApprovalDraftWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showPreApprovalDraftWindow(entityId) {
		$.dialog.open("preApprovalController.do?draftInfo&entityId="+ entityId, {
			title : "前置审批信息",
			width : "100%",
			height : "100%",
			left : "0%",
			top : "0%",
			fixed : true,
			lock : true,
			resize : false,
			close : function() {
				$("#PreApprovalDraftGrid").datagrid('reload');
			}
		}, false);
	}
	
	function delPreApprovalDraftGridRecord(){
		AppUtil.deleteDataGridRecord("preApprovalController.do?multiDel",
	            "PreApprovalDraftGrid");
	}
	
	function formateStatus(val, row) {
		if (val == "-1") {
			return "<font color='grey'><b>草稿</b></font>";
		}  else if (val == "-2") {
			return "<font color='black'><b>编辑撤回</b></font>";
		} else if (val == "3") {
			return "<font color='red'><b>退回</b></font>";
		} else if (val == "7") {
			return "<font color='red'><b>变更申请退回</b></font>";
		}
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("PreApprovalDraftToolbar");
	});

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PreApprovalDraftToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="ADD_PreApproval" iconcls="eicon-plus"
								plain="true" onclick="showPreApprovalDraftWindow('');">新建</a>
							<a href="#" class="easyui-linkbutton"
								reskey="EDIT_PreApproval" iconcls="eicon-pencil"
								plain="true" onclick="editPreApprovalDraftGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton"
								reskey="APPLY_PreApproval" iconcls="eicon-level-up"
								plain="true" onclick="applyPreApprovalDraftGridRecord();">提交审核</a>
							<a href="#" class="easyui-linkbutton"
								reskey="ONLYPUBLISH_PreApproval" iconcls="eicon-eject"
								plain="true" onclick="publishPreApprovalDraftGridRecord();">直接发布</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="PreApprovalDraftForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:78px;text-align:right;">审批事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('PreApprovalDraftToolbar','PreApprovalDraftGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PreApprovalDraftForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="PreApprovalDraftGrid" fitcolumns="true"
			toolbar="#PreApprovalDraftToolbar" method="post" idfield="PRE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="preApprovalController.do?datagrid&Q_T.STATUS_IN=-1,-2,3,7">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PRE_ID',hidden:true">PRE_ID</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="50%">审批事项名称</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="16%">实施部门</th>
					<th data-options="field:'STATUS',align:'left'" width="30%" formatter="formateStatus" >状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
