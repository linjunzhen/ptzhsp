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
                        newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                        newhtml+='</p>';
                    }
                    $("#fileListDiv1").html(newhtml);
                }
            });
		});
		
		$(function(){
            var fileids="${busRecord.FILE_ID2}";
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
                        newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile2('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                        newhtml+='</p>';
                    }
                    $("#fileListDiv2").html(newhtml);
                }
            });
		});
		
		$(function(){
            var fileids="${busRecord.FILE_ID3}";
            $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
                if(resultJson!="websessiontimeout"){
                    $("#fileListDiv3").html("");
                    var newhtml = "";
                    var list=resultJson.rows;
                    for(var i=0; i<list.length; i++){
                        newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                        newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                        newhtml+=list[i].FILE_NAME+'</a>';
                        newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                        newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                        newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile3('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                        newhtml+='</p>';
                    }
                    $("#fileListDiv3").html(newhtml);
                }
            });
		});

      

		function delUploadFile1(fileId,attacheKey){
			//AppUtil.delUploadMater(fileId,attacheKey);
			art.dialog.confirm("您确定要删除该文件吗?", function() {
				//移除目标span
				$("#"+fileId).remove();
				var FILE_ID1=$("input[name='FILE_ID1']").val();
				var arrayIds=FILE_ID1.split(";");
				for(var i=0;i<arrayIds.length;i++){
					if(arrayIds[i]==fileId){
						arrayIds.splice(i,1); 
						break;
					}
				}
				$("input[name='FILE_ID1']").val(arrayIds.join(";"));
			});
		}
		
		
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
		
		function delUploadFile3(fileId,attacheKey){
		//AppUtil.delUploadMater(fileId,attacheKey);
		art.dialog.confirm("您确定要删除该文件吗?", function() {
				//移除目标span
				$("#"+fileId).remove();
				var FILE_ID3=$("input[name='FILE_ID3']").val();
				var arrayIds=FILE_ID3.split(";");
				for(var i=0;i<arrayIds.length;i++){
					if(arrayIds[i]==fileId){
						arrayIds.splice(i,1); 
						break;
					}
				}
				$("input[name='FILE_ID3']").val(arrayIds.join(";"));
			});
		}
				
		/**
		* 标题附件上传对话框
		*/
		function openResultFileUploadDialog(){
			//定义上传的人员的ID
			var uploadUserId = $("input[name='uploadUserId']").val();
			//定义上传的人员的NAME
			var uploadUserName = $("input[name='uploadUserName']").val();
			//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
			art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_COMPANY&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
				title: "上传(附件)",
				width: "620px",
				height: "300px",
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
							var fileid=$("input[name='FILE_ID1']").val();
							if(fileid!=""&&fileid!=null){
								$("input[name='FILE_ID1']").val(fileid+";"+uploadedFileInfo.fileId);
							}else{
								$("input[name='FILE_ID1']").val(uploadedFileInfo.fileId);
							}
							var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
							spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
							spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
							spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
							$("#fileListDiv1").append(spanHtml);
						}
					}
					art.dialog.removeData("uploadedFileInfo");
				}
			});
		}
		
				
		/**
		* 标题附件上传对话框
		*/
		function openResultFileUploadDialog1(){
			//定义上传的人员的ID
			var uploadUserId = $("input[name='uploadUserId']").val();
			//定义上传的人员的NAME
			var uploadUserName = $("input[name='uploadUserName']").val();
			//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
			art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_COMPANY&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
				title: "上传(附件)",
				width: "620px",
				height: "300px",
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
							var fileid=$("input[name='FILE_ID2']").val();
							if(fileid!=""&&fileid!=null){
								$("input[name='FILE_ID2']").val(fileid+";"+uploadedFileInfo.fileId);
							}else{
								$("input[name='FILE_ID2']").val(uploadedFileInfo.fileId);
							}
							var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
							spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
							spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
							spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile2('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
							$("#fileListDiv2").append(spanHtml);
						}
					}
					art.dialog.removeData("uploadedFileInfo");
				}
			});
		}
		
				/**
		* 标题附件上传对话框
		*/
		function openResultFileUploadDialog2(){
			//定义上传的人员的ID
			var uploadUserId = $("input[name='uploadUserId']").val();
			//定义上传的人员的NAME
			var uploadUserName = $("input[name='uploadUserName']").val();
			//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
			art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_COMPANY&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
				title: "上传(附件)",
				width: "620px",
				height: "300px",
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
							var fileid=$("input[name='FILE_ID3']").val();
							if(fileid!=""&&fileid!=null){
								$("input[name='FILE_ID3']").val(fileid+";"+uploadedFileInfo.fileId);
							}else{
								$("input[name='FILE_ID3']").val(uploadedFileInfo.fileId);
							}
							var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
							spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
							spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
							spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile3('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
							$("#fileListDiv3").append(spanHtml);
						}
					}
					art.dialog.removeData("uploadedFileInfo");
				}
			});
		}
	</script>
<form action="" id="FILE_FORM"  >
		<input  name="FILE_ID1"  type="hidden"
		<c:if test="${requestParams.ISFIRSTAUDIT==1||busRecord.ISFIRSTAUDIT==1}">	class="validate[required]"</c:if>
			   value="${busRecord.FILE_ID1 }">
		<input  name="FILE_ID2"  type="hidden" value="${busRecord.FILE_ID2 }">
		<input  name="FILE_ID3"  type="hidden" value="${busRecord.FILE_ID3 }">
	<div class="syj-title1 tmargin20">
		<span>前置审批及其他文件
		</span>
	</div>	
	<div style="position:relative;">		
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><c:if test="${requestParams.ISFIRSTAUDIT==1||busRecord.ISFIRSTAUDIT==1}"><font class="syj-color2">*</font></c:if>附件：</th>
				<td colspan="3">
					<div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div>
						<a href="javascript:void(0);" onclick="openResultFileUploadDialog()">
							<img id="UPDATE_FILE_BTN" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
					<div style="width:100%;" id="fileListDiv1"></div>
				</td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>冠福建省名或无行政区规划名称材料
		</span>
	</div>	
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th>附件：</th>
				<td colspan="3">
					<div style="width:100%;display: none;" id="UPDATE_FILE_DIV1"></div>
						<a href="javascript:void(0);" onclick="openResultFileUploadDialog1()">
							<img id="UPDATE_FILE_BTN" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
					<div style="width:100%;" id="fileListDiv2"></div>
				</td>
			</tr>	
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>外省企业法人股东
		</span>
	</div>	
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th>附件：</th>
				<td colspan="3">
					<div style="width:100%;display: none;" id="UPDATE_FILE_DIV2"></div>
						<a href="javascript:void(0);" onclick="openResultFileUploadDialog2()">
							<img id="UPDATE_FILE_BTN" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
					<div style="width:100%;" id="fileListDiv3"></div>
				</td>
			</tr>	
		</table>
	</div>
</form>
