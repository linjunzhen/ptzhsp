<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addTagTreeHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addItemHref_"+treeNode.id).length>0) return;
	if ($("#editItemHref_"+treeNode.id).length>0) return;
	if ($("#delItemHref_"+treeNode.id).length>0) return;
	var operateStr = "";
	if(treeNode.id=="0"){
		operateStr += "<a id='addItemHref_" +treeNode.id+ "' title='新增' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
	}
	if(treeNode.id!="0"){
		operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
		operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
	}
	aObj.append(operateStr);
	$("#addItemHref_" + treeNode.id).bind("click", function() {
		showCatalogWindow(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		showCatalogWindow(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"tagTree",
			url:"tagController.do?multiDelType",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function removeTagTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 树形节点拖放排序
 */
function onTagTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType!=null){
		AppUtil.ajaxProgress({
		   url:"baseController.do?updateTreeSn",
		   params:{
			   "dragTreeNodeId":treeNodes[0].id,
			   "dragTreeNodeNewLevel":treeNodes[0].level,
			   "targetNodeId":targetNode.id,
			   "moveType":moveType,
			   "targetNodeLevel":targetNode.level,
			   "tableName":"T_USERCENTER_TAGTYPE"
		   },
		   callback:function(resultJson){
				if(resultJson.success){
					/* $.fn.zTree.getZTreeObj("tagTree").reAsyncChildNodes(null, "refresh"); */
	    	   	    alert("成功完成拖拽排序!");
				}else{
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("tagTree").reAsyncChildNodes(null, "refresh");
				}
		   	    
		   }
		});
	}
	
}
/**
 * 点击树形节点触发的事件
 */
function onTagTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		if(treeNode.id=="0"){
			$("#centerTagToolbar input[name='TYPE_ID']").val("");
			$("#centerTagToolbar input[name='Q_t.TYPE_ID_=']").val("");
		}else{
			$("#centerTagToolbar input[name='TYPE_ID']").val(treeNode.id);
			$("#centerTagToolbar input[name='Q_t.TYPE_ID_=']").val(treeNode.id);
		}
		AppUtil.gridDoSearch("centerTagToolbar","centerTagGrid");
	}
}

/**
 * 弹出标签类别窗口
 */
function showCatalogWindow(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "tagController.do?typeInfo&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
	}else{
		var parentNode = treeNode.getParentNode();
		url = "tagController.do?typeInfo&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "标签类别信息",
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
	AppUtil.gridSearchReset("centerTagForm");
}

/**
 * 删除标签列表记录
 */
function deleteCatalogDataGridRecord() {
    var rows = $("#centerTagGrid").datagrid("getChecked");
    var childFlag = false;
    var useFlag = false;
    var childMsg = "";
    var useMsg = "";
    for(var i = 0;i<rows.length;i++){
    	if(rows[i].HASCHILD>0){
    		childFlag = true;
    		childMsg = "此标签下含有子标签，请先删除对应子标签；";
    	}
    	if(rows[i].ISUSE>0){
    		useFlag = true;
    		useMsg = "此标签使用中，请先前往解除材料标签关系后，再行删除；";
    	}
    }
    var msg="";
    if(childFlag){
    	msg += childMsg+"<br>";
    }
    if(useFlag){
    	msg += useMsg;
    }
    if(msg==""){
		AppUtil.deleteDataGridRecord(
				"tagController.do?multiDel", "centerTagGrid");
    }else{    	
       	 parent.art.dialog({
			content: msg,
			icon:"error",
			time:3,
			ok: true
		});
		return;
    }
};


/**
 * 编辑标签表记录
 */
function editcenterTagGridRecord(){
	var DIC_ID = AppUtil.getEditDataGridRecord("centerTagGrid");
	if(DIC_ID){
		openCatalogWindow(DIC_ID);
	}
}

/**
 * 显示标签信息对话框
 */
function openCatalogWindow(entityId) {
	if(entityId){
		$.dialog.open("tagController.do?info&entityId="+entityId, {
			title : "标签信息",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"200px",
		}, false);
	}else{
		var typeId = $("#centerTagToolbar input[name='TYPE_ID']").val();
		if(typeId&&typeId!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("tagTree");
			var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
			$.dialog.open("tagController.do?info&TYPE_ID="+treeNode.id+"&TYPE_NAME="+treeNode.name, {
				title : "标签信息",
		        width:"600px",
		        lock: true,
		        resize:false,
		        height:"200px",
			}, false);
		}else{
			art.dialog({
				content: "请先选择标签类别!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var tagTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addTagTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeTagTreeHoverDom
		},
		callback : {
			onClick : onTagTreeClick,
			onDrop: onTagTreeDrop
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
	//AppUtil.initAuthorityRes("centerTagToolbar");
	
});

function formatLevel(val, row) {
	if (row.TAG_LEVEL == "01"&&row.HASCHILD>0) {
		return "<font><b>"+val+"</b></font>";
	} else if (row.TAG_LEVEL == "02") {
		return "　　"+val;
	}else{
		return val;
	}
};
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
					  <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;标签类别树</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="tagTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="centerTagToolbar">
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_t.TYPE_ID_=" value="" />
		      <input type="hidden" name="TYPE_ID"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_Tag" iconCls="icon-note-add"
								plain="true" onclick="openCatalogWindow();">新建标签</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_Tag" iconCls="icon-note-edit"
								plain="true" onclick="editcenterTagGridRecord();">编辑标签</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_Tag" iconCls="icon-note-delete" plain="true"
								onclick="deleteCatalogDataGridRecord();">删除标签</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="centerTagForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr>
						<td style="width:68px;text-align:right;">标签编码</td>
						<td style="width:135px;" ><input class="eve-input" type="text"
							 name="Q_t.TAG_KEY_LIKE" style="width:128px;"></td>
						<td style="width:68px;text-align:right;">标签名称</td>
						<td style="width:135px;" ><input class="eve-input" type="text"
							 name="Q_t.TAG_NAME_LIKE" style="width:128px;"></td>
						<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('centerTagToolbar','centerTagGrid')">
							<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
					</tr>
				</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="centerTagGrid" fitColumns="true" toolbar="#centerTagToolbar" 
			method="post" idField="TAG_ID" checkOnSelect="true"
			selectOnCheck="true" fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var typeId = $('#centerTagToolbar input[name=TYPE_ID]').val();
			     if(typeId&&typeId!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="tagController.do?tagData">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TAG_ID',hidden:true" width="80">TAG_ID</th>
					<th data-options="field:'HASCHILD',hidden:true" width="80">HASCHILD</th>
					<th data-options="field:'ISUSE',hidden:true" width="80">ISUSE</th>
					<th data-options="field:'TAG_NAME',align:'left'" width="100" formatter="formatLevel" >标签名称</th>
					<th data-options="field:'TAG_KEY',align:'left'" width="100">标签编码</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="100" >所属类别</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




