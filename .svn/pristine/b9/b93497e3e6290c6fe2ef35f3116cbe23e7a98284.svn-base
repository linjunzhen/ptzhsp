
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addProductTypeTreeHoverDom(treeId, treeNode) {
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
			showProductTypeWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showProductTypeWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "productTypeTree",
				url : "productTypeController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeProductTypeTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onProductTypeTreeDrop(event, treeId, treeNodes, targetNode,
			moveType, isCopy) {
		AppUtil.ajaxProgress({
			url : "baseController.do?updateTreeSn",
			params : {
				"dragTreeNodeId" : treeNodes[0].id,
				"dragTreeNodeNewLevel" : treeNodes[0].level,
				"targetNodeId" : targetNode.id,
				"targetNodeLevel" : targetNode.level,
				"tableName" : "T_DEMO_PRODUCTTYPE"
			},
			callback : function(resultJson) {
				if (resultJson.success) {
					$.fn.zTree.getZTreeObj("productTypeTree")
							.reAsyncChildNodes(null, "refresh");
					alert("成功完成拖拽排序!");
				} else {
					alert(resultJson.msg);
					$.fn.zTree.getZTreeObj("productTypeTree")
							.reAsyncChildNodes(null, "refresh");
				}

			}
		});
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onProductTypeTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			$("#productToolbar input[name='TYPE_ID']").val(treeNode.id);
			$("#productToolbar input[name='Q_T.PATH_LIKE']").val(treeNode.PATH);
			AppUtil.gridDoSearch("productToolbar", "productGrid");
		}
	}

	/**
	 * 弹出产品类别信息窗口
	 */
	function showProductTypeWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = "productTypeController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name;
		} else {
			var parentNode = treeNode.getParentNode();
			url = "productTypeController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id;
		}
		$.layer({
			type : 2,
			maxmin : false,
			title : "产品类别信息",
			area : [ "600px", "250px" ],
			iframe : {
				src : url
			}
		})
	};
	/**
	 * 重置查询条件
	 */
	function resetSearch() {
		var TYPE_ID = $("#productToolbar input[name='TYPE_ID']").val();
		var PATH = $("#productToolbar input[name='Q_T.PATH_LIKE']").val();
		AppUtil.gridSearchReset("ProductForm");
		$("#productToolbar input[name='TYPE_ID']").val(TYPE_ID);
		$("#productToolbar input[name='Q_T.PATH_LIKE']").val(PATH);
	}

	/**
	 * 删除产品列表记录
	 */
	function deleteProductDataGridRecord() {
		AppUtil.deleteDataGridRecord("productController.do?multiDel",
				"productGrid");
	};
	/**
	 * 编辑产品列表记录
	 */
	function editProductGridRecord() {
		var PRODUCT_ID = AppUtil.getEditDataGridRecord("productGrid");
		if (PRODUCT_ID) {
			showProductWindow(PRODUCT_ID);
		}
	}

	/**
	 * 显示产品信息对话框
	 */
	function showProductWindow(entityId) {
		if (entityId) {
			$.layer({
				type : 2,
				maxmin : false,
				title : "产品信息",
				area : [ "600px", "500px" ],
				iframe : {
					src : "productController.do?info&entityId=" + entityId
				}
			})
		} else {
			var TYPE_ID = $("#productToolbar input[name='TYPE_ID']").val();
			if (TYPE_ID && TYPE_ID != "0") {
				var treeObj = $.fn.zTree.getZTreeObj("productTypeTree");
				var treeNode = treeObj.getNodesByParam("id", TYPE_ID, null)[0];
				$.layer({
					type : 2,
					maxmin : false,
					title : "产品信息",
					area : [ "600px", "500px" ],
					iframe : {
						src : "ProductController.do?info&TYPE_ID="
								+ treeNode.id + "&TYPE_NAME=" + treeNode.name
					}
				})
			} else {
				layer.alert("请先选择产品类别!");
			}
		}

	};

	$(document).ready(function() {
		var productTypeTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addProductTypeTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeProductTypeTreeHoverDom
			},
			callback : {
				onClick : onProductTypeTreeClick,
				onDrop : onProductTypeTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_DEMO_PRODUCTTYPE",
					"idAndNameCol" : "TYPE_ID,TYPE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "产品类别树"
				}
			}
		};
		$.fn.zTree.init($("#productTypeTree"), productTypeTreeSetting);
		AppUtil.initAuthorityRes("productToolbar");
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
								style="position: relative; top:7px; float:left;" />&nbsp;产品类别树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="productTypeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="ProductToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_Product"
								iconCls="icon-note-add" plain="true"
								onclick="showProductWindow();">新建产品</a> <a href="#"
								class="easyui-linkbutton" resKey="EDIT_Product"
								iconCls="icon-note-edit" plain="true"
								onclick="editProductGridRecord();">编辑产品</a> <a href="#"
								class="easyui-linkbutton" resKey="DEL_Product"
								iconCls="icon-note-delete" plain="true"
								onclick="deleteProductDataGridRecord();">删除产品</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ProductForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
						<td style="width:68px;text-align:right;">产品名称</td>
						<td style="width:135px;"><input class="eve-input" type="text"
							maxlength="20" style="width:128px;" name="Q_T.PRODUCT_NAME_LIKE" /></td>
						<td colspan="6"><input type="button" value="查询"
							class="eve-button"
							onclick="AppUtil.gridDoSearch('ProductToolbar','ProductGrid')" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('ProductForm')" /></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ProductGrid" fitColumns="false" toolbar="#ProductToolbar"
			method="post" idField="PRODUCT_ID" checkOnSelect="false"
			selectOnCheck="false" fit="true" border="false"
			url="productController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>

					<th data-options="field:'PRODUCT_ID',hidden:true" width="80">PRODUCT_ID</th>
					<th data-options="field:'PRODUCT_CODE',align:'left'" width="100">产品编码</th>
					<th data-options="field:'PRODUCT_NAME',align:'left'" width="100">产品名称</th>
					<th data-options="field:'PRODUCT_PRICE',align:'left'" width="100">产品价格</th>
					<th data-options="field:'MANU_DATE',align:'left'" width="100">生产日期</th>
					<th data-options="field:'PRODUCT_STATUS',align:'left'" width="100">产品状态</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="100">类别名称</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



