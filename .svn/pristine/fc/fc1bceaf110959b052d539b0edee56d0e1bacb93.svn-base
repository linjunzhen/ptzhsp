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
        <th colspan="6">工资基数</th>
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <td style="height:150px">
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="salaryBaseGrid" fitcolumns="true" checkonselect="true"
               selectoncheck="true" fit="true" border="false"
               data-options="autoSave:true,method:'post',url:'sbQyzjyController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}'"
        >
            <thead>
            <tr>
                <th data-options="field:'ck',align:'left',checkbox:true"></th>
                <th data-options="field:'aab301',width:'12%',align:'center'">单位管理码</th>
                <th data-options="field:'aab999',width:'20%',align:'center'">单位名称</th>
                <th data-options="field:'aab004',width:'15%',align:'center'">开始年月</th>
                <th data-options="field:'aae140',width:'15%',align:'center'">停止年月</th>
                <th data-options="field:'aae140',width:'10%',align:'center'">工资</th>
                <th data-options="field:'aac084',width:'10%',align:'center'">缴费基数</th>
            </tr>
            </thead>
        </table>
        </td>
    </tr>

</table>


