<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript">
    $(function(){
        var fileids="${busRecord.FILE_ID1 }";
        $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                $("#fileListDiv1").html("");
                var newhtml = "";
                var list=resultJson.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml +="</p>"
                }
                $("#fileListDiv1").html(newhtml);
            }
        });
    });

    function delUploadFile1(fileId,attacheKey){
        AppUtil.delUploadMater(fileId,attacheKey);
        var FILE_ID1=$("input[name='FILE_ID1']").val();
        var arrayIds=FILE_ID1.split(";");
        for(var i=0;i<arrayIds.length;i++){
            if(arrayIds[i]==fileId){
                arrayIds.splice(i,1);
                break;
            }
        }
        $("input[name='FILE_ID1']").val(arrayIds.join(";"));
    }



    $(function(){
        var fileids="${busRecord.FILE_ID2 }";
        $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                $("#fileListDiv2").html("");
                var newhtml = "";
                var list=resultJson.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml+='</p>';
                }
                $("#fileListDiv2").html(newhtml);
            }
        });
    });



    function delUploadFile2(fileId,attacheKey){
        //AppUtil.delUploadMater(fileId,attacheKey);
        art.dialog.confirm("您确定要删除该文件吗?", function() {
            //移除目标span
            $("#"+fileId).remove();
            var FILE_ID2=$("input[name='FILE_ID2']").val();
            var arrayIds=FILE_ID2.split(";");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    break;
                }
            }
            $("input[name='FILE_ID2']").val(arrayIds.join(";"));
        });
    }

	/**
	* 标题附件上传对话框
	*/
	function openPthotoFileUploadDialog(id,name){
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=image&materType=image&busTableName=T_COMMERCIAL_INDIVIDUAL'
		+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
			title: "上传(附件)",
			width: "620px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#"+id).attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
					}else{
						$("#"+id).attr("src",'<%=path%>/webpage/common/images/nopic.jpg');
						$("input[name='"+name+"']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
</script>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
<tr>
	<th colspan="4" >前台用户上传附件</th>
</tr>
<tr>
	<td class="tab_width">前置审批及其他文件：</td>
	<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div><a id="UPDATE_FILE_BTN"></a>
		<div style="width:100%;" id="fileListDiv1"></div></td>
</tr>
<tr>
	<td class="tab_width">冠福建省名或无行政区划名称材料：</td>
	<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV2"></div><a id="UPDATE_FILE_BTN2"></a>
		<div style="width:100%;" id="fileListDiv2"></div></td>
</tr>
<c:if test="${execution.ISNEEDSIGN != 1}">
<tr>
	<td class="tab_width">法定代表人身份证正面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.LEGAL_SFZZM!=null&&busRecord.LEGAL_SFZZM!=''}">
				<img id="LEGAL_SFZZM_IMG" src="${_file_Server}${busRecord.LEGAL_SFZZM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZZM_IMG','LEGAL_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="LEGAL_SFZZM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZZM_IMG','LEGAL_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="LEGAL_SFZZM" value="${busRecord.LEGAL_SFZZM}">
	</td>
	<td class="tab_width">法定代表人身份证反面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.LEGAL_SFZFM!=null&&busRecord.LEGAL_SFZFM!=''}">
				<img id="LEGAL_SFZFM_IMG" src="${_file_Server}${busRecord.LEGAL_SFZFM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZFM_IMG','LEGAL_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="LEGAL_SFZFM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('LEGAL_SFZFM_IMG','LEGAL_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="LEGAL_SFZFM" value="${busRecord.LEGAL_SFZFM}">
	</td>
</tr>
<tr>
	<td class="tab_width">经办人身份证正面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.OPERATOR_SFZZM!=null&&busRecord.OPERATOR_SFZZM!=''}">
				<img id="OPERATOR_SFZZM_IMG" src="${_file_Server}${busRecord.OPERATOR_SFZZM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZZM_IMG','OPERATOR_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="OPERATOR_SFZZM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZZM_IMG','OPERATOR_SFZZM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="OPERATOR_SFZZM"  value="${busRecord.OPERATOR_SFZZM}">
	</td>
	<td class="tab_width">经办人身份证反面：</td>
	<td>
		<c:choose>
			<c:when test="${busRecord.OPERATOR_SFZFM!=null&&busRecord.OPERATOR_SFZFM!=''}">
				<img id="OPERATOR_SFZFM_IMG" src="${_file_Server}${busRecord.OPERATOR_SFZFM}"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZFM_IMG','OPERATOR_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:when>
			<c:otherwise>
				<img id="OPERATOR_SFZFM_IMG" src="webpage/common/images/nopic.jpg"
					height="140px" width="200px">
				<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('OPERATOR_SFZFM_IMG','OPERATOR_SFZFM')" style="float:none;">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</c:otherwise>
		</c:choose>	
		<input type="hidden" class="validate[]" name="OPERATOR_SFZFM"  value="${busRecord.OPERATOR_SFZFM}">
	</td>
</tr>
</c:if>
</table>