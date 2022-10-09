<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

function doSelectRows(){
    var rows = $("#SelectedWtItemsGrid").datagrid("getRows");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var wtitemcodes = "";
        var wtitemNames = "";
        for(var i = 0;i<rows.length;i++){
        	if(i==0){
        		wtitemNames=(i+1)+"、"
        	}
            if(i>0){
            	wtitemcodes+=",";
            	wtitemNames+="\r\n"+(i+1)+"、";
            }
            wtitemcodes+=rows[i].DIC_CODE;
            wtitemNames+=rows[i].DIC_NAME;
        }
        art.dialog.data("selectWtItemsInfo", {
        	wtitemcodes:wtitemcodes,
        	wtitemNames:wtitemNames
        });
        AppUtil.closeLayer();
    }
    
}


$(function() {
	var count = 1;
	$("#WtitemsGrid").datagrid({
	    onDblClickRow: function(index, row){
	        var rows = $("#SelectedWtItemsGrid").datagrid("getRows");
	        var rowIndex = $("#SelectedWtItemsGrid").datagrid("getRowIndex",row.DIC_ID);
	        if(rowIndex==-1){
	            $("#SelectedWtItemsGrid").datagrid("appendRow",row);
	        }
	    },
	    onLoadSuccess:function(){
	    	//确保初始化后只执行一次初始化查询查询
	    	if(count == "1"){
	    		setTimeout(function(){
	    			AppUtil.gridDoSearch('WtItemsToolbar','WtitemsGrid');
	    			count = count + 1;
	    		}, 500);
	    	}
	    }
	});
	
	$("#SelectedWtItemsGrid").datagrid({
	    onDblClickRow: function(index, row){
	        $("#SelectedWtItemsGrid").datagrid("deleteRow",index);
	    }
	});
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="WtItemsToolbar">
                <!--====================开始编写隐藏域============== -->
                <input type="hidden" name="Q_T.TYPE_CODE_=" value="wtitems"/>
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选事项列表<font color="red">(双击选择)</font>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="#" name="WtItemsForm">
                    <table class="dddl-contentBorder dddl_table"
                        style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                        <tr style="height:28px;">
                            <td style="width:78px;text-align:right;">委托事项名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_D.DIC_NAME_LIKE" /></td>
                            <td ><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('WtItemsToolbar','WtitemsGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('WtItemsForm')" /></td>
                        </tr>
    
                    </table>
                </form>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                id="WtitemsGrid" fitColumns="true" toolbar="#WtItemsToolbar"
                method="post" idField="DIC_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="dictionaryController.do?datagrid">
                <thead>
                    <tr>
                        <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_CODE',hidden:true" width="80">DIC_CODE</th>
                        <th data-options="field:'DIC_NAME',align:'left'" width="100">事项名称</th>
                    </tr>
                </thead>
            </table>
    
        </div>
        
        <div data-options="region:'east',split:false" style="width: 375px;">
            
            <div id="SelectedWtItemsToolbar">
                <!--====================开始编写隐藏域============== -->
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;已选事项列表<font color="red">(双击取消)</font>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" id="SelectedWtItemsGrid" 
                fitColumns="true" toolbar="#SelectedWtItemsToolbar" nowrap="false"
                method="post" idField="DIC_ID" checkOnSelect="false" url="bdcApplyController.do?selected&wtitemCodes=${wtitemCodes}"
                selectOnCheck="false" fit="true" border="false" >
                <thead>
                    <tr>
                        <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_CODE',hidden:true" width="80">DIC_CODE</th>
                        <th data-options="field:'DIC_NAME',align:'left'" width="100">委托事项名称</th>
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


