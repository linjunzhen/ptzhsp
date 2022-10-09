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

    function showSelectDeparts(){
        var departId = $("input[name='Q_D.DEPART_ID_=']").val();
        $.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
            +"checkCascadeN=", {
            title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_D.DEPART_ID_=']").val(selectDepInfo.departIds);
                    $("input[name='Q_C.DEPART_NAME_EQ']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
    }

    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.APPOINTMENT_BDCQZ_BEGIN",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.APPOINTMENT_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_T.APPOINTMENT_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_T.APPOINTMENT_DATE_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.APPOINTMENT_BDCQZ_END",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.APPOINTMENT_DATE_>=']")
                        .val();
                    var endTime = $("input[name='Q_T.APPOINTMENT_DATE_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_T.APPOINTMENT_DATE_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);

        });

    /*导出*/
    function exportBdcqzAppointExcel() {
        var beginDate = $("input[name='Q_T.APPOINTMENT_DATE_>=']").val();
        var endDate = $("input[name='Q_T.APPOINTMENT_DATE_<=']").val();
        var cqrName = $("input[name='Q_T.CQR_NAME_LIKE']").val();
        var sjrName = $("input[name='Q_T.SJR_NAME_LIKE']").val();
        var url = "bdcAppointController.do?bdcqzAppointExport"
        if (beginDate) {
            url += "&Q_T.APPOINTMENT_DATE_GE=" + beginDate + " 00:00:00";
        }
        if (endDate) {
            url += "&Q_T.APPOINTMENT_DATE_LE=" + endDate + " 23:59:59";
        }
        if (cqrName) {
            url += "&Q_T.CQR_NAME_LIKE=" + cqrName;
        }
        if (sjrName) {
            url += "&Q_T.SJR_NAME_LIKE=" + sjrName;
        }
        window.location.href = encodeURI(url + "&isExport=1");
    }

    /*修改状态*/
    function editBdcqzAppoint() {
        var ID = AppUtil.getEditDataGridRecord("bdcqzAppointStatisGrid");
        if (ID) {
            $.dialog.open("bdcAppointController.do?editBdcqzAppointView&ID=" + ID , {
                title : "修改",
                width : "600px",
                height : "300px",
                lock : true,
                resize : false
            },false)
        }
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="bdcqzAppointStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
                        <div class="right-con">
                            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                                <div class="e-toolbar-ct">
                                    <div class="e-toolbar-overflow">
                                        <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportBdcqzAppointExcel();">导出数据</a>
                                        <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="editBdcqzAppoint();">编辑</a>
                                    </div>
                                </div>
                            </div>
                        </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">产权人：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_T.CQR_NAME_LIKE"/>
                        </td>
                        <td style="width:68px;text-align:right;">收件人：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_T.SJR_NAME_LIKE"/>
                        </td>
                        <td style="width:100px;text-align:right;">领证状态：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:128px;" name="Q_T.STATUS_EQ"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=BDCQZLZZT',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
                        <td style="width:68px;text-align:right;">起始日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.APPOINTMENT_BDCQZ_BEGIN" name="Q_T.APPOINTMENT_DATE_>="/></td>
                        <td style="width:68px;text-align:right;">截至日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.APPOINTMENT_BDCQZ_END" name="Q_T.APPOINTMENT_DATE_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('bdcqzAppointStatisToolBar','bdcqzAppointStatisGrid')" />
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
                   id="bdcqzAppointStatisGrid" fitcolumns="false" toolbar="#bdcqzAppointStatisToolBar"
                   method="post" idfield="ID" checkonselect="true" nowrap="false"
                   selectoncheck="true" fit="true" border="false"
                   url="bdcAppointController.do?bdcqzAppointListData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true" colspan="1"></th>
                    <th data-options="field:'CQR_NAME',align:'center'" width="7%">产权人姓名</th>
                    <th data-options="field:'CQR_CARDNO',align:'center'" width="10%">产权人身份证</th>
                    <th data-options="field:'APPOINTMENT_DATE',align:'center'" width="10%">预约日期</th>
                    <th data-options="field:'TYPE',align:'center'" width="7%" >领证方式</th>
                    <th data-options="field:'STATUS',align:'center'" width="7%" >领证状态</th>
                    <th data-options="field:'REASON',align:'center'" width="10%" >说明</th>
                    <th data-options="field:'CQR_PHONE',align:'center'" width="7%">联系号码</th>
                    <th data-options="field:'SJR_NAME',align:'center'" width="7%">收件人姓名</th>
                    <th data-options="field:'SJR_CARDNO',align:'center'" width="10%">收件人身份证</th>
                    <th data-options="field:'SJR_ADDRESS',align:'center'" width="20%">收件地址</th>
                    <th data-options="field:'EXE_ID',align:'center'" width="10%">申报号</th>
                    <th data-options="field:'REMARK',align:'center'" width="20%">备注</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>