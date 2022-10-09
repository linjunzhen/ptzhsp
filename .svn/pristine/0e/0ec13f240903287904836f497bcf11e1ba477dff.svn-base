<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function selectImplDepartBillOfRightPublish(){
		var departId = $("#implDepartIdPublish").val();	
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
                    $("#implDepartIdPublish").val(selectDepInfo.departIds);
                    $("#implDepartnamePublish").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function showBillOfRightDetail(){
		var entityId = AppUtil.getEditDataGridRecord("BillOfRightPublishGrid");
		if (entityId) {
			$.dialog.open("billOfRightController.do?draftDetail&operType=view&entityId="+ entityId, {
				title : "权责清单信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#BillOfRightPublishGrid").datagrid('reload');
				}
			}, false);
		}
	}

	function backBillOfRightPublishGridRecord() {
		var $dataGrid = $("#BillOfRightPublishGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要退回的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var rightIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					rightIds+=",";
				}
				rightIds+=rowsData[i].RIGHT_ID;
			}
			art.dialog.confirm("将撤至草稿库，您确定需要进行修改? ", function() {
				$.post("billOfRightController.do?updateStatus", {
					rightIds:rightIds,
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
	
	function editBillOfRightPublishGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("BillOfRightPublishGrid");
		if (entityId) {
			$.dialog.open("billOfRightController.do?modifyInfo&operType=edit&entityId="+ entityId, {
				title : "权责清单变更",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#BillOfRightPublishGrid").datagrid('reload');
					$("#BillOfRightPublishGrid").datagrid('clearSelections').datagrid('clearChecked');
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
		AppUtil.initAuthorityRes("BillOfRightPublishToolbar");
	});

	function showBillOfRightExcelExportSelect(){
		var RIGHT_NAME = ($("#BillOfRightPublishForm input[name='Q_T.RIGHT_NAME_LIKE']").val()).trim();
		var SUBITEM_NAME = ($("#BillOfRightPublishForm input[name='Q_T.SUBITEM_NAME_LIKE']").val()).trim();
        AppUtil.showExcelExportWin({
            dataGridId:"BillOfRightPublishGrid",
            tplKey:"BillOfRightReportTpl",
            excelName:"权责清单",
            queryParams:{
                "T.IMPL_DEPART_ID":$("#BillOfRightPublishForm input[name='Q_T.IMPL_DEPART_ID_=']").val(),
                "T.RIGHT_NAME":RIGHT_NAME,
                "T.SUBITEM_NAME":SUBITEM_NAME,
                "T.RIGHT_TYPE":$("#BillOfRightPublishForm input[name='Q_T.RIGHT_TYPE_=']").val()
            }
        });
	}
	//复制
	function copyBillOfRightPublishGridRecord(){
		var $dataGrid = $("#BillOfRightPublishGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要复制的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var rightIds = "";
			var olddepartIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					rightIds+=",";
				}
				rightIds+=rowsData[i].RIGHT_ID;
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
	                    $.post("billOfRightController.do?copyToOtherDepart",{
	                    	rightIds:rightIds,
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
	
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});
	$("#BillOfRightPublishGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BillOfRightPublishToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="DETAIL_BillOfRight" iconcls="eicon-file-text-o"
								plain="true" onclick="showBillOfRightDetail();">详细信息</a>
							<a href="#" class="easyui-linkbutton"
								reskey="BACK_BillOfRight" iconcls="eicon-rotate-left"
								plain="true" onclick="backBillOfRightPublishGridRecord();">退回草稿编辑</a>
							<a href="#" class="easyui-linkbutton"
								reskey="CHANGE_BillOfRight" iconcls="eicon-edit"
								plain="true" onclick="editBillOfRightPublishGridRecord();">变更</a>
							<a href="#" class="easyui-linkbutton" reskey="Export_BillOfRight"
                                iconcls="eicon-file-excel-o" plain="true" onclick="showBillOfRightExcelExportSelect();">导出数据</a> 
							<a href="#" class="easyui-linkbutton"
								reskey="COPY_BillOfRight" iconcls="eicon-copy"
								plain="true" onclick="copyBillOfRightPublishGridRecord();">复制</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BillOfRightPublishForm" id="BillOfRightPublishForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" id="implDepartIdPublish" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartBillOfRightPublish()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartnamePublish" /></td>
							<td style="width:68px;text-align:right;">项目名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.RIGHT_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">子项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SUBITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">权责类别</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RIGHT_TYPE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=qzlb',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BillOfRightPublishToolbar','BillOfRightPublishGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BillOfRightPublishForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BillOfRightPublishGrid" fitcolumns="true"
			toolbar="#BillOfRightPublishToolbar" method="post" idfield="RIGHT_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="billOfRightController.do?datagrid&Q_T.STATUS_EQ=1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RIGHT_ID',hidden:true">RIGHT_ID</th>
					<th data-options="field:'IMPL_DEPART_ID',hidden:true">IMPL_DEPART_ID</th>
					<th data-options="field:'RIGHT_NAME',align:'left'" width="27%">项目名称</th>
					<th data-options="field:'SUBITEM_NAME',align:'left'" width="27%">子项名称</th>
					<th data-options="field:'RIGHT_TYPE',align:'left'" width="10%">权责类别</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'C_SN',align:'left'" width="8%">排序值</th>
					<th data-options="field:'STATUS',align:'left'" width="8%" formatter="formateStatus">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
