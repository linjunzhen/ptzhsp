<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    
    function reloadFlowDefGrid(){
    	AppUtil.gridDoSearch("flowDefToolbar","flowDefGrid");
    }
	/**
	 * 添加树形hover工具按钮
	 */
	function addFlowTypeTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "";
		if(treeNode.id=="0"){
			operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		}
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showFlowTypeWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showFlowTypeWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "flowTypeTree",
				url : "flowTypeController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeFlowTypeTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onFlowTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType,
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
					"tableName" : "JBPM6_FLOWTYPE"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("flowTypeTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("flowTypeTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onFlowTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if(event.target.tagName=="SPAN"){
			if(treeNode.id=="0"){
				$("#flowDefToolbar input[name='TYPE_ID']").val("");
				$("#flowDefToolbar input[name='Q_P.PATH_LIKE']").val("");
			}else{
				$("#flowDefToolbar input[name='TYPE_ID']").val(treeNode.id);
				$("#flowDefToolbar input[name='Q_P.PATH_LIKE']").val(treeNode.PATH);
			}
			
			AppUtil.gridDoSearch("flowDefToolbar","flowDefGrid");
		}
	}

	/**
	 * 弹出流程类别信息窗口
	 */
	function showFlowTypeWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "flowTypeController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name;
		} else {
			var parentNode = treeNode.getParentNode();
			url = "flowTypeController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id;
		}
		$.dialog.open(url, {
			title : "流程类别信息",
			width : "400px",
			height : "200px",
			lock : true,
			resize : false
		}, false);

	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		var TYPE_ID = $("#flowDefToolbar input[name='TYPE_ID']").val();
		var PATH = $("#flowDefToolbar input[name='Q_P.PATH_LIKE']").val();
		AppUtil.gridSearchReset("FlowDefForm");
		$("#flowDefToolbar input[name='TYPE_ID']").val(TYPE_ID);
		$("#flowDefToolbar input[name='Q_P.PATH_LIKE']").val(PATH);
	}

	/**
	 * 删除流程定义列表记录
	 */
	function deleteFlowDefDataGridRecord() {
		AppUtil.deleteDataGridRecord("flowDefController.do?multiDel",
				"flowDefGrid");
	};
	/**
	 * 编辑流程定义列表记录
	 */
	function editFlowDefGridRecord() {
		var DEF_ID = AppUtil.getEditDataGridRecord("flowDefGrid");
		if (DEF_ID) {
			showFlowDefWindow(DEF_ID);
		}
	}

	/**
	 * 显示流程定义信息对话框
	 */
	function showFlowDefWindow(entityId) {
		if (entityId) {
			window.open("flowDefController.do?godesign&DEF_ID="+entityId, "_blank");
		} else {
			var TYPE_ID = $("#flowDefToolbar input[name='TYPE_ID']").val();
			if (TYPE_ID && TYPE_ID != "0") {
				var treeObj = $.fn.zTree.getZTreeObj("flowTypeTree");
				var treeNode = treeObj.getNodesByParam("id", TYPE_ID, null)[0];
				window.open("flowDefController.do?godesign&TYPE_ID="+TYPE_ID+"&TYPE_NAME="+treeNode.name, "_blank");
			} else {
				art.dialog({
					content : "请先选择流程类别!",
					icon : "warning",
					ok : true
				});
			}
		}

	};
	
	/**
	 * 配置流程定义列表记录
	 */
	function configFlowDefGridRecord() {
		var DEF_ID = AppUtil.getEditDataGridRecord("flowDefGrid");
		if (DEF_ID) {
			window.open("flowDefController.do?flowDefConfig&defId="+DEF_ID, "_blank");
		}
	}
	
	/**
	 * 查看流程版本
	 */
	function queryFlowVersionGridRecord() {
		var DEF_ID = AppUtil.getEditDataGridRecord("flowDefGrid");
		if (DEF_ID) {
			$.dialog.open("flowDefController.do?versionManager&defId=" + DEF_ID, {
				title : "历史版本管理",
				width : "500px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		}
	}

	$(document).ready(function() {
		var flowTypeTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addFlowTypeTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeFlowTypeTreeHoverDom
			},
			callback : {
				onClick : onFlowTypeTreeClick,
				onDrop : onFlowTypeTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "JBPM6_FLOWTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "流程类别树"
				}
			}
		};
		$.fn.zTree.init($("#flowTypeTree"), flowTypeTreeSetting);
		//AppUtil.initAuthorityRes("flowDefToolbar");
	});
	/**
     * 配置流程映射记录
     */
    function configFlowMappedGridRecord() {
        var DEF_ID = AppUtil.getEditDataGridRecord("flowDefGrid");
        if (DEF_ID) {
        	var version = $("#flowDefGrid").datagrid("getChecked")[0].VERSION;
            $.dialog.open("flowMappedController.do?flowMappedConfig&defId="+DEF_ID+"&version="+version, {
                title : "流程映射管理",
                width : "800px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
        }
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;流程类别树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="flowTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="flowDefToolbar">
			<!--====================开始编写隐藏域============== -->
			   <input type="hidden" name="TYPE_ID"  />
		      <input type="hidden" name="Q_P.PATH_LIKE"  />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_FlowDef"
								iconCls="eicon-plus" plain="true"
								onclick="showFlowDefWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_FlowDef"
								iconCls="eicon-pencil" plain="true"
								onclick="editFlowDefGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_FlowDef"
								iconCls="eicon-trash-o" plain="true"
								onclick="deleteFlowDefDataGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_FlowDef"
								iconCls="eicon-edit" plain="true"
								onclick="configFlowDefGridRecord();">配置流程定义</a>
							<a href="#"
								class="easyui-linkbutton" 
								iconCls="eicon-paperclip" plain="true"
								onclick="queryFlowVersionGridRecord();">历史版本</a>
							<a href="#"
                                class="easyui-linkbutton" 
                                iconCls="eicon-sitemap" plain="true"
                                onclick="configFlowMappedGridRecord();">配置流程映射</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="FlowDefForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">定义名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.DEF_NAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">定义KEY</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.DEF_KEY_LIKE" /></td>
						<td colspan="6"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('flowDefToolbar','flowDefGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('FlowDefForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="flowDefGrid" fitColumns="true" toolbar="#flowDefToolbar"
			method="post" idField="DEF_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true" fit="true" border="false" 
			url="flowDefController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'DEF_ID',hidden:true">DEF_ID</th>
					<th data-options="field:'DEF_NAME',align:'left'" width="25%">定义名称</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="10%">类别名称</th>
					<th data-options="field:'DEF_KEY',align:'left'" width="15%">流程定义KEY</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="17%">创建时间</th>
					<th data-options="field:'FORM_NAME',align:'left'" width="20%">绑定表单</th>
					<th data-options="field:'VERSION',align:'left'" width="6%">版本号</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



