var isRemove = "0";
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
			//初始化基本信息
			$("#INFO_FORM input[name='PROJECTCODE']").val(eflowObj.busRecord.PROJECTCODE);
			$("#INFO_FORM input[name='PROJECT_CODE']").val(eflowObj.busRecord.PROJECT_CODE);
			$("#INFO_FORM input[name='SCBJSJ']").val(eflowObj.busRecord.SCBJSJ);
			$("#INFO_FORM input[name='SUBDATE']").val(eflowObj.busRecord.SUBDATE);

			$("[name='APPLYUNITREMARK']").val(eflowObj.busRecord.APPLYUNITREMARK);
			//初始化危险源信息						
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#wxy_div"));		
			//初始化申报人信息						
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#USERINFO_FORM"));
			//初始化单位工程信息
			//setDwgc(eflowObj.busRecord.DWGC_JSON);
			
			changeProType(eflowObj.busRecord.PROTYPE);
	
			//初始化字段审核意见
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){	
				setFieldAudit(eflowObj.EFLOW_EXEID);	
				loadTZXMXXData();
			}else{					
				if(!eflowObj.busRecord.JLDW_JSON){									
					$("#jldwDiv").html("");
				}
				if(!eflowObj.busRecord.KCDW_JSON){	
					$("#kcdwDiv").html("");
				}
			}
			if(eflowObj.busRecord.RUN_STATUS==2){
				
			}
			if(eflowObj.busRecord.SGXK_SBXMLX=='2'){
				$("#zjgcDiv").show();
				addZjgcClass();
			}else{
				setZjgcSingleProMaintype('01','');
				removeZjgcClass();
			}
			
			if(eflowObj.busRecord.SGXK_SBXMLX=='3'){
				$("[name='SGXK_SBXMLX']").attr("disabled",true);
				$("#sbgcDiv").show();
				addSbgcClass();
			}else{
				setSbgcSingleProMaintype('01','');
				$("#sbgcDiv").hide();
				removeSbgcClass();
			}
			
		}else{
			
			//获取事项编码
			var itemCode = $("#RITEMCODE").val();
			if(itemCode=='345071904GF02704'){//上部工程事项
				$("#SGXK_SBXMLX3").attr("checked","checked"); 
				$("[name='SGXK_SBXMLX']").attr("disabled",true);
				$("#sbgcDiv").show();
				setSbgcSingleProMaintype('01','');
				addSbgcClass();
			}else{
				setSbgcSingleProMaintype('01','');
				$("#sbgcDiv").hide();
				removeSbgcClass();
			}
			
			setSingleProMaintype('01','');
			setZjgcSingleProMaintype('01','');
			
			//$("[name='PROTYPE']").val('01');
			changeProType('01');
			$("[name='SINGLEPRONUM']").val(1);
			initJlryDiv();
			var code = $("input[name='PROJECT_CODE']").val();
			if(code!=null && code!=''){
				loadTZXMXXData();		
			}
			//loadPRJNUM();
			removeZjgcClass();
		}
	}	
	$("#sgdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#jldwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#kcdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#sjdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#sgtscdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#kzjdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#jcdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	$("#zbdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	
	$("#SGXKJBXX_FORM [name='sgxkgclx']").attr("disabled",true);
	$("#SGXKJBXX_FORM [name='sgxkjglx']").attr("disabled",true);
	setBuildUnits();
	setSupervisionUnits();
	
	 $(":checkbox").click(function(){
        $(this).attr("checked",true);//设置当前选中checkbox的状态为checked
        $(this).siblings().attr("checked",false); //设置当前选中的checkbox同级(兄弟级)其他checkbox状态为未选中
    });
	
	$('#dwgcDiv').find("[name$='STRUCTQUALTYPE'][type='checkbox']").each(function() {
		var name = $(this).attr("name");
		$(this).click(function() {
			if ($(this).is(':checked')) {
				$('#dwgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			} else {
				$('#dwgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			}
			setDwgcInfo('')
		});
	});
	$('#zjgcDiv').find("[name$='STRUCTQUALTYPE_ZJGC'][type='checkbox']").each(function() {
		var name = $(this).attr("name");
		$(this).click(function() {
			if ($(this).is(':checked')) {
				$('#zjgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			} else {
				$('#zjgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			}
		});
	});
	$('#sbgcDiv').find("[name$='STRUCTQUALTYPE_SBGC'][type='checkbox']").each(function() {
		var name = $(this).attr("name");
		$(this).click(function() {
			if ($(this).is(':checked')) {
				$('#sbgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			} else {
				$('#sbgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
			}
		});
	});
	
	
	var projectCode =$("[name='PROJECT_CODE']").val();
	console.log("projectCode："+projectCode);
	if(projectCode.search("-88-") != -1){
		//装修工程不验证监理单位
		$("#jldwDiv").find("[name$='CORPNAME']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='CORPCREDITCODE']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='QUALLEVEL']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='QUALCERTNO']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='ORGCODE']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='LEGAL_NAME']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='LEGAL_IDTYPE']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='SGDW_LEGAL_IDNO']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='PERSONNAME']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='PERSONPHONE']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='IDCARDTYPENUM']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='SGDW_PERSONIDCARD']").removeClass(" validate[required],custom[vidcard]");
		$("#jldwDiv").find("[name$='PERSONQUALLEVEL']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='PERSONCERT']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='JLDW_PERSONPHOTO']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='CONTRACTNUMBER']").removeClass(" validate[required]");
		$("#jldwDiv").find("[name$='CONTRACTCOST']").removeClass(" validate[required],custom[JustNumber]");
		$("#jldwDiv").find("[name$='BIDDINGDATE']").removeClass(" validate[required]");
		
		//装修工程不验证勘察单位
		$("#kcdwDiv").find("[name$='CORPNAME']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='CORPCREDITCODE']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='QUALLEVEL']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='QUALCERTNO']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='ORGCODE']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='LEGAL_NAME']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='LEGAL_IDTYPE']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='KCDW_LEGAL_IDNO']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='PERSONNAME']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='PERSONPHONE']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='IDCARDTYPENUM']").removeClass(" validate[required]");
		$("#kcdwDiv").find("[name$='KCDW_PERSONIDCARD']").removeClass(" validate[required],custom[vidcard]");
		$("#kcdwDiv").find("[name$='SPECIALTYTYPNUM']").removeClass(" validate[required]");
	}

	
});

function setSgxkjbxx(){
	$("#PRONAME").val($("[name='TITLE']").val());
	$("#BELONG").val($("[name='PROADDRESS']").val());
	$("#planBegDate").val($("[name='PLANBEGDATE']").val());
	$("#planEndDate").val($("[name='PLANENDDATE']").val());
	$("#workDays").val($("[name='WORKDAYS']").val());
	$("#proCost").val($("[name='PROCOST']").val());
	$("#PURIDJS").val($("[name='PRJSIZE']").val());
	$("#proArea").val($("[name='PROAREA']").val());
	$("#structUpfloorNum").val($("[name='STRUCTUPFLOORNUMS']").val());
	$("#muniLength").val($("[name='MUNILENGTHS']").val());
	$("#structDwfloorNum").val($("[name='STRUCTDWFLOORNUMS']").val());
	
	$("#SGXKJBXX_FORM [name='sgxkgclx']").val($("[name='PRJFUNCTIONNUM']").val());
	
	$("#SGXKJBXX_FORM [name='sgxkjglx']").val($("[name='STRUCTQUALTYPES']").val());
	
	var BUILDCORPNAME = "";
	var BUILDCORPCODE = "";
	var BUILDLEGALNAME = "";
	var BUILDPERSONNAME = "";
	$("#JsdwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();
		if(BUILDCORPNAME!=null && BUILDCORPNAME!=""){
			BUILDCORPNAME += ","+CORPNAME;
		} else{
			BUILDCORPNAME += CORPNAME;
		}
		if(BUILDCORPCODE!=null && BUILDCORPCODE!=""){
			BUILDCORPCODE += ","+CORPCREDITCODE;
		} else{
			BUILDCORPCODE += CORPCREDITCODE;
		}
		if(BUILDLEGALNAME!=null && BUILDLEGALNAME!=""){
			BUILDLEGALNAME += ","+LEGAL_NAME;
		} else{
			BUILDLEGALNAME += LEGAL_NAME;
		}
		if(BUILDPERSONNAME!=null && BUILDPERSONNAME!=""){
			BUILDPERSONNAME += ","+PERSONNAME;
		} else{
			BUILDPERSONNAME += PERSONNAME;
		}
	});
	$("[name='BUILDCORPNAME']").val(BUILDCORPNAME);
	$("[name='BUILDCORPCODE']").val(BUILDCORPCODE);
	$("#CONUNIT1").val(BUILDCORPNAME);
	$("#CONORGCODE").val(BUILDCORPCODE);
	$("[name='LEGALPERSON']").val(BUILDLEGALNAME);
	$("[name='CONPERSON']").val(BUILDPERSONNAME);
	
	
	var html = "";
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();
		var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();
		html +='<tr class="sgxkjbxxTr">';
		html +='<td style="text-align: center;">施工单位</td>';
		html +='<td style="text-align: center;">'+CORPNAME+'</td>';
		html +='<td style="text-align: center;">'+LEGAL_NAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONNAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONPHONE+'</td>';
		html +='</tr>';
	});
	$("#sjdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();
		var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();
		html +='<tr class="sgxkjbxxTr">';
		html +='<td style="text-align: center;">设计单位</td>';
		html +='<td style="text-align: center;">'+CORPNAME+'</td>';
		html +='<td style="text-align: center;">'+LEGAL_NAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONNAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONPHONE+'</td>';
		html +='</tr>';
	});
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();
		var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();
		html +='<tr class="sgxkjbxxTr">';
		html +='<td style="text-align: center;">监理单位</td>';
		html +='<td style="text-align: center;">'+CORPNAME+'</td>';
		html +='<td style="text-align: center;">'+LEGAL_NAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONNAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONPHONE+'</td>';
		html +='</tr>';
	});
	$("#kcdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();
		var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();
		html +='<tr class="sgxkjbxxTr">';
		html +='<td style="text-align: center;">勘察单位</td>';
		html +='<td style="text-align: center;">'+CORPNAME+'</td>';
		html +='<td style="text-align: center;">'+LEGAL_NAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONNAME+'</td>';
		html +='<td style="text-align: center;">'+PERSONPHONE+'</td>';
		html +='</tr>';
	});
	$(".sgxkjbxxTr").remove();
	$("#sgxkjbxxTable").append(html);
}
function loadPRJNUM(){
	$.post("projectSgxkController/getPrjCode.do",{XZQHCode:'350128',codetype:1},
		function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if (resultJson.status) {
				$("[name='PRJNUM']").val(resultJson.code);			
			} else {
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		}
	);
}
function loadTZXMXXData(){
	var code = $("input[name='PROJECT_CODE']").val();
	if(null==code||''==code){
		art.dialog({
			content: "请填写投资项目编号",
			icon:"error",
			ok: true
		});
	}else{
		var layload = layer.load('正在提交校验中…');
		$.post("projectApplyController.do?loadTzxmxxData",{
			projectCode:code},
			function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				console.log(resultJson);
				if (resultJson.result) {
					for(var key in resultJson.datas){						
						$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);	
						if(key.toUpperCase()=='PROJECT_NAME'){
							//申报项目名称字段修改为由业主来填写
							//$("[name='TITLE']").val(resultJson.datas[key]);
						}else if(key.toUpperCase()=='REPORT_CODE'){	
							if(resultJson.datas[key]){								
								$("[name='PRJNUM']").val(resultJson.datas[key]);
							} else {								
								$("[name='PRJNUM']").attr("readonly",false);
							}
						}						
					}	
					$("[name='PROJECT_NUM']").val(code);
					if(code.substring(15, 17)=="88"){
						//获取流程信息对象JSON
						var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
						if(EFLOW_FLOWOBJ){
							//将其转换成JSON对象
							var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
							//初始化表单字段值
							if(eflowObj.busRecord){	
								if(!eflowObj.busRecord.JLDW_JSON){									
									$("#jldwDiv").html("");
								}
								if(!eflowObj.busRecord.KCDW_JSON){	
									$("#kcdwDiv").html("");
								}
							} else{											
								$("#jldwDiv").html("");
								$("#kcdwDiv").html("");
							}
						}
					}
				} else {
					art.dialog({
						content: "校验失败",
						icon:"error",
						ok: true
					});
				}
			}
		);
	}
};
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
function showOrHide(o,id){
	var html = $(o).html();
	if(html=='收 起'){		
		$(o).html("展 开");
		$("#"+id).hide();
	} else {			
		$(o).html("收 起");
		$("#"+id).show();
	}
	
}

//提交流程按钮
function FLOW_SubmitFun() {
	
	var proType=$('input:radio[name="SGXK_SBXMLX"]:checked').val();
	if(proType){
	}else{
		art.dialog({
			content : "请选择申报项目类型！",
			icon : "warning",
			ok : true
		});
		return;
	}
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	var num = $("[name='NUM']").val();
	if(num){
		if($("#dwgcDiv").children("div").length>num){
			art.dialog({
				content : "单位工程不能大于本次申报单位工程个数!",
				icon : "warning",
				ok : true
			});
			return;
		}
	}
	var isuploadPhoto = false
	$("[name$='PERSONPHOTO']").each(function(i){
		var val = $(this).val();
		if(val==null||val==''){
			isuploadPhoto = true;
		}
	});
	if(isuploadPhoto){
		art.dialog({
			content : "请上传责任主体信息中的电子照片！",
			icon : "warning",
			ok : true
		});
		return;
	}	
	var isok = false;
	$("#dwgcDiv").children("div").each(function(i){
		var SINGLEPROMAINTYPE = $(this).find("[name$='SINGLEPROMAINTYPE']").val();//单位工程类别
		var SINGLEPROTYPE = $(this).find("[name$='SINGLEPROTYPE']").val();//单位工程类别
		var SINGLEPROSUBTYPE = $(this).find("[name$='SINGLEPROSUBTYPE']").val();//单位工程类别
		if(SINGLEPROMAINTYPE && SINGLEPROTYPE && SINGLEPROSUBTYPE){
			isok = true;
		}
	});	
	if(!isok){
		art.dialog({
			content : "请把单位工程类别填写完整！",
			icon : "warning",
			ok : true
		});
		return;
	}	
	

	
	//验证表单是否合法
	var valiateTabForm = AppUtil.validateWebsiteTabForm();
	if (valiateTabForm) {
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(-1);
		if(submitMaterFileJson==null){
			return false;
		}
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
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
		//获取施工图审查合格证书编号信息
		flowSubmitObj.CHARTREVIEWNUM_JSON=getChartreviewnumJson();
		//获取建设单位信息
		flowSubmitObj.JSDW_JSON=getJsdwJson();
		//获取代建单位信息
		flowSubmitObj.DJDW_JSON=getDjdwJson();
		//获取施工单位信息
		flowSubmitObj.SGDW_JSON=getSgdwJson();
		//获取监理单位信息
		flowSubmitObj.JLDW_JSON=getJldwJson();	
		//获取勘察单位信息
		flowSubmitObj.KCDW_JSON=getKcdwJson();	
		//获取设计单位信息
		flowSubmitObj.SJDW_JSON=getSjdwJson(); 
		//获取设施工图审查单位信息
		flowSubmitObj.SGTSCDW_JSON=getSgtscdwJson();
		//控制价（预算价）计价文件编制单位信息
		flowSubmitObj.KZJDW_JSON=getKzjdwJson();		 
		//检测单位信息
		flowSubmitObj.JCDW_JSON=getJcdwJson();
		//招标代理单位
		flowSubmitObj.ZBDW_JSON=getZbdwJson();
		//单位工程
		flowSubmitObj.DWGC_JSON=getDwgcJson();
		//桩基工程
		flowSubmitObj.ZJGC_JSON=getZjgcJson();
		//上部工程
		flowSubmitObj.SBGC_JSON=getSbgcJson();
		
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
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
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
	 
	//获取施工图审查合格证书编号信息
	flowSubmitObj.CHARTREVIEWNUM_JSON=getChartreviewnumJson();
	//获取建设单位信息
	flowSubmitObj.JSDW_JSON=getJsdwJson();
	//获取代建单位信息
	flowSubmitObj.DJDW_JSON=getDjdwJson();
	//获取施工单位信息
	flowSubmitObj.SGDW_JSON=getSgdwJson();
	//获取监理单位信息
	flowSubmitObj.JLDW_JSON=getJldwJson();
	//获取勘察单位信息
	flowSubmitObj.KCDW_JSON=getKcdwJson();
	//获取设计单位信息
	flowSubmitObj.SJDW_JSON=getSjdwJson();
	//获取设施工图审查单位信息
	flowSubmitObj.SGTSCDW_JSON=getSgtscdwJson();
	//控制价（预算价）计价文件编制单位信息
	flowSubmitObj.KZJDW_JSON=getKzjdwJson();
	//检测单位信息
	flowSubmitObj.JCDW_JSON=getJcdwJson();
	//招标代理单位
	flowSubmitObj.ZBDW_JSON=getZbdwJson();
	//单位工程
	flowSubmitObj.DWGC_JSON=getDwgcJson();
	//桩基工程
	flowSubmitObj.ZJGC_JSON=getZjgcJson();	
	//上部工程
	flowSubmitObj.SBGC_JSON=getSbgcJson();
	 
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


/**
* 标题附件上传对话框
*/
function openPthotoFileUploadDialog(id,name){
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
					$("#"+id).attr("src",__file_server + uploadedFileInfo.filePath);
					$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
				}else{
					$("#"+id).attr("src",'<%=path%>/webpage/common/images/nopic.jpg');
					$("input[name='"+name+"']").val('');
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}


/**
 * 删除DIV
 */
function delClosestDiv(o){
	art.dialog.confirm("您确定要删除该记录吗?", function() {	
		$(o).closest("div").remove();
		setDwgcInfo('');
	});
} 
/**
 * 删除Tr
 */
function delClosestTr(o){
	art.dialog.confirm("您确定要删除该记录吗?", function() {		
		$(o).closest("tr").remove();		
		setDwgcInfo('');
	});
} 

var gcyh1Html = "";
var gcyh2Html = "";
function changeProType(val){
	if(val=='01'){
		$("[name='ISGARDEN']").attr("disabled",true);
		$("[name='ISGARDEN']").removeAttr('checked');
		if(gcyh2Html==null||gcyh2Html==''){			
			gcyh2Html = $("#gcyh2").html();
		}
		$("#gcyh2").html("");
		$("#gcyh2").hide();
		$("#gcyh1").show();
		
		$(".pro_area_2").hide();
		$(".pro_area_1").show();
		
		if(gcyh1Html!=null && gcyh1Html!=""){
			$("#gcyh1").html(gcyh1Html);
		}
	}else{		
		$("[name='ISGARDEN']").attr("disabled",false);
		if(gcyh1Html==null||gcyh1Html==''){			
			gcyh1Html = $("#gcyh1").html();
		}
		$("#gcyh1").html("");
		$("#gcyh1").hide();
		$("#gcyh2").show();
		
		$(".pro_area_1").hide();
		$(".pro_area_2").show();
		if(gcyh2Html!=null && gcyh2Html!=""){
			$("#gcyh2").html(gcyh2Html);
		}
	}
}
function clearDwgcType(){	
	/* $("#dwgcDiv").children("div").each(function(i){
		var currentTime = $(this).find("[name='currentTime']").val();	
		if(currentTime){	
			$('#'+currentTime+'singlepromaintype').combobox('clear');
			$('#'+currentTime+'singleprotype').combobox('clear');
			$('#'+currentTime+'singleprosubtype').combobox('clear');
		} else{			
			$('#singlepromaintype').combobox('clear');
			$('#singleprotype').combobox('clear');
			$('#singleprosubtype').combobox('clear');
		}
	});	 */
}
function setSingleProMaintype(val,i){
	if(val=='01'){
		$("."+i+"singleProMaintype_2").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2").find("input,select").val('');
		$("."+i+"singleProMaintype_2").hide();
		$("."+i+"singleProMaintype_1").show();		
		$("."+i+"singleProMaintype_1").find("input,select").attr("disabled",false);
		
		$("."+i+"singleProMaintype_2_jglx").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2_jglx").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2_jglx").hide();
		$("."+i+"singleProMaintype_1_jglx").show();		
		$("."+i+"singleProMaintype_1_jglx").find("input,select").attr("disabled",false);
		
		
	}else{		
		$("."+i+"singleProMaintype_1").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1").find("input,select").val('');
		$("."+i+"singleProMaintype_1").hide();
		$("."+i+"singleProMaintype_2").show();
		$("."+i+"singleProMaintype_2").find("input,select").attr("disabled",false);
		
		
		$("."+i+"singleProMaintype_1_jglx").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1_jglx").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1_jglx").hide();
		$("."+i+"singleProMaintype_2_jglx").show();		
		$("."+i+"singleProMaintype_2_jglx").find("input,select").attr("disabled",false);
	}
}

function setZjgcSingleProMaintype(val,i){
	if(val=='01'){
		$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2_zjgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2_zjgc").find("input,select").val('');
		$("."+i+"singleProMaintype_2_zjgc").hide();
		$("."+i+"singleProMaintype_1_zjgc").show();		
		$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",false);
		
		$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2_jglx_zjgc").hide();
		$("."+i+"singleProMaintype_1_jglx_zjgc").show();		
		$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").attr("disabled",false);
		
		
	}else{		
		$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1_zjgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1_zjgc").find("input,select").val('');
		$("."+i+"singleProMaintype_1_zjgc").hide();
		$("."+i+"singleProMaintype_2_zjgc").show();
		$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",false);
		
		
		$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1_jglx_zjgc").hide();
		$("."+i+"singleProMaintype_2_jglx_zjgc").show();		
		$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").attr("disabled",false);
	}
}

function setSbgcSingleProMaintype(val,i){
	if(val=='01'){
		$("."+i+"singleProMaintype_2_sbgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2_sbgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2_sbgc").find("input,select").val('');
		$("."+i+"singleProMaintype_2_sbgc").hide();
		$("."+i+"singleProMaintype_1_sbgc").show();		
		$("."+i+"singleProMaintype_1_sbgc").find("input,select").attr("disabled",false);
		
		$("."+i+"singleProMaintype_2_jglx_sbgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2_jglx_sbgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2_jglx_sbgc").hide();
		$("."+i+"singleProMaintype_1_jglx_sbgc").show();		
		$("."+i+"singleProMaintype_1_jglx_sbgc").find("input,select").attr("disabled",false);
		
		
	}else{		
		$("."+i+"singleProMaintype_1_sbgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1_sbgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1_sbgc").find("input,select").val('');
		$("."+i+"singleProMaintype_1_sbgc").hide();
		$("."+i+"singleProMaintype_2_sbgc").show();
		$("."+i+"singleProMaintype_2_sbgc").find("input,select").attr("disabled",false);
		
		
		$("."+i+"singleProMaintype_1_jglx_sbgc").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1_jglx_sbgc").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1_jglx_sbgc").hide();
		$("."+i+"singleProMaintype_2_jglx_sbgc").show();		
		$("."+i+"singleProMaintype_2_jglx_sbgc").find("input,select").attr("disabled",false);
	}
}



 function setSingleprosubtype(rec,index){
   $('#'+index+'singleprosubtype').combobox('clear');
   if(rec.VALUE){
	   var url = 'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+rec.VALUE;
	   if(rec.VALUE=='100'||rec.VALUE=='300'||rec.VALUE=='900'){
		   $('#'+index+'singleprosubtype').combobox('reload',url); 
	   }else{
		   var data, json;
			json = '[{"VALUE":"'+rec.TEXT+'","TEXT":"'+rec.TEXT+'","selected":true}]';
			data = $.parseJSON(json);
		   $('#'+index+'singleprosubtype').combobox('loadData',data);  
	   }
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
//选择证件类型为身份证时添加证件号码验证
function setSgxkZjValidate(zjlx,name){
	if(zjlx=="1"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
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
* 输入数字且小数点之后只能出现3位
**/
function onlyNumber3(obj){       
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
	//只能输入小数点后三位
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加  
	if(t == '-'){  
		obj.value = '-'+obj.value;  
	}   
}  
/**
* 输入数字且小数点之后只能出现2位
**/
function onlyNumber2(obj){       
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
	//只能输入小数点
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加  
	if(t == '-'){  
		obj.value = '-'+obj.value;  
	}   
}

/**
 * 输入数字且小数点之后只能出现6位
 **/
function onlyNumber6(obj){
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
	//只能输入小数点
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加
	if(t == '-'){
		obj.value = '-'+obj.value;
	}
}
//计算时间差
function GetDateDiff(startDate,endDate)  
{  
	var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
	var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
	var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
	return  dates;    
}
//获取合同工期
function getWorkDays(){
	if($("#PLANBEGDATE").val()==null||$("#PLANBEGDATE").val()=="")
		return;
	if($("#PLANENDDATE").val()==null||$("#PLANENDDATE").val()=="")
		return;
	$("#WORKDAYS").val(GetDateDiff($("#PLANBEGDATE").val(),$("#PLANENDDATE").val()) +"天");
}




/*************施工图审查合格证书编号信息JS开始****************/
/**
 * 添加施工图审查合格证书编号信息
 */
function addChartreviewnumDiv(){
	$.post("projectSgxkController/refreshChartreviewnumDiv.do",{
	}, function(responseText, status, xhr) {
		$("#chartreviewnumDiv").append(responseText);
	});
}

/**
 * 删除施工图审查合格证书编号信息
 */
function delChartreviewnumDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取施工图审查合格证书编号信息
 */
function getChartreviewnumJson(){
	var ChartreviewnumArray = [];
	$("#chartreviewnumDiv").children("div").each(function(i){
		var CHARTREVIEWNUM = $(this).find("[name$='CHARTREVIEWNUM']").val();//姓名
		if(null!=CHARTREVIEWNUM&&CHARTREVIEWNUM!=""){
			var Chartreviewnum = {};
			Chartreviewnum.CHARTREVIEWNUM = CHARTREVIEWNUM;	
			ChartreviewnumArray.push(Chartreviewnum);	
		}
		
	});	
	if(ChartreviewnumArray.length>0){
		return JSON.stringify(ChartreviewnumArray);
	}else{
		return "";
	}
}
/*************施工图审查合格证书编号信息JS结束****************/


/*************建设单位信息JS开始****************/
/**
 * 添加建设单位信息
 */
function addJsdwDiv(){
	$.post("projectSgxkController/refreshJsdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#JsdwDiv").append(responseText);
	});
}

/**
 * 删除建设单位信息
 */
function delJsdwDiv(o){
	$(o).closest("div").remove();
	setSgxkjbxx();
} 

/**
 * 获取建设单位信息
 */
function getJsdwJson(){
	var infoArray = [];
		$("#JsdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}


/**
 * 
 */
function setJsdwInfo(){
	var BUILDCORPNAME = "";
	var BUILDCORPCODE = "";
	$("#JsdwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();
		if(BUILDCORPNAME!=null && BUILDCORPNAME!=""){
			BUILDCORPNAME += ","+CORPNAME;
		} else{
			BUILDCORPNAME += CORPNAME;
		}
		if(BUILDCORPCODE!=null && BUILDCORPCODE!=""){
			BUILDCORPCODE += ","+CORPCREDITCODE;
		} else{
			BUILDCORPCODE += CORPCREDITCODE;
		}
	});
	$("[name='BUILDCORPNAME']").val(BUILDCORPNAME);
	$("[name='BUILDCORPCODE']").val(BUILDCORPCODE);
}
/*************建设单位信息JS结束****************/




/*************代建单位信息JS开始****************/
/**
 * 添加代建单位信息
 */
function addDjdwDiv(){
	$.post("projectSgxkController/refreshDjdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#DjdwDiv").append(responseText);
	});
}

/**
 * 删除代建单位信息
 */
function delDjdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取代建单位信息
 */
function getDjdwJson(){
	var infoArray = [];
		$("#DjdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var UNITTYPE = $(this).find("[name$='UNITTYPE']").val();//单位类型
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.UNITTYPE = UNITTYPE;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************代建单位信息JS结束****************/




/*************施工单位信息JS开始****************/

/**
 * 获取施工单位信息
 */
function getSgdwJson(){
	var infoArray = [];
	$("#sgdwDiv").children("div").each(function(i){
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
		var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
		var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
		var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
		var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
		var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
		var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
		var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
		var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
		var PERSONQUALLEVEL = $(this).find("[name$='PERSONQUALLEVEL']").val();//执业资格等级
		var PERSONCERT = $(this).find("[name$='PERSONCERT']").val();//负责人执业资格证书号
		var CERTNUM = $(this).find("[name$='CERTNUM']").val();//安全生产考核合格证书编号
		var PERSONPHOTO = $(this).find("[name$='PERSONPHOTO']").val();//电子照片
		var SECTION = $(this).find("[name$='SECTION']").val();//标段名称
		var CONTRACTNUMBER = $(this).find("[name$='CONTRACTNUMBER']").val();//合同编号
		if(null!=CORPNAME && CORPNAME!=""){
			var info = {};
			info.CORPNAME = CORPNAME;	
			info.CORPCREDITCODE = CORPCREDITCODE;	
			info.ORGCODE = ORGCODE;	
			info.LEGAL_NAME = LEGAL_NAME;	
			info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
			info.LEGAL_IDNO = LEGAL_IDNO;	
			info.PERSONNAME = PERSONNAME;	
			info.PERSONPHONE = PERSONPHONE;	
			info.IDCARDTYPENUM = IDCARDTYPENUM;	
			info.PERSONIDCARD = PERSONIDCARD;	
			info.PERSONQUALLEVEL = PERSONQUALLEVEL;	
			info.PERSONCERT = PERSONCERT;	
			info.CERTNUM = CERTNUM;	
			info.PERSONPHOTO = PERSONPHOTO;	
			info.SECTION = SECTION;	
			info.CONTRACTNUMBER = CONTRACTNUMBER;	
			var sgryArray = [];
			if($("#SGRY_FORM").children("div").length>0){				
				$("#SGRY_FORM").children("div").eq(i).each(function(i){				
					$(this).find("[name='sgryxx']").each(function(){
						var val = $(this).val();
						if(val){						
							var sgryxx = JSON.parse(val);
							sgryxx.SGRY = val;
							sgryArray.push(sgryxx);
						}
					});
				});
				if(sgryArray.length>0){
					info.SGRY = sgryArray;
				}
			} else{				
				var sgry = {};
				sgry.STATION = PERSONNAME;//姓名
				sgry.PERSONNAME = "项目负责人";//工作岗位
				sgry.IDCARDTYPENUM = IDCARDTYPENUM;//证件类型
				sgry.PERSONIDENTITY = PERSONIDCARD;//身份证号码
				sgry.PERSONCERTID = PERSONCERT;//证书编号
				sgry.OBTAINEDYEAR = "";//从业年限
				sgry.CERTISSUINGORGAN = "";//证书发证机关
				sgry.PERSONTITLE = "";//职称
				sgry.PERSONTEL = "";//联系电话
				sgry.PERSONPROF = "";//专业
				sgry.SGRY = JSON.stringify(sgry);
				sgryArray.push(sgry);
				info.SGRY = JSON.stringify(sgryArray);
			}
			
			
			infoArray.push(info);	
		}
		
	});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}
function setSgdwInfo(){
	var SGUNITS = "";
	$("#sgdwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();
		if(CORPNAME!=null && CORPNAME!=""){
			if(SGUNITS!=null && SGUNITS!=""){				
				SGUNITS += ","+CORPNAME;
			} else{				
				SGUNITS += CORPNAME;
			}
			if(CORPCREDITCODE!=null && CORPCREDITCODE!=""){
				SGUNITS +="（"+CORPCREDITCODE+"）";
			}
		} 
	});
	$("[name='SGUNITS']").val(SGUNITS);
}
function setContractNumber(val,i){
	if(i==2){
		$("input[name='CONTRACTNUMBER2']").val(val);
	} else{
		var CONTRACTNUMBER1 = "";
		$("#jldwDiv").children("div").each(function(i){		
			var CONTRACTNUMBER = $(this).find("[name$='CONTRACTNUMBER']").val();
			if(CONTRACTNUMBER!=null && CONTRACTNUMBER!=""){
				 CONTRACTNUMBER1+=(CONTRACTNUMBER+",");
			} 
		});
	    if(CONTRACTNUMBER1!=""){
	    	CONTRACTNUMBER1 = CONTRACTNUMBER1.substring(0,CONTRACTNUMBER1.lastIndexOf(","));
	    }
		$("input[name='CONTRACTNUMBER1']").val(CONTRACTNUMBER1);
	}
}

function addSgryDiv(){
	$("#sgdwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
		var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
		var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
		var PERSONCERT = $(this).find("[name$='PERSONCERT']").val();//负责人执业资格证书号
	
		$("#SGRY_FORM").children("div").each(function(){ //遍历全部option
			var sgdwmc = $(this).find("[name='sgdwmc']").val(); //获取option的内容
			if(CORPNAME!=sgdwmc){
				$(this).find("[name='sgdwmc']").val(CORPNAME);
				$(this).children("div").eq(0).children("span").eq(0).html("施工单位名称："+CORPNAME);
			}
		});
		if($("#SGRY_FORM").children("div").length<1){			
			$.post("projectSgxkController/refreshSgryDiv.do?CORPNAME="+encodeURIComponent(CORPNAME),{
			}, function(responseText, status, xhr) {
				$("#SGRY_FORM").append(responseText);				
			});
		}
	});
}
/*************施工单位信息JS结束****************/



/*************监理单位信息JS开始****************/
/**
 * 添加监理单位信息
 */
function addJldwDiv(){
	$.post("projectSgxkController/refreshJldwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jldwDiv").append(responseText);
		$("#jldwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
		initJlryDiv();
	});
}
function initJlryDiv(){	
	$.post("projectSgxkController/refreshJlryDiv.do",{
	}, function(responseText, status, xhr) {
		$("#JLRY_FORM").append(responseText);
	});
}
/**
 * 删除监理单位信息
 */
function delJldwDiv(o){
	$(o).closest("div").remove();
	var  CORPNAME = $(o).closest("div").find("[name$='CORPNAME']").val()
	$("#JLRY_FORM").children("div").find("[name='jldwmc']").each(function(i){
		var val = $(this).val();
		if(CORPNAME==val){
			$(this).closest("div").remove();
		}
	});
	setJldwInfo();
	setSupervisionUnits();
	setSgxkjbxx();
} 

/**
 * 获取监理单位信息
 */
function getJldwJson(){
	var infoArray = [];
		$("#jldwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			var PERSONQUALLEVEL = $(this).find("[name$='PERSONQUALLEVEL']").val();//执业资格等级
			var PERSONCERT = $(this).find("[name$='PERSONCERT']").val();//负责人执业资格证书号
			var CERTNUM = $(this).find("[name$='CERTNUM']").val();//安全生产考核合格证书编号
			var PERSONPHOTO = $(this).find("[name$='PERSONPHOTO']").val();//电子照片
			var SECTION = $(this).find("[name$='SECTION']").val();//标段名称
			var CONTRACTNUMBER = $(this).find("[name$='CONTRACTNUMBER']").val();//合同编号
			var CONTRACTCOST = $(this).find("[name$='CONTRACTCOST']").val();//监理合同价
			var BIDDINGDATE = $(this).find("[name$='BIDDINGDATE']").val();//中标通知书时间
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				info.PERSONQUALLEVEL = PERSONQUALLEVEL;	
				info.PERSONCERT = PERSONCERT;	
				info.CERTNUM = CERTNUM;	
				info.PERSONPHOTO = PERSONPHOTO;	
				info.SECTION = SECTION;	
				info.CONTRACTNUMBER = CONTRACTNUMBER;	
				info.CONTRACTCOST = CONTRACTCOST;	
				info.BIDDINGDATE = BIDDINGDATE;	
				var jlryArray = [];
				if($("#JLRY_FORM").children("div").length>0){				
					$("#JLRY_FORM").children("div").eq(i).each(function(i){				
						$(this).find("[name='jlryxx']").each(function(){
							var val = $(this).val();
							if(val){						
								var jlryxx = JSON.parse(val);
								jlryxx.JLRY = val;
								jlryArray.push(jlryxx);
							}
						});
					});
					if(jlryArray.length>0){
						info.JLRY = jlryArray;
					}
				} else{				
					var jlry = {};
					jlry.STATION = PERSONNAME;//姓名
					jlry.PERSONNAME = "项目负责人";//工作岗位
					jlry.IDCARDTYPENUM = IDCARDTYPENUM;//证件类型
					jlry.PERSONIDENTITY = PERSONIDCARD;//身份证号码
					jlry.PERSONCERTID = PERSONCERT;//证书编号
					jlry.OBTAINEDYEAR = "";//从业年限
					jlry.CERTISSUINGORGAN = "";//证书发证机关
					jlry.PERSONTITLE = "";//职称
					jlry.PERSONTEL = "";//联系电话
					jlry.PERSONPROF = "";//专业
					jlry.JLRY = JSON.stringify(jlry);
					jlryArray.push(jlry);
					info.JLRY = JSON.stringify(jlryArray);
				}
				
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}


/**
 * 
 */
function setJldwInfo(){	
	var JLUNITS = "";
	$("#jldwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();
		if(CORPNAME!=null && CORPNAME!=""){
			if(JLUNITS!=null && JLUNITS!=""){				
				JLUNITS += ","+CORPNAME;
			} else{				
				JLUNITS += CORPNAME;
			}
			if(CORPCREDITCODE!=null && CORPCREDITCODE!=""){
				JLUNITS +="（"+CORPCREDITCODE+"）";
			}
		} 
	});
	$("[name='JLUNITS']").val(JLUNITS);
}
function addJlryDiv(){
	$("#jldwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		$("#JLRY_FORM").children("div").eq(i).find("[name='jldwmc']").val(CORPNAME);
		$("#JLRY_FORM").children("div").eq(i).children("div").eq(0).children("span").eq(0).html("监理单位名称："+CORPNAME);	
		if($("#JLRY_FORM").children("div").length<1){			
			$.post("projectSgxkController/refreshJlryDiv.do?CORPNAME="+encodeURIComponent(CORPNAME),{
			}, function(responseText, status, xhr) {
				$("#JLRY_FORM").append(responseText);				
			});
		}
	});
}
/*************监理单位信息JS结束****************/



/*************勘察单位信息JS开始****************/
/**
 * 添加勘察单位信息
 */
function addKcdwDiv(){
	$.post("projectSgxkController/refreshKcdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#kcdwDiv").append(responseText);
		$("#kcdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除勘察单位信息
 */
function delKcdwDiv(o){
	$(o).closest("div").remove();
	setSgxkjbxx();
} 

/**
 * 获取勘察单位信息
 */
function getKcdwJson(){
	var infoArray = [];
		$("#kcdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			var SPECIALTYTYPNUM = $(this).find("[name$='SPECIALTYTYPNUM']").val();//注册类型及等级
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				info.SPECIALTYTYPNUM = SPECIALTYTYPNUM;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************勘察单位信息JS结束****************/



/*************设计单位信息JS开始****************/
/**
 * 添加设计单位信息
 */
function addSjdwDiv(){
	$.post("projectSgxkController/refreshSjdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#sjdwDiv").append(responseText);
		$("#sjdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除设计单位信息
 */
function delSjdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取设计单位信息
 */
function getSjdwJson(){
	var infoArray = [];
		$("#sjdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************设计单位信息JS结束****************/




/*************施工图审查单位信息JS开始****************/
/**
 * 添加施工图审查单位信息
 */
function addSgtscdwDiv(){
	$.post("projectSgxkController/refreshSgtscdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#sgtscdwDiv").append(responseText);
		$("#sgtscdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除施工图审查单位信息
 */
function delSgtscdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取施工图审查单位信息
 */
function getSgtscdwJson(){
	var infoArray = [];
		$("#sgtscdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			var SPECIALTYTYPNUM = $(this).find("[name$='SPECIALTYTYPNUM']").val();//注册类型及等级
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				info.SPECIALTYTYPNUM = SPECIALTYTYPNUM;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************施工图审查单位信息JS结束****************/





/*************控制价（预算价）计价文件编制单位信息JS开始****************/
/**
 * 添加控制价（预算价）计价文件编制单位信息
 */
function addKzjdwDiv(){
	$.post("projectSgxkController/refreshKzjdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#kzjdwDiv").append(responseText);
		$("#kzjdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除控制价（预算价）计价文件编制单位信息
 */
function delKzjdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取控制价（预算价）计价文件编制单位信息
 */
function getKzjdwJson(){
	var infoArray = [];
		$("#kzjdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			var PERSONQUALLEVEL = $(this).find("[name$='PERSONQUALLEVEL']").val();//执业资格等级
			var PERSONCERT = $(this).find("[name$='PERSONCERT']").val();//负责人执业资格证书号
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				info.PERSONQUALLEVEL = PERSONQUALLEVEL;	
				info.PERSONCERT = PERSONCERT;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************控制价（预算价）计价文件编制单位信息JS结束****************/




/*************检测单位信息JS开始****************/
/**
 * 添加检测单位信息
 */
function addJcdwDiv(){
	$.post("projectSgxkController/refreshJcdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#jcdwDiv").append(responseText);
		$("#jcdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除检测单位信息
 */
function delJcdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取检测单位信息
 */
function getJcdwJson(){
	var infoArray = [];
		$("#jcdwDiv").children("div").each(function(i){
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			if(null!=CORPNAME && CORPNAME!=""){
				var info = {};
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/*************检测单位信息JS结束****************/



/*************招标代理单位信息JS开始****************/
/**
 * 添加招标代理单位信息
 */
function addZbdwDiv(){
	$.post("projectSgxkController/refreshZbdwDiv.do",{
	}, function(responseText, status, xhr) {
		$("#zbdwDiv").append(responseText);
		$("#zbdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}

/**
 * 删除招标代理单位信息
 */
function delZbdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取招标代理单位信息
 */
function getZbdwJson(){
	var infoArray = [];
		$("#zbdwDiv").children("div").each(function(i){
			var TENDERCLASSNUM = $(this).find("[name$='TENDERCLASSNUM']").val();//招标类型
			var TENDERTYPENUM = $(this).find("[name$='TENDERTYPENUM']").val();//招标方式
			var BIDDINGUNITNAME = $(this).find("[name$='BIDDINGUNITNAME']").val();//中标单位
			var BIDDINGNOTICENUMPROVINCE = $(this).find("[name$='BIDDINGNOTICENUMPROVINCE']").val();//监理中标通知单编号
			var TENDERRESULTDATE = $(this).find("[name$='TENDERRESULTDATE']").val();//中标日期
			var TENDERMONEY = $(this).find("[name$='TENDERMONEY']").val();//中标金额（万元）
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//单位名称
			var CORPCREDITCODE = $(this).find("[name$='CORPCREDITCODE']").val();//统一社会信用代码
			var QUALLEVEL = $(this).find("[name$='QUALLEVEL']").val();//单位资质等级
			var QUALCERTNO = $(this).find("[name$='QUALCERTNO']").val();//单位资质证书号
			var ORGCODE = $(this).find("[name$='ORGCODE']").val();//组织机构代码
			var LEGAL_NAME = $(this).find("[name$='LEGAL_NAME']").val();//法定代表人姓名
			var LEGAL_IDTYPE = $(this).find("[name$='LEGAL_IDTYPE']").val();//法定代表人证件类型
			var LEGAL_IDNO = $(this).find("[name$='LEGAL_IDNO']").val();//法定代表人证件号码
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目负责人
			var PERSONPHONE = $(this).find("[name$='PERSONPHONE']").val();//负责人电话号码
			var IDCARDTYPENUM = $(this).find("[name$='IDCARDTYPENUM']").val();//负责人证件类型
			var PERSONIDCARD = $(this).find("[name$='PERSONIDCARD']").val();//负责人证件号码
			var PERSONQUALLEVEL = $(this).find("[name$='PERSONQUALLEVEL']").val();//执业资格等级
			var PERSONCERT = $(this).find("[name$='PERSONCERT']").val();//负责人执业资格证书号
			if(null!=TENDERCLASSNUM && TENDERCLASSNUM!=""){
				var info = {};
				info.TENDERCLASSNUM = TENDERCLASSNUM;	
				info.TENDERTYPENUM = TENDERTYPENUM;	
				info.BIDDINGUNITNAME = BIDDINGUNITNAME;	
				info.BIDDINGNOTICENUMPROVINCE = BIDDINGNOTICENUMPROVINCE;	
				info.TENDERRESULTDATE = TENDERRESULTDATE;	
				info.TENDERMONEY = TENDERMONEY;	
				info.CORPNAME = CORPNAME;	
				info.CORPCREDITCODE = CORPCREDITCODE;	
				info.QUALLEVEL = QUALLEVEL;	
				info.QUALCERTNO = QUALCERTNO;	
				info.ORGCODE = ORGCODE;	
				info.LEGAL_NAME = LEGAL_NAME;	
				info.LEGAL_IDTYPE = LEGAL_IDTYPE;	
				info.LEGAL_IDNO = LEGAL_IDNO;	
				info.PERSONNAME = PERSONNAME;	
				info.PERSONPHONE = PERSONPHONE;	
				info.IDCARDTYPENUM = IDCARDTYPENUM;	
				info.PERSONIDCARD = PERSONIDCARD;	
				info.PERSONQUALLEVEL = PERSONQUALLEVEL;	
				info.PERSONCERT = PERSONCERT;	
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

function setTenderClassNum(val,index){
	if(val=='001'||val=='002'||val=='003'||val=='004'){
		$("."+index+"_zbdwxx").show();
		$("."+index+"_zbdwxx").find("input").addClass(" validate[required]");
	} else{		
		$("."+index+"_zbdwxx").hide();
		$("."+index+"_zbdwxx").find("input").removeClass(" validate[required]");
		$("."+index+"_zbdwxx").find("input").val('');
	}
}
function setTenderTypeNum(val,index){
	
	if(val=='003'||val=='099'){
		$("."+index+"_zbdldwxx").hide();
		$("."+index+"_zbdldwxx").find("input,select").attr("disabled",true);
		$("."+index+"_zbdldwxx").find("input,select").val('');
	} else{		
		$("."+index+"_zbdldwxx").show();
		$("."+index+"_zbdldwxx").find("input,select").attr("disabled",false);
		$("#zbdwDiv [name$='IDCARDTYPENUM']").val(1);
		$("#zbdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	}
}

/*************招标代理单位信息JS结束****************/



/*************单位工程信息JS开始****************/
function setBuildUnits(){
	for(var j=0;j<$("#dwgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		//删除重复
		$("#dwgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){ //遍历全部option
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
			$("#dwgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#dwgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}
	
	for(var j=0;j<$("#zjjcgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		//删除重复
		$("#zjjcgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){ //遍历全部option
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
			$("#zjjcgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#zjjcgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}	
	
	
	for(var j=0;j<$("#sbdxsgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		//删除重复
		$("#sbdxsgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){ //遍历全部option
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
			$("#sbdxsgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#sbdxsgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}	
	
	
	
	
}
function setInitBuildUnits(val){
	for(var j=0;j<$("#dwgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		array.push(val);
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#dwgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#dwgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}
	
	for(var j=0;j<$("#zjjcgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		array.push(val);
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#zjjcgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#zjjcgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}	

	for(var j=0;j<$("#sbdxsgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#sgdwDiv").children("div").each(function(i){		
			var buildUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				buildUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					buildUnits +="（"+PERSONNAME+"）"
				}
				array.push(buildUnits);			
			}
		});
		array.push(val);
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#sbdxsgcDiv").children("div").eq(j).find(".BUILDUNITS_UL [name$='BUILDUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#sbdxsgcDiv").children("div").eq(j).find(".BUILDUNITS_UL")
					.append("<li><input name=\""+j+"BUILDUNITS\" class=\"checkbox validate[required]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}	
	
	
}
function setSupervisionUnits(){
	for(var j=0;j<$("#dwgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//删除重复
		$("#dwgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){ //遍历全部option
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
			$("#dwgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#dwgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}
	
	for(var j=0;j<$("#zjjcgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//删除重复
		$("#zjjcgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){ //遍历全部option
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
			$("#zjjcgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#zjjcgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}	
	
	for(var j=0;j<$("#sbdxsgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组		
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//删除重复
		$("#sbdxsgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){ //遍历全部option
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
			$("#sbdxsgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#sbdxsgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
				
			}
		}
	}
	
	
	
}

function setInitSupervisionUnits(val){
	for(var j=0;j<$("#dwgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#dwgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#dwgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}
	
	for(var j=0;j<$("#zjjcgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#zjjcgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#zjjcgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}	
	
	for(var j=0;j<$("#sbdxsgcDiv").children("div").length;j++){
		var array = new Array(); //定义数组	
		$("#jldwDiv").children("div").each(function(i){		
			var supervisionUnits = "";
			var CORPNAME = $(this).find("[name$='CORPNAME']").val();//施工单位名称
			var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
			if(CORPNAME!=null&&CORPNAME!=''){
				supervisionUnits +=CORPNAME;	
				if(PERSONNAME!=null&&PERSONNAME!=''){
					supervisionUnits +="（"+PERSONNAME+"）"
				}
				array.push(supervisionUnits);			
			}
		});
		//添加未重复
		for(var k=0;k<array.length;k++){	
			var isok = true;
			$("#sbdxsgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL [name$='SUPERVISIONUNITS']").each(function(){
				var txt = $(this).val(); //获取option的内容
				if(txt==array[k]){
					isok = false;						
				}
			});
			if(isok){
				$("#sbdxsgcDiv").children("div").eq(j).find(".SUPERVISIONUNITS_UL")
					.append("<li><input name=\""+j+"SUPERVISIONUNITS\" class=\"checkbox validate[]\" value=\""+array[k]+"\" type=\"checkbox\">"+array[k]+"</li>");							
			}
		}
	}
	
	
	
}

function setDwgcInfo(index){
	var PROAREA = 0;
	var STRUCTUPFLOORNUMS = 0;
	var STRUCTDWFLOORNUMS = 0;
	var STRUCTUPFLOORAREAS = 0;
	var STRUCTDWFLOORAREAS = 0;
	var STRUCTQUALTYPES = "";
	var BASICQUALTYPES = "";
	var MUNILENGTHS = 0;
	var INVEST = 0;
	
	/* $("[id$='childrenDwgcTable']").find("tr").each(function(i){
		var ROW_JSON = $(this).find("[name$='ROW_JSON']").val();// 备注 
		if(ROW_JSON){				
			var dwgcinfo = JSON.parse(ROW_JSON);	
			var STRUCTUPFLOORNUM = dwgcinfo.STRUCTUPFLOORNUM;//±0.000以上层数
			var STRUCTDWFLOORNUM = dwgcinfo.STRUCTDWFLOORNUM;//±0.000以下层数
			var STRUCTUPFLOORAREA_DWGC = dwgcinfo.STRUCTUPFLOORAREA_DWGC;//±0.000以上面积
			var STRUCTDWFLOORAREA_DWGC = dwgcinfo.STRUCTDWFLOORAREA_DWGC;//±0.000以下面积
			var ARCHHEIGHT = dwgcinfo.ARCHHEIGHT;//建筑高度
			var MUNILENGTH = dwgcinfo.MUNILENGTH;//市政长度
			var ITEMINVEST = dwgcinfo.ITEMINVEST;//工程总造价
			if(Number(STRUCTUPFLOORNUMS)<Number(STRUCTUPFLOORNUM)){				
				//STRUCTUPFLOORNUMS=Number(STRUCTUPFLOORNUM);
			}
			if(Number(STRUCTDWFLOORNUMS)<Number(STRUCTDWFLOORNUM)){				
				//STRUCTDWFLOORNUMS=Number(STRUCTDWFLOORNUM);
			}
			if(Number(STRUCTUPFLOORAREA_DWGC)>0){	
				//STRUCTUPFLOORAREAS+=Number(STRUCTUPFLOORAREA_DWGC);
			}
			if(Number(STRUCTDWFLOORAREA_DWGC)>0){	
				//STRUCTDWFLOORAREAS+=Number(STRUCTDWFLOORAREA_DWGC);
			}
			if(Number(MUNILENGTH)>0){	
				MUNILENGTHS+=Number(MUNILENGTH);
			}
			if(Number(ARCHHEIGHT)>0){	
				MUNILENGTHS+=Number(ARCHHEIGHT);
			}
			if(Number(ITEMINVEST)>0){	
				INVEST+=Number(ITEMINVEST);
			}
			var ARCHAREA = Number(STRUCTUPFLOORAREA_DWGC)+Number(STRUCTDWFLOORAREA_DWGC);	
			if(Number(ARCHAREA)>0){	
				//PROAREA+=Number(ARCHAREA);
			}
			// 结构类型
			var STRUCTQUALTYPE = dwgcinfo.STRUCTQUALTYPE;
			var jglx = STRUCTQUALTYPE.split(",");			
			for(var j=0;j<jglx.length;j++){					
				  if(STRUCTQUALTYPES=="" || STRUCTQUALTYPES.indexOf(jglx[j])==-1){					  
						STRUCTQUALTYPES+=(jglx[j]+",");
				  }
			}
			// 基础类型
			var BASICQUALTYPE = dwgcinfo.BASICQUALTYPE;
			var jclx = BASICQUALTYPE.split(",");			
			for(var j=0;j<jclx.length;j++){					
				  if(BASICQUALTYPES=="" || BASICQUALTYPES.indexOf(jclx[j])==-1){					  
						BASICQUALTYPES+=(jclx[j]+",");
				  }
			}
		}		
	});		 */
	$("#dwgcDiv").children("div").each(function(i){	
		
		var MUNILENGTH = $(this).find("[name$='MUNILENGTH']").val();//市政长度
		if(Number(MUNILENGTH)>0){	
			MUNILENGTHS+=Number(MUNILENGTH);
		}
		var ARCHHEIGHT = $(this).find("[name$='ARCHHEIGHT']").val();//建筑高度
		if(Number(ARCHHEIGHT)>0){	
			MUNILENGTHS+=Number(ARCHHEIGHT);
		}
		var ITEMINVEST = $(this).find("[name$='ITEMINVEST']").val();//工程总造价
		if(Number(ITEMINVEST)>0){	
			INVEST+=Number(ITEMINVEST);
		}
		var STRUCTUPFLOORNUM = $(this).find("[name$='STRUCTUPFLOORNUM']").val();//±0.000以上层数  取最大值
		if(Number(STRUCTUPFLOORNUM)>0){	
			if(Number(STRUCTUPFLOORNUMS)<Number(STRUCTUPFLOORNUM)){				
				STRUCTUPFLOORNUMS=Number(STRUCTUPFLOORNUM);
			}
		}
		var STRUCTDWFLOORNUM = $(this).find("[name$='STRUCTDWFLOORNUM']").val();//±0.000以下层数 取最大值
		if(Number(STRUCTDWFLOORNUM)>0){	
			if(Number(STRUCTDWFLOORNUMS)<Number(STRUCTDWFLOORNUM)){				
				STRUCTDWFLOORNUMS=Number(STRUCTDWFLOORNUM);
			}
		}
		var STRUCTUPFLOORAREA_DWGC = $(this).find("[name$='STRUCTUPFLOORAREA_DWGC']").val();//±0.000以上面积
		if(Number(STRUCTUPFLOORAREA_DWGC)>0){	
			STRUCTUPFLOORAREAS+=Number(STRUCTUPFLOORAREA_DWGC);
		}
		var STRUCTDWFLOORAREA_DWGC = $(this).find("[name$='STRUCTDWFLOORAREA_DWGC']").val();//±0.000以下面积
		if(Number(STRUCTDWFLOORAREA_DWGC)>0){	
			STRUCTDWFLOORAREAS+=Number(STRUCTDWFLOORAREA_DWGC);
		}
		var ARCHAREA = Number(STRUCTUPFLOORAREA_DWGC)+Number(STRUCTDWFLOORAREA_DWGC);	
		if(Number(ARCHAREA)>0){	
			PROAREA+=Number(ARCHAREA);
			$(this).find("[name$='ARCHAREA']").val(ARCHAREA.toFixed(3));
		}else{
			$(this).find("[name$='ARCHAREA']").val('');
		}
		// 结构类型
    	$(this).find("[name$='STRUCTQUALTYPE'][type='checkbox']").each(function(){
			  //console.log($(this).val() + " : " + $(this).is(':checked'));
	          var checked= $(this).is(':checked');
	          if(checked){
				  var val = $(this).val();
				  if(STRUCTQUALTYPES=="" || STRUCTQUALTYPES.indexOf(val)==-1){					  
					STRUCTQUALTYPES+=($(this).val()+",");
				  }
	          }
	    });
		// 基础类型
    	$(this).find("[name$='BASICQUALTYPE'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
				  var val = $(this).val();
				  if(BASICQUALTYPES=="" || BASICQUALTYPES.indexOf(val)==-1){					  
					BASICQUALTYPES+=($(this).val()+",");
				  }
	          }
	    });
	});
	//$("[name='INVEST']").val(INVEST);
	//$("[name='PROCOST']").val(INVEST);
	$("[name='MUNILENGTHS']").val(MUNILENGTHS);
	$("[name='PROAREA']").val(PROAREA.toFixed(3));
	$("[name='STRUCTUPFLOORNUMS']").val(Number(STRUCTUPFLOORNUMS));
	$("[name='STRUCTDWFLOORNUMS']").val(Number(STRUCTDWFLOORNUMS));
	$("[name='STRUCTUPFLOORAREAS']").val(STRUCTUPFLOORAREAS.toFixed(3));
	$("[name='STRUCTDWFLOORAREAS']").val(STRUCTDWFLOORAREAS.toFixed(3));
	if(STRUCTQUALTYPES!=""){
		STRUCTQUALTYPES = STRUCTQUALTYPES.substring(0,STRUCTQUALTYPES.lastIndexOf(","));
	}
	if(BASICQUALTYPES!=""){
		BASICQUALTYPES = BASICQUALTYPES.substring(0,BASICQUALTYPES.lastIndexOf(","));
	}
	$("[name='STRUCTQUALTYPES']").val(STRUCTQUALTYPES);
	$("[name='BASICQUALTYPES']").val(BASICQUALTYPES);
	setSgxkjbxx();
}

/**
 * 添加单位工程信息
 */
function addDwgcDiv(){
	var num = $("[name='NUM']").val();
	if(num){
		if($("#dwgcDiv").children("div").length>=num){
			art.dialog({
				content : "单位工程不能大于本次申报单位工程个数!",
				icon : "warning",
				ok : true
			});
			return;
		}
	}
	$.post("projectSgxkController/refreshDwgcDiv.do",{
	}, function(responseText, status, xhr) {
		$("#dwgcDiv").append(responseText); 
		setBuildUnits();
		setSupervisionUnits();
		$("[name='SINGLEPRONUM']").val($("#dwgcDiv").children("div").length);		
		$('#dwgcDiv').find("[name$='STRUCTQUALTYPE'][type='checkbox']").each(function() {
			var name = $(this).attr("name");
			$(this).click(function() {
				if ($(this).is(':checked')) {
					$('#dwgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				} else {
					$('#dwgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				}
				setDwgcInfo('')
			});
		});
	});
}

/**
 * 添加桩基工程信息
 */
function addZjDiv(){
	$.post("projectSgxkController/refreshZjgcDiv.do",{
	}, function(responseText, status, xhr) {
		$("#zjjcgcDiv").append(responseText); 
		setBuildUnits();
		setSupervisionUnits();
		$('#zjgcDiv').find("[name$='STRUCTQUALTYPE_ZJGC'][type='checkbox']").each(function() {
			var name = $(this).attr("name");
			$(this).click(function() {
				if ($(this).is(':checked')) {
					$('#zjgcDiv').find("[name$='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				} else {
					$('#zjgcDiv').find("[name$='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				}
			});
		});
	});
}

/**
 * 添加桩基工程信息
 */
function addSbDiv(){
	$.post("projectSgxkController/refreshSbgcDiv.do",{
	}, function(responseText, status, xhr) {
		$("#sbdxsgcDiv").append(responseText); 
		setBuildUnits();
		setSupervisionUnits();		
		$('#sbgcDiv').find("[name$='STRUCTQUALTYPE_SBGC'][type='checkbox']").each(function() {
			var name = $(this).attr("name");
			$(this).click(function() {
				if ($(this).is(':checked')) {
					$('#sbgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				} else {
					$('#sbgcDiv').find("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				}
			});
		});
	});
}



/**
 * 初始化时添加单位工程信息
 */
function addInitDwgcDiv(obj,i){
	$.post("projectSgxkController/refreshDwgcDiv.do",{
	}, function(responseText, status, xhr) {
		$("#dwgcDiv").append(responseText);		
		setInitBuildUnits(obj.BUILDUNITS);	
		setInitSupervisionUnits(obj.SUPERVISIONUNITS);
		var currentTime = $("#dwgcDiv").children("div").eq(i).find("input[name='currentTime']").val();
		setGreenbuidinglevel(obj.ISGREENBUILDING_DWGC,currentTime);
		setSingleProMaintype(obj.SINGLEPROMAINTYPE,currentTime);
		var dicDesc = 1;
		if(obj.SINGLEPROMAINTYPE=='01'){
		   dicDesc = 1;
		} else if(obj.SINGLEPROMAINTYPE=='02'){
		   dicDesc = 2;
		}
		$('#'+currentTime+'singleprotype').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc,		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});
		$('#'+currentTime+'singleprosubtype').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+obj.SINGLEPROTYPE,		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});
		FlowUtil.initFormObjValue(obj,$("#dwgcDiv").children("div").eq(i));			
		var CHILDRENDWGC_JSON = obj.CHILDRENDWGC_JSON;
		if(CHILDRENDWGC_JSON){					
			var childrenDwgcInfo = eval(CHILDRENDWGC_JSON);
			if(childrenDwgcInfo && childrenDwgcInfo.length>0){						
				initChildrenDwgc(childrenDwgcInfo,i,currentTime)
			}
		}
	});
}
/**
 * 删除单位工程信息
 */
function delDwgcDiv(o){
	$(o).closest("div").remove();
	setDwgcInfo('');
	$("[name='SINGLEPRONUM']").val($("#dwgcDiv").children("div").length);
} 

/**
 * 删除桩基工程信息
 */
function delZjgcDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取单位工程信息
 */
function getDwgcJson(){
	var infoArray = [];
	$("#dwgcDiv").children("div").each(function(i){
		//结构类型
		var STRUCTQUALTYPE = "";
    	$(this).find("[name$='STRUCTQUALTYPE'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  STRUCTQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(STRUCTQUALTYPE!=""){
	    	STRUCTQUALTYPE = STRUCTQUALTYPE.substring(0,STRUCTQUALTYPE.lastIndexOf(","));
	    }
		//基础类型
		var BASICQUALTYPE = "";
    	$(this).find("[name$='BASICQUALTYPE'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BASICQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(BASICQUALTYPE!=""){
	    	BASICQUALTYPE = BASICQUALTYPE.substring(0,BASICQUALTYPE.lastIndexOf(","));
	    }
		//结构抗震等级
		var REBELQUAKELEVEL = "";
    	$(this).find("[name$='REBELQUAKELEVEL'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  REBELQUAKELEVEL+=($(this).val()+",");
	          }
	    });
	    if(REBELQUAKELEVEL!=""){
	    	REBELQUAKELEVEL = REBELQUAKELEVEL.substring(0,REBELQUAKELEVEL.lastIndexOf(","));
	    }
		var SINGLEPRONAME = $(this).find("[name$='SINGLEPRONAME']").val();//单位工程名称
		//施工单位
		var BUILDUNITS = "";
    	$(this).find("[name$='BUILDUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BUILDUNITS+=($(this).val()+",");
	          }
	    });
	    if(BUILDUNITS!=""){
	    	BUILDUNITS = BUILDUNITS.substring(0,BUILDUNITS.lastIndexOf(","));
	    }
		//监理单位
		var SUPERVISIONUNITS = "";
    	$(this).find("[name$='SUPERVISIONUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  SUPERVISIONUNITS+=($(this).val()+",");
	          }
	    });
	    if(SUPERVISIONUNITS!=""){
	    	SUPERVISIONUNITS = SUPERVISIONUNITS.substring(0,SUPERVISIONUNITS.lastIndexOf(","));
	    }
		var SINGLEPROMAINTYPE = $(this).find("[name$='SINGLEPROMAINTYPE']").val();//单位工程类别
		var SINGLEPROTYPE = $(this).find("[name$='SINGLEPROTYPE']").val();//单位工程类别
		var SINGLEPROSUBTYPE = $(this).find("[name$='SINGLEPROSUBTYPE']").val();//单位工程类别
		var ARCHHEIGHT = $(this).find("[name$='ARCHHEIGHT']").val();//建筑高度		
		var ARCHAREA = $(this).find("[name$='ARCHAREA']").val();	//建筑面积
		var MUNILENGTH = $(this).find("[name$='MUNILENGTH']").val();//市政长度
		var FLOORAREA = $(this).find("[name$='FLOORAREA']").val();// 路桥面积		
		var ISSUPERHIGHTBUILDING = $(this).find("[name$='ISSUPERHIGHTBUILDING']:checked").val();//是否超限高层建筑
		var ISSHOCKISOLATIONBUILDING = $(this).find("[name$='ISSHOCKISOLATIONBUILDING']:checked").val();//是否为减隔震建筑
		var ISEQUIPPEDARCHITECTURE = $(this).find("[name$='ISEQUIPPEDARCHITECTURE']:checked").val();//是否为装配式建筑
		var ISGREENBUILDING_DWGC = $(this).find("[name$='ISGREENBUILDING_DWGC']:checked").val();//是否绿色建筑
		var GREENBUIDINGLEVEL = $(this).find("[name$='GREENBUIDINGLEVEL']").val();//绿色建筑等级	
		var STRUCTUPFLOORNUM = $(this).find("[name$='STRUCTUPFLOORNUM']").val();//±0.000以上层数		
		var STRUCTDWFLOORNUM = $(this).find("[name$='STRUCTDWFLOORNUM']").val();	// ±0.000以下层数
		var STRUCTUPFLOORAREA_DWGC = $(this).find("[name$='STRUCTUPFLOORAREA_DWGC']").val();// ±0.000以上面积
		var STRUCTDWFLOORAREA_DWGC = $(this).find("[name$='STRUCTDWFLOORAREA_DWGC']").val();// ±0.000以下面积	
		
		var FOUNDSUPWAY = $(this).find("[name$='FOUNDSUPWAY']").val();//基坑支护方法	
		var FOUNDDEPTH = $(this).find("[name$='FOUNDDEPTH']").val();	// 基坑开挖深度
		var SLOPESUPWAY = $(this).find("[name$='SLOPESUPWAY']").val();// 高边坡支护方法
		var SLOPEHEIGHT = $(this).find("[name$='SLOPEHEIGHT']").val();// 边坡高度
		var REFRACTLEVEL = $(this).find("[name$='REFRACTLEVEL']").val();//耐火等级		
		var STRUCTMAXSPAN = $(this).find("[name$='STRUCTMAXSPAN']").val();	// 结构最大跨度
		var ITEMINVEST = $(this).find("[name$='ITEMINVEST']").val();// 工程总造价（万元）
		var INFRALEVEL = $(this).find("[name$='INFRALEVEL']").val();// 基础建设等级
		var REMARK = $(this).find("[name$='REMARK']").val();// 备注		

		var CHILDRENDWGC_JSON = ""// 子单位工程	
		var childrenArray = [];
		$(this).find("[id$='childrenDwgcTable']").find("tr").each(function(i){
			var ROW_JSON = $(this).find("[name$='ROW_JSON']").val();// 备注 
			if(ROW_JSON){				
				childrenArray.push(JSON.parse(ROW_JSON));	
			}		
		});		
		CHILDRENDWGC_JSON = JSON.stringify(childrenArray);
		var info = {};
		info.FOUNDSUPWAY = FOUNDSUPWAY;
		info.FOUNDDEPTH = FOUNDDEPTH;
		info.SLOPESUPWAY = SLOPESUPWAY;
		info.SLOPEHEIGHT = SLOPEHEIGHT;
		info.REFRACTLEVEL = REFRACTLEVEL;
		info.STRUCTMAXSPAN = STRUCTMAXSPAN;
		info.ITEMINVEST = ITEMINVEST;
		info.INFRALEVEL = INFRALEVEL;
		info.REMARK = REMARK;
		info.GREENBUIDINGLEVEL = GREENBUIDINGLEVEL;
		info.STRUCTUPFLOORNUM = STRUCTUPFLOORNUM;
		info.STRUCTDWFLOORNUM = STRUCTDWFLOORNUM;
		info.STRUCTUPFLOORAREA_DWGC = STRUCTUPFLOORAREA_DWGC;
		info.STRUCTDWFLOORAREA_DWGC = STRUCTDWFLOORAREA_DWGC;
		info.SINGLEPRONAME = SINGLEPRONAME;
		info.BUILDUNITS = BUILDUNITS;
		info.SUPERVISIONUNITS = SUPERVISIONUNITS;
		info.SINGLEPROMAINTYPE = SINGLEPROMAINTYPE;
		info.SINGLEPROTYPE = SINGLEPROTYPE;
		info.SINGLEPROSUBTYPE = SINGLEPROSUBTYPE;
		info.ARCHHEIGHT = ARCHHEIGHT;
		info.ARCHAREA = ARCHAREA;
		info.MUNILENGTH = MUNILENGTH;
		info.FLOORAREA = FLOORAREA;
		info.ISSUPERHIGHTBUILDING = ISSUPERHIGHTBUILDING;
		info.ISSHOCKISOLATIONBUILDING = ISSHOCKISOLATIONBUILDING;
		info.ISEQUIPPEDARCHITECTURE = ISEQUIPPEDARCHITECTURE;
		info.ISGREENBUILDING_DWGC = ISGREENBUILDING_DWGC;
		info.STRUCTQUALTYPE = STRUCTQUALTYPE;
		info.BASICQUALTYPE = BASICQUALTYPE;
		info.REBELQUAKELEVEL = REBELQUAKELEVEL;
		info.CHILDRENDWGC_JSON = CHILDRENDWGC_JSON;
		infoArray.push(info);
	});
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}

/**
 * 获取桩基工程信息
 */
function getZjgcJson(){
	var radioVal = $("input[name='SGXK_SBXMLX']:checked").val();
	if(radioVal!='2'){
		return "";
	}
	var infoArray = [];
	$("#zjjcgcDiv").children("div").each(function(i){
		//结构类型
		var STRUCTQUALTYPE = "";
    	$(this).find("[name$='STRUCTQUALTYPE_ZJGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  STRUCTQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(STRUCTQUALTYPE!=""){
	    	STRUCTQUALTYPE = STRUCTQUALTYPE.substring(0,STRUCTQUALTYPE.lastIndexOf(","));
	    }
		//基础类型
		var BASICQUALTYPE = "";
    	$(this).find("[name$='BASICQUALTYPE_ZJGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BASICQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(BASICQUALTYPE!=""){
	    	BASICQUALTYPE = BASICQUALTYPE.substring(0,BASICQUALTYPE.lastIndexOf(","));
	    }
		//结构抗震等级
		var REBELQUAKELEVEL = "";
    	$(this).find("[name$='REBELQUAKELEVEL_ZJGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  REBELQUAKELEVEL+=($(this).val()+",");
	          }
	    });
	    if(REBELQUAKELEVEL!=""){
	    	REBELQUAKELEVEL = REBELQUAKELEVEL.substring(0,REBELQUAKELEVEL.lastIndexOf(","));
	    }
		var SINGLEPRONAME = $(this).find("[name$='SINGLEPRONAME']").val();//单位工程名称
		//施工单位
		var BUILDUNITS = "";
    	$(this).find("[name$='BUILDUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BUILDUNITS+=($(this).val()+",");
	          }
	    });
	    if(BUILDUNITS!=""){
	    	BUILDUNITS = BUILDUNITS.substring(0,BUILDUNITS.lastIndexOf(","));
	    }
		//监理单位
		var SUPERVISIONUNITS = "";
    	$(this).find("[name$='SUPERVISIONUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  SUPERVISIONUNITS+=($(this).val()+",");
	          }
	    });
	    if(SUPERVISIONUNITS!=""){
	    	SUPERVISIONUNITS = SUPERVISIONUNITS.substring(0,SUPERVISIONUNITS.lastIndexOf(","));
	    }
		var SINGLEPROMAINTYPE = $(this).find("[name$='SINGLEPROMAINTYPE']").val();//单位工程类别
		var SINGLEPROTYPE = $(this).find("[name$='SINGLEPROTYPE']").val();//单位工程类别
		var SINGLEPROSUBTYPE = $(this).find("[name$='SINGLEPROSUBTYPE']").val();//单位工程类别
		var ARCHHEIGHT = $(this).find("[name$='ARCHHEIGHT']").val();//建筑高度		
		var ARCHAREA = $(this).find("[name$='ARCHAREA']").val();	//建筑面积
		var MUNILENGTH = $(this).find("[name$='MUNILENGTH']").val();//市政长度
		var FLOORAREA = $(this).find("[name$='FLOORAREA']").val();// 路桥面积		
		var ISSUPERHIGHTBUILDING_ZJGC = $(this).find("[name$='ISSUPERHIGHTBUILDING_ZJGC']:checked").val();//是否超限高层建筑
		var ISSHOCKISOLATIONBUILDING_ZJGC = $(this).find("[name$='ISSHOCKISOLATIONBUILDING_ZJGC']:checked").val();//是否为减隔震建筑
		var ISEQUIPPEDARCHITECTURE_ZJGC = $(this).find("[name$='ISEQUIPPEDARCHITECTURE_ZJGC']:checked").val();//是否为装配式建筑
		var ISGREENBUILDING_ZJGC = $(this).find("[name$='ISGREENBUILDING_ZJGC']:checked").val();//是否绿色建筑
		var GREENBUIDINGLEVEL = $(this).find("[name$='GREENBUIDINGLEVEL_ZJGC']").val();//绿色建筑等级	
		var STRUCTUPFLOORNUM = $(this).find("[name$='STRUCTUPFLOORNUM']").val();//±0.000以上层数		
		var STRUCTDWFLOORNUM = $(this).find("[name$='STRUCTDWFLOORNUM']").val();	// ±0.000以下层数
		var STRUCTUPFLOORAREA_DWGC = $(this).find("[name$='STRUCTUPFLOORAREA_DWGC']").val();// ±0.000以上面积
		var STRUCTDWFLOORAREA_DWGC = $(this).find("[name$='STRUCTDWFLOORAREA_DWGC']").val();// ±0.000以下面积	
		
		var FOUNDSUPWAY = $(this).find("[name$='FOUNDSUPWAY']").val();//基坑支护方法	
		var FOUNDDEPTH = $(this).find("[name$='FOUNDDEPTH']").val();	// 基坑开挖深度
		var SLOPESUPWAY = $(this).find("[name$='SLOPESUPWAY']").val();// 高边坡支护方法
		var SLOPEHEIGHT = $(this).find("[name$='SLOPEHEIGHT']").val();// 边坡高度
		var REFRACTLEVEL = $(this).find("[name$='REFRACTLEVEL']").val();//耐火等级		
		var STRUCTMAXSPAN = $(this).find("[name$='STRUCTMAXSPAN']").val();	// 结构最大跨度
		var ITEMINVEST = $(this).find("[name$='ITEMINVEST']").val();// 工程总造价（万元）
		var INFRALEVEL = $(this).find("[name$='INFRALEVEL']").val();// 基础建设等级
		var REMARK = $(this).find("[name$='REMARK']").val();// 备注		

		var CHILDRENZJGC_JSON = ""// 子桩基工程	
		var childrenArray = [];
		$(this).find("[id$='childrenZjgcTable']").find("tr").each(function(i){
			var ROW_JSON = $(this).find("[name$='ROW_JSON']").val();// 备注 
			if(ROW_JSON){				
				childrenArray.push(JSON.parse(ROW_JSON));	
			}		
		});		
		CHILDRENZJGC_JSON = JSON.stringify(childrenArray);
		var info = {};
		info.FOUNDSUPWAY = FOUNDSUPWAY;
		info.FOUNDDEPTH = FOUNDDEPTH;
		info.SLOPESUPWAY = SLOPESUPWAY;
		info.SLOPEHEIGHT = SLOPEHEIGHT;
		info.REFRACTLEVEL = REFRACTLEVEL;
		info.STRUCTMAXSPAN = STRUCTMAXSPAN;
		info.ITEMINVEST = ITEMINVEST;
		info.INFRALEVEL = INFRALEVEL;
		info.REMARK = REMARK;
		info.GREENBUIDINGLEVEL = GREENBUIDINGLEVEL;
		info.STRUCTUPFLOORNUM = STRUCTUPFLOORNUM;
		info.STRUCTDWFLOORNUM = STRUCTDWFLOORNUM;
		info.STRUCTUPFLOORAREA_DWGC = STRUCTUPFLOORAREA_DWGC;
		info.STRUCTDWFLOORAREA_DWGC = STRUCTDWFLOORAREA_DWGC;
		info.SINGLEPRONAME = SINGLEPRONAME;
		info.BUILDUNITS = BUILDUNITS;
		info.SUPERVISIONUNITS = SUPERVISIONUNITS;
		info.SINGLEPROMAINTYPE = SINGLEPROMAINTYPE;
		info.SINGLEPROTYPE = SINGLEPROTYPE;
		info.SINGLEPROSUBTYPE = SINGLEPROSUBTYPE;
		info.ARCHHEIGHT = ARCHHEIGHT;
		info.ARCHAREA = ARCHAREA;
		info.MUNILENGTH = MUNILENGTH;
		info.FLOORAREA = FLOORAREA;
		info.ISSUPERHIGHTBUILDING_ZJGC = ISSUPERHIGHTBUILDING_ZJGC;
		info.ISSHOCKISOLATIONBUILDING_ZJGC = ISSHOCKISOLATIONBUILDING_ZJGC;
		info.ISEQUIPPEDARCHITECTURE_ZJGC = ISEQUIPPEDARCHITECTURE_ZJGC;
		info.ISGREENBUILDING_ZJGC = ISGREENBUILDING_ZJGC;
		info.STRUCTQUALTYPE = STRUCTQUALTYPE;
		info.BASICQUALTYPE = BASICQUALTYPE;
		info.REBELQUAKELEVEL = REBELQUAKELEVEL;
		info.CHILDRENZJGC_JSON = CHILDRENZJGC_JSON;
		infoArray.push(info);
	});
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}




//初始化单位工程信息
function setDwgc(json){
	var info = eval(json);
	if(info){				
		for(var i=0;i<info.length;i++){			
			if(i>0){
				addInitDwgcDiv(info[i],i);
			}else{	
				setBuildUnits();
				setSupervisionUnits();		
				setGreenbuidinglevel(info[i].ISGREENBUILDING_DWGC,'');
				setSingleProMaintype(info[i].SINGLEPROMAINTYPE,'');
				var dicDesc = 1;
				if(info[i].SINGLEPROMAINTYPE=='01'){
				   dicDesc = 1;
				} else if(info[i].SINGLEPROMAINTYPE=='02'){
				   dicDesc = 2;
				}
				$('#singleprotype').combobox({
					url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc,		
					method:'post',
					valueField:'VALUE',
					textField:'TEXT',
					panelHeight:'auto',
					required:true,
					editable:false
				});
				$('#singleprosubtype').combobox({
					url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+info[i].SINGLEPROTYPE,		
					method:'post',
					valueField:'VALUE',
					textField:'TEXT',
					panelHeight:'auto',
					required:true,
					editable:false
				});
				FlowUtil.initFormObjValue(info[i],$("#dwgcDiv").children("div").eq(i));
				var CHILDRENDWGC_JSON = info[i].CHILDRENDWGC_JSON;
				if(CHILDRENDWGC_JSON){					
					var childrenDwgcInfo = eval(CHILDRENDWGC_JSON);
					if(childrenDwgcInfo && childrenDwgcInfo.length>0){						
						initChildrenDwgc(childrenDwgcInfo,i,'')
					}
				}
			}
		}
	}else{
		setBuildUnits();
		setSupervisionUnits();
	}
}

function setGreenbuidinglevel(val,index){
	if(val==1){
		$("."+index+"greenbuidinglevelTr").show();
		$("[name='"+index+"GREENBUIDINGLEVEL']").addClass(" validate[required]");
	} else{		
		$("."+index+"greenbuidinglevelTr").hide();
		$("[name='"+index+"GREENBUIDINGLEVEL']").removeClass(" validate[required]");
	}
}

/*************单位工程信息JS结束****************/


/*************施工人员信息JS开始****************/
function addSgry(index){	
	var url = "projectSgxkController.do?addSgry&index="+index;
	art.dialog.open(url, {
		title : "人员信息",
        width:"700px",
        lock: true,
        resize:false,
        height:"390px",
		close: function () {
			var sgryInfo = art.dialog.data("sgryInfo");
			if(sgryInfo){
				var html = "";
				html +='<tr class="sgryTr" id="sgryTr_'+sgryInfo.index+'_'+sgryInfo.currentTime+'">';
				html +='<input type="hidden" name="sgryxx"/>';
				html +='<td style="text-align: center;">'+sgryInfo.STATION+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONIDENTITY+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONNAME+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONTITLE+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONPROF+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONCERTID+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getSgry(\'sgryTr_'+sgryInfo.index+'_'+sgryInfo.currentTime+'\');">编 辑</a>';
				html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';
				html +='</tr>';
				$("#"+index+"sgryTable").append(html);		
				$("#sgryTr_"+sgryInfo.index+"_"+sgryInfo.currentTime+" input[name='sgryxx']").val(JSON.stringify(sgryInfo));
			}
			art.dialog.removeData("sgryInfo");
		}
	}, false);
}

function getSgry(id){	
	var sgryxx = $("#"+id).find("[name='sgryxx']").val();
	var url = "projectSgxkController.do?addSgry&info="+encodeURIComponent(sgryxx);
	art.dialog.open(url, {
		title : "人员信息",
        width:"700px",
        lock: true,
        resize:false,
        height:"390px",
		close: function () {
			var sgryInfo = art.dialog.data("sgryInfo");
			if(sgryInfo){
				var html = "";
				html +='<input type="hidden" name="sgryxx"/>';
				html +='<td style="text-align: center;">'+sgryInfo.STATION+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONIDENTITY+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONNAME+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONTITLE+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONPROF+'</td>';
				html +='<td style="text-align: center;">'+sgryInfo.PERSONCERTID+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getSgry(\''+id+'\');">编 辑</a>';
				html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';			
				$("#"+id).html(html);	
				$("#"+id).find("[name='sgryxx']").val(JSON.stringify(sgryInfo));
			}
			art.dialog.removeData("sgryInfo");
		}
	}, false);
}
/*************施工人员信息JS结束****************/


/*************监理人员信息JS开始****************/
function addJlry(index){	
	var url = "projectSgxkController.do?addJlry&index="+index;
	art.dialog.open(url, {
		title : "人员信息",
        width:"700px",
        lock: true,
        resize:false,
        height:"390px",
		close: function () {
			var jlryInfo = art.dialog.data("jlryInfo");
			if(jlryInfo){
				var html = "";
				html +='<tr class="jlryTr" id="jlryTr_'+jlryInfo.index+'_'+jlryInfo.currentTime+'">';
				html +='<input type="hidden" name="jlryxx"/>';
				html +='<td style="text-align: center;">'+jlryInfo.STATION+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONIDENTITY+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONNAME+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONTITLE+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONPROF+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONCERTID+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getJlry(\'jlryTr_'+jlryInfo.index+'_'+jlryInfo.currentTime+'\');">编 辑</a>';
				html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';
				html +='</tr>';
				$("#"+index+"jlryTable").append(html);		
				$("#jlryTr_"+jlryInfo.index+"_"+jlryInfo.currentTime+" input[name='jlryxx']").val(JSON.stringify(jlryInfo));
			}
			art.dialog.removeData("jlryInfo");
		}
	}, false);
}

function getJlry(id){	
	var jlryxx = $("#"+id).find("[name='jlryxx']").val();
	var url = "projectSgxkController.do?addJlry&info="+encodeURIComponent(jlryxx);
	art.dialog.open(url, {
		title : "人员信息",
        width:"700px",
        lock: true,
        resize:false,
        height:"390px",
		close: function () {
			var jlryInfo = art.dialog.data("jlryInfo");
			if(jlryInfo){
				var html = "";
				html +='<input type="hidden" name="jlryxx"/>';
				html +='<td style="text-align: center;">'+jlryInfo.STATION+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONIDENTITY+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONNAME+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONTITLE+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONPROF+'</td>';
				html +='<td style="text-align: center;">'+jlryInfo.PERSONCERTID+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getJlry(\''+id+'\');">编 辑</a>';
				html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';			
				$("#"+id).html(html);	
				$("#"+id).find("[name='jlryxx']").val(JSON.stringify(jlryInfo));
			}
			art.dialog.removeData("jlryInfo");
		}
	}, false);
}
/*************监理人员信息JS结束****************/



function showSelectEnterprise(id,name,code){	
	$.dialog.open("projectSgxkController.do?enterpriseSelector&allowCount=1", {
		title : "企业查询",
		width:"800px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var enterpriseInfo = art.dialog.data("enterpriseInfo");
			if(enterpriseInfo){
				$("#"+id).find("[name='"+name+"']").val(enterpriseInfo[0].name);
				$("#"+id).find("[name='"+code+"']").val(enterpriseInfo[0].socialcreditcode);
				if(id=='jldwDiv'){
					setJldwInfo();
					setSupervisionUnits();
					addJlryDiv();
				} else if(id=='sgdwDiv'){
					setSgdwInfo();
					setBuildUnits();
					addSgryDiv();
					setSgxkjbxx();
				} else if(id=='JsdwDiv'){
					setSgxkjbxx();
				}
			}
		}
	}, false);
};

/**
* 新增单位子工程信息
**/
var dwgcChildrenNum=0;
function addDwgcChildrenDiv(i){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName=addDwgc&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);
	art.dialog.open(url, {
		title : "单位子工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<tr class="dwgcTr" id="dwgcTr_'+dwgcChildrenNum+'">';
				html+='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editDwgcChildren(\'addDwgc\',\'dwgcTr_'+dwgcChildrenNum+'\',\''+i+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				html+='</tr>';
				$("#"+i+"childrenDwgcTable").append(html);
				$("#dwgcTr_"+dwgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				dwgcChildrenNum++;
				setDwgcInfo(i);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}
/**
* 编辑单位子工程信息
**/
function editDwgcChildren(formName,id,index){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
	var rowxx = JSON.parse(rowJson);
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx))+"&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);;
	art.dialog.open(url, {
		title : "单位工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editDwgcChildren(\'addDwgc\',\''+id+'\',\''+index+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				$("#"+id).html(html);
				$("#"+id+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				setDwgcInfo(index);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}


var initDwgcChildrenNum = 0;
function initChildrenDwgc(childrenDwgcInfo,index,currentTime){	
	for(var i=0;i<childrenDwgcInfo.length;i++){			
		var html='<tr class="dwgcTr" id="dwgcTr_'+index+'_'+initDwgcChildrenNum+'">';
		html+='<input type="hidden" name="ROW_JSON">';
		html+='<td style="text-align: center;">'+childrenDwgcInfo[i].SINGLEPRONAME+'</td>';
		var SINGLEPROMAINTYPE_NAME= "";
		if(childrenDwgcInfo[i].SINGLEPROMAINTYPE=="01"){
			SINGLEPROMAINTYPE_NAME = "房建工程"
		} else if(childrenDwgcInfo[i].SINGLEPROMAINTYPE=="02"){
			SINGLEPROMAINTYPE_NAME = "市政工程"
		} 
		html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
		html+='<td style="text-align: center;">'+childrenDwgcInfo[i].STRUCTQUALTYPE+'</td>';
		if(childrenDwgcInfo[i].ARCHHEIGHT){					
			html+='<td style="text-align: center;">'+childrenDwgcInfo[i].ARCHHEIGHT+' </td>';
		} else if(childrenDwgcInfo[i].MUNILENGTH){					
			html+='<td style="text-align: center;">'+childrenDwgcInfo[i].MUNILENGTH+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		if(childrenDwgcInfo[i].ARCHAREA){					
			html+='<td style="text-align: center;">'+childrenDwgcInfo[i].ARCHAREA+' </td>';
		} else if(childrenDwgcInfo[i].FLOORAREA){					
			html+='<td style="text-align: center;">'+childrenDwgcInfo[i].FLOORAREA+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		html+='<td style="text-align: center;">'+childrenDwgcInfo[i].ITEMINVEST+'</td>';
		html+='<td style="text-align: center;">';
		html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editDwgcChildren(\'addDwgc\',\'dwgcTr_'+index+'_'+initDwgcChildrenNum+'\',\''+currentTime+'\');">编 辑</a>';
		html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
		html+='</td>';
		html+='</tr>';
		$("#dwgcDiv").children("div").eq(index).find("[id$='childrenDwgcTable']").append(html);
		$("#dwgcTr_"+index+"_"+initDwgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(childrenDwgcInfo[i]));
		initDwgcChildrenNum++;
	}
	
}





/**
* 新增桩基子工程信息
**/
var zjgcChildrenNum=0;
function addZjgcChildrenDiv(i){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName=addZjgc&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);
	art.dialog.open(url, {
		title : "桩基子工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<tr class="zjgcTr" id="zjgcTr_'+zjgcChildrenNum+'">';
				html+='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE_ZJGC+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editZjgcChildren(\'addZjgc\',\'zjgcTr_'+zjgcChildrenNum+'\',\''+i+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				html+='</tr>';
				$("#"+i+"childrenZjgcTable").append(html);
				$("#zjgcTr_"+zjgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				zjgcChildrenNum++;
				//setDwgcInfo(i);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}
/**
* 编辑桩基单位子工程信息
**/
function editZjgcChildren(formName,id,index){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
	var rowxx = JSON.parse(rowJson);
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx))+"&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);;
	art.dialog.open(url, {
		title : "桩基子工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE_ZJGC+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editZjgcChildren(\'addZjgc\',\''+id+'\',\''+index+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				$("#"+id).html(html);
				$("#"+id+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				//setDwgcInfo(index);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}


var initZjgcChildrenNum = 0;
function initChildrenZjgc(childrenZjgcInfo,index,currentTime){	
	for(var i=0;i<childrenZjgcInfo.length;i++){			
		var html='<tr class="zjgcTr" id="zjgcTr_'+index+'_'+initZjgcChildrenNum+'">';
		html+='<input type="hidden" name="ROW_JSON">';
		html+='<td style="text-align: center;">'+childrenZjgcInfo[i].SINGLEPRONAME+'</td>';
		var SINGLEPROMAINTYPE_NAME= "";
		if(childrenZjgcInfo[i].SINGLEPROMAINTYPE=="01"){
			SINGLEPROMAINTYPE_NAME = "房建工程"
		} else if(childrenZjgcInfo[i].SINGLEPROMAINTYPE=="02"){
			SINGLEPROMAINTYPE_NAME = "市政工程"
		} 
		html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
		html+='<td style="text-align: center;">'+childrenZjgcInfo[i].STRUCTQUALTYPE_ZJGC+'</td>';
		if(childrenZjgcInfo[i].ARCHHEIGHT){					
			html+='<td style="text-align: center;">'+childrenZjgcInfo[i].ARCHHEIGHT+' </td>';
		} else if(childrenZjgcInfo[i].MUNILENGTH){					
			html+='<td style="text-align: center;">'+childrenZjgcInfo[i].MUNILENGTH+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		if(childrenZjgcInfo[i].ARCHAREA){					
			html+='<td style="text-align: center;">'+childrenZjgcInfo[i].ARCHAREA+' </td>';
		} else if(childrenZjgcInfo[i].FLOORAREA){					
			html+='<td style="text-align: center;">'+childrenZjgcInfo[i].FLOORAREA+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		html+='<td style="text-align: center;">'+childrenZjgcInfo[i].ITEMINVEST+'</td>';
		html+='<td style="text-align: center;">';
		html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editZjgcChildren(\'addZjgc\',\'zjgcTr_'+index+'_'+initZjgcChildrenNum+'\',\''+currentTime+'\');">编 辑</a>';
		html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
		html+='</td>';
		html+='</tr>';
		$("#zjjcgcDiv").children("div").eq(index).find("[id$='childrenZjgcTable']").append(html);
		$("#zjgcTr_"+index+"_"+initZjgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(childrenZjgcInfo[i]));
		initZjgcChildrenNum++;
	}
	
}



function setZjgcSingleprosubtype(rec,index){
	   $('#'+index+'singleprosubtype_zjgc').combobox('clear');
	   if(rec.VALUE){
		   var url = 'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+rec.VALUE;
		   if(rec.VALUE=='100'||rec.VALUE=='300'||rec.VALUE=='900'){
			   $('#'+index+'singleprosubtype_zjgc').combobox('reload',url); 
		   }else{
			   var data, json;
				json = '[{"VALUE":"'+rec.TEXT+'","TEXT":"'+rec.TEXT+'","selected":true}]';
				data = $.parseJSON(json);
			   $('#'+index+'singleprosubtype_zjgc').combobox('loadData',data);  
		   }
	   }
}





function setZjgcGreenbuidinglevel(val,index){
	if(val==1){
		$("."+index+"greenbuidinglevelTr_zjgc").show();
		$("[name='"+index+"GREENBUIDINGLEVEL_ZJGC']").addClass(" validate[required]");
	} else{		
		$("."+index+"greenbuidinglevelTr_zjgc").hide();
		$("[name='"+index+"GREENBUIDINGLEVEL_ZJGC']").removeClass(" validate[required]");
	}
}

function setChildZjgcGreenbuidinglevel(val,index){
	if(val==1){
		$("."+index+"greenbuidinglevelTr_zjgc").show();
		$("[name='"+index+"GREENBUIDINGLEVEL']").addClass(" validate[required]");
	} else{		
		$("#zjjcgcDiv").find("[name='"+index+"GREENBUIDINGLEVEL']").removeClass(" validate[required]");
		$("#zjjcgcDiv").find("."+index+"greenbuidinglevelTr_zjgc").hide();
	}
}

function removeZjgcClass(){
	if(isRemove=='0'){
		$("#zjjcgcDiv").find("[name$='SINGLEPRONAME']").removeClass(" validate[required]");
		$("#zjjcgcDiv").find("[name$='ARCHHEIGHT']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='MUNILENGTH']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='STRUCTUPFLOORNUM']").removeClass(" validate[required],custom[numberplus]");
		$("#zjjcgcDiv").find("[name$='STRUCTDWFLOORNUM']").removeClass(" validate[required],custom[numberplus]");
		$("#zjjcgcDiv").find("[name$='STRUCTUPFLOORAREA_DWGC']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='STRUCTDWFLOORAREA_DWGC']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='ITEMINVEST']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='INFRALEVEL']").removeClass(" validate[required] field_width");
		$("#zjjcgcDiv").find("[name$='STRUCTMAXSPAN']").removeClass(" validate[required],custom[JustNumber]");
		$("#zjjcgcDiv").find("[name$='BASICQUALTYPE_ZJGC']").attr("disabled",true);
		$("#zjjcgcDiv").find("[name$='STRUCTQUALTYPE_ZJGC']").attr("disabled",true);
		$("#zjjcgcDiv").find("[name$='REBELQUAKELEVEL_ZJGC']").attr("disabled",true);
		isRemove="1";
		$("#zjgcDiv").hide();
	}
}


function addZjgcClass(){
	$("#zjjcgcDiv").find("[name$='SINGLEPRONAME']").addClass(" validate[required]");
	$("#zjjcgcDiv").find("[name$='ARCHHEIGHT']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='MUNILENGTH']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='STRUCTUPFLOORNUM']").addClass(" validate[required],custom[numberplus]");
	$("#zjjcgcDiv").find("[name$='STRUCTDWFLOORNUM']").addClass(" validate[required],custom[numberplus]");
	$("#zjjcgcDiv").find("[name$='STRUCTUPFLOORAREA_DWGC']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='STRUCTDWFLOORAREA_DWGC']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='ITEMINVEST']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='INFRALEVEL']").addClass(" validate[required] field_width");
	$("#zjjcgcDiv").find("[name$='STRUCTMAXSPAN']").addClass(" validate[required],custom[JustNumber]");
	$("#zjjcgcDiv").find("[name$='BASICQUALTYPE_ZJGC']").attr("disabled",false);
	$("#zjjcgcDiv").find("[name$='STRUCTQUALTYPE_ZJGC']").attr("disabled",false);
	$("#zjjcgcDiv").find("[name$='REBELQUAKELEVEL_ZJGC']").attr("disabled",false);
	isRemove="0";
}


function removeSbgcClass(){
		$("#sbdxsgcDiv").find("[name$='SINGLEPRONAME']").removeClass(" validate[required]");
		$("#sbdxsgcDiv").find("[name$='ARCHHEIGHT']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='MUNILENGTH']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='STRUCTUPFLOORNUM']").removeClass(" validate[required],custom[numberplus]");
		$("#sbdxsgcDiv").find("[name$='STRUCTDWFLOORNUM']").removeClass(" validate[required],custom[numberplus]");
		$("#sbdxsgcDiv").find("[name$='STRUCTUPFLOORAREA_DWGC']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='STRUCTDWFLOORAREA_DWGC']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='ITEMINVEST']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='INFRALEVEL']").removeClass(" validate[required] field_width");
		$("#sbdxsgcDiv").find("[name$='STRUCTMAXSPAN']").removeClass(" validate[required],custom[JustNumber]");
		$("#sbdxsgcDiv").find("[name$='BASICQUALTYPE_SBGC']").attr("disabled",true);
		$("#sbdxsgcDiv").find("[name$='STRUCTQUALTYPE_SBGC']").attr("disabled",true);
		$("#sbdxsgcDiv").find("[name$='REBELQUAKELEVEL_SBGC']").attr("disabled",true);
}



function addSbgcClass(){
	$("#sbdxsgcDiv").find("[name$='SINGLEPRONAME']").addClass(" validate[required]");
	$("#sbdxsgcDiv").find("[name$='ARCHHEIGHT']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='MUNILENGTH']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='STRUCTUPFLOORNUM']").addClass(" validate[required],custom[numberplus]");
	$("#sbdxsgcDiv").find("[name$='STRUCTDWFLOORNUM']").addClass(" validate[required],custom[numberplus]");
	$("#sbdxsgcDiv").find("[name$='STRUCTUPFLOORAREA_DWGC']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='STRUCTDWFLOORAREA_DWGC']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='ITEMINVEST']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='INFRALEVEL']").addClass(" validate[required] field_width");
	$("#sbdxsgcDiv").find("[name$='STRUCTMAXSPAN']").addClass(" validate[required],custom[JustNumber]");
	$("#sbdxsgcDiv").find("[name$='BASICQUALTYPE_SBGC']").attr("disabled",false);
	$("#sbdxsgcDiv").find("[name$='STRUCTQUALTYPE_SBGC']").attr("disabled",false);
	$("#sbdxsgcDiv").find("[name$='REBELQUAKELEVEL_SBGC']").attr("disabled",false);
}

/**
* 显示或隐藏桩基（基础）工程Tab
**/
function showOrHideZjdwgcTab(val){
	if(val==2){
		addZjgcClass();
		$("#zjgcDiv").show();
	} else{	
		removeZjgcClass();
	}
}

function setArcharea(index){
		var STRUCTUPFLOORAREA_DWGC = $("#zjjcgcDiv").find("[name='"+index+"STRUCTUPFLOORAREA_DWGC']").val();//±0.000以上面积
		var STRUCTDWFLOORAREA_DWGC = $("#zjjcgcDiv").find("[name='"+index+"STRUCTDWFLOORAREA_DWGC']").val();//±0.000以下面积
		var ARCHAREA = Number(STRUCTUPFLOORAREA_DWGC)+Number(STRUCTDWFLOORAREA_DWGC);	
		if(Number(ARCHAREA)>0){	
			$("#zjjcgcDiv").find("[name='"+index+"ARCHAREA']").val(ARCHAREA.toFixed(3));
		}else{
			$("#zjjcgcDiv").find("[name='"+index+"ARCHAREA']").val('');
		}
}

/**=================上部（地下室）开始================================*/

function setSbgcSingleprosubtype(rec,index){
	   $('#'+index+'singleprosubtype_sbgc').combobox('clear');
	   if(rec.VALUE){
		   var url = 'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+rec.VALUE;
		   if(rec.VALUE=='100'||rec.VALUE=='300'||rec.VALUE=='900'){
			   $('#'+index+'singleprosubtype_sbgc').combobox('reload',url); 
		   }else{
			   var data, json;
				json = '[{"VALUE":"'+rec.TEXT+'","TEXT":"'+rec.TEXT+'","selected":true}]';
				data = $.parseJSON(json);
			   $('#'+index+'singleprosubtype_sbgc').combobox('loadData',data);  
		   }
	   }
	}

/**
 * 删除桩基工程信息
 */
function delSbgcDiv(o){
	$(o).closest("div").remove();
} 

/**
* 新增上部子工程信息
**/
var sbgcChildrenNum=0;
function addSbgcChildrenDiv(i){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName=addSbgc&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);
	art.dialog.open(url, {
		title : "上部(地下室)子工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<tr class="sbgcTr" id="sbgcTr_'+sbgcChildrenNum+'">';
				html+='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE_SBGC+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editSbgcChildren(\'addSbgc\',\'sbgcTr_'+sbgcChildrenNum+'\',\''+i+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				html+='</tr>';
				$("#"+i+"childrenSbgcTable").append(html);
				$("#sbgcTr_"+sbgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				sbgcChildrenNum++;
				//setDwgcInfo(i);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}


/**
* 编辑上部单位子工程信息
**/
function editSbgcChildren(formName,id,index){	
	var sgdwCorpName = ""
	$("#sgdwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(sgdwCorpName!=''){
			sgdwCorpName +=","+CORPNAME;
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}
		} else {
			sgdwCorpName +=CORPNAME;	
			if(PERSONNAME!=null&&PERSONNAME!=''){
				sgdwCorpName +="（"+PERSONNAME+"）"
			}		
		}
	});
	var jldwCorpName = ""
	$("#jldwDiv").children("div").each(function(i){	
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		var PERSONNAME = $(this).find("[name$='PERSONNAME']").val();//项目经理
		if(jldwCorpName!=''){
			jldwCorpName +=","+CORPNAME;			
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		

		} else {
			jldwCorpName +=CORPNAME;				
			if(PERSONNAME!=null&&PERSONNAME!=''){
				jldwCorpName +="（"+PERSONNAME+"）"
			}		
		
		}
	});
	var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
	var rowxx = JSON.parse(rowJson);
	var url = "projectSgxkController.do?addDwgcChildrenInfo&type=1&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx))+"&sgdwCorpName="+encodeURIComponent(sgdwCorpName)+"&jldwCorpName="+encodeURIComponent(jldwCorpName);;
	art.dialog.open(url, {
		title : "上部(地下室)子工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {			
            var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){	
				var html='<input type="hidden" name="ROW_JSON">';
				html+='<td style="text-align: center;">'+dwInfo.SINGLEPRONAME+'</td>';
				var SINGLEPROMAINTYPE_NAME= "";
				if(dwInfo.SINGLEPROMAINTYPE=="01"){
					SINGLEPROMAINTYPE_NAME = "房建工程"
				} else if(dwInfo.SINGLEPROMAINTYPE=="02"){
					SINGLEPROMAINTYPE_NAME = "市政工程"
				} 
				html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
				html+='<td style="text-align: center;">'+dwInfo.STRUCTQUALTYPE_SBGC+'</td>';
				if(dwInfo.ARCHHEIGHT){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHHEIGHT+' </td>';
				} else if(dwInfo.MUNILENGTH){					
					html+='<td style="text-align: center;">'+dwInfo.MUNILENGTH+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				if(dwInfo.ARCHAREA){					
					html+='<td style="text-align: center;">'+dwInfo.ARCHAREA+' </td>';
				} else if(dwInfo.FLOORAREA){					
					html+='<td style="text-align: center;">'+dwInfo.FLOORAREA+' </td>';
				} else{
					html+='<td style="text-align: center;"></td>';
				}
				html+='<td style="text-align: center;">'+dwInfo.ITEMINVEST+'</td>';
				html+='<td style="text-align: center;">';
				html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editSbgcChildren(\'addSbgc\',\''+id+'\',\''+index+'\');">编 辑</a>';
				html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
				html+='</td>';
				$("#"+id).html(html);
				$("#"+id+" [name='ROW_JSON']").val(JSON.stringify(dwInfo));
				//setDwgcInfo(index);
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}


var initSbgcChildrenNum = 0;
function initChildrenSbgc(childrenSbgcInfo,index,currentTime){	
	for(var i=0;i<childrenSbgcInfo.length;i++){			
		var html='<tr class="sbgcTr" id="sbgcTr_'+index+'_'+initSbgcChildrenNum+'">';
		html+='<input type="hidden" name="ROW_JSON">';
		html+='<td style="text-align: center;">'+childrenSbgcInfo[i].SINGLEPRONAME+'</td>';
		var SINGLEPROMAINTYPE_NAME= "";
		if(childrenSbgcInfo[i].SINGLEPROMAINTYPE=="01"){
			SINGLEPROMAINTYPE_NAME = "房建工程"
		} else if(childrenSbgcInfo[i].SINGLEPROMAINTYPE=="02"){
			SINGLEPROMAINTYPE_NAME = "市政工程"
		} 
		html+='<td style="text-align: center;">'+SINGLEPROMAINTYPE_NAME+'</td>';
		html+='<td style="text-align: center;">'+childrenSbgcInfo[i].STRUCTQUALTYPE_SBGC+'</td>';
		if(childrenSbgcInfo[i].ARCHHEIGHT){					
			html+='<td style="text-align: center;">'+childrenSbgcInfo[i].ARCHHEIGHT+' </td>';
		} else if(childrenSbgcInfo[i].MUNILENGTH){					
			html+='<td style="text-align: center;">'+childrenSbgcInfo[i].MUNILENGTH+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		if(childrenSbgcInfo[i].ARCHAREA){					
			html+='<td style="text-align: center;">'+childrenSbgcInfo[i].ARCHAREA+' </td>';
		} else if(childrenSbgcInfo[i].FLOORAREA){					
			html+='<td style="text-align: center;">'+childrenSbgcInfo[i].FLOORAREA+' </td>';
		} else{
			html+='<td style="text-align: center;"></td>';
		}
		html+='<td style="text-align: center;">'+childrenSbgcInfo[i].ITEMINVEST+'</td>';
		html+='<td style="text-align: center;">';
		html+='<a href="javascript:void(0);" class="eflowbutton" onclick="editSbgcChildren(\'addSbgc\',\'sbgcTr_'+index+'_'+initSbgcChildrenNum+'\',\''+currentTime+'\');">编 辑</a>';
		html+='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>';
		html+='</td>';
		html+='</tr>';
		$("#sbdxsgcDiv").children("div").eq(index).find("[id$='childrenSbgcTable']").append(html);
		$("#sbgcTr_"+index+"_"+initSbgcChildrenNum+" [name='ROW_JSON']").val(JSON.stringify(childrenSbgcInfo[i]));
		initSbgcChildrenNum++;
	}
	
}


/**
 * 获取上部工程信息
 */
function getSbgcJson(){
	var radioVal = $("input[name='SGXK_SBXMLX']:checked").val();
	if(radioVal!='3'){
		return "";
	}
	var infoArray = [];
	$("#sbdxsgcDiv").children("div").each(function(i){
		//结构类型
		var STRUCTQUALTYPE = "";
    	$(this).find("[name$='STRUCTQUALTYPE_SBGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  STRUCTQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(STRUCTQUALTYPE!=""){
	    	STRUCTQUALTYPE = STRUCTQUALTYPE.substring(0,STRUCTQUALTYPE.lastIndexOf(","));
	    }
		//基础类型
		var BASICQUALTYPE = "";
    	$(this).find("[name$='BASICQUALTYPE_SBGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BASICQUALTYPE+=($(this).val()+",");
	          }
	    });
	    if(BASICQUALTYPE!=""){
	    	BASICQUALTYPE = BASICQUALTYPE.substring(0,BASICQUALTYPE.lastIndexOf(","));
	    }
		//结构抗震等级
		var REBELQUAKELEVEL = "";
    	$(this).find("[name$='REBELQUAKELEVEL_SBGC'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  REBELQUAKELEVEL+=($(this).val()+",");
	          }
	    });
	    if(REBELQUAKELEVEL!=""){
	    	REBELQUAKELEVEL = REBELQUAKELEVEL.substring(0,REBELQUAKELEVEL.lastIndexOf(","));
	    }
		var SINGLEPRONAME = $(this).find("[name$='SINGLEPRONAME']").val();//单位工程名称
		//施工单位
		var BUILDUNITS = "";
    	$(this).find("[name$='BUILDUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  BUILDUNITS+=($(this).val()+",");
	          }
	    });
	    if(BUILDUNITS!=""){
	    	BUILDUNITS = BUILDUNITS.substring(0,BUILDUNITS.lastIndexOf(","));
	    }
		//监理单位
		var SUPERVISIONUNITS = "";
    	$(this).find("[name$='SUPERVISIONUNITS'][type='checkbox']").each(function(){
	          var checked= $(this).is(':checked');
	          if(checked){
	          	  SUPERVISIONUNITS+=($(this).val()+",");
	          }
	    });
	    if(SUPERVISIONUNITS!=""){
	    	SUPERVISIONUNITS = SUPERVISIONUNITS.substring(0,SUPERVISIONUNITS.lastIndexOf(","));
	    }
		var SINGLEPROMAINTYPE = $(this).find("[name$='SINGLEPROMAINTYPE']").val();//单位工程类别
		var SINGLEPROTYPE = $(this).find("[name$='SINGLEPROTYPE']").val();//单位工程类别
		var SINGLEPROSUBTYPE = $(this).find("[name$='SINGLEPROSUBTYPE']").val();//单位工程类别
		var ARCHHEIGHT = $(this).find("[name$='ARCHHEIGHT']").val();//建筑高度		
		var ARCHAREA = $(this).find("[name$='ARCHAREA']").val();	//建筑面积
		var MUNILENGTH = $(this).find("[name$='MUNILENGTH']").val();//市政长度
		var FLOORAREA = $(this).find("[name$='FLOORAREA']").val();// 路桥面积		
		var ISSUPERHIGHTBUILDING_SBGC = $(this).find("[name$='ISSUPERHIGHTBUILDING_SBGC']:checked").val();//是否超限高层建筑
		var ISSHOCKISOLATIONBUILDING_SBGC = $(this).find("[name$='ISSHOCKISOLATIONBUILDING_SBGC']:checked").val();//是否为减隔震建筑
		var ISEQUIPPEDARCHITECTURE_SBGC = $(this).find("[name$='ISEQUIPPEDARCHITECTURE_SBGC']:checked").val();//是否为装配式建筑
		var ISGREENBUILDING_SBGC = $(this).find("[name$='ISGREENBUILDING_SBGC']:checked").val();//是否绿色建筑
		var GREENBUIDINGLEVEL = $(this).find("[name$='GREENBUIDINGLEVEL_SBGC']").val();//绿色建筑等级	
		var STRUCTUPFLOORNUM = $(this).find("[name$='STRUCTUPFLOORNUM']").val();//±0.000以上层数		
		var STRUCTDWFLOORNUM = $(this).find("[name$='STRUCTDWFLOORNUM']").val();	// ±0.000以下层数
		var STRUCTUPFLOORAREA_DWGC = $(this).find("[name$='STRUCTUPFLOORAREA_DWGC']").val();// ±0.000以上面积
		var STRUCTDWFLOORAREA_DWGC = $(this).find("[name$='STRUCTDWFLOORAREA_DWGC']").val();// ±0.000以下面积	
		
		var FOUNDSUPWAY = $(this).find("[name$='FOUNDSUPWAY']").val();//基坑支护方法	
		var FOUNDDEPTH = $(this).find("[name$='FOUNDDEPTH']").val();	// 基坑开挖深度
		var SLOPESUPWAY = $(this).find("[name$='SLOPESUPWAY']").val();// 高边坡支护方法
		var SLOPEHEIGHT = $(this).find("[name$='SLOPEHEIGHT']").val();// 边坡高度
		var REFRACTLEVEL = $(this).find("[name$='REFRACTLEVEL']").val();//耐火等级		
		var STRUCTMAXSPAN = $(this).find("[name$='STRUCTMAXSPAN']").val();	// 结构最大跨度
		var ITEMINVEST = $(this).find("[name$='ITEMINVEST']").val();// 工程总造价（万元）
		var INFRALEVEL = $(this).find("[name$='INFRALEVEL']").val();// 基础建设等级
		var REMARK = $(this).find("[name$='REMARK']").val();// 备注		

		var CHILDRENSBGC_JSON = ""// 子桩基工程	
		var childrenArray = [];
		$(this).find("[id$='childrenSbgcTable']").find("tr").each(function(i){
			var ROW_JSON = $(this).find("[name$='ROW_JSON']").val();// 备注 
			if(ROW_JSON){				
				childrenArray.push(JSON.parse(ROW_JSON));	
			}		
		});		
		CHILDRENSBGC_JSON = JSON.stringify(childrenArray);
		var info = {};
		info.FOUNDSUPWAY = FOUNDSUPWAY;
		info.FOUNDDEPTH = FOUNDDEPTH;
		info.SLOPESUPWAY = SLOPESUPWAY;
		info.SLOPEHEIGHT = SLOPEHEIGHT;
		info.REFRACTLEVEL = REFRACTLEVEL;
		info.STRUCTMAXSPAN = STRUCTMAXSPAN;
		info.ITEMINVEST = ITEMINVEST;
		info.INFRALEVEL = INFRALEVEL;
		info.REMARK = REMARK;
		info.GREENBUIDINGLEVEL = GREENBUIDINGLEVEL;
		info.STRUCTUPFLOORNUM = STRUCTUPFLOORNUM;
		info.STRUCTDWFLOORNUM = STRUCTDWFLOORNUM;
		info.STRUCTUPFLOORAREA_DWGC = STRUCTUPFLOORAREA_DWGC;
		info.STRUCTDWFLOORAREA_DWGC = STRUCTDWFLOORAREA_DWGC;
		info.SINGLEPRONAME = SINGLEPRONAME;
		info.BUILDUNITS = BUILDUNITS;
		info.SUPERVISIONUNITS = SUPERVISIONUNITS;
		info.SINGLEPROMAINTYPE = SINGLEPROMAINTYPE;
		info.SINGLEPROTYPE = SINGLEPROTYPE;
		info.SINGLEPROSUBTYPE = SINGLEPROSUBTYPE;
		info.ARCHHEIGHT = ARCHHEIGHT;
		info.ARCHAREA = ARCHAREA;
		info.MUNILENGTH = MUNILENGTH;
		info.FLOORAREA = FLOORAREA;
		info.ISSUPERHIGHTBUILDING_SBGC = ISSUPERHIGHTBUILDING_SBGC;
		info.ISSHOCKISOLATIONBUILDING_SBGC = ISSHOCKISOLATIONBUILDING_SBGC;
		info.ISEQUIPPEDARCHITECTURE_SBGC = ISEQUIPPEDARCHITECTURE_SBGC;
		info.ISGREENBUILDING_SBGC = ISGREENBUILDING_SBGC;
		info.STRUCTQUALTYPE = STRUCTQUALTYPE;
		info.BASICQUALTYPE = BASICQUALTYPE;
		info.REBELQUAKELEVEL = REBELQUAKELEVEL;
		info.CHILDRENSBGC_JSON = CHILDRENSBGC_JSON;
		infoArray.push(info);
	});
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}



function setSbgcGreenbuidinglevel(val,index){
	if(val==1){
		$("."+index+"greenbuidinglevelTr_sbgc").show();
		$("[name='"+index+"GREENBUIDINGLEVEL_SBGC']").addClass(" validate[required]");
	} else{		
		$("."+index+"greenbuidinglevelTr_sbgc").hide();
		$("[name='"+index+"GREENBUIDINGLEVEL_SBGC']").removeClass(" validate[required]");
	}
}


function setSbArcharea(index){
	var STRUCTUPFLOORAREA_DWGC = $("#sbdxsgcDiv").find("[name='"+index+"STRUCTUPFLOORAREA_DWGC']").val();//±0.000以上面积
	var STRUCTDWFLOORAREA_DWGC = $("#sbdxsgcDiv").find("[name='"+index+"STRUCTDWFLOORAREA_DWGC']").val();//±0.000以下面积
	var ARCHAREA = Number(STRUCTUPFLOORAREA_DWGC)+Number(STRUCTDWFLOORAREA_DWGC);	
	if(Number(ARCHAREA)>0){	
		$("#sbdxsgcDiv").find("[name='"+index+"ARCHAREA']").val(ARCHAREA.toFixed(3));
	}else{
		$("#sbdxsgcDiv").find("[name='"+index+"ARCHAREA']").val('');
	}
}
/**=================上部（地下室）结束================================*/





