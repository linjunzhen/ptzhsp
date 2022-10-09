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
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var itemIds = "";
			var itemNames = "";
			var itemCodes = "";
			var treeSns="";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					itemIds+=",";
					itemNames+=",";
					itemCodes+=",";
					treeSns+=",";
				}
				itemIds+=rows[i].DEPART_ID;
				itemNames+=rows[i].DEPART_NAME;
				itemCodes+=rows[i].DEPART_CODE;
				treeSns+=rows[i].TREE_SN;
			}
    		art.dialog.data("selectBSDeptInfo", {
    			departIds:itemIds,
    			departNames:itemNames,
    			departCodes:itemCodes,
    			treeSns:treeSns
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
				var rowIndex = $("#SelectedCatalogGrid").datagrid("getRowIndex",row.DEPART_ID);
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
	
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="CatalogToolbar">
				<!--====================开始编写隐藏域============== -->
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
				</div>
				<form action="#" name="CatalogForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
                            <td style="width:68px;text-align:right;">机构名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_U.DEPART_NAME_LIKE" /></td>
							<td colspan=""><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('CatalogToolbar','CatalogGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('CatalogForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="CatalogGrid" fitColumns="true" toolbar="#CatalogToolbar"
				method="post" idField="DEPART_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="tabletMenuController.do?deptdatagrid">
				<thead>
					<tr>
	                    <th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
	                    <th data-options="field:'TREE_SN',hidden:true" width="80">TREE_SN</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="160">机构名称</th>
						<th data-options="field:'DEPART_CODE',align:'left'" width="126">机构编码</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedCatalogToolbar">
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
				method="post" idField="DEPART_ID" checkOnSelect="false" 
				url="tabletMenuController.do?deptselected"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
	                    <th data-options="field:'DEPART_ID',hidden:true" width="80">DEPART_ID</th>
	                    <th data-options="field:'TREE_SN',hidden:true" width="80">TREE_SN</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="160">机构名称</th>
						<th data-options="field:'DEPART_CODE',align:'left'" width="120">机构编码</th>
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

