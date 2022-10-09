$(function(){
	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj){
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			$('#T_BSFW_GCJSSGXK_FORM').find('input,textarea').prop("readonly", true);
			$('#T_BSFW_GCJSSGXK_FORM').find('select').prop("disabled", "disabled");
			$('#T_BSFW_GCJSSGXK_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#T_BSFW_GCJSSGXK_FORM').find(".laydate-icon").attr('disabled',"disabled");			
			$('#projectCodeA').hide();			
			$('#projectCodeFont').hide();
			
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_GCJSSGXK_FORM");
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			 //初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_GCJSSGXK_FORM");
			
			changeProType(flowSubmitObj.busRecord.PROTYPE);
			if(flowSubmitObj.busRecord.RUN_STATUS!=0){
				$("#userinfo_div input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#userinfo_div select").each(function(index){
					$(this).attr("disabled","disabled");
				});
			}
			if((flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='受理'&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='初审')||flowSubmitObj.busRecord.RUN_STATUS==2){
				$("#constrNumTr").show();
				$("#certNumTr").show();
				$("input[name='CONSTRNUM']").addClass(" validate[required]");
				$("input[name='CONSTRNUM']").prop("readonly", false);
				$("input[name='RELEASEDATE']").addClass(" validate[required]");
				$("#HQZZ").hide();
				$("#SGXKDZ").hide();
				$("#SGXKDZZZ").hide();
			}
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='制证'||flowSubmitObj.busRecord.RUN_STATUS==2){
				$("#HQZZ").show();
				$("#SGXKDZ").show();
				$("#SGXKDZZZ").show();
			}
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='审批'){
				if(!flowSubmitObj.busRecord.CONSTRNUM){					
					loadConstrnum(flowSubmitObj);
				}
			}
			//审批环节生成电子证照施工许可证编号
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="审批") {
				if(!flowSubmitObj.busRecord.CERT_NUM){
					getCertNum(flowSubmitObj);
				}
			}
			if(flowSubmitObj.busRecord.SGXK_SBXMLX=='1'){
				$("#zjgcInfoDiv").hide();
				$("#sbgcInfoDiv").hide();
			}
			if(flowSubmitObj.busRecord.SGXK_SBXMLX=='2'){
				$("#sbgcInfoDiv").hide();
			}
			if(flowSubmitObj.busRecord.SGXK_SBXMLX=='3'){
				$("#zjgcInfoDiv").hide();
			}
		}else{
			setSingleProMaintype('01','')
			//$("[name='PROTYPE']").val('01');
			changeProType('01');
			$("[name='SINGLEPRONUM']").val(1);
			initJlryDiv();
			var code = $("input[name='PROJECT_CODE']").val();
			if(code!=null && code!=''){
				loadTZXMXXData();		
			}
			loadPRJNUM();
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

});
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BSFW_GCJSSGXK_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXK_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 return flowSubmitObj;
		 }else{
			 return null;
		 }
	 }else{
		 return null;
	 }
}

function FLOW_TempSaveFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BSFW_GCJSSGXK_FORM").validationEngine("validate");
	 if(validateResult){
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXK_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 return flowSubmitObj;
		 }else{
			 return null;
		 }
	 }else{
		 return null;
	 }
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}

function FLOW_ViewSumOpinionFun(flowSubmitObj){
	return flowSubmitObj;
}



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
	var PRJFUNCTIONNUM = $("[name='PRJFUNCTIONNUM']").val();
	$("[name='sgxkgclx']").each(function(){
		var targetField = $(this);
		var checkBoxValue = targetField.val();
		var isChecked = AppUtil.isContain(PRJFUNCTIONNUM.split(","),checkBoxValue);
		if(isChecked){
			$(this).attr("checked","checked");
		} else{
			$(this).removeAttr('checked');
		}
	});	
	$("[name='sgxkjglx']").val($("[name='STRUCTQUALTYPES']").val());
	
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
				if (resultJson.result) {
					for(var key in resultJson.datas){						
						$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);		
						if(key.toUpperCase()=='PROJECT_NAME'){
							$("[name='TITLE']").val(resultJson.datas[key]);
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

function setSingleProMaintype(val,i){
	if(val=='01'){
		$("."+i+"singleProMaintype_2").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_2").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_2").find("input,select").val('');
		$("."+i+"singleProMaintype_2").hide();
		$("."+i+"singleProMaintype_1").show();		
		$("."+i+"singleProMaintype_1").find("input,select").attr("disabled",false);
	}else{		
		$("."+i+"singleProMaintype_1").find("input,select").attr("disabled",true);
		$("."+i+"singleProMaintype_1").find("input,select").removeAttr('checked');
		$("."+i+"singleProMaintype_1").find("input,select").val('');
		$("."+i+"singleProMaintype_1").hide();
		$("."+i+"singleProMaintype_2").show();
		$("."+i+"singleProMaintype_2").find("input,select").attr("disabled",false);
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
			SGUNITS = CORPNAME;
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
			JLUNITS += CORPNAME;
			if(CORPCREDITCODE!=null && CORPCREDITCODE!=""){
				JLUNITS +="（"+CORPCREDITCODE+"）";
			}
		} 
	});
	$("[name='JLUNITS']").val(JLUNITS);
}
function addJlryDiv(){
	var sgdwmcArray = new Array(); //定义数组		
	$("#SGRY_FORM").children("div").each(function(){ //遍历全部
		var sgdwmc = $(this).find("[name='sgdwmc']").val(); //获取VALUE
		sgdwmcArray.push(sgdwmc);	
	});
	$("#jldwDiv").children("div").each(function(i){		
		var CORPNAME = $(this).find("[name$='CORPNAME']").val();
		$("#JLRY_FORM").children("div").eq(i).find("[name='jldwmc']").val(CORPNAME);
		$("#JLRY_FORM").children("div").eq(i).children("div").eq(0).children("span").eq(0).html("监理单位名称："+CORPNAME);	
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
		if(val){
			array.push(val);			
		}
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
}

function setDwgcInfo(index){
	var PROAREA = 0;
	var STRUCTUPFLOORNUMS = 0;
	var STRUCTDWFLOORNUMS = 0;
	var STRUCTUPFLOORAREAS = 0;
	var STRUCTDWFLOORAREAS = 0;
	var STRUCTQUALTYPES = "";
	var BASICQUALTYPES = "";
	$("#dwgcDiv").children("div").each(function(i){	
		
		var STRUCTUPFLOORNUM = $(this).find("[name$='STRUCTUPFLOORNUM']").val();//±0.000以上层数
		if(Number(STRUCTUPFLOORNUM)>0){	
			STRUCTUPFLOORNUMS+=Number(STRUCTUPFLOORNUM);
		}
		var STRUCTDWFLOORNUM = $(this).find("[name$='STRUCTDWFLOORNUM']").val();//±0.000以下层数
		if(Number(STRUCTDWFLOORNUM)>0){	
			STRUCTDWFLOORNUMS+=Number(STRUCTDWFLOORNUM);
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
			url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+obj.SINGLEPROSUBTYPE,		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});
		FlowUtil.initFormObjValue(obj,$("#dwgcDiv").children("div").eq(i));
	});
}
/**
 * 删除单位工程信息
 */
function delDwgcDiv(o){
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
				$('#singleprosubtype').combobox({
					url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+info[i].SINGLEPROSUBTYPE,		
					method:'post',
					valueField:'VALUE',
					textField:'TEXT',
					panelHeight:'auto',
					required:true,
					editable:false
				});
				FlowUtil.initFormObjValue(info[i],$("#dwgcDiv").children("div").eq(i));
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
	var url = "projectSgxkController.do?addSgry&type=0&info="+encodeURIComponent(sgryxx);
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
	var url = "projectSgxkController.do?addJlry&type=0&info="+encodeURIComponent(jlryxx);
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
/**
* 查看单位信息
**/
function addDwInfo(formName,id){	
	var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
	var rowxx = JSON.parse(rowJson);
	delete rowxx.SGRY;
	delete rowxx.SGRYLIST;
	delete rowxx.JLRY;
	delete rowxx.JLRYLIST;
	var url = "projectSgxkController.do?addDwInfo&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx));
	art.dialog.open(url, {
		title : "单位信息",
        width:"800px",
        lock: true,
        resize:false,
        height:"400px",
		close: function () {
			var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){
				if(formName!='zbdw'){
					initDw(formName,id,dwInfo);
				} else{					
					initZbdw(formName,id,dwInfo);
				}
			}
			art.dialog.removeData("dwInfo");
		}
	}, false);
}
/**
* 查看单位工程信息
**/
function getDwgcInfo(formName,index){	
	var YW_ID = $("#YW_ID").val();
	var url = "projectSgxkController.do?addDwInfo&type=0&formName="+formName+"&YW_ID="+YW_ID+"&index="+index;
	art.dialog.open(url, {
		title : "单位工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
	}, false);
}

/**
* 查看桩基工程信息
**/
function getZjgcInfo(formName,index){	
	var YW_ID = $("#YW_ID").val();
	var url = "projectSgxkController.do?addZjInfo&type=0&formName="+formName+"&YW_ID="+YW_ID+"&index="+index;
	art.dialog.open(url, {
		title : "桩基工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
	}, false);
}

/**
* 查看上部工程信息
**/
function getSbgcInfo(formName,index){	
	var YW_ID = $("#YW_ID").val();
	var url = "projectSgxkController.do?addSbInfo&type=0&formName="+formName+"&YW_ID="+YW_ID+"&index="+index;
	art.dialog.open(url, {
		title : "上部工程信息",
        width:"1150px",
        lock: true,
        resize:false,
        height:"100%",
	}, false);
}


/**
* 查看单位信息
**/
function getDwInfo(formName,id){	
	var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
	var rowxx = JSON.parse(rowJson);
	delete rowxx.SGRY;
	delete rowxx.SGRYLIST;
	delete rowxx.JLRY;
	delete rowxx.JLRYLIST;
	var height = "400px";
	if(formName=='sgdw'){
		height = "600px";
	} else if(formName=='jldw'){
		height = "610px";
	} else if(formName=='kcdw'){
		height = "450px";
	} else if(formName=='zbdw'){
		height = "550px";
	}
	var url = "projectSgxkController.do?addDwInfo&type=0&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx));
	art.dialog.open(url, {
		title : "单位信息",
        width:"800px",
        lock: true,
        resize:false,
        height:height
	}, false);
}

function initDw(formName,id,info){	
	var html = "";
	html +='<input type="hidden" name="ROW_JSON"/>';
	html +='<td style="text-align: center;">'+info.CORPNAME+'</td>';
	html +='<td style="text-align: center;">'+info.CORPCREDITCODE+'</td>';
	html +='<td style="text-align: center;">'+info.ORGCODE+'</td>';
	html +='<td style="text-align: center;">'+info.LEGAL_NAME+'</td>';
	html +='<td style="text-align: center;">'+info.PERSONNAME+'</td>';
	html +='<td style="text-align: center;">'+info.PERSONPHONE+'</td>';
	html +='<td style="text-align: center;">';
	html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo(\''+formName+'\',\''+id+'\');">编 辑</a>';
	html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';			
	$("#"+id).html(html);	
	$("#"+id).find("[name='ROW_JSON']").val(JSON.stringify(info));
}
function initZbdw(formName,id,info){	
	var html = "";
	html +='<input type="hidden" name="ROW_JSON"/>';
	html +='<td style="text-align: center;">'+info.CORPNAME+'</td>';
	html +='<td style="text-align: center;">'+info.CORPCREDITCODE+'</td>';
	html +='<td style="text-align: center;">'+info.ORGCODE+'</td>';
	html +='<td style="text-align: center;">'+info.LEGAL_NAME+'</td>';
	html +='<td style="text-align: center;">'+info.PERSONNAME+'</td>';
	html +='<td style="text-align: center;">'+info.PERSONPHONE+'</td>';
	html +='<td style="text-align: center;">';
	html +='<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo(\''+formName+'\',\''+id+'\');">编 辑</a>';
	html +='<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a></td>';			
	$("#"+id).html(html);	
	$("#"+id).find("[name='ROW_JSON']").val(JSON.stringify(info));
}

function loadConstrnum(flowSubmitObj){
	var PRJNUM = flowSubmitObj.busRecord.PRJNUM;
	$.post("projectSgxkController/getConstrnum.do",{proNum:PRJNUM},
		function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if (resultJson.status) {
				$("[name='CONSTRNUM']").val(resultJson.constrnum);			
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

function getCertNum(flowSubmitObj){
	var PROTYPE = flowSubmitObj.busRecord.PROTYPE;
	$.post("projectSgxkController/getCertNum.do",{proType:PROTYPE},
			function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.status) {
					$("input[name='CERT_NUM']").val(resultJson.certNum);
				}else {
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				} 
			}
		);
}


function getCertificate() {
	var flowObj = FlowUtil.getFlowObj();
	var EXE_ID = flowObj.EFLOW_EXEID;
	var YW_ID = $("input[name='YW_ID']").val();
	var BUILDCORPCODE = $("input[name='BUILDCORPCODE']").val();
	var layload = layer.load('正在提交校验中…');
	$.post("projectSgxkController/getCertificate.do", {
		YW_ID : YW_ID,
		EXE_ID: EXE_ID,
		BUILDCORPCODE:BUILDCORPCODE
	},
		function(responseText, status, xhr) {
		layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if (resultJson.SIGN_FLAG) {
				$("input[name='CERTIFICATEIDENTIFIERFILENAME']").val(resultJson.CERTIFICATEIDENTIFIERFILENAME);
				$("input[name='CERTIFICATEIDENTIFIERFILEID']").val(resultJson.CERTIFICATEIDENTIFIERFILEID);
				$("input[name='CERTIFICATEIDENTIFIERFILEPATH']").val(resultJson.CERTIFICATEIDENTIFIERFILEPATH);
				art.dialog({
					content: "获取施工许可电子证照信息成功",
					icon:"succeed",
					time:3,
				    ok: true
				});
			} else {
				art.dialog({
					content : resultJson.SIGN_MSG,
					icon : "error",
					ok : true
				});
			}
		}
	);
}


function downloadCertificate(){
	var FILE_ID = $("input[name='CERTIFICATEIDENTIFIERFILEID']").val();
	if(FILE_ID){
		AppUtil.downLoadFile(FILE_ID);
	}else{
		art.dialog({
			content : "获取文件信息错误，下载失败！",
			icon : "error",
			ok : true
		});
	}
}