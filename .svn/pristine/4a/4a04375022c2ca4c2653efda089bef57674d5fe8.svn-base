
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}
//设置用途说明
function setYtms(value){
	$("input[name='YTMS']").val(value);
}
//房地产合同备案查询
function showSelectFdchtbacx(){
	$.dialog.open("bdcYgspfygdjController.do?fdchtbaxxcxSelector&allowCount=1", {
		title : "房地产合同备案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var fdchtbaxxInfo = art.dialog.data("fdchtbaxxInfo");
			if(fdchtbaxxInfo&&fdchtbaxxInfo.length==1){
				for(var i = 0;i<fdchtbaxxInfo.length;i++){
					$('input[name="ZL"]').val(fdchtbaxxInfo[i].ZL);//坐落
					$('input[name="BDCDYH"]').val(fdchtbaxxInfo[i].BDCDYH);//不动产单元号
					$('input[name="FWDZ"]').val(fdchtbaxxInfo[i].ZL);//房屋地址
					$('input[name="APPLICANT_UNIT"]').val(fdchtbaxxInfo[i].MSFXM);//申请人
					$('input[name="YTMS"]').val(fdchtbaxxInfo[i].YTSM);//用途说明
					FlowUtil.initFormFieldValue(fdchtbaxxInfo[i],"ygygInfo");//预购预告信息回填
					//申报对象信息回填
					$('input[name="SQRMC"]').val(fdchtbaxxInfo[i].MSFXM);//申请人
					$('#SQRZJLX').find("option").filter(function(index){
						return fdchtbaxxInfo[i].MSFZJLB===$(this).text();
					}).attr("selected","selected");//证件类别
					$('input[name="SQRSFZ"]').val(fdchtbaxxInfo[i].MSFZJHM);//证件号码
					
					
				}
			}	
			art.dialog.removeData("fdchtbaxxInfo");
		}	
	}, false);
}

//流程表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_YGSPFYGDJ_FORM").validationEngine("validate");
	 if(validateResult){
      var isAuditPass = $('input[name="isAuditPass"]:checked').val();
	     if(isAuditPass == "-1"){
	     	 parent.art.dialog({
				content : "检查上传的审批表扫描件不合规，请先退回补件。",
				icon : "warning",
				ok : true
			 });
			 return null;
	     }else{
	     	 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BDC_YGSPFYGDJ_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				// console.dir(flowSubmitObj);	
				return flowSubmitObj;
			 }else{
				 return null;
			 }
	     }			 
	 }else{
		 return null;
	 }
}

//流程暂存方法
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_YGSPFYGDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

//流程退回方法
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}