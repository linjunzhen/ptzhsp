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
                elem : "#SysLogL.OPERATE_TIME_BEGIN_JXJFKF",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.JFKF_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.JFKF_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_A.JFKF_DATE_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_JXJFKF",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_A.JFKF_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_A.JFKF_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_A.JFKF_DATE_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
        });

    function reviseJxjfkfStatis() {
        var entityId = AppUtil.getEditDataGridRecord("jxjfkfStatisGrid");
        if (entityId) {
            addJxjfkfStatis(entityId);
        }
    }


    /*新增*/
    function addJxjfkfStatis(entityId) {
        $.dialog.open("statisticsNewController.do?addJxjfkfStatis&JFKF_ID=" + entityId , {
            title : "加分扣分信息",
            width : "800px",
            height : "300px",
            lock : true,
            resize : false
        },false)
    }
    
    function deleteJxjfkfStatis() {
        AppUtil.deleteDataGridRecord("statisticsNewController.do?deleteJxjfkfStatis", "jxjfkfStatisGrid");
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="jxjfkfStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-plus" plain="true" onclick="addJxjfkfStatis();">新增</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="reviseJxjfkfStatis();">编辑</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-trash-o" plain="true" onclick="deleteJxjfkfStatis();">删除</a>
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
                                                        id="SysLogL.OPERATE_TIME_BEGIN_JXJFKF" name="Q_A.JFKF_DATE_>=" /></td>
                        <td style="width:68px;text-align:right;">结束日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_JXJFKF" name="Q_A.JFKF_DATE_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('jxjfkfStatisToolBar','jxjfkfStatisGrid')" />
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
                   id="jxjfkfStatisGrid" fitcolumns="false" toolbar="#jxjfkfStatisToolBar"
                   method="post" idfield="JFKF_ID" checkonselect="true"
                   selectoncheck="true" fit="true" border="false"
                   url="statisticsNewController.do?jxjfkfData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true" colspan="1"></th>
                    <th data-options="field:'JFKF_DATE',align:'center'" width="150px">操作时间</th>
                    <th data-options="field:'FULLNAME',align:'center'" width="100px">操作人员</th>
                    <th data-options="field:'JFKF_TYPE',align:'center'" width="100px">加分扣分</th>
                    <th data-options="field:'JF_TYPE',align:'center'" width="100px">类型</th>
                    <th data-options="field:'JFKF_USERNAMES',align:'center'" width="500px">人员</th>
                    <th data-options="field:'JFKF_CONTENT',align:'center'" width="300px">原因</th>
                    <th data-options="field:'JFKF_FZ',align:'center'" width="100px">分值</th>
                    <th data-options="field:'JFKF_EFFECT_DATE',align:'center'" width="100px">生效时间</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>