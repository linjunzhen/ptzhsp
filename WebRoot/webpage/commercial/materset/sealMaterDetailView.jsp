<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    function downloadSealMater(e) {
        var fileId = e.id;
        if (fileId) {
            window.location.href = __ctxPath + "/DownLoadServlet?fileId=" + fileId;
        } else {
            art.dialog({
                content: "无材料，材料可能为窗口收取",
                icon: "error",
                ok: true
            });
        }
    }

    function downloadSealMaterS(e) {
        var fileId = e.id;
        var itemCode = '${subject.ITEM_CODE}';
        var EXE_ID = '${subject.EXE_ID}';
        if (fileId != null && fileId.length > 0) {
            window.location.href = __ctxPath + "/domesticControllerController/downLoad.do?fileId=" + fileId
                + "&exeId=" + EXE_ID
                + "&itemCode=" + itemCode;
        }
    }

</script>


<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
<form id="sealMaterDetailForm" method="post" action="">
    <div id="sealMaterDetailFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend" style="font-weight:bold;">申报号：${subject.EXE_ID}</td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">法人身份证正面</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoFR.SIGN_IDPHOTO_FRONT}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">法人身份证反面</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoFR.SIGN_IDPHOTO_BACK}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">法人面签视频</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoFR.SIGN_VIDEO}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">经办人身份证正面</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoJBR.SIGN_IDPHOTO_FRONT}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">经办人身份证反面</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoJBR.SIGN_IDPHOTO_BACK}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">经办人面签视频</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.signInfoJBR.SIGN_VIDEO}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">营业执照</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.YYZZ}" onclick='downloadSealMater(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">委托书</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.WTS}" onclick='downloadSealMaterS(this)'>下载</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="margin-left: 20px;">确认书</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.QRS}" onclick='downloadSealMaterS(this)'>下载</span>
                </td>
            </tr>
            <c:if test="${subject.ISEMAIL=='1'}">
            <tr>
                <td>
                    <span style="margin-left: 20px;">邮寄送达委托书</span>
                </td>
                <td>
                    <span style="cursor: pointer;margin-left: 100px;color: cornflowerblue;" id="${subject.YJSDWTS}" onclick='downloadSealMaterS(this)'>下载</span>
                </td>
            </tr>
            </c:if>
        </table>


    </div>
    <div data-options="region:'south'" style="height:50px;" >
        <div class="eve_buttons" >
            <input value="确定" type="button" onclick="AppUtil.closeLayer();"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
        </div>
    </div>
</form>

</body>