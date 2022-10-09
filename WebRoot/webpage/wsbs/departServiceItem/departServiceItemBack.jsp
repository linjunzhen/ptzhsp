<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
     * 申请审核服务事项列表记录
     */
    function applyDepartServiceBackItemGridRecord() {
        var $dataGrid = $("#DepartServiceBackItemGrid");
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要提交审核的记录!",
                icon:"warning",
                ok: true
            });
        }else{
        	var colName = $dataGrid.datagrid('options').idField;  
			var selectColNames = "";
			for ( var i = 0; i < rowsData.length; i++) {
                if(i>0){
                    selectColNames+=",";
                }
                selectColNames+=eval('rowsData[i].'+colName);
            }
			 $.dialog.open("serviceItemController.do?applyInfo&itemIds=" + selectColNames+"&fileFlag=-1", {
                    title : "审核意见",
                    width: "600px",
                    height: "400px",
                    fixed: true,
                    lock : true,
                    resize : false,
                    close: function () {
                        var backinfo = art.dialog.data("backinfo");
                        if(backinfo&&backinfo.back){
                            art.dialog({
                                content: "提交成功",
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                            art.dialog.removeData("backinfo");
                            $dataGrid.datagrid('reload');
                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
                        }
                    }
                }, false);
        }
    };
	/**
	 * 编辑服务事项列表记录
	 */
	function editDepartServiceBackItemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("DepartServiceBackItemGrid");
		if (entityId) {
			showDepartServiceBackItemWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showDepartServiceBackItemWindow(entityId) {
		$.dialog.open("departServiceItemController.do?info&entityId=" + entityId, {
			title : "服务事项信息",
			width: "100%",
		    height: "100%",
		    left: "0%",
		    top: "0%",
		    fixed: true,
			lock : true,
			resize : false,
			close: function () {
				$("#DepartServiceBackItemGrid").datagrid('reload');
				//AppUtil.gridDoSearch("DepartServiceBackItemToolbar","DepartServiceBackItemGrid");
			}
		}, false);
	};
	
	//附件格式
    function  formatAtach(val,row){
       var fileids=val;
       var newhtml="";
       var fileName=row.FILE_NAME;
       if(fileName!=null){
			newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
			newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
			newhtml+=fileName+'</a>';
			newhtml+='</p>';
		    //$("#fileListDiv").html(newhtml);
		   	return  newhtml;
	   }
    } 

	$(document).ready(function() {
		AppUtil.initAuthorityRes("DepartServiceBackItemToolbar");
	});
		
	//空数据，横向滚动
	$('#DepartServiceBackItemGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DepartServiceBackItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							 <a href="#"
								class="easyui-linkbutton" reskey="EDIT_DepartServiceItemBack"
								iconcls="eicon-edit" plain="true"
								onclick="editDepartServiceBackItemGridRecord();">编辑</a> <a href="#"
                                class="easyui-linkbutton" reskey="APPLY_DepartServiceItemBack"
                                iconcls="eicon-level-up" plain="true"
                                onclick="applyDepartServiceBackItemGridRecord();">申请审核</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DepartServiceBackItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="36" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServiceBackItemToolbar','DepartServiceBackItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('DepartServiceBackItemForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartServiceBackItemGrid" fitcolumns="true" toolbar="#DepartServiceBackItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?datagrid&Q_T.FWSXZT_EQ=4">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="15%">唯一码（事项编码）</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="8%" >事项性质</th>		
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>			
					<th data-options="field:'OPERATE_TIME',align:'left'" width="13%" >退回时间</th>
					<th data-options="field:'BACKOR_NAME',align:'left'" width="8%" >审核人</th>
					<th data-options="field:'PTHYJ',align:'left'" width="15%" nowrap="false">退回意见</th>
					<th data-options="field:'FILEATTACH_PATH',align:'left'" width="15%" formatter="formatAtach">审核附件</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
