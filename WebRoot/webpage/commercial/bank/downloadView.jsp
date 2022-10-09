<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function downLoadMater(){
		var entityId = AppUtil.getEditDataGridRecord("DownloadDataGrid");
		if (entityId) {			
			$.dialog.open("bankDealController.do?downloadInfo&entityId=" + entityId, {
				title : "材料明细",
				width : "600px",
				height : "350px",
				lock : true,
				resize : false
			}, false);
		}
	}
	
	function downLoadMaterAll(){
		var entityId = AppUtil.getEditDataGridRecord("DownloadDataGrid");
		
		window.location.href=__ctxPath+"/bankDealController.do?downloadApplyRar&entityId="+entityId;
	}
	
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
	
	function writeAcc(){
		var entityId = AppUtil.getEditDataGridRecord("DownloadDataGrid");
		if (entityId) {			
			$.dialog.open("bankDealController.do?writeInfo&entityId=" + entityId, {
				title : "开户许可证号",
				width : "500px",
				height : "150px",
				lock : true,
				resize : false
			}, false);
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DownloadDataToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey=""
								iconcls="eicon-download" plain="true"
								onclick="downLoadMaterAll();">材料下载</a>
							<a href="#" class="easyui-linkbutton" reskey=""
								iconcls="eicon-file-text-o" plain="true"
								onclick="downLoadMater();">材料明细</a>
							<a href="#" class="easyui-linkbutton" reskey=""
								iconcls="eicon-ticket" plain="true"
								onclick="writeAcc();">开户许可证号</a>
						</div>
					</div>
				</div>
			</div>				
			<form action="#" name="downloadDataForm">
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
								onclick="AppUtil.gridDoSearch('DownloadDataToolBar','DownloadDataGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('downloadDataForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DownloadDataGrid" fitcolumns="true" nowrap="false"
			toolbar="#DownloadDataToolBar" method="post" idfield="EXE_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="bankDealController.do?downloadGrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
					<th data-options="field:'COMPANY_NAME',align:'left'" width="30%">公司名称</th>
					<th data-options="field:'BANK_TYPE',align:'left'" width="10%" formatter="formatBank">银行</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'BANK_LICENSE',align:'left'" width="29%">开户许可证号</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
