<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

    function signRightFlag(flagValue){
    	AppUtil.multiOperateDataGridRecord("fieldRightController.do?updateRightFlag&rightFlag="+flagValue,
		"fieldRightConfGrid");
    }
    
	function formatRightFlag(val,row){
		if(val=="1"){
			return "<font color='green'><b>可写</b></font>";
		}else if(val=="2"){
			return "<font color='blue'><b>只读</b></font>";
		}else if(val=="3"){
			return "<font color='red'><b>不可见</b></font>";
		}
	};
	
	$(function(){
		var nodeName = $("input[name='NODE_NAME']").val();
		$("#fieldRightConfGrid").datagrid({
			url:"fieldRightController.do?datagrid&start=0&limit=1000",
			queryParams: {
				"Q_T.NODE_NAME_EQ": nodeName,
				"Q_T.DEF_ID_EQ": '${nodeData.defId}',
				"Q_T.DEF_VERSION_EQ": '${nodeData.flowVersion}'
			}
		});
	});
</script>

<!-- =========================查询面板开始=========================-->
<div id="fieldRightConfToolBar">
	<div class="right-con">
		<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
			<div class="e-toolbar-ct">
				<div class="e-toolbar-overflow">
					<a href="#" class="easyui-linkbutton" resKey="ADD_FlowDef"
						iconCls="icon-note-edit" plain="true"
						onclick="signRightFlag('1');">标记为可写</a> 
					<a href="#"
						class="easyui-linkbutton" resKey="EDIT_FlowDef"
						iconCls="icon-eye" plain="true"
						onclick="signRightFlag('2');">标记为只读</a> 
					<a href="#"
						class="easyui-linkbutton" resKey="DEL_FlowDef"
						iconCls="icon-disabled" plain="true"
						onclick="signRightFlag('3');">标记为隐藏</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- =========================查询面板结束========================= -->
<!-- =========================表格开始==========================-->
<table class="easyui-datagrid" rownumbers="true" pagination="false"
	id="fieldRightConfGrid" fitcolumns="true" toolbar="#fieldRightConfToolBar"
	method="post" idfield="RIGHT_ID" checkonselect="false"
	selectoncheck="false" fit="true" border="false"    
	>
	<thead>
		<tr>
			<th field="ck" checkbox="true"></th>
			<th data-options="field:'RIGHT_ID',hidden:true" width="80">RIGHT_ID</th>
			<th data-options="field:'FIELD_NAME',align:'left'" width="100">字段名称</th>
			<th data-options="field:'RIGHTFLAG',align:'left'" width="100" formatter="formatRightFlag">权限值</th>
		</tr>
	</thead>
</table>