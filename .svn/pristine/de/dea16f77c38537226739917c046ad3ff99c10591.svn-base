<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    function showSqlExportConfigWindow(sqlId) {
        $.dialog.open("excelConfigController.do?sqlExportConfigInfo&entityId="+sqlId, {
            title : "sql导出配置信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"600px",
        }, false);
    }

    function editSqlExportConfigGridRecord() {
        var sqlId = AppUtil.getEditDataGridRecord("sqlExportConfigGrid");
        if (sqlId) {
            showSqlExportConfigWindow(sqlId);
        }
    }

    function deleteSqlExportConfigGridRecord() {
        AppUtil.deleteDataGridRecord(
            "excelConfigController.do?sqlExportConfigMultiDel", "sqlExportConfigGrid");
    }

    function exportSqlExportConfigGridRecord() {
        var sqlId = AppUtil.getEditDataGridRecord("sqlExportConfigGrid");
        if (sqlId) {
            var url = "excelConfigController.do?exportSqlExportConfig&sqlId=" + sqlId;
            window.open(url);
        }
    }



</script>
<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="sqlExportConfigToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" reskey="ADD_sqlExportConfig" iconcls="eicon-plus" plain="true" onclick="showSqlExportConfigWindow();">新建</a>
                            <a href="#" class="easyui-linkbutton" reskey="EDIT_sqlExportConfig" iconcls="eicon-pencil" plain="true" onclick="editSqlExportConfigGridRecord();">编辑</a>
                            <a href="#" class="easyui-linkbutton" reskey="DEL_sqlExportConfig" iconcls="eicon-trash-o" plain="true" onclick="deleteSqlExportConfigGridRecord();">删除</a>
                            <a href="#" class="easyui-linkbutton" reskey="EXPORT_sqlExportConfig" iconcls="eicon-file-excel-o" plain="true" onclick="exportSqlExportConfigGridRecord();">导出</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="sqlExportConfigForm">
                <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:left;">模版名称<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SQL_NAME_LIKE" />
                            <input type="button" value="查询"
                                   class="eve-button"
                                   onclick="AppUtil.gridDoSearch('sqlExportConfigToolbar','sqlExportConfigGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('sqlExportConfigForm')" />
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true" id="sqlExportConfigGrid" fitcolumns="false" toolbar="#sqlExportConfigToolbar" method="post" idfield="SQL_ID" checkonselect="true" selectoncheck="true" fit="true" border="false" url="excelConfigController.do?sqlExportConfigData">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'SQL_ID',hidden:true">SQL_ID</th>
                <th data-options="field:'SQL_NAME',align:'left'" width="25%">SQL模版名称</th>
                <th data-options="field:'SQL_KEY',align:'left'" width="20%">SQL编码</th>
                <th data-options="field:'START_ROW',align:'left'" width="10%">开始行</th>
                <th data-options="field:'START_COL',align:'left'" width="10%">开始列</th>
                <th data-options="field:'CREATE_TIME',align:'left'" width="20%">创建时间</th>
            </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>