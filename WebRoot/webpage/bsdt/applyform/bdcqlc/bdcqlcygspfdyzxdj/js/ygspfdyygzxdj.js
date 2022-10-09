
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="SF"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}
function setQLRName(val){
	var datas = $('#QLR').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='QLRZJH']").val(datas[i].DIC_CODE);
			break ;
		}
	}
	$("input[name='QLR_LABEL']").val(val); 
}

//不动产预告档案查询
function showSelectBdcYgdacx(){
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var dyinfo = bdcdydacxInfo[0];
				dyinfo["ESTATE_NUM"] = bdcdydacxInfo[0].BDCDYH;
				dyinfo["LOCATED"] = bdcdydacxInfo[0].BDCZL;
				dyinfo["APPLICANT_UNIT"] = bdcdydacxInfo[0].QLR;
				var result = checkFwCqzt(dyinfo.BDCDYH);
				if(result.isPass){
					/*获取房屋单元信息*/
					$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcdydacxInfo[0].BDCDYH}, function (responseText, status, xhr) {
						$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
					})

					/*将预告信息存入数据库*/
					$("input[name='YGXX_JSON']").val(JSON.stringify(bdcdydacxInfo));

					FlowUtil.initFormFieldValue(dyinfo,"T_BDCQLC_YGSPFDYYGZXDJ_FORM");		
					//$("#QLR").combobox("setValue", bdcdydacxInfo[0].QLR);
					//申报对象信息回填
					$('input[name="SQRMC"]').val(bdcdydacxInfo[0].QLR);//申请人
					$('#SQRZJLX').find("option").filter(function(index){
						return bdcdydacxInfo[0].QLRZJZL===$(this).text();
					}).attr("selected","selected");//证件类别
					$('input[name="SQRSFZ"]').val(bdcdydacxInfo[0].QLRZJH);//证件号码	
				}else{
					parent.art.dialog({
						lock: true,
						content: result.msg,
						icon:"warning",
						ok: true
					});
				}
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

/**
 * 登簿操作
 */
function bdcYgspfdyygzxdjBooking(){
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
