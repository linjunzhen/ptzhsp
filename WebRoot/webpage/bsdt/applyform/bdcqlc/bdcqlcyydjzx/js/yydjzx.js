
function FLOW_SubmitFun(flowSubmitObj){	
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDCQLC_YYDJZX_FORM").validationEngine("validate");
	 if(validateResult){		 
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
				 var formData = FlowUtil.getFormEleData("T_BDCQLC_YYDJZX_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
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
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_YYDJZX_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

function initAutoTable(flowSubmitObj){
	
	var qslyJson = flowSubmitObj.busRecord.QSLY_JSON;
	if(null != qslyJson && '' != qslyJson){
		var qslyInfos = eval(qslyJson);
		for(var i=0;i<qslyInfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(qslyInfos[i],"qslyInfo_1");
				$("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
			}else{
				addQslyInfo();
				FlowUtil.initFormFieldValue(qslyInfos[i],"qslyInfo_"+(i+1));
				$("#qslyInfo_"+(i+1)+" input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
			}
		}
	}
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
	/* var text = $("select[name='QLR_MC']").find("option:selected").text();
	$("input[name='QLR_ZJNO']").val(val);
	$("input[name='QLR_LABEL']").val(text); */
}

//不动产预告登记查询
function showSelectBdcYgdacx(){
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var dyinfo = {
					"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
					"LOCATED":bdcdydacxInfo[0].BDCZL,
					"ZRFXM":bdcdydacxInfo[0].YWR,
					"ZRFZJLB":bdcdydacxInfo[0].YWRZJZL,
					"ZRFZJHM":bdcdydacxInfo[0].YWRZJH,
					"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
					"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
					"MSFXM":bdcdydacxInfo[0].QLR,
					"MSFZJLB":bdcdydacxInfo[0].QLRZJZL,
					"MSFZJHM":bdcdydacxInfo[0].QLRZJH,
					"JZMJ":bdcdydacxInfo[0].JZMJ,
					"CJJG":bdcdydacxInfo[0].QDJG,
					"FWDZ":bdcdydacxInfo[0].BDCZL,
					"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
					"ZW_END":bdcdydacxInfo[0].JSSJ,
					"HTLX":bdcdydacxInfo[0].HTLX,
					"BDCDYH":bdcdydacxInfo[0].BDCDYH,
					"YWH":bdcdydacxInfo[0].YWH,
					"GLH":bdcdydacxInfo[0].GLH,
					"APPLICANT_UNIT":bdcdydacxInfo[0].QLR,
					"HT_NO":bdcdydacxInfo[0].HTH,
					"HT_LX":bdcdydacxInfo[0].HTLX
				};	
				//var result = checkFwCqzt(dyinfo.ESTATE_NUM);
				//if(result.isPass){
					FlowUtil.initFormFieldValue(dyinfo,"T_BDCQLC_YYDJZX_FORM");		
					//$("#QLR_MC").combobox("setValue", bdcdydacxInfo[0].QLR);	
					//申报对象信息回填
					$('input[name="SQRMC"]').val(bdcdydacxInfo[0].QLR);//申请人
					$('#SQRZJLX').find("option").filter(function(index){
						return bdcdydacxInfo[0].QLRZJZL===$(this).text();
					}).attr("selected","selected");//证件类别
					$('input[name="SQRSFZ"]').val(bdcdydacxInfo[0].QLRZJH);//证件号码	
				//}else{
				//	parent.art.dialog({
				//		lock: true,
				//		content: result.msg,
				//		icon:"warning",
				//		ok: true
				//	});
				//}
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
