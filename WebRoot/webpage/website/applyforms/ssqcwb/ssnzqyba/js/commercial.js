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
			
			$("input:radio[name='OPRRATE_TERM_TYPE']").attr("disabled", "true").prop("disabled", "true");
			$("#gdxxDiv .syj-closebtn").remove();
			//初始化企业基本信息
			$("#COMPANY_FORM input[name='CANCEL_TYPE']").val(eflowObj.busRecord.CANCEL_TYPE);
			$("#COMPANY_FORM input[name='CANCEL_TYPENAME']").val(eflowObj.busRecord.CANCEL_TYPENAME);	
			//初始化备案选择
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#basxxzDiv"));	
			//初始化原董事信息
			//setOldDsxx(eflowObj.busRecord.OLD_DIRECTOR_JSON);
			//初始化监理信息
			//setJsxx(eflowObj.busRecord.SUPERVISOR_JSON);
			//setOldJsxx(eflowObj.busRecord.OLD_SUPERVISOR_JSON);
			//初始化经理信息
			setJlxx(eflowObj.busRecord.MANAGER_JSON);
			//setOldJlxx(eflowObj.busRecord.OLD_MANAGER_JSON);
			
			//$("[name='OPERATOR_IDTYPE']").attr("disabled",true);
			setZjValidate(eflowObj.busRecord.OPERATOR_IDTYPE,'OPERATOR_IDNO')
			//$("[name='LEGAL_IDTYPE']").attr("disabled",true);
			setZjValidate(eflowObj.busRecord.LEGAL_IDTYPE,'LEGAL_IDNO')
			
			//初始化人事信息			
			//FlowUtil.initFormFieldValue(eflowObj.busRecord,"PERSONNEL_FORM");			
			//初始化申报人信息						
			FlowUtil.initFormFieldValue(eflowObj.busRecord,"USERINFO_FORM");
			//初始化其他信息						
			//FlowUtil.initFormFieldValue(eflowObj.busRecord,"OTHER_FORM");
			
			//初始化经营期限
			setJyqx(eflowObj.busRecord.OPRRATE_TERM_TYPE);
			
			//初始化字段审核意见
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){	
				addOldDirectorInfo();
				setFieldAudit(eflowObj.EFLOW_EXEID);	
			}else{
				$("#itemMaterchoose").attr('style','display:none;');
				$("#itemMaterdel").attr('style','display:none;');
			}
			if(eflowObj.busRecord.RUN_STATUS==2){
				$("#itemMaterchoose").attr('style','display:none;');
				$("#itemMaterdel").attr('style','display:none;');
			}
			//initDisabled();
			
			var COMPANY_TYPECODE = eflowObj.busRecord.COMPANY_TYPECODE
			if(COMPANY_TYPECODE){
				initGsslxz(getSlxzType(COMPANY_TYPECODE));
			} else{				
				initGsslxz('gykg');
			}
			var BASX=eflowObj.busRecord.BASX;
			var companySetNature= eflowObj.busRecord.COMPANY_SETNATURE;//公司设立性质
			if(BASX){				
				if(BASX.indexOf("1")>-1){//董事备案
					$("#xdsxx").show();
					if(companySetNature){	
						if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){
							$("#addDsxx").show();
						} else{
							if($("#dsxxDiv div").length==0){			
								$("#addDsxx").show();
							} else{
								$("#addDsxx").hide();
							}						
						}
					}
				} 
				if(BASX.indexOf("2")>-1){//监理备案
					$("#xjsxx").show();
					if(companySetNature){	
						if(companySetNature=='04'){
							$("#addJsxx").show();
						} else{
							if($("#jsxxDiv div").length==0){			
								$("#addJsxx").show();
							} else{
								$("#addJsxx").hide();
							}						
						}
					}
				} 
				if(BASX.indexOf("3")>-1){//经理备案
					$("#xjlxx").show();
				}
			}
			
            //邮寄公章信息
            if (eflowObj.busRecord.ISEMAIL == 1) {
                $("#emailInfo").attr("style", "");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "1");
            } else {
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "-1");
            }
			
			
            var ISEMAIL_ITEM = $("input[name='ISEMAIL_ITEM']").val();
            if("1"==ISEMAIL_ITEM){
                $("input[name='ISEMAIL']").attr("checked","checked");
                $("#emailInfo").attr("style","");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
            }

			initJobInfo();
			if($("#oldjsxxDiv").children("div").length>1){//设监事会
				$("#jsba").hide();
			} else{					
				$("#jsba").show();
			}
		}else{					
			var SSBA_TYPE = $("[name='SSBA_TYPE']").val();
			//判断是否选择企业类型
			if(SSBA_TYPE==null||SSBA_TYPE==''){
				window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
			}
			$("[name='OPERATOR_IDTYPE']").val('SF');
			//$("[name='OPERATOR_IDTYPE']").attr("disabled",true);
			setZjValidate("SF",'OPERATOR_IDNO')
			$("[name='LEGAL_IDTYPE']").val('SF');
			//$("[name='LEGAL_IDTYPE']").attr("disabled",true);
			setZjValidate("SF",'LEGAL_IDNO');
			
			//initDisabled();
			initGsslxz('gykg');
			
            var ISEMAIL_ITEM = $("input[name='ISEMAIL_ITEM']").val();
            if("1"==ISEMAIL_ITEM){
                $("input[name='ISEMAIL']").attr("checked","checked");
                $("#emailInfo").attr("style","");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
            }
			//进入页面就进行企业选择
			showEnterpriseInfo();
		}
	}	
	
	//经营期限
	$("input[name='OPRRATE_TERM_TYPE']").on("click", function(event) {
		setJyqx(this.value);
	});
	$("input[name='BASX']").click(function() {
		let v = this.value;
        if ($(this).is(":checked")== true) {
           //选中触发事件
		   if(v==1){
				$("#xdsxx").show();
				$("#addDsxx").show();
		   } else if(v==2){
				$("#xjsxx").show();
				$("#addJsxx").show();
		   } else if(v==3){
				$("#xjlxx").show();
				$("#addJlxx").show();
		   }
        } else {
           //取消选中触发事件
		   if(v==1){
			   $("#xdsxx").hide();
			   $("#dsxxDiv").html("");
			   $("[name='DSBGQNR']").val('');
			   $("[name='DSBGHNR']").val('');
			   $("#DSBGQNR_TD").html('');
			   $("#DSBGHNR_TD").html('');
		   } else if(v==2){
			   $("#xjsxx").hide();
			   $("#jsxxDiv").html("");
			   $("[name='JSBGQNR']").val('');
			   $("[name='JSBGHNR']").val('');
			   $("#JSBGQNR_TD").html('');
			   $("#JSBGHNR_TD").html('');
		   } else if(v==3){
			   $("#xjlxx").hide();
			   $("#jlxxDiv").html("");
			   $("[name='JLBGQNR']").val('');
			   $("[name='JLBGHNR']").val('');
			   $("#JLBGQNR_TD").html('');
			   $("#JLBGHNR_TD").html('');
		   }
        }
    });
    //邮寄公章信息
    $("input[name='ISEMAIL']").click(function(){
        var Value = $(this).val();
        if(Value=='1'){
            $("#emailInfo").attr("style","");
            AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
        }else{
            $("#emailInfo").attr("style","display:none;");
            AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","-1");
        }
    });
});

function initJobInfo(){
	var COMPANY_TYPE= $("[name='COMPANY_TYPE']").val();
	var COMPANY_SETNATURE= $("[name='COMPANY_SETNATURE']").val();//公司设立性质编码
	if(COMPANY_TYPE=='zrrdz' && COMPANY_SETNATURE=='01'){//有限责任公司（自然人独资）  一人有限公司
		$("[name$='SHAREHOLDER_TYPE']").val("zrr");
		$("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
		setGdlxValidate("zrr",'LEGAL_PERSON');
		setGdlxValidate("zrr",'LEGAL_IDNO_PERSON');
		$("[name$='DIRECTOR_JOB']").val("30");
		$("[name$='DIRECTOR_JOB']").attr("disabled",true);		
		$("[name$='DIRECTOR_GENERATION_MODE']").val("01");
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("01");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);		
	} else if(COMPANY_TYPE=='zrrdz' && COMPANY_SETNATURE=='07'){//有限责任公司（自然人独资）  一人有限公司设董事会、监事设立
		$("#gdxxDiv").children("div").each(function(i){
			$(this).find("[name$='SHAREHOLDER_TYPE']").val("zrr");
			$(this).find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
			var LEGAL_PERSON = $(this).find("[name$='LEGAL_PERSON']").attr("name");
			var LEGAL_IDNO_PERSON =$(this).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
			setGdlxValidate("zrr",LEGAL_PERSON);
			setGdlxValidate("zrr",LEGAL_IDNO_PERSON);
		});		
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("01");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);		
	} else if(COMPANY_TYPE=='frdz' && COMPANY_SETNATURE=='01'){//有限责任公司（法人独资）  一人有限公司
		$("#gdxxDiv").children("div").each(function(i){
			$(this).find("[name$='SHAREHOLDER_TYPE']").find("option[value='zrr']").remove();
		});
		$("[name$='DIRECTOR_JOB']").val("30");
		$("[name$='DIRECTOR_JOB']").attr("disabled",true);		
		$("[name$='DIRECTOR_GENERATION_MODE']").val("01");
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("01");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);		
	} else if(COMPANY_TYPE=='frdz' && COMPANY_SETNATURE=='07'){//有限责任公司（法人独资）  一人有限公司设董事会、监事设立
		$("#gdxxDiv").children("div").each(function(i){
			$(this).find("[name$='SHAREHOLDER_TYPE']").find("option[value='zrr']").remove();
		});		
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("01");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);		
	} else if(COMPANY_TYPE=='zrrkg'){//有限责任公司（自然人投资或控股）
		$("#gdxxDiv").children("div").each(function(i){
			$(this).find("[name$='SHAREHOLDER_TYPE']").val("zrr");
			$(this).find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
			var LEGAL_PERSON = $(this).find("[name$='LEGAL_PERSON']").attr("name");
			var LEGAL_IDNO_PERSON =$(this).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
			setGdlxValidate("zrr",LEGAL_PERSON);
			setGdlxValidate("zrr",LEGAL_IDNO_PERSON);
		});	
		$("[name$='DIRECTOR_GENERATION_MODE']").val("02");
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("02");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);
		//if(COMPANY_SETNATURE=='04'){
			if($("#gdxxDiv").children("div").length<2){
				addGdxxDiv();
			}
			$("#gdxxDiv").children("div").eq(1).find(".syj-closebtn").remove();
		//}
	} else if(COMPANY_TYPE=='qtyxzrgs'){//其他有限责任公司
		$("[name$='DIRECTOR_GENERATION_MODE']").val("02");
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='SUPERVISOR_GENERATION_MODE']").val("02");
		$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);		
		$("[name$='MANAGER_GENERATION_MODE']").val("03");
		$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true);
		if(COMPANY_SETNATURE!='03'){
			if($("#gdxxDiv").children("div").length<2){
				addGdxxDiv();
			}			
			$("#gdxxDiv").children("div").eq(1).find(".syj-closebtn").remove();
		}
	} 
}
//设置经营期限
function setJyqx(value){
	if (value === "1") {			
		$("input[name='BUSSINESS_YEARS']").attr("disabled",false);
	} else {			
		$("input[name='BUSSINESS_YEARS']").val("");
		$("input[name='BUSSINESS_YEARS']").attr("disabled",true); 
	}
}

function initDisabled(){
	$("#gdxxDiv div").find("[name$='SHAREHOLDER_NAME']").attr("disabled",true);
	$("#gdxxDiv div").find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
	$("#gdxxDiv div").find("[name$='LICENCE_TYPE']").attr("disabled",true);			
	//$("[name='DIRECTOR_JOB']").attr("disabled",true);
	$("[name='SUPERVISOR_JOB']").attr("disabled",true);
	$("[name='MANAGER_JOB']").attr("disabled",true);
	//$("[name='LEGAL_JOB']").attr("disabled",true);
	$("[name='CURRENCY']").attr("disabled",true);	
	$("#olddsxxDiv div").find("[name$='DIRECTOR_JOB']").attr("disabled",true);
	$("#oldjsxxDiv div").find("[name$='SUPERVISOR_JOB']").attr("disabled",true);
	$("#oldjlxxDiv div").find("[name$='MANAGER_JOB']").attr("disabled",true);
}


function initGsslxz(typeCode){			
   var url = __ctxPath+'/dictionaryController/loadData.do?defaultEmptyText=公司设立性质&typeCode='+typeCode+'&orderType=asc';
	$('#COMPANY_SETNATURE').combobox({
		url:url,
		method:'post',
		valueField:'DIC_CODE',
		textField:'DIC_NAME',
		panelHeight:'auto',
		required:true,
		editable:false,
		onSelect:function(rec){
			let s = rec.DIC_CODE;
			if(s){
				$("#dsxxDiv").html('');
				$("#addDsxx").show();
				$("#jsxxDiv").html('');
				$("#addJsxx").show();
				$("#jlxxDiv").html('');
				$("#addJlxx").show();
				setDsbaxx();
				initJobInfo();
			} 
		}
	});
}
function setCancelReason(v){	
	if(v && v.indexOf("99")>-1){
		$("[name='CANCEL_REASON_OTHER']").show();
		$("[name='CANCEL_REASON_OTHER']").addClass(" validate[required]");
	} else{
		$("[name='CANCEL_REASON_OTHER']").hide();
		$("[name='CANCEL_REASON_OTHER']").removeClass(" validate[required]");
	}
}
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

/**
* 标题附件上传对话框
*/
function openCancelFileUploadDialog(name){
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=attachToImage&busTableName=T_COMMERCIAL_INDIVIDUAL'
	+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
		title: "上传(附件)",
		width: "660px",
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
					$("input[name='"+name+"_NAME']").val(uploadedFileInfo.fileName);
					$("input[name='"+name+"_PATH']").val(uploadedFileInfo.filePath);
					$("input[name='"+name+"_FILEID']").val(uploadedFileInfo.fileId);
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a style=\"cursor: pointer;color: blue;margin-right: 5px;\"";
					spanHtml+=(" href=\"" + __file_server
						+ "download/fileDownload?attachId=" +uploadedFileInfo.fileId
						+ "&attachType="+attachType+"\" target=\"_blank\" >");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>";
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"','"+name+"');\" ><font color=\"red\">删除</font></a></p>";
					$("#SWBMCJDQYQSWS_DIV").html(spanHtml)
				}else{
					$("input[name='"+name+"_NAME']").val('');
					$("input[name='"+name+"_PATH']").val('');
					$("input[name='"+name+"_FILEID']").val('');
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
/**
 * 删除已上传的材料文件
 * @param {} fileId
 */
function delUploadFile(fileId,name){
	art.dialog.confirm("您确定要删除该文件吗?", function() {		
		//移除目标span
		$("#"+fileId).remove();
		$("input[name='"+name+"_NAME']").val('');
		$("input[name='"+name+"_PATH']").val('');
		$("input[name='"+name+"_FILEID']").val('');
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
			var num = 0;
			if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("director")==0){
				num = Number($("#olddsxxDiv div").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);
			} else if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("supervisor")==0){
				num = Number($("#oldjsxxDiv div").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);				
			} else if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("manager")==0){
				num = Number($("#oldjlxxDiv div").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);				
			} else if(BELONG_ID!=null && BELONG_ID!=''){
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
	//验证表单是否合法
	var valiateTabForm = AppUtil.validateWebsiteTabForm();
	
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质编码
	if(companySetNature==null||companySetNature==''){
		art.dialog({
			content : "请选择公司设立性质！",
			icon : "error",
			ok : true
		}); 
		return;
	} 
	var COMPANY_TYPE= $("[name='COMPANY_TYPE']").val();//公司设立性质编码
	if(COMPANY_TYPE==null||COMPANY_TYPE==''){
		art.dialog({
			content : "请选择公司类型！",
			icon : "error",
			ok : true
		}); 
		return;
	} 
	if($("#oldjsxxDiv").children("div").length>1){	
		if(companySetNature!='04'){	
			art.dialog({
				content : "该企业设立监事会，请重新选择公司设立性质！",
				icon : "error",
				ok : true
			}); 
			return;
		}	
	}else if($("#oldjsxxDiv").children("div").length==1){		
		if(companySetNature=='04'){	
			art.dialog({
				content : "该企业不设立监事会，请重新选择公司设立性质！",
				icon : "error",
				ok : true
			}); 
			return;
		}
	}

	if($("#olddsxxDiv").children("div").length==0){	
		art.dialog({
			content : "原董事信息不能为空！",
			icon : "error",
			ok : true
		}); 
		return;
	}
	if($("#oldjsxxDiv").children("div").length==0){	
		art.dialog({
			content : "原监事信息不能为空！",
			icon : "error",
			ok : true
		}); 
		return;
	}
	if($("#oldjlxxDiv").children("div").length==0){	
		art.dialog({
			content : "原经理信息不能为空！",
			icon : "error",
			ok : true
		}); 
		return;
	}
	var LEGAL_JOB= $("[name='LEGAL_JOB']").val();//法定代表人职务
	var LEGAL_NAME= $("[name='LEGAL_NAME']").val();//法定代表人名称
	var LEGAL_IDNO= $("[name='LEGAL_IDNO']").val();//法定代表人证件号码
	var isok = false;
	var basx = "";
	$("input[name='BASX']:checked").each(function(i){
		basx +=$(this).val()+",";
		if($(this).val()==1){//董事备案		
			if($("#dsxxDiv").children("div").length==0){	
				art.dialog({
					content : "请添加新董事信息！",
					icon : "error",
					ok : true
				}); 
				isok = true;
				return;
			}
			if(LEGAL_JOB=='01'||LEGAL_JOB=='30'){
				var flag = true;
				$("#dsxxDiv div").each(function(i){
					var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
					var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务		
					var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
					if(LEGAL_JOB==DIRECTOR_JOB && DIRECTOR_NAME==LEGAL_NAME && LEGAL_IDNO==DIRECTOR_IDNO){
						flag = false;
					}
				});
				if(flag){						
					art.dialog({
						content : "兼任法定代表人员不可变动！<br/>相同职务人员的姓名、证件号码要相同",
						icon : "error",
						ok : true
					}); 
					isok = true;
					return;
				}
			} 
			if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){//设董事会、监事设立;设董事会监事会设立
				var num = 0;
				var dsnum = 0;
				$("#dsxxDiv select[name$='DIRECTOR_JOB']").each(function(i){		
					var DIRECTOR_JOB = $(this).val();
					if(DIRECTOR_JOB=='01'){	
						num++;
					}
					dsnum++;
				});
				
				if(num==0){
					art.dialog({
						content : "董事信息中必须要有董事长！",
						icon : "warning",
						ok : true
					});
					isok = true;
					return;
				}else if(num>1){
					art.dialog({
						content : "董事信息中只能有1位董事长！",
						icon : "warning",
						ok : true
					});
					isok = true;
					return;
				}else if(dsnum<3){
					art.dialog({
						content : "董事数量必须大于等于3！",
						icon : "warning",
						ok : true
					});
					isok = true;
					return;
				}else if(dsnum>13){
					art.dialog({
						content : "董事数量必须小于等于13！",
						icon : "warning",
						ok : true
					});
					isok = true;
					return;
				}
			}
		} else if($(this).val()==2){//监事备案	
			if($("#oldjsxxDiv").children("div").length>1){	
				art.dialog({
					content : "原公司监事存在多人不允许变更监事！",
					icon : "error",
					ok : true
				}); 
				isok = true;
				return;
			}		
			if($("#jsxxDiv").children("div").length==0){	
				art.dialog({
					content : "请添加新监事信息！",
					icon : "error",
					ok : true
				}); 
				isok = true;
				return;
			}
		} else if($(this).val()==3){//经理备案		
			if($("#jlxxDiv").children("div").length==0){	
				art.dialog({
					content : "请添加新经理信息！",
					icon : "error",
					ok : true
				}); 
				isok = true;
				return;
			}
			
			if(LEGAL_JOB=='20'){
				var flag = true;
				$("#jlxxDiv div").each(function(i){
					var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
					var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();  //职务
					var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();//证件号码
					if(LEGAL_JOB==MANAGER_JOB && MANAGER_NAME==LEGAL_NAME && LEGAL_IDNO==MANAGER_IDNO){
						flag = false;
					}
				});
				if(flag){						
					art.dialog({
						content : "兼任法定代表人员不可变动！<br/>相同职务人员的姓名、证件号码要相同",
						icon : "error",
						ok : true
					}); 
					isok = true;
					return;
				}
			}
		}			
	});
	if(LEGAL_JOB=='20' && basx.indexOf("3")<0){//法定代表人职务为经理，且不做经理备案时判断旧经理是否有该法定代表人		
			var flag = true;
			$("#oldjlxxDiv div").each(function(i){
				var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
				var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();  //职务
				if(LEGAL_JOB==MANAGER_JOB && MANAGER_NAME==LEGAL_NAME){
					flag = false;
				}
			});
			if(flag){						
				art.dialog({
					content : "兼任法定代表人员不可变动！<br/>相同职务人员的姓名、证件号码等要相同",
					icon : "error",
					ok : true
				}); 
				isok = true;
				return;
			}
	} else if((LEGAL_JOB=='01'||LEGAL_JOB=='30') && basx.indexOf("1")<0){//法定代表人职务为执行董事或者董事长，且不做董事备案时判断旧董事是否有该法定代表人		
		var flag = true;
		$("#olddsxxDiv div").each(function(i){
			var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
			var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务		
			if(LEGAL_JOB==DIRECTOR_JOB && DIRECTOR_NAME==LEGAL_NAME){
				flag = false;
			}
		});
		if(flag){						
			art.dialog({
				content : "兼任法定代表人员不可变动！<br/>相同职务人员的姓名、证件号码等要相同",
				icon : "error",
				ok : true
			}); 
			isok = true;
			return;
		}
	} 
	if(isok){
		return;
	}
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
		 //获取股东信息
		 flowSubmitObj.HOLDER_JSON =getGdxxJson();
		 //获取新董事信息
		 flowSubmitObj.DIRECTOR_JSON =getDsxxJson();
		 //获取原董事信息
		 flowSubmitObj.OLD_DIRECTOR_JSON = getOldDsxxJson();
		 //获取监事信息
		 flowSubmitObj.SUPERVISOR_JSON =getJsxxJson();
		 //获取原监事信息
		 flowSubmitObj.OLD_SUPERVISOR_JSON = getOldJsxxJson();
		 //获取经理信息
		 flowSubmitObj.MANAGER_JSON =getJlxxJson();
		 //获取原经理信息
		 flowSubmitObj.OLD_MANAGER_JSON =getOldJlxxJson();
		 
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
	 //获取股东信息
	 flowSubmitObj.HOLDER_JSON =getGdxxJson();
	 //获取新董事信息
	 flowSubmitObj.DIRECTOR_JSON =getDsxxJson();
	 //获取原董事信息
	 flowSubmitObj.OLD_DIRECTOR_JSON = getOldDsxxJson();
	 //获取监事信息
	 flowSubmitObj.SUPERVISOR_JSON =getJsxxJson();
	 //获取原监事信息
	 flowSubmitObj.OLD_SUPERVISOR_JSON = getOldJsxxJson();
	 //获取经理信息
	 flowSubmitObj.MANAGER_JSON =getJlxxJson();
	 //获取原经理信息
	 flowSubmitObj.OLD_MANAGER_JSON =getOldJlxxJson();
	 
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
					$("[name='BUSSINESS_SCOPE_ID']").val(selectBusScopeInfo.ids);
					$("[name='BUSSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
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

/*************法定代表人JS开始****************/
function setLegalValue(val){
	//$("#LEGAL_IDTYPE").attr("disabled",false);
	var isok = false;//标识位
	$("#dsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
        var DIRECTOR_PHONE=$(this).find("[name$='DIRECTOR_PHONE']").val();	//手机号码
        var DIRECTOR_GENERATION_MODE=$(this).find("[name$='DIRECTOR_GENERATION_MODE']").val();	//产生方式
		if(DIRECTOR_JOB==val){
			//$("input[name='LEGAL_NAME']").val(DIRECTOR_NAME);
			setZjValidate(DIRECTOR_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(DIRECTOR_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(DIRECTOR_IDNO);
            $("input[name='LEGAL_MOBILE']").val(DIRECTOR_PHONE);
			$("[name='LEGAL_PRODUCEMODE']").val(DIRECTOR_GENERATION_MODE);
			isok = true;
			return;
		}
	});
	$("#jlxxDiv div").each(function(i){
		var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
		var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
		var MANAGER_IDTYPE = $(this).find("[name$='MANAGER_IDTYPE']").val();//证件类型		
		var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();	//证件号码
		var MANAGER_PHONE=$(this).find("[name$='MANAGER_PHONE']").val();	//手机号码
        var MANAGER_GENERATION_MODE=$(this).find("[name$='MANAGER_GENERATION_MODE']").val();	//产生方式
		if(MANAGER_JOB==val){
			//$("input[name='LEGAL_NAME']").val(MANAGER_NAME);	
			setZjValidate(MANAGER_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(MANAGER_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(MANAGER_IDNO);
            $("input[name='LEGAL_MOBILE']").val(MANAGER_PHONE);
			$("[name='LEGAL_PRODUCEMODE']").val(MANAGER_GENERATION_MODE);
			isok = true;
			return;
		}
	});
	if(!isok){		
		$("#LEGAL_IDTYPE").val('');
		$("input[name='LEGAL_IDNO']").val('');
		$("#LEGAL_IDTYPE").attr("disabled",false);
		$("#LEGAL_COUNTRY").attr("disabled",false);
		$("[name='LEGAL_PRODUCEMODE']").val("");
		$("[name='LEGAL_PRODUCEMODE']").attr("disabled",false); 
	}else{		
		$("#LEGAL_IDTYPE").attr("disabled",true);
		$("#LEGAL_COUNTRY").attr("disabled",true);
		$("[name='LEGAL_PRODUCEMODE']").attr("disabled",true); 
	}
	//$("#LEGAL_IDTYPE").attr("disabled",true);
}
/*************法定代表人JS结束****************/



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



/*************股东信息JS开始****************/

//设置股东类型
function setGdlxValidate(val,name){
	if (val === "zrr") {
		$("input[name='"+name+"']").val("");
		$("input[name='"+name+"']").attr("disabled",true);
        $("input[name='"+name+"']").removeClass("  validate[required]");
		$("."+name+"_CLASS").attr("style","display:none");
	} else {
		$("input[name='"+name+"']").attr("disabled",false);
		$("input[name='"+name+"']").addClass("  validate[required]");
		$("."+name+"_CLASS").attr("style","display:''");
	}
}
//设置股东类型
function setGdlxValidateToType(val,typename,typeCodeName){
	if (val === "zrr") {
		$("[name='"+typename+"']").val("SF");
		$("[name='"+typename+"']").attr("disabled",true);
		setZjValidate("SF",typeCodeName);
	} else{		
		$("[name='"+typename+"']").attr("disabled",false);
	}
}

function setSelectHolderName(val,name){
	$("[name='"+name+"']").text(val);
}
/**
 * 添加股东信息
 */
function addGdxxDiv(){
	$.post("ssnzqybaController/refreshGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);
		initJobInfo();
	});
}

/**
 * 删除股东信息
 */
function delGdxxDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取股东信息
 */
function getGdxxJson(){
	var gdxxArray = [];
	$("#gdxxDiv div").each(function(i){
		var SHAREHOLDER_NAME = $(this).find("[name$='SHAREHOLDER_NAME']").val();//股东姓名/名称
		var SHAREHOLDER_TYPE = $(this).find("[name$='SHAREHOLDER_TYPE']").val();//股东类型
		var LICENCE_TYPE = $(this).find("[name$='LICENCE_TYPE']").val();//证照类型
		var LICENCE_NO = $(this).find("[name$='LICENCE_NO']").val();//证件号码		
		var ID_ADDRESS = $(this).find("[name$='ID_ADDRESS']").val();	//身份证件地址
		var CONTACT_WAY = $(this).find("[name$='CONTACT_WAY']").val();//联系方式
		var LEGAL_PERSON = $(this).find("[name$='LEGAL_PERSON']").val();// 投资企业或其他组织机构法定代表人姓名
        var LEGAL_IDNO_PERSON = $(this).find("[name$='LEGAL_IDNO_PERSON']").val();// 投资企业或其他组织机构法定代表人身份证号码		
		var INVESTMENT_CASH = $(this).find("[name$='INVESTMENT_CASH']").val();//现金
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//实物
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		
		var CONTRIBUTIONS = $(this).find("[name$='CONTRIBUTIONS']").val();//认缴出资额
		var PROPORTION = $(this).find("[name$='PROPORTION']").val();//占注册资本比例
        var SHAREHOLDER_COMPANYCOUNTRY = $(this).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//国别
        var PAIDCAPITAL = $(this).find("[name$='PAIDCAPITAL']").val();//实缴出资额
		
		if(null!=SHAREHOLDER_NAME&&SHAREHOLDER_NAME!=""){
			var gdxx = {};
			gdxx.SHAREHOLDER_NAME = SHAREHOLDER_NAME;
			gdxx.SHAREHOLDER_TYPE = SHAREHOLDER_TYPE;
			gdxx.LICENCE_TYPE = LICENCE_TYPE;
			gdxx.LICENCE_NO = LICENCE_NO;
			gdxx.ID_ADDRESS = ID_ADDRESS;
			gdxx.CONTACT_WAY = CONTACT_WAY;
			gdxx.LEGAL_PERSON = LEGAL_PERSON;
            gdxx.LEGAL_IDNO_PERSON = LEGAL_IDNO_PERSON;
			gdxx.INVESTMENT_CASH = INVESTMENT_CASH;
			gdxx.INVESTMENT_MATERIAL = INVESTMENT_MATERIAL;
			gdxx.INVESTMENT_TECHNOLOGY = INVESTMENT_TECHNOLOGY;
			gdxx.INVESTMENT_LAND = INVESTMENT_LAND;
			gdxx.INVESTMENT_STOCK = INVESTMENT_STOCK;
			gdxx.INVESTMENT_OTHER = INVESTMENT_OTHER;
			
			gdxx.CONTRIBUTIONS = CONTRIBUTIONS;
			gdxx.PROPORTION = PROPORTION;
			gdxx.PAIDCAPITAL=PAIDCAPITAL;
			gdxx.SHAREHOLDER_COMPANYCOUNTRY=SHAREHOLDER_COMPANYCOUNTRY;
			gdxxArray.push(gdxx);			
		}
		
	});
	if(gdxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(gdxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
//初始化股东信息
function setGdxx(holderJson){
	var holders = eval(holderJson);
	if(holders){				
		for(var i=0;i<holders.length;i++){			
			if(i>0){
				addInitGdxxDiv(holders[i],i);
			}else{				
				FlowUtil.initFormObjValue(holders[i],$("#gdxxDiv div").eq(i));
			}
		}
	}
}
/*************股东信息JS结束****************/




/*************董事信息JS开始****************/
/**
 * 添加董事信息
 */
function addDsxxDiv(isClose){
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质
	if(companySetNature){		
		var ssdjzw = '04'
		if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){
			ssdjzw = '01';
		} else{
			isClose = 1;
			$("#addDsxx").hide();
		}
		if($("#dsxxDiv div").length==0){			
			isClose = 1;
		}
		$.post("ssnzqybaController/refreshDsxxDiv.do",{
			ssdjzw:ssdjzw,
			isClose:isClose
		}, function(responseText, status, xhr) {
			$("#dsxxDiv").append(responseText);
			$("#dsxxDiv div").eq($("#dsxxDiv div").length-1).find("[name$='DIRECTOR_APPOINTOR']").val($("[name='DIRECTOR_APPOINTOR']").val());
			addOldDirectorInfoToNew();
			initJobInfo();
		});
	} else{		
		art.dialog({
			content : "请先选择公司设立性质!",
			icon : "error",
			ok : true
		});
	}
}
/**
 * 添加董事信息
 */
function addOldDsxxDiv(name,job,isClose){
	$.post("ssnzqybaController/refreshOldDsxxDiv.do",{
		isClose:isClose
	}, function(responseText, status, xhr) {
		$("#olddsxxDiv").append(responseText);
		$("#olddsxxDiv div").eq($("#olddsxxDiv div").length-1).find("[name$='DIRECTOR_NAME']").val(name);
		$("#olddsxxDiv div").eq($("#olddsxxDiv div").length-1).find("[name$='DIRECTOR_JOB']").val(job);
		//$("#olddsxxDiv div").eq($("#olddsxxDiv div").length-1).find("[name$='DIRECTOR_JOB']").attr("disabled",true);
		addOldDirectorInfo();
	});
}
function addOldDirectorInfo(){
	var selectInfo = "<option value=''>请选择复用人员信息</option>";
	$("#olddsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名	
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		if(DIRECTOR_NAME){			
			selectInfo+="<option value='"+DIRECTOR_NAME+"' num='"+i+"'>"+"董事信息"+DIRECTOR_NAME+"</option>";
		}
	});
	$("select[name='oldDirectorInfo']").html(selectInfo);
}
function addOldDirectorInfoToNew(){
	var selectInfo = "<option value=''>请选择复用人员信息</option>";
	$("#olddsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名	
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		if(DIRECTOR_NAME){			
			selectInfo+="<option value='"+DIRECTOR_NAME+"' num='"+i+"'>"+"董事信息"+DIRECTOR_NAME+"</option>";
		}
	});
	$("#dsxxDiv div").eq($("#dsxxDiv div").length-1).find("select[name='oldDirectorInfo']").html(selectInfo);
}
function setDirectorInfo(obj,currentTime){
	var name = $(obj).val();
	if(name){		
		var num = $(obj).find("option:selected").attr("num");
		$("#olddsxxDiv div").each(function(i){
			var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
			var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
			var DIRECTOR_PHONE = $(this).find("[name$='DIRECTOR_PHONE']").val();//手机号码
			var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
			var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
			if(name==DIRECTOR_NAME && i==num){
				$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_NAME']").val(DIRECTOR_NAME);//姓名
				$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_JOB']").val(DIRECTOR_JOB);//职务
				$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_PHONE']").val(DIRECTOR_PHONE);//手机号码
				$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_IDTYPE']").val(DIRECTOR_IDTYPE);//证件类型
				$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_IDNO']").val(DIRECTOR_IDNO);//证件号码
				setZjValidate(DIRECTOR_IDTYPE,currentTime+'DIRECTOR_IDNO');
				setDirectorJob(DIRECTOR_JOB,currentTime+'DIRECTOR_APPOINTOR');
				setDsbaxx();
			}
		});
	} else{
		$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_NAME']").val('');//姓名
		$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_JOB']").val('');//职务
		$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_PHONE']").val('');//手机号码
		$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_IDTYPE']").val('');//证件类型
		$("#dsxxDiv div").find("[name='"+currentTime+"DIRECTOR_IDNO']").val('');//证件号码
		setZjValidate(DIRECTOR_IDTYPE,currentTime+'DIRECTOR_IDNO');
		setDirectorJob(DIRECTOR_JOB,currentTime+'DIRECTOR_APPOINTOR');
		
	}
}
/**
 * 初始化时添加董事信息
 */
function addInitOldDsxxDiv(director,i){
	
	$.post("ssnzqybaController/refreshOldDsxxDiv.do",{
		
	}, function(responseText, status, xhr) {
		$("#olddsxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(director,$("#olddsxxDiv div").eq(i));
		$("#olddsxxDiv div").eq(i).find("[name$='DIRECTOR_JOB']").attr("disabled",true);
	});
	
}
/**
 * 初始化时添加董事信息
 */
function addInitDsxxDiv(director,i){
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
	if(companySetNature){
		var ssdjzw = '04'
		if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){
			ssdjzw = '01';
		}
		$.post("ssnzqybaController/refreshDsxxDiv.do",{
			ssdjzw:ssdjzw
		}, function(responseText, status, xhr) {
			$("#dsxxDiv").append(responseText);	
			FlowUtil.initFormObjValue(director,$("#dsxxDiv div").eq(i));			
			setZjValidate(director.DIRECTOR_IDTYPE,$("#dsxxDiv div").eq(i).find("[name$='DIRECTOR_IDNO']").attr('name'));
			initJobInfo();
		});
	} else{		
		art.dialog({
			content : "请先选择公司设立性质!",
			icon : "error",
			ok : true
		});
	}
}
/**
 * 删除董事信息
 */
function delDsxxDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取董事信息
 */
function getDsxxJson(){
	var dsxxArray = [];
	$("#dsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
        var DIRECTOR_PHONE = $(this).find("[name$='DIRECTOR_PHONE']").val();//手机号码
		var DIRECTOR_COUNTRY = $(this).find("[name$='DIRECTOR_COUNTRY']").val();//国别
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		var DIRECTOR_GENERATION_MODE = $(this).find("[name$='DIRECTOR_GENERATION_MODE']").val();//产生方式
		var DIRECTOR_APPOINTOR = $(this).find("[name$='DIRECTOR_APPOINTOR']").val();// 任命方		
		var DIRECTOR_OFFICETERM = $(this).find("[name$='DIRECTOR_OFFICETERM']").val();// 任期

		if(null!=DIRECTOR_NAME&&DIRECTOR_NAME!=""){
			var dsxx = {};
			dsxx.DIRECTOR_NAME = DIRECTOR_NAME;
			dsxx.DIRECTOR_JOB = DIRECTOR_JOB;
			dsxx.DIRECTOR_COUNTRY = DIRECTOR_COUNTRY;
            dsxx.DIRECTOR_PHONE=DIRECTOR_PHONE;
			dsxx.DIRECTOR_IDTYPE = DIRECTOR_IDTYPE;
			dsxx.DIRECTOR_IDNO = DIRECTOR_IDNO;
			dsxx.DIRECTOR_GENERATION_MODE = DIRECTOR_GENERATION_MODE;
			dsxx.DIRECTOR_APPOINTOR = DIRECTOR_APPOINTOR;
			dsxx.DIRECTOR_OFFICETERM = DIRECTOR_OFFICETERM;
            dsxxArray.push(dsxx);
		}		
		
	});
	if(dsxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(dsxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}

/**
 * 获取董事信息
 */
function getOldDsxxJson(){
	var dsxxArray = [];
	$("#olddsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
        var DIRECTOR_PHONE = $(this).find("[name$='DIRECTOR_PHONE']").val();//手机号码
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码

		if(null!=DIRECTOR_NAME&&DIRECTOR_NAME!=""){
			var dsxx = {};
			dsxx.DIRECTOR_NAME = DIRECTOR_NAME;
			dsxx.DIRECTOR_JOB = DIRECTOR_JOB;
            dsxx.DIRECTOR_PHONE=DIRECTOR_PHONE;
			dsxx.DIRECTOR_IDTYPE = DIRECTOR_IDTYPE;
			dsxx.DIRECTOR_IDNO = DIRECTOR_IDNO;
            dsxxArray.push(dsxx);
		}		
		
	});
	if(dsxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(dsxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
//初始化董事信息
function setDsxx(directorJson){
	var directors = eval(directorJson);
	if(directors){				
		for(var i=0;i<directors.length;i++){			
			if(i>0){
				addInitDsxxDiv(directors[i],i);
			}else{				
				FlowUtil.initFormObjValue(directors[i],$("#dsxxDiv div").eq(i));
			}
		}
	}
}
function setOldDsxx(directorJson){
	var directors = eval(directorJson);
	if(directors){				
		for(var i=0;i<directors.length;i++){			
			if(i>0){
				addInitOldDsxxDiv(directors[i],i);
			}else{				
				FlowUtil.initFormObjValue(directors[i],$("#olddsxxDiv div").eq(i));
			}
		}
	}
}
function setDirectorJob(val,name){
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
	var directorGenerationMode=name.replace("DIRECTOR_APPOINTOR","DIRECTOR_GENERATION_MODE");
	if(val=='30' && companySetNature=='01'){
		$("[name='"+name+"']").val("股东");
	}else if(val=='03'&&companySetNature=='07'){
        $("[name='"+name+"']").val("股东");
        $("[name='"+directorGenerationMode+"']").val("01");
    }else if((val=='01'||val=='02')&&companySetNature=='07'){
        $("[name='"+name+"']").val("董事会");
        $("[name='"+directorGenerationMode+"']").val("02");
	}else if(val=='03'||val=='30'){
		$("[name='"+name+"']").val("股东会");
	}else{		
		$("[name='"+name+"']").val("董事会");
	}
}
//产生方式
function setGenerationModeByDirector(val,name){
	var SSSBLX = $("input[name='SSSBLX']").val();
	if(SSSBLX=='1'){
		var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
		var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
		var directorGenerationMode=name.replace("DIRECTOR_APPOINTOR","DIRECTOR_GENERATION_MODE");
		if( companySetNature=='01'){
			$("[name='"+name+"']").val("01");
			$("[name='"+name+"']").attr("disabled","disabled");
		}else if(val=='03'&&companySetNature=='07'){
			$("[name='"+name+"']").val("01");
			$("[name='"+name+"']").attr("disabled","disabled");
		}else if((val=='01'||val=='02')&&companySetNature=='07'){
			$("[name='"+name+"']").val("02");
			$("[name='"+name+"']").attr("disabled","disabled");
		}else if(companySetNature=='02'||companySetNature=='03'||companySetNature=='04'){
			$("[name='"+name+"']").val("02");
			$("[name='"+name+"']").attr("disabled","disabled");
		}
		//监事产生方式
		if(companyTypeCode=='zrrdz'){
			$("[name$='SUPERVISOR_GENERATION_MODE']").val('01');
			$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled","disabled");
		}else if(companyTypeCode=='zrrkg'||companyTypeCode=='qtyxzrgs'){
			$("[name$='SUPERVISOR_GENERATION_MODE']").val('02');
			$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled","disabled");
		}
	}
}

function setDsbaxx(){
	$("input[name='BASX']:checked").each(function(i){
		if($(this).val()==1){//董事备案	
			let bgqdsxx = "";
			$("#olddsxxDiv div").each(function(i){
				let DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
				let DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
				let DIRECTOR_TEXT = $(this).find("[name$='DIRECTOR_JOB']").find("option:selected").text();//职务
				if(DIRECTOR_JOB){			
					bgqdsxx +=DIRECTOR_TEXT+"："+DIRECTOR_NAME+"；";
				}
			});
			$("[name='DSBGQNR']").val(bgqdsxx);
			$("#DSBGQNR_TD").html(bgqdsxx);
			let bghdsxx ="";
			$("#dsxxDiv div").each(function(i){
				let DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
				let DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
				let DIRECTOR_TEXT = $(this).find("[name$='DIRECTOR_JOB']").find("option:selected").text();//职务	
				if(DIRECTOR_JOB){			
					bghdsxx +=DIRECTOR_TEXT+"："+DIRECTOR_NAME+"；";
				}
			});
			$("[name='DSBGHNR']").val(bghdsxx);
			$("#DSBGHNR_TD").html(bghdsxx);
		} else if($(this).val()==2){//监事备案		
			let bgqjsxx = "";
			$("#oldjsxxDiv div").each(function(i){
				let SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
				let SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
				let SUPERVISOR_TEXT = $(this).find("[name$='SUPERVISOR_JOB']").find("option:selected").text();//职务
				if(SUPERVISOR_JOB){			
					bgqjsxx +=SUPERVISOR_TEXT+"："+SUPERVISOR_NAME+"；";
				}
			});
			$("[name='JSBGQNR']").val(bgqjsxx);
			$("#JSBGQNR_TD").html(bgqjsxx);
			let bghjsxx ="";
			$("#jsxxDiv div").each(function(i){
				let SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
				let SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
				let SUPERVISOR_TEXT = $(this).find("[name$='SUPERVISOR_JOB']").find("option:selected").text();//职务	
				if(SUPERVISOR_JOB){			
					bghjsxx +=SUPERVISOR_TEXT+"："+SUPERVISOR_NAME+"；";
				}
			});
			$("[name='JSBGHNR']").val(bghjsxx);
			$("#JSBGHNR_TD").html(bghjsxx);
		} else if($(this).val()==3){//经理备案
			let bgqjlxx = "";
			$("#oldjlxxDiv div").each(function(i){
				let MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
				let MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
				let MANAGER_TEXT = $(this).find("[name$='MANAGER_JOB']").find("option:selected").text();//职务
				if(MANAGER_JOB){			
					bgqjlxx +=MANAGER_TEXT+"："+MANAGER_NAME+"；";
				}
			});
			$("[name='JLBGQNR']").val(bgqjlxx);
			$("#JLBGQNR_TD").html(bgqjlxx);
			let bghjlxx ="";
			$("#jlxxDiv div").each(function(i){
				let MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
				let MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
				let MANAGER_TEXT = $(this).find("[name$='MANAGER_JOB']").find("option:selected").text();//职务	
				if(MANAGER_JOB){			
					bghjlxx +=MANAGER_TEXT+"："+MANAGER_NAME+"；";
				}
			});
			$("[name='JLBGHNR']").val(bghjlxx);
			$("#JLBGHNR_TD").html(bghjlxx);
		}
	});
	
}

/*************董事信息JS结束****************/


/*************监事信息JS开始****************/
/**
 * 添加监事信息
 */
function addJsxxDiv(isClose){
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质
	var SUPERVISOR_APPOINTOR="监事会";
	if(companySetNature){	
		if(companySetNature=='04'){
			$("#addJsxx").show();
		} else{
			isClose = 1;
			$("#addJsxx").hide();
		}
		if($("#jsxxDiv div").length==0){			
			isClose = 1;
		}		
		$.post("ssnzqybaController/refreshJsxxDiv.do?",{
			isClose : isClose
		}, function(responseText, status, xhr) {
			$("#jsxxDiv").append(responseText);
			if(companySetNature=='03'||companySetNature=='02'||companySetNature=='07'){			
				SUPERVISOR_APPOINTOR="股东会";
				$("[name$='SUPERVISOR_JOB']").attr("disabled",true);
				$("[name$='SUPERVISOR_APPOINTOR']").attr("disabled",true);
			} else if(companySetNature=='01'){			
				SUPERVISOR_APPOINTOR="股东";
				$("[name$='SUPERVISOR_JOB']").attr("disabled",true);
				$("[name$='SUPERVISOR_APPOINTOR']").attr("disabled",true);
			}
			$("[name$='SUPERVISOR_APPOINTOR']").val(SUPERVISOR_APPOINTOR);
			initJobInfo();
		});
	} else{		
		art.dialog({
			content : "请先选择公司设立性质!",
			icon : "error",
			ok : true
		});
	}
}
/**
 * 添加原监事信息
 */
function addOldJsxxDiv(name,job,isClose){
	$.post("ssnzqybaController/refreshOldJsxxDiv.do",{
		isClose:isClose
	}, function(responseText, status, xhr) {
		$("#oldjsxxDiv").append(responseText);
		$("#oldjsxxDiv div").eq($("#oldjsxxDiv div").length-1).find("[name$='SUPERVISOR_NAME']").val(name);
		$("#oldjsxxDiv div").eq($("#oldjsxxDiv div").length-1).find("[name$='SUPERVISOR_JOB']").val(job);
		//$("#oldjsxxDiv div").find("[name$='SUPERVISOR_JOB']").attr("disabled",true);
	});
}
/**
 * 初始化时添加监事信息
 */
function addInitJsxxDiv(supervisor,i){
	var isClose=0;
	if($("#jsxxDiv div").length==0){
		isClose=1;
	}
	var companySetNature = $("input[name='COMPANY_SETNATURE']").val();//公司设立性质					
	$.post("ssnzqybaController/refreshJsxxDiv.do?isClose="+isClose,{
	}, function(responseText, status, xhr) {
		$("#jsxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(supervisor,$("#jsxxDiv div").eq(i));
		setZjValidateOfObj(supervisor.SUPERVISOR_IDTYPE,$("#jsxxDiv div").eq(i).find("[name$='SUPERVISOR_IDNO']"));
		if(companySetNature=='04'){
			$("#addJsxx").show();
		} else{
			if($("#jsxxDiv div").length==0){			
				$("#addJsxx").show();
			} else{
				$("#addJsxx").hide();
			}
			//$("#jsxxDiv div").eq(i).find("[name$='SUPERVISOR_JOB']").attr("disabled",true);
			//$("#jsxxDiv div").eq(i).find("[name$='SUPERVISOR_APPOINTOR']").attr("disabled",true);						
		}
		initJobInfo();
	});
}
/**
 * 初始化时添加监事信息
 */
function addInitOldJsxxDiv(supervisor,i){
	$.post("ssnzqybaController/refreshOldJsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#oldjsxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(supervisor,$("#oldjsxxDiv div").eq(i));
	});
}
/**
 * 删除监事信息
 */
function delJsxxDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取监事信息
 */
function getJsxxJson(){
	var jsxxArray = [];
	$("#jsxxDiv div").each(function(i){
		var SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
		var SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
		var SUPERVISOR_COUNTRY = $(this).find("[name$='SUPERVISOR_COUNTRY']").val();//国别
        var SUPERVISOR_PHONE= $(this).find("[name$='SUPERVISOR_PHONE']").val();//电话号码
		var SUPERVISOR_IDTYPE = $(this).find("[name$='SUPERVISOR_IDTYPE']").val();//证件类型		
		var SUPERVISOR_IDNO = $(this).find("[name$='SUPERVISOR_IDNO']").val();	//证件号码
		var SUPERVISOR_GENERATION_MODE = $(this).find("[name$='SUPERVISOR_GENERATION_MODE']").val();//产生方式
		var SUPERVISOR_APPOINTOR = $(this).find("[name$='SUPERVISOR_APPOINTOR']").val();// 任命方		
		var SUPERVISOR_OFFICETERM = $(this).find("[name$='SUPERVISOR_OFFICETERM']").val();// 任期


		if(null!=SUPERVISOR_OFFICETERM&&SUPERVISOR_OFFICETERM!=""){
			var jsxx = {};
			jsxx.SUPERVISOR_NAME = SUPERVISOR_NAME;
			jsxx.SUPERVISOR_JOB = SUPERVISOR_JOB;
			jsxx.SUPERVISOR_COUNTRY = SUPERVISOR_COUNTRY;
            jsxx.SUPERVISOR_PHONE= SUPERVISOR_PHONE;
			jsxx.SUPERVISOR_IDTYPE = SUPERVISOR_IDTYPE;
			jsxx.SUPERVISOR_IDNO = SUPERVISOR_IDNO;
			jsxx.SUPERVISOR_GENERATION_MODE = SUPERVISOR_GENERATION_MODE;
			jsxx.SUPERVISOR_APPOINTOR = SUPERVISOR_APPOINTOR;
			jsxx.SUPERVISOR_OFFICETERM = SUPERVISOR_OFFICETERM;
			jsxxArray.push(jsxx);			
		}
		
	});
	if(jsxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(jsxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
/**
 * 获取监事信息
 */
function getOldJsxxJson(){
	var jsxxArray = [];
	$("#oldjsxxDiv div").each(function(i){
		var SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
		var SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
		if(null!=SUPERVISOR_NAME&&SUPERVISOR_NAME!=""){
			var jsxx = {};
			jsxx.SUPERVISOR_NAME = SUPERVISOR_NAME;
			jsxx.SUPERVISOR_JOB = SUPERVISOR_JOB;
			jsxxArray.push(jsxx);			
		}
		
	});
	if(jsxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(jsxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
//初始化监事信息
function setJsxx(supervisorJson){
	var supervisors = eval(supervisorJson);
	if(supervisors){				
		for(var i=0;i<supervisors.length;i++){			
			addInitJsxxDiv(supervisors[i],i);
		}
	}
}
//初始化监事信息
function setOldJsxx(supervisorJson){
	var supervisors = eval(supervisorJson);
	if(supervisors){				
		for(var i=0;i<supervisors.length;i++){			
			if(i>0){
				addInitOldJsxxDiv(supervisors[i],i);
			}else{				
				FlowUtil.initFormObjValue(supervisors[i],$("#oldjsxxDiv div").eq(i));
				setZjValidate(supervisors[i].SUPERVISOR_IDTYPE,'SUPERVISOR_IDNO');
			}
		}
	}
}
/*************监事信息JS结束****************/


/*************经理信息JS开始****************/
/**
 * 添加经理信息
 */
function addJlxxDiv(isClose){	
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质	
	if(companySetNature){	
		$("#addJlxx").hide();		
		$.post("ssnzqybaController/refreshJlxxDiv.do",{
			isClose : isClose
		}, function(responseText, status, xhr) {
			$("#jlxxDiv").append(responseText);
			setManagerAppointor();
			//经理职务不能选择
			//$("[name$='MANAGER_JOB']").attr("disabled",true); 
			$("[name$='MANAGER_JOB']").val(20);
			initJobInfo();
		});
	} else{		
		art.dialog({
			content : "请先选择公司设立性质!",
			icon : "error",
			ok : true
		});
	}
}
/**
 * 添加原经理信息
 */
function addOldJlxxDiv(name,job,isClose){
	$.post("ssnzqybaController/refreshOldJlxxDiv.do",{
		isClose:isClose
	}, function(responseText, status, xhr) {
		$("#oldjlxxDiv").append(responseText);
		$("#oldjlxxDiv div").eq($("#oldjlxxDiv div").length-1).find("[name$='MANAGER_NAME']").val(name);
		$("#oldjlxxDiv div").eq($("#oldjlxxDiv div").length-1).find("[name$='MANAGER_JOB']").val(job);
		//$("#oldjlxxDiv div").eq($("#oldjlxxDiv div").length-1).find("[name$='MANAGER_JOB']").attr("disabled",true);
		setManagerAppointor();
	});
}
/**
 * 初始化时添加经理信息
 */
function addInitJlxxDiv(manager,i){
	$("#addJlxx").hide();
	$.post("ssnzqybaController/refreshJlxxDiv.do",{
		isClose : 1
	}, function(responseText, status, xhr) {
		$("#jlxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(manager,$("#jlxxDiv div").eq(i));
		setManagerAppointor();			
		//经理职务不能选择
		$("[name$='MANAGER_JOB']").attr("disabled",true); 
		setZjValidate(manager.MANAGER_IDTYPE,$("#jlxxDiv div").eq(i).find("[name$='MANAGER_IDNO']").attr('name'));
		initJobInfo();

	});
}
/**
 * 初始化时添加经理信息
 */
function addInitOldJlxxDiv(manager,i){
	$.post("ssnzqybaController/refreshOldJlxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#oldjlxxDiv").append(responseText);	
		//$("#oldjlxxDiv div").eq($("#oldjlxxDiv div").length-1).find("[name$='MANAGER_JOB']").attr("disabled",true);
		FlowUtil.initFormObjValue(manager,$("#oldjlxxDiv div").eq(i));
	});
}
/**
 * 删除经理信息
 */
function delJlxxDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取经理信息
 */
function getJlxxJson(){
	var jlxxArray = [];
	$("#jlxxDiv div").each(function(i){
		var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
		var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
		var MANAGER_COUNTRY = $(this).find("[name$='MANAGER_COUNTRY']").val();//国别
        var MANAGER_PHONE = $(this).find("[name$='MANAGER_PHONE']").val();//国别
		var MANAGER_IDTYPE = $(this).find("[name$='MANAGER_IDTYPE']").val();//证件类型		
		var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();	//证件号码
		var MANAGER_GENERATION_MODE = $(this).find("[name$='MANAGER_GENERATION_MODE']").val();//产生方式
		var MANAGER_APPOINTOR = $(this).find("[name$='MANAGER_APPOINTOR']").val();// 任命方		
		var MANAGER_OFFICETERM = $(this).find("[name$='MANAGER_OFFICETERM']").val();// 任期
				
		if(null!=MANAGER_OFFICETERM&&MANAGER_OFFICETERM!=""){
			var jlxx = {};
			jlxx.MANAGER_NAME = MANAGER_NAME;
			jlxx.MANAGER_JOB = MANAGER_JOB;
			jlxx.MANAGER_COUNTRY = MANAGER_COUNTRY;
			jlxx.MANAGER_IDTYPE = MANAGER_IDTYPE;
			jlxx.MANAGER_IDNO = MANAGER_IDNO;
			jlxx.MANAGER_GENERATION_MODE = MANAGER_GENERATION_MODE;
			jlxx.MANAGER_APPOINTOR = MANAGER_APPOINTOR;
			jlxx.MANAGER_OFFICETERM = MANAGER_OFFICETERM;
			jlxx.MANAGER_PHONE=MANAGER_PHONE;
			jlxxArray.push(jlxx);		
		}
		
	});
	if(jlxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(jlxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
/**
 * 获取经理信息
 */
function getOldJlxxJson(){
	var jlxxArray = [];
	$("#oldjlxxDiv div").each(function(i){
		var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
		var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
				
		if(null!=MANAGER_NAME&&MANAGER_NAME!=""){
			var jlxx = {};
			jlxx.MANAGER_NAME = MANAGER_NAME;
			jlxx.MANAGER_JOB = MANAGER_JOB;
			jlxxArray.push(jlxx);		
		}
		
	});
	if(jlxxArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(jlxxArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
//初始化经理信息
function setJlxx(managerJson){
	var managers = eval(managerJson);
	if(managers){				
		for(var i=0;i<managers.length;i++){	
			addInitJlxxDiv(managers[i],i);
		}
	}
}
//初始化原经理信息
function setOldJlxx(managerJson){
	var managers = eval(managerJson);
	if(managers){				
		for(var i=0;i<managers.length;i++){			
			if(i>0){
				addInitOldJlxxDiv(managers[i],i);
			}else{				
				FlowUtil.initFormObjValue(managers[i],$("#oldjlxxDiv div").eq(i));
				setZjValidate(managers[i].MANAGER_IDTYPE,'MANAGER_IDNO')
			}
		}
	}
}
function setManagerAppointor(){
	var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
	if(companySetNature=='01'){
		$("[name$='MANAGER_APPOINTOR']").val("股东");
	}else if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){
		$("[name$='MANAGER_APPOINTOR']").val("董事会");
    }else if(companySetNature=='03'){
		$("[name$='MANAGER_APPOINTOR']").val("股东会");
	}
}
/*************经理信息JS结束****************/

//选择证件类型为身份证时添加证件号码验证
function setZjValidateOfObj(zjlx,obj){
	var SSSBLX = $("input[name='SSSBLX']").val();
	if(SSSBLX=='1') {
		if (zjlx == "SF") {
			obj.addClass(",custom[vidcard],custom[isEighteen]");
		} else {
			obj.removeClass(",custom[vidcard],custom[isEighteen]");
		}
	}else{
		if (zjlx == "SF") {
			obj.addClass(",custom[vidcard]");
		} else {
			obj.removeClass(",custom[vidcard]");
		}
	}
}

function showEnterpriseInfo(){	
	$.dialog.open("cancelController.do?enterpriseInfo", {
		title : "企业查询",
		width:"500px",
		esc: false,//取消esc键  
		cancel:false,
		lock: true,
		resize:false,
		height:"180px",
		close: function () {
			var enterpriseInfo = art.dialog.data("enterpriseInfo");
			if(enterpriseInfo){				
				$("[name='COMPANY_NAME']").val(enterpriseInfo.baseInfo.ENTNAME);//企业名称
				$("[name='COMPANY_CODE']").val(enterpriseInfo.baseInfo.UNISCID);//统一社会信用代码
				$("[name='COMPANY_TYPECODE']").val(enterpriseInfo.baseInfo.ENTTYPE);//企业类型	
				$("[name='BUSSINESS_SCOPE']").val(enterpriseInfo.baseInfo.OPSCOPE.replace("<br>",""));//经营范围
				//$("[name='CONTACT_PHONE']").val(enterpriseInfo.baseInfo.TELEPHONE);//企业联系电话
				$("[name='INVESTMENT']").val(enterpriseInfo.baseInfo.REGCAP);
				var OP_FROM = enterpriseInfo.baseInfo.OPFROM;//经营期限起
				var OP_TO = enterpriseInfo.baseInfo.OPTO;//经营期限止
				var OPRRATE_TERM_TYPE= "1";
				if(null==OP_TO||''==OP_TO){//经营期限结束时间为空时，为长期经营
					OPRRATE_TERM_TYPE = "0"
				} else{
					var BUSSINESS_YEARS=getDateYearSub(OP_FROM,OP_TO);
					$("[name='BUSSINESS_YEARS']").val(BUSSINESS_YEARS);
				}
				$("[name='OPRRATE_TERM_TYPE'][value='"+OPRRATE_TERM_TYPE+"']").click();
				$("input:radio[name='OPRRATE_TERM_TYPE']").attr("disabled", "true").prop("disabled", "true");

				//setJyqx(OPRRATE_TERM_TYPE);
				
				var typeCode = getSlxzType(enterpriseInfo.baseInfo.ENTTYPE);
				initGsslxz(typeCode);
				$("#COMPANY_TYPE").combobox("setValue",typeCode);	
				/*$.post("cancelController.do?getCommercialDicType",{
					typecode:enterpriseInfo.baseInfo.ENTTYPE
				}, function(responseText, status, xhr) {
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
						$("[name='COMPANY_TYPE']").val(resultJson.data.TYPE_NAME);
					}
				});*/
				$("[name='LEGAL_NAME']").val(enterpriseInfo.fddbr.NAME);	//法定代表人
				$("[name='YFDBRMC']").val(enterpriseInfo.fddbr.NAME);	//法定代表人
				var members = enterpriseInfo.members;		
				for(var i=0;i<members.length;i++){					
					var PERSON_NAME = members[i].NAME;
					var POSITION = members[i].POSITION;
					if(PERSON_NAME==enterpriseInfo.fddbr.NAME){
						if(POSITION=='431A'||POSITION=='410A'){//董事长							
							$("[name='LEGAL_JOB']").val("01");					
							$("[name='YFDBRZW']").val("01");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						} else if(POSITION=='410C'||POSITION=='432K'){//执行董事			
							$("[name='LEGAL_JOB']").val("30");		
							$("[name='YFDBRZW']").val("30");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						} else if(POSITION=='436A'){//经理			
							$("[name='LEGAL_JOB']").val("20");	
							$("[name='YFDBRZW']").val("20");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						}
					}
				}		
				$("[name='REGISTER_ADDR']").val(enterpriseInfo.baseInfo.DOM);	
				//初始化股东信息
				var investors = enterpriseInfo.investors;
				var infoArray = [];
				for(var i=0;i<investors.length;i++){	
					var holder = investors[i];
					var info = {};
					if(holder.investor_type==1){//自然人类型						
						info.SHAREHOLDER_NAME = holder.INV;//股东姓名
						info.SHAREHOLDER_TYPE = "zrr";//股东类型
						info.LICENCE_TYPE = getZjlxType(holder.CERTYPE);//证照类型
					} else if(holder.investor_type==2){//非自然类型					
						info.SHAREHOLDER_NAME = holder.INV;//股东姓名
						info.SHAREHOLDER_TYPE = getShareholderType(holder.INVTYPE);//股东类型
						info.LICENCE_TYPE = getZjlxType(holder.BLICTYPE);//证照类型
					}
					
					infoArray.push(info);	
				}
				if(infoArray.length>0){
					addInitGdxx(infoArray);
				}
				//初始化董事信息	
				$("#olddsxxDiv").html("");
				$("#oldjsxxDiv").html("");
				$("#oldjlxxDiv").html("");
				var jsnum = 0;
				var dsnum = 0;
				var jlnum = 0;
				for(var i=0;i<members.length;i++){	
					var POSITION = members[i].POSITION;
					var PERSON_NAME = members[i].NAME;
					var dsjob = "";
					var jsjob = "";
					var jljob = "";
					if(POSITION=='410A'||POSITION=='431A'){//董事长
						dsjob ='01';
					} else if(POSITION=='410B'||POSITION=='432A'){//董事	
						dsjob ='03';				
					} else if(POSITION=='410C'||POSITION=='432K'){//执行董事		
						dsjob ='30';
					} else if(POSITION=='431B'){//副董事长	
						dsjob ='02';				
					}

					if(''!=dsjob){		
						dsnum++;
						addOldDsxxDiv(PERSON_NAME,dsjob,dsnum);						
						sleep(50);//暂停50毫秒
					}
					if(POSITION=='408A'){//监事
						jsjob ='10';				
					} 
					if(''!=jsjob){		
						jsnum++;
						addOldJsxxDiv(PERSON_NAME,jsjob,jsnum);				
						sleep(50);//暂停50毫秒
					}

					if(POSITION=='410A'||POSITION=='410B'||POSITION=='410C'||POSITION=='434Q'||POSITION=='434R'){//总经理
						jljob ='21';
					} else if(POSITION=='436A'){//经理
						jljob ='20';			
					}
					if(''!=jljob){		
						jlnum++;
						addOldJlxxDiv(PERSON_NAME,jljob,1);										
						sleep(50);//暂停50毫秒
					}
					
				}
				if(jsnum>1){//设监事会
					$("#jsba").hide();
				} else{					
					$("#jsba").show();
				}
			}			
		}
	}, false);
};
function sleep(d){
  for(var t = Date.now();Date.now() - t <= d;);
}

function getDateYearSub(startDateStr, endDateStr) {
	var day = 24 * 60 * 60 *1000; 

	var sDate = new Date(Date.parse(startDateStr.replace(/-/g, "/")));
	var eDate = new Date(Date.parse(endDateStr.replace(/-/g, "/")));

	//得到前一天(算头不算尾)
	sDate = new Date(sDate.getTime() - day);

	//获得各自的年、月、日
	var sY  = sDate.getFullYear();     
	var sM  = sDate.getMonth()+1;
	var sD  = sDate.getDate();
	var eY  = eDate.getFullYear();
	var eM  = eDate.getMonth()+1;
	var eD  = eDate.getDate();
	if(eY > sY) {
		return eY - sY;
	} else {
		alert("两个日期之间并非整年，请重新选择");
		return 0;
	}
}
/**
 * 初始化时添加股东信息
 */
function addInitGdxx(investors){
	$.post("ssnzqybaController/initGdxxDiv.do",{
		holder:JSON.stringify(investors)
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").html(responseText);
		//$("#gdxxDiv div").find("[name$='SHAREHOLDER_NAME']").attr("disabled",true);
		//$("#gdxxDiv div").find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
		//$("#gdxxDiv div").find("[name$='LICENCE_TYPE']").attr("disabled",true);
		initJobInfo();
	});
}
/**
 * 初始化时添加股东信息
 */
function addInitGdxxDiv(holder,i){
	$.post("ssnzqybaController/refreshGdxxDiv.do",{
		isClose:1,
		holder:encodeURIComponent(JSON.stringify(holder))
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);	
		initHolderInfo(holder,i);
		initJobInfo();
	});
}
function initHolderInfo(holder,i){	
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").val(holder.INV_NAME);
	//$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").attr("disabled",true)
	var LEGAL_PERSON = $("#gdxxDiv div").eq(i).find("[name$='LEGAL_PERSON']").attr("name");
	var LEGAL_IDNO_PERSON = $("#gdxxDiv div").eq(i).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
	var LICENCE_TYPE = $("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").attr("name");
	var LICENCE_NO = $("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").attr("name");
	var SHAREHOLDER_TYPE = getShareholderType(holder.INV_TYPE);
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").val(SHAREHOLDER_TYPE);
	//$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true)
	setGdlxValidate(SHAREHOLDER_TYPE,LEGAL_PERSON);
	setGdlxValidate(SHAREHOLDER_TYPE,LEGAL_IDNO_PERSON);
	setGdlxValidateToType(SHAREHOLDER_TYPE,LICENCE_TYPE,LICENCE_NO);
	$("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").val(getZjlxType(holder.B_LIC_TYPE));
	//$("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").attr("disabled",true);
	//$("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").val(holder.B_LIC_NO);
	//$("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").attr("disabled",true)
}
function getShareholderType(type){
	var SHAREHOLDER_TYPE="";
	if(type==20||type==21||type==22){//投资自然人	
		SHAREHOLDER_TYPE = "zrr";
	} else if(type==90||type==35){//其他		
		SHAREHOLDER_TYPE = "qtjg";
	} else{							
		SHAREHOLDER_TYPE = "qy";
	}	
	return SHAREHOLDER_TYPE;		
}
function getZjlxType(type){
	var zjlxType="SF";
	if(type==1||type==10){//中华人民共和国居民身份证
		zjlxType="SF";
	} else if(type==20){//中华人民共和国军官证
		zjlxType="JG";
	} else if(type==30){//中华人民共和国警官证
		zjlxType="QT";
	} else if(type==40){//外国（地区）护照
		zjlxType="HZ";
	} else if(type==51){//香港特别行政区护照
		zjlxType="HZ";
	} else if(type==52){//香港（永久性）居民身份证
		zjlxType="HKSF";
	} else if(type==53){//澳门特别行政区护照
		zjlxType="HZ";
	} else if(type==54){//澳门（永久性）居民身份证
		zjlxType="AMSF";
	} else if(type==55){//港澳居民来往内地通行证
		zjlxType="GATX";
	} else if(type==56){//台湾居民身份证
		zjlxType="TWSF";
	} else if(type==57){//台湾居民来往大陆通行证
		zjlxType="TWTX";
	} else if(type==58){//台湾农民身份有关证明
		zjlxType="QT";
	} else if(type==60){//户口薄
		zjlxType="QT";
	} else if(type==90){//其他有效身份证件
		zjlxType="QT";
	} else if(type){
		zjlxType = "YYZZ";
	}
	return zjlxType;
}

function getSlxzType(type){
	var slxzType="gykg";
	if(type==1151||type==1150){// 有限责任公司(自然人独资) 一人有限责任公司
		slxzType="zrrdz";
	} else if(type==1152||type==1153){//有限责任公司(自然人投资或控股的法人独资),有限责任公司（非自然人投资或控股的法人独资）
		slxzType="frdz";
	} else if(type==1130){//有限责任公司(自然人投资或控股)
		slxzType="zrrkg";
	} else if(type==1190){//其他有限责任公司
		slxzType="qtyxzrgs";
	} 
	return slxzType;
}


/**
 * 股东资金计算
 */
function setInvestment(){
	var totla = 0;
	$("#gdxxDiv div").each(function(i){		
		var INVESTMENT_CASH = $(this).find("[name$='INVESTMENT_CASH']").val();
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();
		var CONTRIBUTIONS = Number(INVESTMENT_CASH)+Number(INVESTMENT_MATERIAL)
		+Number(INVESTMENT_TECHNOLOGY)+Number(INVESTMENT_LAND)
		+Number(INVESTMENT_STOCK)+Number(INVESTMENT_OTHER);	
		if(Number(CONTRIBUTIONS)>0){	
			totla+=Number(CONTRIBUTIONS);
			$(this).find("[name$='CONTRIBUTIONS']").val(CONTRIBUTIONS.toFixed(6));
		}else{
			$(this).find("[name$='CONTRIBUTIONS']").val('');
			$(this).find("[name$='PROPORTION']").val('');
		}
	});
	if(Number(totla)>0){
		//$("input[name='INVESTMENT']").val(totla.toFixed(6));			
		$("#gdxxDiv div").each(function(i){
			var rjcze = $(this).find("[name$='CONTRIBUTIONS']").val();	
			var a = Number(rjcze)/(Number(totla))*100;
			$(this).find("[name$='PROPORTION']").val(a.toFixed(2)+"%");
		});
	}else{		
		//$("input[name='INVESTMENT']").val('');		
	}
}
/**
 * 删除
 */
function delDiv(o){
	$(o).closest("div").remove();
} 