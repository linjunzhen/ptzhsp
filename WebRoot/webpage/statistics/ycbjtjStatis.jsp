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

    parent.art.dialog({
        content: "请输入时间查询！",
        icon:"warning",
        ok: true
    });
    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_BJTJ",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_E.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_E.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_E.CREATE_TIME_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_BJTJ",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_E.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_E.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_E.CREATE_TIME_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);

        });

    $('#ycbjtjStatisGrid').datagrid({ pagination: false,
        onLoadSuccess: function (data) {
            if (data.rows.length > 0) {
                //调用mergeCellsByField()合并单元格
                mergeCellsByField("ycbjtjStatisGrid", "SLRY");
            }
        }
    });

    /**
     * EasyUI DataGrid根据字段动态合并单元格
     * 参数 tableID 要合并table的id
     * 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
     */
    function mergeCellsByField(tableID, colList) {
        var ColArray = colList.split(",");
        var tTable = $("#" + tableID);
        var TableRowCnts = tTable.datagrid("getRows").length;
        var tmpA;
        var tmpB;
        var PerTxt = "";
        var CurTxt = "";
        var alertStr = "";
        for (j = ColArray.length - 1; j >= 0; j--) {
            PerTxt = "";
            tmpA = 1;
            tmpB = 0;

            for (i = 0; i <= TableRowCnts; i++) {
                if (i == TableRowCnts) {
                    CurTxt = "";
                }
                else {
                    CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                }
                if (PerTxt == CurTxt) {
                    tmpA += 1;
                }
                else {
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


    /*导出表格*/
    function exportYcbjtjExcel(){
        var beginDate = $("input[name='Q_E.CREATE_TIME_>=']").val();
        var endDate = $("input[name='Q_E.CREATE_TIME_<=']").val();
        var url = "statisticsNewController.do?exportYcbjtjExcel&beginDate="+beginDate+"&endDate="+endDate;
        if(null!=beginDate&&''!=beginDate){
            url+="&Q_E.CREATE_TIME_GE="+beginDate+" 00:00:00";
        }
        if(null!=endDate&&''!=endDate){
            url+="&Q_E.CREATE_TIME_LE="+endDate+" 23:59:59";
        }
        window.location.href = url;
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="ycbjtjStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportYcbjtjExcel();">导出数据</a>
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
                                                        id="SysLogL.OPERATE_TIME_BEGIN_BJTJ" name="Q_A.CREATE_TIME_>="/></td>
                        <td style="width:68px;text-align:right;">结束日期</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_BJTJ" name="Q_A.CREATE_TIME_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('ycbjtjStatisToolBar','ycbjtjStatisGrid')" />
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
                   id="ycbjtjStatisGrid" fitcolumns="false" toolbar="#ycbjtjStatisToolBar"
                   method="post" idfield="LOG_ID" checkonselect="false"
                   selectoncheck="false" fit="true" border="false"
                   url="statisticsNewController.do?ycbjtjData">
                <thead>
                <tr>
                    <th data-options="field:'SLRY',align:'center'" rowspan="2">受理人员</th>
                    <th data-options="field:'BM',align:'center'" rowspan="2">部门</th>
                    <th colspan="4">办件数量</th>
                    <th colspan="4">平均受理时长</th>
                    <th colspan="4">平均办结时长</th>
                    <th colspan="4">非常满意评议量</th>
                    <th colspan="4">满意评议量</th>
                    <th colspan="4">一般评议量</th>
                    <th colspan="4">不满意评议量</th>
                </tr>
                <tr>

                    <th data-options="field:'BJHJ',align:'center'" width="5%" >合计</th>
                    <th data-options="field:'BJJB',align:'center'" width="5%" >即办件</th>
                    <th data-options="field:'BJPT',align:'center'" width="5%" >普通件</th>
                    <th data-options="field:'BJCN',align:'center'" width="5%" >承诺件</th>

                    <th data-options="field:'SLSCHJ',align:'center'" width="8%" >合计</th>
                    <th data-options="field:'SLSCJB',align:'center'" width="8%" >即办件</th>
                    <th data-options="field:'SLSCPT',align:'center'" width="8%" >普通件</th>
                    <th data-options="field:'SLSCCN',align:'center'" width="8%" >承诺件</th>

                    <th data-options="field:'BJSCHJ',align:'center'" width="8%" >合计</th>
                    <th data-options="field:'BJSCJB',align:'center'" width="8%" >即办件</th>
                    <th data-options="field:'BJSCPT',align:'center'" width="8%" >普通件</th>
                    <th data-options="field:'BJSCCN',align:'center'" width="8%" >承诺件</th>

                    <th data-options="field:'FCMYHJ',align:'center'" width="5%" >合计</th>
                    <th data-options="field:'FCMYJB',align:'center'" width="5%" >即办件</th>
                    <th data-options="field:'FCMYPT',align:'center'" width="5%" >普通件</th>
                    <th data-options="field:'FCMYCN',align:'center'" width="5%" >承诺件</th>

                    <th data-options="field:'MYHJ',align:'center'" width="5%" >合计</th>
                    <th data-options="field:'MYJB',align:'center'" width="5%" >即办件</th>
                    <th data-options="field:'MYPT',align:'center'" width="5%" >普通件</th>
                    <th data-options="field:'MYCN',align:'center'" width="5%" >承诺件</th>

                    <th data-options="field:'YBHJ',align:'center'" width="5%" >合计</th>
                    <th data-options="field:'YBJB',align:'center'" width="5%" >即办件</th>
                    <th data-options="field:'YBPT',align:'center'" width="5%" >普通件</th>
                    <th data-options="field:'YBCN',align:'center'" width="5%" >承诺件</th>

                    <th data-options="field:'BMYHJ',align:'center'" width="5%" >合计</th>
                    <th data-options="field:'BMYJB',align:'center'" width="5%" >即办件</th>
                    <th data-options="field:'BMYPT',align:'center'" width="5%" >普通件</th>
                    <th data-options="field:'BMYCN',align:'center'" width="5%" >承诺件</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
<%--</div>--%>