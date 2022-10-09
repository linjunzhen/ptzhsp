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
    		var catalogCode = "";
			var catalogName = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					catalogCode+=",";
					catalogName+=",";
				}
				catalogCode+=rows[i].CATALOG_CODE;
				catalogName+=rows[i].CATALOG_NAME;
			}
    		art.dialog.data("selectCatalogInfo", {
    			catalogCode:catalogCode,
    			catalogName:catalogName
			});
    		AppUtil.closeLayer();
    	}
    	
    }

    /**
	 * 点击树形节点触发的事件
	 */
	function onDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#CatalogToolbar input[name='DEPART_ID']").val(treeNode.id);
			$("#CatalogToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			AppUtil.gridDoSearch("CatalogToolbar","CatalogGrid");
		}
	}
    

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		var departmentTreeSetting = {
	            edit : {
	                enable : false,
	                showRemoveBtn : false,
	                showRenameBtn : false
	            },
	            view : {
	                selectedMulti : false
	            },
	            callback : {
	                onClick : onDepartmentTreeClick
	            },
	            async : {
	                enable : true,
	                url : "departmentController.do?tree",
	                otherParam : {
	                    "tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
	                    "idAndNameCol" : "DEPART_ID,DEPART_NAME",
	                    "targetCols" : "PARENT_ID,PATH",
	                    "rootParentId" : "0",
	                    "Q_STATUS_!=":0,
	                    "isShowRoot" : "true",
	                    "rootName" : "组织机构树"
	                }
	            }
	        };
	        $.fn.zTree.init($("#departmentTree"), departmentTreeSetting);
	        $("#CatalogGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedCatalogGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}
				var rowIndex = $("#SelectedCatalogGrid").datagrid("getRowIndex",row.USER_ID);
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
		if("${catalogCode}"!=null&&"${catalogCode}"!=""&&"${catalogCode}"!="undefind"){
			$("#SelectedCatalogGrid").datagrid('appendRow',{
			    CATALOG_NAME: "${catalogName}",
			    CATALOG_CODE: "${catalogCode}"
			});
		}

	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >
		<div data-options="region:'west',split:false"
			style="width:250px;">
			<ul id="departmentTree" class="ztree"></ul>
		</div>
		
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
										style="position: relative; top:7px; float:left;" />&nbsp;可选目录列表
								</div>
							</div>
						</div>
					</div>
				</div>
				<form action="#" name="CatalogForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">目录编码</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="30" style="width:128px;"
                                name="Q_SC.CATALOG_CODE_LIKE" /></td>
                            <td style="width:68px;text-align:right;">目录名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_SC.CATALOG_NAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
							<td colspan="4"><input type="button" value="查询"
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
				method="post" idField="CATALOG_ID" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="catalogController.do?datagrid">
				<thead>
					<tr>
						<th data-options="field:'CATALOG_ID',hidden:true" width="80">CATALOG_ID</th>
	                    <th data-options="field:'CATALOG_NAME',align:'left'" width="100">目录名称</th>
	                    <th data-options="field:'CATALOG_CODE',align:'left'" width="100">目录编号</th>
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
										style="position: relative; top:7px; float:left;" />&nbsp;已选目录列表
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" id="SelectedCatalogGrid" 
			    fitColumns="true" toolbar="#SelectedCatalogToolbar" nowrap="false"
				method="post" idField="CATALOG_ID" checkOnSelect="false" 
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
						<th data-options="field:'CATALOG_ID',hidden:true" width="80">CATALOG_ID</th>
	                    <th data-options="field:'CATALOG_NAME',align:'left'" width="100">目录名称</th>
	                    <th data-options="field:'CATALOG_CODE',align:'left'" width="100">目录编号</th>
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

