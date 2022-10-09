
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">


	/**
	* 查看服务事项详细信息
	*/
	function ckServiceItemXzywGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("BankServiceItemXzywGrid");
	 if (entityId) {
	     showServiceItemXzywWindow(entityId);
	 }
	}
	
	/**
	* 显示服务事项信息对话框
	*/
	function showServiceItemXzywWindow(entityId) {
	 $.dialog.open("serviceItemController.do?detailedInfo&entityId=" + entityId, {
	     title : "详细信息",
	     width: "100%",
	     height: "100%",
	     left: "0%",
	     top: "0%",
	     fixed: true,
	     lock : true,
	     resize : false,
	     close: function () {
	         //AppUtil.gridDoSearch("BankServiceItemXzywToolbar","BankServiceItemXzywGrid");
	     }
	 }, false);
	};
	
	function addNewBusiness() {
		var itemId = AppUtil.getEditDataGridRecord("BankServiceItemXzywGrid");
		if (itemId) {
			var rowData = $("#BankServiceItemXzywGrid").datagrid("getChecked")[0];
			var defKey = rowData.DEF_KEY;
			var itemCode = rowData.ITEM_CODE;
			toUrl("executionController.do?goStart&defKey="+defKey+"&itemCode="+itemCode);
		}
	}
	function toUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#BankServiceItemXzywToolbar").append(ssoForm);
		ssoForm.submit();		
	}
	$(document).ready(function() {
		$("#BankServiceItemXzywGrid").datagrid({
	        onDblClickRow: function(index, row){
	        	showServiceItemXzywWindow(row.ITEM_ID);
	        }
	    });
	});
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'center',split:false">
		<div id="BankServiceItemXzywToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							 <a href="#" class="easyui-linkbutton" resKey="ADD_YW" iconCls="eicon-plus"
                                plain="true" onclick="addNewBusiness();">新建业务</a>
                                <a href="#" class="easyui-linkbutton" resKey="CK_YW" iconCls="eicon-eye"
                                plain="true" onclick="ckServiceItemXzywGridRecord();">详细信息</a>
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
                            <td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_T.SXXZ_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                            <td colspan="2"></td>
                        </tr>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">办件类型</td>
                            <td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_T.SXLX_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                            <td colspan="4"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('BankServiceItemXzywToolbar','BankServiceItemXzywGrid')" />
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
            id="BankServiceItemXzywGrid" fitcolumns="true" toolbar="#BankServiceItemXzywToolbar"
            method="post" idfield="ITEM_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="bdcExecutionController.do?publishdatagrid&Q_T.FWSXMXLB_NEQ=2">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'DEF_KEY',hidden:true">DEF_KEY</th>
                    <th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
                    <th data-options="field:'ITEM_CODE',align:'left'" width="20%">事项编码</th>
                    <th data-options="field:'ITEM_NAME',align:'left'" width="35%">事项名称</th>
                    <th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
                    <th data-options="field:'SXLX',align:'left'" width="10%" >办件类型</th>
                    <th data-options="field:'DEPART_NAME',align:'left'" width="20%" >所属部门</th>
                </tr>
            </thead>
        </table>

	</div>
</div>



