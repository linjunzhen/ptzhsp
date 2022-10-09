<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="<%=basePath%>">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources
		loadres="jquery,easyui,apputil,artdialog,json2,layer,ztree"></eve:resources>
	<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
<script type="text/javascript">function doSelectRows(){
    var rows = $("#SelectedReadGrid").datagrid("getRows");
    if(rows.length==0){
        //alert("请至少选择一条记录!");
        var readIds = "";
        var readNames = "";
        var readCodes = "";
        art.dialog.data("selectLimitStatusInfo", {
        	readIds : readIds,
        	readNames : readNames,
        	readCodes : readCodes
        });
        
        AppUtil.closeLayer();
    }else{
        var readIds = "";
        var readNames = "";
        var readCodes = "";
        for(var i = 0;i<rows.length;i++){
            if(i>0){
            	readIds+=",";
            	readNames+=",";
            	readCodes+=",";
            }
            readIds+=rows[i].DIC_ID;
            readNames+=rows[i].DIC_NAME;
            readCodes+=rows[i].DIC_CODE;
        }
        art.dialog.data("selectLimitStatusInfo", {
        	readIds : readIds,
        	readNames : readNames,
        	readCodes : readCodes
        });
        
        AppUtil.closeLayer();
    }
    
}


$(function() {
	$("#ReadGrid").datagrid({
	    onDblClickRow: function(index, row){
	        var rows = $("#SelectedReadGrid").datagrid("getRows");
	        var rowIndex = $("#SelectedReadGrid").datagrid("getRowIndex",row.READ_ID);
	        if(rowIndex==-1){
	            $("#SelectedReadGrid").datagrid("appendRow",row);
	        }
	    }
	});
	
	$("#SelectedReadGrid").datagrid({
	    onDblClickRow: function(index, row){
	        $("#SelectedReadGrid").datagrid("deleteRow",index);
	    }
	});
});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="ReadToolbar">
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选状态列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                id="ReadGrid" fitColumns="true" toolbar="#ReadToolbar"
                method="post" idField="DIC_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="readTemplateController.do?limitStatusDatagrid">
                <thead>
                    <tr>
                        <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_CODE',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_NAME',align:'left'" width="100">状态</th>
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
                                        style="position: relative; top:7px; float:left;" />&nbsp;已选状态列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" id="SelectedReadGrid" 
                fitColumns="true" toolbar="#SelectedReadToolbar" nowrap="false"
                method="post" idField="DIC_ID" checkOnSelect="false" url="readTemplateController.do?limitStatusSelected&readIds=${readIds}"
                selectOnCheck="false" fit="true" border="false" >
                <thead>
                    <tr>
                        <th data-options="field:'DIC_ID',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_CODE',hidden:true" width="80">DIC_ID</th>
                        <th data-options="field:'DIC_NAME',align:'left'" width="100">状态名称</th>
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


