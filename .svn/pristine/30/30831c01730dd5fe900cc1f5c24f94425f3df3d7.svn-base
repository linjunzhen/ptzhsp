<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
function dialogalert(msg,flag){
	art.dialog({
		content : msg,
		icon : flag==null?"warning":flag,
		ok : true
	});
}

/**
 * 更新datagird拖拽排序结果
 */
function updatematrixSn(){
	var rows = $("#bespeakTimeGrid").datagrid("getRows");
	var configIds = [];
	for(var i = 0; i < rows.length; i++){
		configIds.push(rows[i].ID);
	}
	if(configIds.length > 0){
		AppUtil.ajaxProgress({
			url: "bespeakOnlineController.do?updateSn",
			params:{
				configIds: configIds
			},
			callback:function(resultJson){
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
				$("#bespeakTimeGrid").datagrid("reload");
			}
		});
	}

}
	function editBespeakTime(){
		var id = AppUtil.getEditDataGridRecord("bespeakTimeGrid");
		if (id) {
			$.dialog.open("bespeakOnlineController.do?goInfoForTime&ID=" + id, {
				title : "编辑时段配置信息",
				width : "500px",
				height : "250px",
				lock : true,
				resize : false
			}, false);
		}
	}
	function addBespeakTime(){
		var onLineId = $("#ONLINE_ID").val();
		if(onLineId){
			$.dialog.open("bespeakOnlineController.do?goInfoForTime&onLineId="+onLineId, {
				title : "新增时段配置信息",
				width : "500px",
				height : "250px",
				lock : true,
				resize : false
			}, false);
		}else{
			dialogalert("请选择需要配置的部门");
		}
	}
	function delBespeakTime(){
		AppUtil.deleteDataGridRecord("bespeakOnlineController.do?doDelForTime","bespeakTimeGrid");
	}
	/**
	 * 显示对接配置信息对话框
	 */
	 function showBespeakDepartmentWindow(treeNode, isAdd){
		var departId = "";
		if (!isAdd) {
			departId = treeNode.departId;
		} 
		parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&checkCascadeN=", {
			title : "组织机构选择器",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"500px",
	        close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					saveSelectDept(selectDepInfo.departIds,selectDepInfo.departNames,treeNode, isAdd);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
	function saveSelectDept(departIds,departNames,treeNode, isAdd){
		var formData = "";
		if (isAdd) {
			var id = treeNode.id;
			formData = "DEPART_ID="+departIds+"&DEPART_NAME="+departNames+"&PARENT_ID="+id;
		} else {//编辑
			var id = treeNode.id;
			var parentNode = treeNode.getParentNode();
			var parentId = parentNode.id;
			formData = "DEPART_ID="+departIds+"&DEPART_NAME="+departNames+"&PARENT_ID="+parentId+"&ID="+id;
		} 
		
		AppUtil.ajaxProgress({
			url : "bespeakOnlineController.do?saveOrUpdate",
			params : formData,
			callback : function(resultJson) {
				if (resultJson.success) {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					$.fn.zTree.getZTreeObj("bespeakDepartmentTree").reAsyncChildNodes(null, "refresh");
					AppUtil.closeLayer();
				} else {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
			}
		});
	}
	
	//添加树形hover工具按钮
	function addBespeakDepartmentTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子机构' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showBespeakDepartmentWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showBespeakDepartmentWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "bespeakDepartmentTree",
				url : "bespeakOnlineController.do?doDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	//移除树形hover工具按钮
	function removeBespeakDepartmentTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	//树形节点拖放排序
	function onBespeakDepartmentTreeDrop(event, treeId, treeNodes, targetNode,
			moveType, isCopy) {
		if(moveType!=null){
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"moveType":moveType,
					"targetNodeId" : targetNode.id,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "T_BESPEAK_ONLINE_CONFIG"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						dialogalert("成功完成拖拽排序!");
					} else {
						dialogalert(resultJson.msg);
					}
				}
			});
		}
	}
	//点击树形节点触发的事件
	function onBespeakDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			//$("#bespeakTimeToolbar input[name='DEPART_ID']").val(treeNode.id);
			//$("#bespeakTimeToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
			//AppUtil.gridDoSearch("bespeakTimeToolbar", "bespeakTimeGrid");
			if(treeNode.parentId==0){
				$("#ONLINE_ID").val(treeNode.id);
				$("#departNamediv").html("_"+treeNode.name);
				AppUtil.gridDoSearch("bespeakTimeToolbar", "bespeakTimeGrid");
			}else{
				dialogalert("仅一级部门可提供时段配置");
			}
		}
	}

	$(document).ready(function() {
		var bespeakDepartmentTreeSetting = {
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			view : {
				addHoverDom: addBespeakDepartmentTreeHoverDom,
				selectedMulti: false,
				removeHoverDom : removeBespeakDepartmentTreeHoverDom
			},
			callback : {
				onClick : onBespeakDepartmentTreeClick,
				onDrop: onBespeakDepartmentTreeDrop,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
				}
			},
			async : {
				enable : true,
				url : "bespeakOnlineController.do?tree",
				otherParam : {
					"tableName" : "T_BESPEAK_ONLINE_CONFIG",
					"idAndNameCol" : "ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"Q_STATUS_!=":0,
					"isShowRoot" : "true",
					"rootName" : "预约部门树"
				}
			}
		};
		$.fn.zTree.init($("#bespeakDepartmentTree"), bespeakDepartmentTreeSetting);

	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false"
		style="width:350px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;预约部门树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="bespeakDepartmentTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
			
		<div id="bespeakTimeToolbar">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;预约时段配置<a id="departNamediv"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" id="ONLINE_ID" name="Q_T.ONLINE_ID_EQ"/>
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_BespeakTime"
								iconCls="l-btn-left icon-note-add" plain="true"
								onclick="addBespeakTime();">新增</a>
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_BespeakTime"
								iconCls="l-btn-left icon-note-edit" plain="true"
								onclick="editBespeakTime();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_BespeakTime"
								iconCls="l-btn-icon icon-note-delete" plain="true"
								onclick="delBespeakTime();">删除</a> 
							<a href="#" class="easyui-linkbutton"
										resKey="UPDATESN_BespeakTime" iconCls="icon-tick"
										plain="true" onclick="updatematrixSn();">保存排序</a>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="50"
			id="bespeakTimeGrid" fitColumns="true" toolbar="#bespeakTimeToolbar"
			method="post" idField="ID" checkonselect="true" selectoncheck="true" 
			 fit="true" border="false"
			  data-options="onLoadSuccess:function(){
				       $(this).datagrid('enableDnd');
			   }"
			url="bespeakOnlineController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true" width="80">ID</th>
					<th data-options="field:'BEGIN_TIME',align:'left'" width="100">开始时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="100">结束时间</th>
					<th data-options="field:'ALLOW_BESPEAK_NUMBER',align:'left'" width="100">该时段可预约数</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



