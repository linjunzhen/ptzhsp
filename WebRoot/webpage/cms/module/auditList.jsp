<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addAuditTreeHoverDom(treeId, treeNode) {
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
			treeId:"auditModuleTree",
			url:"moduleController.do?multiDel",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeAuditTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_"+treeNode.id).unbind().remove();
		$("#editItemHref_"+treeNode.id).unbind().remove();
		$("#delItemHref_"+treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onAuditMoudleTreeDrop(event, treeId, treeNodes, targetNode, moveType,
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
						$.fn.zTree.getZTreeObj("auditModuleTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onAuditMoudleTreeClick(event, treeId, treeNode, clickFlag) {
		if(event.target.tagName=="SPAN"&&!treeNode.isParent){
			if(treeNode.id=="0"){
				$("#auditContentToolbar input[name='MODULE_ID']").val("");
				$("#auditContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
			}else{
				$("#auditContentToolbar input[name='MODULE_ID']").val(treeNode.id);
				$("#auditContentToolbar input[name='Q_T.MODULE_ID_=']").val(treeNode.id);
			}
			
			AppUtil.gridDoSearch("auditContentToolbar","auditContentGrid");
		}else if(treeNode.id=="0"){
			$("#auditContentToolbar input[name='MODULE_ID']").val("");
			$("#auditContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
			AppUtil.gridDoSearch("auditContentToolbar","auditContentGrid");
		}
	}

	/**
	 * 重置查询条件
	 */
	function auditResetSearch(){
		AppUtil.gridSearchReset("moduleForm");
	}

	/**
	 * 删除文章列表记录
	 */
	function deleteAuditContentDataGridRecord() {
		AppUtil.deleteDataGridRecord(
				"contentController.do?multiDel", "auditContentGrid");
	};

	/**
	 * 预览文章
	 */
	function previewAuditContentDataGridRecord(){
		var TID = AppUtil.getEditDataGridRecord("auditContentGrid");
		var $dataGrid = $("#auditContentGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		window.open("contentController/view.do?contentId="+rowsData[0].TID, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
	};
	/**
	 * 审核文章
	 */
	function auditAuditContentDataGridRecord() {
		var contentId = AppUtil.getEditDataGridRecord("auditContentGrid");
		if (contentId) {
			showAuditWindow(contentId);
		}
	}
	/**
	 * 显示审核文章对话框
	 */
	function showAuditWindow(contentId) {
		$.dialog.open("auditContentController.do?info&contentId=" + contentId, {
			title : "审核信息",
			width : "800px",
			height : "460px",
			lock : true,
			resize : false
		}, false);
	};

$(document).ready(function(){
	var AuditTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			//addHoverDom: addAuditTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeAuditTreeHoverDom
		},
		callback : {
			onClick : onAuditMoudleTreeClick,
			onDrop: onAuditMoudleTreeDrop
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
	$.fn.zTree.init($("#auditModuleTree"), AuditTreeSetting);
	AppUtil.initAuthorityRes("auditContentToolbar");
	
});
	function formatContentStatus(val,row){
		if(val=="1"){
			return '<font color="blue">已发布&nbsp;&nbsp;<img title="不发布" src="plug-in/easyui-1.4/themes/icons/cancel.png" onclick="noPublishAuditContentDataGridRecord(0,'+row.TID+')" style="cursor:pointer;width: 12px; height: 12px;" /></font>';
		}else if(val=="0"){
			return '<font color="red">待发布</font>';
		}else if(val=="-1"){
			return '<font color="red">待审核</font>';
		}else if(val=="-2"){
			return '<font color="red">回退</font>';
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
	function infoauditListSearch(resultJson){
		if(resultJson.success){			
			art.dialog({
				content: resultJson.msg,
				icon:"succeed",
				time:3,
				ok: true
			});			
		}
		AppUtil.gridDoSearch('auditContentToolbar','auditContentGrid');
	}
	function auditListSearch(){
		AppUtil.gridDoSearch('auditContentToolbar','auditContentGrid');
	};
	
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
		<ul id="auditModuleTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="auditContentToolbar">
		    <%--====================开始编写隐藏域============== --%>
		    <input type="hidden" name="Q_T.MODULE_ID_=" value="" />
		    <input type="hidden" name="MODULE_ID"  />
			<input type="hidden" name="MODULEIDS"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="PREVIEW_CONTENT" iconCls="eicon-eye" plain="true"
								onclick="previewAuditContentDataGridRecord();">预览</a>	
							<a href="#" class="easyui-linkbutton" resKey="AUDIT_CONTENT" iconCls="eicon-check" plain="true"
								onclick="auditAuditContentDataGridRecord();">审核</a>
 <%--
							<a href="#" class="easyui-linkbutton" resKey="DEL_CONTENT" iconCls="icon-note-delete" plain="true"
								onclick="deleteAuditContentDataGridRecord();">删除文章</a>	
--%> 
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
					<td><input type="button" value="查询" class="eve-button" onclick="auditListSearch();">
						<input type="button" value="重置" class="eve-button" onclick="auditResetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="auditContentGrid" fitColumns="true" toolbar="#auditContentToolbar"
			method="post" idField="TID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true"  fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var TID = $('#auditContentToolbar input[name=TID]').val();
			     if(TID&&TID!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="moduleController.do?auditDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TID',hidden:true">文章编号</th>
					<th data-options="field:'CONTENT_ID',hidden:true">文章编号</th>
					<th data-options="field:'CONTENT_TITLE',align:'left'" width="40%">文章标题</th>
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




