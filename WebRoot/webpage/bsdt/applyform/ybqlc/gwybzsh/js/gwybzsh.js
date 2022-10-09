

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //获取推送标志位
	 var pushFlag = $("input[name='PUSH_FLAG']").val();
	 //先判断表单是否验证通过
	 var validateResult = $("#T_YBQLC_GWYBZSH_FORM").validationEngine("validate");
	 if(validateResult){
		 if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME !="开始"){
			 var inithfvalue = $("input[name='BZSH_TYPE']:checked").val();
			 var validateResult1 =$("#page_"+inithfvalue).validationEngine("validate");
			 if(!validateResult1){
				 return null;
			 }
		 }
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理") {
				 getFormData(flowSubmitObj);
			 }
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查" && pushFlag!='1') {
				if (!isPushYb(flowSubmitObj)){//推送数据至医保系统
					 return null;
				 }else{
					 alert("业务数据推送至平潭医保系统成功,请完成流程办结！");
				 }	
			 }
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYBZSH_FORM");
			 //办结结果领取方式&其他信息
			 var otherData = FlowUtil.getFormEleData("other");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 for(var index in otherData){
				 flowSubmitObj[eval("index")] = otherData[index];
			 }
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
	//获取子项的业务数据
	 var bzshValue = $("input[name='BZSH_TYPE']:checked").val();
	 if(bzshValue == "1"){
		 //登记信息
		 var formData = FlowUtil.getFormEleData("page_1");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }			
	 }else if(bzshValue == "2"){
		 getBgxxJson_Page2("bgxx");		
	 }else if(bzshValue == "3"){
		 getZxxxJson_Page3("zxxx");	
	 }
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYBZSH_FORM");
	 //办结结果领取方式&其他信息
	 var otherData = FlowUtil.getFormEleData("other");
	 for(var index in formData){
		 flowSubmitObj[eval("index")] = formData[index];
	 }
	 for(var index in otherData){
		 flowSubmitObj[eval("index")] = otherData[index];
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
 * 受理环节-获取表单业务数据
 */
function getFormData(flowSubmitObj){
	 //获取子项的业务数据
	 var bzshValue = $("input[name='BZSH_TYPE']:checked").val();
	 if(bzshValue == "1"){
		 //登记信息
		 var formData = FlowUtil.getFormEleData("page_1");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }			
	 }else if(bzshValue == "2"){
		 //变更信息
		 getBgxxJson_Page2("bgxx");			
	 }else if(bzshValue == "3"){
		 //注销信息
		 getZxxxJson_Page3("zxxx");
	 }		 
}


//业务数据推送医保
function isPushYb(flowSubmitObj){
	var bzshValue = $("input[name='BZSH_TYPE']:checked").val();//子项类型
	var result = true;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
		type: "POST",
        url: "ybGwybzshController.do?pushYb&ywId="+ywId+"&type="+bzshValue,
        async: false, //采用同步方式进行数据判断
        success: function (responseText) {
            var data=$.parseJSON(responseText);
        	if(data.success){
                result = true;
                $('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
        	}else if(!data.success){
        		art.dialog({
        			content: "推送医保系统异常："+data.msg,
					icon:"warning",
					time:5,
				    ok: true
				});
        		result = false;
        	}	
        }
	});
	return result;
}

/**
 * 获取变更信息
 */
function getBgxxJson_Page2(id){	
	var datas = {};
	var array = $("#"+id+" [name]");
	for(var i =0;i<array.length;i++){
		var value = array.eq(i).val();
		value = AppUtil.trim(value);
		var name = array.eq(i).attr("name");
		datas[name] = value;	
	}
	$("input[type='hidden'][name='BGXX_JSON']").val(JSON.stringify(datas));		
}

/**
 * 获取注销信息
 */
function getZxxxJson_Page3(id){
	var datas = {};
	var array = $("#"+id+" [name]");
	for(var i =0;i<array.length;i++){
		var value = array.eq(i).val();
		value = AppUtil.trim(value); 
		var name = array.eq(i).attr("name");
		datas[name] = value;	
	}
	$("input[type='hidden'][name='ZXXX_JSON']").val(JSON.stringify(datas));	
}


/**
 * 类型切换操作
 */
function bzshtypeclick(){
	var value = $("input[name='BZSH_TYPE']:checked").val();
	if(value == "1"){
		$("#page_1").attr("style","");
		//截止时间默认-99991231
		$("input[name='JZSJ']").val("99991231");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","display:none;");	
	}else if(value == "2"){
		$("#page_1").attr("style","display:none");
		$("#page_2").attr("style","");
		$("#page_3").attr("style","display:none;");	
	}else if(value == "3"){
		$("#page_1").attr("style","display:none");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","");	
	}
}

/**
 * 可登记人员查询
 */
function showSelectDjInfos(){
	$.dialog.open("ybGwybzshController.do?showSelectDjInfos&allowCount=1", {
		title : "信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			 var djInfos = art.dialog.data("djInfos");
			 if(djInfos!=undefined&&djInfos!=null&&djInfos!=""){
				 var info = {
						 	"GRBH": djInfos[0].aac001,//个人编号
							"ZJHM":djInfos[0].aac002,//身份证
							"XM":djInfos[0].aac003,//姓名
							"GRBXH":djInfos[0].bac503,//个人保险号
							"DWBXH":djInfos[0].aab999,//单位保险号
							"DWMC":djInfos[0].aab004,//单位名称
							"FZX":djInfos[0].orgcode,//分中心
							"GZZT":djInfos[0].aac066,//工作状态
						};	
				 FlowUtil.initFormFieldValue(info,"cbdj");
			 }
			 art.dialog.removeData("djInfos");			
		}
	},false);
}

/**
 * 变更-已登记人员信息查询
 */
function showSelectBgYdjInfos(){
	$.dialog.open("ybGwybzshController.do?showSelectYdjInfos&allowCount=1", {
		title : "已登记人员信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			 var djInfos = art.dialog.data("djInfos");
			 if(djInfos!=undefined&&djInfos!=null&&djInfos!=""){
				 var info = {
							"BG_RYLBID":djInfos[0].baz518,//特殊人员类别ID
							"BG_WZBID":djInfos[0].baz522,//公务员危重病ID
							"BG_HM":djInfos[0].aac002,//身份证
							"BG_GRBH":djInfos[0].aac001,//个人编号								
							"BG_XM":djInfos[0].aac003,//姓名
							"BG_BXH":djInfos[0].bac503,//个人保险号
							"BG_DWBXH":djInfos[0].aab999,//单位保险号
							"BG_DWMC":djInfos[0].aab004,//单位名称
							"BG_RYLB":djInfos[0].bac508,//特殊人员类别
							"BG_WBZBM":djInfos[0].aae012,//危重病类别
							"BG_QSNY":djInfos[0].aae041,//起始年月
							"BG_ZZNY":djInfos[0].aae042,//终止年月
							"BG_YHHM":djInfos[0].aae009,//银行户名
							"BG_YHZH":djInfos[0].aae010,//银行账号
							"BG_FZX":djInfos[0].orgcode,//分中心
							"BG_BZ":djInfos[0].aac058,//备注 
							"BG_GZZT":djInfos[0].aac066,//工作状态 
						};	
				 FlowUtil.initFormFieldValue(info,"bgxx");
			 }
			 art.dialog.removeData("djInfos");			
		}
	},false);	
}


/**
 * 注销-已登记人员信息查询
 */
function showSelectZxYdjInfos(){
	$.dialog.open("ybGwybzshController.do?showSelectYdjInfos&allowCount=1", {
		title : "已登记人员信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			 var djInfos = art.dialog.data("djInfos");
			 if(djInfos!=undefined&&djInfos!=null&&djInfos!=""){
				 var info = {
							"ZX_RYLBID":djInfos[0].baz518,//特殊人员类别ID
							"ZX_WZBID":djInfos[0].baz522,//公务员危重病ID
							"ZX_HM":djInfos[0].aac002,//身份证
							"ZX_GRBH":djInfos[0].aac001,//个人编号		
							"ZX_XM":djInfos[0].aac003,//姓名
							"ZX_BXH":djInfos[0].bac503,//个人保险号
							"ZX_DWBXH":djInfos[0].aab999,//单位保险号
							"ZX_DWMC":djInfos[0].aab004,//单位名称
							"ZX_RYLB":djInfos[0].bac508,//特殊人员类别
							"ZX_WBZBM":djInfos[0].aae012,//危重病类别
							"ZX_QSNY":djInfos[0].aae041,//起始年月
							"ZX_ZZNY":djInfos[0].aae042,//终止年月
							"ZX_YHHM":djInfos[0].aae009,//银行户名
							"ZX_YHZH":djInfos[0].aae010,//银行账号
							"ZX_FZX":djInfos[0].orgcode,//分中心
							"ZX_BZ":djInfos[0].aac058,//备注 
							"ZX_GZZT":djInfos[0].aac066,//工作状态 
						};	
				 FlowUtil.initFormFieldValue(info,"zxxx");				 
			 }
			 art.dialog.removeData("djInfos");			
		}
	},false);	
}








