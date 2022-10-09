$(function() {
	
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//初始化表单字段值
		if(eflowObj.busRecord){				
			//初始化事项基本信息
			$("#SBXX_FORM input[name='SBMC']").val(eflowObj.busRecord.SBMC);			
			//初始化人事信息			
			//FlowUtil.initFormFieldValue(eflowObj.busRecord,"PERSONNEL_FORM");			
			//初始化申报人信息						
			FlowUtil.initFormFieldValue(eflowObj.busRecord,"USERINFO_FORM");
			//初始化其他信息						
			//FlowUtil.initFormFieldValue(eflowObj.busRecord,"OTHER_FORM");
			//经营形式
			setInvestFamily(eflowObj.busRecord.MANAGE_FORM);
			
			setZjValidate(eflowObj.busRecord.INVESTOR_IDTYPE,'INVESTOR_IDCARD');
			setZjValidate(eflowObj.busRecord.LIAISON_IDTYPE,'LIAISON_IDNO');
			setZjValidate(eflowObj.busRecord.FINANCE_IDTYPE,'FINANCE_IDNO');
			setZjValidate(eflowObj.busRecord.TAXMAN_IDTYPE,'TAXMAN_IDNO');
			var SSSBLX = eflowObj.busRecord.SSSBLX;
			if(SSSBLX && SSSBLX==1){				
				if(eflowObj.busRecord.ARMY_REGISTER_HOURSE){				
					setRequired(eflowObj.busRecord.ARMY_REGISTER_HOURSE,'ARMYHOURSE_REGISTER_REMARKS','03');
				}		
				$("#CAPITAL_FORM").find("input[type='text']").attr("disabled", "disabled");
				$("#CAPITAL_FORM").find("input[type='button']").attr("disabled", "disabled");
				$("#CAPITAL_FORM").find("input[type='checkbox']").attr("disabled", "disabled");
				$("#CAPITAL_FORM").find("select").attr("disabled", "disabled");
				$("input[name='TAG_OPTIOIN1']").attr("disabled",false); 
				$("input[name='TAG_OPTIOIN2']").attr("disabled",false); 
				if(eflowObj.busRecord.MANAGE_FORM==1){					
					$("input[name$='FAMILY_NAME']").attr("disabled",false); 
					$("input[name$='FAMILY_IDNO']").attr("disabled",false); 
				}
				if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){		
					//$("input[name='WORD_NUM']").attr("disabled",false);
					$("input[name='PLACE_PHONE']").attr("disabled",false);  
					$("input[name='VALIDITY_START_DATE']").attr("disabled",false); 
					$("input[name='VALIDITY_END_DATE']").attr("disabled",false); 
					$("input[name='LAW_EMAIL_ADDR']").attr("disabled",false); 
					$("input[name='LAW_FAX_ADDR']").attr("disabled",false); 
					$("input[name='LAW_IM_ADDR']").attr("disabled",false); 
					$("[name='ZH_NUM']").attr("disabled",false); 
					$("[name='INDIVIDUAL_NAME']").attr("disabled",false);
					$("[name='INDIVIDUAL_NAME']").attr("readonly",true); 	
					$("[name='WORD_NAME']").attr("disabled",false); 
					$("input[name='PUSH_STATUS_INTER']").val(0);
					//getGtptxx(eflowObj.busRecord.PT_ID);	

					//setInterval(function () {
					//	checkCompanyNameResult();
					//}, 1000 * 15);		
					if(eflowObj.EFLOW_EXERUNSTATUS!='0'){		
						AppUtil.ajaxProgress({
							url : "gtptxxglController/getTaskStatus.do",
							params : {
								exeId:eflowObj.EFLOW_EXEID
							},
							callback : function(resultJson) {
								if (resultJson.success) {	
									if(resultJson.data=='-1'){
										$("[name='WORD_NAME']").show();
										$("[name='ZH_NUM']").show();
										$("[name='ZH_NUM']").css("width","125px");
										$("[name='ZH_NUM']").addClass(" validate[required]");										
									} else{										
										$("[name='ZH_NUM']").hide();
										$("[name='WORD_NAME']").hide();	
									}
								} else {
									$("[name='ZH_NUM']").hide();
									$("[name='WORD_NAME']").hide();	
								}
							}
						});
					} else{						
						$("[name='ZH_NUM']").hide();
						$("[name='WORD_NAME']").hide();	
					}
						
				} else{					
					$("[name='ZH_NUM']").hide();
					$("[name='WORD_NAME']").hide();	
				}
				
				$("#wordNum").show();
				$("#yjfwA").hide();
				$("#yhmlA").hide();
				$("#sfzzmScA").hide();
				$("#sfzfmScA").hide();
				$("input[name='SSSBLX']").val(SSSBLX);
				$("input[name='PT_ID']").val(eflowObj.busRecord.PT_ID);	
				$("input[name='MANAGE_FORM'][value='0']").attr("checked","checked");		
				$("input[name='MANAGE_FORM']").attr("disabled", "disabled");		
				$("input[name='IS_REGISTER_ADDR'][value='1']").attr("checked","checked");				
				$("input[name='IS_REGISTER_ADDR']").attr("disabled", "disabled");	
				$("input[name='LAW_SEND_ADDR']").attr("disabled", "disabled");	
				$("#OTHER_FORM_A").remove();
				$("#OTHER_FORM").remove();
				$(".oldtr").remove();			
			} else {				
				$(".hymlTr").remove();	
				$("#lyydiv").remove();
				$(".newtr").remove();
				
			}
			$("input[name='ISNEEDSIGN']").val(eflowObj.busRecord.ISNEEDSIGN);
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){				
				//初始化字段审核意见
				setFieldAudit(eflowObj.EFLOW_EXEID);
			}
		}else{		
			var SSSBLX = $("input[name='SSSBLX']").val();
			if(SSSBLX && SSSBLX==1){				
				var PT_ID = $("input[name='PT_ID']").val();
				getGtptxx(PT_ID);
				$("input[name='ISNEEDSIGN']").val(1);
				$("input[name='MANAGE_FORM'][value='0']").attr("checked","checked");	
				$("input[name='MANAGE_FORM']").attr("disabled", "disabled");		
				$("input[name='IS_REGISTER_ADDR'][value='1']").attr("checked","checked");		
				$("input[name='IS_REGISTER_ADDR']").attr("disabled", "disabled");
				$("input[name='LAW_SEND_ADDR']").attr("disabled", "disabled");			
				
				$("#OTHER_FORM_A").remove();
				$("#OTHER_FORM").remove();
				$(".oldtr").remove();
				$("#wordNum").show();
				$("[name='ZH_NUM']").hide();
				$("[name='WORD_NAME']").hide();
				
				setInterval(function () {
					checkCompanyNameResult();
				}, 1000 * 15);
			} else{				
				$(".hymlTr").remove();
				$("#lyydiv").remove();
				$(".newtr").remove();
			}
			
			//设置采集时间
			$("[name='COLLECT_TIME']").val($("input[name='time']").val());
			//经营形式
			setInvestFamily(0);
		}
	}	
	/* //初始化上传
	AppUtil.initUploadControl({
		file_types : "*.jpg;*.jpeg;*.gif;*.png;",
		file_upload_limit : 1000,
		file_queue_limit : 1000,
		uploadPath : "photo",
		busTableName : "T_COMMERCIAL_INDIVIDUAL",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "IMAGE_PATH_BTN",
		uploadFileType : "image",
		uploadImageId : "IMAGE_PATH_IMG",
		uploadImageFieldName : "DEALER_PHOTO",
		limit_size : "4 MB",
		uploadSuccess : function(file, serverData, responseReceived) {
			var resultJson = $.parseJSON(serverData);
			var filePath = resultJson.filePath;
			$("#IMAGE_PATH_IMG").attr("src", __ctxPath+"/"+filePath);
			$("input[name='DEALER_PHOTO']").val(filePath);
		}
	}); */
	//点击经营形式
	$("input[name='MANAGE_FORM']").click(function(){
		//获取值
		var Value = $(this).val();
		setInvestFamily(Value);
	});
});

//设置字段审批意见
function getGtptxx(ptId){
	AppUtil.ajaxProgress({
		url : "gtptxxglController/getGtptxx.do",
		params : {
			PT_ID:ptId
		},
		callback : function(resultJson) {
			if (resultJson.success) {
				initFormFieldValueToReadOnly(resultJson.data,"CAPITAL_FORM");
				setRequired(resultJson.data.ARMY_REGISTER_HOURSE,'ARMYHOURSE_REGISTER_REMARKS','03');
				$("#LIAISON_SFZZM_PATH_IMG").attr("src",__file_server+resultJson.data.LIAISON_SFZZM);
				$("#LIAISON_SFZFM_PATH_IMG").attr("src",__file_server+resultJson.data.LIAISON_SFZFM);
				$("#yjfwA").hide();
				$("#yhmlA").hide();
				$("#sfzzmScA").hide();
				$("#sfzfmScA").hide();
				setIndividualName();
				$("input[name='LAW_SEND_ADDR']").val($("input[name='BUSINESS_PLACE']").val());
				$("[name='INDIVIDUAL_NAME']").attr("disabled",false); 		
			} else {
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		}
	});
}
var  dealerName = "";
function setIndividualName(){
	var SSSBLX = $("input[name='SSSBLX']").val();	
	var PT_NAME = $("input[name='PT_NAME']").val();
	var PT_HY = $("input[name='PT_HY']").val();
	var PT_ZZXS = $("input[name='PT_ZZXS']").val();
	var DEALER_NAME = $("input[name='DEALER_NAME']").val();	
	var WORD_NAME = $("input[name='WORD_NAME']").val();
	if(WORD_NAME!=null && WORD_NAME != ''){
		DEALER_NAME = WORD_NAME;
	}
	if(dealerName!=DEALER_NAME){
		$("input[name='IS_CHECKCOMPANYNAME']").val(0);
		$("input[name='COMPANYNAME_PASS']").val(0);	
	}
	dealerName = DEALER_NAME;
	var ZH_NUM = $("[name='ZH_NUM']").val();
	if(SSSBLX && SSSBLX==1){
		$("input[name='INDIVIDUAL_NAME']").val("平潭"+PT_NAME+DEALER_NAME+ZH_NUM+PT_HY+PT_ZZXS);
		$("input[name='WORD_NUM']").val(PT_NAME+DEALER_NAME+ZH_NUM);
	}
}



function checkCompanyName() {
	var WORD_NUM = $("input[name='WORD_NUM']").val();
	if (WORD_NUM == '') {
		art.dialog({
			content : "字号不能为空！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var DEALER_NAME = $("input[name='DEALER_NAME']").val();
	if (DEALER_NAME == null || DEALER_NAME == '') {
		art.dialog({
			content : "请填写经营者信息姓名！",
			icon : "warning",
			ok : true
		});
		return;
	}
	changeCheckCompanyNameText();
	$.post("exeDataController/pushCompanyNameToGt.do", {
		WORD_NUM: WORD_NUM,
		NAME_TYPE: 2
	}, function (responseText, status, xhr) {
		var resultJson = $.parseJSON(responseText);
		if (resultJson.success) {
			art.dialog({
				content: "正在查重中，请耐心等待!",
				icon: "warning",
				ok: true
			});
            //$("#checkNameDiv").html("正在查重中，请耐心等待......");
			$("input[name='IS_CHECKCOMPANYNAME']").val(1);
			checkCompanyNameResult();
			$("[name='ZH_NUM']").hide();
			$("[name='WORD_NAME']").hide();
		}
	});
}
function changeCheckCompanyNameText() {
    $("input[name='COMPANYNAME_PASS']").val(0);
    $("#checkCompanyNameId").removeAttr("onclick");
    var count = 180;
    var countdown = setInterval(CountDown, 1000);
    function CountDown() {
        if (count == 0) {
			$("#checkCompanyNameId").text("校验");
			$("#checkCompanyNameId").attr("onclick","checkCompanyName()");
			$("#checkCompanyNameId").css("width","60px");
            clearInterval(countdown);
        }else{
            $("#checkCompanyNameId").text(count+"秒后可重新校验");
			$("#checkCompanyNameId").css("width","140px");
        }
        count--;
    }
}
function checkCompanyNameResult() {
    var ischeck= $("input[name='IS_CHECKCOMPANYNAME']").val();
    if(ischeck=='1') {
        var WORD_NUM = $("input[name='WORD_NUM']").val();
        if (WORD_NUM !== '') {
            $.post("exeDataController/getCompanyNameResultToGt.do", {
                WORD_NUM: WORD_NUM,
                NAME_TYPE: 2
            }, function (responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.code == '-1') {
					var resultMsg = resultJson.msg;		
					if(resultMsg.indexOf("禁")>-1){				
						$("[name='WORD_NAME']").show();
						resultMsg += "，请修改字号中的经营者名称或者字号数字后重新校验！";
					} else {						
						$("[name='ZH_NUM']").addClass(" validate[required]");	
						resultMsg += "，请修改字号数字后重新校验！";
					} 
					$("[name='ZH_NUM']").show();
					$("[name='ZH_NUM']").css("width","125px");
					art.dialog({
						content: resultMsg,
						icon: "warning",
						ok: true
					});
                    $("input[name='IS_CHECKCOMPANYNAME']").val(0);	
					$("#checkNameDiv").html(resultMsg);					
                }
                if (resultJson.code == '1') {
                    art.dialog({
                        content:  resultJson.msg,
                        icon: "succeed",
                        ok: true
                    });
					$("#checkNameDiv").html("");	
                    $("input[name='IS_CHECKCOMPANYNAME']").val(0);
                    $("input[name='COMPANYNAME_PASS']").val(1);
                }
				if (resultJson.code == '00'&&resultJson.msg!='0') {
					var waitMsg="正在查重中，前面还有"+resultJson.msg+"人，请耐心等待......";
					$("#checkNameDiv").html(waitMsg);
				}
				if (resultJson.code == '00'&&resultJson.msg=='0') {
					$("#checkNameDiv").html("正在查重中，请耐心等待......");
				}
            });
        }
    }
}


//阿拉伯数字转中文数字
function NoToChinese(num) {
    if (!/^\d*(\.\d*)?$/.test(num)) {
        return "";
    }
    var AA = new Array("零", "一", "二", "三", "四", "五", "六", "七", "八", "九");
    var BB = new Array("", "十", "百", "千", "万", "亿", "点", "");
    var a = ("" + num).replace(/(^0*)/g, "").split("."),
        k = 0,
        re = "";
    for (var i = a[0].length - 1; i >= 0; i--) {
        switch (k) {
            case 0:
                re = BB[7] + re;
                break;
            case 4:
                if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
                    re = BB[4] + re;
                break;
            case 8:
                re = BB[5] + re;
                BB[7] = BB[5];
                k = 0;
                break;
        }
        if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re;
        if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
        k++;
    }
    if (a.length > 1) //加上小数部分(如果有小数部分) 
    {
        re += BB[6];
        for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
    }
    return re;
};
/**
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementId
 */
function initFormFieldValueToReadOnly(fieldValueObj,elementId){
	for(var fieldName in fieldValueObj){
		//获取目标控件对象
		var targetFields = $("#"+elementId).find("[name='"+fieldName+"']");
		targetFields.each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = fieldValueObj[fieldName];
			targetField.prop("readonly", true);
			targetField.prop("disabled", "disabled");	
			if(type=="radio"){
				var radioValue = targetField.val();
				if(radioValue==fieldValue){
					$(this).attr("checked","checked");
				}
			}else if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}else if(tagName=="SELECT"){
				targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
				.attr("selected", "selected");
			}else{
				targetField.val(fieldValueObj[fieldName]);
			}
		});
	}
}
function openGtptxxglImageUploadDialog(name){
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	/**
	 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	 */
	art.dialog.open(__ctxPath+'/fileTypeController.do?openWebStieUploadDialog&attachType=image&materType=image&busTableName=T_COMMERCIAL_INDIVIDUAL'
		+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
		title: "上传(图片)",
		width: "620px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					$("#"+name+"_PATH_IMG").attr("src",__file_server + uploadedFileInfo.filePath);
					$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
				}else{
					$("#"+name+"_PATH_IMG").attr("src",__ctxPath+'/webpage/common/images/nopic.jpg');
					$("input[name='"+name+"']").val('');
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}

/**
* 标题附件上传对话框
*/
function openDealerPthotoFileUploadDialog(){
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=image&materType=image&busTableName=T_COMMERCIAL_INDIVIDUAL'
	+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
		title: "上传(附件)",
		width: "620px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					$("#IMAGE_PATH_IMG").attr("src",__file_server + uploadedFileInfo.filePath);
					$("input[name='DEALER_PHOTO']").val(uploadedFileInfo.filePath);
				}else{
					$("#IMAGE_PATH_IMG").attr("src",__ctxPath+'/webpage/common/images/nopic.jpg');
					$("input[name='DEALER_PHOTO']").val('');
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
//设置字段审批意见
function setFieldAudit(exeId){
	$.post("fieldAuditController/datagrid.do?isShow=0&Q_T.EXE_ID_EQ="+exeId,{
	}, function(responseText, status, xhr) {
		var resultJson = responseText.rows;
		for(var i=0;i<resultJson.length;i++){	
			//console.log(resultJson[i].FIELD_NAME);
			var BELONG_ID =resultJson[i].BELONG_ID;
			var num = 0
			if(BELONG_ID!=null && BELONG_ID!=''){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
			}
			if(isNaN(num)){
			   num = 0;
			}
			$("[name$='"+resultJson[i].FIELD_NAME+"']").each(function(index){				
				if(num==index){
					var targetField = $(this);
					var type = targetField.attr("type");
					if(type=="checkbox"||type=="radio"){					
						if(index==0){
							targetField.parent().append('<div style="color:red;float:left;" class="zzhyZdshyj '+resultJson[i].RECORD_ID+'">审核意见：'
							+resultJson[i].AUDIT_OPINION+'   <img title="已读" src="plug-in/easyui-1.4/themes/icons/cancel.png" onclick="updateFieldAudit(\''+resultJson[i].RECORD_ID+'\')" style="cursor:pointer;width: 12px; height: 12px;vertical-align:middle;"></div>');					
						}
					}else{
						
						targetField.parent().append('<div style="color:red;float:left;" class="zzhyZdshyj '+resultJson[i].RECORD_ID+'">审核意见：'
						+resultJson[i].AUDIT_OPINION+'   <img title="已读" src="plug-in/easyui-1.4/themes/icons/cancel.png" onclick="updateFieldAudit(\''+resultJson[i].RECORD_ID+'\')" style="cursor:pointer;width: 12px; height: 12px;vertical-align:middle;"></div>');	
					}
				}				
			});
		}
	});
}
//更改字段审批意见状态
function updateFieldAudit(recordId){
	$.post("fieldAuditController/update.do",{
		RECORD_ID:recordId
	}, function(responseText, status, xhr) {
		var responseJson = JSON2.parse(responseText);
		if(responseJson.success){
			$("."+recordId).remove();			
		}
	});
}
function setInvestFamily(Value){
	if(Value=="1"){
		$("#family").show();
		$("input[name$='FAMILY_NAME']").attr("disabled",false);
		$("input[name$='FAMILY_IDNO']").attr("disabled",false);
	}else{
		$("#family").hide();
		$("input[name$='FAMILY_NAME']").attr("disabled","disabled");
		$("input[name$='FAMILY_IDNO']").attr("disabled","disabled");
		$("input[name$='FAMILY_NAME']").val();
		$("input[name$='FAMILY_IDNO']").val();
	}
}

//提交流程按钮
function FLOW_SubmitFun() {
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var SSSBLX = $("input[name='SSSBLX']").val();
    if (SSSBLX == '1') {
        var companynamePass=$("input[name='COMPANYNAME_PASS']").val();
		companynamePass="1";
        if(companynamePass=='0'){
            art.dialog({
                content:"名称未校验或校验未通过，请检验名称！",
                icon:"warning",
                ok: true
            });
            return;
        }
		var INDIVIDUAL_NAME = $("input[name='INDIVIDUAL_NAME']").val();
		var WORD_NUM = $("input[name='WORD_NUM']").val();
		if(INDIVIDUAL_NAME.indexOf(WORD_NUM)<0){//名称中不存在字号不能提交			
            art.dialog({
                content:"名称不包含字号，请重新检验名称！",
                icon:"warning",
                ok: true
            });
            return;
		}
	}
	//验证表单是否合法
	var valiateTabForm = AppUtil.validateWebsiteTabForm();
	/*var DEALER_PHOTO =  $("input[name='DEALER_PHOTO']").val();
	if(DEALER_PHOTO==null||DEALER_PHOTO==''){
		art.dialog({
			content : "请上传照片！",
			icon : "error",
			ok : true
		});
		return;
	}*/
	if (valiateTabForm) {
		//获取流程信息对象JSON
		 var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		 //将其转换成JSON对象
		 var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
		 //$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

		 //先获取表单上的所有值
		var forms = $("form[id]");
		forms.each(function() {
			var formId = $(this).attr("id");
			var formData = FlowUtil.getFormEleData(formId);
			for ( var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
		});	
		 flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		 //获取家庭成员信息
		 flowSubmitObj.FAMILY_JSON =getJtcyxxJson();
		 //获取房地产经纪机构及其分支机构备案JSON
		 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
		 //广告发布单位广告从业、审查人员JSON
		 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
		 
		 var postParam = $.param(flowSubmitObj);
		 AppUtil.ajaxProgress({
				url : "webSiteController.do?submitApply",
				params : postParam,
				callback : function(resultJson) {
					if(resultJson.OPER_SUCCESS){
						art.dialog({
							content : resultJson.OPER_MSG,
							icon : "succeed",
							lock : true,
							ok:function(){
								window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
							},
							close: function(){
								window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
							}
						});
					}else{
						art.dialog({
							content : resultJson.OPER_MSG,
							icon : "error",
							ok : true
						});
					}
				}
			});
	}
}

//流程暂存操作
function FLOW_TempSaveFun() {
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	//将其转换成JSON对象
	var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
	//$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

	//先获取表单上的所有值
	var forms = $("form[id]");
	forms.each(function() {
		var formId = $(this).attr("id");
		var formData = FlowUtil.getFormEleData(formId);
		for ( var index in formData) {
			flowSubmitObj[eval("index")] = formData[index];
		}
	});	
	 flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
	 //获取家庭成员信息
	 flowSubmitObj.FAMILY_JSON =getJtcyxxJson();
	 //获取房地产经纪机构及其分支机构备案JSON
	 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
	 //广告发布单位广告从业、审查人员JSON
	 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
	 var postParam = $.param(flowSubmitObj);
	 AppUtil.ajaxProgress({
			url : "webSiteController.do?submitApply",
			params : postParam,
			callback : function(resultJson) {
				if(resultJson.OPER_SUCCESS){
					art.dialog({
						content : "保存成功,草稿数据只保存90天，过期系统自动清理!草稿信息可在个人中心我的办件中查看!",
						icon : "succeed",
						cancel:false,
						lock: true,
						ok:function(){
							window.top.location.href=__newUserCenter;
						},
						close: function(){
							window.top.location.href=__newUserCenter;
						}
					});
				}else{
					art.dialog({
						content : resultJson.OPER_MSG,
						icon : "error",
						ok : true
					});
				}
			}
		});
	
}
/**
*  流程回退操作
**/
function FLOW_Recover(){	
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		art.dialog.confirm("您确定要撤回该流程吗?", function() {
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			$.post("executionController.do?getBackMyApply",{
				exeId:eflowObj.EFLOW_EXEID
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						lock : true,
						ok:function(){
							window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
						},
						close: function(){
							window.top.location.href=__ctxPath+"/webSiteController.do?usercenter";
						}
					});
				}else{
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

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="SF"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}

/**
* 经营范围选择器
**/
function showSelectJyfw(){
	var induIds = $("input[name='BUSINESS_SCOPE_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=-1";
	$.dialog.open(url, {
		title : "经营范围选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='BUSINESS_SCOPE']").val(selectInduInfo.induNames+"。（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
				$("[name='BUSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				art.dialog.removeData("selectInduInfo");
			}else{				
				$("[name='BUSINESS_SCOPE']").val('');
				$("[name='BUSINESS_SCOPE_ID']").val('');
			}
		}
	}, false);
};
/**
* 经营范围选择器
**/
function showSelectJyfwNew(){
	var ids = $("input[name='BUSINESS_SCOPE_ID']").val();
	var defId=$("input[name='FLOW_DEFID']").val();
	parent.$.dialog.open("domesticControllerController.do?selectorNew&allowCount=30&noAuth=true&ids="
		+ ids, {
			title : "经营范围选择器",
			width : "850px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectBusScopeInfo = art.dialog.data("selectBusScopeInfo");
				if (selectBusScopeInfo && selectBusScopeInfo.ids) {
					$("[name='BUSINESS_SCOPE_ID']").val(selectBusScopeInfo.ids);
					$("[name='BUSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
				}
			}
		}, false);
};
/**
* 投资行业选择器
**/
function showSelectTzhy(){
	var induIds = $("input[name='INVEST_INDUSTRY_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=1";
	$.dialog.open(url, {
		title : "投资行业选择器",
		width:"700px",
		lock: true,
		resize:false,
		height:"400px",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='INVEST_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='INVEST_INDUSTRY_ID']").val(selectInduInfo.induIds);
				$("[name='INDUSTRY_CODE']").val(selectInduInfo.induCodes);
				art.dialog.removeData("selectInduInfo");
			}else{				
				$("[name='INVEST_INDUSTRY']").val('');
				$("[name='INVEST_INDUSTRY_ID']").val('');
				$("[name='INDUSTRY_CODE']").val('');
			}
		}
	}, false);
};

/**
* 负面清单选择器
**/
function showSelectFmqd(){
	var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();
	
	$.dialog.open("negativeListController.do?selector&allowCount=0&negativeListCodes="+negativeListCodes, {
		title : "负面清单选择器",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var negativeListInfo = art.dialog.data("negativeListInfo");
			if(negativeListInfo){
				$("[name='NEGATIVELIST_CODES']").val(negativeListInfo.negativeListCodes);
				$("[name='NEGATIVELIST_NAMES']").val(negativeListInfo.negativeListNames);
				art.dialog.removeData("negativeListInfo");
			}
		}
	}, false);
};
/**
* 选择负面清单之后显示“批准证书编号”、“进出口企业代码”、“批准文号”
*/
function showFmqdTr(value){
	if (value == "1") {			
		$(".fmqd_tr").show();	
		$("input[name='CERTIFICATE_NO']").attr("disabled",false);
		$("input[name='CERTIFICATE_NO']").addClass(" validate[required]");		
		
		$("input[name='IMPANDEXP_CODE']").attr("disabled",false);
		$("input[name='IMPANDEXP_CODE']").addClass(" validate[required]");
		
		$("input[name='APPROVAL_NO']").attr("disabled",false);
		$("input[name='APPROVAL_NO']").addClass(" validate[required]");	
	} else {	
		$(".fmqd_tr").hide();		
		$("input[name='CERTIFICATE_NO']").attr("disabled",true); 
		$("input[name='CERTIFICATE_NO']").removeClass("validate[required]");	
		
		$("input[name='IMPANDEXP_CODE']").attr("disabled",true); 
		$("input[name='IMPANDEXP_CODE']").removeClass("validate[required]");	
		
		$("input[name='APPROVAL_NO']").attr("disabled",true); 
		$("input[name='APPROVAL_NO']").removeClass("validate[required]");	
	}
}
/**
* 输入数字且小数点之后只能出现2位
**/
function onlyNumber(obj){       
	//得到第一个字符是否为负号  
	var t = obj.value.charAt(0);    
	//先把非数字的都替换掉，除了数字和.   
	obj.value = obj.value.replace(/[^\d\.]/g,'');     
	//必须保证第一个为数字而不是.     
	obj.value = obj.value.replace(/^\./g,'');     
	//保证只有出现一个.而没有多个.     
	obj.value = obj.value.replace(/\.{2,}/g,'.');     
	//保证.只出现一次，而不能出现两次以上     
	obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
	//只能输入小数点后两位
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加  
	if(t == '-'){  
		obj.value = '-'+obj.value;  
	}   
}  

/*************家庭成员信息JS开始****************/
/**
 * 添加家庭成员信息
 */
function addJtcyxxDiv(){
	$.post("individualController/refreshJtcyxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jtcyxxDiv").append(responseText);
	});
}

/**
 * 删除家庭成员信息
 */
function delJtcyxxDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取家庭成员信息
 */
function getJtcyxxJson(){
	var jtcyxxArray = [];
	var MANAGE_FORM = $("input[name='MANAGE_FORM']:checked").val();
	if(MANAGE_FORM==1){
		$("#jtcyxxDiv div").each(function(i){
			var FAMILY_NAME = $(this).find("[name$='FAMILY_NAME']").val();//姓名
			var FAMILY_IDNO = $(this).find("[name$='FAMILY_IDNO']").val();//身份证号码
			if((null!=FAMILY_NAME&&FAMILY_NAME!="")||(null!=FAMILY_IDNO&&FAMILY_IDNO!="")){
				var jtcyxx = {};
				jtcyxx.FAMILY_NAME = FAMILY_NAME;
				jtcyxx.FAMILY_IDNO = FAMILY_IDNO;		
				jtcyxxArray.push(jtcyxx);	
			}
			
		});		
	}
	if(jtcyxxArray.length>0){
		return JSON.stringify(jtcyxxArray);
	}else{
		return "";
	}
}
/*************家庭成员信息JS结束****************/



/**
 * 下载附件
 * @param {} fileId
 */
function downLoadFile(fileId){
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//console.log(EFLOW_FLOWOBJ);
		//初始化表单字段值
		if(eflowObj.busRecord){			
			window.location.href=__ctxPath+"/domesticControllerController/downLoad.do?fileId="+fileId
			+"&exeId="+eflowObj.busRecord.EXE_ID
			+"&itemCode="+eflowObj.busRecord.ITEM_CODE;
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
}



/**
* 打印事项
**/
function printItem(){	
	var itemCodes = $("input[name='ITEMCODES']").val();
	var itemNames = $("textarea[name='ITEMNAMES']").val();
	if(!itemCodes) {
		art.dialog({
			content : "请先选择关联事项!",
			icon : "error",
			ok : true
		});
		return;
	}
	parent.$.dialog.open("domesticControllerController/printItem.do?itemCodes="+itemCodes + '&itemNames=' + itemNames, {
		title : "打印事项",
		width:"1100px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
		}
	});
}

/**
 *当值为val1时，设置为必填
 * @param val
 * @param inputName
 * @param otherValue
 */
function setRequired(val,inputName,val1){
    if (val===val1) {
        $("input[name="+inputName+"]").attr("disabled",false);
        $("input[name="+inputName+"]").addClass(" validate[required]");
    } else {
        $("input[name="+inputName+"]").attr("disabled",true);
        $("input[name="+inputName+"]").removeClass(" validate[required]");
        $("input[name="+inputName+"]").val('');
    }
}