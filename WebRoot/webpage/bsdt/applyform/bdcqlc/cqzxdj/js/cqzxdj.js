//流程表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDCQLC_CQZXDJ_FORM").validationEngine("validate");
		if(validateResult){
			getZxmxInfoJson();//获取注销明细JSON 
			// 提交流程设值
			$("#DJSH_GGQSSJ").val();
			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始'){
				if($(".bdczxxxcxTr").length<1){
					parent.art.dialog({
						content: "注销明细为空，请添加！",
						icon:"warning",
						ok: true
					});
					return;
				}
			}
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
					 var formData = FlowUtil.getFormEleData("T_BDCQLC_CQZXDJ_FORM");
					 for(var index in formData){
						 flowSubmitObj[eval("index")] = formData[index];
					 }
					 console.dir(flowSubmitObj);	
					return flowSubmitObj;
				 }else{
					 return null;
				 }
		     }			 
		}else{
			 return null;
		}		
}

//流程暂存方法
function FLOW_TempSaveFun(flowSubmitObj){
	getZxmxInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDCQLC_CQZXDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}

function isButtonAvailable(){
	var result = {};
	result["ITEM_CODE"] = $("input[name='ITEM_CODE']").val();
	result["EXE_ID"] = $("#EXEID").text();
	result["CUR_STEPNAMES"] = $("#CUR_STEPNAMES").val();
	$.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
		  var itemList = resultJson.rows;
          var newhtml = "";
          for(var i=0; i<itemList.length; i++){
        	  if(itemList[i].READ_NAME=='窗口受理通知单【不动产】'){
        		  $("#dyslhzd").attr("onclick","goPrintTemplate('"+itemList[i].READ_NAME+"',3)");
        	  }else if(itemList[i].READ_NAME=='不动产申请书通用模板'){
        		  $("#dysqb").attr("onclick","goPrintTemplate('"+itemList[i].READ_NAME+"',3)");
        	  }else if(itemList[i].READ_NAME=='窗口不予受理件通知单'){
        		  $("#dybysl").attr("onclick","goPrintTemplate('"+itemList[i].READ_NAME+"',3)");
        	  }else if(itemList[i].READ_NAME=='窗口补办件通知单'){
        		  $("#dybjcl").attr("onclick","goPrintTemplate('"+itemList[i].READ_NAME+"',3)");
        	  }
          }
	});
	/*$.post("certificateTemplateController.do?selectedPrint",result, function(resultJson) {
		  var itemList = resultJson.rows;
          var newhtml = "";
          console.log(itemList[0].CERTIFICATE_NAME);
          if(itemList.length > 0) {
        	  $("#dyzs").attr("onclick","goPrintTemplate('"+itemList[0].CERTIFICATE_NAME+"',4)");
          }
	});*/
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



/**
 * 获取注销明细JSON
 */
function getZxmxInfoJson(){
	var datas = [];
	 $("#zxmxTable tr").each(function(i){
		var bdczxxx = $(this).find("[name='bdczxxx']").val();
		if(''!=bdczxxx && null!=bdczxxx){
			datas.push(JSON.parse(bdczxxx));
		}
	 });
	var zxmxJson = JSON.stringify(datas);
	$("input[type='hidden'][name='ZXMX_JSON']").val(zxmxJson);	
}	
		
/**=================注销明细信息开始================================*/

//不动产档案信息查询
function showSelectBdcdaxxcx(){	
	$.dialog.open("bdcqZxdjController.do?selector&allowCount=0", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
		    var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
			if(bdcdydacxInfo!=undefined&&bdcdydacxInfo!=null&&bdcdydacxInfo!=""){
			    var html = "";
				for(var i = 0;i<bdcdydacxInfo.length;i++){
					html +='<tr class="bdczxxxcxTr" id="bdczxxxcxTr_'+bdczxxxcxTr+'">';
					html +='<input type="hidden" name="bdczxxx"/>';	 
					html +='<td style="text-align: center;">'+bdczxxxcxTr+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].BDCQZH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].QLRMC+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].FDZL+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].QLXZ+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].TDYT+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].ZDQLLX+'</td>';
					html +='<td style="text-align: center;">';
					html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" class="syj-closebtn"></a></td>';
					html +='</tr>';
					$("#zxmxTable").append(html);
					$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(bdcdydacxInfo[i]));
					bdczxxxcxTr ++;
					html = "";
				}	
			    art.dialog.removeData("bdcdydacxInfo");		
			    loadBdczxxxcxToId();
			}
		}
	
	}, false);
};

/**
 * 删除注销明细
 */
function delZxmxTr(o){
	$(o).closest("tr").remove();
	loadBdczxxxcxToId();
};
 
function loadBdczxxxcxToId(){
	$("#zxmxDetail input[type='text']").val('');
	$("#zxmxDetail select").val('');	
	if($(".bdczxxxcxTr").length>=1){		
		var bdczxxx = $("#zxmxTable").find("tr").eq(1).find("[name='bdczxxx']").val();
		FlowUtil.initFormFieldValue(JSON.parse(bdczxxx),"zxmxDetail");
		var trId = $("#zxmxTable").find("tr").eq(1).attr("id");
		$("#zxmxDetail input[name='trid']").val(trId);
		$(".bdczxxxcxTr").removeClass("bdczxxxcxTrRed");
		$("#zxmxTable").find("tr").eq(1).addClass("bdczxxxcxTrRed");
	}
};
	

// 流程提交自动计算公告结束时间
function setDjshxx() {
	$("#DJSH_GGQSSJ").val(moment().format('YYYY-MM-DD'));
	$("#DJSH_GGJSSJ").val(moment().format('YYYY-MM-DD'));
	var delay = $("#DJSH_GGSC").val();
	if (delay != null && delay != undefined) {
		$("#DJSH_GGJSSJ").val(moment().add('days', delay).format('YYYY-MM-DD'));
	}
};

function setUnchangeable(){
	$("#slxx input").attr("disabled","disabled");
	$("#slxx select").attr("disabled","disabled");
	$("#slxx textarea").attr("disabled","disabled");
	$("#zxjbxx input").attr("disabled","disabled");
	$("#zxjbxx select").attr("disabled","disabled");
	$("#zxjbxx textarea").attr("disabled","disabled");
	$("#zxmxTable a").attr("onclick","errorAction()"); 
	$("#zxmxAdd").attr("onclick","errorAction()");
	$("#zxmxDetail input").attr("disabled","disabled");
	$("#sqr input").attr("disabled","disabled");
	$("#sqr select").attr("disabled","disabled");
	$("#sqr textarea").attr("disabled","disabled");
	$("#sbdxxx input").attr("disabled","disabled");
	$("#sbdxxx select").attr("disabled","disabled");
	$("#sbdxxx textarea").attr("disabled","disabled");
}

var bdczxxxcxTr = 1;
//初始化业务表json形式数据
function initAutoTable(flowSubmitObj){
	var zxmxJson = flowSubmitObj.busRecord.ZXMX_JSON;//注销明细
		if(null != zxmxJson && '' != zxmxJson){
			var zxmxInfos = eval(zxmxJson);
			var html = "";
			for(var i=0;i<zxmxInfos.length;i++){
				html +='<tr class="bdczxxxcxTr" id="bdczxxxcxTr_'+bdczxxxcxTr+'">';
				html +='<input type="hidden" name="bdczxxx"/>';
				html +='<td style="text-align: center;">'+bdczxxxcxTr+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].BDCQZH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].QLRMC+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].FDZL+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].QLXZ+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].TDYT+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].ZDQLLX+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" class="syj-closebtn"></a></td>';
				html +='</tr>';
				$("#zxmxTable").append(html);
				$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(zxmxInfos[i]));
				bdczxxxcxTr ++;
				html = "";
			}
			loadBdczxxxcxToId();
		}
};

function errorAction(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}
