<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String dateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
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
 <script type="text/javascript" 
 	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/bdcqlcygspfdyqygdj/js/ygspfdyqygdj.js"></script>
 <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
<script type="text/javascript">

$(function(){
	
	 //默认企业法人
	 //qyclick();
	 //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
	 $("input[name='JBR_NAME']").removeAttr('readonly');
	 $("input[name='JBR_ZJHM']").removeAttr('readonly');

	 /*审批表在开始环节无法打印*/
	$("#SPBDF").attr("onclick","notPrint();")
	$("#SPBSF").attr("onclick","notPrint();")


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
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDCQLC_YGSPFDYQYGDJ_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_YGSPFDYQYGDJ_FORM");
			initAutoTable(flowSubmitObj);
			disabledDbBtn("qrdb");
			if (flowSubmitObj.busRecord.RUN_STATUS && flowSubmitObj.busRecord.RUN_STATUS != 0) {
				/*其它环节审批表可以进行打印*/
				$("#BDC_QZBSM").removeAttr("disabled");
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
					$("#printBtn").show();
				}
				$("#SPBDF").attr("onclick","goPrintTemplate('YGSPFDYQYGDJSPB','3');");
				$("#SPBSF").attr("onclick","goPrintTemplate('YGSPFDYQYGDJSPB','3');");
				
			}

			var BDC_WWSQBH = $("input[name='BDC_WWSQBH']").val()
			if (BDC_WWSQBH && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='网上预审') {
				getBdcWwCl(BDC_WWSQBH);
			}
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				if($("input[name='SBMC']").val()){
				}else{
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
				}

				$("#hqsjww").hide();


				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='登簿'){
					$(".dbxxtr").show();
					/* $("input[name='BDC_BDCDYH']").attr("disabled",false);
					$("input[name='BDC_BDCDYH']").addClass(" validate[required]");
					$("[name='HT_LX']").attr("disabled",false);
					$("[name='DLR_MC']").attr("disabled",false);
					$("[name='DLR_ZJLX']").attr("disabled",false);
					$("[name='DLR_ZJNO']").attr("disabled",false);
					$("[name='DLR2_MC']").attr("disabled",false);
					$("[name='DLR2_ZJLX']").attr("disabled",false);
					$("[name='DLR2_ZJNO']").attr("disabled",false); */
					//期限、贷款额度可编辑、保存按钮开放
					/* $("input[name='ZW_BEGIN']").attr("disabled",false);
					$("input[name='ZW_END']").attr("disabled",false);
					$("input[name='DBSE']").attr("disabled",false);
					$("#saveBtn").show(); */
					
				} else if(flowSubmitObj.busRecord.RUN_STATUS!=0 && (flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='缮证' 
					|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='办结')){
					$(".dbxxtr").show();
					$("#qrdb").hide();//确认登簿按钮
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
			//待受理、受理环节不可修改
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && (flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='待受理' 
				|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='受理') ){
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM textarea").each(function(index){
					$(this).attr("disabled","disabled");
				});
			} 
			//开始、待受理、受理环节可以新增义务人
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='待受理' 
				|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='受理'){
				$("[name='addYwrxx']").show();
			} else{
				$("[name='addYwrxx']").hide();
			}
			
			//流程结束
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.busRecord.RUN_STATUS!=1 ){
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDCQLC_YGSPFDYQYGDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$(".dbxxtr").show();
				$("#qrdb").hide();//确认登簿按钮
			} else{				
				checkIsAuditPass();
			}
		}else{
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
			//预购商品房抵押预告登记模块中，持证类型默认为共同持有，登记原因默认为"预购商品房抵押权预告登记"。
			$("select[name='TAKECARD_TYPE']").val("1");
			$('#DJYY').val("预购商品房抵押权预告登记");
		    
		    //合同类型默认为'预售合同'
		    $('select[name="HT_LX"]').val('预售合同');
		    $('select[name="YTSM"]').val('住宅');
		    $('select[name="FWXZ"]').val('市场化商品房');
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
	 var validateResult =$("#T_BDCQLC_YGSPFDYQYGDJ_FORM").validationEngine("validate");
	 if(validateResult){

		 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
			 /*判断此业务是否已被办理过*/
			 var bdcdyh = $("input[name='ESTATE_NUM']").val();
			 var bdcdyhFlag = AppUtil.verifyBdcdyhExistSubmit(bdcdyh);
			 if (!bdcdyhFlag) {
				 art.dialog({
					 content: "请注意：该不动产单元号已经办理过该业务！",
					 icon:"warning",
					 ok: true
				 });
				 return null;
			 }
		 }

		 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
				var isdbflag = $("input[name='BDC_DBZT']").val();
				if(isdbflag){
					if(isdbflag =="-1"){
						parent.art.dialog({
							content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
							icon : "warning",
							ok : true
						});
						return;
					}else if(isdbflag =="0"){
						var dbjg = $("input[name='BDC_DBJG']").val();
						parent.art.dialog({
							content : "登簿异常！"+dbjg,
							icon : "warning",
							ok : true
						});
						return;
					}
				}else{
					parent.art.dialog({
						content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
						icon : "warning",
						ok : true
					});
					return;
				}
				var  HZ_OPINION_NAME = $("input[name='HZ_OPINION_NAME']").val();
				if(null==HZ_OPINION_NAME || ''==HZ_OPINION_NAME){						
					parent.art.dialog({
						content : "未保存核定意见，请先完成核定意见保存操作，并确认保存成功。",
						icon : "warning",
						ok : true
					});
					return;
				}
		 }
		 getYwrInfoJson();
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDCQLC_YGSPFDYQYGDJ_FORM");
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
	getYwrInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_YGSPFDYQYGDJ_FORM");
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


//------------------权证打印字段控制-------------------------
$(function () {
	$("input[name='BDC_QZBSM']").removeAttr("disabled");

	var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	if (BDC_OPTYPE) {
		if (BDC_OPTYPE == '1') {
			$(".dbxxtr").show();//登簿打印表单
			$("#qrdb").hide();//确认登簿按钮
		}
	} else {
		$("#dyzm").hide();
	}
})
//------------------权证打印字段控制-------------------------


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
});

function openSlxxFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_YGSPFDYQYGDJ', {
		title: "上传(附件)",
		width: "620px",
		height: "320px",
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
	var onlineBusTableName = "T_BDCQLC_YGSPFDYQYGDJ";
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
	var onlineBusTableName = "T_BDCQLC_YGSPFDYQYGDJ";
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
	var onlineBusTableName = "T_BDCQLC_YGSPFDYQYGDJ";
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

		function cardRead(o,name,cardno)//开始读卡
		{  		
 			var id = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				
				$("#"+id+" input[name='"+name+"']").val(GT2ICROCX.Name)
				$("#"+id+" input[name='"+cardno+"']").val(GT2ICROCX.CardNo)
			}
		} 
		
		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------

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
	<form id="T_BDCQLC_YGSPFDYQYGDJ_FORM" method="post">
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

		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		
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
		<%-- <jsp:include page="./applyuserinfo.jsp" /> --%>
		<jsp:include page="./bdcqlc/bdcqlcygspfdyqygdj/applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		
		<%--开始引入预售抵押权预告信息 --%>
		<jsp:include page="./bdcqlc/bdcqlcygspfdyqygdj/T_YGSPFDYQYGDJ_ACCEPTINFO.jsp" />
		<%--结束引入预售抵押权预告信息--%>

		<%--开始引入领证人信息--%>
		<%-- <jsp:include page="./bdcqlc/lzrInfo.jsp" /> --%>
		<%--结束引入领证人信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
				
		
		
		<%--开始引入预售抵押权预告信息 --%>
		<jsp:include page="./bdcqlc/bdcqlcygspfdyqygdj/T_YGSPFDYQYGDJ_YGDYQYG.jsp" />
		<%--结束引入预售抵押权预告信息--%>
		
		<%--不动产EMS模块--%>
		<jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>
		
		<%--开始审批表打印按钮--%>
		<div id="printBtn" name="printBtn" style="display:none;">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="SPBDF"
							>审批表（单方）</a>
						<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="SPBSF"
							>审批表（双方）</a>
					</th>
				</tr>
		    </table>
	    </div>
	    <%--结束审批表打印按钮--%>
		
		<%--开始登记审核意见信息（不动产通用） 只有核准意见--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshHzOpinion.jsp" /> 
		<%--结束登记审核意见信息（不动产通用）--%>
		<br/><br/>
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
