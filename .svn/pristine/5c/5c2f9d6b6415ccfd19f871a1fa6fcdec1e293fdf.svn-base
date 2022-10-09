
//流程表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_DYQZXDJ_FORM").validationEngine("validate");
		/*if(validateResult){*/
			getZxmxInfoJson();//获取注销明细JSON 
			if($(".bdczxxxcxTr").length<1){
				parent.art.dialog({
					content: "注销明细为空，请添加！",
					icon:"warning",
					ok: true
				});
				return;
			}
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
					 var formData = FlowUtil.getFormEleData("T_BDC_DYQZXDJ_FORM");
					 for(var index in formData){
						 flowSubmitObj[eval("index")] = formData[index];
					 }
					 console.dir(flowSubmitObj);	
					return flowSubmitObj;
				 }else{
					 return null;
				 }
		     }			 
		/*}else{
			 return null;
		}*/		
}

//流程暂存方法
function FLOW_TempSaveFun(flowSubmitObj){
	getZxmxInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_DYQZXDJ_FORM");
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