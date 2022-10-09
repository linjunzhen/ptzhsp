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

    initTime();

    function initTime() {
        var ddsctjDateMonth = new Date().getMonth();
        var ddsctjDateMonth = ddsctjDateMonth.toString() < 10 ? '0' + ddsctjDateMonth : '' + ddsctjDateMonth
        var ddsctjDateYear = new Date().getFullYear();
        var ddsctjDate = ddsctjDateYear + '-' + ddsctjDateMonth + '-01'
        $("#ddsctjStatisGrid").attr("url", "statisticsNewController.do?ddsctjData&Q_A.CREATE_TIME_GE=" + ddsctjDate + " 00:00:00");
        $("input[name='Q_a.create_time_>=']").val(ddsctjDate)
    }


    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_DDSC",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_a.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_a.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_a.create_time_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_DDSC",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_a.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_a.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_a.create_time_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);

        });


    /*合并表*/
    $("#ddsctjStatisGrid").datagrid({
        pagination:false,
        onLoadSuccess:function (data) {
            if (data.rows.length > 0){
                mergeCellsByField("ddsctjStatisGrid", "SLRY");
            }
        }
    });

    function mergeCellsByField(tableID, colList) {
        var ColArray = colList.split(",");
        var tTable = $("#" + tableID);
        var TableRowCnts = tTable.datagrid("getRows").length;   //统计表格行数 668
        var tmpA;
        var tmpB;
        var PerTxt = "";
        var CurTxt = "";
        var alertStr = "";
        for (j = ColArray.length - 1; j >= 0; j--){
            PerTxt = "";
            tmpA = 1;
            tmpB = 0;
            for (i = 0; i <= TableRowCnts; i++){
                if (i == TableRowCnts) {
                    CurTxt = "";
                }
                else {
                    CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                }
                if (PerTxt == CurTxt) {
                    tmpA += 1;
                }else {
                    tmpB += tmpA;

                    tTable.datagrid("mergeCells", {
                        index: i - tmpA,
                        field: ColArray[j],　　//合并字段
                        rowspan: tmpA,
                        colspan: null
                    });
                    tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
                        index: i - tmpA,
                        field: "Ideparture",
                        rowspan: tmpA,
                        colspan: null
                    });

                    tmpA = 1;
                }
                PerTxt = CurTxt;
            }
        }
    }

    /*导出数据*/
    function exportDdsctjExcel() {
        var beginDate = $("input[name='Q_a.create_time_>=']").val();
        var endDate = $("input[name='Q_a.create_time_<=']").val();
        var url = "statisticsNewController.do?exportDdsctjExcel&beginDate="+beginDate+"&endDate="+endDate;
        if(null!=beginDate&&''!=beginDate){
            url+="&Q_a.CREATE_TIME_GE="+beginDate+" 00:00:00";
        }
        if(null!=endDate&&''!=endDate){
            url+="&Q_a.CREATE_TIME_LE="+endDate+" 23:59:59";
        }
        window.location.href = url;
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="ddsctjStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportDdsctjExcel();">导出数据</a>
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
                                                        id="SysLogL.OPERATE_TIME_BEGIN_DDSC" name="Q_a.create_time_>=" /></td>
                        <td style="width:68px;text-align:right;">结束日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_DDSC" name="Q_a.create_time_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('ddsctjStatisToolBar','ddsctjStatisGrid')" />
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
            <table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
                   id="ddsctjStatisGrid" fitcolumns="false" toolbar="#ddsctjStatisToolBar"
                   method="post" idfield="LOG_ID" checkonselect="false"
                   selectoncheck="false" fit="true" border="false"
                   url="">
                <thead>
                    <tr>
                        <th data-options="field:'SLRY',align:'center'" width="10%" >受理人员</th>
                        <th data-options="field:'SXLB',align:'center'" width="15%" >事项类别（含VIP）</th>
                        <th data-options="field:'YWLX',align:'center'" width="15%" >业务类型</th>
                        <th data-options="field:'QHS',align:'center'" width="10%" >取号数</th>
                        <th data-options="field:'GHS',align:'center'" width="10%" >过号数</th>
                        <th data-options="field:'YSLS',align:'center'" width="10%" >已受理数</th>
                        <th data-options="field:'PJDHSC',align:'center'" width="15%" >平均等候时长(分钟)</th>
                    </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>

