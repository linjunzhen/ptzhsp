<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>

<script type="text/javascript">

	//确定
    function doSelectRows(){
    	var rows = $("#SelectedIndustryGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var childIndustryIds = "";
			var childIndustryNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					childIndustryIds+=",";
					childIndustryNames+=",";
				}
				childIndustryIds+=rows[i].INDUSTRY_ID;
				childIndustryNames+=rows[i].INDUSTRY_NAME;
			}
    		art.dialog.data("selectChildIndustryInfo", {
    			childIndustryIds:childIndustryIds,
    			childIndustryNames:childIndustryNames,
			});
    		AppUtil.closeLayer();
    	}
    	
    }

	/**
	 * easyUI grid做勾选API（选中可选子行业）
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	function doMultiCheck(){
		var $dataGrid = $("#IndustryGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		var rows = $("#SelectedIndustryGrid").datagrid("getRows");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>=allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		if(rowsData.length>0){
			for (var i = 0; i < rowsData.length; i++) {
				var row=rowsData[i];
				var rowIndex = $("#SelectedIndustryGrid").datagrid("getRowIndex",row.INDUSTRY_ID);
				if(rowIndex==-1){
					$("#SelectedIndustryGrid").datagrid("appendRow",row);
				}
			}
			$('#IndustryGrid').datagrid('clearChecked')=='none';
		}else{
			alert("至少选择一条记录");
		}
	}
	
	//移除已选用户列表
    function doRemoveCheck(){
		var $dataGrid = $("#SelectedIndustryGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if(rowsData.length>0){
			removeSelectedIndustryGrid();
		}else{
			alert("至少选择一条记录");
		}
	}
    function removeSelectedIndustryGrid(){
		var $dataGrid = $("#SelectedIndustryGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if(rowsData.length>0){
			for (var i = 0; i < rowsData.length; i++) {
				var row=rowsData[i];
				var rowIndex = $("#SelectedIndustryGrid").datagrid("getRowIndex",row);
				$("#SelectedIndustryGrid").datagrid("deleteRow",rowIndex);
			}
			removeSelectedIndustryGrid();
		}else{
			$('#SelectedIndustryGrid').datagrid('clearChecked')=='none';
			return;
		}
	}
	
	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		
		//主行业树
		var industryTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onIndustryTreeClick
			},
			async : {
				enable : true,
				url : "busScopeController.do?industryTree",
			}
		};
		$.fn.zTree.init($("#industryTree"), industryTreeSetting);
		
		//可选子行业列表双击
		$("#IndustryGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedIndustryGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedIndustryGrid").datagrid("getRowIndex",row.INDUSTRY_ID);
				if(rowIndex==-1){
					$("#SelectedIndustryGrid").datagrid("appendRow",row);
				}
			}
		});
		
		//已选子行业列表
		$("#SelectedIndustryGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedIndustryGrid").datagrid("deleteRow",index);
			}
		});

	});
	
	/**
	 * 点击树形节点触发的事件
	 */
	function onIndustryTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id!="0"){
				$("#IndustryToolbar input[name='PARENT_ID']").val(treeNode.id);
			}else{
				$("#IndustryToolbar input[name='PARENT_ID']").val("0");
			}
			AppUtil.gridDoSearch("IndustryToolbar","IndustryGrid");
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout eui-jh-box" fit="true" >
		<div data-options="region:'west',split:false"
			style="width:250px;">
			<div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" >
				<div title="主行业" >
				    <ul id="industryTree" class="ztree"></ul>
				</div>
				
			</div>
		</div>
		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="IndustryToolbar">
				<!--====================开始编写隐藏域============== -->
			 	<input type="hidden" name="PARENT_ID"  />	
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;&nbsp;可选子行业列表</img>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="IndustryForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:40px;text-align:left;">子行业名称</td>
							<td  style="width:150px;"><input class="eve-input"
								type="text" maxlength="128" style="width:128px;"
								name="Q_T.INDUSTRY_NAME_LIKE" /></td>
							
						</tr>
						<tr style="height:28px;">
							<td colspan="2" style="width:250px;"><input type="button" value="查询" 
								class="eve-button"
								onclick="AppUtil.gridDoSearch('IndustryToolbar','IndustryGrid')" />
								<input type="button" value="重置" class="eve-button" 
								onclick="AppUtil.gridSearchReset('IndustryForm')" />
								<input type="button" value="选中" class="eve-button"
								   onclick="doMultiCheck()" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="IndustryGrid" fitColumns="true" toolbar="#IndustryToolbar"
				method="post" idField="INDUSTRY_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="busScopeController.do?childIndusDatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'INDUSTRY_ID',hidden:true" width="80">INDUSTRY_ID</th>
						<th data-options="field:'INDUSTRY_NAME',align:'left'" width="100">子行业名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			 <div id="SelectedIndustryToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="top: 0px;left: 0px; height: 70px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选子行业列表
								</div>
							</div>
							<div class="e-toolbar-overflow">
								<div style="color:#005588;" >
									<input type="button"  style="margin-top: 2px;" value="移除" class="eve-button"
										   onclick="doRemoveCheck()" />
								</div>
							</div>
						</div>
					</div>
				</div> 
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" resize="true" id="SelectedIndustryGrid" striped="true"
			    fitColumns="true" toolbar="#SelectedIndustryToolbar" nowrap="false"
				method="post" idField="INDUSTRY_ID" checkOnSelect="false" url="busScopeController.do?selectedIndustry&childIndustryIds=${childIndustryIds}"
				selectOnCheck="false" fit="false" height="420px" style="margin-top: 300px;" border="false" >
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'INDUSTRY_ID',hidden:true" width="80">INDUSTRY_ID</th>
						<th data-options="field:'INDUSTRY_NAME',align:'left'" width="100">子行业名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south'" style="height:46px;" >
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

