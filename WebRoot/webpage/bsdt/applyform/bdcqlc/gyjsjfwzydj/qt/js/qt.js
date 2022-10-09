//表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	/*判断是否有保存意见*/
	if (!isSaveOpinion()) {
		parent.art.dialog({
			content : "填写完登记审核意见后请点击右上角保存！",
			icon : "warning",
			ok : true
		});
		return;
	}
	//先判断表单是否验证通过
	var validateResult =$("#T_BDCQLC_GYJSJFWZYDJQT_FORM").validationEngine("validate");
	if(validateResult){
		var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val();
		if (ZYDJ_TYPE == '5') {
			var vaObj = {"fieldName": "DY_DYQR", "fieldText": "抵押权人"};
			var vaFlag = verificaSpeField([vaObj]);
			if (!vaFlag.flag) {
				parent.art.dialog({
					content : vaFlag.msg,
					icon : "warning",
					ok : true
				});
				return;
			}
		}

		// 获得权利人信息
		getPowPeopleJson();
		// 获得权属来源信息
		getPowSourceJson();
		// 获得权属来源限制信息
		getPowLimitJson();
		// 获取发证明细数据
		getDjfzxxJson_zydj();
		// 获取缴费信息
		getDjjfxxJson_zydj();
		// 获取涉税登记买方信息
		getSsdjBuyJson();
		//获取涉税登记卖方信息
		getSsdjSellJson();

		var POWERPEOPLEINFO_JSON = $("#POWERPEOPLEINFO_JSON").val();
		var FW_GYQK = $("#FW_GYQK").val();
		if (POWERPEOPLEINFO_JSON && POWERPEOPLEINFO_JSON != "" && POWERPEOPLEINFO_JSON != "[]") {
			var POWERPEOPLEINFO_OBJ = JSON.parse(POWERPEOPLEINFO_JSON);
			if (POWERPEOPLEINFO_OBJ && POWERPEOPLEINFO_OBJ.length > 1 && FW_GYQK == '0') {
				parent.art.dialog({
					content : "多个权利人的时候不能选择单独所有。",
					icon : "warning",
					ok : true
				});
				return ;
			}
		}

         //setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //如果是收费环节要先进行登簿操作
			 if (!(flowSubmitObj.IS_HANDUP && flowSubmitObj.IS_HANDUP == '1')) {

				 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
					 /*判断此业务是否已被办理过*/
					 var bdcdyh = $("input[name='ESTATE_NUM']").val();
					 var bdcdyhFlag = AppUtil.verifyBdcdyhExistSubmit(bdcdyh);
					 if (!bdcdyhFlag) {
						 art.dialog({
							 content: "请注意：该不动产单元号已经办理过该业务！",
							 icon:"warning",
							 ok: true
						 });
						 return null;
					 }
				 }

				 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '收费') {
					 var flag = BDCQLC_Autobdcdbcz();
					 if(!flag){
						 var isdbflag = $("input[name='BDC_DBZT']").val();
						 if(isdbflag){
							 if(isdbflag =="-1"){
								 parent.art.dialog({
									 content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
									 icon : "warning",
									 ok : true
								 });
								 return;
							 }else if(isdbflag =="0"){
								 var dbjg = $("input[name='BDC_DBJG']").val();
								 parent.art.dialog({
									 content : "登簿异常！"+dbjg,
									 icon : "warning",
									 ok : true
								 });
								 return;
							 }
						 }else{
							 parent.art.dialog({
								 content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
								 icon : "warning",
								 ok : true
							 });
							 return;
						 }
					 }
				 }
			 }
			 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '登簿') {
				 var isdbflag = $("input[name='BDC_DBZT']").val();
				 if(isdbflag){
					 if(isdbflag =="-1"){
						 parent.art.dialog({
							 content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
							 icon : "warning",
							 ok : true
						 });
						 return;
					 }else if(isdbflag =="0"){
						 var dbjg = $("input[name='BDC_DBJG']").val();
						 parent.art.dialog({
							 content : "登簿异常！"+dbjg,
							 icon : "warning",
							 ok : true
						 });
						 return;
					 }
				 }else{
					 parent.art.dialog({
						 content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
						 icon : "warning",
						 ok : true
					 });
					 return;
				 }
			 }
			 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "预登簿") {
				 var IS_FINISHTAX_AUDIT = $("input[name='IS_FINISHTAX_AUDIT']:checked").val();
				 var IS_TAX_SUCCESS = $("#IS_TAX_SUCCESS").val();
				 if (IS_FINISHTAX_AUDIT && IS_FINISHTAX_AUDIT == '0') {
				 	if (!(IS_TAX_SUCCESS && IS_TAX_SUCCESS == '1')){
						parent.art.dialog({
							content : "尚未进行涉税申报。",
							icon : "warning",
							ok : true
						});
						return;
					}
				 }
			 }
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSJFWZYDJQT_FORM");
			 for (var index in formData) {
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 /*宗地用途特殊获取*/
			 var ZD_TDYT = $("#ZD_TDYT").combobox("getValues");
			 if (ZD_TDYT) {
				 flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
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
	// 获得权利人信息
    getPowPeopleJson();
    // 获得权属来源信息
    getPowSourceJson();
	// 获得权属来源限制信息
	getPowLimitJson();
    // 获取发证明细数据
    getDjfzxxJson_zydj();
    // 获取缴费信息
    getDjjfxxJson_zydj();
	// 获取涉税登记买方信息
	getSsdjBuyJson();
	//获取涉税登记卖方信息
	getSsdjSellJson();
    
    
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSJFWZYDJQT_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}
	/*宗地用途特殊获取*/
	var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
	if (ZD_TDYT) {
		flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
	}
	return flowSubmitObj;
		 
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}
  
//社会限制人员检验接口
function checkLimitPerson(){
	var data = [];
	var qlrtrs = $("#powerPeopleInfo tr[id*='powerPeopleInfo_']");
	for(var i=0;i<qlrtrs.length;i++){		
		var id = $(qlrtrs[i]).attr("id");
		var qlrData = {};
		var POWERPEOPLENAME = $("#"+id).find("[name='POWERPEOPLENAME']").val();
		var POWERPEOPLEIDNUM = $("#"+id).find("[name='POWERPEOPLEIDNUM']").val();
		if(POWERPEOPLENAME!='' && POWERPEOPLEIDNUM!='' ){
			qlrData["xm"] = POWERPEOPLENAME;
			qlrData["zjhm"] = POWERPEOPLEIDNUM;
			data.push(qlrData);
		}
	
		
		var frData = {};
		var POWLEGALNAME = $("#"+id).find("[name='POWLEGALNAME']").val();
		var POWLEGALIDNUM = $("#"+id).find("[name='POWLEGALIDNUM']").val();
		if(POWLEGALNAME!=''&& POWLEGALIDNUM!=''){		
			frData["xm"] = POWLEGALNAME;
			frData["zjhm"] = POWLEGALIDNUM;
			data.push(frData);
		}
		var dlrData = {};
		var POWAGENTNAME = $("#"+id).find("[name='POWAGENTNAME']").val();
		var POWAGENTIDNUM = $("#"+id).find("[name='POWAGENTIDNUM']").val();
		if(POWAGENTNAME!=''&& POWAGENTIDNUM!=''){		
			dlrData["xm"] = POWAGENTNAME;
			dlrData["zjhm"] = POWAGENTIDNUM;
			data.push(dlrData);
		}
	}
	var qslytrs = $("#powerSourceInfo tr[id*='powerSourceInfo_']");
	for(var i=0;i<qslytrs.length;i++){		
		var id = $(qslytrs[i]).attr("id");
		var qlrData = {};
		var POWERSOURCE_QLRMC = $("#"+id).find("[name='POWERSOURCE_QLRMC']").val();
		var POWERSOURCE_ZJHM = $("#"+id).find("[name='POWERSOURCE_ZJHM']").val();
		if(POWERSOURCE_QLRMC!='' && POWERSOURCE_ZJHM!=''){
		 	 qlrData["xm"] = POWERSOURCE_QLRMC;
			 qlrData["zjhm"] = POWERSOURCE_ZJHM;
			 data.push(qlrData);
		}
	
		var frData = {};
		var POWERSOURCE_FRDB = $("#"+id).find("[name='POWERSOURCE_FRDB']").val();
		var POWERSOURCE_FRZJHM = $("#"+id).find("[name='POWERSOURCE_FRZJHM']").val();
		if(POWERSOURCE_FRDB!='' && POWERSOURCE_FRZJHM!=''){		
			frData["xm"] = POWERSOURCE_FRDB;
			frData["zjhm"] = POWERSOURCE_FRZJHM;
			data.push(frData);
		}
		var dlrData = {};
		var POWERSOURCE_DLRXM = $("#"+id).find("[name='POWERSOURCE_DLRXM']").val();
		var POWERSOURCE_DLRZJHM = $("#"+id).find("[name='POWERSOURCE_DLRZJHM']").val();
		if(POWERSOURCE_DLRXM!='' && POWERSOURCE_DLRZJHM!=''){		
			dlrData["xm"] = POWERSOURCE_DLRXM;
			dlrData["zjhm"] = POWERSOURCE_DLRZJHM;
			data.push(dlrData);
		}
	}
	var flag = true;
	if(data.length<1){
		flag = false ;
	}
	if(flag){
		var cxlist = JSON.stringify(data);
		$.ajaxSettings.async = false;
		$.post("<%=basePath%>/bdcApplyController.do?checkLimitPerson",{cxlist:cxlist},
			function(responseText, status, xhr) {
				var obj =responseText.rows;
				if(responseText.total>0){
				    var name="";
				    for(var i=0;i<obj.length;i++){
				    	name+=obj[i].XM+"("+obj[i].ZJHM+")、";
				    }
				    name=name.substring(0,name.length-1);
					parent.art.dialog({
						content: "存在涉会/涉黑人员【"+name+"】,不可受理此登记！",
						icon:"warning",
						ok: true
					});
					flag = false;
				}		
	   	}); 
   	}
   	return flag;
}




//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}

//设置抵押权人
function setDYQLName(val){
	var datas = $('#DY_DYQR').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='DY_DYQRZJHM']").val(datas[i].DIC_CODE);
			$('#DY_DYQR').combobox('setValue',val);
			$("select[name='DY_DYQRZJLX'] option[value='营业执照']").prop("selected", true);
			break ;
		}
	}
}

function setFileNumber(serialNum){
	/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
	var enterprise = '${execution.SQJG_CODE}';
	var idCard = '${execution.SQRSFZ}';
// 	alert(idCard + "," + enterprise);
	var fileNum;
	if (enterprise != ""){
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
	} else {
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
	}
	$("#fileNumber").val(fileNum);
}


function initAutoTable(flowSubmitObj){
	var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
	if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
		var powerpeopleItems = JSON.parse(powerpeopleinfoJson);
		initPowPeople(powerpeopleItems);
	}
	var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if(null != powersourceinfoJson && '' != powersourceinfoJson && '[]' != powersourceinfoJson){
		var powersourceItems = JSON.parse(powersourceinfoJson);
		initPowSource(powersourceItems);
	}
	var powerlimitinfoJson = flowSubmitObj.busRecord.POWERLIMITINFO_JSON;
	if (null != powerlimitinfoJson && '' != powerlimitinfoJson && '[]' != powerlimitinfoJson) {
		var powerlimitItems = JSON.parse(powerlimitinfoJson);
		initPowLimit(powerlimitItems);
	}
	
	var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
	if(null != djfzxx_json && '' != djfzxx_json){
		var djfzxx_jsonItems = JSON.parse(djfzxx_json);
		initDjfzxx_zydj(djfzxx_jsonItems);
	}
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_zydj(djjfmx_jsonItems);
	}
	if(flowSubmitObj.busRecord.DY_SFZGEDY){
		setSfzgedy(flowSubmitObj.busRecord.DY_SFZGEDY);	
	}
	var ssdjbuyinfojson = flowSubmitObj.busRecord.SSDJBUYINFO_JSON;
	if(null != ssdjbuyinfojson && '' != ssdjbuyinfojson && '[]' != ssdjbuyinfojson){
		var ssdjbuyItems = JSON.parse(ssdjbuyinfojson);
		initSsdjBuy(ssdjbuyItems);
	}
	var ssdjsellinfojson = flowSubmitObj.busRecord.SSDJSELLINFO_JSON;
	if(null != ssdjsellinfojson && '' != ssdjsellinfojson && '[]' != ssdjsellinfojson){
		var ssdjsellItems = JSON.parse(ssdjsellinfojson);
		initSsdjSell(ssdjsellItems);
	}

}

/**=================权利信息开始================================*/
function changePower(val){
	if(val=="0"){
	   $("input[name='POWERPEOPLEPRO']").val("100");
	}else if(val="1"){
		 $("input[name='POWERPEOPLEPRO']").val("共同共有");
	}else{
       //$("input[name='POWERPEOPLEPRO']").attr("disabled","disabled");
       $("input[name='POWERPEOPLEPRO']").val("");
	}
}
//------------------------------------身份身份证读取开始---------------------------------------------------
		function MyGetData()//OCX读卡成功后的回调函数
		{	
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>		
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
		}

		function MyClearData()//OCX读卡失败后的回调函数
		{
	        alert("未能有效识别身份证，请重新读卡！");
			$("input[name='POWERPEOPLENAME']").val("");   
			$("input[name='POWERPEOPLEIDNUM']").val("");  
		}

		function MyGetErrMsg()//OCX读卡消息回调函数
		{
// 			Status.value = GT2ICROCX.ErrMsg;	   
		}

		function PowerPeopleRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
				$("[name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		function PowLegalRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWLEGALNAME']").val(GT2ICROCX.Name);
				$("[name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		function PowAgentRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWAGENTNAME']").val(GT2ICROCX.Name);
				$("[name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		
		function PowSrcRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWERSOURCE_QLRMC']").val(GT2ICROCX.Name);
				$("[name='POWERSOURCE_ZJHM']").val(GT2ICROCX.CardNo);
				$("#POWERSOURCE_ZJLX").val("身份证");
				checkLimitPerson();
			}
		} 
		
		function PowFRDHRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWERSOURCE_FRDB']").val(GT2ICROCX.Name);
				$("[name='POWERSOURCE_FRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		
		function PowDLRRead(o)//开始读卡
		{
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("[name='POWERSOURCE_DLRXM']").val(GT2ICROCX.Name);
				$("[name='POWERSOURCE_DLRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------





function Trim(str){ 
  if(str){
  	 return str.replace(/(^\s*)|(\s*$)/g, ""); 
  }else{
  	return str;
  }
}

function queryTypeFn(){
	var val  = $("input[name='ZYDJ_TYPE']").val();
	var itemList = $("#TEMPLATE_LIST").val();
	$("#powerLimitInfo").hide();
	$("#powerLimitInfoList").hide();
	$("#hthCheck").hide();
	if(val == '5'){
		disabledQslyForm();
		$("#hthCheck").show();
		$("#powerDY_div").attr("style","");
		// 分户 抵押联办 
		if(val == '5' && itemList.indexOf('不动产登记审批表两审(抵押联办)')!=-1) {
			$("#dfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		}else {
			$("#dfspb").attr("onclick","errorAction()");
			$("#sfspb").attr("onclick","errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class","tab_text")
		$("#zswhPow").hide();
		$("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
	} else if (val == '3'){
		//权利限制
		$("#powerLimitInfo").show();
		$("#powerLimitInfoList").show();
		$("#powerDY_div").attr("style","display:none;");
		$("#powerSourceInfo select , #powerSourceInfo input").each(function (index) {
			$(this).attr("disabled", false);
		})
		if (val == '3' && itemList.indexOf('国有建设及房屋转移登记通用审批表') != -1) {
			$("#dfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		} else {
			$("#dfspb").attr("onclick","errorAction()");
			$("#sfspb").attr("onclick","errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class","tab_text validate[required]")
		$("#zswhPow").show();
		$("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
	} else if (val == '1') {
		disabledQslyForm();
		$("#hthCheck").show();
		$("#powerDY_div").attr("style","display:none;");
		//分户
		if (val == '1' && itemList.indexOf('国有建设及房屋转移登记通用审批表') != -1) {
			$("#dfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		} else {
			$("#dfspb").attr("onclick","errorAction()");
			$("#sfspb").attr("onclick","errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class","tab_text")
		$("#zswhPow").hide();
		$("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
	} else if (val == '6') {
		$("#powerSourceInfo select , #powerSourceInfo input").each(function (index) {
			$(this).attr("disabled", false);
		})
		$("#powerDY_div").attr("style","display:none;");
		//其它
		if (val == '6' && itemList.indexOf('国有建设及房屋转移登记通用审批表') != -1) {
			$("#dfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick","goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		} else {
			$("#dfspb").attr("onclick","errorAction()");
			$("#sfspb").attr("onclick","errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class","tab_text validate[required]")
		$("#zswhPow").show();
		$("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
	} else if (val == '4') {
		disabledQslyForm();
		$("#powerDY_div").attr("style", "display:none;");
		// 存量房税费联办
		if (val == '4' && itemList.indexOf('不动产登记审批表三审') != -1) {
			$("#dfspb").attr("onclick", "goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick", "goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		} else {
			$("#dfspb").attr("onclick", "errorAction()");
			$("#sfspb").attr("onclick", "errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class", "tab_text validate[required]");
		$("#zswhPow").show();
		$("#powTr").show();
		$("#gasTr").show();
		$("#waterTr").show();
	} else {
		disabledQslyForm();
		$("#powerDY_div").attr("style", "display:none;");
		// 存量房税费联办
		if (val == '4' && itemList.indexOf('不动产登记审批表三审') != -1) {
			$("#dfspb").attr("onclick", "goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
			$("#sfspb").attr("onclick", "goPrintTemplate('GYJSJFWZYDJTYSPB','3')");
		} else {
			$("#dfspb").attr("onclick", "errorAction()");
			$("#sfspb").attr("onclick", "errorAction()");
		}
		$("input[name='POWERSOURCE_QSWH']").attr("class", "tab_text validate[required]");
		$("#zswhPow").show();
	}
}

function setSfzgedy(value) {
	if (value === "1") {
		$("input[name='DY_ZGZQSE']").attr("disabled", false).addClass("validate[required]");
		$("#zgzqeFont").show();
		$("#bdbzzqseFont").hide();
		$("input[name='DY_DBSE']").attr("disabled", true).removeClass("validate[required]");
		$("select[name='DY_DYFS']").val("2");
	} else {
		$("input[name='DY_ZGZQSE']").attr("disabled", true).removeClass("validate[required]");
		$("#bdbzzqseFont").show();
		$("#zgzqeFont").hide();
		$("input[name='DY_DBSE']").attr("disabled", false).addClass("validate[required]");
		$("select[name='DY_DYFS']").val("1");
	}

}

/**
 * 抵押档案查询
 */
function showSelectBdcdydacx(){
	$.dialog.open("bdcApplyController.do?bdcDyInfoSelector&allowCount=1", {
		title : "不动产抵押档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdydacxInfo");
			if(bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1){
				var data = bdcdaxxcxInfo[0];
				var info = {
					"DY_DJJG":"平潭综合实验区不动产登记中心",
					"DY_DYQR":data.DYQR,
					//"DY_DYQRZJLX":"",
					//"DY_DYQRZJHM":"",
					"DY_ZWR":data.ZWR,
					//"DY_ZWRZJLX":"",
					//"DY_ZWRZJHM":"",
					"DY_DYR":data.DYR,
					//"DY_DYWJZ":"",
					"DY_SFZGEDY":"",//是否最高额抵押
					"DY_DBSE":data.BDBZZQSE,
					"DY_ZGZQSE":data.ZGZQSE,
					"DY_DYFS":data.DYFS,//抵押方式
					"DY_QLQSSJ":data.QLQSSJ,
					"DY_QLJSSJ":data.QLJSSJ,
					"DY_DYZS":"",//抵押宗数
					//"DY_BDCDJZMH":data.BDCDJZMH,
					//"DY_DYDJYY":data.DJYY,
					"DY_ZGZQQDSS":data.ZGZQQDSS,
					//"DY_DBR":data.DBR,
					//"DY_DJSJ":data.DJSJ,
					"DY_DYFW":data.ZJJZWDYFW,
					"DY_FJ":data.FJ,
					"DY_BDCDYH":data.BDCDYH,
					"DY_YWH":data.YWH,
					"DY_DYCODE":data.DYCODE,
					"DY_GLH":data.GLH
				}
				FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSJFWZYDJQT_FORM");
				
				if(info.DY_DYQR){
					setDYQLName(info.DY_DYQR.trim());
				}
				
				$("#DY_DYFS option").each(function(){
					var text = $(this).text();
					if(text == data.DYFS){
						$(this).attr("selected",true);
					}
				});

			}else{
				parent.art.dialog({
					content: "请根据查询选择一条不动产抵押档案信息。",
					icon:"warning",
					ok: true
				})
			}
			art.dialog.removeData("bdcdaxxcxInfo");
		}
	}, false);
}

/*
* 不动产预告登记查询
* */
function showSelectBdcygdjcx() {
	var IS_KFSYWSL = $("input[name='IS_KFSYWSL']").val();
	var isKfsywsl = $("input[name='isKfsywsl']").val();
	var url = "bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1";
	if ((IS_KFSYWSL && IS_KFSYWSL == '1') || (isKfsywsl && isKfsywsl == '1')) {
		url += "&isKfsywsl=1";
	}
	$.dialog.open(url, {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcygdacxInfo && bdcygdacxInfo.length == 1){
				var data = bdcygdacxInfo[0];
				var info = {
					"DY_DJJG":"平潭综合实验区不动产登记中心",
					"DY_DYQRZJHM":data.QLRZJH,
					"DY_ZWR":data.ZWR,
					"DY_ZWRZJHM":data.ZWRZJHM,
					"DY_DYR":data.YWR,
					"DY_DBSE":data.QDJG,
					"DY_QLQSSJ":data.QSSJ,
					"DY_QLJSSJ":data.JSSJ,
					"DY_BDCDYH":data.BDCDYH,
					"DY_YWH":data.YWH,
					"DY_GLH":data.GLH,
					"DY_DYRZJHM1":data.YWRZJH
				};
				FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSJFWZYDJQT_FORM");
				$("#DY_DYQR").combobox("setValue", data.QLR);
				formatDic("DY_ZWRZJLX", data.ZWRZJZL);
				formatDic("DY_DYRZJLX1", data.YWRZJZL);
				art.dialog.removeData("bdcygdacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条预告登记信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
}

/**
 * 国有建设用地及房屋所有权转移登记查询不动产档案信息
 */
function showSelectBdcfwzdcx(){
	var isBank = $("input[name='isBank']").val();
	var url = "bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1";
	if (isBank == 'true') {
		url = "bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0";
	}
	$.dialog.open(url, {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if(bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1){
				var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
				var result = {
					"isPass":true
				}
				/*抵押、限制、查封状态中的房子可以在二手房交易中读取，并无提示弹窗*/
				/*需要对房子状态进行提示，一手房(分户)提示，二手房（存量）不允许办理，数据不会填*/
				var zydjType = $('input[name="ZYDJ_TYPE"]').val();
				if (zydjType == '5' || zydjType == '1') {
					if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
						result = {
							"isPass": true,
							"msg": "请注意，该不动产单元号为" + cqzt + "状态",
							"type": "0"
						};
					}
				} else if (zydjType == '4' || zydjType == '6' || zydjType == '3') {
					if (zydjType == '4' || zydjType == '6') {
						if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
							result = {
								"isPass": false,
								"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
								"type": "0"
							};
						}
					} else if (zydjType == '3') {
						if (cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
							result = {
								"isPass": false,
								"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
								"type": "0"
							};
						}
					}

				}
				if(cqzt.indexOf("注销")!=-1 || cqzt.indexOf("无效")!=-1){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为"+cqzt+"状态，不可受理此登记。",
						"type":"0"
					};
				}

				var info = daxxFillInfoJson(bdcdaxxcxInfo[0]);

				/*创建权属来源信息*/
				var powerSourceItems = daxxFillPowerSource(info);

				if(result.isPass == true){

					daxxFillAllInfo(info, bdcdaxxcxInfo[0], powerSourceItems);

					daxxQlxzcxInfo(bdcdaxxcxInfo[0])

					/*回填涉税登记信息*/
					fillSsdjFormInfo(bdcdaxxcxInfo[0]);

					if(result.type=="0"){
						parent.art.dialog({
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}
				}else{
					if(result.type=="0"){
						parent.art.dialog({
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}
				}
				art.dialog.removeData("bdcdaxxcxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条不动产登记信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
};

//房地产合同备案查询
function showSelectFdchtbacx(){
	var IS_KFSYWSL = $("input[name='IS_KFSYWSL']").val();
	var isKfsywsl = $("input[name='isKfsywsl']").val();
	var url = "bdcApplyController.do?fdchtbaxxcxSelector&allowCount=1";
	if ((IS_KFSYWSL && IS_KFSYWSL == '1') || (isKfsywsl && isKfsywsl == '1')) {
		url += "&isKfsywsl=1";
	}
	$.dialog.open(url, {
		title : "房地产合同备案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var fdchtbaxxInfo = art.dialog.data("fdchtbaxxInfo");
			if (fdchtbaxxInfo && fdchtbaxxInfo.length == 1) {
				/*自动查询档案信息*/
				fillBdcDaxxForm(fdchtbaxxInfo[0]);
				$("#FW_JYJG").val(fdchtbaxxInfo[0].CJJG);
				$("#FW_BAHTH").val(fdchtbaxxInfo[0].FWMMHTH);
				$("input[name='HTBAH']").val(fdchtbaxxInfo[0].FWMMHTH);
				$("input[name='ESTATE_NUM']").val(fdchtbaxxInfo[0].BDCDYH);

				/*涉税登记信息回填*/
				$("#SSDJ_FYXX_JYHTBH").val(fdchtbaxxInfo[0].FWMMHTH);
				$("#SSDJ_FWXX_JYHTBH").val(fdchtbaxxInfo[0].FWMMHTH);
				if (fdchtbaxxInfo[0].CJJG) {
					var cjjg = (fdchtbaxxInfo[0].CJJG * 10000).toFixed(2);
					$("#SSDJ_FYXX_JYJG").val(cjjg);
					$("#SSDJ_FYXX_HTJE").val(cjjg);
					$("#SSDJ_FYXX_DQYSKJE").val(cjjg);
					$("#SSDJ_FWXX_JYJG").val(cjjg);
					$("#SSDJ_FWXX_HTJE").val(cjjg);
					$("#SSDJ_FWXX_BCJYJE").val(cjjg);
				}
				$("#SSDJ_FWXX_HTQDSJ").val(fdchtbaxxInfo[0].HTQDSJ);
				$("#SSDJ_FWXX_JFSJ").val(fdchtbaxxInfo[0].HTQDSJ);
				$("#SSDJ_FYXX_HTQDSJ").val(fdchtbaxxInfo[0].HTQDSJ);
				$("#SSDJ_FWXX_FJH").val(fdchtbaxxInfo[0].HH);
				$("#SSDJ_FWXX_PQMC_S").combobox("setValue", "350128100000002001");
				$("#SSDJ_FWXX_PQDM").val("350128100000002001");

				/*买方信息*/
				fillSsdjBuyxxForm(fdchtbaxxInfo[0]);
				/*卖方信息*/
				fillSsdjSellxxForm(fdchtbaxxInfo[0]);

			} else {
				parent.art.dialog({
					content: "请根据查询选择一条备案信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
}

/*回填涉税登记买方信息*/
function fillSsdjBuyxxForm(info) {
	$(".ssdjbuyinfo_tr").each(function (index) {
		$(this).remove();
	})
	$("#ssdjBuyInfo").attr("trid", "");

	var MSFXM = info.MSFXM;
	var MSFXMARR = splitInfo(MSFXM);
	var MSFZJHM = info.MSFZJHM;
	var MSFZJHMARR = splitInfo(MSFZJHM);

	var MSFLXDH = info.MSFLXDH;
	var MSFLXDHARR = splitInfo(MSFLXDH);

	var MSFLXDZ = info.MSFLXDZ;
	var MSFLXDZARR = splitInfo(MSFLXDZ);

	for (let i = 0; i < MSFXMARR.length; i++) {
		$("#SSDJ_BUY_XM").val(MSFXMARR[i]);//买方姓名
		fillSsdjZjlx(info.MSFZJLB,"SSDJ_BUY_ZJLX");
		$("#SSDJ_BUY_ZJHM").val(MSFZJHMARR[i]);
		$("#SSDJ_BUY_LXDH").val(MSFLXDHARR[i]);
		$("#SSDJ_BUY_LXDZ").val(MSFLXDZARR[i]);
		submitBuyInfo('1');
		if (i < (MSFXMARR.length - 1)) {
			$("#ssdjBuyInfo").attr("trid", "");
		}
	}
}

function splitInfo(str) {
	if (str.indexOf("|") != -1) {
		return str.split("|");
	} else if (str.indexOf(",") != -1) {
		return str.split(",");
	} else {
		return str.split(",");
	}
}

/*回填涉税登记卖方信息*/
function fillSsdjSellxxForm(info) {
	$(".ssdjsellinfo_tr").each(function (index) {
		$(this).remove();
	})
	$("#ssdjSellInfo").attr("trid", "");

	var ZRFXM = info.ZRFXM;
	var ZRFXMARR = splitInfo(ZRFXM);

	var ZRFZJHM = info.ZRFZJHM;
	var ZRFZJHMARR = splitInfo(ZRFZJHM);

	var ZRFLXDH = info.ZRFLXDH;
	var ZRFLXDHARR = splitInfo(ZRFLXDH);

	var ZRFLXDZ = info.ZRFLXDZ;
	var ZRFLXDZARR = splitInfo(ZRFLXDZ);

	for (let i = 0; i < ZRFXMARR.length; i++) {
		$("#SSDJ_SELL_XM").val(ZRFXMARR[i]);
		fillSsdjZjlx(info.ZRFZJLB,"SSDJ_SELL_ZJLX");
		$("#SSDJ_SELL_ZJHM").val(ZRFZJHMARR[i]);
		$("#SSDJ_SELL_LXDH").val(ZRFLXDHARR[i]);
		$("#SSDJ_SELL_LXDZ").val(ZRFLXDZARR[i]);
		$("#SSDJ_SELL_SCQDFWCB").val(0);
		submitSellInfo('1');
		if (i < (ZRFXMARR.length - 1)) {
			$("#ssdjSellInfo").attr("trid", "");
		}
	}
}


function fillSsdjFormInfo(info) {
	$("#SSDJ_FYXX_BDCDYH").val(info.BDCDYH);
	$("#SSDJ_FWXX_BDCDYH").val(info.BDCDYH);
	$("#SSDJ_FYXX_XZQH").val('350128');
	$("#SSDJ_FWXX_XZQH").val('350128');
	$("#SSDJ_FYXX_FWDZ").val(info.FDZL);
	$("#SSDJ_FWXX_FWDZ").val(info.FDZL);
	$("#SSDJ_FYXX_FWZLC").val(info.ZCS);
	$("#SSDJ_FYXX_JZMJ").val(info.JZMJ);
	$("#SSDJ_FWXX_JZMJ").val(info.JZMJ);
	//单价
	// if (info.JZMJ && info.JYJG) {
	// 	var dj = (Math.round((info.JYJG * 10000) / info.JZMJ *100) / 100).toFixed(2);
	// 	$("#SSDJ_FYXX_DJ").val(dj);
	// 	$("#SSDJ_FWXX_DJ").val(dj);
	// }
	if (info.JYJG) {
		var jyjg = (info.JYJG * 10000).toFixed(2);
		$("#SSDJ_FYXX_JYJG").val(jyjg);
		$("#SSDJ_FYXX_HTJE").val(jyjg);
		$("#SSDJ_FYXX_DQYSKJE").val(jyjg);
		$("#SSDJ_FWXX_JYJG").val(jyjg);
		// $("#SSDJ_FWXX_BCJYDJ").val(jyjg);
		$("#SSDJ_FWXX_HTJE").val(jyjg);
		$("#SSDJ_FWXX_BCJYJE").val(jyjg);
	}
	$("#SSDJ_FWXX_JCNF").val(info.JGSJ);
	/*回填开发企业名称*/
	var val = $('input[name="ZYDJ_TYPE"]').val();
	if (val == '1' || val == '5') {
		$("#SSDJ_FYXX_KFQYMC").val(info.QLRMC);
		$("#SSDJ_FYXX_NSRSBH").val(info.ZJHM);
	}
}

/*格式化证件类别*/
function fillSsdjZjlx(type,id) {
	var parse = "";
	if (type) {
		if (type == "身份证") {
			parse = '201';
		} else if (type == '护照') {
			parse = '227';
		} else if (type == '组织机构代码') {
			parse = '101';
		} else if (type == '营业执照') {
			parse = '102';
		} else if (type == '其它') {
			parse = '299';
		} else if (type == '港澳居民来往内地通行证') {
			parse = '210';
		} else if (type == '统一社会编码'){
			parse = '101';
		} else {
			parse = '201';
		}
	} else {
		parse = "201";
	}
	$("#" + id).val(parse);
}

/**
 * 不动产登簿操作
 */
function BDCQLC_bdcdbcz(){
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeId = flowSubmitObj.EFLOW_EXEID;
	if(exeId != null && typeof exeId != undefined){
		var layload = layer.load('正在提交登簿数据…');
		$.post("bdcQlcApplyController/bdcdbcz.do", {
			"eveId" : exeId
		}, function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){					
				var data = $.parseJSON(resultJson.data);
				if(data.retcode == "00" && data.dbzt == "1"){
					if(data.qzinfo && data.qzinfo.length > 0){
						var qzinfos = data.qzinfo;
						var val = $('input[name="ZYDJ_TYPE"]').val(); 
						if(val == "4"){
							//存量房税费联办
							flag = bdcclfsflbdbfun(qzinfos,true);
						}else if(val == "5"){
							//分户抵押联办
							flag = bdcdylbdbfun(qzinfos,true);
						}else if (val == '1') {
							//分户
							flag = bdcfhdbfun(qzinfos, true);
						} else if (val == '3') {
							//权利限制
							flag = bdcqlxzdbfun(qzinfos, true);
						} else if (val == '6') {
							//其它
							flag = bdcqtdbfun(qzinfos, true);
						}
					}
				}else if(data.retcode == "99" ){
					$("input[name='BDC_DBZT']").val(0);
					$("input[name='BDC_DBJG']").val(data.msg);
					art.dialog({
						content : data.msg+"登簿失败。",
						icon : "warning",
						ok : true
					});
				}else{
					$("input[name='BDC_DBZT']").val(data.dbzt);
					$("input[name='BDC_DBJG']").val(data.ret_msg);
					art.dialog({
						content : data.ret_msg+"登簿失败。",
						icon : "warning",
						ok : true
					});
				}
			}else{
				art.dialog({
					content : resultJson.msg,
					icon : "warning",
					ok : true
				});
			}
		});
	}
	return flag;
}

//自动登簿分方法
function BDCQLC_Autobdcdbcz(){
	var flag = false;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeId = flowSubmitObj.EFLOW_EXEID;
	$.ajaxSettings.async = false;
	var layload = layer.load('正在提交登簿数据…');
	$.post("bdcQlcApplyController/bdcdbcz.do",
		{"eveId":exeId},
		function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){					
				var data = $.parseJSON(resultJson.data);
				if(data.retcode == "00" && data.dbzt == "1"){
					if(data.qzinfo && data.qzinfo.length > 0){
						var qzinfos = data.qzinfo;
						var val = $('input[name="ZYDJ_TYPE"]').val();
						if(val == "4"){
							//存量房税费联办
							flag = bdcclfsflbdbfun(qzinfos,true);
						}else if(val == "5"){
							//分户抵押联办
							flag = bdcdylbdbfun(qzinfos,true);
						}else if (val == '1') {
							//分户
							flag = bdcfhdbfun(qzinfos, true);
						} else if (val == '3') {
							//权利限制
							flag = bdcqlxzdbfun(qzinfos, true);
						} else if (val == '6') {
							//其它
							flag = bdcqtdbfun(qzinfos, true);
						}
					}
				}else{
					$("input[name='BDC_DBZT']").val(data.dbzt);
					$("input[name='BDC_DBJG']").val(data.ret_msg);
				}
			}else{
				$("input[name='BDC_DBZT']").val("-1");
				$("input[name='BDC_DBJG']").val("登簿失败。");
			}		
		}
	); 
	return flag;
}

/**
 * 存量房税费联办登簿方法
 */
function bdcclfsflbdbfun(qzinfos,dialog){	
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getPowPeopleJson();
	if(qzinfos.length == qlrs.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME 
							&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(qlrs);
		if(iflag != qlrs.length){
			$("input[name='BDC_DBZT']").val("0");
			$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
			if(dialog){
				art.dialog({
					content :"归档失败，存在与受理时的权利不一致的情况。",
					icon : "warning",
					ok : true
				});
			}
		}else{
			flag = true;
			$("input[name='BDC_DBZT']").val("1");
			$("input[name='BDC_DBJG']").val("登簿成功");
			/*按钮无法点击*/
			disabledDbBtn("BDC_DBBTN");
			if(dialog){
				art.dialog({
					content :"登簿成功",
					icon : "succeed",
					ok : true
				});
			}
			
		}
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

/*
* 权利限制
* */
function bdcqlxzdbfun(qzinfos,dialog) {
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getPowPeopleJson();
	if(qzinfos.length == qlrs.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME
						&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(qlrs);
		if(iflag != qlrs.length){
			$("input[name='BDC_DBZT']").val("0");
			$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
			if(dialog){
				art.dialog({
					content :"归档失败，存在与受理时的权利不一致的情况。",
					icon : "warning",
					ok : true
				});
			}
		}else{
			flag = true;
			$("input[name='BDC_DBZT']").val("1");
			$("input[name='BDC_DBJG']").val("登簿成功");
			disabledDbBtn("BDC_DBBTN");
			if(dialog){
				art.dialog({
					content :"登簿成功",
					icon : "succeed",
					ok : true
				});
			}

		}
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

/*
* 分户登簿方法
* */
function bdcfhdbfun(qzinfos,dialog) {
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getPowPeopleJson();
	if(qzinfos.length == qlrs.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME
						&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(qlrs);
		if(iflag != qlrs.length){
			$("input[name='BDC_DBZT']").val("0");
			$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
			if(dialog){
				art.dialog({
					content :"归档失败，存在与受理时的权利不一致的情况。",
					icon : "warning",
					ok : true
				});
			}
		}else{
			flag = true;
			$("input[name='BDC_DBZT']").val("1");
			$("input[name='BDC_DBJG']").val("登簿成功");
			disabledDbBtn("BDC_DBBTN");
			if(dialog){
				art.dialog({
					content :"登簿成功",
					icon : "succeed",
					ok : true
				});
			}

		}
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

/*
* 其它
* */
function bdcqtdbfun(qzinfos,dialog) {
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getPowPeopleJson();
	if(qzinfos.length == qlrs.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME
						&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(qlrs);
		if(iflag != qlrs.length){
			$("input[name='BDC_DBZT']").val("0");
			$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
			if(dialog){
				art.dialog({
					content :"归档失败，存在与受理时的权利不一致的情况。",
					icon : "warning",
					ok : true
				});
			}
		}else{
			flag = true;
			$("input[name='BDC_DBZT']").val("1");
			$("input[name='BDC_DBJG']").val("登簿成功");
			disabledDbBtn("BDC_DBBTN");
			if(dialog){
				art.dialog({
					content :"登簿成功",
					icon : "succeed",
					ok : true
				});
			}

		}
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

/**
 * 抵押联办登簿方法
 */
function bdcdylbdbfun(qzinfos,dialog){	
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getPowPeopleJson();
	var dyqr = $('#DY_DYQR').combobox('getValue');
	var dyqrzh = $("input[name='DY_DYQRZJHM']").val();
	if(qzinfos.length == (qlrs.length+1)){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME 
							&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
				// //抵押人
				if(qzinfos[i].qlr_mc == dyqr
						&& qzinfos[i].qlr_zh == dyqrzh){
					$("input[name='DY_BDCDJZMH']").val(qzinfos[i].qzzh);
					$("input[name='DY_DBR']").val(currentUser);
					$("input[name='DY_DJSJ']").val(currentTime);
					iflag = iflag + 1;
				}
			}
		}
		initPowPeople(qlrs);
		// if(iflag != (qlrs.length+1)){
		// 	$("input[name='BDC_DBZT']").val("0");
		// 	$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
		// 	if(dialog){
		// 		art.dialog({
		// 			content :"归档失败，存在与受理时的权利不一致的情况。",
		// 			icon : "warning",
		// 			ok : true
		// 		});
		// 	}
		// }else{
		flag = true;
		$("input[name='BDC_DBZT']").val("1");
		$("input[name='BDC_DBJG']").val("登簿成功");
		disabledDbBtn("BDC_DBBTN");
		if(dialog){
			art.dialog({
				content :"登簿成功",
				icon : "succeed",
				ok : true
			});
		}
		// }
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
	if (typeId == 2) {
		var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
		if(!BDC_QZBSM){
			art.dialog({
				content :"不动产产权证书权证标识码未填写。",
				icon : "warning",
				ok : true
			});
			return null;
		}
	}
	// typeId: 1为证书预览  2为证书打印
	var title = "证书打印";
	var templateAlias = 'BDCQZ';
	if(typeId==1) {
		title = "证书预览";
	}
	var dataStr = "";
	dataStr +="&QLRZJH="+$("input[name='POWERPEOPLEIDNUM']").val();
	dataStr +="&BDCQZH="+$("input[name='BDC_SZZH']").val();
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //1为证书预览  2为证书打印
	dataStr +="&templateAlias=" + templateAlias;
	var url=encodeURI("certificateTemplateController.do?invokeCustomMethod"+dataStr);
	$.dialog.open(url, {
		title : title,
		width: "100%",
		height: "100%",
		left: "0%",
		top: "0%",
		fixed: true,
		lock : true,
		resize : false
	}, false);
}



//不动产登记证明打印与预览
function bdcDJZMprint(typeId){
	if (typeId == 2) {
		var DY_BDCQZBSM = $("input[name='DY_BDCQZBSM']").val();
		if(!DY_BDCQZBSM){
			art.dialog({
				content :"不动产登记证明权证标识码未填写。",
				icon : "warning",
				ok : true
			});
			return null;
		}
	}
	// typeId: 1为证书预览  2为证书打印
	var title = "证书打印";
	var templateAlias = 'DJZM';
	if(typeId==1) {
		title = "证书预览";
	}
	var dataStr = "";
	dataStr +="&QLRZJH="+$("input[name='DY_DYQRZJHM']").val();
	dataStr +="&BDCQZH="+$("input[name='DY_BDCDJZMH']").val();
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //1为证书预览  2为证书打印
	dataStr +="&templateAlias=" + templateAlias;
	var url=encodeURI("certificateTemplateController.do?invokeCustomMethod"+dataStr);
	$.dialog.open(url, {
		title : title,
		width: "100%",
		height: "100%",
		left: "0%",
		top: "0%",
		fixed: true,
		lock : true,
		resize : false
	}, false);
}

function viewCertificate(typeId){
	// typeId: 1为证书预览  2为证书打印   3打印登记证明
	var title = "证书打印";
	var templateAlias = 'BDCQZ';
    if(typeId==1) {
    	title = "证书预览";
    }
    if(typeId==3) {
    	templateAlias = 'DJZM';
    }
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //1为证书预览  2为证书打印
	//dateStr +="&TemplatePath="+TemplatePath;	
	dataStr +="&templateAlias=" + templateAlias;
	dataStr +="&POWAGENTIDNUM="+$("#POWAGENTIDNUM").val();
	//dateStr +="&isSignButton="+isSignButton;
	var url=encodeURI("certificateTemplateController.do?invokeCustomMethod"+dataStr);
    $.dialog.open(url, {
        title : title,
        width: "100%",
        height: "100%",
        left: "0%",
        top: "0%",
        fixed: true,
        lock : true,
        resize : false
	}, false);
}

function goPrintTemplate(TemplateName,typeId) {
	// 税费联办 or 抵押联办
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val(); 
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板
	//dataStr +="&TemplatePath="+TemplatePath;	
	dataStr +="&TemplateName="+TemplateName;
	dataStr +="&ZYDJ_TYPE="+ZYDJ_TYPE;
	//dataStr +="&isSignButton="+isSignButton;
	var url=encodeURI("printAttachController.do?printTemplate"+dataStr);
    //打印模版
    $.dialog.open(url, {
            title : "打印模版",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
     }, false);
}

function isButtonAvailable(){
	var result = {};
	result["ITEM_CODE"] = $("input[name='ITEM_CODE']").val();
	result["EXE_ID"] = $("#EXEID").text();
	result["CUR_STEPNAMES"] = $("#CUR_STEPNAMES").val();
	$.ajaxSettings.async = false; //关闭异步
	$.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
		  var itemList = resultJson.rows;
		  var readNames = "";
          for(var i=0; i<itemList.length; i++){
        	  readNames += itemList[i].READ_NAME + ",";
        	  if(itemList[i].READ_NAME=='抵押明细_在建工程'){
        		  $("#dfdymxzjgc").attr("onclick","goPrintTemplate('DYMXZJGC',3)");
        	  }
        	  if(itemList[i].READ_NAME=='抵押明细_审批表'){
        		  $("#dfdymxspb").attr("onclick","goPrintTemplate('DYMXSPB',3)");
        	  }
          }
          $("#TEMPLATE_LIST").val(readNames);
	});
	$.ajaxSettings.async = true//打开异步
}

function errorAction(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}

/*是否完税表单校验修改*/
function isFinishTax() {
	var IS_FINISHTAX_AUDIT = $("input[name='IS_FINISHTAX_AUDIT']:checked").val();
	if (IS_FINISHTAX_AUDIT == 1) {
		$("textarea[name='TAX_AUDITCOMMENTS']").attr("class", "validate[maxSize[500]]");
		$("#sssbFont").hide();
		$("#taxRelatedInfo").hide();
	} else {
		$("textarea[name='TAX_AUDITCOMMENTS']").attr("class", "validate[[required],maxSize[500]]");
		$("#sssbFont").show();
		$("#taxRelatedInfo").show();
	}
}

/*权属来源部分字段不可填写*/
function disabledQslyForm() {
	$("#POWERSOURCE_QSLYLX").attr("disabled", "disabled");
	$("#POWERSOURCE_QSWH").attr("disabled", "disabled");
	$("#POWERSOURCE_DYH").attr("disabled", "disabled");
	$("#POWERSOURCE_QLRMC").attr("disabled", "disabled");
	$("#POWERSOURCE_QSZT").attr("disabled", "disabled");
	$("#POWERSOURCE_ZJLX").attr("disabled", "disabled");
	$("#POWERSOURCE_ZJHM").attr("disabled", "disabled");
	$("#POWERSOURCE_ZDDM").attr("disabled", "disabled");
	$("#POWERSOURCE_GLH").attr("disabled", "disabled");
	$("#POWERSOURCE_FDDBR").attr("disabled", "disabled");
	$("#POWERSOURCE_FDDBR_TEL").attr("disabled", "disabled");
}

/*附记可修改*/
function setFjRevise() {
	$("textarea[name='GYTD_FJ']").attr("disabled", false).removeAttr("readonly");
	$("textarea[name='FW_FJ']").attr("disabled", false).removeAttr("readonly");
}

/*展示登记证明按钮*/
function showDjzm() {
	$("#dy_bdcdjzmh_tr").attr("style", "");
	$("#dy_bdcqzdbr_tr").attr("style", "");
	$("#DY_BDC_QZVIEW").attr("style", "");
	$("#DY_BDCDJZMH").attr("disabled", false);
	$("#DY_BDCQZBSM").attr("disabled", false);
}

function isSaveOpinion() {
	var flag = true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	if (flowSubmitObj && flowSubmitObj.EFLOW_CURUSEROPERNODENAME) {
		if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
			var CS_OPINION_CONTENT = $("textarea[name='CS_OPINION_CONTENT']").val();
			if (CS_OPINION_CONTENT && CS_OPINION_CONTENT != '') {
				var CS_OPINION_NAME = $("input[name='CS_OPINION_NAME']").val();
				var CS_OPINION_TIME = $("input[name='CS_OPINION_TIME']").val();
				if (!(CS_OPINION_NAME && CS_OPINION_TIME && CS_OPINION_NAME != '' && CS_OPINION_TIME != '')) {
					flag = false;
				}
			}
		}
		if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '预登簿' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
			var HZ_OPINION_CONTENT = $("textarea[name='HZ_OPINION_CONTENT']").val();
			if (HZ_OPINION_CONTENT && HZ_OPINION_CONTENT != '') {
				var HZ_OPINION_NAME = $("input[name='HZ_OPINION_NAME']").val();
				var HZ_OPINION_TIME = $("input[name='HZ_OPINION_TIME']").val();
				if (!(HZ_OPINION_NAME && HZ_OPINION_TIME && HZ_OPINION_NAME != '' && HZ_OPINION_TIME != '')) {
					flag = false;
				}
			}
		}
	}
	return flag;
}

/*当为分户和分户抵押联办时自动查询档案信息*/
function fillBdcDaxxForm(bdcHtbaxxInfo) {
	var val = $('input[name="ZYDJ_TYPE"]').val();
	if (val == '1' || val == '5') {
		if (bdcHtbaxxInfo.BDCDYH) {
			var layload = layer.load('正在查询数据…');
			$.ajaxSetup({async: false});
			$.post("bdcDyqscdjController.do?bdcdaxxcxDatagrid", {"bdcdyh": bdcHtbaxxInfo.BDCDYH}, function (data) {
				layer.close(layload);
				if (data && data.rows && data.rows.length > 0) {
					for (let i = 0; i < data.rows.length; i++) {
						var QSZT = data.rows[i].QSZT;
						if (QSZT == '现势') {
							var info = daxxFillInfoJson(data.rows[i]);
							var powerSourceItems = daxxFillPowerSource(info);
							daxxFillAllInfo(info, data.rows[i], powerSourceItems);
							daxxQlxzcxInfo(data.rows[i])
							fillSsdjFormInfo(data.rows[i]);
						}
					}
				}
			});
			$.ajaxSetup({async: true});
		}
	}
}

/*创建权属来源信息*/
function daxxFillPowerSource(info) {
	//定义个startWithOwn
	String.prototype.startWithOwn = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substr(0, s.length) == s)
			return true;
		else
			return false;
		//return true;
	}
	var powerSource = {};
	for(var name in info){
		if(name.startWithOwn("POWERSOURCE_")){
			powerSource[name]=info[name];
		}
	}

	var powerSourceItems = [];
	powerSourceItems.push(powerSource);
	return powerSourceItems;
}

/*回填所有档案信息*/
function daxxFillAllInfo(info, bdcdaxxcxInfo, powerSourceItems) {
	FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSJFWZYDJQT_FORM");

	//回填权属来源信息
	initPowSource(powerSourceItems);

	//回填权利类型
	$("#ZD_QLLX option").each(function(){
		var text = $(this).text();
		if(text == info.ZD_QLLX.trim()){
			$(this).attr("selected",true);
		}
	});

	//回填GYTD_GYFS
	$("#GYTD_GYFS option").each(function(){
		var text = $(this).text();
		if(text == info.GYTD_GYFS.trim()){
			$(this).attr("selected",true);
		}
	});
	//回填FW_GYQK
	$("#FW_GYQK option").each(function(){
		var text = $(this).text();
		if(text == info.FW_GYQK.trim()){
			$(this).attr("selected",true);
		}
	});
	//回填FW_QLLX
	$("#FW_QLLX option").each(function(){
		var text = $(this).text();
		if(text == info.FW_QLLX.trim()){
			$(this).attr("selected",true);
		}
	});

	formatDic("ZD_TZM", bdcdaxxcxInfo.ZDTZM); //宗地特征码

	//回填ZD_QLXZ
	if(info.ZD_QLXZ){
		var datas = $("#ZD_QLXZ").combobox("getData");
		for(var i=0;i<datas.length;i++){
			if(datas[i].text == info.ZD_QLXZ){
				$("#ZD_QLXZ").combobox("setValue",datas[i].value);
				break;
			}
		}
	}

	//回填宗地坐落区县
	if (bdcdaxxcxInfo.XZQ) {
		$("#ZDZL_XIAN").combobox("setValue", bdcdaxxcxInfo.XZQ);
	}

	//回填宗地坐落镇
	if (bdcdaxxcxInfo.DJQ) {
		$("#ZDZL_ZHENG").combobox("setValue", bdcdaxxcxInfo.DJQ);
	}

	//回填宗地坐落乡村
	if (bdcdaxxcxInfo.DJZQ) {
		$("#ZDZL_CUN").combobox("setValue", bdcdaxxcxInfo.DJZQ);
	}

	//回填土地用途
	if (bdcdaxxcxInfo.TDYT) {
		$("#ZD_TDYT").combobox("setValues", bdcdaxxcxInfo.TDYT.split(","));
	}
}

/*权利限制查询抵押或查封数据*/
function daxxQlxzcxInfo(bdcdaxxcxInfo) {
	var zydjType = $('input[name="ZYDJ_TYPE"]').val();
	var cqzt = bdcdaxxcxInfo.CQZT.trim();
	/*权利限制需要查询抵押或查封数据*/
	if (zydjType && zydjType == '3') {
		if (cqzt.indexOf('抵押') != -1) {
			$.ajaxSettings.async = false;
			$.post("bdcDyqscdjController.do?datagrid",{bdcdyh:bdcdaxxcxInfo.BDCDYH},function (data) {
				if (data) {
					var rows = data.rows;
					for (let i = 0; i < rows.length; i++) {
						if (rows[i].QSZT == '现势') {
							$("#LIMIT_SW").val(1);
							$("#LIMIT_HZ").val(1);
							$("#LIMIT_SFYX").val(1);
							$("#LIMIT_QLLX").val('A');
							$("#LIMIT_QLRQ").val(rows[i].DJSJ);
							$("#LIMIT_QLRMC").val(rows[i].DYR);
							$("#LIMIT_CQZH").val(rows[i].BDCQZH);
							$("#LIMIT_YWRMC").val(rows[i].ZWR);
							$("#LIMIT_WJH").val(rows[i].BDCDJZMH);
							$("#LIMIT_ADDR").val(rows[i].ZJJZWZL);
							$("#LIMIT_CODE").val(rows[i].DYCODE);
							submitPowLimitInfo('1');
						}
					}
				}
			})
			$.ajaxSettings.async = true;
		}
		if (cqzt.indexOf('查封') != -1) {
			if (cqzt.indexOf('抵押') != -1) {
				cleanInfo();
			}
			$.ajaxSettings.async = false;
			$.post("bdcDyqscdjController.do?bdccfxxDatagrid",{bdcdyh:bdcdaxxcxInfo.BDCDYH},function (data) {
				if (data) {
					var rows = data.rows;
					for (let i = 0; i < rows.length; i++) {
						if (rows[i].QSZT == '现势') {
							$("#LIMIT_SW").val(1);
							$("#LIMIT_HZ").val(1);
							$("#LIMIT_SFYX").val(1);
							$("#LIMIT_QLLX").val('B');
							$("#LIMIT_QLRQ").val(rows[i].DJSJ);
							$("#LIMIT_CQZH").val(rows[i].BDCDJZH);
							$("#LIMIT_WJH").val(rows[i].CFWH);
							$("#LIMIT_ADDR").val(rows[i].ZL);
							$("#LIMIT_CODE").val(rows[i].CFBH);
							submitPowLimitInfo('1');
						}
					}
				}
			});
			$.ajaxSettings.async = true;
		}
	}
}

function daxxFillInfoJson(bdcdaxxcxInfo) {
	var info = {
		"ESTATE_NUM":bdcdaxxcxInfo.BDCDYH,
		"LOCATED":bdcdaxxcxInfo.FDZL,
		"POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
		"POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
		"POWERSOURCE_QSZT":bdcdaxxcxInfo.QSZT,
		"POWERSOURCE_ZJLX":bdcdaxxcxInfo.ZJLX,
		"POWERSOURCE_ZJHM":bdcdaxxcxInfo.ZJHM,
		"POWERSOURCE_ZDDM":bdcdaxxcxInfo.ZDDM,
		"POWERSOURCE_GLH":bdcdaxxcxInfo.GLH,
		"POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo.QLQSSJ,//权利开始时间
		"POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo.QLJSSJ,//权利结束时间
		"POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo.QLJSSJ1,
		"POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo.QLJSSJ2,
		"ZD_BM":bdcdaxxcxInfo.ZDDM,
		"ZD_QLLX":bdcdaxxcxInfo.ZDQLLX,//宗地权利类型
		"ZD_QLSDFS":bdcdaxxcxInfo.QLSDFS,
		"ZD_ZL":bdcdaxxcxInfo.TDZL,
		"ZD_MJ":bdcdaxxcxInfo.ZDMJ,
		"ZD_YTSM":bdcdaxxcxInfo.TDYTSM,
		"ZD_QLXZ":bdcdaxxcxInfo.QLXZ,//权利性质
		"ZD_LEVEL":bdcdaxxcxInfo.DJ,
		"ZD_RJL":bdcdaxxcxInfo.RJL,
		"ZD_JZXG":bdcdaxxcxInfo.JZXG,
		"ZD_JZMD":bdcdaxxcxInfo.JZMD,
		"ZD_E":bdcdaxxcxInfo.TD_SZ_D,
		"ZD_W":bdcdaxxcxInfo.TD_SZ_X,
		"ZD_N":bdcdaxxcxInfo.TD_SZ_B,
		"ZD_S":bdcdaxxcxInfo.TD_SZ_N,
		"GYTD_BEGIN_TIME":bdcdaxxcxInfo.QLQSSJ,
		"GYTD_END_TIME1":bdcdaxxcxInfo.QLJSSJ,
		"GYTD_END_TIME2":bdcdaxxcxInfo.QLJSSJ1,
		"GYTD_END_TIME3":bdcdaxxcxInfo.QLJSSJ2,
		"GYTD_GYFS":bdcdaxxcxInfo.GYFS,
		"GYTD_QDJG":bdcdaxxcxInfo.JG,
		"GYTD_SYQMJ":bdcdaxxcxInfo.SYQMJ,
		"GYTD_QSZT":bdcdaxxcxInfo.QSZT,
		"FW_ZL":bdcdaxxcxInfo.FDZL,
		"FW_ZH":bdcdaxxcxInfo.ZH,
		"FW_SZC":bdcdaxxcxInfo.SZC,
		"FW_HH":bdcdaxxcxInfo.HH,
		"FW_ZCS":bdcdaxxcxInfo.ZCS,
		"FW_GHYT":bdcdaxxcxInfo.FW_GHYT,
		"FW_YTSM":bdcdaxxcxInfo.GHYTSM,
		"FW_XZ":bdcdaxxcxInfo.FWXZ,
		"FW_FWJG":bdcdaxxcxInfo.FWJG,//房屋结构
		"FW_DYDYTDMJ":bdcdaxxcxInfo.DYTDMJ,
		"FW_FTTDMJ":bdcdaxxcxInfo.FTTDMJ,
		"FW_JZMJ":bdcdaxxcxInfo.JZMJ,
		"FW_GYQK":bdcdaxxcxInfo.GYFS,//房屋共有情况
		"FW_ZYJZMJ":bdcdaxxcxInfo.ZYJZMJ,
		"FW_FTJZMJ":bdcdaxxcxInfo.FTJZMJ,
		"FW_QLLX":bdcdaxxcxInfo.FW_QLLX,
		"FW_JGSJ":bdcdaxxcxInfo.JGSJ,
		"POWERSOURCE_DYH":bdcdaxxcxInfo.BDCDYH,
		"POWERSOURCE_QSWH":bdcdaxxcxInfo.BDCQZH,//证书文号（权属来源）
		"POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
		"BDCDYH":bdcdaxxcxInfo.BDCDYH,//不动产单元号
		"ZDDM":bdcdaxxcxInfo.ZDDM,//宗地代码
		"FWBM":bdcdaxxcxInfo.FWBM,//房屋编码
		"YWH":bdcdaxxcxInfo.YWH,//业务号
		"ZXYWH":bdcdaxxcxInfo.ZXYWH,//注销业务号
		"GLH":bdcdaxxcxInfo.GLH//关联号
	};
	return info;
}

function initEasyUiForm() {
	$("#ZD_TDYT").combobox({
		url:'bdcGyjsjfwzydjController/loadTdytData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		prompt:'请选择土地用途',
		panelHeight:'200',
		multiple:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
		},
		onLoadSuccess: function (row) {  //下拉框数据加载成功调用
			var opts = $(this).combobox('options');
			var target = this;
			var values = $(target).combobox('getValues');//获取选中的值的values
			$.map(values, function (value) {
				var el = opts.finder.getEl(target, value);
				el.find('input.combobox-checkbox')._propAttr('checked', true);
			})
		},
		onSelect: function (row) { //选中一个选项时调用
			var opts = $(this).combobox('options');
			//设置选中值所对应的复选框为选中状态
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', true);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		},
		onUnselect: function (row) {
			//不选中一个选项时调用
			var opts = $(this).combobox('options');
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', false);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		}
	});

	$("#ZDZL_XIAN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onLoadSuccess:function(row) {
			$('#ZDZL_XIAN').combobox('setValue','351001');
		},
		onSelect:function (row) {
			$('#ZDZL_ZHENG').combobox('clear');
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
				$('#ZDZL_ZHENG').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_ZHENG").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
				$('#ZDZL_CUN').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_CUN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {

		}
	});

	$("#SSDJ_FWXX_PQMC_S").combobox({
		valueField: 'DIC_CODE',
		textField: 'DIC_NAME',
		editable:true,
		hasDownArrow:true,
		url: "dictionaryController.do?load&typeCode=SSDJFWSZPQ",
		required:false,
		panelHeight: '200',
		filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		onSelect:function (row) {
			$("#SSDJ_FWXX_PQDM").val(row.DIC_CODE);
			$("#SSDJ_FWXX_PQMC").val(row.DIC_NAME);
		}
	});
}

function calSinglePrice() {
	/*计算单价*/
	var SSDJ_FYXX_JYJG = $("#SSDJ_FYXX_JYJG").val();
	var SSDJ_FYXX_JZMJ = $("#SSDJ_FYXX_JZMJ").val();
	if (SSDJ_FYXX_JYJG && SSDJ_FYXX_JZMJ) {
		var dj = (Math.round((SSDJ_FYXX_JYJG) / SSDJ_FYXX_JZMJ * 100) / 100).toFixed(2);
		$("#SSDJ_FYXX_DJ").val(dj);
	}
	var SSDJ_FWXX_JYJG = $("#SSDJ_FWXX_JYJG").val();
	var SSDJ_FWXX_JZMJ = $("#SSDJ_FWXX_JZMJ").val();
	if (SSDJ_FWXX_JYJG && SSDJ_FWXX_JZMJ) {
		var dj = (Math.round((SSDJ_FWXX_JYJG) / SSDJ_FWXX_JZMJ * 100) / 100).toFixed(2);
		$("#SSDJ_FWXX_DJ").val(dj);
		$("#SSDJ_FWXX_BCJYDJ").val(dj);
	}
}

/*展示涉税登记，登记审核表单*/
function showSsdjForm() {
	$("#djshxx").attr("style", "");
	$("textarea[name='TAX_AUDITCOMMENTS']").removeAttr("readOnly");
	var IS_FINISHTAX_AUDIT = $("input[name='IS_FINISHTAX_AUDIT']:checked").val();
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val();
	var IS_FINISHTAX = $('input[name="IS_FINISHTAX"]:checked').val();
	if (ZYDJ_TYPE == '4') {
		if (IS_FINISHTAX_AUDIT == 0) {
			$("#taxRelatedInfo").show();
		}
	} else if (ZYDJ_TYPE == '5' || ZYDJ_TYPE == '1' || ZYDJ_TYPE == '6') {
		if (IS_FINISHTAX_AUDIT == 0 && IS_FINISHTAX == 0) {
			$("#taxRelatedInfo").show();
		} else {
			$("#taxRelatedInfo").hide();
		}
	} else if (ZYDJ_TYPE == '3') {
		$("#taxRelatedInfo").hide();
	}
}

function checkIsPowTransfer(val) {
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val();
	if (ZYDJ_TYPE == '4') {
		if (val) {
			if (val == '1') {
				$("#dlhhName").show();
				$("#dlhhInput").show();
			} else {
				$("#dlhhName").hide();
				$("#dlhhInput").hide();
			}
		}
	}
}
function checkIsWaterTransfer(val) {
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val();
	if (ZYDJ_TYPE == '4') {
		if (val) {
			if (val == '1') {
				$("#slhhName").show();
				$("#slhhInput").show();
			} else {
				$("#slhhName").hide();
				$("#slhhInput").hide();
			}
		}
	}
}
function checkIsGasTransfer(val) {
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]').val();
	if (ZYDJ_TYPE == '4') {
		if (val) {
			if (val == '1') {
				$("#rqhhName").show();
				$("#rqhhInput").show();
			} else {
				$("#rqhhName").hide();
				$("#rqhhInput").hide();
			}
		}
	}
}

function delChildItem() {
	$("#xhTitle").hide();
	$("#sxzxTitle").hide();
	$(".materIndex").each(function () {
		$(this).hide();
	});
	$("td[name='busClass']").each(function () {
		$(this).hide();
	});
	$("#xhTitleCheck").hide();
	$("#sxzxTitleCheck").hide();
	$(".materCheckIndex").each(function () {
		$(this).hide();
	});
	$("td[name='busClassCheck']").each(function () {
		$(this).hide();
	});

}




