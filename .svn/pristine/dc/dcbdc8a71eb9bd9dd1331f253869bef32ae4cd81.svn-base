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
    	var rows = $("#SelectedTagGrid").datagrid("getRows");
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
    		art.dialog.data("selectTagInfo", {
    			tagIds:tagIds,
    			tagNames:tagNames
			});
    		AppUtil.closeLayer();
    	}
    	
    }

    /**
	 * 点击树形节点触发的事件
	 */
	function onTagTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id=="0"){
				$("#TagToolbar input[name='TYPE_ID']").val("");
				$("#TagToolbar input[name='Q_t.TYPE_ID_=']").val("");
			}else{
				$("#TagToolbar input[name='TYPE_ID']").val(treeNode.id);
				$("#TagToolbar input[name='Q_t.TYPE_ID_=']").val(treeNode.id);
			}
			AppUtil.gridDoSearch("TagToolbar","TagGrid");
		}
	}

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		var tagTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onTagTreeClick
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_USERCENTER_TAGTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PATH,PARENT_ID",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName":"标签类别树"
				}
			}
		};
		$.fn.zTree.init($("#tagTree"), tagTreeSetting);
		$("#TagGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedTagGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedTagGrid").datagrid("getRowIndex",row.TAG_ID);
				if(rowIndex==-1){
					$("#SelectedTagGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedTagGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedTagGrid").datagrid("deleteRow",index);
			}
		});

	});
	
	function formatLevel(val, row) {
		if (row.TAG_LEVEL == "01") {
			return "<font><b>"+val+"</b></font>";
		} else if (row.TAG_LEVEL == "02") {
			return "　　"+val;
		}
	};
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >
		<div data-options="region:'west',split:false" style="width:250px;">
			<ul id="tagTree" class="ztree"></ul>
		</div>
		
		<div data-options="region:'center',split:false" style="width: 375px;">
			<div id="TagToolbar">
				<!--====================开始编写隐藏域============== -->
			    <input type="hidden" name="Q_t.TYPE_ID_=" value="" />
			    <input type="hidden" name="TYPE_ID"  />
			 	
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
				<form action="#" name="TagForm">
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
								onclick="AppUtil.gridDoSearch('TagToolbar','TagGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('TagForm')" /></td>
						</tr>
	
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="TagGrid" fitColumns="true" toolbar="#TagToolbar"
				method="post" idField="TAG_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="tagController.do?tagData">
				<thead>
					<tr>
						<th data-options="field:'TAG_ID',hidden:true" width="80">TAG_ID</th>
						<th data-options="field:'TAG_NAME',align:'left'" width="100" formatter="formatLevel">标签名称</th>
						<th data-options="field:'TAG_KEY',align:'left'" width="100">标签编码</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'east',split:false" style="width: 375px;">
			
			<div id="SelectedTagToolbar">
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
			<table class="easyui-datagrid" rownumbers="true" id="SelectedTagGrid" 
			    fitColumns="true" toolbar="#SelectedTagToolbar" nowrap="false"
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

