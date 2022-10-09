<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addLogConfigTreeHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addItemHref_"+treeNode.id).length>0) return;
	if ($("#editItemHref_"+treeNode.id).length>0) return;
	if ($("#delItemHref_"+treeNode.id).length>0) return;
	var operateStr = "";
	if(treeNode.id=="0"){
		operateStr += "<a id='addItemHref_" +treeNode.id+ "' title='创建' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
	}
	if(treeNode.id!="0"){
		operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
		operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
	}
	aObj.append(operateStr);
	$("#addItemHref_" + treeNode.id).bind("click", function() {
		showLogInfoConfigWindow(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		showLogInfoConfigWindow(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"LogConfigTree",
			url:"logConfigController.do?multiDel",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function removeLogConfigTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 树形节点拖放排序
 */
function onLogConfigTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType!=null){
		AppUtil.ajaxProgress({
		   url:"baseController.do?updateTreeSn",
		   params:{
			   "dragTreeNodeId":treeNodes[0].id,
			   "dragTreeNodeNewLevel":treeNodes[0].level,
			   "targetNodeId":targetNode.id,
			   "moveType":moveType,
			   "targetNodeLevel":targetNode.level,
			   "tableName":"T_SYSTEM_LOGCONFIG_TABLES"
		   },
		   callback:function(resultJson){
				if(resultJson.success){
					/* $.fn.zTree.getZTreeObj("LogConfigTree").reAsyncChildNodes(null, "refresh"); */
	    	   	    alert("成功完成拖拽排序!");
				}else{
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("LogConfigTree").reAsyncChildNodes(null, "refresh");
				}
		   	    
		   }
		});
	}
	
}
/**
 * 点击树形节点触发的事件
 */
function onLogConfigTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		if(treeNode.id=="0"){
			$("#logConfigToolbar input[name='CONFIG_ID']").val("");
			$("#logConfigToolbar input[name='Q_T.CONFIG_ID_=']").val("");
		}else{
			$("#logConfigToolbar input[name='CONFIG_ID']").val(treeNode.id);
			$("#logConfigToolbar input[name='Q_T.CONFIG_ID_=']").val(treeNode.id);
		}
		
		AppUtil.gridDoSearch("logConfigToolbar","logConfigGrid");
	}
}

/**
 * 弹出字典类别窗口
 */
function showLogInfoConfigWindow(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "logConfigController.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
	}else{
		var parentNode = treeNode.getParentNode();
		url = "logConfigController.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "日志维护表信息",
        width:"600px",
        lock: true,
        resize:false,
        height:"250px",
	}, false);
};
/**
 * 重置查询条件
 */
function resetSearch(){
	AppUtil.gridSearchReset("LogConfigForm");
}

/**
 * 删除数据字典列表记录
 */
function deleteDictionaryDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"logConfigController.do?multiDel", "logConfigGrid");
};

/**
 * 编辑数据字典列表记录
 */
function editLogConfigGridRecord(){
	var DIC_ID = AppUtil.getEditDataGridRecord("logConfigGrid");
	if(DIC_ID){
		showLogConfigWindow(DIC_ID);
	}
}

/**
 * 显示字典信息对话框
 */
function showLogConfigWindow(entityId) {
	if(entityId){
		$.dialog.open("logConfigController.do?columnInfo&entityId="+entityId, {
			title : "字段配置信息",
	        width:"650px",
	        lock: true,
	        resize:false,
	        height:"350px",
		}, false);
	}else{
		var typeId = $("#logConfigToolbar input[name='CONFIG_ID']").val();
		if(typeId&&typeId!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("LogConfigTree");
			var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
			$.dialog.open("logConfigController.do?columnInfo&CONFIG_ID="+treeNode.id+"&CONFIG_NAME="+treeNode.name, {
				title : "字段配置信息",
		        width:"650px",
		        lock: true,
		        resize:false,
		        height:"350px",
			}, false);
		}else{
			art.dialog({
				content: "请先选择配置表!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var LogConfigTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addLogConfigTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeLogConfigTreeHoverDom
		},
		callback : {
			onClick : onLogConfigTreeClick,
			onDrop: onLogConfigTreeDrop
		},
		async : {
			enable : true,
			url : "logConfigController/tree.do",
			otherParam : {
				"tableName" : "T_SYSTEM_LOGCONFIG_TABLES",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "TREE_PATH,TYPE_CODE,PARENT_ID",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"rootName":"日志维护表"
			}
		}
	};
	$.fn.zTree.init($("#LogConfigTree"), LogConfigTreeSetting);
	//AppUtil.initAuthorityRes("logConfigToolbar");
});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						  <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;字典类别树</div>
						</div>
					</div>
				</div>
		</div>
		<ul id="LogConfigTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="logConfigToolbar">
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_T.CONFIG_ID_=" value="" />
		     <input type="hidden" name="CONFIG_ID"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_logconfig" iconCls="eicon-plus"
								plain="true" onclick="showLogConfigWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_logconfig" iconCls="eicon-pencil"
								plain="true" onclick="editLogConfigGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_logconfig" iconCls="eicon-trash-o" plain="true"
								onclick="deleteLogConfigGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="LogConfigForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:68px;text-align:right;">字段名称</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_NAME_=" style="width:128px;"></td>
					<td style="width:68px;text-align:right;">字段注释</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_COMMENT_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('logConfigToolbar','logConfigGrid')">
						<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="logConfigGrid" fitColumns="true" toolbar="#logConfigToolbar"
			method="post" idField="COLUMN_ID" checkOnSelect="true"
			selectOnCheck="true" fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var typeId = $('#logConfigToolbar input[name=CONFIG_ID]').val();
			     if(typeId&&typeId!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="logConfigController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'COLUMN_ID',hidden:true">COLUMN_ID</th>
					<th data-options="field:'COLUMN_NAME',align:'left'" width="30%">字段名</th>
					<th data-options="field:'COLUMN_COMMENT',align:'left'" width="30%">字段注释</th>
					<th data-options="field:'CONFIG_NAME',align:'left'" width="34%" >所属类别</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




