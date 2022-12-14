<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
    request.setAttribute("nowDate", nowDate);
    String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    request.setAttribute("nowTime", nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,autocomplete"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
    <script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/dyqscdj/js/bdcdyqscdj.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>

    <script type="text/javascript">
        $(function() {

            $("#djjfxx_dyqscdj").attr("style","display:none;");
            $("#djfzxx_dyqscdj").attr("style","display:none;");

            //??????????????????????????????
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;
            var flowSubmitObj = FlowUtil.getFlowObj();
            var dealItems = '${dealItem.DEALITEM_NODE}'; //???DB????????????????????????????????????,JBPM6_CHECKDEALITEM
            dealItems = "," + dealItems + ",";
            if (flowSubmitObj) {
                //????????????????????????????????????
                var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                var exeid = flowSubmitObj.EFLOW_EXEID;
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
                //?????????????????????????????????
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_DYQSCDJ_FORM");
                //????????????????????????
                if (flowSubmitObj.busRecord) {
                    initAutoTable(flowSubmitObj); //?????????????????????
                    //initDjxxTable(flowSubmitObj); //?????????????????????
                    setTakecardType(flowSubmitObj.busRecord.TAKECARD_TYPE);
                    setSfzgedy(flowSubmitObj.busRecord.DYQDJ_SFZGEDY);
                    //????????????????????????
                    dyqrChangeFun(flowSubmitObj.busRecord.DYQRXX_NATURE,false);
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_DYQSCDJ_FORM");
                    var isEndFlow = false;
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
                        isEndFlow = true;
                    }
                    if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='??????'){
					      //??????????????????-???????????????????????????       
					      $("select[name='DYQRXX_IDTYPE']").removeAttr("disabled");
					}
                    // ??????????????????????????????
                    if (isEndFlow) {
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='text']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='button']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='checkbox']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='radio']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input,select").attr("disabled", "disabled");

                        //???????????????
                        $("#bdcqzh_tr").attr("style","");
                        $("#bdcdbc_tr").attr("style","");
                        $("#bdcqzbsm_tr").attr("style","");
                        $("#bdcczr_tr").attr("style","");
                        $("#qzbsmsavebtn").remove();
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        //???????????????????????????
                        $("#djjfxx_dyqscdj").attr("style","");
                        $("#djfzxx_dyqscdj").attr("style","");
                    }

                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '??????') {
                        $("#T_BDCQLC_DYQSCDJ_FORM input").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        $("#T_BDCQLC_DYQSCDJ_FORM select").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        $("#T_BDCQLC_DYQSCDJ_FORM textarea").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        if ($("input[name='SBMC']").val()) {
                        } else {
                            $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
                        }
                        $('#DYQRXX_NAME').combobox('disable');
                        //????????????????????????????????????
                        $("#sfspb").attr("onclick","goPrintTemplate('DYQSCDJSPB','3');");
						$("#dfspb").attr("onclick","goPrintTemplate('DYQSCDJSPB','3');");
                    } else if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
                        $("#userinfo_div input").each(function(index) {
                            $(this).attr("disabled", "disabled");
                        });
                        $("#userinfo_div select").each(function(index) {
                            $(this).attr("disabled", "disabled");
                        });
                        $("#baseinfo input").each(function(index) {
                            $(this).attr("disabled", "disabled");
                        });
                    }

                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
                        /* $("#powerInfo input , #powerInfo select , #powerInfo textarea").each(function () {
                            $(this).attr("disabled", false);
                        }); */
                       	//????????????-??????????????????????????????????????????????????????
						$("input[name='DYSW']").attr("disabled", false);
						$("input[name='deleteDymxInfoInput']").show();
						$("#dymxInfo").find("input[type='button']").attr("disabled", false);
                    }

                    //????????????????????????
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
                        $("#bdcdcz_btn").attr("style","");
                        $("#bdcdbc_tr").attr("style","");
                        $("#dyqbc").show();
                        //?????????????????????
                   	    disabledDbBtn("qrdb");
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        /*  $("#powerInfo input , #powerInfo select , #powerInfo textarea").each(function () {
                            $(this).attr("disabled", false);
                        }); */
                        //???????????????-????????????????????????????????????????????????????????????????????????
                        $("#powerInfo").find("input[type='button']").attr("disabled", false);
                        $("#zgzqqdssFont").show();
						$("#dyfwFont").show();
						$("#fjFont").show();
                        $("input[name='DYQDJ_ZGZQQDSS']").attr("disabled", false).addClass("validate[required]");
						$("input[name='DYQDJ_DYFW']").attr("disabled", false).addClass("validate[required]");
						$("textarea[name='DYQDJ_FJ']").attr("disabled", false).addClass("validate[required]");
                    }
                    
                    //?????????????????????????????????
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
                        $("#bdcqzh_tr").attr("style","");
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        $("#bdcdbc_tr").attr("style","");
                        $("#bdcqzbsm_tr").attr("style","");
                        $("#bdcczr_tr").attr("style","");
                        $("#qzbsmsavebtn").remove();
                    }


                    //????????????????????????????????????
                    var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
                    if(BDC_OPTYPE != null && BDC_OPTYPE !=""){
                        $("#T_BDCQLC_DYQSCDJ_FORM input").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        $("#T_BDCQLC_DYQSCDJ_FORM select").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        //?????????????????????
                        if(BDC_OPTYPE == "1"){
                            $("#bdcqzh_tr").attr("style","");
                            $("#bdcdbc_tr").attr("style","");
                            $("#bdcqzbsm_tr").attr("style","");
                            $("#bdcczr_tr").attr("style","");
                            $("#BDC_QZVIEW").attr("style","");
                            $("#BDC_QZPRINT").attr("style","");
                            $("#BDC_QZVIEW").removeAttr("disabled");
                            $("#BDC_QZPRINT").removeAttr("disabled");
                            $("input[name='BDC_QZBSM']").removeAttr("disabled");
                            $("input[name='BDC_QZBSM']").addClass(" validate[required]");
                            $("#qzbsmsavebtn").attr("style","");
                            $("#qzbsmsavebtn").removeAttr("disabled");
                        }else if(BDC_OPTYPE == "2"){
                            $("#bdcqzh_tr").attr("style","");
                            $("#bdcdbc_tr").attr("style","");
                            $("#bdcqzbsm_tr").attr("style","");
                            $("#bdcczr_tr").attr("style","");
                            $("#qzbsmsavebtn").remove();
                            $("#BDC_QZVIEW").attr("style","");
                            $("#BDC_QZVIEW").removeAttr("disabled");
                            //???????????????????????????
                            $("#djjfxx_dyqscdj").attr("style","");
                            $("#djfzxx_dyqscdj").attr("style","");

                            $("#djjfxx_dyqscdj input").each(function(index){
                                $(this).removeAttr("disabled");
                            });
                            $("#djjfxx_dyqscdj select").each(function(index){
                                $(this).removeAttr("disabled");
                            });

                            $("#djfzxx_dyqscdj input").each(function(index){
                                $(this).removeAttr("disabled");
                            });
                            $("#djfzxx_dyqscdj select").each(function(index){
                                $(this).removeAttr("disabled");
                            });

                            var currentUser = $("input[name='uploadUserName']").val();
                            var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
                            $("input[name='DJFZXX_FZRY']").val(currentUser);
                            $("input[name='DJFZXX_FZSJ']").val(currentTime);
                            $("input[name='DJJFMX_SFRQ']").val(currentTime);
                        }else if(BDC_OPTYPE == "flag1"){
                            $("#bdcqzh_tr").attr("style","");
                            $("#bdcdbc_tr").attr("style","");
                            $("#bdcqzbsm_tr").attr("style","");
                            $("#bdcczr_tr").attr("style","");
                            $("#BDC_QZVIEW").attr("style","");
                            //$("#BDC_QZPRINT").attr("style","");
                            $("#BDC_QZVIEW").removeAttr("disabled");
                            //$("#BDC_QZPRINT").removeAttr("disabled");
                            //$("input[name='BDC_QZBSM']").removeAttr("disabled");
                            $("input[name='BDC_QZBSM']").addClass(" validate[required]");
                            //$("#qzbsmsavebtn").attr("style","");
                            //$("#qzbsmsavebtn").removeAttr("disabled");
                        }else if(BDC_OPTYPE == "flag2"){
                            $("#bdcqzh_tr").attr("style","");
                            $("#bdcdbc_tr").attr("style","");
                            $("#bdcqzbsm_tr").attr("style","");
                            $("#bdcczr_tr").attr("style","");
                            $("#qzbsmsavebtn").remove();
                            $("#BDC_QZVIEW").attr("style","");
                            $("#BDC_QZVIEW").removeAttr("disabled");
                            //???????????????????????????
                            $("#djjfxx_dyqscdj").attr("style","");
                            $("#djfzxx_dyqscdj").attr("style","");
                        }

                    }
                } else {
                    $("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
                    //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1
                    $("select[name='DYQDJ_SFZGEDY']").val("0");
                    $("select[name='DYQDJ_DYFS']").val("1");
                    $("input[name='DYQDJ_DYZS']").val("1");
                    $("input[name='DYQDJ_ZGZQE']").attr("disabled", true);
                    //5??????????????????????????????????????????
                    $("input[name='DYQDJ_DJYY']").val("????????????");
                    //6?????????????????????????????????????????????????????????????????????????????????????????????
                    $("select[name='DYQRXX_LEGAL_IDTYPE']").val("?????????");
                    //$("input[name='DYQRXX_LEGAL_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQRXX_AGENT_IDTYPE']").val("?????????");
                    //$("input[name='DYQRXX_AGENT_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_IDTYPE']").val("?????????");
                    //$("input[name='DYQDJ_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_AGENT_IDTYPE']").val("?????????");
                    //$("input[name='DYQDJ_AGENT_IDNO']").addClass(",custom[vidcard]");

                    //??????????????????????????????'???????????????'
                    $("select[name='DYBDCLX']").val("2");
                }

                var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
                if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
                    $("#ptcyjb").attr("style", "");
                    if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "??????") {
                        $("input[name='JBR_NAME']").removeAttr('readonly');
                    }
                }
            }
            //???????????????????????????????????????
            //isButtonAvailable();
        });

        // -------------------???????????????????????????????????? ??????-------------------------------------------------------------
        $(function() {
            //?????????????????????
            var fileids = $("input[name='DYQRXX_FILE_ID']").val();
            $.post("executionController.do?getResultFiles&fileIds=" + fileids, {
                fileIds : fileids
            }, function(resultJson) {
                if (resultJson != "websessiontimeout") {
                    $("#DYQRXX_fileListDiv").html("");
                    var newhtml = "";
                    var list = resultJson.rows;
                    for (var i = 0; i < list.length; i++) {
                        newhtml += '<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
                        newhtml += '<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\'' + list[i].FILE_ID + '\');">';
                        newhtml += list[i].FILE_NAME + '</a>';
                        newhtml += '</p>';
                    }
                    $("#DYQRXX_fileListDiv").html(newhtml);
                }
            });
        });

        /**
         * ???????????????????????????
         */
        function openDyqrxxxFileUploadDialog() {
            //???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_DYQSCDJ', {
                title : "??????(??????)",
                width : "620px",
                height : "320px",
                lock : true,
                resize : false,
                close : function() {
                    var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                    if (uploadedFileInfo) {
                        if (uploadedFileInfo.fileId) {
                            var fileType = 'gif,jpg,jpeg,bmp,png';
                            var attachType = 'attach';
                            if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                                attachType = "image";
                            }
                            var fileurl = $("input[name='DYQRXX_FILE_URL']").val();
                            var fileid = $("input[name='DYQRXX_FILE_ID']").val();
                            if (fileurl != "" && fileurl != null) {
                                $("input[name='DYQRXX_FILE_URL']").val(fileurl + ';download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
                                $("input[name='DYQRXX_FILE_ID']").val(fileid + ";" + uploadedFileInfo.fileId);
                            } else {
                                $("input[name='DYQRXX_FILE_URL']").val('download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
                                $("input[name='DYQRXX_FILE_ID']").val(uploadedFileInfo.fileId);
                            }
                            var spanHtml = "<p id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                                + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                                + "&attachType=" + attachType + "\" ";
                            spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                            spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>"
                            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQRXXdelUploadFile('" + uploadedFileInfo.fileId + "','');\" ><font color=\"red\">??????</font></a></p>"
                            $("#DYQRXX_fileListDiv").append(spanHtml);
                        }
                    }
                    art.dialog.removeData("uploadedFileInfo");
                }
            });
        }

        function DYQRXXchooseCtrl() {
            var gpytype = $('input[name="GPYTYPE"]:checked').val();
            if (gpytype == "" || gpytype == undefined) {
                parent.art.dialog({
                    content : "????????????????????????",
                    icon : "error",
                    time : 3,
                    ok : true
                });
            } else if (gpytype == "1") {
                DYQRXXbindScanCtrl();
            } else if (gpytype == "2") {
                DYQRXXbindScanCtrlLT();
            } else if (gpytype == "3") {
                DYQRXXbindScanCtrlZT();
            }
        }
        function DYQRXXbindScanCtrl() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQRXXinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function DYQRXXbindScanCtrlZT() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQRXXinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function DYQRXXbindScanCtrlLT() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQRXXinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }

        function DYQRXXinitScanUploadMaters(resultJson) {
            for (var i = 0; i < resultJson.length; i++) {

                var fileurl = $("input[name='DYQRXX_FILE_URL']").val();
                var fileid = $("input[name='DYQRXX_FILE_ID']").val();
                if (fileurl != "" && fileurl != null) {
                    $("input[name='DYQRXX_FILE_URL']").val(fileurl + ";" + resultJson[i].data.filePath);
                    $("input[name='DYQRXX_FILE_ID']").val(fileid + ";" + resultJson[i].data.fileId);
                } else {
                    $("input[name='DYQRXX_FILE_URL']").val(resultJson[i].data.filePath);
                    $("input[name='DYQRXX_FILE_ID']").val(resultJson[i].data.fileId);
                }
                //????????????ID
                var fileId = resultJson[i].data.fileId;
                //??????????????????
                var fileName = resultJson[i].data.fileName;
                //????????????KEY
                var attachKey = resultJson[i].data.attachKey;
                //??????????????????
                var filePath = resultJson[i].data.filePath;
                //?????????????????????
                var fileType = resultJson[i].data.fileType;
                var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQRXXdelUploadFile('" + fileId + "','" + attachKey + "');\" ><font color=\"red\">??????</font></a></p>"
                $("#DYQRXX_fileListDiv").append(spanHtml);
            }
        }
        function DYQRXXdelUploadFile(fileId, attacheKey) {
            //AppUtil.delUploadMater(fileId,attacheKey);
            art.dialog.confirm("???????????????????????????????", function() {
                //????????????span
                $("#" + fileId).remove();
                var fileurl = $("input[name='DYQRXX_FILE_URL']").val();
                var fileid = $("input[name='DYQRXX_FILE_ID']").val();
                var arrayIds = fileid.split(";");
                var arrayurls = fileurl.split(";");
                for (var i = 0; i < arrayIds.length; i++) {
                    if (arrayIds[i] == fileId) {
                        arrayIds.splice(i, 1);
                        arrayurls.splice(i, 1);
                        break;
                    }
                }
                $("input[name='DYQRXX_FILE_URL']").val(arrayurls.join(";"));
                $("input[name='DYQRXX_FILE_ID']").val(arrayIds.join(";"));
            });
        }
        // -------------------???????????????????????????????????? ??????-------------------------------------------------------------
        // -------------------????????????????????????????????? ??????-------------------------------------------------------------
        $(function() {
            //?????????????????????
            var fileids = $("input[name='DYQDJ_FILE_ID']").val();
            $.post("executionController.do?getResultFiles&fileIds=" + fileids, {
                fileIds : fileids
            }, function(resultJson) {
                if (resultJson != "websessiontimeout") {
                    $("#DYQDJ_fileListDiv").html("");
                    var newhtml = "";
                    var list = resultJson.rows;
                    for (var i = 0; i < list.length; i++) {
                        newhtml += '<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
                        newhtml += '<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\'' + list[i].FILE_ID + '\');">';
                        newhtml += list[i].FILE_NAME + '</a>';
                        newhtml += '</p>';
                    }
                    $("#DYQDJ_fileListDiv").html(newhtml);
                }
            });
        });

        /**
         * ???????????????????????????
         */
        function openDyqdjFileUploadDialog() {
            //???????????????:video-?????? attach-?????? audio-?????? flash-flash image-??????
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_DYQSCDJ', {
                title : "??????(??????)",
                width : "620px",
                height : "320px",
                lock : true,
                resize : false,
                close : function() {
                    var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                    if (uploadedFileInfo) {
                        if (uploadedFileInfo.fileId) {
                            var fileType = 'gif,jpg,jpeg,bmp,png';
                            var attachType = 'attach';
                            if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                                attachType = "image";
                            }
                            var fileurl = $("input[name='DYQDJ_FILE_URL']").val();
                            var fileid = $("input[name='DYQDJ_FILE_ID']").val();
                            if (fileurl != "" && fileurl != null) {
                                $("input[name='DYQDJ_FILE_URL']").val(fileurl + ';download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
                                $("input[name='DYQDJ_FILE_ID']").val(fileid + ";" + uploadedFileInfo.fileId);
                            } else {
                                $("input[name='DYQDJ_FILE_URL']").val('download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
                                $("input[name='DYQDJ_FILE_ID']").val(uploadedFileInfo.fileId);
                            }
                            var spanHtml = "<p id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                                + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                                + "&attachType=" + attachType + "\" ";
                            spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                            spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>"
                            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('" + uploadedFileInfo.fileId + "','');\" ><font color=\"red\">??????</font></a></p>"
                            $("#DYQDJ_fileListDiv").append(spanHtml);
                        }
                    }
                    art.dialog.removeData("uploadedFileInfo");
                }
            });
        }
        function DYQDJchooseCtrl() {
            var gpytype = $('input[name="GPYTYPE"]:checked').val();
            if (gpytype == "" || gpytype == undefined) {
                parent.art.dialog({
                    content : "????????????????????????",
                    icon : "error",
                    time : 3,
                    ok : true
                });
            } else if (gpytype == "1") {
                DYQDJbindScanCtrl();
            } else if (gpytype == "2") {
                DYQDJbindScanCtrlLT();
            } else if (gpytype == "3") {
                DYQDJbindScanCtrlZT();
            }
        }
        function DYQDJbindScanCtrl() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQDJinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function DYQDJbindScanCtrlLT() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQDJinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }
        function DYQDJbindScanCtrlZT() {
            var onlineBusTableName = "T_BDCQLC_DYQSCDJ";
            //????????????????????????ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //????????????????????????NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "?????????",
                width : "800px",
                lock : true,
                resize : false,
                height : "620px",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        DYQDJinitScanUploadMaters(resultJsonInfo);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
        }

        // ????????????
        function doBooking() {
            BdcUtil.doBooking();
        }

        // ????????????
        function provePreview() {
            alert("????????????");
        }

        function DYQDJinitScanUploadMaters(resultJson) {
            for (var i = 0; i < resultJson.length; i++) {

                var fileurl = $("input[name='DYQDJ_FILE_URL']").val();
                var fileid = $("input[name='DYQDJ_FILE_ID']").val();
                if (fileurl != "" && fileurl != null) {
                    $("input[name='DYQDJ_FILE_URL']").val(fileurl + ";" + resultJson[i].data.filePath);
                    $("input[name='DYQDJ_FILE_ID']").val(fileid + ";" + resultJson[i].data.fileId);
                } else {
                    $("input[name='DYQDJ_FILE_URL']").val(resultJson[i].data.filePath);
                    $("input[name='DYQDJ_FILE_ID']").val(resultJson[i].data.fileId);
                }
                //????????????ID
                var fileId = resultJson[i].data.fileId;
                //??????????????????
                var fileName = resultJson[i].data.fileName;
                //??????????????????
                var filePath = resultJson[i].data.filePath;
                //?????????????????????
                var fileType = resultJson[i].data.fileType;
                var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('" + fileId + "','');\" ><font color=\"red\">??????</font></a></p>"
                $("#DYQDJ_fileListDiv").append(spanHtml);
            }
        }
        /* function DYQDJdelUploadFile(fileId,attacheKey){
            AppUtil.delUploadMater(fileId,attacheKey);
            var fileurl=$("input[name='DYQDJ_FILE_URL']").val();
            var fileid=$("input[name='DYQDJ_FILE_ID']").val();
            var arrayIds=fileid.split(";");
            var arrayurls=fileurl.split(";");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    arrayurls.splice(i,1);
                    break;
                }
            }
            $("input[name='DYQDJ_FILE_URL']").val(arrayurls.join(";"));
            $("input[name='DYQDJ_FILE_ID']").val(arrayIds.join(";"));
        } */
        function DYQDJdelUploadFile(fileId, attacheKey) {
            //AppUtil.delUploadMater(fileId,attacheKey);
            art.dialog.confirm("???????????????????????????????", function() {
                //????????????span
                $("#" + fileId).remove();
                var fileurl = $("input[name='DYQDJ_FILE_URL']").val();
                var fileid = $("input[name='DYQDJ_FILE_ID']").val();
                var arrayIds = fileid.split(";");
                var arrayurls = fileurl.split(";");
                for (var i = 0; i < arrayIds.length; i++) {
                    if (arrayIds[i] == fileId) {
                        arrayIds.splice(i, 1);
                        arrayurls.splice(i, 1);
                        break;
                    }
                }
                $("input[name='DYQDJ_FILE_URL']").val(arrayurls.join(";"));
                $("input[name='DYQDJ_FILE_ID']").val(arrayIds.join(";"));
            });
        }
        // -------------------????????????????????????????????? ??????-------------------------------------------------------------

        //------------------------------------???????????????????????????---------------------------------------------------
        function MyGetData() //OCX??????????????????????????????
        {
            // 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- ??????--!>
            // 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- ??????--!>
        }

        function MyClearData() //OCX??????????????????????????????
        {
            alert("????????????????????????????????????????????????");
            $("input[name='POWERPEOPLENAME']").val("");
            $("input[name='POWERPEOPLEIDNUM']").val("");
        }

        function MyGetErrMsg() //OCX????????????????????????
        {
            // 			Status.value = GT2ICROCX.ErrMsg;
        }

        function PowLegalRead() //????????????
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //????????????
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQRXX_LEGAL_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQRXX_LEGAL_IDNO']").val(GT2ICROCX.CardNo)
            }
        }
        function PowAgentRead() //????????????
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //????????????
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQRXX_AGENT_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQRXX_AGENT_IDNO']").val(GT2ICROCX.CardNo)
            }
        }
        function PowDyqdjDyrRead() //????????????
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //????????????
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                var dyqdjName = $("input[name='DYQDJ_NAME']").val();
                var dyqdjIdno = $("input[name='DYQDJ_IDNO']").val();
                if (null != dyqdjName && '' != dyqdjName) {
                    $("input[name='DYQDJ_NAME']").val(dyqdjName + "???" + GT2ICROCX.Name)
                } else {
                    $("input[name='DYQDJ_NAME']").val(GT2ICROCX.Name)
                }

                if (null != dyqdjIdno && '' != dyqdjIdno) {
                    $("input[name='DYQDJ_IDNO']").val(dyqdjIdno + "???" + GT2ICROCX.CardNo)
                } else {
                    $("input[name='DYQDJ_IDNO']").val(GT2ICROCX.CardNo)
                }
                checkLimitPerson();
            }
        }
        function DjxxLzrLegalRead() //????????????
        {
            GT2ICROCX.PhotoPath = "c:"
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DJFZXX_LZRXM']").val(GT2ICROCX.Name)
                $("input[name='DJFZXX_LZRZJHM']").val(GT2ICROCX.CardNo)
            }
        }
        function PowDyqdjAgentRead() //????????????
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //????????????
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQDJ_AGENT_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQDJ_AGENT_IDNO']").val(GT2ICROCX.CardNo)
            }
        }

        function LZRRead(){
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //????????????
            //????????????(?????????????????????)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='QZR_NAME']").val(GT2ICROCX.Name);
                $("input[name='QZR_ZJH']").val(GT2ICROCX.CardNo);
            }
        }

        function print() //??????
        {
            GT2ICROCX.PrintFaceImage(0, 30, 10) //0 ?????????1 ?????????2 ??????
        }

        //------------------------------------???????????????????????????---------------------------------------------------
    </script>
    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//??????????????????
    MyGetData()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//??????????????????
    MyGetErrMsg()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//??????????????????
    MyClearData()
    </SCRIPT>
    <style>
        .sel {
            border: solid 1px red;
        }
        .tab_text {
            width: 350px;
            height: 24px;
            line-height: 24px;
            /* padding: 0 5px; */
            padding: 2px 3px 2px 1px;
            border: 1px solid #bbb;
        }
    </style>
</head>

<body>
<input type="hidden" id="sxtsx" name="sxtsx" value="0" />
<input id="AutoExposure" type="hidden" onclick="autoexposure()" />
<input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
<input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
<input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
<div class="detail_title">
    <h1>${serviceItem.ITEM_NAME}</h1>
</div>
<form id="T_BDCQLC_DYQSCDJ_FORM" method="post">
    <%--===================????????????????????????=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
    <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
    <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
    <input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
    <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
    <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
    <!-- ???????????????????????????????????????????????????????????????????????????????????? -->
    <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

    <%--????????????--%>
    <input type="hidden" name="QSLY_JSON" />
    <%--????????????--%>
    <input type="hidden" name="DYMX_JSON" />
    
    <input type="hidden" name="ISQCWB" value="${busRecord.ISQCWB}" />



    <%--===================????????????????????????=========== --%>
    <%--?????????????????????????????????--%>
    <jsp:include page="./bdcqlc/bdcJbxx.jsp" />
    <%--????????????????????????????????? --%>

    <%--??????????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyuserinfo.jsp" />
    <%--?????????????????????????????? --%>

    <%--????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/T_BDCDYSSCDJ_ACCEPTINFO.jsp" />
    <%--????????????????????????--%>

    <%--???????????????????????????????????? --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="2" name="isWebsite" />
    </jsp:include>
    <%--???????????????????????????????????? --%>

    <%--??????????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyQsly.jsp" />
    <%--??????????????????????????????--%>

    <%--???????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/T_BDCDYSSCDJ_PLEDGEEINFO.jsp" />
    <%--???????????????????????????--%>

    <%--??????????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyDymx.jsp" />
    <%--??????????????????????????????--%>

    <%--????????????????????????--%>
    <jsp:include page="./bdcqlc/dyqscdj/bdcDjshxx.jsp" />
    <%--????????????????????????--%>

    <%--????????????????????????????????????--%>
    <jsp:include page="./bdcqlc/gyjsjfwzydj/yhXwjl.jsp" />
    <%--????????????????????????????????????--%>

    <%-- ????????????????????????????????????????????????????????? --%>
    <!-- djshxx:??????????????????,djjfxx:??????????????????,djfzxx:??????????????????,djdaxx:?????????????????? -->
    <!-- optype:??????0??????????????????1????????????????????????????????? -->
    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
        <jsp:param value="dyqscdj" name="domId" />
        <jsp:param value="djjfxx,djfzxx" name="initDomShow" />
    </jsp:include>
    <%-- ????????????????????????????????????????????????????????? --%>

    <jsp:include page="./bdcqlc/bdcRemark.jsp" />

</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
        CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
