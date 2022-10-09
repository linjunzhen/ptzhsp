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
    	var rows = $("#SelectedApplyMaterGrid").datagrid("getRows");
    	if(rows.length==0){
    		//alert("请至少选择一条记录!");
			art.dialog.data("selectMaterInfo", {
    			materIds:"",
    			materNames:""
			});
    		AppUtil.closeLayer();
    	}else{
    		var materIds = "";
			var materNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					materIds+=",";
					materNames+=",";
				}
				materIds+=rows[i].MATER_ID;
				materNames+=rows[i].MATER_NAME;
			}
    		art.dialog.data("selectMaterInfo", {
    			materIds:materIds,
    			materNames:materNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }

	/**
	 * 点击树形节点触发的事件
	 */
	function onBusTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#ApplyMaterToolbar input[name='TYPE_ID']").val(treeNode.id);
			AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
		}
	}

	$(function() {
		$("#ApplyMaterGrid").datagrid({
			onDblClickRow: function(index, row){
				var rowIndex = $("#SelectedApplyMaterGrid").datagrid("getRowIndex",row.MATER_ID);
				if(rowIndex==-1){
					$("#SelectedApplyMaterGrid").datagrid("appendRow",row);
				}else{
					alert("已选择该材料!");
				}
			}
		});
		
		$("#SelectedApplyMaterGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedApplyMaterGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >
		<div data-options="region:'center',split:false" style="width: 500px;">
			<div id="ApplyMaterToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" />&nbsp;可选材料列表
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="ApplyMaterForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">材料编码</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="MATER_CODE" /></td>
							<td style="width:68px;text-align:right;">材料名称</td>
							<td style="width:135px;"><input class="eve-input" type="text"
								maxlength="20" style="width:128px;" name="MATER_NAME" /></td>
						</tr>
						<tr style="height:28px;">
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('ApplyMaterToolbar','ApplyMaterGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ApplyMaterForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ApplyMaterGrid" fitColumns="true" toolbar="#ApplyMaterToolbar"
				method="post" idField="MATER_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="applyMaterController.do?itemDatagrid&taskId=${taskId}&exeId=${exeId}">
				<thead>
					<tr>
						<th data-options="field:'MATER_ID',hidden:true" width="80">MATER_ID</th>
						<th data-options="field:'MATER_CODE',align:'left'" width="100">材料编码</th>
						<th data-options="field:'MATER_NAME',align:'left'" width="200">材料名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 500px;">
			
			<div id="SelectedApplyMaterToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选材料列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedApplyMaterGrid" 
			    fitColumns="true" toolbar="#SelectedApplyMaterToolbar" nowrap="false"
				method="post" idField="MATER_ID" checkOnSelect="false" url="applyMaterController.do?itemSelected&userName=${userName}&exeId=${exeId}&taskId=${taskId}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'MATER_ID',hidden:true" width="80">MATER_ID</th>
						<th data-options="field:'MATER_CODE',align:'left'" width="100">材料编码</th>
						<th data-options="field:'MATER_NAME',align:'left'" width="200">材料名称</th>
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

