<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    function formateStatus(val,row) {
        if (val == '1') {
            return '受理';
        } else if (val == '2') {
            return '办结';
        }
    }

</script>

<body class="eui-diabody" style="margin:0px;">
<form id="gcjsxmcxDealItemForm" method="post" action="">
    <div id="gcjsxmcxDealItemFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="gcjsxmcxTable">
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>项目编码：${PROJECT_CODE}</a></div></td>
            </tr>
            <tr>
                <td style="text-align: center;font-weight: bold;width:12%;">申报号</td>
                <td style="text-align: center;font-weight: bold;width:50%;">办件名称</td>
                <td style="text-align: center;font-weight: bold;width:23%;">事项</td>
                <td style="text-align: center;font-weight: bold;width:15%;">申报时间</td>
            </tr>
            <c:forEach items="${subject}" var="subject">
                <tr>
                    <td style="text-align: left">
                        <span>${subject.EXE_ID}</span>
                    </td>
                    <td style="text-align: left">
                        <span>${subject.SUBJECT}</span>
                    </td>
                    <td style="text-align: left">
                        <span>${subject.ITEM_NAME}</span>
                    </td>
                    <td style="text-align: center">
                        <span>${subject.CREATE_TIME}</span>
                    </td>
                </tr>
            </c:forEach>
        </table>


    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons" >
            <input value="确定" type="button" onclick="AppUtil.closeLayer();"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
        </div>
    </div>
</form>

</body>