<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
    /**
     * 删除流程实例列表记录
     */
    function deleteYbJwspGridRecord() {
        AppUtil.deleteDataGridRecord("executionController.do?multiDel",
            "YbJwspGrid");
    };
    /**
     * 编辑流程实例列表记录
     */
    function revokeFlowExe() {
        var exeId = AppUtil.getEditDataGridRecord("YbJwspGrid");
        if (exeId) {
            art.dialog.confirm("您确定要撤回该流程吗?", function() {
                var layload = layer.load("正在提交请求中…");
                $.post("executionController.do?revokeFlow",{
                    exeId:exeId
                }, function(responseText, status, xhr) {
                    layer.close(layload);
                    var resultJson = $.parseJSON(responseText);
                    if(resultJson.success == true){
                        art.dialog({
                            content: resultJson.msg,
                            icon:"succeed",
                            time:3,
                            ok: true
                        });
                        $("#YbJwspGrid").datagrid('reload');
                        $("#NeedMeHandleGrid").datagrid('reload');
                    }else{
                        art.dialog({
                            content: resultJson.msg,
                            icon:"error",
                            ok: true
                        });
                    }
                });
            });
        }
    }

    function formatSubject(val,row){
        //获取流程状态
        var runStatus = row.RUN_STATUS;
        //获取流程申报号
        var exeId = row.EXE_ID;
        //当前环节
        var curTache=row.CUR_STEPNAMES;
        //获取流程申报号
        var defkey= row.DEF_KEY;
        var dicstate= row.DIC_STATE;
        var href = "<a href='";
        if(runStatus=="0"){
            href += "executionController.do?goStart&exeId="+exeId;
        }else if(runStatus=="1"&&curTache=="并联审查"&&dicstate=="1"){
            href += "executionController.do?goDetailGover&exeId="+exeId;
        }else{
            href += "executionController.do?goDetail&exeId="+exeId;
        }
        href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
        return href;
    }

    /**
     * 显示流程实例信息对话框
     */
    function showExecutionWindow(entityId) {
        $.dialog.open("executionController.do?info&entityId=" + entityId, {
            title : "流程实例信息",
            width : "600px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
    };

    $(document).ready(function() {
        var start1 = {
            elem : "#MyApplyExecT.CREATE_TIME_BEGIN",
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
            elem : "#MyApplyExecT.CREATE_TIME_END",
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

        AppUtil.initAuthorityRes("YbJwspToolbar");

    });

    //空数据，横向滚动
    $('#YbJwspGrid').datagrid({
        onLoadSuccess: function(data){
            if(data.total==0){
                var dc = $(this).data('datagrid').dc;
                var header2Row = dc.header2.find('tr.datagrid-header-row');
                dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
            }
        }
    });
</script>
<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="YbJwspToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" reskey="backMyFlow"
                               iconCls="eicon-rotate-left" plain="true"
                               onclick="revokeFlowExe();">撤回流程</a>

                            <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="ExecutionForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申报号</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.EXE_ID_LIKE" /></td>
                        <td style="width:68px;text-align:right;">流程状态</td>
                        <td style="width:135px;"><input class="easyui-combobox"
                                                        style="width:128px;" name="Q_T.RUN_STATUS_="
                                                        data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                        </td>
                        <td style="width:68px;text-align:right;">流程标题</td>
                        <td style="width:135px;">
                            <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
                        </td>
                        <td colspan="2"></td>
                    </tr>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申请日期从</td>
                        <td style="width:135px;padding-left:4px;"><input type="text"
                                                                         style="width:108px;float:left;" class="laydate-icon"
                                                                         id="MyApplyExecT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
                        <td style="width:68px;text-align:right;">申请日期至</td>
                        <td style="width:135px;padding-left:4px;"><input type="text"
                                                                         style="width:108px;float:left;" class="laydate-icon"
                                                                         id="MyApplyExecT.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
                        <td style="width:68px;text-align:right;">申请人</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.SQRMC_LIKE" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('YbJwspToolbar','YbJwspGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('ExecutionForm')" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="YbJwspGrid" fitcolumns="true" toolbar="#YbJwspToolbar"
               method="post" idfield="EXE_ID" checkonselect="false"
               selectoncheck="false" fit="true" border="false" nowrap="false"
               url="ybExecutionController.do?ybHandledByMe">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                <th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程标题</th>
                <th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
                <th data-options="field:'SQRMC',align:'left'" width="8%">申请人</th>
                <th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th>
                <th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
                <th data-options="field:'CUR_STEPNAMES',align:'left'" width="10%" >当前环节</th>
                <th data-options="field:'CUR_STEPAUDITNAMES',align:'left'" width="15%">当前环节审核人</th>
                <th data-options="field:'END_TIME',align:'left'" width="15%">办结时间</th>
                <!--<th data-options="field:'WORK_HOURS',align:'left'" width="100">运行时间</th>-->
            </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>
