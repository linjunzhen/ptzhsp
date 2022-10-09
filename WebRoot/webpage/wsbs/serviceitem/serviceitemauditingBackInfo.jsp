<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("BackForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#BackForm").serialize();
			var url = $("#BackForm").attr("action");
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if(resultJson.success){
						art.dialog.data("backinfo", {
					            back:"1"
					    });
						AppUtil.closeLayer();
					}else{
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				}
			});
		}
	},"T_WSBS_SERVICEITEM",null, $("input[name='ITEM_IDS']").val());
	
	/* AppUtil.initUploadControl({
		file_types : "*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.RAR;*.pdf;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "applymater",
		busTableName : "T_WSBS_SERVICEITEM",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "FILEATTACH_PATH_BTN",
		singleFileDivId : "FILEATTACH_PATH_DIV",
		singleFileId : $("input[name='FILEATTACH_PATH']").val(),
		singleFileFieldName : "FILEATTACH_PATH",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			$("input[name='FILEATTACH_PATH']").val(resultJson.fileId);
			$("input[name='FILE_NAME']").val(resultJson.fileName);
		}

	}); */

	
	var FILEATTACH_PATH= $("input[name='FILEATTACH_PATH']").val();
	$.post("executionController.do?getResultFiles&fileIds="+FILEATTACH_PATH,{}, function(resultJson) {
		 if(resultJson!="websessiontimeout"){
			$("#FILEATTACH_PATH_DIV").html("");
			var newhtml = "";
			var list=resultJson.rows;
			for(var i=0; i<list.length; i++){
				newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
				newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
				newhtml+=list[i].FILE_NAME+'</a>';
				newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\'FILEATTACH_PATH\');"><font color="red">删除</font></a>';
				newhtml+='</p>';
			} 
			$("#FILEATTACH_PATH_DIV").html(newhtml);
		 }
	});	
});

function send(){
	var val=$("#isSendChang").get(0).checked;
	//alert($("#isSendMessage").val());
	if(val){
		$("#isSend").val(1); 
	}else{
		$("#isSend").val(0); 
	}
}
/**
* 标题附件上传对话框
*/
function openFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_WSBS_SERVICEITEM', {
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
					$("input[name='FILEATTACH_PATH']").val(uploadedFileInfo.fileId);		
					$("input[name='FILE_NAME']").val(uploadedFileInfo.fileName);							
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','FILEATTACH_PATH');\" ><font color=\"red\">删除</font></a></p>"
					$("#FILEATTACH_PATH_DIV").html(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
function delUploadFile(fileId,id){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		$("input[name='"+id+"']").val("");
	});
}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BackForm" method="post" action="serviceItemController.do?updateTHYJ">
	    <div  id="BackFormDiv">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="ITEM_IDS" id="ITEM_ID" value="${itemIds}"> 
		    <c:if test="${fileFlag==2||fileFlag==5||fileFlag==8 }">
		     <input type="hidden" name="FILE_NAME" value="${FILE_NAME}">
		    <input type="hidden" name="FILEATTACH_PATH" value="${FILEATTACH_PATH}">
		    </c:if>
		  
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
		    <tr><td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>审核信息</a></div></td></tr>
		    <c:if test="${state!=null}">
		    <tr style="height:50px;"><td><span style="width: 100px;float:left;text-align:right;">审核内容：</span>
		          <c:if test="${state==2}">
		                                      审核拟发布
		          </c:if>
		          <c:if test="${state==5}">
                                                                 审核取消
                  </c:if>
                  <c:if test="${state==8}">
                                                                 审核发布
                  </c:if>
		    </td></tr>
		    </c:if>
<tr style="height:50px;"><td><span style="width: 100px;float:left;text-align:right;">是否同意：</span>
同意<input type="radio" value="1" name="SFTY" checked="checked">不同意<input type="radio" value="2" name="SFTY"><font
                                class="dddl_platform_html_requiredFlag">*</font>      
                                <span style="width: 100px;margin-left:100px">是否短信通知：</span>
<input type="checkbox" id="isSendChang" name="isSendChang" onclick="send();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="isSend" name="isSend" value="0"/>             </td></tr>
<tr style="height:50px;"><td><span style="width: 100px;float:left;text-align:right;">审核意见：</span>
<c:if test="${fileFlag==2||fileFlag==5||fileFlag==8 }">
<textarea rows="7" cols="5" class="eve-textarea validate[required]"  style="width: 450px" name="THYJ" maxlength="500"></textarea>
</c:if>


<c:if test="${fileFlag=='' }">
<textarea rows="7" cols="5" class="eve-textarea validate[required]"  style="width: 450px" name="THYJ" maxlength="500"></textarea>

</c:if>
<font class="dddl_platform_html_requiredFlag">*</font>  </td></tr>
                                <tr><td>
                                <span style="width: 300px;color:red;margin-left:100px;">备注：请填写审核依据</span>
                                </td>
                                
                                </tr>
                                <tr>
                                <c:if test="${fileFlag==2||fileFlag==5||fileFlag==8}">
                                <td><span style="width: 120px;float:left;text-align:right;">附件上传：</span>
						<a href="javascript:void(0);" onclick="openFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="FILEATTACH_PATH_DIV"></div></td>
						</c:if>
                                </tr>
</table>

</div>

		<div class="eve_buttons">
		    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
