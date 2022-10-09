/*
	function showSelectBdcdaxxcx() {
		$.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
			title : "不动产档案信息查询",
			width : "100%",
			lock : true,
			resize : false,
			height : "100%",
			close : function() {
				var zdxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
				if (bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1) {
					var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
					var result = {
						"isPass": true
					};
					if (cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
						result = {
							"isPass": false,
							"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
							"type": "0"
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
						"POWERSOURCE_GLH":bdcdaxxcxInfo[0].GLH,
						"POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo[0].QLQSSJ,//权利开始时间
						"POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo[0].QLJSSJ,//权利结束时间
						"POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo[0].QLJSSJ1,
						"POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo[0].QLJSSJ2,
						"ZD_BM":bdcdaxxcxInfo[0].ZDDM,
						"ZD_QLLX":bdcdaxxcxInfo[0].ZDQLLX,//宗地权利类型
						"ZD_QLSDFS":bdcdaxxcxInfo[0].QLSDFS,
						"ZD_ZL":bdcdaxxcxInfo[0].TDZL,
						"ZD_MJ":bdcdaxxcxInfo[0].ZDMJ,
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
						"GYTD_BEGIN_TIME":bdcdaxxcxInfo[0].QLQSSJ,
						"GYTD_END_TIME1":bdcdaxxcxInfo[0].QLJSSJ,
						"GYTD_END_TIME2":bdcdaxxcxInfo[0].QLJSSJ1,
						"GYTD_END_TIME3":bdcdaxxcxInfo[0].QLJSSJ2,
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
						"FW_FTJZMJ":bdcdaxxcxInfo[0].FTJZMJ,
						"FW_QLLX":bdcdaxxcxInfo[0].FW_QLLX,
						"FW_JGSJ":bdcdaxxcxInfo[0].JGSJ,
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
					if (result.isPass == true) {
						FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSSCDJ_FORM");
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
						formatDic("ZD_TZM", bdcdaxxcxInfo[0].ZDTZM); //宗地特征码
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

						if(result.type=="0"){
							parent.art.dialog({
								content: result.msg,
								icon:"warning",
								ok: true
							});
						}
					} else {
						if (result.type == "0") {
							parent.art.dialog({
								content: result.msg,
								icon: "warning",
								ok: true
							});
						}
					}
					art.dialog.removeData("bdcdaxxcxInfo");
				} else {
					parent.art.dialog({
						content: "请根据查询选择一条不动产登记信息。",
						icon:"warning",
						ok: true
					});
				}
			}
		}, false);
	}

	function yjmbSelect() {
		$("#zddjyy").attr("onclick","bdcCyyjmbSelector('gyjsscdj_yy','GYTD_DJYY','input')")
		$("#zdfj").attr("onclick","bdcCyyjmbSelector('gyjsscdj_fj','GYTD_FJ','textarea')")
	}
	
	*/

//提交流程
function FLOW_SubmitFun(flowSubmitObj) {
	//先判断表单是否验证通过
	var validateResult = $("#T_BDCQLC_GYJSSCDJ_FORM").validationEngine("validate");
	if (validateResult) {
		// 获得权利人信息
		getPowPeopleJson();
		// 获得权属来源信息
		getPowSourceJson();
		// 获取发证明细数据
		getDjfzxxJson_scdj();
		// 获取缴费信息
		getDjjfxxJson_scdj();
		//登簿的环节需完成登簿才可提交
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
		/*if (!isPowerPeople()) {
			parent.art.dialog({
				content : "申请人的名字必须出现在权利人信息中",
				icon : "warning",
				ok : true
			});
			return;
		}*/
		var isAuditPass = $('input[name="isAuditPass"]:checked').val();
		if (isAuditPass == "-1") {
			parent.art.dialog({
				content : "检查上传的审批表扫描件不合规，请先退回补件。",
				icon : "warning",
				ok : true
			});
			return null;
		} else {
			setGrBsjbr(); //个人不显示经办人设置经办人的值
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
			if (submitMaterFileJson || submitMaterFileJson == "") {
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				//先获取表单上的所有值
				var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSSCDJ_FORM");
				for (var index in formData) {
					flowSubmitObj[eval("index")] = formData[index];
				}
				/*宗地用途特殊获取*/
				var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
				if (ZD_TDYT) {
					flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
				}
				return flowSubmitObj;
			} else {
				return null;
			}
		}
	} else {
		return null;
	}
}
	
//暂存流程
function FLOW_TempSaveFun(flowSubmitObj) {
	//先判断表单是否验证通过
	//getPowerInfoJson();
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
	var formData = FlowUtil.getFormEleData("T_BDCQLC_GYJSSCDJ_FORM");
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

//退回流程
function FLOW_BackFun(flowSubmitObj) {
	return flowSubmitObj;
}


//初始化权利人信息、权属来源、登记缴费、登记发证信息
function initAutoTable(flowSubmitObj) {
	/*var powerinfoJson = flowSubmitObj.busRecord.POWERINFO_JSON;
	if (null != powerinfoJson && '' != powerinfoJson) {
		var powerinfos = eval(powerinfoJson);
		for (var i = 0; i < powerinfos.length; i++) {
			if (i == 0) {
				FlowUtil.initFormFieldValue(powerinfos[i], "powerInfo_1");
			} else {
				addPowerInfo();
				FlowUtil.initFormFieldValue(powerinfos[i], "powerInfo_" + (i + 1));
			}
		}
	}*/
	initPowerPeopleJson(flowSubmitObj);//权利人信息
	initPowerSourceJson(flowSubmitObj);//权属来源
	var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
	if(null != djfzxx_json && '' != djfzxx_json){
		var djfzxx_jsonItems = JSON.parse(djfzxx_json);
		initDjfzxx_scdj(djfzxx_jsonItems);
	}//登记缴费信息
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_scdj(djjfmx_jsonItems);
	}//登记发证信息
}

//初始化权利人信息
function initPowerPeopleJson(flowSubmitObj){
  var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
  if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
      var powerpeopleItems = JSON.parse(powerpeopleinfoJson);
      initPowPeopleInfo(powerpeopleItems);
  } 
}

//初始化权属来源信息
function initPowerSourceJson(flowSubmitObj){
	var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if(null != powersourceinfoJson && '' != powersourceinfoJson && '[]' != powersourceinfoJson){
		var powersourceItems = JSON.parse(powersourceinfoJson);
		initPowSource(powersourceItems);
	}
}


//初始化下拉框动态选值（宗地基本信息-土地用途、坐落）
function initCombox(){
	
	//宗地土地用途
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
			$("#ZD_YTSM").val($(this).combobox('getText'));
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
 
	//坐落-乡
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
	
	//坐落-镇
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

	//坐落-村
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
}

//权利人性质动态更新（个人与法人）
function qlrChangeFun(c,flag){
  if(c == '1'){//个人
      if(flag){
          $("select[name='POWERPEOPLEIDTYPE'] option[value='SF']").prop("selected", true);
      }
      $("select[name='POWERPEOPLEIDTYPE']").removeAttr("disabled");
      $("#qlr_gr").attr("style","");
      $("#qlr_qy").attr("style","display:none;");
  }else{//法人
      $("select[name='POWERPEOPLEIDTYPE'] option[value='YYZZ']").prop("selected", true);//营业执照
      //$("select[name='POWERPEOPLEIDTYPE']").attr("disabled","disabled");        
      $("#qlr_qy").attr("style","");
      $("#qlr_gr").attr("style","display:none;");    
  }
}

//宗地基本信息查询
function showSelectBdcZdjbxx(){
	$.dialog.open("bdcGyjsscdjController.do?zdxxcxSelector&allowCount=1", {
		title : "宗地信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var zdxxcxInfo = art.dialog.data("zdxxcxInfo");
			if(zdxxcxInfo && zdxxcxInfo.length == 1){
				/*var cqzt = zdxxcxInfo[0].CQZT.trim();
				var result = {
					"isPass":true
				}
				if (cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
					result = {
						"isPass": false,
						"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
						"type": "0"
					};
				}*/
				var info = {
					"ESTATE_NUM":zdxxcxInfo[0].BDCDYH,
					"LOCATED":zdxxcxInfo[0].ZL,
					//宗地基本信息
					"ZD_BM":zdxxcxInfo[0].ZDDM,//宗地代码
					"ZD_QLLX":zdxxcxInfo[0].QLLX,//宗地权利类型
					"ZD_TZM":zdxxcxInfo[0].ZDDM,//宗地特征码				
					"ZD_QLSDFS":zdxxcxInfo[0].QLSDFS,//宗地权利设定方式
					"ZD_ZL":zdxxcxInfo[0].ZL,//宗地坐落
					"ZD_MJ":zdxxcxInfo[0].ZDMJ,//宗地面积					
					"ZD_TDYT":zdxxcxInfo[0].TDYT,//土地用途					
					"ZD_YTSM":zdxxcxInfo[0].TDYTSM,//宗地用途说明
					"ZD_QLXZ":zdxxcxInfo[0].QLXZ,//权利性质					
					"ZD_LEVEL":zdxxcxInfo[0].TDDJ,//等级					
					"ZD_RJL":zdxxcxInfo[0].RJL,//容积率
					"ZD_JZXG":zdxxcxInfo[0].JZXG,//建筑限高（米）
					"ZD_JZMD":zdxxcxInfo[0].JZMD,//建筑密度
					"ZD_E":zdxxcxInfo[0].TD_SZ_D,//宗地四至_东
					"ZD_W":zdxxcxInfo[0].TD_SZ_X,//宗地四至_西
					"ZD_N":zdxxcxInfo[0].TD_SZ_B,//宗地四至_北
					"ZD_S":zdxxcxInfo[0].TD_SZ_N,//宗地四至_南
					//国有土地使用权-权利信息
					"GYTD_BEGIN_TIME":zdxxcxInfo[0].QLQSSJ,
					"GYTD_END_TIME1":zdxxcxInfo[0].QLJSSJ,
					"GYTD_END_TIME2":zdxxcxInfo[0].QLJSSJ1,
					"GYTD_END_TIME3":zdxxcxInfo[0].QLJSSJ2,
					"GYTD_GYFS":zdxxcxInfo[0].GYQK,
					"GYTD_QDJG":zdxxcxInfo[0].JG,
					"GYTD_SYQMJ":zdxxcxInfo[0].SYQMJ,
					"GYTD_QSZT":zdxxcxInfo[0].QSZT,				
				};
				FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSSCDJ_FORM");
				
				//回填权利类型
				$("#ZD_QLLX option").each(function(){
					var text = $(this).text();
					if(text == info.ZD_QLLX.trim()){
						$(this).attr("selected",true);
					}
				});
				//宗地特征码
				formatDic("ZD_TZM", zdxxcxInfo[0].ZDTZM); 
				//回填权利设定方式
				$("#ZD_QLSDFS option").each(function(){
					var text = $(this).text();
					if(text == info.ZD_QLSDFS.trim()){
						$(this).attr("selected",true);
					}
				});
				//回填土地用途
				if (zdxxcxInfo[0].TDYT) {
					$("#ZD_TDYT").combobox("setValues", zdxxcxInfo[0].TDYT.split(","));
				}
				
				//回填宗地权利性质
				if(info.ZD_QLXZ){
					var datas = $("#·").combobox("getData");
					for(var i=0;i<datas.length;i++){
						if(datas[i].text == info.ZD_QLXZ){
							$("#ZD_QLXZ").combobox("setValue",datas[i].value);
							break;
						}
					}
				}
				//回填土地等级
				$("#ZD_LEVEL option").each(function(){
					var text = $(this).text();
					if(text == info.ZD_LEVEL.trim()){
						$(this).attr("selected",true);
					}
				});
				//回填共有方式
				$("#GYTD_GYFS option").each(function(){
					var text = $(this).text();
					if(text == info.GYTD_GYFS.trim()){
						$(this).attr("selected",true);
					}
				});	
				//回填宗地坐落区县
				if (zdxxcxInfo[0].XZQ) {
					$("#ZDZL_XIAN").combobox("setValue", zdxxcxInfo[0].XZQ);
				}

				//回填宗地坐落镇
				if (zdxxcxInfo[0].DJQ) {
					$("#ZDZL_ZHENG").combobox("setValue", zdxxcxInfo[0].DJQ);
				}

				//回填宗地坐落乡村
				if (zdxxcxInfo[0].DJZQ) {
					$("#ZDZL_CUN").combobox("setValue", zdxxcxInfo[0].DJZQ);
				}				
				art.dialog.removeData("zdxxcxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条宗地信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
}


//不动产登簿操作
function BDCQLC_bdcdb(){
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
												qlrs[j].BDC_SZZH = qzinfos[i].qzzh;//缮证证号
												qlrs[j].BDC_CZR = currentUser;//操作人
												qlrs[j].BDC_CZSJ = currentTime;//操作时间
												iflag = iflag + 1;
											}
										}
									}
								}
								initPowPeopleInfo(qlrs);
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
									$("input[name='BDC_DBR']").val(currentUser);
									$("input[name='BDC_DBSJ']").val(currentTime);
									disabledDbBtn("BDC_DBBTN");
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
function bdcqlcCQZSprint(typeId){
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

//不可打印提示
function notPrint(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}


//不动产审批表打印
function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板  //3代表审批表
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


//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证" || zjlx=="SF" ){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}



