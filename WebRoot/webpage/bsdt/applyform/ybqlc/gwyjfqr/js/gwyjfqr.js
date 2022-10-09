

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_GWYJFQR_FORM").validationEngine("validate");
	 if(validateResult){	
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if(!validateCheck(flowSubmitObj))//判断受理信息是否正确选择
				 return null;
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYJFQR_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 console.info(JSON.stringify(flowSubmitObj)); 
			return flowSubmitObj;
		 }else{
			 return null;
		 }			 
	 }else{
		 return null;
	 }
}

/**
 * 暂存流程
 */
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	if(!!validateCheck(flowSubmitObj))//判断受理信息是否正确选择
		 return null;	
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYJFQR_FORM");
	 for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	 }	 
	return flowSubmitObj;		 
}


/**
 * 退回流程
 */
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}



/**
 * 判断受理信息选择是否通过
 */
function validateCheck(flowSubmitObj){
	var flag = true;
	var data = "";
	if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
		$("input[name='type']:checked").each(function(i){ 
			 var value = $(this).val();
			 if(value == "1"){
				 data +=value;
				 var rows = $("#zzryGrid").datagrid("getChecked");
				 if(rows.length < 1){
					 alert("至少勾选一条在职人员参保信息！");
					 flag = false;
				 }else{
					$("input[type='hidden'][name='ZZRY_JSON']").val(JSON.stringify(rows));	
				 }
			 }else if(value == '2'){
				 data += value;
				 var rows = $("#txryGrid").datagrid("getChecked");
				 if(rows.length < 1){
					 alert("至少勾选一条退休人员参保信息！");
					 flag = false;
				 }else{
					$("input[type='hidden'][name='TXRY_JSON']").val(JSON.stringify(rows));	
				 }
			 }
		});
		if(data == ""){
			 alert("请至少勾选一种受理方式！");
			 flag  = false;
		}	
	}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查"){
		 var zzdata = $("#zzryGrid").datagrid("getRows");
		 $("input[type='hidden'][name='ZZRY_JSON']").val(JSON.stringify(zzdata));	
		 var txdata = $("#txryGrid").datagrid("getRows");
		 $("input[type='hidden'][name='TXRY_JSON']").val(JSON.stringify(txdata));			 
	}
	
	return flag;
}


/**
 * 类型切换操作(复选框)
 */
function typeClick(){
	$("#type1").attr("style","display:none");
	$("#type2").attr("style","display:none");
	$("input[name='type']:checked").each(function(i){ 
        var value = $(this).val();
        if(value=="1"){
        	$("#type1").attr("style",""); 	
        	$("#zzryGrid").datagrid("resize");
        }else if(value =="2"){
        	$("#type2").attr("style","");
        	$("#txryGrid").datagrid("resize");
        }
    });
}


//批量在职人员-重置操作
function resetZzSearch(){
    $("input[name='ZZ_DWBXH']").val("");
	$("input[name='ZZ_DWMC']").val(""); 
	$("input[name='ZZ_XM']").val(""); 
	$("input[name='ZZ_ZJHM']").val("");
	$("#zz_cbsf option").removeAttr("selected");
	$("#zz_cbzt option").removeAttr("selected");		
}

//批量在职人员-更改操作
function zzChange(){
	var cbsf = $("select[name='ZZ_XCBSF']").find("option:selected").text();//参保身份
	var cbrq = $("input[name='ZZ_CBTIME']").val();//参保日期
	var rows = $("#zzryGrid").datagrid("getChecked");
	if(rows.length > 0){		
		for(var i=0;i<rows.length;i++){
			if(cbsf != null && cbsf != ""){
				rows[i].ZZ_SF = cbsf;
			}
			if(cbrq != null && cbrq != ""){
				rows[i].ZZ_CBRQ = cbrq;
			}
		}
		$('#zzryGrid').datagrid('loadData',rows);
	}else{
		alert("至少选择一条信息进行更改！");
	}
		
}

//批量退休人员-重置操作
function resetTxSearch(){
  $("input[name='TX_DWBXH']").val("");
	$("input[name='TX_DWMC']").val(""); 
	$("input[name='TX_XM']").val(""); 
	$("input[name='TX_ZJHM']").val("");
	$("#tx_cbsf option").removeAttr("selected");
	$("#tx_cbzt option").removeAttr("selected");		
}

//批量退休人员-更改操作
function txChange(){
	var cbsf = $("select[name='TX_XCBSF']").find("option:selected").text();//参保身份
	var cbrq = $("input[name='TX_CBTIME']").val();//参保日期
	var rows = $("#txryGrid").datagrid("getChecked");
	if(rows.length > 0){		
		for(var i=0;i<rows.length;i++){
			if(cbsf != null && cbsf != ""){
				rows[i].TX_SF = cbsf;
			}
			if(cbrq != null && cbrq != ""){
				rows[i].TX_CBRQ = cbrq;
			}
		}
		$('#txryGrid').datagrid('loadData',rows);
	}else{
		alert("至少选择一条信息进行更改！");
	}
		
}