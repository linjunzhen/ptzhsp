<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>

</script>

<body class="eui-diabody" style="margin:0px;">
<form id="checkServiceItemForm" method="post" action="">
    <div id="checkServiceItemFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="checkServiceItemTable">
            <tr>
                <td style="text-align: center;font-weight: bold;width:20%;">事项编码</td>
                <td style="text-align: center;font-weight: bold;width:50%;">事项名称</td>
            </tr>
            <c:forEach items="${itemList}" var="itemList">
                <tr>
                    <td style="text-align: center">
                        <span>${itemList.ITEM_CODE}</span>
                    </td>
                    <td style="text-align: center">
                        <span>${itemList.ITEM_NAME}</span>
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
