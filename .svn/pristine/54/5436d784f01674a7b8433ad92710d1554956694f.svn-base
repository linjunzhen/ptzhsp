
//表单提交方法
function FLOW_SubmitFun(flowSubmitObj){	
	//先判断表单是否验证通过
	 var validateResult =$("#T_BDCQLC_YYDJ_FORM").validationEngine("validate");
	 if(validateResult){
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
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
			 // 获取发证明细数据
			 getDjfzxxJson_yydj();
			 // 获取缴费信息
			 getDjjfxxJson_yydj();			 
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_BDCQLC_YYDJ_FORM");
			 for(var index in formData){
				 flowSubmitObj[eval("index")] = formData[index];
			 }
			 return flowSubmitObj;
		 }else{
			 return null;
		 }			 
	 }else{
		 return null;
	 }
	
}



//流程暂存
function FLOW_TempSaveFun(flowSubmitObj){
	 // 获取发证明细数据
	 getDjfzxxJson_yydj();
	 // 获取缴费信息
	 getDjjfxxJson_yydj();   
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_YYDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
		 
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx, name){
	if (zjlx == "SF") {
		$("input[name='" + name + "']").addClass(",custom[vidcard]");
	} else {
		$("input[name='" + name + "']").removeClass(",custom[vidcard]");
	}
}

//查询不动产档案信息
function showSelectBdcdaxxcx() {	
	$.dialog.open("bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");		
			if(bdcdaxxcxInfo!=undefined && bdcdaxxcxInfo!=null && bdcdaxxcxInfo!=""){			
				var qszt = bdcdaxxcxInfo[0].QSZT.trim();//权属状态（非现势的不可受理登记）				
				if(qszt=='现势'){//状态为"现势",数据回填
					$("input[name='ESTATE_NUM']").val(bdcdaxxcxInfo[0].BDCDYH);//不动产单元号
					$("input[name='YYDXBDC_BDCDYH']").val(bdcdaxxcxInfo[0].BDCDYH);//不动产单元号					
					$("input[name='LOCATED']").val(bdcdaxxcxInfo[0].FDZL);//房地坐落
					$("input[name='YYDXBDC_BDCQZH']").val(bdcdaxxcxInfo[0].BDCQZH);//不动产权证号
					$("input[name='YYDXBDC_QLR']").val(bdcdaxxcxInfo[0].QLRMC);//权利人
					$("input[name='YYDXBDC_QLRZJH']").val(bdcdaxxcxInfo[0].ZJHM);//权利人证件号码					
					$("input[name='YYDXBDC_ZL']").val(bdcdaxxcxInfo[0].FDZL);//坐落
					$("input[name='YYDXBDC_QLLX']").val(bdcdaxxcxInfo[0].FW_QLLX);//权利类型
					$("input[name='YYDXBDC_MJ']").val(bdcdaxxcxInfo[0].JZMJ);//面积
					$("input[name='JBXX_FJ']").val(bdcdaxxcxInfo[0].FJ);//附记
					$("input[name='YYDXBDC_QLRZJZL']").val(bdcdaxxcxInfo[0].ZJLX);//权利人证件种类
					$("input[name='YYDXBDC_QLXZ']").val(bdcdaxxcxInfo[0].QLXZ);//权利性质
					$("input[name='YYDXBDC_BDCLX']").val(bdcdaxxcxInfo[0].BDCLX);//不动产类型
					$("input[name='YYDXBDC_GLH']").val(bdcdaxxcxInfo[0].GLH);//关联号					
				}else{
					parent.art.dialog({
						content: "请注意，该不动产单元号权属状态为"+qszt+"，因此不可受理此登记。",
						icon:"warning",
						ok: true
					});
				}										
				art.dialog.removeData("bdcdaxxcxInfo");	
			}
	   }
	}, false);
}

//打印审批表（初始化提示）
function errorAction(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}


//打印审批表
function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板、3代表阅办模板
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

//初始化JSON格式数据
function initAutoTable(flowSubmitObj){
	//登记发证信息
	var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
	if(null != djfzxx_json && '' != djfzxx_json){
		var djfzxx_jsonItems = JSON.parse(djfzxx_json);
		initDjfzxx_yydj(djfzxx_jsonItems);
	}
	//登记缴费明细
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_yydj(djjfmx_jsonItems);
	}
}


/**
 * 登簿操作
 */
function bdcYydjBooking(){
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
							if(qzinfo.gdzt == "1"){//归档状态
								$("input[name='YYDJRB_BDCDJZMH']").val(qzinfo.qzzh);//不动产登记证明号
								$("input[name='BDC_DBJG']").val("登簿成功");
								$("input[name='YYDJRB_DBR']").val(currentUser);
								$("input[name='YYDJRB_DJSJ']").val(currentTime);
								$("select[name='YYDJRB_QXDM']").val("351001");
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

//不动产登记证明打印与预览
function bdcDJZMprint(typeId){
	var YYDJRB_BDCDJZMH = $("input[name='YYDJRB_BDCDJZMH']").val();
	if(YYDJRB_BDCDJZMH){
		// typeId: 1为证书预览  2为证书打印   
		var title = "证书打印";
		var templateAlias = 'BDCQZ';//不动产权证书打印
	    if(typeId==1) {
	    	title = "证书预览";
	    }
		var dataStr = "";
		dataStr +="&QLRZJH="+$("input[name='YYDXBDC_QLRZJH']").val();//权利人证件号码
		dataStr +="&BDCQZH="+$("input[name='YYDJRB_BDCDJZMH']").val();//不动产权证号
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
			content :"不动产登记证明号未填写。",
			icon : "warning",
			ok : true
		});
	}
}



