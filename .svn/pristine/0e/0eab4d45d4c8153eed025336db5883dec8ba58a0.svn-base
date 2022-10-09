<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
    .gridtable {
        width: 100%;
        height: 100%
    }

    .gridtable .datagrid-htable {
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc
    }

    .gridtable .datagrid-btable {
        border-left: 1px solid #ccc;
        border-bottom: 1px solid #ccc
    }

    .gridtable .datagrid-header-row td {
        border-width: 0;
        border-left: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }

    .gridtable .datagrid-header-row td:last-child {
        border-left: 1px solid #ccc;
        border-right: 1px solid #ccc;
    }

    .gridtable .datagrid-body td {
        border-width: 0;
        border-right: 1px solid #ccc;
        border-top: 1px solid #ccc;
    }
</style>
<script>
    function reviseHTIndustry() {
        var INDUSTRY_ID = AppUtil.getEditDataGridRecord("HTIndustryListDataGrid");
        if (INDUSTRY_ID != null && INDUSTRY_ID.length > 0) {
            $.dialog.open("statisticsNewController.do?reviseHTIndustry&INDUSTRY_ID=" + INDUSTRY_ID, {
                title : "数据修改",
                width : "800px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
        }
    }

    function addHTIndustry() {
        $.dialog.open("statisticsNewController.do?reviseHTIndustry", {
            title : "数据新增",
            width : "800px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
    }

    function deleteHTIndustry() {
        AppUtil.deleteDataGridRecord("statisticsNewController.do?deleteHTIndustry", "HTIndustryListDataGrid");
    }
</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="HTIndustryListDataBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="reviseHTIndustry();">编辑</a>
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-plus" plain="true" onclick="addHTIndustry();">新增</a>
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-trash-o" plain="true" onclick="deleteHTIndustry();">删除</a>
                        </div>
                    </div>
                </div>
            </div>
<%--            <form action="#" name="SysLogForm">--%>
<%--                <table class="dddl-contentBorder dddl_table"--%>
<%--                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">--%>
<%--                    <tbody>--%>
<%--                    <tr style="height:28px;">--%>
<%--                        <td style="width:80px;text-align:right;">服务事项名称</td>--%>
<%--                        <td style="width:135px;">--%>
<%--                            <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_a.ITEM_NAME_LIKE" />--%>
<%--                        </td>--%>
<%--                        <td colspan="2"><input type="button" value="查询"--%>
<%--                                               class="eve-button"--%>
<%--                                               onclick="AppUtil.gridDoSearch('HTIndustryListDataBar','HTIndustryListDataGrid')" />--%>
<%--                            <input type="button" value="重置" class="eve-button"--%>
<%--                                   onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>--%>
<%--                    </tr>--%>
<%--                    </tbody>--%>
<%--                </table>--%>
<%--            </form>--%>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <div class="gridtable">
            <table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
                   id="HTIndustryListDataGrid" fitcolumns="false" toolbar="#HTIndustryListDataBar"
                   method="post" idfield="INDUSTRY_ID" checkonselect="true"
                   selectoncheck="true" fit="true" border="true"
                   url="statisticsNewController.do?HTIndustryListData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'INDUSTRY_CODE',align:'center'" width="15%">INDUSTRY_CODE</th>
                    <th data-options="field:'INDUSTRY_NAME',align:'center'" width="20%">INDUSTRY_NAME</th>
                    <th data-options="field:'PROJECT_CODE',align:'center'" width="15%">PROJECT_CODE</th>
                    <th data-options="field:'PROJECT_NAME',align:'center'" width="20%">PROJECT_NAME</th>
                    <th data-options="field:'REPORT',align:'center'" width="30%">REPORT</th>
                    <th data-options="field:'REPORT_FORM',align:'center'" width="30%">REPORT_FORM</th>
                    <th data-options="field:'REGISTRAT_FORM',align:'center'" width="20%">REGISTRAT_FORM</th>
                    <th data-options="field:'EXEMPT',align:'center'" width="20%">EXEMPT</th>
                    <th data-options="field:'SENSITIVE_MEAN',align:'center'" width="20%">SENSITIVE_MEAN</th>
                    <th data-options="field:'CLAUSE_CONTENT',align:'center'" width="60%">CLAUSE_CONTENT</th>
                    <th data-options="field:'PRIORITY',align:'center'" width="15%">PRIORITY</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
