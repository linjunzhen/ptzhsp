
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" href="webpage/main/css/fonticon.css">
<script type="text/javascript">
	/**
	 * 删除流程映射类列表记录
	 */
	function deleteFlowMappedGridRecord() {
		AppUtil.deleteDataGridRecord("flowMappedController.do?multiDel",
				"FlowMappedGrid");
	};
	/**
	 * 编辑流程映射类列表记录
	 */
	function editFlowMappedGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("FlowMappedGrid");
		if (entityId) {
			showFlowMappedWindow(entityId);
		}
	}

	/**
	 * 显示流程映射类信息对话框
	 */
	function showFlowMappedWindow(entityId) {
		$.dialog.open("flowMappedController.do?info&defId=${defId}&version=${version}&entityId=" + entityId, {
			title : "流程映射类信息",
			width : "600px",
			height : "350px",
			lock : true,
			resize : false,
            close: function () {
            	$("#FlowMappedGrid").datagrid("reload");
            }
		}, false);
	};

	function formatType(val,row){
        if(val=="1"){
            return "<font color='green'><b>面向省网办</b></font>";
        }else{
            return "<font color='red'><b>面向网站前台</b></font>";
        }
    }
</script>
<body class="eui-diabody" style="margin:0px;">
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowMappedToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FlowMapped"
								iconcls="eicon-plus" plain="true"
								onclick="showFlowMappedWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_FlowMapped"
								iconcls="eicon-pencil" plain="true"
								onclick="editFlowMappedGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_FlowMapped"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteFlowMappedGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="FlowMappedForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">映射名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.YS_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">映射类型</td>
							<td><eve:eveselect clazz="eve-input validate[required]" dataParams="lcyspz"
                            dataInterface="dictionaryService.findDatasForSelect"  
                             defaultEmptyText="==选择映射类型==" name="Q_T.YS_TYPE_EQ"></eve:eveselect>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FlowMappedToolbar','FlowMappedGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('FlowMappedForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="FlowMappedGrid" fitcolumns="false" toolbar="#FlowMappedToolbar"
			method="post" idfield="YS_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="flowMappedController.do?datagrid&Q_T.DEF_ID_EQ=${defId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YS_ID',hidden:true" width="80">YS_ID</th>
					<th data-options="field:'YS_NAME',align:'left'" width="100">映射名称</th>
					<th data-options="field:'YS_CN',align:'left'" width="100">映射排序</th>
					<th data-options="field:'YS_TYPE',align:'left'" width="100" formatter="formatType">映射类型</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>