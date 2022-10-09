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

    function ryfzReviseView() {
        var USER_ID = AppUtil.getEditDataGridRecord("ryfzStatisGrid")
        if (USER_ID != null && USER_ID.length > 0) {
            $.dialog.open("statisticsNewController.do?ryfzReviseView&USER_ID=" + USER_ID , {
                title : "当前已办事项查询",
                width : "500px",
                height : "200px",
                lock : true,
                resize : false
            },false)
        }
    }

</script>

<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="ryfzStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-pencil" plain="true" onclick="ryfzReviseView();">编辑</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">姓名：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_a.FULLNAME_LIKE" name="Q_a.FULLNAME_LIKE" /></td>
                        <td style="width:68px;text-align:right;">账号：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_a.USERNAME_LIKE" name="Q_a.USERNAME_LIKE" /></td>
                        <td style="width:68px;text-align:right;">部门：</td>
                        <td style="width:135px;"><input type="text"
                                                        style="width:128px;float:left;height: 20px;"
                                                        id="Q_b.DEPART_NAME_LIKE" name="Q_b.DEPART_NAME_LIKE" /></td>
                        <td style="width:80px;">岗位类型：</td>
                        <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:150px;" name="Q_a.GWLX_EQ"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=GWLX',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('ryfzStatisToolBar','ryfzStatisGrid')" />
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
            <table class="easyui-datagrid" rownumbers="true" pagination="true"
                   id="ryfzStatisGrid" fitcolumns="false" toolbar="#ryfzStatisToolBar"
                   method="post" idfield="USER_ID" checkonselect="false"
                   selectoncheck="false" fit="true" border="false"
                   url="statisticsNewController.do?ryfzData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true" colspan="1"></th>
                    <th data-options="field:'DEPART_NAME',align:'center'" width="20%">部门名称</th>
                    <th data-options="field:'FULLNAME',align:'center'" width="10%">姓名</th>
                    <th data-options="field:'USERNAME',align:'center'" width="10%">账号</th>
                    <th data-options="field:'GWLX',align:'center'" width="20%">岗位类型</th>
                    <th data-options="field:'ZB',align:'center'" width="20%">组别</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
