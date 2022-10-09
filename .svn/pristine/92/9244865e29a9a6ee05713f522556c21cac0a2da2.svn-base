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
        <th colspan="6">银行信息</th>
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <td style="height:150px">
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="bankinfoGrid" fitcolumns="true" checkonselect="true"
               selectoncheck="true" fit="true" border="false"
               data-options="autoSave:true,method:'post',url:'sbQyzjyController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}'"
        >
            <thead>
            <tr>
                <th data-options="field:'ck',align:'left',checkbox:true"></th>
                <th data-options="field:'aae140',width:'10%',align:'center',formatter:commonFormat">险种类型</th>
                <th data-options="field:'aab999',width:'10%',align:'center'">银行账号用途</th>
                <th data-options="field:'aab004',width:'15%',align:'center'">银行名称</th>
                <th data-options="field:'aae140',width:'15%',align:'center'">账号户名</th>
                <th data-options="field:'aae140',width:'15%',align:'center'">银行账号</th>
                <th data-options="field:'aac084',width:'10%',align:'center'">银行级别</th>
                <th data-options="field:'1',width:'10%',align:'center'">银行行号</th>
                <th data-options="field:'2',width:'10%',align:'center'">开始年月</th>
                <th data-options="field:'3',width:'10%',align:'center'">终止年月</th>
                <th data-options="field:'3',width:'10%',align:'center'">参保身份</th>
            </tr>
            </thead>
        </table>
        </td>
    </tr>

</table>


