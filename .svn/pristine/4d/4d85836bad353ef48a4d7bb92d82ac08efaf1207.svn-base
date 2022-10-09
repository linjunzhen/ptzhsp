<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<style>
    .flexBox {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>
<script>
    function downLoadBdcImgFile() {
        var id = '${requestMap.id}';
        window.location.href = "<%=path%>/bdcApplyController/downLoadBdcQueryFtFile.do?id=" + id;
    }
</script>

<body class="eui-diabody" style="margin:0px;">
    <div data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
            <tr>
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
            </tr>
            <tr>
                <td style="text-align:center;width: 50px;">
                    <span style="width: 120px;float:left;">不动产坐落</span>
                </td>
                <td style="text-align: center;">
                    <span>${requestMap.ADDRESS}</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>附图</a></div></td>
            </tr>
        </table>
        <c:if test="${requestMap.isImg == true}">
            <c:if test="${requestMap.zdtFileUrl != null}">
                <div>
                    <img src="${requestMap.zdtFileUrl}" alt="" style="width: 100%;height: auto;">
                </div>
            </c:if>
            <c:if test="${requestMap.fhtFileUrl != null}">
                <div>
                    <img src="${requestMap.fhtFileUrl}" alt="" style="width: 100%;height: auto;">
                </div>
            </c:if>
        </c:if>
        <c:if test="${requestMap.isImg == false}">
            <div class="flexBox">
                <span>该不动产暂无附图信息</span>
            </div>
        </c:if>
        <div data-options="region:'south'" style="height:46px;" >
            <div class="eve_buttons">
                <c:if test="${requestMap.isImg == true}">
                    <input value="下载" type="button"
                           class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="downLoadBdcImgFile();"/>
                </c:if>
                <input
                        value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
                        onclick="AppUtil.closeLayer();" />
            </div>
        </div>
    </div>
</body>