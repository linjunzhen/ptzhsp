<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script>

        $(function() {

            $("#CREDIT_NAME").combobox({
                valueField: 'DIC_CODE',
                textField: 'DIC_NAME',
                editable:true,
                hasDownArrow:true,
                url: "creditController.do?getCreditNameList&ITEM_ID=${itemId}&AHEAD_USER_ID=${aheadUserId}&BACK_USER_ID=${backUserId}",
                required:false,
                panelHeight: '200',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) != -1;
                },
                onSelect:function (row) {

                }
            });

            $("#CREDIT_ORGANIZATION").combobox({
                valueField: 'DIC_CODE',
                textField: 'DIC_NAME',
                editable:true,
                hasDownArrow:true,
                url: "dictionaryController.do?load&typeCode=BZJG",
                required:false,
                panelHeight: '200',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) != -1;
                },
                onSelect:function (row) {

                }
            });

            $($("#CREDIT_NAME").next().children()[1]).change(function () {
                $("input[name='CREDIT_NAME_ELSE']").val($(this).val());
            });

            $($("#CREDIT_ORGANIZATION").next().children()[1]).change(function () {
                $("input[name='CREDIT_ORGANIZATION_ELSE']").val($(this).val());
            });

            AppUtil.initWindowForm("creditFeedBackForm", function(form, valid) {
                if (valid) {
                    var formData = FlowUtil.getFormEleData("creditFeedBackForm");
                    var CREDIT_NAME = $("input[name='CREDIT_NAME']").val();
                    var CREDIT_ORGANIZATION = $("input[name='CREDIT_ORGANIZATION']").val();
                    var CREDIT_NAME_ELSE = $("input[name='CREDIT_NAME_ELSE']").val();
                    var CREDIT_ORGANIZATION_ELSE = $("input[name='CREDIT_ORGANIZATION_ELSE']").val();
                    var CREDIT_TYPE = $("input[name='CREDIT_TYPE']").val();
                    var CREDIT_FILE_ID = $("input[name='CREDIT_FILE_ID']").val();
                    var url = $("#creditFeedBackForm").attr("action");
                    if (CREDIT_TYPE == '1') {
                        if (!isFieldNotEmpty(CREDIT_NAME) && !isFieldNotEmpty(CREDIT_NAME_ELSE)) {
                            parent.art.dialog({
                                content: "证照名称不能为空！",
                                icon: "warning",
                                time: 3,
                                ok: true
                            });
                            return;
                        }

                        if (!isFieldNotEmpty(CREDIT_NAME) && isFieldNotEmpty(CREDIT_NAME_ELSE)) {
                            formData['CREDIT_NAME'] = CREDIT_NAME_ELSE;
                            formData['CREDIT_NAME_FLAG'] = '1';
                        }

                        if (!isFieldNotEmpty(CREDIT_ORGANIZATION) && !isFieldNotEmpty(CREDIT_ORGANIZATION_ELSE)) {
                            parent.art.dialog({
                                content: "发证机构不能为空！",
                                icon: "warning",
                                time: 3,
                                ok: true
                            });
                            return;
                        }

                        if (!isFieldNotEmpty(CREDIT_ORGANIZATION) && isFieldNotEmpty(CREDIT_ORGANIZATION_ELSE)) {
                            formData['CREDIT_ORGANIZATION'] = CREDIT_ORGANIZATION_ELSE;
                        }

                    }

                    if (!isFieldNotEmpty(CREDIT_FILE_ID)) {
                        parent.art.dialog({
                            content: "请上传图片！",
                            icon: "warning",
                            time: 3,
                            ok: true
                        });
                        return;
                    }

                    //将提交按钮禁用,防止重复提交
                    $("input[type='submit']").attr("disabled", "disabled");
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
                                AppUtil.closeLayer();
                            } else {
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon: "error",
                                    ok: true
                                });
                                //将提交按钮禁用,防止重复提交
                                $("input[type='submit']").attr("disabled", false);
                            }
                        }
                    });
                }
            });
        });

        function isFieldNotEmpty(val) {
            return val && val != ''
        }


        function openCreditFeedBackFileUploadDialog() {
            //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
            //'fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BSFW_CREDIT_FEEDBACK'
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=image&materType=image&busTableName=T_BSFW_CREDIT_FEEDBACK', {
                title: "上传(图片)",
                width: "650px",
                height: "300px",
                lock: true,
                resize: false,
                close: function(){
                    var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                    if(uploadedFileInfo){
                        if(uploadedFileInfo.fileId){
                            //var fileTypes = 'gif、png、bmp、jpg、pdf、doc、docx';
                            var fileTypes = 'gif,jpg,jpeg,bmp,png';
                            if (fileTypes.indexOf(uploadedFileInfo.fileSuffix.toLowerCase()) != -1) {
                                /*var fileType = 'gif,jpg,jpeg,bmp,png';
                                var attachType = 'attach';
                                if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                                    attachType = "image";
                                }*/
                                var attachType = 'image';
                                var fileid = $("input[name='CREDIT_FILE_ID']").val();
                                if (fileid != "" && fileid != null) {
                                    $("input[name='CREDIT_FILE_ID']").val(fileid + ";" + uploadedFileInfo.fileId);
                                } else {
                                    $("input[name='CREDIT_FILE_ID']").val(uploadedFileInfo.fileId);
                                }
                                var spanHtml = "<p style='margin-left: 120px;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                                    + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                                    + "&attachType=" + attachType + "\" ";
                                spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                                spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "');\" ><font color=\"red\">删除</font></a></p>";
                                $("#creditFeedBackFile").append(spanHtml);
                            } else {
                                art.dialog({
                                    //content:"文件类型为gif、png、bmp、jpg、pdf、doc、docx",
                                    content:"文件类型为" + fileTypes,
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
                //移除目标span
                $("#"+fileId).remove();
                var fileid=$("input[name='CREDIT_FILE_ID']").val();
                var arrayIds=fileid.split(";");
                for(var i=0;i<arrayIds.length;i++){
                    if(arrayIds[i]==fileId){
                        arrayIds.splice(i,1);
                        break;
                    }
                }
                $("input[name='CREDIT_FILE_ID']").val(arrayIds.join(";"));
            });
        }

        function chooseXXCJCtrl() {
            var gpytype = $('input[name="GPYTYPE"]:checked').val();
            if(gpytype==""||gpytype==undefined){
                parent.art.dialog({
                    content : "请选择高拍仪类型",
                    icon : "error",
                    time : 3,
                    ok : true
                });
            }else if(gpytype=="1"){
                bindScanCtrl();
            }else if(gpytype=="2"){
                bindScanCtrlLT();
            }else if(gpytype=="3"){
                bindScanCtrlZT();
            }else if(gpytype=="4"){
                bindScanCtrlXWJ();
            }
        }
        function bindScanCtrl() {
            //定义上传的人员的ID
            var uploadUserId = 'creditFeedBack';
            //定义上传的人员的NAME
            var uploadUserName = 'creditFeedBack';

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&attachKey="
                + "creditFeedBack" + "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName) + "&busTableName=T_BSFW_CREDIT_FEEDBACK", {
                title : "高拍仪",
                width : "810px",
                lock : true,
                resize : false,
                height : "90%",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        initScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function bindScanCtrlZT() {
            //定义上传的人员的ID
            var uploadUserId = 'creditFeedBack';
            //定义上传的人员的NAME
            var uploadUserName = 'creditFeedBack';
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&attachKey="
                + "creditFeedBack" + "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName) + "&busTableName=T_BSFW_CREDIT_FEEDBACK", {
                title : "高拍仪",
                width : "810px",
                lock : true,
                resize : false,
                height : "90%",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        initScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function bindScanCtrlXWJ() {
            //定义上传的人员的ID
            var uploadUserId = 'creditFeedBack';
            //定义上传的人员的NAME
            var uploadUserName = 'creditFeedBack';
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlXWJ.jsp?uploadPath=applyform&attachKey="
                + "creditFeedBack" + "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName) + "&busTableName=T_BSFW_CREDIT_FEEDBACK", {
                title : "高拍仪",
                width : "810px",
                lock : true,
                resize : false,
                height : "90%",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        initScanUploadMaters(resultJsonInfo );
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function initScanUploadMaters(resultJson) {
            for (var i = 0; i < resultJson.length; i++) {
                var uploadedFileInfo = resultJson[i].data;
                if(uploadedFileInfo.fileId) {
                    var fileid = $("input[name='CREDIT_FILE_ID']").val();
                    if (fileid != "" && fileid != null) {
                        $("input[name='CREDIT_FILE_ID']").val(fileid + ";" + uploadedFileInfo.fileId);
                    } else {
                        $("input[name='CREDIT_FILE_ID']").val(uploadedFileInfo.fileId);
                    }
                    var spanHtml = "<p style='margin-left: 120px;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                        + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                        + "&attachType=" + uploadedFileInfo.attachType + "\" ";
                    spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                    spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                    spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "');\" ><font color=\"red\">删除</font></a></p>";
                    $("#creditFeedBackFile").append(spanHtml);
                }
            }
        }
    </script>
</head>

<body class="easyui-layout eui-diabody" fit="true" style="margin:0px;">
    <form id="creditFeedBackForm" method="post" action="creditController.do?saveOrUpdateCreditFeedBack">
        <div id="creditFeedBackFormDiv" data-options="region:'center',split:true,border:false">
            <!--==========隐藏域部分开始 ===========-->
            <input type="hidden" name="CREDIT_TYPE" value="${creditFeedBackType}">
            <input type="hidden" name="CREDIT_FILE_ID" value="${creditFeedBack.CREDIT_FILE_ID}">
            <input type="hidden" name="ITEM_ID" value="${itemId}">
            <input type="hidden" name="AHEAD_USER_ID" value="${aheadUserId}">
            <input type="hidden" name="BACK_USER_ID" value="${backUserId}">
            <input type="hidden" name="LICENSE_FILE_ID" value="${fileId}">
            <input type="hidden" name="CREDIT_FEEDBACK_MARK" value="${creditMark}">
            <input type="hidden" name="CREDIT_NAME_ELSE">
            <input type="hidden" name="CREDIT_ORGANIZATION_ELSE">
            <!--==========隐藏域部分结束 ===========-->
            <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
                <tr>
                    <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
                </tr>
                <c:if test="${creditFeedBackType == '1'}">
                    <tr>
                        <td>
                            <span style="width: 120px;float:left;text-align:right;">证照名称：</span>
                            <input class="easyui-combobox" name="CREDIT_NAME" id="CREDIT_NAME" style="width: 180px;height: 30px;"/>
                            <font class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                        <td><span style="width: 120px;float:left;text-align:right;">发证机构：</span>
<%--                            <eve:eveselect clazz="tab_text validate[required]" dataParams="BZJG"--%>
<%--                                           dataInterface="dictionaryService.findDatasForSelect"--%>
<%--                                           defaultEmptyText="请选择发证机构"  name="CREDIT_ORGANIZATION">--%>
<%--                            </eve:eveselect>--%>
                            <input class="easyui-combobox" name="CREDIT_ORGANIZATION" id="CREDIT_ORGANIZATION" style="width: 180px;height: 30px;"/>
                            <font class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="width: 120px;float:left;text-align:right;">证照编号：</span>
                            <input type="text" style="width:180px;float:left;" maxlength="128"
                                   class="eve-input validate[required]"
                                    name="CREDIT_NUM" /><font
                                    class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                        <td><span style="width: 120px;float:left;text-align:right;">发证时间：</span>
                            <input type="text" style="width:180px;float:left;" maxlength="36"
                                   class="eve-input Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"
                                    name="CREDIT_TIME" /><font
                                    class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><span style="width: 120px;float:left;text-align:right;">持证者名称：</span>
                            <input type="text" style="width:180px;float:left;" maxlength="128"
                                   class="eve-input validate[required]" value="${names}"
                                    name="CREDIT_HOLDER_NAME" /><font
                                    class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                        <td><span style="width: 120px;float:left;text-align:right;">持证者代码类型：</span>
                            <eve:eveselect clazz="tab_text validate[required]" dataParams="CZZDMLX"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="请选择持证者代码类型"  name="CREDIT_HOLDER_TYPE">
                            </eve:eveselect>
                            <font class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <c:if test="${creditFeedBackType == '1'}">
                        <td><span style="width: 120px;float:left;text-align:right;">持证者代码：</span>
                            <input type="text" style="width:180px;float:left;" maxlength="18" value="${codes}"
                                   class="eve-input validate[required]"
                                    name="CREDIT_HOLDER_CODE" /><font
                                    class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                    </c:if>
                    <td><span style="width: 120px;float:left;text-align:right;">联系电话：</span>
                        <input type="text" style="width:180px;float:left;" maxlength="16"
                               class="eve-input"
                                name="CREDIT_PHONE" />
                    </td>
                </tr>
                <c:if test="${creditFeedBackType == '2'}">
                    <tr>
                        <td colspan="4"><span style="width: 120px;float:left;text-align:right;">问题描述：</span>
                            <textarea name="CREDIT_DESCRIBE" cols="95" rows="5" class="validate[maxSize[512],required]"></textarea><font class="dddl_platform_html_requiredFlag">*</font>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="tab_width"> <span style="width: 120px;float:left;text-align:right;">高拍仪类型：</span>
                        <input type="radio" name="GPYTYPE" value="1" >服务中心
                        <input type="radio" name="GPYTYPE" value="2" >台胞台企
                        <input type="radio" name="GPYTYPE" value="3" >苏平
                        <input type="radio" name="GPYTYPE" value="4" >农商
                    </td>
                </tr>
                <tr id="upload">
                    <td colspan="4">
                        <span style="width: 120px;float:left;text-align:right;">图片上传：</span>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openCreditFeedBackFileUploadDialog()">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <a href="javascript:chooseXXCJCtrl()"><img src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
                            <font class="dddl_platform_html_requiredFlag">*</font>
                            <span id="creditFeedBackFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
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