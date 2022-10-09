<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addExtraDicTypeTreeHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addItemHref_"+treeNode.id).length>0) return;
	if ($("#editItemHref_"+treeNode.id).length>0) return;
	if ($("#delItemHref_"+treeNode.id).length>0) return;
	var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
	if(treeNode.id!="0"){
		operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
		operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
	}
	aObj.append(operateStr);
	$("#addItemHref_" + treeNode.id).bind("click", function() {
		showExtraDicTypeWindow(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		showExtraDicTypeWindow(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"ExtraDicTypeTree",
			url:"extraDicTypeController.do?multiDel",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function removeExtraDicTypeTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 树形节点拖放排序
 */
function onExtraDicTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType!=null){
		AppUtil.ajaxProgress({
		   url:"baseController.do?updateTreeSn",
		   params:{
			   "dragTreeNodeId":treeNodes[0].id,
			   "dragTreeNodeNewLevel":treeNodes[0].level,
			   "targetNodeId":targetNode.id,
			   "moveType":moveType,
			   "targetNodeLevel":targetNode.level,
			   "tableName":"T_COMMERCIAL_DICTYPE"
		   },
		   callback:function(resultJson){
				if(resultJson.success){
					/* $.fn.zTree.getZTreeObj("ExtraDicTypeTree").reAsyncChildNodes(null, "refresh"); */
	    	   	    alert("成功完成拖拽排序!");
				}else{
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("ExtraDicTypeTree").reAsyncChildNodes(null, "refresh");
				}
		   	    
		   }
		});
	}
	
}
/**
 * 点击树形节点触发的事件
 */
function onExtraDicTypeTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		if(treeNode.id=="0"){
			$("#ExtraDictionaryToolbar input[name='TYPE_ID']").val("");
			$("#ExtraDictionaryToolbar input[name='Q_T.TYPE_ID_=']").val("");
		}else{
			$("#ExtraDictionaryToolbar input[name='TYPE_ID']").val(treeNode.id);
			$("#ExtraDictionaryToolbar input[name='Q_T.TYPE_ID_=']").val(treeNode.id);
		}
		
		AppUtil.gridDoSearch("ExtraDictionaryToolbar","ExtraDictionaryGrid");
	}
}

/**
 * 弹出字典类别窗口
 */
function showExtraDicTypeWindow(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "extraDicTypeController.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
	}else{
		var parentNode = treeNode.getParentNode();
		url = "extraDicTypeController.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "字典类别信息",
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
	AppUtil.gridSearchReset("ExtraDictionaryForm");
}

/**
 * 删除数据字典列表记录
 */
function deleteDictionaryDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"extraDictionaryController.do?multiDel", "ExtraDictionaryGrid");
};

function updateDicSn(){
	var typeId = $("#ExtraDictionaryToolbar input[name='TYPE_ID']").val();
	if(typeId&&typeId!="0"){
		var rows = $("#ExtraDictionaryGrid").datagrid("getRows"); 
		var dicIds = [];
		for(var i=0;i<rows.length;i++){
			dicIds.push(rows[i].DIC_ID);
		}
		if(dicIds.length>0){
			AppUtil.ajaxProgress({
				url:"extraDictionaryController.do?updateSn",
				params:{
					dicIds:dicIds
				},
				callback:function(resultJson){
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					$("#ExtraDictionaryGrid").datagrid("reload");
				}
			})
		}
	}else{
		art.dialog({
			content: "请先选择字典类别!",
			icon:"warning",
		    ok: true
		});

	}
	
}
/**
 * 编辑数据字典列表记录
 */
function editExtraDictionaryGridRecord(){
	var DIC_ID = AppUtil.getEditDataGridRecord("ExtraDictionaryGrid");
	if(DIC_ID){
		showExtraDicWindow(DIC_ID);
	}
}

/**
 * 显示字典信息对话框
 */
function showExtraDicWindow(entityId) {
	if(entityId){
		$.dialog.open("extraDictionaryController.do?info&entityId="+entityId, {
			title : "字典信息",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"250px",
		}, false);
	}else{
		var typeId = $("#ExtraDictionaryToolbar input[name='TYPE_ID']").val();
		if(typeId&&typeId!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("ExtraDicTypeTree");
			var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
			$.dialog.open("extraDictionaryController.do?info&TYPE_ID="+treeNode.id+"&TYPE_NAME="+treeNode.name, {
				title : "字典信息",
		        width:"600px",
		        lock: true,
		        resize:false,
		        height:"250px",
			}, false);
		}else{
			art.dialog({
				content: "请先选择字典类别!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var ExtraDicTypeTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addExtraDicTypeTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeExtraDicTypeTreeHoverDom
		},
		callback : {
			onClick : onExtraDicTypeTreeClick,
			onDrop: onExtraDicTypeTreeDrop
		},
		async : {
			enable : true,
			url : "extraDicTypeController/tree.do",
			otherParam : {
				"tableName" : "T_COMMERCIAL_DICTYPE",
				"idAndNameCol" : "TYPE_ID,TYPE_NAME",
				"targetCols" : "PATH,TYPE_CODE,PARENT_ID",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"rootName":"字典类别树"
			}
		}
	};
	$.fn.zTree.init($("#ExtraDicTypeTree"), ExtraDicTypeTreeSetting);
	//AppUtil.initAuthorityRes("ExtraDictionaryToolbar");
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
		<ul id="ExtraDicTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="ExtraDictionaryToolbar">
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_T.TYPE_ID_=" value="" />
		      <input type="hidden" name="TYPE_ID"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_Dictionary" iconCls="eicon-plus"
								plain="true" onclick="showExtraDicWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_Dictionary" iconCls="eicon-pencil"
								plain="true" onclick="editExtraDictionaryGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_Dictionary" iconCls="eicon-trash-o" plain="true"
								onclick="deleteDictionaryDataGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" resKey="SAVEDICSN_Dictionary" iconCls="eicon-check" plain="true"
								onclick="updateDicSn();">保存排序</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ExtraDictionaryForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:68px;text-align:right;">字典编码</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.DIC_CODE_LIKE" style="width:128px;"></td>
					<td style="width:68px;text-align:right;">字典名称</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.DIC_NAME_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('ExtraDictionaryToolbar','ExtraDictionaryGrid')">
						<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ExtraDictionaryGrid" fitColumns="true" toolbar="#ExtraDictionaryToolbar"
			method="post" idField="DIC_ID" checkOnSelect="false" nowrap="false"
			selectOnCheck="false" fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var typeId = $('#ExtraDictionaryToolbar input[name=TYPE_ID]').val();
			     if(typeId&&typeId!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="extraDictionaryController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'DIC_ID',hidden:true">DIC_ID</th>
					<th data-options="field:'DIC_CODE',align:'left'" width="25%">字典编码</th>
					<th data-options="field:'DIC_NAME',align:'left'" width="25%">字典名称</th>
					<th data-options="field:'DIC_DESC',align:'left'" width="19%">字典描述</th>
					<th data-options="field:'DIC_SN',align:'left'" width="10%">字典排序值</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="15%" >所属类别</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




