<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除商户信息
	 */
	function deleteMerchantGridRecord() {
		AppUtil.deleteDataGridRecord("departServiceItemController.do?multiDelMerchant",
				"MerchantGrid");
	}
	/**
	 * 编辑商户信息
	 */
	function editMerchantGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("MerchantGrid");
		if (entityId) {
			showMerchantWindow(entityId);
		}
	}

	/**
	 * 显示商户信息
	 */
	function showMerchantWindow(entityId) {
		$.dialog.open("departServiceItemController.do?merchantInfo&entityId=" + entityId, {
			title : "商户信息",
			width : "600px",
			height : "400px",
			lock : true,
			resize : false,
			close : function() {
				$("#MerchantGrid").datagrid('reload');
			}
		}, false);
	}
	function formatUse(value,row,index){
		if(value=='1'){
			return "<font style='color:green;'>是</font>";
		}else{
			return "<font style='color:red;'>否</font>";
		}
	}
	
	$(document).ready(function() {
		AppUtil.initAuthorityRes("MerchantToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="MerchantToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								resKey="ADD_Merchant" iconCls="eicon-plus"
								plain="true" onclick="showMerchantWindow();">新建</a>
							<a href="#" class="easyui-linkbutton"
								resKey="EDIT_Merchant" iconCls="eicon-pencil"
								plain="true" onclick="editMerchantGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton"
								resKey="DEL_Merchant" iconCls="eicon-trash-o"
								plain="true" onclick="deleteMerchantGridRecord();">删除</a>
                                
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="MerchantForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">商户代码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.MERCHANT_CODE_=" /></td>
<!-- 							<td style="width:68px;text-align:right;">事项编码</td> -->
<!-- 							<td style="width:135px;"><input class="eve-input" -->
<!-- 								type="text" maxlength="20" style="width:128px;" -->
<!-- 								name="Q_t.BIND_ITEMCODES_LIKE" /></td> -->
<!-- 							<td style="width:68px;text-align:right;">事项名称</td> -->
<!-- 							<td style="width:135px;"><input class="eve-input" -->
<!-- 								type="text" maxlength="20" style="width:128px;" -->
<!-- 								name="Q_t.BIND_ITEMNAMES_LIKE" /></td> -->
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('MerchantToolbar','MerchantGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('MerchantForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="MerchantGrid" fitcolumns="true" toolbar="#MerchantToolbar"
			method="post" idfield="RECORD_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?merchantData">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'PAY_WAY_NAME',align:'left'" width="25%">支付渠道</th>
					<th data-options="field:'MERCHANT_NO',align:'left'" width="10%" >商户号</th>
					<th data-options="field:'MERCHANT_CODE',align:'left'" width="10%" >商户代码</th>			
					<th data-options="field:'IS_USE',align:'left'" width="51%" formatter="formatUse" >是否启用</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
