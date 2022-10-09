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
	$(document).ready(function() {
	    /* var catalogTreeSetting = {
	        edit : {
	            enable : false,
	            showRemoveBtn : false,
	            showRenameBtn : false
	        },
	        view : {
	            selectedMulti : false
	        },
	        callback : {
	            onClick : onCatalogTreeClick
	        },
	        async : {
	            enable : true,
	            url : "catalogController.do?tree"
	        }
	    };
	    $.fn.zTree.init($("#catalogTree"), catalogTreeSetting);	  */   
	    var DepartTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartTreeClick
			},
			async : {
				enable : true,
				url : "departmentController.do?treeAuth"
			}
		};
		$.fn.zTree.init($("#DepartTree"), DepartTreeSetting);
	});
	
	function gbUrl(){
	    var  cname = $("#mlmc").val();
	    var zTree = $.fn.zTree.getZTreeObj("catalogTree");
	    zTree.setting.async.url = "catalogController.do?tree";
	    zTree.setting.async.otherParam = {
	        "Q_SC.CATALOG_NAME_LIKE" : cname,
	    }
	    zTree.reAsyncChildNodes(null, "refresh");
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
	    if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
	        $("#ItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(treeNode.id);
	        AppUtil.gridDoSearch("ItemToolbar","ItemGrid");
	    }else if(event.target.tagName == "SPAN"&&treeNode.id==0){
	        $("#ItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val("");
	        AppUtil.gridDoSearch("ItemToolbar","ItemGrid");
	    }
	}
	function onDepartTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
			$("#ItemToolbar input[name='Q_T.DEPART_ID_EQ']").val(treeNode.id);
			AppUtil.gridDoSearch("ItemToolbar", "ItemGrid");
		}else if(event.target.tagName == "SPAN"&&treeNode.id==0){
			$("#ItemToolbar input[name='Q_T.DEPART_ID_EQ']").val("");
            AppUtil.gridDoSearch("ItemToolbar", "ItemGrid");
		}
	}

    function doSelectRows(){
    	var rows = $("#ItemGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if('${account}'<=1&&rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
    		var defKeys = "";
			var itemCodes = "";
			var itemNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					defKeys+=",";
					itemCodes+=",";
					itemNames+=",";
				}
				defKeys+=rows[i].DEF_KEY;
				itemCodes+=rows[i].ITEM_CODE;
				itemNames+=rows[i].ITEM_NAME;
			}
			if(defKeys.indexOf('6')>-1){
				parent.art.dialog({
					content: "该事项审批流程未规范，请梳理后联系技术处（059123122315）协助重新配置。 ",
					icon:"error",
					ok: true
				});
				return null;
			}
    		art.dialog.data("selectItemInfo", {
    			defKeys:defKeys,
    			itemCodes:itemCodes,
    			itemNames:itemNames
			}); 
    		AppUtil.closeLayer();
    	}
    	
    }


	$(function() {
	});
	
	function dosearchItem(){
		$("#ItemGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ItemToolbar','ItemGrid');
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout eui-dialog" fit="true" >	
		<!-- <div data-options="region:'west',split:false"
	        style="width:250px;">
	        <div class="right-con">
	            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">                
	                <div class="e-toolbar-ct">
	                    <div class="e-toolbar-overflow">
	                        <div style="color:#005588;">
	                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
	                                style="position: relative; top:7px; float:left;" />&nbsp;服务目录树
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	                            目录名称：<input class="eve-input" style="width:90px;" name="mlmc" id="mlmc"/>
	        <input type="button" value="查询" class="eve-button" onclick="gbUrl();" />
	        <ul id="catalogTree" class="ztree"></ul>
	    </div> -->	
		<div data-options="region:'west',split:false"
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
			<ul id="DepartTree" class="ztree"></ul>
		</div>
		<div data-options="region:'center',split:false">
			<div id="ItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<!-- <input type="hidden" name="Q_T.CATALOG_CODE_EQ" /> -->
				<input type="hidden" name="Q_T.DEPART_ID_EQ" />
				<!--====================结束编写隐藏域============== -->
				<form name="ItemForm">
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
                           <td style="width:68px;text-align:right;">事项性质</td>
                           <td style="width:135px;"><input class="easyui-combobox"
                               style="width:128px;" name="Q_T.SXXZ_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                        </tr>
                        <tr style="height:28px;">
                           <td style="width:68px;text-align:right;">办件类型</td>
                           <td style="width:135px;"><input class="easyui-combobox"
                               style="width:128px;" name="Q_T.SXLX_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="dosearchItem();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('ItemForm')" /></td>
					</tr>
				</table>
			    </form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
				id="ItemGrid" fitColumns="true" toolbar="#ItemToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="serviceItemController.do?publishYctbDatagrid&Q_T.FWSXMXLB_NEQ=2&Q_T.IS_YCTB_EQ=1">
				<thead>
					<tr>
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'DEF_KEY',hidden:true">DEF_KEY</th>
	                    <th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
	                    <th data-options="field:'ITEM_CODE',align:'left'" width="18%">事项编码</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="35%">事项名称</th>
	                    <th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
	                    <th data-options="field:'SXLX',align:'left'" width="10%" >办件类型</th>
	                    <th data-options="field:'DEPART_NAME',align:'left'" width="17%" >所属部门</th>
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
