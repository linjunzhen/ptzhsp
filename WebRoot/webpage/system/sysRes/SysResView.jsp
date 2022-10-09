<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 添加树形hover工具按钮
	 */
	function addSysResTreeHoverDom(treeId, treeNode) {
		if (treeNode.parentNode && treeNode.parentNode.id != 1)
			return;
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#addItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#editItemHref_" + treeNode.id).length > 0)
			return;
		if ($("#delItemHref_" + treeNode.id).length > 0)
			return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子资源' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		if (treeNode.id != "0") {
			operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
			operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		}
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showSysResWindow(treeNode, true);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			showSysResWindow(treeNode, false);
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId : "sysResTree",
				url : "sysResController.do?multiDel",
				noAllowDeleteIfHasChild : true,
				treeNode : treeNode
			});
		});
	}
	/**
	 * 移除树形hover工具按钮
	 */
	function removeSysResTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_" + treeNode.id).unbind().remove();
		$("#editItemHref_" + treeNode.id).unbind().remove();
		$("#delItemHref_" + treeNode.id).unbind().remove();
	}
	/**
	 * 树形节点拖放排序
	 */
	function onSysResTreeDrop(event, treeId, treeNodes, targetNode, moveType,
			isCopy) {
		if(moveType!=null){
			AppUtil.ajaxProgress({
				url : "baseController.do?updateTreeSn",
				params : {
					"dragTreeNodeId" : treeNodes[0].id,
					"dragTreeNodeNewLevel" : treeNodes[0].level,
					"targetNodeId" : targetNode.id,
					"moveType":moveType,
					"targetNodeLevel" : targetNode.level,
					"tableName" : "T_MSJW_SYSTEM_RES"
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						/* $.fn.zTree.getZTreeObj("sysResTree").reAsyncChildNodes(
								null, "refresh"); */
						alert("成功完成拖拽排序!");
					} else {
						alert(resultJson.msg);
						$.fn.zTree.getZTreeObj("sysResTree").reAsyncChildNodes(
								null, "refresh");
					}

				}
			});
		}
		
	}
	/**
	 * 点击树形节点触发的事件
	 */
	function onSysResTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
		    var id = treeNode.id;
		    if(id!="0"){
		    	var parentNode = treeNode.getParentNode();
		    	$("#PARENT_RESNAME").html(parentNode.name);
		    	if(treeNode.RES_TYPE=="1"){
		    		$("#RES_TYPE").html("菜单");
		    	}else{
		    		$("#RES_TYPE").html("按钮");
		    	}		    	
		    	$("#RES_NAME").html(treeNode.name);
		    	$("#RES_KEY").html(treeNode.RES_KEY);
		    	if(treeNode.RES_URL){
		    		$("#RES_URL").html(treeNode.RES_URL);
		    	}else{
		    		$("#RES_URL").html("");
		    	}
		    	
		    	$("#ICON_PATH").html(treeNode.ICON_PATH);
		    }
		}
	}

	/**
	 * 弹出字典类别窗口
	 */
	function showSysResWindow(treeNode, isAdd) {
		var url = "";
		if (isAdd) {
			url = encodeURI("sysResController.do?info&PARENT_ID=" + treeNode.id
					+ "&PARENT_NAME=" + treeNode.name);
		} else {
			var parentNode = treeNode.getParentNode();
			url = encodeURI("sysResController.do?info&PARENT_ID=" + parentNode.id
					+ "&PARENT_NAME=" + parentNode.name + "&entityId="
					+ treeNode.id);
		}
		$.dialog.open(url, {
    		title : "系统资源信息",
            width:"800px",
            lock: true,
            resize:false,
            height:"600px",
    	}, false);
	};

	$(document).ready(function() {
		var sysResTreeSetting = {
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				addHoverDom : addSysResTreeHoverDom,
				selectedMulti : false,
				removeHoverDom : removeSysResTreeHoverDom
			},
			callback : {
				onClick : onSysResTreeClick,
				onDrop : onSysResTreeDrop
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_RES",
					"idAndNameCol" : "RES_ID,RES_NAME",
					"targetCols" : "RES_KEY,PARENT_ID,RES_URL,RES_TYPE,ICON_PATH",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "系统资源树"
				}
			}
		};
		$.fn.zTree.init($("#sysResTree"), sysResTreeSetting);
	});
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
								style="position: relative; top:7px; float:left;" />&nbsp;系统资源树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="sysResTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" style="background-color: #f7f7f7;">
	   <table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">资源信息</td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">上级资源：</span>
				<div id="PARENT_RESNAME"></div>
				</td>
				<td>
				<span style="width: 100px;float:left;text-align:right;">资源类型：</span>
				<div id="RES_TYPE"></div>
				</td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">资源名称：</span>
				<div id="RES_NAME"></div>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">资源KEY：</span>
				<div id="RES_KEY"></div>
				</td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">资源URL：</span>
				<div id="RES_URL"></div>
				</td>
				<td >
				<span style="width: 100px;float:left;text-align:right;">资源图片路径：</span>
				<div id="ICON_PATH"></div>
				</td>
			</tr>
		</table>
	</div>
</div>
