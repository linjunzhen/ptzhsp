<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,swfupload"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
 <script type="text/javascript" src="<%=path%>/plug-in/jqueryUpload/AppUtilNew.js"></script> 
<script type="text/javascript">
var __ctxPath="<%=request.getContextPath() %>";
	$(function() {
		AppUtil.initWindowForm("sysNoticeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#sysNoticeForm").serialize();
				var url = $("#sysNoticeForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							//parent.reloadNoticeList(resultJson.msg);
							parent.$("#sysNoticeViewGrid").datagrid("reload");
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
			}
		}, "T_MSJW_SYSTEM_NOTICE");
		
		
	
	var fileids=$("#NOTICE_FILE_IDS").val();
		$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    		 if(resultJson!="websessiontimeout"){
	    		 	$("#fileListDiv").html("");
	    		 	var newhtml = "";
	    		 	var list=resultJson.rows;
	    		 	for(var i=0; i<list.length; i++){
	    		 		newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 		newhtml+=list[i].FILE_NAME+'</a>';
	    		 		newhtml+='<a href="javascript:void(0);" onclick="delUploadFile(\''+list[i].FILE_ID+'\',\''+list[i].ATTACH_KEY+'\');"><font color="red">删除</font></a>';
	    		 		newhtml+='</p>';
	    		 	} 
	    		 	$("#fileListDiv").html(newhtml);
	    		 }
         });	
});
function delUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='NOTICE_FILE_URLS']").val();
		var fileid=$("input[name='NOTICE_FILE_IDS']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='NOTICE_FILE_URLS']").val(arrayurls.join(";"));
		$("input[name='NOTICE_FILE_IDS']").val(arrayIds.join(";"));
	});
}
	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
	
/**
* 标题附件上传对话框
*/
function openNoticeFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openMultifileUploadDialog&attachType=attachToImage&materType=image&busTableName=T_MSJW_SYSTEM_NOTICE', {
		title: "上传(附件)",
		width: "820px",
		height: "440px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){				
				for(var i=0;i<uploadedFileInfo.length;i++){					
					if(uploadedFileInfo[i].fileId){
						var fileType = 'gif,jpg,jpeg,bmp,png';
						var attachType = 'attach';
						if(fileType.indexOf(uploadedFileInfo[i].fileSuffix)>-1){
							attachType = "image";
						}								
						var fileurl=$("input[name='NOTICE_FILE_URLS']").val();
						var fileid=$("input[name='NOTICE_FILE_IDS']").val();
						if(fileurl!=""&&fileurl!=null){
							$("input[name='NOTICE_FILE_URLS']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo[i].fileId+'&attachType='+attachType);
							$("input[name='NOTICE_FILE_IDS']").val(fileid+";"+uploadedFileInfo[i].fileId);
						}else{
							$("input[name='NOTICE_FILE_URLS']").val('download/fileDownload?attachId='+uploadedFileInfo[i].fileId+'&attachType='+attachType);
							$("input[name='NOTICE_FILE_IDS']").val(uploadedFileInfo[i].fileId);
						}					
						var spanHtml = "<p id=\""+uploadedFileInfo[i].fileId+"\"><a href=\""+__file_server 
						+ "download/fileDownload?attachId="+uploadedFileInfo[i].fileId
						+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo[i].fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo[i].fileId+"');\" ><font color=\"red\">删除</font></a></p>"
						$("#fileListDiv").append(spanHtml); 
					}
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
</style>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="sysNoticeForm" method="post"
		action="sysNoticeController.do?saveOrUpdate">
		<div region="center" style="min-height:450px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="NOTICE_ID" value="${noticeInfo.NOTICE_ID}">
			<input type="hidden" name="NOTICE_FILE_URLS" id="NOTICE_FILE_URLS" value="${noticeInfo.NOTICE_FILE_URLS}">
			<input type="hidden" name="NOTICE_FILE_IDS" id="NOTICE_FILE_IDS" value="${noticeInfo.NOTICE_FILE_IDS}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
			<!--  <tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			   </tr>  -->	
				<tr style="height:35px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">公告标题：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="100" type="text" name="NOTICE_TITLE" value="${noticeInfo.NOTICE_TITLE}"
						 style="width:500px;"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				
				<tr style="height:35px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">开始日期：</span>
					</td>
					<td style="width: 207px;">
						<input type="text"
                                style="width:187px;float:left; height: 24px; line-height: 24px;"  readonly="ture" 
                                class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_date\')}'})"
                                 id="start_date" name="BEGIN_TIME" value="${noticeInfo.BEGIN_TIME}"  />
                                 <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">结束日期：</span>
					</td>
					<td>
						<input type="text"
                                style="width:197px;float:left; height: 24px; line-height: 24px;" class="validate[required] Wdate"
                                id="end_date" name="END_TIME" value="${noticeInfo.END_TIME}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_date\')}'})"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;height:100px;">公告内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:500px;height:200px;" 
						  name="NOTICE_CONTENT">${noticeInfo.NOTICE_CONTENT}</textarea><font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 100px;float:left;text-align:right;">状态：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择状态" class="eve-input validate[required]" name="STATUS" >
						<option value="">请选择状态</option>						
						<option value="0" <c:if test="${noticeInfo.STATUS==0}">selected="selected"</c:if>>暂存</option>
						<option value="1" <c:if test="${noticeInfo.STATUS==1}">selected="selected"</c:if>>发布</option>
					</select>	
					</td>
				</tr>
				<tr style="height:35px;">
					<td ><span style="width: 100px;float:left;text-align:right;">附件：</span>
			        </td>
			        <td colspan="3">
						
						<a href="javascript:void(0);" onclick="openNoticeFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</td>
				</tr>
				
				<tr style="height:35px;">
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=fileListDiv></div>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

