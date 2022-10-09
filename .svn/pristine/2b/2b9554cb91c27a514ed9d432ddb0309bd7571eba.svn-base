<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addDicTypeTreeHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addItemHref_"+treeNode.id).length>0) return;
	if ($("#editItemHref_"+treeNode.id).length>0) return;
	if ($("#delItemHref_"+treeNode.id).length>0) return;
	var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建栏目' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
	if(treeNode.id!="0"){
		operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
		operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
	}
	aObj.append(operateStr);
	$("#addItemHref_" + treeNode.id).bind("click", function() {
		showDicTypeWindow(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		showDicTypeWindow(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"moduleTree",
			url:"moduleController.do?multiDel",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function removeDicTypeTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
	 * 树形节点拖放排序
	 */
	function onMoudleTreeDrop(event, treeId, treeNodes, targetNode, moveType,
			isCopy) {
		if (moveType != null) {
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"moveType" : moveType,
					"targetNodeId" : targetNode.id,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "T_CMS_ARTICLE_MODULE"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("busTypeTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("moduleTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
/**
 * 点击树形节点触发的事件
 */
function onMoudleTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"&&!treeNode.isParent){
		if(treeNode.id=="0"){
			$("#contentToolbar input[name='MODULE_ID']").val("");
			$("#contentToolbar input[name='Q_T.MODULE_ID_=']").val("");
		}else{
			$("#contentToolbar input[name='MODULE_ID']").val(treeNode.id);
			$("#contentToolbar input[name='Q_T.MODULE_ID_=']").val(treeNode.id);
		}
		
		AppUtil.gridDoSearch("contentToolbar","contentGrid");
	}else if(treeNode.id=="0"){
		$("#contentToolbar input[name='MODULE_ID']").val("");
		$("#contentToolbar input[name='Q_T.MODULE_ID_=']").val("");
		AppUtil.gridDoSearch("contentToolbar","contentGrid");
	}
}

/**
 * 弹出栏目窗口
 */
function showDicTypeWindow(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "moduleController.do?info&PARENT_ID="+treeNode.id+"&PARENT_NAME="+encodeURIComponent(treeNode.name);
	}else{
		var parentNode = treeNode.getParentNode();
		url = "moduleController.do?info&PARENT_ID="+parentNode.id+"&PARENT_NAME="+encodeURIComponent(parentNode.name)+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "栏目信息",
        width:"600px",
        lock: true,
        resize:false,
        height:"390px",
	}, false);
};
/**
 * 重置查询条件
 */
function resetSearch(){
	AppUtil.gridSearchReset("moduleForm");
}

/**
 * 删除文章列表记录
 */
function deleteContentDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"contentController.do?multiDel", "contentGrid");
};

/**
 * 编辑文章列表记录
 */
function editContentGridRecord(){
	var TID = AppUtil.getEditDataGridRecord("contentGrid");
	if(TID){
		showContentWindow(TID);
	}
};
/**
 * 预览文章
 */
function previewContentDataGridRecord(){
	var TID = AppUtil.getEditDataGridRecord("contentGrid");
	var $dataGrid = $("#contentGrid");
	var rowsData = $dataGrid.datagrid("getChecked");
	window.open("contentController/view.do?contentId="+rowsData[0].TID, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
};
function auditContentDataGridRecord(status){
	var $dataGrid = $("#contentGrid");
	var rowsData = $dataGrid.datagrid("getChecked");
	var colName = $dataGrid.datagrid("options").idField; 
	var selectColNames = "";
	for ( var i = 0; i < rowsData.length; i++) {
		if(i>0){
			selectColNames+=",";
		}
		selectColNames+=eval('rowsData[i].'+colName);
	}
	if(selectColNames==""){
		art.dialog({
			content: "请选择需要被操作的记录!",
			icon:"warning",
			ok: true
		});
	}else{
		var layload = layer.load('正在提交请求中…');
		$.post("auditContentController.do?multiAudit",{
		   selectColNames:selectColNames,
		   status:status
		}, function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success == true){
				art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				listSearch();
			}else{
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		});
	}
}
/**
 * 发布文章
 */
function publishContentDataGridRecord(status){
	var $dataGrid = $("#contentGrid");
	var rowsData = $dataGrid.datagrid("getChecked");
	var colName = $dataGrid.datagrid("options").idField; 
	var selectColNames = "";
	for ( var i = 0; i < rowsData.length; i++) {
		if(i>0){
			selectColNames+=",";
		}
		selectColNames+=eval('rowsData[i].'+colName);
	}
	if(selectColNames==""){
		art.dialog({
			content: "请选择需要被操作的记录!",
			icon:"warning",
			ok: true
		});
	}else{
		var layload = layer.load('正在提交请求中…');
		$.post("contentController.do?multiPublish",{
		   selectColNames:selectColNames,
		   status:status
		}, function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success == true){
				art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				listSearch();
			}else{
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		});
	}
};
/**
 * 取消发布文章
 */
function noPublishContentDataGridRecord(status,id){
	var layload = layer.load('正在提交请求中…');
	$.post("contentController.do?multiPublish",{
	   selectColNames:id,
	   status:status
	}, function(responseText, status, xhr) {
		layer.close(layload);
		var resultJson = $.parseJSON(responseText);
		if(resultJson.success == true){
			art.dialog({
				content: resultJson.msg,
				icon:"succeed",
				time:3,
				ok: true
			});
			listSearch();
		}else{
			art.dialog({
				content: resultJson.msg,
				icon:"error",
				ok: true
			});
		}
	});
};
/**
 * 显示文章信息对话框
 */
function showContentWindow(entityId) {
	if(entityId){
		window.open("contentController.do?info&entityId="+entityId, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
	}else{
		var typeId = $("#contentToolbar input[name='MODULE_ID']").val();
		if(typeId&&typeId!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("moduleTree");
			var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
			window.open("contentController.do?info&MODULE_ID="+treeNode.id+"&MODULE_NAME="+encodeURIComponent(treeNode.name), "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
		}else{
			art.dialog({
				content: "请先选择栏目!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var dicTypeTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addDicTypeTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeDicTypeTreeHoverDom
		},
		callback : {
			onClick : onMoudleTreeClick,
			onDrop: onMoudleTreeDrop
		},
		async : {
			enable : true,
			url : "moduleController.do?tree",
			otherParam : {
				"tableName" : "T_CMS_ARTICLE_MODULE",
				"idAndNameCol" : "MODULE_ID,MODULE_NAME",
				"targetCols" : "PARENT_ID,PATH",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"rootName" : "栏目类别树"
			}
		}
	};
	$.fn.zTree.init($("#moduleTree"), dicTypeTreeSetting);
	AppUtil.initAuthorityRes("contentToolbar");
	
});
	function formatContentStatus(val,row){
		if(val=="1"){
			return '<font color="blue">已发布&nbsp;&nbsp;<img title="不发布" src="plug-in/easyui-1.4/themes/icons/cancel.png" onclick="noPublishContentDataGridRecord(0,'+row.TID+')" style="cursor:pointer;width: 12px; height: 12px;" /></font>';
		}else if(val=="0"){
			return '<font color="red">待发布</font>';
		}else if(val=="-1"){
			return '<font color="red">待审核</font>';
		}else if(val=="-2"){
			return '<font color="red">回退&nbsp;&nbsp;<img title="查看回退信息" src="plug-in/easyui-1.4/themes/icons/folder_table.png" onclick="showAuditInfoWindow('+row.TID+')" style="cursor:pointer;width: 12px; height: 12px;" /></font>';
		} 
	};
	function formatAuditStatus(val,row){
		if(val=="0"){
			return "信息采编";
		}else if(val=="1"){
			return "处室领导审核";
		}else if(val=="2"){
			return "中心主任审核";
		}else if(val=="3"){
			return "信息发布";
		}else if(val=="4"){
			return "结束";
		}else{
			return "";
		}
	};
	function formatIsTop(val,row){
		if(val=="1"){
			return "<font color='red'>是</font>";
		}else if(val=="0"){
			return "<font color='green'>否</font>";
		}
	};
	function infoListSearch(resultJson){
		if(resultJson.success){			
			art.dialog({
				content: resultJson.msg,
				icon:"succeed",
				time:3,
				ok: true
			});			
		}
		AppUtil.gridDoSearch('contentToolbar','contentGrid');
	}
	function listSearch(){
		AppUtil.gridDoSearch('contentToolbar','contentGrid');
	};
	/**
	 * 剪切文章
	 */
	function pasteContentDataGridRecord(){
		var $dataGrid = $("#contentGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var colName = $dataGrid.datagrid("options").idField; 
		var selectColNames = "";
		var moduleIds = $("input[name='MODULEIDS']").val();
		for ( var i = 0; i < rowsData.length; i++) {
			if(i>0){
				selectColNames+=",";
			}
			selectColNames+=eval('rowsData[i].'+colName);
		}
		if(selectColNames==""){
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
				ok: true
			});
		}else if(moduleIds==""){
			$.dialog.open("moduleController/selector.do?moduleIds="+moduleIds+"&allowCount=1&checkCascadeY=&"
					+"checkCascadeN=", {
				title : "栏目选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectModuleInfo = art.dialog.data("selectModuleInfo");
					if(selectModuleInfo){
						$("input[name='MODULEIDS']").val(selectModuleInfo.moduleIds);
						art.dialog.removeData("selectModuleInfo");
						pasteContentDataGridRecord();
					}
				}
			}, false);
		}else{
			var layload = layer.load('正在提交请求中…');
			$.post("contentController.do?paste",{
			   selectColNames:selectColNames,
			   moduleId:moduleIds
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					$("input[name='MODULEIDS']").val("");
					listSearch();
				}else{
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			});
		}
	};
	
	/**
	 * 复制文章
	 */
	function multicopyContentDataGridRecord(){
		var $dataGrid = $("#contentGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var colName = $dataGrid.datagrid("options").idField; 
		var selectColNames = "";
		var moduleIds = $("input[name='MODULEIDS']").val();
		for ( var i = 0; i < rowsData.length; i++) {
			if(i>0){
				selectColNames+=",";
			}
			selectColNames+=eval('rowsData[i].'+colName);
		}
		if(selectColNames==""){
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
				ok: true
			});
		}else if(moduleIds==""){
			$.dialog.open("moduleController/selector.do?moduleIds="+moduleIds+"&allowCount=1&checkCascadeY=&"
					+"checkCascadeN=", {
				title : "栏目选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectModuleInfo = art.dialog.data("selectModuleInfo");
					if(selectModuleInfo){
						$("input[name='MODULEIDS']").val(selectModuleInfo.moduleIds);
						art.dialog.removeData("selectModuleInfo");
						multicopyContentDataGridRecord();
					}
				}
			}, false);
		}else{
			var layload = layer.load('正在提交请求中…');
			$.post("contentController.do?multicopy",{
			   selectColNames:selectColNames,
			   selectmoduleIds:moduleIds
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					$("input[name='MODULEIDS']").val("");
					listSearch();
				}else{
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			});
		}
	};
	/**
	 * 显示审核文章对话框
	 */
	function showAuditInfoWindow(contentId) {
		$.dialog.open("auditContentController.do?showInfo&contentId=" + contentId, {
			title : "审核信息",
			width : "800px",
			height : "460px",
			lock : true,
			resize : false
		}, false);
	};
	/*
	 * 文章编辑回调父类方法
	 */
	function infoListSearch(resultJson){
	    if(resultJson.success){
	    	art.dialog({
	    		content:resultJson.msg,
	    		icon:"succeed",
	    		time:3,
				ok: true
	    	});
	    }
	    AppUtil.gridDoSearch('contentToolbar','contentGrid');
	}
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						  <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;栏目类别树</div>
						</div>
					</div>
				</div>
		</div>
		<ul id="moduleTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="contentToolbar">
		    <%--====================开始编写隐藏域============== --%>
		    <input type="hidden" name="Q_T.MODULE_ID_=" value="" />
		    <input type="hidden" name="MODULE_ID"  />
			<input type="hidden" name="MODULEIDS"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_CONTENT" iconCls="eicon-plus"
								plain="true" onclick="showContentWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_CONTENT" iconCls="eicon-pencil"
								plain="true" onclick="editContentGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_CONTENT" iconCls="eicon-trash-o" plain="true"
								onclick="deleteContentDataGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" resKey="AUDIT_TO_CONTENT" iconCls="eicon-send-o" plain="true"
								onclick="auditContentDataGridRecord(1);">发送审核</a>	
							<a href="#" class="easyui-linkbutton" resKey="PREVIEW_CONTENT" iconCls="eicon-eye" plain="true"
								onclick="previewContentDataGridRecord();">预览</a>									
							<!--<a href="#" class="easyui-linkbutton" resKey="PASTR_CONTENT" iconCls="icon-cut" plain="true"
								onclick="pasteContentDataGridRecord();">剪切</a>				
							<a href="#" class="easyui-linkbutton" resKey="MULTICOPY_CONTENT" iconCls="icon-arrow-branch" plain="true"
								onclick="multicopyContentDataGridRecord();">复制</a>-->	
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="moduleForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:68px;text-align:right;">文章标题：</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_T.CONTENT_TITLE_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="listSearch();">
						<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="contentGrid" fitColumns="true" toolbar="#contentToolbar"
			method="post" idField="TID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true"  fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var TID = $('#contentToolbar input[name=TID]').val();
			     if(TID&&TID!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="moduleController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TID',hidden:true">文章编号</th>
					<th data-options="field:'CONTENT_ID',hidden:true">文章编号</th>
					<th data-options="field:'CONTENT_TITLE',align:'left'" width="41%">文章标题</th>
					<th data-options="field:'CONTENT_STATUS',align:'left'" width="10%" formatter="formatContentStatus">状态</th>
					<th data-options="field:'RELEASE_TIME',align:'left'" width="16%">发布时间</th>
					<th data-options="field:'AUTHOR',align:'left'" width="10%">责任编辑</th>
					<th data-options="field:'AUDIT_STATUS',align:'left'" width="10%" formatter="formatAuditStatus">审核环节</th>
					<th data-options="field:'ISTOP',align:'left'" width="8%"formatter="formatIsTop">置顶</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




