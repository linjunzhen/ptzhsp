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

            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;
            var flowSubmitObj = FlowUtil.getFlowObj();
            var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
            dealItems = "," + dealItems + ",";
            if (flowSubmitObj) {
                //获取表单字段权限控制信息
                var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                var exeid = flowSubmitObj.EFLOW_EXEID;
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
                //初始化表单字段权限控制
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_DYQSCDJ_FORM");
                //初始化表单字段值
                if (flowSubmitObj.busRecord) {
                    initAutoTable(flowSubmitObj); //初始化权利信息
                    //initDjxxTable(flowSubmitObj); //初始化登记信息
                    setTakecardType(flowSubmitObj.busRecord.TAKECARD_TYPE);
                    setSfzgedy(flowSubmitObj.busRecord.DYQDJ_SFZGEDY);
                    //初始化权利人性质
                    dyqrChangeFun(flowSubmitObj.busRecord.DYQRXX_NATURE,false);
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_DYQSCDJ_FORM");
                    var isEndFlow = false;
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
                        isEndFlow = true;
                    }
                    if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
					      //抵押权人信息-证件类型开放可编辑       
					      $("select[name='DYQRXX_IDTYPE']").removeAttr("disabled");
					}
                    // 流程办理结束页面只读
                    if (isEndFlow) {
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='text']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='button']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='checkbox']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input[type='radio']").attr("disabled", "disabled");
                        $("#T_BDCQLC_DYQSCDJ_FORM").find("input,select").attr("disabled", "disabled");

                        //当流程结束
                        $("#bdcqzh_tr").attr("style","");
                        $("#bdcdbc_tr").attr("style","");
                        $("#bdcqzbsm_tr").attr("style","");
                        $("#bdcczr_tr").attr("style","");
                        $("#qzbsmsavebtn").remove();
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        //登记缴费和发证信息
                        $("#djjfxx_dyqscdj").attr("style","");
                        $("#djfzxx_dyqscdj").attr("style","");
                    }

                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
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
                        //除开始环节外审批表可打印
                        $("#sfspb").attr("onclick","goPrintTemplate('DYQSCDJSPB','3');");
						$("#dfspb").attr("onclick","goPrintTemplate('DYQSCDJSPB','3');");
                    } else if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
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

                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
                        /* $("#powerInfo input , #powerInfo select , #powerInfo textarea").each(function () {
                            $(this).attr("disabled", false);
                        }); */
                       	//抵押明细-抵押顺位可编辑、保存按钮开放且可点击
						$("input[name='DYSW']").attr("disabled", false);
						$("input[name='deleteDymxInfoInput']").show();
						$("#dymxInfo").find("input[type='button']").attr("disabled", false);
                    }

                    //登簿环节确认登簿
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
                        $("#bdcdcz_btn").attr("style","");
                        $("#bdcdbc_tr").attr("style","");
                        $("#dyqbc").show();
                        //初始化登簿按钮
                   	    disabledDbBtn("qrdb");
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        /*  $("#powerInfo input , #powerInfo select , #powerInfo textarea").each(function () {
                            $(this).attr("disabled", false);
                        }); */
                        //抵押权登记-最高债权确定事实、抵押范围、附记必填、按钮可点击
                        $("#powerInfo").find("input[type='button']").attr("disabled", false);
                        $("#zgzqqdssFont").show();
						$("#dyfwFont").show();
						$("#fjFont").show();
                        $("input[name='DYQDJ_ZGZQQDSS']").attr("disabled", false).addClass("validate[required]");
						$("input[name='DYQDJ_DYFW']").attr("disabled", false).addClass("validate[required]");
						$("textarea[name='DYQDJ_FJ']").attr("disabled", false).addClass("validate[required]");
                    }
                    
                    //办结环节不显示登簿按钮
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {
                        $("#bdcqzh_tr").attr("style","");
                        $("#BDC_QZVIEW").attr("style","");
                        $("#BDC_QZVIEW").removeAttr("disabled");
                        $("#bdcdbc_tr").attr("style","");
                        $("#bdcqzbsm_tr").attr("style","");
                        $("#bdcczr_tr").attr("style","");
                        $("#qzbsmsavebtn").remove();
                    }


                    //若从后台收费发证功能进入
                    var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
                    if(BDC_OPTYPE != null && BDC_OPTYPE !=""){
                        $("#T_BDCQLC_DYQSCDJ_FORM input").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        $("#T_BDCQLC_DYQSCDJ_FORM select").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        //填写权证标识码
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
                            //登记缴费和发证信息
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
                            //登记缴费和发证信息
                            $("#djjfxx_dyqscdj").attr("style","");
                            $("#djfzxx_dyqscdj").attr("style","");
                        }

                    }
                } else {
                    $("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
                    //抵押权登记模块中，是否最高额抵押值为否（默认选项），抵押方式值为一般抵押，被担保主债权数额为可编辑状态，最高债权额为不可编辑状态，抵押宗数默认值为1
                    $("select[name='DYQDJ_SFZGEDY']").val("0");
                    $("select[name='DYQDJ_DYFS']").val("1");
                    $("input[name='DYQDJ_DYZS']").val("1");
                    $("input[name='DYQDJ_ZGZQE']").attr("disabled", true);
                    //5、登记原因默认“首次登记”。
                    $("input[name='DYQDJ_DJYY']").val("首次登记");
                    //6、法定代表人、代理人、抵押人的证件类型默认为“身份证”可修改。
                    $("select[name='DYQRXX_LEGAL_IDTYPE']").val("身份证");
                    //$("input[name='DYQRXX_LEGAL_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQRXX_AGENT_IDTYPE']").val("身份证");
                    //$("input[name='DYQRXX_AGENT_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_IDTYPE']").val("身份证");
                    //$("input[name='DYQDJ_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_AGENT_IDTYPE']").val("身份证");
                    //$("input[name='DYQDJ_AGENT_IDNO']").addClass(",custom[vidcard]");

                    //抵押不动产类型默认为'土地和房屋'
                    $("select[name='DYBDCLX']").val("2");
                }

                var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
                if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
                    $("#ptcyjb").attr("style", "");
                    if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
                        $("input[name='JBR_NAME']").removeAttr('readonly');
                    }
                }
            }
            //表单内打印按钮是否可以操作
            //isButtonAvailable();
        });

        // -------------------抵押权人信息上传及高拍仪 开始-------------------------------------------------------------
        $(function() {
            //初始化材料列表
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
         * 标题附件上传对话框
         */
        function openDyqrxxxFileUploadDialog() {
            //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_DYQSCDJ', {
                title : "上传(附件)",
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
                            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQRXXdelUploadFile('" + uploadedFileInfo.fileId + "','');\" ><font color=\"red\">删除</font></a></p>"
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
                    content : "请选择高拍仪类型",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();
            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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
                //获取文件ID
                var fileId = resultJson[i].data.fileId;
                //获取文件名称
                var fileName = resultJson[i].data.fileName;
                //获取材料KEY
                var attachKey = resultJson[i].data.attachKey;
                //获取文件路径
                var filePath = resultJson[i].data.filePath;
                //获取文件的类型
                var fileType = resultJson[i].data.fileType;
                var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQRXXdelUploadFile('" + fileId + "','" + attachKey + "');\" ><font color=\"red\">删除</font></a></p>"
                $("#DYQRXX_fileListDiv").append(spanHtml);
            }
        }
        function DYQRXXdelUploadFile(fileId, attacheKey) {
            //AppUtil.delUploadMater(fileId,attacheKey);
            art.dialog.confirm("您确定要删除该文件吗?", function() {
                //移除目标span
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
        // -------------------抵押权人信息上传及高拍仪 结束-------------------------------------------------------------
        // -------------------抵押权登记上传及高拍仪 开始-------------------------------------------------------------
        $(function() {
            //初始化材料列表
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
         * 标题附件上传对话框
         */
        function openDyqdjFileUploadDialog() {
            //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_DYQSCDJ', {
                title : "上传(附件)",
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
                            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('" + uploadedFileInfo.fileId + "','');\" ><font color=\"red\">删除</font></a></p>"
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
                    content : "请选择高拍仪类型",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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
            //定义上传的人员的ID
            var uploadUserId = $("input[name='uploadUserId']").val();
            //定义上传的人员的NAME
            var uploadUserName = $("input[name='uploadUserName']").val();

            $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
                "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
                title : "高拍仪",
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

        // 确认登簿
        function doBooking() {
            BdcUtil.doBooking();
        }

        // 证明预览
        function provePreview() {
            alert("证明预览");
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
                //获取文件ID
                var fileId = resultJson[i].data.fileId;
                //获取文件名称
                var fileName = resultJson[i].data.fileName;
                //获取文件路径
                var filePath = resultJson[i].data.filePath;
                //获取文件的类型
                var fileType = resultJson[i].data.fileType;
                var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
                spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"DYQDJdelUploadFile('" + fileId + "','');\" ><font color=\"red\">删除</font></a></p>"
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
            art.dialog.confirm("您确定要删除该文件吗?", function() {
                //移除目标span
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
        // -------------------抵押权登记上传及高拍仪 结束-------------------------------------------------------------

        //------------------------------------身份身份证读取开始---------------------------------------------------
        function MyGetData() //OCX读卡成功后的回调函数
        {
            // 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>
            // 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>
        }

        function MyClearData() //OCX读卡失败后的回调函数
        {
            alert("未能有效识别身份证，请重新读卡！");
            $("input[name='POWERPEOPLENAME']").val("");
            $("input[name='POWERPEOPLEIDNUM']").val("");
        }

        function MyGetErrMsg() //OCX读卡消息回调函数
        {
            // 			Status.value = GT2ICROCX.ErrMsg;
        }

        function PowLegalRead() //开始读卡
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQRXX_LEGAL_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQRXX_LEGAL_IDNO']").val(GT2ICROCX.CardNo)
            }
        }
        function PowAgentRead() //开始读卡
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQRXX_AGENT_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQRXX_AGENT_IDNO']").val(GT2ICROCX.CardNo)
            }
        }
        function PowDyqdjDyrRead() //开始读卡
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                var dyqdjName = $("input[name='DYQDJ_NAME']").val();
                var dyqdjIdno = $("input[name='DYQDJ_IDNO']").val();
                if (null != dyqdjName && '' != dyqdjName) {
                    $("input[name='DYQDJ_NAME']").val(dyqdjName + "、" + GT2ICROCX.Name)
                } else {
                    $("input[name='DYQDJ_NAME']").val(GT2ICROCX.Name)
                }

                if (null != dyqdjIdno && '' != dyqdjIdno) {
                    $("input[name='DYQDJ_IDNO']").val(dyqdjIdno + "、" + GT2ICROCX.CardNo)
                } else {
                    $("input[name='DYQDJ_IDNO']").val(GT2ICROCX.CardNo)
                }
                checkLimitPerson();
            }
        }
        function DjxxLzrLegalRead() //开始读卡
        {
            GT2ICROCX.PhotoPath = "c:"
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DJFZXX_LZRXM']").val(GT2ICROCX.Name)
                $("input[name='DJFZXX_LZRZJHM']").val(GT2ICROCX.CardNo)
            }
        }
        function PowDyqdjAgentRead() //开始读卡
        {
            // 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='DYQDJ_AGENT_NAME']").val(GT2ICROCX.Name)
                $("input[name='DYQDJ_AGENT_IDNO']").val(GT2ICROCX.CardNo)
            }
        }

        function LZRRead(){
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("input[name='QZR_NAME']").val(GT2ICROCX.Name);
                $("input[name='QZR_ZJH']").val(GT2ICROCX.CardNo);
            }
        }

        function print() //打印
        {
            GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
        }

        //------------------------------------身份身份证读取结束---------------------------------------------------
    </script>
    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//设置回调函数
    MyGetData()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//设置回调函数
    MyGetErrMsg()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//设置回调函数
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
    <%--===================重要的隐藏域内容=========== --%>
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
    <!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
    <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

    <%--权属来源--%>
    <input type="hidden" name="QSLY_JSON" />
    <%--抵押明细--%>
    <input type="hidden" name="DYMX_JSON" />
    
    <input type="hidden" name="ISQCWB" value="${busRecord.ISQCWB}" />



    <%--===================重要的隐藏域内容=========== --%>
    <%--开始引入不动产基本信息--%>
    <jsp:include page="./bdcqlc/bdcJbxx.jsp" />
    <%--开始引入不动产基本信息 --%>

    <%--开始引入公用申报对象--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>

    <%--开始引入受理信息--%>
    <jsp:include page="./bdcqlc/dyqscdj/T_BDCDYSSCDJ_ACCEPTINFO.jsp" />
    <%--结束引入受理信息--%>

    <%--开始引入公用上传材料界面 --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="2" name="isWebsite" />
    </jsp:include>
    <%--结束引入公用上传材料界面 --%>

    <%--开始引入权属来源信息--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyQsly.jsp" />
    <%--结束引入权属来源信息--%>

    <%--开始引入抵押权信息--%>
    <jsp:include page="./bdcqlc/dyqscdj/T_BDCDYSSCDJ_PLEDGEEINFO.jsp" />
    <%--结束引入抵押权信息--%>

    <%--开始引入抵押明细信息--%>
    <jsp:include page="./bdcqlc/dyqscdj/applyDymx.jsp" />
    <%--结束引入抵押明细信息--%>

    <%--开始登簿审核信息--%>
    <jsp:include page="./bdcqlc/dyqscdj/bdcDjshxx.jsp" />
    <%--开始登簿审核信息--%>

    <%--开始银行询问记录基本信息--%>
    <jsp:include page="./bdcqlc/gyjsjfwzydj/yhXwjl.jsp" />
    <%--开始银行询问记录基本信息--%>

    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
        <jsp:param value="dyqscdj" name="domId" />
        <jsp:param value="djjfxx,djfzxx" name="initDomShow" />
    </jsp:include>
    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>

    <jsp:include page="./bdcqlc/bdcRemark.jsp" />

</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
        CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
