/***********************************初始化**********************************/

var progressBarUnit = 7.69;
$(function() {

	var isaudit = $("input[name='shan']").val();
	if (isaudit != null && isaudit != '') {
		//$("#undertake").attr("style", "");
		$("#billtype").attr("style", "");
	}

	var qlly = '${serviceItem.RIGHT_SOURCE}';
	if (qlly == 'qt') {
		$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", false);
	}

	

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
	
	$("input[type=checkbox][name='SFFS']:checked").each(function() {
		if($(this).val()=='02'){
			$("input:radio[name='ONLINEPAY_SUPPORT'][value=1]").prop("checked",true);
		}
	});
	
	var sfsfval = $("input[name='SFSF']:checked").val();
	if(sfsfval=='1'){
		$("#SFFS_TR").attr("style", "");
		$("#SFBZJYJ_TR").attr("style", "");
		$("#SFFSSM_TR").attr("style", "");
		$("#SFZFQD_TR").attr("style", "");
	}else{
		$('#sfmxtable').hide();
	}
	
	//是否展开公共服务选项
	var gfvalue = $("input[name='SXXZ']:checked").val();
	if (gfvalue == "GF") {
		$("#gftype").attr("style", "");
	} else {
		$("#gftype").attr("style", "display:none;");
	}
	
	var oldsbtext = $("input[name='RZBSDTFS']:checked")[0].nextSibling.nodeValue;
	$("input[name='OLDRZBSDTFS_TEXT']").val(oldsbtext);
	
	//申报方式到窗口次数初始选项处理
	$("select[name='CKCS_1'] option[value='-1']").remove();
	$("select[name='CKCS_2'] option[value='3']").remove();
	$("select[name='CKCS_3'] option[value='2']").remove();
	$("select[name='CKCS_4'] option[value='2']").remove();
	$("select[name='CKCS_3'] option[value='3']").remove();
	$("select[name='CKCS_4'] option[value='3']").remove();

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
		$("input:radio[name='EXPRESS_SUPPORT'][value=1]").prop("checked",true);
	}
	$("input[type=checkbox][name='FINISH_GETTYPE']:checked").each(function() {
		if($(this).val()=='02'){
			$("input:radio[name='EXPRESS_SUPPORT'][value=1]").prop("checked",true);
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
	$("input[name='SXXZ']").attr("disabled", "disabled");
	//入驻方式不可更改
	//$("input[name='RZBSDTFS']").attr("disabled", "disabled");

	//结果样本初始化
	AppUtil.initUploadControl({
		file_types : "*.jpg;*.jpeg;*.png;*.bmp;",
		file_upload_limit : 0,
		file_queue_limit : 1,
		uploadPath : "resultSample",
		busTableName : "T_WSBS_APPLYMATER",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		//uploadButtonId : "RESULT_PATH_BTN",
		singleFileDivId : "RESULT_PATH_DIV",
		singleFileId : $("input[name='RESULT_PATH']").val(),
		singleFileFieldName : "RESULT_PATH",
		limit_size : "5 MB",
		uploadSuccess : function(resultJson) {
			$("input[name='RESULT_PATH']").val(resultJson.fileId);
		}
	});
	
	$("input[name='EXERCISE_LEVEL']").attr("disabled",true);
	$("input[name='RIGHT_SOURCE']").attr("disabled",true);
	


	$('#SysRoleGrantFormDiv').find('input,textarea').prop("readonly", true);
	$('#SysRoleGrantFormDiv').find('select').prop("disabled", "disabled");
	$('#SysRoleGrantFormDiv').find(":radio,:checkbox").attr('disabled',"disabled");
	$('#SysRoleGrantFormDiv').find(".laydate-icon").attr('disabled',"disabled");
	$('#SysRoleGrantFormDiv').find(":button").attr('style',"display:none");
});


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
	if (nextStep == 10) {
		var date = $("#tshjGrid").datagrid("getData");
		//alert(date.total);
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
	if (nextStep == 4) {
		$("#StepDiv_" + nextStep).css("display", "block");
		$("#tshjGrid").datagrid("resize");
	}else if (nextStep == 8) {
		$("#StepDiv_" + nextStep).css("display", "block");
		$("#sfmxGrid").datagrid("resize");
	}else if (nextStep == 6) {
		$("#StepDiv_" + nextStep).css("display", "block");
		$("#ApplyMaterGrid").datagrid("resize");
	}else if (nextStep == 11) {
		$("#StepDiv_" + nextStep).css("display", "block");
		$("#layout_" + nextStep).layout("resize");
	}else if (nextStep == 12) {
		$("#StepDiv_" + nextStep).css("display", "block");
		//获取事项ID
		var itemId = $("input[name='ITEM_ID']").val();
		$("#CommonProblemGrid").datagrid("resize");
		/*$("#CommonProblemGrid").datagrid({
			url : "commonProblemController.do?datagrid&Q_T.ITEM_ID_EQ=" + itemId
		});*/
	} else if (nextStep == 13) {
		$("#StepDiv_" + nextStep).css("display", "block");
		$("#defGrid").datagrid("resize");
	} else {
		$("#StepDiv_" + nextStep).attr("style", "");
	}
	$("input[name='CurrentStep']").val(nextStep);
	$("#flow_node_" + nextStep).attr("class", "flow_active");
	for (var index = nextStep - 1; index >= 1; index--) {
		$("#flow_node_" + index).attr("class", "flow_done");
	}
	if (nextStep == 14) {
		if (shan != null && shan != "") {
			$("#nextStepButton").val("保存");
			$("#nextStepButton").attr("disabled", "disabled");
		} else {
			$("#nextStepButton").val("保存");
		}
	}else if (nextStep == 15) {
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
	if (currentStep == 9) {
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
	} else if (currentStep == 1 || currentStep == 2 || currentStep == 3 || currentStep == 4 || currentStep == 5 || currentStep == 7 || currentStep == 8 || currentStep == 9 || currentStep == 10 || currentStep == 13 || currentStep == 14) {
		if (currentStep == 1 && !checkBusType()) {
			return false;
		}
		if (currentStep == 3 && !checkSbfs()) {
			return false;
		}
		if (currentStep == 8) {
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
		if (currentStep == 10) {
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
		if (currentStep == 13) {
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
			for (var i = 0; i < nodeData.length; i++) {
				if ((nodeData[i].USER_ACCOUNT == null || nodeData[i].USER_ACCOUNT == "undefined") && nodeData[i].NODE_TYPE != "start" && nodeData[i].NODE_TYPE != "end" && nodeData[i].NODE_NAME != "网上预审") {
					isfull = false;
					break;
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

//下一步
function toNext() {
	//获取事项ID
	var itemId = $("input[name='ITEM_ID']").val();
	var currentStep = $("input[name='CurrentStep']").val();
	if (currentStep == 1 && !checkBusType()) {
		return false;
	}
	if (currentStep == 3 && !checkSbfs()) {
		return false;
	}
	if (currentStep == 10 && !checkFinishGet()) {
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

/***********************************基础js结束**********************************/

/***********************************基本信息**********************************/

function qllyCheck(thiz) {
	if (thiz.value == 'qt') {
		$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", false);
	} else {
		$("input[name='RIGHT_SOURCE_OTHER']").attr("disabled", true);
	}
}

/***********************************基本信息结束***********************************/

/***********************************申报方式***********************************/

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

/***********************************时限配置结束***********************************/

/***********************************办公指引***********************************/

/***********************************办公指引结束*******************************/

/***********************************申请材料***********************************/

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

/***********************************申请材料结束***********************************/

/***********************************收费配置***********************************/
function amountformatter(val, row) {
	return val + " " + row.UNITS;
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

$(document).ready(function() {
	var itemId = $("input[name='ITEM_ID']").val();
	var handleTypeTreeSetting = {
		edit : {
			enable : false,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		view : {
			selectedMulti : false
		},
		callback : {
			onClick : onHandleTypeTreeClick
		},
        async : {
            enable : true,
            url : "applyMaterController.do?handleTypeTree&itemId="+itemId
        }
	};
	$.fn.zTree.init($("#handleTypeTree"), handleTypeTreeSetting);
});
/***********************************自查条件结束*******************************/

/***********************************常见问题***********************************/

/***********************************常见问题结束***********************************/

/***********************************预审人员配置***********************************/

/***********************************预审人员配置结束***********************************/

/***********************************办理结果***********************************/

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
/*************************************流程配置结束*************************************/


/*************************************操作日志结束*************************************/
/*************************************操作日志结束*************************************/