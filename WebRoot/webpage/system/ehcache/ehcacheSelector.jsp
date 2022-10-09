<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south {
	border-top-width: 0px !important;
}

.eve_buttons {
	position: relative !important;
}
</style>
<script type="text/javascript">
	function doSelectRows() {
		var rows = $("#SelectedSysEhcacheGrid").datagrid("getRows");
		if (rows.length == 0) {
			alert("请至少选择一条记录!");
		} else {
			var delEhcacheKeys = "";
			var delEhcacheNames = "";
			for (var i = 0; i < rows.length; i++) {
				if (i > 0) {
					delEhcacheKeys += ",";
					delEhcacheNames += ",";
				}
				delEhcacheKeys += rows[i].EHCACHE_KEY;
				delEhcacheNames += rows[i].EHCACHE_NAME;
			}
			art.dialog.data("delEhcacheKeysInfo", {
				delEhcacheKeys : delEhcacheKeys,
				delEhcacheNames : delEhcacheNames
			});

			AppUtil.closeLayer();
		}

	}

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#SysEhcacheGrid").datagrid(
				{
					onDblClickRow : function(index, row) {
						var rows = $("#SelectedSysEhcacheGrid").datagrid("getRows");
						if ((rows.length >= allowCount) && allowCount != 0) {
							alert("最多只能选择" + allowCount + "条记录!");
							return;
						}
						var rowIndex = $("#SelectedSysEhcacheGrid").datagrid(
								"getRowIndex", row.EHCACHE_ID);
						if (rowIndex == -1) {
							$("#SelectedSysEhcacheGrid").datagrid("appendRow", row);
						}
					}
				});

		$("#SelectedSysEhcacheGrid").datagrid({
			onDblClickRow : function(index, row) {
				$("#SelectedSysEhcacheGrid").datagrid("deleteRow", index);
			}
		});
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true">
		<div data-options="region:'center',split:false" style="width: 425px;">
			<div id="SysEhcacheToolbar">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" />&nbsp;可选缓存列表
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="SysEhcacheForm">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">缓存名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.EHCACHE_NAME_LIKE" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('SysEhcacheToolbar','SysEhcacheGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('SysEhcacheForm')" /></td>
					</tr>

					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->

			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="SysEhcacheGrid" fitColumns="true" toolbar="#SysEhcacheToolbar"
				method="post" idField="EHCACHE_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="sysEhcacheController.do?datagrid&Q_T.EHCACHE_STATUE_EQ=1">
				<thead>
					<tr>
						<th data-options="field:'EHCACHE_ID',hidden:true" width="100">EHCACHE_ID</th> 
                        <th data-options="field:'EHCACHE_NAME',align:'left'" width="300" >缓存名称</th> 
                        <th data-options="field:'EHCACHE_CLASS_NAME',align:'left'" width="400">缓存类名</th> 
					</tr>
				</thead>
			</table>

		</div>

		<div data-options="region:'east',split:false" style="width: 375px;">

			<div id="SelectedSysEhcacheToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选缓存列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true"
				id="SelectedSysEhcacheGrid" fitColumns="true"
				toolbar="#SelectedSysEhcacheToolbar" nowrap="false" method="post"
				idField="EHCACHE_ID" checkOnSelect="false"
				url="sysEhcacheController.do?selected&delEhcacheKeys=${delEhcacheKeys}"
				selectOnCheck="false" fit="true" border="false">
				<thead>
					<tr>
						<th data-options="field:'EHCACHE_ID',hidden:true" width="100">EHCACHE_ID</th> 
                        <th data-options="field:'EHCACHE_NAME',align:'left'" width="300" >缓存名称</th> 
                        <th data-options="field:'EHCACHE_CLASS_NAME',align:'left'" width="400">缓存类名</th> 
					</tr>
				</thead>
			</table>

		</div>

		<div data-options="region:'south',split:true,border:false">
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>


</body>


