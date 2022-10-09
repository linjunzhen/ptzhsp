<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String ywId = request.getParameter("SB_YWID");
    request.setAttribute("ywId",ywId);
%>
<style>
    .eflowbutton{
        background: #3a81d0;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height:26px;
        color: #fff;
        border-radius: 5px;

    }
</style>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <th colspan="6">人员参保信息</th>
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <td style="height:150px">
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="insuredDetailGrid" fitcolumns="true" checkonselect="true"
               selectoncheck="true" fit="true" border="false"
               data-options="autoSave:true,method:'post',url:'sbQyzgcxController.do?insuredDetailJson&ywId=${ywId}&exeId=${exeId}'"
        >
            <thead>
            <tr>
                <th data-options="field:'ck',align:'left',checkbox:true"></th>
                <th data-options="field:'aab301',width:'12%',align:'center'">所属分中心</th>
                <th data-options="field:'aab999',width:'12%',align:'center'">单位管理码</th>
                <th data-options="field:'aab004',width:'20%',align:'center'">所在单位</th>
                <th data-options="field:'aae140',width:'10%',align:'center',formatter:commonFormat">险种类型</th>
                <th data-options="field:'aae140',width:'10%',align:'center',formatter:commonFormat">离退休状态</th>
                <th data-options="field:'aac084',width:'10%',align:'center',formatter:commonFormat">参保状态</th>
                <th data-options="field:'aac031',width:'10%',align:'center',formatter:commonFormat">当前缴费状态</th>
                <th data-options="field:'aac049',width:'10%',align:'center'">首次参保年月</th>
                <th data-options="field:'aae030',width:'10%',align:'center'">本次参保日期</th>
                <th data-options="field:'aae002',width:'10%',align:'center'">本次审核日期</th>
                <th data-options="field:'baeai6',width:'10%',align:'center'">账号建立年月</th>
                <th data-options="field:'aae030',width:'10%',align:'center'">首次参保地</th>
            </tr>
            </thead>
        </table>
        </td>
    </tr>

</table>


