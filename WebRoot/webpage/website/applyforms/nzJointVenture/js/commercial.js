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
			//初始化企业基本信息
			$("#COMPANY_FORM input[name='COMPANY_TYPE']").val(eflowObj.busRecord.COMPANY_TYPE);
			$("#COMPANY_FORM input[name='COMPANY_TYPECODE']").val(eflowObj.busRecord.COMPANY_TYPECODE);
			$("#COMPANY_FORM input[name='COMPANY_SETTYPE']").val(eflowObj.busRecord.COMPANY_SETTYPE);
			$("#COMPANY_FORM input[name='COMPANY_SETNATURE']").val(eflowObj.busRecord.COMPANY_SETNATURE);
			//初始化资金信息				
			$("#CAPITAL_FORM input[name='PAYMENT_PERIOD']").val(eflowObj.busRecord.PAYMENT_PERIOD);
			$("#CAPITAL_FORM [name='CURRENCY']").val(eflowObj.busRecord.CURRENCY);
			setCurrency(eflowObj.busRecord.CURRENCY);
			//初始化法定代表人信息						
			//FlowUtil.initFormObjValue(eflowObj.busRecord,$("#LEGAL_FORM"));		
			//初始化印章信息						
			//FlowUtil.initFormObjValue(eflowObj.busRecord,$("#SEAL_FORM"));				
			//初始化其他信息						
			//FlowUtil.initFormObjValue(eflowObj.busRecord,$("#OTHER_FORM"));	
			$("#OTHER_FORM input[name='REGISTER_AUTHORITY']").val(eflowObj.busRecord.REGISTER_AUTHORITY);
			$("#OTHER_FORM input[name='APPROVAL_AUTHORITY']").val(eflowObj.busRecord.APPROVAL_AUTHORITY);
			$("#OTHER_FORM input[name='AUTHORITY_CODE']").val(eflowObj.busRecord.AUTHORITY_CODE);
			$("#OTHER_FORM input[name='LICENSE_NUM']").val(eflowObj.busRecord.LICENSE_NUM);
			$("#OTHER_FORM input[name='CERTIFICATE_NUM']").val(eflowObj.busRecord.CERTIFICATE_NUM);
			$("#OTHER_FORM input[name='FILL_DATE']").val(eflowObj.busRecord.FILL_DATE);
			
			//初始化申报人信息						
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#USERINFO_FORM"));
			
			
			//初始化经营期限
			setJyqx(eflowObj.busRecord.OPRRATE_TERM_TYPE);
			//初始化是否已通过名称自主选用
			setPreapprovalPass(eflowObj.busRecord.IS_PREAPPROVAL_PASS);
			//初始化是否另设生产经营地址
			setSflsscjydz(eflowObj.busRecord.IS_OTHER_PLACE);
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
            //初始化公积金登记信息
			initGjjdjxxForm(eflowObj.busRecord.FUNDS_GJJLX);
			
			//初始化是否同时进行员工参保(社保)
			initSfCb(eflowObj.busRecord.IS_EMPLOY_INSU);
			//初始化是否同时进行员工参保(医保)
			initYbCb(eflowObj.busRecord.IS_EMPLOY_INMEDICAL);

			//判断是否选择企业类型
			if(eflowObj.busRecord.COMPANY_TYPECODE==null||eflowObj.busRecord.COMPANY_TYPECODE==''){
				window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
			}
			$("#companyTypeFont").html(eflowObj.busRecord.COMPANY_TYPE);
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

			//初始化1+N证合一已编辑表单化附件ID记录
			$.post("domesticControllerController/checkHaveOnlineMater.do",{
				exeId:eflowObj.EFLOW_EXEID,
				itemCodes:eflowObj.busRecord.ITEMCODES
			}, function(responseText, status, xhr) {
				resultJson = $.parseJSON(responseText);
				var onlineForm = resultJson.jsonString;
				if( null != onlineForm && ''!=onlineForm ){	
					$.each(JSON.parse(onlineForm), function(idx, obj) {
						store[obj.formName] = obj.recordId;
					});				
				}
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
			//加载执行事务合伙人
			setPartnershipName();
			
		}else{
			//判断是否选择企业类型
			var COMPANY_TYPECODE= $("input[name='COMPANY_TYPECODE']").val();
			if(COMPANY_TYPECODE==null||COMPANY_TYPECODE==''){
				window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
			}

			var ritemCode = $("input[name='RITEMCODES']").val();
			if(ritemCode==null||ritemCode==""){
				$("#applyItemMater").attr("style","display:none;");
			}else{
				$("input[name='ITEMCODES']").val($("input[name='RITEMCODES']").val());
				$("textarea[name='ITEMNAMES']").val($("input[name='RITEMNAMES']").val());
			}
			var SSSBLX = $("input[name='SSSBLX']").val();
			selectMainIndustry();
			if(SSSBLX!='1') {
				/*art.dialog.confirm("请确认是否已通过名称登记，已通过请点击 <p>确定，未通过请点击此处前往<a href='http://61.154.11.191/usermana/login.do?method=index' target='_blank'><span style='color: red;text-decoration:underline'>红盾网</span></a>办理!</p>", function () {
				}, function () {
					window.top.location.href = __ctxPath + "/webSiteController.do?zzhywssb";
				});*/
				/*art.dialog.confirm("您是否需要申请银行开户?", function () {
					$("input[name='IS_ACCOUNT_OPEN']").attr("checked", "checked");
					$("#bankInfo").attr("style", "");
					$("input[name='BANK_TYPE']").attr("disabled", false);
					$("input[name='CONTROLLER']").attr("disabled", false);

					$("#legalPersonName").addClass(" validate[required]");
					$("#legalPersonIDType").addClass(" validate[required]");
					$("#legalPersonIDNo").addClass(" validate[required]");
				});*/
			}
		}
		
		setcdzfs();
	}	
	
	//点击是否已通过名称自主选用触发事件
	$("input[name='IS_PREAPPROVAL_PASS']").on("click", function(event) {
		setPreapprovalPass(this.value);
	});
	
	//经营期限
	$("input[name='OPRRATE_TERM_TYPE']").on("click", function(event) {
		setJyqx(this.value);
	});
	//是否另设生产经营地址
	$("input[name='IS_OTHER_PLACE']").on("click", function(event) {
		setSflsscjydz(this.value);
	});
	//注册地址失去焦点事件
	$("input[name='REGISTER_ADDR']").on('blur', function(event) { 
		isEqualRegisterAddr();
	});
	//生产经营地址失去焦点事件
	$("input[name='BUSINESS_PLACE']").on('blur', function(event) { 
		isEqualRegisterAddr();
	});
	//承担责任方式
	$("select[name='DUTY_MODE']").on("click", function(event) {
		setsfzxswhhr(this.value);
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
	//----------------印章信息结束-----------------------
	
	
	//币种不可编辑 
	$("[name='CURRENCY']").attr("disabled",true); 
	$("[name$='CURRENCY_1']").attr("disabled",true);

	//银行开户信息
	$("input[name='IS_ACCOUNT_OPEN']").click(function(){
		var Value = $(this).val();
		if(Value=='1'){
			$("#bankInfo").attr("style","");
			$("input[name='BANK_TYPE']").attr("disabled",false);
			$("input[name='CONTROLLER']").attr("disabled",false);
			
			$("#legalPersonName").addClass(" validate[required]");
			$("#legalPersonIDType").addClass(" validate[required]");
			$("#legalPersonIDNo").addClass(" validate[required]");
			
		}else{
			$("#bankInfo").attr("style","display:none;");
			$("#bankInfo input[type='text']").val("");
			$("#bankInfo textarea").val("");
			$("input[name='BANK_TYPE']").removeAttr("checked");
			
			$("#pdblist").attr("style","display:none;");
			$("#ccblist").attr("style","display:none;");
			$("#boclist").attr("style","display:none;");
			$("#abclist").attr("style","display:none;");
			$("#rcblist").attr("style","display:none;");
			$("#templateLoad").attr("style","display:none;");
			$("input[name='BANK_TYPE']").attr("disabled",true);
			$("input[name='CONTROLLER']").attr("disabled",true);
			
			$("#legalPersonName").removeClass(" validate[required]");
			$("#legalPersonIDType").removeClass(" validate[required]");
			$("#legalPersonIDNo").removeClass(" validate[required]");
		}
	});
	
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
	$("input[name='REGISTER_ADDR']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("input[name='DEPOSITOR_ADDR']").val($(this).val());
		}
	}); 
});


//设置经营期限
function setJyqx(value){
	if (value === "1") {			
		$("input[name='BUSSINESS_YEARS']").attr("disabled",false);
		$("input[name='BUSSINESS_YEARS_START']").attr("disabled",false); 
		$("input[name='BUSSINESS_YEARS_END']").attr("disabled",false);
		$("input[name='BUSSINESS_YEARS_START']").addClass("Wdate validate[required]");
		$("input[name='BUSSINESS_YEARS_END']").addClass("Wdate validate[required]"); 
	} else {			
		$("input[name='BUSSINESS_YEARS']").val("");
		$("input[name='BUSSINESS_YEARS']").attr("disabled",true); 
		$("input[name='BUSSINESS_YEARS_START']").val("");
		$("input[name='BUSSINESS_YEARS_END']").val("");
		$("input[name='BUSSINESS_YEARS_START']").attr("disabled",true); 
		$("input[name='BUSSINESS_YEARS_END']").attr("disabled",true); 
		$("input[name='BUSSINESS_YEARS_START']").removeClass("Wdate validate[required]");
		$("input[name='BUSSINESS_YEARS_END']").removeClass("Wdate validate[required]");
	}
}
//设置是否已通过名称自主选用
function setPreapprovalPass(value){
	if (value === "1") {			
		$("input[name='PREAPPROVAL_NUM']").attr("disabled",false);
		$("input[name='PREAPPROVAL_NUM']").addClass(" validate[required]");
	} else {			
		$("input[name='PREAPPROVAL_NUM']").attr("disabled",true); 
		$("input[name='PREAPPROVAL_NUM']").removeClass(" validate[required]");
		$("input[name='PREAPPROVAL_NUM']").val('');
	}
}
//设置是否另设生产经营地址
function setSflsscjydz(value){
	if (value === "1") {
		$("#bussinessAddrTable").attr("style","margin-top: -1px;");
		AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSSINESS_ADDR;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","1");
		$("input[name='BUSINESS_PLACE']").attr("disabled",false);
		$("input[name='PLACE_OWNER']").attr("disabled",false);
		$("input[name='PLACE_TEL']").attr("disabled",false);
	} else {
		$("#bussinessAddrTable").attr("style","display:none;");
		AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSSINESS_ADDR;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","-1");
		$("input[name='BUSINESS_PLACE']").val("");
		$("input[name='BUSINESS_PLACE']").attr("disabled",true); 
		$("input[name='PLACE_OWNER']").val("");
		$("input[name='PLACE_OWNER']").attr("disabled",true); 
		$("input[name='PLACE_TEL']").val("");
		$("input[name='PLACE_TEL']").attr("disabled",true); 
	}
}
//判断生产经营地址与注册地址是否一致
function isEqualRegisterAddr(){
	var REGISTER_ADDR = $("input[name='REGISTER_ADDR']").val();
	var BUSINESS_PLACE = $("input[name='BUSINESS_PLACE']").val();
	if(REGISTER_ADDR && BUSINESS_PLACE && REGISTER_ADDR==BUSINESS_PLACE){		
		art.dialog({
			content : "当生产经营地址与注册地址一致，生产经营地址无需填写！",
			icon : "warning",
			ok : true
		});
		return;
	}
	
}
function isEqualShahareholderName(index){
	var SHAREHOLDER_NAME = $("input[name='"+index+"SHAREHOLDER_NAME']").val();
	var LICENCE_APPOINT_NAME = $("input[name='"+index+"LICENCE_APPOINT_NAME']").val();
	if(SHAREHOLDER_NAME && LICENCE_APPOINT_NAME && SHAREHOLDER_NAME==LICENCE_APPOINT_NAME){		
		art.dialog({
			content : "委派人不能与合伙人相同！",
			icon : "warning",
			ok : true
		});
		return;
	}
	
}


function isEqualToCash(value){
	if(value && value>=10000){				
		art.dialog({
			content : "出资单位为“万元”",
			icon : "warning",
			ok : true
		});
		return;
	}
}
function isEqualToNoCash(value){
	if(value && value>0){				
		art.dialog({
			content : "以非货币财产出资，应当评估作价并填写评估价。",
			icon : "warning",
			ok : true
		});
		return;
	}
}
function isEqualToStock(value){
	if(value && value>0){				
		art.dialog({
			content : "以股权出资的，该股权应当权属清楚、权能完整、依法可以转让，<br/>已被设立质权不得以股权方式出资。",
			icon : "warning",
			ok : true
		});
		return;
	}
}
function isEqualToSjcze(value){
	if(value && value>0){				
		art.dialog({
			content : "请确认注册资本是否全部到位，若无，请填写0。",
			icon : "warning",
			ok : true
		});
		return;
	}
}
function handleDate(){
	var newDate = new Date();
	var PAYMENT_PERIOD = $("input[name='PAYMENT_PERIOD']").val();　　
	var start = PAYMENT_PERIOD.replace(new RegExp(/-/gm),"/");
	var startData = new Date(PAYMENT_PERIOD);
	if (startData<=newDate) {			
		art.dialog({
			content : "请确认注册资本是否全部到位，出资时间应在经营期限内。",
			icon : "warning",
			ok : true
		});
		return;
	}
}
//设置承担责任方式
function setsfzxswhhr(value,name){
	if (value === "有限责任") {			
		$("select[name='"+name+"']").attr("disabled","disabled").css("background-color","#EEEEEE;");
		$("select[name='"+name+"']").val("0").trigger("change");
	} else {			
		$("select[name='"+name+"']").removeAttr("disabled","true").css("background-color","#fff;");
		$("select[name='"+name+"']").val('');
	}
	setPartnershipName();
}
function setcdzfs(){
	
	var COMPANY_SETNATURE = $("#COMPANY_FORM input[name='COMPANY_SETNATURE']").val();
	if(COMPANY_SETNATURE=="pthhqy"){
		$("select[name$='DUTY_MODE']").each(function(i){
			//console.log(1);
			$(this).attr("disabled","disabled").css("background-color","#EEEEEE;");
			$(this).val("无限责任");
		});
	}
}
function setBankInfo(Value){
	$("input[name='DEPOSITOR_POSTCODE']").val("350400");
	$("input[name='AREA_CODE']").val("391008");
	$("input[name='DEPOSITOR_TYPE']").val("企业法人");
	$("input[name='PROOFDOC']").val("工商营业执照");
	$("input[name='ACCOUNT_NATURE']").val("基本");
	
	
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
	$("#legalPersonName").addClass(" validate[required]");
	$("#legalPersonIDType").addClass(" validate[required]");
	$("#legalPersonIDNo").addClass(" validate[required]");
	var legalPersonIDType = $("#legalPersonIDType").val();
	if(legalPersonIDType=="SF"){
		$("#legalPersonIDNo").addClass(",custom[vidcard]");
	}
	$("input[name='DEPOSITOR']").val($("input[name='COMPANY_NAME']").val());
	var addr = $("input[name='REGISTER_ADDR']").val();
	$("input[name='DEPOSITOR_ADDR']").val(addr);
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
/**
*  实缴出资额计算
*/
function setRealCapital(){
	$("#gdxxDiv div").each(function(i){		
		var PAIDIN_QUOTA = $(this).find("[name$='PAIDIN_QUOTA']").val();//实缴出资额
		if(Number(PAIDIN_QUOTA)>0){	
			$(this).find("[name$='PAIDIN_DATE']").attr("disabled",false); 
			$(this).find("[name$='PAIDIN_DATE']").addClass("Wdate validate[required]");		
		} else if(Number(PAIDIN_QUOTA) == 0){	
			$(this).find("[name$='PAIDIN_DATE']").removeClass("Wdate validate[required]");		
			$(this).find("[name$='PAIDIN_DATE']").attr("disabled",true); 
			$(this).find("[name$='PAIDIN_DATE']").val(''); 
		}
	});
}

function showLicenceAppoint(index){
	var  SHAREHOLDER_TYPE = $("#gdxxDiv [name='"+index+"SHAREHOLDER_TYPE']").val();
	var  IS_PARTNERSHIP = $("#gdxxDiv [name='"+index+"IS_PARTNERSHIP']").val();
	if(SHAREHOLDER_TYPE != 'zrr' && IS_PARTNERSHIP == 1){
		$("#gdxxDiv ." + index + "licenceAppointTr").show();
		$("#gdxxDiv ." + index + "licenceAppointTr").find("input").addClass(" validate[required]");
		$("#gdxxDiv ." + index + "licenceAppointTr").find("select").addClass(" validate[required]");
		var SSSBLX = $("input[name='SSSBLX']").val();
		if(SSSBLX=='1') {
			$("#gdxxDiv ." + index + "licenceAppointTr").find("input[name='" + index + "LICENCE_APPOINT_IDNO']").addClass(" ,custom[vidcard],custom[isEighteen],custom[equalHhShareHolder]");
			$("#gdxxDiv ." + index + "licenceAppointTr").find("select[name='" + index + "LICENCE_APPOINT_IDTYPE']").val('SF');
			$("#gdxxDiv ." + index + "licenceAppointTr").find("select[name='" + index + "LICENCE_APPOINT_IDTYPE']").attr("disabled", "disabled");
		}
	} else{
		$("#gdxxDiv ." + index + "licenceAppointTr").hide();
		$("#gdxxDiv ." + index + "licenceAppointTr").find("input").removeClass(" validate[required]");
		$("#gdxxDiv ." + index + "licenceAppointTr").find("select").removeClass(" validate[required]");
		$("#gdxxDiv ." + index + "licenceAppointTr").find("input").val("");
		$("#gdxxDiv ." + index + "licenceAppointTr").find("select").val("");
		var SSSBLX = $("input[name='SSSBLX']").val();
		if(SSSBLX=='1') {
			$("#gdxxDiv ." + index + "licenceAppointTr").find("input[name='" + index + "LICENCE_APPOINT_IDNO']").removeClass(" ,custom[vidcard],custom[isEighteen],custom[equalHhShareHolder]");
			$("#gdxxDiv ." + index + "licenceAppointTr").find("select[name='" + index + "LICENCE_APPOINT_IDTYPE']").val('');
			$("#gdxxDiv ." + index + "licenceAppointTr").find("select[name='" + index + "LICENCE_APPOINT_IDTYPE']").attr("disabled", "false");
		}
	}
}
//提交流程按钮
function FLOW_SubmitFun() {
	//添加是否同时进行员工参保判断，选是则必须上传对应的材料
	var isCb = $("input[name='IS_EMPLOY_INSU']:checked").val();
	var FILE_ID3 = $("input[name='FILE_ID3']").val();
	if(isCb=='1'){
		if(FILE_ID3==null || FILE_ID3 == undefined || FILE_ID3 ==""){
			art.dialog({
				content : "请上传同时进行员工参保所对应的材料！",
				icon : "warning",
				ok : true
			});
			return;
		}
	}
	
	var flagOfMp=validateSubmitFunOfMp();
	if(!flagOfMp){
		return;
	}

	var COMPANY_SETNATURE = $("#COMPANY_FORM input[name='COMPANY_SETNATURE']").val();
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var gdnum = 0;
	$("input[name$='SHAREHOLDER_NAME']").each(function(i){		
		gdnum++;
	});
	
	var wxzrNum = 0;
	var yxzrNum = 0;
	if(COMPANY_SETNATURE=="yxhhqy"){//有限合伙企业 合伙人有限责任合伙人 和无限责任合伙人 至少各有一个。
		$("select[name$='DUTY_MODE']").each(function(i){
			if($(this).val() == '无限责任'){
				wxzrNum++ ;
			} else if($(this).val() == '有限责任'){
				yxzrNum++ ;
			}
		});		
		if(wxzrNum == 0 || yxzrNum == 0){		
			art.dialog({
				content : "有限责任合伙人和无限责任合伙人至少各有一个！",
				icon : "warning",
				ok : true
			});
			return;	
		}

	}
	
	if(COMPANY_SETNATURE=="pthhqy" && gdnum<2){
		art.dialog({
			content : "合伙人数量必须大于等于2！",
			icon : "warning",
			ok : true
		});
		return;
	}
	if(COMPANY_SETNATURE=="yxhhqy" && (gdnum<2 || gdnum>50)){
		art.dialog({
			content : "合伙人数量必须大于等于2并且小于等于50！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var ispartnership = 0;
	$("select[name$='IS_PARTNERSHIP']").each(function(i){		
		var is = $(this).val();
		if(is=="1"){
			ispartnership++;
		}
	});
	if(ispartnership>1 && COMPANY_SETNATURE=="pthhqy"){
		art.dialog({
			content : "执行事务合伙人有且仅有一个！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var BUSSINESS_YEARS= $("input[name='BUSSINESS_YEARS']").val();//经营期限
	var PAYMENT_PERIOD= $("input[name='PAYMENT_PERIOD']").val();//出资全部缴付到位期限
	if(BUSSINESS_YEARS != null && BUSSINESS_YEARS != '' 
		&& PAYMENT_PERIOD != null && PAYMENT_PERIOD != ''){
		var myDate = new Date();   
		var fullYear = myDate.getFullYear();
		var year = PAYMENT_PERIOD.substring(0,4);
		if((Number(fullYear)+Number(BUSSINESS_YEARS))<Number(year)){
			art.dialog({
				content : "出资全部缴付到位期限必须小于等于当前时间加上经营期限",
				icon : "warning",
				ok : true
			});
			return;
		}
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
		//var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(-1);
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
		 //获取执行事务合伙人信息
		 //flowSubmitObj.PARTNER_JSON =getZxswhhrJson();
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
	 //获取股东信息
	 flowSubmitObj.HOLDER_JSON =getGdxxJson();
	 //获取执行事务合伙人信息
	 //flowSubmitObj.PARTNER_JSON =getZxswhhrJson();
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
	var SSSBLX = $("input[name='SSSBLX']").val();
	if(SSSBLX=='1') {
		if (zjlx == "SF") {
			$("input[name='" + name + "']").addClass(",custom[vidcard],custom[isEighteen]");
		} else {
			$("input[name='" + name + "']").removeClass(",custom[vidcard],custom[isEighteen]");
		}
	}else{
		if (zjlx == "SF") {
			$("input[name='" + name + "']").addClass(",custom[vidcard]");
		} else {
			$("input[name='" + name + "']").removeClass(",custom[vidcard]");
		}
	}
}

/**
* 经营范围选择器
**/
function showSelectJyfw(){
	var induIds = $("input[name='BUSSINESS_SCOPE_ID']").val();
	var defId=$("input[name='FLOW_DEFID']").val();
	
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
				if(defId=='ss001'){
					$("[name='BUSSINESS_SCOPE']").val(selectInduInfo.induNames+"。法律法规、国务院决定未规定许可的，均可自主选择经营项目开展经营。（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
				}else{
					$("[name='BUSSINESS_SCOPE']").val(selectInduInfo.induNames+"。（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
				}
				$("[name='BUSSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				$("[name='INVEST_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='INVEST_INDUSTRY_ID']").val(selectInduInfo.induIds);
				$("[name='INDUSTRY_CODE']").val(selectInduInfo.induCodes);
				var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
				if(chk=='1'){
					$("#businessScope").val(selectInduInfo.induNames);
					$("#INDUSTRY_CATEGORY_CDOE").val(selectInduInfo.firstCodes);
				}
				$("[name='INDUSTRY_CATEGORY']").val(selectInduInfo.firstCodes);
				art.dialog.removeData("selectInduInfo");
			}
			/* else{
				$("[name='BUSSINESS_SCOPE']").val('');
				$("[name='BUSSINESS_SCOPE_ID']").val('');				
			} */
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
					$("[name='INVEST_INDUSTRY_ID']").val(selectBusScopeInfo.ids);
					$("[name='BUSSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
					$("[name='INVEST_INDUSTRY']").val(selectBusScopeInfo.induNames);
					$("[name='INDUSTRY_CODE']").val(selectBusScopeInfo.induCodes);
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
/**
* 设置人民币
**/
function setCurrency(val){
	if(val=="人民币"){
		$("[name$='FOLDING_RMB']").attr("readonly","readonly");
		$("[name$='FOLDING_RMB']").addClass("inputBackgroundCcc");
		$("[name$='CURRENCY_1']").val(val);
	}else{		
		$("[name$='FOLDING_RMB']").removeAttr("readonly");
		$("[name$='FOLDING_RMB']").removeClass("inputBackgroundCcc");
		$("[name$='CURRENCY_1']").val(val);
	}
	$("[name$='CURRENCY_1']").attr("disabled",true); 
}

/*************股东信息JS开始****************/

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


/**
 * 添加股东信息
 */
function addGdxxDiv(){
	$.post("jointVentureController/refreshGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
		setcdzfs();
	});
	
}
/**
 * 初始化时添加股东信息
 */
function addInitGdxxDiv(holder,i){
	$.post("jointVentureController/refreshGdxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#gdxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(holder,$("#gdxxDiv div").eq(i));
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
		setcdzfs();
	});
}
/**
 * 删除股东信息
 */
function delGdxxDiv(o){
	$(o).closest("div").remove();
	setInvestment();
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
		$("input[name='INVESTMENT']").val(totla.toFixed(6));					
		$("#gdxxDiv div").each(function(i){
			var rjcze = $(this).find("[name$='CONTRIBUTIONS']").val();	
			var a = Number(rjcze)/(Number(totla))*100;
			$(this).find("[name$='PROPORTION']").val(a.toFixed(2)+"%");
		});
	}else{		
		$("input[name='INVESTMENT']").val('');		
	}
	var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
	if(chk=='1'){
		$("#REG_CAPITAL").val($("input[name='INVESTMENT']").val());
	}
	
	$("input[name='CAPITAL_CONTRIBUTION']").val($("input[name='INVESTMENT']").val());

}

/**
 * 股东资金计算
 */
function setEvaluateWayNeed(name,name2){
	var cash = $("input[name='"+name2+"']").val();
	if(cash!='' && cash!=null && cash!=undefined){
		$("input[name='"+name+"']").attr("class","syj-input1 validate[]");
	}else{
		$("input[name='"+name+"']").attr("class","syj-input1 validate[required]");
	}
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
		var LEGAL_MOBILE_PERSON = $(this).find("[name$='LEGAL_MOBILE_PERSON']").val();// 投资企业或其他组织机构法定代表人电话号码
		var DUTY_MODE = $(this).find("[name$='DUTY_MODE']").val();//承担责任方式
		var IS_PARTNERSHIP = $(this).find("[name$='IS_PARTNERSHIP']").val();//执行事务合伙人
        var SHAREHOLDER_COMPANYCOUNTRY = $(this).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//执行事务合伙人国别
		var EVALUATE_WAY=$(this).find("[name$='EVALUATE_WAY']").val();//评估方式
        var SHAREHOLDER_FIXPHONE=$(this).find("[name$='SHAREHOLDER_FIXPHONE']").val();
        var SHAREHOLDER_EMAIL=$(this).find("[name$='SHAREHOLDER_EMAIL']").val();
		var LICENCE_APPOINT_NAME = $(this).find("[name$='LICENCE_APPOINT_NAME']").val();//委派人姓名
		var LICENCE_APPOINT_IDTYPE = $(this).find("[name$='LICENCE_APPOINT_IDTYPE']").val();//委派人证件类型
		var LICENCE_APPOINT_IDNO = $(this).find("[name$='LICENCE_APPOINT_IDNO']").val();//委派人证件号码
		
			
		var INVESTMENT_CASH = $(this).find("[name$='INVESTMENT_CASH']").val();//现金
		var INVESTMENT_MATERIAL = $(this).find("[name$='INVESTMENT_MATERIAL']").val();//实物
		var INVESTMENT_TECHNOLOGY = $(this).find("[name$='INVESTMENT_TECHNOLOGY']").val();//技术出资
		var INVESTMENT_LAND = $(this).find("[name$='INVESTMENT_LAND']").val();//土地使用权
		var INVESTMENT_STOCK = $(this).find("[name$='INVESTMENT_STOCK']").val();//股权
		var INVESTMENT_OTHER = $(this).find("[name$='INVESTMENT_OTHER']").val();//其他
		
		var CONTRIBUTIONS = $(this).find("[name$='CONTRIBUTIONS']").val();//认缴出资额
		var PROPORTION = $(this).find("[name$='PROPORTION']").val();//占注册资本比例
		
		var PAIDIN_QUOTA = $(this).find("[name$='PAIDIN_QUOTA']").val();//实缴出资额（万元）
		var PAIDIN_DATE = $(this).find("[name$='PAIDIN_DATE']").val();//缴款日期
		
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
			gdxx.LEGAL_MOBILE_PERSON = LEGAL_MOBILE_PERSON;
			gdxx.SHAREHOLDER_COMPANYCOUNTRY=SHAREHOLDER_COMPANYCOUNTRY;
			gdxx.EVALUATE_WAY=EVALUATE_WAY;
            gdxx.SHAREHOLDER_FIXPHONE=SHAREHOLDER_FIXPHONE;
            gdxx.SHAREHOLDER_EMAIL=SHAREHOLDER_EMAIL;
			gdxx.DUTY_MODE = DUTY_MODE;
			gdxx.IS_PARTNERSHIP = IS_PARTNERSHIP;
						
			gdxx.LICENCE_APPOINT_NAME = LICENCE_APPOINT_NAME;
			gdxx.LICENCE_APPOINT_IDTYPE = LICENCE_APPOINT_IDTYPE;
			gdxx.LICENCE_APPOINT_IDNO = LICENCE_APPOINT_IDNO;
			
			gdxx.INVESTMENT_CASH = INVESTMENT_CASH;
			gdxx.INVESTMENT_MATERIAL = INVESTMENT_MATERIAL;
			gdxx.INVESTMENT_TECHNOLOGY = INVESTMENT_TECHNOLOGY;
			gdxx.INVESTMENT_LAND = INVESTMENT_LAND;
			gdxx.INVESTMENT_STOCK = INVESTMENT_STOCK;
			gdxx.INVESTMENT_OTHER = INVESTMENT_OTHER;
			
			gdxx.CONTRIBUTIONS = CONTRIBUTIONS;
			gdxx.PROPORTION = PROPORTION;
			
			gdxx.PAIDIN_QUOTA = PAIDIN_QUOTA;
			gdxx.PAIDIN_DATE = PAIDIN_DATE;
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
			var CURRENCY = $("[name='CURRENCY']").val(); 
			setCurrency(CURRENCY);
		}
	}
}





/*************股东信息JS结束****************/



/*************执行事务合伙人信息JS开始****************/


/**
 * 设置执行事务合伙人
 */
function setPartnershipName(){
	for(var j=0;j<$("#zxswhhrDiv table").length;j++){
		var array = new Array(); //定义数组			
		array.push("请选择执行事务合伙人");
		$("#gdxxDiv div").each(function(i){		
			var IS_PARTNERSHIP = $(this).find("[name$='IS_PARTNERSHIP']").val();//是否执行事务合伙人
			var SHAREHOLDER_NAME = $(this).find("[name$='SHAREHOLDER_NAME']").val();//境外投资者名称
			if(IS_PARTNERSHIP==1){
				array.push(SHAREHOLDER_NAME);			
			}
		});
		//删除重复的任命方
		$("#zxswhhrDiv table").eq(j).find("[name$='PARTNER_PARTNERSHIP'] option").each(function(){ //遍历全部option
			var txt = $(this).text(); //获取option的内容
			var isok = true;
			for(var k=0;k<array.length;k++){						
				if(txt==array[k]){					
					isok = false;
				} 
			}
			if(isok){
				$(this).remove();
			}
		});
		//添加未重复的任命方
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#zxswhhrDiv table").eq(j).find("[name$='PARTNER_PARTNERSHIP'] option").each(function(){
				var txt = $(this).text(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				if(array[k]=="请选择任命方"){
					$("#zxswhhrDiv table").eq(j).find("[name$='PARTNER_PARTNERSHIP']")
					.append("<option value=''>"+array[k]+"</option>");						
				}else{
					$("#zxswhhrDiv table").eq(j).find("[name$='PARTNER_PARTNERSHIP']")
					.append("<option value='"+array[k]+"'>"+array[k]+"</option>");							
				}
			}
		}
	}
}


/**
 * 添加执行事务合伙人信息
 */
function addZxswhhrDiv(){
	if($("#zxswhhrDiv table").length<$("#gdxxDiv div").length){
		$.post("jointVentureController/refreshZxswhhrDiv.do",{
		}, function(responseText, status, xhr) {
			$("#zxswhhrDiv").append(responseText);	
			setPartnershipName();
		});		
	}else{		
		art.dialog({
			content : "人数不能超过合伙出资人！",
			icon : "warning",
			ok : true
		});
	}
}
/**
 * 删除执行事务合伙人信息
 */
function delZxswhhrDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取执行事务合伙人信息
 */
function getZxswhhrJson(){
	var zxswhhrArray = [];
	$("#zxswhhrDiv div").each(function(i){
		var PARTNER_NAME = $(this).find("[name$='PARTNER_NAME']").val();//姓名
		var PARTNER_FIXEDLINE = $(this).find("[name$='PARTNER_FIXEDLINE']").val();//固定电话
		var PARTNER_MOBILE = $(this).find("[name$='PARTNER_MOBILE']").val();//移动电话
		var PARTNER_EMAIL = $(this).find("[name$='PARTNER_EMAIL']").val();//电子邮箱		
		var PARTNER_IDTYPE = $(this).find("[name$='PARTNER_IDTYPE']").val();	//证件类型
		var PARTNER_IDNO = $(this).find("[name$='PARTNER_IDNO']").val();//证件号码
		var PARTNER_PARTNERSHIP = $(this).find("[name$='PARTNER_PARTNERSHIP']").val();//执行事务合伙人
		
		if(null!=PARTNER_PARTNERSHIP&&PARTNER_PARTNERSHIP!=""){
			var zxswhhr = {};
			zxswhhr.PARTNER_NAME = PARTNER_NAME;
			zxswhhr.PARTNER_FIXEDLINE = PARTNER_FIXEDLINE;
			zxswhhr.PARTNER_MOBILE = PARTNER_MOBILE;
			zxswhhr.PARTNER_EMAIL = PARTNER_EMAIL;
			zxswhhr.PARTNER_IDTYPE = PARTNER_IDTYPE;
			zxswhhr.PARTNER_IDNO = PARTNER_IDNO;
			zxswhhr.PARTNER_PARTNERSHIP = PARTNER_PARTNERSHIP;
			zxswhhrArray.push(zxswhhr);		
		}		
		
	});
	if(zxswhhrArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(zxswhhrArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}
/*************执行事务合伙人信息JS结束****************/

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
	if(EFLOW_FLOWOBJ && itemCodes!=null && itemCodes != ''){
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
				busTableName:"T_COMMERCIAL_NZ_JOINTVENTURE"
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
/*************银行开户及其他信息JS结束****************/

$(function() {
	//初始化材料信息
	addMaterDiv();
});
