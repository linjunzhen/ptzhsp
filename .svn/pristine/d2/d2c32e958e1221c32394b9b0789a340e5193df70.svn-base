
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BSFW_GCJSSGXKFQZBL_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXKFQZBL_FORM");
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
		 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXKFQZBL_FORM");
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
	var url = "projectSgxkController.do?addDwInfo&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx));
	art.dialog.open(url, {
		title : "单位信息",
        width:"800px",
        lock: true,
        resize:false,
        height:height,
		close: function () {
			var dwInfo = art.dialog.data("dwInfo");
			if(dwInfo){
				if(formName!='zbdw'){
					initDw(formName,id,dwInfo);
				} else{					
					initZbdw(formName,id,dwInfo);
				}
				getdAfterDwJson();
			}
			art.dialog.removeData("dwInfo");
		}
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
	html +='</td>';			
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
	html +='</td>';			
	$("#"+id).html(html);	
	$("#"+id).find("[name='ROW_JSON']").val(JSON.stringify(info));
}

function getdAfterDwJson(){
	var jsdwDatas = [];
	$("#jsdwAfterTable").find("input[name='ROW_JSON']").each(function(i){	
		var inputValue = $(this).val();
		var trData = {};
		if(""!=inputValue && null!= inputValue && undefined!=inputValue){		
			trData = JSON.parse(inputValue);
		}
		jsdwDatas.push(trData);
	});
	$("input[type='hidden'][name='JSDW_JSON_AFTER']").val(JSON.stringify(jsdwDatas));
	
	var sgdwDatas = [];
	$("#sgdwAfterTable").find("input[name='ROW_JSON']").each(function(i){	
		var inputValue = $(this).val();
		var trData = {};
		if(""!=inputValue && null!= inputValue && undefined!=inputValue){		
			trData = JSON.parse(inputValue);
		}
		sgdwDatas.push(trData);
	});
	$("input[type='hidden'][name='SGDW_JSON_AFTER']").val(JSON.stringify(sgdwDatas));
	
	
	var jldwDatas = [];
	$("#jldwAfterTable").find("input[name='ROW_JSON']").each(function(i){	
		var inputValue = $(this).val();
		var trData = {};
		if(""!=inputValue && null!= inputValue && undefined!=inputValue){		
			trData = JSON.parse(inputValue);
		}
		jldwDatas.push(trData);
	});
	$("input[type='hidden'][name='JLDW_JSON_AFTER']").val(JSON.stringify(jldwDatas));
}