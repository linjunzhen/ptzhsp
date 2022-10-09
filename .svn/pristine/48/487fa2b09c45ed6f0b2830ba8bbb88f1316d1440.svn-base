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
    var rows = $("#WtitemsGrid").datagrid("getSelections");
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

/**
 * 删除数据字典列表记录
 */
function deleteDictionaryDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"dictionaryController.do?multiDel", "WtitemsGrid");
};

/**
 * 显示字典信息对话框
 */
function showDicWindow() {
	$.dialog.open("bdcApplyController.do?wtItemInfo", {
		title : "委托事项信息",
        width:"600px",
        lock: true,
        resize:false,
        height:"150px",
        close: function(){
			$("#WtitemsGrid").datagrid("reload");
		}
	}, false);
};

$(function() {
    //回填数据选中
	$("#WtitemsGrid").datagrid({
	    onLoadSuccess:function(){
	    	var  wtitemCodes = $("#wtitemCodes").val();
	    	var selectedCodes = wtitemCodes.split(",");
	    	var rows = $("#WtitemsGrid").datagrid('getRows');
	    	for(var i=0;i<rows.length;i++){
	    		var code = rows[i].DIC_CODE;
	    		for(var j=0;j<selectedCodes.length;j++){
	    			if(selectedCodes[j]==code){
	    				var rowIndex = $('#WtitemsGrid').datagrid('getRowIndex', rows[i]);
	    				$('#WtitemsGrid').datagrid('selectRow', rowIndex);
	    				continue ;
	    			}
	    		}
	    	}
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
                <input type="hidden" id="wtitemCodes" name="wtitemCodes" value="${wtitemCodes}"/>
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选事项列表
                                        <a href="#" class="easyui-linkbutton" 
											iconcls="icon-note-add" plain="true"
											onclick="showDicWindow();">新增</a> 
											<a href="#" class="easyui-linkbutton"
											 iconCls="icon-note-delete" plain="true"
											onclick="deleteDictionaryDataGridRecord();">删除</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- <form action="#" name="WtItemsForm">
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
                </form> -->
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="false"
                id="WtitemsGrid" fitColumns="true" toolbar="#WtItemsToolbar"
                method="post" idField="DIC_ID" checkOnSelect="true"
                selectOnCheck="true" fit="true" border="false" nowrap="false"
                url="bdcApplyController.do?wtItemsdatagrid">
                <thead>
                    <tr>
                    	<th field="ck" checkbox="true"></th>
                        <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_CODE',hidden:true" width="80">DIC_CODE</th>
                        <th data-options="field:'DIC_NAME',align:'left'" width="100">事项名称</th>
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


