$(function() {
	//点击类型触发事件
	$("input[name='TYPE']").on("click", function(event) {
		//setTakecardType(this.value);
		addBbfbdcdjzmInfo(this.value);
		isSetQlrxx(this.value);
	});
	
});
function isSetQlrxx(type){
	if(type==2 || type==3){
		$("#qlrxx").hide();
		$("#qlrxx input").each(function(index){
			$(this).val("");
		});
		$("#qlrxx select").each(function(index){			
			$(this).val("");
		});
	} else{
		$("#qlrxx").show();
	}
}

function FLOW_SubmitFun(flowSubmitObj){
	//先判断表单是否验证通过
	var validateResult =$("#T_BDCQLC_BFBDCQZSHDJZM_FORM").validationEngine("validate");
	if(validateResult){

		if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
			var isdbflag = $("input[name='BDC_DBZT']").val();
			if (isdbflag) {
				if (isdbflag == "-1") {
					parent.art.dialog({
						content: "未确认登簿，请先完成登簿操作，并确认登簿成功。",
						icon: "warning",
						ok: true
					});
					return;
				} else if (isdbflag == "0") {
					var dbjg = $("input[name='BDC_DBJG']").val();
					parent.art.dialog({
						content: "登簿异常！" + dbjg,
						icon: "warning",
						ok: true
					});
					return;
				}
			} else {
				parent.art.dialog({
					content: "未确认登簿，请先完成登簿操作，并确认登簿成功。",
					icon: "warning",
					ok: true
				});
				return;
			}
		}

		// 获得权利人信息
		getPowPeopleJson();
		// 获得权属来源信息
		getPowSourceJson();

		// 获取发证明细数据
		getDjfzxxJson_bfbdcqzshdjzm();
		// 获取缴费信息
		getDjjfxxJson_bfbdcqzshdjzm();
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
				var formData = FlowUtil.getFormEleData("T_BDCQLC_BFBDCQZSHDJZM_FORM");
				for(var index in formData){
					flowSubmitObj[eval("index")] = formData[index];
				}
				/*宗地用途特殊获取*/
				var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
				if (ZD_TDYT) {
					flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
				}
				//console.dir(flowSubmitObj);
				return flowSubmitObj;
			}else{
				return null;
			}
		}
	}else{
		return null;
	}
}

function FLOW_TempSaveFun(flowSubmitObj) {

	// 获得权利人信息
	getPowPeopleJson();
	// 获得权属来源信息
	getPowSourceJson();
	// 获取发证明细数据
	getDjfzxxJson_bfbdcqzshdjzm();
	// 获取缴费信息
	getDjjfxxJson_bfbdcqzshdjzm();

	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_BFBDCQZSHDJZM_FORM");
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
		initDjfzxx_bfbdcqzshdjzm(djfzxx_jsonItems);
	}
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_bfbdcqzshdjzm(djjfmx_jsonItems);
	}
}

function addBbfbdcdjzmInfo(type){
	$.post("bdcExecutionController/addBbfbdcdjzmInfo.do",{
		type:type
	}, function(responseText, status, xhr) {
		$("#bfbdcdjzmInfo").html(responseText);
	});
}

function addInitBbfbdcdjzmInfo(o){
	$.post("bdcExecutionController/addBbfbdcdjzmInfo.do",{
		type:o.TYPE
	}, function(responseText, status, xhr) {
		$("#bfbdcdjzmInfo").append(responseText);	
		FlowUtil.initFormFieldValue(o,"bfbdcdjzmInfo");
	});
}
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="SF"||zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
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
	$("input[name='QLR_LABEL']").val(val);
}

/***==============不动产信息查询开始=========================*/

function showPageBdcInfoSelector() {
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1", {
		title: "不动产档案信息查询",
		width: "100%",
		lock: true,
		resize: false,
		height: "100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1) {
				var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
				var result = {
					"isPass":true
				}
				if (cqzt.indexOf("注销") != -1) {
					result = {
						"isPass": false,
						"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
						"type": "0"
					};
				}
				var dyinfo = {
					"APPLICANT_UNIT": bdcdaxxcxInfo[0].QLRMC,
					"ESTATE_NUM": bdcdaxxcxInfo[0].BDCDYH,
					"LOCATED": bdcdaxxcxInfo[0].FDZL,
					"ZD_BM": bdcdaxxcxInfo[0].ZDDM,
					"ZD_QLSDFS": bdcdaxxcxInfo[0].QLSDFS,
					"ZD_ZL": bdcdaxxcxInfo[0].TDZL,
					"ZD_MJ": bdcdaxxcxInfo[0].ZDMJ,
					"ZD_TDYT": bdcdaxxcxInfo[0].TDYT,
					"ZD_YTSM": bdcdaxxcxInfo[0].TDYTSM,
					"ZD_RJL": bdcdaxxcxInfo[0].RJL,
					"ZD_JZXG": bdcdaxxcxInfo[0].JZXG,
					"ZD_JZMD": bdcdaxxcxInfo[0].JZMD,
					"ZD_E": bdcdaxxcxInfo[0].TD_SZ_D,
					"ZD_S": bdcdaxxcxInfo[0].TD_SZ_N,
					"ZD_W": bdcdaxxcxInfo[0].TD_SZ_X,
					"ZD_N": bdcdaxxcxInfo[0].TD_SZ_B,
					"GYTD_BEGIN_TIME": DateConvertFun(bdcdaxxcxInfo[0].QLQSSJ),
					"GYTD_END_TIME1": DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ),
					"GYTD_END_TIME2": DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ1),
					"GYTD_END_TIME3": DateConvertFun(bdcdaxxcxInfo[0].QLJSSJ2),
					"GYTD_QDJG": bdcdaxxcxInfo[0].JG,
					"GYTD_SYQMJ": bdcdaxxcxInfo[0].SYQMJ,
					"GYTD_FJ": bdcdaxxcxInfo[0].FJ,
					"BDC_DBR": bdcdaxxcxInfo[0].DBR,
					"GYTD_DJSJ": bdcdaxxcxInfo[0].DBSJ,
					"FW_ZL": bdcdaxxcxInfo[0].FDZL,
					"FW_ZH": bdcdaxxcxInfo[0].ZH,
					"FW_SZC": bdcdaxxcxInfo[0].SZC,
					"FW_HH": bdcdaxxcxInfo[0].HH,
					"FW_ZCS": bdcdaxxcxInfo[0].ZCS,
					"FW_GHYT": bdcdaxxcxInfo[0].FW_GHYT,
					"FW_JGSJ": bdcdaxxcxInfo[0].JGSJ,
					"FW_YTSM": bdcdaxxcxInfo[0].GHYTSM,
					"FW_XZ": bdcdaxxcxInfo[0].FWXZ,
					"FW_FWJG":bdcdaxxcxInfo[0].FWJG,//房屋结构
					"FW_JYJG": bdcdaxxcxInfo[0].JYJG,
					"FW_DYDYTDMJ": bdcdaxxcxInfo[0].DYTDMJ,
					"FW_FTTDMJ": bdcdaxxcxInfo[0].FTTDMJ,
					"FW_JZMJ": bdcdaxxcxInfo[0].JZMJ,
					"FW_ZYJZMJ": bdcdaxxcxInfo[0].ZYJZMJ,
					"FW_FTJZMJ": bdcdaxxcxInfo[0].FTJZMJ,
					"FW_DJRQ": bdcdaxxcxInfo[0].DBSJ,
					"FW_DBRMC": bdcdaxxcxInfo[0].DBR,
					"POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
					"POWERSOURCE_QSZT":bdcdaxxcxInfo[0].QSZT,
					"POWERSOURCE_ZJLX":bdcdaxxcxInfo[0].ZJLX,
					"POWERSOURCE_ZJHM":bdcdaxxcxInfo[0].ZJHM,
					"POWERSOURCE_ZDDM":bdcdaxxcxInfo[0].ZDDM,
					"POWERSOURCE_GLH":bdcdaxxcxInfo[0].GLH,
					"POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo[0].QLQSSJ,//权利开始时间
					"POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo[0].QLJSSJ,//权利结束时间
					"POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo[0].QLJSSJ1,
					"POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo[0].QLJSSJ2,
					"POWERSOURCE_DYH":bdcdaxxcxInfo[0].BDCDYH,
					"POWERSOURCE_QSWH":bdcdaxxcxInfo[0].BDCQZH,//证书文号（权属来源）
					"POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
				};
				if (result.isPass == true) {
					/*获取房屋单元信息*/
					$.post("bdcApplyController.do?fwdyxxcxDatagrid", {bdcdyh: bdcdaxxcxInfo[0].BDCDYH}, function (responseText, status, xhr) {
						$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
					});
					/*将预告信息存入数据库*/
					$("input[name='YGXX_JSON']").val(JSON.stringify(bdcdaxxcxInfo));
					FlowUtil.initFormFieldValue(dyinfo, "T_BDCQLC_BFBDCQZSHDJZM_FORM");
					formatDic("HF_QLLX", bdcdaxxcxInfo[0].ZDQLLX); //
					formatDic("ZD_QLLX", bdcdaxxcxInfo[0].ZDQLLX); //
					formatDic("ZD_LEVEL", bdcdaxxcxInfo[0].DJ); //
					formatDic("GYTD_GYFS", bdcdaxxcxInfo[0].GYFS); //
					formatDic("GYTD_QSZT", bdcdaxxcxInfo[0].QSZT); //
					formatDic("FW_QLLX", bdcdaxxcxInfo[0].ZDQLLX); //

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
					for(var name in dyinfo){
						if(name.startWithOwn("POWERSOURCE_")){
							powerSource[name]=dyinfo[name];
						}
					}

					var powerSourceItems = [];
					powerSourceItems.push(powerSource);

					//回填权属来源信息
					initPowSource(powerSourceItems);

					//回填宗地权利性质
					if (bdcdaxxcxInfo[0].QLXZ) {
						var datas = $("#ZD_QLXZ").combobox("getData");
						for (var i = 0; i < datas.length; i++) {
							if (datas[i].text == bdcdaxxcxInfo[0].QLXZ) {
								$("#ZD_QLXZ").combobox("setValue", datas[i].value);
								break;
							}
						}
					}
					//回填宗地坐落区县
					if (bdcdaxxcxInfo[0].XZQ) {
						$("#ZDZL_XIAN").combobox("setValue", bdcdaxxcxInfo[0].XZQ);
					}

					//回填宗地坐落镇
					if (bdcdaxxcxInfo[0].DJQ) {
						$("#ZDZL_ZHENG").combobox("setValue", bdcdaxxcxInfo[0].DJQ);
					}

					//回填宗地坐落乡村
					if (bdcdaxxcxInfo[0].DJZQ) {
						$("#ZDZL_CUN").combobox("setValue", bdcdaxxcxInfo[0].DJZQ);
					}

					//回填土地用途
					if (bdcdaxxcxInfo[0].TDYT) {
						$("#ZD_TDYT").combobox("setValues", bdcdaxxcxInfo[0].TDYT.split(","));
					}

					formatDic("ZD_TZM", bdcdaxxcxInfo[0].ZDTZM); //宗地特征码
					//申报对象信息回填
					$('input[name="SQRMC"]').val(bdcdaxxcxInfo[0].QLRMC);//申请人
					$('#SQRZJLX').find("option").filter(function (index) {
						return bdcdaxxcxInfo[0].ZJLX === $(this).text();
					}).attr("selected", "selected");//证件类别
					$('input[name="SQRSFZ"]').val(bdcdaxxcxInfo[0].ZJHM);//证件号码
					art.dialog.removeData("bdcdaxxcxInfo");
				} else {
					if(result.type=="0"){
						parent.art.dialog({
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}
				}
			} else {
				parent.art.dialog({
					content: "请根据查询选择一条不动产登记信息。",
					icon: "warning",
					ok: true
				});
			}
		}
	}, false);
}


//查看访问状态
function checkFwCqzt(dyh){
	var result = {
		"isPass":false,
		"type":"0",
		"msg":"系统异常"
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
					if(list.length == 1 && list[0].CQZT != null){
						if("预售预告" == list[0].CQZT.trim()||"正常" == list[0].CQZT.trim()){
							$("#isAuditPass").val("1");						
							result = {
								"isPass":true
							};
						}else{
							$("#isAuditPass").val("0");
							result = {
								"isPass":false,
								"msg":"请注意，该不动产单元号不为预售预告，不可办理该业务。",
								"type":"0"
							};							
						}
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

//检验是不动产单元号对应的房屋状态
function checkIsAuditPass(){
	var isPass = false;
	var reg = /^(\d{6})([a-zA-Z0-9]{6})([a-zA-Z]{2})([a-zA-Z0-9]{14})$/;
	var val = $("#ESTATE_NUM").val();
	if(val.match(reg)){
		AppUtil.ajaxProgress({
			url : "bdcApplyController/bdcFwztCheck.do",
			params : {
				"bdcdyh":val
			},
			callback : function(resultJson) {
				if(resultJson.success){
					var list = resultJson.data;			
					if(list != null && list.length > 0){
						if(list.length == 1 && list[0].CQZT != null){
							if("预售预告" == list[0].CQZT.trim()){
								$("#isAuditPass").val("1");						
								isPass = true;
							}else{
								$("#isAuditPass").val("0");
								art.dialog({
									lock: true,
									content: "请注意，该不动产单元号不为预售预告，不可办理该业务。",
									icon:"warning",
									ok: true
								});
							}
						}
				   }else{
						art.dialog({
							lock: true,
							content: "暂查无该房屋状态信息,无法查验该房屋状态，不可继续办理业务。",
							icon:"warning",
							ok: true
						});
				   }
			   }else{
					art.dialog({
						lock: true,
						content: resultJson.msg,
						icon:"warning",
						ok: true
					});
			   }
			}
		});
	}else{
		$("#isAuditPass").val("0");
	}
	return isPass;
}


		
function newDicInfoWin(typeCode,combId){
	$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
		title : "新增",
        width:"600px",
        lock: true,
        resize:false,
		fixed: true,
        height:"240px",
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

//社会限制人员校验
function checkLimitPerson() {
	var data = [];
	var flag = true;
	//获取抵押人
	var dyrmc = $("input[name='DYR']").val();
	var idno = $("input[name='DYRZJHM']").val();
	if (dyrmc != '' && idno != '') {
		var dyrmcs = dyrmc.split('、');
		var idnos = idno.split('、');
		if (dyrmcs.length == idnos.length) {
			for (var i = 0; i < idnos.length; i++) {
				var dyqData = {};
				dyqData["xm"] = dyrmcs[i];
				dyqData["zjhm"] = idnos[i];
				data.push(dyqData);
			}
		} else {
			parent.art.dialog({
				content : "抵押权人姓名与证件号需一一对应！",
				icon : "warning",
				ok : true
			});
			flag = false;
		}
	}
	if (data.length < 1) {
		flag = false ;
	}
	var cxlist = JSON.stringify(data);
	$.ajaxSettings.async = false;
	$.post("bdcApplyController.do?checkLimitPerson", {
		cxlist : cxlist
	},
		function(responseText, status, xhr) {
			var obj = responseText.rows;
			if (responseText.total > 0) {
				var name = "";
				for (var i = 0; i < obj.length; i++) {
					name += obj[i].XM + "(" + obj[i].ZJHM + ")、";
				}
				name = name.substring(0, name.length - 1);
				parent.art.dialog({
					content : "存在涉会/涉黑人员【" + name + "】,不可受理此登记！",
					icon : "warning",
					ok : true
				});
				flag = false;
			}
		});
	return flag;
}

function setSfzgedy(value) {
	if (value === "1") {
		$("input[name='ZGZQE']").attr("disabled", false);
		$("input[name='BDBZZQSE']").attr("disabled", true);
		$("select[name='DYFS']").val("2");
	} else {
		$("input[name='ZGZQE']").attr("disabled", true);
		$("input[name='BDBZZQSE']").attr("disabled", false);
		$("select[name='DYFS']").val("1");
	}
}

function getPowerPeopleInfoJson() {
	var datas = [];
	$("#PowerPeopleTable .bdcdydacxTr").each(function() {
		var iteminfo = $(this).find("input[name='PowerPeopleTableTrMx']").val();
		datas.push(JSON.parse(iteminfo));
	});
	if (datas.length != 0) {
		$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
	}
}

function getPowerSourceInfoJson() {
	var datas = [];
	$("#PowerSourceTable .bdcdydacxTr").each(function() {
		var iteminfo = $(this).find("input[name='PowerSourceTableTrMx']").val();
		datas.push(JSON.parse(iteminfo));
	});
	if (datas.length != 0) {
		$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
	}
}

function notPrint() {
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
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


function formatDic(name,value) {
	$("select[name='"+name+"']").find("option").each(function () {
		if ($(this).text() == value) {
			$(this).attr("selected", "selected");
		}
	});
}

/**返回时间格式YYYY-MM-DD*/
function DateConvertFun(str){
	var time = "";
	if(str){
		time=str;
	}
	return time;
}

function bdcBfbdcqzshdjzmDJZMprint(typeId) {
	var DY_BDCQZBSM = $("input[name='BDC_QZBSM']").val();
	if(DY_BDCQZBSM){
		// typeId: 1为证书预览  2为证书打印
		var title = "证书打印";
		var templateAlias = 'DJZM';
		if(typeId==1) {
			title = "证书预览";
		}
		var dataStr = "";
		var QLRZJH = getQlrMc();
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
			content :"不动产登记证明权证标识码未填写。",
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

function yjmbSelect() {
	$("#zddjyy").attr("onclick", "bdcCyyjmbSelector('bfbdcqzshdjzmzd_yy','GYTD_DJYY','input')");
	$("#zdfj").attr("onclick", "bdcCyyjmbSelector('bfbdcqzshdjzmzd_fj','GYTD_FJ','textarea')");
	$("#fwdjyy").attr("onclick", "bdcCyyjmbSelector('bfbdcqzshdjzmzdfw_yy','FW_DJYY','input')");
	$("#fwfj").attr("onclick", "bdcCyyjmbSelector('bfbdcqzshdjzmzdfw_fj','FW_FJ','textarea')");
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
								initPowPeople(qlrs);
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

function disabledForm() {
	$("#T_BDCQLC_BFBDCQZSHDJZM_FORM select , #T_BDCQLC_BFBDCQZSHDJZM_FORM input , #T_BDCQLC_BFBDCQZSHDJZM_FORM textarea").each(function (index) {
		$(this).attr("disabled", "disabled");
	});
	$("#BDC_DBBTN").attr("disabled", false);
	$("#BDC_QZVIEW").attr("disabled", false);
	$("#BDC_QZPRINT").attr("disabled", false);
}