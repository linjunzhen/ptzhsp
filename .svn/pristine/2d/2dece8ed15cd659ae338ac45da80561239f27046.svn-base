<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<script type="text/javascript">
    <!-- 查询事件开始 -->
    function gridDoSearchByParam(EstateToolbar, EstateGrid) {
        var queryName = $("#queryName").val();
        $("input[name='Q_T.NAME_LIKE']").val(queryName);
        $("input[name='OBLIGEE_NAME']").val(queryName);
        var queryIdNum = $("#queryIdNum").val();
        $("input[name='Q_T.IDNUM_LIKE']").val(queryIdNum);
        $("input[name='OBLIGEE_IDNUM']").val(queryIdNum);
        var layload = layer.load('正在查询，请稍等…');
        setTimeout(function(){
            layer.close(layload);
            AppUtil.gridDoSearch(EstateToolbar, EstateGrid);
        },4000);
    }
    <!-- 查询事件结束 -->
    function errorShow(data) {
        var name = $("input[name='OBLIGEE_NAME']").val();
        var id = $("input[name='OBLIGEE_IDNUM']").val();
        var flowSubmitObj = FlowUtil.getFlowObj();
        var runstatus1 = $("input[name='runstatus1']").val();
        if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES = "开始" && runstatus1 != "1" && runstatus1 != "2") {
            if (name != '' || id != '') {
                var total = $("#EstateGrid").datagrid("getData").total;
                if (total == 0) {
                    $("#EstateGrid").datagrid('appendRow', {NAME: '<div style="text-align:center;color:red">查询接口异常,请联系管理员</div>'}).datagrid('mergeCells', {index: 0, field: 'NAME', colspan: 10});
                }
            }
        }
    }
    function successShow(data){
        var  name= $("input[name='OBLIGEE_NAME']").val();
        var id=$("input[name='OBLIGEE_IDNUM']").val();
        if(name!=''||id!=''){
            var total= $("#EstateGrid").datagrid("getData").total;
            if(total==0){
                $("#EstateGrid").datagrid('appendRow', {NAME: '<div style="text-align:center;color:red">查询无记录</div>'}).datagrid('mergeCells', {index: 0, field: 'NAME', colspan: 10});
            }
            }
    }
</script>
<div id="DATA_QUERY_1" style="display:none;">
    <input class="eve-input" type="hidden" name="OBLIGEE_NAME"
           value="${busRecord.OBLIGEE_NAME}"/>
    <input class="eve-input"
           type="hidden" name="OBLIGEE_IDNUM" value="${busRecord.OBLIGEE_IDNUM}"/>


    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2 dataquery">
        <tr>
            <th colspan="4">查档证明</th>
        </tr>
        <tr>
            <div id="DATA_QUERY_1_div">
                <!-- =========================查询面板开始========================= -->
                <div id="EstateToolbar">
                    <input class="eve-input" type="hidden" name="Q_T.NAME_LIKE"
                           value="${busRecord.OBLIGEE_NAME}"/> <input class="eve-input" type="hidden"
                                                                      name="Q_T.IDNUM_LIKE" value="${busRecord.OBLIGEE_IDNUM}"/>
                    <!--====================开始编写隐藏域============== -->
                    <!--====================结束编写隐藏域============== -->
                    <form action="#" name="EstateForm">
                        <table class="dddl-contentBorder dddl_table"
                               style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                            <tbody>
                            <tr style="height:28px;">
                                <td style="width:68px;text-align:right;">姓名:</td>
                                <td style="width:135px;"><input class="eve-input"
                                                                id="queryName" type="text" maxlength="20" style="width:228px;"
                                                                value="${busRecord.OBLIGEE_NAME}" name="Q_T.OBLIGEE_NAME_LIKE"/></td>
                                <td style="width:168px;text-align:right;">身份证号:</td>
                                <td style="width:235px;"><input class="eve-input"
                                                                id="queryIdNum" type="text" maxlength="20"
                                                                style="width:228px;" value="${busRecord.OBLIGEE_IDNUM}"
                                                                name="Q_T.OBLIGEE_IDNUM_LIKE"/></td>
                                <td colspan="6"><input type="button" value="查询" id="queryButton"
                                                       class="eve-button"
                                                       onclick="gridDoSearchByParam('EstateToolbar','EstateGrid')"/> <a
                                        style="width: 25%;margin-left: 50%;display:none; " class="readCardButton"
                                        id="printQueryCertify"
                                        onclick="showTemplate(2,'bdcApoveHave.doc','不动产查档证明表')">打印查档证明</a>
                                </td>

                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <!-- =========================查询面板结束========================= -->
                <!-- =========================表格开始=========================-->
                <table class="easyui-datagrid" rownumbers="true" id="EstateGrid"
                       fitcolumns="false" toolbar="#EstateToolbar" method="post"
                       idfield="OB_ID" checkonselect="false" selectoncheck="false"
                       fit="false" border="false"  data-options="onLoadSuccess:function(data){
			     successShow(data);
			},onLoadError:function(data){
			    errorShow(data);
			}"
                       url="bdcQueryController.do?queryDatagrid&EXE_ID=${execution.EXE_ID}&APPLY_STATUS=${execution.APPLY_STATUS}">
                    <thead>
                    <tr>
                        <th field="ck" checkbox="true"></th>
                        <th data-options="field:'OB_ID',hidden:true" width="80">OB_ID</th>
                        <th data-options="field:'NAME',align:'left'" width="60">姓名</th>
                        <th data-options="field:'IDNUM',align:'left'" width="150">身份证号</th>
                        <th data-options="field:'PROPERTY_TYPE',align:'left'" width="100">产权类型</th>
                        <th data-options="field:'PROPERTY_STATUS',align:'left'"
                            width="100">产权状态
                        </th>
                        <th data-options="field:'PROPERTY_HUMAN',align:'left'"
                            width="100">权利人
                        </th>
                        <th data-options="field:'PROPERTY_ADDR',align:'left'" width="150">不动产坐落</th>
                        <th data-options="field:'PROPERTY_NUM',align:'left'" width="150">证号</th>
                        <th data-options="field:'PROPERTY_TIME',align:'left'" width="100">登记时间</th>
                        <th data-options="field:'PROPERTY_AREA',align:'left'" width="60">面积</th>
                        <th data-options="field:'PROPERTY_USE',align:'left'" width="200">用途</th>
                        <th data-options="field:'PROPERTY_NATURE',align:'left'"
                            width="60">性质
                        </th>
                    </tr>
                    </thead>
                </table>
                <!-- =========================表格结束==========================-->
            </div>

        </tr>
    </table>
</div>

