function cbrQuery(){
	$.dialog.open("YbCbryxxbgController.do?cbrQueryForSelect", {
        title : "参保人员信息查询",
        width: "100%",
        height: "100%",
        fixed: true,
        lock : true,
        resize : false
    }, false);
}

/*function FLOW_SubmitFun(flowSubmitObj) {
	 //先判断表单是否验证通过
	var validateResult = $("#T_YBQLC_CBRYXXBG_FORM").validationEngine("validate");
	if (validateResult) {
		setGrBsjbr(); //个人不显示经办人设置经办人的值
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
		if (submitMaterFileJson || submitMaterFileJson == "") {
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			//先获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_YBQLC_CBRYXXBG_FORM");
			for (var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}	
			return flowSubmitObj;
		} else {
			return null;
		}
	}
	return null;
}*/


/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult = $("#T_YBQLC_CBRYXXBG_FORM").validationEngine("validate");
	 if(validateResult){
		 if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME !="开始"){
			 var inithfvalue = $("input[name='CXJM_TYPE']:checked").val();
			 var validateResult1 =$("#page_"+inithfvalue).validationEngine("validate");
			 if(!validateResult1){
				 return null;
			 }
		 }
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_CBRYXXBG_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			/* var pageMata = FlowUtil.getFormEleData("page_"+cxjmValue);
			 for(var index in pageMata){
				 flowSubmitObj[eval("index")] = pageMata[index];
			 }*/
			 console.info(JSON.stringify(flowSubmitObj)); 
			return flowSubmitObj;
		 }else{
			 return null;
		 }			 
	 }else{
		 return null;
	 }
}
