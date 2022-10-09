
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
    function showSelectBusiness(){
        var ycthqhId = $("#TreeYqyzqhBusToolbar input[name='YQYZQH_ID']").val();
        if(ycthqhId&&ycthqhId!="0"){
            AppUtil.ajaxProgress({
                url:"callYqyzController.do?getYqyzBusinessCodes",
                params:{
                    "YQYZQH_ID":ycthqhId
                },
                callback:function(resultJson){
                    var businessCodes = "";
                    if(resultJson.BUSINESS_CODE){
                        businessCodes = resultJson.BUSINESS_CODE;
                    }
                    $.dialog.open("callSetController.do?selector&allowCount=0&businessCodes="+businessCodes, {
                        title : "业务选择器",
                        width:"1000px",
                        lock: true,
                        resize:false,
                        height:"500px",
                        close: function () {
                            var selectBueinessInfo = art.dialog.data("selectBueinessInfo");
                            if(selectBueinessInfo){
                                AppUtil.ajaxProgress({
                                    url:"callYqyzController.do?addYqyzBueiness",
                                    params:{
                                        "YQYZQH_ID":ycthqhId,
                                        "businessCodes":selectBueinessInfo.businessCodes
                                    },
                                    callback:function(resultJson){
                                        art.dialog({
                                            content: resultJson.msg,
                                            icon:"succeed",
                                            time:3,
                                            ok: true
                                        });
                                        AppUtil.gridDoSearch("TreeYqyzqhBusToolbar","TreeYqyzqhBusGrid");
                                    }
                                });
                                art.dialog.removeData("selectBueinessInfo");
                            }
                        }
                    }, false);
                }
            });

        }else{
            art.dialog({
                content: "请先选择类别!",
                icon:"warning",
                ok: true
            });
        }

    }

    function removeSelectBusiness(){
        var rowsData = $("#TreeYqyzqhBusGrid").datagrid("getChecked");
        if (!(rowsData != null && typeof (rowsData) != "undefined" && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要被解绑的业务!",
                icon:"warning",
                ok: true
            });
        }else{
            var busIds = "";
            for ( var i = 0; i < rowsData.length; i++) {
                if(i>0){
                    busIds+=",";
                }
                busIds+=rowsData[i].BUS_ID;
            }

            art.dialog.confirm("您确定要解绑该业务吗?", function() {
                AppUtil.ajaxProgress({
                    url:"callYqyzController.do?removeYqyzBueiness",
                    params:{
                        "busIds":busIds
                    },
                    callback:function(resultJson){
                        art.dialog({
                            content: resultJson.msg,
                            icon:"succeed",
                            time:3,
                            ok: true
                        });
                        AppUtil.gridDoSearch("TreeYqyzqhBusToolbar","TreeYqyzqhBusGrid");
                    }
                });
            });
        }


    }
    /**
     * 添加树形hover工具按钮
     */
    function addYqyzqhTreeHoverDom(treeId, treeNode) {
        if (treeNode.parentNode && treeNode.parentNode.id != 1)
            return;
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#addItemHref_" + treeNode.id).length > 0)
            return;
        if ($("#editItemHref_" + treeNode.id).length > 0)
            return;
        if ($("#delItemHref_" + treeNode.id).length > 0)
            return;
        var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建类型' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
        if (treeNode.id != "0") {
            operateStr += "<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
            operateStr += "<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
        }
        aObj.append(operateStr);
        $("#addItemHref_" + treeNode.id).bind("click", function() {
            showYqyzqhWindow(treeNode, true);
        });
        $("#editItemHref_" + treeNode.id).bind("click", function() {
            showYqyzqhWindow(treeNode, false);
        });
        $("#delItemHref_" + treeNode.id).bind("click", function() {
            AppUtil.deleteTreeNode({
                treeId : "yqyzqhTree",
                url : "callYqyzController.do?multiYqyzDel",
                noAllowDeleteIfHasChild : true,
                treeNode : treeNode
            });
        });
    }
    /**
     * 移除树形hover工具按钮
     */
    function removeYqyzqhTreeHoverDom(treeId, treeNode) {
        $("#addItemHref_" + treeNode.id).unbind().remove();
        $("#editItemHref_" + treeNode.id).unbind().remove();
        $("#delItemHref_" + treeNode.id).unbind().remove();
    }
    /**
     * 树形节点拖放排序
     */
    function onYqyzqhTreeDrop(event, treeId, treeNodes, targetNode,
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
                    "tableName" : "T_CKBS_YQYZQH"
                },
                callback : function(resultJson) {
                    if (resultJson.success) {
                        /* $.fn.zTree.getZTreeObj("yqyzqhTree").reAsyncChildNodes(
                                null, "refresh"); */
                        alert("成功完成拖拽排序!");
                    } else {
                        alert(resultJson.msg);
                        /* $.fn.zTree.getZTreeObj("yqyzqhTree").reAsyncChildNodes(
                                null, "refresh"); */
                    }
                }
            });
        }

    }
    /**
     * 点击树形节点触发的事件
     */
    function onYqyzqhTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN") {
            $("#TreeYqyzqhBusToolbar input[name='YQYZQH_ID']").val(treeNode.id);
            $("#TreeYqyzqhBusToolbar input[name='Q_D.PATH_LIKE']").val(treeNode.PATH);
            AppUtil.gridDoSearch("TreeYqyzqhBusToolbar", "TreeYqyzqhBusGrid");
        }
    }

    /**
     * 弹出组织机构信息窗口
     */
    function showYqyzqhWindow(treeNode, isAdd) {
        var url = "";
        if (isAdd) {
            url = encodeURI("callYqyzController.do?info&PARENT_ID=" + treeNode.id
                + "&PARENT_NAME=" + treeNode.name+"&time="+new Date());
        } else {
            var parentNode = treeNode.getParentNode();
            url = encodeURI("callYqyzController.do?info&PARENT_ID=" + parentNode.id
                + "&PARENT_NAME=" + parentNode.name + "&entityId="
                + treeNode.id+"&time="+new Date());
        }
        $.dialog.open(url, {
            title : "配置信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"320px",
        }, false);
    };


    $(document).ready(function() {
        var yqyzqhTreeSetting = {
            edit : {
                enable : true,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                addHoverDom : addYqyzqhTreeHoverDom,
                selectedMulti : false,
                removeHoverDom : removeYqyzqhTreeHoverDom
            },
            callback : {
                onClick : onYqyzqhTreeClick,
                onDrop : onYqyzqhTreeDrop,
                onAsyncSuccess : function(event, treeId, treeNode, msg) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    treeObj.expandAll(true);
                }
            },
            async : {
                enable : true,
                url : "baseController.do?tree",
                otherParam : {
                    "tableName" : "T_CKBS_YQYZQH",
                    "idAndNameCol" : "YQYZQH_ID,TYPE_NAME",
                    "targetCols" : "PARENT_ID,PATH",
                    "rootParentId" : "0",
                    "Q_STATUS_!=":0,
                    "isShowRoot" : "true",
                    "rootName" : "取号配置树"
                }
            }
        };
        $.fn.zTree.init($("#yqyzqhTree"), yqyzqhTreeSetting);
        //AppUtil.initAuthorityRes("TreeYqyzqhBusToolbar");
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
                                 style="position: relative; top:7px; float:left;" />&nbsp;取号配置树
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ul id="yqyzqhTree" class="ztree"></ul>
    </div>
    <div data-options="region:'center',split:false">
        <div id="TreeYqyzqhBusToolbar">
            <!--====================开始编写隐藏域============== -->
            <input type="hidden" name="Q_D.PATH_LIKE" />
            <input type="hidden" name="YQYZQH_ID"  />
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" resKey="ADD_SysUser"
                               iconCls="eicon-compress" plain="true"
                               onclick="showSelectBusiness();">加入业务</a> <a href="#"
                                                                           class="easyui-linkbutton" resKey="EDIT_SysUser"
                                                                           iconCls="eicon-expand" plain="true"
                                                                           onclick="removeSelectBusiness();">解绑业务</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="TreeYqyzqhBusForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">业务编码</td>
                        <td style="width:135px;"><input class="eve-input" type="text"
                                                        maxlength="20" style="width:128px;" name="Q_T.BUSINESS_CODE_LIKE" /></td>
                        <td style="width:68px;text-align:right;">业务名称</td>
                        <td style="width:135px;"><input class="eve-input" type="text"
                                                        maxlength="20" style="width:128px;" name="Q_B.BUSINESS_NAME_LIKE" /></td>
                        <td colspan="4"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('TreeYqyzqhBusToolbar','TreeYqyzqhBusGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('TreeYqyzqhBusForm')" /></td>
                    </tr>

                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->

        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="TreeYqyzqhBusGrid" fitColumns="true" toolbar="#TreeYqyzqhBusToolbar"
               method="post" idField="BUS_ID" checkOnSelect="false"
               selectOnCheck="false" fit="true" border="false"
               url="callYqyzController.do?datagrid">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>

                <th data-options="field:'BUS_ID',hidden:true">BUS_ID</th>
                <th data-options="field:'CATALOG',align:'left'" width="20%">所属目录</th>
                <th data-options="field:'TYPE_NAME',align:'left'" width="20%">所属类别</th>
                <th data-options="field:'BUSINESS_NAME',align:'left'" width="30%">业务名称</th>
                <th data-options="field:'BUSINESS_CODE',align:'left'" width="43%">业务编码</th>
            </tr>
            </thead>
        </table>

    </div>
</div>



