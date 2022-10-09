<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<eve:resources	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcDjxx.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/bdcqlcbfbdcqzshdjzm/js/bfbdcqzshdjzm.js"></script>
<script type="text/javascript">
$(function(){

	$("#ZD_TDYT").combobox({
		url:'bdcGyjsjfwzydjController/loadTdytData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		prompt:'请选择土地用途',
		panelHeight:'200',
		multiple:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
		},
		onLoadSuccess: function (row) {  //下拉框数据加载成功调用
			var opts = $(this).combobox('options');
			var target = this;
			var values = $(target).combobox('getValues');//获取选中的值的values
			$.map(values, function (value) {
				var el = opts.finder.getEl(target, value);
				el.find('input.combobox-checkbox')._propAttr('checked', true);
			})
		},
		onSelect: function (row) { //选中一个选项时调用
			var opts = $(this).combobox('options');
			//设置选中值所对应的复选框为选中状态
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', true);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		},
		onUnselect: function (row) {
			//不选中一个选项时调用
			var opts = $(this).combobox('options');
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', false);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		}
	});

	$("#ZDZL_XIAN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onLoadSuccess:function(row) {
			$('#ZDZL_XIAN').combobox('setValue','351001');
		},
		onSelect:function (row) {
			$('#ZDZL_ZHENG').combobox('clear');
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
				$('#ZDZL_ZHENG').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_ZHENG").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
				$('#ZDZL_CUN').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_CUN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {

		}
	});


	 $("input[name='JBR_NAME']").removeAttr('readonly');
	 $("input[name='JBR_ZJHM']").removeAttr('readonly');

	/*隐藏三个业务缴费发证信息*/
	$("#djjfxx").hide();
	$("#djfzxx").hide();
	$("#djgdxx").hide();

	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
	dealItems = "," + dealItems + ",";
	notPrintSpb();
	hideDjxx('bfbdcqzshdjzm');
	yjmbSelect();
	if(flowSubmitObj){
		//获取表单字段权限控制信息
		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		var exeid = flowSubmitObj.EFLOW_EXEID;
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		initDjxxTable(flowSubmitObj); //初始化登记信息
		 //初始化表单字段权限控制
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDCQLC_BFBDCQZSHDJZM_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_BFBDCQZSHDJZM_FORM");
			initAutoTable(flowSubmitObj); //初始化权利信息
			/*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
			if (flowSubmitObj.busRecord.ZD_TDYT) {
				$("#ZD_TDYT").combobox("setValues", flowSubmitObj.busRecord.ZD_TDYT.split(","));
			}
			if (flowSubmitObj.busRecord.ZDZL_XIAN) {
				$("#ZDZL_XIAN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_XIAN);
			}
			if (flowSubmitObj.busRecord.ZDZL_ZHENG) {
				$("#ZDZL_ZHENG").combobox("setValue", flowSubmitObj.busRecord.ZDZL_ZHENG);
			}
			if (flowSubmitObj.busRecord.ZDZL_CUN) {
				$("#ZDZL_CUN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_CUN);
			}
			if (flowSubmitObj.busRecord.RUN_STATUS) {
				if (flowSubmitObj.busRecord.RUN_STATUS != 0) {
					/*其它环节审批表可以进行打印*/
					$("#spbdf").attr("onclick","goPrintTemplate('BFBDCQZSHDJZMSPB','3');");
					$("#spbsf").attr("onclick","goPrintTemplate('BFBDCQZSHDJZMSPB','3');");

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
						$("#bdcqzh_tr").attr("style", "");
						$("#bdcczr_tr").attr("style", "");
						$("#bdcqzbsm_tr").show();
						$("#BDC_QZVIEW").show();
					}
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
						disabledForm();
						if($("input[name='SBMC']").val()){
						}else{
							$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');
						}
					}else{
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
				}
			}
			var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
			//设置文件是否合规
			if(isAuditPass == "-1"){
				$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
			}
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
});


function FLOW_TempSaveFun(flowSubmitObj){
	// getQslyInfoJson();	
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_BFBDCQZSHDJZM_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}
	/*宗地用途特殊获取*/
	var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
	if (ZD_TDYT) {
		flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
	}
	return flowSubmitObj;
		 
}

//------------------权证打印字段控制-------------------------
$(function () {
	$("input[name='BDC_QZBSM']").removeAttr("disabled");
	var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	if (BDC_OPTYPE) {
		if (BDC_OPTYPE == '1' || BDC_OPTYPE == 'flag1') {
			$("#zsyl").show();
			$("#zsdy").show();
		} else if (BDC_OPTYPE == '2' || BDC_OPTYPE == 'flag2') {
			$("#djjfxx").show();
			$("#djfzxx").show();
			$("#djgdxx").show();
			$("#djjfxx input , #djjfxx select").each(function (index) {
				$(this).attr("disabled", false);
			});
			$("#djfzxx input , #djfzxx select").each(function (index) {
				$(this).attr("disabled", false);
			});
			$("#djgdxx input , #djgdxx select").each(function (index) {
				$(this).attr("disabled", false);
			});
			$("#djshxx input , #djshxx select").each(function (index) {
				$(this).attr("disabled", false);
			});
		}
	}
})
//------------------权证打印字段控制-------------------------

// -------------------补发不动产登记证明上传及高拍仪 开始-------------------------------------------------------------
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
    
});

function openSlxxFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDC_DYQSCDJ', {
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
// -------------------补发不动产登记证明上传及高拍仪 结束-------------------------------------------------------------
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
		function IDCRAD_Read(name,card)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				if(name){					
					$("input[name='"+name+"']").val(GT2ICROCX.Name)
				}
				if(card){					
					$("input[name='"+card+"']").val(GT2ICROCX.CardNo)
				}
			}
		}
		
		function PowerPeopleRead(o) //开始读卡
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
				$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo)
			}
		}
		function PowLegalRead(o) //开始读卡
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name)
				$("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo)
			}
		}
		function PowAgentRead(o) //开始读卡
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name)
				$("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo)
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
	<form id="T_BDCQLC_BFBDCQZSHDJZM_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" /> 
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> 
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" /> 
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" /> 
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		<%-- 权利人信息明细 --%>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<%-- 权属来源信息明细 --%>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<%--登记发证信息明细--%>
		<input type="hidden" name="DJFZXX_JSON" id="DJFZXX_JSON"/>
		<%--登记缴费信息明细--%>
		<input type="hidden" name="DJJFXX_JSON" id="DJJFXX_JSON"/>

		<input type="hidden" name="YGXX_JSON" value="${busRecord.YGXX_JSON}">
		<input type="hidden" name="FWXX_JSON" value="${busRecord.FWXX_JSON}">

		<%--登簿状态--%>
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
		<input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />

		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

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
		<jsp:include page="./bdcqlc/bdcqlcbfbdcqzshdjzm/T_BFBDCQZSHDJZM_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入领证人信息--%>
		<jsp:include page="./bdcqlc/lzrInfo.jsp" />
		<%--结束引入领证人信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>

		<%--开始引入权属来源信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQsly.jsp" />
		<%--结束引入权属来源信息--%>
		
		<%--开始引入宗地信息-国有权力人信息--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--结束引入宗地信息-国有权力人信息--%>
		
		<%--开始引入房产基本信息--%>
	    <jsp:include page="./bdcqlc/bdcFwjbxx.jsp" />
		<%--开始引入房产基本信息--%>

		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
		<!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djgdxx:登记归档信息 -->
		<!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
		<jsp:include page="./bdcqlc/common/djauditinfo.jsp">
			<jsp:param value="bfbdcqzshdjzm" name="domId" />
			<jsp:param value="djshxx,djjfxx,djfzxx,djgdxx" name="initDomShow" />
		</jsp:include>
		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>

		<br/><br/>
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
