<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#SelectedBusinessGrid").datagrid("getRows");
    	if(rows.length==0){
			art.dialog.data("selectBueinessInfo","none");
			AppUtil.closeLayer();
    	}else{
    		var businessCodes = "";
			var businessNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					businessCodes+=",";
					businessNames+=",";
				}
				businessCodes+=rows[i].BUSINESS_CODE;
				businessNames+=rows[i].BUSINESS_NAME;
			}
    		art.dialog.data("selectBueinessInfo", {
    			businessCodes:businessCodes,
    			businessNames:businessNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#BusinessGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedBusinessGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedBusinessGrid").datagrid("getRowIndex",row.DATA_ID);
				if(rowIndex==-1){
					$("#SelectedBusinessGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedBusinessGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedBusinessGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout eui-dialog" fit="true" >
				
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="BusinessToolbar">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选业务列表</img>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="BusinessForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">所属大厅</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.BELONG_ROOM_="
								data-options="
	url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=roomNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">业务名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.BUSINESS_NAME_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusinessToolbar','BusinessGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusinessForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="BusinessGrid" fitColumns="true" toolbar="#BusinessToolbar"
				method="post" idField="DATA_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="callSetController.do?busManageData&Q_t.SERVICE_STATUS_EQ=1">
				<thead>
					<tr>
						<th data-options="field:'DATA_ID',hidden:true" width="80">DATA_ID</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="100">单位/部门</th>
						<th data-options="field:'BUSINESS_NAME',align:'left'" width="100">业务名称</th>
						<th data-options="field:'BUSINESS_CODE',align:'left'" width="80">业务编码</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedBusinessToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选业务列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedBusinessGrid"  striped="true"
			    fitColumns="true" toolbar="#SelectedBusinessToolbar" nowrap="false"
				method="post" idField="DATA_ID" checkOnSelect="false" url="callSetController.do?selected&businessCodes=${businessCodes}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'DATA_ID',hidden:true" width="80">DATA_ID</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="100">单位/部门</th>
						<th data-options="field:'BUSINESS_NAME',align:'left'" width="100">业务名称</th>
						<th data-options="field:'BUSINESS_CODE',align:'left'" width="80">业务编码</th>
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

