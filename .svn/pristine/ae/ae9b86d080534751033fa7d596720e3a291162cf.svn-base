<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addBusTypeTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类别' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showBusTypeWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showBusTypeWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "busTypeTree",
				url : "busTypeController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeBusTypeTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onBusTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType,
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
					"tableName" : "T_WSBS_BUSTYPE"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						//$.fn.zTree.getZTreeObj("busTypeTree").reAsyncChildNodes(null, "refresh");
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("busTypeTree")
								.reAsyncChildNodes(null, "refresh");
					}

				}
			});
		}

	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onBusTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.id=="0"){
				$("#ServiceGuideToolbar input[name='TYPE_ID']").val("");
				$("#ServiceGuideToolbar input[name='Q_BUS.PATH_LIKE']").val("");
			}else{
				$("#ServiceGuideToolbar input[name='TYPE_ID']").val(treeNode.id);
				$("#ServiceGuideToolbar input[name='Q_BUS.PATH_LIKE']").val(treeNode.PATH);
			}
			AppUtil.gridDoSearch("ServiceGuideToolbar", "ServiceGuideGrid");
		}
	}

	/**
	 * 弹出业务类别信息窗口
	 */
	function showBusTypeWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "busTypeController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name;
		} else {
			var parentNode = treeNode.getParentNode();
			url = "busTypeController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id;
		}
		$.dialog.open(url, {
			title : "业务类别信息",
			width : "600px",
			height : "250px",
			lock : true,
			resize : false
		}, false);

	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		var TYPE_ID = $("#ServiceGuideToolbar input[name='TYPE_ID']").val();
		var PATH = $("#ServiceGuideToolbar input[name='Q_T.PATH_LIKE']").val();
		AppUtil.gridSearchReset("ServiceGuideForm");
		$("#ServiceGuideToolbar input[name='TYPE_ID']").val(TYPE_ID);
		$("#ServiceGuideToolbar input[name='Q_T.PATH_LIKE']").val(PATH);
	}

	/**
	 * 删除办事项目列表记录
	 */
	function deleteServiceGuideDataGridRecord() {
		AppUtil.deleteDataGridRecord("serviceGuideController.do?multiDel",
				"ServiceGuideGrid");
	};
	/**
	 * 编辑办事项目列表记录
	 */
	function editServiceGuideGridRecord() {
		var GUIDE_ID = AppUtil.getEditDataGridRecord("ServiceGuideGrid");
		if (GUIDE_ID) {
			showServiceGuideWindow(GUIDE_ID);
		}
	}
	
	/**
	**更新或者保存法律法规到办事指南
	**/
	function saveOrUpdateLawToGuide(lawIds,GUIDE_ID) {
		AppUtil.ajaxProgress({
			url : "lawReguController.do?grantLaws",
			params : {
				"lawIds" :lawIds,
				"guideId":GUIDE_ID
			},
			callback : function(resultJson) {
			    art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
			    
			}
		});
	}
	
	/**
	 * 配置法律法规
	 */
	function configLawRuleDataGrid() {
		var GUIDE_ID = AppUtil.getEditDataGridRecord("ServiceGuideGrid");
		if (GUIDE_ID) {
			$.post("lawReguController.do?lawIdAndTitle",{
				guideId:GUIDE_ID
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				var lawIds = resultJson.lawIds;
				var lawTitles = resultJson.lawTitles;
				$.dialog.open("lawReguController.do?selector&allowCount=0&lawIds="+lawIds+"&lawTitles="+lawTitles, {
					title : "法律法规选择器",
		            width:"600px",
		            lock: true,
		            resize:false,
		            height:"500px",
		            close: function () {
		    			var selectLawRuleInfo = art.dialog.data("selectLawRuleInfo");
		    			if(selectLawRuleInfo){
		    				saveOrUpdateLawToGuide(selectLawRuleInfo.lawIds,GUIDE_ID);
		    				art.dialog.removeData("selectLawRuleInfo");
		    			}
		    		}
		    	}, false);
			});
		}
	}

	/**
	 * 显示办事项目信息对话框
	 */
	function showServiceGuideWindow(entityId) {
		if (entityId) {
			$.dialog.open(
					"serviceGuideController.do?info&entityId=" + entityId, {
						title : "办事项目信息",
						width : "800px",
						height : "500px",
						lock : true,
						resize : false
					}, false);

		} else {
			var TYPE_ID = $("#ServiceGuideToolbar input[name='TYPE_ID']").val();
			if (TYPE_ID && TYPE_ID != "0") {
				var treeObj = $.fn.zTree.getZTreeObj("busTypeTree");
				var treeNode = treeObj.getNodesByParam("id", TYPE_ID, null)[0];
				$.dialog.open("serviceGuideController.do?info&TYPE_ID="
						+ treeNode.id + "&TYPE_NAME=" + treeNode.name, {
					title : "办事项目信息",
					width : "800px",
					height : "500px",
					lock : true,
					resize : false
				}, false);
			} else {
				art.dialog({
					content : "请先选择业务类别!",
					icon : "warning",
					ok : true
				});
			}
		}

	};

	$(document).ready(function() {
		var busTypeTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addBusTypeTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeBusTypeTreeHoverDom
			},
			callback : {
				onClick : onBusTypeTreeClick,
				onDrop : onBusTypeTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_WSBS_BUSTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "业务类别树"
				}
			}
		};
		$.fn.zTree.init($("#busTypeTree"), busTypeTreeSetting);
		AppUtil.initAuthorityRes("ServiceGuideToolbar");
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
								style="position: relative; top:7px; float:left;" />&nbsp;业务类别树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="busTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="ServiceGuideToolbar">
			<!--====================开始编写隐藏域============== -->
			 <input type="hidden" name="TYPE_ID"  />
	         <input type="hidden" name="Q_T.PATH_LIKE"  />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_ServiceGuide"
								iconCls="icon-note-add" plain="true"
								onclick="showServiceGuideWindow();">新建办事项目</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="EDIT_ServiceGuide"
								iconCls="icon-note-edit" plain="true"
								onclick="editServiceGuideGridRecord();">编辑办事项目</a> 
							<a href="#"
								class="easyui-linkbutton" resKey="DEL_ServiceGuide"
								iconCls="icon-note-delete" plain="true"
								onclick="deleteServiceGuideDataGridRecord();">删除办事项目</a>
							<a href="#"
								class="easyui-linkbutton" 
								iconCls="icon-script" plain="true"
								onclick="configLawRuleDataGrid();">配置法律法规</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServiceGuideForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">项目名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.PROJECT_NAME_LIKE" /></td>
						<td style="width:68px;text-align:right;">项目编码</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.PROJECT_CODE_LIKE" /></td>
						<td colspan="4"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('ServiceGuideToolbar','ServiceGuideGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('ServiceGuideForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ServiceGuideGrid" fitColumns="true"
			toolbar="#ServiceGuideToolbar" method="post" idField="GUIDE_ID"
			checkOnSelect="false" selectOnCheck="false" fit="true" border="false"
			url="serviceGuideController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'GUIDE_ID',hidden:true" width="80">GUIDE_ID</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="100">项目名称</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="100">所属业务类别</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="100">项目编码</th>
					<th data-options="field:'SERVER_OBJECTS',align:'left'" width="100">服务对象</th>
					<th data-options="field:'HANDLE_DEPT',align:'left'" width="100">办理机构(科室)</th>
					
				</tr>
			</thead>
		</table>

	</div>
</div>



