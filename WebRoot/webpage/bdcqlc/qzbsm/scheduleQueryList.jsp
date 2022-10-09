<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
function formatSubject(val,row) {
    var exeId = row.EXE_ID;
    var href = "<a href='";
    href += "executionController.do?goDetail&exeId="+exeId;
    href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
    return href;
}

$(document).ready(function() {
    var start1 = {
        elem : "#scheduleQuery.CREATE_TIME_BEGIN",
        format : "YYYY-MM-DD",
        istime : false,
        choose : function(datas) {
            var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
            var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
            if (beginTime != "" && endTime != "") {
                var start = new Date(beginTime);
                var end = new Date(endTime);
                if (start > end) {
                    alert("开始时间必须小于等于结束时间,请重新输入!");
                    $("input[name='Q_T.CREATE_TIME_>=']").val("");
                }
            }
        }
    };
    var end1 = {
        elem : "#scheduleQuery.CREATE_TIME_END",
        format : "YYYY-MM-DD",
        istime : false,
        choose : function(datas) {
            var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
            var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
            if (beginTime != "" && endTime != "") {
                var start = new Date(beginTime);
                var end = new Date(endTime);
                if (start > end) {
                    alert("结束时间必须大于等于开始时间,请重新输入!");
                    $("input[name='Q_T.CREATE_TIME_<=']").val("");
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
        <div id="scheduleQueryToolbar">
            <form action="#" name="scheduleQueryForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申报号</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.EXE_ID_LIKE" /></td>
                        <td style="width:68px;text-align:right;">发起人</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.CREATOR_NAME_LIKE" /></td>
                        <td style="width:68px;text-align:right;">流程标题</td>
                        <td style="width:135px;">
                            <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
                        </td>
                    </tr>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申请日期从</td>
                        <td style="width:135px;padding-left:4px;"><input type="text"
                                                                         style="width:108px;float:left;" class="laydate-icon"
                                                                         id="scheduleQuery.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
                        <td style="width:68px;text-align:right;">申请日期至</td>
                        <td style="width:135px;padding-left:4px;"><input type="text"
                                                                         style="width:108px;float:left;" class="laydate-icon"
                                                                         id="scheduleQuery.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
                        <td style="width:68px;text-align:right;">申请人</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.SQRMC_LIKE" /></td>
                        <td colspan="2"></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('scheduleQueryToolbar','scheduleQueryGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('scheduleQueryForm')" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="scheduleQueryGrid" fitcolumns="false" toolbar="#scheduleQueryToolbar"
               method="post" checkonselect="false"
               selectoncheck="false" fit="true" border="false" nowrap="false"
               url="bdcQlcSffzInfoController.do?sheduleQueryDatagrid">
            <thead>
            <tr>
                <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                <th data-options="field:'SUBJECT',align:'left'" width="30%" formatter="formatSubject">流程标题</th>
                <th data-options="field:'CREATOR_NAME',align:'left'" width="8%">发起人</th>
                <th data-options="field:'SQRMC',align:'left'" width="15%">申请人</th>
                <th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
                <th data-options="field:'RUN_STATUS',align:'left'" width="15%" formatter="FlowUtil.formatRunStatus">流程状态</th>
                <th data-options="field:'CUR_STEPNAMES',align:'left'" width="15%">当前流程环节</th>
                <th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="20%">处理人</th>
            </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>
