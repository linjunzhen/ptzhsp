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
                elem : "#SysLogL.OPERATE_TIME_BEGIN_GCJSXMCX",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_A.CREATE_TIME_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_GCJSXMCX",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.CREATE_TIME_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.CREATE_TIME_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_A.CREATE_TIME_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
        });

    function reviseGcjsxmcx() {
        var PROJECT_CODE = AppUtil.getEditDataGridRecord("gcjsxmcxStatisGrid");
        if (PROJECT_CODE != null && PROJECT_CODE.length > 0) {
            $.dialog.open("statisticsNewController.do?gcjsxmcxReviseView&PROJECT_CODE=" + PROJECT_CODE , {
                title : "修改",
                width : "600px",
                height : "150px",
                lock : true,
                resize : false
            },false)
        }
    }

    function downLoadGcjsxmcxView() {
        var PROJECT_CODE = AppUtil.getEditDataGridRecord("gcjsxmcxStatisGrid");
        if (PROJECT_CODE != null && PROJECT_CODE.length > 0) {
            $.dialog.open("statisticsNewController.do?downLoadGcjsxmcxView&PROJECT_CODE=" + PROJECT_CODE , {
                title : "下载材料",
                width : "600px",
                height : "400px",
                lock : true,
                resize : false
            },false)
        }
    }

    function gcjsxmcxDealItem() {
        var PROJECT_CODE = AppUtil.getEditDataGridRecord("gcjsxmcxStatisGrid");
        if (PROJECT_CODE != null && PROJECT_CODE.length > 0) {
            $.dialog.open("statisticsNewController.do?gcjsxmcxDealItem&PROJECT_CODE=" + PROJECT_CODE , {
                title : "当前已办事项查询",
                width : "1100px",
                height : "500px",
                lock : true,
                resize : false
            },false)
        }
    }

    function formateYN(val,row) {
         if (val == 1) {
             return '是';
         } else {
             return '否';
         }
    }

    function formateBLZT(val,row) {
        if (val == null || val == '' || val.length == 0) {
            return "暂未办理事项";
        } else {
            return val;
        }
    }
</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="gcjsxmcxStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-download" plain="true" onclick="downLoadGcjsxmcxView();">下载材料</a>
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="reviseGcjsxmcx();">修改是否联系</a>
                                <a href="#" class="easyui-linkbutton" iconcls="eicon-list" plain="true" onclick="gcjsxmcxDealItem();">当前已办事项</a>
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
                                                            id="SysLogL.OPERATE_TIME_BEGIN_GCJSXMCX" name="Q_A.CREATE_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">结束日期</td>
                            <td style="width:135px;"><input type="text"
                                                            style="width:128px;float:left;" class="laydate-icon"
                                                            id="SysLogL.OPERATE_TIME_END_GCJSXMCX" name="Q_A.CREATE_TIME_<=" /></td>
                            <td style="width:68px;text-align:right;">项目编码</td>
                            <td style="width:135px;"><input type="text"
                                                            style="width:128px;float:left;height: 20px;"
                                                            id="Q_a.PROJECT_CODE_LIKE" name="Q_a.PROJECT_CODE_LIKE" /></td>
                            <td style="width:68px;text-align:right;">项目名称</td>
                            <td style="width:135px;"><input type="text"
                                                            style="width:128px;float:left;height: 20px;"
                                                            id="Q_a.PROJECT_NAME_LIKE" name="Q_a.PROJECT_NAME_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                                   class="eve-button"
                                                   onclick="AppUtil.gridDoSearch('gcjsxmcxStatisToolBar','gcjsxmcxStatisGrid')" />
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
                   id="gcjsxmcxStatisGrid" fitcolumns="false" toolbar="#gcjsxmcxStatisToolBar"
                   method="post" idfield="PROJECT_CODE" checkonselect="true"
                   selectoncheck="true" fit="true" border="false"
                   url="statisticsNewController.do?gcjsxmcxData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true" colspan="1"></th>
                    <th data-options="field:'PROJECT_CODE',align:'center'" width="20%">项目编码</th>
                    <th data-options="field:'PROJECT_NAME',align:'center'" width="35%">项目名称</th>
                    <th data-options="field:'ENTERPRISE_NAME',align:'center'" width="20%">法人单位</th>
                    <th data-options="field:'CONTACT_NAME',align:'center'" width="10%">联系人</th>
                    <th data-options="field:'CONTACT_PHONE',align:'center'" width="10%">联系人电话</th>
                    <th data-options="field:'CREATE_TIME',align:'center'" width="10%">申报时间</th>
<%--                    <th data-options="field:'BLZT',align:'center'" width="200" formatter="formateBLZT">当前办理状态</th>--%>
                    <th data-options="field:'SFLXYZ',align:'center'" width="10%" formatter="formateYN">是否已联系业主</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
