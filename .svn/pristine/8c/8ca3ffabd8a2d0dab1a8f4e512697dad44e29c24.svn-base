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
                elem : "#SysLogL.OPERATE_TIME_BEGIN_JXKHB",
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
                elem : "#SysLogL.OPERATE_TIME_END_JXKHB",
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

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="jxkhdfStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
<%--            <div class="right-con">--%>
<%--                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">--%>
<%--                    <div class="e-toolbar-ct">--%>
<%--                        <div class="e-toolbar-overflow">--%>

<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">开始日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_BEGIN_JXKHB" name="Q_a.create_time_>="/></td>
                        <td style="width:68px;text-align:right;">结束日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_JXKHB" name="Q_a.create_time_<=" /></td>
                        <td style="width:68px;text-align:right;">部门：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_b.DEPART_NAME_LIKE" name="Q_b.DEPART_NAME_LIKE" /></td>
                    </tr>
                    <tr>
                        <td style="width:68px;text-align:right;">姓名：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_a.FULLNAME_LIKE" name="Q_a.FULLNAME_LIKE" /></td>
                        <td style="width:68px;text-align:right;">账号：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_a.USERNAME_LIKE" name="Q_a.USERNAME_LIKE" /></td>
                        <td style="width:60px;">岗位类型：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:150px;" name="Q_a.GWLX_EQ"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=GWLX',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('jxkhdfStatisToolBar','jxkhdfStatisGrid')" />
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
                   id="jxkhdfStatisGrid" fitcolumns="false" toolbar="#jxkhdfStatisToolBar"
                   method="post" checkonselect="false"
                   selectoncheck="false" fit="true" border="false"
                   url="statisticsNewController.do?jxkhdfCal">
                <thead>
                <tr>
                    <th data-options="field:'DEPART_NAME',align:'center'" rowspan="2">部门名称</th>
                    <th data-options="field:'ZB',align:'center'" rowspan="2">组别</th>
                    <th data-options="field:'GWLX',align:'center'" rowspan="2">岗位类型</th>
                    <th data-options="field:'FULLNAME',align:'center'" rowspan="2">姓名</th>
                    <th data-options="field:'USERNAME',align:'center'" rowspan="2">账号</th>
                    <th colspan="4">办件得分</th>
                    <th colspan="3">重点工作落实加分</th>
                    <th data-options="field:'ZFJLKF',align:'center'" rowspan="2">作风纪律扣分</th>
                    <th data-options="field:'DFHJ',align:'center'" rowspan="2">得分合计</th>
                    <th data-options="field:'ZHJXBT',align:'center'" rowspan="2">折合绩效补贴</th>
                </tr>
                <tr>
                    <th data-options="field:'JCF',align:'center'" width="10%">基础分</th>
                    <th data-options="field:'SLDF',align:'center'" width="10%">受理得分</th>
                    <th data-options="field:'SHDF',align:'center'" width="10%">审核得分</th>
                    <th data-options="field:'BJXJ',align:'center'" width="10%">小计</th>

                    <th data-options="field:'GRJF',align:'center'" width="10%">个人加分</th>
                    <th data-options="field:'JTJF',align:'center'" width="10%">集体加分</th>
                    <th data-options="field:'JFXJ',align:'center'" width="10%">小计</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
