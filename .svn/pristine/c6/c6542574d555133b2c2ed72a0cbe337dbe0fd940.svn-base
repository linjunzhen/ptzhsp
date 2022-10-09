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
        <th colspan="6">相关信息过滤</th>
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <td colspan="2">
                        险种过滤:<input type="checkbox"  name="XZLX" style="width:30px;padding-left: 0px;margin-left: 0px;" value="110"> 养老保险
            <input type="checkbox"  name="XZLX" style="width:30px;padding-left: 0px;margin-left: 0px;" value="410"> 工伤保险
        </td>
        <td class="tab_width">开始时间：</td>
           <td><input type="text" class="tab_text Wdate validate[]" name="KFSJ"
                   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:true})" readonly="true" /></td>
        <td class="tab_width">结束时间：</td>
        <td><input type="text" class="tab_text Wdate validate[]" name="JSSJ"
                   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:true})" readonly="true" /></td>
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <tr>
        <th colspan="6">缴费明细统计</th>
    </tr>
</table>
<table>
    <td class="tab_width">建账前缴费月数：</td>
    <td>
    <td><input type="text" class="tab_text Wdate validate[]" name="JZQJFYS"
               onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:true})" readonly="true" /></td>
    </td>
    <td class="tab_width">建账后缴费月数：</td>
    <td>
    <td><input type="text" class="tab_text Wdate validate[]" name="JZHJFYS"
               onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:true})" readonly="true" /></td>
    </td>
    <td class="tab_width">累计缴费月数：</td>
    <td>
        <input type="text" class="tab_text validate[]" name="LJJFYY" />
    </td>
</table>

<table cellpadding="0" cellspacing="1" style="width:100%">
    <tr>
        <td style="height:300px">
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="payDetailGrid" fitcolumns="true" checkonselect="true"
               selectoncheck="true" fit="true" border="false"
               data-options="autoSave:true,method:'post',url:'sbQyzgcxController.do?payDetailJson&ywId=${ywId}&exeId=${exeId}'"
        >
            <thead>
            <tr>
                <th data-options="field:'ck',align:'left',checkbox:true"></th>
                <th data-options="field:'aae140',align:'center',width:'15%',formatter:commonFormat">险种类型</th>
                <th data-options="field:'aab004',align:'center',width:'20%'">单位名称</th>
                <th data-options="field:'aab001',align:'center',width:'20%'">单位编号</th>
                <th data-options="field:'aac066',align:'center',width:'15%',formatter:commonFormat">参保身份</th>
                <th data-options="field:'skc572',align:'center',width:'15%'">数据来源</th>
                <th data-options="field:'aaa103',align:'center',width:'15%',formatter:commonFormat">应缴类型</th>
                <th data-options="field:'aae002',align:'center',width:'15%'">缴款所属期</th>
                <th data-options="field:'aae033',align:'center',width:'15%'">对应缴款期</th>
                <th data-options="field:'111111',align:'center',width:'15%'">对应缴款截止期</th>
                <th data-options="field:'aae202',align:'center',width:'15%'">累计月份 </th>
                <th data-options="field:'aae180',align:'center',width:'15%'">缴费基数</th>
                <th data-options="field:'aaa042',align:'center',width:'15%'">单位缴费比例</th>
                <th data-options="field:'aaa041',align:'center',width:'15%'">个人缴费比例</th>
                <th data-options="field:'aaa042',align:'center',width:'15%'">单位应缴金额</th>
                <th data-options="field:'aae022',align:'center',width:'15%'">个人应缴金额</th>
                <th data-options="field:'222222',align:'center',width:'15%'">足额到账年月</th>
                <th data-options="field:'222222',align:'center',width:'15%'">计息年月</th>
                <th data-options="field:'222222',align:'center',width:'15%'">是否欠费补缴</th>
                <th data-options="field:'222222',align:'center',width:'15%'">单位应缴划个账比例</th>
                <th data-options="field:'222222',align:'center',width:'15%'">个人应缴划个账比例</th>
                <th data-options="field:'222222',align:'center',width:'15%'">个人应缴滞纳金</th>
                <th data-options="field:'222222',align:'center',width:'15%'">单位应缴滞纳金</th>
                <th data-options="field:'222222',align:'center',width:'15%'">年度累计缴费基数增加额</th>
                <th data-options="field:'222222',align:'center',width:'15%'">征收方式</th>
                <th data-options="field:'222222',align:'center',width:'15%'">核销标志</th>
                <th data-options="field:'222222',align:'center',width:'15%'">核销年月</th>
                <th data-options="field:'222222',align:'center',width:'15%'">核销原因</th>
            </tr>
            </thead>
        </table>
        </td>
    </tr>
</table>


