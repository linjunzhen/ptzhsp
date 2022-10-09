
/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_YRDWZX_FORM").validationEngine("validate");
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWZX_FORM");
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

/**
 * 暂存流程
 */
function FLOW_TempSaveFun(flowSubmitObj){
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWZX_FORM");
	 for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	 }	 
	return flowSubmitObj;		 
}


/**
 * 退回流程
 */
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


/**
 * 参保单位核对标记查询
 */
function showSelectUnitFlag(){
	$.dialog.open("ybYrdwzxController.do?showSelectUnitFlag&allowCount=1", {
		title : "参保单位核对标记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var dwInfos = art.dialog.data("dwInfos");
			if(dwInfos!=undefined&&dwInfos!=null&&dwInfos!=""){
				var info = {
							"CBID":dwInfos[0].CBID,//单位ID
							"CB_SBBM":dwInfos[0].CB_SBBM,//社保登记证号码
							"CB_DWBXH":dwInfos[0].CB_DWBXH,//单位保险号
							"CB_DWMC":dwInfos[0].CB_DWMC,//参保日期
							"CB_SHBZ":dwInfos[0].CB_SHBZ,//单位名称
							"CB_HDBJ":dwInfos[0].CB_HDBJ,//单位档案号
							"CB_DWDAH":dwInfos[0].CB_DWDAH,//单位类型
							"CB_DWLX":dwInfos[0].CB_DWLX,//单位编号
							"CB_DWBH":dwInfos[0].CB_DWBH,//税号
							"CS_LSGX":dwInfos[0].CS_LSGX,//隶属关系
							"CS_DWLB":dwInfos[0].CS_DWLB,//单位类别							
						};	
				FlowUtil.initFormFieldValue(info,"CBXX");
			}
		    art.dialog.removeData("dwInfos");	
		}
	},false);
}


/**
 * 注销单位信息查询
 */
function showSelectZxUnit(){
	$.dialog.open("ybYrdwzxController.do?showSelectZxUnit&allowCount=1", {
		title : "注销单位信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var zxInfos = art.dialog.data("zxInfos");
			if(zxInfos!=undefined&&zxInfos!=null&&zxInfos!=""){
				var info={
						"ZXID":zxInfos[0].ZXID,//单位ID
						"ZX_DWRS":zxInfos[0].ZX_DWRS,//单位人数
						"ZX_DWBXH":zxInfos[0].ZX_DWBXH,//单位保险号
						"ZX_HDBJ":zxInfos[0].ZX_HDBJ,//核对标记
						"ZX_DWMC":zxInfos[0].ZX_DWMC,//单位名称
						"ZX_SH":zxInfos[0].ZX_SH,//税号
						"ZX_JGBM":zxInfos[0].ZX_JGBM,//单位机构编码
						"ZX_DABH":zxInfos[0].ZX_DABH,//单位档案编号
						"ZX_DSBDJG":zxInfos[0].ZX_DSBDJG,//地税比对结果
				};
				FlowUtil.initFormFieldValue(info,"ZXXX");
			}
			art.dialog.removeData("zxInfos");		
		}		
	},false);
}