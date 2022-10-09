<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">function doSelectRows(){
    var rows = $("#SelectedTicketsGrid").datagrid("getRows");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var ticketsIds = "";
        var ticketsNames = "";
        for(var i = 0;i<rows.length;i++){
            if(i>0){
            	ticketsIds+=",";
            	ticketsNames+=",";
            }
            ticketsIds+=rows[i].TICKETS_ID;
            ticketsNames+=rows[i].TICKETS_NAME;
        }
        art.dialog.data("selectTicketsInfo", {
        	ticketsIds:ticketsIds,
        	ticketsNames:ticketsNames
        });
        
        AppUtil.closeLayer();
    }
    
}


$(function() {
	$("#TicketsGrid").datagrid({
	    onDblClickRow: function(index, row){
	        var rows = $("#SelectedTicketsGrid").datagrid("getRows");
	        var rowIndex = $("#SelectedTicketsGrid").datagrid("getRowIndex",row.TICKETS_ID);
	        if(rowIndex==-1){
	            $("#SelectedTicketsGrid").datagrid("appendRow",row);
	        }
	    }
	});
	
	$("#SelectedTicketsGrid").datagrid({
	    onDblClickRow: function(index, row){
	        $("#SelectedTicketsGrid").datagrid("deleteRow",index);
	    }
	});
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="TicketsToolbar">
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选票单列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="#" name="TicketsForm">
                    <table class="dddl-contentBorder dddl_table"
                        style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">票单模板名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.TICKETS_NAME_LIKE" /></td>
                            <td ><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('TicketsToolbar','TicketsGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('TicketsForm')" /></td>
                        </tr>
    
                    </table>
                </form>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                id="TicketsGrid" fitColumns="true" toolbar="#TicketsToolbar"
                method="post" idField="TICKETS_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="ticketsController.do?datagrid">
                <thead>
                    <tr>
                        <th data-options="field:'TICKETS_ID',hidden:true" width="80">TICKETS_ID</th>
                        <th data-options="field:'TICKETS_NAME',align:'left'" width="100">票单名称</th>
                    </tr>
                </thead>
            </table>
    
        </div>
        
        <div data-options="region:'east',split:false" style="width: 375px;">
            
            <div id="SelectedSysUserToolbar">
                <!--====================开始编写隐藏域============== -->
                <input type="hidden" name="TYPE_ID">
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;已选票单模板列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" id="SelectedTicketsGrid" 
                fitColumns="true" toolbar="#SelectedTicketsToolbar" nowrap="false"
                method="post" idField="TICKETS_ID" checkOnSelect="false" url="ticketsController.do?selected&ticketsIds=${ticketsIds}"
                selectOnCheck="false" fit="true" border="false" >
                <thead>
                    <tr>
                        <th data-options="field:'TICKETS_ID',hidden:true" width="80">TICKETS_ID</th>
                        <th data-options="field:'TICKETS_NAME',align:'left'" width="100">票单模板名称</th>
                    </tr>
                </thead>
            </table>
    
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

    
</body>


