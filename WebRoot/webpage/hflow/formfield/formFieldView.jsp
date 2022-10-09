<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	
</script>
</head>

<body style="margin:0px;">
<div class="easyui-layout eui-dialog" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= 
		<div id="FormFieldToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_FormField"
								iconcls="icon-reload" plain="true"
								onclick="refreshFormField();">刷新表单字段</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		-->
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="FormFieldGrid" fitcolumns="true" toolbar="#FormFieldToolbar"
			method="post" idfield="FIELD_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="formFieldController.do?datagrid&Q_T.FORM_ID_EQ=${formId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'FIELD_ID',hidden:true" width="80">FIELD_ID</th>
					<th data-options="field:'FORM_ID',align:'left',hidden:true" width="100">表单ID</th>
					<th data-options="field:'FIELD_NAME',align:'left'" width="100">字段名称</th>
					<th data-options="field:'FIELD_CONTROLTYPE',align:'left'" width="100">字段控件类型</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>
