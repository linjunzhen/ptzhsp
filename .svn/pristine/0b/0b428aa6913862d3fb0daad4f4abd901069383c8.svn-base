<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function publishBillOfRightDraftGridRecord(){
		var $dataGrid = $("#BillOfRightDraftGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要直接发布的记录!",
				icon : "warning",
				ok : true
			});
		} else {
			var rightIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					rightIds+=",";
				}
				if(rowsData[i].STATUS!='-1'){
					art.dialog({
						content : "仅草稿状态的记录可直接发布",
						icon : "error",
						ok : true
					});
					return;
				}
				rightIds+=rowsData[i].RIGHT_ID;
			}			
			art.dialog.confirm("您确定要将所选择的记录直接发布吗?", function() {
				$.post("billOfRightController.do?updateStatus", {
					rightIds:rightIds,
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
	function applyBillOfRightDraftGridRecord() {
		var $dataGrid = $("#BillOfRightDraftGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		var isSourceNotEmpty = true;
		if (rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0) {
			for ( var i = 0; i < rowsData.length; i++) {
				if(rowsData[i].RIGHT_SOURCE == null ||rowsData[i].RIGHT_SOURCE == 'undefined' ||rowsData[i].RIGHT_SOURCE == ''){
					isSourceNotEmpty = false;
				}
			}
		}
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要提交审核的记录!",
				icon : "warning",
				ok : true
			});
		}else if (!isSourceNotEmpty) {
			art.dialog({
				content : "请补充需要提交审核记录的权利来源信息!",
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
			$.post("billOfRightController.do?updateStatus", {
				rightIds:rightIds,
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
	function editBillOfRightDraftGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BillOfRightDraftGrid");
		if (entityId) {
			showBillOfRightDraftWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showBillOfRightDraftWindow(entityId) {
		$.dialog.open("billOfRightController.do?draftInfo&entityId="+ entityId, {
			title : "权责清单信息",
			width : "100%",
			height : "100%",
			left : "0%",
			top : "0%",
			fixed : true,
			lock : true,
			resize : false,
			close : function() {
				$("#BillOfRightDraftGrid").datagrid('reload');
			}
		}, false);
	}
	
	function delBillOfRightDraftGridRecord(){
		AppUtil.deleteDataGridRecord("billOfRightController.do?multiDel",
	            "BillOfRightDraftGrid");
	}
	
	function formateRightSource(val, row) {
		if (val == "01") {
			return "<font color='grey'><b>法定本级行使</b></font>";
		}  else if (val == "02") {
			return "<font color='grey'><b>上级授权</b></font>";
		} else if (val == "03") {
			return "<font color='grey'><b>同级授权</b></font>";
		} else if (val == "04") {
			return "<font color='grey'><b>上级委托</b></font>";
		} else if (val == "05") {
			return "<font color='grey'><b>同级委托</b></font>";
		} else if (val == "06") {
			return "<font color='grey'><b>上级下放</b></font>";
		} else if (val == "qt") {
			return "<font color='grey'><b>其他</b></font>";
		}
	};
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
		AppUtil.initAuthorityRes("BillOfRightDraftToolbar");
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
	$("#BillOfRightDraftGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BillOfRightDraftToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="ADD_BillOfRight" iconcls="eicon-plus"
								plain="true" onclick="showBillOfRightDraftWindow('');">新建</a>
							<a href="#" class="easyui-linkbutton"
								reskey="EDIT_BillOfRight" iconcls="eicon-pencil"
								plain="true" onclick="editBillOfRightDraftGridRecord();">编辑</a>
							<!-- <a href="#" class="easyui-linkbutton"
								reskey="DEL_BillOfRight" iconcls="icon-note-delete"
								plain="true" onclick="delBillOfRightDraftGridRecord();">删除</a> -->
							<a href="#" class="easyui-linkbutton"
								reskey="APPLY_BillOfRight" iconcls="eicon-level-up"
								plain="true" onclick="applyBillOfRightDraftGridRecord();">提交审核</a>
							<a href="#" class="easyui-linkbutton"
								reskey="ONLYPUBLISH_BillOfRight" iconcls="eicon-eject"
								plain="true" onclick="publishBillOfRightDraftGridRecord();">直接发布</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BillOfRightDraftForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
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
								onclick="AppUtil.gridDoSearch('BillOfRightDraftToolbar','BillOfRightDraftGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BillOfRightDraftForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BillOfRightDraftGrid" fitcolumns="true"
			toolbar="#BillOfRightDraftToolbar" method="post" idfield="RIGHT_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="billOfRightController.do?datagrid&Q_T.STATUS_IN=-1,-2,3,7">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RIGHT_ID',hidden:true">RIGHT_ID</th>
					<th data-options="field:'RIGHT_NAME',align:'left'" width="25%">项目名称</th>
					<th data-options="field:'SUBITEM_NAME',align:'left'" width="25%">子项名称</th>
					<th data-options="field:'RIGHT_TYPE',align:'left'" width="10%">权责类别</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'RIGHT_SOURCE',align:'left'" width="12%" formatter="formateRightSource"  >权利来源</th>
					<th data-options="field:'STATUS',align:'left'" width="8%" formatter="formateStatus" >状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
