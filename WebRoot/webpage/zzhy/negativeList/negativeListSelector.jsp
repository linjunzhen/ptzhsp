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
    	var rows = $("#SelectednegativeListGrid").datagrid("getRows");
    	
		var negativeListCodes = "";
		var negativeListNames = "";
		for(var i = 0;i<rows.length;i++){
			if(i>0){
				negativeListCodes+=",";
				negativeListNames+=",";
			}
			negativeListCodes+=rows[i].NEGATIVELIST_CODE;
			negativeListNames+=rows[i].NEGATIVELIST_NAME;
		}
		art.dialog.data("negativeListInfo", {
			negativeListCodes:negativeListCodes,
			negativeListNames:negativeListNames
		});
		AppUtil.closeLayer();
    	
    	
    }


	$(function() {	
		var allowCount = $("input[name='allowCount']").val();
		$("#negativeListGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectednegativeListGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectednegativeListGrid").datagrid("getRowIndex",row.NEGATIVELIST_ID);
				if(rowIndex==-1){
					$("#SelectednegativeListGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectednegativeListGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectednegativeListGrid").datagrid("deleteRow",index);
			}
		});

	});
	
	function searchReset() {
		$("input[name='Q_T.NEGATIVELIST_NAME_LIKE']").val("");
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="negativeListToolbar">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:100px;text-align:right;">负面清单领域：</td>
						<td style="width:135px;"><input class="eve-input"
							type="text" maxlength="20" style="width:128px;"
							name="Q_T.NEGATIVELIST_NAME_LIKE" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('negativeListToolbar','negativeListGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="searchReset();" /></td>
					</tr>
				</table>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="negativeListGrid" fitColumns="true" toolbar="#negativeListToolbar"
				method="post" idField="NEGATIVELIST_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="negativeListController.do?datagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th data-options="field:'NEGATIVELIST_ID',hidden:true" width="80">NEGATIVELIST_ID</th>
						<th data-options="field:'NEGATIVELIST_CODE',hidden:true" width="80">NEGATIVELIST_CODE</th>
						<th data-options="field:'NEGATIVELIST_NAME',align:'left'" width="120">负面清单领域</th>
						<th data-options="field:'NEGATIVELIST_MEASURES',align:'left'" width="200">特别管理措施</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectednegativeListToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con" style="height: 29px;">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选领域列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectednegativeListGrid" 
			    fitColumns="true" toolbar="#SelectednegativeListToolbar" nowrap="false"
				method="post" idField="NEGATIVELIST_ID" checkOnSelect="false" url="negativeListController/selected.do?negativeListCodes=${negativeListCodes}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'NEGATIVELIST_ID',hidden:true" width="80">NEGATIVELIST_ID</th>
						<th data-options="field:'NEGATIVELIST_CODE',hidden:true" width="80">NEGATIVELIST_CODE</th>
						<th data-options="field:'NEGATIVELIST_NAME',align:'left'" width="120">负面清单领域</th>
						<th data-options="field:'NEGATIVELIST_MEASURES',align:'left'" width="200">特别管理措施</th>
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

