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

    function exportWsysExcel() {
        var beginDate = $("input[name='Q_t.create_time_>=']").val();
        var endDate = $("input[name='Q_t.create_time_<=']").val();
        var url = "statisticsNewController.do?exportWsysExcel&beginDate=" + beginDate + "&endDate=" + endDate;
        window.location.href = url;
    }

    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_WSYS",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_t.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_t.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_t.create_time_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_WSYS",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_t.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_t.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_t.create_time_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);

        });
</script>



<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="wsysDataBar">
<%--            <!--====================开始编写隐藏域============== -->--%>
<%--            <!--====================结束编写隐藏域============== -->--%>
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportWsysExcel();">导出数据</a>
                        </div>
                    </div>
                </div>
            </div>

    <form action="#" name="SysLogForm">
        <input type="hidden" id="DEPARTID" name="Q_D.DEPART_ID_=" value="">
        <table class="dddl-contentBorder dddl_table"
               style="background-repeat:repeat;width:100%;border-collapse:collapse;">
            <tbody>
            <tr>
                <td style="width:68px;text-align:right;">开始日期：</td>
                <td style="width:135px;"><input type="text"
                                                style="width:150px;float:left;" class="laydate-icon"
                                                id="SysLogL.OPERATE_TIME_BEGIN_WSYS" name="Q_t.create_time_>="/></td>
                <td style="width:68px;text-align:right;">结束日期：</td>
                <td style="width:135px;"><input type="text"
                                                style="width:150px;float:left;" class="laydate-icon"
                                                id="SysLogL.OPERATE_TIME_END_WSYS" name="Q_t.create_time_<=" /></td>
                <td colspan="2"><input type="button" value="查询"
                                       class="eve-button"
                                       onclick="AppUtil.gridDoSearch('wsysDataBar','wsysDataGrid')" />
                    <input type="button" value="重置" class="eve-button"
                           onclick="resetSpsxmxForm()" /></td>
            </tr>
            </tbody>
        </table>
    </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <div class="gridtable">
        <table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
               id="wsysDataGrid" fitcolumns="false" toolbar="#wsysDataBar"
               method="post" checkonselect="false" nowrap="false"
               selectoncheck="true" fit="true" border="false"
               url="statisticsNewController.do?wsysData">
            <thead>
            <tr>
                <th data-options="field:'EXE_ID',align:'center'" width="15%">申报号</th>
                <th data-options="field:'CREATE_TIME',align:'center'" width="15%">申报时间</th>
                <th data-options="field:'DEPART_NAME',align:'center'" width="15%">部门</th>
                <th data-options="field:'DEPART_NAME_Z',align:'center'" width="15%">子部门</th>
                <th data-options="field:'ITEM_NAME',align:'center'" width="10%">事项名称</th>
                <th data-options="field:'SUBJECT',align:'center'" width="30%">办件名称</th>
                <th data-options="field:'CUR_STEPAUDITNAMES',align:'center'" width="10%">审核人员</th>
                <th data-options="field:'ASSIGNER_NAME',align:'center'" width="10%">联系人</th>
                <th data-options="field:'MOBILE',align:'center'" width="10%">联系电话</th>
                <th data-options="field:'ASSIGNER_CODE',align:'center'" width="10%">账号</th>
            </tr>
            </thead>
        </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>