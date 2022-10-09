
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BSFW_GCJSDLSQ_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSDLSQ_FORM");
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
	 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
	 if(submitMaterFileJson||submitMaterFileJson==""){
		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		 //先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSDLSQ_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }
		 return flowSubmitObj;
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


//清除前后空格
function trim(str){ 
  return str.replace(/(^\s*)|(\s*$)/g,""); 
}

$().ready(function() {
	$("input[name='LEREP_INFO']").val('${projectApply.lerep_info}');	
	//清除前后空格
	$("input,textarea").on('blur', function(event) { 
		$(this).val(trim($(this).val()));
	});
	isShowWztzxx();
});

function loadTzxmxxData(){
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
						//console.log(key + " : "+ resultJson.datas[key]);
						$("[name='"+key.toUpperCase()+"']").prop("readonly", true);
						$("[name='"+key.toUpperCase()+"']").prop("disabled", "disabled");	
						if(key=='industry'){
							var typeCode = resultJson.datas[key];
							$.post( "dicTypeController/info.do",{
								typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
								function(responseText1, status, xhr) {									
									var resultJson1 = $.parseJSON(responseText1);
									if(null!=resultJson1){	
										$("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
									}
								});
						}else if(key=='place_code'){
							var typeCode2=resultJson.datas[key];
							$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
								 function(responseText2,status,xhr){
									var  resultJson2=$.parseJSON(responseText2);
									if(null!=resultJson2){
										$("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
									}
							});
						}else if(key=='lerep_info'){
							var lerepInfoList = resultJson.datas[key];		
							if(lerepInfoList.length>0){	
								initFormObjValue(lerepInfoList[0],$("#xmdwxxTable"));
							}
							
						}else if(key=='contribution_info'){
							$("[name='CONTRIBUTION_INFO']").val(JSON.stringify(resultJson.datas[key]));
						}else if(key=='get_land_mode'){
							if(null != resultJson.datas[key] && '' != resultJson.datas[key]){									
								$("[name='GET_LAND_MODE']").val(resultJson.datas[key]);
							} else{									
								$("[name='"+key.toUpperCase()+"']").prop("readonly", false);
								$("[name='"+key.toUpperCase()+"']").prop("disabled", "");	
							}
						}else{							
							$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);
						}
						//项目地址，总用地面积（平方米），总建筑面积（平方米） 存在且为空时可以修改
						if(key=='land_area' ||key=='built_area'||key=='project_site'){	
							if(null == resultJson.datas[key] || '' == resultJson.datas[key]){		
								$("[name='"+key.toUpperCase()+"']").prop("readonly", false);
								$("[name='"+key.toUpperCase()+"']").prop("disabled", "");	
							}
						}
					}			
					$("[name='STZXM_PROJECT_CODE']").val(code);		
				} else {
					art.dialog({
						content: "校验失败",
						icon:"error",
						ok: true
					});
				}
				isShowWztzxx();
				$("[name='PROJECT_CODE']").prop("disabled", "");	
			}
		);
	}
};
function isShowWztzxx(){		
	if($("#totalMoney").val()==0){
		$("#totalMoneyExplain").removeClass("");
		$("#totalMoneyExplain").toggleClass('validate[required],maxSize[512]');
	}else{ 			
		$("#totalMoneyExplain").removeClass("validate[required],maxSize[512]");
		$("#totalMoneyExplain").toggleClass('validate[],maxSize[512]');
		$("#totalMoneyExplain").toggleClass('');
	}
}
/**
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementObj
 */
function initFormObjValue(fieldValueObj,elementObj){
	for(var fieldName in fieldValueObj){
		//获取目标控件对象
		var targetFields = elementObj.find("[name$='"+fieldName.toUpperCase()+"']");
		targetFields.each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = fieldValueObj[fieldName];
			
			if(type=="radio"){
				var radioValue = targetField.val();
				if(radioValue==fieldValue){
					$(this).attr("checked","checked");
				}
			}else if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}else if(tagName=="SELECT"){
				targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
				.attr("selected", "selected");
			}else{
				targetField.val(fieldValueObj[fieldName]);
			}		
			if(fieldName!='dwlx'){			
				targetField.prop("readonly", true);
				targetField.prop("disabled", "disabled");
			}	
		});	
	}
}

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="SF"){
		$("[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("[name='"+name+"']").removeClass(",custom[vidcard]");
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
