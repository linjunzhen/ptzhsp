$(function() {
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//初始化表单字段值
		if(eflowObj.busRecord){
			//变更企业信息查询不可用、开放暂存可用
			$("#queryArea").attr("style","display:none");
						
			//初始化新法人信息				
			var nature = eflowObj.busRecord.COMPANY_NATURE;
			var ssdjzw = '';
			if(nature=='02'||nature=='04'||nature=='07'){
				ssdjzw = '02,03,10,11,12,21,30';
			}else if(nature=='01'||nature=='03'){
				ssdjzw = '01,02,03,10,11,12,21';
			}
			$.post("enterpriseChangeController/refreshLegalDiv.do",{
				ssdjzw:ssdjzw
			}, function(responseText, status, xhr) {
				$("#legalDiv").html(responseText);
				$("[name='LEGAL_NAME_CHANGE']").val(eflowObj.busRecord.LEGAL_NAME_CHANGE);
				$("[name='LEGAL_JOB_CHANGE']").val(eflowObj.busRecord.LEGAL_JOB_CHANGE);
				$("[name='LEGAL_MOBILE_CHANGE']").val(eflowObj.busRecord.LEGAL_MOBILE_CHANGE);
				$("[name='LEGAL_FIXEDLINE_CHANGE']").val(eflowObj.busRecord.LEGAL_FIXEDLINE_CHANGE);
				$("[name='LEGAL_EMAIL_CHANGE']").val(eflowObj.busRecord.LEGAL_EMAIL_CHANGE);
				$("[name='LEGAL_IDTYPE_CHANGE']").val(eflowObj.busRecord.LEGAL_IDTYPE_CHANGE);
				$("[name='LEGAL_IDNO_CHANGE']").val(eflowObj.busRecord.LEGAL_IDNO_CHANGE);
				$("[name='LEGAL_COUNTRY_CHANGE']").val(eflowObj.busRecord.LEGAL_COUNTRY_CHANGE);
				$("[name='LEGAL_PRODUCEMODE_CHANGE']").val(eflowObj.busRecord.LEGAL_PRODUCEMODE_CHANGE);
				$("[name='LEGAL_ADDRESS_CHANGE']").val(eflowObj.busRecord.LEGAL_ADDRESS_CHANGE);
				
				//初始化字段审核意见
				if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){	
					setFieldAudit(eflowObj.EFLOW_EXEID);	
				}
			});
			
			//邮寄营业执照信息
            if (eflowObj.busRecord.ISEMAIL == 1) {
                $("#emailInfo").attr("style", "");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "1");
            } else {
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "-1");
            }
            
            //经营期限变更
            if(eflowObj.busRecord.OPRRATE_TERM_TYPE_CHANGE =="1"){//年
            	$("input[name='BUSSINESS_YEARS_CHANGE']").attr("disabled",false);
            	$("input[name='BUSSINESS_YEARS_CHANGE']").addClass(" validate[required]");
            }else{
            	$("input[name='BUSSINESS_YEARS_CHANGE']").val("");
        		$("input[name='BUSSINESS_YEARS_CHANGE']").attr("disabled",true); 
        		$("input[name='BUSSINESS_YEARS_CHANGE']").removeClass(" validate[required]");
            }
            
            //原经营期限
            if(eflowObj.busRecord.OPRRATE_TERM_TYPE =="1"){//年
            	$("input[name='BUSSINESS_YEARS']").attr("disabled",false);
            	//$("input[name='BUSSINESS_YEARS']").addClass(" validate[required]");
            }else{
            	$("input[name='BUSSINESS_YEARS']").val("");
        		$("input[name='BUSSINESS_YEARS']").attr("disabled",true); 
        		$("input[name='BUSSINESS_YEARS']").removeClass(" validate[required]");
            }
            
            //回填原/新出资全部缴付到位期限
            if(eflowObj.busRecord.PAYMENT_PERIOD_OLD){
            	$("input[name='PAYMENT_PERIOD_OLD']").val(eflowObj.busRecord.PAYMENT_PERIOD_OLD);
            }
            if(eflowObj.busRecord.PAYMENT_PERIOD){
            	$("input[name='PAYMENT_PERIOD']").val(eflowObj.busRecord.PAYMENT_PERIOD);
            }
            
		}
		
	}
    var IS_BUSSINESS_ADDR=$("input[name='IS_BUSSINESS_ADDR']:checked").val();
    if(IS_BUSSINESS_ADDR=='1'){
        $("#bussinessAddrTable").attr("style","");
    }
    $("#gdxxDiv").find("[name$='SHAREHOLDER_TYPE']").each(function(){
    	$(this).attr("disabled",true);
    })
    
	var companyType = $("#COMPANY_TYPE").combobox("getValue");
    var companyTypeChange =$("#COMPANY_TYPE_CHANGE").combobox("getValue");
    var url="";
    if(companyTypeChange!=null && companyTypeChange!="" && companyTypeChange!=undefined){
    	url = 'dictionaryController/loadData.do?typeCode='+companyTypeChange+'&orderType=asc';
    }else{
    	url = 'dictionaryController/loadData.do?typeCode='+companyType+'&orderType=asc';
    }
    $('#COMPANY_NATURE').combobox('reload',url);
       
    //点击是否有生产经营地址
    $("input[name='IS_BUSSINESS_ADDR']").on("click", function(event) {
        if("1"==this.value){
            $("#bussinessAddrTable").attr("style","");
            AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSSINESS_ADDR;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","1");
		}else{
            $("#bussinessAddrTable").attr("style","display:none;");
            AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSSINESS_ADDR;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","-1");
		}
    });

	//经营期限
	$("input[name='OPRRATE_TERM_TYPE_CHANGE']").on("click", function(event) {
		setJyqx(this.value);
	});	
	
	//原经营期限
	$("input[name='OPRRATE_TERM_TYPE']").on("click", function(event) {
		setYjyqx(this.value);
	});	

	//联络员是否变更选择
	$('[name="LIAISON_IDTYPE"]').attr("disabled",true);
	$("input[name='IS_LIAISON_CHANGE']").on("click",function(event){
		if($(this).is(":checked")){
			$(this).closest("div").find('[name="setHolderInfo"]').attr("disabled",false);
			$('[name="LIAISON_NAME"]').attr("disabled",false);
			$('[name="LIAISON_FIXEDLINE"]').attr("disabled",false);
			$('[name="LIAISON_MOBILE"]').attr("disabled",false);
			$('[name="LIAISON_EMAIL"]').attr("disabled",false);
			$('[name="LIAISON_IDTYPE"]').attr("disabled",false);
			$('[name="LIAISON_IDNO"]').attr("disabled",false);
		}else{
			$(this).closest("div").find('[name="setHolderInfo"]').attr("disabled",true);
			$('[name="LIAISON_NAME"]').attr("disabled",true);
			$('[name="LIAISON_FIXEDLINE"]').attr("disabled",true);
			$('[name="LIAISON_MOBILE"]').attr("disabled",true);
			$('[name="LIAISON_EMAIL"]').attr("disabled",true);
			$('[name="LIAISON_IDTYPE"]').attr("disabled",true);
			$('[name="LIAISON_IDNO"]').attr("disabled",true);
		}
	});

    //邮寄营业执照信息
	var ISEMAIL_ITEM = $("input[name='ISEMAIL_ITEM']").val();
    if("1"==ISEMAIL_ITEM){
        $("input[name='ISEMAIL']").attr("checked","checked");
        $("#emailInfo").attr("style","");
        AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
    }	
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

/*************初始化JS开始**********************/
//设置字段审批意见
function setFieldAudit(exeId){
	$.post("fieldAuditController/datagrid.do?isShow=0&Q_T.EXE_ID_EQ="+exeId,{
	}, function(responseText, status, xhr) {
		var resultJson = responseText.rows;
		for(var i=0;i<resultJson.length;i++){	
			//console.log(resultJson[i].FIELD_NAME);
			var BELONG_ID =resultJson[i].BELONG_ID;
			var num = 0

			if(BELONG_ID.indexOf("waytabble")>0){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
				var parentNum = BELONG_ID.substring(BELONG_ID.indexOf("_")+1,BELONG_ID.indexOf("_",BELONG_ID.indexOf("_")+1))-1;
				$("#newGdxxDiv").find(".gqlytable").eq(parentNum).find("[name$='"+resultJson[i].FIELD_NAME+"']").each(function(index){
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
			}else if(BELONG_ID.indexOf("bgholder")>=0){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
				if(isNaN(num)){
				   num = 0;
				}
				$("#newGdxxDiv").find("[name$='"+resultJson[i].FIELD_NAME+"']").each(function(index){
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
			}else{
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
/*************初始化JS结束**********************/

/*************数据提交JS结束**********************/
//流程暂存操作
function FLOW_TempSaveFun() {
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
	//将其转换成JSON对象
	var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
	
	flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
	//变更选项
	var CHANGE_OPTIONS = "";
	$.each($('input[name="CHANGE_OPTIONS"]:checkbox:checked'),function(){
		CHANGE_OPTIONS+=","+$(this).val();
    });
	flowSubmitObj.CHANGE_OPTIONS = CHANGE_OPTIONS.substring(1);
	
	var forms = $("form[id]");
	forms.each(function() {
		var formId = $(this).attr("id");
		var formData = FlowUtil.getFormEleData(formId);
		for ( var index in formData) {
			flowSubmitObj[eval("index")] = formData[index];
		}
	});	

	//获取原股东信息
	flowSubmitObj.HOLDER_JSON =getGdxxJson();
	//获取股东信息
	if($('input[name="CHANGE_OPTIONS"][value="05"]').is(':checked')||$('input[name="CHANGE_OPTIONS"][value="07"]').is(':checked')
			||$('input[name="CHANGE_OPTIONS"][value="09"]').is(':checked')){
		flowSubmitObj.HOLDER_JSON_CHANGE =getNewGdxxJson();
	}
	//获取原董事信息
	flowSubmitObj.DIRECTOR_JSON =getOldDsxxJson();
	//获取原监事信息
	flowSubmitObj.SUPERVISOR_JSON =getOldJsxxJson();
	//获取原经理信息
	flowSubmitObj.MANAGER_JSON =getOldJlxxJson();
	//获取董事信息
	if($('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){
		flowSubmitObj.DIRECTOR_JSON_CHANGE =getDsxxJson();
	}
	//获取监事信息
	if($('input[name="CHANGE_OPTIONS"][value="11"]').is(':checked')){
		flowSubmitObj.SUPERVISOR_JSON_CHANGE =getJsxxJson();
	}
	//获取经理信息
	if($('input[name="CHANGE_OPTIONS"][value="12"]').is(':checked')){
		flowSubmitObj.MANAGER_JSON_CHANGE =getJlxxJson();
	}	 
	 
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
 * 提交流程按钮
 */
function FLOW_SubmitFun() {
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var companySetNature= $("#COMPANY_NATURE").combobox('getValue');//公司设立性质编码
	
	//校验原监事会不可变更为不设监事会的类型、原公司为一个监事时不可以变更为设监事会的类型
	if($("#oldJsxxDiv").children("div").length>1){	
		if(companySetNature!='04'){//04为监事会
			art.dialog({
				content : "该企业设立监事会，请重新选择公司设立性质！",
				icon : "error",
				ok : true
			}); 
			return;
		}	
	}else if($("#oldJsxxDiv").children("div").length==1){		
		if(companySetNature=='04'){	
			art.dialog({
				content : "该企业不设立监事会，请重新选择公司设立性质！",
				icon : "error",
				ok : true
			}); 
			return;
		}
	}
	
	
	if($('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){//董事变更
		if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){//设董事会、监事设立;设董事会监事会设立
			var num = 0;
			var dsnum = 0;
			$("select[name$='DIRECTOR_JOB']").each(function(i){		
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
				return;
			}else if(num>1){
				art.dialog({
					content : "董事信息中只能有1位董事长！",
					icon : "warning",
					ok : true
				});
				return;
			}else if(dsnum<3){
				art.dialog({
					content : "董事数量必须大于等于3！",
					icon : "warning",
					ok : true
				});
				return;
			}else if(dsnum>13){
				art.dialog({
					content : "董事数量必须小于等于13！",
					icon : "warning",
					ok : true
				});
				return;
			}
		}
	}
	
	if($('input[name="CHANGE_OPTIONS"][value="11"]').is(':checked')){//监事变更
		if(companySetNature=='04'){
			var num = 0;
			var jsnum = 0;
			$("select[name$='SUPERVISOR_JOB']").each(function(i){		
				var SUPERVISOR_JOB = $(this).val();
				if(SUPERVISOR_JOB=='11'){	
					num++;
				}
				jsnum++;
			});
			if(num==0){
				art.dialog({
					content : "监事信息中必须要有监事会主席！",
					icon : "warning",
					ok : true
				});
				return;
			}else if(num>1){
				art.dialog({
					content : "监事信息中只能有1位监事会主席！",
					icon : "warning",
					ok : true
				});
				return;
			}else if(jsnum<3){
				art.dialog({
					content : "监事数量必须大于等于3！",
					icon : "warning",
					ok : true
				});
				return;
			}
		}
	}

    
	//未变更法人、进行董事/经理备案情况下，校验原法人信息与人事信息是否相符。
	var isOk  = false;
	var legalJob = $("select[name='LEGAL_JOB']").val();
	var legalName = $("input[name='LEGAL_NAME']").val();
	var legalIdNo = $("input[name='LEGAL_IDNO']").val();
	if(!$('input[name="CHANGE_OPTIONS"][value="03"]').is(':checked')){
		if(legalJob=="01" || legalJob=="30"){//执行董事或董事长
			if($('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){//董事备案
				//新董事比对姓名、职务、证件号码
				$("#dsxxDiv div").each(function(i){
					var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
					var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();	//职务
					var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
					if((AppUtil.isObjectValueEqual(legalName,DIRECTOR_NAME)) &&  (AppUtil.isObjectValueEqual(legalJob,DIRECTOR_JOB))
							&&(AppUtil.isObjectValueEqual(legalIdNo,DIRECTOR_IDNO))){
						isOk = true;
					}
				});
			}else{
				//旧董事比对姓名、职务
				$("#oldDsxxDiv div").each(function(i){//旧董事信息
					var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME_OLD']").val();//姓名
					var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB_OLD']").val();//职务
					if((AppUtil.isObjectValueEqual(legalName,DIRECTOR_NAME)) && (AppUtil.isObjectValueEqual(legalJob,DIRECTOR_JOB))){
						isOk = true;
					}
				});
			}
		}else if(legalJob=="20"){//经理
			if($('input[name="CHANGE_OPTIONS"][value="12"]').is(':checked')){//经理备案
				//新经理比对姓名、职务、证件号码
				$("#jlxxDiv div").each(function(i){
					var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
					var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
					var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();//证件号码
					if((AppUtil.isObjectValueEqual(legalName,MANAGER_NAME)) && (AppUtil.isObjectValueEqual(legalJob,MANAGER_JOB))
							&&(AppUtil.isObjectValueEqual(legalIdNo,MANAGER_IDNO))){
						isOk = true;
					}
				});
			}else{
				//旧经理比对姓名、职务
				$("#oldJlxxDiv div").each(function(i){//旧经理信息
					var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME_OLD']").val();//姓名
					var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB_OLD']").val();//职务
					if((AppUtil.isObjectValueEqual(legalName,MANAGER_NAME)) && (AppUtil.isObjectValueEqual(legalJob,MANAGER_JOB))){
						isOk = true;
					}
				});
			}
		}
		if(!isOk){
			art.dialog({
				content : "法人信息与人事信息不符，请确认！",
				icon : "error",
				ok : true
			});
			return ;
		}
	}
	


	//验证表单是否合法
	var valiateTabForm = AppUtil.validateWebsiteTabForm();
	if (valiateTabForm) {
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
		//将其转换成JSON对象
		var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
		
		flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
		//变更选项
		var CHANGE_OPTIONS = "";
		$.each($('input[name="CHANGE_OPTIONS"]:checkbox:checked'),function(){
			CHANGE_OPTIONS+=","+$(this).val();
	    });
		flowSubmitObj.CHANGE_OPTIONS = CHANGE_OPTIONS.substring(1);
		
		var forms = $("form[id]");
		forms.each(function() {
			var formId = $(this).attr("id");
			var formData = FlowUtil.getFormEleData(formId);
			for ( var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
		});

		//获取原股东信息
		flowSubmitObj.HOLDER_JSON =getGdxxJson();
		//获取股东信息
		if($('input[name="CHANGE_OPTIONS"][value="05"]').is(':checked')||$('input[name="CHANGE_OPTIONS"][value="07"]').is(':checked')
				||$('input[name="CHANGE_OPTIONS"][value="09"]').is(':checked')){
			flowSubmitObj.HOLDER_JSON_CHANGE =getNewGdxxJson();
		}		

		//获取原董事信息
		flowSubmitObj.DIRECTOR_JSON =getOldDsxxJson();
		//获取原监事信息
		flowSubmitObj.SUPERVISOR_JSON =getOldJsxxJson();
		//获取原经理信息
		flowSubmitObj.MANAGER_JSON =getOldJlxxJson();
		//获取董事信息
		if($('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){
			flowSubmitObj.DIRECTOR_JSON_CHANGE =getDsxxJson();
		}
		//获取监事信息
		if($('input[name="CHANGE_OPTIONS"][value="11"]').is(':checked')){
			flowSubmitObj.SUPERVISOR_JSON_CHANGE =getJsxxJson();
		}
		//获取经理信息
		if($('input[name="CHANGE_OPTIONS"][value="12"]').is(':checked')){
			flowSubmitObj.MANAGER_JSON_CHANGE =getJlxxJson();
		}

		var postParam = $.param(flowSubmitObj);
		AppUtil.ajaxProgress({
			url : "webSiteController.do?submitApply",
			params : postParam,
			callback : function(resultJson) {
				if (resultJson.OPER_SUCCESS) {
					art.dialog({
						content : resultJson.OPER_MSG,
						icon : "succeed",
						lock : true,
						ok : function() {
							window.top.location.href = __ctxPath + "/webSiteController.do?usercenter";
						},
						close : function() {
							window.top.location.href = __ctxPath + "/webSiteController.do?usercenter";
						}
					});
				} else {
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


/**
 * 获取原股东信息
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
        var SHAREHOLDER_COMPANYCOUNTRY = $(this).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//国别
		var LEGAL_PERSON = $(this).find("[name$='LEGAL_PERSON']").val();// 投资企业或其他组织机构法定代表人姓名
        var LEGAL_IDNO_PERSON = $(this).find("[name$='LEGAL_IDNO_PERSON']").val();// 投资企业或其他组织机构法定代表人身份证号码
		var INVESTMENT_CASH = $(this).find("[name$='INVESTMENT_CASH']").val();//现金
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//实物
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		
		var CONTRIBUTIONS = $(this).find("[name$='CONTRIBUTIONS']").val();//认缴出资额
		var CONTRIBUTIONS_REMAIN = $(this).find("[name$='CONTRIBUTIONS_REMAIN']").val();//认缴出资额
		var PROPORTION = $(this).find("[name$='PROPORTION']").val();//占注册资本比例
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
			gdxx.CONTRIBUTIONS_REMAIN = CONTRIBUTIONS_REMAIN;
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

/**
 * 获取原董事信息
 */
function getOldDsxxJson(){
	var dsxxArray = [];
	$("#oldDsxxDiv div").each(function(i){
		var DIRECTOR_NAME_OLD = $(this).find("[name$='DIRECTOR_NAME_OLD']").val();//姓名
		var DIRECTOR_JOB_OLD = $(this).find("[name$='DIRECTOR_JOB_OLD']").val();//职务
		var DIRECTOR_IDTYPE_OLD = $(this).find("[name$='DIRECTOR_IDTYPE_OLD']").val();//证件类型
		var DIRECTOR_IDNO_OLD = $(this).find("[name$='DIRECTOR_IDNO_OLD']").val();//证件号码
		var DIRECTOR_PHONE_OLD = $(this).find("[name$='DIRECTOR_PHONE_OLD']").val();//手机号码

		
		var dsxx = {};
		dsxx.DIRECTOR_NAME_OLD = DIRECTOR_NAME_OLD;
		dsxx.DIRECTOR_JOB_OLD = DIRECTOR_JOB_OLD;
		dsxx.DIRECTOR_IDTYPE_OLD = DIRECTOR_IDTYPE_OLD;
		dsxx.DIRECTOR_IDNO_OLD = DIRECTOR_IDNO_OLD;
		dsxx.DIRECTOR_PHONE_OLD = DIRECTOR_PHONE_OLD;
		if(DIRECTOR_NAME_OLD!=null && DIRECTOR_NAME_OLD!=""){//不为空存入信息
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
 * 获取原监事信息
 */
function getOldJsxxJson(){
	var jsxxArray = [];
	$("#oldJsxxDiv div").each(function(i){
		var SUPERVISOR_NAME_OLD = $(this).find("[name$='SUPERVISOR_NAME_OLD']").val();//姓名
		var SUPERVISOR_JOB_OLD = $(this).find("[name$='SUPERVISOR_JOB_OLD']").val();//职务
		var jsxx = {};
		jsxx.SUPERVISOR_NAME_OLD = SUPERVISOR_NAME_OLD;
		jsxx.SUPERVISOR_JOB_OLD = SUPERVISOR_JOB_OLD;
		if(SUPERVISOR_NAME_OLD!=null && SUPERVISOR_NAME_OLD!=""){
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
 * 获取原经理信息
 */
function getOldJlxxJson(){
	var jlxxArray = [];
	$("#oldJlxxDiv div").each(function(i){
		var MANAGER_NAME_OLD = $(this).find("[name$='MANAGER_NAME_OLD']").val();//姓名
		var MANAGER_JOB_OLD = $(this).find("[name$='MANAGER_JOB_OLD']").val();//职务
		var jlxx = {};
		jlxx.MANAGER_NAME_OLD = MANAGER_NAME_OLD;
		jlxx.MANAGER_JOB_OLD = MANAGER_JOB_OLD;
		if(MANAGER_NAME_OLD!=null && MANAGER_NAME_OLD!=""){
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
 * 获取股东信息
 */
function getNewGdxxJson(){
	var gdxxArray = [];
	$("#newGdxxDiv div").each(function(i){
		var that = this;
		var SHAREHOLDER_NAME = $(that).find("[name$='SHAREHOLDER_NAME']").val();//股东姓名/名称
		var SHAREHOLDER_TYPE = $(that).find("[name$='SHAREHOLDER_TYPE']").val();//股东类型
		var LICENCE_TYPE = $(that).find("[name$='LICENCE_TYPE']").val();//证照类型
		var LICENCE_NO = $(that).find("[name$='LICENCE_NO']").val();//证件号码		
		var ID_ADDRESS = $(that).find("[name$='ID_ADDRESS']").val();	//身份证件地址
		var CONTACT_WAY = $(that).find("[name$='CONTACT_WAY']").val();//联系方式
        var SHAREHOLDER_COMPANYCOUNTRY = $(that).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//国别
		var LEGAL_PERSON = $(that).find("[name$='LEGAL_PERSON']").val();// 投资企业或其他组织机构法定代表人姓名
        var LEGAL_IDNO_PERSON = $(that).find("[name$='LEGAL_IDNO_PERSON']").val();// 投资企业或其他组织机构法定代表人身份证号码
        
        //股权来源
    	var gqlyArray = [];
        $(that).find(".gqlytable tbody").each(function(i){
        	var stockFrom = $(this).find("[name='stockFrom']").val();
        	var INVESTMENT_CASH = $(this).find("[name='INVESTMENT_CASH']").val();
        	var INVESTMENT_MATERIAL = $(this).find("[name='INVESTMENT_MATERIAL']").val();
        	var INVESTMENT_TECHNOLOGY = $(this).find("[name='INVESTMENT_TECHNOLOGY']").val();
        	var INVESTMENT_LAND = $(this).find("[name='INVESTMENT_LAND']").val();
        	var INVESTMENT_STOCK = $(this).find("[name='INVESTMENT_STOCK']").val();
        	var INVESTMENT_OTHER = $(this).find("[name='INVESTMENT_OTHER']").val();
        	var TRANSFER_CONTRIBUTIONS = $(this).find("[name='TRANSFER_CONTRIBUTIONS']").val();
        	var OLD_PROPORTION = $(this).find("[name='OLD_PROPORTION']").val();
        	var TRANSFER_PRICE = $(this).find("[name='TRANSFER_PRICE']").val();
        	var PAYMETHOD = $(this).find("[name='PAYMETHOD']").val();
        	var gqly = {};
        	gqly.stockFrom = stockFrom;
        	gqly.INVESTMENT_CASH = INVESTMENT_CASH;
        	gqly.INVESTMENT_MATERIAL = INVESTMENT_MATERIAL;
        	gqly.INVESTMENT_TECHNOLOGY = INVESTMENT_TECHNOLOGY;
        	gqly.INVESTMENT_LAND = INVESTMENT_LAND;
        	gqly.INVESTMENT_STOCK = INVESTMENT_STOCK;
        	gqly.INVESTMENT_OTHER = INVESTMENT_OTHER;
        	gqly.TRANSFER_CONTRIBUTIONS = TRANSFER_CONTRIBUTIONS;
        	gqly.OLD_PROPORTION = OLD_PROPORTION;
        	gqly.TRANSFER_PRICE = TRANSFER_PRICE;
        	gqly.PAYMETHOD = PAYMETHOD;
        	
        	gqlyArray.push(gqly);
        });
    	var reg = /[\(]/g,reg2 = /[\)]/g;
		var GQLY_JSON = JSON.stringify(gqlyArray).replace(reg,"（").replace(reg2,"）");
        
		var INVESTMENT_CASH_TOTAL = $(that).find("[name$='INVESTMENT_CASH_TOTAL']").val();//现金总计
		var INVESTMENT_MATERIAL_TOTAL = $(that).find("[name$='INVESTMENT_MATERIAL_TOTAL']").val();//实物总计
		var INVESTMENT_TECHNOLOGY_TOTAL = $(that).find("[name$='INVESTMENT_TECHNOLOGY_TOTAL']").val();//技术出资总计
		var INVESTMENT_LAND_TOTAL = $(that).find("[name$='INVESTMENT_LAND_TOTAL']").val();//土地使用权总计
		var INVESTMENT_STOCK_TOTAL = $(that).find("[name$='INVESTMENT_STOCK_TOTAL']").val();//股权总计
		var INVESTMENT_OTHER_TOTAL = $(that).find("[name$='INVESTMENT_OTHER_TOTAL']").val();//其他总计
		
		var CONTRIBUTIONS = $(that).find("[name='CONTRIBUTIONS']").val();//认缴出资额
		var PROPORTION = $(that).find("[name='PROPORTION']").val();//占注册资本比例
        var PAIDCAPITAL = $(that).find("[name='PAIDCAPITAL']").val();//实缴出资额
		
        if(null!=SHAREHOLDER_NAME&&SHAREHOLDER_NAME!=""){
			var gdxx = {};
			gdxx.SHAREHOLDER_NAME = SHAREHOLDER_NAME;
			gdxx.SHAREHOLDER_TYPE = SHAREHOLDER_TYPE;
			gdxx.LICENCE_TYPE = LICENCE_TYPE;
			gdxx.LICENCE_NO = LICENCE_NO;
			gdxx.ID_ADDRESS = ID_ADDRESS;
			gdxx.CONTACT_WAY = CONTACT_WAY;
			gdxx.SHAREHOLDER_COMPANYCOUNTRY=SHAREHOLDER_COMPANYCOUNTRY;
			gdxx.LEGAL_PERSON = LEGAL_PERSON;
			gdxx.LEGAL_IDNO_PERSON = LEGAL_IDNO_PERSON;
			gdxx.INVESTMENT_OTHER_TOTAL = INVESTMENT_OTHER_TOTAL;
            gdxx.GQLY_JSON = GQLY_JSON;
			gdxx.INVESTMENT_CASH_TOTAL = INVESTMENT_CASH_TOTAL;
			gdxx.INVESTMENT_MATERIAL_TOTAL = INVESTMENT_MATERIAL_TOTAL;
			gdxx.INVESTMENT_TECHNOLOGY_TOTAL = INVESTMENT_TECHNOLOGY_TOTAL;
			gdxx.INVESTMENT_LAND_TOTAL = INVESTMENT_LAND_TOTAL;
			gdxx.INVESTMENT_STOCK_TOTAL = INVESTMENT_STOCK_TOTAL;
			gdxx.INVESTMENT_OTHER_TOTAL = INVESTMENT_OTHER_TOTAL;
			
			gdxx.CONTRIBUTIONS = CONTRIBUTIONS;
			gdxx.PROPORTION = PROPORTION;
			gdxx.PAIDCAPITAL=PAIDCAPITAL;
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
        var DIRECTOR_PROVINCE=idCardArea.getProvinceByIdCard(DIRECTOR_IDNO);
        var DIRECTOR_CITY=idCardArea.getCityByIdCard(DIRECTOR_IDNO);
        var DIRECTOR_COUNTRY_AREA=idCardArea.getCountryByIdCard(DIRECTOR_IDNO);

		if(null!=DIRECTOR_OFFICETERM&&DIRECTOR_OFFICETERM!=""){
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
            dsxx.DIRECTOR_PROVINCE = DIRECTOR_PROVINCE;
            dsxx.DIRECTOR_CITY = DIRECTOR_CITY;
            dsxx.DIRECTOR_COUNTRY_AREA = DIRECTOR_COUNTRY_AREA;
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
        var SUPERVISOR_PROVINCE=idCardArea.getProvinceByIdCard(SUPERVISOR_IDNO);
        var SUPERVISOR_CITY=idCardArea.getCityByIdCard(SUPERVISOR_IDNO);
        var SUPERVISOR_COUNTRY_AREA=idCardArea.getCountryByIdCard(SUPERVISOR_IDNO);


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

            jsxx.SUPERVISOR_PROVINCE = SUPERVISOR_PROVINCE;
            jsxx.SUPERVISOR_CITY = SUPERVISOR_CITY;
            jsxx.SUPERVISOR_COUNTRY_AREA = SUPERVISOR_COUNTRY_AREA;
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

        var MANAGER_PROVINCE=idCardArea.getProvinceByIdCard(MANAGER_IDNO);
        var MANAGER_CITY=idCardArea.getCityByIdCard(MANAGER_IDNO);
        var MANAGER_COUNTRY_AREA=idCardArea.getCountryByIdCard(MANAGER_IDNO);
				
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

            jlxx.MANAGER_PROVINCE = MANAGER_PROVINCE;
            jlxx.MANAGER_CITY = MANAGER_CITY;
            jlxx.MANAGER_COUNTRY_AREA = MANAGER_COUNTRY_AREA;
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

/*************数据提交JS结束**********************/

/*************变更选择企业查询JS开始****************/
function queryBackBaseInfo(baseInfo){
	$("input[name='COMPANY_NAME']").val(baseInfo.ENT_NAME);
	//$("input[name='SOCIAL_CREDITUNICODE']").val(baseInfo.SOCIAL_CREDIT_UNICODE);//统一社会信用代码
	$("input[name='REGISTER_ADDR']").val(baseInfo.DOM);
	$("input[name='CONTACT_PHONE']").val(baseInfo.TELEPHONE);
	var companyType;
	if(baseInfo.ENT_TYPE=='1151'){
		companyType = "zrrdz";
	}else if(baseInfo.ENT_TYPE=='1123'||baseInfo.ENT_TYPE=='1152'||baseInfo.ENT_TYPE=='1153'){
		companyType = "frdz";
	}else if(baseInfo.ENT_TYPE=='1110'){
		companyType = "gydz";
	}else if(baseInfo.ENT_TYPE=='1130'){
		companyType = "zrrkg";
	}else if(baseInfo.ENT_TYPE=='1140'){
		companyType = "gykg";
	}else{
		companyType = "qtyxzrgs";
	}
	$("#COMPANY_TYPE").combobox("setValue",companyType);
	var url = 'dictionaryController/loadData.do?typeCode='+companyType+'&orderType=asc';
    $('#COMPANY_NATURE').combobox('reload',url);
    
	$("input[name='YEAR_FROM']").val((baseInfo.OP_FROM).substring(0,10));
	//回填经营期限起始
	$("input[name='YEAR_FROM_CHANGE']").val((baseInfo.OP_FROM).substring(0,10));
	$("input[name='YEAR_TO']").val(baseInfo.OP_TO?(baseInfo.OP_TO).substring(0,10):'');
	$("textarea[name='BUSSINESS_SCOPE']").val(baseInfo.OP_SCOPE.replace(/\s/g,""));
	$("input[name='INVESTMENT']").val(baseInfo.REG_CAP);
	$("select[name='CURRENCY']").val(baseInfo.REG_CAP_CUR);
	$("select[name='CURRENCY_CHANGE']").val(baseInfo.REG_CAP_CUR);
	setCurrency1(baseInfo.REG_CAP_CUR);
	$("[name$='CURRENCY']").attr("disabled",true); 
	$("[name$='CURRENCY_CHANGE']").attr("disabled",true); 
	
	$("input[name='LEGAL_NAME']").val(baseInfo.LEREP);
}

function queryBackInvestors(investors){
	$("#gdxxDiv").html("");	
	stockFromOptionHtml = "<option value='新增'>新增</option>";
	$("select[name='setHolderInfo']").empty();
	$("select[name='setHolderInfo']").append("<option value=''>请选择复用人员信息</option>");
	for(var i=0;i<investors.length;i++){
		var investor = investors[i];
		addInitGdxxDiv(investor,i);
	}
}

function queryBackDirectors(members){
	var j=0;
	$("#oldDsxxDiv").html("");
	for(var i=0;i<members.length;i++){
		var member = members[i];
		var position = member.POSITION;
		if(position=='410A'||position=='410B'||position=='410C'||position=='431A'||position=='431B'||position=='432A'||position=='432K'){
			j = addOldDsxxDiv(member,j);
		}
	}
}

function queryBackSupervisors(members){
	var j=0;
	$("#oldJsxxDiv").html("");
	for(var i=0;i<members.length;i++){
		var member = members[i];
		var position = member.POSITION;
		if(position=='408A'){
			j = addOldJsxxDiv(member,j);
		}
	}
}

function queryBackManagers(members){
	$("#oldJlxxDiv").html("");
	for(var i=0;i<members.length;i++){
		var member = members[i];
		var position = member.POSITION;
		if(position=='410A'||position=='410B'||position=='410C'||position=='434Q'||position=='434R'||position=='436A'){
			addOldJlxxDiv(member,i);
			break;
		}
	}
}

function queryBackContact(contact){
	$("[name='LIAISON_NAME']").val(contact.NAME);
	$("[name='LIAISON_MOBILE']").val(contact.TELEPHONE);
	$("[name='LIAISON_IDNO']").val(contact.CERTNO);
}

/*************变更选择企业查询JS结束****************/


/*************基本信息JS开始****************/
function natureChage(){
	$("#dsxxDiv").html("");
	$("#jsxxDiv").html("");
	$("#jlxxDiv").html("");
	
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	if(nature==null||nature==''||nature==undefined){
		
	}else{
		addDsxxDiv();
		addJsxxDiv();
		addJlxxDiv();
		addLegalDiv();
	}	

	if(nature=='02'){//设董事会、监事设立 
		$("#addGdxx").show();
		$("#addDsxx").show();
	}else if(nature=='07'){//设执行董事设立
        $("#addGdxx").show();
        $("#addDsxx").show();
    }else if(nature=='03'){//设执行董事设立
		$("#addGdxx").show();
	}else if(nature=='04'){//设董事会监事会设立
		$("#addGdxx").show();
		$("#addDsxx").show();
		$("#addJsxx").show();
		
		//监事职务能选择
		$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
	}else{
		$("#addGdxx").hide();
		$("#addDsxx").hide();
		$("#addJsxx").hide();
		$("#addJlxx").hide();
		$("[name$='MANAGER_APPOINTOR']").val('股东');
	}
}

//设置经营期限
function setJyqx(value){
	if (value === "1") {//年		
		$("input[name='BUSSINESS_YEARS_CHANGE']").attr("disabled",false);
		$("input[name='BUSSINESS_YEARS_CHANGE']").addClass(" validate[required]");
	} else {			
		$("input[name='BUSSINESS_YEARS_CHANGE']").val("");
		$("input[name='BUSSINESS_YEARS_CHANGE']").attr("disabled",true); 
		$("input[name='BUSSINESS_YEARS_CHANGE']").removeClass(" validate[required]");
	}
}


//设置原经营期限
function setYjyqx(value){
	if (value === "1") {			
		$("input[name='BUSSINESS_YEARS']").attr("disabled",false);
		$("input[name='BUSSINESS_YEARS']").addClass(" validate[required]");

	} else {			
		$("input[name='BUSSINESS_YEARS']").val("");
		$("input[name='BUSSINESS_YEARS']").attr("disabled",true); 
		$("input[name='BUSSINESS_YEARS']").removeClass(" validate[required]");
	}
}
/**
* 经营范围选择器
**/
function showSelectJyfwNew(){
	var ids = $("input[name='BUSSINESS_SCOPE_ID_CHANGE']").val();
	parent.$.dialog.open("domesticControllerController.do?selectorNew&allowCount=30&noAuth=true&ids="
		+ ids, {
			title : "经营范围选择器",
			width : "950px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectBusScopeInfo = art.dialog.data("selectBusScopeInfo");
				if (selectBusScopeInfo && selectBusScopeInfo.ids) {
					$("[name='BUSSINESS_SCOPE_ID_CHANGE']").val(selectBusScopeInfo.ids);
					$("[name='INVEST_INDUSTRY_ID_CHANGE']").val(selectBusScopeInfo.ids);
					$("[name='BUSSINESS_SCOPE_CHANGE']").val(selectBusScopeInfo.busscopeNames);
					$("[name='INVEST_INDUSTRY_CHANGE']").val(selectBusScopeInfo.induNames);
					$("[name='INDUSTRY_CODE_CHANGE']").val(selectBusScopeInfo.induCodes);

					art.dialog.removeData("selectBusScopeInfo");
				}
			}
		}, false);
};

/*************基本信息JS结束****************/


/*************资金、股东信息JS开始****************/
/**
* 设置币种
**/
function setCurrency1(val){
	$("[name$='CURRENCY_1']").val(val);
	$("[name$='CURRENCY_1']").attr("disabled",true); 
}
function setCurrency2(val){
	$("[name$='CURRENCY_2']").val(val);
	$("[name$='CURRENCY_2']").attr("disabled",true); 
}

//设置付款方式
function setPayWay(o){
	var thisPrice = $(o).closest("tbody").find("[name='TRANSFER_PRICE']").val();
	if(thisPrice>0){
		$(o).closest("tbody").find("[name='PAYMETHOD']").addClass("  validate[required]");
		$(o).closest("tbody").find("[id='fkfs']").attr("style","");
	}else{
		$(o).closest("tbody").find("[name='PAYMETHOD']").removeClass("  validate[required]");
		$(o).closest("tbody").find("[id='fkfs']").attr("style","display:none");
	}
	
}

//设置缴付日期
function setPayTime(o){
	var thisPrice = $(o).closest("tbody").find("[name='PAIDCAPITAL']").val();//实缴出资额
	if(thisPrice>0){
		$(o).closest("tbody").find("[name='PAYMENT_TIME']").addClass("  validate[required]");
		$(o).closest("tbody").find("[id='jfrq']").attr("style","");
	}else{
		$(o).closest("tbody").find("[name='PAYMENT_TIME']").removeClass("  validate[required]");
		$(o).closest("tbody").find("[id='jfrq']").attr("style","display:none");
	}
	
}

//设置股东类型
function setGdlxValidate(val,name){
	if (val=="20"||val=="21"||val=="22") {
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

/**
 * 初始化时添加原股东信息
 */

function addInitGdxxDiv(investor,i){
	$.ajaxSettings.async = false;
	$.post("enterpriseChangeController/refreshGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);	
		var CURRENCY = $("[name='CURRENCY']").val(); 
		if(CURRENCY==null || CURRENCY=="" || CURRENCY==undefined){
			CURRENCY="002";//默认人民币
			$("[name='CURRENCY']").val("002");
		}
		setCurrency1(CURRENCY);
		$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").val(investor.INV_NAME);
		$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_NAME']").attr("readonly",true);
		$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").val(investor.INV_TYPE==1?'10':investor.INV_TYPE);
		$("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").attr("disabled",true);
		var val = $("#gdxxDiv div").eq(i).find("[name$='SHAREHOLDER_TYPE']").val();
		if (val=="20"||val=="21"||val=="22") {
			$("#gdxxDiv div").eq(i).find("[name$='LEGAL_PERSON']").removeClass("  validate[required]");
			$("#gdxxDiv div").eq(i).find("[name$='LEGAL_IDNO_PERSON']").removeClass("  validate[required]");
		} else {
			$("#gdxxDiv div").eq(i).find("[name$='LEGAL_PERSON']").addClass("  validate[required]");
			$("#gdxxDiv div").eq(i).find("[name$='LEGAL_IDNO_PERSON']").addClass("  validate[required]");
		}
		
		/*$("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").val(investor.B_LIC_NO);
		$("#gdxxDiv div").eq(i).find("[name$='LICENCE_NO']").attr("readonly",true);*/
		$("#gdxxDiv div").eq(i).find("[name$='CONTRIBUTIONS']").val(investor.SUBCON_AM);
		$("#gdxxDiv div").eq(i).find("[name$='CONTRIBUTIONS_REMAIN']").val(investor.SUBCON_AM);
		$("#gdxxDiv div").eq(i).find("[name$='PROPORTION']").val(investor.CON_PROP);
		$("#gdxxDiv div").eq(i).find("[name$='PAIDCAPITAL']").val(investor.ACCON_AM);
		$("#gdxxDiv div").eq(i).find(".syj-closebtn").remove();
		
		var stockFromOption = "原股东-"+investor.INV_NAME; 
		stockFromOptionHtml += "<option value='"+stockFromOption+"'>"+stockFromOption+"</option>";

		$("select[name='stockFrom']").html(stockFromOptionHtml);		

		var reg= /="[a-z0-9]*SHAREHOLDER_NAME/gi;
        var arr1=responseText.match(reg);
        if(arr1.length>0){
	        var id=arr1[0].replace("=\"","").replace("_NAME","");
	        $("select[name='setHolderInfo']").append("<option value='"+id+"'>"+"原股东-"+investor.INV_NAME+"</option>");
        }
//        $("select[name='setHolderInfo']").append("<option value='"+investor.INV_NAME+"'>"+"原股东-"+investor.INV_NAME+"</option>");
		
	});
	$.ajaxSettings.async = true;
}
/**
 * 股东资金计算
 */
function setInvestment(){
	var total = 0;
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
			total+=Number(CONTRIBUTIONS);
			$(this).find("[name$='CONTRIBUTIONS']").val(CONTRIBUTIONS.toFixed(6));
			$(this).find("[name$='CONTRIBUTIONS_REMAIN']").val(CONTRIBUTIONS.toFixed(6));
		}else{
			$(this).find("[name$='CONTRIBUTIONS']").val('');
			$(this).find("[name$='PROPORTION']").val('');
		}
	});
	if(Number(total)>0){
		total = $("input[name='INVESTMENT']").val();	
		$("#gdxxDiv div").each(function(i){
			var rjcze = $(this).find("[name$='CONTRIBUTIONS']").val();	
			var a = Number(rjcze)/(Number(total))*100;
			$(this).find("[name$='PROPORTION']").val(a.toFixed(2)+"%");
		});
	}else{		
		//$("input[name='INVESTMENT']").val('');		
	}
}

/**
 * 添加股东信息
 */
function addNewGdxxDiv(){
	$.post("enterpriseChangeController/refreshNewGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#newGdxxDiv").append(responseText);
		var CURRENCY = $("[name='CURRENCY_CHANGE']").val();   
		if(CURRENCY==null || CURRENCY=="" || CURRENCY==undefined){
			CURRENCY = "002";
			$("[name='CURRENCY_CHANGE']").val("002"); 
		}
		setCurrency2(CURRENCY);
		//$("#newGdxxDiv").find("select[name='stockFrom']").html(stockFromOptionHtml);
		$("#newGdxxDiv").find("select[name='stockFrom']").each(function(){
			if($(this).val()==''||$(this).val()==null||$(this).val()==undefined){
				$(this).html(stockFromOptionHtml);
			}
		});
	});
}
function setSelectHolderName(val,name){
	$("select[name='setHolderInfo']").append("<option value='"+name+"'>"+"新股东-"+val+"</option>");
}

/**
 *回填信息
 * @param val
 * @param name
 */
function setInfoMsg(val,name){
    if(val.indexOf('SHAREHOLDER')>-1){
        v2=val.replace("SHAREHOLDER","");
        $("input[name$='" + name + "_NAME" + "']").eq(0).val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name$='" + name + "_MOBILE" + "']").eq(0).val($("input[name='"+v2+"CONTACT_WAY']").val());
        $("input[name$='" + name + "_PHONE" + "']").eq(0).val($("input[name='"+v2+"CONTACT_WAY']").val());
        $("select[name$='" + name + "_IDTYPE" + "']").eq(0).val($("select[name='"+v2+"LICENCE_TYPE']").val());
        $("input[name$='" + name + "_IDNO" + "']").eq(0).val($("input[name='"+v2+"LICENCE_NO']").val());
        
    }else if(val!="") {
        $("input[name='" + name + "_NAME" + "']").val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name='" + name + "_OPERRATOR" + "']").val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name='" + name + "_MOBILE" + "']").val($("input[name='" + val + "_MOBILE" + "']").val());
        $("input[name='" + name + "_PHONE" + "']").val($("input[name='" + val + "_MOBILE" + "']").val());
        $("input[name='" + name + "_FIXEDLINE" + "']").val($("input[name='" + val + "_FIXEDLINE" + "']").val());
        $("select[name='" + name + "_IDTYPE" + "']").val($("select[name='" + val + "_IDTYPE" + "']").val());
        $("input[name='" + name + "_IDNO" + "']").val($("input[name='" + val + "_IDNO" + "']").val());
        
    }
}

/**
 * 删除股东信息
 */
function delGdxxDiv(o){
	$(o).closest("div").remove();
	setNewInvestment();
}
/**
 * 添加股权来源信息
 */
function addGqlyDiv(o){
	$.post("enterpriseChangeController/refreshGqlyDiv.do",{
	}, function(responseText, status, xhr) {
		$(o).closest('table').append(responseText);
		var CURRENCY = $("[name='CURRENCY_CHANGE']").val();  
		if(CURRENCY==null || CURRENCY=="" || CURRENCY==undefined){
			CURRENCY = "002";
		}
		setCurrency2(CURRENCY);
		$("#newGdxxDiv").find("select[name='stockFrom']").each(function(){
			if($(this).val()==''||$(this).val()==null||$(this).val()==undefined){
				$(this).html(stockFromOptionHtml);
			}
		});
	});
}
/**
 * 删除股权来源信息
 */
function delGqlyDiv(o){
	$(o).closest("tbody").remove();
	setNewInvestment();
}

/**
 * 设置股权来源选择项
 */
function setStockFrom(o){
	var val = $(o).val();
	if(val!='新增'){
		$(o).closest('tbody').find("tr[id='transfer1']").attr("style","");
		$(o).closest('tbody').find("tr[id='transfer2']").attr("style","");
		$(o).closest('tbody').find("[name='TRANSFER_PRICE']").addClass("validate[required]");
		$(o).closest('tbody').find("[name='PAYMETHOD']").addClass("validate[required]");
	}else{
		$(o).closest('tbody').find("tr[id='transfer1']").attr("style","display:none");
		$(o).closest('tbody').find("tr[id='transfer2']").attr("style","display:none");
		$(o).closest('tbody').find("[name='TRANSFER_PRICE']").removeClass("validate[required]");
		$(o).closest('tbody').find("[name='PAYMETHOD']").removeClass("validate[required]");
	}
}
/**
 * 新股东信息资金计算
 */
function setNewInvestment(o){
	var thisstockFrom = $(o).closest("tbody").find("[name='stockFrom']").val();
	var total = 0;
	var stockfromtotal = 0;
	$("#newGdxxDiv div").each(function(i){
		var cashTotal = 0;
		var materialTotal = 0;
		var technologyTotal = 0;
		var landTotal = 0;
		var stockTotal = 0;
		var otherTotal = 0;
		var target = this;
		$(target).find(".gqlytable tbody").each(function(){
			var stockFrom = $(this).find("[name='stockFrom']").val();
			var INVESTMENT_CASH = $(this).find("[name='INVESTMENT_CASH']").val();
			var INVESTMENT_MATERIAL = $(this).find("[name='INVESTMENT_MATERIAL']").val();
			var INVESTMENT_TECHNOLOGY = $(this).find("[name='INVESTMENT_TECHNOLOGY']").val();
			var INVESTMENT_LAND = $(this).find("[name='INVESTMENT_LAND']").val();
			var INVESTMENT_STOCK = $(this).find("[name='INVESTMENT_STOCK']").val();
			var INVESTMENT_OTHER = $(this).find("[name='INVESTMENT_OTHER']").val();
			cashTotal += Number(INVESTMENT_CASH);
			materialTotal += Number(INVESTMENT_MATERIAL);
			technologyTotal += Number(INVESTMENT_TECHNOLOGY);
			landTotal += Number(INVESTMENT_LAND);
			stockTotal += Number(INVESTMENT_STOCK);
			otherTotal += Number(INVESTMENT_OTHER);
			
			var TRANSFER_CONTRIBUTIONS = Number(INVESTMENT_CASH)+Number(INVESTMENT_MATERIAL)+Number(INVESTMENT_TECHNOLOGY)+Number(INVESTMENT_LAND)+Number(INVESTMENT_STOCK)+Number(INVESTMENT_OTHER);
			var oldinvest = $("input[name='INVESTMENT']").val();
			if(stockFrom=='新增'){
				total += Number(INVESTMENT_CASH)+Number(INVESTMENT_MATERIAL)+Number(INVESTMENT_TECHNOLOGY)+Number(INVESTMENT_LAND)+Number(INVESTMENT_STOCK)+Number(INVESTMENT_OTHER);
			}else{
				if(stockFrom==thisstockFrom){
					stockfromtotal += Number(TRANSFER_CONTRIBUTIONS);
				}
			}	
			if(Number(TRANSFER_CONTRIBUTIONS)>0){
				$(this).find("[name='TRANSFER_CONTRIBUTIONS']").val(TRANSFER_CONTRIBUTIONS);
				var op = Number(TRANSFER_CONTRIBUTIONS)/(Number(oldinvest))*100;
				$(this).find("[name='OLD_PROPORTION']").val(op.toFixed(2)+"%");
			}else{
				$(this).find("[name='TRANSFER_CONTRIBUTIONS']").val('');
				$(this).find("[name='OLD_PROPORTION']").val('');
			}
		});
		if(thisstockFrom!="新增"){
			var fromHolderName = thisstockFrom.substring(thisstockFrom.indexOf("-")+1);
			$("#gdxxDiv div").find("input[name$='SHAREHOLDER_NAME']").each(function(){
				if($(this).val()==fromHolderName){
					var CONTRIBUTIONS = $(this).closest("div").find("[name$='CONTRIBUTIONS']").val();
					if((Number(stockfromtotal)>Number(CONTRIBUTIONS))){
						art.dialog({
							content : "股权来源为股东"+fromHolderName+"的股权金额总和("+stockfromtotal.toFixed(6)+"万元)超过了其所持有的股权金额("+Number(CONTRIBUTIONS).toFixed(6)+"万元)，请核对修正",
							icon : "warning",
							ok : true
						});
						return ;
					}else{
						$(this).closest("div").find("[name$='CONTRIBUTIONS_REMAIN']").val(Number(CONTRIBUTIONS)-Number(stockfromtotal));
					}						
				}
			});			
		}
		if(Number(cashTotal)>0){
			$(target).find("input[name='INVESTMENT_CASH_TOTAL']").val(cashTotal.toFixed(6));
		}
		if(Number(materialTotal)>0){
			$(target).find("input[name='INVESTMENT_MATERIAL_TOTAL']").val(materialTotal.toFixed(6));
		}
		if(Number(technologyTotal)>0){
			$(target).find("input[name='INVESTMENT_TECHNOLOGY_TOTAL']").val(technologyTotal.toFixed(6));
		}
		if(Number(landTotal)>0){
			$(target).find("input[name='INVESTMENT_LAND_TOTAL']").val(landTotal.toFixed(6));
		}
		if(Number(stockTotal)>0){
			$(target).find("input[name='INVESTMENT_STOCK_TOTAL']").val(stockTotal.toFixed(6));
		}
		if(Number(otherTotal)>0){
			$(target).find("input[name='INVESTMENT_OTHER_TOTAL']").val(otherTotal.toFixed(6));
		}
		var CONTRIBUTIONS = Number(cashTotal)+Number(materialTotal)+Number(technologyTotal)+Number(landTotal)+Number(stockTotal)+Number(otherTotal);
		if(Number(CONTRIBUTIONS)>=0){
		//	total+=Number(CONTRIBUTIONS);
			$(target).find("input[name='CONTRIBUTIONS']").val(CONTRIBUTIONS.toFixed(6));
		}else{
			$(target).find("[name='CONTRIBUTIONS']").val('');
			$(target).find("[name='PROPORTION']").val('');
		}
	});
	
	var investment = $("input[name='INVESTMENT']").val();

	if(Number(total)>=0){
		total += Number(investment);
		$("input[name='INVESTMENT_CHANGE']").val(total.toFixed(6));			
		$("#newGdxxDiv div").each(function(i){
			var rjcze = $(this).find("[name='CONTRIBUTIONS']").val();	
			var a = Number(rjcze)/(Number(total))*100;
			$(this).find("[name='PROPORTION']").val(a.toFixed(2)+"%");
		});
	}else{		
		$("input[name='INVESTMENT_CHANGE']").val('');		
	}
}


/*************资金、股东信息JS结束****************/

/*************董事信息JS开始****************/
/**
 * 初始化添加原董事信息
 */
function addOldDsxxDiv(member,j){
	$.ajaxSettings.async = false;
	$.post("enterpriseChangeController/refreshOldDsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#oldDsxxDiv").append(responseText);
		$("#oldDsxxDiv div").eq(j).find("[name='DIRECTOR_NAME_OLD']").val(member.PERSON_NAME);
		var position = member.POSITION;
		var dsjob;
		if(position=='410A'||position=='431A'){
			dsjob = '01';
		}else if(position=='410B'||position=='432A'){
			dsjob = '03';
		}else if(position=='410C'||position=='432K'){
			dsjob = '30';
		}else if(position=='431B'){
			dsjob = '02';
		}
		$("#oldDsxxDiv div").eq(j).find("[name='DIRECTOR_JOB_OLD']").val(dsjob);
		$("#oldDsxxDiv div").eq(j).find("[name='DIRECTOR_JOB_OLD']").attr("disabled",true);
	});
	$.ajaxSettings.async = true;
	
	return ++j;
}

/**
 * 添加董事信息
 */
function addDsxxDiv(){
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	var ssdjzw = '04'
	if(nature=='02'||nature=='04'||nature=='07'){
		ssdjzw = '01';
	}
	$.post("enterpriseChangeController/refreshDsxxDiv.do",{
		ssdjzw:ssdjzw
	}, function(responseText, status, xhr) {
		$("#dsxxDiv").append(responseText);
		$("#dsxxDiv div").eq($("#dsxxDiv div").length-1).find("[name$='DIRECTOR_APPOINTOR']").val($("[name='DIRECTOR_APPOINTOR']").val());
		
		$("#dsxxDiv div").eq(0).find('a').remove();
		if(!$('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){
			$("[name$='DIRECTOR_NAME']").removeClass("validate[required]");
			$("[name$='DIRECTOR_JOB']").removeClass("validate[required]");
			$("[name$='DIRECTOR_COUNTRY']").removeClass("validate[required]");
			$("[name$='DIRECTOR_IDTYPE']").removeClass("validate[required]");
			$("[name$='DIRECTOR_IDNO']").removeClass("validate[required],custom[equalsDirectorIdno]");
			$("[name$='DIRECTOR_GENERATION_MODE']").removeClass("validate[required]");
			$("[name$='DIRECTOR_APPOINTOR']").removeClass("validate[required]");
			$("[name$='DIRECTOR_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
		}
	});
}
/**
 * 删除董事信息
 */
function delDsxxDiv(o){
	$(o).closest("div").remove();
}

function setDirectorJob(val,name){
	var companySetNature= $("input[name='COMPANY_NATURE']").val();//公司类型编码
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
    var companySetNature= $("#COMPANY_NATURE").combobox('getValue');//公司设立性质编码	
    var companyTypeCode;
	if($('input[name="CHANGE_OPTIONS"][value="04"]:checked')){
		companyTypeCode= $("#COMPANY_TYPE_CHANGE").combobox('getValue');//公司类型编码
	}else{
		companyTypeCode= $("#COMPANY_TYPE").combobox('getValue');//公司类型编码
	}
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
/*************董事信息JS结束****************/


/*************监事信息JS开始****************/
/**
 * 初始化添加原监事信息
 */
function addOldJsxxDiv(member,j){
	$.ajaxSettings.async = false;
	$.post("enterpriseChangeController/refreshOldJsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#oldJsxxDiv").append(responseText);
		$("#oldJsxxDiv div").eq(j).find("[name='SUPERVISOR_NAME_OLD']").val(member.PERSON_NAME);
		var position = member.POSITION;
		var dsjob;
		if(position=='408A'){
			dsjob = '10';
		}
		$("#oldJsxxDiv div").eq(j).find("[name='SUPERVISOR_JOB_OLD']").val(dsjob);
		$("#oldJsxxDiv div").eq(j).find("[name='SUPERVISOR_JOB_OLD']").attr("disabled",true);
	});
	$.ajaxSettings.async = true;
	
	return ++j;
}
/**
 * 添加监事信息
 */
function addJsxxDiv(deleKey){
	$.post("enterpriseChangeController/refreshJsxxDiv.do?deleKey="+deleKey,{
	}, function(responseText, status, xhr) {
		$("#jsxxDiv").append(responseText);
		$("[name$='SUPERVISOR_APPOINTOR']").val($("[name='SUPERVISOR_APPOINTOR']").val());

		$("#jsxxDiv div").eq(0).find('a').remove();

		var Nature= $("input[name='COMPANY_NATURE']").val();//公司性质编码
		if(Nature=='04'){
			$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
		}else{
			$("[name$='SUPERVISOR_JOB']").attr("disabled",true);
		}
		
		if(!$('input[name="CHANGE_OPTIONS"][value="11"]').is(':checked')){
			$("[name$='SUPERVISOR_NAME']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_JOB']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_IDTYPE']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_IDNO']").removeClass("validate[required],custom[equalsDirectorOrManagerIdno],custom[equalsJsIdNo]");
			$("[name$='SUPERVISOR_GENERATION_MODE']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_APPOINTOR']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_COUNTRY']").removeClass("validate[required]");
			$("[name$='SUPERVISOR_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
		}
	});
}
/**
 * 删除监事信息
 */
function delJsxxDiv(o){
	$(o).closest("div").remove();
} 
/*************监事信息JS结束****************/


/*************经理信息JS开始****************/
/**
 * 初始化添加原经理信息
 */
function addOldJlxxDiv(member,i){
	$.ajaxSettings.async = false;
	$.post("enterpriseChangeController/refreshOldJlxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#oldJlxxDiv").append(responseText);
		$("#oldJlxxDiv div").find("[name='MANAGER_NAME_OLD']").val(member.PERSON_NAME);
		var position = member.POSITION;
		var dsjob;
		if(position=='410A'||position=='410B'||position=='410C'||position=='434Q'){
			dsjob = '21';
		}else if(position=='436A'){
			dsjob = '20';
		}
		$("#oldJlxxDiv div").find("[name='MANAGER_JOB_OLD']").val(dsjob);
		$("#oldJlxxDiv div").find("[name='MANAGER_JOB_OLD']").attr("disabled",true);
	});
	$.ajaxSettings.async = true;
}
/**
 * 添加经理信息
 */
function addJlxxDiv(){
	$.post("enterpriseChangeController/refreshJlxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jlxxDiv").append(responseText);
//		$("[name$='MANAGER_APPOINTOR']").val($("[name='MANAGER_APPOINTOR']").val());
		var nature = $("#COMPANY_NATURE").combobox('getValue');
		if(nature=='01'){
			$("[name$='MANAGER_APPOINTOR']").val('股东');
		}else if(nature=='03'){
			$("[name$='MANAGER_APPOINTOR']").val('股东会');
		}else if(nature=='02'||nature=='04'||nature=='07'){
			$("[name$='MANAGER_APPOINTOR']").val('董事会');
		}

		$("#jlxxDiv div").eq(0).find('a').remove();
		//经理职务能选择
		$("#jlxxDiv div").find("[name$='MANAGER_JOB']").attr("disabled",true);
		

		if(!$('input[name="CHANGE_OPTIONS"][value="12"]').is(':checked')){
			$("[name$='MANAGER_NAME']").removeClass("validate[required]");
			$("[name$='MANAGER_JOB']").removeClass("validate[required]");
			$("[name$='MANAGER_IDTYPE']").removeClass("validate[required]");
			$("[name$='MANAGER_IDNO']").removeClass("validate[required]");
			$("[name$='MANAGER_GENERATION_MODE']").removeClass("validate[required]");
			$("[name$='MANAGER_APPOINTOR']").removeClass("validate[required]");
			$("[name$='MANAGER_COUNTRY']").removeClass("validate[required]");
			$("[name$='MANAGER_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
		}
	});
}
/**
 * 删除经理信息
 */
function delJlxxDiv(o){
	$(o).closest("div").remove();
}
/*************经理信息JS结束****************/

/*************法人代表信息JS开始****************/

function addLegalDiv(){
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	var ssdjzw = '';
	if(nature=='02'||nature=='04'||nature=='07'){
		ssdjzw = '02,03,10,11,12,21,30';
	}else if(nature=='01'||nature=='03'){
		ssdjzw = '01,02,03,10,11,12,21';
	}
	$.post("enterpriseChangeController/refreshLegalDiv.do",{
		ssdjzw:ssdjzw
	}, function(responseText, status, xhr) {
		$("#legalDiv").html(responseText);
		if($('input[name="CHANGE_OPTIONS"][value="03"]').is(':checked')){
			AppUtil.changeRequireStatus("LEGAL_NAME_CHANGE;LEGAL_JOB_CHANGE;LEGAL_MOBILE_CHANGE;LEGAL_IDTYPE_CHANGE;LEGAL_IDNO_CHANGE;LEGAL_COUNTRY_CHANGE;LEGAL_PRODUCEMODE_CHANGE;LEGAL_ADDRESS_CHANGE;","1");
		}
	});
}


//更改新法定代表人职务
function setLegalValue(val){
    /*1、股权变化下、只变更法定代表人、不涉及董事/经理备案、选取职务取原有人事信息回填
	  2、股权未变化、变更法定代表人、涉及董事/经理备案，选取职务取对应新人事信息回填。*/
	//清空原有信息
	$("input[name='LEGAL_NAME_CHANGE']").val("");
	$("#LEGAL_IDTYPE_CHANGE").val("");
	$("input[name='LEGAL_IDNO_CHANGE']").val("");
    $("input[name='LEGAL_MOBILE_CHANGE']").val("");
	var isok = false;//标识位
	if(val=="01" || val=="30"){//执行董事/董事长
		if($('input[name="CHANGE_OPTIONS"][value="10"]').is(':checked')){
			$("#dsxxDiv div").each(function(i){//新董事信息
				var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
				var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
				var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
				var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		        var DIRECTOR_PHONE=$(this).find("[name$='DIRECTOR_PHONE']").val();	//手机号码
				if(DIRECTOR_JOB==val){
					$("input[name='LEGAL_NAME_CHANGE']").val(DIRECTOR_NAME);
					setZjValidate(DIRECTOR_IDTYPE,'LEGAL_IDNO_CHANGE');
					$("#LEGAL_IDTYPE_CHANGE").val(DIRECTOR_IDTYPE);
					if(DIRECTOR_IDNO==null || DIRECTOR_IDNO =="" || DIRECTOR_IDNO==undefined){
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",false);
						$("input[name='LEGAL_IDNO_CHANGE']").removeClass("inputBackgroundCcc");
					}else{
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",true);
						$("input[name='LEGAL_IDNO_CHANGE']").addClass("inputBackgroundCcc");
					}
					$("input[name='LEGAL_IDNO_CHANGE']").val(DIRECTOR_IDNO);
		            $("input[name='LEGAL_MOBILE_CHANGE']").val(DIRECTOR_PHONE);
					isok = true;
					return;
				}
			});
		}else{
			$("#oldDsxxDiv div").each(function(i){//旧董事信息
				var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME_OLD']").val();//姓名
				var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB_OLD']").val();//职务
				var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE_OLD']").val();//证件类型		
				var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO_OLD']").val();	//证件号码
		        var DIRECTOR_PHONE=$(this).find("[name$='DIRECTOR_PHONE_OLD']").val();	//手机号码
				if(DIRECTOR_JOB==val){
					$("input[name='LEGAL_NAME_CHANGE']").val(DIRECTOR_NAME);
					setZjValidate(DIRECTOR_IDTYPE,'LEGAL_IDNO_CHANGE');
					$("#LEGAL_IDTYPE_CHANGE").val(DIRECTOR_IDTYPE);
					if(DIRECTOR_IDNO==null || DIRECTOR_IDNO =="" || DIRECTOR_IDNO==undefined){
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",false);
						$("input[name='LEGAL_IDNO_CHANGE']").removeClass("inputBackgroundCcc");
					}else{
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",true);
						$("input[name='LEGAL_IDNO_CHANGE']").addClass("inputBackgroundCcc");
					}
					$("input[name='LEGAL_IDNO_CHANGE']").val(DIRECTOR_IDNO);
		            $("input[name='LEGAL_MOBILE_CHANGE']").val(DIRECTOR_PHONE);
					isok = true;
					return;
				}
			});
			
		}
	}else if(val=="20"){//经理
		if($('input[name="CHANGE_OPTIONS"][value="12"]').is(':checked')){
			$("#jlxxDiv div").each(function(i){//新经理信息
				var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
				var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
				var MANAGER_IDTYPE = $(this).find("[name$='MANAGER_IDTYPE']").val();//证件类型		
				var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();	//证件号码
				var MANAGER_PHONE=$(this).find("[name$='MANAGER_PHONE']").val();	//手机号码
				if(MANAGER_JOB==val){
					$("input[name='LEGAL_NAME_CHANGE']").val(MANAGER_NAME);	
					setZjValidate(MANAGER_IDTYPE,'LEGAL_IDNO_CHANGE');
					$("#LEGAL_IDTYPE_CHANGE").val(MANAGER_IDTYPE);
					if(MANAGER_IDNO==null || MANAGER_IDNO =="" || MANAGER_IDNO==undefined){
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",false);
						$("input[name='LEGAL_IDNO_CHANGE']").removeClass("inputBackgroundCcc");
					}else{
						$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",true);
						$("input[name='LEGAL_IDNO_CHANGE']").addClass("inputBackgroundCcc");
					}
					$("input[name='LEGAL_IDNO_CHANGE']").val(MANAGER_IDNO);
		            $("input[name='LEGAL_MOBILE_CHANGE']").val(MANAGER_PHONE);
					isok = true;
					return;
				}
			});
		}else{
			$("#oldJlxxDiv div").each(function(i){//旧经理信息
				var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME_OLD']").val();//姓名
				var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB_OLD']").val();//职务
				if(MANAGER_JOB==val){
					$("input[name='LEGAL_NAME_CHANGE']").val(MANAGER_NAME);	
					$("input[name='LEGAL_IDNO_CHANGE']").attr("readonly",false);
					$("input[name='LEGAL_IDNO_CHANGE']").removeClass("inputBackgroundCcc");
					isok = true;
					return;
				}
			});
		}
	}
	/*$("#jsxxDiv div").each(function(i){//新监事信息
		var SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
		var SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
		var SUPERVISOR_IDTYPE = $(this).find("[name$='SUPERVISOR_IDTYPE']").val();//证件类型		
		var SUPERVISOR_IDNO = $(this).find("[name$='SUPERVISOR_IDNO']").val();	//证件号码
        var SUPERVISOR_PHONE=$(this).find("[name$='SUPERVISOR_PHONE']").val();	//手机号码
		if(SUPERVISOR_JOB==val){			
			$("input[name='LEGAL_NAME_CHANGE']").val(SUPERVISOR_NAME);
			setZjValidate(SUPERVISOR_IDTYPE,'LEGAL_IDNO_CHANGE');
			$("#LEGAL_IDTYPE_CHANGE").val(SUPERVISOR_IDTYPE);
			$("input[name='LEGAL_IDNO_CHANGE']").val(SUPERVISOR_IDNO);
            $("input[name='LEGAL_MOBILE_CHANGE']").val(SUPERVISOR_PHONE);
			isok = true;
			return;
		}
	});*/
	if(!isok){//若未回填数据，则可开放可编辑。	
		$("input[name='LEGAL_NAME_CHANGE']").val('');	
		$("#LEGAL_IDTYPE_CHANGE").val('');
		$("input[name='LEGAL_IDNO_CHANGE']").val('');
		$("#LEGAL_IDTYPE_CHANGE").attr("disabled",false);
		$("#LEGAL_COUNTRY_CHANGE").attr("disabled",false);
	}else{	
		var LEGAL_IDTYPE_CHANGE = $("#LEGAL_IDTYPE_CHANGE").val();
		if(LEGAL_IDTYPE_CHANGE==null || LEGAL_IDTYPE_CHANGE=="" || LEGAL_IDTYPE_CHANGE==undefined){
			$("#LEGAL_IDTYPE_CHANGE").attr("disabled",false);//证件类型
		}else{
			$("#LEGAL_IDTYPE_CHANGE").attr("disabled",true);//证件类型
		}
		$("#LEGAL_COUNTRY_CHANGE").attr("disabled",true);//国别
	}
}

function setlegalProducemode(val) {
    var companySetNature= $("#COMPANY_NATURE").combobox('getValue');//公司设立性质编码	
    var companyTypeCode;
	if($('input[name="CHANGE_OPTIONS"][value="04"]').is(':checked')){
		companyTypeCode= $("#COMPANY_TYPE_CHANGE").combobox('getValue');//公司类型编码
	}else{
		companyTypeCode= $("#COMPANY_TYPE").combobox('getValue');//公司类型编码
	}
    if(companyTypeCode=='zrrdz'&&companySetNature=='01'){
        if(val=='30'){
            $("[name='LEGAL_PRODUCEMODE']").val("01");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }else if(companyTypeCode=='zrrdz'&&companySetNature=='07'){
        if(val=='01'){
            $("[name='LEGAL_PRODUCEMODE']").val("02");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }else if(companyTypeCode=='zrrkg'&&companySetNature=='03'){
        if(val=='30'){
            $("[name='LEGAL_PRODUCEMODE']").val("02");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }else if(companyTypeCode=='zrrkg'&&companySetNature=='02'){
        if(val=='01'){
            $("[name='LEGAL_PRODUCEMODE']").val("02");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }else if(companyTypeCode=='zrrkg'&&companySetNature=='04'){
        if(val=='01'){
            $("[name='LEGAL_PRODUCEMODE']").val("02");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }else if(companyTypeCode=='qtyxzrgs'){
        if(val=='20'){
            $("[name='LEGAL_PRODUCEMODE']").val("03");
        }else{
            $("[name='LEGAL_PRODUCEMODE']").val("02");
        }
        $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
    }

}
/*************法人代表信息JS结束****************/


/*************变更选项选择事件JS开始**********************/
/**
 * 名称变更
 */
function C_COMPANYNAME_FN(val){
	if(val=='1'){
		$("#C_COMPANYNAME").attr("style","");
		AppUtil.changeRequireStatus("COMPANY_NAME_CHANGE","1");
	}else{
		$("#C_COMPANYNAME").attr("style","display: none;");
		AppUtil.changeRequireStatus("COMPANY_NAME_CHANGE","-1");
	}
}
/**
 * 注所变更
 */
function C_REGADDRESS_FN(val){
	if(val=='1'){
		$("#C_REGADDRESS").attr("style","");
		AppUtil.changeRequireStatus("REGISTER_ADDR_CHANGE;REGISTER_SQUARE_ADDR;IS_REGISTER_ADDR;LAW_SEND_ADDR;PLACE_REGISTER_OWNER;PLACE_REGISTER_TEL;RESIDENCE_REGISTER_WAYOFGET;ARMY_REGISTER_HOURSE;IS_BUSSINESS_ADDR;","1");
		$("input[name='LAW_EMAIL_ADDR']").addClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]");
		$("input[name='LAW_FAX_ADDR']").addClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]");
		$("input[name='LAW_IM_ADDR']").addClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]");
		$("input[name='REGISTER_SQUARE_ADDR']").addClass("validate[custom[JustNumber]]");
	}else{
		$("#C_REGADDRESS").attr("style","display: none;");
		AppUtil.changeRequireStatus("REGISTER_ADDR_CHANGE;REGISTER_SQUARE_ADDR;IS_REGISTER_ADDR;LAW_SEND_ADDR;PLACE_REGISTER_OWNER;PLACE_REGISTER_TEL;RESIDENCE_REGISTER_WAYOFGET;ARMY_REGISTER_HOURSE;IS_BUSSINESS_ADDR;","-1");
		$("input[name='LAW_EMAIL_ADDR']").removeClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]");
		$("input[name='LAW_FAX_ADDR']").removeClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]");
		$("input[name='LAW_IM_ADDR']").removeClass("validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]");
		$("input[name='REGISTER_SQUARE_ADDR']").removeClass("validate[custom[JustNumber]]");
	}
}
/**
 * 法人变更
 */
function C_LEGAL_FN(val){
	if(val=='1'){
		$("#C_LEGAL").attr("style","");
		AppUtil.changeRequireStatus("LEGAL_NAME_CHANGE;LEGAL_JOB_CHANGE;LEGAL_MOBILE_CHANGE;LEGAL_IDTYPE_CHANGE;LEGAL_IDNO_CHANGE;LEGAL_COUNTRY_CHANGE;LEGAL_PRODUCEMODE_CHANGE;LEGAL_ADDRESS_CHANGE;","1");
	}else{
		$("#C_LEGAL").attr("style","display: none;");
		AppUtil.changeRequireStatus("LEGAL_NAME_CHANGE;LEGAL_JOB_CHANGE;LEGAL_MOBILE_CHANGE;LEGAL_IDTYPE_CHANGE;LEGAL_IDNO_CHANGE;LEGAL_COUNTRY_CHANGE;LEGAL_PRODUCEMODE_CHANGE;LEGAL_ADDRESS_CHANGE;","-1");
	}
}
/**
 * 企业类型
 */
function C_COMPANYTYPE_FN(val){
	if(val=='1'){
		$("#C_COMPANYTYPE").attr("style","");
		$("#COMPANY_TYPE_CHANGE").combobox({required:true});
	}else{
		$("#C_COMPANYTYPE").attr("style","display: none;");
		$("#COMPANY_TYPE_CHANGE").combobox({required:false});
	}
}
/**
 * 注册资本金\投资人股权\股东名称 变更
 */
function C_CAPTITAL_FN(val){
	if(val=='1'){
		$("#C_CAPTITAL").attr("style","");
		$("input[name='INVESTMENT_CHANGE']").addClass(" validate[required]");//投资总额
		$("select[name='CURRENCY_CHANGE']").addClass(" validate[required]");//币种
		$("select[name='CURRENCY_CHANGE']").val("002");//默认人民币
		$("[name$='CURRENCY_CHANGE']").addClass("validate[required]");
		$("[name$='SHAREHOLDER_NAME']").addClass("validate[required]");
		$("[name$='SHAREHOLDER_TYPE']").addClass("validate[required]");
		$("[name$='LICENCE_TYPE']").addClass("validate[required]");
		$("[name$='LICENCE_NO']").addClass("validate[required]");
		$("[name$='ID_ADDRESS']").addClass("validate[required]");
		$("[name$='CONTACT_WAY']").addClass("validate[required]");
		$("[name$='SHAREHOLDER_COMPANYCOUNTRY']").addClass("validate[required]");
		$("[name$='CONTRIBUTIONS']").addClass("validate[required]");
		$("[name$='PROPORTION']").addClass("validate[required]");
		$("[name$='PAIDCAPITAL']").addClass("validate[required]");
		$("[name$='CURRENCY_2']").addClass("validate[required]");
		$("[name$='CURRENCY_3']").addClass("validate[required]");
		$("[name$='PAYMENT_PERIOD']").addClass("validate[required]");
	}else{
		if(!$('input[name="CHANGE_OPTIONS"][value="05"]').is(':checked')&&!$('input[name="CHANGE_OPTIONS"][value="07"]').is(':checked')
				&&!$('input[name="CHANGE_OPTIONS"][value="09"]').is(':checked')){
			$("#C_CAPTITAL").attr("style","display: none;");
			$("input[name='INVESTMENT_CHANGE']").removeClass(" validate[required]");//投资总额
			$("select[name='CURRENCY_CHANGE']").removeClass(" validate[required]");//币种
			$("[name$='CURRENCY_CHANGE']").removeClass("validate[required]");
			$("[name$='SHAREHOLDER_NAME']").removeClass("validate[required]");
			$("[name$='SHAREHOLDER_TYPE']").removeClass("validate[required]");
			$("[name$='LICENCE_TYPE']").removeClass("validate[required]");
			$("[name$='LICENCE_NO']").removeClass("validate[required]");
			$("[name$='ID_ADDRESS']").removeClass("validate[required]");
			$("[name$='CONTACT_WAY']").removeClass("validate[required]");
			$("[name$='SHAREHOLDER_COMPANYCOUNTRY']").removeClass("validate[required]");
			$("[name$='CONTRIBUTIONS']").removeClass("validate[required]");
			$("[name$='PROPORTION']").removeClass("validate[required]");
			$("[name$='PAIDCAPITAL']").removeClass("validate[required]");
			$("[name$='CURRENCY_2']").removeClass("validate[required]");
			$("[name$='CURRENCY_3']").removeClass("validate[required]");
			$("[name$='PAYMENT_PERIOD']").removeClass("validate[required]");
		}
	}
}
/**
 * 经营期限变更
 */
function C_BUSINESSYEAR_FN(val){
	if(val=='1'){
		$("#C_BUSINESSYEAR").attr("style","");
		//AppUtil.changeRequireStatus("BUSSINESS_YEARS_CHANGE;YEAR_FROM_CHANGE;YEAR_TO_CHANGE;","1");
		AppUtil.changeRequireStatus("BUSSINESS_YEARS_CHANGE;","1");
	}else{
		$("#C_BUSINESSYEAR").attr("style","display: none;");
		//AppUtil.changeRequireStatus("BUSSINESS_YEARS_CHANGE;YEAR_FROM_CHANGE;YEAR_TO_CHANGE;","-1");
		AppUtil.changeRequireStatus("BUSSINESS_YEARS_CHANGE;","-1");
	}
}
/**
 * 经营范围变更
 */
function C_BUSINESSSCOPE_FN(val){
	if(val=='1'){
		$("#C_BUSINESSSCOPE").attr("style","");
		$("[name='BUSSINESS_SCOPE_CHANGE']").addClass("validate[required]");
	}else{
		$("#C_BUSINESSSCOPE").attr("style","display: none;");
		$("[name='BUSSINESS_SCOPE_CHANGE']").removeClass("validate[required]");
	}
}
/**
 * 董事备案
 */
function C_DIRECTOR_FN(val){
	if(val=='1'){
		$("#C_DIRECTOR").attr("style","");
		$("[name$='DIRECTOR_NAME']").addClass("validate[required]");
		$("[name$='DIRECTOR_JOB']").addClass("validate[required]");
		$("[name$='DIRECTOR_COUNTRY']").addClass("validate[required]");
		$("[name$='DIRECTOR_IDTYPE']").addClass("validate[required]");
		$("[name$='DIRECTOR_IDNO']").addClass("validate[required],custom[equalsDirectorIdno]");
		$("[name$='DIRECTOR_GENERATION_MODE']").addClass("validate[required]");
		$("[name$='DIRECTOR_APPOINTOR']").addClass("validate[required]");
		$("[name$='DIRECTOR_PHONE']").addClass("validate[required,custom[mobilePhone]]");
	}else{
		$("#C_DIRECTOR").attr("style","display: none;");
		$("#dsxxDiv").html("");
		$("[name$='DIRECTOR_NAME']").removeClass("validate[required]");
		$("[name$='DIRECTOR_JOB']").removeClass("validate[required]");
		$("[name$='DIRECTOR_COUNTRY']").removeClass("validate[required]");
		$("[name$='DIRECTOR_IDTYPE']").removeClass("validate[required]");
		$("[name$='DIRECTOR_IDNO']").removeClass("validate[required],custom[equalsDirectorIdno]");
		$("[name$='DIRECTOR_GENERATION_MODE']").removeClass("validate[required]");
		$("[name$='DIRECTOR_APPOINTOR']").removeClass("validate[required]");
		$("[name$='DIRECTOR_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
	}
	
	
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
	}
	
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	if(nature==null||nature==''||nature==undefined){
		
	}else if(eflowObj.busRecord){
		if(eflowObj.busRecord.DIRECTOR_JSON_CHANGE==undefined 
				|| eflowObj.busRecord.DIRECTOR_JSON_CHANGE==null
				|| eflowObj.busRecord.DIRECTOR_JSON_CHANGE==""){
			//初始化已选企业设立性质且未有变更数据，进行董事备案
			$("#dsxxDiv").html("");
			$("#jsxxDiv").html("");
			$("#jlxxDiv").html("");
			addDsxxDiv();
			addJsxxDiv();
			addJlxxDiv();
			addLegalDiv();
		}
	}	

	if(nature=='02'){//设董事会、监事设立 
		$("#addGdxx").show();
		$("#addDsxx").show();
	}else if(nature=='07'){//设执行董事设立
        $("#addGdxx").show();
        $("#addDsxx").show();
    }else if(nature=='03'){//设执行董事设立
		$("#addGdxx").show();
	}else if(nature=='04'){//设董事会监事会设立
		$("#addGdxx").show();
		$("#addDsxx").show();
		$("#addJsxx").show();
		
		//监事职务能选择
		$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
	}else{
		$("#addGdxx").hide();
		$("#addDsxx").hide();
		$("#addJsxx").hide();
		$("#addJlxx").hide();
		$("[name$='MANAGER_APPOINTOR']").val('股东');
	}
}
/**
 * 监事备案
 */
function C_SUPERVISOR_FN(val){
	if(val=='1'){
		$("#C_SUPERVISOR").attr("style","");
		$("[name$='SUPERVISOR_NAME']").addClass("validate[required]");
		$("[name$='SUPERVISOR_JOB']").addClass("validate[required]");
		$("[name$='SUPERVISOR_IDTYPE']").addClass("validate[required]");
		$("[name$='SUPERVISOR_IDNO']").addClass("validate[required],custom[equalsDirectorOrManagerIdno],custom[equalsJsIdNo]");
		$("[name$='SUPERVISOR_GENERATION_MODE']").addClass("validate[required]");
		$("[name$='SUPERVISOR_APPOINTOR']").addClass("validate[required]");
		$("[name$='SUPERVISOR_COUNTRY']").addClass("validate[required]");
		$("[name$='SUPERVISOR_PHONE']").addClass("validate[required,custom[mobilePhone]]");
	}else{
		$("#C_SUPERVISOR").attr("style","display: none;");
		$("#jsxxDiv").html("");
		$("[name$='SUPERVISOR_NAME']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_JOB']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_IDTYPE']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_IDNO']").removeClass("validate[required],custom[equalsDirectorOrManagerIdno],custom[equalsJsIdNo]");
		$("[name$='SUPERVISOR_GENERATION_MODE']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_APPOINTOR']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_COUNTRY']").removeClass("validate[required]");
		$("[name$='SUPERVISOR_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
	}
	
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
	}
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	if(nature==null||nature==''||nature==undefined){
		
	}else if(eflowObj.busRecord){
		if(eflowObj.busRecord.SUPERVISOR_JSON_CHANGE==undefined 
				|| eflowObj.busRecord.SUPERVISOR_JSON_CHANGE==null
				|| eflowObj.busRecord.SUPERVISOR_JSON_CHANGE==""){
			//初始化已选企业设立性质且未有变更数据，进行监事备案
			$("#dsxxDiv").html("");
			$("#jsxxDiv").html("");
			$("#jlxxDiv").html("");
			addDsxxDiv();
			addJsxxDiv();
			addJlxxDiv();
			addLegalDiv();
		}
	}

	if(nature=='02'){//设董事会、监事设立 
		$("#addGdxx").show();
		$("#addDsxx").show();
	}else if(nature=='07'){//设执行董事设立
        $("#addGdxx").show();
        $("#addDsxx").show();
    }else if(nature=='03'){//设执行董事设立
		$("#addGdxx").show();
	}else if(nature=='04'){//设董事会监事会设立
		$("#addGdxx").show();
		$("#addDsxx").show();
		$("#addJsxx").show();
		
		//监事职务能选择
		$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
	}else{
		$("#addGdxx").hide();
		$("#addDsxx").hide();
		$("#addJsxx").hide();
		$("#addJlxx").hide();
		$("[name$='MANAGER_APPOINTOR']").val('股东');
	}
}
/**
 * 经理备案
 */
function C_MANAGER_FN(val){
	if(val=='1'){
		$("#C_MANAGER").attr("style","");
		$("[name$='MANAGER_NAME']").addClass("validate[required]");
		$("[name$='MANAGER_JOB']").addClass("validate[required]");
		$("[name$='MANAGER_IDTYPE']").addClass("validate[required]");
		$("[name$='MANAGER_IDNO']").addClass("validate[required]");
		$("[name$='MANAGER_GENERATION_MODE']").addClass("validate[required]");
		$("[name$='MANAGER_APPOINTOR']").addClass("validate[required]");
		$("[name$='MANAGER_COUNTRY']").addClass("validate[required]");
		$("[name$='MANAGER_PHONE']").addClass("validate[required,custom[mobilePhone]]");
	}else{
		$("#C_MANAGER").attr("style","display: none;");
		$("#jlxxDiv").html("");
		$("[name$='MANAGER_NAME']").removeClass("validate[required]");
		$("[name$='MANAGER_JOB']").removeClass("validate[required]");
		$("[name$='MANAGER_IDTYPE']").removeClass("validate[required]");
		$("[name$='MANAGER_IDNO']").removeClass("validate[required]");
		$("[name$='MANAGER_GENERATION_MODE']").removeClass("validate[required]");
		$("[name$='MANAGER_APPOINTOR']").removeClass("validate[required]");
		$("[name$='MANAGER_COUNTRY']").removeClass("validate[required]");
		$("[name$='MANAGER_PHONE']").removeClass("validate[required,custom[mobilePhone]]");
	}
	
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
	}
	var nature = $("#COMPANY_NATURE").combobox('getValue');
	if(nature==null||nature==''||nature==undefined){
		
	}else if(eflowObj.busRecord.MANAGER_JSON_CHANGE==undefined 
			|| eflowObj.busRecord.MANAGER_JSON_CHANGE==null
			|| eflowObj.busRecord.MANAGER_JSON_CHANGE==""){
		//初始化已选企业设立性质且未有变更数据，进行经理备案
		$("#dsxxDiv").html("");
		$("#jsxxDiv").html("");
		$("#jlxxDiv").html("");
		addDsxxDiv();
		addJsxxDiv();
		addJlxxDiv();
		addLegalDiv();
	}	
	else if(eflowObj.busRecord){
		if(eflowObj.busRecord.MANAGER_JSON_CHANGE==undefined 
				|| eflowObj.busRecord.MANAGER_JSON_CHANGE==null
				|| eflowObj.busRecord.MANAGER_JSON_CHANGE==""){
			//初始化已选企业设立性质且未有变更数据，进行经理备案
			$("#dsxxDiv").html("");
			$("#jsxxDiv").html("");
			$("#jlxxDiv").html("");
			addDsxxDiv();
			addJsxxDiv();
			addJlxxDiv();
			addLegalDiv();
		}
	}

	if(nature=='02'){//设董事会、监事设立 
		$("#addGdxx").show();
		$("#addDsxx").show();
	}else if(nature=='07'){//设执行董事设立
        $("#addGdxx").show();
        $("#addDsxx").show();
    }else if(nature=='03'){//设执行董事设立
		$("#addGdxx").show();
	}else if(nature=='04'){//设董事会监事会设立
		$("#addGdxx").show();
		$("#addDsxx").show();
		$("#addJsxx").show();
		
		//监事职务能选择
		$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
	}else{
		$("#addGdxx").hide();
		$("#addDsxx").hide();
		$("#addJsxx").hide();
		$("#addJlxx").hide();
		$("[name$='MANAGER_APPOINTOR']").val('股东');
	}
}

/*************变更选项选择事件JS结束**********************/


/**
 * 验证变更事项选择
 */
function validateChangeOptions(){
	var checked = false;
	var legalChecked = false;
	var legalRelateChecked = false;
	var personnelChecked = false;
	var companyTypeChecked = false;
	var holderChecked = false;
	$.each($('input[name="CHANGE_OPTIONS"]:checked'),function(){
        checked = true;
        if($(this).val()=='03'){
        	legalChecked = true;
        }
        if($(this).val()=='10'||$(this).val()=='12'){
        	legalRelateChecked = true;
        	personnelChecked = true;
        }
        if($(this).val()=='11'){
        	personnelChecked = true;
        }
        if($(this).val()=='04'){
        	companyTypeChecked = true;
        }
        if($(this).val()=='07'){
        	holderChecked = true;
        }
    });
	if(!checked){		
		art.dialog({
			content : "请选择变更项",
			icon : "warning",
			ok : true
		});
		return false;
	}
	if(companyTypeChecked&&!holderChecked){		
		art.dialog({
			content : "企业类型变更，需要同时选择投资人(股权)变更！",
			icon : "warning",
			ok : true
		});
		return false;
	}
	if(legalChecked&&!legalRelateChecked&&!holderChecked){		
		art.dialog({
			content : "法定代表人不可单独变更，必定涉及到董事或经理备案！",
			icon : "warning",
			ok : true
		});
		return false;
	}
	if(personnelChecked&&!legalChecked&&!holderChecked){		
		art.dialog({
			content : "只进行董事、监事或经理备案，请选择企业备案登记申报！",
			icon : "warning",
			ok : true
		});
		return false;
	}
	if(!queryDone){		
		art.dialog({
			content : "请先正确查询企业信息后再进行下一步",
			icon : "warning",
			ok : true
		});
		return false;
	}
	return true;
}

function validateSingleForm(formId){
	if(validateChangeOptions()){
		var flag = true;
		if(formId=='PERSONNEL_FORM'||formId=='CAPITAL_FORM'||formId=='LEGAL_FORM'){
			var nature = $("#COMPANY_NATURE").combobox('getValue');
			if(nature==null||nature==''||nature==undefined){
				flag = false;
				art.dialog({
					content : "请先选择公司类型和性质",
					icon : "warning",
					ok : true
				});
			}
		}
		if(!flag){
			$("#COMPANY_FORM_A").click();
		}else{
			AppUtil.validateWebsiteTabForm();
			$("#"+formId).validationEngine("hideAll");
		}
	}else{
		$("#CHANGEOPTIONS_FORM_A").click();
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

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){	
	if (zjlx == "SF") {
		$("input[name='" + name + "']").addClass(",custom[vidcard]");
	} else {
		$("input[name='" + name + "']").removeClass(",custom[vidcard]");
	}
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
		$("."+inputName+"_CLASS").attr("style","display:''");
		$("#qtzm").attr("style","display:''");
    } else {
        $("input[name="+inputName+"]").attr("disabled",true);
        $("input[name="+inputName+"]").removeClass(" validate[required]");
        $("input[name="+inputName+"]").val('');
		$("."+inputName+"_CLASS").attr("style","display:none");
		$("#qtzm").attr("style","display:none");
    }
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


