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
    	var rows = $("#parentSelectedTagGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var tagIds = "";
			var tagNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					tagIds+=",";
					tagNames+=",";
				}
				tagIds+=rows[i].TAG_ID;
				tagNames+=rows[i].TAG_NAME;
			}
    		art.dialog.data("selectParentTagInfo", {
    			tagIds:tagIds,
    			tagNames:tagNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#parentTagGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#parentSelectedparentparentTagGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#parentSelectedTagGrid").datagrid("getRowIndex",row.TAG_ID);
				if(rowIndex==-1){
					$("#parentSelectedTagGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#parentSelectedTagGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#parentSelectedTagGrid").datagrid("deleteRow",index);
			}
		});

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >
			
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="parentTagToolbar">
				<!--====================开始编写隐藏域============== -->			 	
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选标签列表</img>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="parentTagForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">标签编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.TAG_KEY_LIKE" /></td>
							<td style="width:68px;text-align:right;">标签名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_t.TAG_NAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('parentTagToolbar','parentTagGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('parentTagForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="parentTagGrid" fitColumns="true" toolbar="#parentTagToolbar"
				method="post" idField="TAG_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="tagController.do?tagData&Q_t.tag_level_EQ=01&Q_t.type_id_EQ=${typeId}">
				<thead>
					<tr>
						<th data-options="field:'TAG_ID',hidden:true" width="80">TAG_ID</th>
						<th data-options="field:'TAG_KEY',align:'left'" width="100">标签编码</th>
						<th data-options="field:'TAG_NAME',align:'left'" width="100">标签名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="parentSelectedTagToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
				<!--====================结束编写隐藏域============== -->
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/tick-btn.png"
										style="position: relative; top:7px; float:left;" />&nbsp;已选标签列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="parentSelectedTagGrid" 
			    fitColumns="true" toolbar="#parentSelectedTagToolbar" nowrap="false"
				method="post" idField="TAG_ID" checkOnSelect="false" url="tagController.do?selected&tagIds=${tagIds}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'TAG_ID',hidden:true" width="80">TAG_ID</th>
						<th data-options="field:'TAG_KEY',align:'left'" width="100">标签编码</th>
						<th data-options="field:'TAG_NAME',align:'left'" width="100">标签名称</th>
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

