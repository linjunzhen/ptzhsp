<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		AppUtil.initAuthorityRes("receivingTimeItemToolbar");
	});
	
	
	/**
     * 编辑服务事项列表记录
     */
    function editReceivingTimeItemGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("receivingTimeItemGrid");
        if (entityId) {
	        $.dialog.open("departServiceItemController.do?receivingTimeInfo&entityId=" + entityId, {
	            title : "收件时限信息",
	            width: "150",
	            height: "100",
	            lock : true,
	            resize : false,
	            close: function () {
					$("#receivingTimeItemGrid").datagrid('reload');
	            }
	        }, false);
        }
    }
    
    function showPSelectDeparts(){
        var departId = $("input[id='PdepartId']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[id='PdepartId']").val(selectDepInfo.departIds);
                    $("input[id='PdepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function resetPublishForm(){
    	AppUtil.gridSearchReset('ServicePublishItemForm');
    	$("input[name='Q_D.DEPART_ID_EQ']").val('');
    	$("input[name='Q_DC.DEPART_ID_EQ']").val('');
    	
    }
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="receivingTimeItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#"
                                class="easyui-linkbutton" reskey="EDIT_DepartServiceItemPub"
                                iconcls="icon-note-edit" plain="true"
                                onclick="editReceivingTimeItemGridRecord();">修改时限</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServicePublishItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showPSelectDeparts();"
                                style="width:128px;" name="DEPART_NAME" id="PdepartName" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" id="PdepartId" value=""/>
                            </td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('receivingTimeItemToolbar','receivingTimeItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetPublishForm();" /></td>
								
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="receivingTimeItemGrid" fitcolumns="false" toolbar="#receivingTimeItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?publishdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="180">唯一码（事项编码）</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="400">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="100" >事项性质</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="180" >所属部门</th>
					<th data-options="field:'RECEIVEDAY',align:'left'" width="100" >收件时限</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
