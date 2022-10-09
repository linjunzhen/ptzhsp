<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("RecallFlowForm", function(form, valid) {
			var PROJECT_CODE  = $("#PROJECT_CODE").val();
			var EXE_ID  = $("#EXE_ID").val();
			var ITEM_CODE  = $("#ITEM_CODE").val();
			var FILE_NAME  = $("#FILE_NAME").val();
			var FILEATTACH_PATH  = $("#FILEATTACH_PATH").val();
			var ATTACH_TYPE  = $("#ATTACH_TYPE").val();
			var RECALL_OPINION  = document.getElementById("RECALL_OPINION").value;
			if(RECALL_OPINION!=""){
				AppUtil.ajaxProgress({
					   url:"executionController.do?saveRecallInfo",
					   params:{
					      PROJECT_CODE:PROJECT_CODE,
					      EXE_ID:EXE_ID,
					      RECALL_OPINION:RECALL_OPINION,
					      ITEM_CODE:ITEM_CODE,
					      FILE_NAME:FILE_NAME,
					      FILEATTACH_PATH:FILEATTACH_PATH,
					      ATTACH_TYPE:ATTACH_TYPE
					   },
					   callback : function(resultJson) {
					      if(resultJson.success){
					         parent.art.dialog({
					            content : resultJson.msg,
					            icon : "succeed",
					            cancel:false,
					            lock: true,
					            ok: function(){
					               AppUtil.closeLayer();
					            }
					         });
					      } else {
					         parent.art.dialog({
					            content : resultJson.msg,
					            icon : "error",
					            ok: function(){
					               AppUtil.closeLayer();
					            }
					         });
					      }
					   }
					})
			}
		}, "JBPM6_EXECUTION");

		
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
	

/**
* 标题附件上传对话框
*/
function openFileUploadDialog(){
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
					$("input[name='FILEATTACH_PATH']").val(uploadedFileInfo.fileId);	
					$("input[name='FILE_NAME']").val(uploadedFileInfo.fileName);	
					$("input[name='ATTACH_TYPE']").val(attachType);				
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
<body style="margin:0px;background-color: #f7f7f7;">
	<form id="RecallFlowForm" method="post"
		action="executionController.do?saveRecallInfo">
		<div region="center">
			<div id="RecallFlowFormDiv">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE" value="${projectCode}" />
				<input type="hidden" id="EXE_ID" name="EXE_ID" value="${exeid}" />
				<input type="hidden" id="ITEM_CODE" name="ITEM_CODE" value="${itemCode}" />
		     	<input type="hidden" id="FILE_NAME" name="FILE_NAME" value="${FILE_NAME}" />
		    	<input type="hidden" id="FILEATTACH_PATH" name="FILEATTACH_PATH" value="${FILEATTACH_PATH}" />
		     	<input type="hidden" id="ATTACH_TYPE" name="ATTACH_TYPE" value="${ATTACH_TYPE}" />
				<!--==========隐藏域部分结束 ===========-->
				<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
					<tr style="height:29px;">
						<td colspan="1" class="dddl-legend" style="font-weight:bold;">撤回意见</td>
					</tr>
					<tr>
						<td>
							<span style="width: 100px;float:left;text-align:right;">意见内容
								<font class="dddl_platform_html_requiredFlag">*</font>：
							</span>
							<textarea rows="9" cols="5" class="eve-textarea validate[required]" 
								maxlength="600" style="width: 500px" id="RECALL_OPINION" name="RECALL_OPINION"></textarea>
						</td>
					</tr>
                    <tr>
                        <td><span style="width: 120px;float:left;text-align:right;">附件上传：</span>
						<a href="javascript:void(0);" onclick="openFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
						<div id="FILEATTACH_PATH_DIV"></div></td>
                    </tr>
				</table>
			</div>
		</div>
		<div data-options="region:'south'" style="height:50px;"   >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

