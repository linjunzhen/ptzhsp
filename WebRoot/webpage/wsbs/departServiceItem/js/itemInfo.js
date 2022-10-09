/***********************************初始化**********************************/

var progressBarUnit = 8.2;

$(function() {

	var isaudit = $("input[name='shan']").val();
	if (isaudit != null && isaudit != '') {
		//$("#undertake").attr("style", "");
		$("#billtype").attr("style", "");
	}

	var qlly = $("input[name='RIGHT_SOURCE']:checked").val();
	qllyCheck(qlly);

	var sfjzzwdt = $("input[name='SFJZZWDT']:checked").val();
	changeSfjzzwdt(sfjzzwdt);

	// var sfzcwlkd = $("input[name='SFZCWLKD']:checked").val();
	// changeSfzcwlkd(sfzcwlkd);

	var sftgdbfw = $("input[name='SFTGDBFW']:checked").val();
	changeSftgdbfw(sftgdbfw);

	var yjbsfsf = $("input[name='YJBSFSF']:checked").val();
	changeYjbsfsf(yjbsfsf);

	var sfsf = $("input[name='SFSF']:checked").val();
	changeSfsf(sfsf);

	$("input[name='BILL_TYPE']").click(function() {
		//获取值
		var billValue = $(this).val();
		if (billValue == "qt") {
			$("input[name='BILL_TYPE_OTHER']").attr("disabled", false);
		} else {
			$("input[name='BILL_TYPE_OTHER']").attr("disabled", true);
			$("input[name='BILL_TYPE_OTHER']").val("");
		}
	});

	//初始判断对象信息是否展示
	var str = ",";
	$("input[type=checkbox][name='MXYHDX']:checked").each(function() {
		str += $(this).val();
	});
	if (str.indexOf("01") > 0) {
		$("#ptheme").attr("style", "");
		$("#pevent").attr("style", "");
		$("#pobject").attr("style", "");
	}
	if (str.indexOf("02") > 0 || str.indexOf("03") > 0 || str.indexOf("04") > 0 || str.indexOf("05") > 0) {
		$("#ltheme").attr("style", "");
		$("#levent").attr("style", "");
		$("#lobject").attr("style", "");
	}
	//是否展开中介服务
	var isNeedAgency = $("input[name='IS_NEED_AGENCY']:checked").val();
	if(isNeedAgency=='0'){
		$("#agencyService").attr("style", "display:none;");
		$("#AGENCYSERVICE_NAME").attr("class", "eve-input validate[]");
	}
	$("input[name='IS_NEED_AGENCY']").click(function() {
		//获取值
		var value = $(this).val();
		if (value == "0") {
			$("#agencyService").attr("style", "display:none;");
			$("#AGENCYSERVICE_NAME").attr("class", "eve-input validate[]");
			$("input[name='AGENCYSERVICE_NAME']").val("");
			$("input[name='AGENCYSERVICE_ID']").val("");
		} else {
			$("#agencyService").attr("style", "");
			$("#AGENCYSERVICE_NAME").attr("class", "eve-input validate[required]");
		}
	});

	$("input[name='IS_UNDERTAKE']").click(function() {
		//获取值
		var sfsfValue = $(this).val();
		if (sfsfValue == "0") {
			$("#IS_UNDERTAKEMC_TR").attr("style", "");
			$("#IS_UNDERTAKEWH_TR").attr("style", "");
		} else {
			$("#IS_UNDERTAKEMC_TR").attr("style", "display:none;");
			$("#IS_UNDERTAKEWH_TR").attr("style", "display:none;");
			$("input[name='WCJLHMC']").val('');
			$("input[name='WCJLHWH']").val('');
		}
	});
	
	$("input[name='SFSF']").click(function() {
		//获取值
		var sfsfValue = $(this).val();
		if (sfsfValue == "1") {
			$("#SFFS_TR").attr("style", "");
			$("#SFBZJYJ_TR").attr("style", "");
			$("#SFBZJYJ_TR1").attr("style", "");
			$("#SFBZJYJ_TR2").attr("style", "");
			$("#SFYXJM_TR").attr("style", "");
			$("#JMYJ_TR").attr("style", "");
			$("#SFFSSM_TR").attr("style", "");
			$("#SFZFQD_TR").attr("style", "");
			$("#CHARGECFGNAME_TR").attr("style", "");
			$("#CHARGECFGCODE_TR").attr("style", "");
			$('#sfmxtable').show();
			$("#sfmxGrid").datagrid("resize");
		} else {
			$("#SFFS_TR").attr("style", "display:none;");
			$("#SFBZJYJ_TR").attr("style", "display:none;");
			$("#SFBZJYJ_TR1").attr("style", "display:none;");
			$("#SFBZJYJ_TR2").attr("style", "display:none;");
			$("#SFYXJM_TR").attr("style", "display:none;");
			$("#JMYJ_TR").attr("style", "display:none;");
			$("#SFFSSM_TR").attr("style", "display:none;");
			$("#SFZFQD_TR").attr("style", "display:none;");
			$("#CHARGECFGNAME_TR").attr("style", "display:none;");
			$("#CHARGECFGCODE_TR").attr("style", "display:none;");
			$('#sfmxtable').hide();
			$("textarea[name='SFBZJYJ']").val('');
			$("input[name='SFFSSM']").val('');
			$("input:checkbox[name='SFFS']").each(function() {
				if (this.checked) {
					this.checked = false;
				}
			});
			$("input:radio[name='PAY_WAY']").each(function() {
				if (this.checked) {
					this.checked = false;
				}
			});
		}
	});
	
	$("input[name='SFFS']").click(function() {
		var choseflag = 0;
		$("input[type=checkbox][name='SFFS']:checked").each(function() {
			if($(this).val()=='02'){
				choseflag = 1;
				$("input:radio[name='ONLINEPAY_SUPPORT'][value=1]").prop("checked",true);
			}
		});
		if(choseflag==0){
			$("input:radio[name='ONLINEPAY_SUPPORT'][value=0]").prop("checked",true);
		}
	});
	$("input[type=checkbox][name='SFFS']:checked").each(function() {
		if($(this).val()=='02'){
			$("input:radio[name='ONLINEPAY_SUPPORT'][value=1]").prop("checked",true);
		}
	});
	
	var sfsfval = $("input[name='SFSF']:checked").val();
	if(sfsfval=='1'){
		$("#SFFS_TR").attr("style", "");
		$("#SFBZJYJ_TR").attr("style", "");
		$("#SFBZJYJ_TR1").attr("style", "");
		$("#SFBZJYJ_TR2").attr("style", "");
		$("#SFYXJM_TR").attr("style", "");
		$("#JMYJ_TR").attr("style", "");
		$("#SFFSSM_TR").attr("style", "");
		$("#SFZFQD_TR").attr("style", "");
		$("#CHARGECFGNAME_TR").attr("style", "");
		$("#CHARGECFGCODE_TR").attr("style", "");
	}else{
		$("#SFFS_TR").attr("style", "display:none;");
		$("#SFBZJYJ_TR").attr("style", "display:none;");
		$("#SFBZJYJ_TR1").attr("style", "display:none;");
		$("#SFBZJYJ_TR2").attr("style", "display:none;");
		$("#SFYXJM_TR").attr("style", "display:none;");
		$("#JMYJ_TR").attr("style", "display:none;");
		$("#SFFSSM_TR").attr("style", "display:none;");
		$("#SFZFQD_TR").attr("style", "display:none;");
		$("#CHARGECFGNAME_TR").attr("style", "display:none;");
		$("#CHARGECFGCODE_TR").attr("style", "display:none;");
		
		$('#sfmxtable').hide();
	}
	
	//是否展开公共服务选项
	var gfvalue = $("input[name='SXXZ']:checked").val();
	if (gfvalue == "GF") {
		$("#gftype").attr("style", "");
	} else {
		$("#gftype").attr("style", "display:none;");
	}
	
	$("input[name='SXXZ']").click(function() {
		//获取值
		var sfsfValue = $(this).val();
		if (sfsfValue == "GF") {
			$("#gftype").attr("style", "");
		} else {
			$("#gftype").attr("style", "display:none;");
		}
	});
	
	$("input[name='MXYHDX']").click(function() {
		var str = ",";
		$("input[type=checkbox][name='MXYHDX']:checked").each(function() {
			str += $(this).val();
		});
		if (str.indexOf("01") > 0) {
			$("#ptheme").attr("style", "");
			$("#pevent").attr("style", "");
			$("#pobject").attr("style", "");
		} else {
			$("#ptheme").attr("style", "display:none;");
			$("#pevent").attr("style", "display:none;");
			$("#pobject").attr("style", "display:none;");
		}
		if (str.indexOf("02") > 0 || str.indexOf("03") > 0 || str.indexOf("04") > 0 || str.indexOf("05") > 0) {
			$("#ltheme").attr("style", "");
			$("#levent").attr("style", "");
			$("#lobject").attr("style", "");
		} else {
			$("#ltheme").attr("style", "display:none;");
			$("#levent").attr("style", "display:none;");
			$("#lobject").attr("style", "display:none;");
		}
	});
	
	$("input[name='RZBSDTFS']").click(function() {
		//获取值
		var rzbsdtfsValue = $(this).val();
		if (rzbsdtfsValue == "in02") {
			$("#applyurltag").attr("style","");
			$("#APPLY_URL").attr("class", "eve-input validate[required,custom[allurl]");
		} else {
			$("#applyurltag").attr("style","display:none;");
			$("#APPLY_URL").attr("class", "eve-input validate[[],custom[allurl]]");
		}
		if (rzbsdtfsValue == "in04") {
			$("#threestar").attr("style", "");
			$("#fourstar").attr("style", "");
			$("#fivestar").attr("style", "");
		} else {
			$("#threestar").attr("style", "display:none;");
			$("#fourstar").attr("style", "display:none;");
			$("#fivestar").attr("style", "display:none;");
		}
	});
	var oldsbtext = $("input[name='RZBSDTFS']:checked")[0].nextSibling.nodeValue;
	$("input[name='OLDRZBSDTFS_TEXT']").val(oldsbtext);
	var rzbsdtfsValue = $("input:radio[name='RZBSDTFS']:checked").val();
	if(rzbsdtfsValue=='in02'){
		$("#applyurltag").attr("style","");
		$("#APPLY_URL").attr("class", "eve-input validate[required,custom[allurl]");
	}
	
	//申报方式到窗口次数初始选项处理
	$("select[name='CKCS_1'] option[value='-1']").remove();
	$("select[name='CKCS_2'] option[value='3']").remove();
	$("select[name='CKCS_3'] option[value='2']").remove();
	$("select[name='CKCS_4'] option[value='2']").remove();
	$("select[name='CKCS_3'] option[value='3']").remove();
	$("select[name='CKCS_4'] option[value='3']").remove();

	$("input[name='PAPERSUB']").click(function() {
		var str = ",";
		$("input[type=checkbox][name='PAPERSUB']:checked").each(function() {
			str += $(this).val() + ",";
		});
		if (str.indexOf("2") > 0) {
			$("#addr").attr("style", "");
			$("#addressee").attr("style", "");
			$("#addrmobile").attr("style", "");
			$("#addrprov").attr("style", "");
			$("#addrcity").attr("style", "");
			$("#addrpostcode").attr("style", "");
			$("#yjbsfsf").attr("style", "");
			$("#mfydblx").attr("style", "");
			$("#addrremarks").attr("style", "");
			$("#officephone").attr("style", "");
			$("#expressname").attr("style", "");
			$("input:radio[name='EXPRESS_SUPPORT'][value='1']").prop("checked","true");
		} else {
			$("#addr").attr("style", "display:none;");
			$("#addressee").attr("style", "display:none;");
			$("#addrmobile").attr("style", "display:none;");
			$("#addrprov").attr("style", "display:none;");
			$("#addrcity").attr("style", "display:none;");
			$("#addrpostcode").attr("style", "display:none;");
			$("#yjbsfsf").attr("style", "display:none;");
			$("#mfydblx").attr("style", "display:none;");
			$("#addrremarks").attr("style", "display:none;");
			$("#officephone").attr("style", "display:none;");
			$("#expressname").attr("style", "display:none;");
			var pstr = ",";
			$("input[type=checkbox][name='FINISH_GETTYPE']:checked").each(function() {
				pstr += $(this).val() + ",";
			});
			if(pstr.indexOf("02") != -1){
				$("input:radio[name='EXPRESS_SUPPORT'][value='1']").prop("checked","true");
			}else{
				$("input:radio[name='EXPRESS_SUPPORT'][value='0']").prop("checked","true");
			}
		}
	});
	var papersub = $("input[name='papersub']").val();
	if (papersub.indexOf("2") != -1) {
		$("#addr").attr("style", "");
		$("#addressee").attr("style", "");
		$("#addrmobile").attr("style", "");
		$("#addrprov").attr("style", "");
		$("#addrcity").attr("style", "");
		$("#addrpostcode").attr("style", "");
		$("#addrremarks").attr("style", "");
		$("#officephone").attr("style", "");
		$("#expressname").attr("style", "");
		$("input:radio[name='EXPRESS_SUPPORT'][value='1']").prop("checked",true);
	}
	
	$("input[name='FINISH_GETTYPE']").click(function(){
		var str = ",";
		$("input[type=checkbox][name='FINISH_GETTYPE']:checked").each(function() {
			str += $(this).val() + ",";
		});
		if(str.indexOf("02")!=-1){
			$("input:radio[name='EXPRESS_SUPPORT'][value='1']").prop("checked","true");
		}else{
			var pstr = ",";
			$("input[type=checkbox][name='PAPERSUB']:checked").each(function() {
				pstr += $(this).val() + ",";
			});
			if(pstr.indexOf("2") != -1){
				$("input:radio[name='EXPRESS_SUPPORT'][value='1']").prop("checked","true");
			}else{
				$("input:radio[name='EXPRESS_SUPPORT'][value='0']").prop("checked","true");
			}
		}
	});
	$("input[type=checkbox][name='FINISH_GETTYPE']:checked").each(function() {
		if($(this).val()=='02'){
			$("input:radio[name='EXPRESS_SUPPORT'][value=1]").prop("checked",true);
		}
	});

	$("#defGrid").datagrid({
		onDblClickRow : function(rowIndex, rowData) {
			var nodeType = rowData.NODE_TYPE;
			if (nodeType == 'start') {
				art.dialog({
					content : "开始环节无需设置办理人员！",
					icon : "warning",
					ok : true
				});
			} else if (nodeType == 'end') {
				art.dialog({
					content : "结束无需设置办理人员！",
					icon : "warning",
					ok : true
				});
			} else {
				showNodeAuditerWindow(rowData.RECORD_ID);
			}
		}
	});
	
	$("input[name='IS_SYNCHRONIZE']").click(function() {
		//获取值
		var sync = $(this).val();
		if (sync == "1") {
			$("#syncperson").attr("style", "");
		} else {
			$("#syncperson").attr("style", "display:none;");
		}
	});

	for (var i = 1; i <= 14; i++) {
		AppUtil.bindValidateEngine({
			selector : "#StepForm_" + i
		});
	}
	var preAuditer = $("input[name='preAuditer']").val();
	if (preAuditer != "") {
		addtoTD("auditusernames", preAuditer);
	}
	var bdlcdyName = $("input[name='bdlcdyName']").val();
	if (bdlcdyName != "") {
		addtoTD("defname", bdlcdyName);
	}

	//事项性质不可更改
	// $("input[name='SXXZ']").attr("disabled", "disabled");
	//入驻方式不可更改
	//$("input[name='RZBSDTFS']").attr("disabled", "disabled");

	//结果样本初始化
	/* AppUtil.initUploadControl({
		file_types : "*.jpg;*.jpeg;*.png;*.bmp;",
		file_upload_limit : 0,
		file_queue_limit : 1,
		uploadPath : "resultSample",
		busTableName : "T_WSBS_APPLYMATER",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "RESULT_PATH_BTN",
		singleFileDivId : "RESULT_PATH_DIV",
		singleFileId : $("input[name='RESULT_PATH']").val(),
		singleFileFieldName : "RESULT_PATH",
		limit_size : "5 MB",
		uploadSuccess : function(resultJson) {
			$("input[name='RESULT_PATH']").val(resultJson.fileId);
		}
	}); */
	
	// $("input[name='EXERCISE_LEVEL']").attr("disabled",true);
	// $("input[name='RIGHT_SOURCE']").attr("disabled",true);
	
	var fileids=$("input[name='RESULT_PATH']").val();
	$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
		if(resultJson!="websessiontimeout"){
			$("#RESULT_PATH_DIV").html("");
			var newhtml = "";
			var list=resultJson.rows;
			for(var i=0; i<list.length; i++){
				newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
				newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
				newhtml+=list[i].FILE_NAME+'</a>';
				newhtml+='</p>';
			 } 
			 $("#RESULT_PATH_DIV").html(newhtml);
		}
    });
});


/**
* 标题附件上传对话框
*/
function openResultFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_WSBS_APPLYMATER', {
		title: "上传(附件)",
		width: "620px",
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
					$("input[name='RESULT_PATH']").val(uploadedFileInfo.fileId);
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#RESULT_PATH_DIV").html(spanHtml); 
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
		$("input[name='RESULT_PATH']").val("");
	});
}
/***********************************初始化结束**********************************/

/***********************************基础js**********************************/
//上一步
function doPre() {
	var currentStep = $("input[name='CurrentStep']").val();
	$("#StepDiv_" + currentStep).attr("style", "display:none;");
	//获取上一步数字
	var preStep = parseInt(currentStep) - 1;
	if (preStep == 1) {
		$("#preStepButton").attr("disabled", "disabled");
	}
	$("#StepDiv_" + preStep).attr("style", "");
	$("input[name='CurrentStep']").val(preStep);
	$("#flow_node_" + preStep).attr("class", "flow_active");
	for (var index = preStep + 1; index <= 11; index++) {
		$("#flow_node_" + index).attr("class", "flow_nodone");
	}
	$("#nextStepButton").val("保存并下一步");
	$("#nextStepButton").removeAttr("disabled");
	//计算百分比
	var progressBarPer = (preStep - 1) * progressBarUnit;
	$("#progress_bar").attr("style", "width:" + progressBarPer + "%;");
}

//跳转指定标签
function jumpToTargetDiv(targetDiv) {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	//获取当前的环节值
	var currentStep = $("input[name='CurrentStep']").val();
	if (itemId && currentStep != targetDiv) {
		if (currentStep > targetDiv) {
			for (var i = targetDiv + 1; i <= currentStep; i++) {
				$("#flow_node_" + i).attr("class", "flow_nodone");
			}
		} else if (currentStep < targetDiv) {
			$("#flow_node_" + currentStep).attr("class", "flow_done");
		}
		changeNextStepPro(currentStep, targetDiv);
	} else {
		return;
	}
}

//更改保存并下一步的样式
function changeNextStepPro(currentStep, nextStep) {
	var shan = $("input[name='shan']").val();
	$("#StepDiv_" + currentStep).attr("style", "display:none;");
	if (nextStep == 8) {
		var date = $("#tshjGrid").datagrid("getData");
		if (date.total == '0') {
			$("#SFZCGQ").val(0);
			$("#SFZCGQ").attr("disabled", "disabled");
		} else {
			$("#SFZCGQ").val(1);
			$("#SFZCGQ").attr("disabled", "disabled");
		}
	}
	if (nextStep == 1) {
		$("#preStepButton").attr("disabled", "disabled");
	} else {
		$("#preStepButton").removeAttr("disabled");
	}
	if (nextStep == 3) {
		$("#StepDiv_" + nextStep).css("display", "block");
		var itemId = $("input[name='ITEM_ID']").val();
		$("#tshjGrid").datagrid("resize");
		$("#tshjGrid").datagrid({
			url : "departServiceItemController.do?specialLinkData&itemId=" + itemId
		});
	}else if (nextStep == 5) {
		$("#StepDiv_" + nextStep).css("display", "block");
		var itemId = $("input[name='ITEM_ID']").val();
		$("#ApplyMaterGrid").datagrid("resize");
		$("#ApplyMaterGrid").datagrid({
			url : "applyMaterController.do?forItemDatas&itemId=" + itemId
		});
	}else if (nextStep == 9) {
		$("#StepDiv_" + nextStep).css("display", "block");
		var itemId = $("input[name='ITEM_ID']").val();
		$("#sfmxGrid").datagrid("resize");
		$("#sfmxGrid").datagrid({
			url : "departServiceItemController.do?chargeData&itemId=" + itemId
		});
	}
	// else if (nextStep == 11) {
	// 	$("#StepDiv_" + nextStep).css("display", "block");
	// 	var itemId = $("input[name='ITEM_ID']").val();
	// 	$("#layout_" + nextStep).layout("resize");
	// 	$("#handleTypeGrid").datagrid({
	// 		url : "departServiceItemController.do?selfExamData&itemId=" + itemId
	// 	});
	//
	// 	var handleTypeTreeSetting = {
	// 		edit : {
	// 			enable : false,
	// 			showRemoveBtn : false,
	// 			showRenameBtn : false
	// 		},
	// 		view : {
	// 			selectedMulti : false
	// 		},
	// 		callback : {
	// 			onClick : onHandleTypeTreeClick
	// 		},
	// 		async : {
	// 			enable : true,
	// 			url : "applyMaterController.do?handleTypeTree&itemId="+itemId
	// 		}
	// 	};
	// 	$.fn.zTree.init($("#handleTypeTree"), handleTypeTreeSetting);
	// }
	else if (nextStep == 11) {
		$("#StepDiv_" + nextStep).css("display", "block");
		//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		$("#CommonProblemGrid").datagrid("resize");
		$("#CommonProblemGrid").datagrid({
			url : "commonProblemController.do?datagrid&Q_T.ITEM_ID_EQ=" + itemId
		});
	} else if (nextStep == 8) {
		$("#StepDiv_" + nextStep).css("display", "block");
		var itemId = $("input[name='ITEM_ID']").val();
		$("#defGrid").datagrid("resize");
		$("#defGrid").datagrid({
			url : "departServiceItemController.do?defNodeData&itemId=" + itemId
		});
	} else {
		$("#StepDiv_" + nextStep).attr("style", "");
	}
	$("input[name='CurrentStep']").val(nextStep);
	$("#flow_node_" + nextStep).attr("class", "flow_active");
	for (var index = nextStep - 1; index >= 1; index--) {
		$("#flow_node_" + index).attr("class", "flow_done");
	}
	if (nextStep == 12) {
		if (shan != null && shan != "") {
			$("#nextStepButton").val("保存");
			$("#nextStepButton").attr("disabled", "disabled");
		} else {
			$("#nextStepButton").val("保存");
		}
	}else if (nextStep == 13) {
		$("#preStepButton").removeAttr("disabled");
		$("#nextStepButton").attr("disabled", "disabled");
		$("#nextStepNoSaveButton").attr("disabled", "disabled");
	} else {
		$("#nextStepButton").val("保存并下一步");
		$("#nextStepButton").removeAttr("disabled");
		$("#nextStepNoSaveButton").removeAttr("disabled");
	}
	//计算百分比
	var progressBarPer = (nextStep - 1) * progressBarUnit;
	$("#progress_bar").attr("style", "width:" + progressBarPer + "%;");
}

//保存
function submitInfo(currentStep) {
	var url = $("#StepForm_" + currentStep).attr("action");
	//获取保存并下一步数字
	var nextStep = parseInt(currentStep) + 1;
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("StepForm_" + currentStep);
	if (currentStep != "1") {
		formData.ITEM_ID = $("input[name='ITEM_ID']").val();
	}else{
		formData.EXERCISE_LEVEL = $("input:radio[name='EXERCISE_LEVEL']:checked").val();
	}
	if (currentStep == "3") {
		var newsbtext = $("input[name='RZBSDTFS']:checked")[0].nextSibling.nodeValue;
		formData.RZBSDTFS_TEXT = newsbtext;
	}
	formData.currentStep = currentStep;
	if (currentStep == '5' || currentStep == '11') {
		url = "departServiceItemController.do?saveOrUpdate";
	}
	AppUtil.ajaxProgress({
		url : url,
		params : formData,
		callback : function(resultJson) {
			if (resultJson.success) {
				if (currentStep == "1") {
					$("input[name='ITEM_ID']").val(resultJson.itemId);
					$("input[name='ITEM_CODE']").attr("readonly", "readonly");
					$("input[name='Q_T.ITEM_ID_EQ']").val(resultJson.itemId);
					var sxlx = resultJson.SXLXflag;
					if (sxlx == 1) { //其他转即办件,可读变只读
						$("input[name='CNQXGZR']").val("0");
						$("input[name='CNQXGZR']").attr("readonly", "readonly");
					} else if (sxlx == 2) { //即办件转其他，只读变可读
						$("input[name='CNQXGZR']").removeAttr("readonly");
					}
				}
				/**
				if(currentStep==10){
				        parent.art.dialog({
				        content : "保存成功",
				        icon : "succeed",
				        time : 3,
				        ok : true
				    });
				    //AppUtil.closeLayer();
				        }else{
					$("input[name='FWSXZT']").val(resultJson.fwsxzt);
				        	changeNextStepPro(currentStep,nextStep);
				        }**/
				$("input[name='FWSXZT']").val(resultJson.fwsxzt);
				changeNextStepPro(currentStep, nextStep);
			} else {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "error",
					ok : true
				});
			}
		}
	});
}

//保存并下一步
function doNext() {
	var currentStep = $("input[name='CurrentStep']").val();
	var status = $("input[name='FWSXZT']").val();
	//获取保存并下一步数字
	var nextStep = parseInt(currentStep) + 1;
	if (currentStep == 10) {
		var PREAUDITER_IDS = $("input[name='PREAUDITER_IDS']").val();
		if (PREAUDITER_IDS == null || PREAUDITER_IDS == "" || PREAUDITER_IDS==undefined) {
			art.dialog({
				content : "请选择预审人员!",
				icon : "warning",
				ok : true
			});
			return false;
		}

		if (status == "1") {
			art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
				saveAuditUsers(currentStep);
			});
		} else {
			saveAuditUsers(currentStep);
		}

		return;
	} else if (currentStep == 1 || currentStep == 2 || currentStep == 3 || currentStep == 4 || currentStep == 5 || currentStep == 6 ||
		currentStep == 7 || currentStep == 8 || currentStep == 9 || currentStep == 10 || currentStep == 11 || currentStep == 12) {
		if (currentStep == 1 && !checkBusType()) {
			return false;
		}
		if (currentStep == 2 && !checkSbfs()) {
			return false;
		}
		if (currentStep == 9) {
			if ($("input[name='SFSF']:checked").val() == "1") {
				var rows = $("#sfmxGrid").datagrid("getRows");
				if(rows.length<1){
					art.dialog({
						content : "收费明细至少要有一条记录!",
						icon : "warning",
						ok : true
					});
					return false;
				}
			}
		}
		if (currentStep == 1) {
			var resultpath = $("input[name='RESULT_PATH']").val();
//			if(resultpath==null||resultpath=='undefined'||resultpath==''){
//				art.dialog({
//					content : "请上传审批结果样本!",
//					icon : "warning",
//					ok : true
//				});
//				return false;
//			}
		}
		if (currentStep == 8) {
			if ($("input[name='BDLCDYID']").val() == null || $("input[name='BDLCDYID']").val() == "") {
				art.dialog({
					content : "请选择审批流程!",
					icon : "warning",
					ok : true
				});
				return false;
			}
			var nodeData = $("#defGrid").datagrid('getRows');
			var isfull = true;
			var bdlcdyId = $("input[name='BDLCDYID']").val();
			var defTypeName=isGcjsDefTypeName(bdlcdyId);
			if (bdlcdyId=='2c90b38a6513e842016514955f4303e9'
				||bdlcdyId=='2c90b38a6513e842016513fb166d0077'
					||bdlcdyId=='2c90b38a650face1016513c7b52c06f7'
						||bdlcdyId=='2c90b38a63d47d970163f1f9aa1614f8'
							||bdlcdyId=='2c90b38a63d47d970163f1cfd07f072b'
								||bdlcdyId=='2c90b38a598becc601598c9e420c1791'
									||bdlcdyId=='2c90b38a58ebb5120158ebcb01f61320'
										||bdlcdyId=='2c90b38a58b9ca320158bd0fedc505c5'
											||bdlcdyId=='2c90b38a58b9ca320158bd0ea4760450'||defTypeName==true){
				
			}else{
				for (var i = 0; i < nodeData.length; i++) {
					if ((nodeData[i].USER_ACCOUNT == null || nodeData[i].USER_ACCOUNT == "undefined") 
							&& nodeData[i].NODE_TYPE != "start" 
								&& nodeData[i].NODE_TYPE != "end" 
									&& nodeData[i].NODE_NAME != "网上预审") {
						isfull = false;
						break;
					}
				}
			}
			if (!isfull) {
				art.dialog({
					content : "请完成审批流程环节审核人配置!",
					icon : "warning",
					ok : true
				});
				return false;
			}
		}
		var validateResult = $("#StepForm_" + currentStep).validationEngine("validate");
		if (!validateResult) {
			return;
		} else {
			var itemId = $("input[name='ITEM_ID']").val();
			if (itemId == null || itemId == "" || itemId == 'undefined') {
				submitInfo(currentStep);
			} else if (status == "1") {
				art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
					submitInfo(currentStep);
				});
			} else {
				submitInfo(currentStep);
			}
		}
	} else {
		changeNextStepPro(currentStep, nextStep);
	}

}
//获取是否是工程项目类型流程
function isGcjsDefTypeName(defId) {
	var flag;
	$.ajax({
		url: "departServiceItemController/isGcjsDefTypeNode.do?",
		type: "POST",
		data: {defId: defId},
		dataType: "json",
		async: false,
		success: function (data) {
			datas = data;
		},
		error: function (data) {
			datas = data;
		}
	});
	return datas;
}

//下一步
function toNext() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var currentStep = $("input[name='CurrentStep']").val();
	if (currentStep == 1 && !checkBusType()) {
		return false;
	}
	if (currentStep == 2 && !checkSbfs()) {
		return false;
	}
	if (currentStep == 1 && !checkFinishGet()) {
		return false;
	}
	var validateResult = $("#StepForm_" + currentStep).validationEngine("validate");
	if (!validateResult) {
		return;
	}
	//获取下一步数字
	if (itemId) {
		var nextStep = parseInt(currentStep) + 1;
		changeNextStepPro(currentStep, nextStep);
	} else {
		return;
	}
}

//通过或者退回
function doPassOrBack() {
	var itemId = $("input[name='ITEM_ID']").val();
	var FWSXZT = $("input[name='FWSXZT']").val();
	$.dialog.open("serviceItemController.do?auditingBackInfo&itemIds=" + itemId + "&state=" +FWSXZT+ "&fileFlag="+FWSXZT, {
		title : "审核意见",
		width : "600px",
		height : "450px",
		fixed : true,
		lock : true,
		resize : false,
		close : function() {
			var backinfo = art.dialog.data("backinfo");
			if (backinfo && backinfo.back) {
				parent.art.dialog({
					content : "提交成功",
					icon : "succeed",
					time : 3,
					ok : true
				});
				art.dialog.removeData("backinfo");
				AppUtil.closeLayer();
			}
		}
	}, false);
}
/***********************************基础js结束**********************************/

/***********************************基本信息**********************************/
//选择权责项目
function selectBillOfRight() {
	parent.$.dialog.open("billOfRightController/itemSelector.do", {
		title : "权责清单项目选择器",
		width : "800px",
		lock : true,
		resize : false,
		height : "500px",
		close : function() {
			var selectItemInfo = art.dialog.data("selectItemInfo");
			if (selectItemInfo) {
				$("input[name='RIGHT_NAME']").val(selectItemInfo.rightName);
				$("input[name='RIGHT_ID']").val(selectItemInfo.rightId);
				// $("input:radio[name='RIGHT_SOURCE'][value='"+selectItemInfo.rightSource+"']").attr("checked",true);
				// if(selectItemInfo.rightSourceOhter=='undefined'||selectItemInfo.rightSourceOhter==null){
				// 	$("input[name='RIGHT_SOURCE_OTHER']").val("");
				// }else{
				// 	$("input[name='RIGHT_SOURCE_OTHER']").val(selectItemInfo.rightSourceOhter);
				// }
				// if(selectItemInfo.subitem=='undefined'||selectItemInfo.subitem==null){
				// 	$("input[name='SUBITEM_NAME']").val("");
				// }else{
				// 	$("input[name='SUBITEM_NAME']").val(selectItemInfo.subitem);
				// }
				// $("input:radio[name='EXERCISE_LEVEL'][value='"+selectItemInfo.exerciseLevel+"']").prop("checked",true);
				// art.dialog.removeData("selectItemInfo");
			}
		}
	}, false);
}

function clearBillOfRight() {
	$("input[name='RIGHT_NAME']").val('');
	$("input[name='RIGHT_ID']").val('');
}

// function selectCatalog() {
// 	parent.$.dialog.open("departCatalogController/catalogSelector.do", {
// 		title : "目录选择器",
// 		width : "800px",
// 		lock : true,
// 		resize : false,
// 		height : "500px",
// 		close : function() {
// 			var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
// 			if (selectCatalogInfo) {
// 				$("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
// 				$("input[name='CATALOG_CODE']").val(selectCatalogInfo.catalogCode);
				// $("input[name='DEPART_ID']").val(selectCatalogInfo.departId);
				// $("input[name='IMPL_DEPART']").val(selectCatalogInfo.departName);
				// $("input[name='SSBMBM']").val(selectCatalogInfo.departCode);
				// $("input[type=radio][name=SXXZ][value=" + selectCatalogInfo.sxxz + "]").prop("checked", "checked");
				// if (selectCatalogInfo.sxxz == "GF") {
				// 	$("#gftype").attr("style", "");
				// }else{
				// 	$("#gftype").attr("style", "display:none;");
				// }
				// $.post("serviceItemController.do?getMaxNumCode", {
				// 	catalogCode : selectCatalogInfo.catalogCode
				// }, function(responseText, status, xhr) {
				// 	var resultJson = $.parseJSON(responseText);
				// 	if (resultJson.success == true) {
				// 		$("input[name='NUM_CODE']").val(resultJson.jsonString);
				// 		$("input[name='ITEM_CODE']").val(selectCatalogInfo.catalogCode + resultJson.jsonString);
				// 	} else {
				// 		art.dialog({
				// 			content : resultJson.msg,
				// 			icon : "error",
				// 			ok : true
				// 		});
				// 	}
				// });
// 				art.dialog.removeData("selectCatalogInfo");
// 			}
// 		}
// 	}, false);
// }

function selectStdCatalog() {
	parent.$.dialog.open("departCatalogController/stdCatalogSelector.do", {
		title : "标准化目录选择器",
		width : "800px",
		lock : true,
		resize : false,
		height : "500px",
		close : function() {
			var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
			if (selectCatalogInfo) {
				$("input[name='STANDARD_CATALOG_ID']").val(selectCatalogInfo.catalogId);
				$("input[name='STANDARD_CATALOG_NAME']").val(selectCatalogInfo.catalogName);
				art.dialog.removeData("selectCatalogInfo");
			}
		}
	}, false);
}

function qllyCheck(val) {
	if (val == 'qt') {
		$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", false);
	} else {
		$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", true);
	}
	if (val == '02'){
		$("[name='IMPL_DEPART_XZ']").attr("disabled", true);
	} else {
		$("[name='IMPL_DEPART_XZ']").attr("disabled", false);
	}
	if (val == '04' || val == '05') {
		$("[name='WTBM']").show();
		$("[name='WTBM']").prev().show();
	} else {
		$("[name='WTBM']").hide();
		$("[name='WTBM']").prev().hide();
	}
}

function selectAgency() {
	parent.$.dialog.open("agencyServiceController/agencySelector.do", {
		title : "中介服务选择器",
		width : "800px",
		lock : true,
		resize : false,
		height : "500px",
		close : function() {
			var selectItemInfo = art.dialog.data("selectItemInfo");
			if (selectItemInfo) {
				$("input[name='AGENCYSERVICE_ID']").val(selectItemInfo.serviceId);
				$("input[name='AGENCYSERVICE_NAME']").val(selectItemInfo.agencyName);
				art.dialog.removeData("selectItemInfo");
			}
		}
	}, false);
}

//检测申报对象是否有被选择
function checkBusType() {
	var returnflag = true;
	var checkIds = "";
	if ($("input[type=checkbox][name='MXYHDX']:checked").length > 0) {
		$("input[type=checkbox][name='MXYHDX']:checked").each(function() {
			var fwdx = $(this).val();
			if (fwdx == '01') {
				var personCheck = false;
				if ($("input[type=checkbox][name='GRZTFL']:checked").length > 0) {
					personCheck = true;
					checkIds += "4028818c512273e70151227569a40001,";
					$("input[type=checkbox][name='GRZTFL']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if ($("input[type=checkbox][name='GRRSSJ']:checked").length > 0) {
					personCheck = true;
					checkIds += "4028818c512273e70151229ae7e00003,";
					$("input[type=checkbox][name='GRRSSJ']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if ($("input[type=checkbox][name='GRTDRQ']:checked").length > 0) {
					personCheck = true;
					checkIds += "4028818c512273e7015122a38a130004,";
					$("input[type=checkbox][name='GRTDRQ']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if (!personCheck) {
					art.dialog({
						content : "请选择面向个人对象信息!",
						icon : "warning",
						ok : true
					});
					returnflag = false;
				}
			} else {
				var legalCheck = false;
				if ($("input[type=checkbox][name='FRZTFL']:checked").length > 0) {
					legalCheck = true;
					checkIds += "4028818c512273e7015122a452220005,";
					$("input[type=checkbox][name='FRZTFL']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if ($("input[type=checkbox][name='QYDXFL']:checked").length > 0) {
					legalCheck = true;
					checkIds += "4028818c512273e7015122c81f850007,";
					$("input[type=checkbox][name='QYDXFL']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if ($("input[type=checkbox][name='QYJYHD']:checked").length > 0) {
					legalCheck = true;
					checkIds += "4028818c512273e7015122c872dc0008,";
					$("input[type=checkbox][name='QYJYHD']:checked").each(function() {
						checkIds += $(this).val() + ",";
					});
				}
				if (!legalCheck) {
					art.dialog({
						content : "请选择面向法人对象信息!",
						icon : "warning",
						ok : true
					});
					returnflag = false;
				}
			}
		});
		//部门服务选项
		checkIds += "402883494fd4f3aa014fd4fc68260003,";
		$("input[name='BUS_TYPEIDS']").val(checkIds);
		if ($("input:radio[name='SXXZ']:checked").val() == "GF" && $("input[type=checkbox][name='GFTYPE']:checked").length <= 0) {
			art.dialog({
				content : "请选择公共服务类别!",
				icon : "warning",
				ok : true
			});
			returnflag = false;
		}
		return returnflag;
	} else {
		art.dialog({
			content : "请选择申报对象和对象信息!",
			icon : "warning",
			ok : true
		});
		return false;
	}
}

/***********************************基本信息结束***********************************/

/***********************************申报方式***********************************/
function checkSbfs() {
	var returnflag = true;
	if ($("input:radio[name='RZBSDTFS']:checked").val() == null) {
		art.dialog({
			content : "请选择入驻办事大厅方式!",
			icon : "warning",
			ok : true
		});
		returnflag = false;
	}
	return returnflag;
}

function smallChange1(obj) {
	if (obj.checked == true) {
		var CKCS_1 = $("select[name='CKCS_1'] option:selected").val();
		if(CKCS_1==-1){
			$("select[name='CKCS_1'] option[value='1']").prop("selected",true);
			$("select[name='CKCS_1'] option[value='-1']").remove();
		}
		//$("input[name='CKCS_1']").attr("class", "eve-input validate[[required],custom[integer],max[3]]");
	} else {
		//$("input[name='CKCS_1']").attr("class", "eve-input validate[[],custom[integer],max[3]]");
		$("select[name='CKCS_1']").prepend("<option value='-1'>未承诺</option>");
		$("select[name='CKCS_1'] option[value='-1']").prop("selected",true);
	}
}
function smallChange2(obj) {
	if (obj.checked == true) {
		//$("input[name='CKCS_2']").attr("class", "eve-input validate[[required],custom[integer],max[2]]");
		$("#SQFS3").attr("checked", false);
		$("#SQFS4").attr("checked", false);
		$("select[name='CKCS_3'] option[value='-1']").prop("selected",true);
		$("select[name='CKCS_4'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_3']").val("");
		$("input[name='CNSM_4']").val("");
		$("input[name='SQZHYQ_3']").attr("checked",false);
		$("input[name='SQZHYQ_4']").attr("checked",false);
		/*$("input[name='CKCS_3']").attr("class", "eve-input validate[[],custom[integer],max[1]]");
		$("input[name='CKCS_4']").attr("class", "eve-input validate[[],custom[integer],max[1]]");*/
	} else {
		//$("input[name='CKCS_2']").attr("class", "eve-input validate[[],custom[integer],max[2]]");
		$("select[name='CKCS_2'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_2']").val("");
		$("input[name='SQZHYQ_2']").attr("checked",false);
	}
}
function smallChange3(obj) {
	if (obj.checked == true) {
		//$("input[name='CKCS_3']").attr("class", "eve-input validate[[required],custom[integer],max[1]]");
		$("#SQFS2").attr("checked", false);
		$("#SQFS4").attr("checked", false);
		$("select[name='CKCS_2'] option[value='-1']").prop("selected",true);
		$("select[name='CKCS_4'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_2']").val("");
		$("input[name='CNSM_4']").val("");
		$("input[name='SQZHYQ_2']").attr("checked",false);
		$("input[name='SQZHYQ_4']").attr("checked",false);
		/*$("input[name='CKCS_2']").attr("class", "eve-input validate[[],custom[integer],max[2]]");
		$("input[name='CKCS_4']").attr("class", "eve-input validate[[],custom[integer],max[1]]");*/
	} else {
		//$("input[name='CKCS_3']").attr("class", "eve-input validate[[],custom[integer],max[1]]");
		$("select[name='CKCS_3'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_3']").val("");
		$("input[name='SQZHYQ_3']").attr("checked",false);
	}
}
function smallChange4(obj) {
	if (obj.checked == true) {
		//$("input[name='CKCS_4']").attr("class", "eve-input validate[[required],custom[integer],max[1]]");
		$("#SQFS3").attr("checked", false);
		$("#SQFS2").attr("checked", false);
		$("select[name='CKCS_3'] option[value='-1']").prop("selected",true);
		$("select[name='CKCS_2'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_3']").val("");
		$("input[name='CNSM_2']").val("");
		$("input[name='SQZHYQ_3']").attr("checked",false);
		$("input[name='SQZHYQ_2']").attr("checked",false);
		/*$("input[name='CKCS_2']").attr("class", "eve-input validate[[],custom[integer],max[2]]");
		$("input[name='CKCS_3']").attr("class", "eve-input validate[[],custom[integer],max[1]]");*/
	} else {
		//$("input[name='CKCS_4']").attr("class", "eve-input validate[[],custom[integer],max[1]]");
		$("select[name='CKCS_4'] option[value='-1']").prop("selected",true);
		$("input[name='CNSM_4']").val("");
		$("input[name='SQZHYQ_4']").attr("checked",false);
	}
}
/***********************************申报方式结束***********************************/

/***********************************时限配置***********************************/
function dayformatter(val, row) {
	return val + "工作日";
}
//根据办件类型 设置即办件承诺时限
function setJBJCNSX(o) {
	if (o == 1) {
		//即办件时 承诺时限设为0 并设为只读
		$("input[name='CNQXGZR']").val("0");
		$("input[name='CNQXGZR']").attr("readonly", "readonly");
		$("input[name='CKCS_1']").val("1");
	} else {
		//其他时 承诺时限设为空  并设为非只读
		$("input[name='CNQXGZR']").val("");
		$("input[name='CNQXGZR']").removeAttr("readonly");
	}
}

function editTshjGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("tshjGrid");
	if (entityId) {
		showTshjWindow(entityId);
	}
}

function showTshjWindow(entityId) {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var itemStatus = $("input[name='FWSXZT']").val();
	if (itemStatus == 1) {
		art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
			$.dialog.open("departServiceItemController.do?specialLinkInfo&entityId=" + entityId + "&itemId=" + itemId, {
				title : "特殊信息",
				width : "700px",
				height : "500px",
				lock : true,
				resize : false,
				close : function() {
					$("#tshjGrid").datagrid("reload");
					$("#tshjGrid").datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		})
	} else {
		$.dialog.open("departServiceItemController.do?specialLinkInfo&entityId=" + entityId + "&itemId=" + itemId, {
			title : "特殊信息",
			width : "600px",
			height : "500px",
			lock : true,
			resize : false,
			close : function() {
				$("#tshjGrid").datagrid("reload");
				$("#tshjGrid").datagrid('clearSelections').datagrid('clearChecked');
			}
		}, false);
	}
}

function deleteTshjGridRecord() {
	var itemId = $("input[name='ITEM_ID']").val();
	AppUtil.deleteDataGridRecordBack("departServiceItemController.do?multiDelTshj&itemId=" + itemId,
		"tshjGrid");
}
/***********************************时限配置结束***********************************/

/***********************************办公指引***********************************/
function selectChildDepartName() {
	var departId = $("input[name='CHILD_DEPART_ID']").val();
	var parentId = $("input[name='DEPART_ID']").val();
	if (parentId == null || parentId == "") {
		parent.art.dialog({
			content : "请先选择事项",
			icon : "warning",
			ok : true
		});
		return false;
	}
	parent.$.dialog.open("departmentController/childSelector.do?rootParentId=" + parentId + "&departIds=" + departId + "&allowCount=1&checkCascadeY=&"
		+ "checkCascadeN=", {
			title : "主办处室选择器",
			width : "700px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if (selectDepInfo) {
					$("input[name='ZBCS']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
}

function selectSSDW(){
	parent.$.dialog.open("departmentController/parentSelector.do?allowCount=1&checkCascadeY=&"
		+ "checkCascadeN=", {
			title : "实施单位选择器",
			width : "700px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if (selectDepInfo) {
					$("input[name='DEPART_ID']").val(selectDepInfo.departIds);
					$("input[name='IMPL_DEPART_ID']").val(selectDepInfo.departIds);
					$("input[name='IMPL_DEPART']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
}

function selectDepartName2() {
	var parentId = $("input[name='IMPL_DEPART_ID']").val();
	if(parentId==''||parentId==undefined||parentId==null){
		parent.art.dialog({
			content : "请先选择实施单位",
			icon : "warning",
			ok : true
		});
		return false;
	}
	parent.$.dialog.open("departmentController/childSelector.do?rootParentId="+parentId+"&allowCount=1&checkCascadeY=&"
		+ "checkCascadeN=", {
			title : "主办处室选择器",
			width : "700px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if (selectDepInfo) {
					$("input[name='ZBCS']").val(selectDepInfo.departNames);
					$("input[name='ZBCS_ID']").val(selectDepInfo.departIds);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);

}

function selectDepartName() {
	parent.$.dialog.open("departmentController/selector.do?allowCount=1&checkCascadeY=&"
		+ "checkCascadeN=", {
			title : "会办处室选择",
			width : "700px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if (selectDepInfo) {
					$("input[name='HBCS']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);

}
/***********************************办公指引结束*******************************/

/***********************************申请材料***********************************/
function updateDicSn() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var rows = $("#ApplyMaterGrid").datagrid("getRows");
	var materIds = [];
	for (var i = 0; i < rows.length; i++) {
		materIds.push(rows[i].MATER_ID);
	}
	if (materIds.length > 0) {
		AppUtil.ajaxProgress({
			url : "applyMaterController.do?updateSn",
			params : {
				itemId : itemId,
				materIds : materIds
			},
			callback : function(resultJson) {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
				$("#ApplyMaterGrid").datagrid("reload");
			}
		})
	}

}

function formatIsNeed(val, row) {
	if (val == "1") {
		return "是";
	} else if (val == "0") {
		return "否";
	}
}
function formatMaterSource(val, row) {
	if (row.MATER_SOURCE_TYPE == '本单位') {
		return "本单位";
	} else {
		return val;
	}
}
function formatBusclassName(val, row) {
	if (val == "无") {
		return "";
	} else {
		return val;
	}
}
function formatclfl(val, row) {
	if (val == "01") {
		return "申请书";
	} else if (val == "02") {
		return "文件";
	} else if (val == "03") {
		return "光盘";
	} else if (val == "04") {
		return "图纸";
	} else if (val == "05") {
		return "其它";
	}
}
function formatxyyj(val, row) {
	if (val == '0' && row.MATER_CLSMLX != '原件') {
		return "否";
	} else if (val == '1') {
		return "是";
	}
}
/**
 * 编辑申请材料列表记录
 */
function editApplyMaterGridRecord() {
	var MATER_ID = AppUtil.getEditDataGridRecord("ApplyMaterGrid");
	if (MATER_ID) {
		showApplyMaterWindow(MATER_ID);
	}
}

/**
 * 显示申请材料信息对话框
 */
function showApplyMaterWindow(entityId) {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var itemStatus = $("input[name='FWSXZT']").val();
	var onlyCode = $("input[name='SWB_ITEM_CODE']").val();
	if (itemStatus == 1) {
		art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
			$.dialog.open("applyMaterController.do?infoDepart&entityId=" + entityId + "&itemId=" + itemId +"&onlyCode="+onlyCode,
				{
					title : "申请材料信息",
					width : "750px",
					height : "500px",
					lock : true,
					resize : false,
					close : function() {
						$("input[name='Q_SM.ITEM_ID_EQ']").val(itemId);
						AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
						//$.fn.zTree.getZTreeObj("handleTypeTree").reAsyncChildNodes(null, "refresh");
					}
				}, false);
		})
	} else {
		$.dialog.open("applyMaterController.do?infoDepart&entityId=" + entityId + "&itemId=" + itemId +"&onlyCode="+onlyCode,
			{
				title : "申请材料信息",
				width : "700px",
				height : "500px",
				lock : true,
				resize : false,
				close : function() {
					$("input[name='Q_SM.ITEM_ID_EQ']").val(itemId);
					AppUtil.gridDoSearch("ApplyMaterToolbar", "ApplyMaterGrid");
					//$.fn.zTree.getZTreeObj("handleTypeTree").reAsyncChildNodes(null, "refresh");
				}
			}, false);
	}
}

function isneedApplyMaterGridRecord() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	updateIsneedApplyMater("applyMaterController.do?updateIsneed&isneed=1&itemId=" + itemId, "ApplyMaterGrid");
}
function noneedApplyMaterGridRecord() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	updateIsneedApplyMater("applyMaterController.do?updateIsneed&isneed=0&itemId=" + itemId, "ApplyMaterGrid");
}
//更新材料是否为必须提供
function updateIsneedApplyMater(url, gridId) {
	var $dataGrid = $("#" + gridId);
	var rowsData = $dataGrid.datagrid('getChecked');
	if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
		art.dialog({
			content : "请选择需要修改的记录!",
			icon : "warning",
			ok : true
		});
	} else {
		art.dialog.confirm("您确定要修改该记录吗?", function() {
			var layload = layer.load('正在提交请求中…');
			var colName = $dataGrid.datagrid('options').idField;
			var selectColNames = "";
			for (var i = 0; i < rowsData.length; i++) {
				if (i > 0) {
					selectColNames += ",";
				}
				selectColNames += eval('rowsData[i].' + colName);
			}
			$.post(url, {
				selectColNames : selectColNames
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
					$dataGrid.datagrid('reload');
					$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
				} else {
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			});
		});
	}
}

function deleteBindApplyMater() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	AppUtil.deleteDataGridRecordBack("serviceItemController.do?delmaters&itemId=" + itemId,
		"ApplyMaterGrid");
}
/***********************************申请材料结束***********************************/

/***********************************收费配置***********************************/
function amountformatter(val, row) {
	return val + " " + row.UNITS;
}

function editSfmxGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("sfmxGrid");
	if (entityId) {
		showSfmxWindow(entityId);
	}
}

function showSfmxWindow(entityId) {
	var itemStatus = $("input[name='FWSXZT']").val();
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	if (itemStatus == 1) {
		art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
			$.dialog.open("departServiceItemController.do?chargeInfo&entityId=" + entityId + "&itemId=" + itemId, {
				title : "收费明细信息",
				width : "550px",
				height : "350px",
				lock : true,
				resize : false,
				close : function() {
					$("#sfmxGrid").datagrid("reload");
					$("#sfmxGrid").datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		})
	}else{
		$.dialog.open("departServiceItemController.do?chargeInfo&entityId=" + entityId + "&itemId=" + itemId, {
			title : "收费明细信息",
			width : "550px",
			height : "350px",
			lock : true,
			resize : false,
			close : function() {
				$("#sfmxGrid").datagrid("reload");
				$("#sfmxGrid").datagrid('clearSelections').datagrid('clearChecked');
			}
		}, false);
	}
}

function deleteSfmxGridRecord() {
	var itemId = $("input[name='ITEM_ID']").val();
	AppUtil.deleteDataGridRecordBack("departServiceItemController.do?multiDelCharge&itemId=" + itemId,
		"sfmxGrid");
}
/***********************************收费配置结束*******************************/

/***********************************自查条件***********************************/

/**
 * 点击树形节点触发的事件
 */
function onHandleTypeTreeClick(event, treeId, treeNode, clickFlag) {
	if (event.target.tagName == "SPAN") {
		if(treeNode.id=='0'){
			$("#handleTypeToolbar input[name='Q_t.BUS_HANDLE_TYPE_=']").val('');
		}else{
			$("#handleTypeToolbar input[name='Q_t.BUS_HANDLE_TYPE_=']").val(treeNode.id);
		}
		AppUtil.gridDoSearch("handleTypeToolbar", "handleTypeGrid");
	}
}

function deleteSelfExamGridRecord(){
	AppUtil.deleteDataGridRecord("departServiceItemController.do?multiDelSelfExam", "handleTypeGrid");
}

function editSelfExamGridRecord(){
    var entityId = AppUtil.getEditDataGridRecord("handleTypeGrid");
    if (entityId) {
    	var handleType = $("input[name='Q_t.BUS_HANDLE_TYPE_=']").val();
    	var itemId = $("input[name='ITEM_ID']").val();
	    $.dialog.open("departServiceItemController.do?selfExamInfo&itemId="+itemId+"&handleType="+encodeURI(handleType)+"&entityId=" + entityId, {
	        title : "自查条件信息",
	        width:"600px",
	        height:"300px",
	        lock: true,
	        resize:false,
			close : function() {
				$("#handleTypeGrid").datagrid('reload');
				$("#handleTypeGrid").datagrid('clearSelections').datagrid('clearChecked');
			}
	    }, false);
    }
}

function showSelfExamWindow(){
	var handleType = $("input[name='Q_t.BUS_HANDLE_TYPE_=']").val();
	var itemId = $("input[name='ITEM_ID']").val();
	if(handleType!=''&&handleType!='undefined'&&handleType!=0){
		var url = "departServiceItemController.do?selfExamInfo&itemId="+itemId+"&handleType="+encodeURI(handleType);
	    $.dialog.open(url, {
	        title : "自查条件信息",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"320px",
			close : function() {
				$("#handleTypeGrid").datagrid('reload');
				$("#handleTypeGrid").datagrid('clearSelections').datagrid('clearChecked');
			}
	    }, false);
	}else{
		art.dialog({
			content : "请选择业务办理分类",
			icon : "warning",
			ok : true
		});
	}
}
/***********************************自查条件结束*******************************/

/***********************************常见问题***********************************/

//删除常见问题列表记录
function deleteCommonProblemGridRecord() {
	AppUtil.deleteDataGridRecord("commonProblemController.do?multiDel",
		"CommonProblemGrid");
}
//编辑常见问题列表记录
function editCommonProblemGridRecord() {
	var entityId = AppUtil.getEditDataGridRecord("CommonProblemGrid");
	if (entityId) {
		showCommonProblemWindow(entityId);
	}
}
//显示常见问题信息对话框
function showCommonProblemWindow(entityId) {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	$.dialog.open("commonProblemController.do?info&entityId=" + entityId + "&itemId=" + itemId, {
		title : "常见问题信息",
		width : "800px",
		height : "500px",
		lock : true,
		resize : false,
		close : function() {
			$("#CommonProblemGrid").datagrid("reload");
			$("#CommonProblemGrid").datagrid('clearSelections').datagrid('clearChecked');
		}
	}, false);
}
/***********************************常见问题结束***********************************/

/***********************************预审人员配置***********************************/

function selectAuditUser() {
	var userIds = $("input[name='PREAUDITER_IDS']").val();
	parent.$.dialog.open("sysUserController.do?selector&allowCount=30&noAuth=true&userIds="
		+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectUserInfo = art.dialog.data("selectUserInfo");
				if (selectUserInfo && selectUserInfo.userIds) {
					$("input[name='PREAUDITER_IDS']").val(selectUserInfo.userIds);
					//$("input[name='PREAUDITER_NAMES']").val(selectUserInfo.userNames);
					addtoTD("auditusernames", selectUserInfo.userNames);
					var username = selectUserInfo.userNames.split(",");
					var mobile = selectUserInfo.mobiles.split(",");
					var rec = "";
					for (var i = 0; i < username.length; i++) {
						if (i > 0) {
							rec += ","
						}
						rec += username[i];
						if (mobile[i] == "undefined" || mobile[i] == "") {
							rec += "()";
						} else {
							rec += "(" + mobile[i] + ")";
						}
					}
					$("input[name='MESSAGE_REC']").val(rec);
					art.dialog.removeData("selectUserInfo");
				}
			}
		}, false);
}

function saveAuditUsers(currentStep) {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var userIds = $("input[name='PREAUDITER_IDS']").val();
	var nextStep = parseInt(currentStep) + 1;
	if (userIds) {
		AppUtil.ajaxProgress({
			url : "serviceItemController.do?bindUsers",
			params : {
				"itemId" : itemId,
				"userIds" : userIds
			},
			callback : function(resultJson) {
				if (resultJson.success) {
					//changeNextStepPro(currentStep,nextStep);
					submitInfo(currentStep);
				} else {
					parent.art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			}
		});
	} else {
		changeNextStepPro(currentStep, nextStep);
	}
}
/***********************************预审人员配置结束***********************************/

/***********************************办理结果***********************************/
function checkFinishGet() {
	var returnflag = true;
	if ($("input[type=checkbox][name='FINISH_TYPE']:checked").length <= 0) {
		art.dialog({
			content : "请选择办理结果!",
			icon : "warning",
			ok : true
		});
		returnflag = false;
	} else if ($("input[type=checkbox][name='FINISH_GETTYPE']:checked").length <= 0) {
		art.dialog({
			content : "请选择办理结果获取方式!",
			icon : "warning",
			ok : true
		});
		returnflag = false;
	}
	return returnflag;
}

function selectLicenseCatalog(){
	var BINDCATALOG_CODE = $("input[name='RESULT_BINDCATALOG_CODE']").val();
	parent.$.dialog.open("licenseCatalogController.do?licenseCatalogSelector&BINDCATALOG_CODE="+BINDCATALOG_CODE, {
			title : "共享材料目录选择器",
			width : "1100px",
			lock : true,
			resize : false,
			height : "500px", 
			close : function() {
				var selectCatalogs = art.dialog.data("selectCatalogs");
				if (selectCatalogs) {
					$("input[name='RESULT_BINDCATALOG_CODE']").val(selectCatalogs.CatalogCodes);
					$("input[name='RESULT_BINDCATALOG_NAME']").val(selectCatalogs.CatalogNames);
				}
				art.dialog.removeData("selectCatalogs");
			}
		}, false);
}
/***********************************办理结果结束***********************************/

/***********************************流程配置***********************************/
function addtoTD(id, values) {
	var v = values.split(",");
	var showtext = "";
	for (var i = 0; i < v.length; i++) {
		showtext += "<p>" + v[i] + "</P>";
	}
	$("#" + id).html(showtext);
}

function selectFlowDef() {
	var defIds = $("input[name='BDLCDYID']").val();
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var status = $("input[name='FWSXZT']").val();
	var currentStep = $("input[name='CurrentStep']").val();
	if (status == "1") {
		art.dialog.confirm("将撤至草稿库，并导致窗口无法办件（重新申请审核并获发布后方可恢复），您确定需要进行修改? ", function() {
			parent.$.dialog.open("flowDefController.do?deptDefSelector&allowCount=1&defIds=" + defIds, {
				title : "流程定义选择器",
				width : "800px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectDefInfo = art.dialog.data("selectDefInfo");
					if (selectDefInfo) {
						$("input[name='BDLCDYID']").val(selectDefInfo.defIds);
						AppUtil.ajaxProgress({
							url : "departServiceItemController.do?saveOrUpdate",
							params : {
								"ITEM_ID" : itemId,
								"BDLCDYID" : selectDefInfo.defIds,
								"currentStep" : currentStep
							},
							callback : function(resultJson) {
								addtoTD("defname", selectDefInfo.defNames);
								AppUtil.gridDoSearch("defToolbar", "defGrid");
								submitInfo(currentStep);
							}
						})
						art.dialog.removeData("selectDefInfo");
					}
				}
			}, false);
		});
	} else {
		parent.$.dialog.open("flowDefController.do?deptDefSelector&allowCount=1&defIds=" + defIds, {
			title : "流程定义选择器",
			width : "800px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectDefInfo = art.dialog.data("selectDefInfo");
				if (selectDefInfo) {
					$("input[name='BDLCDYID']").val(selectDefInfo.defIds);
					AppUtil.ajaxProgress({
						url : "departServiceItemController.do?saveOrUpdate",
						params : {
							"ITEM_ID" : itemId,
							"BDLCDYID" : selectDefInfo.defIds,
							"currentStep" : currentStep
						},
						callback : function(resultJson) {
							addtoTD("defname", selectDefInfo.defNames);
							AppUtil.gridDoSearch("defToolbar", "defGrid");
						}
					})
					art.dialog.removeData("selectDefInfo");
				}
			}
		}, false);
	}

}

function editNodeAuditer() {
	var entityId = AppUtil.getEditDataGridRecord("defGrid");
	if (entityId) {
		var rowsData = $("#defGrid").datagrid('getChecked');
		var nodeType = rowsData[0].NODE_TYPE;
		if (nodeType == 'start') {
			art.dialog({
				content : "开始环节无需设置办理人员！",
				icon : "warning",
				ok : true
			});
		} else if (nodeType == 'end') {
			art.dialog({
				content : "结束无需设置办理人员！",
				icon : "warning",
				ok : true
			});
		}
		showNodeAuditerWindow(entityId);
	}
}

function showNodeAuditerWindow(entityId) {
	$.dialog.open("departServiceItemController.do?auditerInfo&entityId=" + entityId, {
		title : "环节审核人设置",
		width : "700px",
		lock : true,
		resize : false,
		height : "500px",
		close : function() {
			AppUtil.gridDoSearch("defToolbar", "defGrid");
            $("#defGrid").datagrid('clearSelections').datagrid('clearChecked');
		}
	}, false);
}

function getBLLC() {
	var lcRows = $('#defGrid').datagrid('getRows');
	var lcTotal = '';
	for (var i = 0; i < lcRows.length; i++) {
		lcTotal += lcRows[i]['NODE_NAME'] + '-';
	}
	lcTotal = lcTotal.substring(0, lcTotal.length - 1);
	document.getElementById('bllc').value = lcTotal;
}

function blsxformater(val, row) {
	if (val != "" && val != undefined) {
		if (row.TIME_TYPE == 'gzr') {
			return val + "工作日";
		} else if (row.TIME_TYPE == 'zrr') {
			return val + "自然日";
		} else if (row.TIME_TYPE == 'y') {
			return val + "月";
		}else{
			return "";
		}
	}
}

function changeSfjzzwdt(val) {
	if (val == '1') {
		$("#ZWDTRZFS").show();
		$("#rzfsText").show();
	} else {
		$("#ZWDTRZFS").hide();
		$("#rzfsText").hide();
	}
}

// function changeSfzcwlkd(val) {
// 	if (val == '1') {
// 		$("[name='PAPERSUB'][value='2']").attr("disabled", false);
// 	} else {
// 		$("[name='PAPERSUB'][value='2']").attr("disabled", true).removeAttr("checked");
// 	}
// }

function changeSftgdbfw(val) {
	if (val == '1') {
		$("#dbsfsf").show();
	} else {
		$("#dbsfsf").hide();
	}
}

function changeYjbsfsf(val) {
	if (val == '0') {
		$("#mfydblx").show();
	} else {
		$("#mfydblx").hide();
	}
}

function changeSfsf(val) {
	if (val == '0') {
		$(".sfsfC").hide();
	} else {
		$(".sfsfC").show();
	}
}
/*************************************流程配置结束*************************************/


/*************************************操作日志结束*************************************/
//日志
function showDetailExplain(tableName, OPERATETIME, index) {
	var str = "&TABLENAME=" + tableName + "&INDEX=" + index + "&OPERATETIME=" + OPERATETIME;
	$.dialog.open("departServiceItemController.do?logDetail" + str, {
		title : "详细说明",
		width : "820px",
		height : "900px",
		lock : true,
		resize : false
	}, false);
}
/*************************************操作日志结束*************************************/