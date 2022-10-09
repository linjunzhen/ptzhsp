<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">function doSelectRows(){
    var rows = $("#SelectedDocumentGrid").datagrid("getRows");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var documentIds = "";
        var documentNames = "";
        for(var i = 0;i<rows.length;i++){
            if(i>0){
            	documentIds+=",";
            	documentNames+=",";
            }
            documentIds+=rows[i].DOCUMENT_ID;
            documentNames+=rows[i].DOCUMENT_NAME;
        }
        art.dialog.data("selectDocumentInfo", {
        	documentIds:documentIds,
        	documentNames:documentNames
        });
        
        AppUtil.closeLayer();
    }
    
}


$(function() {
	$("#DocumentGrid").datagrid({
	    onDblClickRow: function(index, row){
	        var rows = $("#SelectedDocumentGrid").datagrid("getRows");
	        var rowIndex = $("#SelectedDocumentGrid").datagrid("getRowIndex",row.DOCUMENT_ID);
	        if(rowIndex==-1){
	            $("#SelectedDocumentGrid").datagrid("appendRow",row);
	        }
	    }
	});
	
	$("#SelectedDocumentGrid").datagrid({
	    onDblClickRow: function(index, row){
	        $("#SelectedDocumentGrid").datagrid("deleteRow",index);
	    }
	});
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="DocumentToolbar">
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选公文模板列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="#" name="DocumentForm">
                    <table class="dddl-contentBorder dddl_table"
                        style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">票单模板名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.DOCUMENT_NAME_LIKE" /></td>
                            <td ><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('DocumentToolbar','DocumentGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('DocumentForm')" /></td>
                        </tr>
    
                    </table>
                </form>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                id="DocumentGrid" fitColumns="true" toolbar="#DocumentToolbar"
                method="post" idField="DOCUMENT_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="documentTemplateController.do?datagrid">
                <thead>
                    <tr>
                        <th data-options="field:'DOCUMENT_ID',hidden:true" width="80">DOCUMENT_ID</th>
                        <th data-options="field:'DOCUMENT_NAME',align:'left'" width="100">公文模板名称</th>
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
                                        style="position: relative; top:7px; float:left;" />&nbsp;已选公文模板列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" id="SelectedDocumentGrid" 
                fitColumns="true" toolbar="#SelectedDocumentToolbar" nowrap="false"
                method="post" idField="DOCUMENT_ID" checkOnSelect="false" url="documentTemplateController.do?selected&documentIds=${documentIds}"
                selectOnCheck="false" fit="true" border="false" >
                <thead>
                    <tr>
                        <th data-options="field:'DOCUMENT_ID',hidden:true" width="80">DOCUMENT_ID</th>
                        <th data-options="field:'DOCUMENT_NAME',align:'left'" width="100">公文模板名称</th>
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


