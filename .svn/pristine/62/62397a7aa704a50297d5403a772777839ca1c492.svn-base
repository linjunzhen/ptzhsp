<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style><head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

function formatIsPass(val,row){
	  if(val=="-1"){
		  return "<font color='red'><b>不通过</b></font>";
	  }else{
		  return "<font color='green'><b>通过</b></font>";
	  }
}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="FlowTaskGrid" fitcolumns="true" nowrap="false" 
			method="post" idfield="TASK_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="executionController.do?opinionDatagrid&Q_T.EXE_ID_EQ=${exeId}&currentTaskId=${currentTaskId}&Q_T.STEP_SEQ_NEQ=0&start=0&limit=100">
			<thead>
				<tr>
					<th data-options="field:'TEAM_NAMES',align:'left'"  width="150">审核人</th>
					<th data-options="field:'ASSIGNER_DEPNAMES',align:'left'"  width="150">审核单位</th>
					<th data-options="field:'IS_PASS',align:'left'" formatter="formatIsPass" width="200">审核结果</th>
					<th data-options="field:'HANDLE_OPINION',align:'left'" width="200">审核意见</th>
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
