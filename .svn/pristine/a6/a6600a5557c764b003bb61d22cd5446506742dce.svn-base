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
			var CANCEL_TYPE = eflowObj.busRecord.CANCEL_TYPE;
			if(CANCEL_TYPE=='SSJDZX'){
				initJyzxyy();
				$(".ybzx").remove();
			} else{							
				var COMPANY_TYPECODE = eflowObj.busRecord.COMPANY_TYPECODE;
				initYbzxyy(1);
				$("[name$='QSZ_IDTYPE']").attr("disabled",true);
				$("#gdxxDiv .syj-closebtn").remove();
				$("#jyzxqttzrcns").remove();
			}
			//初始化企业基本信息
			$("#COMPANY_FORM input[name='CANCEL_TYPE']").val(eflowObj.busRecord.CANCEL_TYPE);
			$("#COMPANY_FORM input[name='CANCEL_TYPENAME']").val(eflowObj.busRecord.CANCEL_TYPENAME);	
			setSyqxValidate(eflowObj.busRecord.SYQX1,'SYQX2');		
			setCancelReason(eflowObj.busRecord.CANCEL_REASON);
			
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
			
			//初始化字段审核意见
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){	
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
			
            //邮寄公章信息
            if (eflowObj.busRecord.ISEMAIL == 1) {
                $("#emailInfo").attr("style", "");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "1");
            } else {
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "-1");
            }
		}else{					
			var CANCEL_TYPE = $("[name='CANCEL_TYPE']").val();
			//判断是否选择企业类型
			if(CANCEL_TYPE==null||CANCEL_TYPE==''){
				window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
			}
			setSyqxValidate(1,'SYQX2');	
			if(CANCEL_TYPE=='SSJDZX'){
				initJyzxyy();
				$(".ybzx").remove();
			} else{				
				initYbzxyy(1);				
				$("[name$='QSZ_IDTYPE']").attr("disabled",true);
				$("#jyzxqttzrcns").remove();
			}
			$("[name='OPERATOR_IDTYPE']").val('SF');
			//$("[name='OPERATOR_IDTYPE']").attr("disabled",true);
			setZjValidate("SF",'OPERATOR_IDNO')
			$("[name='LEGAL_IDTYPE']").val('SF');
			//$("[name='LEGAL_IDTYPE']").attr("disabled",true);
			setZjValidate("SF",'LEGAL_IDNO');
			
			//initDisabled();
			
			
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
function initDisabled(){
	$("#gdxxDiv div").find("[name$='SHAREHOLDER_NAME']").attr("disabled",true);
	$("#gdxxDiv div").find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
	$("#gdxxDiv div").find("[name$='LICENCE_TYPE']").attr("disabled",true);
	$("[name='DIRECTOR_NAME']").attr("disabled",true);			
	$("[name='DIRECTOR_JOB']").attr("disabled",true);
	$("[name='LEGAL_JOB']").attr("disabled",true);
}
function initJyzxyy(){	
   var url = __ctxPath+'/dictionaryController.do?load&defaultEmpty=true&typeCode=jdzxzxyy';
	$('#cancelReason').combobox({
		url:url,
		method:'post',
		valueField:'DIC_CODE',
		textField:'DIC_NAME',
		panelHeight:'auto',
		required:true,
		editable:false,
		onSelect:function(rec){
			
		}
	});	
}
function initYbzxyy(dicdesc){			
   var url = __ctxPath+'/dictionaryController/loadDataToDesc.do?typeCode=ybzxzxyy&dicDesc='+dicdesc;
	$('#cancelReason').combobox({
		url:url,
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight:'auto',
		required:true,
		editable:false,
		onSelect:function(rec){
			var v = rec.VALUE;
			setCancelReason(v);
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
					$("#"+name+"_DIV").html(spanHtml)
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
	
	var CANCEL_REASON =  $("[name='CANCEL_REASON']").val();
	if(CANCEL_REASON==null||CANCEL_REASON==''){
		art.dialog({
			content : "请选择注销原因！",
			icon : "error",
			ok : true
		}); 
		return;
	}
	var COMPANY_TYPE =  $("[name='COMPANY_TYPE']").val();
	if(COMPANY_TYPE==null||COMPANY_TYPE==''){
		art.dialog({
			content : "请选择公司类型！",
			icon : "error",
			ok : true
		}); 
		return;
	}
	var CANCEL_TYPE = $("[name='CANCEL_TYPE']").val();
	if(CANCEL_TYPE=='SSYBZX'){
		var QSWJQK = $("[name='QSWJQK']").val();
		var ZQZWQLQKWCBZ = $("[name='ZQZWQLQKWCBZ']").val();
		var FFRFZJGZXWBBZ = $("[name='FFRFZJGZXWBBZ']").val();
		if(QSWJQK && QSWJQK!="1"){
			art.dialog({
				content : "必须完成清算完结情况！",
				icon : "error",
				ok : true
			}); 
			return;
		}
		if(ZQZWQLQKWCBZ && ZQZWQLQKWCBZ!="1"){
			art.dialog({
				content : "必须完成债权债务清理情况完成标准！",
				icon : "error",
				ok : true
			}); 
			return;
		}
		if(FFRFZJGZXWBBZ && FFRFZJGZXWBBZ!="1"){
			art.dialog({
				content : "必须完成非法人分支机构注销完毕标准！",
				icon : "error",
				ok : true
			}); 
			return;
		}
		var flag = true;
		var flagNum = 0;
		$("#qszcyxxDiv div").each(function(i){
			var QSZ_JOB = $(this).find("[name$='QSZ_JOB']").val();//职务
			if(QSZ_JOB && QSZ_JOB=='1'){
				flag = false;
				flagNum ++;
			}
		});
		if($("#qszcyxxDiv").children("div").length<2){			
			art.dialog({
				content : "至少需要有两个清算组成员！",
				icon : "error",
				ok : true
			}); 
			return;
		}
		if(flag){
			art.dialog({
				content : "必须要有清算组成员负责人！",
				icon : "error",
				ok : true
			}); 
			return;
		} else if(flagNum!=1){		
			art.dialog({
				content : "只能有一个清算组成员负责人！",
				icon : "error",
				ok : true
			}); 
			return;
		}
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
		 //获取清算组成员信息
		 flowSubmitObj.QSZCY_JSON =getQszcyxxJson();
		 
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
	 //获取清算组成员信息
	 flowSubmitObj.QSZCY_JSON =getQszcyxxJson();
	 
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
	$.post("cancelController/refreshGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);
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
        var SHAREHOLDER_COMPANYCOUNTRY = $(this).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//国别
		
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



/*************清算组成员信息JS开始****************/

/**
 * 添加清算组成员信息
 */
function addQszcyxxDiv(){
	$.post("cancelController/refreshQszcyxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qszcyxxDiv").append(responseText);
		$("[name$='QSZ_IDTYPE']").attr("disabled",true);
	});
}
/**
 * 初始化时添加清算组成员信息
 */
function addInitQszcyxxDiv(obj,i){
	$.post("cancelController/refreshQszcyxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qszcyxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(obj,$("#qszcyxxDiv div").eq(i));
		$("[name$='QSZ_IDTYPE']").attr("disabled",true);
	});
}
/**
 * 删除清算组成员信息
 */
function delQszcyxxDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取清算组成员信息
 */
function getQszcyxxJson(){
	var infoArray = [];
	$("#qszcyxxDiv div").each(function(i){
		var QSZ_NAME = $(this).find("[name$='QSZ_NAME']").val();//姓名
		var QSZ_JOB = $(this).find("[name$='QSZ_JOB']").val();//职务
		var QSZ_IDTYPE = $(this).find("[name$='QSZ_IDTYPE']").val();//证照类型
		var QSZ_IDNO = $(this).find("[name$='QSZ_IDNO']").val();//证件号码		
		var QSZ_ADDR = $(this).find("[name$='QSZ_ADDR']").val();	//地址
		var QSZ_MOBILE = $(this).find("[name$='QSZ_MOBILE']").val();//联系方式
		
		if(null!=QSZ_NAME&&QSZ_NAME!=""){
			var info = {};
			info.QSZ_NAME = QSZ_NAME;
			info.QSZ_JOB = QSZ_JOB;
			info.QSZ_IDTYPE = QSZ_IDTYPE;
			info.QSZ_IDNO = QSZ_IDNO;
			info.QSZ_ADDR = QSZ_ADDR;
			info.QSZ_MOBILE = QSZ_MOBILE;
			infoArray.push(info);			
		}
		
	});
	if(infoArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(infoArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
//初始化清算组成员信息
function setQszcyxx(json){
	var infos = eval(json);
	if(infos){				
		for(var i=0;i<infos.length;i++){			
			if(i>0){
				addInitQszcyxxDiv(infos[i],i);
			}else{				
				FlowUtil.initFormObjValue(infos[i],$("#qszcyxxDiv div").eq(i));
			}
		}
	}
}
/*************清算组成员信息JS结束****************/


//选择适用情形为"未开业，无债权债务"
function setSyqxValidate(s,name){
	if(s=="1"||s=="2"){
		$("[name='"+name+"']").show();
		$("[name='"+name+"']").addClass(" validate[required]");
	}else{
		$("[name='"+name+"']").hide();		
		$("[name='"+name+"']").val('');
		$("[name='"+name+"']").removeClass(" validate[required]");
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
				$("[name='COMPANY_NAME']").val(enterpriseInfo.baseInfo.ENTNAME);
				$("[name='COMPANY_CODE']").val(enterpriseInfo.baseInfo.UNISCID);
				$("[name='COMPANY_TYPECODE']").val(enterpriseInfo.baseInfo.ENTTYPE);	
				var typeCode = getSlxzType(enterpriseInfo.baseInfo.ENTTYPE);
				$("#COMPANY_TYPE").combobox("setValue",typeCode);		
				/*$.post("cancelController.do?getCommercialDicType",{
					typecode:enterpriseInfo.baseInfo.ENTTYPE
				}, function(responseText, status, xhr) {
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
						$("[name='COMPANY_TYPE']").val(resultJson.data.TYPE_NAME);
					}
				});*/
				$("[name='LEGAL_NAME']").val(enterpriseInfo.fddbr.NAME);
				//$("[name='LEGAL_IDNO']").val(enterpriseInfo.fddbr.CERNO);	
				var members = enterpriseInfo.members;		
				for(var i=0;i<members.length;i++){					
					var PERSON_NAME = members[i].NAME;
					var POSITION = members[i].POSITION;
					
					if(PERSON_NAME==enterpriseInfo.fddbr.NAME){
						if(POSITION=='431A'||POSITION=='410A'){//董事长							
							$("[name='LEGAL_JOB']").val("01");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						} else if(POSITION=='410C'||POSITION=='432K'){//执行董事			
							$("[name='LEGAL_JOB']").val("30");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						} else if(POSITION=='436A'){//经理			
							$("[name='LEGAL_JOB']").val("20");
							//$("[name='LEGAL_JOB']").attr("disabled",true);
							break; // 跳出循环
						}
					}
				}
				var CANCEL_TYPE = $("[name='CANCEL_TYPE']").val();
				if(CANCEL_TYPE=='SSYBZX'){				
					$("[name='REGISTER_ADDR']").val(enterpriseInfo.baseInfo.DOM);	
					//初始化股东信息
					var investors = enterpriseInfo.investors;
					var infoArray = [];
					for(var i=0;i<investors.length;i++){	
						var holder = investors[i];
						
						var info = {};
						if(holder.investor_type==1){
							
							info.SHAREHOLDER_NAME = holder.INV;
							info.SHAREHOLDER_TYPE = "zrr";
							info.LICENCE_TYPE = getZjlxType(holder.CERTYPE);
						} else if(holder.investor_type==2){								
							info.SHAREHOLDER_NAME = holder.INV;
							info.SHAREHOLDER_TYPE = getShareholderType(holder.INVTYPE);
							info.LICENCE_TYPE = getZjlxType(holder.BLICTYPE);
						}
						//info.LICENCE_NO = holder.B_LIC_NO;
						infoArray.push(info);	
					}
					if(infoArray.length>0){
						addInitGdxx(infoArray);
					}
					//初始化董事信息		
					for(var i=0;i<members.length;i++){	
						var POSITION = members[i].POSITION;
						if(POSITION=='431A'||POSITION=='410A'){//董事长					
							$("[name='DIRECTOR_NAME']").val(members[i].NAME);
							//$("[name='DIRECTOR_NAME']").attr("disabled",true);					
							$("[name='DIRECTOR_JOB']").val("01");
							//$("[name='DIRECTOR_JOB']").attr("disabled",true);
							break; // 跳出循环
						} else if(POSITION=='410C'||POSITION=='432K'){//执行董事						
							$("[name='DIRECTOR_NAME']").val(members[i].NAME);
							//$("[name='DIRECTOR_NAME']").attr("disabled",true);			
							$("[name='DIRECTOR_JOB']").val("30");
							//$("[name='DIRECTOR_JOB']").attr("disabled",true);
							break; // 跳出循环
						}
					}
				}
			}			
		}
	}, false);
};
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
 * 初始化时添加股东信息
 */
function addInitGdxx(investors){
	$.post("cancelController/initGdxxDiv.do",{
		isClose:1,
		holder:JSON.stringify(investors)
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").html(responseText);
		//$("#gdxxDiv div").find("[name$='SHAREHOLDER_NAME']").attr("disabled",true);
		//$("#gdxxDiv div").find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
		//$("#gdxxDiv div").find("[name$='LICENCE_TYPE']").attr("disabled",true);
	});
}
/**
 * 初始化时添加股东信息
 */
function addInitGdxxDiv(holder,i){
	$.post("cancelController/refreshGdxxDiv.do",{
		isClose:1,
		holder:encodeURIComponent(JSON.stringify(holder))
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);	
		initHolderInfo(holder,i)
	});
}
function initHolderInfo(holder,i){	
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").val(holder.INV_NAME);
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").attr("disabled",true)
	var LEGAL_PERSON = $("#gdxxDiv div").eq(i).find("[name$='LEGAL_PERSON']").attr("name");
	var LEGAL_IDNO_PERSON = $("#gdxxDiv div").eq(i).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
	var LICENCE_TYPE = $("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").attr("name");
	var LICENCE_NO = $("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").attr("name");
	var SHAREHOLDER_TYPE = getShareholderType(holder.INV_TYPE);
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").val(SHAREHOLDER_TYPE);
	$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true)
	setGdlxValidate(SHAREHOLDER_TYPE,LEGAL_PERSON);
	setGdlxValidate(SHAREHOLDER_TYPE,LEGAL_IDNO_PERSON);
	setGdlxValidateToType(SHAREHOLDER_TYPE,LICENCE_TYPE,LICENCE_NO);
	$("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").val(getZjlxType(holder.B_LIC_TYPE));
	$("#gdxxDiv div").eq(i).find("[name$='LICENCE_TYPE']").attr("disabled",true)
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