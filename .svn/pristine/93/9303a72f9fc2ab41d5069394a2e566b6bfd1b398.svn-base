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
        var bjmxDateMonth = new Date().getMonth() ;
        var bjmxDateMonth = bjmxDateMonth.toString() < 10 ? '0' + bjmxDateMonth : '' + bjmxDateMonth
        var bjmxDateYear = new Date().getFullYear();
        var bjmxDate = bjmxDateYear + '-' + bjmxDateMonth + '-01'
        $("#bjmxbStatisGrid").attr("url", "statisticsNewController.do?bjmxbData&Q_t.CREATE_TIME_GE=" + bjmxDate + " 00:00:00");
        $("input[name='Q_t.create_time_>=']").val(bjmxDate)
    }

    /*导出表格*/
    function exportBjmxbExcel(){
        var beginDate = $("input[name='Q_t.create_time_>=']").val();
        var endDate = $("input[name='Q_t.create_time_<=']").val();
        var departId = $("input[name='Q_D.DEPART_ID_=']").val()
        var itemName = $("input[name='Q_S.ITEM_NAME_LIKE']").val()
        var url = "statisticsNewController.do?exportBjmxbExcel&beginDate="+beginDate+"&endDate="+endDate + "&Q_D.DEPART_ID_EQ=" + departId + "&isPage=N";
        if(null!=beginDate&&''!=beginDate){
            url+="&Q_t.CREATE_TIME_GE="+beginDate+" 00:00:00";
        }
        if(null!=endDate&&''!=endDate){
            url+="&Q_t.CREATE_TIME_LE="+endDate+" 23:59:59";
        }
        if(departId == '' && (beginDate==''&&endDate=='')){
            $.messager.alert('警告',"请选择部门或者当前年份时间，谢谢！");
            return;
        }
        window.location.href = url;
        $.messager.alert('提醒',"数据量过大，请耐心等待，谢谢！");
    }


    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN_BJMX",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_t.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_t.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_t.create_time_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END_BJMX",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_t.create_time_>=']")
                        .val();
                    var endTime = $("input[name='Q_t.create_time_<=']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_t.create_time_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);

        });


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
                    $("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
    }

    function resetSpsxmxForm(){
        $("#DEPARTID").val("");
        AppUtil.gridSearchReset('SysLogForm');
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="bjmxbStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                           	<a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportBjmxbExcel();">导出数据</a>
                           
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <input type="hidden" id="DEPARTID" name="Q_D.DEPART_ID_=" value="">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申报号：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_T.EXE_ID_LIKE"/>
                        </td>
                        <td style="width:68px;text-align:right;">查询部门：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="DEPT_NAME" onclick="showSelectDeparts();"/>
                        </td>
                        <td style="width:96px;text-align:right;">服务项目名称：</td>
                        <td style="width:135px;">
                            <input type="text"
                                   style="width:150px;float:left;" class="eve-input" name="Q_S.ITEM_NAME_LIKE"/>
                        </td>
                        <td style="width:80px;text-align:right;">一窗通办：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_S.IS_YCTB_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
                    </tr>
                    <tr>
                        <td style="width:68px;text-align:right;">办件名称：</td>
                        <td style="width:135px;">
                            <input class="eve-input" type="text" maxlength="20" style="width:150px;" name="Q_T.SUBJECT_LIKE" />
                        </td>
                        <td style="width:68px;text-align:right;">开始日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_BEGIN_BJMX" name="Q_t.create_time_>="/></td>
                        <td style="width:68px;text-align:right;">结束日期：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:150px;float:left;" class="laydate-icon"
                                                        id="SysLogL.OPERATE_TIME_END_BJMX" name="Q_t.create_time_<=" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('bjmxbStatisToolBar','bjmxbStatisGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="resetSpsxmxForm()" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        
		<div class="gridtable">
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="bjmxbStatisGrid" fitcolumns="false" toolbar="#bjmxbStatisToolBar"
               method="post" idfield="LOG_ID" checkonselect="false" nowrap="false"
               selectoncheck="false" fit="true" border="false"
               url="">
            <thead>
            <tr>
                <th colspan="16">上级部门</th>
                <th rowspan="2" data-options="field:'WCQK',align:'center'" width="10%">完成情况</th>
            </tr>
            <tr>
                <th data-options="field:'SBH',align:'center'" width="13%" >申报号</th>
                <th data-options="field:'BM',align:'center'" width="20%" >部门</th>
                <th data-options="field:'XMMC',align:'center'" width="20%" >审批服务项目名称</th>
                <th data-options="field:'BJMC',align:'center'" width="30%" >办件名称</th>

                <th data-options="field:'YCTB',align:'center'" width="8%">一窗通办</th>
                <th data-options="field:'BJLX',align:'center'" width="10%" >办件类型</th>
                <th data-options="field:'SLRY',align:'center'" width="10%" >受理人员</th>
                <th data-options="field:'SHRY',align:'center'" width="10%" >审核人员</th>
                <th data-options="field:'SLDF',align:'center'" width="7%" >受理得分</th>
                <th data-options="field:'SHDF',align:'center'" width="7%" >审核得分</th>
                <th data-options="field:'SJKS',align:'center'" width="15%" >收件开始时间</th>
                <th data-options="field:'SJJS',align:'center'" width="15%" >收件结束时间</th>
                <th data-options="field:'BJJS',align:'center'" width="15%" >办结时间</th>
                <th data-options="field:'SLSC',align:'center'" width="10%" >受理时长(分钟)</th>

                <th data-options="field:'BJSC',align:'center'" width="10%" >办结时长(分钟)</th>
                <th data-options="field:'BJPY',align:'center'" width="10%">办件评议</th>
            </tr>
            </thead>
        </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>

