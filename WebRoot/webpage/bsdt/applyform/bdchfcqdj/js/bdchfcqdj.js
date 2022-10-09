
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var inithfvalue = $("input[name='HF_TYPE']:checked").val();
	 var validateResult1 =$("#page_"+inithfvalue).validationEngine("validate");
	 var validateResult =$("#T_BDC_HFCQDJ_FORM").validationEngine("validate");
	 if(validateResult&&validateResult1){
		 setGrBsjbr();//个人不显示经办人设置经办人的值
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDC_HFCQDJ_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 //获取子项的业务数据
			 var hfvalue = $("input[name='HF_TYPE']:checked").val();
			 if(hfvalue == "1"){
				 getPowerPeopleInfoJson1();
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
					getPowerSourceInfoJson1();
				} else{
					getPowerSourceInfoJson();
				}
			 }else if(hfvalue == "2"){
				 getPowerPeopleInfoJson();
			 }
			 var pageMata = FlowUtil.getFormEleData("page_"+hfvalue);
			 for(var index in pageMata){
				 flowSubmitObj[eval("index")] = pageMata[index];
			 }
			return flowSubmitObj;
		 }else{
			 return null;
		 }			 
	 }else{
		 return null;
	 }
}
/*function isPowerPeople(){
     var powerPeopleInfoSn=$("input[name='POWERPEOPLEINFO_JSON']").val();
     var sqrmc=$("input[name='SQRMC']").val();
     if(powerPeopleInfoSn.indexOf(sqrmc)>0){
         return true;
	 }else{
         return false;
	 }
}*/
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_HFCQDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}	
	//获取子项的业务数据
	 var hfvalue = $("input[name='HF_TYPE']:checked").val();
	 if(hfvalue == "1"){
		 getPowerPeopleInfoJson1();
		 if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
			getPowerSourceInfoJson1();
		 } else{
			getPowerSourceInfoJson();
		 }
	 }else if(hfvalue == "2"){
		 getPowerPeopleInfoJson();
	 }
	 var pageMata = FlowUtil.getFormEleData("page_"+hfvalue);
	 for(var index in pageMata){
		 flowSubmitObj[eval("index")] = pageMata[index];
	 }
	return flowSubmitObj;
		 
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}

/*类型切换操作*/
function hftypeclick(){
	var hfvalue = $("input[name='HF_TYPE']:checked").val();
	// 获取流程信息对象JSON，将选择的类型设值给流程对象，用于申请书打印
	var flowSubmitObjReg = FlowUtil.getFlowObj();
	flowSubmitObjReg['BDC_DYLX'] = hfvalue;
	parent.$("#EFLOW_FLOWOBJ").val(JSON.stringify(flowSubmitObjReg));
	
	if(hfvalue == 1){
		$("#page_1").attr("style","");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","display:none;");
		$("#page_4").attr("style","display:none;");
		$("#DYH_ISSHOW").attr("style","");
		if(!$("#ESTATE_NUM").hasClass("validate[required,custom[estateNum]]")){
			$("#ESTATE_NUM").addClass("validate[required,custom[estateNum]]");
		}
		$("#DYH_BTN").val("不动产档案信息查询");
		$("#DYH_BTN").unbind("click");
		$("#DYH_BTN").on("click",function(){
			showPage1Selector();
		});
		//设置默认的权利类型
		//$("select[name='HF_QLLX']").val("");
	}else if(hfvalue == 2){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","");
		$("#page_3").attr("style","display:none;");
		$("#page_4").attr("style","display:none;");
		$("#DYH_ISSHOW").attr("style","display:none;");
		if($("#ESTATE_NUM").hasClass("validate[required,custom[estateNum]]")){
			$("#ESTATE_NUM").removeClass("validate[required,custom[estateNum]]");
		}		
		initDyYgDjHfField();
		//设置默认的权利类型
		$("select[name='HF_QLLX']").val("18");
	}else if(hfvalue == 3){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","");
		$("#page_4").attr("style","display:none;");
		$("#DYH_ISSHOW").attr("style","");
		if(!$("#ESTATE_NUM").hasClass("validate[required,custom[estateNum]]")){
			$("#ESTATE_NUM").addClass("validate[required,custom[estateNum]]");
		}
		$("#DYH_BTN").val("不动产预告档案信息");
		$("#DYH_BTN").unbind("click");
		$("#DYH_BTN").on("click",function(){
			showSelectBdcYgdacx();
		});
		initYgDjHfField();
		//设置默认的权利类型
		$("select[name='HF_QLLX']").val("19");
	}else if(hfvalue == 4){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","display:none;");
		$("#page_4").attr("style","");
		$("#DYH_ISSHOW").attr("style","");
		if(!$("#ESTATE_NUM").hasClass("validate[required,custom[estateNum]]")){
			$("#ESTATE_NUM").addClass("validate[required,custom[estateNum]]");
		}
		$("#DYH_BTN").val("不动产预告档案信息");
		$("#DYH_BTN").unbind("click");
		$("#DYH_BTN").on("click",function(){
			showPage4Selector();
		});
		initPage4field();
		//设置默认的权利类型
		$("select[name='HF_QLLX']").val("19");
	}
}
		
function setQLRName(val){
	var datas = $('#QLR_MC').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='QLR_ZJNO']").val(datas[i].DIC_CODE);
			//$("input[name='QLR_ZJNO']").val(datas[i].DIC_DESC);//法人代表
			break ;
		}
	}
}

//设置抵押权人
function setDYQLName(val){
	var datas = $('#QLR_DYQR').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='QLR_DYQRZJHM']").val(datas[i].DIC_CODE);
			$("input[name='QLR_LEGALNAME']").val(datas[i].DIC_DESC);//法人代表
			break ;
		}
	}
}

/**返回时间格式YYYY-MM-DD*/
function DateConvertFun(str){
	var time = "";
	if(str){
		/*var result=str.match(/\d+/g); 
		var year = result[0];
		
		var day = result[2];
		time = year +"-";
		if(result[1]){
			var month = result[1];
			if(parseInt(month) > 9){
				time = time + month +"-";
			}else {
				time = time + "0"+ month + "-";
			}
		}
		if(result[2]){
			var day = result[2];
			if(parseInt(day) > 9){
				time = time + day;
			}else {
				time = time + "0"+ day;
			}
		}*/
		time=str;
	}
	return time;
}

/**
 * 换发产权证书
 * 不动产档案信息查询
 */
function showPage1Selector(){
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1", {
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
				/*if(cqzt == "限制"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为限制状态，不可受理此登记。",
						"type":"0"
					};
				} else if(cqzt=="查封"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为查封状态，不可受理此登记。",
						"type":"0"
					};
				}else if(cqzt=="预查封"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为预查封状态，不可受理此登记。",
						"type":"0"
					};
				}else if(cqzt=="注销"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为注销状态，不可受理此登记。",
						"type":"0"
					};
				}else if(cqzt == "查封办文中"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为查封办文中状态，不可受理此登记。",
						"type":"0"
					};
				}else if(cqzt=="无效"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为无效状态，不可受理此登记。",
						"type":"0"
					};
				}else if(cqzt=="异议"){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为异议状态，不可受理此登记。",
						"type":"0"
					};
				}else{
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为"+cqzt+"状态，是否继续办理业务。",
						"type":"1"
					};
				}*/
				if(cqzt.indexOf("注销")!=-1 || cqzt.indexOf("无效")!=-1){
					result = {
						"isPass":false,
						"msg":"请注意，该不动产单元号为"+cqzt+"状态，不可受理此登记。",
						"type":"0"
					};
				}
				
				var info = {
					"ESTATE_NUM":bdcdaxxcxInfo[0].BDCDYH,
					"LOCATED":bdcdaxxcxInfo[0].FDZL,
					"POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
					"POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
					"POWERSOURCE_QSZT":bdcdaxxcxInfo[0].QSZT,
					"POWERSOURCE_ZJLX":bdcdaxxcxInfo[0].ZJLX,
					"POWERSOURCE_ZJHM":bdcdaxxcxInfo[0].ZJHM,
					"POWERSOURCE_ZDDM":bdcdaxxcxInfo[0].ZDDM,
					"POWERSOURCE_EFFECT_TIME":DateConvertFun(bdcdaxxcxInfo[0].QLQSSJ),//权利开始时间
					"POWERSOURCE_CLOSE_TIME1":DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ),//权利结束时间
					//"POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo[0].QLJSSJ,//权利结束时间
					"ZD_BM":bdcdaxxcxInfo[0].ZDDM,
					"ZD_QLLX":bdcdaxxcxInfo[0].ZDQLLX,//宗地权利类型
					"ZD_QLSDFS":bdcdaxxcxInfo[0].QLSDFS,
					"ZD_ZL":bdcdaxxcxInfo[0].TDZL,
					"ZD_MJ":bdcdaxxcxInfo[0].ZDMJ,
					"ZD_TDYT":bdcdaxxcxInfo[0].TDYT,
					"ZD_YTSM":bdcdaxxcxInfo[0].TDYTSM,
					"ZD_QLXZ":bdcdaxxcxInfo[0].QLXZ,//权利性质
					"ZD_LEVEL":bdcdaxxcxInfo[0].DJ,
					"ZD_RJL":bdcdaxxcxInfo[0].RJL,
					"ZD_JZXG":bdcdaxxcxInfo[0].JZXG,
					"ZD_JZMD":bdcdaxxcxInfo[0].JZMD,
					"ZD_E":bdcdaxxcxInfo[0].TD_SZ_D,
					"ZD_W":bdcdaxxcxInfo[0].TD_SZ_X,
					"ZD_N":bdcdaxxcxInfo[0].TD_SZ_B,
					"ZD_S":bdcdaxxcxInfo[0].TD_SZ_N,
					"GYTD_BEGIN_TIME":DateConvertFun(bdcdaxxcxInfo[0].QLQSSJ),
					"GYTD_END_TIME1":DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ),
					//"GYTD_END_TIME2":bdcdaxxcxInfo[0].QLJSSJ,
					//"GYTD_END_TIME3":bdcdaxxcxInfo[0].QLJSSJ,
					"GYTD_GYFS":bdcdaxxcxInfo[0].GYFS,
					"GYTD_QDJG":bdcdaxxcxInfo[0].JG,
					"GYTD_SYQMJ":bdcdaxxcxInfo[0].SYQMJ,
					"GYTD_QSZT":bdcdaxxcxInfo[0].QSZT,
					"FW_ZL":bdcdaxxcxInfo[0].FDZL,
					"FW_ZH":bdcdaxxcxInfo[0].ZH,
					"FW_SZC":bdcdaxxcxInfo[0].SZC,
					"FW_HH":bdcdaxxcxInfo[0].HH,
					"FW_ZCS":bdcdaxxcxInfo[0].ZCS,
					"FW_GHYT":bdcdaxxcxInfo[0].FW_GHYT,
					"FW_YTSM":bdcdaxxcxInfo[0].GHYTSM,
					"FW_XZ":bdcdaxxcxInfo[0].FWXZ,
					"FW_FWJG":bdcdaxxcxInfo[0].FWJG,//房屋结构
					"FW_JYJG":bdcdaxxcxInfo[0].JYJG,//交易价格
					"FW_DYDYTDMJ":bdcdaxxcxInfo[0].DYTDMJ,
					"FW_FTTDMJ":bdcdaxxcxInfo[0].FTTDMJ,
					"FW_JZMJ":bdcdaxxcxInfo[0].JZMJ,
					"FW_GYQK":bdcdaxxcxInfo[0].GYFS,//房屋共有情况
					"FW_ZYJZMJ":bdcdaxxcxInfo[0].ZYJZMJ,
					"FW_FTJZMJ":bdcdaxxcxInfo[0].ZYJZMJ,
					"FW_QLLX":bdcdaxxcxInfo[0].FW_QLLX,
					"POWERSOURCE_DYH":bdcdaxxcxInfo[0].BDCDYH,
					"POWERSOURCE_QSWH":bdcdaxxcxInfo[0].BDCQZH,//证书文号（权属来源）
					"POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
					"BDCDYH":bdcdaxxcxInfo[0].BDCDYH,//不动产单元号
					"ZDDM":bdcdaxxcxInfo[0].ZDDM,//宗地代码
					"FWBM":bdcdaxxcxInfo[0].FWBM,//房屋编码
					"YWH":bdcdaxxcxInfo[0].YWH,//业务号
					"ZXYWH":bdcdaxxcxInfo[0].ZXYWH,//注销业务号
					"GLH":bdcdaxxcxInfo[0].GLH//关联号
				};	
				
				if(result.isPass == true){
					FlowUtil.initFormFieldValue(info,"T_BDC_HFCQDJ_FORM");		
					FlowUtil.initFormFieldValue(info,"page_1");
					
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

					formatDic("ZD_TZM", bdcdaxxcxInfo[0].ZDTZM); //宗地特征码

				}else{
					if(result.type=="0"){
						parent.art.dialog({
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}/*else{
						parent.art.dialog.confirm(result.msg, function() {
							FlowUtil.initFormFieldValue(info,"T_BDC_HFCQDJ_FORM");		
							FlowUtil.initFormFieldValue(info,"page_1");
							
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
						});
					}*/
				}
				art.dialog.removeData("bdcdydacxInfo");
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

/**
 * 权属来源
 * 不动产档案信息查询
 */
function showPageSelectorForQsly(){
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1", {
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
				var info = {
					"POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
					"POWERSOURCE_QSZT":bdcdaxxcxInfo[0].QSZT,
					"POWERSOURCE_ZJLX":bdcdaxxcxInfo[0].ZJLX,
					"POWERSOURCE_ZJHM":bdcdaxxcxInfo[0].ZJHM,
					"POWERSOURCE_ZDDM":bdcdaxxcxInfo[0].ZDDM,
					"POWERSOURCE_EFFECT_TIME":DateConvertFun(bdcdaxxcxInfo[0].QLQSSJ),//权利开始时间
					"POWERSOURCE_CLOSE_TIME1":DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ),//权利结束时间
					"POWERSOURCE_DYH":bdcdaxxcxInfo[0].BDCDYH,
					"POWERSOURCE_QSWH":bdcdaxxcxInfo[0].BDCQZH,//证书文号（权属来源）
					"POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
				};	
				if(result.isPass == true){
					$("#POWERSOURCE_QLRMC").val(info.POWERSOURCE_QLRMC);
					$("#POWERSOURCE_QSZT").val(info.POWERSOURCE_QSZT);
					$("#POWERSOURCE_ZJLX").val(info.POWERSOURCE_ZJLX);
					$("#POWERSOURCE_ZJHM").val(info.POWERSOURCE_ZJHM);
					$("#POWERSOURCE_ZDDM").val(info.POWERSOURCE_ZDDM);
					$("#POWERSOURCE_EFFECT_TIME").val(info.POWERSOURCE_EFFECT_TIME);
					$("#POWERSOURCE_CLOSE_TIME1").val(info.POWERSOURCE_CLOSE_TIME1);
					$("#POWERSOURCE_DYH").val(info.POWERSOURCE_DYH);
					$("#POWERSOURCE_QSWH").val(info.POWERSOURCE_QSWH);
					$("#POWERSOURCE_QSLYLX").val(info.POWERSOURCE_QSLYLX);
				}else{
					if(result.type=="0"){
						parent.art.dialog({
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}
				}
				art.dialog.removeData("bdcdydacxInfo");
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


/**
 * 换发预告登记证明
 * 不动产预告登记查询
 */
function showSelectBdcYgdacx(){
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcygdacxInfo && bdcygdacxInfo.length == 1){	
				//根据不动产单元号调取房屋单元信息查询接口	
		    	$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcygdacxInfo[0].BDCDYH},
					function(responseText, status, xhr) {
		    		if(responseText.total==1){
	    				var out=responseText.rows[0];
	    				//判断产权状态
	    				if((out.ZT.indexOf("注销")!=-1) || (out.ZT.indexOf("无效")!=-1)){
				    		art.dialog({
								lock: true,
								content: "请注意，该不动产单元号为"+out.ZT+"状态，不可受理此登记。",
								icon:"warning",
								ok: true
							});
	    				}else{
	    						$("input[name='ESTATE_NUM']").val(bdcygdacxInfo[0].BDCDYH);
							    $("input[name='LOCATED']").val(out.ZL);
							 	FlowUtil.initFormFieldValue(bdcygdacxInfo[0],"qsly3");	//权利来源
								FlowUtil.initFormFieldValue(bdcygdacxInfo[0],"jbxx");//基本信息(预告接口)	
								$("input[name='JBXX_QLR']").val(bdcygdacxInfo[0].QLR);
								$("input[name='JBXX_ZJZL']").val(bdcygdacxInfo[0].QLRZJZL);
								$("input[name='JBXX_ZJHM']").val(bdcygdacxInfo[0].QLRZJH);
								$("input[name='JBXX_YWR']").val(bdcygdacxInfo[0].YWR);
								$("input[name='JBXX_YWRZJLB']").val(bdcygdacxInfo[0].YWRZJZL);
								$("input[name='JBXX_YWRZJHM']").val(bdcygdacxInfo[0].YWRZJH);	
					    		FlowUtil.initFormFieldValue(out,"jbxx");//基本信息(档案接口)	
					    		$("input[name='JBXX_QDJG']").val(bdcygdacxInfo[0].QDJG);//取得价格
					       		$("input[name='FW_GHYT']").val(out.GHYT);//规划用途
					       		$("input[name='FDZL']").val(out.ZL);//房屋地址	
					       		$("input[name='JBXX_XMMC']").val(out.XMMC);//项目名称
					    		/*********不动产预告档案信息接口需回传字段**********/
					    		$("input[name='YWH']").val(bdcygdacxInfo[0].YWH);//业务号
					    		$("input[name='GLH']").val(bdcygdacxInfo[0].GLH);//关联号
	    				}
		    		}else if(responseText.total==0){
		    			parent.art.dialog({
							content: "该不动产单元号暂查无对应的房屋单元信息！",
							icon:"warning",
							ok: true
						});
		    		}else{
		    			parent.art.dialog({
							content: "该不动产单元号返回多条房屋单元信息，请检查！",
							icon:"warning",
							ok: true
						});
		    		}
		    	});
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
};

/**
 * 换发抵押权预告登记证明
 * 不动产预告登记查询
 */
function showPage4Selector(){
	$.dialog.open("bdcApplyController.do?bdcygdacxSelector&allowCount=1&noLike=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			var flag = true;
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var dyinfo = {};	
				var data = bdcdydacxInfo[0];
				dyinfo['ESTATE_NUM'] = data.BDCDYH;
				dyinfo['LOCATED'] = data.BDCZL;
				dyinfo['QS_DYH'] = data.BDCDYH;
				dyinfo['QS_CQZH'] = data.BDCDJZMH;
				dyinfo['QS_QLR_MC'] = data.QLR;
				dyinfo['QS_QSZT'] = data.QSZT;
				dyinfo['QLR_ZJLX'] = data.QLRZJZL;
				dyinfo['QS_QLR_ZJNO'] = data.QLRZJH;
				dyinfo['QS_YWR_MC'] = data.YWR;
				dyinfo['QS_YWR_ZJNO'] = data.YWRZJH;
				dyinfo['QS_ZL'] = data.BDCZL;
				dyinfo['QS_JZAREA'] = data.JZMJ;
				//dyinfo['QS_DYHTH'] = data.;//抵押合同号
				dyinfo['QS_DBSE'] = data.QDJG;
				dyinfo['QS_BEGIN'] = DateConvertFun(data.QSSJ);
				dyinfo['QS_END'] = DateConvertFun(data.JSSJ);
				dyinfo['QLR_MC'] = data.QLR;
				dyinfo['QLR_ZJLX'] = data.QLRZJZL;
				dyinfo['QLR_ZJNO'] = data.QLRZJH;
				dyinfo['YWR_MC'] = data.YWR;
				dyinfo['YWR_ZJLX'] = data.YWRZJZL;
				dyinfo['YWR_ZJNO'] = data.YWRZJH;
				dyinfo['ZWR_MC'] = data.ZWR;
				dyinfo['ZWR_ZJLX'] = data.ZWRZJZL;
				dyinfo['ZWR_ZJNO'] = data.ZWRZJH;
				//dyinfo['HT_NO'] = data.;//预售合同号
				//dyinfo['HT_LX'] = data.HTLX;
				dyinfo['DBSE'] = data.QDJG;
				dyinfo['ZW_BEGIN'] = DateConvertFun(data.QSSJ);
				dyinfo['ZW_END'] = DateConvertFun(data.JSSJ);
				dyinfo['FW_ADDR'] = data.BDCZL;
				dyinfo['FJ_INFO'] = data.FJ;
				/*****不动产预告信息接口(第一条记录)回传字段 *****/
				dyinfo['BDCDYH'] = data.BDCDYH;
				dyinfo['YWH'] = data.YWH;	
				dyinfo['GLH'] = data.GLH;	
				
			/*	dyinfo['DJXX_DYH'] = data.BDCDYH;
				dyinfo['DJXX_CQZH'] = data.BDCDJZMH;
				dyinfo['DJXX_QLR'] = data.QLR;
				dyinfo['DJXX_JZAREA'] = data.JZMJ;
				dyinfo['DJXX_ZL'] = data.BDCZL;
				*//*****不动产预告信息接口(第二条记录)回传字段 *****//*	
				dyinfo['YG_YWH'] = data.YWH;
				dyinfo['YG_GLH'] = data.GLH; 
			*/	
				//检查房屋状态
				if(dyinfo.ESTATE_NUM){
					var result = checkFwCqzt(dyinfo.ESTATE_NUM);
					if(result.isPass == true){
						FlowUtil.initFormFieldValue(dyinfo,"T_BDC_HFCQDJ_FORM");		
						FlowUtil.initFormFieldValue(dyinfo,"page_4");	
						//回填权利人
						if(dyinfo.QLR_MC){
							$("#QLR_MC").combobox("setValue",dyinfo.QLR_MC);
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
				}
				art.dialog.removeData("bdcygdacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条预告信息！",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
};

//检验是不动产单元号对应的房屋状态
function checkFwCqzt(dyh){
	var result = {
		"isPass":true 
	};
	$.ajax({
        type: "POST",
        url: "bdcApplyController/bdcFwztCheck.do?bdcdyh="+dyh,
        async: false, //采用同步方式进行数据判断
        cache: false,
        success: function (responseText) {
        	var resultJson = $.parseJSON(responseText);
        	if(resultJson.success){
				var list = resultJson.data;			
				if(list != null && list.length > 0){
					//CQZT
					for(var i=0;i<list.length;i++){
						var cqzt = list[i].CQZT.trim();
						if((cqzt.indexOf("注销") != -1)|| (cqzt.indexOf("无效") != -1)){
							result = {
								"isPass":false,
								"msg":"请注意，该不动产单元号为"+cqzt+"状态，不可受理此登记。",
								"type":"0"
							};
							break ;
						}
						/*result = {
							"isPass":false,
							"msg":"请注意，该不动产单元号为"+cqzt+"状态，是否继续办理业务。",
							"type":"1"
						};*/
					}
			   }else{
				   result = {
						"isPass":false,
						"msg":"暂查无该房屋状态信息,无法查验该房屋状态,不可继续办理业务。",
						"type":"0"
					};
			   }
		   }else{
			   result = {
					"isPass":false,
					"msg":resultJson.msg,
					"type":"0"
				}; 
		   }
        }
    });
	return result;
}

/**=================权利人信息开始================================*/
var powerPeopleInfoSn1 = 1;
function addPowerPeopleInf1(){
	powerPeopleInfoSn1 = powerPeopleInfoSn1+1;
	var table = document.getElementById("powerPeopleInf1");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerPeopleInf1('");
	if(powerPeopleInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerPeopleInf1('"+powerPeopleInfoSn1+"')");
	trHtml = "<tr id=\"powerPeopleInf1_"+powerPeopleInfoSn1+"\">"+trHtml+"</tr>";
	$("#powerPeopleInf1").append(trHtml);
}

function deletePowerPeopleInf1(idSn){
	var table = document.getElementById("powerPeopleInf1");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerPeopleInf1_"+idSn).remove();
}

function getPowerPeopleInfoJson1(){
	var datas = [];
	var trs = $("#powerPeopleInf1 tr[id*='powerPeopleInf1_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("#POWERPEOPLEINFO_JSON").val(JSON.stringify(datas));
}
/**=================权利人信息开始================================*/
/**=================权属来源信息开始================================*/
var powerSourceInfoSn = 1;
function addPowerSourceInfo(){
	powerSourceInfoSn = powerSourceInfoSn+1;
	var table = document.getElementById("powerSourceInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerSourceInfo('");
	if(powerSourceInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerSourceInfo('"+powerSourceInfoSn+"')");
	trHtml = "<tr id=\"powerSourceInfo_"+powerSourceInfoSn+"\">"+trHtml+"</tr>";
	$("#powerSourceInfo").append(trHtml);
}

function deletePowerSourceInfo(idSn){
	var table = document.getElementById("powerSourceInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权属来源信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerSourceInfo_"+idSn).remove();
}

function getPowerSourceInfoJson(){
	var datas = [];
	var trs = $("#powerSourceInfo tr[id*='powerSourceInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
}


function getPowerSourceInfoJson1(){
	var datas = [];
	for(var j=0;j<$(".powersourceinfo_tr").length;j++){
		loadPowSourceInfo($("#djxxItem"+(j+1)),0)
		var trs = $("#powerSourceInfo tr[id*='powerSourceInfo_']");
		for(var i=0;i<trs.length;i++){
			var id = $(trs[i]).attr("id");
			var trData = {};
			$("#"+id).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			datas.push(trData);
		}
	}
	$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/

function initAutoTable(){
	var hfvalue = $("input[name='HF_TYPE']:checked").val();
	if(hfvalue == "1"){
		initBdcHfJsonField();
	}else if(hfvalue == "2"){
		initDyYgDjHfField();
	}else if(hfvalue == "3"){
		initYgDjHfField();
	}else if(hfvalue == "4"){
		initPage4field();
	}
}

/*换发预告登记证明默认值开始*/
function initYgDjHfField(){
	//设置权利人、义务人证件种类默认值
	$("select[name='JBXX_ZJZL']").val("身份证");
	$("select[name='JBXX_YWRZJLB']").val("身份证");
	
	//设置合同类型默认值为'预售合同'、规划用途默认值为'住宅'、房屋性质默认值为'商品房'
	$("select[name='HTLX']").val("预售合同");
	$("select[name='FW_GHYT']").val("住宅");
	$("select[name='FWXZ']").val("市场化商品房");
	
	//设置字段不可修改	
	$("#qsly3 input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#qsly3 select").each(function(index){
		$(this).attr("disabled","disabled");
	});	
	$('#qsly3').find(".Wdate").attr('disabled',"disabled");
	
	$("select[name='HTLX']").attr("disabled","disabled");	
	$("select[name='FW_GHYT']").attr("disabled","disabled");	
	$("select[name='FWXZ']").attr("disabled","disabled");	
}

/*换发抵押权证明登记默认值开始*/
function initDyYgDjHfField(){
	
	//设置权利人中抵押权人、法定代表人、代理人证件类型,基本信息中抵押人证件类型
	$("select[name='QLR_DYQRZJLX']").val("统一社会信用代码");
	$("select[name='QLR_LEGALZJLX']").val("身份证");
	$("select[name='QLR_DLRZJLX']").val("身份证");
	$("select[name='DYRZJLX']").val("身份证");
	
	//最高抵押额默认为否,抵押方式默认显示'一般抵押'且最高债权数额不可编辑,登记原因默认显示'变更登记',抵押宗数默人为'1'
	$("select[name='SFZGEDY']").val("0");
	$("input[name='DYFS']").val("一般抵押");
	$("input[name='DJYY']").val("变更登记");
	$("input[name='DYZS']").val("1");
	$("input[name='ZGZQSE']").attr("disabled",true);
	
	
	//设置字段不可修改	
	$("#qslyInfo input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#qslyInfo select").each(function(index){
		$(this).attr("disabled","disabled");
	});	
	$('#qslyInfo').find(".Wdate").attr('disabled',"disabled");
	
	$("#jbxxInfo input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#jbxxInfo select").each(function(index){
		$(this).attr("disabled","disabled");
	});	
	$('#jbxxInfo').find(".Wdate").attr('disabled',"disabled");
	
	$("input[name='JBXX_DYR']").attr('readonly',false);
	$("select[name='DYRZJLX']").attr('disabled',false);
	$("input[name='DYRZJHM']").attr('readonly',false);
	
	$("#daInfo input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	
	var powerpeopleinfoJson = $("#POWERPEOPLEINFO_JSON2").val();
	if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson){
		var powerpeopleinfos = eval(powerpeopleinfoJson);
		for(var i=0;i<powerpeopleinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_1");
			}else{
				addPowerPeopleInfo();
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_"+(i+1));
			}
		}
	}
}

/*换发抵押权预告登记证明*/
function initPage4field(){
	$("select[name='QLR_ZJLX']").val("统一社会信用代码");
	//$("select[name='DLR_ZJLX']").val("SF");
	$("select[name='YWR_ZJLX']").val("YYZZ");
	//$("select[name='DLR2_ZJLX']").val("SF");
	$("select[name='ZWR_ZJLX']").val("SF");
	
	//设置字段不可修改
	$("#qsly input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#qsly select").each(function(index){
		$(this).attr("disabled","disabled");
	});	
	$('#qsly').find(".Wdate").attr('disabled',"disabled");

	$('#ysdyqyg').find(".Wdate").attr('disabled',"disabled");
}

/*回填换发产权证书中的权利人和权属来源信息字段*/
function initBdcHfJsonField(){
	var powerpeopleinfoJson = $("#POWERPEOPLEINFO_JSON").val();
	if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson){
		var powerpeopleinfos = eval(powerpeopleinfoJson);
		for(var i=0;i<powerpeopleinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInf1_1");
			}else{
				addPowerPeopleInf1();
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInf1_"+(i+1));
			}
		}
	}
	var powersourceinfoJson = $("#POWERSOURCEINFO_JSON").val();
	if(null != powersourceinfoJson && '' != powersourceinfoJson){
		var powersourceinfos = eval(powersourceinfoJson);
		for(var i=0;i<powersourceinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_1");
			}else{
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
			        if($("#powerSourceInfoList_blank_tr")){
			            $("#powerSourceInfoList_blank_tr").remove();
			        }
					for(var i=0;i<powersourceinfos.length;i++){
						var index = i+1;
			            appendPowSourceRow(powersourceinfos[i],index,null);
					}
				} else{
					addPowerSourceInfo();
				}
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_"+(i+1));
			}
		}
	}
}

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}


/**=================换发抵押权证明登记开始================================*/


//新增权利人信息
var powerPeopleInfoSn = 1;
function addPowerPeopleInfo(){
	powerPeopleInfoSn = powerPeopleInfoSn+1;
	var table = document.getElementById("powerPeopleInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerPeopleInfo('");
	if(powerPeopleInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerPeopleInfo('"+powerPeopleInfoSn+"')");
	trHtml = "<tr id=\"powerPeopleInfo_"+powerPeopleInfoSn+"\">"+trHtml+"</tr>";
	$("#powerPeopleInfo").append(trHtml);
}

//删除权利人信息
function deletePowerPeopleInfo(idSn){
	var table = document.getElementById("powerPeopleInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerPeopleInfo_"+idSn).remove();
}

/*获取权力人信息*/
function getPowerPeopleInfoJson(){
	var datas = [];
	var trs = $("#powerPeopleInfo tr[id*='powerPeopleInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("#POWERPEOPLEINFO_JSON2").val(JSON.stringify(datas));
}

//设置是否为最高额抵押
function setSfzgedy(value){
	if (value === "1") {	
		$("input[name='ZGZQSE']").attr("disabled",false);
		$("input[name='BDBZZQSE']").attr("disabled",true);
		$("input[name='DYFS']").val("最高额抵押");		
	}else{		
		$("input[name='ZGZQSE']").attr("disabled",true); 
		$("input[name='BDBZZQSE']").attr("disabled",false);
		$("input[name='DYFS']").val("一般抵押");
	}
}
/** 换发抵押权登记证明
 * 不动产抵押档案查询 
 */
function showSelectBdcdydacx(){
	var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();	
	$.dialog.open("bdcDyqzxdjController.do?selector&allowCount=1&negativeListCodes="+negativeListCodes, {
		title : "不动产抵押档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
		    var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
		    if(bdcdydacxInfo&& bdcdydacxInfo.length==1){
		    	//根据不动产单元号调取房屋单元信息查询接口	
		    	$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcdydacxInfo[0].BDCDYH},
					function(responseText, status, xhr) {
		    		if(responseText.total==1){
		    			var list=responseText.rows;
		    			if(list!=null && list.length>0){
		    				var out=list[0];
		    				//判断产权状态
		    				if((out.ZT.indexOf("注销")!=-1) || (out.ZT.indexOf("无效")!=-1)){
					    		art.dialog({
									lock: true,
									content: "请注意，该不动产单元号为"+out.ZT+"状态，不可受理此登记。",
									icon:"warning",
									ok: true
								});
		    				}else{
		    					 	$("input[name='LOCATED']").val(out.ZL);
								 	FlowUtil.initFormFieldValue(bdcdydacxInfo[0],"qslyInfo");//权属来源
						    		FlowUtil.initFormFieldValue(bdcdydacxInfo[0],"jbxxInfo");//基本信息
						    		FlowUtil.initFormFieldValue(bdcdydacxInfo[0],"daInfo");//档案信息
						    		$("input[name='JBXX_DYR']").val(bdcdydacxInfo[0].DYR);	
						    		$("input[name='DAXX_BDCDYH']").val(bdcdydacxInfo[0].BDCDYH);
						    		$("input[name='DAXX_BDCQZH']").val(bdcdydacxInfo[0].BDCQZH);
						    		$("input[name='DAXX_QSZT']").val(bdcdydacxInfo[0].QSZT);
						    		$("input[name='CQZT']").val(out.ZT);//产权状态
						    		$("input[name='FDZL']").val(out.ZL);//房地坐落
						    		$("input[name='JZMJ']").val(out.JZMJ);//面积	
						    		
						    		/********不动产抵押信息接口需回传字段*******/
									$("input[name='YWH']").val(bdcdydacxInfo[0].YWH);//业务号
									$("input[name='DYCODE']").val(bdcdydacxInfo[0].DYCODE);//抵押编号
									$("input[name='GLH']").val(bdcdydacxInfo[0].GLH);//关联号
		    				}
		    			}
		    		}else if(responseText.total==0){
		    			art.dialog({
							lock: true,
							content: "暂查无对应的房屋单元信息。",
							icon:"warning",
							ok: true
						});
		    		}else{
		    			art.dialog({
							lock: true,
							content: "房屋单元信息返回多条记录，请检查!",
							icon:"warning",
							ok: true
						});
		    		}
		    		
		    	});
		    	art.dialog.removeData("bdcdydacxInfo");	
		    }else{
		    	parent.art.dialog({
					content: "请根据查询选择一条抵押信息！",
					icon:"warning",
					ok: true
				});
		    }    		
		}
	}, false);
};

//不动产档案信息查询
/*function showSelectBdcdaxxcx(){	
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if(bdcdaxxcxInfo){
				for(var i = 0;i<bdcdaxxcxInfo.length;i++){
					if((bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("限制")!=-1) || (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("查封")!=-1)
						|| (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("预查封")!=-1) || (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("注销")!=-1)
						|| (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("查封办文中")!=-1) || (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("无效")!=-1)
						|| (bdcdaxxcxInfo[i].POWERSOURCE_CQZT.indexOf("异议")!=-1)){
						art.dialog({
							lock: true,
							content: "请注意，该不动产单元号为"+bdcdaxxcxInfo[i].POWERSOURCE_CQZT+"状态，不可受理此登记。",
							icon:"warning",
							ok: true
						});
					}else{
						var bdcdaxxcx=bdcdaxxcxInfo[i];
						art.dialog.confirm("请注意，该不动产单元号为"+bdcdaxxcxInfo[i].POWERSOURCE_CQZT+"状态，是否继续办理业务?", function() {
							$("input[name='CQZT']").val(bdcdaxxcx.POWERSOURCE_CQZT);//产权状态
							$("input[name='QLRMC']").val(bdcdaxxcx.POWERSOURCE_QLRMC);//不动产权利人
							$("input[name='ZJLX']").val(bdcdaxxcx.POWERSOURCE_ZJLX);//证件类型
							$("input[name='DAXX_ZJHM']").val(bdcdaxxcx.POWERSOURCE_ZJHM);//证件号码
							$("input[name='FDZL']").val(bdcdaxxcx.FDZL);//房地坐落
							$("input[name='JZMJ']").val(bdcdaxxcx.ZJMJ);//面积	
						});  
					}
				}
					
			}	
			art.dialog.removeData("bdcdaxxcxInfo");
		}
	}, false);
};*/


/**=================换发抵押权证明登记结束================================*/



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

		function QLR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				//$("input[name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
				$("input[name='QLR_ZJNO']").val(GT2ICROCX.CardNo);
			}
		} ;
		function DLR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='DLR_MC']").val(GT2ICROCX.Name);
				$("input[name='DLR_ZJNO']").val(GT2ICROCX.CardNo);
			}
		} ;
		
		function YWR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='YWR_MC']").val(GT2ICROCX.Name);
				$("input[name='YWR_ZJNO']").val(GT2ICROCX.CardNo);
			}
		};
		
		function DLR2_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='DLR2_MC']").val(GT2ICROCX.Name);
				$("input[name='DLR2_ZJNO']").val(GT2ICROCX.CardNo);
			}
		}; 
		function ZWR_Read(o)//开始读卡
		{  		
			GT2ICROCX.PhotoPath = "c:"
				//GT2ICROCX.Start() //循环读卡
				//单次读卡(点击一次读一次)
				if (GT2ICROCX.GetState() == 0){
					GT2ICROCX.ReadCard();
					$("input[name='ZWR_MC']").val(GT2ICROCX.Name);
					$("input[name='ZWR_ZJNO']").val(GT2ICROCX.CardNo);
				}
		}; 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
		
		/**=================换发产权证书-身份证读卡开始================================*/
		function PowerPeopleRead(thiz){//开始读卡（权利人）
			GT2ICROCX.PhotoPath = "c:"
				//GT2ICROCX.Start() //循环读卡
				//单次读卡(点击一次读一次)
				if (GT2ICROCX.GetState() == 0){
					GT2ICROCX.ReadCard();
					var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");
					$("#"+id+" input[name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
					$("#"+id+" input[name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
				}
		}
		
		function PowLegalRead(thiz){//开始读卡（法定代表人）
			GT2ICROCX.PhotoPath = "c:"
				//GT2ICROCX.Start() //循环读卡
				//单次读卡(点击一次读一次)
				if (GT2ICROCX.GetState() == 0){
					GT2ICROCX.ReadCard();
					var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");	
					$("#"+id+" input[name='POWLEGALNAME']").val(GT2ICROCX.Name);
					$("#"+id+" input[name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
				}
		}
		
		function PowAgentRead(thiz){
			GT2ICROCX.PhotoPath = "c:"
				//GT2ICROCX.Start() //循环读卡
				//单次读卡(点击一次读一次)
				if (GT2ICROCX.GetState() == 0){
					GT2ICROCX.ReadCard();
					var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");	
					$("#"+id+" input[name='POWAGENTNAME']").val(GT2ICROCX.Name);
					$("#"+id+" input[name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
				}
		}	
		/**=================换发产权证书-身份证读卡结束================================*/
		
		
		/**=================换发抵押权证明登记-身份证读卡开始================================*/
		function QLR1_Read(thiz)//开始读卡（抵押权人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");	
				$("#"+id+" input[name='QLR_DYQL']").val(GT2ICROCX.Name);
				$("#"+id+" input[name='QLR_DYQRZJHM']").val(GT2ICROCX.CardNo);
			}
		} 

		function QLR2_Read(thiz)//开始读卡（法定代表人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");
				$("#"+id+" input[name='QLR_LEGALNAME']").val(GT2ICROCX.Name);
				$("#"+id+" input[name='QLR_LEGALZJHM']").val(GT2ICROCX.CardNo);
			}
		} 

		function QLR3_Read(thiz)//开始读卡（代理人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				var id = $(thiz).parent().parent().parent().parent().parent().parent().attr("id");
				$("#"+id+" input[name='QLR_DLRNAME']").val(GT2ICROCX.Name);
				$("#"+id+" input[name='QLR_DLRZJHM']").val(GT2ICROCX.CardNo);
			}
		} 
		/**=================换发抵押权证明登记- 身份证读卡结束=============================*/

		/**=================换发预告登记证明-身份证读卡开始================================*/
		function JbxxQlrRead()//开始读卡（权利人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='JBXX_QLR']").val(GT2ICROCX.Name);
				$("input[name='JBXX_ZJHM']").val(GT2ICROCX.CardNo);
			}
		}

		function JbxxLzrRead()//开始读卡（代理人（领证人））
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='JBXX_LZR']").val(GT2ICROCX.Name);
				$("input[name='JBXX_LZRZJHM']").val(GT2ICROCX.CardNo);
			}
		}

		function JbxxYwrRead()//开始读卡（义务人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='JBXX_YWR']").val(GT2ICROCX.Name);
				$("input[name='JBXX_YWRZJHM']").val(GT2ICROCX.CardNo);
			}
		}

		function JbxxDlrRead()//开始读卡（代理人）
		{  		
//				alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("input[name='JBXX_DLR']").val(GT2ICROCX.Name);
				$("input[name='JBXX_DLRZJHM']").val(GT2ICROCX.CardNo);
			}
		}
		/**=================换发预告登记证明-身份证读卡结束==========================*/
		
//------------------------------------身份身份证读取结束---------------------------------------------------
		
		
function newDicInfoWin(typeCode,combId){
	$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
		title : "新增",
        width:"600px",
        lock: true,
        resize:false,
        height:"180px",
        close: function(){
			$("#"+combId).combobox("reload");
		}
	}, false);
}

function removeDicInfo(combId){
	var datas = $("#"+combId).combobox("getData");
	var val = $("#"+combId).combobox("getValue");
	var id = "";
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME==val){
			id = datas[i].DIC_ID;
			break;
		}
	}
	art.dialog.confirm("您确定要删除该记录吗?", function() {
		var layload = layer.load('正在提交请求中…');
		$.post("dictionaryController.do?multiDel",{
			   selectColNames:id
		    }, function(responseText, status, xhr) {
		    	layer.close(layload);
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					$("#"+combId).combobox("reload");
					$("#"+combId).combobox("setValue","");
				}else{
					$("#"+combId).combobox("reload");
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
		});
	});
}