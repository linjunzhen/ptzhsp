<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>

   function downloadGcjsxmcxMater(e) {
       var fileId = e.id;
       window.location.href=__ctxPath+"/DownLoadServlet?fileId="+fileId;
   }

</script>

<body class="eui-diabody" style="margin:0px;">
<form id="gcjsxmcxMaterDetailForm" method="post" action="">
    <div id="gcjsxmcxMaterDetailFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="gcjsxmcxTable">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>项目编码：${PROJECT_CODE}</a></div></td>
            </tr>
            <c:forEach items="${subject}" var="subject">
            <tr>
                <td>
                    <span style="margin-left: 20px;">${subject.FILE_NAME}</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 20px;color: cornflowerblue;" id="${subject.FILE_ID}" onclick='downloadGcjsxmcxMater(this)'>下载</span>
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