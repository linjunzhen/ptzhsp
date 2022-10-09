<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" -->
<!-- 	src="<%=basePath%>/webpage/bsdt/applyform/estate/js/commercial.js"></script> -->

<script type="text/javascript">

$(function(){
	
	 //默认企业法人
	 //qyclick();
	 //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
	 $("input[name='JBR_NAME']").removeAttr('readonly');
	 $("input[name='JBR_ZJHM']").removeAttr('readonly');

	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
	dealItems = "," + dealItems + ",";
	if(flowSubmitObj){
		//获取表单字段权限控制信息
		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		var exeid = flowSubmitObj.EFLOW_EXEID;
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		 //初始化表单字段权限控制
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_GYJSZYDJ_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			initAutoTable(flowSubmitObj);//初始化权利信息
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_GYJSZYDJ_FORM");
			//动态切换抵押信息
			queryTypeFn();
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
				$("#T_BDC_GYJSZYDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDC_GYJSZYDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				if($("input[name='SBMC']").val()){
				}else{
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
				}
			}else if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
				//申报对象信息不可修改
				$("#userinfo_div input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#userinfo_div select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				//事项基本信息不可修改
				$("#baseinfo input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				//经办人信息可修改		
				$("#JBRXX input").each(function(index){
					$(this).attr("disabled",false);
				});
				$("#JBRXX select").each(function(index){
					$(this).attr("disabled",false);
				});	
			}
		}else{
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
			//默认抵押情况栏中抵押权人证件类型为'统一社会信用代码'
			$("select[name='POWERSOURCE_DYQRZJLX']").val("统一社会信用代码");
		}

		var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
			$("#ptcyjb").attr("style","");
			if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
				$("input[name='JBR_NAME']").removeAttr('readonly');
			}
		}
	}
	
	//初始化材料列表
	//AppUtil.initNetUploadMaters({
	//	busTableName:"T_BDC_GYJSZYDJ"
	//});
	
	//初始化字段不可修改
	 $("select[name='POWERSOURCE_QSLYLX']").attr('disabled','disabled');
	 $("select[name='POWERSOURCE_FRZJLX']").attr('disabled','disabled');
	
});

// -------------------权利人信息-权利人上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='QLR_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#QLR_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#QLR_fileListDiv").html(newhtml);
	    	}
    });
         
	/* AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_GYJSZYDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "QLR_FILE_BTN",
		singleFileDivId : "QLR_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='QLR_FILE_URL']").val();
				var fileid=$("input[name='QLR_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='QLR_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='QLR_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='QLR_FILE_URL']").val(resultJson.filePath);
					$("input[name='QLR_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#DYQRXX_FILE_DIV").children("a").eq(1).html());
				//$("#DYQRXX_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"QLRdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#QLR_fileListDiv").append(spanHtml); 
		}
	});  */
});
function openSlxxFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDC_GYJSZYDJ', {
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
					var fileurl=$("input[name='QLR_FILE_URL']").val();
					var fileid=$("input[name='QLR_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='QLR_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='QLR_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='QLR_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='QLR_FILE_ID']").val(uploadedFileInfo.fileId);
					}					
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"QLRdelUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#QLR_fileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
    function QLRchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	QLRbindScanCtrl();
        }else if(gpytype=="2"){
        	QLRbindScanCtrlLT();
        }else if(gpytype=="3"){
        	QLRbindScanCtrlZT();
        }
    }
function QLRbindScanCtrl(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
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
				QLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function QLRbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName+
		"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				QLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function QLRbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName+
		"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				QLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function QLRinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='QLR_FILE_URL']").val();
		var fileid=$("input[name='QLR_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='QLR_FILE_URL']").val(fileurl+";"+resultJson[i].data.filePath);
			$("input[name='QLR_FILE_ID']").val(fileid+";"+resultJson[i].data.fileId);
		}else{
			$("input[name='QLR_FILE_URL']").val(resultJson[i].data.filePath);
			$("input[name='QLR_FILE_ID']").val(resultJson[i].data.fileId);
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
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"QLRdelUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
        $("#QLR_fileListDiv").append(spanHtml); 
	}
}

function QLRdelUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='QLR_FILE_URL']").val();
		var fileid=$("input[name='QLR_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='QLR_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='QLR_FILE_ID']").val(arrayIds.join(";"));
	});
}
// -------------------权利人信息-权利人上传及高拍仪 结束-------------------------------------------------------------
// -------------------权利人信息-法定代表人上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='LEGAL_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#LEGAL_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#LEGAL_fileListDiv").html(newhtml);
	    	}
    });
         
	AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_GYJSZYDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "LEGAL_FILE_BTN",
		singleFileDivId : "LEGAL_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='LEGAL_FILE_URL']").val();
				var fileid=$("input[name='LEGAL_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='LEGAL_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='LEGAL_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='LEGAL_FILE_URL']").val(resultJson.filePath);
					$("input[name='LEGAL_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#DYQRXX_FILE_DIV").children("a").eq(1).html());
				//$("#DYQRXX_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"LEGALdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#LEGAL_fileListDiv").append(spanHtml); 
		}
	}); 
});
    function LEGALchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	LEGALbindScanCtrl();
        }else if(gpytype=="2"){
        	LEGALbindScanCtrlLT();
        }else if(gpytype=="3"){
        	LEGALbindScanCtrlZT();
        }
    }
function LEGALbindScanCtrl(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				LEGALinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function LEGALbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				LEGALinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function LEGALbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				LEGALinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function LEGALinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='LEGAL_FILE_URL']").val();
		var fileid=$("input[name='LEGAL_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='LEGAL_FILE_URL']").val(fileurl+";"+resultJson[i].filePath);
			$("input[name='LEGAL_FILE_ID']").val(fileid+";"+resultJson[i].fileId);
		}else{
			$("input[name='LEGAL_FILE_URL']").val(resultJson[i].filePath);
			$("input[name='LEGAL_FILE_ID']").val(resultJson[i].fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        //获取材料KEY
        var attachKey =resultJson[i].attachKey;
        //获取文件路径
        var filePath = resultJson[i].filePath;
        //获取文件的类型
        var fileType = resultJson[i].fileType;
        //获取是否是图片类型
        var isImg = resultJson[i].isImg;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"LEGALdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
        $("#LEGAL_fileListDiv").append(spanHtml); 
	}
}
function LEGALdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='LEGAL_FILE_URL']").val();
	var fileid=$("input[name='LEGAL_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='LEGAL_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='LEGAL_FILE_ID']").val(arrayIds.join(";"));
}
// -------------------权利人信息-法定代表人上传及高拍仪 结束-------------------------------------------------------------
// -------------------权利人信息-代理人上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='POWAGENT_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#POWAGENT_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#POWAGENT_fileListDiv").html(newhtml);
	    	}
    });
         
	AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_GYJSZYDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "POWAGENT_FILE_BTN",
		singleFileDivId : "POWAGENT_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='POWAGENT_FILE_URL']").val();
				var fileid=$("input[name='POWAGENT_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='POWAGENT_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='POWAGENT_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='POWAGENT_FILE_URL']").val(resultJson.filePath);
					$("input[name='POWAGENT_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#DYQRXX_FILE_DIV").children("a").eq(1).html());
				//$("#DYQRXX_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"POWAGENTdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#POWAGENT_fileListDiv").append(spanHtml); 
		}
	}); 
});
    function POWAGENTchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	POWAGENTbindScanCtrl();
        }else if(gpytype=="2"){
        	POWAGENTbindScanCtrlLT();
        }else if(gpytype=="3"){
        	POWAGENTbindScanCtrlZT();
        }
    }
function POWAGENTbindScanCtrl(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				POWAGENTinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function POWAGENTbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				POWAGENTinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function POWAGENTbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				POWAGENTinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function POWAGENTinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='POWAGENT_FILE_URL']").val();
		var fileid=$("input[name='POWAGENT_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='POWAGENT_FILE_URL']").val(fileurl+";"+resultJson[i].filePath);
			$("input[name='POWAGENT_FILE_ID']").val(fileid+";"+resultJson[i].fileId);
		}else{
			$("input[name='POWAGENT_FILE_URL']").val(resultJson[i].filePath);
			$("input[name='POWAGENT_FILE_ID']").val(resultJson[i].fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        //获取材料KEY
        var attachKey =resultJson[i].attachKey;
        //获取文件路径
        var filePath = resultJson[i].filePath;
        //获取文件的类型
        var fileType = resultJson[i].fileType;
        //获取是否是图片类型
        var isImg = resultJson[i].isImg;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"POWAGENTdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
        $("#POWAGENT_fileListDiv").append(spanHtml); 
	}
}
function POWAGENTdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='POWAGENT_FILE_URL']").val();
	var fileid=$("input[name='POWAGENT_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='POWAGENT_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='POWAGENT_FILE_ID']").val(arrayIds.join(";"));
}
// -------------------权利人信息-代理人上传及高拍仪 结束-------------------------------------------------------------
// -------------------权属来源-法定代表人上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='FRDB_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#FRDB_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#FRDB_fileListDiv").html(newhtml);
	    	}
    });
         
	AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_GYJSZYDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "FRDB_FILE_BTN",
		singleFileDivId : "FRDB_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='FRDB_FILE_URL']").val();
				var fileid=$("input[name='FRDB_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='FRDB_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='FRDB_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='FRDB_FILE_URL']").val(resultJson.filePath);
					$("input[name='FRDB_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#DYQRXX_FILE_DIV").children("a").eq(1).html());
				//$("#DYQRXX_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"FRDBdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#FRDB_fileListDiv").append(spanHtml); 
		}
	}); 
});
    function FRDBchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	FRDBbindScanCtrl();
        }else if(gpytype=="2"){
        	FRDBbindScanCtrlLT();
        }else if(gpytype=="3"){
        	FRDBbindScanCtrlZT();
        }
    }
function FRDBbindScanCtrl(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				FRDBinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function FRDBbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				FRDBinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function FRDBbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				FRDBinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function FRDBinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='FRDB_FILE_URL']").val();
		var fileid=$("input[name='FRDB_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='FRDB_FILE_URL']").val(fileurl+";"+resultJson[i].filePath);
			$("input[name='FRDB_FILE_ID']").val(fileid+";"+resultJson[i].fileId);
		}else{
			$("input[name='FRDB_FILE_URL']").val(resultJson[i].filePath);
			$("input[name='FRDB_FILE_ID']").val(resultJson[i].fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        //获取材料KEY
        var attachKey =resultJson[i].attachKey;
        //获取文件路径
        var filePath = resultJson[i].filePath;
        //获取文件的类型
        var fileType = resultJson[i].fileType;
        //获取是否是图片类型
        var isImg = resultJson[i].isImg;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"FRDBdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
        $("#FRDB_fileListDiv").append(spanHtml); 
	}
}
function FRDBdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='FRDB_FILE_URL']").val();
	var fileid=$("input[name='FRDB_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='FRDB_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='FRDB_FILE_ID']").val(arrayIds.join(";"));
}
// -------------------权属来源-法定代表人上传及高拍仪 结束-------------------------------------------------------------
// -------------------权属来源-代理人上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='DLR_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#DLR_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#DLR_fileListDiv").html(newhtml);
	    	}
    });
         
	AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_GYJSZYDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "DLR_FILE_BTN",
		singleFileDivId : "DLR_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='DLR_FILE_URL']").val();
				var fileid=$("input[name='DLR_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='DLR_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='DLR_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='DLR_FILE_URL']").val(resultJson.filePath);
					$("input[name='DLR_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#DYQRXX_FILE_DIV").children("a").eq(1).html());
				//$("#DYQRXX_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"DLRdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#DLR_fileListDiv").append(spanHtml); 
		}
	}); 
});
    function DLRchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	DLRbindScanCtrl();
        }else if(gpytype=="2"){
        	DLRbindScanCtrlLT();
        }else if(gpytype=="3"){
        	DLRbindScanCtrlZT();
        }
    }
function DLRbindScanCtrl(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				DLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function DLRbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				DLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function DLRbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_GYJSZYDJ";
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				DLRinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function DLRinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='DLR_FILE_URL']").val();
		var fileid=$("input[name='DLR_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='DLR_FILE_URL']").val(fileurl+";"+resultJson[i].filePath);
			$("input[name='DLR_FILE_ID']").val(fileid+";"+resultJson[i].fileId);
		}else{
			$("input[name='DLR_FILE_URL']").val(resultJson[i].filePath);
			$("input[name='DLR_FILE_ID']").val(resultJson[i].fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        //获取材料KEY
        var attachKey =resultJson[i].attachKey;
        //获取文件路径
        var filePath = resultJson[i].filePath;
        //获取文件的类型
        var fileType = resultJson[i].fileType;
        //获取是否是图片类型
        var isImg = resultJson[i].isImg;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"DLRdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
        $("#DLR_fileListDiv").append(spanHtml); 
	}
}
function DLRdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='DLR_FILE_URL']").val();
	var fileid=$("input[name='DLR_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='DLR_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='DLR_FILE_ID']").val(arrayIds.join(";"));
}
// -------------------权属来源-代理人上传及高拍仪 结束-------------------------------------------------------------

//表单提交方法
function FLOW_SubmitFun(flowSubmitObj){	
	//先判断表单是否验证通过
	 var validateResult =$("#T_BDC_GYJSZYDJ_FORM").validationEngine("validate");
	 if(validateResult){
		 var flag = checkLimitPerson();
		 if(!flag){
		 	return;
		 }
		 getPowerInfoJson();
         getPowerPeopleInfoJson();
         getPowerSourceInfoJson();
         
         //setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDC_GYJSZYDJ_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 //console.dir(flowSubmitObj);	
			return flowSubmitObj;
		 }else{
			 return null;
		 }			 
	 }else{
		 return null;
	 } 
	
}
  
//社会限制人员检验接口
function checkLimitPerson(){
	var data = [];
	var qlrtrs = $("#powerPeopleInfo tr[id*='powerPeopleInfo_']");
	for(var i=0;i<qlrtrs.length;i++){		
		var id = $(qlrtrs[i]).attr("id");
		var qlrData = {};
		var POWERPEOPLENAME = $("#"+id).find("[name='POWERPEOPLENAME']").val();
		var POWERPEOPLEIDNUM = $("#"+id).find("[name='POWERPEOPLEIDNUM']").val();
		if(POWERPEOPLENAME!='' && POWERPEOPLEIDNUM!='' ){
			qlrData["xm"] = POWERPEOPLENAME;
			qlrData["zjhm"] = POWERPEOPLEIDNUM;
			data.push(qlrData);
		}
	
		
		var frData = {};
		var POWLEGALNAME = $("#"+id).find("[name='POWLEGALNAME']").val();
		var POWLEGALIDNUM = $("#"+id).find("[name='POWLEGALIDNUM']").val();
		if(POWLEGALNAME!=''&& POWLEGALIDNUM!=''){		
			frData["xm"] = POWLEGALNAME;
			frData["zjhm"] = POWLEGALIDNUM;
			data.push(frData);
		}
		var dlrData = {};
		var POWAGENTNAME = $("#"+id).find("[name='POWAGENTNAME']").val();
		var POWAGENTIDNUM = $("#"+id).find("[name='POWAGENTIDNUM']").val();
		if(POWAGENTNAME!=''&& POWAGENTIDNUM!=''){		
			dlrData["xm"] = POWAGENTNAME;
			dlrData["zjhm"] = POWAGENTIDNUM;
			data.push(dlrData);
		}
	}
	var qslytrs = $("#powerSourceInfo tr[id*='powerSourceInfo_']");
	for(var i=0;i<qslytrs.length;i++){		
		var id = $(qslytrs[i]).attr("id");
		var qlrData = {};
		var POWERSOURCE_QLRMC = $("#"+id).find("[name='POWERSOURCE_QLRMC']").val();
		var POWERSOURCE_ZJHM = $("#"+id).find("[name='POWERSOURCE_ZJHM']").val();
		if(POWERSOURCE_QLRMC!='' && POWERSOURCE_ZJHM!=''){
		 	 qlrData["xm"] = POWERSOURCE_QLRMC;
			 qlrData["zjhm"] = POWERSOURCE_ZJHM;
			 data.push(qlrData);
		}
	
		var frData = {};
		var POWERSOURCE_FRDB = $("#"+id).find("[name='POWERSOURCE_FRDB']").val();
		var POWERSOURCE_FRZJHM = $("#"+id).find("[name='POWERSOURCE_FRZJHM']").val();
		if(POWERSOURCE_FRDB!='' && POWERSOURCE_FRZJHM!=''){		
			frData["xm"] = POWERSOURCE_FRDB;
			frData["zjhm"] = POWERSOURCE_FRZJHM;
			data.push(frData);
		}
		var dlrData = {};
		var POWERSOURCE_DLRXM = $("#"+id).find("[name='POWERSOURCE_DLRXM']").val();
		var POWERSOURCE_DLRZJHM = $("#"+id).find("[name='POWERSOURCE_DLRZJHM']").val();
		if(POWERSOURCE_DLRXM!='' && POWERSOURCE_DLRZJHM!=''){		
			dlrData["xm"] = POWERSOURCE_DLRXM;
			dlrData["zjhm"] = POWERSOURCE_DLRZJHM;
			data.push(dlrData);
		}
	}
	var flag = true;
	if(data.length<1){
		flag = false ;
	}
	if(flag){
		var cxlist = JSON.stringify(data);
		$.ajaxSettings.async = false;
		$.post("<%=basePath%>/bdcApplyController.do?checkLimitPerson",{cxlist:cxlist},
			function(responseText, status, xhr) {
				var obj =responseText.rows;
				if(responseText.total>0){
				    var name="";
				    for(var i=0;i<obj.length;i++){
				    	name+=obj[i].XM+"("+obj[i].ZJHM+")、";
				    }
				    name=name.substring(0,name.length-1);
					parent.art.dialog({
						content: "存在涉会/涉黑人员【"+name+"】,不可受理此登记！",
						icon:"warning",
						ok: true
					});
					flag = false;
				}		
	   	}); 
   	}
   	return flag;
}

function FLOW_TempSaveFun(flowSubmitObj){
	getPowerInfoJson();
	getPowerPeopleInfoJson();
	getPowerSourceInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_GYJSZYDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}

//设置抵押权人
function setDYQLName(val){
	var datas = $('#POWERSOURCE_DYQR').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='POWERSOURCE_DYQRZJHM']").val(datas[i].DIC_CODE);
			break ;
		}
	}
}

function setFileNumber(serialNum){
	/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
	var enterprise = '${execution.SQJG_CODE}';
	var idCard = '${execution.SQRSFZ}';
// 	alert(idCard + "," + enterprise);
	var fileNum;
	if (enterprise != ""){
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
	} else {
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
	}
	$("#fileNumber").val(fileNum);
}


function initAutoTable(flowSubmitObj){
	var powerinfoJson = flowSubmitObj.busRecord.POWERINFO_JSON;
	if(null != powerinfoJson && '' != powerinfoJson){
		var powerinfos = eval(powerinfoJson);
		for(var i=0;i<powerinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_1");
			}else{
				addPowerInfo();
				FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_"+(i+1));
			}
		}
	}
	var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
	if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson){
		var powerpeopleinfos = eval(powerpeopleinfoJson);
		for(var i=0;i<powerpeopleinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_1");
			}else{
				addPowerPeopleInfo();
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_"+(i+1));
			}
		}
	}
	var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if(null != powersourceinfoJson && '' != powersourceinfoJson){
		var powersourceinfos = eval(powersourceinfoJson);
		for(var i=0;i<powersourceinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_1");
			}else{
				addPowerSourceInfo();
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_"+(i+1));
			}
		}
	}	
}

/**=================权利信息开始================================*/
function changePower(val){
	if(val=="0"){
	   $("input[name='POWERPEOPLEPRO']").val("100");
	}else if(val="1"){
		 $("input[name='POWERPEOPLEPRO']").val("共同共有");
	}else{
       //$("input[name='POWERPEOPLEPRO']").attr("disabled","disabled");
       $("input[name='POWERPEOPLEPRO']").val("");
	}
}
var powerInfoSn = 1;
function addPowerInfo(){
	powerInfoSn = powerInfoSn+1;
	var table = document.getElementById("powerInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerInfo('");
	if(powerInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+21);
	}else{
		var replacestr = trHtml.substring(findex,findex+20);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerInfo('"+powerInfoSn+"')");
	trHtml = "<tr id=\"powerInfo_"+powerInfoSn+"\">"+trHtml+"</tr>";
	$("#powerInfo").append(trHtml);
}

function deletePowerInfo(idSn){
	var table = document.getElementById("powerInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerInfo_"+idSn).remove();
}

function getPowerInfoJson(){
	var datas = [];
	var trs = $("#powerInfo tr[id*='powerInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权利信息结束================================*/
/**=================权利人信息开始================================*/
var powerPeopleInfoSn = 1;
function addPowerPeopleInfo(){
	powerPeopleInfoSn = powerPeopleInfoSn+1;
	var table = document.getElementById("powerPeopleInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerPeopleInfo('");
	if(powerPeopleInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerPeopleInfo('"+powerPeopleInfoSn+"')");
	trHtml = "<tr id=\"powerPeopleInfo_"+powerPeopleInfoSn+"\">"+trHtml+"</tr>";
	$("#powerPeopleInfo").append(trHtml);
}

function deletePowerPeopleInfo(idSn){
	var table = document.getElementById("powerPeopleInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerPeopleInfo_"+idSn).remove();
}

function getPowerPeopleInfoJson(){
	var datas = [];
	var trs = $("#powerPeopleInfo tr[id*='powerPeopleInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权利人信息开始================================*/
/**=================权属来源信息开始================================*/
var powerSourceInfoSn = 1;
function addPowerSourceInfo(){
	powerSourceInfoSn = powerSourceInfoSn+1;
	var table = document.getElementById("powerSourceInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerSourceInfo('");
	if(powerSourceInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerSourceInfo('"+powerSourceInfoSn+"')");
	trHtml = "<tr id=\"powerSourceInfo_"+powerSourceInfoSn+"\">"+trHtml+"</tr>";
	$("#powerSourceInfo").append(trHtml);
}

function deletePowerSourceInfo(idSn){
	var table = document.getElementById("powerSourceInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权属来源信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerSourceInfo_"+idSn).remove();
}

function getPowerSourceInfoJson(){
	var datas = [];
	var trs = $("#powerSourceInfo tr[id*='powerSourceInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/


//------------------------------------身份身份证读取开始---------------------------------------------------
		function MyGetData()//OCX读卡成功后的回调函数
		{	
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>		
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
		}

		function MyClearData()//OCX读卡失败后的回调函数
		{
	        alert("未能有效识别身份证，请重新读卡！");
			$("input[name='POWERPEOPLENAME']").val("");   
			$("input[name='POWERPEOPLEIDNUM']").val("");  
		}

		function MyGetErrMsg()//OCX读卡消息回调函数
		{
// 			Status.value = GT2ICROCX.ErrMsg;	   
		}

		function PowerPeopleRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
				$("#"+powerPeopleInfoID+" [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		function PowLegalRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWLEGALNAME']").val(GT2ICROCX.Name);
				$("#"+powerPeopleInfoID+" [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		function PowAgentRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWAGENTNAME']").val(GT2ICROCX.Name);
				$("#"+powerPeopleInfoID+" [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		
		function PowFRDHRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWERSOURCE_FRDB']").val(GT2ICROCX.Name);
				$("#"+powerPeopleInfoID+" [name='POWERSOURCE_FRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		
		function PowDLRRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWERSOURCE_DLRXM']").val(GT2ICROCX.Name);
				$("#"+powerPeopleInfoID+" [name='POWERSOURCE_DLRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------

//不动产抵押档案查询
function showSelectBdcdydacx(){
	var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();
	
	/* $.dialog.open("bdcApplyController.do?bdcDyInfoSelector&allowCount=1", {
		title : "不动产抵押档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var dyinfo = {
					"POWERSOURCE_DYQR":bdcdydacxInfo[0].DYQR,
					"POWERSOURCE_DBSE":bdcdydacxInfo[0].BDBZZQSE,
					//"POWERSOURCE_LXQX":bdcdydacxInfo[0].QLJSSJ+"("+bdcdydacxInfo[0].QLQSSJ+")",
					"POWERSOURCE_LXQX":bdcdydacxInfo[0].QLJSSJ,
					"POWERSOURCE_DYFW":bdcdydacxInfo[0].ZJJZWDYFW,
					"DY_BDCDYH":bdcdydacxInfo[0].BDCDYH,
					"DY_YWH":bdcdydacxInfo[0].YWH,
					"DY_DYCODE":bdcdydacxInfo[0].DYCODE,
					"DY_GLH":bdcdydacxInfo[0].GLH,
					"POWERSOURCE_DYQRZJLX":bdcdydacxInfo[0].ZJLB,
					"POWERSOURCE_DYQRZJHM":bdcdydacxInfo[0].ZJHM,
					"POWERSOURCE_QLQSSJ":bdcdydacxInfo[0].QLQSSJ,
					"POWERSOURCE_QLJSSJ":bdcdydacxInfo[0].QLJSSJ
				};				
				FlowUtil.initFormFieldValue(dyinfo,"powerDYInfo_1");
				
				//回填抵押权人
				$('#POWERSOURCE_DYQR').combobox('setValue',bdcdydacxInfo[0].DYQR);
			 /* 	var rows = $('#POWERSOURCE_DYQR').combobox('getData');
				if(rows){
					for(var i=0;i<rows.length;i++){
						var name = rows[i].DIC_NAME;
						if(name == bdcdydacxInfo[0].DYQR.trim()){
							$('#POWERSOURCE_DYQR').combobox('setValue',name);
							//$("input[name='POWERSOURCE_DYQRZJHM']").val(rows[i].DIC_CODE);
							break;
						}
					}
				} */
				
				
				/* $("#POWERSOURCE_DYQR option").each(function(){
					var text = $(this).text();
					if(text == bdcdydacxInfo[0].DYQR.trim()){
						$(this).attr("selected",true);
					}
				});					
				art.dialog.removeData("bdcdydacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条抵押信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false); 
	 */	
	
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcygdacxInfo && bdcygdacxInfo.length == 1){
				var dyinfo = {
					"POWERSOURCE_DYQR":bdcygdacxInfo[0].QLR,
					"POWERSOURCE_DBSE":bdcygdacxInfo[0].QDJG,
					"DY_BDCDYH":bdcygdacxInfo[0].BDCDYH,
					"DY_YWH":bdcygdacxInfo[0].YWH,
					"DY_GLH":bdcygdacxInfo[0].GLH,
					"POWERSOURCE_DYQRZJLX":bdcygdacxInfo[0].QLRZJZL,
					"POWERSOURCE_DYQRZJHM":bdcygdacxInfo[0].QLRZJH,
					"POWERSOURCE_QLQSSJ":bdcygdacxInfo[0].QSSJ,
					"POWERSOURCE_QLJSSJ":bdcygdacxInfo[0].JSSJ,
					"POWERSOURCE_DYR":bdcygdacxInfo[0].YWR,
					"POWERSOURCE_DYRZJLX":bdcygdacxInfo[0].YWRZJZL,
					"POWERSOURCE_DYRZJHM":bdcygdacxInfo[0].YWRZJH
				};				
				FlowUtil.initFormFieldValue(dyinfo,"powerDYInfo_1");
				
				//回填抵押权人
				$('#POWERSOURCE_DYQR').combobox('setValue',bdcygdacxInfo[0].QLR);
			 /* 	var rows = $('#POWERSOURCE_DYQR').combobox('getData');
				if(rows){
					for(var i=0;i<rows.length;i++){
						var name = rows[i].DIC_NAME;
						if(name == bdcygdacxInfo[0].DYQR.trim()){
							$('#POWERSOURCE_DYQR').combobox('setValue',name);
							//$("input[name='POWERSOURCE_DYQRZJHM']").val(rows[i].DIC_CODE);
							break;
						}
					}
				} */
				
				
				/* $("#POWERSOURCE_DYQR option").each(function(){
					var text = $(this).text();
					if(text == bdcygdacxInfo[0].DYQR.trim()){
						$(this).attr("selected",true);
					}
				}); */						
				art.dialog.removeData("bdcygdacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条预告信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
};

//房地产合同备案查询
function showSelectFdchtbacx(){
	$.dialog.open("bdcApplyController.do?fdchtbaxxcxSelector&allowCount=1", {
		title : "房地产合同备案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
		}	
	}, false);
}

//不动产档案信息查询
function showSelectBdcdaxxcx(type){	
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if(bdcdaxxcxInfo && bdcdaxxcxInfo.length==1){  
					var info = {};
					info['power_effect_time']=DateConvertTimeFun(Trim(bdcdaxxcxInfo[0].QLQSSJ));
					info['power_close_time']=DateConvertTimeFun(Trim(bdcdaxxcxInfo[0].QLJSSJ));
					info['power_close_time2']=DateConvertTimeFun(Trim(bdcdaxxcxInfo[0].QLJSSJ1));
					info['power_close_time3']=DateConvertTimeFun(Trim(bdcdaxxcxInfo[0].QLJSSJ2));
					info['power_used_for']=Trim(bdcdaxxcxInfo[0].TDYTSM);	
					info['ghytsm']=Trim(bdcdaxxcxInfo[0].GHYTSM);
					info['power_right_type']=Trim(bdcdaxxcxInfo[0].QLXZ);
					info['area']=Trim(bdcdaxxcxInfo[0].ZDMJ);
					info['jzarea']=Trim(bdcdaxxcxInfo[0].JZMJ);
					info['power_common_way']=Trim(bdcdaxxcxInfo[0].GYFS);
					info['POWERSOURCE_QLRMC']=bdcdaxxcxInfo[0].QLRMC;
					info['POWERSOURCE_CQZT']=Trim(bdcdaxxcxInfo[0].CQZT);
					info['POWERSOURCE_ZJLX']=Trim(bdcdaxxcxInfo[0].ZJLX);
					info['POWERSOURCE_ZJHM']=Trim(bdcdaxxcxInfo[0].ZJHM);
					info['POWERSOURCE_QSWH']=Trim(bdcdaxxcxInfo[0].BDCQZH);
					info['POWERSOURCE_QSLYLX']=3;
					//接口回填内容
					info['BDCDYH']=Trim(bdcdaxxcxInfo[0].BDCDYH);
					info['ZDDM']=Trim(bdcdaxxcxInfo[0].ZDDM);
					info['FWBM']=Trim(bdcdaxxcxInfo[0].FWBM);
					info['YWH']=Trim(bdcdaxxcxInfo[0].YWH);
					info['ZXYWH']=Trim(bdcdaxxcxInfo[0].ZXYWH);
					info['GLH']=Trim(bdcdaxxcxInfo[0].GLH);
					
					//判断房屋产权状态
				    if(bdcdaxxcxInfo[0].CQZT.indexOf("查封")!=-1){
				    	parent.art.dialog({
							lock: true,
							content: "请注意，该不动产单元号为查封状态，不可受理此登记。",
							icon:"warning",
							ok: true
						});
				    }else if(bdcdaxxcxInfo[0].CQZT.indexOf("抵押")!=-1){
				        parent.art.dialog.confirm("请注意，该不动产单元号为抵押状态，是否继续办理业务?", function() {
					      	//受理信息中-不动产单元号与坐落回填
							$("input[name='ESTATE_NUM']").val(Trim(bdcdaxxcxInfo[0].BDCDYH));
							$("input[name='LOCATED']").val(Trim(bdcdaxxcxInfo[0].FDZL));
							
							if(type == '1'){
								FlowUtil.initFormFieldValue(info,"powerInfo_1");
								//回填用途
								/* if(info.power_used_for){
									var datas = $("#power_used_for").combobox("getData");
									for(var i=0;i<datas.length;i++){
										if(datas[i].text == info.power_used_for){
											$("#power_used_for").combobox("setValue",datas[i].value);
											break;
										}
									}
								} */
								//土地权利性质
								if(info.power_right_type){
									var datas = $("#power_right_type").combobox("getData");
									for(var i=0;i<datas.length;i++){
										if(datas[i].text == info.power_right_type){
											$("#power_right_type").combobox("setValue",datas[i].value);
											break;
										}
									}
								}
								//共有方式
								if(info.power_common_way){
									$("#power_common_way option").each(function(){
										var text = $(this).text();
										if(text == info.power_common_way){
											$(this).attr("selected",true);
										}
									});
								}
								if(powerSourceInfoSn==1){
									FlowUtil.initFormFieldValue(info,"powerSourceInfo_1");
									powerSourceInfoSn++;
								}else{
									addPowerSourceInfo();
									FlowUtil.initFormFieldValue(info,"powerSourceInfo_"+(powerSourceInfoSn));
								}
							}else if(type == '2'){
								if(powerSourceInfoSn==1){
									FlowUtil.initFormFieldValue(info,"powerSourceInfo_1");
									powerSourceInfoSn++;
								}else{
									addPowerSourceInfo();
									FlowUtil.initFormFieldValue(info,"powerSourceInfo_"+(powerSourceInfoSn));
								}
							}
				       }); 
				    }else{
				    	//受理信息中-不动产单元号与坐落回填
						$("input[name='ESTATE_NUM']").val(Trim(bdcdaxxcxInfo[0].BDCDYH));
						$("input[name='LOCATED']").val(Trim(bdcdaxxcxInfo[0].FDZL));
						
						if(type == '1'){
							FlowUtil.initFormFieldValue(info,"powerInfo_1");
							//回填用途
							/* if(info.power_used_for){
								var datas = $("#power_used_for").combobox("getData");
								for(var i=0;i<datas.length;i++){
									if(datas[i].text == info.power_used_for){
										$("#power_used_for").combobox("setValue",datas[i].value);
										break;
									}
								}
							} */
							//土地权利性质
							if(info.power_right_type){
								var datas = $("#power_right_type").combobox("getData");
								for(var i=0;i<datas.length;i++){
									if(datas[i].text == info.power_right_type){
										$("#power_right_type").combobox("setValue",datas[i].value);
										break;
									}
								}
							}
							//共有方式
							if(info.power_common_way){
								$("#power_common_way option").each(function(){
									var text = $(this).text();
									if(text == info.power_common_way){
										$(this).attr("selected",true);
									}
								});
							}
							if(powerSourceInfoSn==1){
								FlowUtil.initFormFieldValue(info,"powerSourceInfo_1");
								powerSourceInfoSn++;
							}else{
								addPowerSourceInfo();
								FlowUtil.initFormFieldValue(info,"powerSourceInfo_"+(powerSourceInfoSn));
							}
						}else if(type == '2'){
							if(powerSourceInfoSn==1){
								FlowUtil.initFormFieldValue(info,"powerSourceInfo_1");
								powerSourceInfoSn++;
							}else{
								addPowerSourceInfo();
								FlowUtil.initFormFieldValue(info,"powerSourceInfo_"+(powerSourceInfoSn));
							}
						}
				    }
				art.dialog.removeData("bdcygdacxInfo");
			}else{
		    	parent.art.dialog({
				content: "请根据查询选择一条不动产档案信息。",
				icon:"warning",
				ok: true
				})
			}
		}
	}, false);
};

/**返回时间格式YYYY-MM-DD*/
function DateConvertTimeFun(str){
	var time = "";
	if(str){
		var result=str.match(/\d+/g); 
		var year = result[0];
		
		var day = result[2];
		time = year +"-";
		if(result[1]){
			var month = result[1];
			if(parseInt(month) > 9){
				time = time + month +"-";
			}else {
				time = time + "0"+ month + "-";
			}
		}
		if(result[2]){
			var day = result[2];
			if(parseInt(day) > 9){
				time = time + day;
			}else {
				time = time + "0"+ day;
			}
		}
	}
	return time;
}

function Trim(str){ 
  if(str){
  	 return str.replace(/(^\s*)|(\s*$)/g, ""); 
  }else{
  	return str;
  }
}

function queryTypeFn(){
	var val = $('input[name="ZYDJ_TYPE"]:checked').val(); 
	if(val == '3' || val == '5'){
		$("#powerDY_div").attr("style","");
	}else{
        $("#powerDY_div").attr("style","display:none;"); 
	}
}

</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData()
</SCRIPT>
    <style>
		.sel{
			border:solid 1px red;
		}
    </style>
</head>

<body>
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDC_GYJSZYDJ_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId"
			value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
			name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
			type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
			type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME"
			value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
			value="${serviceItem.SXLX}" /> <input type="hidden"
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
			
			
		<input type="hidden" name="POWERINFO_JSON" />
		<input type="hidden" name="POWERPEOPLEINFO_JSON" />
		<input type="hidden" name="POWERSOURCEINFO_JSON" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td > 所属部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		<%--开始引入受理信息--%>
		<jsp:include page="./estate/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始引入权利信息--%>
		<jsp:include page="./estate/T_ESTATE_ZYDJ_RIGHTINFO.jsp" />
		<%--结束引入权利信息--%>
		
		<%--开始引入权属来源信息及抵押情况--%>
		<jsp:include page="./estate/T_ESTATE_ZYDJ_OBLIGEEINFO.jsp" />
		<%--结束引入权属来源信息及抵押情况--%>

	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
