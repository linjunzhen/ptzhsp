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
    	var rows = $("#SelectedCatalogGrid").datagrid("getRows");
    	var catalogCode=$("#CatalogToolbar input[name='Q_T.CATALOG_CODE_EQ']").val();
    	var catalogName=$("#CatalogToolbar input[name='catalogName']").val();
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var itemIds = "";
			var itemNames = "";
			var itemCodes = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					itemIds+=",";
					itemNames+=",";
					itemCodes+=",";
				}
				itemIds+=rows[i].ITEM_ID;
				itemNames+=rows[i].ITEM_NAME;
				itemCodes+=rows[i].ITEM_CODE;
			}
    		art.dialog.data("selectItemInfo", {
    			itemIds:itemIds,
    			itemNames:itemNames,
    			itemCodes:itemCodes,
    			catalogCode:catalogCode,
    			catalogName:catalogName,
    			itemNum:rows.length
			});
    		AppUtil.closeLayer();
    	}
    }
    

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
	        $("#CatalogGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedCatalogGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedCatalogGrid").datagrid("getRowIndex",row.ITEM_ID);
				if(rowIndex==-1){
					$("#SelectedCatalogGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedCatalogGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedCatalogGrid").datagrid("deleteRow",index);
			}
		});
		
		var catalogTreeSetting = {
            edit : {
                enable : false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                selectedMulti : false
            },
            callback : {
                onClick : onCatalogTreeClick
            },
            async : {
                enable : true,
                url : "tabletMenuController.do?catalogtree&itemKind=${itemKind}"
            }
        };
        $.fn.zTree.init($("#catalogTree"), catalogTreeSetting);
	
	});

/**
     * 点击树形节点触发的事件
     */
    function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
            $("#CatalogToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(treeNode.id);
            $("#CatalogToolbar input[name='catalogName']").val(treeNode.name);
            AppUtil.gridDoSearch("CatalogToolbar","CatalogGrid");
            
            $("#SelectedCatalogToolbar input[name='Q_T.ITEM_CATALOGCODE_EQ']").val(treeNode.id);
            $("#SelectedCatalogGrid").datagrid("loadData", { total: 0, rows: [] });
            AppUtil.gridDoSearch("SelectedCatalogToolbar","SelectedCatalogGrid");
        }else if(event.target.tagName == "SPAN"&&treeNode.id==0){
            $("#CatalogToolbar input[name='Q_T.CATALOG_CODE_EQ']").val("");
            $("#CatalogToolbar input[name='catalogName']").val("");
            AppUtil.gridDoSearch("CatalogToolbar","CatalogGrid");
            
            $("#SelectedCatalogToolbar input[name='Q_T.ITEM_CATALOGCODE_EQ']").val("");
            $("#SelectedCatalogGrid").datagrid("loadData", { total: 0, rows: [] });
            AppUtil.gridDoSearch("SelectedCatalogToolbar","SelectedCatalogGrid");
            //AppUtil.gridDoSearch("SelectedCatalogToolbar","SelectedCatalogGrid");
        }
    }	
function gbUrl(){
        var  cname = $("#mlmc").val();
        var zTree = $.fn.zTree.getZTreeObj("catalogTree");
        zTree.setting.async.url = "catalogController.do?tree";
        zTree.setting.async.otherParam = {
            "Q_SC.CATALOG_NAME_LIKE" : cname,
        }
        zTree.reAsyncChildNodes(null, "refresh");
    }	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >
	
	<div data-options="region:'west',split:false"
        style="width:250px;">
        <div class="right-con">
            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">                
                <div class="e-toolbar-ct">
                    <div class="e-toolbar-overflow">
                        <div style="color:#005588;">
                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                style="position: relative; top:7px; float:left;" />&nbsp;服务目录树
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            目录名称：<input class="eve-input" style="width:90px;" name="mlmc" id="mlmc"/>
        <input type="button" value="查询" class="eve-button" onclick="gbUrl();" />
        <ul id="catalogTree" class="ztree"></ul>
    </div>
    
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="CatalogToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
				<input type="hidden" name="catalogName" />
				<input type="hidden" name="Q_D.PATH_LIKE" />
			 	<input type="hidden" name="DEPART_ID"  />	
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" />&nbsp;可选列表
								</div>
							</div>
						</div>
					</div>
				</div><!-- 
				<form action="#" name="CatalogForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
                            <td style="width:68px;text-align:right;">事项名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan=""><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('CatalogToolbar','CatalogGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('CatalogForm')" /></td>
						</tr>
	
					</table>
				</form>   -->
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="CatalogGrid" fitColumns="true" toolbar="#CatalogToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="false"
				selectoncheck="false" fit="true" border="false" nowrap="false"
				url="tabletMenuController.do?kxdatagrid&tabletId=${tabletId}&itemKind=${itemKind}">
				<thead>
					<tr>
	                    <th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="250">事项名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedCatalogToolbar">
				<input type="hidden" name="Q_T.ITEM_CATALOGCODE_EQ" />
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedCatalogGrid" 
			    fitColumns="true" toolbar="#SelectedCatalogToolbar" nowrap="false"
				method="post" idField="ITEM_ID" checkOnSelect="false" 
				url="tabletMenuController.do?selected&tabletId=${tabletId}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
	                    <th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="250">事项名称</th>
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

