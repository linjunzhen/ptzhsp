<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 添加树形hover工具按钮
 */
function addValidteFormTreeHoverDom(treeId, treeNode) {
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
		showValidteRuleWindow(treeNode,true);
	});
	$("#editItemHref_" + treeNode.id).bind("click", function() {
		showValidteRuleWindow(treeNode,false);
	});
	$("#delItemHref_" + treeNode.id).bind("click", function() {
		AppUtil.deleteTreeNode({
			treeId:"validteFormTree",
			url:"formValidateController.do?multiDelTree",
			noAllowDeleteIfHasChild:true,
			treeNode:treeNode
		});
	});
}
/**
 * 移除树形hover工具按钮
 */
function removeValidteFormTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 树形节点拖放排序
 */
function onValidteFormTreeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType!=null){
		AppUtil.ajaxProgress({
		   url:"baseController.do?updateTreeSn",
		   params:{
			   "dragTreeNodeId":treeNodes[0].id,
			   "dragTreeNodeNewLevel":treeNodes[0].level,
			   "targetNodeId":targetNode.id,
			   "moveType":moveType,
			   "targetNodeLevel":targetNode.level,
			   "tableName":"T_MSJW_SYSTEM_VALIDATE_FORM"
		   },
		   callback:function(resultJson){
				if(resultJson.success){
					/* $.fn.zTree.getZTreeObj("dicTypeTree").reAsyncChildNodes(null, "refresh"); */
	    	   	    alert("成功完成拖拽排序!");
				}else{
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("validteFormTree").reAsyncChildNodes(null, "refresh");
				}
		   	    
		   }
		});
	}
	
}
/**
 * 点击树形节点触发的事件
 */
function onValidteFormTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		if(treeNode.id=="0"){
			$("#validateToolbar input[name='FORM_ID']").val("");
			$("#validateToolbar input[name='Q_T.FORM_ID_=']").val("");
		}else{
			$("#validateToolbar input[name='FORM_ID']").val(treeNode.id);
			$("#validateToolbar input[name='Q_T.FORM_ID_=']").val(treeNode.id);
		}
		
		AppUtil.gridDoSearch("validateToolbar","validateGrid");
	}
}

/**
 * 弹出表单信息窗口
 */
function showValidteRuleWindow(treeNode,isAdd) {
	var url = "";
	if(isAdd){
		url = "formValidateController.do?infoTree&PARENT_ID="+treeNode.id+"&PARENT_NAME="+treeNode.name;
	}else{
		var parentNode = treeNode.getParentNode();
		url = "formValidateController.do?infoTree&PARENT_ID="+parentNode.id+"&PARENT_NAME="+parentNode.name+"&entityId="+treeNode.id;
	}
	$.dialog.open(url, {
		title : "表单信息",
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
	AppUtil.gridSearchReset("ValidateForm");
}

/**
 * 删除验证列表记录
 */
function deleteDictionaryDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"formValidateController.do?multiDel", "validateGrid");
};

function updateDicSn(){
	var typeId = $("#validateToolbar input[name='FORM_ID']").val();
	if(typeId&&typeId!="0"){
		var rows = $("#validateGrid").datagrid("getRows"); 
		var ruleIds = [];
		for(var i=0;i<rows.length;i++){
			ruleIds.push(rows[i].RULE_ID);
		}
		if(ruleIds.length>0){
			AppUtil.ajaxProgress({
				url:"formValidateController.do?updateSn",
				params:{
					ruleIds:ruleIds
				},
				callback:function(resultJson){
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					$("#validateGrid").datagrid("reload");
				}
			})
		}
	}else{
		art.dialog({
			content: "请先选择表单!",
			icon:"warning",
		    ok: true
		});

	}
	
}
/**
 * 编辑验证信息列表记录
 */
function editValidateGridRecord(){
	var RULE_ID = AppUtil.getEditDataGridRecord("validateGrid");
	if(RULE_ID){
		showRuleWindow(RULE_ID);
	}
}

/**
 * 显示验证信息对话框
 */
function showRuleWindow(entityId) {
	if(entityId){
		$.dialog.open("formValidateController.do?info&entityId="+entityId, {
			title : "验证信息",
	        width:"700px",
	        lock: true,
	        resize:false,
	        height:"500px",
		}, false);
	}else{
		var typeId = $("#validateToolbar input[name='FORM_ID']").val();
		if(typeId&&typeId!="0"){
			var treeObj = $.fn.zTree.getZTreeObj("validteFormTree");
			var treeNode = treeObj.getNodesByParam("id",typeId, null)[0];
			var isParent = treeNode.isParent;
			if(isParent){
				layer.alert("不能选择父栏目，请重新选择!");
				return;
			}else{
				$.dialog.open("formValidateController.do?info&FORM_ID="+treeNode.id+"&FORM_NAME="+treeNode.name, {
					title : "验证信息",
			        width:"700px",
			        lock: true,
			        resize:false,
			        height:"500px",
				}, false);
			}
		}else{
			art.dialog({
				content: "请先选择表单!",
				icon:"warning",
			    ok: true
			});
		}
	}
	
};


$(document).ready(function(){
	var ValidteFormTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addValidteFormTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeValidteFormTreeHoverDom
		},
		callback : {
			onClick : onValidteFormTreeClick,
			onDrop: onValidteFormTreeDrop
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_MSJW_SYSTEM_VALIDATE_FORM",
				"idAndNameCol" : "FORM_ID,FORM_NAME",
				"targetCols" : "PATH,FORM_CODE,PARENT_ID",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"rootName":"配置表单树"
			}
		}
	};
	$.fn.zTree.init($("#validteFormTree"), ValidteFormTreeSetting);
	//AppUtil.initAuthorityRes("validateToolbar");
});
</script>
<div class="easyui-layout" fit="true">
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
		<ul id="validteFormTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="validateToolbar">
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_T.FORM_ID_=" value="" />
		      <input type="hidden" name="FORM_ID"  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_Validate" iconCls="icon-note-add"
								plain="true" onclick="showRuleWindow();">新建</a>
							<a href="#" class="easyui-linkbutton" resKey="EDIT_Validate" iconCls="icon-note-edit"
								plain="true" onclick="editValidateGridRecord();">编辑</a>
							<a href="#" class="easyui-linkbutton" resKey="DEL_Validate" iconCls="icon-note-delete" plain="true"
								onclick="deleteDictionaryDataGridRecord();">删除</a>
							<a href="#" class="easyui-linkbutton" resKey="SAVEDICSN_Dictionary" iconCls="icon-tick" plain="true"
								onclick="updateDicSn();">保存排序</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ValidateForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:68px;text-align:right;">验证编码</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_CODE_LIKE" style="width:128px;"></td>
					<td style="width:68px;text-align:right;">验证名称</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_D.COLUMN_NAME_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('validateToolbar','validateGrid')">
						<input type="button" value="重置" class="eve-button" onclick="resetSearch();"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="validateGrid" fitColumns="true" toolbar="#validateToolbar"
			method="post" idField="RULE_ID" checkOnSelect="false"
			selectOnCheck="false" fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var typeId = $('#validateToolbar input[name=FORM_ID]').val();
			     if(typeId&&typeId!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="formValidateController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RULE_ID',hidden:true" width="80">RULE_ID</th>
					<th data-options="field:'COLUMN_CODE',align:'left'" width="100">验证编码</th>
					<th data-options="field:'COLUMN_NAME',align:'left'" width="100">验证名称</th>
					<th data-options="field:'RULES',align:'left'" width="100">验证规则</th>
					<th data-options="field:'RULE_SN',align:'left'" width="100">字典排序值</th>
					<th data-options="field:'FORM_NAME',align:'left'" width="100" >所属表单</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




