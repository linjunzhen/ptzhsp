
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<script type="text/javascript">
function doSelectRows(){
    var rows = $("#FlowNodeGrid").datagrid("getChecked");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var nodeNames = "";
        for(var i = 0;i<rows.length;i++){
            if(i>0){
                nodeNames+=",";
            }
            nodeNames+=rows[i].NODENAME;
        }
        art.dialog.data("selectFlowNodesInfo", {
        	nodeNames:nodeNames
        });
        AppUtil.closeLayer();
    }
    
}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="FlowNodeToolbar">
			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="FlowNodeGrid" fitcolumns="false" toolbar="#FlowNodeToolbar"
			method="post" idfield="NODENAME" checkonselect="false" showFooter="false"
			selectoncheck="false" fit="true" border="false"
			url="flowMappedController.do?nodeDatagrid&defId=${defId}&version=${version}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'NODENAME',align:'left'" width="300">节点名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'south',split:true,border:false"  >
            <div class="eve_buttons">
                <input value="确定" type="button" onclick="doSelectRows();"
                    class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
                 <input
                    value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                    onclick="AppUtil.closeLayer();" />
            </div>
     </div>
</div>
