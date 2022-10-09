<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	/**
	 * 删除全局URL列表记录
	 */
	function deleteGlobalUrlGridRecord() {
		AppUtil.deleteDataGridRecord("globalUrlController.do?multiDel",
				"GlobalUrlGrid");
	};

	/**
	 * 编辑全局URL列表记录
	 */
	function editGlobalUrlGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("GlobalUrlGrid");
		if (entityId) {
			showGrobalUrlWindow(entityId);
		}
	}

	/**
	 * 显示全局URL信息对话框
	 */
	function showGrobalUrlWindow(entityId) {
		$.dialog.open("globalUrlController.do?info&entityId=" + entityId, {
			title : "全局URL信息",
			width : "600px",
			lock : true,
			resize : false,
			height : "200px",
		}, false);
	};
	
	function formatType(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>匿名类型</b></font>";
		}else if(val=="2"){
			return "<font color='#ff4b4b'><b>会话类型</b></font>";
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("GlobalUrlToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="GlobalUrlToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_GrobalUrl"
								iconcls="eicon-plus" plain="true"
								onclick="showGrobalUrlWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_GrobalUrl"
								iconcls="eicon-pencil" plain="true"
								onclick="editGlobalUrlGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_GrobalUrl"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteGlobalUrlGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="GlobalUrlGrid" fitcolumns="true" toolbar="#GlobalUrlToolbar"
			method="post" idfield="URL_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="globalUrlController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'URL_ID',hidden:true">URL_ID</th>
					<th data-options="field:'URL_ADDRESS',align:'left'" width="80%">全局URL</th>
					<th data-options="field:'URL_FILTERTYPE',align:'left'" formatter="formatType" width="15%">会话类型</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>