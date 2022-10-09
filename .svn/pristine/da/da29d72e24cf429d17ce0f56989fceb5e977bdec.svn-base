
//初始化表单字段值
function initForm(){
	//初始化表单字段值
	$("#qyzgbgdj").find("select").attr("disabled","disabled");		
	$("input[name='JBXX_KSRQ']").attr("disabled","disabled");	
	$("input[name='JBXX_JZRQ']").attr("disabled","disabled");
	$("input[name='GJXX_PZCLRQ']").attr("disabled","disabled");
	$("input[name='GJXX_CLRQ']").attr("disabled","disabled");	
}

//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	//获取推送标志位
	var pushFlag = $("input[name='PUSH_FLAG']").val();
	//先判断表单是否验证通过
	 var validateResult =$("#T_SBQLC_QYZGBGDJ_FORM").validationEngine("validate");
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				 getXzxxJson(flowSubmitObj);//获取已参保险种信息
			 }
			 //办结环节推送数据
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "办结" && pushFlag!='1'){
				 if(!pushQyZgBgDj(flowSubmitObj)){//推送数据至平潭社保系统
					 return null;
				 }else{
					 alert("业务数据申报至社保系统成功，请完成流程办结！");
				 }		 
			 }
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGBGDJ_FORM");
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


//暂存流程
function FLOW_TempSaveFun(flowSubmitObj) {
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	var rows = $("#xzxxGrid").datagrid("getRows");
	//alert("险种信息："+JSON.stringify(rows));
	if(rows.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(rows));
	}
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGBGDJ_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}


//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//业务数据推送平潭医保系统
function pushQyZgBgDj(flowSubmitObj){
	 var result = false;
	 var ywId = flowSubmitObj.busRecord.YW_ID;
	//判断单位是否正常在参
	 var dwzt = $('input[name="JBXX_DWZT"]').val();//单位状态
	 var rows = $("#xzxxGrid").datagrid("getRows");
	 var cbzt = rows[0].aab051;//单位参保状态
	 var jfbz = rows[0].aab066;//单位暂停缴费标志
	 //alert("单位状态："+dwzt+";参保状态："+cbzt+";暂停标志："+jfbz);
	 if(dwzt=="1" && cbzt=="1" && jfbz=="0"){
		 alert("业务数据一经推送社保系统，不可回退！");
		 var layload = layer.load("正在提交请求中…");
		 $.ajax({
			  type: "POST",
		      url: "sbQyzgbgdjController.do?pushZgBgDj&ywId="+ywId,
		      async: true, //采用同步方式进行数据判断
		      success: function (responseText) {
		    	layer.close(layload);
		        var data=$.parseJSON(responseText);
		      	if(data.success){			        
			        $('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)			   
			        result = true;
		      	}else if(!data.success){
		      		art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
					    ok: true
					});
		      		result = false;
		      	}	  	
		      }
		 });
	 }else{
		 alert("该单位为非正常在参状态，不可受理！"); 
		 result = false;
	 }
	 return result;
}


//获取已参保险种信息
function getXzxxJson(flowSubmitObj) {
	var rows = $("#xzxxGrid").datagrid("getRows");	
	//alert("已参加险种信息："+rows.length);
	if(rows.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(rows));
	}
}


//单位基本信息查询（弹框）
function dwjbxxcx(){
	var dwglm = $("input[name='JBXX_DWGLM']").val();//单位管理码
	$.dialog.open("sbQyzgbgdjController.do?dwjbxxSelector&dwglm="+dwglm, {
		title : "单位基本信息查询",
		width:"100%",
		height:"100%",
	    lock : true,
	    resize : false,
	    close: function () {
	    	var dwxx = art.dialog.data("dwjbxxInfo");
	        if(dwxx){	        	
	        	FlowUtil.initFormFieldValue(dwxx[0],"T_SBQLC_QYZGBGDJ_FORM");
	        	//加载已参加险种信息
	        	$("#xzxxGrid").datagrid('loadData',dwxx[0].cbxx);	        	        	       	
        	}
	        art.dialog.removeData("dwxx");
	    }
	}, false);		
}

//单位信息查询（不弹框）
function dwxxcx(){
	 var dwglm = $("input[name='JBXX_DWGLM']").val();//单位管理码;
	 if( dwglm!="" && dwglm!="undefined" && dwglm!=null){
		 var layload = layer.load("正在提交请求中…");
		 $.ajax({
			  type: "POST",
			  url: "sbQyzgbgdjController.do?getDwxx&dwglm="+dwglm,
			  async: true, //采用同步方式进行数据判断
			  success: function (responseText) {
				layer.close(layload);
			    var data=$.parseJSON(responseText);
			  	if(data.success){
			  	   //清空业务表单数据
			  	   $("#qyzgbgdj").find("input[type='text'],.Wdate").val("");
			  	   $("#qyzgbgdj").find("select").find("option:selected").attr("selected", false);
			  	   $("#xzxxGrid").datagrid("loadData", { total: 0, rows: [] });
			       //数据回填
			  	   var data = JSON.parse(data.jsonString);
			  	   FlowUtil.initFormFieldValue(data,"T_SBQLC_QYZGBGDJ_FORM");
		           //加载已参加险种信息
		           $("#xzxxGrid").datagrid('loadData',data.cbxx);
			  	}else if(!data.success){
			  		art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
					    ok: true
					});	
			  		//清空业务表单数据
			  		$("#qyzgbgdj").find("input[type='text'],.Wdate").val("");
			  		$("#qyzgbgdj").find("select").find("option:selected").attr("selected", false);
			  		$("#xzxxGrid").datagrid("loadData", { total: 0, rows: [] });
			  	}	  	
			  }
		});
	 }else{
		 art.dialog({
			content : "请先输入完整的单位管理码！",
			icon : "warning",
			ok : true
		 });
	 }
}



//银行信息查询
function yhxxcx(){
	$.dialog.open("sbQyzgbgdjController.do?yhxxSelector", {
		title : "银行信息查询",
		width:"100%",
		height:"100%",
	    lock : true,
	    resize : false,
	    close: function () {
	    	 /*var yhxxInfo = art.dialog.data("yhxxInfo");
	        if(yhxxInfo){
	       	FlowUtil.initFormFieldValue(dwjbxxInfo,"qyzgbgdj");
	        	$("input[name='UNIT_FILE']").val(selectInfo.unitFile);//单位档案号	        	       	
        	}*/	
	    }
	}, false);
}




