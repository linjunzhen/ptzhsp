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
    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_BDCCALL",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.STATIS_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.STATIS_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_A.STATIS_DATE_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_BDCCALL",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.STATIS_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.STATIS_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_A.STATIS_DATE_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
        });


    function bdcCallDataExport() {
        var beginDate = $("input[name='Q_A.STATIS_DATE_>=']").val();
        var endDate = $("input[name='Q_A.STATIS_DATE_<=']").val();
        var url = "statisticsNewController.do?exportBdcCallExcel&Q_A.STATIS_DATE_GE="+beginDate+"&Q_A.STATIS_DATE_LE="+endDate + "&isExp=1"
        window.location.href = url;
    }

    function bdcCallDataRefresh() {
        $.dialog.open("statisticsNewController.do?bdcCallDataRefresh", {
            title : "同步数据",
            width:"400px",
            lock: true,
            resize:false,
            height:"300px",
        }, false);
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="bdcCallStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-refresh" plain="true" onclick="bdcCallDataRefresh();">数据同步</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="bdcCallDataExport();">导出表格</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">开始日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_BEGIN_BDCCALL" name="Q_A.STATIS_DATE_>=" /></td>
                        <td style="width:68px;text-align:right;">结束日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_BDCCALL" name="Q_A.STATIS_DATE_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('bdcCallStatisToolBar','bdcCallStatisGrid')" />
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
                   id="bdcCallStatisGrid" fitcolumns="false" toolbar="#bdcCallStatisToolBar"
                   method="post" checkonselect="true"
                   selectoncheck="true" fit="true" border="false"
                   url="statisticsNewController.do?bdcCallData">
                <thead>
                <tr>
                    <th data-options="field:'STATIS_DATE',align:'center'" rowspan="2" width="7%">日期</th>
                    <th data-options="field:'ZSLL',align:'center'" rowspan="2" width="5%">总受理量</th>
                    <th data-options="field:'ZQHL',align:'center'" rowspan="2" width="5%">总取号量</th>
                    <th colspan="6">不动产登记业务受理</th>
                    <th colspan="4">缴费领证</th>
                    <th colspan="4">物业维修资金</th>
                    <th colspan="4">存量房网签</th>
                    <th colspan="6">批量受理</th>
                </tr>
                <tr>
                    <th data-options="field:'BDCDJYWSL_QHL',align:'center'" width="5%" >取号量</th>
                    <th data-options="field:'BDCDJYWSL_SLL',align:'center'" width="5%" >受理量</th>
                    <th data-options="field:'BDCDJYWSL_GHL',align:'center'" width="5%" >过号量</th>
                    <th data-options="field:'BDCDJYWSL_QTQK',align:'center'" width="5%" >其他情况</th>
                    <th data-options="field:'BDCDJYWSL_PJSLSC',align:'center'" width="10%" >平均受理时长（分钟）</th>
                    <th data-options="field:'BDCDJYWSL_PJBJSC',align:'center'" width="10%" >平均办结时长（天）</th>

                    <th data-options="field:'JFLZ_QHL',align:'center'" width="5%" >取号量</th>
                    <th data-options="field:'JFLZ_WCL',align:'center'" width="5%" >完成量</th>
                    <th data-options="field:'JFLZ_GHL',align:'center'" width="5%" >过号量</th>
                    <th data-options="field:'JFLZ_QTQK',align:'center'" width="5%" >其他情况</th>

                    <th data-options="field:'WYWXZJ_QHL',align:'center'" width="5%" >取号量</th>
                    <th data-options="field:'WYWXZJ_WCL',align:'center'" width="5%" >完成量</th>
                    <th data-options="field:'WYWXZJ_GHL',align:'center'" width="5%" >过号量</th>
                    <th data-options="field:'WYWXZJ_QTQK',align:'center'" width="5%" >其他情况</th>

                    <th data-options="field:'CLFWQ_QHL',align:'center'" width="5%" >取号量</th>
                    <th data-options="field:'CLFWQ_SLL',align:'center'" width="5%" >完成量</th>
                    <th data-options="field:'CLFWQ_GHL',align:'center'" width="5%" >过号量</th>
                    <th data-options="field:'CLFWQ_QTQK',align:'center'" width="5%" >其他情况</th>

                    <th data-options="field:'PLSL_QHL',align:'center'" width="5%" >取号量</th>
                    <th data-options="field:'PLSL_SLL',align:'center'" width="5%" >受理量</th>
                    <th data-options="field:'PLSL_GHL',align:'center'" width="5%" >过号量</th>
                    <th data-options="field:'PLSL_QTQK',align:'center'" width="5%" >其他情况</th>
                    <th data-options="field:'PLSL_PJSLSC',align:'center'" width="10%" >平均受理时长（分钟）</th>
                    <th data-options="field:'PLSL_PJBJSC',align:'center'" width="10%" >平均办结时长（天）</th>

                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
