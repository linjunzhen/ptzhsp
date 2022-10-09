
function FLOW_SubmitFun(flowSubmitObj){
	var isok = true;
	$("#bg_table").find("[name$='_AFTER']").each(function(i){
		var val = $(this).val();
		if(null!=val && ''!=val){
			isok = false;
		}
	});
	if(isok){
		art.dialog({
			content: "必须填写一项变更内容",
			icon:"error",
			ok: true
		});
		return;
	}
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BSFW_GCJSSGXKBG_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXKBG_FORM");
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
		 var formData = FlowUtil.getFormEleData("T_BSFW_GCJSSGXKBG_FORM");
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
var changeItemVal;
function changeChangeItem(val){
	if(!changeItemVal){
		changeItemVal = val;
	}else if(changeItemVal!=val){
		changeItemVal = val;
	}else{
		return;
	}
	if(val=='3'){		
		$(".qt_tr").show();	
		$(".xmjlorxmzj_tr").hide();
		$(".xmjlorxmzj_tr").find("[name$='_AFTER']").val('');
		$(".xmjlorxmzj_tr").find("[name$='_AFTER']").removeClass(" validate[required]");
		$(".xmjlorxmzj_tr").find("[name$='_AFTER']").removeClass(" ,custom[vidcard]");
	} else{			
		$(".xmjlorxmzj_tr").show();	
		$('#IDCARDTYPENUM').attr("disabled", "disabled");
		$(".qt_tr").hide();
		$(".qt_tr").find("[name$='_AFTER']").val('');
		$(".xmjlorxmzj_tr").find("[name$='_AFTER']").addClass(" validate[required]");
		$(".xmjlorxmzj_tr").find("[name='PERSONIDCARD_AFTER']").addClass(" ,custom[vidcard]");
		$(".xmjlorxmzj_tr [name='IDCARDTYPENUM_AFTER']").val(1);
		$(".xmjlorxmzj_tr [name='IDCARDTYPENUM_AFTER']").attr("disabled",true);
		initData(val);
	}
}

function changePersonName(){
	var indexNum =$("#PERSONNAME").find('option:selected').attr('indexNum');	
	initPerson(indexNum);
	$(".xmjlorxmzj_tr").find("[name$='_AFTER']").val('');
}
function initPerson(indexNum){
	var list;
	var CHANGEITEM = $("[name='CHANGEITEM']:checked").val();
	if(CHANGEITEM==1){
		var json = $("[name='SGDW_JSON']").val();
		if(json){				
			list = $.parseJSON(json);	
		}
	}else if(CHANGEITEM==2){			
		var json = $("[name='JLDW_JSON']").val();
		if(json){				
			list = $.parseJSON(json);	
		}
	}	
	if(list.length>=Number(indexNum)+1){									
		$("[name='IDCARDTYPENUM']").val(list[indexNum].IDCARDTYPENUM);
		setSgxkZjValidate(list[indexNum].IDCARDTYPENUM,'PERSONIDCARD');
		$("[name='PERSONIDCARD']").val(list[indexNum].PERSONIDCARD);
		$("[name='PERSONPHONE']").val(list[indexNum].PERSONPHONE);
		$("[name='INDEXNUM']").val(indexNum);		
	}
}

function initData(val){	
	var list;
	if(val==1){
		var json = $("[name='SGDW_JSON']").val();
		if(json){				
			list = $.parseJSON(json);	
		}
	}else if(val==2){			
		var json = $("[name='JLDW_JSON']").val();
		if(json){				
			list = $.parseJSON(json);	
		}
	}
	$("#PERSONNAME").find("option").remove();
	if(list){		
		for(var i=0;i<list.length;i++){	
			$("#PERSONNAME").append("<option indexNum='"+i+"' value='"+list[i].PERSONNAME+"'>"+list[i].PERSONNAME+"</option>");	
		}		
		if(list.length>0){									
			$("[name='IDCARDTYPENUM']").val(list[0].IDCARDTYPENUM);
			setSgxkZjValidate(list[0].IDCARDTYPENUM,'PERSONIDCARD');
			$("[name='PERSONIDCARD']").val(list[0].PERSONIDCARD);
			$("[name='PERSONPHONE']").val(list[0].PERSONPHONE);
			$("[name='INDEXNUM']").val(0);		
		}
	}
}
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
				}
				initData(CHANGEITEM);
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
