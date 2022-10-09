<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteServiceItemGridRecord() {
		AppUtil.deleteDataGridRecord("serviceItemController.do?multiDel",
				"ServiceItemGrid");
	};
	/**
     * 申请审核服务事项列表记录
     */
    function applyServiceItemGridRecord() {
        var $dataGrid = $("#ServiceItemGrid");
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要提交审核的记录!",
                icon:"warning",
                ok: true
            });
        }else{
            art.dialog.confirm("您确定要提交审核选中的记录吗?", function() {
                var layload = layer.load('正在提交请求中…');
                var colName = $dataGrid.datagrid('options').idField;  
                var selectColNames = "";
                for ( var i = 0; i < rowsData.length; i++) {
                    if(i>0){
                        selectColNames+=",";
                    }
                    selectColNames+=eval('rowsData[i].'+colName);
                }
                $.post("serviceItemController.do?updateFwsxzt",{
                       selectColNames:selectColNames,
                       state:"2"
                    }, function(responseText, status, xhr) {
                        layer.close(layload);
                        var resultJson = $.parseJSON(responseText);
                        if(resultJson.success == true){
                            art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                                $dataGrid.datagrid('reload');
                                $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
                        }else{
                            art.dialog({
                                content: resultJson.msg,
                                icon:"error",
                                ok: true
                            });
                        }
                });
            });
        }
    };
	/**
	 * 编辑服务事项列表记录
	 */
	function editServiceItemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ServiceItemGrid");
		if (entityId) {
			showServiceItemWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showServiceItemWindow(entityId) {
		$.dialog.open("serviceItemController.do?info&entityId=" + entityId, {
			title : "服务事项信息",
			width: "100%",
		    height: "100%",
		    left: "0%",
		    top: "0%",
		    fixed: true,
			lock : true,
			resize : false,
			close: function () {
				AppUtil.gridDoSearch("ServiceItemToolbar","ServiceItemGrid");
			}
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("ServiceItemToolbar");
	});
	
	function formatFwdx(val,row){
		if(val=="1"){
			return "公众";
		}else if(val=="2"){
			return "企业";
		}else if(val=="3"){
			return "部门";
		}
	}
	
	function formatFwsxzt(val,row){
		if(val=="-1"){
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>发布</b></font>";
		}else if(val=="2"||val=="5"){
			return "<font color='green'><b>审核中</b></font>";
		}else if(val=="3"){
			return "<font color='black'><b>取消</b></font>";
		}else if(val=="4"){
            return "<font color='red'><b>退回</b></font>";
        }
	};
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ServiceItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_ServiceItem"
								iconcls="eicon-plus" plain="true"
								onclick="showServiceItemWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_ServiceItem"
								iconcls="eicon-pencil" plain="true"
								onclick="editServiceItemGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_ServiceItem"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteServiceItemGridRecord();">删除</a> <a href="#"
                                class="easyui-linkbutton" reskey="APPLY_ServiceItem"
                                iconcls="eicon-level-up" plain="true"
                                onclick="applyServiceItemGridRecord();">申请审核</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServiceItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<%-- 
							<td style="width:68px;text-align:right;">服务对象</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.FWDX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=HandleServerObject',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							
							<td style="width:68px;text-align:right;">事项状态</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.FWSXZT_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>--%>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('ServiceItemToolbar','ServiceItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ServiceItemForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ServiceItemGrid" fitcolumns="true" toolbar="#ServiceItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="serviceItemController.do?draftdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="13%">事项编码</th>
					<th data-options="field:'FWSXZT',align:'left'" width="8%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="26%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="8%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'AUDITING_NAMES',align:'left'" width="8%" >审核人</th>
					<!--  
					<th data-options="field:'FWDX',align:'left'" width="100" formatter="formatFwdx">服务对象</th>
					-->
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
