
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
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
	/* var text = $("select[name='QLR_MC']").find("option:selected").text();
	$("input[name='QLR_ZJNO']").val(val);
	$("input[name='QLR_LABEL']").val(text); */
}


//暂存
function saveTempFlow(formId) {
    //先判断表单是否验证通过
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,1);
    //获取流程信息对象JSON
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    //将其转换成JSON对象
    var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值

    var formData = FlowUtil.getFormEleData(formId);
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
	
	//获取义务人信息
	flowSubmitObj.YWR_JSON = getYwrJson();
	
    flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
    var postParam = $.param(flowSubmitObj);
    AppUtil.ajaxProgress({
        url : "webSiteController.do?submitApply",
        params : postParam,
        callback : function(resultJson) {
            if(resultJson.OPER_SUCCESS){
                art.dialog({
                    content :"保存成功,草稿数据只保存90天，过期系统自动清理,申报号是:"+resultJson.EFLOW_EXEID,// resultJson.OPER_MSG,
                    icon : "succeed",
                    lock : true,
                    ok:function(){
                        window.top.location.href=__newUserCenter;
                    },
                    close: function(){
                        window.top.location.href=__newUserCenter;
                    }
                });
            }else{
                art.dialog({
                    content : resultJson.OPER_MSG,
                    icon : "error",
                    ok : true
                });
            }
        }
    });
}


//提交事件（开始环节-执行签章首次提交，需要先暂存（生成申报号））
function submitFlow(formId){
	var EXE_ID = $("input[name='EXE_ID']").val();
	//先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
		var isValid = $("#ygdyqygInfo").form('validate');
		if(isValid){			
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			if(submitMaterFileJson||submitMaterFileJson==""){
				//获取流程信息对象JSON
				var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				//将其转换成JSON对象
				var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
				//材料信息
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

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

				//先获取表单上的所有值
				var formData = FlowUtil.getFormEleData(formId);
				for(var index in formData){
					flowSubmitObj[eval("index")] = formData[index];
				}
				//获取义务人信息
				flowSubmitObj.YWR_JSON = getYwrJson();	
				//判断是否首次提交
				//alert("流程数据："+flowSubmitObj.busRecord+"--申报号："+EXE_ID);
				if((flowSubmitObj.busRecord=="" || flowSubmitObj.busRecord==undefined || flowSubmitObj.busRecord==null )
					&& (EXE_ID=="" || EXE_ID==undefined || EXE_ID==null)){
					submitFlowForQcwb(flowSubmitObj);//首次提交（需先暂存生成申报号）
	            }else{
	            	if(EXE_ID!=null){
	            		flowSubmitObj.EFLOW_EXEID = EXE_ID;
	            	}
	            	submitFlowForCg(flowSubmitObj);//（已有申报号再次提交）
	            }
			}
		}
    }
}


//提交（开始环节-执行签章首次提交，需要先暂存在提交（生成申报号））
function submitFlowForQcwb(flowSubmitObj){
	 flowSubmitObj.EFLOW_ISTEMPSAVE = "1";//暂存
     var postParam = $.param(flowSubmitObj);
	   AppUtil.ajaxProgress({
         url: "webSiteController.do?submitApply",
         params: postParam,
         callback: function (resultJson) {
             if (resultJson.OPER_SUCCESS) {
                 //暂存成功、进行提交
            	 /*art.dialog({
                     content : "友情提示：执行签章过程，需要点时间，请耐心等待哦！",
                     icon : "warning",
                     ok : true
                 });*/
             	flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";//提交
             	flowSubmitObj.EFLOW_EXEID = resultJson.EFLOW_EXEID;//获取申报号
             	$("input[name='EXE_ID']").val(resultJson.EFLOW_EXEID);
             	flowSubmitObj.EFLOW_BUSRECORDID = resultJson.EFLOW_BUSRECORDID;//获取业务表记录ID
             	postParam = $.param(flowSubmitObj);
             	//alert("申报号："+resultJson.EFLOW_EXEID);
             	 AppUtil.ajaxProgress({
                      url: "webSiteController.do?submitApply",
                      params: postParam,
                      callback: function (resultJson) {
                          if (resultJson.OPER_SUCCESS) {
                              art.dialog({
                                  content: resultJson.OPER_MSG,
                                  icon: "succeed",
                                  lock: true,
                                  ok: function () {
                                      window.top.location.href = __newUserCenter;
                                  },
                                  close: function () {
                                      window.top.location.href = __newUserCenter;
                                  }
                              });
                          } else {
                              art.dialog({
                                  content : resultJson.OPER_MSG,
                                  icon : "error",
                                  ok : true
                              });
                          }
                      }
                  });
             } else {
                 art.dialog({
                     content : resultJson.OPER_MSG,
                     icon : "error",
                     ok : true
                 });
             }
         }
     });
}


//提交（草稿库提交）
function submitFlowForCg(flowSubmitObj){
  //直接提交
  flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
  var postParam = $.param(flowSubmitObj);
  AppUtil.ajaxProgress({
      url: "webSiteController.do?submitApply",
      params: postParam,
      callback: function (resultJson) {
          if (resultJson.OPER_SUCCESS) {
              art.dialog({
                  content: resultJson.OPER_MSG,
                  icon: "succeed",
                  lock: true,
                  ok: function () {
                      window.top.location.href = __newUserCenter;
                  },
                  close: function () {
                      window.top.location.href = __newUserCenter;
                  }
              });
          } else {
              art.dialog({
                  content : resultJson.OPER_MSG,
                  icon : "error",
                  ok : true
              });
          }
      }
  });
}


/*提交事件*/
/*function submitFlow(formId){
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
		var isValid = $("#ygdyqygInfo").form('validate');
		if(isValid){			
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			if(submitMaterFileJson||submitMaterFileJson==""){
				//获取流程信息对象JSON
				var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				//将其转换成JSON对象
				var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
				if(!flowSubmitObj.busRecord){				
					parent.art.dialog({
						content: "请先暂存后提交。",
						icon:"warning",
						ok: true
					});
					return;
				}

				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

				//先获取表单上的所有值
				var formData = FlowUtil.getFormEleData(formId);
				for(var index in formData){
					flowSubmitObj[eval("index")] = formData[index];
				}
				//获取义务人信息
				flowSubmitObj.YWR_JSON = getYwrJson();	

				flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
				var postParam = $.param(flowSubmitObj);

				AppUtil.ajaxProgress({
					url: "webSiteController.do?submitApply",
					params: postParam,
					callback: function (resultJson) {
						if (resultJson.OPER_SUCCESS) {
							art.dialog({
								content: resultJson.OPER_MSG,
								icon: "succeed",
								lock: true,
								ok: function () {
									window.top.location.href = __newUserCenter;
								},
								close: function () {
									window.top.location.href = __newUserCenter;
								}
							});
						} else {
							art.dialog({
								content : resultJson.OPER_MSG,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}
    }
}*/

//不动产预告登记查询
function showSelectBdcYgdacx(){
	$.dialog.open("bdcQlcApplyController.do?bdcygdacxSelectorWw&allowCount=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var YGDJZL = bdcdydacxInfo[0].YGDJZL;
				var dyinfo;
				if(YGDJZL && YGDJZL=='预售商品房买卖预告登记'){		
					dyinfo = {
						"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
						"LOCATED":bdcdydacxInfo[0].BDCZL,
						/*"YWR_MC":bdcdydacxInfo[0].QLR, 
						"YWR_ZJLX":bdcdydacxInfo[0].QLRZJZL,
						"YWR_ZJNO":bdcdydacxInfo[0].QLRZJH,
						"DBSE":bdcdydacxInfo[0].QDJG,*/
						"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
						"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
						"DJXX_QLR":bdcdydacxInfo[0].QLR,
						"DJXX_JZAREA":bdcdydacxInfo[0].JZMJ,
						"DJXX_ZL":bdcdydacxInfo[0].BDCZL,
						/*"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
						"ZW_END":bdcdydacxInfo[0].JSSJ,*/
						"FW_ADDR":bdcdydacxInfo[0].BDCZL,
						"HT_LX":bdcdydacxInfo[0].HTLX,
						"BDCDYH":bdcdydacxInfo[0].BDCDYH,
						"YWH":bdcdydacxInfo[0].YWH,
						"GLH":bdcdydacxInfo[0].GLH,
						"APPLICANT_UNIT":bdcdydacxInfo[0].QLR,
						"HT_NO":bdcdydacxInfo[0].HTH,
						"BDC_BDCDYH": bdcdydacxInfo[0].BDCDYH
					};	
					setYwrInfo(bdcdydacxInfo[0].QLR,bdcdydacxInfo[0].QLRZJH);
					$('input[name="SBMC"]').val(bdcdydacxInfo[0].QLR+"-预购商品房抵押预告登记");
				} else {				
					dyinfo = {
						"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
						"LOCATED":bdcdydacxInfo[0].BDCZL,
						/*"YWR_MC":bdcdydacxInfo[0].QLR,
						"YWR_ZJLX":bdcdydacxInfo[0].QLRZJZL,
						"YWR_ZJNO":bdcdydacxInfo[0].QLRZJH,
						"DBSE":bdcdydacxInfo[0].QDJG, */
						"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
						"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
						"DJXX_QLR":bdcdydacxInfo[0].QLR,
						"DJXX_JZAREA":bdcdydacxInfo[0].JZMJ,
						"DJXX_ZL":bdcdydacxInfo[0].BDCZL,
						/*"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
						"ZW_END":bdcdydacxInfo[0].JSSJ,*/
						"FW_ADDR":bdcdydacxInfo[0].BDCZL,
						"HT_LX":bdcdydacxInfo[0].HTLX,
						"BDCDYH":bdcdydacxInfo[0].BDCDYH,
						"YWH":bdcdydacxInfo[0].YWH,
						"GLH":bdcdydacxInfo[0].GLH,
						"APPLICANT_UNIT":bdcdydacxInfo[0].YWR,
						"HT_NO":bdcdydacxInfo[0].HTH,
						"BDC_BDCDYH": bdcdydacxInfo[0].BDCDYH
					};	
					setYwrInfo(bdcdydacxInfo[0].YWR,bdcdydacxInfo[0].YWRZJH);
					$('input[name="SBMC"]').val(bdcdydacxInfo[0].YWR+"-预购商品房抵押预告登记");
				}
				/*获取房屋单元信息*/
				$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcdydacxInfo[0].BDCDYH}, function (responseText, status, xhr) {
					$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
					if(responseText.rows && responseText.rows.length>0){
						$("[name='YTSM']").val(responseText.rows[0].GHYT);
						$("[name='FWXZ']").val(responseText.rows[0].FWXZ);
					}
				})
				initFormFieldValueToReadOnly(dyinfo,"T_BDCQLC_YGSPFDYQYGDJ_FORM");
				/*将预告信息存入数据库*/
				$("input[name='YGXX_JSON']").val(JSON.stringify(bdcdydacxInfo));	
				
				
				//$("#QLR_MC").combobox("setValue",bdcdydacxInfo[0].QLR);	
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
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementId
 */
function initFormFieldValueToReadOnly(fieldValueObj,elementId){
	for(var fieldName in fieldValueObj){
		//获取目标控件对象
		var targetFields = $("#"+elementId).find("[name='"+fieldName+"']");
		targetFields.each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = fieldValueObj[fieldName];
			if(type=="radio"){
				var radioValue = targetField.val();
				if(radioValue==fieldValue){
					$(this).attr("checked","checked");
				}
			}else if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}else if(tagName=="SELECT"){
				targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
				.prop("selected", "selected");
			}else{
				targetField.val(fieldValueObj[fieldName]);
			}
			//targetField.attr("disabled", "disabled");
	   });
	}
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
						if(list[0].CQZT.indexOf("预售预告 已售") != -1 || list[0].CQZT=='预售预告'){				
							result = {
								"isPass":true,
								"msg":""
							};
						} else{							
							result = {
								"isPass":true,
								"msg":"请注意，该不动产单元号为“"+list[0].CQZT+"”状态，是否继续办理业务?",
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

		

/*转换证件类型*/
function changeDocumentType(type) {
    var str = "";
    switch (type) {
        case '1':
            str = "身份证";
            break;
        case '2':
            str = "港澳台身份证";
            break;
        case '3':
            str = "护照";
            break;
        case '4':
            str = "户口簿";
            break;
        case '5':
            str = "军官证（士兵证）";
            break;
        case '6':
            str = "组织机构代码";
            break;
        case '7':
            str = "营业执照";
            break;
        case '8':
            str = "港澳通行证";
            break;
        case '9':
            str = "台胞证";
            break;
        case '10':
            str = "统一社会信用代码";
            break;
        case '99':
            str = "其它";
    }
    return str;
}

function setYwrInfo(YWR_MC,YWR_ZJNO){	
	var sign = [',',';','|'];
	var names;
	var cards;
	for(var i = 0; i < sign.length; i++){
		if(YWR_MC.indexOf(sign[i])!=-1){
			names = YWR_MC.split(sign[i]);
		}
		if(YWR_ZJNO.indexOf(sign[i])!=-1){
			cards = YWR_ZJNO.split(sign[i]);
		}
	}
	var div = $("#ywrDiv").children("div");
	if(names){		
		for (var i = 0; i < names.length; i++) {
			if(div.length>=(Number(i)+Number(1))){
				$("#ywrDiv").find('table').eq(i).find("[name$='YWR_MC']").val(names[i]);
				$("#ywrDiv").find('table').eq(i).find("[name$='YWR_ZJNO']").val(cards[i]);
			} else{			
				var data = {};
				data.YWR_MC = names[i];	
				data.YWR_ZJLX = "身份证";
				data.YWR_ZJNO = cards[i];	
				initAddYwrDiv(data,i);
			}
		}
	}else{		
		$("#ywrDiv").find('table').eq(0).find("[name$='YWR_MC']").val(YWR_MC);
		$("#ywrDiv").find('table').eq(0).find("[name$='YWR_ZJNO']").val(YWR_ZJNO);
	}
}

function initAutoTable(flowSubmitObj) {
	var ywrJson = flowSubmitObj.busRecord.YWR_JSON;
	if (null != ywrJson && '' != ywrJson) {
		var ywrInfos = eval(ywrJson);
		for (var i = 0; i < ywrInfos.length; i++) {
			if (i == 0) {
				$("#ywrDiv").children("div").eq(i).find("[name$='YWR_MC']").val(ywrInfos[i].YWR_MC);
				$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJLX']").val(ywrInfos[i].YWR_ZJLX);
				$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").val(ywrInfos[i].YWR_ZJNO);
				$("#ywrDiv").children("div").eq(i).find("[name$='YWR_SJHM']").val(ywrInfos[i].YWR_SJHM);
				if(ywrInfos[i].YWR_ZJLX=="身份证"){					
					$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").addClass(",custom[vidcard]");
				}else{					
					$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").removeClass(",custom[vidcard]");
				}
			} else {
				/* var isok =  false;
				if(flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
					isok = true;
				} */
				initAddYwrAutoDiv(ywrInfos[i],i,true);
			}
		}
	}
	
}


/*************义务人JS开始****************/
/**
 * 添加义务人信息
 */
function addYwrDiv(){
	$.post("bdcYgspfygdjController/refreshYwrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#ywrDiv").append(responseText);
		$('#ywrDiv').find('input,textarea').attr("disabled", "disabled");
		$('#ywrDiv').find('select').attr("disabled", "disabled");
		$("[name='YWR_SJHM']").removeAttr("disabled");
	});
}
/**
 * 添加义务人信息
 */
function initAddYwrDiv(data,i){
	$.post("bdcYgspfygdjController/refreshYwrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#ywrDiv").append(responseText);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_MC']").val(data.YWR_MC);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJLX']").val(data.YWR_ZJLX);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").val(data.YWR_ZJNO);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_SJHM']").val(data.YWR_SJHM);
		
		$("#ywrDiv").children("div").eq(i).find("a").hide();
		
		//$('#ywrDiv').find('input,textarea').attr("disabled", "disabled");
		//$('#ywrDiv').find('select').attr("disabled", "disabled");
		$('#ywrDiv').find('input,textarea').removeAttr("disabled");
		$('#ywrDiv').find('select').removeAttr("disabled");
		$("[name='YWR_SJHM']").removeAttr("disabled");
		$("input[name$='YWR_ZJNO']").on('blur', function(event) {		
			$(this).val($(this).val().toUpperCase());
		});
	});
}
/**
 * 添加义务人信息
 */
function initAddYwrAutoDiv(data,i,isok){
	$.post("bdcYgspfygdjController/refreshYwrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#ywrDiv").append(responseText);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_MC']").val(data.YWR_MC);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJLX']").val(data.YWR_ZJLX);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").val(data.YWR_ZJNO);
		$("#ywrDiv").children("div").eq(i).find("[name$='YWR_SJHM']").val(data.YWR_SJHM);		
		if(data.YWR_ZJLX=="身份证"){					
			$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").addClass(",custom[vidcard]");
		}else{					
			$("#ywrDiv").children("div").eq(i).find("[name$='YWR_ZJNO']").removeClass(",custom[vidcard]");
		}
		if(isok){			
			$("#ywrDiv").children("div").eq(i).find("a").hide();
		}
		
		$('#ywrDiv').find('input,textarea').removeAttr("disabled");
		$('#ywrDiv').find('select').removeAttr("disabled");
		$("[name='YWR_SJHM']").removeAttr("disabled");
		$("input[name$='YWR_ZJNO']").on('blur', function(event) {		
			$(this).val($(this).val().toUpperCase());
		});
	});
}

/**
 * 删除义务人信息
 */
function delYwrDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取义务人信息
 */
function getYwrJson(){
	var dataArray = [];
	$("#ywrDiv").children("div").each(function(i){
		var YWR_MC = $(this).find("[name$='YWR_MC']").val();//姓名
		var YWR_ZJLX = $(this).find("[name$='YWR_ZJLX']").val();//证件类别
		var YWR_ZJNO = $(this).find("[name$='YWR_ZJNO']").val();//证件号码
		var YWR_SJHM = $(this).find("[name$='YWR_SJHM']").val();//手机号码
		if(YWR_MC || YWR_ZJLX || YWR_ZJNO || YWR_SJHM){
			var data = {};
			data.YWR_MC = YWR_MC;	
			data.YWR_ZJLX = YWR_ZJLX;	
			data.YWR_ZJNO = YWR_ZJNO;	
			data.YWR_SJHM = YWR_SJHM;	
			dataArray.push(data);	
		}
		
	});	
	if(dataArray.length>0){
		return JSON.stringify(dataArray);
	}else{
		return "";
	}
}
/*************义务人JS结束****************/



/*格式化证件类别*/
function formatZjlx(zjlx) {
    if (zjlx) {
		if (zjlx == '统一社会编码'){
            return "统一社会信用代码";
        } else {
            return zjlx;
        }
    } 
	return zjlx;
}


//是否在线签章
function openSelector() {
  $.dialog.open("bdcDyqzxdjController.do?busSelectorItemView", {
      title : "业务选择",
      width : "600px",
      height : "200px",
      lock : true,
      resize : false,
      close: function () {
          var busSelectData = art.dialog.data("busSelectData");
          if (busSelectData) {
              var IS_SIGN = busSelectData.IS_SIGN;
              if (IS_SIGN == '1') {
              	$("input[name='ISQCWB']").val("1");
              }else{
              	$("input[name='ISQCWB']").val("0");
              }
              
          } else {
              window.location.href = __ctxPath + '/contentController/list.do?moduleId=605';
          }
      }
  }, false);
}
