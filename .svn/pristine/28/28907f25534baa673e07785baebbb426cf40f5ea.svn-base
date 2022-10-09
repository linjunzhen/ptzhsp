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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>

 <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcygspfygdj/js/bdcygspfygdj.js"></script> 

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
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_YGSPFYGDJ_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_YGSPFYGDJ_FORM");
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
				$("#T_BDC_YGSPFYGDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDC_YGSPFYGDJ_FORM select").each(function(index){
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
			var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
			//设置文件是否合规
			if(isAuditPass == "-1"){
				$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
			}
		}else{
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
			//预购商品房预告登记模块中，持证类型默认为共同持有，登记原因默认为"预购商品房预告登记"。
			$("select[name='TAKECARD_TYPE']").val("1");
			$('#DJYY').val("预购商品房预告登记");
			//权利人、代理人（领证人）、义务人、代理人证件种类初始化为'身份证'
			$('select[name="MSFZJLB"]').val('身份证');
			$('select[name="LZRZJLB"]').val('身份证');
			$('select[name="ZRFZJLB"]').val('身份证');
		    $('select[name="DLRZJLB"]').val('身份证');
		    
		    //合同类型默认为'预售合同'、规划用途默认为'住宅'、房屋性质默认为'市场化商品房'
		    $('select[name="HTLX"]').val('预售合同');
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
		// busTableName:"T_BDC_YGSPFYGDJ"
	// });
});


// -------------------预购预告上传及高拍仪 开始-------------------------------------------------------------
$(function() {
    /**
	//初始化材料列表
	**/
	var fileids=$("input[name='YGYG_FILE_ID']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    	if(resultJson!="websessiontimeout"){
	    		$("#YGYG_fileListDiv").html("");
	    		var newhtml = "";
	    		var list=resultJson.rows;
	    		for(var i=0; i<list.length; i++){
	    		 	newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 	newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 	newhtml+=list[i].FILE_NAME+'</a>';
	    		 	newhtml+='</p>';
	    		 } 
	    		 $("#YGYG_fileListDiv").html(newhtml);
	    	}
    });
         
	/* AppUtilNew.initUploadControl({
		file_types : "*.png;*.jpg;*.bmp;*.tif;*.tiff;*.jpeg;*.gif;*.pdf;*.edc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "hflow",
		busTableName : "T_BDC_DYQSCDJ",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "YGYG_FILE_BTN",
		singleFileDivId : "YGYG_FILE_DIV",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			var fileurl=$("input[name='YGYG_FILE_URL']").val();
				var fileid=$("input[name='YGYG_FILE_ID']").val();
				if(fileurl!=""&&fileurl!=null){
					$("input[name='YGYG_FILE_URL']").val(fileurl+";"+resultJson.filePath);
					$("input[name='YGYG_FILE_ID']").val(fileid+";"+resultJson.fileId);
				}else{
					$("input[name='YGYG_FILE_URL']").val(resultJson.filePath);
					$("input[name='YGYG_FILE_ID']").val(resultJson.fileId);
				}
				
				//alert($("#YGYG_FILE_DIV").children("a").eq(1).html());
				//$("#YGYG_FILE_DIV a").eq(1).hide();//$('div').find('a');$(this).children('td').eq(1).addClass('red');
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
	            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YGYGdelUploadFile('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
	            $("#YGYG_fileListDiv").append(spanHtml); 
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
					var fileurl=$("input[name='YGYG_FILE_URL']").val();
					var fileid=$("input[name='YGYG_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='YGYG_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='YGYG_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='YGYG_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='YGYG_FILE_ID']").val(uploadedFileInfo.fileId);
					}					
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YGYGdelUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#YGYG_fileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}

    function YGYGchooseCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	YGYGbindScanCtrl();
        }else if(gpytype=="2"){
        	YGYGbindScanCtrlLT();
        }else if(gpytype=="3"){
        	YGYGbindScanCtrlZT();
        }
    }
function YGYGbindScanCtrl(){
	var onlineBusTableName = "T_BDC_DYQSCDJ";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName, {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				YGYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function YGYGbindScanCtrlLT(){
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
				YGYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function YGYGbindScanCtrlZT(){
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
				YGYGinitScanUploadMaters(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function YGYGinitScanUploadMaters(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='YGYG_FILE_URL']").val();
		var fileid=$("input[name='YGYG_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='YGYG_FILE_URL']").val(fileurl+";"+resultJson[i].data.filePath);
			$("input[name='YGYG_FILE_ID']").val(fileid+";"+resultJson[i].data.fileId);
		}else{
			$("input[name='YGYG_FILE_URL']").val(resultJson[i].data.filePath);
			$("input[name='YGYG_FILE_ID']").val(resultJson[i].data.fileId);
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
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"YGYGdelUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
        $("#YGYG_fileListDiv").append(spanHtml); 
	}
}
/* function YGYGdelUploadFile(fileId,attacheKey){
	AppUtil.delUploadMater(fileId,attacheKey);
	var fileurl=$("input[name='YGYG_FILE_URL']").val();
	var fileid=$("input[name='YGYG_FILE_ID']").val();
	var arrayIds=fileid.split(";");
	var arrayurls=fileurl.split(";");
	for(var i=0;i<arrayIds.length;i++){
		if(arrayIds[i]==fileId){
			arrayIds.splice(i,1); 
			arrayurls.splice(i,1); 
			break;
		}
	}
	$("input[name='YGYG_FILE_URL']").val(arrayurls.join(";"));
	$("input[name='YGYG_FILE_ID']").val(arrayIds.join(";"));
} */
function YGYGdelUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='YGYG_FILE_URL']").val();
		var fileid=$("input[name='YGYG_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='YGYG_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='YGYG_FILE_ID']").val(arrayIds.join(";"));
	});
}
// -------------------预购预告上传及高拍仪 结束-------------------------------------------------------------
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

		function YgygQlrRead()//开始读卡(预购预告-权利人)
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='MSFXM']").val(GT2ICROCX.Name)
				$("input[name='MSFZJHM']").val(GT2ICROCX.CardNo)
			}
		} 
		function YgygLzrRead()//开始读卡(预购预告-代理人（领证人）)
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='LZRXM']").val(GT2ICROCX.Name)
				$("input[name='LZRZJHM']").val(GT2ICROCX.CardNo)
			}
		} 
		function YgygYwrRead()//开始读卡(预购预告-义务人)
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='ZRFXM']").val(GT2ICROCX.Name)
				$("input[name='ZRFZJHM']").val(GT2ICROCX.CardNo)
			}
		} 
		function YgygDlrRead()//开始读卡(预购预告-代理人)
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='DLRXM']").val(GT2ICROCX.Name)
				$("input[name='DLRZJHM']").val(GT2ICROCX.CardNo)
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
    </style>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDC_YGSPFYGDJ_FORM" method="post">
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
	
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
			<tr>
				<th colspan="4">基本信息</th>
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
		<jsp:include page="./bdcygspfygdj/T_BDCYGSPFYGDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入预购预告信息 --%>
		<jsp:include page="./bdcygspfygdj/T_BDCYGSPFYGDJ_YGYG.jsp" />
		<%--结束引入预购预告信息--%>
	

	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
