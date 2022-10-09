<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function selectImplDepartAgencyServiceCancel(){
		var departId = $("#AgencyServiceCancelForm input[name='Q_T.IMPL_DEPART_ID_=']").val();	
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
                    $("#AgencyServiceCancelForm input[name='Q_T.IMPL_DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("#AgencyServiceCancelForm input[id='implDepartname']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function showAgencyServiceCancelDetail(){
		var entityId = AppUtil.getEditDataGridRecord("AgencyServiceCancelGrid");
		if (entityId) {
			$.dialog.open("agencyServiceController.do?draftDetail&operType=cancelView&entityId="+ entityId, {
				title : "中介服务信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#AgencyServiceCancelGrid").datagrid('reload');
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
		AppUtil.initAuthorityRes("AgencyServiceCancelToolbar");
	});

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="AgencyServiceCancelToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="DETAIL_AgencyService" iconcls="eicon-file-text-o"
								plain="true" onclick="showAgencyServiceCancelDetail();">详细信息</a>
							<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="AgencyServiceCancelForm" id="AgencyServiceCancelForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartAgencyServiceCancel()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartname" /></td>
							<td style="width:78px;text-align:right;">审批事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('AgencyServiceCancelToolbar','AgencyServiceCancelGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AgencyServiceCancelForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="AgencyServiceCancelGrid" fitcolumns="true"
			toolbar="#AgencyServiceCancelToolbar" method="post" idfield="SERVICE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="agencyServiceController.do?datagrid&Q_T.STATUS_EQ=5">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SERVICE_ID',hidden:true"">SERVICE_ID</th>
					<th data-options="field:'AGENCY_ITEM_NAME',align:'left'" width="30%">中介事项名称</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="30%">审批事项名称</th>
					<th data-options="field:'AGENCY_ORG_TYPE_NAME',align:'left'" width="10%">中介类型</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'STATUS',align:'left'" width="10%" formatter="formateStatus" >状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
