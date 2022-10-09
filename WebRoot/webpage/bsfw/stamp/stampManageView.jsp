<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    function formateStatus(val,row) {
        if (val == '1') {
            return "已启用";
        } else {
            return "已禁用";
        }
    }

    function showStampManageWindow(entityId) {
        $.dialog.open("ktStampController.do?stampManageEditView&entityId=" + entityId, {
            title : "签章管理信息",
            width : "800px",
            height : "620px",
            lock : true,
            resize : false
        }, false);
    }

    function editStampManageGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("StampManageGrid");
        if (entityId) {
            showStampManageWindow(entityId);
        }
    }

    function deleteStampManageGridRecord() {
        AppUtil.deleteDataGridRecord("ktStampController.do?multiDelStamp",
            "StampManageGrid");
    }
</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <div id="StampManageToolbar">
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton"
                               iconcls="eicon-plus" plain="true"
                               onclick="showStampManageWindow();">新建</a>
                            <a href="#"
                               class="easyui-linkbutton"
                               iconcls="eicon-pencil" plain="true"
                               onclick="editStampManageGridRecord();">编辑</a>
                            <a href="#"
                               class="easyui-linkbutton"
                               iconcls="eicon-trash-o" plain="true"
                               onclick="deleteStampManageGridRecord();">删除</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="StampManageForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">签章编码</td>
                            <td style="width:135px;"><input class="eve-input"
                                                            type="text" maxlength="32" style="width:128px;"
                                                            name="Q_T.STAMP_CODE_LIKE" /></td>
                            <td style="width:68px;text-align:right;">签章名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                                            type="text" maxlength="32" style="width:128px;"
                                                            name="Q_T.STAMP_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                                   class="eve-button"
                                                   onclick="AppUtil.gridDoSearch('StampManageToolbar','StampManageGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                       onclick="AppUtil.gridSearchReset('StampManageForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>

        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="StampManageGrid" fitcolumns="true" nowrap="false"
               toolbar="#StampManageToolbar" method="post" idfield="STAMP_ID"
               checkonselect="false" selectoncheck="false" fit="true" border="false"
               url="ktStampController.do?stampManageData">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'STAMP_ID',hidden:true">STAMP_ID</th>
                <th data-options="field:'STAMP_CODE',align:'left'" width="20%">签章编码</th>
                <th data-options="field:'STAMP_NAME',align:'left'" width="25%">签章名称</th>
                <th data-options="field:'STAMP_INTERFACE',align:'left'" width="15%">签章数据方法</th>
                <th data-options="field:'TEMPLATE_FILE_NAME',align:'left'" width="25%">印章名称</th>
                <th data-options="field:'STAMP_STATUS',align:'left'" width="15%" formatter="formateStatus">状态</th>
            </tr>
            </thead>
        </table>

    </div>
</div>
