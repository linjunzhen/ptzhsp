<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

    /**
     * 编辑流程实例列表记录
     */
    function editBdcDwspGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("BdcMyQzjffzGrid");
        if (entityId) {
            showExecutionWindow(entityId);
        }
    }
    
    function formatLzState(val,row){
        if(val=="1"){
            return "<font color='#0368ff'><b>已领取</b></font>";
        }else{
            return "<font color='#red'><b>待领取</b></font>";
        }
    }
    
    function formatSubject3(val,row){
        //获取流程状态
        var exeId = row.EXE_ID;
        var href = "<a href='";
        href += "executionController.do?goBdcFzjfmx&exeId="+exeId+"&bdc_optype=flag2";
        /* if(taskStatus!="1"){
            
        }else{
            href += "executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId;
        } */
        href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
        return href;
    }
    
    function formatPrintState(val,row){
        if(val=="1"){
            return "<font color='#0368ff'><b>已打证</b></font>";
        }else{
            return "<font color='#red'><b>否</b></font>";
        }
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
            elem : "#BdcQlcMyjffzT.CREATE_TIME_BEGIN",
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
            elem : "#BdcQlcMyjffzT.CREATE_TIME_END",
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

    //空数据，横向滚动
    $('#BdcMyQzjffzGrid').datagrid({
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
        <div id="BdcMyQzjffzToolbar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <%-- <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <div style="color:#005588;">
                                <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                    style="position: relative; top:7px; float:left;" />&nbsp;待审批列表
                                <a href="#"
                                class="easyui-linkbutton" reskey="SHOW_Bjxx"
                                iconcls="eicon-eye" plain="true"
                                onclick="showBdcDwspBjxxGridRecord();">查看补件要求</a>
                               <a href="#"
                               class="easyui-linkbutton"
                               iconcls="icon-tick" plain="true"
                               onclick="restartFlowTask();">重启流程任务</a>
                                <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-play" plain="true"
                                onclick="restartFlowTaskExplain();">重启流程任务</a>
                            </div>
                             
                        </div>
                    </div>
                </div>
            </div> --%>
            <form action="#" name="BdcMyjffzForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">申报号</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.EXE_ID_LIKE" /></td>
                            <td style="width:68px;text-align:right;">发起人</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.CREATOR_NAME_LIKE" /></td>
                            <td style="width:68px;text-align:right;">流程标题</td>
                            <td style="width:135px;">
                            <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.SUBJECT_LIKE" />
                            </td>
                            <td colspan="2"></td>
                        </tr>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">申请日期从</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="BdcQlcMyjffzT.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" /></td>
                            <td style="width:68px;text-align:right;">申请日期至</td>
                            <td style="width:135px;padding-left:4px;"><input type="text"
                                style="width:108px;float:left;" class="laydate-icon"
                                id="BdcQlcMyjffzT.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" /></td>
                            <td style="width:68px;text-align:right;">申请人</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_E.SQRMC_LIKE" /></td>
                            <td colspan="2"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('BdcMyQzjffzToolbar','BdcMyQzjffzGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('BdcMyjffzForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
            id="BdcMyQzjffzGrid" fitcolumns="false" toolbar="#BdcMyQzjffzToolbar"
            method="post" idfield="EVEID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="bdcQlcSffzInfoController.do?myJffzListdatagrid">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'EVEID',hidden:true">EVEID</th>
                    <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                    <th data-options="field:'SUBJECT',align:'left'" width="30%" formatter="formatSubject3">流程标题</th>
                    <th data-options="field:'CREATOR_NAME',align:'left'" width="8%">发起人</th>
                    <th data-options="field:'APPLYTIME',align:'left'" width="15%">申请时间</th>
                    <th data-options="field:'SQRMC',align:'left'" width="15%">申请人</th>
                    <!-- <th data-options="field:'JBR_NAME',align:'left'" width="8%">经办人</th> -->
                    <th data-options="field:'QZPRINT_STATE',align:'center'" formatter="formatPrintState" width="8%">打证状态</th>
                    <th data-options="field:'LZ_STATE',align:'center'" formatter="formatLzState" width="8%">领证状态</th>
                </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>
