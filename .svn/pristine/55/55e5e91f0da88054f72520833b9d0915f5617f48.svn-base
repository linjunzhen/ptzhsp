<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>公章上传</title>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css"
          href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <!--[if lte IE 6]>
    <script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
    </script>
    <![endif]-->
    <eve:resources loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2,swfupload"></eve:resources>
    <script type="text/javascript">
        $(function(){
            AppUtil.initWindowForm("SubmitResultForm", function(form, valid) {
                    var updateId=$("input[name='UPDATE_FILE_ID']").val();
                    if(updateId==null||updateId==""){
                        $.messager.alert('警告',"请上传附件，谢谢！");
                        return ;
                    }
                    var formData = $("#SubmitResultForm").serialize();
                    var url = $("#SubmitResultForm").attr("action");
                    AppUtil.ajaxProgress({
                        url : url,
                        params : formData,
                        callback : function(resultJson) {
                            if (resultJson.success) {
                                parent.art.dialog({
                                    content: "上传成功",
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                AppUtil.closeLayer();
                            } else {
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"error",
                                    ok: true
                                });
                            }
                        }
                    });
            }, "T_CKBS_NUMRECORD");
        });


function downLoadFile(legalIdNo,legalCompanyName) {
    var exeId = '${exeId}';
    var itemCode = '${itemCode}';
    window.location.href=__ctxPath+"/domesticControllerController/downloadLegalSeal.do?exeId="+exeId
        +"&itemCode="+itemCode+"&legalIdNo="+legalIdNo+"&legalCompanyName="+encodeURI(legalCompanyName);
}
</script>
    <script type="text/javascript">
        var __ctxPath="<%=request.getContextPath() %>";
        var previewPhotoStr = "";

        $(function() {
            var rList='${rJson}'
            var list = JSON2.parse(rList);;
            $(list).each(function(i,dic){
                var COMPANYLETAL_ID=dic.COMPANYLETAL_ID;
                AppUtilNew.initUploadControl({
                    file_types: "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;*.doc;*.docx",
                    file_upload_limit: 1,
                    file_queue_limit: 0,
                    uploadPath: "hflow",
                    busTableName: "JBPM6_FLOW_RESULT",
                    uploadUserId: $("input[name='CURLOGIN_USERID']").val(),
                    uploadButtonId: COMPANYLETAL_ID+"UPDATE_FILE_BTN",
                    singleFileDivId: COMPANYLETAL_ID+"UPDATE_FILE_DIV",
                    limit_size: "30 MB",
                    uploadTableId: "AUDID_UPLOADTABLE",
                    uploadSuccess: function (resultJson) {
                        var fileurl = $("input[name='UPDATE_FILE_URL']").val();
                        var fileid = $("input[name='UPDATE_FILE_ID']").val();
                        if (fileurl != "" && fileurl != null) {
                            $("input[name='UPDATE_FILE_URL']").val(fileurl + ";" + resultJson.filePath);
                            $("input[name='UPDATE_FILE_ID']").val(fileid + ";" + resultJson.fileId);
                        } else {
                            $("input[name='UPDATE_FILE_URL']").val(resultJson.filePath);
                            $("input[name='UPDATE_FILE_ID']").val(resultJson.fileId);
                        }
                            $.post("exeDataController/uploadCompanyFile.do",{companyId:COMPANYLETAL_ID,legalFileId:resultJson.fileId},
                                function(responseText, status, xhr) {

                                }
                            );

                        //获取文件ID
                        var fileId = resultJson.fileId;
                        //获取文件名称
                        var fileName = resultJson.fileName;
                        //获取材料KEY
                        var attachKey = resultJson.attachKey;
                        //获取文件路径
                        var filePath = resultJson.filePath;
                        //获取文件的类型
                        var fileType = resultJson.fileType;
                        //获取是否是图片类型
                        var isImg = resultJson.isImg;
                        var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                        spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                        spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
                        spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('" + fileId + "','" + attachKey + "');\" ><font color=\"red\">删除</font></a></p>"

                        $("#"+COMPANYLETAL_ID+"fileListDiv").append(spanHtml);
                    }
                });
            });
        });

        function delUploadFile(fileId,attacheKey){
            AppUtil.delUploadMater(fileId,attacheKey);
            var fileurl=$("input[name='RESULT_FILE_URL']").val();
            var fileid=$("input[name='RESULT_FILE_ID']").val();
            var arrayIds=fileid.split(";");
            var arrayurls=fileurl.split(";");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    arrayurls.splice(i,1);
                    break;
                }
            }
            $("input[name='RESULT_FILE_URL']").val(arrayurls.join(";"));
            $("input[name='RESULT_FILE_ID']").val(arrayIds.join(";"));
        }
        function delUploadFile1(fileId,attacheKey){
            AppUtil.delUploadMater(fileId,attacheKey);
            var fileurl=$("input[name='UPDATE_FILE_URL']").val();
            var fileid=$("input[name='UPDATE_FILE_ID']").val();
            var arrayIds=fileid.split(";");
            var arrayurls=fileurl.split(";");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    arrayurls.splice(i,1);
                    break;
                }
            }
            $("input[name='UPDATE_FILE_URL']").val(arrayurls.join(";"));
            $("input[name='UPDATE_FILE_ID']").val(arrayIds.join(";"));
        }

    </script>
    <style>
        .eve-select-width{
            width:400px !important;
        }
        .sel-width{
            width:170px !important;
        }
        body{
            font-size:12px;
        }
    </style>
</head>

<body style="background: none; min-width:800px;">
<form id="SubmitResultForm" method="post"
      action="exeDataController/goSignNextStep.do">
<input type="hidden" name="exeId"  value="${exeId}">
<input type="hidden" name="UPDATE_FILE_ID" >

<table cellpadding="0" cellspacing="0" class="zxtable2">
    <tr>
        <th colspan="6"><font style="font-size: 18px;color: #0b6fa2"> 闽政通认证后，下载对应的公司盖章模板，盖上公司公章，然后上传</font></th>
    </tr>
    <tr>
        <th width="10%">序号</th>
        <th width="20%">公司名</th>
        <th width="10%">法定代表人</th>
        <th width="20%">法定代表人身份证号</th>
        <th width="20%">公司盖章模板下载</th>
        <th width="20%">上传公司公章</th>
    </tr>
    <c:forEach items="${rList}" var="rMap" varStatus="rStatus">
        <tr>
            <td>${rStatus.index+1}</td>
            <td>
               ${rMap.LEGAL_COMPANYNAME}
            </td>
            <td>
                    ${rMap.LEGAL_PERSON}
            </td>
            <td>
                    ${rMap.LEGAL_IDNO_PERSON}
            </td>
            <td>
                <a class="btn1" href="javascript:void(0);"
                   onclick="downLoadFile('${rMap.LEGAL_IDNO_PERSON}','${rMap.LEGAL_COMPANYNAME}');"
                >下载</a>
            </td>
            <td >
                <div style="width:100%;display: none;" id="${rMap.COMPANYLETAL_ID}UPDATE_FILE_DIV"></div><a id="${rMap.COMPANYLETAL_ID}UPDATE_FILE_BTN"></a>
                <div style="width:100%;" id="${rMap.COMPANYLETAL_ID}fileListDiv"></div>
            </td>
        </tr>
    </c:forEach>
</table>
    <div data-options="region:'south'" style="height:50px;" >
        <div class="eve_buttons">
            <input value="提交公章" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>
</body>

</html>
