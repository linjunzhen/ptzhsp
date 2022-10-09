
//流程表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDCQLC_DYQZXDJ_FORM").validationEngine("validate");
		if(validateResult){
			if($(".bdczxxxcxTr").length<1){
				parent.art.dialog({
					content: "注销明细为空，请添加！",
					icon:"warning",
					ok: true
				});
				return;
			}
			getZxmxInfoJson();//获取注销明细JSON 
			//判断注销明细中必填字段是否为空
			var flag = true;
			var zxmxJson = $("input[type='hidden'][name='ZXMX_JSON']").val();
			var zxmxObj = JSON.parse(zxmxJson);
			for(var i = 0;i<zxmxObj.length;i++){
				var QLQSSJ = zxmxObj[i].QLQSSJ.trim();//债务起始时间
	            var QLJSSJ = zxmxObj[i].QLQSSJ.trim();//债务结束时间
	            var BDBZZQSE = zxmxObj[i].BDBZZQSE.trim();//被担保主债权数额
	            var ZGZQSE = zxmxObj[i].ZGZQSE.trim();//最高债权数额
	            if((QLQSSJ == "" || QLQSSJ ==undefined || QLQSSJ =="") ||
	            	(QLJSSJ == "" || QLJSSJ ==undefined || QLJSSJ =="") || 
	            		((BDBZZQSE == "" || BDBZZQSE ==undefined || BDBZZQSE =="" || BDBZZQSE =="0") &&
	            		(ZGZQSE == "" || ZGZQSE ==undefined || ZGZQSE =="" || ZGZQSE =="0"))){
	            	flag = false;
	            	break;
	            }    
			}
			if(!flag){
	        	parent.art.dialog({
	                content : "注销明细中债务起始时间、债务结束时间、被担保主债权数额/最高债权数额不允许为空!",
	                icon : "warning",
	                ok : true
	            });
	            return;
	        }
			
			if (flowSubmitObj) {
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
					// 登簿环节初始化公告开始结束时间
					var BDC_ZXR = $("input[name='BDC_ZXR']").val();
					if(null==BDC_ZXR || ''==BDC_ZXR){						
						parent.art.dialog({
							content: "请登薄成功后再进行提交!",
							icon:"warning",
							ok: true
						});
						return;
					}
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

                     if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
                         /*判断此业务是否已被办理过*/
                         var zxmxJson = $("input[type='hidden'][name='ZXMX_JSON']").val();
                         if (zxmxJson) {
                             var parse = JSON.parse(zxmxJson);
                             var bdcdyh = parse[0].BDCDYH;
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
                     }

					 //先获取表单上的所有值
					 var formData = FlowUtil.getFormEleData("T_BDCQLC_DYQZXDJ_FORM");
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
	var formData = FlowUtil.getFormEleData("T_BDCQLC_DYQZXDJ_FORM");
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
				html +='<td style="text-align: center;">'+zxmxInfos[i].ZH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].HH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].BDCDYH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].BDCDJZMH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].DYQR+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].DYR+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdczxxxcx(this);" id="zxmxAdd">查 看</a>';
				html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
				html +='</tr>';
				$("#zxmxTable").append(html);
				$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(zxmxInfos[i]));
				bdczxxxcxTr ++;
				html = "";
			}
			loadBdczxxxcxToId();
		}
}


/**=================注销明细信息开始================================*/

//不动产抵押档案查询
function showSelectBdcdydacx(){	
	$.dialog.open("bdcDyqzxdjController.do?selector&allowCount=0", {
		title : "不动产抵押档案查询",
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
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].ZH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].HH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].BDCDYH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].BDCDJZMH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].DYQR+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].DYR+'</td>';
					html +='<td style="text-align: center;">';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdczxxxcx(this);" id="zxmxAdd">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html +='</tr>';
					$("#zxmxTable").append(html);
					$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(bdcdydacxInfo[i]));
					bdczxxxcxTr ++;
					html = "";
				}	
			    art.dialog.removeData("bdcdydacxInfo");		
			    loadBdczxxxcxToId();
			}
			if($("#zxmxTable").find("tr").length>1){				
				$("input[name='BDC_ZXZS']").val($("#zxmxTable").find("tr").length-1);
			} else{				
				$("input[name='BDC_ZXZS']").val(1);
			}
		}
	
	}, false);
};
/**
 * 查看
 */
function loadBdczxxxcx(o){
	$("#zxmxInfo_1 input[type='text']").val('');
	$("#zxmxInfo_1 select").val('');
	var bdczxxx = $(o).closest("tr").find("[name='bdczxxx']").val();
	FlowUtil.initFormFieldValue(JSON.parse(bdczxxx),"zxmxInfo_1");
	var trId = $(o).closest("tr").attr("id");
	$("#zxmxInfo_1 input[name='trid']").val(trId);
	$(".bdczxxxcxTr").removeClass("bdczxxxcxTrRed");
	$(o).closest("tr").addClass("bdczxxxcxTrRed");
}
/**
 * 删除注销明细
 */
function delZxmxTr(o){
	$(o).closest("tr").remove();
	loadBdczxxxcxToId();
} 

function loadBdczxxxcxToId(){
	$("#zxmxInfo_1 input[type='text']").val('');
	$("#zxmxInfo_1 select").val('');
	if($(".bdczxxxcxTr").length>=1){		
		var bdczxxx = $("#zxmxTable").find("tr").eq(1).find("[name='bdczxxx']").val();
		FlowUtil.initFormFieldValue(JSON.parse(bdczxxx),"zxmxInfo_1");
		var trId = $("#zxmxTable").find("tr").eq(1).attr("id");
		$("#zxmxInfo_1 input[name='trid']").val(trId);
		$(".bdczxxxcxTr").removeClass("bdczxxxcxTrRed");
		$("#zxmxTable").find("tr").eq(1).addClass("bdczxxxcxTrRed");
	}
}

/**
 * 保存注销明细
 */
function updateZxmxInfo(){
	var trid = $("#zxmxInfo_1 input[name='trid']").val();	
	var bdczxxx = $("#"+trid+" input[name='bdczxxx']").val();
	if(""!=bdczxxx && null!= bdczxxx && undefined!=bdczxxx){		
		var bdczxxxInfos = JSON.parse(bdczxxx);
		$("#zxmxInfo").find('table').eq(0).find("*[name]").each(function(){
			  var inputName= $(this).attr("name");
			  var inputValue = $(this).val();
			  //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
				  var isChecked = $(this).is(':checked');
				  if(isChecked){
					  bdczxxxInfos[inputName] = inputValue;
				  }
			  }else if(fieldType=="checkbox"){
				  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  bdczxxxInfos[inputName] = inputValues;
			  }else if(fieldType!="button"){
				  bdczxxxInfos[inputName] = inputValue;
			  }          
		});
		$("#"+trid+" input[name='bdczxxx']").val(JSON.stringify(bdczxxxInfos));
		art.dialog({
			content: "保存成功",
			icon:"succeed",
			time:3,
			ok: true
		});
	} else {
		art.dialog({
			content: "请先选择注销明细信息！",
			icon:"warning",
			ok: true
		});
	}
}
/**=================注销明细信息结束================================*/
function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板 3为阅办
	//dataStr +="&TemplatePath="+TemplatePath;	
	dataStr +="&TemplateName="+TemplateName;	//TemplateName为模板名称或别名
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

function errorAction(){
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}



/**
 * 不动产登簿操作
 */
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
							var qzinfo = data.qzinfo[0];
							$("input[name='BDC_DBZT']").val(qzinfo.gdzt);
							if(qzinfo.gdzt == "1"){
								//$("input[name='BDC_BDCDJZMH']").val(qzinfo.qzzh);
								$("input[name='BDC_ZXR']").val(currentUser);//注销人（即登簿人）
								$("input[name='BDC_ZXSJ']").val(currentTime);//注销时间
								$("input[name='BDC_DBR']").val(currentUser);
								$("input[name='BDC_DBZT']").val("1");
								$("input[name='BDC_DBSJ']").val(currentTime);								
								$("input[name='BDC_DBJG']").val("登簿成功");
                                disabledDbBtn("doBooking");
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