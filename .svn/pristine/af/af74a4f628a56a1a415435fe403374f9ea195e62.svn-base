<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
    <script type="text/javascript">
        $(function() {
            var TEMPLATE_FILE_GETTYPE = '${stampManage.TEMPLATE_FILE_GETTYPE}';
            if (TEMPLATE_FILE_GETTYPE && !TEMPLATE_FILE_GETTYPE == '') {
                changeTemplate(TEMPLATE_FILE_GETTYPE);
            }

            AppUtil.initWindowForm("StampManageForm", function(form, valid) {
                if (valid) {
                    var TEMPLATE_FILE_GETTYPE = $("input[name='TEMPLATE_FILE_GETTYPE']:checked").val();
                    var TEMPLATE_FILE_ID = $("input[name='TEMPLATE_FILE_ID']").val();
                    if (TEMPLATE_FILE_GETTYPE == 'upload') {
                        if (!(TEMPLATE_FILE_ID && TEMPLATE_FILE_ID != '')) {
                            parent.art.dialog({
                                content: "请上传签章模板",
                                icon: "error",
                                time: 3,
                                ok: true
                            });
                            return;
                        }
                    }
                    //将提交按钮禁用,防止重复提交
                    $("input[type='submit']").attr("disabled", "disabled");
                    var formData = $("#StampManageForm").serialize();
                    var url = $("#StampManageForm").attr("action");
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
                                parent.$("#StampManageGrid").datagrid("reload");
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
            }, "T_BSFW_STAMPMANAGE");

        });

        function openStampFileUploadDialog() {
            //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BSFW_STAMPMANAGE', {
                title: "上传(附件)",
                width: "650px",
                height: "300px",
                lock: true,
                resize: false,
                close: function(){
                    var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                    if(uploadedFileInfo){
                        if(uploadedFileInfo.fileId){
                            if (uploadedFileInfo.fileSuffix == 'xlsx' || uploadedFileInfo.fileSuffix == 'docx') {
                                var fileType = 'gif,jpg,jpeg,bmp,png';
                                var attachType = 'attach';
                                if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                                    attachType = "image";
                                }
                                $("input[name='TEMPLATE_FILE_ID']").val(uploadedFileInfo.fileId);
                                var spanHtml = "<p style='width:300px;margin-left: 50px;display: flex;justify-content: left;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                                    + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                                    + "&attachType=" + attachType + "\" ";
                                spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                                spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "');\" ><font color=\"red\">删除</font></a></p>";
                                $("#stampFile").html(spanHtml);
                            } else {
                                art.dialog({
                                    content:"请上传后缀为xlsx或者docx的文件",
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
                $("input[name='TEMPLATE_FILE_ID']").val('');
            });
        }

        function openItemSelectorsDialog() {
            var stampId = $("input[name='STAMP_ID']").val();
            var itemCodeLimit = $("input[name='STAMP_ITEMCODELIMIT']").val();
            $.dialog.open("departServiceItemController.do?stampSelector&stampId=" + stampId + "&itemCodeLimit=" + itemCodeLimit, {
                title : "事项选择器",
                width : "1200px",
                lock : true,
                resize : false,
                height : "500px",
                close : function() {
                    var selectItemInfo = art.dialog.data("selectItemInfo");
                    if (selectItemInfo) {
                        $("#STAMP_ITEMNAMELIMIT").text(selectItemInfo.itemNames);
                        $("input[name='STAMP_ITEMCODELIMIT']").val(selectItemInfo.itemCodes);
                        $("input[name='STAMP_ITEMNAMELIMIT']").val(selectItemInfo.itemNames);
                        art.dialog.removeData("selectItemInfo");
                    }
                }
            }, false);
        }

        function changeTemplate(val) {
            if (val == 'upload') {
                $("#upload").show();
                $("#interface").show();
                $("#mater").hide();
            } else {
                $("#upload").hide();
                $("#interface").hide();
                $("#mater").show();
            }
        }

    </script>
</head>

<body class="easyui-layout eui-diabody" fit="true" style="margin:0px;">
<form id="StampManageForm" method="post"
      action="ktStampController.do?saveOrUpdateStamp">
    <div id="StampManageFormDiv" data-options="region:'center',split:true,border:false">
        <!--==========隐藏域部分开始 ===========-->
        <input type="hidden" name="STAMP_ID" value="${stampManage.STAMP_ID}">
        <input type="hidden" name="TEMPLATE_FILE_ID" value="${stampManage.TEMPLATE_FILE_ID}">
        <input type="hidden" name="STAMP_ITEMCODELIMIT" value="${stampManage.STAMP_ITEMCODELIMIT}">
        <input type="hidden" name="STAMP_ITEMNAMELIMIT" value="${stampManage.STAMP_ITEMNAMELIMIT}">

        <!--==========隐藏域部分结束 ===========-->
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
            </tr>
            <tr>
                <td><span style="width: 120px;float:left;text-align:right;">签章编码：</span>
                    <c:if test="${stampManage.STAMP_ID!=null}">
                        ${stampManage.STAMP_CODE}
                    </c:if>
                    <c:if test="${stampManage.STAMP_ID==null}">
                        <input type="text" style="width:180px;float:left;" maxlength="32"
                               class="eve-input validate[required,custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]]"
                               value="${stampManage.STAMP_CODE}" id="STAMP_CODE"
                               name="STAMP_CODE" />
                    </c:if>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td><span style="width: 120px;float:left;text-align:right;">签章名称：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="32"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_NAME}" name="STAMP_NAME" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td>
                    <span style="width: 120px;float:left;text-align:right;">签章模板：</span>
                    <eve:radiogroup typecode="QZMBHQFS" width="130px" onclick="changeTemplate(this.value)"
                                    defaultvalue="upload" value="${stampManage.TEMPLATE_FILE_GETTYPE}"
                                    fieldname="TEMPLATE_FILE_GETTYPE">
                    </eve:radiogroup>
                </td>
                <td>
                    <span style="width: 120px;float:left;text-align:right;">是否启用：</span>
                    <eve:radiogroup typecode="YesOrNo" width="130px"
                                    defaultvalue="1" value="${stampManage.STAMP_STATUS}"
                                    fieldname="STAMP_STATUS">
                    </eve:radiogroup>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td><span style="width: 120px;float:left;text-align:right;">印章选择：</span>
                    <eve:eveselect clazz="tab_text validate[required]" dataParams="YZLB"
                                   dataInterface="dictionaryService.findDatasForSelect" value="${stampManage.SIGN_ID}"
                                   defaultEmptyText="请选择印章"  name="SIGN_ID">
                    </eve:eveselect>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td id="interface"><span style="width: 120px;float:left;text-align:right;">签章数据接口：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="64"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_INTERFACE}" name="STAMP_INTERFACE" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
                <td id="mater" colspan="4" style="display: none;"><span style="width: 120px;float:left;text-align:right;">材料编码：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="64"
                           class="eve-input validate[required]"
                           value="${stampManage.TEMPLATE_FILE_ATTACHKEY}" name="TEMPLATE_FILE_ATTACHKEY" />
                </td>
            </tr>
            <tr id="upload">
                <td colspan="4">
                    <span style="width: 120px;float:left;text-align:right;">模板上传：</span>
                    <div style="margin-top: 4px;">
                        <a href="javascript:void(0);" onclick="openStampFileUploadDialog()">
                            <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                        </a>
                        <span id="stampFile">
                            <c:if test="${stampManage.TEMPLATE_FILE_ID != null}">
                                <p id="${stampManage.TEMPLATE_FILE_ID}" style="width: 300px;margin-left: 50px;display: flex;justify-content: left;">
                                    <a href="javascript:void(0);" style="color: blue;margin-right: 5px;" onclick="AppUtil.downLoadFile('${stampManage.TEMPLATE_FILE_ID}')">
                                        <font style="color: blue;">${stampManage.TEMPLATE_FILE_NAME}</font>
                                    </a>
                                    <a href="javascript:void(0);" onclick="delUploadFile('${stampManage.TEMPLATE_FILE_ID}');"><font color="red">删除</font></a>
                                </p>
                            </c:if>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="width: 120px;float:left;text-align:right;">文件获取方式：</span>
                    <eve:eveselect clazz="tab_text validate[required]" dataParams="WJHQFS"
                                   dataInterface="dictionaryService.findDatasForSelect" value="${stampManage.STAMP_FILEGETTYPE}"
                                   defaultEmptyValue="interface"  name="STAMP_FILEGETTYPE">
                    </eve:eveselect>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td>
                    <span style="width: 120px;float:left;text-align:right;">是否盖章：</span>
                    <eve:radiogroup typecode="YesOrNo" width="130px"
                                    defaultvalue="1" value="${stampManage.STAMP_ISSIGN}"
                                    fieldname="STAMP_ISSIGN">
                    </eve:radiogroup>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>限制信息</a></div></td>
            </tr>
            <tr>
                <td colspan="4"><span style="width: 120px;float:left;text-align:right;">签章事项：</span>
                    <textarea rows="4" cols="200" style="width:75%;float:left;"
                           class="" placeholder="请选择签章事项" id="STAMP_ITEMNAMELIMIT" onclick="openItemSelectorsDialog();">${stampManage.STAMP_ITEMNAMELIMIT}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="4"><span style="width: 120px;float:left;text-align:right;">签章环节限制：</span>
                    <textarea rows="4" cols="200" style="width:75%;float:left;"
                              class="" placeholder="请填写无法签章的环节，多个用,隔开" name="STAMP_NODELIMIT" >${stampManage.STAMP_NODELIMIT}</textarea></td>
            </tr>
            <tr>
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>签章位置信息</a></div></td>
            </tr>
            <tr>
                <td><span style="width: 120px;float:left;text-align:right;">关键字：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="32"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_KEYWORD}" name="STAMP_KEYWORD" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
                <td><span style="width: 120px;float:left;text-align:right;">第几页：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="8"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_BEGINPAGE == null ? '0' : stampManage.STAMP_BEGINPAGE}" name="STAMP_BEGINPAGE" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td><span style="width: 120px;float:left;text-align:right;">x轴偏移量：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="8"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_XOFFSET == null ? '0' : stampManage.STAMP_XOFFSET}" name="STAMP_XOFFSET" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
                <td><span style="width: 120px;float:left;text-align:right;">y轴偏移量：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="8"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_YOFFSET == null ? '0' : stampManage.STAMP_YOFFSET}" name="STAMP_YOFFSET" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td><span style="width: 120px;float:left;text-align:right;">标识码：</span>
                    <input type="text" style="width:180px;float:left;" maxlength="8"
                           class="eve-input validate[required]"
                           value="${stampManage.STAMP_MARK == null ? '0' : stampManage.STAMP_MARK}" name="STAMP_MARK" /><font
                            class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
        </table>
        <div style="margin-left: 10px;margin-top:10px;color: red;">
            <span>偏移量：</span><br>
            <span>&nbsp;&nbsp;X坐标方向偏移：小于0向左，大于0向右；</span><br>
            <span>&nbsp;&nbsp;Y坐标方向偏移：小于0向上，大于0向下</span><br>
        </div>
        <div style="margin-left: 10px;margin-top:10px;color: red;">
            <span>标识码：</span><br>
            <span>&nbsp;&nbsp;-2代表查找的关键字有且只能有一个；</span><br>
            <span>&nbsp;&nbsp;-1代表查找文档中的所有关键字；</span><br>
            <span>&nbsp;&nbsp;0代表查找某页中的所有关键字；</span><br>
            <span>&nbsp;&nbsp;>0代表某页中的第几个关键字(序数查找形式从左到右、从上到下)</span><br>
        </div>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons">
            <input value="确定" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>

</body>

