

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_YRDWBG_FORM").validationEngine("validate");
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if(!getXzxxJson(flowSubmitObj))//获取险种信息
				 return null;
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWBG_FORM");
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
	getXzxxJson(flowSubmitObj);//获取险种信息
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWBG_FORM");
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
 * 获取险种信息
 */
function getXzxxJson(flowSubmitObj){
	var datas = [];
	var flag = true;
	if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
		endEditing();
		var rows = $("#paramConfGrid").datagrid("getSelections");
		if(rows.length > 0){		
			for(var i=0;i<rows.length;i++){
				datas.push(rows[i]);
			}
		}else{
			alert("至少勾选一条险种信息！");
			flag = false;
		}
	}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查"){
		var rows = $("#paramConfGrid").datagrid("getRows");
		if(rows.length > 0){		
			for(var i=0;i<rows.length;i++){
				datas.push(rows[i]);
			}
		}
	}	
	$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(datas));	
	return flag;
}



/**
 * 单位参保信息可编辑表格（easyui）操作方法
 */
var editIndex = undefined;
//结束编辑模式
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#paramConfGrid").datagrid("validateRow", editIndex)){
		$("#paramConfGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//点击行触发操作
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#paramConfGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#paramConfGrid").datagrid("selectRow", editIndex);
		}
	}
}
//添加行操作
function addParam(){
	if (endEditing()){
		$("#paramConfGrid").datagrid("appendRow",{});
		editIndex = $("#paramConfGrid").datagrid("getRows").length-1;
		$("#paramConfGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}
//删除行操作
function delParam() {
	if (editIndex == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#paramConfGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

/**
* 初始化加载单位参保信息-下拉框值
*/
var xzData='';//险种类型
var zsData='';//征收方式
function getComboboxData(){	
	//获取险种类型集合
	$.ajax({
       url: 'dictionaryController/loadData.do?typeCode=TypeOfInsurance&orderType=asc',
       type: 'get',
       async: false,//此处必须是同步
       dataType: 'json',
       success: function (data) {
       	xzData = data;
       }
   });
   //获取参保状态信息集合
	$.ajax({
	    url: 'dictionaryController/loadData.do?typeCode=collectionMethod&orderType=asc',
	    type: 'get',
	    async: false,//此处必须是同步
	    dataType: 'json',
	    success: function (data) {
	    	zsData = data;
	    }
   })	
}


/**
 * 单位信息查询
 */
function showSelectUnitInfos(){
	$.dialog.open("ybYrdwbgController.do?selectUnitInfos&allowCount=1", {
		title : "单位信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			 var dwInfos = art.dialog.data("dwInfos");
			 if(dwInfos!=undefined&&dwInfos!=null&&dwInfos!=""){
				 var info = {
							"YWH":dwInfos[0].YWH,//单位ID
							"SBBM":dwInfos[0].SBBM,//社保登记证号码
							"DWBXH":dwInfos[0].DWBXH,//单位保险号
							"CBRQ":dwInfos[0].CBRQ,//参保日期
							"DWMC":dwInfos[0].DWMC,//单位名称
							"DWDAH":dwInfos[0].DWDAH,//单位档案号
							"DWLX":dwInfos[0].DWLX,//单位类型
							"DWBH":dwInfos[0].DWBH,//单位编号
							"SH":dwInfos[0].SH,//税号
							"LSGX":dwInfos[0].LSGX,//隶属关系
							"DWLB":dwInfos[0].DWLB,//单位类别
							"SHBZ":dwInfos[0].SHBZ,//审核标志
							"HDBZ":dwInfos[0].HDBZ,//核对标记
							
						};	
				 FlowUtil.initFormFieldValue(info,"page_1");
			 }
			 art.dialog.removeData("dwInfos");			
		}
	},false);
}

/**
 * 是否审核
 */
function setValue(value,row,index){
	if(value == '1')
		return "是";
	else if(value == '0')
		return "否";
}





















