

function FLOW_SubmitFun(flowSubmitObj) {
	//先判断表单是否验证通过
	var validateResult = $("#T_BDCQLC_GYJSYDJFWSYQ_FORM").validationEngine("validate");
	if (validateResult) {

		// 获得权利人信息
		getPowPeopleJson();
		// 获得权属来源信息
		getPowSourceJson();
		// 获取发证明细数据
		getDjfzxxJson_scdj();
		// 获取缴费信息
		getDjjfxxJson_scdj();

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

		//登簿的环节需完成登簿才可提交
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

		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
		if (submitMaterFileJson || submitMaterFileJson == "") {
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			//先获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSYDJFWSYQ_FORM");
			for (var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
			/*宗地用途特殊获取*/
			var ZD_TDYT = $("#ZD_TDYT").combobox("getValues");
			if (ZD_TDYT) {
				flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
			}
			return flowSubmitObj;
		} else {
			return null;
		}

	} else {
		return null;
	}
}
function FLOW_TempSaveFun(flowSubmitObj) {
	// 获得权利人信息
	getPowPeopleJson();
	// 获得权属来源信息
	getPowSourceJson();
	// 获取发证明细数据
	getDjfzxxJson_scdj();
	// 获取缴费信息
	getDjjfxxJson_scdj();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSYDJFWSYQ_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	/*宗地用途特殊获取*/
	var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
	if (ZD_TDYT) {
		flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
	}
	return flowSubmitObj;
}

function FLOW_BackFun(flowSubmitObj) {
	return flowSubmitObj;
}

function queryTypeFn() {
	var val = $("input[name='SYQ_TYPE']:checked").val();
	if (val == '1') {
		$("#bdc_zqqxx").hide();
		$("#bdc_tjmx").hide();
		$("#jzwqf").hide();
		$("#zdjbxx").show();
		$("#gyqlxx").show();
		$("#fwjbxx").show();
	} else if (val == '2') {
		$("#bdc_zqqxx").show();
		$("#bdc_tjmx").show();
		$("#jzwqf").show();
		$("#zdjbxx").hide();
		$("#gyqlxx").hide();
		$("#fwjbxx").hide();
	}
}

/*登簿后按钮无法点击*/
function disabledDbBtn(id) {
	var bdcDbzt = $("input[name='BDC_DBZT']").val();
	if (bdcDbzt == '1') {
		$("#" + id).attr("disabled", "disabled").addClass("eflowbutton-disabled").attr("style", "pointer-events: none;");
	}else{
		$("#" + id).attr("disabled", false).removeClass("eflowbutton-disabled");
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


//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
	var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
	if(BDC_QZBSM){
		// typeId: 1为证书预览  2为证书打印
		var title = "证书打印";
		var templateAlias = 'BDCQZ';
		if(typeId==1) {
			title = "证书预览";
		}
		var dataStr = "";
		var QLRZJH = getQlrMc()
		dataStr += "&QLRZJH=" + (QLRZJH == "" ? QLRZJH : QLRZJH.substring(0, QLRZJH.length - 1));
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
	}else{
		art.dialog({
			content :"请先填写权证标识码。",
			icon : "warning",
			ok : true
		});
	}
}

function getQlrMc() {
	var flowSubmitObj = FlowUtil.getFlowObj();
	var POWERPEOPLEINFO_JSON = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON
	var QLRZJH = "";
	if (POWERPEOPLEINFO_JSON) {
		var powParse = JSON.parse(POWERPEOPLEINFO_JSON);
		for (let i = 0; i < powParse.length; i++) {
			QLRZJH += powParse[i].POWERPEOPLEIDNUM + ",";
		}
	}
	return QLRZJH;
}

/*
* 不动产登簿操作
* */
function BDCQLC_bdcdbcz(){
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeId = flowSubmitObj.EFLOW_EXEID;
	if(exeId != null && typeof exeId != undefined){
		art.dialog.confirm("您确定要进行登簿吗?", function() {
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
							var qlrs = JSON.parse(flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON);
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
								initQlrs(qlrs);
								if(iflag != qlrs.length){
									$("input[name='BDC_DBZT']").val("0");
									$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
									art.dialog({
										content :"归档失败，存在与受理时的权利不一致的情况。",
										icon : "warning",
										ok : true
									});
								}else{
									$("input[name='BDC_DBZT']").val("1");
									$("input[name='BDC_DBJG']").val("登簿成功");
									art.dialog({
										content :"登簿成功",
										icon : "succeed",
										ok : true
									});
								}
							}else{
								$("input[name='BDC_DBZT']").val("0");
								$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
								art.dialog({
									content :"归档失败，与受理时的权利人数不匹配。",
									icon : "warning",
									ok : true
								});
							}
						}
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
		});
	}
}

function initQlrs(POWERPEOPLEINFO_JSON) {
	$("#PowerPeopleTable .bdcdydacxTr").remove();
	if (null != POWERPEOPLEINFO_JSON && '' != POWERPEOPLEINFO_JSON) {
		var POWERPEOPLEINFO_JSONS = eval(POWERPEOPLEINFO_JSON);
		for (var i = 0; i < POWERPEOPLEINFO_JSONS.length; i++) {
			// 追加权力人明细行数据
			addPowerTableItemHtml(PowerPeopleTableId, PowerPeopleListTableId, POWERPEOPLEINFO_JSONS[i], PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexItemName);
		}
		// 默认展示第一条权力人明细数据
		loadTableTr($("#" + PowerPeopleListTableId + " tr")[1], PowerPeopleTableId);
	}
}

function initDbxx(qzinfos,dialog){
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var datas =  [];
	$("input[name='PowerPeopleTableTrMx']").each(function() {
		var trData = {};
		var inputName = $(this).attr("name");
		var inputValue = $(this).val();
		trData = JSON.parse(inputValue);
		trData[inputName] = inputValue;
		datas.push(trData);
	});
	if(qzinfos.length == datas.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<datas.length;j++){
					if(qzinfos[i].qlr_mc == datas[j].POWERPEOPLENAME
						&& qzinfos[i].qlr_zh == datas[j].POWERPEOPLEIDNUM){
						datas[j].BDC_SZZH = qzinfos[i].qzzh;
						var PowerPeopleTableTrMx =JSON.parse(datas[j].PowerPeopleTableTrMx);
						PowerPeopleTableTrMx.BDC_SZZH = qzinfos[i].qzzh;
						datas[j].PowerPeopleTableTrMx = PowerPeopleTableTrMx;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(datas);
		if(iflag != datas.length){
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

//保存权证标识码的操作
function saveQzbsm(){
	var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
	if(BDC_QZBSM){
		var currentUser = $("input[name='uploadUserName']").val();
		var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
		$("input[name='BDC_CZR']").val(currentUser);
		$("input[name='BDC_CZSJ']").val(currentTime);
		saveQzbsmToUpdateTable('PowerPeopleInfo');
		// 获得权利人信息
		getPowerPeopleInfoJson();
		//并完成暂存
		BdcQzPrintUtil.BDC_jffzTempSavePageFun();
	}else{
		parent.art.dialog({
			content : "请填写权证标识码。",
			icon : "warning",
			ok : true
		});
	}
}

/*不动产档案信息查询*/
function showSelectBdcDaxx() {
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
}

function showSelectBdcdaxxcx_TBG() {
	$.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
		title : "不动产档案信息查询",
		width : "100%",
		lock : true,
		resize : false,
		height : "100%",
		close : function() {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo) {
				var data=bdcdaxxcxInfo[0];
				var info = daxxFillInfoQslyJson(data)
				initPowSource(info);
				art.dialog.removeData("bdcdaxxcxInfo");
			}
		}
	}, false);
}

function daxxFillInfoQslyJson(data) {
	var items = [];
	var info = {
		"POWERSOURCEIDNUM":data.BDCQZH,
		"BDC_BDCDYH":data.BDCDYH,
		"POWERSOURCEIDTYPE":data.QSZT,
		"QLR":data.QLRMC,
		"BDC_ZDDM":data.ZDDM,
		"BDC_QLRZJLX":data.ZJLX,
		"BDC_QLRZJHM":data.ZJHM,
		"BDC_QLKSSJ":data.QLQSSJ,
		"BDC_QLJSSJ1":data.QLJSSJ,
		"BDC_QLJSSJ2":data.QLJSSJ1,
		"POWERSOURCENATURE":3
	};
	items.push(info);
	return items;
}

function DateConvertFun(str){
	var time = "";
	if(str){
		time=str;
	}
	return time;
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
	FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSYDJFWSYQ_FORM");

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

function showZMX() {
	$.dialog.open("bdcDyqscdjController.do?bdczmxSelector&allowCount=0", {
		title : "幢明细查询",
		width : "100%",
		lock : true,
		resize : false,
		height : "100%",
		close : function() {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo) {
				var html = "";
				for (var i = 0; i < bdcdaxxcxInfo.length; i++) {
					html += '<tr class="bdcdaxxcxTr" id="bdcdaxxcxTr_' + bdcdaxxcxTr + '">';
					html += '<input type="hidden" name="bdcdaxxcx"/>';
					html += '<td style="text-align: center;">' + bdcdaxxcxTr + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCDYH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLRMC + '</td>';
					html += '<td>不动产权证书</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QSZT + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLQSSJ + '</td>';
					html += '<td>';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delTableTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html += '</tr>';
					$("#PowerSourceTable").append(html);
					$("#bdcdaxxcxTr_" + bdcdaxxcxTr + " input[name='bdcdaxxcx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
					html="";
					bdcdaxxcxTr++;
				}
				var data=bdcdaxxcxInfo[0];
				data.ESTATE_NUM=data.BDCDYH,
					data.LOCATED=data.FDZL,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					FlowUtil.initFormFieldValue(data,"T_BDCQLC_GYJSYDJFWSYQ_FORM");
				art.dialog.removeData("bdcdaxxcxInfo");
			}
		}
	}, false);
}
function showTJMX() {
	$.dialog.open("bdcDyqscdjController.do?bdctjmxSelector&allowCount=0", {
		title : "幢明细查询",
		width : "100%",
		lock : true,
		resize : false,
		height : "100%",
		close : function() {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo) {
				var html = "";
				for (var i = 0; i < bdcdaxxcxInfo.length; i++) {
					html += '<tr class="bdcdaxxcxTr" id="bdcdaxxcxTr_' + bdcdaxxcxTr + '">';
					html += '<input type="hidden" name="bdcdaxxcx"/>';
					html += '<td style="text-align: center;">' + bdcdaxxcxTr + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCDYH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLRMC + '</td>';
					html += '<td>不动产权证书</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QSZT + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLQSSJ + '</td>';
					html += '<td>';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delTableTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html += '</tr>';
					$("#PowerSourceTable").append(html);
					$("#bdcdaxxcxTr_" + bdcdaxxcxTr + " input[name='bdcdaxxcx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
					html="";
					bdcdaxxcxTr++;
				}
				var data=bdcdaxxcxInfo[0];
				data.ESTATE_NUM=data.BDCDYH,
					data.LOCATED=data.FDZL,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					FlowUtil.initFormFieldValue(data,"T_BDCQLC_GYJSYDJFWSYQ_FORM");
				art.dialog.removeData("bdcdaxxcxInfo");
			}
		}
	}, false);
}

function initBeforForm() {
	$("input[name='JBR_NAME']").removeAttr('readonly');
	$("input[name='JBR_ZJHM']").removeAttr('readonly');

	$("#djjfxx_scdj").attr("style","display:none;");
	$("#djfzxx_scdj").attr("style","display:none;");
	$("#djgdxx_scdj").attr("style","display:none;");

	/*宗地房屋信息登记原因和附记事件修改*/
	$("#zddjyy").attr("onclick", "AppUtil.cyyjmbSelector('gyjsydjfwsyqscdj_zdyy','GYTD_DJYY');");
	$("#zdfj").attr("onclick","bdcCyyjmbSelector('gyjsydjfwsyqscdj_zdfj','GYTD_FJ','textarea');")
	$("#fwdjyy").attr("onclick", "AppUtil.cyyjmbSelector('gyjsydjfwsyqscdj_fwyy','FW_DJYY');");
	$("#fwfj").attr("onclick", "bdcCyyjmbSelector('gyjsydjfwsyqscdj_fwfj','FW_FJ','textarea');");
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

	var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
	if(null != djfzxx_json && '' != djfzxx_json){
		var djfzxx_jsonItems = JSON.parse(djfzxx_json);
		initDjfzxx_scdj(djfzxx_jsonItems);
	}
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_scdj(djjfmx_jsonItems);
	}
}

function setQSLYLX(value){
	if(value == "3"){
		$("#bdcdacxButton").show();
		$("#addOrSaveButton").hide();
		$("#qslyxx input").each(function(index) {
			$(this).attr("disabled", "disabled");
		});
		$("#qslyxx select").each(function(index) {
			$(this).attr("disabled", "disabled");
		});
		//权属来源类型都是可编辑状态
		$('#POWERSOURCENATURE').attr("disabled",false);
	}else{;
		$("#bdcdacxButton").hide();
		$("#addOrSaveButton").show();
		$("#qslyxx input").each(function(index) {
			$(this).attr("disabled", false);
		});
		$("#qslyxx select").each(function(index) {
			$(this).attr("disabled", false);
		});
	}
}

/*初始化登记审核信息*/
function initDjshForm() {
//若从后台收费发证功能进入
	var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	if(BDC_OPTYPE != null && BDC_OPTYPE !=""){
		$("#T_BDCQLC_GYJSYDJFWSYQ_FORM input").each(function(index){
			$(this).attr("disabled","disabled");
		});
		$("#T_BDCQLC_GYJSYDJFWSYQ_FORM select").each(function(index){
			$(this).attr("disabled","disabled");
		});
		//填写权证标识码
		if(BDC_OPTYPE == "1"){
			$("#bdcqzh_tr").attr("style","");
			$("#BDC_DBBTN").remove();
			$("#bdcqzbsm_tr").attr("style","");
			$("#bdcczr_tr").attr("style","");
			$("#dy_bdcqzbsm_tr").attr("style","");
			//权证打印按钮
			$("#BDC_QZVIEW").attr("style","");
			$("#BDC_QZPRINT").attr("style","");
			$("#BDC_QZVIEW").removeAttr("disabled");
			$("#BDC_QZPRINT").removeAttr("disabled");
			//
			$("input[name='BDC_QZBSM']").removeAttr("disabled");
			$("input[name='BDC_QZBSM']").addClass(" validate[required]");
			$("#qzbsmsavebtn").attr("style","");
			$("#qzbsmsavebtn").removeAttr("disabled");

			/*隐藏登记缴费和登记发证信息*/
			$("#djjfxx_scdj").hide();
			$("#djfzxx_scdj").hide();

			$("#djshOpinion").show();
			$("#djshCsOpinionInfo").show();
			$("#djshHzOpinionInfo").show();
			$("#saveDjshOpinionx").hide();
			$("#djshOpinion textarea").each(function (index) {
				$(this).attr("disabled","disabled");
			});

		}else if(BDC_OPTYPE == "2"){
			$("#bdcqzh_tr").attr("style","");
			$("#BDC_DBBTN").remove();
			$("#bdcqzbsm_tr").attr("style","");
			$("#dy_bdcqzbsm_tr").attr("style","");
			$("#bdcczr_tr").attr("style","");
			$("#qzbsmsavebtn").remove();
			//证书预览
			$("#BDC_QZVIEW").attr("style","");
			$("#BDC_QZVIEW").removeAttr("disabled");
			//登记缴费和发证信息
			$("#djjfxx_scdj").attr("style","");
			$("#djfzxx_scdj").attr("style","");

			$("#djjfxx_scdj input").each(function(index){
				$(this).removeAttr("disabled");
			});
			$("#djjfxx_scdj select").each(function(index){
				$(this).removeAttr("disabled");
			});

			$("#djfzxx_scdj input").each(function(index){
				$(this).removeAttr("disabled");
			});
			$("#djfzxx_scdj select").each(function(index){
				$(this).removeAttr("disabled");
			});

			var currentUser = $("input[name='uploadUserName']").val();
			var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
			$("input[name='DJFZXX_FZRY']").val(currentUser);
			$("input[name='DJFZXX_FZSJ']").val(currentTime);
			$("input[name='DJJFMX_SFRQ']").val(currentTime);

			$("#djshOpinion").show();
			$("#djshCsOpinionInfo").show();
			$("#djshHzOpinionInfo").show();
			$("#saveDjshOpinionx").hide();
			$("#djshOpinion textarea").each(function (index) {
				$(this).attr("disabled","disabled");
			});

		}else if(BDC_OPTYPE == "flag1"){
			$("#bdcqzh_tr").attr("style","");
			$("#BDC_DBBTN").remove();
			$("#bdcqzbsm_tr").attr("style","");
			$("#bdcczr_tr").attr("style","");
			$("#dy_bdcqzbsm_tr").attr("style","");
			//权证打印按钮
			$("#BDC_QZVIEW").attr("style","");
			//$("#BDC_QZPRINT").attr("style","");
			$("#BDC_QZVIEW").removeAttr("disabled");
			//$("#BDC_QZPRINT").removeAttr("disabled");
			$("input[name='BDC_QZBSM']").removeAttr("disabled");
			$("input[name='BDC_QZBSM']").addClass(" validate[required]");

			/*隐藏登记缴费和登记发证信息*/
			$("#djjfxx_scdj").hide();
			$("#djfzxx_scdj").hide();

			$("#djshOpinion").show();
			$("#djshCsOpinionInfo").show();
			$("#djshHzOpinionInfo").show();
			$("#saveDjshOpinionx").hide();
			$("#djshOpinion textarea").each(function (index) {
				$(this).attr("disabled","disabled");
			});

		}else if(BDC_OPTYPE == "flag2"){
			$("#bdcqzh_tr").attr("style","");
			$("#BDC_DBBTN").remove();
			$("#bdcqzbsm_tr").attr("style","");
			$("#dy_bdcqzbsm_tr").attr("style","");
			$("#bdcczr_tr").attr("style","");
			$("#qzbsmsavebtn").remove();
			//证书预览
			$("#BDC_QZVIEW").attr("style","");
			$("#BDC_QZVIEW").removeAttr("disabled");
			//登记证书预览
			$("#DY_BDC_QZVIEW").attr("style","");
			$("#DY_BDC_QZVIEW").removeAttr("disabled");

			//登记缴费和发证信息
			$("#djjfxx_scdj").attr("style","");
			$("#djfzxx_scdj").attr("style","");

			$("#djshOpinion").show();
			$("#djshCsOpinionInfo").show();
			$("#djshHzOpinionInfo").show();
			$("#saveDjshOpinionx").hide();
			$("#djshOpinion textarea").each(function (index) {
				$(this).attr("disabled","disabled");
			});

		}
	}
}

function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板
	//dataStr +="&TemplatePath="+TemplatePath;
	dataStr +="&TemplateName="+TemplateName;
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

//------------------------------------身份身份证读取开始---------------------------------------------------
function MyGetData() //OCX读卡成功后的回调函数
{
	// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>
	// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>
}

function MyClearData() //OCX读卡失败后的回调函数
{
	alert("未能有效识别身份证，请重新读卡！");
	$("input[name='POWERPEOPLENAME']").val("");
	$("input[name='POWERPEOPLEIDNUM']").val("");
}

function MyGetErrMsg() //OCX读卡消息回调函数
{
	// 			Status.value = GT2ICROCX.ErrMsg;
}

function PowerPeopleRead(o) //开始读卡
{
	var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
	GT2ICROCX.PhotoPath = "c:"
	//GT2ICROCX.Start() //循环读卡
	//单次读卡(点击一次读一次)
	if (GT2ICROCX.GetState() == 0) {
		GT2ICROCX.ReadCard();
		$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
		$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo)
	}
}
function PowLegalRead(o) //开始读卡
{
	var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
	GT2ICROCX.PhotoPath = "c:"
	//GT2ICROCX.Start() //循环读卡
	//单次读卡(点击一次读一次)
	if (GT2ICROCX.GetState() == 0) {
		GT2ICROCX.ReadCard();
		$("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name)
		$("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo)
	}
}
function PowAgentRead(o) //开始读卡
{
	var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
	GT2ICROCX.PhotoPath = "c:"
	//GT2ICROCX.Start() //循环读卡
	//单次读卡(点击一次读一次)
	if (GT2ICROCX.GetState() == 0) {
		GT2ICROCX.ReadCard();
		$("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name)
		$("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo)
	}
}

function print() //打印
{
	GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
}
//------------------------------------身份身份证读取结束---------------------------------------------------

