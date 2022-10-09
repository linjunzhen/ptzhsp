
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}
//设置用途说明
function setYtms(value){
	$("input[name='YTMS']").val(value);
}

//房地产合同备案查询
function showSelectFdchtbacxWw(){
    $.dialog.open("bdcQlcApplyController.do?fdchtbaxxcxSelectorWw&allowCount=1", {
        title : "房地产合同备案信息查询",
        width:"100%",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {
            var fdchtbaxxInfo = art.dialog.data("fdchtbaxxInfo");
            if (fdchtbaxxInfo && fdchtbaxxInfo.length == 1) {
                /*保存合同备案信息json*/
				$("input[name='HTBAXX_JSON']").val(JSON.stringify(fdchtbaxxInfo));
				/*回填数据*/
				for(var i = 0;i<fdchtbaxxInfo.length;i++){
					$('input[name="ZL"]').val(fdchtbaxxInfo[i].ZL);//坐落
					$('input[name="BDCDYH"]').val(fdchtbaxxInfo[i].BDCDYH);//不动产单元号
					$("input[name='BDC_BDCDYH']").val(fdchtbaxxInfo[i].BDCDYH);//不动产单元号
					$('input[name="FWDZ"]').val(fdchtbaxxInfo[i].ZL);//房屋地址
					$('input[name="APPLICANT_UNIT"]').val(fdchtbaxxInfo[i].MSFXM);//申请人
					$('input[name="YTMS"]').val(fdchtbaxxInfo[i].YTSM);//用途说明
					$('[name="YTSM"]').val(fdchtbaxxInfo[i].YTSM);//用途说明
					initFormFieldValueToReadOnly(fdchtbaxxInfo[i],"ygygInfo");//预购预告信息回填
					
					$('[name="ZRFZJLB"]').val(formatZjlx(fdchtbaxxInfo[i].ZRFZJLB));//义务人证件种类
					setZjValidate(fdchtbaxxInfo[i].ZRFZJLB,'ZRFZJHM');
					
					//回填权利人
					setQlrInfo(fdchtbaxxInfo[i].MSFXM,fdchtbaxxInfo[i].MSFZJHM);
					
					$('input[name="SBMC"]').val(fdchtbaxxInfo[i].MSFXM+"-预购商品房预告登记");
					 
					//回填申报对象信息
					setSbdxInfo(fdchtbaxxInfo[i]);
				}
				/*获取房屋单元信息*/
				$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:fdchtbaxxInfo[0].BDCDYH}, function (responseText, status, xhr) {
					$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
					$("[name='YTMS']").val(responseText.rows[0].YTSM);//用途说明
					$('[name="YTSM"]').val(responseText.rows[0].GHYT);//规划用途
					$('[name="FWXZ"]').val(responseText.rows[0].FWXZ);//房屋性质
				})
				$('input[name="ZL"]').attr("disabled", "disabled");
				$('input[name="BDCDYH"]').attr("disabled", "disabled");
				$("input[name='BDC_BDCDYH']").attr("disabled", "disabled");
				$('input[name="FWDZ"]').attr("disabled", "disabled");
				$('input[name="APPLICANT_UNIT"]').attr("disabled", "disabled");
				$('input[name="YTMS"]').attr("disabled", "disabled");
				$('[name="YTSM"]').attr("disabled", "disabled");
				$('[name="FWXZ"]').attr("disabled", "disabled");
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
	
	//获取权利人信息
	flowSubmitObj.QLR_JSON = getQlrJson();
	

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
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            
			if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
				//判断此业务是否已被办理过
				var bdcdyh = $("input[name='BDCDYH']").val();
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
			//获取权利人信息
			flowSubmitObj.QLR_JSON = getQlrJson();	
			
			//判断是否首次提交
			//alert("流程数据："+flowSubmitObj.busRecord+"--申报号："+EXE_ID);
			if((flowSubmitObj.busRecord=="" || flowSubmitObj.busRecord==undefined || flowSubmitObj.busRecord==null )
				&& (EXE_ID=="" || EXE_ID==undefined || EXE_ID==null)){
				submitFlowForZnsp(flowSubmitObj);//首次提交（需先暂存生成申报号）
            }else{
            	if(EXE_ID!=null){
            		flowSubmitObj.EFLOW_EXEID = EXE_ID;
            	}
            	submitFlowForCg(flowSubmitObj);//（已有申报号再次提交）
            }
        }
    }
}

//提交（开始环节-执行签章首次提交，需要先暂存在提交（生成申报号））
function submitFlowForZnsp(flowSubmitObj){
   flowSubmitObj.EFLOW_ISTEMPSAVE = "1";//暂存
   var postParam = $.param(flowSubmitObj);
	   AppUtil.ajaxProgress({
       url: "webSiteController.do?submitApply",
       params: postParam,
       callback: function (resultJson) {
           if (resultJson.OPER_SUCCESS) {//暂存成功进行提交
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

			if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
				判断此业务是否已被办理过
				var bdcdyh = $("input[name='BDCDYH']").val();
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
			//获取权利人信息
			flowSubmitObj.QLR_JSON = getQlrJson();	

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
}*/

//流程暂存方法
function FLOW_TempSaveFun(flowSubmitObj){
	//权利人信息
	getQlrInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_YGSPFYGDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

//流程退回方法
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
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


//不动产登记证明打印与预览
function bdcYgspfygdjDJZMprint(typeId){
	// typeId: 1为证书预览  2为证书打印
	var title = "证书打印";
	var templateAlias = 'DJZM';
	if(typeId==1) {
		title = "证书预览";
	}
	var dataStr = "";
	dataStr +="&QLRZJH="+$("input[name='MSFZJHM']").val();
	dataStr +="&BDCQZH="+$("input[name='BDCDYH']").val();
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

/**
 * 预购预告登簿操作
 */
function bdcYgspfygdjBooking(){
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
							var qzinfo = data.qzinfo[0];
							$("input[name='BDC_DBZT']").val(qzinfo.gdzt);
							if(qzinfo.gdzt == "1"){
								$("input[name='BDC_BDCDJZMH']").val(qzinfo.qzzh);
								$("input[name='BDC_DBJG']").val("登簿成功");
								$("input[name='BDC_DBR']").val(currentUser);
								$("input[name='BDC_DJSJ']").val(currentTime);
								art.dialog({
									content :"登簿成功",
									icon : "succeed",
									ok : true
								});
							}else{
								$("input[name='BDC_DBJG']").val(qzinfo.ret_msg);
								art.dialog({
									content :"归档失败，"+qzinfo.ret_msg,
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



function setQlrInfo(MSFXM,MSFZJHM){
	var sign = [',',';','|'];
	var names;
	var cards;
	for(var i = 0; i < sign.length; i++){
		if(MSFXM.indexOf(sign[i])!=-1){
			names = MSFXM.split(sign[i]);
		}
		if(MSFZJHM.indexOf(sign[i])!=-1){
			cards = MSFZJHM.split(sign[i]);
		}
	}
	var div = $("#qlrDiv").children("div");
	if(names){	
		for (var i = 0; i < names.length; i++) {
			if(div.length>=(Number(i)+Number(1))){
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFXM']").val(names[i]);
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").val(cards[i]);
			} else{			
				var data = {};
				data.MSFXM = names[i];	
				data.MSFZJLB = "身份证";
				data.MSFZJHM = cards[i];	
				initAddQlrDiv(data,i);
			}
		}
	} else{		
		$("#qlrInfo").find('table').eq(0).find("[name$='MSFXM']").val(MSFXM);
		$("#qlrInfo").find('table').eq(0).find("[name$='MSFZJHM']").val(MSFZJHM);
	}
}


function setSbdxInfo(fdchtbaxxInfo){
	//if($("[name$='SQRMC']").val().trim()==""){
		$("[name$='SQRMC']").val(fdchtbaxxInfo.MSFXM);
	//}
	//if($("[name$='SQRSFZ']").val().trim()==""){
		$("[name$='SQRSFZ']").val(fdchtbaxxInfo.MSFZJHM);
	//}
}

function initAutoTable(flowSubmitObj) {
	var qlrJson = flowSubmitObj.busRecord.QLR_JSON;
	if (null != qlrJson && '' != qlrJson) {
		var qlrInfos = eval(qlrJson);
		for (var i = 0; i < qlrInfos.length; i++) {
			if (i == 0) {
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFXM']").val(qlrInfos[i].MSFXM);
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJLB']").val(qlrInfos[i].MSFZJLB);
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").val(qlrInfos[i].MSFZJHM);
				$("#qlrDiv").children("div").eq(i).find("[name$='MSFSJHM']").val(qlrInfos[i].MSFSJHM);
				if(qlrInfos[i].MSFZJLB=="身份证"){					
					$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").addClass(",custom[vidcard]");
				}else{					
					$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").removeClass(",custom[vidcard]");
				}
			} else {
				/* var isok =  false;
				if(flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
					isok = true;
				} */
				initAddQlrAutoDiv(qlrInfos[i],i,true);
			}
		}
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
						result = {
							"isPass":true,
							"msg":"请注意，该不动产单元号为“"+list[0].CQZT+"”状态，是否继续办理业务?",
							"type":"0"
						};	
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



/*************权利人JS开始****************/
/**
 * 添加权利人信息
 */
function addQlrDiv(){
	$.post("bdcYgspfygdjController/refreshQlrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qlrDiv").append(responseText);
		$('#qlrDiv').find('input,textarea').attr("disabled", "disabled");
		$('#qlrDiv').find('select').attr("disabled", "disabled");
		$("[name='MSFSJHM']").removeAttr("disabled");
	});
}
/**
 * 添加权利人信息
 */
function initAddQlrDiv(data,i){
	$.post("bdcYgspfygdjController/refreshQlrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qlrDiv").append(responseText);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFXM']").val(data.MSFXM);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJLB']").val(data.MSFZJLB);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").val(data.MSFZJHM);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFSJHM']").val(data.MSFSJHM);
		
		$("#qlrDiv").children("div").eq(i).find("a").hide();
		
		$('#qlrDiv').find('input,textarea').attr("disabled", "disabled");
		$('#qlrDiv').find('select').attr("disabled", "disabled");
		/*$('#qlrDiv').find('input,textarea').removeAttr("disabled");
		$('#qlrDiv').find('select').removeAttr("disabled");*/
		$('#qlrDiv').find("[name$='MSFSJHM']").removeAttr("disabled");
		$('#qlrDiv').find("[name$='MSFZJLB']").removeAttr("disabled");
		
		$("input[name$='MSFZJHM']").on('blur', function(event) { 	
			$(this).val($(this).val().toUpperCase());
		});
	});
}
/**
 * 添加权利人信息
 */
function initAddQlrAutoDiv(data,i,isok){
	$.post("bdcYgspfygdjController/refreshQlrDiv.do",{
	}, function(responseText, status, xhr) {
		$("#qlrDiv").append(responseText);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFXM']").val(data.MSFXM);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJLB']").val(data.MSFZJLB);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").val(data.MSFZJHM);
		$("#qlrDiv").children("div").eq(i).find("[name$='MSFSJHM']").val(data.MSFSJHM);		
		if(data.MSFZJLB=="身份证"){					
			$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").addClass(",custom[vidcard]");
		}else{					
			$("#qlrDiv").children("div").eq(i).find("[name$='MSFZJHM']").removeClass(",custom[vidcard]");
		}
		if(isok){			
			$("#qlrDiv").children("div").eq(i).find("a").hide();
		}
		
		$('#qlrDiv').find('input,textarea').attr("disabled", "disabled");
		$('#qlrDiv').find('select').attr("disabled", "disabled");
		/*$('#qlrDiv').find('input,textarea').removeAttr("disabled");
		$('#qlrDiv').find('select').removeAttr("disabled");*/
		$('#qlrDiv').find("[name$='MSFSJHM']").removeAttr("disabled");
		$('#qlrDiv').find("[name$='MSFZJLB']").removeAttr("disabled");
		$("input[name$='MSFZJHM']").on('blur', function(event) {		
			$(this).val($(this).val().toUpperCase());
		});
	});
}

/**
 * 删除权利人信息
 */
function delQlrDiv(o){
	$(o).closest("div").remove();
} 

/**
 * 获取权利人信息
 */
function getQlrJson(){
	var dataArray = [];
	$("#qlrDiv").children("div").each(function(i){
		var MSFXM = $(this).find("[name$='MSFXM']").val();//姓名
		var MSFZJLB = $(this).find("[name$='MSFZJLB']").val();//证件类别
		var MSFZJHM = $(this).find("[name$='MSFZJHM']").val();//证件号码
		var MSFSJHM = $(this).find("[name$='MSFSJHM']").val();//手机号码
		if(MSFXM || MSFZJLB || MSFZJHM || MSFSJHM){
			var data = {};
			data.MSFXM = MSFXM;	
			data.MSFZJLB = MSFZJLB;	
			data.MSFZJHM = MSFZJHM;	
			data.MSFSJHM = MSFSJHM;	
			dataArray.push(data);	
		}
		
	});	
	if(dataArray.length>0){
		return JSON.stringify(dataArray);
	}else{
		return "";
	}
}
/*************权利人JS结束****************/



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
