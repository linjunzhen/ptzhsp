<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    $(document).ready(function() {

        var flowTypeTreeSetting = {
            edit : {
                enable : true,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                addHoverDom : addFlowConfigTreeHoverDom,
                selectedMulti : false,
				removeHoverDom : removeFlowConfigTreeHoverDom
            },
            callback : {
                onClick : onFlowConfigTreeClick,
                onDrop : onTypeTreeDrop,
                onAsyncSuccess : function(event, treeId, treeNode, msg) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    treeObj.expandAll(true);
                    //     					if (curUserResKeys != "__ALL") {
                    //     						var loginUserInfoJson = $(
                    //     								"input[name='loginUserInfoJson']").val();
                    //     						var loginUser = JSON2.parse(loginUserInfoJson);
                    //     						//获取授权的区划代码数组
                    //     						var authDepCodeArray = loginUser.authDepCodes
                    //     								.split(",");
                    //     						var nodes = treeObj.transformToArray(treeObj
                    //     								.getNodes());
                    //     						for (var i = 0; i < nodes.length; i++) {
                    //     							var child = nodes[i];
                    //     							if (!AppUtil.isContain(authDepCodeArray,
                    //     									child.DEPART_CODE)) {
                    //     								if(child.DEPART_CODE){
                    //     									treeObj.hideNode(child);
                    //     								}
                    //     							}
                    //     						}
                    //     					}
                }
            },
            async : {
                enable : true,
                url : "baseController.do?tree",
                otherParam : {
                    "tableName" : "T_FLOW_CONFIG_CATEGORY",
                    "idAndNameCol" : "ID,NAME",
                    "targetCols" : "PARENT_ID",
                    "rootParentId" : "0",
                    // 						"needCheckIds" : needCheckIds,
                    "isShowRoot" : "true",
                    "rootName" : "集成流程类型树"
                }

            //     				url : "departmentController.do?tree",
            //     				otherParam : {
            //     					"tableName" : "T_FLOW_CONFIG_CATEGORY",
            //     					"idAndNameCol" : "ID,NAME",
            //     					"targetCols" : "PARENT_ID",
            //     					"rootParentId" : "0",
            //     					"Q_STATUS_!=":0,
            //     					"isShowRoot" : "true",
            //     					"rootName" : "组织机构树"
            //     				}
            }
        };
        $.fn.zTree.init($("#flowTypeTree"), flowTypeTreeSetting);

        $("#FlowConfigCateGrid").datagrid({
            onClickRow : function(index, row) {
                doloadType(row.ID, row.NAME);
            }
        });
        $("#FlowConfigTypeGrid").datagrid({
            onClickRow : function(index, row) {
                doloadType2(row.ID, row.NAME);
            }
        });
        $("#FlowConfigTypeGrid2").datagrid({
            onClickRow : function(index, row) {
                doloadFlow(row.ID, row.NAME);
            }
        });
        $("#FlowConfigFlowGrid").datagrid({
            onClickRow : function(index, row) {
                doloadLink(row.ID, row.NAME);
            }
        });

        //     $("#linkDiv").width(500);
    });

    function onTypeTreeDrop(event, treeId, treeNodes, targetNode, moveType,
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
                    "tableName" : "T_FLOW_CONFIG_CATEGORY"
                },
                callback : function(resultJson) {
                    if (resultJson.success) {
                        /* $.fn.zTree.getZTreeObj("departmentTree").reAsyncChildNodes(
                        		null, "refresh"); */
                        alert("成功完成拖拽排序!");
                    } else {
                        alert(resultJson.msg);
                        /* $.fn.zTree.getZTreeObj("departmentTree").reAsyncChildNodes(
                        		null, "refresh"); */
                    }
                }
            });
        }

    }

    /**
     * 点击树形节点触发的事件
     */
    function onFlowConfigTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN") {
            
            //             AppUtil.gridDoSearch("TreeSysUserToolbar", "TreeSysUserGrid");
        }
    }

    /**
     * 弹出组织机构信息窗口
     */
    function showTypeWindow(treeNode, isAdd) {
        var url = "";
        if (isAdd) {
            url = "flowConfigController.do?info&PARENT_ID=" + treeNode.id
                    + "&PARENT_NAME=" + encodeURIComponent(treeNode.name) + "&time=" + encodeURIComponent(new Date());
        } else {
            var parentNode = treeNode.getParentNode();
            url = "flowConfigController.do?info&PARENT_ID=" + parentNode.id
                    + "&PARENT_NAME=" + encodeURIComponent(parentNode.name) + "&entityId="
                    + treeNode.id + "&time=" + encodeURIComponent(new Date());
        }
        $.dialog.open(url, {
            title : "流程类型配置",
            width : "400px",
            lock : true,
            resize : false,
            height : "100px",
        }, false);
    };

    /**
     * 弹出组织机构信息窗口
     */
    function showTaskListWindow() {

        if ($('#flow_parentId').val() == '-1') {
            $.messager.alert('Warning', '请先选择类型');
        } else {
            var url = "";
            url = "flowConfigController.do?goTaskList&PARENT_ID="
                    + $('#flow_parentId').val() + "&time=" + new Date();
            ;
            $.dialog.open(url, {
                title : "流程类型配置",
                width : "400px",
                lock : true,
                resize : false,
                height : "100px",
            }, false);
        }
    };

    function addFlowConfigTreeHoverDom(treeId, treeNode) {
        if (treeNode.parentNode && treeNode.parentNode.id != 1)
            return;
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#addItemHref_" + treeNode.id).length > 0)
            return;
        if ($("#editItemHref_" + treeNode.id).length > 0)
            return;
        if ($("#delItemHref_" + treeNode.id).length > 0)
            return;
        var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子类型' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
        if (treeNode.id != "0") {
            operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
            operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
        }
        aObj.append(operateStr);
        $("#addItemHref_" + treeNode.id).bind("click", function() {
            showTypeWindow(treeNode, true);
        });
        $("#editItemHref_" + treeNode.id).bind("click", function() {
            showTypeWindow(treeNode, false);
        });
        $("#delItemHref_" + treeNode.id).bind("click", function() {
            AppUtil.deleteTreeNode({
                treeId : "flowTypeTree",
                url : "flowConfigController.do?multiDel",
                noAllowDeleteIfHasChild : true,
                treeNode : treeNode
            });
        });
    }

	/**
	 * 移除树形hover工具按钮
	 */
	function removeFlowConfigTreeHoverDom(treeId, treeNode) {
		$("#addItemHref_"+treeNode.id).unbind().remove();
		$("#editItemHref_"+treeNode.id).unbind().remove();
		$("#delItemHref_"+treeNode.id).unbind().remove();
	}
</script>
<style>
.panel-header {
    position: relative;
    height: 37px;
    border-color: #eee;
    border-width: 0 0 1px 0;
    padding: 0;
    background: #fff;
}
.panel-header:after{
	content: '';
	width: 2px;
	background: #3e8bff;
	display: block;
	height: 16px;
	position: absolute;
	left: 1px;
	top: 8px;
}
.panel-title {
    font-size: 16px;
    line-height: 32px;
    padding-left: 8px;
    color: #333;
    font-weight: normal;
    height: 32px;
}
</style>
<div class="eui-jh-box" style='margin-left: 10px; height: 99%; margin-top: 5px;'>
	<div style='float: left; height: 100%; width: 100%;'>
		<div class='easyui-panel' style="width: 100%; height: 99%;" title='类型'>
<!-- 			<div class="right-con"> -->
<!-- 				<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;"> -->
<!-- 					<div class="e-toolbar-ct"> -->
<!-- 						<div class="e-toolbar-overflow"> -->
<!-- 							<div style="color: #005588;"> -->
<!-- 								<img src="plug-in/easyui-1.4/themes/icons/script.png" -->
<!-- 									style="position: relative; top: 7px; float: left;" />&nbsp;基成流程分类树 -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<ul id="flowTypeTree" class="ztree"></ul>
			<!-- 			<div id="FlowConfigCateToolbar"> -->
			<!-- 				<div class="right-con"> -->
			<!-- 					<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;"> -->
			<!-- 						<div class="e-toolbar-ct"> -->
			<!-- 							<div class="e-toolbar-overflow"> -->
			<!-- 								<a href="#" class="easyui-linkbutton" reskey="Add_FlowConfig" -->
			<!-- 									iconcls="icon-note-add" plain="true" -->
			<!-- 									onclick="showFlowConfigWindow('','0','0');">新建</a> <a href="#" -->
			<!-- 									class="easyui-linkbutton" reskey="EDIT_SysUser" -->
			<!-- 									iconcls="icon-note-edit" plain="true" -->
			<!-- 									onclick="editFlowConfigGateGridRecord();">编辑</a> <a href="#" -->
			<!-- 									class="easyui-linkbutton" reskey="DEL_SysUser" -->
			<!-- 									iconcls="icon-note-delete" plain="true" -->
			<!-- 									onclick="deleteFlowConfigGridRecord('FlowConfigCateGrid');">删除</a> -->
			<!-- 							</div> -->
			<!-- 						</div> -->
			<!-- 					</div> -->
			<!-- 				</div> -->
			<!-- 			</div> -->
			<!-- =========================查询面板结束========================= -->
			<!-- =========================表格开始==========================-->
			<!-- 			<table class="easyui-datagrid" rownumbers="true" pagination="false" -->
			<!-- 				id="FlowConfigCateGrid" fitcolumns="true" -->
			<!-- 				toolbar="#FlowConfigCateToolbar" method="post" idfield="ID" -->
			<!-- 				singleSelect="true" checkonselect="false" selectoncheck="false" -->
			<!-- 				fit="true" border="false" style='width: 100px' nowrap='false' -->
			<!-- 				url="flowConfigController.do?datagrid&Q_T.PARENT_ID_EQ=0"> -->
			<!-- 				<thead> -->
			<!-- 					<tr> -->
			<!-- 						<th field="ck" checkbox="true"></th> -->
			<!-- 						<th data-options="field:'ID',hidden:true" width="80">ID</th> -->
			<!-- 						<th data-options="field:'NAME',align:'left'" width="150">类型名</th> -->
			<!-- 					</tr> -->
			<!-- 				</thead> -->
			<!-- 			</table> -->
		</div>
		<!-- 		<div style="width: 98%; height: 59%; margin-top: 2%"> -->
		<!-- 			<div id='typeDiv' class='easyui-panel' -->
		<!-- 				style="width: 100%; height: 100%;" title='一级分类'> -->
		<!-- 				<div id="FlowConfigTypeToolbar"> -->
		<!-- 					====================开始编写隐藏域============== -->
		<!-- 					<input id='type_parentId' type="hidden" name="Q_T.PARENT_ID_EQ" -->
		<!-- 						value='-1' /> -->
		<!-- 					====================结束编写隐藏域============== -->
		<!-- 					<div class="right-con"> -->
		<!-- 						<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;"> -->
		<!-- 							<div class="e-toolbar-ct"> -->
		<!-- 								<div class="e-toolbar-overflow"> -->
		<!-- 									<a href="#" class="easyui-linkbutton" reskey="Add_FlowConfig" -->
		<!-- 										iconcls="icon-note-add" plain="true" onclick="doAddType();">新建</a> -->
		<!-- 									<a href="#" class="easyui-linkbutton" reskey="EDIT_SysUser" -->
		<!-- 										iconcls="icon-note-edit" plain="true" -->
		<!-- 										onclick="editFlowConfigTypeGridRecord();">编辑</a> <a href="#" -->
		<!-- 										class="easyui-linkbutton" reskey="DEL_SysUser" -->
		<!-- 										iconcls="icon-note-delete" plain="true" -->
		<!-- 										onclick="deleteFlowConfigGridRecord('FlowConfigTypeGrid');">删除</a> -->
		<!-- 								</div> -->
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 				=========================查询面板结束========================= -->
		<!-- 				=========================表格开始========================== -->
		<!-- 				<table class="easyui-datagrid" rownumbers="true" pagination="false" -->
		<!-- 					id="FlowConfigTypeGrid" fitcolumns="true" nowrap='false' -->
		<!-- 					toolbar="#FlowConfigTypeToolbar" method="post" idfield="ID" -->
		<!-- 					singleSelect="true" checkonselect="false" selectoncheck="false" -->
		<!-- 					fit="true" border="false" url=""> -->
		<!-- 					<thead> -->
		<!-- 						<tr> -->
		<!-- 							<th field="ck" checkbox="true"></th> -->
		<!-- 							<th data-options="field:'ID',hidden:true" width="80">ID</th> -->
		<!-- 							<th data-options="field:'NAME',align:'left'" width="150">分类名</th> -->
		<!-- 						</tr> -->
		<!-- 					</thead> -->
		<!-- 				</table> -->
		<!-- 			</div> -->
		<!-- 		</div> -->
	</div>
	<!-- <div style='float: left; height: 100%;width:25%;'> -->
	<!-- </div> -->
	<!-- 	<div style='float: left; height: 100%; width: 25%;'> -->
	<!-- 		<div id='typeDiv2' class='easyui-panel' -->
	<!-- 			style="width: 98%; height: 99%;" title='二级分类'> -->
	<!-- 			<div id="FlowConfigType2Toolbar"> -->
	<!-- 				====================开始编写隐藏域============== -->
	<!-- 				<input id='type_parentId_2' type="hidden" name="Q_T.PARENT_ID_EQ" -->
	<!-- 					value='-1' /> -->
	<!-- 				====================结束编写隐藏域============== -->
	<!-- 				<div class="right-con"> -->
	<!-- 					<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;"> -->
	<!-- 						<div class="e-toolbar-ct"> -->
	<!-- 							<div class="e-toolbar-overflow"> -->
	<!-- 								<a href="#" class="easyui-linkbutton" reskey="Add_FlowConfig" -->
	<!-- 									iconcls="icon-note-add" plain="true" onclick="doAddType2();">新建</a> -->
	<!-- 								<a href="#" class="easyui-linkbutton" reskey="EDIT_SysUser" -->
	<!-- 									iconcls="icon-note-edit" plain="true" -->
	<!-- 									onclick="editFlowConfigTypeGridRecord2();">编辑</a> <a href="#" -->
	<!-- 									class="easyui-linkbutton" reskey="DEL_SysUser" -->
	<!-- 									iconcls="icon-note-delete" plain="true" -->
	<!-- 									onclick="deleteFlowConfigGridRecord('FlowConfigTypeGrid');">删除</a> -->
	<!-- 							</div> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->
	<!-- 			</div> -->
	<!-- 			<table class="easyui-datagrid" rownumbers="true" pagination="false" -->
	<!-- 				id="FlowConfigTypeGrid2" fitcolumns="true" nowrap='false' -->
	<!-- 				toolbar="#FlowConfigType2Toolbar" method="post" idfield="ID" -->
	<!-- 				singleSelect="true" checkonselect="false" selectoncheck="false" -->
	<!-- 				fit="true" border="false" url=""> -->
	<!-- 				<thead> -->
	<!-- 					<tr> -->
	<!-- 						<th field="ck" checkbox="true"></th> -->
	<!-- 						<th data-options="field:'ID',hidden:true" width="80">ID</th> -->
	<!-- 						<th data-options="field:'NAME',align:'left'" width="150">分类名</th> -->
	<!-- 					</tr> -->
	<!-- 				</thead> -->
	<!-- 			</table> -->
	<!-- 		</div> -->
	<!-- 	</div> -->

</div>