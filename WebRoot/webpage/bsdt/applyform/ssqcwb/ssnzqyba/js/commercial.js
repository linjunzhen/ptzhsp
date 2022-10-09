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
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
			$('#T_COMMERCIAL_SSNZQYBA_FORM').find('input,textarea').prop("readonly", true);
			$('#T_COMMERCIAL_SSNZQYBA_FORM').find('select').prop("disabled", "disabled");
			$('#T_COMMERCIAL_SSNZQYBA_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#T_COMMERCIAL_SSNZQYBA_FORM').find(".laydate-icon").attr('disabled',"disabled");
			$('#T_COMMERCIAL_SSNZQYBA_FORM').find(":button").attr('style',"display:none");
			
			$("select[name='yjfwcyxdyy']").attr("disabled",false);
			$("#sllx").combobox("disable");
		}
		//初始化表单字段值
		if(flowSubmitObj.busRecord){	
			var basx = flowSubmitObj.busRecord.BASX;
			if(basx){
				$("[name='BASX']").each(function(){
					var checkBoxValue = $(this).val();
					var isChecked = AppUtil.isContain(basx.split(","),checkBoxValue);
					if(isChecked){
						$(this).attr("checked","checked");
					}
				});				
			}
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			initAutoTable(flowSubmitObj);
			
			
			var COMPANY_TYPECODE = flowSubmitObj.busRecord.COMPANY_TYPECODE
			if(COMPANY_TYPECODE){
				initGsslxz(getSlxzType(COMPANY_TYPECODE));
			} else{				
				initGsslxz('gykg');
			}
		}else{		
		}
		
		var notFiledCheck = ['网上预审'];
		if(notFiledCheck.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0
			&&!(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='undefined')
			&&flowSubmitObj.EFLOW_ISQUERYDETAIL=='false'){
			var tabs = $('#T_COMMERCIAL_SSNZQYBA_FORM').find(".tab_width");
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
				$(this).prepend("<input class=\"shzdsh\" type=\"checkbox\" id=\""+fieldid+"\"name=\""+tabtext+"\" value=\""+text+"\" onclick=\"columnCheck(this.id,this.checked,this.value,this.name,'"+belongId+"')\" checked=\"checked\">");
			});
		}
		setFieldAudit(flowSubmitObj.EFLOW_EXEID,flowSubmitObj.EFLOW_CURUSEROPERNODENAME,flowSubmitObj.EFLOW_ISQUERYDETAIL);
		//获取表单字段权限控制信息
		//var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		 //初始化表单字段权限控制
		//FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_COMMERCIAL_DOMESTIC_FORM");
	}
	//----------------主页面结束------------------------
	
	
});

function initGsslxz(typeCode){			
   var url = __ctxPath+'/dictionaryController/loadData.do?defaultEmptyText=公司设立性质&typeCode='+typeCode+'&orderType=asc';
	$('#sllx').combobox({
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
	$("#sllx").combobox("disable");
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
			if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("director")==0){
				num = Number($("[id^='old_director_']").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);
			} else if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("supervisor")==0){
				num = Number($("[id^='old_supervisor_']").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);				
			} else if(BELONG_ID!=null && BELONG_ID!='' && BELONG_ID.indexOf("manager")==0){
				num = Number($("[id^='old_manager_']").length) +Number(BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length))-Number(1);				
			} else if(BELONG_ID!=null && BELONG_ID!=''){
				num = BELONG_ID.substring(BELONG_ID.lastIndexOf("_")+1,BELONG_ID.length)-1;
			} 
			if(isNaN(num)){
			   num = 0;
			}
			$("[name='"+resultJson[i].FIELD_NAME+"']").each(function(index){				
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
function initAutoTable(flowSubmitObj){
	var BASX=flowSubmitObj.busRecord.BASX;
	if(BASX){		
		if(BASX.indexOf("1")>-1){//董事备案
			$("#dsxx").show();
		} else{
			$("#dsxx").hide();		
		}
		if(BASX.indexOf("2")>-1){//监理备案
			$("#jsxx").show();
		} else{
			$("#jsxx").hide();		
		}
		if(BASX.indexOf("3")>-1){//经理备案
			$("#jlxx").show();
		} else{
			$("#jlxx").hide();		
		}
	}
	
	var holderJson = flowSubmitObj.busRecord.HOLDER_JSON;
	if(holderJson){		
		var holders = eval(holderJson);
		for(var i=0;i<holders.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(holders[i],"holder_1");
			}else{
				addHolder();
				FlowUtil.initFormFieldValue(holders[i],"holder_"+(i+1));
			}
		}
	}
	
	var olddirectorJson = flowSubmitObj.busRecord.OLD_DIRECTOR_JSON;
	if(olddirectorJson){
		var olddirectors = eval(olddirectorJson);
		for(var i=0;i<olddirectors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(olddirectors[i],"old_director_1");
			}else{
				addOldDirector();
				FlowUtil.initFormFieldValue(olddirectors[i],"old_director_"+(i+1));
			}
		}
	}
	
	var directorJson = flowSubmitObj.busRecord.DIRECTOR_JSON;
	if(directorJson){
		$("#director").show();
		var directors = eval(directorJson);
		for(var i=0;i<directors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(directors[i],"director_1");
			}else{
				addDirector();
				FlowUtil.initFormFieldValue(directors[i],"director_"+(i+1));
			}
		}
	}
	
	var oldsupervisorJson = flowSubmitObj.busRecord.OLD_SUPERVISOR_JSON;
	if(oldsupervisorJson){
		var oldsupervisors = eval(oldsupervisorJson);
		for(var i=0;i<oldsupervisors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(oldsupervisors[i],"old_supervisor_1");
			}else{
				addOldSupervisor();
				FlowUtil.initFormFieldValue(oldsupervisors[i],"old_supervisor_"+(i+1));
			}
		}
	}
	var supervisorJson = flowSubmitObj.busRecord.SUPERVISOR_JSON;
	if(supervisorJson){
		$("#supervisor").show();
		var supervisors = eval(supervisorJson);
		for(var i=0;i<supervisors.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(supervisors[i],"supervisor_1");
			}else{
				addSupervisor();
				FlowUtil.initFormFieldValue(supervisors[i],"supervisor_"+(i+1));
			}
		}
	}
	
	var oldmanagerJson = flowSubmitObj.busRecord.OLD_MANAGER_JSON;
	if(oldmanagerJson){
		var oldmanagers = eval(oldmanagerJson);
		for(var i=0;i<oldmanagers.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(oldmanagers[i],"old_manager_1");
			}else{
				addOldManager();
				FlowUtil.initFormFieldValue(oldmanagers[i],"old_manager_"+(i+1));
			}
		}
	}
	
	var managerJson = flowSubmitObj.busRecord.MANAGER_JSON;
	if(managerJson){
		var managers = eval(managerJson);
		for(var i=0;i<managers.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(managers[i],"manager_1");
			}else{
				addManager();
				FlowUtil.initFormFieldValue(managers[i],"manager_"+(i+1));
			}
		}
	}
	
}
/**==========页面初始化js开始=======================================*/





/**==========主页面js开始===========================================*/

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
		 //先获取表单上的所有值
		 //var formData = FlowUtil.getFormEleData("T_COMMERCIAL_DOMESTIC_FORM");
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
//		 for(var index in formData){
//			 flowSubmitObj[eval("index")] = formData[index];
//		 }
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

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
		 // 先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_COMMERCIAL_DOMESTIC_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 // console.dir(flowSubmitObj);
		 return flowSubmitObj;
	 }else{
		 return null;
	 }
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}
/**==========主页面js结束===========================================*/



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
	calculation();
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
				content: "股东出资方式至少包含一项！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		datas.push(trData);
	}
	$("input[type='hidden'][name='HOLDER_JSON']").val(JSON.stringify(datas));
	return true;
}
/**==========股东信息页面js结束=======================================*/


/**==========董事信息页面js开始=======================================*/
var directorSn = 1;
function addDirector(){
	directorSn = directorSn+1;
	var table = document.getElementById("director");
	var trContent = table.getElementsByTagName("tr")[1];
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
var old_directorSn = 1;
function addOldDirector(){
	old_directorSn = old_directorSn+1;
	var table = document.getElementById("old_director");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	trHtml = "<tr id=\"old_director_"+old_directorSn+"\">"+trHtml+"</tr>";
	$("#old_director").append(trHtml);
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
		datas.push(trData);
	}
	$("input[type='hidden'][name='DIRECTOR_JSON']").val(JSON.stringify(datas));
}
/** ==========董事信息页面js结束======================================= */


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
var old_supervisorSn = 1;
function addOldSupervisor(){
	old_supervisorSn = old_supervisorSn+1;
	var table = document.getElementById("old_supervisor");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	trHtml = "<tr id=\"old_supervisor_"+old_supervisorSn+"\">"+trHtml+"</tr>";
	$("#old_supervisor").append(trHtml);
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
		datas.push(trData);
	}
	$("input[type='hidden'][name='SUPERVISOR_JSON']").val(JSON.stringify(datas));
}
/**==========监事信息页面js结束=======================================*/


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
var old_managerSn = 1;
function addOldManager(){
	old_managerSn = old_managerSn+1;
	var table = document.getElementById("old_manager");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	trHtml = "<tr id=\"old_manager_"+old_managerSn+"\">"+trHtml+"</tr>";
	$("#old_manager").append(trHtml);
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
		datas.push(trData);
	}
	$("input[type='hidden'][name='MANAGER_JSON']").val(JSON.stringify(datas));
}
/**==========经理信息页面js结束=======================================*/


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