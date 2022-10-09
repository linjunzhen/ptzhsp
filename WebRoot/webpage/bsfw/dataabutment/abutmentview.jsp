<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除对接配置列表记录
	 */
	function deleteDataAbutmentGridRecord() {
		AppUtil.deleteDataGridRecord("dataAbutmentController.do?multiDel",
				"DataAbutmentGrid");
	};
	/**
	 * 编辑对接配置列表记录
	 */
	function editDataAbutmentGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("DataAbutmentGrid");
		if (entityId) {
			showDataAbutmentWindow(entityId);
		}
	}

	/**
	 * 显示对接配置信息对话框
	 */
	function showDataAbutmentWindow(entityId) {
		$.dialog.open("dataAbutmentController.do?info&entityId=" + entityId, {
			title : "对接配置信息",
			width : "800px",
			height : "620px",
			lock : true,
			resize : false
		}, false);
	};
	
	function formatAbutType(val,row){
		if(val=="1"){
			return "数据库方式";
		}else if(val=="2"){
			return "WebService方式";
		}
	};
	
	function formatInvokeType(val,row){
		if(val=="1"){
			return "定时调用";
		}else if(val=="2"){
			return "触发调用";
		}
	}
	
	function formatNetworkType(val,row){
		if(val=="1"){
			return "政务内网";
		}else if(val=="2"){
			return "互联网";
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("DataAbutmentToolbar");

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DataAbutmentToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_DataAbutment"
								iconcls="eicon-plus" plain="true"
								onclick="showDataAbutmentWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_DataAbutment"
								iconcls="eicon-pencil" plain="true"
								onclick="editDataAbutmentGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_DataAbutment"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteDataAbutmentGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DataAbutmentForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">配置名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.AABUT_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">配置编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.AABUT_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">对接方式</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.AABUT_TYPE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=AABUTTYPE',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DataAbutmentToolbar','DataAbutmentGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('DataAbutmentForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DataAbutmentGrid" fitcolumns="true" nowrap="false"
			toolbar="#DataAbutmentToolbar" method="post" idfield="AABUT_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="dataAbutmentController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'AABUT_ID',hidden:true">AABUT_ID</th>
					<th data-options="field:'AABUT_CODE',align:'left'" width="8%">配置编码</th>
					<th data-options="field:'AABUT_NAME',align:'left'" width="27%">配置名称</th>
					<th data-options="field:'AABUT_TYPE',align:'left'" width="15%" formatter="formatAbutType">对接方式</th>
					<th data-options="field:'AABUT_INTERFACE',align:'left'" width="30%">对接实现接口</th>
					<th data-options="field:'INVOKE_TYPE',align:'left'" width="8%" formatter="formatInvokeType">调用方式</th>
					<th data-options="field:'NETWORK_TYPE',align:'left'" width="8%" formatter="formatNetworkType">网络环境</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
