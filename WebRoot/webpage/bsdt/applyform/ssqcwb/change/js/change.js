/**==========页面初始化js开始=======================================*/
$(function(){
  //----------------主页面开始------------------------
	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		 
		/*初始化表单数据*/
	    //公司类型默认-有限责任公司设立-默认不可修改
		$('input[name="COMPANY_SETTYPE"]').val("yxzrgssl");
		$("#gslx").combobox("disable");
		var COMPANY_SETTYPE = $('input[name="COMPANY_SETTYPE"]').val();
		var url = 'dicTypeController/load.do?parentTypeCode='+COMPANY_SETTYPE;
		$('#gslxzl').combobox('reload',url);
		
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
			$("#gslx").combobox("disable");
			$("#gslxzl").combobox("disable");
			$("#sllx").combobox("disable");
			$('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find('input,textarea').prop("readonly", true);
			$('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find('select').prop("disabled", "disabled");
			$('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find(".laydate-icon").attr('disabled',"disabled");
			$('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find(":button").attr('style',"display:none");
		}
		
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='网上预审'){
			$("#holder").find("[name='CONTRIBUTIONS_REMAIN']").prop("readonly",false);
			$("#holder").find("[name='CONTRIBUTIONS_PRO']").prop("readonly", false);
			$("#holder").find("[name='PAIDCAPITAL']").prop("readonly", false);
		}
		
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			//初始化展示对应的变更项
			showChangeItem(flowSubmitObj.busRecord.CHANGE_OPTIONS);
			//动态加载原企业类型、公司性质&变更后的企业类型、公司性质
			$("#COMPANY_TYPE").combobox("setValue",flowSubmitObj.busRecord.COMPANY_TYPE);
			$("#gslxzl").combobox("setValue",flowSubmitObj.busRecord.COMPANY_TYPE_CHANGE);
			var COMPANY_TYPE_CHANGE = flowSubmitObj.busRecord.COMPANY_TYPE_CHANGE;
			var url = "";
			if(COMPANY_TYPE_CHANGE==null || COMPANY_TYPE_CHANGE=="" || COMPANY_TYPE_CHANGE==undefined){//性质未变更
				url = 'dictionaryController/loadData.do?typeCode='+flowSubmitObj.busRecord.COMPANY_TYPE+'&orderType=asc';
			}else{//性质变更
				url = 'dictionaryController/loadData.do?typeCode='+flowSubmitObj.busRecord.COMPANY_TYPE_CHANGE+'&orderType=asc';
			}
		    $('#sllx').combobox('reload',url);
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_COMMERCIAL_DOMESTIC_FORM");
			initAutoTable(flowSubmitObj);
			
			if(flowSubmitObj.busRecord.IS_LIAISON_CHANGE!='1'){
				$("#lxyxx").find("input,select").attr("disabled",true);
			}
		}else{
			$("input[name='IS_BUSSINESS_ADDR']").eq(0).attr("checked","checked");//生产经营地址
			$("input[name='OPRRATE_TERM_TYPE']").eq(0).attr("checked","checked");//经营期限
			$("input[name='FINANCE_TYPE']").eq(1).attr("checked","checked");
			$("input[name='BILL_TYPE']").eq(1).attr("checked","checked");
			$("input[name='CONTRACT_TYPE']").eq(1).attr("checked","checked");
			$("input[name='LEGALSEAL_TYPE']").eq(1).attr("checked","checked");
			$("input[name='FILL_DATE']").val(laydate.now());
			$("#selectcurrency").find("option").eq(1).attr("selected","selected");
			//$("select[name='CURRENCY']").find("option:selected").value("人民币");
			$('#jlzw').find('option').eq(6).attr('selected','selected');
		}
		
		var notFiledCheck = ['网上预审'];
		if(notFiledCheck.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0
			&&!(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='undefined')
			&&flowSubmitObj.EFLOW_ISQUERYDETAIL=='false'){
			var tabs = $('#T_COMMERCIAL_CHANGE_DOMESTIC_FORM').find(".tab_width");
			var index = 0;
			tabs.each(function(){
				var fieldid = "field_"+index;
				var tabtext = $(this).text();
				var text = $(this).next("td").find("input,select,textarea").attr("name");
				var belongId = $(this).parent().parent().parent().parent().parent().attr("id");
				if(tabtext.indexOf("*")>=0){
					tabtext = tabtext.substr(1,tabtext.length-2);
				}else{
					tabtext = tabtext.substr(0,tabtext.length-1);
				}
				index++;
				if($(this).closest("div").attr("id")!='uploadFile'){
					$(this).prepend("<input class=\"shzdsh\" type=\"checkbox\" id=\""+fieldid+"\"name=\""+tabtext+"\" value=\""+text+"\" onclick=\"columnCheck(this.id,this.checked,this.value,this.name,'"+belongId+"')\" checked=\"checked\">");
				}
			});
		}
		setFieldAudit(flowSubmitObj.EFLOW_EXEID,flowSubmitObj.EFLOW_CURUSEROPERNODENAME,flowSubmitObj.EFLOW_ISQUERYDETAIL);
	}
  //----------------主页面结束------------------------
	
  //----------------企业变更选择项开始-------------------
	
	//变更选择事项-点击事件
	/*$("input[name='CHANGE_OPTIONS']").click(function(){
		var check_value=[];
		$('input[name="CHANGE_OPTIONS"]:checked').each(function(){    
			check_value.push($(this).val());    
		}); 
		//动态展示对应的变更项
		showChangeItem(check_value);
	});*/
	
	
	
  //----------------企业变更选择项结束-------------------
	
  //----------------企业基本信息开始-------------------
    
	    //经营期限起始-结束（原）
		var ystart = {
			    elem: "#ystart",
			    format: "YYYY-MM-DD",
			    istime: false,
			    choose: function(datas){
			    	if(datas==undefined){		
						yend.start = datas;
					}else{
						yend.min=datas;
						yend.start = datas;
					}
			    }
		};
		var yend = {
		    elem: "#yend",
		    format: "YYYY-MM-DD",
		    istime: false,
		    choose: function(datas){
		    	if(datas==undefined){
					ystart.max=null;
				}else{
					ystart.max=datas;
				}
		    }
		};
		laydate(ystart);
		laydate(yend);
		
		/*//经营期限起始-结束（变更后）
		var cstart = {
			    elem: "#cstart",
			    format: "YYYY-MM-DD",
			    istime: false,
			    choose: function(datas){
			    	if(datas==undefined){		
						cend.start = datas;
					}else{
						cend.min=datas;
						cend.start = datas;
					}
			    }
		};
		var cend = {
		    elem: "#cend",
		    format: "YYYY-MM-DD",
		    istime: false,
		    choose: function(datas){
		    	if(datas==undefined){
					cstart.max=null;
				}else{
					cstart.max=datas;
				}
		    }
		};
		laydate(cstart);
		laydate(cend);*/
	
	//----------------企业基本信息结束-------------------
		
	//----------------股东信息开始-----------------------
		var period = {
	    	elem: "#period",
		    format: "YYYY-MM-DD",
		    istime: false,
		    choose : function(data){
		    	checkPeriod(data);
		    }
		};
		laydate(period);
	//----------------股东信息结束-----------------------
	
	//----------------经办人和财务负责人和办税人员信息开始--------
	
		/*var vstart = {
		    elem: "#vstart",
		    format: "YYYY-MM-DD",
		    istime: false,
		    choose: function(datas){
		    	if(datas==undefined){		
					vend.start = datas;
				}else{
					vend.min=datas;
					vend.start = datas;
				}
		    }
		};
		var vend = {
		    elem: "#vend",
		    format: "YYYY-MM-DD",
		    istime: false,
		    choose: function(datas){
		    	if(datas==undefined){
					vstart.max=null;
				}else{
					vstart.max=datas;
				}
		    }
		};
		laydate(vstart);
		laydate(vend);*/

	//----------------经办人和财务负责人和办税人员信息结束-----------------------
});	

/**==========页面初始化js结束=======================================*/
		
		

//初始化动态展示对应的变更项
function showChangeItem(value){
	var check_value = value.split(",");
	for(var i = 0;i<check_value.length;i++){
		if(check_value[i]=="01"){//名称变更
			$("#bgmcxx").attr("style"," ");
		}else if(check_value[i]=="02"){//住所变更
			$("#bgdzxx").attr("style"," ");
		}else if(check_value[i]=="03"){//法定代表人变更
			$("#newfddbrxx").attr("style"," ");
		}else if(check_value[i]=="04"){//企业类型变更
			$("#gslxtable").attr("style"," ");
		}else if(check_value[i]=="05" || check_value[i]=="07" || check_value[i]=="09"){
			//注册资本（金）变更/投资人（股权）变更/股东或股权发起人改变姓名或名称
			$("#bgzjxx").attr("style"," ");
			$("#bgholder").attr("style"," ");
			$("#bgholdertime").attr("style"," ");
		}else if(check_value[i]=="06"){//经营（营业）期限变更
			$("#bgjyqx").attr("style"," ");
		}else if(check_value[i]=="08"){//经营范围变更
			$("#bgjjfw").attr("style"," ");
		}else if(check_value[i]=="10"){//董事备案
			$("#newdirector").attr("style"," ");
		}else if(check_value[i]=="11"){//监事备案
			$("#newsupervisor").attr("style"," ");
		}else if(check_value[i]=="12"){//经理备案
			$("#newmanager").attr("style"," ");
		}
	}
}

//初始化表单值（JSON字段）
function initAutoTable(flowSubmitObj){
	//股东信息JSON
	var holderJson = flowSubmitObj.busRecord.HOLDER_JSON;
	var holders = eval(holderJson);
	for(var i=0;i<holders.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(holders[i],"holder_1");
			if(holders[i].CONTRIBUTIONS_PRO==null || holders[i].CONTRIBUTIONS_PRO==undefined
					|| holders[i].CONTRIBUTIONS_PRO==""){
				var zrcze = holders[i].CONTRIBUTIONS_REMAIN;	
				var investment = flowSubmitObj.busRecord.INVESTMENT;
				var a = Number(zrcze)/(Number(investment))*100;
				$("#holder_1").find("[name='CONTRIBUTIONS_PRO']").val(a.toFixed(2)+"%");
			}
		}else{
			addHolder();
			FlowUtil.initFormFieldValue(holders[i],"holder_"+(i+1));
			if(holders[i].CONTRIBUTIONS_PRO==null || holders[i].CONTRIBUTIONS_PRO==undefined
					|| holders[i].CONTRIBUTIONS_PRO==""){
				var zrcze = holders[i].CONTRIBUTIONS_REMAIN;	
				var investment = flowSubmitObj.busRecord.INVESTMENT;
				var a = Number(zrcze)/(Number(investment))*100;
				$("#holder_"+(i+1)).find("[name='CONTRIBUTIONS_PRO']").val(a.toFixed(2)+"%");
			}
		}
	}
	
	//变更股东信息JSON
	var holderChangeJson = flowSubmitObj.busRecord.HOLDER_JSON_CHANGE;
	if(holderChangeJson!=null && holderChangeJson!=undefined && holderChangeJson!=""){
		var holderChanges = eval(holderChangeJson);
		for(var i=0;i<holderChanges.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(holderChanges[i],"bgholder_1");
				//一个股东可能存在多个股权来源、出资方式
				var holderChangeWay =  eval(holderChanges[i].GQLY_JSON);
				for(var j=0;j<holderChangeWay.length;j++){
					if(j==0){
						FlowUtil.initFormFieldValue(holderChangeWay[j],"bgholder_1_waytabble_1");
						if(holderChangeWay[j].stockFrom=="新增"){
							$("#bgholder_1_total_1").attr("style","display:none");
						}else{
							$("#bgholder_1_total_1").attr("style","");
						}
					}else{
						var sn = j+1;
						addBgGdWayHolder('1',sn);
						FlowUtil.initFormFieldValue(holderChangeWay[j],"bgholder_1_waytabble_"+(sn));
						if(holderChangeWay[j].stockFrom=="新增"){
							$("#bgholder_1_total_"+sn).attr("style","display:none");
						}else{
							$("#bgholder_1_total_"+sn).attr("style","");
						}
					}
				}
			}else{
				var holderIdsn = i+1;
				addHolderChange(holderIdsn);
				//一个股东可能存在多个股权来源、出资方式
				var holderChangeWay =  eval(holderChanges[i].GQLY_JSON);
				for(var j=0;j<holderChangeWay.length;j++){
					if(j==0){
						FlowUtil.initFormFieldValue(holderChangeWay[j],"bgholder_"+holderIdsn+"_waytabble_1");
						if(holderChangeWay[j].stockFrom=="新增"){
							$("#bgholder_"+holderIdsn+"_total_1").attr("style","display:none");
						}else{
							$("#bgholder_"+holderIdsn+"_total_1").attr("style","");
						}
					}else{
						var sn = j+1;
						addBgGdWayHolder(holderIdsn,sn);
						FlowUtil.initFormFieldValue(holderChangeWay[j],"bgholder_"+holderIdsn+"_waytabble_"+(sn));
						if(holderChangeWay[j].stockFrom=="新增"){
							$("#bgholder_"+holderIdsn+"_total_"+sn).attr("style","display:none");
						}else{
							$("#bgholder_"+holderIdsn+"_total_"+sn).attr("style","");
						}
					}
				}
				FlowUtil.initFormFieldValue(holderChanges[i],"bgholder_"+(i+1));
			}
		}
	}
	
	
	
	//董事信息JSON
	var directorJson = flowSubmitObj.busRecord.DIRECTOR_JSON;
	var directors = eval(directorJson);
	for(var i=0;i<directors.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(directors[i],"director_1");
		}else{
			addDirector();
			FlowUtil.initFormFieldValue(directors[i],"director_"+(i+1));
		}
	}
	
	//变更董事信息JSON
	var bgDirectorJson = flowSubmitObj.busRecord.DIRECTOR_JSON_CHANGE;
	if(bgDirectorJson!=null && bgDirectorJson!=undefined && bgDirectorJson!=""){
		var bgDirectors = eval(bgDirectorJson);
		for(var i=0;i<bgDirectors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(bgDirectors[i],"newdirector_1");
			}else{
				addBgDirector();
				FlowUtil.initFormFieldValue(bgDirectors[i],"newdirector_"+(i+1));
			}
		}
	}
	
	
	//监事信息JSON
	var supervisorJson = flowSubmitObj.busRecord.SUPERVISOR_JSON;
	var supervisors = eval(supervisorJson);
	for(var i=0;i<supervisors.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(supervisors[i],"supervisor_1");
		}else{
			addSupervisor();
			FlowUtil.initFormFieldValue(supervisors[i],"supervisor_"+(i+1));
		}
	}
	
	//变更监事信息JSON
	var bgSupervisorJson = flowSubmitObj.busRecord.SUPERVISOR_JSON_CHANGE;
	if(bgSupervisorJson!=null && bgSupervisorJson!=undefined && bgSupervisorJson!=""){
		var bgSupervisors = eval(bgSupervisorJson);
		for(var i=0;i<bgSupervisors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(bgSupervisors[i],"newsupervisor_1");
			}else{
				addBgSupervisor();
				FlowUtil.initFormFieldValue(bgSupervisors[i],"newsupervisor_"+(i+1));
			}
		}
	}
	
	
	//经理信息JSON
	var managerJson = flowSubmitObj.busRecord.MANAGER_JSON;
	var managers = eval(managerJson);
	for(var i=0;i<managers.length;i++){
		if(i==0){
			FlowUtil.initFormFieldValue(managers[i],"manager_1");
		}else{
			addManager();
			FlowUtil.initFormFieldValue(managers[i],"manager_"+(i+1));
		}
	}
	

	//变更经理信息JSON
	var bgManagerJson = flowSubmitObj.busRecord.MANAGER_JSON_CHANGE;
	if(bgManagerJson!=null && bgManagerJson!=undefined && bgManagerJson!=""){
		var bgManagers = eval(bgManagerJson);
		for(var i=0;i<bgManagers.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(bgManagers[i],"newmanager_1");
			}else{
				addBgManager();
				FlowUtil.initFormFieldValue(bgManagers[i],"newmanager_"+(i+1));
			}
		}
	}
	
}

function columnCheck(id,checked,value,name,belongId){
	if(belongId=='undefined'){
		belongId='';
	}
	var flowSubmitObj = FlowUtil.getFlowObj();
	var curNode = flowSubmitObj.EFLOW_CURUSEROPERNODENAME;
	var exeId = flowSubmitObj.EFLOW_EXEID;
	var url1 = "commercialController.do?goFieldAudit&fieldName=" + value + "&curNode=" + curNode + "&exeId=" + exeId + "&fieldId=" + id + "&fieldtext=" + name + "&belongId=" + belongId;
	var url2 = "commercialController.do?deleteFieldOpinion&fieldName=" + value + "&curNode=" + curNode + "&exeId=" + exeId;
	if(!checked){
		$.dialog.open(encodeURI(url1), {
			title : "字段审核意见",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false,
			cancel : false
		}, false);
	}else{
		$.ajax({
            type: "POST",
            url: encodeURI(url2),
            async: false, 
            cache: false,
            success: function (responseText) {
            	var resultJson = $.parseJSON(responseText);
            	if(resultJson.success){
					$("[id='opinion_"+id+"']").each(function(index){							
						$(this).remove();
					});
            		parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						ok: true
					});
            	}
            }
        });
	}
}
/**显示字段审核意见*/
function showopinion(id,opinion){
	if($("#"+id).parents('td:first').next().find("input:text,select,textarea").length>0){
		$("#"+id).parents('td:first').next().find("input:text,select,textarea").after("<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见："+opinion+"</span>");
	}else if($("#"+id).parents('td:first').next().find(":radio,:checkbox").length>0){
		var html = $("#"+id).parents('td:first').next().html();
		$("#"+id).parents('td:first').next().html(html+"<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见："+opinion+"</span>");
	}
	
}
/**取消审核*/
function cancelCheck(id){
	$("#"+id).prop("checked",true);
}
/**初始化字段意见*/
function setFieldAudit(exeId,curNode,EFLOW_ISQUERYDETAIL){
	var url = "fieldAuditController/datagrid.do?isShow=1&Q_T.EXE_ID_EQ="+exeId;
	if(EFLOW_ISQUERYDETAIL=='true'){
		url+= "&isShowDelete=1"
	}
	$.post(url,{
	}, function(responseText, status, xhr) {
		var resultJson = responseText.rows;
		for(var i=0;i<resultJson.length;i++){	
			var BELONG_ID =resultJson[i].BELONG_ID;
			var num = 0;
			var parentId = "";
			var $obj;
			if(BELONG_ID!=null && BELONG_ID!=''){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
				parentId = BELONG_ID.substring(0,BELONG_ID.lastIndexOf("_"));
			}
			if(isNaN(num)){
				num = 0;
				$obj = $("[name='"+resultJson[i].FIELD_NAME+"']");
			}else{
				$obj = $("#"+parentId).find("[name='"+resultJson[i].FIELD_NAME+"']");
			}
			//$("[name='"+resultJson[i].FIELD_NAME+"']").each(function(index){
			$obj.each(function(index){				
				if(num==index&&(resultJson[i].AUDIT_NODE==curNode||EFLOW_ISQUERYDETAIL=='true')){
					var targetField = $(this);
					var type = targetField.attr("type");
					var id = targetField.parent().prev().find("input:checkbox").attr("id");
					$("#"+id).prop("checked",false);
					if(type=="checkbox"||type=="radio"){	
						var html = targetField.parent().html();
						targetField.parent().html(html+"<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见（"+resultJson[i].CREATE_TIME+"）："+resultJson[i].AUDIT_OPINION+"</span>");					
						
					}else{						
						targetField.parent().append("<span id=\"opinion_"+id+"\" style=\"color:red;\"><br>审核意见（"+resultJson[i].CREATE_TIME+"）："+resultJson[i].AUDIT_OPINION+"</span>");
					}
				}				
			});
		}
	});
}

/**==========企业基本信息页面js开始=======================================*/

function choseScope(){
	var selected = $("textarea[name='BUSSINESS_SCOPE']").val();
	selected = selected.substr(0,selected.indexOf("("));
	$.dialog.open("commercialController.do?scopeSelector&selected="+selected, {
		title : "经营范围筛选",
		width : "550px",
		height : "480px",
		lock : true,
		resize : false,
        close: function () {
			var selectClassify = art.dialog.data("selectClassify");
			if(selectClassify){
				$("textarea[name='BUSSINESS_SCOPE']").val(selectClassify.chooseClassify+"(依法须经批准的项目，经相关部门批准后方可展开经营活动)");
			}
			art.dialog.removeData("selectClassify");
		}
	}, false);
}

//经营范围选择器
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
				$("[name='BUSSINESS_SCOPE']").val(selectInduInfo.induNames);
				$("[name='BUSSINESS_SCOPE_ID']").val(selectInduInfo.induIds);
				$("[name='INVEST_INDUSTRY']").val(selectInduInfo.induNames);
				$("[name='INVEST_INDUSTRY_ID']").val(selectInduInfo.induIds);
				$("[name='INDUSTRY_CODE']").val(selectInduInfo.induCodes);
				art.dialog.removeData("selectInduInfo");
			}
		}
	}, false);
};

//经营范围选择器
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
					art.dialog.removeData("selectBusScopeInfo");
				}
			}
		}, false);
};

/**==========企业基本信息页面js结束=======================================*/



/**==========股东信息页面js开始=======================================*/
var currentSn = 1;
function addHolder(){
	currentSn = currentSn+1;
	var table = document.getElementById("holder");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteHolder('");
	if(currentSn>10){
		var replacestr = trHtml.substring(findex,findex+18);
	}else{
		var replacestr = trHtml.substring(findex,findex+17);
	}
	trHtml = trHtml.replace(replacestr,"deleteHolder('"+currentSn+"')");
	trHtml = "<tr id=\"holder_"+currentSn+"\">"+trHtml+"</tr>";
	$("#holder").append(trHtml);
}

function deleteHolder(idSn){
	var table = document.getElementById("holder");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个股东信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#holder_"+idSn).remove();
	//calculation();
}

function calculation(){
	var allSum = 0;
	var table = document.getElementById("holder");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var checkHaveName = ['INVESTMENT_CASH','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var holderSum = 0;
		$("#holder_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""){
				  holderSum = parseFloat(holderSum)+parseFloat(inputValue);
				  allSum = parseFloat(allSum)+parseFloat(inputValue);
			  }
	    });
		$("#holder_"+i).find("*[name='CONTRIBUTIONS']").val(holderSum);
	}
	$("input[name='INVESTMENT']").val(allSum);
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var holderSum = $("#holder_"+i).find("*[name='CONTRIBUTIONS']").val();
		var propotion = parseFloat(holderSum/allSum)*100;
		if(propotion==100){
			$("#holder_"+i).find("*[name='PROPORTION']").val(propotion+"%");
		}else{
			$("#holder_"+i).find("*[name='PROPORTION']").val(propotion.toFixed(2)+"%");
		}
	}
}

function getHolderJson(){
	var datas = [];
	var table = document.getElementById("holder");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var haveOne = false;
		var checkHaveName = ['INVESTMENT_CASH','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var trData = {};
		$("#holder_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
				  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""&&inputValue!=null&&inputValue!="undefined"){
					  haveOne = true;
				  }
			  }          
	    });
		if(!haveOne){
			parent.art.dialog({
				content: "原股东出资方式至少包含一项！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		//过滤一下
		if(null!=trData.SHAREHOLDER_NAME&& trData.SHAREHOLDER_NAME!=""){
			var gdxx = {};
			gdxx.SHAREHOLDER_NAME = trData.SHAREHOLDER_NAME;
			gdxx.SHAREHOLDER_TYPE = trData.SHAREHOLDER_TYPE;
			gdxx.LICENCE_TYPE = trData.LICENCE_TYPE;
			gdxx.LICENCE_NO = trData.LICENCE_NO;
			gdxx.ID_ADDRESS = trData.ID_ADDRESS;
			gdxx.CONTACT_WAY = trData.CONTACT_WAY;
			gdxx.LEGAL_PERSON = trData.LEGAL_PERSON;
            gdxx.LEGAL_IDNO_PERSON = trData.LEGAL_IDNO_PERSON;
			gdxx.INVESTMENT_CASH = trData.INVESTMENT_CASH;
			gdxx.INVESTMENT_MATERIAL = trData.INVESTMENT_MATERIAL;
			gdxx.INVESTMENT_TECHNOLOGY = trData.INVESTMENT_TECHNOLOGY;
			gdxx.INVESTMENT_LAND = trData.INVESTMENT_LAND;
			gdxx.INVESTMENT_STOCK = trData.INVESTMENT_STOCK;
			gdxx.INVESTMENT_OTHER = trData.INVESTMENT_OTHER;
			
			gdxx.CONTRIBUTIONS = trData.CONTRIBUTIONS;
			gdxx.CONTRIBUTIONS_REMAIN = trData.CONTRIBUTIONS_REMAIN;
			gdxx.CONTRIBUTIONS_PRO = trData.CONTRIBUTIONS_PRO;
			gdxx.PROPORTION = trData.PROPORTION;
			gdxx.PAIDCAPITAL=trData.PAIDCAPITAL;
			gdxx.PAYMENT_TIME=trData.PAYMENT_TIME;
			gdxx.SHAREHOLDER_COMPANYCOUNTRY=trData.SHAREHOLDER_COMPANYCOUNTRY;
			datas.push(gdxx);
		}
	}
	$("input[type='hidden'][name='HOLDER_JSON']").val(JSON.stringify(datas));
	return true;
}
/**==========股东信息页面js结束=======================================*/

/**==========变更后股东信息页面js开始=======================================*/

//股东信息变更
function addHolderChange(idSn){
	var table = document.getElementById("bgholder");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteBgHolder('");
	var waytableIndex = trHtml.indexOf("bgholder_1_waytabble");

	var replacestr = trHtml.substring(findex,findex+19);
	var replaceWaytable = trHtml.substring(waytableIndex,waytableIndex+20);
	
	trHtml = trHtml.replace(replacestr,"deleteBgHolder('"+idSn+"')");
	trHtml = "<tr id=\"bgholder_"+idSn+"\">"+trHtml+"</tr>";
	
	trHtml = trHtml.replace(replaceWaytable,"bgholder_"+idSn+"_waytabble");
	$("#bgholder").append(trHtml);
	//每次增加股东信息，对应出资方式只初始化一个
	$("#bgholder_"+idSn+"_waytabble").html("");
	var bgWayTable = document.getElementById("bgholder_1_waytabble");
	var trBgWayContent = bgWayTable.getElementsByTagName("tr")[0];
	
	var trBgWayHtml = trBgWayContent.innerHTML;
	var waytotalIndex = trBgWayHtml.indexOf("bgholder_1_total_1");
	var waydelbtnIndex = trBgWayHtml.indexOf("deleteWayHolder('1','1')");
	
	var replaceWaytotal = trBgWayHtml.substring(waytotalIndex,waytotalIndex+18);
	var replaceWaybtn = trBgWayHtml.substring(waydelbtnIndex,waydelbtnIndex+24);
	trBgWayHtml = trBgWayHtml.replace(replaceWaytotal,"bgholder_"+idSn+"_total_1");
	trBgWayHtml = trBgWayHtml.replace(replaceWaybtn,"deleteWayHolder('"+idSn+"','1')");
	trBgWayHtml = "<tr id=\"bgholder_"+idSn+"_waytabble_1\">"+trBgWayHtml+"</tr>";
	$("#bgholder_"+idSn+"_waytabble").append(trBgWayHtml);
}

function deleteBgHolder(idSn){
	var table = document.getElementById("bgholder");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个股东信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#bgholder_"+idSn).remove();
	//calculation();
}

//股东出资方式变更
function addBgGdWayHolder(hoderIdsn,idsn){
	var table = document.getElementById("bgholder_"+hoderIdsn+"_waytabble");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteWayHolder('");
	var totalIndex = trHtml.indexOf("bgholder_"+hoderIdsn+"_total_");
	var replacestr;
	var replacestr1;
	if(hoderIdsn>=10){
		replacestr = trHtml.substring(findex,findex+25);
		replacestr1 = trHtml.substring(totalIndex,totalIndex+19);
	}else{
		replacestr = trHtml.substring(findex,findex+24);
		replacestr1 = trHtml.substring(totalIndex,totalIndex+18);
	}
	trHtml = trHtml.replace(replacestr,"deleteWayHolder('"+hoderIdsn+"','"+idsn+"')");
	trHtml = trHtml.replace(replacestr1,"bgholder_"+hoderIdsn+"_total_"+idsn);
	trHtml = "<tr id=\"bgholder_"+hoderIdsn+"_waytabble_"+idsn+"\">"+trHtml+"</tr>";
	$("#bgholder_"+hoderIdsn+"_waytabble").append(trHtml);
}

function deleteWayHolder(hoderIdsn,idSn){
	var table = document.getElementById("bgholder_"+hoderIdsn+"_waytabble");
	if(table.rows.length==1){
		parent.art.dialog({
			content: "最少一个股东出资方式！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("bgholder_"+hoderIdsn+"_waytabble_"+idSn).remove();
	//calculation();
}

function calculation(){
	var allSum = 0;
	var table = document.getElementById("holder");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var checkHaveName = ['INVESTMENT_CASH','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var holderSum = 0;
		$("#holder_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(checkHaveName.indexOf(inputName)>=0&&inputValue!=""){
				  holderSum = parseFloat(holderSum)+parseFloat(inputValue);
				  allSum = parseFloat(allSum)+parseFloat(inputValue);
			  }
	    });
		$("#holder_"+i).find("*[name='CONTRIBUTIONS']").val(holderSum);
	}
	$("input[name='INVESTMENT']").val(allSum);
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var holderSum = $("#holder_"+i).find("*[name='CONTRIBUTIONS']").val();
		var propotion = parseFloat(holderSum/allSum)*100;
		if(propotion==100){
			$("#holder_"+i).find("*[name='PROPORTION']").val(propotion+"%");
		}else{
			$("#holder_"+i).find("*[name='PROPORTION']").val(propotion.toFixed(2)+"%");
		}
	}
}

function getHolderChangeJson(){
	var bgHolderTable = 1;
	var bgHolderTr = 1;
	var datas = [];
	var table = document.getElementById("bgholder");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var haveOne = false;
		var checkHaveName = ['INVESTMENT_CASH','INVESTMENT_MATERIAL','INVESTMENT_TECHNOLOGY','INVESTMENT_LAND','INVESTMENT_STOCK','INVESTMENT_OTHER'];
		var trData = {};
		$("#bgholder_"+i).each(function(){
			 var SHAREHOLDER_NAME = $(this).find("[name$='SHAREHOLDER_NAME']").val();//股东姓名/名称
			 var SHAREHOLDER_TYPE = $(this).find("[name$='SHAREHOLDER_TYPE']").val();//股东类型
			 var LICENCE_TYPE = $(this).find("[name$='LICENCE_TYPE']").val();//证照类型
			 var LICENCE_NO = $(this).find("[name$='LICENCE_NO']").val();//证件号码		
			 var ID_ADDRESS = $(this).find("[name$='ID_ADDRESS']").val();	//身份证件地址
			 var CONTACT_WAY = $(this).find("[name$='CONTACT_WAY']").val();//联系方式
	         var SHAREHOLDER_COMPANYCOUNTRY = $(this).find("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val();//国别
			 var LEGAL_PERSON = $(this).find("[name$='LEGAL_PERSON']").val();// 投资企业或其他组织机构法定代表人姓名
	         var LEGAL_IDNO_PERSON = $(this).find("[name$='LEGAL_IDNO_PERSON']").val();// 投资企业或其他组织机构法定代表人身份证号码  
	         
	         var INVESTMENT_CASH_TOTAL = $(this).find("[name$='INVESTMENT_CASH_TOTAL']").val();//现金总计
	 		 var INVESTMENT_MATERIAL_TOTAL = $(this).find("[name$='INVESTMENT_MATERIAL_TOTAL']").val();//实物总计
	 		 var INVESTMENT_TECHNOLOGY_TOTAL = $(this).find("[name$='INVESTMENT_TECHNOLOGY_TOTAL']").val();//技术出资总计
	 		 var INVESTMENT_LAND_TOTAL = $(this).find("[name$='INVESTMENT_LAND_TOTAL']").val();//土地使用权总计
	 		 var INVESTMENT_STOCK_TOTAL = $(this).find("[name$='INVESTMENT_STOCK_TOTAL']").val();//股权总计
	 		 var INVESTMENT_OTHER_TOTAL = $(this).find("[name$='INVESTMENT_OTHER_TOTAL']").val();//其他总计
	 		
	 		 var CONTRIBUTIONS = $(this).find("[name='CONTRIBUTIONS']").val();//认缴出资额
	 		 var PROPORTION = $(this).find("[name='PROPORTION']").val();//占注册资本比例
	         var PAIDCAPITAL = $(this).find("[name='PAIDCAPITAL']").val();//实缴出资额
	         
	         if(null!=SHAREHOLDER_NAME&&SHAREHOLDER_NAME!=""){
	 			trData["SHAREHOLDER_NAME"] = SHAREHOLDER_NAME;
	 			trData["SHAREHOLDER_TYPE"] = SHAREHOLDER_TYPE;
	 			trData["LICENCE_TYPE"] = LICENCE_TYPE;
	 			trData["LICENCE_NO"] = LICENCE_NO;
	 			trData["ID_ADDRESS"] = ID_ADDRESS;
	 			trData["CONTACT_WAY"] = CONTACT_WAY;
	 			trData["SHAREHOLDER_COMPANYCOUNTRY"]=SHAREHOLDER_COMPANYCOUNTRY;
	 			trData["LEGAL_PERSON"] = LEGAL_PERSON;
	 			trData["LEGAL_IDNO_PERSON"] = LEGAL_IDNO_PERSON;
	 			trData["INVESTMENT_OTHER_TOTAL"] = INVESTMENT_OTHER_TOTAL;
	 			trData["INVESTMENT_CASH_TOTAL"] = INVESTMENT_CASH_TOTAL;
	 			trData["INVESTMENT_MATERIAL_TOTAL"] = INVESTMENT_MATERIAL_TOTAL;
	 			trData["INVESTMENT_TECHNOLOGY_TOTAL"] = INVESTMENT_TECHNOLOGY_TOTAL;
	 			trData["INVESTMENT_LAND_TOTAL"] = INVESTMENT_LAND_TOTAL;
	 			trData["INVESTMENT_STOCK_TOTAL"] = INVESTMENT_STOCK_TOTAL;
	 			trData["INVESTMENT_OTHER_TOTAL"] = INVESTMENT_OTHER_TOTAL;
	 			trData["CONTRIBUTIONS"] = CONTRIBUTIONS;
	 			trData["PROPORTION"] = PROPORTION;
	 			trData["PAIDCAPITAL"]=PAIDCAPITAL;
	 		}
	    });
		if(trData["SHAREHOLDER_NAME"]!=null && trData["SHAREHOLDER_NAME"]!=""){
			 //获取股东出资方式（存在多个）
			  var bgholderways = getHolderBgCzJson(bgHolderTable,bgHolderTr);
			  if(bgholderways.length>0){
				  trData["GQLY_JSON"] = JSON.stringify(bgholderways);
				  haveOne = true;
			  }
			  bgHolderTable++;
			  if((JSON.stringify(trData)!="{}") && !haveOne){
				  parent.art.dialog({
					  content: "拟变更后的股东出资方式至少包含一项！",
					  icon:"warning",
					  ok: true
				  });
				  return false;
			  }
			datas.push(trData);
		}
	}
	if(datas.length>0){
		$("input[type='hidden'][name='HOLDER_JSON_CHANGE']").val(JSON.stringify(datas));
	}
	return true;
}

//获取股东出资方式JSON
function getHolderBgCzJson(bgHolderTable,bgHolderTr){
	var datas = [];
	var table = document.getElementById("bgholder_"+bgHolderTable+"_waytabble");
	if(table!=null){
		for ( var i = 1; i <= table.rows.length; i++) {
			var trData = {};
			$("#bgholder_"+bgHolderTable+"_waytabble_"+bgHolderTr).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }  
		    });
			bgHolderTr++;
			delete trData.deleteWayHolder;
			delete trData.addOneWayHolder;
			datas.push(trData);
		}
	}
	return datas;
}
/**==========变更股东信息页面js结束=======================================*/

/**==========董事信息页面js开始=======================================*/
var directorSn = 1;
function addDirector(){
	directorSn = directorSn+1;
	var table = document.getElementById("director");
	var trContent = table.getElementsByTagName("tr")[2];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteDirector('");
	if(directorSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteDirector('"+directorSn+"')");
	trHtml = "<tr id=\"director_"+directorSn+"\">"+trHtml+"</tr>";
	$("#director").append(trHtml);
}

function deleteDirector(idSn){
	var table = document.getElementById("director");
	if(table.rows.length==3){
		parent.art.dialog({
			content: "最少一个董事信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#director_"+idSn).remove();
}

function getDirectorJson(){
	var datas = [];
	var table = document.getElementById("director");
	for ( var i = 1; i <= table.rows.length-2; i++) {
		var trData = {};
		$("#director_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.DIRECTOR_NAME_OLD&& trData.DIRECTOR_NAME_OLD!=""){
			var director = {};
			director.DIRECTOR_NAME_OLD = trData.DIRECTOR_NAME_OLD;
			director.DIRECTOR_JOB_OLD = trData.DIRECTOR_JOB_OLD;
			director.DIRECTOR_IDTYPE_OLD = trData.DIRECTOR_IDTYPE_OLD;
			director.DIRECTOR_IDNO_OLD = trData.DIRECTOR_IDNO_OLD;
			director.DIRECTOR_PHONE_OLD = trData.DIRECTOR_PHONE_OLD;
			datas.push(director);
		}
	}
	$("input[type='hidden'][name='DIRECTOR_JSON']").val(JSON.stringify(datas));
}
/** ==========董事信息页面js结束======================================= */


/**==========变更董事信息页面js开始=======================================*/
var directorBgSn = 1;
function addBgDirector(){
	directorBgSn = directorBgSn+1;
	var table = document.getElementById("newdirector");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteBgDirector('");
	if(directorBgSn>10){
		var replacestr = trHtml.substring(findex,findex+22);
	}else{
		var replacestr = trHtml.substring(findex,findex+21);
	}
	trHtml = trHtml.replace(replacestr,"deleteBgDirector('"+directorBgSn+"')");
	trHtml = "<tr id=\"newdirector_"+directorBgSn+"\">"+trHtml+"</tr>";
	$("#newdirector").append(trHtml);
}

function deleteBgDirector(idSn){
	var table = document.getElementById("newdirector");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个董事信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#newdirector_"+idSn).remove();
}

function getBgDirectorJson(){
	var datas = [];
	var table = document.getElementById("newdirector");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#newdirector_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.DIRECTOR_NAME&& trData.DIRECTOR_NAME!=""){
			var bgDirector = {};
			bgDirector.DIRECTOR_NAME = trData.DIRECTOR_NAME;
			bgDirector.DIRECTOR_JOB = trData.DIRECTOR_JOB;
			bgDirector.DIRECTOR_IDTYPE = trData.DIRECTOR_IDTYPE;
			bgDirector.DIRECTOR_IDNO = trData.DIRECTOR_IDNO;
			bgDirector.DIRECTOR_PHONE = trData.DIRECTOR_PHONE;
			bgDirector.DIRECTOR_COUNTRY = trData.DIRECTOR_COUNTRY;
			bgDirector.DIRECTOR_GENERATION_MODE = trData.DIRECTOR_GENERATION_MODE;
			bgDirector.DIRECTOR_APPOINTOR = trData.DIRECTOR_APPOINTOR;
			bgDirector.DIRECTOR_OFFICETERM = trData.DIRECTOR_OFFICETERM;
			datas.push(bgDirector);
		}
		
	}
	if(datas.length>0){
		$("input[type='hidden'][name='DIRECTOR_JSON_CHANGE']").val(JSON.stringify(datas));
	}
}
/** ==========变更董事信息页面js结束======================================= */

/**==========监事信息页面js开始=======================================*/
var supervisorSn = 1;
function addSupervisor(){
	supervisorSn = supervisorSn+1;
	var table = document.getElementById("supervisor");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteSupervisor('");
	if(supervisorSn>10){
		var replacestr = trHtml.substring(findex,findex+22);
	}else{
		var replacestr = trHtml.substring(findex,findex+21);
	}
	trHtml = trHtml.replace(replacestr,"deleteSupervisor('"+supervisorSn+"')");
	trHtml = "<tr id=\"supervisor_"+supervisorSn+"\">"+trHtml+"</tr>";
	$("#supervisor").append(trHtml);
	//$("[id='sappointor']").val($("input[name='SUPERVISOR_APPOINTOR']").val());
}

function deleteSupervisor(idSn){
	var table = document.getElementById("supervisor");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个监事信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#supervisor_"+idSn).remove();
}

function getSupervisorJson(){
	var datas = [];
	var table = document.getElementById("supervisor");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#supervisor_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.SUPERVISOR_NAME_OLD&& trData.SUPERVISOR_NAME_OLD!=""){
			var supervisor = {};
			supervisor.SUPERVISOR_NAME_OLD = trData.SUPERVISOR_NAME_OLD;
			supervisor.SUPERVISOR_JOB_OLD = trData.SUPERVISOR_JOB_OLD;
			datas.push(supervisor);
		}
	}
	$("input[type='hidden'][name='SUPERVISOR_JSON']").val(JSON.stringify(datas));
}
/**==========监事信息页面js结束=======================================*/

/**==========变更监事信息页面js开始=======================================*/
var supervisorBgSn = 1;
function addBgSupervisor(){
	supervisorBgSn = supervisorBgSn+1;
	var table = document.getElementById("newsupervisor");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteBgSupervisor('");
	if(supervisorBgSn>10){
		var replacestr = trHtml.substring(findex,findex+24);
	}else{
		var replacestr = trHtml.substring(findex,findex+23);
	}
	trHtml = trHtml.replace(replacestr,"deleteBgSupervisor('"+supervisorBgSn+"')");
	trHtml = "<tr id=\"newsupervisor_"+supervisorBgSn+"\">"+trHtml+"</tr>";
	$("#newsupervisor").append(trHtml);
}

function deleteBgSupervisor(idSn){
	var table = document.getElementById("newsupervisor");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个监事信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#newsupervisor_"+idSn).remove();
}

function getBgSupervisorJson(){
	var datas = [];
	var table = document.getElementById("newsupervisor");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#newsupervisor_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.SUPERVISOR_NAME&& trData.SUPERVISOR_NAME!=""){
			var bgSupervisor = {};
			bgSupervisor.SUPERVISOR_NAME = trData.SUPERVISOR_NAME;
			bgSupervisor.SUPERVISOR_JOB = trData.SUPERVISOR_JOB;
			bgSupervisor.SUPERVISOR_IDTYPE = trData.SUPERVISOR_IDTYPE;
			bgSupervisor.SUPERVISOR_IDNO = trData.SUPERVISOR_IDNO;
			bgSupervisor.SUPERVISOR_GENERATION_MODE = trData.SUPERVISOR_GENERATION_MODE;
			bgSupervisor.SUPERVISOR_APPOINTOR = trData.SUPERVISOR_APPOINTOR;
			bgSupervisor.SUPERVISOR_OFFICETERM = trData.SUPERVISOR_OFFICETERM;
			bgSupervisor.SUPERVISOR_COUNTRY = trData.SUPERVISOR_COUNTRY;
			bgSupervisor.SUPERVISOR_GENERATION_MODE = trData.SUPERVISOR_GENERATION_MODE;
			bgSupervisor.SUPERVISOR_PHONE = trData.SUPERVISOR_PHONE;
			datas.push(bgSupervisor);
		}
	}
	if(datas.length>0){
		$("input[type='hidden'][name='SUPERVISOR_JSON_CHANGE']").val(JSON.stringify(datas));
	}

}
/**==========变更监事信息页面js结束=======================================*/


/**==========经理信息页面js开始=======================================*/
var managerSn = 1;
function addManager(){
	managerSn = managerSn+1;
	var table = document.getElementById("manager");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最多一个经理信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteManager('");
	if(managerSn>10){
		var replacestr = trHtml.substring(findex,findex+19);
	}else{
		var replacestr = trHtml.substring(findex,findex+18);
	}
	trHtml = trHtml.replace(replacestr,"deleteManager('"+managerSn+"')");
	trHtml = "<tr id=\"manager_"+managerSn+"\">"+trHtml+"</tr>";
	$("#manager").append(trHtml);
}

function deleteManager(idSn){
	var table = document.getElementById("manager");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个经理信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#manager_"+idSn).remove();
}

function getManagerJson(){
	var datas = [];
	var table = document.getElementById("manager");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#manager_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.MANAGER_NAME_OLD&& trData.MANAGER_NAME_OLD!=""){
			var manager = {};
			manager.MANAGER_NAME_OLD = trData.MANAGER_NAME_OLD;
			manager.MANAGER_JOB_OLD = trData.MANAGER_JOB_OLD;
			datas.push(manager);
		}
	}
	$("input[type='hidden'][name='MANAGER_JSON']").val(JSON.stringify(datas));
}
/**==========经理信息页面js结束=======================================*/

/**==========变更经理信息页面js开始=======================================*/
var managerBgSn = 1;
function addBgManager(){
	managerBgSn = managerBgSn+1;
	var table = document.getElementById("newmanager");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最多一个经理信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteBgManager('");
	if(managerBgSn>10){
		var replacestr = trHtml.substring(findex,findex+21);
	}else{
		var replacestr = trHtml.substring(findex,findex+20);
	}
	trHtml = trHtml.replace(replacestr,"deleteBgManager('"+managerBgSn+"')");
	trHtml = "<tr id=\"newmanager_"+managerBgSn+"\">"+trHtml+"</tr>";
	$("#newmanager").append(trHtml);
}

function deleteBgManager(idSn){
	var table = document.getElementById("newmanager");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个经理信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#newmanager_"+idSn).remove();
}

function getBgManagerJson(){
	var datas = [];
	var table = document.getElementById("newmanager");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#newmanager_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		//过滤一下
		if(null!=trData.MANAGER_NAME&& trData.MANAGER_NAME!=""){
			var bgManager = {};
			bgManager.MANAGER_NAME = trData.MANAGER_NAME;
			bgManager.MANAGER_JOB = trData.MANAGER_JOB;
			bgManager.MANAGER_IDTYPE = trData.MANAGER_IDTYPE;
			bgManager.MANAGER_IDNO = trData.MANAGER_IDNO;
			bgManager.MANAGER_GENERATION_MODE = trData.MANAGER_GENERATION_MODE;
			bgManager.MANAGER_APPOINTOR = trData.MANAGER_APPOINTOR;
			bgManager.MANAGER_OFFICETERM = trData.MANAGER_OFFICETERM;
			bgManager.MANAGER_COUNTRY = trData.MANAGER_COUNTRY;
			bgManager.MANAGER_PHONE = trData.MANAGER_PHONE;
			datas.push(bgManager);
		}
	}
	if(datas.length>0){
		$("input[type='hidden'][name='MANAGER_JSON_CHANGE']").val(JSON.stringify(datas));
	}
}
/**==========变更经理信息页面js结束=======================================*/


//出资全部缴付到位期限验证
function checkPeriod(value){
	var type = $("input:radio[name='OPRRATE_TERM_TYPE']:checked").val();
	if(type==1){
		var byear = $("input[name='BUSSINESS_YEARS']").val();
		var now = new Date;
        now.setFullYear(now.getFullYear () + Number(byear));
        var year=now.getFullYear();
        var month=now.getMonth();
        var day=now.getDate();
        month=month+1;
        month=checkTime(month);
        day=checkTime(day);
        var time = year+"-"+month+"-"+day;
        var res = dateCompare(value,time);
        if(res==1){
        	parent.art.dialog({
    			content: "出资全部缴付到位期限不能超过"+time+"！",
    			icon:"warning",
    			ok: true
    		});
    		$("input[name='PAYMENT_PERIOD']").val('');
        }
	}
}

function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time1 > time2){
		return 1;
	}else if(time1 == time2){
		return 2;
	}else{
		return 3;
	}
} 

function checkTime(i){
    if (i<10){
    	i="0" + i;
    }
    return i;
}

//保存经营范围备注描述
function saveScopeRemark(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){
			var COMPANY_ID = flowSubmitObj.busRecord.COMPANY_ID;
			var SCOPE_REMARK = $("[name='SCOPE_REMARK']").val();
			var url = "domesticControllerController.do?saveScopeRemark";
			$.post(url,{
				SCOPE_REMARK:SCOPE_REMARK,
				COMPANY_ID:COMPANY_ID,
				tableName:'T_COMMERCIAL_COMPANY',
				tableColumn:'COMPANY_ID'
			}, function(responseText, status, xhr) {
				var responseJson = JSON2.parse(responseText);
				if(responseJson.success){
					alert("保存成功！")		
				}else{
					alert("保存失败！");
				}
				
			});
		}else{
			alert("保存失败！");
		}		
	}else{
		alert("保存失败！");
	}	
}

//设置经营范围
var jyfwAdd = "";
function setJyfw(val){	
	var BUSSINESS_SCOPE = $("[name='BUSSINESS_SCOPE']").val();
	if(null!=BUSSINESS_SCOPE&&''!=BUSSINESS_SCOPE && val!=null && val!=''){
		jyfwAdd = jyfwAdd.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace("（依法须经批准的项目，经相关部门批准后方可开展经营活动）","").replace("（"+val+"）", "").replace("("+val+")", "") + "（"+val+"）"+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
	}else{
		BUSSINESS_SCOPE = BUSSINESS_SCOPE.replace(jyfwAdd, "")+"（依法须经批准的项目，经相关部门批准后方可开展经营活动）";
		jyfwAdd="";
	} 
	$("[name='BUSSINESS_SCOPE']").val(BUSSINESS_SCOPE)
}


//法定代表人与财务负责人不能为同一人
function checkDifferent(name){
	var legalNo = $("input[name='LEGAL_IDNO']").val();
	var financeNo = $("input[name='FINANCE_IDNO']").val();
	if(legalNo==financeNo){
		parent.art.dialog({
			content: "法定代表人与财务负责人不能为同一人！",
			icon:"warning",
			ok: true
		});
		$("input[name='"+name+"']").val('');
	}
}

function changelegaljob(val){
	if(val==1){
		$("#legalJob option[value='30']").remove();
		$("#legalJob option[value='01']").remove();
		$("#legalJob").append("<option value='01'>董事长</option>"); 
	}else{
		$("#legalJob option[value='30']").remove();
		$("#legalJob option[value='01']").remove();
		$("#legalJob").append("<option value='30'>执行董事</option>"); 
	}
}

/**
 * 下载附件
 * @param {} fileId
 */
function downLoadFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){			
			window.location.href=__ctxPath+"/domesticControllerController/downLoad.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE;
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
    var layload = layer.load('正在处理，请稍等…');
    setTimeout(function(){
        layer.close(layload);
    },2000);
}
/**
 * 下载附件
 * @param {} fileId
 */
function downLoadWordFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){			
			window.location.href=__ctxPath+"/domesticControllerController/downLoadWord.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE;
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
	}
    var layload = layer.load('正在处理，请稍等…');
    setTimeout(function(){
        layer.close(layload);
    },2000);
}

/**
 * 预览附件
 * @param {} fileId
 */
function previewFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){	
			window.open(__ctxPath+"/domesticControllerController/pdfPreview.do?fileId="+fileId
			+"&exeId="+flowSubmitObj.busRecord.EXE_ID
			+"&itemCode="+flowSubmitObj.busRecord.ITEM_CODE, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
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
 * 预览附件(针对股权转让协议个性化处理)
 * @param {} fileId
 */
function previewGqzrFile(fileId){
	//获取流程信息对象JSON
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		if(flowSubmitObj.busRecord){
			//跳转至多个股东转让界面
			$.dialog.open(encodeURI("domesticControllerController.do?goGqzrView&fileId="+fileId+"&exeId="+flowSubmitObj.busRecord.EXE_ID), {
				title : "股权转让协议",
				width : "80%",
				height : "60%",
				lock : true,
				resize : false
			}, false);
		}else{
			art.dialog({
				content : "无法下载",
				icon : "error",
				ok : true
			});
		}
		
	}
}

//流程提交
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var forms = $("form[id]");
	 var validateResult = true;
	 forms.each(function() {
		var formId = $(this).attr("id");
		if(!$("#"+formId).validationEngine("validate")){
			validateResult = false;
			return false;
		}
	 });
	 //var validateResult =$("#T_COMMERCIAL_DOMESTIC_FORM").validationEngine("validate");
	 if(validateResult){
		 //获取原股东信息
		 if(!getHolderJson()){
			 return null;
		 }
		 //获取股东信息变更后
		 if(!getHolderChangeJson()){
			 return null;
		 }
		 //获取原董事信息
		 getDirectorJson();
		 //获取董事信息变更后
		 getBgDirectorJson();
		 //获取原监事信息
		 getSupervisorJson();
		 //获取监事信息变更后
		 getBgSupervisorJson();
		 //获取原经理信息
		 getManagerJson();
		 //获取经理信息变更后
		 getBgManagerJson();
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 //先获取表单上的所有值
		 var forms = $("form[id]");
		 forms.each(function() {
			var formId = $(this).attr("id");
			var formData = FlowUtil.getFormEleData(formId);
			for ( var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
		 });
		 flowSubmitObj.shzdshIsOk = true;
		 flowSubmitObj.EFLOW_APPLY_STATUS = "2";
		 $(".shzdsh").each(function(i){		
			if(!$(this).is(":checked")){
				flowSubmitObj.shzdshIsOk = $(this).is(":checked");
				flowSubmitObj.EFLOW_APPLY_STATUS = "1";
			}
		 });
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}


//流程暂存
function FLOW_TempSaveFun(flowSubmitObj){
	 // 先判断表单是否验证通过
	 var forms = $("form[id]");
	 var validateResult = true;
	 forms.each(function() {
		var formId = $(this).attr("id");
		if(!$("#"+formId).validationEngine("validate")){
			validateResult = false;
			return false;
		}
	 });
	 //var validateResult=$("#T_COMMERCIAL_DOMESTIC_FORM").validationEngine("validate");
	 if(validateResult){
		 //获取原股东信息
		 if(!getHolderJson()){
			 return null;
		 }
		 //获取股东信息变更后
		 if(!getHolderChangeJson()){
			 return null;
		 }
		 //获取原董事信息
		 getDirectorJson();
		 //获取董事信息变更后
		 getBgDirectorJson();
		 //获取原监事信息
		 getSupervisorJson();
		 //获取监事信息变更后
		 getBgSupervisorJson();
		 //获取原经理信息
		 getManagerJson();
		 //获取经理信息变更后
		 getBgManagerJson();
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 // 先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_COMMERCIAL_CHANGE_DOMESTIC_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 return flowSubmitObj;
		 }else{
		 return null;
	 }
}


//流程退回
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


		
		