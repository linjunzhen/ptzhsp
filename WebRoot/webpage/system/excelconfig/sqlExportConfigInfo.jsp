<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
        loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,ztree,swfupload,artdialog"></eve:resources>
</head>

<script>
    $(function () {
        AppUtil.initWindowForm("sqlExportConfigForm", function (form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#sqlExportConfigForm").serialize();
                var url = $("#sqlExportConfigForm").attr("action");
                AppUtil.ajaxProgress({
                    url: url,
                    params: formData,
                    callback: function (resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content: resultJson.msg,
                                icon: "succeed",
                                time: 3,
                                ok: true
                            });
                            parent.$("#sqlExportConfigGrid").datagrid("reload");
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content: resultJson.msg,
                                icon: "error",
                                ok: true
                            });
                        }
                    }
                });
            }
        }, "T_MSJW_SYSTEM_SQLEXPORTCONFIG")
    });

    function openStampFileUploadDialog() {
        //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
        art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_MSJW_SYSTEM_SQLEXPORTCONFIG', {
            title: "上传(附件)",
            width: "650px",
            height: "300px",
            lock: true,
            resize: false,
            close: function(){
                var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                if(uploadedFileInfo){
                    if(uploadedFileInfo.fileId){
                        if (uploadedFileInfo.fileSuffix == 'xls') {
                            var fileType = 'gif,jpg,jpeg,bmp,png';
                            var attachType = 'attach';
                            if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                                attachType = "image";
                            }
                            $("input[name='EXCEL_FILE_ID']").val(uploadedFileInfo.fileId);
                            var spanHtml = "<p style='width:300px;margin-left: 50px;display: flex;justify-content: left;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                                + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                                + "&attachType=" + attachType + "\" ";
                            spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                            spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "');\" ><font color=\"red\">删除</font></a></p>";
                            $("#stampFile").html(spanHtml);
                        } else {
                            art.dialog({
                                content:"请上传后缀为xls",
                                icon:"warning",
                                ok: true
                            })
                        }
                    }
                }
                art.dialog.removeData("uploadedFileInfo");
            }
        });
    }

    function delUploadFile(fileId) {
        art.dialog.confirm("您确定要删除该文件吗?", function() {
            $("#"+fileId).remove();
            $("input[name='EXCEL_FILE_ID']").val('');
        });
    }
</script>

<body class="eui-diabody" style="margin:0px;">
<form id="sqlExportConfigForm" method="post"
      action="excelConfigController.do?sqlExportConfigSaveOrUpdate">
    <div id="sqlExportConfigFormDiv" data-options="region:'center',split:true,border:false">
        <input type="hidden" name="SQL_ID" value="${sqlExportConfig.SQL_ID}">
        <input type="hidden" name="EXCEL_FILE_ID" value="${sqlExportConfig.EXCEL_FILE_ID}">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本配置</a></div></td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">sql模版名称：</span>
                    <input type="text" style="width:70%;float:left;" maxlength="128"
                           class="eve-input validate[required]"
                           value="${sqlExportConfig.SQL_NAME}" name="SQL_NAME" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">sql模版编码：</span>
                    <input type="text" style="width:70%;float:left;" maxlength="64"
                           class="eve-input validate[required]"
                           value="${sqlExportConfig.SQL_KEY}" name="SQL_KEY" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">开始行：</span>
                    <input type="text" style="width:70%;float:left;" maxlength="4"
                           class="eve-input validate[required]"
                           value="${sqlExportConfig.START_ROW}" name="START_ROW" /></td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">开始列：</span>
                    <input type="text" style="width:70%;float:left;" maxlength="4"
                           class="eve-input validate[required]"
                           value="${sqlExportConfig.START_COL}" name="START_COL" /></td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">参数：</span>
                    <input type="text" style="width:70%;float:left;" maxlength="128" placeholder="参数之间用,隔开"
                           class="eve-input" value="${sqlExportConfig.SQL_PARAMS}" name="SQL_PARAMS" /></td>
            </tr>
            <tr id="upload">
                <td colspan="4">
                    <span style="width: 100px;float:left;text-align:right;">模板上传：</span>
                    <div style="margin-top: 4px;">
                        <a href="javascript:void(0);" onclick="openStampFileUploadDialog()">
                            <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                        </a>
                        <span id="stampFile">
                            <c:if test="${sqlExportConfig.EXCEL_FILE_ID != null}">
                                <p id="${sqlExportConfig.EXCEL_FILE_ID}" style="width: 300px;margin-left: 50px;display: flex;justify-content: left;">
                                    <a href="javascript:void(0);" style="color: blue;margin-right: 5px;" onclick="AppUtil.downLoadFile('${sqlExportConfig.EXCEL_FILE_ID}')">
                                        <font style="color: blue;">${sqlExportConfig.EXCEL_FILE_NAME}</font>
                                    </a>
                                    <a href="javascript:void(0);" onclick="delUploadFile('${sqlExportConfig.EXCEL_FILE_ID}');"><font color="red">删除</font></a>
                                </p>
                            </c:if>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">sql内容：</span>
                    <textarea rows="10" cols="5"
                              class="eve-textarea validate[required]" style="width: 400px"
                              name="SQL_CONTENT">${sqlExportConfig.SQL_CONTENT}</textarea><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
        </table>
    </div>
    <div class="eve_buttons">
        <input value="确定" type="submit"
               class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
            value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
            onclick="AppUtil.closeLayer();" />
    </div>
</form>