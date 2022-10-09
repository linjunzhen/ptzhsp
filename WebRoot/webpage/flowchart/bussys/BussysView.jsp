<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addDepartmentTreeHoverDom(treeId, treeNode) {
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
			showDepartmentWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showDepartmentWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "busSysTree",
				url : "departmentController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeDepartmentTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onBussysTreeDrop(event, treeId, treeNodes, targetNode,
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
					"tableName" : "T_LCJC_SYSTEM_BUSUNIT"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						/* $.fn.zTree.getZTreeObj("busSysTree").reAsyncChildNodes(
								null, "refresh"); */
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						/* $.fn.zTree.getZTreeObj("busSysTree").reAsyncChildNodes(
								null, "refresh"); */
					}
				}
			});
		}
		
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onBussysTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#busSys_path").val(treeNode.DEPART_ID);
			AppUtil.gridDoSearch('busSysToolbar','busSysGrid');
		}
	}
	/**
	 * 删除处室记录
	 */
	function deleteUnitGridRecord() {
		AppUtil.deleteDataGridRecord("busSysController.do?multiDel",
				"busSysGrid");
	}
	/**
	 * 编辑处室列表记录
	 */
	function editUnitGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("busSysGrid");
		if (entityId) {
			showUnitWindow(entityId);
		}
	}

	/**
	 * 显示处室信息对话框
	 */
	function showUnitWindow(entityId) {
		if(entityId){
			$.dialog.open("busSysController.do?info&entityId=" + entityId, {
	    		title : "业务系统信息",
	            width:"450px",
	            lock: true,
	            resize:false,
	            height:"250px",
	    	}, false);
		}else{
			var parentId = $("#busSys_path").val();
			if (parentId){
				var url = "busSysController.do?info&entityId="+entityId+"&parentId="+parentId;
				$.dialog.open(url, {
		    		title : "业务系统信息",
		            width:"450px",
		            lock: true,
		            resize:false,
		            height:"250px",
		    	}, false);
			}else{
				alert('请选择所属单位!');
			}			
		}		
	}

	$(document).ready(function() {
		var busSysTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			/*view : {
				addHoverDom : addDepartmentTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeDepartmentTreeHoverDom
			},*/
			callback : {
				onClick : onBussysTreeClick,
				onDrop : onBussysTreeDrop
			},
			async : {
				enable : true,
				url : "departmentController.do?tree",
				otherParam : {
					"tableName" : "t_msjw_system_department",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"Q_STATUS_!=":0,
					"isShowRoot" : "true",
					"rootName" : "业务单位树"
				}
			}
		};
		$.fn.zTree.init($("#busSysTree"), busSysTreeSetting);
		//AppUtil.initAuthorityRes("TreeSysUserToolbar");
	});
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;业务单位树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="busSysTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="busSysToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_A.PATH_LIKE" id="busSys_path">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_BusSys"
								iconcls="icon-note-add" plain="true"
								onclick="showUnitWindow();">新建系统</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="EDIT_BusSys"
								iconcls="icon-note-edit" plain="true"
								onclick="editUnitGridRecord();">编辑系统</a> 
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_BusSys"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteUnitGridRecord();">删除系统</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="busSysForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">系统名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SYSTEM_NAME_LIKE" /></td>
							<td colspan="6">
							<input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('busSysToolbar','busSysGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('busSysForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" id="busSysGrid" 
		  	fitcolumns="true" toolbar="#busSysToolbar" method="post" 
		  	data-options="pageSize:10,pageList:[10,20,30,40,50]" nowrap="false"
		  	idfield="SYSTEM_ID" selectoncheck="false" singleSelect="true" checkonselect="true"
		  	fit="true" border="false" url="busSysController.do?datagrid"> 
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SYSTEM_ID',hidden:true" width="80">SYSTEM_ID</th>
					<th data-options="field:'SYSTEM_CODE',align:'left'" width="100">系统编码</th>
					<th data-options="field:'SYSTEM_NAME',align:'left'" width="250">系统名称</th>
					<th data-options="field:'UNIT_NAME',align:'left'" width="150">系统开发商</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="150">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>	
</div>



