

//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	 var exeId = flowSubmitObj.EFLOW_EXEID;
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_QSLYSHBSY_FORM").validationEngine("validate");
	 if(validateResult){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);			
			 //并联审查
			 var checkNodeName = ['公安审核','自然资源审核','农业农村局审核'];
			 if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
				 flowSubmitObj.EFLOW_JOINPRENODENAMES="公安审核,自然资源审核,农业农村局审核";
			 }
			 //判断当前环节是否已完成签章
			/* var checkSignNodeName = ['公安审核','自然资源审核','农业农村局审核','受理'];
			 var curNodeName=flowSubmitObj.EFLOW_CURUSEROPERNODENAME;
			 if(checkSignNodeName.indexOf(curNodeName)>=0){
				 if(!isFinishSign(exeId,curNodeName)){
					 parent.art.dialog({
						 content : "未进行签章，请先进行签章操作！",
						 icon : "warning",
						 ok : true
					 });
				 	return ; 
				 } 				 
			 }*/
			//先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDC_QSLYSHBSY_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 //console.dir(flowSubmitObj);	
			return flowSubmitObj;
		 }else{
			 return null;
		 }		 
	 }else{
		 return null;
	 }	 
}



/**
 * 当前环节是否已签署完成
 */
function isFinishSign(exeId,curNodeName){
	 var flag = true;
	 $.ajax({
         type: "POST",
	     url: "bdcQslyshbsyController/isFinishSign.do?exeId="+exeId+"&curNodeName="+encodeURI(curNodeName),
	     async: false, //采用同步方式进行数据判断
	     success: function (responseText) {
	    	var  resultJson=$.parseJSON(responseText);	    
			if(!resultJson.success){			
				flag = false;//未签署	
			}
	     }
	 });
	 alert(curNodeName+"-环节签章状态为："+flag);
	 return flag;
}

/**
 * 点击签章按钮事件---进行验证
 */
function ktSignRemoteFileView(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeId = flowSubmitObj.EFLOW_EXEID;
	$.post("bdcQslyshbsyController/isPermitSign.do",{exeId:exeId},
		function(responseText,status,xhr){
			var  resultJson=$.parseJSON(responseText);
			if(resultJson.success){//未占用，可签署
				ktSignRemoteFileView1(exeId);
			}else{//已占用，需等待
				parent.art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
	});
}

/**
 * 点击签章按钮事件---进行跳转页面
 */
function ktSignRemoteFileView1(exeId){
	//签署文件类型
	var signFileType=$("input[name='SIGN_FILE_TYPE']").val();
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
	art.dialog.open('bdcQslyshbsyController/ktStampUploadView.do?busTableName='+EFLOW_BUSTABLENAME + "&uploadUserId="+
		uploadUserId + "&uploadUserName="+encodeURI(uploadUserName) + "&exeId="+exeId+"&signFileType="+signFileType,{
		title : "盖章",
		width : "1100px",
		height : "800px",
		lock : true,
		resize : false,
		close:function () {	
			var flowSubmitObj = FlowUtil.getFlowObj();
			var exeId = flowSubmitObj.EFLOW_EXEID;
			var curNodeName = flowSubmitObj.EFLOW_CURUSEROPERNODENAME;
			var resultJsonInfo = art.dialog.data("resultJsonInfo");			
			if (resultJsonInfo!='undefined' && resultJsonInfo!='' && resultJsonInfo!=null) {//签署成功
				//回填签署文件id、文件类型
				var fileId = resultJsonInfo.data.fileId;
				var fileType=resultJsonInfo.data.fileType;		
				$("input[name='SIGN_FILE_TYPE']").val(fileType);	
				$("#downloadBtn").attr("onclick","AppUtil.downLoadFile('"+fileId+"')");
				//更新签署文件
				$.ajax({
			         type: "POST",
				     url: "bdcQslyshbsyController/saveOrUpdateSignFile.do?exeId="+exeId+"&fileId="+fileId+"&fileType="+fileType,
				     async: false, //采用同步方式进行数据判断
				     success: function (responseText) {
				    	var  resultJson=$.parseJSON(responseText);
						if(resultJson.success){
							parent.art.dialog({
								content: "签章完成",
								icon:"succeed",
								ok: true
							});
						} 
				     }
				 });				
				art.dialog.removeData("resultJsonInfo");
			}
			//更改是否允许签章的状态（释放资源）
			$.post("bdcQslyshbsyController/changeSignStatus.do",{exeId:exeId},
				function(responseText,status,xhr){
			});				
		}
	})
}

//暂存流程
function FLOW_TempSaveFun(flowSubmitObj){
	//先判断表单是否验证通过
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_QSLYSHBSY_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//校验宗地编号是否合乎规则
function validate(ZDBH){
	var flag = true;
	//alert(ZDBH);
	if(ZDBH.length==19){
		var numberOne = ZDBH.substring("0","6");
		//alert("1:"+numberOne);
		if(numberOne!="351001")
			flag = false;
		var numberNext = ZDBH.substring("6","12");
		//alert("2:"+numberNext);
		if(isNaN(numberNext))
			flag = false;
		var charOne = ZDBH.substring("12","14");
		//alert("3:"+charOne);
		if(!(/^[a-zA-Z]+$/.test(charOne))){
			flag = false;
		}
		var numberLast = ZDBH.substring("14","19");
		//alert("4:"+numberLast);
		if(isNaN(numberLast))
			flag = false;	
		//alert(flag);
		if(!flag){
			parent.art.dialog({
				content: "宗地编号格式校验不通过，请确认!",
				icon:"warning",
				ok: true
			});
		}
	}else{
		parent.art.dialog({
			content: "宗地编号长度需为19位，请确认!",
			icon:"warning",
			ok: true
		});
	}
}