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
    function exportProjectNodeExcel() {
        var url = "projectItemController.do?exportProjectNodeExcel";
        window.location.href = url;
    }

    function formatTaskStatus(val,row){
        var shzt = "";
        if(val=="1"){
            shzt = "<font color='green'><b>正在审核</b></font>";
        }else if(val=="2"){
            shzt = "<font color='blue'><b>已审核</b></font>";
        }else if(val=="3"){
            shzt = "<font color='red'><b>退回</b></font>";
        }else if(row.TASK_NODENAME == "开始" && val == "-1" ){
            shzt = "<font color='purple'><b>退回补件</b></font>";
        }else if(val=="4"){
            shzt = "<font color='purple'><b>转发</b></font>";
        }else if(val=="5"){
            shzt = "<font color='purple'><b>委托</b></font>";
        }else if(val=="6"){
            shzt = "<font color='black'><b>结束流程</b></font>";
        }else if(val=="-1"){
            shzt = "<font color='purple'><b>挂起</b></font>";
        }else if(val=="7"){
            return "<font color='purple'><b>申请人撤回</b></font>";
        }
        return shzt;
    }
</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="projectNodeStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportProjectNodeExcel();">导出数据</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
<%--                    <tr style="height:28px;">--%>
<%--                        <td style="width:68px;text-align:right;">开始日期</td>--%>
<%--                        <td style="width:135px;"><input type="text"--%>
<%--                                                        style="width:128px;float:left;" class="laydate-icon"--%>
<%--                                                        id="SysLogL.OPERATE_TIME_BEGIN_GCJSXMCX" name="Q_A.CREATE_TIME_>=" /></td>--%>
<%--                        <td style="width:68px;text-align:right;">结束日期</td>--%>
<%--                        <td style="width:135px;"><input type="text"--%>
<%--                                                        style="width:128px;float:left;" class="laydate-icon"--%>
<%--                                                        id="SysLogL.OPERATE_TIME_END_GCJSXMCX" name="Q_A.CREATE_TIME_<=" /></td>--%>
<%--                        <td style="width:68px;text-align:right;">项目编码</td>--%>
<%--                        <td style="width:135px;"><input type="text"--%>
<%--                                                        style="width:128px;float:left;height: 20px;"--%>
<%--                                                        id="Q_a.PROJECT_CODE_LIKE" name="Q_a.PROJECT_CODE_LIKE" /></td>--%>
<%--                        <td style="width:68px;text-align:right;">项目名称</td>--%>
<%--                        <td style="width:135px;"><input type="text"--%>
<%--                                                        style="width:128px;float:left;height: 20px;"--%>
<%--                                                        id="Q_a.PROJECT_NAME_LIKE" name="Q_a.PROJECT_NAME_LIKE" /></td>--%>
<%--                        <td colspan="2"><input type="button" value="查询"--%>
<%--                                               class="eve-button"--%>
<%--                                               onclick="AppUtil.gridDoSearch('projectNodeStatisToolBar','projectNodeStatisGrid')" />--%>
<%--                            <input type="button" value="重置" class="eve-button"--%>
<%--                                   onclick="AppUtil.gridSearchReset('SysLogForm')" /></td>--%>
<%--                    </tr>--%>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <div class="gridtable">
            <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
                   id="projectNodeStatisGrid" fitcolumns="false" toolbar="#projectNodeStatisToolBar"
                   method="post" idfield="EXE_ID" checkonselect="false" nowrap="false"
                   selectoncheck="false" fit="true" border="false"
                   url="projectItemController.do?projectNodeData">
                <thead>
                <tr>
                    <th data-options="field:'EXE_ID',align:'center'" width="10%">申报号</th>
                    <th data-options="field:'PROJECT_CODE',align:'center'" width="15%">项目编码</th>
                    <th data-options="field:'SUBJECT',align:'center'" width="30%">项目名称</th>
                    <th data-options="field:'ENTERPRISE_NAME',align:'center'" width="15%">业主单位名称</th>
                    <th data-options="field:'CONTACT_NAME',align:'center'" width="10%">负责人</th>
                    <th data-options="field:'CONTACT_TEL',align:'center'" width="10%">联系电话</th>
                    <th data-options="field:'ITEM_NAME',align:'center'" width="20%">事项名称</th>
                    <th data-options="field:'TASK_NODENAME',align:'center'" width="10%">当前环节</th>
                    <th data-options="field:'TASK_STATUS',align:'center'" width="10%" formatter="formatTaskStatus">环节状态</th>
                    <th data-options="field:'ASSIGNER_NAMES',align:'center'" width="20%">审核人</th>
                    <th data-options="field:'CNQXGZR',align:'center'" width="10%">承诺时限</th>
                    <th data-options="field:'CREATE_TIME',align:'center'" width="10%">办件申请时间</th>
                    <th data-options="field:'TASK_DEADTIME',align:'center'" width="10%">办件截至时间</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
