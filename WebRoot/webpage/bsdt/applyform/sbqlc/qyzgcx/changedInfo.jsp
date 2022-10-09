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
        <th colspan="3"><span   class="easyui-linkbutton" iconCls="icon-search">基础信息变更记录&nbsp;</span>
            <span style="margin-left: 50px;"  class="easyui-linkbutton" iconCls="icon-search">参保信息变更记录&nbsp;</span>
        </th>
        <th colspan="3">
        </th>
    </tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <td style="height:150px">
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="changedInfoGrid" fitcolumns="true" checkonselect="true"
               selectoncheck="true" fit="true" border="false"
               data-options="autoSave:true,method:'post',url:'sbQyzjyController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}'"
        >
            <thead>
            <tr>
                <th data-options="field:'ck',align:'left',checkbox:true"></th>
                <th data-options="field:'2',width:'12%',align:'center'">变更中文含义</th>
                <th data-options="field:'aab999',width:'12%',align:'center'">变更前信息</th>
                <th data-options="field:'aab004',width:'20%',align:'center'">变更后信息</th>
                <th data-options="field:'aae140',width:'10%',align:'center'">经办人</th>
                <th data-options="field:'aae140',width:'10%',align:'center'">经办时间</th>
                <th data-options="field:'aac084',width:'10%',align:'center'">复核人</th>
                <th data-options="field:'aac031',width:'10%',align:'center'">复核时间</th>
                <th data-options="field:'aac049',width:'10%',align:'center'">复核标志</th>
                <th data-options="field:'aae030',width:'10%',align:'center'">分中心</th>
                <th data-options="field:'aae002',width:'10%',align:'center'">网点</th>
            </tr>
            </thead>
        </table>
        </td>
    </tr>

</table>


