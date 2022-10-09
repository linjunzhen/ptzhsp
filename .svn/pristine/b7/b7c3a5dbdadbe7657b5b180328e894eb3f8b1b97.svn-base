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
			FlowUtil.initFormFieldValue(eflowObj.busRecord,"OTHER_FORM");
			
			//初始化是否已通过名称自主选用
			setPreapprovalPass(eflowObj.busRecord.IS_PREAPPROVAL_PASS);
			
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
		}else{
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

	$("input[name='OPRRATE_TERM_TYPE']").click(function(){
		var Value = $(this).val();
		if(Value==1){
			$("input[name='BUSSINESS_YEARS']").attr("disabled",false);
		}else{
			$("input[name='BUSSINESS_YEARS']").attr("disabled",true);
		}
	});
	
	$("input[name='SUBORDINATE_LOCATION']").click(function(){
		var Value = $(this).val();
		if(Value==1){
			$("#locationInfo").attr("style","display:none;");
		}else{
			$("#locationInfo").attr("style","");
		}
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
			
			$("#INVESTMENT").removeClass(" validate[required,custom[JustNumber]]");
		}
	});
	$("#legalPersonIDType").attr("disabled",true);
	$("input[name='BANK_TYPE']").click(function(){
		var Value = $(this).val();
		setBankInfo(Value);
	});
	$("input[name='BRANCH_NAME']").bind('input propertychange', function() {
		var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
		if(chk=='1'){
			$("input[name='DEPOSITOR']").val($(this).val());
			$("input[name='ACCOUNT_NAME']").val($(this).val());
		}
	}); 
	$("input[name='BUSINESS_PLACE']").bind('input propertychange', function() {
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
		$("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/开立单位银行结算账户申请书.docx");
	}else if(Value=='boc'){
		$("input[name='BANK_NAME']").val("中国银行股份有限公司平潭金井湾支行");
		$("input[name='BANK_CODE']").val("104391012065");
		$("#boclist").attr("style","");
		$("#rcblist").attr("style","display:none;");
		$("#abclist").attr("style","display:none;");
		$("#ccblist").attr("style","display:none;");
		$("#pdblist").attr("style","display:none;");
		$("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/中国银行开立单位银行结算账户申请书.docx");
	}else if(Value=='abc'){
		$("input[name='BANK_NAME']").val("中国农业银行股份有限公司平潭综合实验区支行");
		$("input[name='BANK_CODE']").val("103391016513");
		$("#abclist").attr("style","");
		$("#boclist").attr("style","display:none;");
		$("#rcblist").attr("style","display:none;");
		$("#ccblist").attr("style","display:none;");
		$("#pdblist").attr("style","display:none;");
		$("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/中国农业银行开立单位银行结算账户申请书.docx");
	}else if(Value=='rcb'){
		$("input[name='BANK_NAME']").val("福建平潭农村商业银行股份有限公司");
		$("input[name='BANK_CODE']").val("402391017047");
		$("#rcblist").attr("style","");
		$("#ccblist").attr("style","display:none;");
		$("#boclist").attr("style","display:none;");
		$("#abclist").attr("style","display:none;");
		$("#pdblist").attr("style","display:none;");
		$("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/平潭农村商业银行开立单位银行结算账户申请书.docx");
	}else if(Value=='pdb'){
		$("input[name='BANK_NAME']").val("上海浦东发展银行福州平潭分行");
		$("input[name='BANK_CODE']").val("310391000053");
		$("#pdblist").attr("style","");
		$("#ccblist").attr("style","display:none;");
		$("#boclist").attr("style","display:none;");
		$("#abclist").attr("style","display:none;");
		$("#rcblist").attr("style","display:none;");
		$("#templateLoad").find("a").attr("href","webpage/website/applyforms/bankTemplate/上海浦东发展银行开立单位银行结算账户申请书.docx");
	}
	$("input[name='DEPOSITOR']").val($("input[name='BRANCH_NAME']").val());
	$("input[name='DEPOSITOR_ADDR']").val($("input[name='BUSINESS_PLACE']").val());
	$("input[name='ACCOUNT_NAME']").val($("input[name='BRANCH_NAME']").val());
	$("#businessScope").val($("[name='BUSSINESS_SCOPE']").val());
	$("#INDUSTRY_CATEGORY_CDOE").val($("input[name='INDUSTRY_CATEGORY']").val());
	$("#INVESTMENT").addClass(" validate[required,custom[JustNumber]]");
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
		 //获取房地产经纪机构及其分支机构备案JSON
		 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
		 //广告发布单位广告从业、审查人员JSON
		 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
		 //工程造价咨询企业设立分支机构备案JSON
		 flowSubmitObj.GCZJZXQYSL_JSON =getGczjzxqyslJson();
		 
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
	 //获取房地产经纪机构及其分支机构备案JSON
	 flowSubmitObj.FDCJJJG_JSON =getFdcjjjgJson();
	 //广告发布单位广告从业、审查人员JSON
	 flowSubmitObj.GGFBDW_JSON =getGgfbdwJson();
	 //工程造价咨询企业设立分支机构备案JSON
	 flowSubmitObj.GCZJZXQYSL_JSON =getGczjzxqyslJson();
	 
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

