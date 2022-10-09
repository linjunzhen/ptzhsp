
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function add${TreeEntityName}TreeHoverDom(treeId, treeNode) {
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
		show${TreeEntityName}Window(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		show${TreeEntityName}Window(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"${TreeEntityName?uncap_first}Tree",
			url:"${TreeEntityName?uncap_first}Controller.do?multiDel",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function remove${TreeEntityName}TreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 树形节点拖放排序
 */
function on${TreeEntityName}TreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType!=null){
		AppUtil.ajaxProgress({
		   url:"baseController.do?updateTreeSn",
		   params:{
			   "dragTreeNodeId":treeNodes[0].id,
			   "dragTreeNodeNewLevel":treeNodes[0].level,
			   "moveType":moveType,
			   "targetNodeId":targetNode.id,
			   "targetNodeLevel":targetNode.level,
			   "tableName":"${TreeTableName}"
		   },
		   callback:function(resultJson){
				if(resultJson.success){
					//$.fn.zTree.getZTreeObj("${TreeEntityName?uncap_first}Tree").reAsyncChildNodes(null, "refresh");
	    	   	    alert("成功完成拖拽排序!");
				}else{
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("${TreeEntityName?uncap_first}Tree").reAsyncChildNodes(null, "refresh");
				}
		   	    
		   }
		});
	}
	
}
/**
 * 点击树形节点触发的事件
 */
function on${TreeEntityName}TreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		$("#${GridEntityName}Toolbar input[name='${TreePrimaryKeyName}']").val(treeNode.id);
		$("#${GridEntityName}Toolbar input[name='Q_T.PATH_LIKE']").val(treeNode.PATH);
		AppUtil.gridDoSearch("${GridEntityName}Toolbar","${GridEntityName}Grid");
	}
}

/**
 * 弹出${TreeChinese}信息窗口
 */
function show${TreeEntityName}Window(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "${TreeEntityName?uncap_first}Controller.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
	}else{
		var parentNode = treeNode.getParentNode();
		url = "${TreeEntityName?uncap_first}Controller.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "${TreeChinese}信息",
        width:"600px",
        height:"250px",
        lock: true,
        resize:false
	}, false);
	
};
/**
 * 重置查询条件
 */
function resetSearch(){
	var TYPE_ID = $("#${GridEntityName}Toolbar input[name='${TreePrimaryKeyName}']").val();
	var PATH = $("#${GridEntityName}Toolbar input[name='Q_T.PATH_LIKE']").val();
	AppUtil.gridSearchReset("${GridEntityName}Form");
	$("#${GridEntityName}Toolbar input[name='${TreePrimaryKeyName}']").val(TYPE_ID);
	$("#${GridEntityName}Toolbar input[name='Q_T.PATH_LIKE']").val(PATH);
}

/**
 * 删除${GridChinese}列表记录
 */
function delete${GridEntityName}DataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"${GridEntityName?uncap_first}Controller.do?multiDel", "${GridEntityName}Grid");
};
/**
 * 编辑${GridChinese}列表记录
 */
function edit${GridEntityName}GridRecord(){
	var ${GridPrimaryName} = AppUtil.getEditDataGridRecord("${GridEntityName}Grid");
	if(${GridPrimaryName}){
		show${GridEntityName}Window(${GridPrimaryName});
	}
}

/**
 * 显示${GridChinese}信息对话框
 */
function show${GridEntityName}Window(entityId) {
	if(entityId){
		$.dialog.open("${GridEntityName?uncap_first}Controller.do?info&entityId="+entityId, {
			title : "${GridChinese}信息",
	        width:"600px",
	        height:"500px",
	        lock: true,
	        resize:false
		}, false);
		
	}else{
		var ${TreePrimaryKeyName} = $("#${GridEntityName}Toolbar input[name='${TreePrimaryKeyName}']").val();
		if(${TreePrimaryKeyName}&&${TreePrimaryKeyName}!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("${TreeEntityName?uncap_first}Tree");
			var treeNode = treeObj.getNodesByParam("id",${TreePrimaryKeyName}, null)[0];
			$.dialog.open("${GridEntityName?uncap_first}Controller.do?info&${TreePrimaryKeyName}="+treeNode.id+"&TYPE_NAME="+treeNode.name, {
				title : "${GridChinese}信息",
		        width:"600px",
		        height:"500px",
		        lock: true,
		        resize:false
			}, false);
		}else{
			art.dialog({
				content: "请先选择${TreeChinese}!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var ${TreeEntityName?uncap_first}TreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: add${TreeEntityName}TreeHoverDom,
			selectedMulti: false,
			removeHoverDom : remove${TreeEntityName}TreeHoverDom
		},
		callback : {
			onClick : on${TreeEntityName}TreeClick,
			onDrop: on${TreeEntityName}TreeDrop
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "${TreeTableName}",
				"idAndNameCol" : "${TreeIdAndName}",
				"targetCols" : "${TreeTargetCols}",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"rootName":"${TreeChinese}树"
			}
		}
	};
	$.fn.zTree.init($("#${TreeEntityName?uncap_first}Tree"), ${TreeEntityName?uncap_first}TreeSetting);
	AppUtil.initAuthorityRes("${GridEntityName}Toolbar");
});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						  <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;${TreeChinese}树</div>
						</div>
					</div>
				</div>
		</div>
		<ul id="${TreeEntityName?uncap_first}Tree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	  	<div id="${GridEntityName}Toolbar">
		<!--====================开始编写隐藏域============== -->
		<!--====================结束编写隐藏域============== --> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_${GridEntityName}" iconCls="icon-note-add"
								plain="true" onclick="show${GridEntityName}Window();">新建${GridChinese}</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_${GridEntityName}" iconCls="icon-note-edit"
								plain="true" onclick="edit${GridEntityName}GridRecord();">编辑${GridChinese}</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_${GridEntityName}" iconCls="icon-note-delete" 
							   plain="true" onclick="delete${GridEntityName}DataGridRecord();">删除${GridChinese}</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="${GridEntityName}Form" >
			<table class="dddl-contentBorder dddl_table" 
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				 ${QueryConfigContent}
			</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		      
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="${GridEntityName}Grid" fitColumns="false" toolbar="#${GridEntityName}Toolbar"
			method="post" idField="${GridPrimaryName}" checkOnSelect="false"
			selectOnCheck="false" fit="true" border="false"
			url="${GridEntityName?uncap_first}Controller.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					
					<#list gridColumns as gridColumn>
					   <#if gridColumn.columnName==GridPrimaryName >
					   <th data-options="field:'${GridPrimaryName}',hidden:true" width="80">${GridPrimaryName}</th>
					   <#else>
					   <th data-options="field:'${gridColumn.columnName}',align:'left'" width="100">${gridColumn.comments}</th>
					   </#if>
			        </#list>
				</tr>
			</thead>
		</table>
		
	</div>
</div>


