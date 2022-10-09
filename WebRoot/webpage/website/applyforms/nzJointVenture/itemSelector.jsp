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
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#SelectedFormGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var itemCodes = "";
			var itemNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					itemCodes+=",";
					itemNames+=",\n";
				}
				itemCodes+=rows[i].ITEM_CODE;
				itemNames+=rows[i].ITEM_NAME;
			}
    		art.dialog.data("selectRelatedItemInfo", {
    			itemCodes:itemCodes,
    			itemNames:itemNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }
	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#FormGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedFormGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedFormGrid").datagrid("getRowIndex",row.RELATED_ID);
				if(rowIndex==-1){
					$("#SelectedFormGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedFormGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedFormGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="FormToolbar">
				<!--====================开始编写隐藏域============== -->
			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选事项列表</img>
									<font style="color:#FF0033;float:right;" >如有重名，请注意事项编码!&nbsp;</font>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="FormForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('FormToolbar','FormGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('FormForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="FormGrid" fitColumns="true" toolbar="#FormToolbar"
				method="post" idField="RELATED_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="domesticControllerController/datagrid.do?noAuth=${noAuth}">
				<thead>
					<tr>
						<th data-options="field:'RELATED_ID',hidden:true" width="80">RELATED_ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="100">事项名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedFormToolbar">
				<!--====================开始编写隐藏域============== -->
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选事项列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedFormGrid" 
			    fitColumns="true" toolbar="#SelectedFormToolbar" nowrap="false"
				method="post" idField="RELATED_ID" checkOnSelect="false" url="domesticControllerController/selected.do?induIds=${induIds}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'RELATED_ID',hidden:true" width="80">RELATED_ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="100">事项名称</th>
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

</html>
