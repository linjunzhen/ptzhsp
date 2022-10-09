<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	/* $(document).ready(function() {
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
	            onClick : onCatalogTreeClick,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
				}
	        },
	        async : {
				enable : true,
				url : "departmentController.do?parentTree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME,DEPART_CODE",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"Q_STATUS_!=" : 0,
					"isShowRoot" : "false",
					"rootName" : "部门树"
				}
			}
	    };
	    $.fn.zTree.init($("#catalogTree"), catalogTreeSetting);	    
	    
	});*/
	
	/**
	 * 点击树形节点触发的事件
	 */
	/* function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
	    if (event.target.tagName == "SPAN"&&treeNode.id!=0&&treeNode.id!='7D120C9034154F0E0000280000000037') {
	        $("#LicenseCatalogToolbar input[name='Q_T.SSBMBM_EQ']").val(treeNode.DEPART_CODE);
	        AppUtil.gridDoSearch("LicenseCatalogToolbar","LicenseCatalogGrid");
	    }else if(event.target.tagName == "SPAN"&&(treeNode.id==0||treeNode.id=='7D120C9034154F0E0000280000000037')){
	        $("#LicenseCatalogToolbar input[name='Q_T.SSBMBM_EQ']").val("");
	        AppUtil.gridDoSearch("LicenseCatalogToolbar","LicenseCatalogGrid");
	    }
	} */

    function doSelectRows(){
    	var rows = $("#SelectedLicenseCatalogGrid").datagrid("getRows");
    	if(rows.length==0){
    		alert("请至少选择一条记录!");
    	}else{
    		var CatalogCodes = "";
			var CatalogNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					CatalogCodes+=",";
					CatalogNames+=",";
				}
				CatalogCodes+=rows[i].CATALOG_CODE;
				CatalogNames+=rows[i].CATALOG_NAME;
				
			}
    		art.dialog.data("selectCatalogs", {
    			CatalogCodes:CatalogCodes,
    			CatalogNames:CatalogNames,
			});
    		AppUtil.closeLayer();
    	}
    	
    }


	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		$("#LicenseCatalogGrid").datagrid({
			onDblClickRow: function(index, row){
				var rows = $("#SelectedLicenseCatalogGrid").datagrid("getRows");
				if((rows.length>=allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}			
				
				var rowIndex = $("#SelectedLicenseCatalogGrid").datagrid("getRowIndex",row.CATALOG_ID);
				if(rowIndex==-1){
					$("#SelectedLicenseCatalogGrid").datagrid("appendRow",row);
				}
			}
		});
		
		$("#SelectedLicenseCatalogGrid").datagrid({
			onDblClickRow: function(index, row){
				$("#SelectedLicenseCatalogGrid").datagrid("deleteRow",index);
			}
		});

	});
	
	function dosearchItem(){
		$("#LicenseCatalogGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('LicenseCatalogToolbar','LicenseCatalogGrid');
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<input type="hidden" name="allowCount" value="1" />
	<div class="easyui-layout eui-dialog" fit="true" >	
		<!-- <div data-options="region:'west',split:false"
	        style="width:250px;">
	        <div class="right-con">
	            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">                
	                <div class="e-toolbar-ct">
	                    <div class="e-toolbar-overflow">
	                        <div style="color:#005588;">
	                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
	                                style="position: relative; top:7px; float:left;" />&nbsp;组织机构树
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <ul id="catalogTree" class="ztree"></ul>
	    </div> -->	
		<div data-options="region:'center',split:false">
			<div id="LicenseCatalogToolbar">
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
							   <div style="color:#005588;">
									<img src="plug-in/easyui-1.4/themes/icons/script.png"
										style="position: relative; top:7px; float:left;" >&nbsp;可选证照目录列表</img>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--====================开始编写隐藏域============== -->
                
				<!--====================结束编写隐藏域============== -->
				<form name="LicenseCatalogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
                        <td style="width:68px;text-align:right;">目录名称</td>
                        <td style="width:135px;"><input class="eve-input"
                            type="text" maxlength="20" style="width:128px;"
                            name="CATALOG_NAME" /></td>
                        <td style="width:68px;text-align:right;">目录编码</td>
                        <td style="width:135px;"><input class="eve-input"
                            type="text" maxlength="20" style="width:128px;"
                            name="CATALOG_CODE" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="dosearchItem();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('LicenseCatalogForm')" /></td>
					</tr>
				</table>
			    </form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================--> 
			<table rownumbers="true" pagination="true" striped="true"
				id="LicenseCatalogGrid" fitColumns="true" toolbar="#LicenseCatalogToolbar"
				method="post" idField="CATALOG_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="licenseCatalogController.do?licenseCatalogData">
				<thead>
					<tr>
	                    <th data-options="field:'CATALOG_ID',hidden:true" width="80">CATALOG_ID</th>
	                    <th data-options="field:'DEPART_NAME',align:'left'" width="150">部门</th>
	                    <th data-options="field:'CATALOG_CODE',align:'left'" width="150">目录编码</th>
	                    <th data-options="field:'CATALOG_NAME',align:'left'" width="250">目录名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		<div data-options="region:'east',split:false" style="width: 500px;">
			
			<div id="SelectedLicenseCatalogToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="TYPE_ID">
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
			<table rownumbers="true" id="SelectedLicenseCatalogGrid" 
			    fitColumns="true" toolbar="#SelectedLicenseCatalogToolbar" nowrap="false"
				method="post" idField="CATALOG_ID" checkOnSelect="false" url="licenseCatalogController.do?materBindSelected&BINDCATALOG_CODE=${BINDCATALOG_CODE}"
				selectOnCheck="false" fit="true" border="false" >
				<thead>
					<tr>
	                    <th data-options="field:'CATALOG_ID',hidden:true" width="80">CATALOG_ID</th>
	                    <th data-options="field:'DEPART_NAME',align:'left'" width="150">部门</th>
	                    <th data-options="field:'CATALOG_CODE',align:'left'" width="150">目录编码</th>
	                    <th data-options="field:'CATALOG_NAME',align:'left'" width="250">目录名称</th>
					</tr>
				</thead>
			</table>
	
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
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
