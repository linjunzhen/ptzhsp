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
			//初始化其他信息
			$("#OTHER_FORM input[name='REGISTER_AUTHORITY']").val(eflowObj.busRecord.REGISTER_AUTHORITY);
			$("#OTHER_FORM input[name='APPROVAL_AUTHORITY']").val(eflowObj.busRecord.APPROVAL_AUTHORITY);
			$("#OTHER_FORM input[name='AUTHORITY_CODE']").val(eflowObj.busRecord.AUTHORITY_CODE);
			$("#OTHER_FORM input[name='LICENSE_NUM']").val(eflowObj.busRecord.LICENSE_NUM);
			$("#OTHER_FORM input[name='CERTIFICATE_NUM']").val(eflowObj.busRecord.CERTIFICATE_NUM);
			$("#OTHER_FORM input[name='FILL_DATE']").val(eflowObj.busRecord.FILL_DATE);
			//初始化申报人信息						
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#USERINFO_FORM"));	
			//初始化外方投资方信息
			//setJwtzzxx(eflowObj.busRecord);
			//初始化中方投资方信息
			//setZftzzxx(eflowObj.busRecord);		
			//初始化监理信息
			setJsxx(eflowObj.busRecord.SUPERVISOR_JSON);
			//初始化经理信息
			setJlxx(eflowObj.busRecord.MANAGER_JSON);
			//初始化董事信息
			setDsxx(eflowObj.busRecord.DIRECTOR_JSON,eflowObj.busRecord.COMPANY_SETTYPE);	
			//初始化外商投资企业法律文件送达授权委托书基本信息
			setAttorney(eflowObj.busRecord.ATTORNEY_JSON);
			
			//初始化经营期限
			setJyqx(eflowObj.busRecord.OPRRATE_TERM_TYPE);
			//初始化是否已通过名称自主选用
			setPreapprovalPass(eflowObj.busRecord.IS_PREAPPROVAL_PASS);
			//初始化是否仅作为联络地址
			setOnlyContact(eflowObj.busRecord.IS_ONLY_CONTACT);
			
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
			if(eflowObj.busRecord.NEGATIVELIST_NAMES!=null&&eflowObj.busRecord.NEGATIVELIST_NAMES!=''){
				showFmqdTr(1);
			}
			
			//判断是否选择企业类型
			if(eflowObj.busRecord.COMPANY_TYPECODE==null||eflowObj.busRecord.COMPANY_TYPECODE==''){
				window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
			}
			$("#companyTypeFont").html(eflowObj.busRecord.COMPANY_TYPE);
			
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
			
			
			//初始化是否仅作为联络地址
			setOnlyContact(eflowObj.busRecord.IS_ONLY_CONTACT);
			
			//初始化企业类型
			setJointStock(eflowObj.busRecord.COMPANY_NATURE);
			setUnlisted(eflowObj.busRecord.JOINT_STOCK_TYPE);
			
			//初始化是否属于国家规定的进口设备减免税范围
			setIsDutyFree(eflowObj.busRecord.IS_DUTY_FREE) ;
			setDutyFreeType(eflowObj.busRecord.DUTY_FREE_TYPE);
			//初始化业务类型
			setBusinessType(eflowObj.busRecord.BUSINESS_TYPE);
			if(eflowObj.busRecord.BUSINESS_TYPE=='03'){
				setFunctionType(eflowObj.busRecord.FUNCTION_TYPE);				
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
            art.dialog.confirm("请确认是否已通过名称登记，已通过请点击 <p>确定，未通过请点击此处前往<a href='http://61.154.11.191/usermana/login.do?method=index' target='_blank'><span style='color: red;text-decoration:underline'>红盾网</span></a>办理!</p>", function() {
            }, function() {
                window.top.location.href=__ctxPath+"/webSiteController.do?zzhywssb";
            });
			art.dialog.confirm("您是否需要申请银行开户?", function() {
				$("input[name='IS_ACCOUNT_OPEN']").attr("checked","checked");
				$("#bankInfo").attr("style","");
				$("input[name='BANK_TYPE']").attr("disabled",false);
				$("input[name='CONTROLLER']").attr("disabled",false);
			});
		}
	}	
	
	//点击是否已通过名称自主选用触发事件
	$("input[name='IS_PREAPPROVAL_PASS']").on("click", function(event) {
		setPreapprovalPass(this.value);
	});
	//注册地址失去焦点事件
	$("input[name='REGISTER_ADDR']").on('blur', function(event) { 
		serRegisterAddr();
	});
	//点击是否仅作为联络地址触发事件
	$("input[name='IS_ONLY_CONTACT']").on("click", function(event) {
		setOnlyContact(this.value);
		
		serRegisterAddr();
	});
	
	//经营期限
	$("input[name='OPRRATE_TERM_TYPE']").on("click", function(event) {
		setJyqx(this.value);
	});
	//初始化法定代表人证件类型不可选择
	//$("#LEGAL_IDTYPE").attr("disabled",true);
	//初始化法定代表人国别不可选择
	//$("#LEGAL_COUNTRY").attr("disabled",true);
	
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
	
	//-------------开始判断人员信息是否可以添加开始-------------
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	if(companySettype=='zwhz'){//中外合资
		$("#addGdxx").show();
		$("#addDsxx").show();
		$("[name$='DIRECTOR_GENERATION_MODE']").val('01');
		$("[name$='SUPERVISOR_GENERATION_MODE']").val('01');
		$("[name$='MANAGER_GENERATION_MODE']").val('03');
	}else if(companySettype=='wshz'){//外商合资
		$("#addGdxx").show();
		$("[name$='DIRECTOR_GENERATION_MODE']").val('02');
		$("[name$='SUPERVISOR_GENERATION_MODE']").val('02');
		$("[name$='MANAGER_GENERATION_MODE']").val('03');
		$("[name$='DIRECTOR_JOB']").val('30');

	}else if(companySettype=='wsdz'){//外商独资
		$("[name$='DIRECTOR_GENERATION_MODE']").val('01');
		$("[name$='SUPERVISOR_GENERATION_MODE']").val('01');
		$("[name$='MANAGER_GENERATION_MODE']").val('03');
		$("[name$='DIRECTOR_JOB']").val('30');		
	}	
	$("[name$='DIRECTOR_JOB']").attr("disabled",true); 
	$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true); 
	$("[name$='SUPERVISOR_JOB']").attr("disabled",true); 
	$("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled",true);
	$("[name$='MANAGER_JOB']").attr("disabled",true);  
	$("[name$='MANAGER_GENERATION_MODE']").attr("disabled",true); 
	
	
	$("#LEGAL_IDTYPE").attr("disabled",true);
	$("#LEGAL_COUNTRY").attr("disabled",true);
	
	$("#zftzzxxDiv [name$='INVESTOR_COUNTRY']").attr("disabled",true); 	
	
	//币种不可编辑
	$("[name$='CURRENCY_1']").attr("disabled",true); 
	
	
	//是否自贸区
	$("input[name='FAT_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		if(Value=='1'){//是
			$("[name='FAT_AREA']").attr("disabled",false); 			
			$("input[name='FAT_AREA']").addClass(" validate[required]");
		}else {//否			
			var addr = $("input[name='REGISTER_ADDR']").val();
			var zhqhfdz = $("input[name='zhqhfdz']").val();  
			var arrayAddr =zhqhfdz.split(",");
			var isok = false;			
			var fatArea = "";
			for(var i=0;i<arrayAddr.length;i++){
				if(addr.indexOf(arrayAddr[i])>-1){
					fatArea+=arrayAddr[i];
					isok =true;
				}
			}
			$("[name='FAT_AREA']").val(fatArea);
			if(isok){
				alert("注册地址中存在自贸区划分片区地址");
				$("input[name='FAT_TYPE'][value='1']").click();
			}else{				
				$("[name='FAT_AREA']").val('');
				$("[name='FAT_AREA']").attr("disabled",true);
			}
		}
	});
	//是否经开区
	$("input[name='IS_ETDZ']").click(function(){
		//获取值
		var Value = $(this).val();
		/* if(Value=='1'){//是
			$("[name='ETDZ_AREA']").attr("disabled",false); 
		}else {//否
			$("[name='ETDZ_AREA']").val('');
			$("[name='ETDZ_AREA']").attr("disabled",true); 
		} */
	});
	
	//企业类型
	$("input[name='COMPANY_NATURE']").click(function(){
		//获取值
		var Value = $(this).val();
		setJointStock(Value);
	});
	$("input[name='JOINT_STOCK_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setUnlisted(Value);
	});
	
	//是否属于国家规定的进口设备减免税范围
	$("input[name='IS_DUTY_FREE']").click(function(){
		//获取值
		var Value = $(this).val();
		setIsDutyFree(Value);
	});
	$("input[name='DUTY_FREE_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setDutyFreeType(Value);
	});
	
	//业务类型
	$("input[name='BUSINESS_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setBusinessType(Value);
	});
	$("input[name='FUNCTION_TYPE']").click(function(){
		//获取值
		var Value = $(this).val();
		setFunctionType(Value);
	});
	//实际控制方式
	$("input[name='CONTROL_METHOD']").click(function(){
		//获取值
		var Value = $(this).val();
		setControlMethod(Value);
	});
	
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
	$("input[name='REGISTER_ADDR']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("input[name='DEPOSITOR_ADDR']").val($(this).val());
		}
	});
});
function setBankInfo(Value){
	$("input[name='DEPOSITOR_POSTCODE']").val("350400");
	$("input[name='AREA_CODE']").val("391008");
	$("input[name='DEPOSITOR_TYPE']").val("企业法人");
	$("input[name='PROOFDOC']").val("工商营业执照");
	$("input[name='ACCOUNT_NATURE']").val("基本");
	$("#legalPersonIDType").attr("disabled",false);
	$("#legalPersonName").val($("input[name='LEGAL_NAME']").val());
	$("#legalPersonIDType").val($("#LEGAL_IDTYPE").val());
	$("#legalPersonIDNo").val($("input[name='LEGAL_IDNO']").val());
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
	var addr = $("input[name='REGISTER_ADDR']").val();
	$("input[name='DEPOSITOR_ADDR']").val(addr);
	$("input[name='ACCOUNT_NAME']").val($("input[name='COMPANY_NAME']").val());
	$("#REG_CAPITAL").val($("input[name='REGISTERED_CAPITAL']").val());
	$("#businessScope").val($("[name='BUSSINESS_SCOPE']").val());
	$("#INDUSTRY_CATEGORY_CDOE").val($("input[name='INDUSTRY_CATEGORY']").val());
}
function onclickControlMethod(Value,currentTime){
	if(Value=='03'){
		$("[name='"+currentTime+"CONTROL_METHOD_OTHER']").attr("disabled",false);
		$("[name='"+currentTime+"CONTROL_METHOD_OTHER']").addClass(" validate[required]"); 
	}else{		
		$("[name='"+currentTime+"CONTROL_METHOD_OTHER']").val('');		
		$("[name='"+currentTime+"CONTROL_METHOD_OTHER']").attr("disabled",true); 
		$("[name='"+currentTime+"CONTROL_METHOD_OTHER']").removeClass("validate[required]");
	}
}
function setControlMethod(Value){
	if(Value=='03'){
		$("[name='CONTROL_METHOD_OTHER']").attr("disabled",false);
		$("[name='CONTROL_METHOD_OTHER']").addClass(" validate[required]"); 
	}else{		
		$("[name='CONTROL_METHOD_OTHER']").val('');		
		$("[name='CONTROL_METHOD_OTHER']").attr("disabled",true); 
		$("[name='CONTROL_METHOD_OTHER']").removeClass("validate[required]");
	}
}
//设置业务类型
function setBusinessType(Value){
	if(Value=='02'){//研发中心			
		$("[name='RESEARCH_TYPE']").attr("disabled",false);
		$("[name='RESEARCH_TYPE']").addClass(" validate[required]"); 
		
		$("[name='FUNCTION_TYPE']").attr("disabled",true); 
		$("[name='FUNCTION_TYPE']").attr("checked",false)
		$("[name='FUNCTION_TYPE']").removeClass("validate[required]");
		$("[name='FUNCTION_OTHER']").val('');		
		$("[name='FUNCTION_OTHER']").attr("disabled",true); 
		$("[name='FUNCTION_OTHER']").removeClass("validate[required]");
	}else if(Value=='03'){//功能性机构
		$("[name='FUNCTION_TYPE']").attr("disabled",false);
		$("[name='FUNCTION_TYPE']").addClass(" validate[required]"); 
		
		$("[name='RESEARCH_TYPE']").attr("disabled",true); 
		$("[name='RESEARCH_TYPE']").attr("checked",false)
		$("[name='RESEARCH_TYPE']").removeClass("validate[required]");
	}else{		
			
		$("[name='RESEARCH_TYPE']").attr("disabled",true); 
		$("[name='RESEARCH_TYPE']").attr("checked",false)
		$("[name='RESEARCH_TYPE']").removeClass("validate[required]");
		$("[name='FUNCTION_TYPE']").attr("disabled",true); 
		$("[name='FUNCTION_TYPE']").attr("checked",false)
		$("[name='FUNCTION_TYPE']").removeClass("validate[required]");
		$("[name='FUNCTION_OTHER']").val('');		
		$("[name='FUNCTION_OTHER']").attr("disabled",true); 
		$("[name='FUNCTION_OTHER']").removeClass("validate[required]");
		
	}
}
function setFunctionType(Value){
	 if(Value=='07'){//其他
		$("[name='FUNCTION_OTHER']").attr("disabled",false);
		$("[name='FUNCTION_OTHER']").addClass(" validate[required]"); 
	}else{					
		$("[name='FUNCTION_OTHER']").val('');		
		$("[name='FUNCTION_OTHER']").attr("disabled",true); 
		$("[name='FUNCTION_OTHER']").removeClass("validate[required]");
		
	}
}
//设置是否属于国家规定的进口设备减免税范围
function setIsDutyFree(Value){
	if(Value=='1'){//是		
		$("[name='DUTY_FREE_TYPE']").attr("disabled",false);
		$("[name='DUTY_FREE_TYPE']").addClass(" validate[required]"); 
	}else if(Value=='0'){		
		$("[name='DUTY_FREE_TYPE']").attr("disabled",true); 
		$("[name='DUTY_FREE_TYPE']").attr("checked",false)
		$("[name='DUTY_FREE_TYPE']").removeClass("validate[required]");
		$("[name='NATIOINEN_INDUSTRY']").val('');
		$("[name='MIDWEST_INDUSTRY']").val('');
		
		$("[name='MIDWEST_INDUSTRY']").attr("disabled",true); 
		$("[name='MIDWEST_INDUSTRY']").removeClass("validate[required]");
		
		$("[name='NATIOINEN_INDUSTRY']").attr("disabled",true); 
		$("[name='NATIOINEN_INDUSTRY']").removeClass("validate[required]");
	}
}
function setDutyFreeType(Value){
	if(Value=='1'){//是		
		$("[name='NATIOINEN_INDUSTRY']").attr("disabled",false);
		$("[name='NATIOINEN_INDUSTRY']").addClass(" validate[required]"); 
		
		$("[name='MIDWEST_INDUSTRY']").val('');
		$("[name='MIDWEST_INDUSTRY']").attr("disabled",true); 
		$("[name='MIDWEST_INDUSTRY']").removeClass("validate[required]");
	}else if(Value=='2'){		
		$("[name='MIDWEST_INDUSTRY']").attr("disabled",false)
		$("[name='MIDWEST_INDUSTRY']").addClass(" validate[required]"); 
		
		$("[name='NATIOINEN_INDUSTRY']").val('');
		$("[name='NATIOINEN_INDUSTRY']").attr("disabled",true); 
		$("[name='NATIOINEN_INDUSTRY']").removeClass("validate[required]");
	}
}
//设置企业类型
function setJointStock(Value){
	if(Value=='04'){//是
		$("#jointStock_span").show();
		$("input[name='JOINT_STOCK_TYPE']").addClass(" validate[required]");	
	}else {//否			
		$("#jointStock_span").hide();
		$("input[name='JOINT_STOCK_TYPE']").attr("checked",false)
		$("input[name='JOINT_STOCK_TYPE']").removeClass("validate[required]");
		$("#unlisted_span").hide();
		$("input[name='UNLISTED_TYPE']").attr("checked",false)
		$("input[name='UNLISTED_TYPE']").removeClass("validate[required]");
	}
}
function setUnlisted(Value){
	if(Value=='0'){//非上市
		$("#unlisted_span").show();
		$("input[name='UNLISTED_TYPE']").addClass(" validate[required]");	
	}else {//上市			
		$("#unlisted_span").hide();
		$("input[name='UNLISTED_TYPE']").attr("checked",false)
		$("input[name='UNLISTED_TYPE']").removeClass("validate[required]");
	}
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
//设置注册地址后缀
function serRegisterAddr(){
	var IS_ONLY_CONTACT = $("input[name='IS_ONLY_CONTACT']:checked").val();
	var addr = $("input[name='REGISTER_ADDR']").val();
	if(null!=addr&&''!=addr&&IS_ONLY_CONTACT==1){
		addr = addr.replace("（仅作为联络地址）", "").replace("(仅作为联络地址)", "") + "（仅作为联络地址）";
	}else{
		addr = addr.replace("（仅作为联络地址）", "").replace("(仅作为联络地址)", "");
	} 
	$("input[name='REGISTER_ADDR']").val(addr);
	var zhqhfdz = $("input[name='zhqhfdz']").val();  
	var arrayAddr =zhqhfdz.split(",");
	var isok = false;
	var fatArea = "";
	for(var i=0;i<arrayAddr.length;i++){
		if(addr.indexOf(arrayAddr[i])>-1){
			fatArea+=arrayAddr[i];
			isok =true;
		}
	}
	$("[name='FAT_AREA']").val(fatArea);
	if(isok){
		$("input[name='FAT_TYPE'][value='1']").click();
	}else{			
		$("input[name='FAT_TYPE'][value='0']").click();
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
//设置是否仅作为联络地址
function setOnlyContact(value){
	if (value == "0") {			
		$("input[name='LESSOR']").attr("disabled",false);
		$("input[name='LESSOR']").addClass(" validate[required]");			
		$("input[name='LEASE_START_DATE']").attr("disabled",false);
		$("input[name='LEASE_START_DATE']").addClass("Wdate validate[required]");			
		$("input[name='LEASE_END_DATE']").attr("disabled",false);
		$("input[name='LEASE_END_DATE']").addClass("Wdate validate[required]");			
		$("input[name='SINGING_TIME']").attr("disabled",false);
		$("input[name='SINGING_TIME']").addClass("Wdate validate[required]");
	} else {			
		$("input[name='LESSOR']").attr("disabled",true); 
		$("input[name='LESSOR']").removeClass(" validate[required]");			
		$("input[name='LEASE_START_DATE']").attr("disabled",true); 
		$("input[name='LEASE_START_DATE']").removeClass("Wdate validate[required]");			
		$("input[name='LEASE_END_DATE']").attr("disabled",true); 
		$("input[name='LEASE_END_DATE']").removeClass("Wdate validate[required]");			
		$("input[name='SINGING_TIME']").attr("disabled",true); 
		$("input[name='SINGING_TIME']").removeClass("Wdate validate[required]");
		
		$("input[name='LESSOR']").val("");		
		$("input[name='LEASE_START_DATE']").val("");
		$("input[name='LEASE_END_DATE']").val("");		
		$("input[name='SINGING_TIME']").val("");
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
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	if(companySettype=='zwhz'){//中外合资	
		var num = 0;
		$("input[name$='DIRECTOR_NUM']").each(function(i){		
			var DIRECTOR_NUM = $(this).val();
			if(DIRECTOR_NUM!=null&&DIRECTOR_NUM!=''){	
				num = Number(num)+Number(DIRECTOR_NUM);
			}
		});
		var isok1 = false;
		var isok2 = false;
		$("#dsxxDiv table").each(function(i){
			var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
			var DIRECTOR_APPOINTOR = $(this).find("[name$='DIRECTOR_APPOINTOR']").val();// 任命方	
			if(DIRECTOR_JOB=='01'){
				if(DIRECTOR_APPOINTOR!=null && DIRECTOR_APPOINTOR.indexOf("外方投资者")>-1){
					isok1 = true;
				}else if(DIRECTOR_APPOINTOR!=null && DIRECTOR_APPOINTOR.indexOf("中方投资者")>-1){
					isok2 = true;
				}
			}else if(DIRECTOR_JOB=='02'){
				if(DIRECTOR_APPOINTOR!=null && DIRECTOR_APPOINTOR.indexOf("外方投资者")>-1){
					isok1 = true;
				}else if(DIRECTOR_APPOINTOR!=null && DIRECTOR_APPOINTOR.indexOf("中方投资者")>-1){
					isok2 = true;
				}
			}	
		});
		if(num<3){
			art.dialog({
				content : "委派董事人数相加必须大于等于3",
				icon : "warning",
				ok : true
			});
			return;
		}else if($("#dsxxDiv table").length<num){
			art.dialog({
				content : "董事人数必须等于委派董事人数相加",
				icon : "warning",
				ok : true
			});
			return;
		}else if(!isok1 || !isok2){			
			art.dialog({
				content : "董事长与副董事长不能同时由外方或中方委派",
				icon : "warning",
				ok : true
			});
			return;
		}
		
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
		// $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
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
		 
		 //获取外商投资企业最终实际控制人信息
		 flowSubmitObj.CONTROLLER_JSON =getQysjkzrDiv();
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
		 
	//获取外商投资企业最终实际控制人信息
	flowSubmitObj.CONTROLLER_JSON =getQysjkzrDiv();
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
			}
			/* else{				
				$("[name='INVEST_INDUSTRY']").val('');
				$("[name='INVEST_INDUSTRY_ID']").val('');
				$("[name='INDUSTRY_CODE']").val('');
			} */
		}
	}, false);
};
/**
* 主营行业选择器
**/
function showSelectZyhy(){
	var induIds = $("input[name='MAIN_INDUSTRY_ID']").val();
	var url = "domesticControllerController/selector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
	+"checkCascadeN=&ISTZINDUSTRY=-1";
	$.dialog.open(url, {
		title : "主营行业选择器",
		width:"760px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectInduInfo = art.dialog.data("selectInduInfo");
			if(selectInduInfo){
				$("[name='MAIN_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='MAIN_INDUSTRY_ID']").val(selectInduInfo.induIds);
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
				if(negativeListInfo.negativeListCodes!=null&&negativeListInfo.negativeListCodes!=''){					
					showFmqdTr(1);
				}else{					
					showFmqdTr(0);
				}
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
		
		
		$("input[name='CERTIFICATE_NO']").val("");	
		$("input[name='IMPANDEXP_CODE']").val("");
		$("input[name='APPROVAL_NO']").val("");	
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
		$("[name$='FOLDING_RMB']").val('');
	}
	$("[name$='CURRENCY_1']").attr("disabled",true); 
}

/*************外商投资企业最终实际控制人信息JS开始****************/
/**
 * 添加外商投资企业最终实际控制人信息
 */
function addQysjkzrDiv(){
	$.post("foreignControllerController/refreshQysjkzrxxDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qysjkzrDiv").append(responseText);		
	});
}
/**
 * 删除外商投资企业最终实际控制人信息
 */
function delQysjkzrDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取外商投资企业最终实际控制人信息
 */
function getQysjkzrDiv(){
	var qysjkzrArray = [];
	$("#qysjkzrDiv div").each(function(i){
		var CONTROLLER_NAME = $(this).find("[name$='CONTROLLER_NAME']").val();//姓名/名称（中文）
		var CONTROLLER_NAME_ENG = $(this).find("[name$='CONTROLLER_NAME_ENG']").val();// 姓名/名称（英文
		var REGISTERED_PLACE = $(this).find("[name$='REGISTERED_PLACE']").val();//国籍（或地区）/注册地
		var CONTROLLER_IDNO = $(this).find("[name$='CONTROLLER_IDNO']").val();//证照号码	
		var CONTROLLER_TYPE = $(this).find("[name$='CONTROLLER_TYPE']:checked").val();	//控制人类别
		var CONTROL_METHOD = $(this).find("[name$='CONTROL_METHOD']:checked").val();//实际控制方式
		var CONTROL_METHOD_OTHER = $(this).find("[name$='CONTROL_METHOD_OTHER']").val();// 其他方式
		if(null!=CONTROLLER_NAME&&CONTROLLER_NAME!=""){
			var qysjkzr = {};
			qysjkzr.CONTROLLER_NAME = CONTROLLER_NAME;
			qysjkzr.CONTROLLER_NAME_ENG = CONTROLLER_NAME_ENG;
			qysjkzr.REGISTERED_PLACE = REGISTERED_PLACE;
			qysjkzr.CONTROLLER_IDNO = CONTROLLER_IDNO;
			qysjkzr.CONTROLLER_TYPE = CONTROLLER_TYPE;
			qysjkzr.CONTROL_METHOD = CONTROL_METHOD;
			qysjkzr.CONTROL_METHOD_OTHER = CONTROL_METHOD_OTHER;
			qysjkzrArray.push(qysjkzr);
		}
	});
	if(qysjkzrArray.length>0){
		return JSON.stringify(qysjkzrArray);
	}else{
		return "";
	}
}
/*************外商投资企业最终实际控制人信息JS结束****************/

/*************外方投资者信息JS开始****************/
/**
 * 添加外方投资者信息
 */
function addJwtzzxxDiv(){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	$.post("foreignControllerController/refreshJwtzzxxDiv.do",{
		companySettype:companySettype
	}, function(responseText, status, xhr) {
		$("#jwtzzxxDiv").append(responseText);
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
	});
}
/**
 * 初始化时外方投资者信息
 */
function addInitJwtzzxxDiv(holder,i){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	$.post("foreignControllerController/refreshJwtzzxxDiv.do",{
		companySettype:companySettype
	}, function(responseText, status, xhr) {
		$("#jwtzzxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(holder,$("#jwtzzxxDiv div").eq(i));
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
	});
}
/**
 * 删除外方投资者信息
 */
function delJwtzzxxDiv(o){
	$(o).closest("div").remove();
	setJwtzzInvestment();
	setInvestorName();
	setAuthorizer();
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
	var holders = eval(holderJson.FOREIGNINVESTOR_JSON);
	if(holders){				
		for(var i=0;i<holders.length;i++){			
			if(i>0){
				addInitJwtzzxxDiv(holders[i],i);
			}else{				
				FlowUtil.initFormObjValue(holders[i],$("#jwtzzxxDiv div").eq(i));
			}			
			var CURRENCY = $("[name='CURRENCY']").val(); 
			setCurrency(CURRENCY);
		}
		setInvestorName();
		setAuthorizer();
	}
}
/*************外方投资者信息JS结束****************/


/*************中方投资者信息JS开始****************/
/**
 * 添加中方投资者信息
 */
function addZftzzxxDiv(){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	$.post("foreignControllerController/refreshZftzzxxDiv.do",{
		companySettype:companySettype
	}, function(responseText, status, xhr) {
		$("#zftzzxxDiv").append(responseText);		
		$("#zftzzxxDiv [name$='INVESTOR_COUNTRY']").attr("disabled",true); 
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
		
	});
}
/**
 * 初始化时中方投资者信息
 */
function addInitZftzzxxDiv(holder,i){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	$.post("foreignControllerController/refreshZftzzxxDiv.do",{
		companySettype:companySettype
	}, function(responseText, status, xhr) {
		$("#zftzzxxDiv").append(responseText);	
		FlowUtil.initFormObjValue(holder,$("#zftzzxxDiv div").eq(i));
		$("#zftzzxxDiv [name$='INVESTOR_COUNTRY']").attr("disabled",true); 
		var CURRENCY = $("[name='CURRENCY']").val(); 
		setCurrency(CURRENCY);
	});
}
/**
 * 删除中方投资者信息
 */
function delZftzzxxDiv(o){
	$(o).closest("div").remove();
	setJwtzzInvestment();
	setInvestorName();
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
	var holders = eval(holderJson.DOMESTICINVESTOR_JSON);
	if(holders){				
		for(var i=0;i<holders.length;i++){			
			if(i>0){
				addInitZftzzxxDiv(holders[i],i);
			}else{				
				FlowUtil.initFormObjValue(holders[i],$("#zftzzxxDiv div").eq(i));
			}
			var CURRENCY = $("[name='CURRENCY']").val(); 
			setCurrency(CURRENCY);
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
	}else{			
		var dsxxLength = $("#dsxxDiv table").length;
		var num = 0;
		$("#jwtzzxxDiv [name$='DIRECTOR_NUM']").each(function(i){
			num += Number($(this).val());
		});
		$("#zftzzxxDiv [name$='DIRECTOR_NUM']").each(function(i){
			num += Number($(this).val());
		});
		if(dsxxLength>=num){
			art.dialog({
				content : "董事数量不能大于投资者委派数量！",
				icon : "error",
				ok : true
			});
			return;
		}		
	}
	$.post("foreignControllerController/refreshDsxxDiv.do",{
		directorJob:directorJob
	}, function(responseText, status, xhr) {
		$("#dsxxDiv").append(responseText);
		setInvestorName();
			
		$("[name$='DIRECTOR_GENERATION_MODE']").val('01');
		$("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled",true); 
		
		$("[name$='DIRECTOR_JOB']").attr("disabled",true); 
	});
}
/**
 * 初始化时添加董事信息
 */
function addInitDsxxDiv(director,i){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	var directorJob = '03'
	if(companySettype!='zwhz'){//中外合资
		directorJob = '30';
	}
	$.post("foreignControllerController/refreshDsxxDiv.do",{
		directorJob:directorJob
	}, function(responseText, status, xhr) {
		$("#dsxxDiv").append(responseText);	
		setInitInvestorName(director.DIRECTOR_APPOINTOR);	
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
				setInvestorName();	
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
	var isok = false;//标识位
	$("#dsxxDiv div").each(function(i){
		var DIRECTOR_NAME = $(this).find("[name$='DIRECTOR_NAME']").val();//姓名
		var DIRECTOR_JOB = $(this).find("[name$='DIRECTOR_JOB']").val();//职务
		var DIRECTOR_COUNTRY = $(this).find("[name$='DIRECTOR_COUNTRY']").val();//国别
		var DIRECTOR_IDTYPE = $(this).find("[name$='DIRECTOR_IDTYPE']").val();//证件类型		
		var DIRECTOR_IDNO = $(this).find("[name$='DIRECTOR_IDNO']").val();	//证件号码
		if(DIRECTOR_JOB==val){
			$("input[name='LEGAL_NAME']").val(DIRECTOR_NAME);
			setZjValidate(DIRECTOR_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(DIRECTOR_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(DIRECTOR_IDNO);
			$("[name='LEGAL_COUNTRY']").val(DIRECTOR_COUNTRY);
			isok = true;
			return;
		}
	});
	$("#jsxxDiv div").each(function(i){
		var SUPERVISOR_NAME = $(this).find("[name$='SUPERVISOR_NAME']").val();//姓名
		var SUPERVISOR_JOB = $(this).find("[name$='SUPERVISOR_JOB']").val();//职务
		var SUPERVISOR_COUNTRY = $(this).find("[name$='SUPERVISOR_COUNTRY']").val();//国别
		var SUPERVISOR_IDTYPE = $(this).find("[name$='SUPERVISOR_IDTYPE']").val();//证件类型		
		var SUPERVISOR_IDNO = $(this).find("[name$='SUPERVISOR_IDNO']").val();	//证件号码
		if(SUPERVISOR_JOB==val){			
			$("input[name='LEGAL_NAME']").val(SUPERVISOR_NAME);
			setZjValidate(SUPERVISOR_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(SUPERVISOR_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(SUPERVISOR_IDNO);
			$("[name='LEGAL_COUNTRY']").val(SUPERVISOR_COUNTRY);
			isok = true;
			return;
		}
	});
	$("#jlxxDiv div").each(function(i){
		var MANAGER_NAME = $(this).find("[name$='MANAGER_NAME']").val();//姓名
		var MANAGER_JOB = $(this).find("[name$='MANAGER_JOB']").val();//职务
		var MANAGER_COUNTRY = $(this).find("[name$='MANAGER_COUNTRY']").val();//国别
		var MANAGER_IDTYPE = $(this).find("[name$='MANAGER_IDTYPE']").val();//证件类型		
		var MANAGER_IDNO = $(this).find("[name$='MANAGER_IDNO']").val();	//证件号码
		if(MANAGER_JOB==val){
			$("input[name='LEGAL_NAME']").val(MANAGER_NAME);	
			setZjValidate(MANAGER_IDTYPE,'LEGAL_IDNO');
			$("#LEGAL_IDTYPE").val(MANAGER_IDTYPE);
			$("input[name='LEGAL_IDNO']").val(MANAGER_IDNO);
			$("[name='LEGAL_COUNTRY']").val(MANAGER_COUNTRY);
			isok = true;		
			return;
		}
	});
	if(!isok){		
		$("input[name='LEGAL_NAME']").val('');	
		$("#LEGAL_IDTYPE").val('');
		$("input[name='LEGAL_IDNO']").val('');
		$("[name='LEGAL_COUNTRY']").val('');
		
		$("#LEGAL_IDTYPE").attr("disabled",false);
		$("#LEGAL_COUNTRY").attr("disabled",false);
	}else{
		
		$("#LEGAL_IDTYPE").attr("disabled",true);
		$("#LEGAL_COUNTRY").attr("disabled",true);
	}
	var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
	if(chk=='1'){
		$("#legalPersonIDType").attr("disabled",false);
		$("#legalPersonName").val($("input[name='LEGAL_NAME']").val());
		$("#legalPersonIDType").val($("#LEGAL_IDTYPE").val());
		$("#legalPersonIDNo").val($("input[name='LEGAL_IDNO']").val());
		$("#legalPersonIDType").attr("disabled",true);
	}
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
		setAuthorizer();
	});
}
/**
 * 初始化时添加外商投资企业法律文件送达授权委托书基本信息
 */
function addInitAttorneyDiv(supervisor,i){
	$.post("foreignControllerController/refreshAttorneyDiv.do",{
	}, function(responseText, status, xhr) {
		$("#attorneyDiv").append(responseText);			
		setInitAuthorizer(supervisor.AUTHORIZER);
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
		//授权人/委托方
		var AUTHORIZER = "";
    	$(this).find("[name$='AUTHORIZER'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  AUTHORIZER+=($(this).val()+",");
	          }
	    });
	    if(AUTHORIZER!=""){
	    	AUTHORIZER = AUTHORIZER.substring(0,AUTHORIZER.lastIndexOf(","));
	    }
		var DELEGATEE = $(this).find("[name$='DELEGATEE']").val();//被授权人
		var DELEGATEE_CONTRACTNAME = $(this).find("[name$='DELEGATEE_CONTRACTNAME']").val();//被授权人联系人名称/姓名
		var DELEGATEE_EMAIL = $(this).find("[name$='DELEGATEE_EMAIL']").val();//电子邮箱		
		var DELEGATEE_MOBILE = $(this).find("[name$='DELEGATEE_MOBILE']").val();	//移动电话
		var DELEGATEE_FIXEDLINE = $(this).find("[name$='DELEGATEE_FIXEDLINE']").val();//固定电话
		var DELEGATEE_ADDR = $(this).find("[name$='DELEGATEE_ADDR']").val();// 被授权人地址		
		var DELEGATEE_POSTCODE = $(this).find("[name$='DELEGATEE_POSTCODE']").val();// 邮政编码		
		if((null!=AUTHORIZER&&AUTHORIZER!="")||(null!=DELEGATEE&&DELEGATEE!="")){
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
				setAuthorizer();			
				FlowUtil.initFormObjValue(supervisors[i],$("#attorneyDiv div").eq(i));
			}
		}
	}else{
		setAuthorizer();
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
				var rmb=$("select[name='CURRENCY']").val();
				if("人民币"==rmb){
					$(this).find("[name$='FOLDING_RMB']").val(CONTRIBUTION.toFixed(6));	
				}else{
					$(this).find("[name$='FOLDING_RMB']").val('');
				}
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
	var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
	if(chk=='1'){
		$("#REG_CAPITAL").val($("input[name='REGISTERED_CAPITAL']").val());
	}
}
/**
 * 设置中外合资董事任命方
 */
function setInvestorName(){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	if(companySettype=='zwhz'){//中外合资	
		for(var j=0;j<$("#dsxxDiv table").length;j++){
			var array = new Array(); //定义数组			
			array.push("请选择任命方");
			$("#jwtzzxxDiv div").each(function(i){		
				var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//境外投资者名称
				if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
					array.push("外方投资者"+INVESTOR_NAME);			
				}
			});
			$("#zftzzxxDiv div").each(function(i){		
				var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//中方投资者名称
				if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
					array.push("中方投资者"+INVESTOR_NAME);				
				}
			});	
			//删除重复的任命方
			$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR'] option").each(function(){ //遍历全部option
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
				$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR'] option").each(function(){
					var txt = $(this).text(); //获取option的内容
					if(txt==array[k]){
						isok = false;						
					}
				});
				if(isok){
					if(array[k]=="请选择任命方"){
						$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR']")
						.append("<option value=''>"+array[k]+"</option>");						
					}else{
						$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR']")
						.append("<option value='"+array[k]+"'>"+array[k]+"</option>");							
					}
				}
			}
		}
		
	}
}
/**
 * 设置中外合资董事任命方
 */
function setInitInvestorName(val){
	var companySettype= $("input[name='COMPANY_SETTYPE']").val();//公司类型编码
	if(companySettype=='zwhz'){//中外合资	
		for(var j=0;j<$("#dsxxDiv table").length;j++){
			var array = new Array(); //定义数组			
			array.push("请选择任命方");
			$("#jwtzzxxDiv div").each(function(i){		
				var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//境外投资者名称
				if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
					array.push("外方投资者"+INVESTOR_NAME);			
				}
			});
			$("#zftzzxxDiv div").each(function(i){		
				var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//中方投资者名称
				if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
					array.push("中方投资者"+INVESTOR_NAME);				
				}
			});	
			array.push(val);
			//删除重复的任命方
			$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR'] option").each(function(){ //遍历全部option
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
				$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR'] option").each(function(){
					var txt = $(this).text(); //获取option的内容
					if(txt==array[k]){
						isok = false;						
					}
				});
				if(isok){
					if(array[k]=="请选择任命方"){
						$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR']")
						.append("<option value=''>"+array[k]+"</option>");						
					}else{
						$("#dsxxDiv table").eq(j).find("[name$='DIRECTOR_APPOINTOR']")
						.append("<option value='"+array[k]+"'>"+array[k]+"</option>");							
					}
				}
			}
		}
		
	}
}

function setAuthorizer(){
	for(var j=0;j<$("#attorneyDiv table").length;j++){
		var array = new Array(); //定义数组		
		$("#jwtzzxxDiv div").each(function(i){		
			var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//境外投资者名称
			if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
				array.push("外方投资者"+INVESTOR_NAME);			
			}
		});
		//删除重复
		$("#attorneyDiv table").eq(j).find(".AUTHORIZER_UL [name$='AUTHORIZER']").each(function(){ //遍历全部option
			var txt = $(this).val(); //获取option的内容
			var isok = true;
			for(var k=0;k<array.length;k++){						
				if(txt==array[k]){					
					isok = false;
				} 
			}
			if(isok){
				$(this).parent("li").remove();
			}
		});
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#attorneyDiv table").eq(j).find(".AUTHORIZER_UL [name$='AUTHORIZER']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#attorneyDiv table").eq(j).find(".AUTHORIZER_UL")
					.append("<li><input name=\""+j+"AUTHORIZER\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}
}
function setInitAuthorizer(val){
	for(var j=0;j<$("#attorneyDiv table").length;j++){
		var array = new Array(); //定义数组	
		$("#jwtzzxxDiv div").each(function(i){		
			var INVESTOR_NAME = $(this).find("[name$='INVESTOR_NAME']").val();//境外投资者名称
			if(INVESTOR_NAME!=null&&INVESTOR_NAME!=''){
				array.push("外方投资者"+INVESTOR_NAME);			
			}
		});
		array.push(val);
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#attorneyDiv table").eq(j).find(".AUTHORIZER_UL [name$='AUTHORIZER']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#attorneyDiv table").eq(j).find(".AUTHORIZER_UL")
					.append("<li><input name=\""+j+"AUTHORIZER\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}
}

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
 * 预览附件
 * @param {} fileId
 */
function previewFile(fileId){
	//获取流程信息对象JSON
	var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
	if(EFLOW_FLOWOBJ){
		//将其转换成JSON对象
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		//console.log(EFLOW_FLOWOBJ);
		//初始化表单字段值
		if(eflowObj.busRecord){			
			window.open(__ctxPath+"/domesticControllerController/pdfPreview.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
		}else{
			art.dialog({
				content : "无法预览",
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
				busTableName:"T_COMMERCIAL_FOREIGN"
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
