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
        var sxxsszDateMonth = new Date().getMonth();
        var sxxsszDateMonth = sxxsszDateMonth.toString() < 10 ? '0' + sxxsszDateMonth : '' + sxxsszDateMonth
        var sxxsszDateYear = new Date().getFullYear();
        var sxxsszDate = sxxsszDateYear + '-' + sxxsszDateMonth + '-01'
        $("#sxxsszStatisGrid").attr("url", "statisticsNewController.do?sxxsszData&Q_C.CREATE_TIME_GE=" + sxxsszDate + " 00:00:00");
        $("input[name='Q_C.CREATE_TIME_>=']").val(sxxsszDate)
    }

    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_SXXSSZ",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_C.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_C.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_C.CREATE_TIME_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_SXXSSZ",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_C.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_C.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_C.CREATE_TIME_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);


        });


    /*导出数据*/
    function exportSxxsszExcel() {
        var beginDate = $("input[name='Q_C.CREATE_TIME_>=']").val();
        var endDate = $("input[name='Q_C.CREATE_TIME_<=']").val();
        var ITEM_NAME = $("input[name='ITEM_NAME']").val();
        var SSCS = $("input[name='SSCS']").val();
        var BJLX = $("input[name='BJLX']").val();
        var YCTB = $("input[name='YCTB']").val();
        var url = "statisticsNewController.do?exportSxxsszExcel&beginDate="+beginDate+"&endDate="+endDate +"&YS_EX=1";
        if(null!=beginDate&&''!=beginDate){
            url+="&Q_c.CREATE_TIME_GE="+beginDate+" 00:00:00";
        }
        if(null!=endDate&&''!=endDate){
            url+="&c.CREATE_TIME_LE="+endDate+" 23:59:59";
        }
        if(null!=ITEM_NAME&&''!=ITEM_NAME){
            url+="&ITEM_NAME="+ITEM_NAME;
        }
        if(null!=SSCS&&''!=SSCS){
            url+="&SSCS="+SSCS;
        }
        if(null!=BJLX&&''!=BJLX){
            url+="&BJLX="+BJLX;
        }
        if(null!=YCTB&&''!=YCTB){
            url+="&YCTB="+YCTB;
        }
        window.location.href = url;
    }

    function reviseSxxsszExcel() {
        var itemCode = AppUtil.getEditDataGridRecord("sxxsszStatisGrid")
        if (itemCode != null && itemCode.length > 0) {
            $.dialog.open("statisticsNewController.do?sxxsszReviseView&itemCode=" + itemCode , {
                title : "修改系数",
                width : "600px",
                height : "200px",
                lock : true,
                resize : false
            },false)
        }
    }

</script>
<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="sxxsszStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportSxxsszExcel();">导出数据</a>
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="reviseSxxsszExcel();">修改系数</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">开始日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_BEGIN_SXXSSZ" name="Q_C.CREATE_TIME_>=" /></td>
                        <td style="width:68px;text-align:right;">结束日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_SXXSSZ" name="Q_C.CREATE_TIME_<=" /></td>
                                                        
                        <td style="width:68px;text-align:right;">项目名称：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="ITEM_NAME" name="ITEM_NAME" /></td>
                        <td style="width:68px;text-align:right;">所属处室：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="SSCS" name="SSCS" /></td>
                    </tr>
                    <tr style="">
                        <td style="width:68px;text-align:right;">办件类型：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:128px;" name="BJLX"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
                        <td style="width:100px;text-align:right;">是否一窗通办：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:128px;" name="YCTB"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('sxxsszStatisToolBar','sxxsszStatisGrid')" />
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
                   id="sxxsszStatisGrid" fitcolumns="false" toolbar="#sxxsszStatisToolBar"
                   method="post" idfield="SXBM" checkonselect="true" nowrap="false"
                   selectoncheck="true" fit="true" border="false"
                   url="">
                <thead>
                <tr>
                    <th field="ck" checkbox="true" rowspan="2"></th>
                    <th data-options="field:'ZXMC',align:'center'" rowspan="2" width="25%">子项名称</th>
                    <th data-options="field:'SSCS',align:'center'" rowspan="2" width="15%">所属处室</th>
                    <th data-options="field:'PJBJSC',align:'center'" rowspan="2" width="10%">平均办结时长</th>
                    <th data-options="field:'PJSLSC',align:'center'" rowspan="2" width="10%">平均受理时长</th>
                    <th data-options="field:'BJLX',align:'center'" rowspan="2" width="10%">办件类型</th>
                    <th data-options="field:'YCTB',align:'center'" rowspan="2" width="10%">一窗通办</th>
                    <th colspan="2">工作日系数</th>
                    <th colspan="2">工作日加班系数</th>
                    <th colspan="2">节假日加班系数</th>
                </tr>
                <tr>
                    <th data-options="field:'GZRSL',align:'center'" width="5%">受理</th>
                    <th data-options="field:'GZRSH',align:'center'" width="5%">审核</th>

                    <th data-options="field:'XXRSL',align:'center'" width="5%">受理</th>
                    <th data-options="field:'XXRSH',align:'center'" width="5%">审核</th>

                    <th data-options="field:'JJRSL',align:'center'" width="5%">受理</th>
                    <th data-options="field:'JJRSH',align:'center'" width="5%">审核</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>