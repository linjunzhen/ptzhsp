<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">function doSelectRows(){
    var rows = $("#SelectedFormGrid").datagrid("getRows");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var bindFormIds = "";
        var bindFormNames = "";
        for(var i = 0;i<rows.length;i++){
            if(i>0){
            	bindFormIds+=",";
            	bindFormNames+=",";
            }
            bindFormIds+=rows[i].DIC_ID;
            bindFormNames+=rows[i].DIC_NAME;
        }
        art.dialog.data("selectBindFormInfo", {
        	bindFormIds:bindFormIds,
        	bindFormNames:bindFormNames
        });
        
        AppUtil.closeLayer();
    }
    
}


$(function() {
	var allowCount = $("input[name='allowCount']").val();
	$("#dictionaryGrid").datagrid({
	    onDblClickRow: function(index, row){
	        var rows = $("#SelectedFormGrid").datagrid("getRows");
	        if((rows.length>=allowCount)&&allowCount!=0){
                alert("最多只能选择"+allowCount+"条记录!");
                return;
            }
	        var rowIndex = $("#SelectedFormGrid").datagrid("getRowIndex",row.DIC_ID);
	        if(rowIndex==-1){
	            $("#SelectedFormGrid").datagrid("appendRow",row);
	        }
	    }
	});
	
	$("#SelectedFormGrid").datagrid({
	    onDblClickRow: function(index, row){
	        $("#SelectedFormGrid").datagrid("deleteRow",index);
	    }
	});
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="dictionaryToolbar">
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选表单列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="#" name="DictionaryForm">
                    <table class="dddl-contentBorder dddl_table"
                        style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                        <tr>
                    <td style="width:68px;text-align:right;">表单名称</td>
                    <td style="width:135px;" ><input class="eve-input" type="text"
                         name="Q_D.DIC_NAME_LIKE" style="width:128px;"></td>
                    <td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('dictionaryToolbar','dictionaryGrid')">
                        <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('DictionaryForm');"></td>
                         </tr>
    
                    </table>
                </form>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                id="dictionaryGrid" fitColumns="true" toolbar="#dictionaryToolbar"
                method="post" idField="DIC_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="dictionaryController.do?datagrid&Q_T.TYPE_ID_EQ=4028b79e510f2b5301510f3f5bec0001">
                <thead>
                    <tr>
                    <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                    <th data-options="field:'DIC_CODE',align:'left'" width="100">字典编码</th>
                    <th data-options="field:'DIC_NAME',align:'left'" width="100">字典名称</th>
                    </tr>
                </thead>
            </table>
    
        </div>
        
        <div data-options="region:'east',split:false" style="width: 375px;">
            
            <div id="SelectedFormToolbar">
                <!--====================开始编写隐藏域============== -->
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;已选表单列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" id="SelectedFormGrid" 
                fitColumns="true" toolbar="#SelectedFormToolbar" nowrap="false"
                method="post" idField="DIC_ID" checkOnSelect="false" url="serviceItemFormController.do?selected&bindFormIds=${bindFormIds}"
                selectOnCheck="false" fit="true" border="false" >
                <thead>
                    <tr>
                    <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                    <th data-options="field:'DIC_CODE',align:'left'" width="100">字典编码</th>
                    <th data-options="field:'DIC_NAME',align:'left'" width="100">字典名称</th>
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


