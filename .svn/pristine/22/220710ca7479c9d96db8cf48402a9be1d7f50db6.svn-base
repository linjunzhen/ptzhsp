<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	/**
	 * 删除IP过滤列表记录
	 */
	function deleteFilterConfigGridRecord() {
		AppUtil.deleteDataGridRecord("ipFilterConfigController.do?multiDel",
				"FilterConfigGrid");
	};

	/**
	 * 编辑IP过滤列表记录
	 */
	function editFilterConfigGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FilterConfigGrid");
		if (entityId) {
			showFilterConfigWindow(entityId);
		}
	}

	/**
	 * 显示IP过滤信息对话框
	 */
	function showFilterConfigWindow(entityId) {
		$.dialog.open("ipFilterConfigController.do?info&entityId=" + entityId, {
			title : "IP过滤信息",
			width : "500px",
			lock : true,
			resize : false,
			height : "300px",
		}, false);
	};
	
	function formatStatus(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>启用</b></font>";
		}else{
			return "<font color='#ff4b4b'><b>禁用</b></font>";
		}
	}
	
	function updateStatus(status){
    	AppUtil.multiOperateDataGridRecord("ipFilterConfigController.do?updateStatus&status="+status,
		"FilterConfigGrid");
    };
	

	$(document).ready(function() {
		AppUtil.initAuthorityRes("FilterConfigToolbar");
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FilterConfigToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FilterConfig"
								iconcls="eicon-plus" plain="true"
								onclick="showFilterConfigWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_FilterConfig"
								iconcls="eicon-pencil" plain="true"
								onclick="editFilterConfigGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_FilterConfig"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteFilterConfigGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" reskey="Enable_Schedule"
								iconcls="eicon-check" plain="true" onclick="updateStatus(1);">启用</a>
							<a href="#" class="easyui-linkbutton" reskey="Disable_Schedule"
								iconcls="eicon-ban" plain="true" onclick="updateStatus(-1);">禁用</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="fileConfigForm">
				<table class="dddl-contentBorder dddl_table"
					   style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
					<tr style="height:28px;">
						<td>
							<span>IP地址段</span>
							<input class="eve-input"
								   type="text" maxlength="20" style="width:128px;"
								   name="Q_T.IP_SEGMENT_LIKE" />
							<input type="button" value="查询"
								   class="eve-button"
								   onclick="AppUtil.gridDoSearch('FilterConfigToolbar','FilterConfigGrid')" />
							<input type="button" value="重置" class="eve-button"
								   onclick="AppUtil.gridSearchReset('fileConfigForm')" />
						</td>

					</tr>
					<tr>

					</tr>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FilterConfigGrid" fitcolumns="true" toolbar="#FilterConfigToolbar"
			method="post" idfield="CONFIG_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="ipFilterConfigController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONFIG_ID',hidden:true">CONFIG_ID</th>
					<th data-options="field:'IP_SEGMENT',align:'left'" width="20%">IP地址段</th>
					<th data-options="field:'IP_BIT_START',align:'left'" width="10%">起始</th>
					<th data-options="field:'IP_BIT_END',align:'left'" width="10%">结束</th>
					<th data-options="field:'STATUS',align:'left'" width="10%" formatter="formatStatus">状态</th>
					<th data-options="field:'IP_REMARK',align:'left'" width="50%">备注</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>