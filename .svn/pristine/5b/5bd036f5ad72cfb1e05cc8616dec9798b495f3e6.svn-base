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
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,autocomplete"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>  
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
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_YGSPFDYQYGDJ_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_YGSPFDYQYGDJ_FORM");
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
				$("#T_BDC_YGSPFDYQYGDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDC_YGSPFDYQYGDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				if($("input[name='SBMC']").val()){
				}else{
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
				}
			}else if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
				$("#userinfo_div input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#userinfo_div select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#baseinfo input").each(function(index){
					$(this).attr("disabled","disabled");
				});
			}
			/* var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
			//设置文件是否合规
			if(isAuditPass == "-1"){
				$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
			} */
		}else{
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
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
	// AppUtil.initNetUploadMaters({
		// busTableName:"T_BDC_YGSPFDYQYGDJ"
	// });
	
});


function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_YGSPFDYQYGDJ_FORM").validationEngine("validate");
	 if(validateResult){
         var isAuditPass = $('#isAuditPass').val();
	     if(isAuditPass == "0"){
	     	 parent.art.dialog({
	     	    lock: true,
				content : "请注意，该不动产单元号不为预售预告，不可办理该业务。",
				icon : "warning",
				ok : true
			 });
			 return null;
	     }else{
	     	 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BDC_YGSPFDYQYGDJ_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 //console.dir(flowSubmitObj);	
				return flowSubmitObj;
			 }else{
				 return null;
			 }
	     }			 
	 }else{
		 return null;
	 }
}
function isPowerPeople(){
     var powerPeopleInfoSn=$("input[name='POWERPEOPLEINFO_JSON']").val();
     var sqrmc=$("input[name='SQRMC']").val();
     if(powerPeopleInfoSn.indexOf(sqrmc)>0){
         return true;
	 }else{
         return false;
	 }
}
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_YGSPFDYQYGDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
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

// -------------------抵押权人信息上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='YSDYQYG_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#YSDYQYG_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#YSDYQYG_fileListDiv").html(newhtml);
	    	}
    });
         
	/* AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_DYQSCDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "YSDYQYG_FILE_BTN",
		singleFileDivId : "YSDYQYG_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='YSDYQYG_FILE_URL']").val();
				var fileid=$("input[name='YSDYQYG_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='YSDYQYG_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='YSDYQYG_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='YSDYQYG_FILE_URL']").val(resultJson.filePath);
					$("input[name='YSDYQYG_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#YSDYQYG_FILE_DIV").children("a").eq(1).html());
				//$("#YSDYQYG_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YSDYQYGdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#YSDYQYG_fileListDiv").append(spanHtml); 
		}
	});  */
});

function openSlxxFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDC_DYQSCDJ', {
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
					var fileurl=$("input[name='YSDYQYG_FILE_URL']").val();
					var fileid=$("input[name='YSDYQYG_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='YSDYQYG_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='YSDYQYG_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='YSDYQYG_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='YSDYQYG_FILE_ID']").val(uploadedFileInfo.fileId);
					}					
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YSDYQYGdelUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#YSDYQYG_fileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
    function YSDYQYGchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	YSDYQYGbindScanCtrl();
        }else if(gpytype=="2"){
        	YSDYQYGbindScanCtrlLT();
        }else if(gpytype=="3"){
        	YSDYQYGbindScanCtrlZT();
        }
    }
function YSDYQYGbindScanCtrl(){
	var onlineBusTableName = "T_BDC_DYQSCDJ";
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
				YSDYQYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function YSDYQYGbindScanCtrlLT(){
	var onlineBusTableName = "T_BDC_DYQSCDJ";
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
				YSDYQYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function YSDYQYGbindScanCtrlZT(){
	var onlineBusTableName = "T_BDC_DYQSCDJ";
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
				YSDYQYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function YSDYQYGinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='YSDYQYG_FILE_URL']").val();
		var fileid=$("input[name='YSDYQYG_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='YSDYQYG_FILE_URL']").val(fileurl+";"+resultJson[i].data.filePath);
			$("input[name='YSDYQYG_FILE_ID']").val(fileid+";"+resultJson[i].data.fileId);
		}else{
			$("input[name='YSDYQYG_FILE_URL']").val(resultJson[i].data.filePath);
			$("input[name='YSDYQYG_FILE_ID']").val(resultJson[i].data.fileId);
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
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YSDYQYGdelUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
        $("#YSDYQYG_fileListDiv").append(spanHtml); 
	}
}
/* function YSDYQYGdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='YSDYQYG_FILE_URL']").val();
	var fileid=$("input[name='YSDYQYG_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='YSDYQYG_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='YSDYQYG_FILE_ID']").val(arrayIds.join(";"));
} */
function YSDYQYGdelUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='YSDYQYG_FILE_URL']").val();
		var fileid=$("input[name='YSDYQYG_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='YSDYQYG_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='YSDYQYG_FILE_ID']").val(arrayIds.join(";"));
	});
}
// -------------------抵押权人信息上传及高拍仪 结束-------------------------------------------------------------
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

		function QLR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				//$("input[name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
				$("input[name='QLR_ZJNO']").val(GT2ICROCX.CardNo)
			}
		} ;
		function DLR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='DLR_MC']").val(GT2ICROCX.Name)
				$("input[name='DLR_ZJNO']").val(GT2ICROCX.CardNo)
			}
		} ;
		
		function YWR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='YWR_MC']").val(GT2ICROCX.Name)
				$("input[name='YWR_ZJNO']").val(GT2ICROCX.CardNo)
			}
		};
		
		function DLR2_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='DLR2_MC']").val(GT2ICROCX.Name)
				$("input[name='DLR2_ZJNO']").val(GT2ICROCX.CardNo)
			}
		}; 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------

function setQLRName(val){
	var datas = $('#QLR_MC').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='QLR_ZJNO']").val(datas[i].DIC_CODE);
			//$("input[name='QLR_ZJNO']").val(datas[i].DIC_DESC);//法人代表
			break ;
		}
	}
	$("input[name='QLR_LABEL']").val(val); 
	/* var text = $("select[name='QLR_MC']").find("option:selected").text();
	$("input[name='QLR_ZJNO']").val(val);
	$("input[name='QLR_LABEL']").val(text); */
}

//不动产预告登记查询
function showSelectBdcYgdacx(){
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var dyinfo = {
					"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
					"LOCATED":bdcdydacxInfo[0].BDCZL,
					"YWR_MC":bdcdydacxInfo[0].QLR,
					"YWR_ZJLX":bdcdydacxInfo[0].QLRZJZL,
					"YWR_ZJNO":bdcdydacxInfo[0].QLRZJH,
				/* 	"DBSE":bdcdydacxInfo[0].QDJG, */
					"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
					"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
					"DJXX_QLR":bdcdydacxInfo[0].QLR,
					"DJXX_JZAREA":bdcdydacxInfo[0].JZMJ,
					"DJXX_ZL":bdcdydacxInfo[0].BDCZL,
					"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
					"ZW_END":bdcdydacxInfo[0].JSSJ,
					"FW_ADDR":bdcdydacxInfo[0].BDCZL,
					"HT_LX":bdcdydacxInfo[0].HTLX,
					"BDCDYH":bdcdydacxInfo[0].BDCDYH,
					"YWH":bdcdydacxInfo[0].YWH,
					"GLH":bdcdydacxInfo[0].GLH,
					"APPLICANT_UNIT":bdcdydacxInfo[0].QLR,
					"HT_NO":bdcdydacxInfo[0].HTH,
					"HT_LX":bdcdydacxInfo[0].HTLX
				};	
				var result = checkFwCqzt(dyinfo.ESTATE_NUM);
				if(result.isPass){
					FlowUtil.initFormFieldValue(dyinfo,"T_BDC_YGSPFDYQYGDJ_FORM");	
					//申报对象信息回填
					$('input[name="SQRMC"]').val(bdcdydacxInfo[0].QLR);//申请人
					$('#SQRZJLX').find("option").filter(function(index){
						return bdcdydacxInfo[0].QLRZJZL===$(this).text();
					}).attr("selected","selected");//证件类别
					$('input[name="SQRSFZ"]').val(bdcdydacxInfo[0].QLRZJH);//证件号码	
				}else{
					parent.art.dialog({
						lock: true,
						content: result.msg,
						icon:"warning",
						ok: true
					});
				}
				art.dialog.removeData("bdcygdacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条预告登记信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
};

//查看访问状态
function checkFwCqzt(dyh){
	var result = {
		"isPass":false,
		"type":"0",
		"msg":"系统异常"
	};
	$.ajax({
        type: "POST",
        url: "bdcApplyController/bdcFwztCheck.do?bdcdyh="+dyh,
        async: false, //采用同步方式进行数据判断
        cache: false,
        success: function (responseText) {
        	var resultJson = $.parseJSON(responseText);
        	if(resultJson.success){
				var list = resultJson.data;			
				if(list != null && list.length > 0){
					//CQZT
					if(list.length == 1 && list[0].CQZT != null){
						if("预售预告" == list[0].CQZT.trim()||"正常" == list[0].CQZT.trim()){
							$("#isAuditPass").val("1");						
							result = {
								"isPass":true
							};
						}else{
							$("#isAuditPass").val("0");
							result = {
								"isPass":false,
								"msg":"请注意，该不动产单元号不为预售预告，不可办理该业务。",
								"type":"0"
							};							
						}
					}
			   }else{
				   result = {
						"isPass":false,
						"msg":"暂查无该房屋状态信息,无法查验该房屋状态,不可继续办理业务。",
						"type":"0"
					};
			   }
		   }else{
			   result = {
					"isPass":false,
					"msg":resultJson.msg,
					"type":"0"
				}; 
		   }
        }
    });
	return result;
}

//检验是不动产单元号对应的房屋状态
function checkIsAuditPass(){
	var isPass = false;
	var reg = /^(\d{6})([a-zA-Z0-9]{6})([a-zA-Z]{2})([a-zA-Z0-9]{14})$/;
	var val = $("#ESTATE_NUM").val();
	if(val.match(reg)){
		AppUtil.ajaxProgress({
			url : "bdcApplyController/bdcFwztCheck.do",
			params : {
				"bdcdyh":val
			},
			callback : function(resultJson) {
				if(resultJson.success){
					var list = resultJson.data;			
					if(list != null && list.length > 0){
						if(list.length == 1 && list[0].CQZT != null){
							if("预售预告" == list[0].CQZT.trim()){
								$("#isAuditPass").val("1");						
								isPass = true;
							}else{
								$("#isAuditPass").val("0");
								art.dialog({
									lock: true,
									content: "请注意，该不动产单元号不为预售预告，不可办理该业务。",
									icon:"warning",
									ok: true
								});
							}
						}
				   }else{
						art.dialog({
							lock: true,
							content: "暂查无该房屋状态信息,无法查验该房屋状态，不可继续办理业务。",
							icon:"warning",
							ok: true
						});
				   }
			   }else{
					art.dialog({
						lock: true,
						content: resultJson.msg,
						icon:"warning",
						ok: true
					});
			   }
			}
		});
	}else{
		$("#isAuditPass").val("0");
	}
	return isPass;
}


		
function newDicInfoWin(typeCode,combId){
	$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
		title : "新增",
        width:"600px",
        lock: true,
        resize:false,
        height:"180px",
        close: function(){
			$("#"+combId).combobox("reload");
		}
	}, false);
}

function removeDicInfo(combId){
	var datas = $("#"+combId).combobox("getData");
	var val = $("#"+combId).combobox("getValue");
	var id = "";
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME==val){
			id = datas[i].DIC_ID;
			break;
		}
	}
	art.dialog.confirm("您确定要删除该记录吗?", function() {
		var layload = layer.load('正在提交请求中…');
		$.post("dictionaryController.do?multiDel",{
			   selectColNames:id
		    }, function(responseText, status, xhr) {
		    	layer.close(layload);
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					$("#"+combId).combobox("reload");
					$("#"+combId).combobox("setValue","");
				}else{
					$("#"+combId).combobox("reload");
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
		});
	});
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
		.tab_text {
		    width: 250px;
		    height: 24px;
		    line-height: 24px;
		    /* padding: 0 5px; */
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}		
		.tab_text1 {
		    width: 50%;
		    height: 25px;
		    line-height: 25px;
		    padding: 0 5px;
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
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
	<form id="T_BDC_YGSPFDYQYGDJ_FORM" method="post">
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
			
		<input type="hidden" name="ISAUDITPASS" id="isAuditPass" value="0"/>
		
		<input type="hidden" name="BDCDYH"/>
		<input type="hidden" name="YWH"/>
		<input type="hidden" name="GLH"/>
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
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		<!-- 受理信息 -->
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
				<td><input type="text" class="tab_text " disabled="disabled" 
					name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
				<td><input type="text" class="tab_text " disabled="disabled"
						   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>不动产单元号：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required,custom[estateNum]]" style="width: 450px" onblur="checkIsAuditPass();"
						   name="ESTATE_NUM" id="ESTATE_NUM" value="${busRecord.ESTATE_NUM }"/>&nbsp;&nbsp;
						   <input type="button" value="不动产预告登记查询" class="eve-button"
                                                       onclick="showSelectBdcYgdacx();"/> </td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
				<td><input type="text" class="tab_text validate[required]"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
				<td style="width:430px">
					<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="1">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>坐落：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="LOCATED" value="${busRecord.LOCATED}" style="width: 72%;"  />
				</td>
			</tr>
			<tr>
				<td class="tab_width">通知人姓名：</td>
				<td><input type="text" class="tab_text "
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<td class="tab_width">通知人电话：</td>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
				<td class="tab_width">备注：</td>
				<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
				</td>
			</tr>
		</table>
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">预售抵押权预告</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
				<td colspan="3">
					<%-- <eve:eveselect clazz="tab_text1 validate[required]" dataParams="CYYHHMC"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setQLRName(this.value);"
						defaultEmptyText="选择权力人" name="QLR_MC" id="QLR_MC" >
						</eve:eveselect> --%>
				    <input type="hidden" name="QLR_LABEL"/> 
				    <input class="easyui-combobox tab_text1 validate[required]" name="QLR_MC" id="QLR_MC" 
						data-options="
						    prompt : '请输入或者选择权利人',
							url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
							method: 'get',
							valueField : 'DIC_NAME',
							textField : 'DIC_NAME',
							filter : function(q, row) {
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) > -1; 
							},
							onSelect:function(){
								var value = $('#QLR_MC').combobox('getValue');
								setQLRName(value);
							}									
				"/><span style="width:25px;display:inline-block;text-align:right;">
						<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="新建权利人">
				  </span>
				  <span style="width:25px;display:inline-block;text-align:right;">
						<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="删除选中的权利人">
				  </span>
				</td>				
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="QLR_ZJLX" id="QLR_ZJLX" value="统一社会信用代码"> 
						</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="128" id="QLR_ZJNO" style="float: left;"
					name="QLR_ZJNO"  /><!-- <a href="javascript:void(0);" onclick="QLR_Read(this);" class="eflowbutton">身份证读卡</a> -->
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>代理人（领证人）：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" 
					name="DLR_MC"/></td>				
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="DLR_ZJLX" id="DLR_ZJLX" value="SF">
						</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="128" id="DLR_ZJNO" style="float: left;"
					name="DLR_ZJNO"  /><a href="javascript:void(0);" onclick="DLR_Read(this);" class="eflowbutton">身份证读卡</a>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>义务人：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" 
					name="YWR_MC"/></td>		
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" value="身份证"
						defaultEmptyText="选择证件类型" name="YWR_ZJLX" id="YWR_ZJLX" >
						</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="128" id="YWR_ZJNO" style="float: left;"
					name="YWR_ZJNO"  /><a href="javascript:void(0);" onclick="YWR_Read(this);" class="eflowbutton">身份证读卡</a>
				</td>
			</tr>
			<tr>
				<td class="tab_width">代理人：</td>
				<td colspan="3"><input type="text" class="tab_text" 
					name="DLR2_MC"/></td>			
			</tr>
			<tr>
				<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="DLR2_ZJLX" id="DLR2_ZJLX" value="SF">
						</eve:eveselect>
				</td>
				<td class="tab_width">证件号码：</td>
				<td>
				  <input type="text" class="tab_text validate[]" maxlength="128" id="DLR2_ZJNO" style="float: left;"
					name="DLR2_ZJNO"  /><a href="javascript:void(0);" onclick="DLR2_Read(this);" class="eflowbutton">身份证读卡</a>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>预售合同号：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="30" id="HT_NO" style="float: left;"
					name="HT_NO"  />
				</td>
				<td class="tab_width"><font class="tab_color">*</font>合同类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="htlx"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择合同类型" name="HT_LX" id="HT_LX" >
						</eve:eveselect>
				</td>				
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>被担保主债权数额：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="30" id="DBSE" style="float: left;"
					name="DBSE"  />
				</td>
				<td class="tab_width"></td>
				<td></td>				
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>债务起始时间：</td>
				<td>
					<input type="text" id="ZW_BEGIN" name="ZW_BEGIN" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZW_END\')}'})" 
						class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>债务结束时间：</td>
				<td>
					<input type="text" id="ZW_END" name="ZW_END" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZW_BEGIN\')}'})" 
						class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="FW_ADDR" style="width: 72%;"  />
				</td>
			</tr>
			<tr>
				<td class="tab_width">登记原因：</td>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="DJ_REASON" style="width: 72%;"  value="预售商品房抵押权预告登记"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width">附记：</td>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="FJ_INFO" style="width: 72%;"  />
				</td>
			</tr>
			
			<input type="hidden" name="YSDYQYG_FILE_URL" >
			<input type="hidden" name="YSDYQYG_FILE_ID">
			<tr>
				<td  class="tab_width">人像信息：
<!-- 				<font class="dddl_platform_html_requiredFlag">*</font> -->
				</td>
				<td colspan="3">
					<div style="width:100%;display: none;" id="YSDYQYG_FILE_DIV"></div>
					<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
						<a href="javascript:YSDYQYGchooseCtrl()"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
					</div>
					<a href="javascript:void(0);" onclick="openSlxxFileUploadDialog()">
						<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
					</a>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div style="width:100%;" id=YSDYQYG_fileListDiv></div>
				</td>
			</tr>
			
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">预售预告登记信息</th>
			</tr>			
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
					<td>
						<input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_DYH" style="float: left;"
					name="DJXX_DYH"  />
				</td>
				<td class="tab_width">不动产登记证明号：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_CQZH" style="float: left;"
					name="DJXX_CQZH"  />
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
					<td>
						<input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_QLR" style="float: left;"
					name="DJXX_QLR"  />
				</td>
				<td class="tab_width"><font class="tab_color">*</font>建筑面积：</td>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_JZAREA" style="float: left;"
					name="DJXX_JZAREA"  />
				</td>
			</tr>			
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>坐落：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="DJXX_ZL" style="width: 72%;"  />
				</td>
			</tr>
			<tr>
				<td class="tab_width">备注：</td>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="DJXX_BZ"  style="width: 72%;"  />
				</td>
			</tr>
		</table>
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
