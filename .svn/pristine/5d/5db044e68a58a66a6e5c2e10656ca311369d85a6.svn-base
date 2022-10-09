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
			//初始化财务专用章			
			setSealType(eflowObj.busRecord.FINANCE_TYPE,"FINANCE");
			//初始发票专用章
			setSealType(eflowObj.busRecord.BILL_TYPE,"BILL");
			//初始合同公章
			setSealType(eflowObj.busRecord.CONTRACT_TYPE,"CONTRACT");
			//初始法人印章
			setSealType(eflowObj.busRecord.LEGALSEAL_TYPE,"LEGALSEAL");	
			//初始化企业印章规格
			showSpecother(eflowObj.busRecord.OFFICIAL_SPEC,'OFFICIAL_SPECOTHER');
			//初始化财务专用章规格
			showSpecother(eflowObj.busRecord.FINANCE_SPEC,'FINANCE_SPECOTHER');
			//初始化发票专用章规格
			showSpecother(eflowObj.busRecord.BILL_SPEC,'BILL_SPECOTHER');
			//初始化合同公章规格
			showSpecother(eflowObj.busRecord.CONTRACT_SPEC,'CONTRACT_SPECOTHER');
			//初始化法人印章规格
			showSpecother(eflowObj.busRecord.LEGALSEAL_SPEC,'LEGALSEAL_SPECOTHER');
			//初始化其他信息						
			//FlowUtil.initFormFieldValue(eflowObj.busRecord,"OTHER_FORM");
			//出资方式
			setInvestFamily(eflowObj.busRecord.INVEST_TYPE);
			
			setZjValidate(eflowObj.busRecord.INVESTOR_IDTYPE,'INVESTOR_IDCARD');
			setZjValidate(eflowObj.busRecord.LIAISON_IDTYPE,'LIAISON_IDNO');
			setZjValidate(eflowObj.busRecord.FINANCE_IDTYPE,'FINANCE_IDNO');
			setZjValidate(eflowObj.busRecord.TAXMAN_IDTYPE,'TAXMAN_IDNO');;

            //初始化公积金登记信息
			initGjjdjxxForm(eflowObj.busRecord.FUNDS_GJJLX);
			//初始化是否同时进行员工参保(社保)
			initSfCb(eflowObj.busRecord.IS_EMPLOY_INSU);
			//初始化是否同时进行员工参保(医保)
			initYbCb(eflowObj.busRecord.IS_EMPLOY_INMEDICAL);
			
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){				
				//初始化字段审核意见
				setFieldAudit(eflowObj.EFLOW_EXEID);
			}else{
				$("#itemMaterchoose").attr('style','display:none;');
				$("#itemMaterdel").attr('style','display:none;');
			}
			if(eflowObj.busRecord.RUN_STATUS==2){
				$("#itemMaterchoose").attr('style','display:none;');
				$("#itemMaterdel").attr('style','display:none;');
			}
			//初始化1+N证合一已编辑表单化附件ID记录
			$.post("domesticControllerController/checkHaveOnlineMater.do",{
				exeId:eflowObj.EFLOW_EXEID,
				itemCodes:eflowObj.busRecord.ITEMCODES
			}, function(responseText, status, xhr) {
				resultJson = $.parseJSON(responseText);
				var onlineForm = resultJson.jsonString;
				$.each(JSON.parse(onlineForm), function(idx, obj) {
				    store[obj.formName] = obj.recordId;
				});
			});
			var ritemCode = $("input[name='ITEMCODES']").val();
			if(ritemCode==null||ritemCode==""){
				$("#applyItemMater").attr("style","display:none;");
			}

			//银行信息
			if(eflowObj.busRecord.IS_ACCOUNT_OPEN==1){
				$("#bankInfo").attr("style","");
				$("input[name='BANK_TYPE']").attr("disabled",false);
				$("input[name='CONTROLLER']").attr("disabled",false);
				setBankInfo(eflowObj.busRecord.BANK_TYPE);
			}else{
				$("input[name='BANK_TYPE']").removeAttr("checked");
			}

            //邮寄公章信息
            if (eflowObj.busRecord.ISEMAIL == 1) {
                $("#emailInfo").attr("style", "");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "1");
            } else {
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "-1");
            }
		}else{
			var ritemCode = $("input[name='RITEMCODES']").val();
			if(ritemCode==null||ritemCode==""){
				$("#applyItemMater").attr("style","display:none;");
			}else{
				$("input[name='ITEMCODES']").val($("input[name='RITEMCODES']").val());
				$("textarea[name='ITEMNAMES']").val($("input[name='RITEMNAMES']").val());
			}
            // art.dialog.confirm("请确认是否已通过名称登记，已通过请点击 <p>确定，未通过请点击此处前往<a href='http://61.154.11.191/usermana/login.do?method=index' target='_blank'><span style='color: red;text-decoration:underline'>红盾网</span></a>办理!</p>", function() {
            // }, function() {
                // window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
            // });
			
            var IS_ACCOUNT_OPEN_ITEM = $("input[name='IS_ACCOUNT_OPEN_ITEM']").val();
            if('1'==IS_ACCOUNT_OPEN_ITEM){
                $("input[name='IS_ACCOUNT_OPEN']").attr("checked","checked");
                $("#bankInfo").attr("style","");
                $("input[name='BANK_TYPE']").attr("disabled",false);
                $("input[name='CONTROLLER']").attr("disabled",false);
            }
            var ISEMAIL_ITEM = $("input[name='ISEMAIL_ITEM']").val();
            if("1"==ISEMAIL_ITEM){
                $("input[name='ISEMAIL']").attr("checked","checked");
                $("#emailInfo").attr("style","");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
            }
		}
	}	
	//出资方式
	$("input[name='INVEST_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setInvestFamily(Value);
	});

	//----------------印章信息开始-----------------------
	
	
	//财务专用章
	$("input[name='FINANCE_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setSealType(Value,"FINANCE");
	});
	//发票专用章
	$("input[name='BILL_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setSealType(Value,"BILL");
	});
	//合同公章
	$("input[name='CONTRACT_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setSealType(Value,"CONTRACT");
	});
	
	//法人公章
	$("input[name='LEGALSEAL_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setSealType(Value,"LEGALSEAL");
	});
	
	//企业印章规格
	$("input[name='OFFICIAL_SPEC']").click(function(){
		//获取值
		var Value = $(this).val();
		showSpecother(Value,'OFFICIAL_SPECOTHER');
	});
	//财务专用章规格
	$("input[name='FINANCE_SPEC']").click(function(){
		//获取值
		var Value = $(this).val();
		showSpecother(Value,'FINANCE_SPECOTHER');
	});
	//发票专用章规格
	$("input[name='BILL_SPEC']").click(function(){
		//获取值
		var Value = $(this).val();
		showSpecother(Value,'BILL_SPECOTHER');
	});
	//合同公章规格
	$("input[name='CONTRACT_SPEC']").click(function(){
		//获取值
		var Value = $(this).val();
		showSpecother(Value,'CONTRACT_SPECOTHER');
	});
	//法人印章规格
	$("input[name='LEGALSEAL_SPEC']").click(function(){
		//获取值
		var Value = $(this).val();
		showSpecother(Value,'LEGALSEAL_SPECOTHER');
	});

	//企业印章材质
	$("input[name='OFFICIAL_MATERIAL']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='01'){//金属铜质
			$("input[name='OFFICIAL_PRICE']").val('140');
		}else if(Value=='02'){//橡胶
			$("input[name='OFFICIAL_PRICE']").val('65');
		}else if(Value=='03'){//光敏
			$("input[name='OFFICIAL_PRICE']").val('90');
		}else if(Value=='04'){//牛角
			$("input[name='OFFICIAL_PRICE']").val('110');
		}else if(Value=='05'){//回墨
			$("input[name='OFFICIAL_PRICE']").val('125');
		}
	});
	//财务专用章材质
	$("input[name='FINANCE_MATERIAL']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='01'){//金属铜质
			$("input[name='FINANCE_PRICE']").val('140');
		}else if(Value=='02'){//橡胶
			$("input[name='FINANCE_PRICE']").val('65');
		}else if(Value=='03'){//光敏
			$("input[name='FINANCE_PRICE']").val('90');
		}else if(Value=='04'){//牛角
			$("input[name='FINANCE_PRICE']").val('110');
		}else if(Value=='05'){//回墨
			$("input[name='FINANCE_PRICE']").val('125');
		}
	});
	//发票专用章材质
	$("input[name='BILL_MATERIAL']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='01'){//金属铜质
			$("input[name='BILL_PRICE']").val('140');
		}else if(Value=='02'){//橡胶
			$("input[name='BILL_PRICE']").val('65');
		}else if(Value=='03'){//光敏
			$("input[name='BILL_PRICE']").val('90');
		}else if(Value=='04'){//牛角
			$("input[name='BILL_PRICE']").val('110');
		}else if(Value=='05'){//回墨
			$("input[name='BILL_PRICE']").val('125');
		}
	});
	//合同公章材质
	$("input[name='CONTRACT_MATERIAL']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='01'){//金属铜质
			$("input[name='CONTRACT_PRICE']").val('140');
		}else if(Value=='02'){//橡胶
			$("input[name='CONTRACT_PRICE']").val('65');
		}else if(Value=='03'){//光敏
			$("input[name='CONTRACT_PRICE']").val('90');
		}else if(Value=='04'){//牛角
			$("input[name='CONTRACT_PRICE']").val('110');
		}else if(Value=='05'){//回墨
			$("input[name='CONTRACT_PRICE']").val('125');
		}
	});
	//法人印章材质
	$("input[name='LEGALSEAL_MATERIAL']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='01'){//金属铜质
			$("input[name='LEGALSEAL_PRICE']").val('140');
		}else if(Value=='02'){//橡胶
			$("input[name='LEGALSEAL_PRICE']").val('65');
		}else if(Value=='03'){//光敏
			$("input[name='LEGALSEAL_PRICE']").val('65');
		}else if(Value=='04'){//牛角
			$("input[name='LEGALSEAL_PRICE']").val('110');
		}else if(Value=='05'){//回墨
			$("input[name='LEGALSEAL_PRICE']").val('');
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
	//----------------印章信息结束-----------------------
	//银行开户信息
	$("input[name='IS_ACCOUNT_OPEN']").click(function(){
		var Value = $(this).val();
		if(Value=='1'){
			$("#bankInfo").attr("style","");
			$("input[name='BANK_TYPE']").attr("disabled",false);
			$("input[name='CONTROLLER']").attr("disabled",false);
		}else{
			$("#bankInfo").attr("style","display:none;");
			$("#bankInfo input[type='text']").val("");
			$("#bankInfo textarea").val("");
			$("input[name='BANK_TYPE']").removeAttr("checked");
			$("#legalPersonIDType").val("");
			$("#pdblist").attr("style","display:none;");
			$("#ccblist").attr("style","display:none;");
			$("#boclist").attr("style","display:none;");
			$("#abclist").attr("style","display:none;");
			$("#rcblist").attr("style","display:none;");
			$("#templateLoad").attr("style","display:none;");
			$("input[name='BANK_TYPE']").attr("disabled",true);
			$("input[name='CONTROLLER']").attr("disabled",true);
		}
	});
	$("#legalPersonIDType").attr("disabled",true);
	$("input[name='BANK_TYPE']").click(function(){
		var Value = $(this).val();
		setBankInfo(Value);
	});
	$("input[name='COMPANY_NAME']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("input[name='DEPOSITOR']").val($(this).val());
			$("input[name='ACCOUNT_NAME']").val($(this).val());
		}
	}); 
	$("input[name='COMPANY_ADDR']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("input[name='DEPOSITOR_ADDR']").val($(this).val());
		}
	}); 
	$("input[name='INVESTMENT']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("#REG_CAPITAL']").val($(this).val());
		}
	}); 
	$("input[name='INVESTOR_NAME']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("#legalPersonName").val($(this).val());
		}
	}); 
	$("input[name='INVESTOR_IDCARD']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("#legalPersonIDNo").val($(this).val());
		}
	});
});


function initGjjdjxxForm(val){
	if (val) {
		if (val == 'ZFGJJ') {
			$(".zfgjj").show();
			$(".zfgjj input").each(function () {
				$(this).attr("disabled", false);
			});
			$(".zfbt").hide();
			$(".zfbt input").each(function () {
				$(this).attr("disabled", true);
			});
		} else if (val == 'ZFBT') {
			$(".zfgjj").hide();
			$(".zfgjj input").each(function () {
				$(this).attr("disabled", true);
			});
			$(".zfbt").show();
			$(".zfbt input").each(function () {
				$(this).attr("disabled", false);
			});
		}
	} else {
		$(".zfgjj").show();
		$(".zfgjj input").each(function () {
			$(this).attr("disabled", false);
		});
		$(".zfbt").hide();
		$(".zfbt input").each(function () {
			$(this).attr("disabled", true);
		});
	}
}
//是否同时进行员工参保(社保)
function initSfCb(val){
	if(val=="1"){//是
		$(".ygupload").show();
	}else if(val!="1"){//否
		$(".ygupload").hide();
	}
}
//是否同时进行员工参保(医保)
function initYbCb(val){
	if(val=="1"){//是
		$("#ybcb").show();
	}else if(val!="1"){//否
		$("#ybcb").hide();
	}
}

function setBankInfo(Value){
	$("input[name='DEPOSITOR_POSTCODE']").val("350400");
	$("input[name='AREA_CODE']").val("391008");
	$("input[name='DEPOSITOR_TYPE']").val("企业法人");
	$("input[name='PROOFDOC']").val("工商营业执照");
	$("input[name='ACCOUNT_NATURE']").val("基本");
	$("#legalPersonIDType").attr("disabled",false);
	$("#legalPersonName").val($("input[name='INVESTOR_NAME']").val());
	$("#legalPersonIDType").val($("#INVESTOR_IDTYPE").val());
	$("#legalPersonIDNo").val($("input[name='INVESTOR_IDCARD']").val());
	$("#legalPersonIDType").attr("disabled",true);
	$("#templateLoad").attr("style","");
    if(Value=='ccb'){
        $("input[name='BANK_NAME']").val("中国建设银行股份有限公司福建自贸试验区平潭片区分行");
        $("input[name='BANK_CODE']").val("105391002138");
        $("#ccblist").attr("style","");
        $("#boclist").attr("style","display:none;");
        $("#abclist").attr("style","display:none;");
        $("#pdblist").attr("style","display:none;");
        $("#rcblist").attr("style","display:none;");
        $("#commlist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/开立单位银行结算账户申请书.docx");
    }else if(Value=='boc'){
        $("input[name='BANK_NAME']").val("中国银行股份有限公司平潭金井湾支行");
        $("input[name='BANK_CODE']").val("104391012065");
        $("#boclist").attr("style","");
        $("#rcblist").attr("style","display:none;");
        $("#abclist").attr("style","display:none;");
        $("#ccblist").attr("style","display:none;");
        $("#pdblist").attr("style","display:none;");
        $("#commlist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/中国银行开立单位银行结算账户申请书.docx");
    }else if(Value=='abc'){
        $("input[name='BANK_NAME']").val("中国农业银行股份有限公司平潭综合实验区支行");
        $("input[name='BANK_CODE']").val("103391016513");
        $("#abclist").attr("style","");
        $("#boclist").attr("style","display:none;");
        $("#rcblist").attr("style","display:none;");
        $("#ccblist").attr("style","display:none;");
        $("#pdblist").attr("style","display:none;");
        $("#commlist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/中国农业银行开立单位银行结算账户申请书.docx");
    }else if(Value=='rcb'){
        $("input[name='BANK_NAME']").val("福建平潭农村商业银行股份有限公司");
        $("input[name='BANK_CODE']").val("402391017047");
        $("#rcblist").attr("style","");
        $("#ccblist").attr("style","display:none;");
        $("#abclist").attr("style","display:none;");
        $("#boclist").attr("style","display:none;");
        $("#pdblist").attr("style","display:none;");
        $("#commlist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/平潭农村商业银行开立单位银行结算账户申请书.docx");
    }else if(Value=='pdb'){
        $("input[name='BANK_NAME']").val("上海浦东发展银行福州平潭分行");
        $("input[name='BANK_CODE']").val("310391000053");
        $("#pdblist").attr("style","");
        $("#ccblist").attr("style","display:none;");
        $("#boclist").attr("style","display:none;");
        $("#abclist").attr("style","display:none;");
        $("#rcblist").attr("style","display:none;");
        $("#commlist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/上海浦东发展银行开立单位银行结算账户申请书.docx");
    }else if(Value=='comm'){
        $("input[name='BANK_NAME']").val("交通银行股份有限公司平潭综合实验区支行");
        $("input[name='BANK_CODE']").val("301391000309");
        $("#commlist").attr("style","");
        $("#boclist").attr("style","display:none;");
        $("#rcblist").attr("style","display:none;");
        $("#ccblist").attr("style","display:none;");
        $("#pdblist").attr("style","display:none;");
        $("#abclist").attr("style","display:none;");
        $("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/交通银行开立单位银行结算账户申请书.docx");
    }
	$("input[name='DEPOSITOR']").val($("input[name='COMPANY_NAME']").val());
	$("input[name='DEPOSITOR_ADDR']").val($("input[name='COMPANY_ADDR']").val());
	$("input[name='ACCOUNT_NAME']").val($("input[name='COMPANY_NAME']").val());
	$("#REG_CAPITAL").val($("input[name='INVESTMENT']").val());
	$("#businessScope").val($("[name='BUSSINESS_SCOPE']").val());
	$("#INDUSTRY_CATEGORY_CDOE").val($("input[name='INDUSTRY_CATEGORY']").val());
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
		$("input[name='INVEST_FAMILY']").attr("disabled","disabled");
		$("input[name='INVEST_FAMILY']").removeClass(" validate[required]");		
		$("input[name='INVEST_FAMILY']").val("");	
	}else if(Value=="2"){
		$("input[name='INVEST_FAMILY']").attr("disabled",false);
		$("input[name='INVEST_FAMILY']").addClass("validate[required]");
	}
}
//设置印章
function setSealType(value,name){
	if(value=="1"){
		$("input[name='"+name+"_SPEC']").attr("disabled",false);
		$("input[name='"+name+"_MATERIAL']").attr("disabled",false);			
		
		$("input[name='"+name+"_SPEC']").addClass("validate[required]");	
		$("input[name='"+name+"_MATERIAL']").addClass("validate[required]");

	}else{
		$("input[name='"+name+"_SPEC']").attr("disabled","disabled");
		$("input[name='"+name+"_MATERIAL']").attr("disabled","disabled");
		$("input[name='"+name+"_SPEC']").removeAttr("checked");
		$("input[name='"+name+"_MATERIAL']").removeAttr("checked");			
		
		$("input[name='"+name+"_SPEC']").removeClass("validate[required]");		
		$("input[name='"+name+"_MATERIAL']").removeClass("validate[required]");
		
		$("input[name='"+name+"_SPECOTHER']").val("");
		$("input[name='"+name+"_PRICE']").val("");			
		$("#"+name+"_SPECOTHER").hide();
	}
}
//根据值与name显示印章规格大小
function showSpecother(val,name){	
	if(val==0){
		$("#"+name).show();
		$("input[name='"+name+"']").attr("disabled",false);
		$("input[name='"+name+"']").addClass(" validate[required]");
	}else{			
		$("#"+name).hide();	
		$("input[name='"+name+"']").val("");	
		$("input[name='"+name+"']").attr("disabled",true);
		$("input[name='"+name+"']").removeClass(" validate[required]");
	}
}
//提交流程按钮
function FLOW_SubmitFun() {
	//添加是否同时进行员工参保判断，选是则必须上传对应的材料
	var isCb = $("input[name='IS_EMPLOY_INSU']:checked").val();
	var FILE_ID3 = $("input[name='FILE_ID3']").val();
	if(isCb=="1"){
		if(FILE_ID3==null || FILE_ID3 == undefined || FILE_ID3 ==""){
			art.dialog({
				content : "请上传同时进行员工参保所对应的材料！",
				icon : "warning",
				ok : true
			});
			return;
		}
	}
		
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var COMPANY_NAME= $("input[name='COMPANY_NAME']").val();//名称
	if(null!=COMPANY_NAME && ''!=COMPANY_NAME && (COMPANY_NAME.indexOf("公司")>-1||COMPANY_NAME.indexOf("企业")>-1)){		
		art.dialog({
			content : "有限公司和有限责任公司请在内资企业设立-有限责任公司(自然人独资)中申报。",
			icon : "warning",
			ok : true
		});
		return;
	}
	//验证表单是否合法
	var valiateTabForm = AppUtil.validateWebsiteTabForm();
	if (valiateTabForm&&setOnlineMater()) {
		if($("input[name='ITEMCODES']").val()!=null&&$("input[name='ITEMCODES']").val()!=''&&$("input[name='ITEMCODES']").val()!=undefined){
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(-1);
			if(submitMaterFileJson==null){
				return false;
			}
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		}
		//获取流程信息对象JSON
		 var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		 //将其转换成JSON对象
		 var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

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
		 //获取中方投资方信息
		 flowSubmitObj.DOMESTICINVESTOR_JSON =getZftzzxxJson();
		 //获取外方投资方信息
		 flowSubmitObj.FOREIGNINVESTOR_JSON =getJwtzzxxJson();
		 //获取董事信息
		 flowSubmitObj.DIRECTOR_JSON =getDsxxJson();
		 //获取监事信息
		 flowSubmitObj.SUPERVISOR_JSON =getJsxxJson();
		 //获取经理信息
		 flowSubmitObj.MANAGER_JSON =getJlxxJson();
		 //获取外商投资企业法律文件送达授权委托书基本信息
		 flowSubmitObj.ATTORNEY_JSON =getAttorneyJson();
		 //获取房地产经纪机构及其分支机构备案JSON
		 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
		 //广告发布单位广告从业、审查人员JSON
		 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
		 //银行账号信息及委托扣款协议JSON
		 if($("input[name='IS_TAX_BANKACCOUNT']:checked").val()=='1'){
			 flowSubmitObj.ACCOUNTANDAGREEMENTJSON =getaAccountAndAgreementJson();
		 }
		 //申领发票JSON
		 if($("input[name='IS_APPLY_INVOICE']:checked").val()=='1'){
			 flowSubmitObj.APPLYINVOICE_JSON =getInvoiceApllyJson();
		 }

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
	setOnlineMater();
	if($("input[name='ITEMCODES']").val()!=null&&$("input[name='ITEMCODES']").val()!=''&&$("input[name='ITEMCODES']").val()!=undefined){
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	}
	//var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(1);
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
	 //获取中方投资方信息
	 flowSubmitObj.DOMESTICINVESTOR_JSON =getZftzzxxJson();
	 //获取外方投资方信息
	 flowSubmitObj.FOREIGNINVESTOR_JSON =getJwtzzxxJson();
	 //获取董事信息
	 flowSubmitObj.DIRECTOR_JSON =getDsxxJson();
	 //获取监事信息
	 flowSubmitObj.SUPERVISOR_JSON =getJsxxJson();
	 //获取经理信息
	 flowSubmitObj.MANAGER_JSON =getJlxxJson();
	 //获取外商投资企业法律文件送达授权委托书基本信息
	 flowSubmitObj.ATTORNEY_JSON =getAttorneyJson();
	 //获取房地产经纪机构及其分支机构备案JSON
	 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
	 //广告发布单位广告从业、审查人员JSON
	 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
	 //银行账号信息及委托扣款协议JSON
	 if($("input[name='IS_TAX_BANKACCOUNT']:checked").val()=='1'){
		 flowSubmitObj.ACCOUNTANDAGREEMENTJSON =getaAccountAndAgreementJson();
	 }
	 //申领发票JSON
	 if($("input[name='IS_APPLY_INVOICE']:checked").val()=='1'){
		 flowSubmitObj.APPLYINVOICE_JSON =getInvoiceApllyJson();
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
	$("#legalPersonIDType").val(zjlx);
}

/**
* 经营范围选择器
**/
function showSelectJyfw(){
	var induIds = $("input[name='BUSSINESS_SCOPE_ID']").val();
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
				$("[name='BUSSINESS_SCOPE']").val(selectInduInfo.induNames+"。（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
				$("[name='BUSSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
				if(chk=='1'){
					$("#businessScope").val(selectInduInfo.induNames);
					$("#INDUSTRY_CATEGORY_CDOE").val(selectInduInfo.firstCodes);
				}
				$("[name='INDUSTRY_CATEGORY']").val(selectInduInfo.firstCodes);
				art.dialog.removeData("selectInduInfo");
			}else{				
				$("[name='BUSSINESS_SCOPE']").val('');
				$("[name='BUSSINESS_SCOPE_ID']").val('');
			}
		}
	}, false);
};
/**
* 经营范围选择器
**/
function showSelectJyfwNew(){
	var ids = $("input[name='BUSSINESS_SCOPE_ID']").val();
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
					var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
					if(chk=='1'){
						$("#businessScope").val(selectBusScopeInfo.induNames);
						$("#INDUSTRY_CATEGORY_CDOE").val(selectBusScopeInfo.induCodes);
					}
					$("[name='INDUSTRY_CATEGORY']").val(selectBusScopeInfo.induCodes);
					art.dialog.removeData("selectBusScopeInfo");
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

/*************外方投资者信息JS开始****************/
/**
 * 添加外方投资者信息
 */
function addJwtzzxxDiv(){
	$.post("foreignControllerController/refreshJwtzzxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jwtzzxxDiv").append(responseText);
	});
}
/**
 * 初始化时外方投资者信息
 */
function addInitJwtzzxxDiv(holder,i){
	$.post("foreignControllerController/refreshJwtzzxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jwtzzxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(holder,$("#jwtzzxxDiv div").eq(i));
	});
}
/**
 * 删除外方投资者信息
 */
function delJwtzzxxDiv(o){
	$(o).closest("div").remove();
	setJwtzzInvestment();
} 


/**
 * 获取外方投资者信息
 */
function getJwtzzxxJson(){
	var jwtzzxxArray = [];
	$("#jwtzzxxDiv div").each(function(i){
		var INVESTOR_TYPE = $(this).find("[name$='INVESTOR_TYPE']").val();//投资者类型
		var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//投资者名称
		var INVESTOR_PHONE = $(this).find("[name$='INVESTOR_PHONE']").val();//投资者联系电话
		var DIRECTOR_NUM = $(this).find("[name$='DIRECTOR_NUM']").val();//委派董事人数		
		var INVESTOR_IDTYPE = $(this).find("[name$='INVESTOR_IDTYPE']").val();	//主体资格证明/护照
		var INVESTOR_IDNO = $(this).find("[name$='INVESTOR_IDNO']").val();//证件号码
		var INVESTOR_ADDR = $(this).find("[name$='INVESTOR_ADDR']").val();// 地址
			
		var INVESTOR_COUNTRY = $(this).find("[name$='INVESTOR_COUNTRY']").val();//国别（地区）
		var FUNDS_SOURCE = $(this).find("[name$='FUNDS_SOURCE']").val();//资金来源地
		
		var INVESTMENT_ABROAD_EXCHANGE = $(this).find("[name$='INVESTMENT_ABROAD_EXCHANGE']").val();//现金_境外现汇
		var INVESTMENT_ABROAD_RMB = $(this).find("[name$='INVESTMENT_ABROAD_RMB']").val();//现金_境外人民币
		var INVESTMENT_DOMESTIC_RMB = $(this).find("[name$='INVESTMENT_DOMESTIC_RMB']").val();//现金_境内人民币
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//实物
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		
		var CONTRIBUTION = $(this).find("[name$='CONTRIBUTION']").val();//认缴出资额
		var PROPORTION = $(this).find("[name$='PROPORTION']").val();//占注册资本比例
		var FOLDING_RMB = $(this).find("[name$='FOLDING_RMB']").val();//折人民币
		if(null!=INVESTOR_TYPE&&INVESTOR_TYPE!=""){
			var jwtzzxx = {};
			jwtzzxx.INVESTOR_TYPE = INVESTOR_TYPE;
			jwtzzxx.INVESTOR_NAME = INVESTOR_NAME;
			jwtzzxx.INVESTOR_PHONE = INVESTOR_PHONE;
			jwtzzxx.DIRECTOR_NUM = DIRECTOR_NUM;
			jwtzzxx.INVESTOR_IDTYPE = INVESTOR_IDTYPE;
			jwtzzxx.INVESTOR_IDNO = INVESTOR_IDNO;
			jwtzzxx.INVESTOR_ADDR = INVESTOR_ADDR;			
			
			jwtzzxx.INVESTOR_COUNTRY = INVESTOR_COUNTRY;
			jwtzzxx.FUNDS_SOURCE = FUNDS_SOURCE;
			
			jwtzzxx.INVESTMENT_ABROAD_EXCHANGE = INVESTMENT_ABROAD_EXCHANGE;
			jwtzzxx.INVESTMENT_ABROAD_RMB = INVESTMENT_ABROAD_RMB;
			jwtzzxx.INVESTMENT_DOMESTIC_RMB = INVESTMENT_DOMESTIC_RMB;
			jwtzzxx.INVESTMENT_MATERIAL = INVESTMENT_MATERIAL;
			jwtzzxx.INVESTMENT_TECHNOLOGY = INVESTMENT_TECHNOLOGY;
			jwtzzxx.INVESTMENT_LAND = INVESTMENT_LAND;
			jwtzzxx.INVESTMENT_STOCK = INVESTMENT_STOCK;
			jwtzzxx.INVESTMENT_OTHER = INVESTMENT_OTHER;
			
			jwtzzxx.CONTRIBUTION = CONTRIBUTION;
			jwtzzxx.PROPORTION = PROPORTION;
			jwtzzxx.FOLDING_RMB = FOLDING_RMB;
		
			jwtzzxxArray.push(jwtzzxx);	
		}
		
	});
	if(jwtzzxxArray.length>0){
		return JSON.stringify(jwtzzxxArray);
	}else{
		return "";
	}
}
//初始化外方投资者信息
function setJwtzzxx(holderJson){
	var holders = eval(holderJson);
	if(holders){				
		for(var i=0;i<holders.length;i++){			
			if(i>0){
				addInitJwtzzxxDiv(holders[i],i);
			}else{				
				FlowUtil.initFormObjValue(holders[i],$("#jwtzzxxDiv div").eq(i));
			}
		}
	}
}
/*************外方投资者信息JS结束****************/


/*************中方投资者信息JS开始****************/
/**
 * 添加中方投资者信息
 */
function addZftzzxxDiv(){
	$.post("foreignControllerController/refreshZftzzxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#zftzzxxDiv").append(responseText);		
		$("#zftzzxxDiv [name$='INVESTOR_COUNTRY']").attr("disabled",true); 
		
	});
}
/**
 * 初始化时中方投资者信息
 */
function addInitZftzzxxDiv(holder,i){
	$.post("foreignControllerController/refreshZftzzxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#zftzzxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(holder,$("#zftzzxxDiv div").eq(i));
		$("#zftzzxxDiv [name$='INVESTOR_COUNTRY']").attr("disabled",true); 
	});
}
/**
 * 删除中方投资者信息
 */
function delZftzzxxDiv(o){
	$(o).closest("div").remove();
	setJwtzzInvestment();
} 


/**
 * 获取中方投资者信息
 */
function getZftzzxxJson(){
	var zftzzxxArray = [];
	$("#zftzzxxDiv div").each(function(i){
		var INVESTOR_TYPE = $(this).find("[name$='INVESTOR_TYPE']").val();//投资者类型
		var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//中方投资者名称
		var INVESTOR_PHONE = $(this).find("[name$='INVESTOR_PHONE']").val();//中方投资者联系电话
		var DIRECTOR_NUM = $(this).find("[name$='DIRECTOR_NUM']").val();//委派董事人数		
		var INVESTOR_IDTYPE = $(this).find("[name$='INVESTOR_IDTYPE']").val();	//证件类型
		var INVESTOR_IDNO = $(this).find("[name$='INVESTOR_IDNO']").val();//证件号码
		var INVESTOR_ADDR = $(this).find("[name$='INVESTOR_ADDR']").val();// 地址
			
		var INVESTOR_COUNTRY = $(this).find("[name$='INVESTOR_COUNTRY']").val();//国别（地区）
		var INVESTOR_PROVINCE = $(this).find("[name$='INVESTOR_PROVINCE']").val();//中方投资者省/市
		var FUNDS_SOURCE = $(this).find("[name$='FUNDS_SOURCE']").val();//资金来源地
		
		var INVESTMENT_DOMESTIC_RMB = $(this).find("[name$='INVESTMENT_DOMESTIC_RMB']").val();//现金_境内人民币
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//实物
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		
		var CONTRIBUTION = $(this).find("[name$='CONTRIBUTION']").val();//认缴出资额
		var PROPORTION = $(this).find("[name$='PROPORTION']").val();//占注册资本比例
		var FOLDING_RMB = $(this).find("[name$='FOLDING_RMB']").val();//折人民币
		
		if(null!=INVESTOR_TYPE&&INVESTOR_TYPE!=""){
			var zftzzxx = {};
			zftzzxx.INVESTOR_TYPE = INVESTOR_TYPE;
			zftzzxx.INVESTOR_NAME = INVESTOR_NAME;
			zftzzxx.INVESTOR_PHONE = INVESTOR_PHONE;
			zftzzxx.DIRECTOR_NUM = DIRECTOR_NUM;
			zftzzxx.INVESTOR_IDTYPE = INVESTOR_IDTYPE;
			zftzzxx.INVESTOR_IDNO = INVESTOR_IDNO;
			zftzzxx.INVESTOR_ADDR = INVESTOR_ADDR;			
			
			zftzzxx.INVESTOR_COUNTRY = INVESTOR_COUNTRY;
			zftzzxx.INVESTOR_PROVINCE = INVESTOR_PROVINCE;
			zftzzxx.FUNDS_SOURCE = FUNDS_SOURCE;
			
			zftzzxx.INVESTMENT_DOMESTIC_RMB = INVESTMENT_DOMESTIC_RMB;
			zftzzxx.INVESTMENT_MATERIAL = INVESTMENT_MATERIAL;
			zftzzxx.INVESTMENT_TECHNOLOGY = INVESTMENT_TECHNOLOGY;
			zftzzxx.INVESTMENT_LAND = INVESTMENT_LAND;
			zftzzxx.INVESTMENT_STOCK = INVESTMENT_STOCK;
			zftzzxx.INVESTMENT_OTHER = INVESTMENT_OTHER;
			
			zftzzxx.CONTRIBUTION = CONTRIBUTION;
			zftzzxx.PROPORTION = PROPORTION;
			zftzzxx.FOLDING_RMB = FOLDING_RMB;
			zftzzxxArray.push(zftzzxx);		
		}	
		
	});
	if(zftzzxxArray.length>0){
		return JSON.stringify(zftzzxxArray);
	}else{
		return "";
	}
}
//初始化中方投资者信息
function setZftzzxx(holderJson){
	var holders = eval(holderJson);
	if(holders){				
		for(var i=0;i<holders.length;i++){			
			if(i>0){
				addInitZftzzxxDiv(holders[i],i);
			}else{				
				FlowUtil.initFormObjValue(holders[i],$("#zftzzxxDiv div").eq(i));
			}
		}
	}
}
/*************中方投资者信息JS结束****************/


/*************董事信息JS开始****************/
/**
 * 添加董事信息
 */
function addDsxxDiv(){	
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	var directorJob = '03'
	if(companySettype!='zwhz'){//中外合资
		directorJob = '30';
	}
	$.post("foreignControllerController/refreshDsxxDiv.do",{
		directorJob:directorJob
	}, function(responseText, status, xhr) {
		$("#dsxxDiv").append(responseText);
		$("[name$='DIRECTOR_APPOINTOR']").val($("[name='DIRECTOR_APPOINTOR']").val());
			
		$("[name$='DIRECTOR_GENERATION_MODE']").val('01');
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true); 
		
		$("[name$='DIRECTOR_JOB']").attr("disabled",true); 
	});
}
/**
 * 初始化时添加董事信息
 */
function addInitDsxxDiv(director,i){
	$.post("foreignControllerController/refreshDsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#dsxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(director,$("#dsxxDiv table").eq(i));
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true); 		
		$("[name$='DIRECTOR_JOB']").attr("disabled",true); 
	});
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
	$("#dsxxDiv table").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
		var DIRECTOR_COUNTRY = $(this).find("[name$='DIRECTOR_COUNTRY']").val();//国别
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		var DIRECTOR_GENERATION_MODE = $(this).find("[name$='DIRECTOR_GENERATION_MODE']").val();//产生方式
		var DIRECTOR_APPOINTOR = $(this).find("[name$='DIRECTOR_APPOINTOR']").val();// 任命方		
		var DIRECTOR_OFFICETERM = $(this).find("[name$='DIRECTOR_OFFICETERM']").val();// 任期
		if(null!=DIRECTOR_OFFICETERM&&DIRECTOR_OFFICETERM!=""){
			var dsxx = {};
			dsxx.DIRECTOR_NAME = DIRECTOR_NAME;
			dsxx.DIRECTOR_JOB = DIRECTOR_JOB;
			dsxx.DIRECTOR_COUNTRY = DIRECTOR_COUNTRY;
			dsxx.DIRECTOR_IDTYPE = DIRECTOR_IDTYPE;
			dsxx.DIRECTOR_IDNO = DIRECTOR_IDNO;
			dsxx.DIRECTOR_GENERATION_MODE = DIRECTOR_GENERATION_MODE;
			dsxx.DIRECTOR_APPOINTOR = DIRECTOR_APPOINTOR;
			dsxx.DIRECTOR_OFFICETERM = DIRECTOR_OFFICETERM;			
			dsxxArray.push(dsxx);
		}	
	});
	if(dsxxArray.length>0){
		return JSON.stringify(dsxxArray);
	}else{
		return "";
	}
}
//初始化董事信息
function setDsxx(directorJson,COMPANY_SETTYPE){
	var directors = eval(directorJson);
	if(directors){				
		for(var i=0;i<directors.length;i++){			
			if(i>2&&COMPANY_SETTYPE=='zwhz'){
				addInitDsxxDiv(directors[i],i);
			}else if(i>0&&COMPANY_SETTYPE!='zwhz'){
				addInitDsxxDiv(directors[i],i);
			}else{				
				FlowUtil.initFormObjValue(directors[i],$("#dsxxDiv table").eq(i));
			}
		}
	}
}
/*************董事信息JS结束****************/

/*************监事信息JS开始****************/
/**
 * 添加监事信息
 */
function addJsxxDiv(){
	$.post("foreignControllerController/refreshJsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jsxxDiv").append(responseText);
		$("[name$='SUPERVISOR_APPOINTOR']").val($("[name='SUPERVISOR_APPOINTOR']").val());
		
		$("[name$='SUPERVISOR_JOB']").attr("disabled",true); 
	});
}
/**
 * 初始化时添加监事信息
 */
function addInitJsxxDiv(supervisor,i){
	$.post("foreignControllerController/refreshJsxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jsxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(supervisor,$("#jsxxDiv div").eq(i));
		$("[name$='SUPERVISOR_JOB']").attr("disabled",true); 
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
			jsxx.SUPERVISOR_IDTYPE = SUPERVISOR_IDTYPE;
			jsxx.SUPERVISOR_IDNO = SUPERVISOR_IDNO;
			jsxx.SUPERVISOR_GENERATION_MODE = SUPERVISOR_GENERATION_MODE;
			jsxx.SUPERVISOR_APPOINTOR = SUPERVISOR_APPOINTOR;
			jsxx.SUPERVISOR_OFFICETERM = SUPERVISOR_OFFICETERM;	
			jsxxArray.push(jsxx);
		}
	});
	if(jsxxArray.length>0){
		return JSON.stringify(jsxxArray);
	}else{
		return "";
	}
}
//初始化监事信息
function setJsxx(supervisorJson){
	var supervisors = eval(supervisorJson);
	if(supervisors){				
		for(var i=0;i<supervisors.length;i++){			
			if(i>0){
				addInitJsxxDiv(supervisors[i],i);
			}else{				
				FlowUtil.initFormObjValue(supervisors[i],$("#jsxxDiv div").eq(i));
			}
		}
	}
}
/*************监事信息JS结束****************/

/*************经理信息JS开始****************/
/**
 * 添加经理信息
 */
function addJlxxDiv(){
	$.post("foreignControllerController/refreshJlxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jlxxDiv").append(responseText);
		$("[name$='MANAGER_APPOINTOR']").val($("[name='MANAGER_APPOINTOR']").val());		
		$("[name$='MANAGER_JOB']").attr("disabled",true); 
	});
}
/**
 * 初始化时添加监事信息
 */
function addInitJlxxDiv(manager,i){
	$.post("foreignControllerController/refreshJlxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jlxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(manager,$("#jlxxDiv div").eq(i));
		$("[name$='MANAGER_JOB']").attr("disabled",true); 
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
			jlxxArray.push(jlxx);			
		}
		
	});
	if(jlxxArray.length>0){
		return JSON.stringify(jlxxArray);
	}else{
		return "";
	}
}
//初始化经理信息
function setJlxx(managerJson){
	var managers = eval(managerJson);
	if(managers){				
		for(var i=0;i<managers.length;i++){			
			if(i>0){
				addInitJlxxDiv(managers[i],i);
			}else{				
				FlowUtil.initFormObjValue(managers[i],$("#jlxxDiv div").eq(i));
			}
		}
	}
}
/*************经理信息JS结束****************/



/*************法定代表人JS开始****************/
function setLegalValue(val){
	//$("#LEGAL_IDTYPE").attr("disabled",false);
	var isok = false;//标识位
	$("#dsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		if(DIRECTOR_JOB==val){
			$("input[name='LEGAL_NAME']").val(DIRECTOR_NAME);
			setZjValidate(DIRECTOR_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(DIRECTOR_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(DIRECTOR_IDNO);
			isok = true;
			return;
		}
	});
	$("#jsxxDiv div").each(function(i){
		var SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
		var SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
		var SUPERVISOR_IDTYPE = $(this).find("[name$='SUPERVISOR_IDTYPE']").val();//证件类型		
		var SUPERVISOR_IDNO = $(this).find("[name$='SUPERVISOR_IDNO']").val();	//证件号码
		if(SUPERVISOR_JOB==val){			
			$("input[name='LEGAL_NAME']").val(SUPERVISOR_NAME);
			setZjValidate(SUPERVISOR_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(SUPERVISOR_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(SUPERVISOR_IDNO);
			isok = true;
			return;
		}
	});
	$("#jlxxDiv div").each(function(i){
		var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
		var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
		var MANAGER_IDTYPE = $(this).find("[name$='MANAGER_IDTYPE']").val();//证件类型		
		var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();	//证件号码
		if(MANAGER_JOB==val){
			$("input[name='LEGAL_NAME']").val(MANAGER_NAME);	
			setZjValidate(MANAGER_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(MANAGER_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(MANAGER_IDNO);
			isok = true;		
			return;
		}
	});
	if(!isok){		
		$("input[name='LEGAL_NAME']").val('');	
		$("#LEGAL_IDTYPE").val('');
		$("input[name='LEGAL_IDNO']").val('');
	}
	//$("#LEGAL_IDTYPE").attr("disabled",true);
}
/*************法定代表人JS结束****************/

/*************外商投资企业法律文件送达授权委托书基本信息JS开始****************/
/**
 * 添加外商投资企业法律文件送达授权委托书基本信息
 */
function addAttorneyDiv(){
	$.post("foreignControllerController/refreshAttorneyDiv.do",{
	}, function(responseText, status, xhr) {
		$("#attorneyDiv").append(responseText); 
	});
}
/**
 * 初始化时添加外商投资企业法律文件送达授权委托书基本信息
 */
function addInitAttorneyDiv(supervisor,i){
	$.post("foreignControllerController/refreshAttorneyDiv.do",{
	}, function(responseText, status, xhr) {
		$("#attorneyDiv").append(responseText);	
		FlowUtil.initFormObjValue(supervisor,$("#attorneyDiv div").eq(i));
	});
}
/**
 * 删除外商投资企业法律文件送达授权委托书基本信息
 */
function delAttorneyDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取外商投资企业法律文件送达授权委托书基本信息
 */
function getAttorneyJson(){
	var attorneyArray = [];
	$("#attorneyDiv div").each(function(i){
		var AUTHORIZER = $(this).find("[name$='AUTHORIZER']").val();//授权人/委托方
		var DELEGATEE = $(this).find("[name$='DELEGATEE']").val();//被授权人
		var DELEGATEE_CONTRACTNAME = $(this).find("[name$='DELEGATEE_CONTRACTNAME']").val();//被授权人联系人名称/姓名
		var DELEGATEE_EMAIL = $(this).find("[name$='DELEGATEE_EMAIL']").val();//电子邮箱		
		var DELEGATEE_MOBILE = $(this).find("[name$='DELEGATEE_MOBILE']").val();	//移动电话
		var DELEGATEE_FIXEDLINE = $(this).find("[name$='DELEGATEE_FIXEDLINE']").val();//固定电话
		var DELEGATEE_ADDR = $(this).find("[name$='DELEGATEE_ADDR']").val();// 被授权人地址		
		var DELEGATEE_POSTCODE = $(this).find("[name$='DELEGATEE_POSTCODE']").val();// 邮政编码		
		if(null!=AUTHORIZER&&AUTHORIZER!=""){
			var attorney = {};
			attorney.AUTHORIZER = AUTHORIZER;
			attorney.DELEGATEE = DELEGATEE;
			attorney.DELEGATEE_CONTRACTNAME = DELEGATEE_CONTRACTNAME;
			attorney.DELEGATEE_EMAIL = DELEGATEE_EMAIL;
			attorney.DELEGATEE_MOBILE = DELEGATEE_MOBILE;
			attorney.DELEGATEE_FIXEDLINE = DELEGATEE_FIXEDLINE;
			attorney.DELEGATEE_ADDR = DELEGATEE_ADDR;
			attorney.DELEGATEE_POSTCODE = DELEGATEE_POSTCODE;
			attorneyArray.push(attorney);	
		}		
	});
	if(attorneyArray.length>0){
		return JSON.stringify(attorneyArray);
	}else{
		return "";
	}
}
//初始化外商投资企业法律文件送达授权委托书基本信息
function setAttorney(supervisorJson){
	var supervisors = eval(supervisorJson);
	if(supervisors){				
		for(var i=0;i<supervisors.length;i++){			
			if(i>0){
				addInitAttorneyDiv(supervisors[i],i);
			}else{				
				FlowUtil.initFormObjValue(supervisors[i],$("#attorneyDiv div").eq(i));
			}
		}
	}
}
/*************外商投资企业法律文件送达授权委托书基本信息JS结束****************/


/** 
* 选择证件类型给证件号码加验证
**/
function setWzqyZjValidate(zjlx,id,name){
	if(zjlx=="SF"){
		$("#"+id+" input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("#"+id+" input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}


/**
 * 外资投资者资金计算
 */
function setJwtzzInvestment(){
	var totla = 0;
	var jwTotla = 0;
	var cnTotla = 0;
	$("#jwtzzxxDiv div").each(function(i){		
		var INVESTMENT_ABROAD_EXCHANGE = $(this).find("[name$='INVESTMENT_ABROAD_EXCHANGE']").val();//现金_境外现汇
		var INVESTMENT_ABROAD_RMB = $(this).find("[name$='INVESTMENT_ABROAD_RMB']").val();//现金_境外人民币
		var INVESTMENT_DOMESTIC_RMB = $(this).find("[name$='INVESTMENT_DOMESTIC_RMB']").val();//现金_境内人民币
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//INVESTMENT_MATERIAL
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		var CONTRIBUTION = Number(INVESTMENT_ABROAD_EXCHANGE)+Number(INVESTMENT_ABROAD_RMB)
		+Number(INVESTMENT_DOMESTIC_RMB)+Number(INVESTMENT_MATERIAL)
		+Number(INVESTMENT_TECHNOLOGY)+Number(INVESTMENT_LAND)
		+Number(INVESTMENT_STOCK)+Number(INVESTMENT_OTHER);	
		if(Number(CONTRIBUTION)>0){	
			totla+=Number(CONTRIBUTION);
			jwTotla += Number(CONTRIBUTION);
			if(Number(CONTRIBUTION)>0){
				$(this).find("[name$='CONTRIBUTION']").val(CONTRIBUTION.toFixed(6));
				$(this).find("[name$='FOLDING_RMB']").val(CONTRIBUTION.toFixed(6));
			}else{
				$(this).find("[name$='CONTRIBUTION']").val('');
				$(this).find("[name$='FOLDING_RMB']").val('');
				$(this).find("[name$='PROPORTION']").val('');
			}
		}
	});
	$("#zftzzxxDiv div").each(function(i){		
		var INVESTMENT_DOMESTIC_RMB = $(this).find("[name$='INVESTMENT_DOMESTIC_RMB']").val();//现金_境内人民币
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//INVESTMENT_MATERIAL
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		var CONTRIBUTION = Number(INVESTMENT_DOMESTIC_RMB)+Number(INVESTMENT_MATERIAL)
		+Number(INVESTMENT_TECHNOLOGY)+Number(INVESTMENT_LAND)
		+Number(INVESTMENT_STOCK)+Number(INVESTMENT_OTHER);		
		if(Number(CONTRIBUTION)>0){	
			totla+=Number(CONTRIBUTION);
			cnTotla+=Number(CONTRIBUTION);
			if(Number(CONTRIBUTION)>0){
				$(this).find("[name$='CONTRIBUTION']").val(CONTRIBUTION.toFixed(6));
				$(this).find("[name$='FOLDING_RMB']").val(CONTRIBUTION.toFixed(6));
			}else{
				$(this).find("[name$='CONTRIBUTION']").val('');
				$(this).find("[name$='FOLDING_RMB']").val('');
				$(this).find("[name$='PROPORTION']").val('');
			}
		}
	});
	if(Number(totla)>0){
		$("input[name='REGISTERED_CAPITAL']").val(totla.toFixed(6));	
		if(Number(jwTotla)>0){	
			$("input[name='FOREIGN_CAPITAL']").val(jwTotla.toFixed(6));
			var FOREIGN_RATIO = Number(jwTotla)/(Number(totla))*100;
			$("input[name='FOREIGN_RATIO']").val(FOREIGN_RATIO.toFixed(2)+"%");
		}
		if(Number(cnTotla)>0){	
			$("input[name='CHINA_CAPITAL']").val(cnTotla.toFixed(6));
			var CHINA_RATIO = Number(cnTotla)/(Number(totla))*100;
			$("input[name='CHINA_RATIO']").val(CHINA_RATIO.toFixed(2)+"%");
		}

		$("#jwtzzxxDiv div").each(function(i){
			var rjcze = $(this).find("[name$='CONTRIBUTION']").val();	
			var a = Number(rjcze)/(Number(totla))*100;
			$(this).find("[name$='PROPORTION']").val(a.toFixed(2)+"%");
		});
		$("#zftzzxxDiv div").each(function(i){
			var rjcze = $(this).find("[name$='CONTRIBUTION']").val();	
			var a = Number(rjcze)/(Number(totla))*100;
			$(this).find("[name$='PROPORTION']").val(a.toFixed(2)+"%");
		});
	}else{		
		$("input[name='REGISTERED_CAPITAL']").val('');		
		$("input[name='FOREIGN_CAPITAL']").val('');
		$("input[name='CHINA_CAPITAL']").val('');	
		$("input[name='FOREIGN_RATIO']").val('');
		$("input[name='CHINA_RATIO']").val('');
	}
}

/*************银行开户及其他信息JS开始****************/
/**
 * 添加银行账号信息及委托扣款协议信息
 */
function addYhzhDiv(){
	$.post("domesticControllerController/refreshYhzhDiv.do",{
	}, function(responseText, status, xhr) {
		$("#YhzhTable").append(responseText);
		$("#YhzhTable tbody:last").find("[id='DIVISION']").combotree({
           url: "dicTypeController/loadSelectTree.do?parentCode=XZQHDM35",
           valueField: "text",
           textField: "text",
           lines: true,
           required: true,
           onChange: function (node) {					                                

           }
            
    	});
    	$("#YhzhTable tbody:last").find("[id='DIVISION']").parent().find("span input").attr("placeholder","请选择行政区划");
    	
    	var companyName = $("#COMPANY_NAME").val();
    	$("#YhzhTable tbody:last").find("[name='ACCOUNT_NAME']").val(companyName);
	});
}
/**
 * 删除银行账号信息及委托扣款协议信息
 */
function delYhzhDiv(o){
	$(o).closest("tbody").remove();
}

/**
 * 获取银行账号信息及委托扣款协议信息
 */
function getaAccountAndAgreementJson(){
	var accountAndAgreementArray = [];
	$("#YhzhTable tbody").each(function(i){
		var ACCOUNT_NATURE = $(this).find("[name$='ACCOUNT_NATURE']").val();
		var ACCOUNT_CERTNO = $(this).find("[name$='ACCOUNT_CERTNO']").val();
		var RELEASE_DATE = $(this).find("[name$='RELEASE_DATE']").val();
		var DIVISION = $(this).find("[name$='DIVISION']").val();
		var BANK_CATEGORY = $(this).find("[name$='BANK_CATEGORY']").val();
		var DEPOSIT_BANK = $(this).find("[name$='DEPOSIT_BANK']").val();
		var ACCOUNT_NAME = $(this).find("[name$='ACCOUNT_NAME']").val();
        var ACCOUNT_NO = $(this).find("[name$='ACCOUNT_NO']").val();
		var ACCOUNT_CURRENCY = $(this).find("[name$='ACCOUNT_CURRENCY']").val();
		var ACCOUNT_IDENTIFY = $(this).find("[name$='ACCOUNT_IDENTIFY']").val();
		var ACCOUNTOPEN_DATE = $(this).find("[name$='ACCOUNTOPEN_DATE']").val();
		var IS_ENTRUST = $(this).find("[name$='IS_ENTRUST']").val();		
		
		var accountAndAgreement = {};
		accountAndAgreement.ACCOUNT_NATURE = ACCOUNT_NATURE;
		accountAndAgreement.ACCOUNT_CERTNO = ACCOUNT_CERTNO;
		accountAndAgreement.RELEASE_DATE = RELEASE_DATE;
		accountAndAgreement.DIVISION = DIVISION;
		accountAndAgreement.BANK_CATEGORY = BANK_CATEGORY;
		accountAndAgreement.DEPOSIT_BANK = DEPOSIT_BANK;
		accountAndAgreement.ACCOUNT_NAME = ACCOUNT_NAME;
		accountAndAgreement.ACCOUNT_NO = ACCOUNT_NO;
		accountAndAgreement.ACCOUNT_CURRENCY = ACCOUNT_CURRENCY;
		accountAndAgreement.ACCOUNT_IDENTIFY = ACCOUNT_IDENTIFY;
		accountAndAgreement.ACCOUNTOPEN_DATE = ACCOUNTOPEN_DATE;
		accountAndAgreement.IS_ENTRUST = IS_ENTRUST;
		accountAndAgreementArray.push(accountAndAgreement);
		
	});
	if(accountAndAgreementArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(accountAndAgreementArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
/**
 * 添加发票核定及申领信息
 */
function addInvoiceApplyDiv(){
	$.post("domesticControllerController/refreshInvoiceApplyDiv.do",{
	}, function(responseText, status, xhr) {
		$("#invoiceapplyTable").append(responseText);
		
	});
}
/**
 * 删除发票核定及申领信息
 */
function delInvoiceApplyDiv(o){
	$(o).closest("tbody").remove();
}
/**
 * 获取发票核定及申领信息
 */
function getInvoiceApllyJson(){
	var invoiceApplyArray = [];
	$("#invoiceapplyTable tbody").each(function(i){
		var INVOICE_TYPE = $(this).find("[name$='INVOICE_TYPE']").val();
		var BILLING_LIMIT = $(this).find("[name$='BILLING_LIMIT']").val();
		var APPLY_NUM = $(this).find("[name$='APPLY_NUM']").val();	
		
		var invoiceApply = {};
		invoiceApply.INVOICE_TYPE = INVOICE_TYPE;
		invoiceApply.BILLING_LIMIT = BILLING_LIMIT;
		invoiceApply.APPLY_NUM = APPLY_NUM;
		invoiceApplyArray.push(invoiceApply);
		
	});
	if(invoiceApplyArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(invoiceApplyArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}

function recivewayselect(val){
	if(val=='邮递'){
		$("#postselect").attr("style","");
	}else{
		$("#postselect").attr("style","display:none;");
	}
}

function setFundsZzygs(){
	var FUNDS_ZZYGRS = $("[name='FUNDS_ZZYGRS']").val();
	$("[name='FUNDS_GJJJCRS']").val(FUNDS_ZZYGRS);
	$("[name='FUNDS_GJJBTJCRS']").val(FUNDS_ZZYGRS);
}

function calYjcze(){
	var FUNDS_GJJDWJCBL = $("input[name='FUNDS_GJJDWJCBL']").val();
	var FUNDS_GJJGRJCBL = $("input[name='FUNDS_GJJGRJCBL']").val();
	var FUNDS_GJJGJZE = $("input[name='FUNDS_GJJGJZE']").val();
	if (FUNDS_GJJDWJCBL && FUNDS_GJJGRJCBL && FUNDS_GJJGJZE) {
		var persent = parseFloat(FUNDS_GJJDWJCBL) / 100 + parseFloat(FUNDS_GJJGRJCBL) / 100;
		var count = parseFloat(FUNDS_GJJGJZE) * persent
		$("input[name='FUNDS_GJJYJCZE']").val(count.toFixed(2));
	}
}

function changeGrjcbl(val){
	if (val) {
		$("input[name='FUNDS_GJJGRJCBL']").val(val);
	}
}
/*************银行开户及其他信息JS结束****************/


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
* 事项选择器
**/
function showItemSelector(){	
	var induIds = $("input[name='ITEMCODES']").val();
	parent.$.dialog.open("domesticControllerController/itemSelector.do?allowCount=0&noAuth=true&induIds="+induIds, {
		title : "事项选择器",
		width:"1000px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectRelatedItemInfo = art.dialog.data("selectRelatedItemInfo");
			if(selectRelatedItemInfo){
				$("input[name='ITEMCODES']").val(selectRelatedItemInfo.itemCodes);
				$("[name='ITEMNAMES']").val(selectRelatedItemInfo.itemNames);
				art.dialog.removeData("selectRelatedItemInfo");
				addMaterDiv();
			}
		}
	});
}

/**
 * 添加材料信息
 */
function addMaterDiv(){
	var itemCodes = $("input[name='ITEMCODES']").val();
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		var exeId = "";
		var busRecordId = "";
		var busTableName = "";
		if(eflowObj.busRecord){	
			exeId = eflowObj.busRecord.EXE_ID;
			busRecordId = eflowObj.EFLOW_BUSRECORDID;
			busTableName = eflowObj.EFLOW_BUSTABLENAME;
		}			
		$.post("domesticControllerController/refreshMaterDiv.do",{
			exeId:exeId,
			itemCodes:itemCodes,			
			busRecordId:busRecordId,
			busTableName:busTableName
		}, function(responseText, status, xhr) {
			$("#mater_div").html(responseText);
			
			//初始化材料列表
			AppUtil.initNetUploadMaters({
				busTableName:"T_COMMERCIAL_SOLELYINVEST"
			});
		});
		
	}
}
function delItemMater(){
	$("input[name='ITEMCODES']").val('');
	$("[name='ITEMNAMES']").val('');
	$("#mater_div").html('');
	//haveOnLine.pop();
	haveOnLine = [];
}

function setOnlineMater(){
	var haveOnLine = $("input[name='haveOnline']").val();
	if(haveOnLine!=null&&haveOnLine!=""&&haveOnLine!=undefined){
		haveOnLine = haveOnLine.substring(0,haveOnLine.length-1);
		var haveOnLines = haveOnLine.split(",");
		if(haveOnLines.length>0){
			var flag = true;
			for(var i=0;i<haveOnLines.length;i++){
				var forName = haveOnLines[i];
				if(store[forName]==''||store[forName]==null){
					alert("1+N证合一中的在线编辑材料未完成填写，请检查!");
					flag = false;
					break;
				}
			}
			var onlineMater = [];
			onlineMater.push(store);
			var onlineMaterIdJson;
			if(onlineMater.length>0){
				var reg = /[\(]/g,reg2 = /[\)]/g;
				onlineMaterIdJson = JSON.stringify(onlineMater).replace(reg,"（").replace(reg2,"）");
			}else{
				onlineMaterIdJson = "";
			}
			$("input[name='onlineMaterIdJson']").val(onlineMaterIdJson);
			if(!flag){
				return false;
			}else{
				return true;
			}
		}
	}
	return true;
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

$(function() {
	//初始化材料信息
	addMaterDiv();
});

