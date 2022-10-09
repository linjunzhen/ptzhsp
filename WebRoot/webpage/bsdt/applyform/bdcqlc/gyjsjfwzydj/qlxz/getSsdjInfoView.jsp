<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script></script>

<body class="eui-diabody" style="margin:0px;">
<div data-options="region:'center',split:true,border:false">
    <table style="width:100%;border-collapse:collapse;"
           class="dddl-contentBorder dddl_table">
        <tr>
            <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
        </tr>
        <tr>
            <td style="text-align:center;width: 50px;">
                <span style="width: 120px;float:left;">受理号</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.slh}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">返回信息：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.reMsg}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">应缴税费总额：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.yjsfze}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">转移方税费：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.zyfse}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">承受方税费：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.csfse}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">操作人：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.operator}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">评估单价：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.pgdj}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">评估价格：</span>
            </td>
            <td style="text-align: center;">
                <span>${requestMap.pgjg}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align:center;">
                <span style="width: 120px;float:left;">完税联系单：</span>
            </td>
            <td style="text-align: center;">
                <c:if test="${requestMap.fileId != null}">
                <span style="cursor: pointer;color: #0a90f5;" onclick="AppUtil.downLoadFile('${requestMap.fileId}')">下载</span>
                </c:if>

            </td>
        </tr>
    </table>
</div>
</body>