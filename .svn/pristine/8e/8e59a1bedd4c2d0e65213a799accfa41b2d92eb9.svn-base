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
        var depart = $("input[name='Q_T.DEPART_NAME_EQ']").val();
        var rownum = $("input[name='Q_T.ROWNUM_EQ']").val()
        var startTime = $("input[name='Q_T.CREATE_TIME_GE']").val()
        var endTime = $("input[name='Q_T.CREATE_TIME_LE']").val()
        var url = "statisticsNewController.do?exportDepartSqrExcel&Q_T.DEPART_NAME_EQ=" + depart + "&Q_T.ROWNUM_EQ=" + rownum;
        url += "&Q_T.CREATE_TIME_GE=" + startTime + "&Q_T.CREATE_TIME_LE=" + endTime;
        window.open(encodeURI(url));
    }

    function showSelectDeparts(){
        var departId = $("input[name='Q_T.DEPART_ID_=']").val();
        $.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
            +"checkCascadeN=", {
            title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_D.DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("input[name='Q_T.DEPART_NAME_EQ']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
    }

    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_DEPARTSQR",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.CREATE_TIME_GE']")
                        .val();
                    var endTime = $("input[name='Q_T.CREATE_TIME_LE']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_T.CREATE_TIME_GE']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_DEPARTSQR",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.CREATE_TIME_GE']")
                        .val();
                    var endTime = $("input[name='Q_T.CREATE_TIME_LE']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_T.CREATE_TIME_LE']").val("");
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
        <div id="callAppointStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
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
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">部门：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_T.DEPART_NAME_EQ" onclick="showSelectDeparts();"/>
                        </td>
                        <td style="width:68px;text-align:right;">导出数量：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_T.ROWNUM_EQ"/>
                        </td>
                        <td style="width:68px;text-align:right;">开始日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_BEGIN_DEPARTSQR" name="Q_T.CREATE_TIME_GE"/></td>
                        <td style="width:68px;text-align:right;">结束日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_DEPARTSQR" name="Q_T.CREATE_TIME_LE" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('callAppointStatisToolBar','callAppointStatisGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <div class="gridtable">
            <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
                   id="callAppointStatisGrid" fitcolumns="false" toolbar="#callAppointStatisToolBar"
                   method="post" idfield="LOG_ID" checkonselect="false" nowrap="false"
                   selectoncheck="false" fit="true" border="false"
                   url="statisticsNewController.do?departSqrData">
                <thead>
                <tr>
                    <th data-options="field:'DEPART_NAME',align:'center'" width="20%">部门</th>
                    <th data-options="field:'EXE_ID',align:'center'" width="10%">申报号</th>
                    <th data-options="field:'SUBJECT',align:'center'" width="30%">办件名称</th>
                    <th data-options="field:'SQRMC',align:'center'" width="20%">申请人</th>
                    <th data-options="field:'SQRSJH',align:'center'" width="20%">申请人电话</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>