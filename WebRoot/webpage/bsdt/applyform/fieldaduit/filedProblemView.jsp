<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style><head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="FieldOpinionGrid" fitcolumns="true" nowrap="false" 
			method="post" idfield="RECORD_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="commercialController.do?opinionDatagrid&Q_t.EXE_ID_EQ=${exeId}">
			<thead>
				<tr>
					<th data-options="field:'FIELD_TEXT',align:'left'"  width="150">字段名称</th>
					<th data-options="field:'AUDIT_OPINION',align:'left'" width="200">审核意见</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
	<div class="eve_buttons">
		<input
			value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	</div>
</div>
