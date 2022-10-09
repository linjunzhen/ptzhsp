<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">
/**
 * 删除流程定义列表记录
 */
function deleteFlowDefVersionRecord() {
	var defId = $("input[name='defId']").val();
	AppUtil.deleteDataGridRecord("histDeployController.do?multiDel&defId="+defId,
			"FlowVersionGrid");
};

/**
 * 配置流程定义列表记录
 */
function configFlowDefGridRecord() {
	var flowVersion = AppUtil.getEditDataGridRecord("FlowVersionGrid");
	if (flowVersion) {
		var DEF_ID = $("input[name='defId']").val();
		window.open("flowDefController.do?flowDefConfig&defId="+DEF_ID+"&flowVersion="+flowVersion, "_blank");
	}
}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
<div class="easyui-layout" fit="true">
    <input type="hidden" name="defId" value="${defId}">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowVersionToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" 
								iconCls="eicon-pencil" plain="true"
								onclick="configFlowDefGridRecord();">编辑历史版本</a> 
							<a href="#"
								class="easyui-linkbutton" 
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteFlowDefVersionRecord();">删除历史版本</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FlowVersionGrid" fitcolumns="true" toolbar="#FlowVersionToolbar"
			method="post" idfield="DEF_VERSION" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="histDeployController.do?datagrid&defId=${defId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'DEF_ID',hidden:true">DEF_ID</th>
					<th data-options="field:'DEF_VERSION',align:'left'" width="15%">版本号</th>
					<th data-options="field:'DEF_KEY',align:'left'" width="75%">流程定义KEY</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>
