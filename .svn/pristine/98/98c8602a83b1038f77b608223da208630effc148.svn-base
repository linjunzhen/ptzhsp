<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate =true;

            var flowSubmitObj = FlowUtil.getFlowObj();
            if (flowSubmitObj) {
                if (flowSubmitObj.busRecord) {
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_LYYDSP_FORM");
                    //获取表单字段权限控制信息
                    var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                    //初始化表单字段权限控制
                    FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_LYYDSP_FORM");
                    /*特殊项目信息判定*/

                    /*经办人地址必填*/
                    $("input[name='JBR_ADDRESS']").attr("class", "tab_text validate[required]");
                    $("input[name='JBR_ADDRESS']").parent().prev().html("<font class=\"tab_color\">*</font>联系地址：")


                    /*获取省林业局回传材料 flowSubmitObj.EFLOW_EXEID*/
                    $.post("projectItemController/getSlyjMaterList.do",{exeId: flowSubmitObj.EFLOW_EXEID},function (data) {
                        if (data) {
                            var json = JSON.parse(data);
                            var str = "";
                            for (let i = 0; i < json.length; i++) {
                                var fileName = json[i].FILE_NAME;
                                var fileId = json[i].FILE_ID;
                                var filePath = json[i].FILE_PATH;
                                str += "<tr><td colspan='2' style='text-align: center;color:blue;' >"+fileName+"</td><td colspan='2' style='text-align: center;cursor: pointer;color:blue;' onclick=\"downloadMater('"+fileId+"');\">下载</td></tr>";
                            }
                            $("#materList").append(str);
                        }
                    })

                    /*获取省补件信息*/
                    $.post("projectItemController/getBjInfoList.do",{exeId: flowSubmitObj.EFLOW_EXEID},function (data) {
                        if (data) {
                            var json = JSON.parse(data);
                            var str = "";
                            for (let i = 0; i < json.length; i++) {
                                var code = json[i].BZ_CODE;
                                var name = json[i].BZ_NAME;
                                var content = json[i].BZ_CONTENT;
                                var count = json[i].BZ_COUNT;
                                var ywId = json[i].YW_ID;
                                var isBz = json[i].IS_BZ;
                                var filename = json[i].BZ_FILENAME
                                var fileid = json[i].BZ_FILEID
                                str += "<tr><td style='color:blue;'>"+code+"</td><td style='color:blue;'>"+name+"</td><td style='color:blue;'>"+content+"</td><td style='color:blue;'>"+count+"</td>";
                                str += "<td>";
                                str += "<a href=\"javascript:void(0);\" id='" + ywId + "' onclick=\"openFileUploadDialog(this)\">";
                                str += "<img src=\"webpage/bsdt/applyform/images/tab_btn_sc.png\"  style=\"width:60px;height:22px;\"/>";
                                str += "</a></td>";
                                if (filename) {
                                    str += "<td style='color:blue;'><span style='cursor: pointer' onclick=\"downloadMater('"+fileid+"');\">" + filename + "</span></td>";
                                } else {
                                    str += "<td style='color:blue;'></td>";
                                }
                                str += "</tr>";
                            }
                            $("#bjInfo").append(str);
                        }
                    })

                    /*获取省返回信息*/
                    $.post("projectItemController/getLyReturnData.do", {exeId: flowSubmitObj.EFLOW_EXEID}, function (data) {
                        if (data) {
                            var json = JSON.parse(data);
                            var str = "";
                            for (let i = 0; i < json.length; i++) {
                                var EXE_ID = json[i].EXE_ID;
                                var STATUS = json[i].STATUS;
                                var MESSAGE = json[i].MESSAGE;
                                var CREATE_TIME = json[i].CREATE_TIME;
                                str += "<tr><td style='color:blue;'>"+EXE_ID+"</td><td style='color:blue;'>";
                                if (STATUS == '0') {
                                    str += "发送失败";
                                } else {
                                    str += "发送成功";
                                }
                                str += "</td><td style='color:blue;'>" + MESSAGE + "</td><td style='color:blue;'>" + CREATE_TIME;
                                str += "</td></tr>";
                            }
                            $("#returnMsg").append(str);
                        }

                    });

                }
            }
        })

        /*上传补正base64code*/
        function getBase64Str(z,x) {
            var file = document.getElementById("file_"+z).files[0];
            r = new FileReader();
            r.onload = function () {
                var base64Code = r.result.substring(r.result.indexOf("base64,") + 7)
                $.post("projectItemController/saveBzBase64Code.do",{base64Code:base64Code,id:x,filename:file.name},function (data) {
                    if (data) {
                        var json = JSON.parse(data)
                        if (json.success) {
                            art.dialog({
                                content: "材料上传成功",
                                icon: "succeed",
                                ok: true
                            });
                        } else {
                            art.dialog({
                                content: "材料上传失败，请联系管理员",
                                icon: "error",
                                ok: true
                            });
                        }
                    }
                })
            }
            r.readAsDataURL(file);    //Base64
        }

        /**
         * 标题附件上传对话框
         */
        function openFileUploadDialog(e){
            //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
            art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_WSBS_PROJECT_RECALL', {
                title: "上传(附件)",
                width: "620px",
                height: "240px",
                lock: true,
                resize: false,
                close: function(){
                    var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                    if(uploadedFileInfo){
                        if(uploadedFileInfo.fileId){
                            var fileType = 'gif,jpg,jpeg,bmp,png';
                            var attachType = 'attach';
                            if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
                                attachType = "image";
                            }
                            var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
                                + "download/fileDownload?attachId="+uploadedFileInfo.fileId
                                +"&attachType="+attachType+"\" ";
                            spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                            spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','FILEATTACH_PATH');\" ><font color=\"red\">删除</font></a></p>"
                            $(e).parent().next().html(spanHtml);
                            $.post("projectItemController/saveBzFile.do",
                                {
                                    fileName:uploadedFileInfo.fileName,
                                    fileId:uploadedFileInfo.fileId,
                                    filePath:uploadedFileInfo.filePath,
                                    id:$(e)[0].id
                                },
                                function (data) {
                                    if (data) {
                                        var json = JSON.parse(data)
                                        if (json.success) {
                                            art.dialog({
                                                content: "材料上传成功",
                                                icon: "succeed",
                                                ok: true
                                            });
                                        } else {
                                            art.dialog({
                                                content: "材料上传失败，请联系管理员",
                                                icon: "error",
                                                ok: true
                                            });
                                        }
                                    }
                                }
                            );
                        }
                    }
                    art.dialog.removeData("uploadedFileInfo");
                }
            });
        }

        /*发送补正信息给省林业局*/
        function sendBzInfo() {
            var flowSubmitObj = FlowUtil.getFlowObj();
            $.post("projectItemController/lyydspSendMsgToSwb.do",{isBz:"1",exeId:flowSubmitObj.EFLOW_EXEID},function (data) {
                if (data) {
                    var json = JSON.parse(data)
                    if (json.success) {
                        art.dialog({
                            content: json.msg,
                            icon: "succeed",
                            ok: true
                        });
                    } else {
                        art.dialog({
                            content: json.msg,
                            icon: "error",
                            ok: true
                        });
                    }
                }
            })
        }

        /*下载文件*/
        function downloadMater(id) {
            window.location.href = __ctxPath + "/DownLoadServlet?fileId=" + id;
        }


        /*提交*/
        function FLOW_SubmitFun(flowSubmitObj) {
            //先判断表单是否验证通过
            var validateResult = $("#T_BSFW_LYYDSP_FORM").validationEngine(
                "validate");
            if (validateResult) {
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
                if (submitMaterFileJson || submitMaterFileJson == "") {
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
                        submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil.getFormEleData("T_BSFW_LYYDSP_FORM");
                    for ( var index in formData) {
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    /*开始随机抽查判定*/
                    return flowSubmitObj;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        /*暂存*/
        function FLOW_TempSaveFun(flowSubmitObj) {
            //先判断表单是否验证通过
            var validateResult = $("#T_BSFW_LYYDSP_FORM").validationEngine(
                "validate");
            if (validateResult) {
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
                if (submitMaterFileJson || submitMaterFileJson == "") {
                    valiSpecialProject();
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
                        submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil
                        .getFormEleData("T_BSFW_LYYDSP_FORM");
                    for ( var index in formData) {
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    return flowSubmitObj;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        /*退回*/
        function FLOW_BackFun(flowSubmitObj) {
            var formData = FlowUtil
                .getFormEleData("T_BSFW_LYYDSP_FORM");
            for ( var index in formData) {
                flowSubmitObj[eval("index")] = formData[index];
            }
            console.log(flowSubmitObj)
            return flowSubmitObj;
        }


        function FLOW_ViewSumOpinionFun(flowSubmitObj){
            return flowSubmitObj;
        }


    </script>
</head>

<body>
<div class="detail_title">
    <h1>
        ${serviceItem.ITEM_NAME}
    </h1>
</div>
<form id="T_BSFW_LYYDSP_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <%--===================重要的隐藏域内容=========== --%>

        <div id="base64Mark"></div>

    <%--申报信息--%>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="4">项目信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 全省统一项目编码：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="PROJECT_CODE"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 项目地址：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="PROJECT_ADDRESS" maxlength="128">
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 用地申请单位：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="LAND_APPLICATION_UNIT" maxlength="128"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 用地类型：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="LAND_TYPE" maxlength="32">
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 用地总面积：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="32" name="USE_LAND_AREA"/><span>公顷</span>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 林地总面积：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="32" name="FOREST_LAND_AREA"><span>公顷</span>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 城镇或乡村规划：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="FHBFH"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择城镇或乡村规划" name="TOWN_VILLAGE_PLANNING">
                </eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 涉密项目：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="FHBFH"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择涉密项目" name="SECRET_PROJECT">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 投资项目：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="FHBFH"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择投资项目" name="INVEST_PROJECT">
                </eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 项目类型类别：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="XMLXLB"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择项目类型类别" name="PROJECT_TYPE">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 公开方式：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="GKFS"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择公开方式" name="OPEN_WAY">
                </eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 申请来源：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="SQLY1"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择申请来源" name="APPLY_FROM">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 重点项目级别：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="ZDXMJB"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择重点项目级别" name="PROJECT_RANK">
                </eve:eveselect>
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="normalProject">
        <tr>
            <th colspan="4">法人信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 法人姓名：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="16" name="UNIT_LEAD_NAME"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 法人证件类型：</td>
            <td colspan="1">
                <eve:eveselect clazz="eve-input sel validate[required]" onchange="setValidate(this.value);"
                               dataParams="DocumentType"
                               dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
                               defaultEmptyText="====选择证件类型====" name="UNIT_LEAD_TYPE"></eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 法人证件号码：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="18" name="UNIT_LEAD_CARDNO"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 法人手机号码：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" name="UNIT_LEAD_PHONE"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 法人性别：</td>
            <td colspan="1">
                <eve:eveselect clazz="eve-input sel validate[required]"
                               dataParams="sex"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="====选择性别====" name="UNIT_LEAD_SEX"></eve:eveselect>
            </td>
            <td class="tab_width"> 法人出生日期：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="UNIT_LEAD_BIRTHDAY"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 家庭住址或机构地址：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="UNIT_LEAD_ADDR" maxlength="128"/>
            </td>
            <td class="tab_width"> 邮政编码：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[[],custom[chinaZip]]" name="UNIT_LEAD_POSTCODE" />
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 邮箱：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[[],custom[email]]" name="UNIT_LEAD_EMAIL"/>
            </td>
            <td class="tab_width"> 办公电话：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="UNIT_LEAD_TEL"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 统一社会信用代码：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="18" name="UNIT_JG_CODE"/>
            </td>
            <td></td>
            <td></td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
        <tr>
            <th colspan="4">项目批准文件</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 项目名称：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="PROJECT_NAME" maxlength="128">
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 批准机关：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="APPROVAL_AUTHORITY" maxlength="128">
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 批准文件名称：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="128" name="APPROVAL_FILE_NAME"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 批准文号：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="64" name="APPROVAL_FILE_NUM"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 批准文件类型：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="PZWJLX"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择批准文件类型" name="APPROVAL_FILE_TYPE">
                </eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 批准等级：</td>
            <td colspan="1">
                <eve:eveselect clazz="tab_text validate[required]" dataParams="PZDJ"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择批准等级" name="APPROVAL_FILE_RANK">
                </eve:eveselect>
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
        <tr>
            <th colspan="4">资质单位</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 单位名称：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="128" name="INTELLIGENCE_NAME"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 资质等级：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="32" name="INTELLIGENCE_RANK"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 单位法人：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="16" name="INTELLIGENCE_LEGAL_NAME"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font> 法人身份证：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="18" name="INTELLIGENCE_LEGAL_CARDNO"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">林地可研报告中是否有对项目的建设背景、拟占林地的情况调查、影响程度、综合评价及制定的保障措施等因素进行综合分析，并认为该项目使用林地方案可行。</td>
            <td class="tab_width"><font class="tab_color">*</font> 页码：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="8" name="INTELLIGENCE_PAGENUM"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 批准等级：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" name="INTELLIGENCE_APPROVAL_RANK" maxlength="32"/>
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="specialProject">
        <tr>
            <th colspan="4">缴款单位信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 开户行：</td>
            <td colspan="3">
                <input type="text" class="tab_text validate[required]" maxlength="64" name="PAY_UNIT_BANK"/>
                <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 户名：</td>
            <td colspan="3">
                <input type="text" class="tab_text validate[required]" maxlength="64" name="PAY_UNIT_BANKNAME"/>
                <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 账号：</td>
            <td colspan="1">
                <input type="text" class="tab_text validate[required]" maxlength="32" name="PAY_UNIT_BANKCOUNT"/>
                <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
            </td>
        </tr>
        <tr>
            <td colspan="4"><span style="color: red; font-weight: 700;">为规范免征植被费，如需免征植被费，请联系省局审批处</span></td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="specialProject">
        <tr>
            <th colspan="4">红线图验证</th>
        </tr>
        <tr>
            <th colspan="4"><input name="upimage" id="upload_file" type="file"><textarea id="base64_output" style="display: none;" ></textarea></th>
        </tr>
    </table>


    <%--开始引入公用上传材料界面 --%>
        <jsp:include page="./applyMaterList.jsp">
            <jsp:param value="2" name="isWebsite" />
        </jsp:include>


    <%--省林业局回传信息--%>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="returnMsg">
        <tr>
            <th colspan="4">省林业局回传信息</th>
        </tr>
        <tr>
            <td>申报号</td>
            <td>发送状态</td>
            <td>返回信息</td>
            <td>返回时间</td>
        </tr>
    </table>
    <%--省林业局回传材料--%>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="materList">
        <tr>
            <th colspan="4">省林业局回传材料</th>
        </tr>
        <tr>
            <td colspan="3">材料名称</td>
            <td>操作</td>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bjInfo">
        <tr>
            <th colspan="6">补件信息列表</th>
        </tr>
        <tr>
            <td>材料编码</td>
            <td>材料名称</td>
            <td>补件说明</td>
            <td>补件数量</td>
            <td><input type="button" class="eflowbutton" value="补正" onclick="sendBzInfo();"></td>
            <td>文件名</td>
        </tr>
    </table>
    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>

</form>
<script type="text/javascript">
    document.getElementById("upload_file").onchange = function () {
        var file = $_('upload_file').files[0];
        r = new FileReader();  //本地预览
        r.onload = function(){
            var result = r.result;
            if (result.indexOf("data:application/zip;base64,") != -1) {
                result = result.replace("data:application/zip;base64,","");
            } else if (result.indexOf("data:;base64,") != -1) {
                result = result.replace("data:;base64,", "");
            }else if(result.indexOf("data:application/x-zip-compressed;base64,") != -1){
                result = result.replace("data:application/x-zip-compressed;base64,", "");
            }
            $_('base64_output').value = result;
            $.post("projectItemController/checkRedLinePicture.do",{base64Code:r.result.replace("data:application/zip;base64,","")},function (data) {
                if (data) {
                    var json = JSON.parse(data)
                    art.dialog({
                        content: json.msg,
                        icon:"warning",
                        time:3,
                        ok: true
                    });
                }
            })
        }
        r.readAsDataURL(file);    //Base64
    };
    function $_(id) {
        return document.getElementById(id);
    }
</script>
</body>
</html>
