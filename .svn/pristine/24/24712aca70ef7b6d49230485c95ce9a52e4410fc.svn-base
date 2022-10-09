<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	/**
	 * 编辑对接配置列表记录
	 */
	function uploadMater() {
		var entityId = AppUtil.getEditDataGridRecord("UploadDataGrid");
		if (entityId) {
			showUploadDataGridRecord(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showUploadDataGridRecord(entityId) {
		$.dialog.open("bankDealController.do?uploadInfo&entityId=" + entityId, {
			title : "材料上传",
			width : "600px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};	
	
	function formatBank(val,row){
		if(val=="ccb"){
			return "建设银行";
		}else if(val=="pdb"){
			return "浦发银行";
		}else if(val=="rcb"){
			return "农商银行";
		}else if(val=="boc"){
			return "中国银行";
		}else if(val=="abc"){
			return "农业银行";
		}
	};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="UploadDataToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Vip"
								iconcls="eicon-upload" plain="true"
								onclick="uploadMater();">材料上传</a>
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="uploadDataForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.EXE_ID_=" /></td>
							<td style="width:68px;text-align:right;">公司名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.COMPANY_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('UploadDataToolbar','UploadDataGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('uploadDataForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>				
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="UploadDataGrid" fitcolumns="true" nowrap="false"
			toolbar="#UploadDataToolbar" method="post" idfield="EXE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="bankDealController.do?uploadGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'COMPANY_NAME',align:'left'" width="30%">公司名称</th>
					<th data-options="field:'BANK_TYPE',align:'left'" width="10%" formatter="formatBank">银行</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'STATUS',align:'left'" width="29%">上传状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
