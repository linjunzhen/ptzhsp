<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  
<script type="text/javascript">
var __ctxPath="<%=request.getContextPath() %>";
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
function selectCommonXK(){
	 var op=$("#XK_CONTENT option:selected");
	 var itemId=$("input[name='itemId']").val();
	 var  xkcontent=op.val();
	 $.post("commonXKController.do?getInfoByName",{"xkcontent":xkcontent,"item_id":itemId},
				function(responseText,status,xhr){
		            $("textarea[name='xkcontent']").val(xkcontent);
				    var  result=$.parseJSON(responseText);
				    var xkfile_num=result.XKFILE_NUM;
				    var xkfile_name=result.XKFILE_NAME;
				    $("[name='xkfile_num']").val(xkfile_num);
				    $("[name='xkfile_name']").val(xkfile_name);
					}
					);
	}
function addXK(){
	var item_id=$("input[name='itemId']").val();
	var exeId=$("input[name='exeId']").val();
	var item_code=$("inout[name='item_code']").val();
	var xkfile_name=$("input[name='xkfile_name']").val();
	var xkfile_num=$("input[name='xkfile_num']").val();
	var xkcontent=$("textarea[name='xkcontent']").val();
	if(xkcontent.replace(/\s/g,"")==""){
		parent.art.dialog({
			content: "请填写您的许可内容",
			icon:"error",
			ok: true
		});
	}else if(xkfile_name.replace(/\s/g,"")==""){
		parent.art.dialog({
			content: "请填写您的许可文件名",
			icon:"error",
			ok: true
		});
	}else if(xkfile_num.replace(/\s/g,"")==""){
		parent.art.dialog({
			content: "请填写您的许可文件编号",
			icon:"error",
			ok: true
		});
	}else if(xkfile_num==item_code){
		parent.art.dialog({
			content: "许可文件名不能为事项编号",
			icon:"error",
			ok: true
		});
	}else if(xkfile_num==exeId){
		parent.art.dialog({
			content: "许可文件名不能为流程号",
			icon:"error",
			ok: true
		});
	}else{
		url="commonXKController.do?saveCommonXK";
		formData="&xkcontent="+xkcontent+"&xkfile_name="+xkfile_name+"&xkfile_num="+xkfile_num+"&item_id="+item_id;
		AppUtil.ajaxProgress({
			url:url,
			params:formData,
			callback:function(resultJson){
				if(resultJson.success){
					$("select[name=XK_CONTENT]").find("option:first").remove(); 
					$("select[name=XK_CONTENT]").prepend("<option value='"+xkcontent+"'>"+xkcontent+"</option>"); 
					$("select[name=XK_CONTENT]").prepend("<option value=''>==选择常用许可==</option>"); 
					$("select[name=XK_CONTENT]").val(xkcontent); 
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
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
}

function  removeCommonXK(){
	if($("select[name=XK_CONTENT]").children('option:selected').val()==""){
		parent.art.dialog({
			content: "请选择要删除的常用许可",
			icon:"error",
			ok: true
		});
	}else{
		var item_id=$("input[name='itemId']").val();
		art.dialog.confirm("您确定要删除吗?", function() {
			var xkContent =$("select[name=XK_CONTENT]").children('option:selected').text();
			url="commonXKController.do?removeXK";
			formData="&item_id="+item_id+"&xkcontent="+xkContent;
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if(resultJson.success){
						$("select[name=XK_CONTENT]").children("option:selected").remove(); 
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
					}else{
						$("select[name=XK_CONTENT]").children("option:selected").remove(); 
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				}
			});
		});
	}
}

// -------------------上传及高拍仪 开始-------------------------------------------------------------
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
function openVoerBtFileUploadDialog(){
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

/*盖章文件上传*/
function openKtStampUploadDialog() {
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	art.dialog.open('ktStampController/ktStampUploadView.do?attachType=attach&busTableName=JBPM6_FLOW_RESULT&uploadUserId='+uploadUserId + '&uploadUserName='+encodeURI(uploadUserName),{
		title : "盖章",
		width : "1000px",
		height : "800px",
		lock : true,
		resize : false,
		close:function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if (resultJsonInfo) {
				var fileId = resultJsonInfo.data.fileId;
				var fileName = resultJsonInfo.data.fileName;
				var filePath = resultJsonInfo.data.filePath;
				var attachType = 'attach';
				var fileurl=$("input[name='RESULT_FILE_URL']").val();
				var fileid=$("input[name='RESULT_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='RESULT_FILE_URL']").val(fileurl+";"+filePath);
					$("input[name='RESULT_FILE_ID']").val(fileid+";"+fileId);
				}else{
					$("input[name='RESULT_FILE_URL']").val(filePath);
					$("input[name='RESULT_FILE_ID']").val(fileId);
				}
				var spanHtml = "<p id=\""+fileId+"\"><a href=\"${_file_Server}download/fileDownload?attachId="+fileId
						+"&attachType="+attachType+"\" ";
				spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
				spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
				spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>";
				$("input[name='APPLYFORMFILEBACKEND']").val("${_file_Server}download/fileDownload?attachId="+fileId+"&attachType=attach");
				$("#fileListDiv").append(spanHtml);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	})
}

function bindScanCtrl(){
	var onlineBusTableName = "JBPM6_FLOW_RESULT";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	
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
// -------------------上传及高拍仪 结束-------------------------------------------------------------
function longChange(){
	var val=$("#islong_term").get(0).checked;//attr("checked");alert(val);
	if(val){
		$("#closespan1").css("display","none"); 
		$("#closespan2").css("display","none"); 
	}else{
		$("#closespan1").css("display",""); 
		$("#closespan2").css("display",""); 
	}
}
function sendMessage(){
	var val=$("#isSendMessageChang").get(0).checked;
// 	alert($("#isSendMessage").val());
	if(val){
		$("#isSendMessage").val(1); 
	}else{
		$("#isSendMessage").val(0); 
	}
}
function isOpenRes(){
	var val=$("#isOpen").get(0).checked;
// 	alert($("#is_open").val());
	if(val){
		$("#is_open").val(1); 
	}else{
		$("#is_open").val(0); 
	}
}

</script>
<style>
.eve-select-width{
	width:400px !important;
}
.sel-width{
   width:170px !important;
}
</style>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">办结结果</th>
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="itemId" value="${item_id}">
			<input type="hidden" name="exeId" value="${exeId}">
			<input type="hidden" name="item_code" value="${item_code}">
			<input type="hidden" name="APPLYFORMFILEBACKEND"
				   value="${APPLYFORMFILEBACKEND}">
			<input type="hidden" name="isYctb" value="${isYctb}">
			<!--==========隐藏域部分结束 ===========-->
		</tr>
		<c:if test="${IS_QDGWXCYYSFZB == 1}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件编号：
				</span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" >
				<input type="hidden" name="RESULT_FILE_ID">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="xkfile_num"
							class="eve-input validate[maxSize[60]]" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件名称：
				</span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name"
							class="eve-input validate[maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<c:if test="${itemXz == 'XK'}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书号：
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_num" name="xkdocument_num"
						   class="eve-input validate[maxSize[120]],custom[equalsSgsStr]" maxlength="120" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书名称：
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_name" name="xkdocument_name"
						   class="eve-input validate[maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可类别：</span>
				<td>
				<eve:eveselect clazz="tab_text validate[]" dataParams="XZXKLB"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="选择许可类别" name="xk_type" id="xk_type">
				</eve:eveselect>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">许可决定日期：</span>
				<td>
					<input type="text" id="xkdecide_time" name="xkdecide_time"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{\'%y-%M-%d\'||\'2099-12-31\'}'})"
						   readonly="true" class="eve-input Wdate validate[]" maxlength="60" style="width:180px;height:22px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可机关统一社会信用代码：</span></td>
				<td>
					<input type="text" id="xk_usc" name="xk_usc"
						   class="eve-input validate[]" maxlength="120" style="width:180px;" value="${USC}"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">持证人：</span>
				<td>
					<input type="text" id="xk_holder" name="xk_holder"
						   class="eve-input validate[]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">申请人税务登记号：</span>
				<td>
					<input type="text" id="xkdept_swdjh" name="xkdept_swdjh"
						   class="eve-input validate[]" maxlength="15" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政相对人事业单位证书号：</span>
				<td>
					<input type="text" id="xkdept_sydwzsh" name="xkdept_sydwzsh"
						   class="eve-input validate[]" maxlength="12" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位：</span>
				<td>
					<input type="text" id="xkdept_sjlydw" name="xkdept_sjlydw"
						   class="eve-input validate[]" maxlength="200" style="width:180px;" value="${XKJG }"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位统一社会信用代码：</span>
				<td>
					<input type="text" id="xkdept_sjlydw_usc" name="xkdept_sjlydw_usc" value="${USC}"
						   class="eve-input validate[]" maxlength="18" style="width:180px;" />
				</td>
			</tr>
			</c:if>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）机关：
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id" value="${XKJGID }"/>
				</td>
				<td>
					<input type="text" id="xkdept_name" name="xkdept_name"
					readonly="readonly" onclick="selectChildDepartName();" value="${XKJG }"
							class="eve-input" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">长期有效：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="islong_term" name="islong_term" onclick="longChange();" value="1"
						   style="width:30px;padding-left: 0px;margin-left: 0px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">生效时间：
				</span>
				</td><td>
					<input type="text" id="effect_time" name="effect_time"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{$dp.$D(\'close_time\')||\'2099-12-31\'}'})" 
					readonly="true" class="eve-input Wdate validate[]" maxlength="60" style="width:180px;height:22px;" />
				</td>
				<td><span id="closespan1" style="width: 125px;float:left;text-align:right;" >截止时间：
				</span>
				</td><td><span id="closespan2">
					<input type="text" id="close_time" name="close_time"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')||\'1949-10-01\'}',maxDate:'2099-12-31'})" 
					readonly="true" class="eve-input Wdate validate[]" style="width:180px;height:22px;" /></span>
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">短信通知用户：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isSendMessageChang" name="isSendMessageChang" onclick="sendMessage();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="isSendMessage" name="isSendMessage" value="0"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">反馈至群众用户中心：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isOpen" name="isOpen" onclick="isOpenRes();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="is_open" name="is_open" value="0"/>
				</td>
			</tr>
			<tr height="80px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">（许可）内容：
				</span>
				</td>
				<td colspan="3">
				<div style="margin-top:5px;margin-bottom:5px;" >
					<eve:eveselect clazz="eve-input eve-select-width" dataParams="${item_id}" 
						 dataInterface="commonXKService.findXKList" value=""
						  defaultEmptyText="==选择常用许可内容==" onchange="selectCommonXK();"  id="XK_CONTENT" name="XK_CONTENT">
					</eve:eveselect>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addXK();"
						style="cursor:pointer;vertical-align:middle;" title="添加到常用许可">
					</span>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonXK();" 
						style="cursor:pointer;vertical-align:middle;" title="删除选中的常用许可">
					</span>
					</div>
				 <textarea rows="5" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="600" style="width: 500px" name="xkcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">送达内容
				：</span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="500" style="width: 500px" name="sdcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">经营方式
				：</span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="500" style="width: 500px" name="run_mode"></textarea>
				</td>
			</tr>
			<tr>
				<td ><span style="width: 125px;float:left;text-align:right;">附件：
				</span>
				</td>				
				<td colspan="3">
					<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div><a id="RESULT_FILE_BTN"></a>
					<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
						<a href="javascript:bindScanCtrl()"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
					</div>
					<a href="javascript:void(0);" onclick="openVoerBtFileUploadDialog()">
						<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
					<a href="javascript:void(0);" onclick="openKtStampUploadDialog()">
						<img id="${applyMater.MATER_CODE}_QZ" src="webpage/bsdt/applyform/images/tab_btn_gz.png"  style="width:60px;height:22px;"/>
					</a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div style="width:100%;" id=fileListDiv></div>
				</td>
			</tr>
		</c:if>
		
		<c:if test="${isYctb==1 && IS_QDGWXCYYSFZB != 1}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件编号：
				</span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" >
				<input type="hidden" name="RESULT_FILE_ID">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="xkfile_num"
							class="eve-input validate[maxSize[60]]" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件名称：
				</span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name"
							class="eve-input validate[maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<c:if test="${itemXz == 'XK'}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书号：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_num" name="xkdocument_num"
						   class="eve-input validate[required,maxSize[120]],custom[equalsSgsStr]" maxlength="120" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书名称：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_name" name="xkdocument_name"
						   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可类别：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="XZXKLB"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="选择许可类别" name="xk_type" id="xk_type">
				</eve:eveselect>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">许可决定日期：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdecide_time" name="xkdecide_time"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{\'%y-%M-%d\'||\'2099-12-31\'}'})"
						   readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">许可机关统一社会信用代码：
			<font class="dddl_platform_html_requiredFlag">*</font></span></td>
				<td>
					<input type="text" id="xk_usc" name="xk_usc"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${USC}"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">持证人：
			<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xk_holder" name="xk_holder"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">申请人税务登记号：</span>
				<td>
					<input type="text" id="xkdept_swdjh" name="xkdept_swdjh"
						   class="eve-input validate[]" maxlength="15" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">行政相对人事业单位证书号：</span>
				<td>
					<input type="text" id="xkdept_sydwzsh" name="xkdept_sydwzsh"
						   class="eve-input validate[]" maxlength="12" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位：<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw" name="xkdept_sjlydw"
						   class="eve-input validate[required]" maxlength="200" style="width:180px;" value="${XKJG }"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">数据来源单位统一社会信用代码：<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw_usc" name="xkdept_sjlydw_usc" value="${USC}"
						   class="eve-input validate[required]" maxlength="18" style="width:180px;" />
				</td>
			</tr>
			</c:if>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）机关：
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id" value="${XKJGID }"/>
				</td>
				<td>
					<input type="text" id="xkdept_name" name="xkdept_name"
					readonly="readonly" onclick="selectChildDepartName();" value="${XKJG }"
							class="eve-input" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">长期有效：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="islong_term" name="islong_term" onclick="longChange();" value="1"
						   style="width:30px;padding-left: 0px;margin-left: 0px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">生效时间：
				</span>
				</td><td>
					<input type="text" id="effect_time" name="effect_time"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{$dp.$D(\'close_time\')||\'2099-12-31\'}'})" 
					readonly="true" class="eve-input Wdate validate[]" maxlength="60" style="width:180px;height:22px;" />
				</td>
				<td><span id="closespan1" style="width: 125px;float:left;text-align:right;" >截止时间：
				</span>
				</td><td><span id="closespan2">
					<input type="text" id="close_time" name="close_time"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')||\'1949-10-01\'}',maxDate:'2099-12-31'})" 
					readonly="true" class="eve-input Wdate validate[]" style="width:180px;height:22px;" /></span>
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">短信通知用户：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isSendMessageChang" name="isSendMessageChang" onclick="sendMessage();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="isSendMessage" name="isSendMessage" value="0"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">反馈至群众用户中心：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isOpen" name="isOpen" onclick="isOpenRes();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="is_open" name="is_open" value="0"/>
				</td>
			</tr>
			<tr height="80px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">（许可）内容：
				</span>
				</td>
				<td colspan="3">
				<div style="margin-top:5px;margin-bottom:5px;" >
					<eve:eveselect clazz="eve-input eve-select-width" dataParams="${item_id}" 
						 dataInterface="commonXKService.findXKList" value=""
						  defaultEmptyText="==选择常用许可内容==" onchange="selectCommonXK();"  id="XK_CONTENT" name="XK_CONTENT">
					</eve:eveselect>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addXK();"
						style="cursor:pointer;vertical-align:middle;" title="添加到常用许可">
					</span>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonXK();" 
						style="cursor:pointer;vertical-align:middle;" title="删除选中的常用许可">
					</span>
					</div>
				 <textarea rows="5" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="600" style="width: 500px" name="xkcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">送达内容
				：</span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="500" style="width: 500px" name="sdcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">经营方式
				：</span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="500" style="width: 500px" name="run_mode"></textarea>
				</td>
			</tr>
			<tr>
				<td ><span style="width: 125px;float:left;text-align:right;">附件：
				</span>
				</td>				
				<td colspan="3">
					<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div><a id="RESULT_FILE_BTN"></a>
					<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
						<a href="javascript:bindScanCtrl()"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
					</div>
					<a href="javascript:void(0);" onclick="openVoerBtFileUploadDialog()">
						<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
					<a href="javascript:void(0);" onclick="openKtStampUploadDialog()">
						<img id="${applyMater.MATER_CODE}_QZ" src="webpage/bsdt/applyform/images/tab_btn_gz.png"  style="width:60px;height:22px;"/>
					</a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div style="width:100%;" id=fileListDiv></div>
				</td>
			</tr>
		</c:if>
		
		<c:if test="${isYctb==null||isYctb==''||isYctb=='undefined'||isYctb!=1 && IS_QDGWXCYYSFZB !=1}">
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件编号：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" >
				<input type="hidden" name="RESULT_FILE_ID">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="xkfile_num"
							class="eve-input validate[required,maxSize[60]]" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）文件名称：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name"
							class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
				</td>
			</tr>
			<%--当事项为行政许可地时候，需要加填以下字段--%>
			<c:if test="${itemXz == 'XK'}">
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书号：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
					</td>
					<td>
						<input type="text" id="xkdocument_num" name="xkdocument_num"
							   class="eve-input validate[required,maxSize[120]],custom[equalsSgsStr]" maxlength="120" style="width:180px;" />
					</td>
					<td><span style="width: 125px;float:left;text-align:right;">行政许可决定文书名称：<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
					</td>
					<td>
						<input type="text" id="xkdocument_name" name="xkdocument_name"
							   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
					</td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">许可类别：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="XZXKLB"
									   dataInterface="dictionaryService.findDatasForSelect"
									   defaultEmptyText="选择许可类别" name="xk_type" id="xk_type">
						</eve:eveselect>
					</td>
					<td><span style="width: 125px;float:left;text-align:right;">许可决定日期：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
					<td>
					<input type="text" id="xkdecide_time" name="xkdecide_time"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{\'%y-%M-%d\'||\'2099-12-31\'}'})"
						   readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" />
					</td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">许可机关统一社会信用代码：
			<font class="dddl_platform_html_requiredFlag">*</font></span></td>
					<td>
						<input type="text" id="xk_usc" name="xk_usc"
							   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${USC}"/>
					</td>
					<td><span style="width: 125px;float:left;text-align:right;">持证人：
			<font class="dddl_platform_html_requiredFlag">*</font></span>
					<td>
						<input type="text" id="xk_holder" name="xk_holder"
							   class="eve-input validate[required]" maxlength="120" style="width:180px;" />
					</td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">申请人税务登记号：</span>
					<td>
						<input type="text" id="xkdept_swdjh" name="xkdept_swdjh"
							   class="eve-input validate[]" maxlength="15" style="width:180px;" />
					</td>
					<td><span style="width: 125px;float:left;text-align:right;">行政相对人事业单位证书号：</span>
					<td>
						<input type="text" id="xkdept_sydwzsh" name="xkdept_sydwzsh"
							   class="eve-input validate[]" maxlength="12" style="width:180px;" />
					</td>
				</tr>
				<tr>
					<td><span style="width: 125px;float:left;text-align:right;">数据来源单位：<font class="dddl_platform_html_requiredFlag">*</font></span>
					<td>
						<input type="text" id="xkdept_sjlydw" name="xkdept_sjlydw" value="${XKJG }"
							   class="eve-input validate[required]" maxlength="200" style="width:180px;" />
					</td>
					<td><span style="width: 125px;float:left;text-align:right;">数据来源单位统一社会信用代码：<font class="dddl_platform_html_requiredFlag">*</font></span>
					<td>
						<input type="text" id="xkdept_sjlydw_usc" name="xkdept_sjlydw_usc" value="${USC}"
							   class="eve-input validate[required]" maxlength="18" style="width:180px;" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">（许可）机关：
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id" value="${XKJGID }"/>
				</td>
				<td>
					<input type="text" id="xkdept_name" name="xkdept_name"
					readonly="readonly" onclick="selectChildDepartName();" value="${XKJG }"
							class="eve-input" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">长期有效：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="islong_term" name="islong_term" onclick="longChange();" value="1"
						   style="width:30px;padding-left: 0px;margin-left: 0px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">生效时间：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td><td>
					<input type="text" id="effect_time" name="effect_time"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'1949-10-01',maxDate:'#F{$dp.$D(\'close_time\')||\'2099-12-31\'}'})" 
					readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" />
				</td>
				<td><span id="closespan1" style="width: 125px;float:left;text-align:right;" >截止时间：
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				</td><td><span id="closespan2">
					<input type="text" id="close_time" name="close_time"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')||\'1949-10-01\'}',maxDate:'2099-12-31'})" 
					readonly="true" class="eve-input Wdate validate[required]" style="width:180px;height:22px;" /></span>
				</td>
			</tr>
			<tr>
				<td><span style="width: 125px;float:left;text-align:right;">短信通知用户：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isSendMessageChang" name="isSendMessageChang" onclick="sendMessage();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="isSendMessage" name="isSendMessage" value="0"/>
				</td>
				<td><span style="width: 125px;float:left;text-align:right;">反馈至群众用户中心：</span>
				</td>
				<td align="left">
					<input type="checkbox" id="isOpen" name="isOpen" onclick="isOpenRes();" value="0"
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
					<input type="hidden" id="is_open" name="is_open" value="0"/>
				</td>
			</tr>
			<tr height="80px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">（许可）内容：
				<font class="dddl_platform_html_requiredFlag">*</font>
				     </span>
				</td>
				<td colspan="3">
				<div style="margin-top:5px;margin-bottom:5px;" >
					<eve:eveselect clazz="eve-input eve-select-width" dataParams="${item_id}" 
						 dataInterface="commonXKService.findXKList" value=""
						  defaultEmptyText="==选择常用许可内容==" onchange="selectCommonXK();"  id="XK_CONTENT" name="XK_CONTENT">
					</eve:eveselect>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addXK();"
						style="cursor:pointer;vertical-align:middle;" title="添加到常用许可">
					</span>
					<span style="width:30px;display:inline-block;text-align:right;">
					<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonXK();" 
						style="cursor:pointer;vertical-align:middle;" title="删除选中的常用许可">
					</span>
					</div>
				 <textarea rows="5" cols="6"
						class="eve-textarea validate[required,maxSize[1500]]"
						maxlength="1600" style="width: 500px" name="xkcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">送达内容
				：
				<font class="dddl_platform_html_requiredFlag">*</font>
				     </span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[required,maxSize[500]]"
						maxlength="500" style="width: 500px" name="sdcontent"></textarea>
				</td>
			</tr>
			<tr height="70px"> 
				<td ><span style="width: 125px;float:left;text-align:right;">经营方式
				：
				     </span>
				</td>
				<td colspan="3">
				 <textarea rows="4" cols="6"
						class="eve-textarea validate[maxSize[500]]"
						maxlength="500" style="width: 500px" name="run_mode"></textarea>
				</td>
			</tr>
			<tr>
				<td ><span style="width: 125px;float:left;text-align:right;">附件：
				<font class="dddl_platform_html_requiredFlag">*</font>
				     </span>
				</td>
				<td colspan="3">
					<div style="width:100%;display: none;" id="RESULT_FILE_DIV"></div><a id="RESULT_FILE_BTN"></a>
					<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
						<a href="javascript:bindScanCtrl()"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
					</div>
					<a href="javascript:void(0);" onclick="openVoerBtFileUploadDialog()">
						<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
					<a href="javascript:void(0);" onclick="openKtStampUploadDialog()">
						<img id="${applyMater.MATER_CODE}_QZ" src="webpage/bsdt/applyform/images/tab_btn_gz.png"  style="width:60px;height:22px;"/>
					</a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div style="width:100%;" id=fileListDiv></div>
				</td>
			</tr>
		</c:if>
	</table>