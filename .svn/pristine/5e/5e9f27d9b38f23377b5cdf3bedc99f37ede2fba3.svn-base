<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  
 <eve:resources loadres="jquery,easyui,apputil,layer,laydate,validationegine,artdialog,json2,swfupload"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/main/css/style1.css"/>
<script type="text/javascript">
var __ctxPath="<%=request.getContextPath() %>";
var previewPhotoStr = "";
function subResult(){
	   var updateId=$("input[name='UPDATE_FILE_ID']").val();
	   var fileId=$("input[name='RESULT_FILE_ID']").val();
	   if(fileId==null||fileId==""){
			$.messager.alert('警告',"办件结果附件不能为空，请上传附件，谢谢！");
			return ; 
		}
	   if(updateId==null||updateId==""){
			$.messager.alert('警告',"变更申请表不能为空，请上传附件，谢谢！");
			return ; 
		}
	 
}
$(function(){
	AppUtil.initWindowForm("SubmitResultForm", function(form, valid) {
		   if (valid) {
			   var updateId=$("input[name='UPDATE_FILE_ID']").val();
			   var fileId=$("input[name='RESULT_FILE_ID']").val();
			   if(fileId==null||fileId==""){
					$.messager.alert('警告',"办件结果附件不能为空，请上传附件，谢谢！");
					return ; 
				}
			   if(updateId==null||updateId==""){
					$.messager.alert('警告',"变更申请表不能为空，请上传附件，谢谢！");
					return ;
				}
			   //将提交按钮禁用,防止重复提交
			   $("input[type='submit']").attr("disabled", "disabled");
			   var formData = $("#SubmitResultForm").serialize();
			    var url = $("#SubmitResultForm").attr("action");
			    AppUtil.ajaxProgress({
				     url : url,
				     params : formData,
				     callback : function(resultJson) {
				      if (resultJson.success) {
				       parent.art.dialog({
				        content: "修改成功",
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
		   }
		  }, "T_CKBS_NUMRECORD");
});
$(function(){
	var flowobj='<%=request.getAttribute("resultJson")%>';
	var test=eval(<%=request.getAttribute("resultJson")%>);
	//var test=JSON.parse(flowobj);//$.parseJSON(flowobj);// JSON2.parse(flowobj);
	//document.getElementById("XKCONTENT").value=test["XKCONTENT"];
	$("textarea[name='xkcontent']").val(test["XKCONTENT"]);
	$("textarea[name='sdcontent']").val(test["SDCONTENT"]);
	$("textarea[name='run_mode']").val(test["RUN_MODE"]);
	var fields = $("#islong_term");
	if(test["ISLONG_TERM"]=="1"){
		fields.attr("checked","checked");
		$("#closespan1").css("display","none"); 
		$("#closespan2").css("display","none"); 
	}
	/**
	//初始化材料列表
	AppUtil.initNetUploadMaters({
		busTableName:"T_BSFW_XMBAQSZHPS"
	});
	**/
	var fileids=test["RESULT_FILE_ID"];
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
    		 if(resultJson!="websessiontimeout"){
    		 	$("#fileListDiv").html("");
    		 	var newhtml = "";
    		 	var list=resultJson.rows;
    		 	for(var i=0; i<list.length; i++){
    		 		newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
    		 		newhtml+=list[i].FILE_NAME+'</a>';
    		 		newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
    		 		newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
    		 	    newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
    		 		newhtml+='</p>';
    		 	} 
    		 	$("#fileListDiv").html(newhtml);
    		 }
     });
	 fileids=test["UPDATE_FILE_ID"];
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
    		 if(resultJson!="websessiontimeout"){
    		 	$("#fileListDiv1").html("");
    		 	var newhtml = "";
    		 	var list=resultJson.rows;
    		 	for(var i=0; i<list.length; i++){
    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
    		 		newhtml+=list[i].FILE_NAME+'</a>';
    		 		newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
    		 		newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
    		 	} 
    		 	$("#fileListDiv1").html(newhtml);
    		 }
     });
});
function selectChildDepartName(){
		var departId = $("input[name='xkdept_id']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=&noAuth=true", {
            title : "部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='xkdept_id']").val(selectDepInfo.departIds);
                    $("input[name='xkdept_name']").val(selectDepInfo.departNames);
                }
            }
        }, false);
	}

$(function() {
	/* AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "JBPM6_FLOW_RESULT",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "RESULT_FILE_BTN",
		singleFileDivId : "RESULT_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='RESULT_FILE_URL']").val();
				var fileid=$("input[name='RESULT_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='RESULT_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='RESULT_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='RESULT_FILE_URL']").val(resultJson.filePath);
					$("input[name='RESULT_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#RESULT_FILE_DIV").children("a").eq(1).html());
				//$("#RESULT_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
function openResultFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=JBPM6_FLOW_RESULT', {
		title: "上传(附件)",
		width: "655px",
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
					var fileurl=$("input[name='RESULT_FILE_URL']").val();
					var fileid=$("input[name='RESULT_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='RESULT_FILE_URL']").val(fileurl+";"+uploadedFileInfo.filePath);
						$("input[name='RESULT_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='RESULT_FILE_URL']").val(uploadedFileInfo.filePath);
						$("input[name='RESULT_FILE_ID']").val(uploadedFileInfo.fileId);
					}
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"${_file_Server}download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#fileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
$(function() {
	/* AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "JBPM6_FLOW_RESULT",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "UPDATE_FILE_BTN",
		singleFileDivId : "UPDATE_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='UPDATE_FILE_URL']").val();
				var fileid=$("input[name='UPDATE_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='UPDATE_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='UPDATE_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='UPDATE_FILE_URL']").val(resultJson.filePath);
					$("input[name='UPDATE_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#UPDATE_FILE_DIV").children("a").eq(1).html());
				//$("#UPDATE_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#fileListDiv1").append(spanHtml); 
		}
	});  */
});

/**
* 标题附件上传对话框
*/
function openUpdateFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=JBPM6_FLOW_RESULT', {
		title: "上传(附件)",
		width: "655px",
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
					var fileurl=$("input[name='UPDATE_FILE_URL']").val();
					var fileid=$("input[name='UPDATE_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='UPDATE_FILE_URL']").val(fileurl+";"+uploadedFileInfo.filePath);
						$("input[name='UPDATE_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='UPDATE_FILE_URL']").val(uploadedFileInfo.filePath);
						$("input[name='UPDATE_FILE_ID']").val(uploadedFileInfo.fileId);
					}
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"${_file_Server}download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#fileListDiv1").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
function bindScanCtrl(){
	var onlineBusTableName = "JBPM6_FLOW_RESULT";
	//定义上传的人员的ID
	var uploadUserId = '${sessionScope.curLoginUser.userId}';
	//定义上传的人员的NAME
	var uploadUserName = '${sessionScope.curLoginUser.fullname}';
	
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName+
		"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				initScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function initScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='RESULT_FILE_URL']").val();
		var fileid=$("input[name='RESULT_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='RESULT_FILE_URL']").val(fileurl+";"+resultJson[i].data.filePath);
			$("input[name='RESULT_FILE_ID']").val(fileid+";"+resultJson[i].data.fileId);
		}else{
			$("input[name='RESULT_FILE_URL']").val(resultJson[i].data.filePath);
			$("input[name='RESULT_FILE_ID']").val(resultJson[i].data.fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].data.fileId;
        //获取文件名称
        var fileName = resultJson[i].data.fileName;
        //获取文件路径
        var filePath = resultJson[i].data.filePath;
        //获取文件的类型
        var fileType = resultJson[i].data.fileType;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
        $("#fileListDiv").append(spanHtml); 
	}
}
/* function delUploadFile(fileId,attacheKey){
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
} */
function delUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
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
	});
}
/* function delUploadFile1(fileId,attacheKey){
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
 */
function delUploadFile1(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
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
	});
}
function longChange(){
	var val=$("#islong_term").get(0).checked;//attr("checked");alert(val);
	if(val){
	    $("input[name='islong_term']").val(1);
		$("#closespan1").css("display","none"); 
		$("#closespan2").css("display","none"); 
	}else{
        $("input[name='islong_term']").val(0);
		$("#closespan1").css("display",""); 
		$("#closespan2").css("display",""); 
	}
}
function sendMessage(){
	var val=$("#isSendMessageChang").get(0).checked;
	//alert($("#isSendMessage").val());
	if(val){
		$("#isSendMessage").val(1); 
	}else{
		$("#isSendMessage").val(0); 
	}
}
</script>

<body class="eui-diabody" >
	<form id="SubmitResultForm" method="post"
		action="executionController.do?saveFlowResult">
	<div  id="SubmitResultFormDiv" data-options="region:'center',split:true,border:false">
	<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
		<tr>
			<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>办理结果修改</a></div></td>
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="itemId" value="${item_id}">
			<input type="hidden" name="EXE_ID" value="${exeId}">
			<input type="hidden" name="item_code" value="${item_code}">
			<input type="hidden" name="RESULT_ID" value="${flowResult.RESULT_ID}">
			<input type="hidden" name="islong_term" value="${flowResult.ISLONG_TERM}">
			<!--==========隐藏域部分结束 ===========-->
		</tr>
		<tr>
				<td><span style="width: 140px;float:left;text-align:right;">（许可）文件编号：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" value="${flowResult.RESULT_FILE_URL }">
				<input type="hidden" name="RESULT_FILE_ID" value="${flowResult.RESULT_FILE_ID }">
				<input type="hidden" name="UPDATE_FILE_URL" value="${flowResult.UPDATE_FILE_URL }">
				<input type="hidden" name="UPDATE_FILE_ID" value="${flowResult.UPDATE_FILE_ID }">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="xkfile_num" value="${flowResult.XKFILE_NUM}"
							class="eve-input validate[required,maxSize[60]]" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 140px;float:left;text-align:right;">（许可）文件名称：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name" value="${flowResult.XKFILE_NAME}"
							class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
		</tr>
		<c:if test="${itemXz == 'XK'}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书号：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_num" name="xkdocument_num"
						   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" value="${flowResult.XKDOCUMENT_NUM}"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书名称：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_name" name="xkdocument_name"
						   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" value="${flowResult.XKDOCUMENT_NAME}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可类别：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="XZXKLB"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="选择许可类别" name="xk_type" id="xk_type" value="${flowResult.XK_TYPE}">
					</eve:eveselect>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">许可决定日期：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdecide_time" name="xkdecide_time"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'close_time\')}'})"
						   readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" value="${flowResult.XKDECIDE_TIME}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可机关统一社会信用代码：
			<font class="dddl_platform_html_requiredFlag">*</font></span></td>
				<td>
					<input type="text" id="xk_usc" name="xk_usc"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${flowResult.XK_USC}"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">持证人：
			<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xk_holder" name="xk_holder"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${flowResult.XK_HOLDER}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">申请人税务登记号：</span>
				<td>
					<input type="text" id="xkdept_swdjh" name="xkdept_swdjh" value="${flowResult.XKDEPT_SWDJH}"
						   class="eve-input validate[]" maxlength="15" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政相对人事业单位证书号：</span>
				<td>
					<input type="text" id="xkdept_sydwzsh" name="xkdept_sydwzsh" value="${flowResult.XKDEPT_SYDWZSH}"
						   class="eve-input validate[]" maxlength="12" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位：<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw" name="xkdept_sjlydw" value="${flowResult.XKDEPT_SJLYDW}"
						   class="eve-input validate[required]" maxlength="200" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位统一社会信用代码：<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw_usc" name="xkdept_sjlydw_usc" value="${flowResult.XKDEPT_SJLYDW_USC}"
						   class="eve-input validate[required]" maxlength="18" style="width:180px;" />
				</td>
			</tr>
		</c:if>
		<tr>
				<td><span style="width: 140px;float:left;text-align:right;">（许可）机关：
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id" value="${flowResult.XKDEPT_ID}"/>
				</td>
				<td>
					<input type="text" id="xkdept_name" name="xkdept_name"
					readonly="readonly" onclick="selectChildDepartName();" value="${flowResult.XKDEPT_NAME}"
							class="eve-input" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 140px;float:left;text-align:right;">长期有效：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="islong_term" name="islong_term_1" onclick="longChange();" value="1"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
				</td>
		</tr>
		<tr>
				<td><span style="width: 140px;float:left;text-align:right;">生效时间：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td><td>
					<input type="text" id="effect_time" name="effect_time" value="${flowResult.EFFECT_TIME}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'close_time\')}'})" 
					readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" />
				</td>
				<td><span id="closespan1" style="width: 140px;float:left;text-align:right;" >截止时间：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td><td><span id="closespan2">
					<input type="text" id="close_time" name="close_time"  value="${flowResult.CLOSE_TIME}"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')}'})" 
					readonly="true" class="eve-input Wdate validate[required]" style="width:180px;height:22px;" /></span>
				</td>
		</tr>

		<tr height="80px"> 
			<td ><span style="width: 140px;float:left;text-align:right;">（许可）内容：
			<font class="dddl_platform_html_requiredFlag">*</font>
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"   value="${flowResult.XKCONTENT}"
					class="eve-textarea validate[required,maxSize[1500]]"
					maxlength="1600" style="width: 500px" name="xkcontent"></textarea>
			</td>
		</tr>
		<tr height="70px"> 
			<td ><span style="width: 140px;float:left;text-align:right;">送达内容
			：
			<font class="dddl_platform_html_requiredFlag">*</font>
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="4" cols="6"    value="${flowResult.SDCONTENT}"
					class="eve-textarea validate[required,maxSize[500]]"
					maxlength="500" style="width: 500px" name="sdcontent"></textarea>
			</td>
		</tr>
		<tr height="70px"> 
			<td ><span style="width: 140px;float:left;text-align:right;">经营方式
			：
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="4" cols="6"
					class="eve-textarea validate[maxSize[500]]"  value="${flowResult.RUN_MODE}"
					maxlength="500" style="width: 500px" name="run_mode"></textarea>
			</td>
		</tr>
		<tr>
			<td ><span style="width: 140px;float:left;text-align:right;">附件：
			<font class="dddl_platform_html_requiredFlag">*</font>
			     </span>
			</td>
			<td colspan="3">
				<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div>
				<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
					<a href="javascript:bindScanCtrl()"><img id="${applyMater.MATER_CODE}"
					src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
				</div>
				<a href="javascript:void(0);" onclick="openResultFileUploadDialog()">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="3">
				<div style="width:100%;" id=fileListDiv></div>
			</td>
		</tr>
		<tr>
			<td ><span style="width: 140px;float:left;text-align:right;">变更申请表：
			<font class="dddl_platform_html_requiredFlag">*</font>
			     </span>
			</td>
			<td colspan="3">
				<div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div>
				<a href="javascript:void(0);" onclick="openUpdateFileUploadDialog()">
					<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
				</a>
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="3">
				<div style="width:100%;" id=fileListDiv1></div>
			</td>
		</tr>
	</table>
	</div>
	<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="提交修改" type="submit" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
		</form>
</body>