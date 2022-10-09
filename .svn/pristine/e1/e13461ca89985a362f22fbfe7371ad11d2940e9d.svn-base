<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function selectImplDepartPreApprovalAudit(){
		var departId = $("#PreApprovalAuditForm input[name='Q_T.IMPL_DEPART_ID_=']").val();	
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "实施部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("#PreApprovalAuditForm input[name='Q_T.IMPL_DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("#PreApprovalAuditForm input[id='implDepartname']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function auditPreApprovalDraftWindow(){
		var entityId = AppUtil.getEditDataGridRecord("PreApprovalAuditGrid");
		if(entityId){
			$.dialog.open("preApprovalController.do?draftDetail&operType=audit&entityId="+ entityId, {
				title : "前置审批信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#PreApprovalAuditGrid").datagrid('reload');
					$("#PreApprovalAuditGrid").datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		}
	}

	function auditPreApprovalAuditGridRecord() {
		var $dataGrid = $("#PreApprovalAuditGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要操作的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var colName = $dataGrid.datagrid('options').idField;
			var selectColNames = "";
			for (var i = 0; i < rowsData.length; i++) {
				if (i > 0) {
					selectColNames += ",";
				}
				selectColNames += eval('rowsData[i].' + colName);
			}
			$.dialog.open("preApprovalController.do?opinionInfo&preIds=" + selectColNames, {
				title : "审核意见",
				width : "600px",
				height : "390px",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					var backinfo = art.dialog.data("backinfo");
					if (backinfo && backinfo.back) {
						art.dialog({
							content : "提交成功",
							icon : "succeed",
							time : 3,
							ok : true
						});
						art.dialog.removeData("backinfo");
						$dataGrid.datagrid('reload');
						$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
					}
				}
			}, false);
		}
	}
	
	function formateStatus(val, row) {
		if (val == "2") {
			return "<font color='blue'><b>审核发布</b></font>";
		}else if (val == "4") {
			return "<font color='black'><b>审核取消</b></font>";
		}else if (val == "6") {
			return "<font color='blue'><b>审核变更发布</b></font>";
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("PreApprovalAuditToolbar");
	});

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PreApprovalAuditToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="Audit_PreApproval" iconcls="eicon-check"
								plain="true" onclick="auditPreApprovalDraftWindow();">审核</a>
							<a href="#" class="easyui-linkbutton"
								reskey="AllAudit_PreApproval" iconcls="eicon-th-list"
								plain="true" onclick="auditPreApprovalAuditGridRecord();">批量审核</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="PreApprovalAuditForm" id="PreApprovalAuditForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartPreApprovalAudit()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartname" /></td>
							<td style="width:78px;text-align:right;">审批事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('PreApprovalAuditToolbar','PreApprovalAuditGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PreApprovalAuditForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="PreApprovalAuditGrid" fitcolumns="true"
			toolbar="#PreApprovalAuditToolbar" method="post" idfield="PRE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="preApprovalController.do?datagrid&Q_T.STATUS_IN=2,4,6">
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
