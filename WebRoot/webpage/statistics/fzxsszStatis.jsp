<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
    .gridtable{width:100%;height:100%}
    .gridtable .datagrid-htable{border-top:1px solid #8DB2E3;border-right:1px solid #8DB2E3}
    .gridtable .datagrid-btable{border-right:1px solid #8DB2E3;border-bottom:1px solid #8DB2E3}
    .gridtable .datagrid-header-row td{border-width:0;border-left:1px solid #8DB2E3;border-bottom: 1px solid #8DB2E3;}
    .gridtable .datagrid-body td{border-width:0;border-left:1px solid #8DB2E3;border-top: 1px solid #8DB2E3}
</style>

<script>

    function changeRatioNum(){
        var itemCode = AppUtil.getEditDataGridRecord("fzxsszStatisGrid");
        if (itemCode != null && itemCode.length > 0) {
            $.dialog.open("statisticsNewController.do?fzxsszChangeRatio&itemCode=" + itemCode, {
                title : "分值系数信息",
                width : "600px",
                height : "200px",
                lock : true,
                resize : false
            }, false);
        }
    }

</script>


<div class="easyui-layout" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="fzxsszStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <div style="color:#005588;">
                                <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true" onclick="changeRatioNum();">更改系数</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="SysLogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:80px;text-align:right;">服务事项名称</td>
                        <td style="width:135px;">
                            <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_a.ITEM_NAME_LIKE" />
                        </td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('fzxsszStatisToolBar','fzxsszStatisGrid')" />
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
            <table class="easyui-datagrid" rownumbers="true" pagination="false"
                   id="fzxsszStatisGrid" fitcolumns="true" toolbar="#fzxsszStatisToolBar"
                   method="post" idfield="ITEM_CODE" checkonselect="false" nowrap="false"
                   selectoncheck="false" fit="true" border="false"
                   url="statisticsNewController.do?fzxsszData">
                <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'ITEM_CODE',align:'left'" width="80">服务事项编码</th>
                    <th data-options="field:'ZXMC',align:'left'" width="200">服务事项名称</th>
                    <th data-options="field:'BM',align:'left'" width="200">部门</th>
                    <th data-options="field:'FZXSSZ',align:'left'" width="50">服务事项分值</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- =========================表格结束==========================-->
    </div>
</div>
