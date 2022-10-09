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
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#USERINFO_FORM"));
			//初始化基本信息						
			FlowUtil.initFormObjValue(eflowObj.busRecord,$("#INFO_FORM"));
			setInitJsdw(eflowObj.busRecord.JSDW_JSON,'JsdwDiv');
			setInitAfterJsdw(eflowObj.busRecord.JSDW_JSON_AFTER,'bghJsdwDiv');
			
			setInitSgdw(eflowObj.busRecord.SGDW_JSON,'sgdwDiv');
			setInitAfterSgdw(eflowObj.busRecord.SGDW_JSON_AFTER,'bghSgdwDiv');
			
			setInitJldw(eflowObj.busRecord.JLDW_JSON,'jldwDiv');
			setInitAfterJldw(eflowObj.busRecord.JLDW_JSON_AFTER,'bghJLdwDiv');
			
			
			//初始化字段审核意见
			if(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'){	
				setFieldAudit(eflowObj.EFLOW_EXEID);	
			}else{
				
			}
			if(eflowObj.busRecord.RUN_STATUS==2){
				
			}
		}else{
		}
	}	
});

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
	if($(".zzhyZdshyj").length>0){
		art.dialog({
			content : "存在字段审核意见，请确认意见都阅读后再提交！",
			icon : "warning",
			ok : true
		});
		return;
	}
	if($("#bghJsdwDiv").children("div").length<=0 && $("#bghSgdwDiv").children("div").length<=0&& $("#bghJLdwDiv").children("div").length<=0){
		art.dialog({
			content : "责任主体信息至少要存在一条变更后的信息",
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

		//获取建设单位信息
		flowSubmitObj.JSDW_JSON_AFTER=getJsdwJson();
		//获取施工单位信息
		flowSubmitObj.SGDW_JSON_AFTER=getSgdwJson();
		//获取监理单位信息
		flowSubmitObj.JLDW_JSON_AFTER=getJldwJson();
		
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
	//获取建设单位信息
	flowSubmitObj.JSDW_JSON_AFTER=getJsdwJson();
	//获取施工单位信息
	flowSubmitObj.SGDW_JSON_AFTER=getSgdwJson();
	//获取监理单位信息
	flowSubmitObj.JLDW_JSON_AFTER=getJldwJson();
	
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
	});
} 
/**
 * 删除Tr
 */
function delClosestTr(o){
	art.dialog.confirm("您确定要删除该记录吗?", function() {		
		$(o).closest("tr").remove();
	});
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
			}
		}
	}, false);
};
function showSelectEnterpriseName(id,name){	
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
			}
		}
	}, false);
};

function loadData(){
	var constrNum = $("input[name='CONSTRNUM']").val();
	if(null==constrNum || ''==constrNum){		
		art.dialog({
			content: "请填写施工许可证编号",
			icon:"error",
			ok: true
		});
		return;
	}
	var CHANGEITEM = $("[name='CHANGEITEM']:checked").val();
	$.post("projectSgxkbgController/getSgxkxx.do",{constrNum:constrNum},
		function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if (resultJson.status) {			
				for(var key in resultJson.data){						
					$("[name='"+key.toUpperCase()+"']").val(resultJson.data[key]);		
					if(key=='JSDW_JSON'){
						setInitJsdw(resultJson.data[key],'JsdwDiv');
					}else if(key=='SGDW_JSON'){
						setInitSgdw(resultJson.data[key],'sgdwDiv');
					}else if(key=='JLDW_JSON'){
						setInitJldw(resultJson.data[key],'jldwDiv');
					}
				}
				art.dialog({
					content : "校验成功",
					icon : "succeed",
					lock : true,
					ok : true
				});
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


/*************建设单位信息JS开始****************/
/**
 * 添加建设单位信息
 */
function addJsdwDiv(){
	$.post("projectSgxkfqzblController/refreshJsdwDiv.do",{
		isDel:1,
		divId:'bghJsdwDiv'
	}, function(responseText, status, xhr) {
		$("#bghJsdwDiv").append(responseText);
	});
}
/**
 * 初始化时添加建设单位信息
 */
function addInitJsdwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshJsdwDiv.do",{
		info:JSON.stringify(info),
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id).find('input,textarea').prop("readonly", true);
		$("#"+id).find('select').prop("disabled", "disabled");
		$("#"+id).find(":radio,:checkbox").attr('disabled',"disabled");
		$("#"+id).find('input,textarea').addClass("inputBackgroundCcc");
		$("#"+id).find('select').addClass("inputBackgroundCcc");
	});
}
//初始化建设单位信息
function setInitJsdw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitJsdwDiv(datas[i],i,id);
		}
	}
}
/**
 * 初始化时添加建设单位信息
 */
function addInitAfterJsdwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshJsdwDiv.do",{
		info:JSON.stringify(info),
		isDel:1,
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id+" [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}
//初始化建设单位信息
function setInitAfterJsdw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitAfterJsdwDiv(datas[i],i,id);
		}
	}
}
/**
/**
 * 删除建设单位信息
 */
function delJsdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取建设单位信息
 */
function getJsdwJson(){
	var infoArray = [];
		$("#bghJsdwDiv").children("div").each(function(i){
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


/*************建设单位信息JS结束****************/




/*************施工单位信息JS开始****************/

/**
 * 添加施工单位信息
 */
function addSgdwDiv(){
	$.post("projectSgxkfqzblController/refreshSgdwDiv.do",{
		isDel:1,
		divId:'bghSgdwDiv'
	}, function(responseText, status, xhr) {
		$("#bghSgdwDiv").append(responseText);
		$("#bghSgdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}
/**
 * 初始化时添加施工单位信息
 */
function addInitSgdwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshSgdwDiv.do",{
		info:JSON.stringify(info),
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id).find('input,textarea').prop("readonly", true);
		$("#"+id).find('select').prop("disabled", "disabled");
		$("#"+id).find(":radio,:checkbox").attr('disabled',"disabled");
		$("#"+id).find('input,textarea').addClass("inputBackgroundCcc");
		$("#"+id).find('select').addClass("inputBackgroundCcc");
	});
}
//初始化施工单位信息
function setInitSgdw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitSgdwDiv(datas[i],i,id);
		}
	}
}
/**
 * 初始化时添加施工单位信息
 */
function addInitAfterSgdwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshSgdwDiv.do",{
		info:JSON.stringify(info),
		isDel:1,
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id+" [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}
//初始化施工单位信息
function setInitAfterSgdw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitAfterSgdwDiv(datas[i],i,id);
		}
	}
}
/**
/**
 * 删除施工单位信息
 */
function delSgdwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取施工单位信息
 */
function getSgdwJson(){
	var infoArray = [];
	$("#bghSgdwDiv").children("div").each(function(i){
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
						
			
			infoArray.push(info);	
		}
		
	});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}
/*************施工单位信息JS结束****************/


/*************监理单位信息JS开始****************/

/**
 * 添加监理单位信息
 */
function addJldwDiv(){
	$.post("projectSgxkfqzblController/refreshJldwDiv.do",{
		isDel:1,
		divId:'bghJLdwDiv'
	}, function(responseText, status, xhr) {
		$("#bghJLdwDiv").append(responseText);
		$("#bghJLdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}
/**
 * 初始化时添加监理单位信息
 */
function addInitJldwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshJldwDiv.do",{
		info:JSON.stringify(info),
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id).find('input,textarea').prop("readonly", true);
		$("#"+id).find('select').prop("disabled", "disabled");
		$("#"+id).find(":radio,:checkbox").attr('disabled',"disabled");
		$("#"+id).find('input,textarea').addClass("inputBackgroundCcc");
		$("#"+id).find('select').addClass("inputBackgroundCcc");
	});
}
//初始化监理单位信息
function setInitJldw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitJldwDiv(datas[i],i,id);
		}
	}
}
/**
 * 初始化时添加监理单位信息
 */
function addInitAfterJldwDiv(info,i,id){
	$.post("projectSgxkfqzblController/refreshJldwDiv.do",{
		info:JSON.stringify(info),
		isDel:1,
		divId:id
	}, function(responseText, status, xhr) {
		$("#"+id).append(responseText);	
		$("#"+id+" [name$='IDCARDTYPENUM']").attr("disabled",true);
	});
}
//初始化监理单位信息
function setInitAfterJldw(json,id){
	var datas = eval(json);
	if(datas){				
		$("#"+id).html('');	
		for(var i=0;i<datas.length;i++){	
			addInitAfterJldwDiv(datas[i],i,id);
		}
	}
}
/**
/**
 * 删除监理单位信息
 */
function delJldwDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取监理单位信息
 */
function getJldwJson(){
	var infoArray = [];
		$("#bghJLdwDiv").children("div").each(function(i){
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
				
				infoArray.push(info);	
			}
			
		});	
	if(infoArray.length>0){
		return JSON.stringify(infoArray);
	}else{
		return "";
	}
}
/*************监理单位信息JS结束****************/