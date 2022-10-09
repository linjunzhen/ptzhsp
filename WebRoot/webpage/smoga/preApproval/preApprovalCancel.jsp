<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function selectImplDepartPreApprovalCancel(){
		var departId = $("#PreApprovalCancelForm input[name='Q_T.IMPL_DEPART_ID_=']").val();	
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
                    $("#PreApprovalCancelForm input[name='Q_T.IMPL_DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("#PreApprovalCancelForm input[id='implDepartname']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}

	function showPreApprovalCancelDetail(){
		var entityId = AppUtil.getEditDataGridRecord("PreApprovalCancelGrid");
		if (entityId) {
			$.dialog.open("preApprovalController.do?draftDetail&operType=cancelView&entityId="+ entityId, {
				title : "前置审批信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#PreApprovalCancelGrid").datagrid('reload');
				}
			}, false);
		}
	}
		
	function formateStatus(val, row) {
		if (val == "5") {
			return "<font color='gray'><b>已取消</b></font>";
		}
	}
	$(document).ready(function() {
		AppUtil.initAuthorityRes("PreApprovalCancelToolbar");
	});

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="PreApprovalCancelToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="DETAIL_PreApproval" iconcls="eicon-file-text-o"
								plain="true" onclick="showPreApprovalCancelDetail();">详细信息</a>
							<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="PreApprovalCancelForm" id="PreApprovalCancelForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartPreApprovalCancel()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartname" /></td>
							<td style="width:78px;text-align:right;">审批事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('PreApprovalCancelToolbar','PreApprovalCancelGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('PreApprovalCancelForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="PreApprovalCancelGrid" fitcolumns="true"
			toolbar="#PreApprovalCancelToolbar" method="post" idfield="PRE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="preApprovalController.do?datagrid&Q_T.STATUS_EQ=5">
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
