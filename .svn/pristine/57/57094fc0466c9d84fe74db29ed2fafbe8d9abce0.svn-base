
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_GCXM_XMJBXX_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			/*  var isok = verifyCheckBoxValue();
			 if(!isok){
				$("[name='S_ITEM_CODE']").focus();
				art.dialog({
					content :"必须办理事项未选择！",
					icon : "warning",
					ok : true
				});
				return;
			 } */
			getXmdwxxJson();
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_GCXM_XMJBXX_FORM");
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
		 getXmdwxxJson();
		 //先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_GCXM_XMJBXX_FORM");
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
