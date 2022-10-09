<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function selectImplDepartAgencyServicePublish(){
		var departId = $("#AgencyServicePublishForm input[name='Q_T.IMPL_DEPART_ID_=']").val();	
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
                    $("#AgencyServicePublishForm input[name='Q_T.IMPL_DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("#AgencyServicePublishForm input[id='implDepartname']").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function showAgencyServiceDetail(){
		var entityId = AppUtil.getEditDataGridRecord("AgencyServicePublishGrid");
		if (entityId) {
			$.dialog.open("agencyServiceController.do?draftDetail&operType=view&entityId="+ entityId, {
				title : "中介服务信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#AgencyServicePublishGrid").datagrid('reload');
				}
			}, false);
		}
	}

	function backAgencyServicePublishGridRecord() {
		var $dataGrid = $("#AgencyServicePublishGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要退回的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var serviceIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					serviceIds+=",";
				}
				serviceIds+=rowsData[i].SERVICE_ID;
			}
			art.dialog.confirm("将撤至草稿库，您确定需要进行修改? ", function() {
				$.post("agencyServiceController.do?updateStatus", {
					serviceIds:serviceIds,
					status:'-2'
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
	
	function editAgencyServicePublishGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("AgencyServicePublishGrid");
		if (entityId) {
			$.dialog.open("agencyServiceController.do?modifyInfo&operType=edit&entityId="+ entityId, {
				title : "中介服务变更",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#AgencyServicePublishGrid").datagrid('reload');
					$("#AgencyServicePublishGrid").datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		}
	}
	
	function formateStatus(val, row) {
		if (val == "1") {
			return "<font color='blue'><b>已发布</b></font>";
		}
	}
	
	$(document).ready(function() {
		AppUtil.initAuthorityRes("AgencyServicePublishToolbar");
	});
	
	function showAgencyServiceExcelExportSelect(){
        AppUtil.showExcelExportWin({
            dataGridId:"AgencyServicePublishGrid",
            tplKey:"AgencyServiceReportTpl",
            excelName:"中介服务",
            queryParams:{
                "T.AGENCY_ITEM_NAME":($("#AgencyServicePublishForm input[name='Q_T.AGENCY_ITEM_NAME_LIKE']").val()).trim(),
                "T.IMPL_DEPART_ID":$("#AgencyServicePublishForm input[name='Q_T.IMPL_DEPART_ID_=']").val(),
                "T.ITEM_NAME":($("#AgencyServicePublishForm input[name='Q_T.ITEM_NAME_LIKE']").val()).trim()
            }
        });
	}
	//复制
	function copyAgencyServicePublishGridRecord(){
		var $dataGrid = $("#AgencyServicePublishGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要复制的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var serviceIds = "";
			var olddepartIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					serviceIds+=",";
				}
				serviceIds+=rowsData[i].SERVICE_ID;
				if(olddepartIds.indexOf(rowsData[i].IMPL_DEPART_ID)==-1){
					olddepartIds+=rowsData[i].IMPL_DEPART_ID;
				}
			}	
	        parent.$.dialog.open("departmentController/parentSelector.do?allowCount=5&checkCascadeY=&"
	                +"checkCascadeN=", {
	            title : "选择复制到的部门",
	            width:"600px",
	            lock: true,
	            resize:false,
	            height:"500px",
	            close: function () {
	                var selectDepInfo = art.dialog.data("selectDepInfo");
	                if(selectDepInfo){
	                    var departIds = selectDepInfo.departIds;
	                    var departNames = selectDepInfo.departNames;
	                    var departId = departIds.split(",");
	                    for(var i=0;i<departId.length;i++){
	                    	if(olddepartIds.indexOf(departId[i])!=-1){
								art.dialog({
									content : "不可复制本部门项目到自己",
									icon : "error",
									ok : true
								});
								return;
	                    	}
	                    }
	                    art.dialog.removeData("selectDepInfo");
	                    $.post("agencyServiceController.do?copyToOtherDepart",{
	                    	serviceIds:serviceIds,
							departIds:departIds,
							departNames:departNames
						}, function(responseText, status, xhr) {
							resultJson = $.parseJSON(responseText);
							if(resultJson.success){
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									ok : true
								});
								$dataGrid.datagrid('reload');
								$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
							}
						});
	                }
	            }
	        }, false);
        }
	}

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="AgencyServicePublishToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="DETAIL_AgencyService" iconcls="eicon-file-text-o"
								plain="true" onclick="showAgencyServiceDetail();">详细信息</a>
							<a href="#" class="easyui-linkbutton"
								reskey="BACK_AgencyService" iconcls="l-btn-icon eicon-rotate-left"
								plain="true" onclick="backAgencyServicePublishGridRecord();">退回草稿编辑</a>
							<a href="#" class="easyui-linkbutton"
								reskey="CHANGE_AgencyService" iconcls="eicon-edit"
								plain="true" onclick="editAgencyServicePublishGridRecord();">变更</a>
							<a href="#" class="easyui-linkbutton" reskey="Export_PreApproval"
                                iconcls="eicon-file-excel-o" plain="true" onclick="showAgencyServiceExcelExportSelect();">导出数据</a>
							<a href="#" class="easyui-linkbutton"
								reskey="COPY_AgencyService" iconcls="eicon-copy"
								plain="true" onclick="copyAgencyServicePublishGridRecord();">复制</a> 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="AgencyServicePublishForm" id="AgencyServicePublishForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartAgencyServicePublish()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartname" /></td>
							<td style="width:78px;text-align:right;">中介事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.AGENCY_ITEM_NAME_LIKE" /></td>
							<td style="width:78px;text-align:right;">审批事项名称</td>
							<td style="width:175px;"><input class="eve-input"
								type="text" maxlength="20" style="width:168px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('AgencyServicePublishToolbar','AgencyServicePublishGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('AgencyServicePublishForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="AgencyServicePublishGrid" fitcolumns="true"
			toolbar="#AgencyServicePublishToolbar" method="post" idfield="SERVICE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="agencyServiceController.do?datagrid&Q_T.STATUS_EQ=1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SERVICE_ID',hidden:true">SERVICE_ID</th>
					<th data-options="field:'IMPL_DEPART_ID',hidden:true">IMPL_DEPART_ID</th>
					<th data-options="field:'AGENCY_ITEM_NAME',align:'left'" width="26%">中介事项名称</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="26%">审批事项名称</th>
					<th data-options="field:'AGENCY_ORG_TYPE_NAME',align:'left'" width="10%">中介类型</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'C_SN',align:'left'" width="8%">排序值</th>
					<th data-options="field:'STATUS',align:'left'" width="10%" formatter="formateStatus" >状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
