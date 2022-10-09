<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script type="text/javascript">
    function openMsbasqFileUploadDialog(val) {
        var fieldName = "";
        if (val == 'zfj') {
            fieldName = 'ZFJ_FILE_ID';
        } else if (val == 'ghj') {
            fieldName = 'GHJ_FILE_ID';
        } else if (val == 'zrzy') {
            fieldName = 'ZRZY_FILE_ID';
        } else if (val == 'sthj') {
            fieldName = 'STHJ_FILE_ID';
        } else {
            fieldName = 'SDPQ_FILE_ID';
        }
        //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片 
        art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BSFW_MSBASQ', {
            title: "上传(附件)",
            width: "650px",
            height: "300px",
            lock: true,
            resize: false,
            close: function(){
                var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                if(uploadedFileInfo){
                    if(uploadedFileInfo.fileId){
                        var fileType = 'gif,jpg,jpeg,bmp,png';
                        var attachType = 'attach';
                        if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                            attachType = "image";
                        }
                        var fileid = $("input[name='" + fieldName + "']").val();
                        if (fileid != "" && fileid != null) {
                            $("input[name='" + fieldName + "']").val(fileid + "," + uploadedFileInfo.fileId);
                        } else {
                            $("input[name='" + fieldName + "']").val(uploadedFileInfo.fileId);
                        }
                        var spanHtml = "<p style='margin-left: 120px;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                            + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                            + "&attachType=" + attachType + "\" ";
                        spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                        spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                        spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "','"+fieldName+"');\" ><font color=\"red\">删除</font></a></p>";
                        $("#" + val + "File").append(spanHtml);

                    }
                }
                art.dialog.removeData("uploadedFileInfo");
            }
        });
    }

    function delUploadFile(fileId,fieldName) {
        art.dialog.confirm("您确定要删除该文件吗?", function() {
            //移除目标span
            $("#"+fileId).remove();
            var fileid=$("input[name='SSDJ_BUY_FILEID']").val();
            var arrayIds=fileid.split(",");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    break;
                }
            }
            $("input[name='" + fieldName + "']").val(arrayIds.join(","));
        });
    }

    /*初始民宿备案申请材料列表*/
    function initMsbasqFileList(busRecord) {
        $.ajaxSettings.async = false;
        $.post("busApplyController.do?msbasqFileList", {
            sdpqFileIds: busRecord.SDPQ_FILE_ID,
            zfjFileIds: busRecord.ZFJ_FILE_ID,
            ghjFileIds: busRecord.GHJ_FILE_ID,
            zrzyFileIds: busRecord.ZRZY_FILE_ID,
            sthjFileIds: busRecord.STHJ_FILE_ID
        }, function (data) {
        	console.log(data);
            if (data) {
                var json = JSON.parse(data);
                var sdpqFileList = json.sdpqFileList;
                var zfjFileList = json.zfjFileList;
                var ghjFileList = json.ghjFileList;
                var zrzyFileList = json.zrzyFileList;
                var sthjFileList = json.sthjFileList;
                if (sdpqFileList) {
                    var str = "";
                    for (let i = 0; i < sdpqFileList.length; i++) {
                        var fileId = sdpqFileList[i].FILE_ID;
                        var fileName = sdpqFileList[i].FILE_NAME;
                        str += "<p id='"+fileId+"'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                        str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                        str += "<a href=\"javascript:void(0);\" id='myBtn' class='msbasqUploadBtn' onclick=\"delUploadFile('" + fileId + "','SDPQ_FILE_ID');\"><font color=\"red\">删除</font></a></p>";
                    }
                    $("#sdpqFile").append(str);
                }
                if (zfjFileList) {
                    var str = "";
                    for (let i = 0; i < zfjFileList.length; i++) {
                        var fileId = zfjFileList[i].FILE_ID;
                        var fileName = zfjFileList[i].FILE_NAME;
                        str += "<p id='"+fileId+"'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                        str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                        str += "<a href=\"javascript:void(0);\" id='myBtn' class='msbasqUploadBtn' onclick=\"delUploadFile('" + fileId + "','ZFJ_FILE_ID');\"><font color=\"red\">删除</font></a></p>";
                    }
                    $("#zfjFile").append(str);
                }
                if (ghjFileList) {
                    var str = "";
                    for (let i = 0; i < ghjFileList.length; i++) {
                        var fileId = ghjFileList[i].FILE_ID;
                        var fileName = ghjFileList[i].FILE_NAME;
                        str += "<p id='"+fileId+"'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                        str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                        str += "<a href=\"javascript:void(0);\" id='myBtn' class='msbasqUploadBtn' onclick=\"delUploadFile('" + fileId + "','GHJ_FILE_ID');\"><font color=\"red\">删除</font></a></p>";
                    }
                    $("#ghjFile").append(str);
                }
                if (sthjFileList) {
                    var str = "";
                    for (let i = 0; i < sthjFileList.length; i++) {
                        var fileId = sthjFileList[i].FILE_ID;
                        var fileName = sthjFileList[i].FILE_NAME;
                        str += "<p id='"+fileId+"'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                        str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                        str += "<a href=\"javascript:void(0);\" id='myBtn' class='msbasqUploadBtn' onclick=\"delUploadFile('" + fileId + "','STHJ_FILE_ID');\"><font color=\"red\">删除</font></a></p>";
                    }
                    $("#sthjFile").append(str);
                }
                if (zrzyFileList) {
                    var str = "";
                    for (let i = 0; i < zrzyFileList.length; i++) {
                        var fileId = zrzyFileList[i].FILE_ID;
                        var fileName = zrzyFileList[i].FILE_NAME;
                        str += "<p id='"+fileId+"'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                        str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                        str += "<a href=\"javascript:void(0);\" id='myBtn' class='msbasqUploadBtn' onclick=\"delUploadFile('" + fileId + "','ZRZY_FILE_ID');\"><font color=\"red\">删除</font></a></p>";
                    }
                    $("#zrzyFile").append(str);
                }
            }
        });
        $.ajaxSettings.async = true;
    }
</script>


<div id="opinion" name="opinion" style="display: none;">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
      <td class="tab_width" >是否有产权：</td>
		<td colspan="3">
			<input type="radio" name="SFYCQ" value="0" <c:if test="${busRecord.SFYCQ=='0'}">checked="checked"</c:if>>否
			<input type="radio" name="SFYCQ" value="1" <c:if test="${busRecord.SFYCQ=='1'}">checked="checked"</c:if>>是
		</td>
            <th>并联审批审核意见</th>
        </tr>
        <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="sdpqOpinion" style="display: none;">
                <tr></tr>
                    <td class="tab_width">四大片区审核意见：</td>
                    <td>
                        <textarea name="SDPQ_OPINION" cols="140" rows="10"
                                  class="validate[]" maxlength="1000">${busRecord.SDPQ_OPINION}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        四大片区附件上传：
                    </td>
                    <td>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openMsbasqFileUploadDialog('sdpq')" class="msbasqUploadBtn">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <span id="sdpqFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
        </tr>
        <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="sthjOpinion" style="display: none">
                <tr></tr>
                <td class="tab_width">自然资源与生态环境局审核意见：</td>
                <td>
                        <textarea name="STHJ_OPINION" cols="140" rows="10"
                                  class="validate[]" maxlength="1000">${busRecord.STHJ_OPINION}</textarea>
                </td>
                </tr>
                <tr>
                    <td>
                        自然资源与生态环境局附件上传：
                    </td>
                    <td>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openMsbasqFileUploadDialog('sthj')" class="msbasqUploadBtn">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <span id="sthjFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
        </tr>
        <%-- <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="ghjOpinion" style="display: none">
                <tr></tr>
                <td class="tab_width">规划局审核意见：</td>
                <td>
                        <textarea name="GHJ_OPINION" cols="140" rows="10"
                                  class="validate[]" maxlength="1000">${busRecord.GHJ_OPINION}</textarea>
                </td>
                </tr>
                <tr>
                    <td>
                        规划局附件上传：
                    </td>
                    <td>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openMsbasqFileUploadDialog('ghj')" class="msbasqUploadBtn">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <span id="ghjFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
        </tr> --%>
        <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="zrzyOpinion" style="display: none">
                <tr></tr>
                <td class="tab_width">自然资源利用处审核意见：</td>
                <td>
                        <textarea name="ZRZY_OPINION" cols="140" rows="10"
                                  class="validate[]" maxlength="1000">${busRecord.ZRZY_OPINION}</textarea>
                </td>
                </tr>
                <tr>
                    <td>
                        自然资源利用处附件上传：
                    </td>
                    <td>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openMsbasqFileUploadDialog('zrzy')" class="msbasqUploadBtn">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <span id="zrzyFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
        </tr>
        <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="zfjOpinion" style="display: none">
                <tr></tr>
                <td class="tab_width">执法局审核意见：</td>
                <td>
                        <textarea name="ZFJ_OPINION" cols="140" rows="10"
                                  class="validate[]" maxlength="1000">${busRecord.ZFJ_OPINION}</textarea>
                </td>
                </tr>
                <tr>
                    <td>
                        执法局附件上传：
                    </td>
                    <td>
                        <div style="margin-top: 4px;">
                            <a href="javascript:void(0);" onclick="openMsbasqFileUploadDialog('zfj')" class="msbasqUploadBtn">
                                <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                            </a>
                            <span id="zfjFile"></span>
                        </div>
                    </td>
                </tr>
            </table>
        </tr>
    </table>
</div>