<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function selectImplDepartBillOfRightAudit(){
		var departId = $("#implDepartIdAudit").val();	
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
                    $("#implDepartIdAudit").val(selectDepInfo.departIds);
                    $("#implDepartnameAudit").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}
	
	function auditBillOfRightDraftWindow(){
		var entityId = AppUtil.getEditDataGridRecord("BillOfRightAuditGrid");
		if(entityId){
			$.dialog.open("billOfRightController.do?draftDetail&operType=audit&entityId="+ entityId, {
				title : "权责清单信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#BillOfRightAuditGrid").datagrid('reload');
					$("#BillOfRightAuditGrid").datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		}
	}

	function auditBillOfRightAuditGridRecord() {
		var $dataGrid = $("#BillOfRightAuditGrid");
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
			$.dialog.open("billOfRightController.do?opinionInfo&rightIds=" + selectColNames, {
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
		AppUtil.initAuthorityRes("BillOfRightAuditToolbar");
	});
	
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
	$("#BillOfRightAuditGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BillOfRightAuditToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="Audit_BillOfRight" iconcls="eicon-check"
								plain="true" onclick="auditBillOfRightDraftWindow();">审核</a>
							<a href="#" class="easyui-linkbutton"
								reskey="AllAudit_BillOfRight" iconcls="eicon-th-list"
								plain="true" onclick="auditBillOfRightAuditGridRecord();">批量审核</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BillOfRightAuditForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" id="implDepartIdAudit" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartBillOfRightAudit()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartnameAudit" /></td>
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
								onclick="AppUtil.gridDoSearch('BillOfRightAuditToolbar','BillOfRightAuditGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BillOfRightAuditForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BillOfRightAuditGrid" fitcolumns="true"
			toolbar="#BillOfRightAuditToolbar" method="post" idfield="RIGHT_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="billOfRightController.do?datagrid&Q_T.STATUS_IN=2,4,6">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RIGHT_ID',hidden:true">RIGHT_ID</th>
					<th data-options="field:'RIGHT_NAME',align:'left'" width="30%">项目名称</th>
					<th data-options="field:'SUBITEM_NAME',align:'left'" width="30%">子项名称</th>
					<th data-options="field:'RIGHT_TYPE',align:'left'" width="10%">权责类别</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'STATUS',align:'left'" width="8" formatter="formateStatus" >状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
