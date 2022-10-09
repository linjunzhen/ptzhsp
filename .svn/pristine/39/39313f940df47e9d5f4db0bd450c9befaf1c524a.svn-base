<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
 
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  

<head>
<eve:resources
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript">
	$(function(){
		AppUtil.initWindowForm("restartFlowTaskExplainForm", function(form, valid) {
			   if (valid) {
			    //将提交按钮禁用,防止重复提交
			    $("input[type='submit']").attr("disabled", "disabled");
			    var formData = $("#restartFlowTaskExplainForm").serialize();
			    var url = $("#restartFlowTaskExplainForm").attr("action");
			    
	    		var resulturls=$("input[name='EXPLAIN_FILE_URL']").val();
	    		if(resulturls==null||resulturls==""){
	    			$.messager.alert('警告',"附件不能为空，请上传附件，谢谢！");
	    			$("input[type='submit']").attr("disabled", false);
					return ; 
	    		}
			    AppUtil.ajaxProgress({
			     url : url,
			     params : formData,
			     callback : function(resultJson) {
			      if (resultJson.success) {
			       parent.art.dialog({
			        content: "启动成功",
			        icon:"succeed",
			        time:3,
			           ok: true
			       });
				   parent.$("#NeedMeHandleGrid").datagrid("reload");
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
			  }, "T_CKBS_NUMRECORD");
	});
	$(function() {
		/* AppUtilNew.initUploadControl({
			file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;*.doc;*.docx;*.xls;*.xlsx;",
			file_upload_limit : 0,
			file_queue_limit : 0,
			uploadPath : "hflow",
			busTableName : "JBPM6_HANGINFO",
			uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
			busTableRecordId : "JBPM6_HANGINFO",
			uploadButtonId : "EXPLAIN_FILE_BTN",
			singleFileDivId : "EXPLAIN_FILE_DIV",
			limit_size : "10 MB",
			uploadTableId:"AUDID_UPLOADTABLE",
			uploadSuccess : function(resultJson) {
				var fileurl=$("input[name='EXPLAIN_FILE_URL']").val();
					var fileid=$("input[name='EXPLAIN_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='EXPLAIN_FILE_URL']").val(fileurl+";"+resultJson.filePath);
						$("input[name='EXPLAIN_FILE_ID']").val(fileid+";"+resultJson.fileId);
					}else{
						$("input[name='EXPLAIN_FILE_URL']").val(resultJson.filePath);
						$("input[name='EXPLAIN_FILE_ID']").val(resultJson.fileId);
					}
					
					//alert($("#EXPLAIN_FILE_DIV").children("a").eq(1).html());
					//$("#EXPLAIN_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
					//获取文件ID
			        var fileId = resultJson.fileId;
		            //获取文件名称
		            var fileName = resultJson.fileName;
		            //获取材料KEY
		            var attachKey =resultJson.attachKey;
		            //获取文件路径
		            var filePath = resultJson.filePath;
		            //获取文件的类型
		            var fileType = resultJson.fileType;
		            //获取是否是图片类型
		            var isImg = resultJson.isImg;
					var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
		            spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
		            spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
		            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
		            $("#fileListDiv").append(spanHtml); 
			}
		});  */
	});
		
	/**
	* 标题附件上传对话框
	*/
	function openRestartFileUploadDialog(){
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=JBPM6_FLOW_RESULT', {
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
						var fileurl=$("input[name='EXPLAIN_FILE_URL']").val();
						var fileid=$("input[name='EXPLAIN_FILE_ID']").val();
						if(fileurl!=""&&fileurl!=null){
							$("input[name='EXPLAIN_FILE_URL']").val(fileurl+";"+uploadedFileInfo.filePath);
							$("input[name='EXPLAIN_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
						}else{
							$("input[name='EXPLAIN_FILE_URL']").val(uploadedFileInfo.filePath);
							$("input[name='EXPLAIN_FILE_ID']").val(uploadedFileInfo.fileId);
						}								
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"${_file_Server}download/fileDownload?attachId="+uploadedFileInfo.fileId
						+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
						$("#fileListDiv").html(spanHtml); 
						
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function delUploadFile(fileId,attacheKey){
		//AppUtil.delUploadMater(fileId,attacheKey);
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#"+fileId).remove();
			var fileurl=$("input[name='EXPLAIN_FILE_URL']").val();
			var fileid=$("input[name='EXPLAIN_FILE_ID']").val();
			var arrayIds=fileid.split(";");
			var arrayurls=fileurl.split(";");
			for(var i=0;i<arrayIds.length;i++){
				if(arrayIds[i]==fileId){
					arrayIds.splice(i,1); 
					arrayurls.splice(i,1); 
					break;
				}
			}
			$("input[name='EXPLAIN_FILE_URL']").val(arrayurls.join(";"));
			$("input[name='EXPLAIN_FILE_ID']").val(arrayIds.join(";"));
		});
	}

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="restartFlowTaskExplainForm" method="post"
		action="flowTaskController.do?reStart&selectTaskIds=${selectTaskIds}">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="selectTaskIds"
			value="${selectTaskIds}">
		<input type="hidden" name="EXPLAIN_FILE_URL" >
		<input type="hidden" name="EXPLAIN_FILE_ID">
		<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;"></td>
			</tr>
			<tr>
				<td ><span style="width: 100px;float:left;text-align:right;">挂起说明
				<font class="dddl_platform_html_requiredFlag">*</font>：
				     </span>
				</td>
				<td  colspan="3">
					<textarea id="Explain" class="eve-textarea validate[required],maxSize[50]" rows="3" cols="200"  
					  name="EXPLAIN" style="width:300px;height:100px;"  ></textarea>
				</td>
			</tr>
			<tr>
				<td ><span style="width: 100px;float:left;text-align:right;">附件
				<font class="dddl_platform_html_requiredFlag">*</font>：
				     </span>
				</td>
				<td colspan="3">
					<div style="width:100%;display: none;" id="EXPLAIN_FILE_DIV"></div>
						<a href="javascript:void(0);" onclick="openRestartFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:80px;height:26px;"/>
					   </a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div style="width:100%;" id="fileListDiv"></div>
				</td>
			</tr>
		</table>
		<div class="eve_buttons">
			<input value="确定" type="submit"class="z-dlg-button z-dialog-cancelbutton" />
			<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>
