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

    $(document).ready(
        function() {
            var start1 = {
                elem : "#START_DATE",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='START_DATE']")
                        .val();
                    var endTime = $("input[name='END_DATE']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='START_DATE']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#END_DATE",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='START_DATE']")
                        .val();
                    var endTime = $("input[name='END_DATE']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='END_DATE']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
        });

    function exportMrsltjXsExcel() {
        var url = "statisticsNewController.do?exportMrsltjXsExcel"
        var startDate = $("[name='START_DATE']").val();
        var endDate = $("[name='END_DATE']").val();
        url += "&START_DATE=" + startDate + "&END_DATE=" + endDate;
        window.open(url,"_blank")
    }


    function search() {
        var queryParams = {};
        var startDate = $("[name='START_DATE']").val();
        var endDate = $("[name='END_DATE']").val();
        if (startDate && startDate != "") {
            queryParams['START_DATE_ELSE'] = startDate;
        }
        if (endDate && endDate != "") {
            queryParams['END_DATE_ELSE'] = endDate;
        }
        $("#mrsltjXsGrid").datagrid('load',queryParams);
    }
</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="mrsltjXsToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportMrsltjXsExcel();">导出数据</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="mrsltjXsForm">
                <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">开始日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="START_DATE" name="START_DATE" value="${date}"/></td>
                        <td style="width:68px;text-align:right;">结束日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="END_DATE" name="END_DATE" value="${date}"/></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="search();" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('mrsltjXsForm')" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <div class="gridtable">
            <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
                   id="mrsltjXsGrid" fitcolumns="false" toolbar="#mrsltjXsToolBar"
                   method="post" checkonselect="true"
                   selectoncheck="true" fit="true" border="false"
                   url="statisticsNewController.do?mrsltjXsData&START_DATE=${date}&END_DATE=${date}">
                <thead>
                <tr>
                    <th data-options="field:'DEPART_NAME',align:'center'" width="20%">部门</th>
                    <th data-options="field:'CHILD_DEPART_NAME',align:'center'" width="20%">处室</th>
                    <th data-options="field:'ITEM_NAME',align:'center'" width="20%">办理业务</th>
                    <th data-options="field:'ZCBJ',align:'center'" width="7%">正常办结</th>
                    <th data-options="field:'ZZBLZ',align:'center'" width="7%">正在办理中</th>
                    <th data-options="field:'SHBTG',align:'center'" width="7%">审核不通过</th>
                    <th data-options="field:'QZJS',align:'center'" width="7%">强制结束</th>
                    <th data-options="field:'GQL',align:'center'" width="7%">挂起量</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>





