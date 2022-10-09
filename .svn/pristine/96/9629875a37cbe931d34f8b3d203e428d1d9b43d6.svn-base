


/**
 * 提交流程
 **/
function FLOW_SubmitFun(flowSubmitObj){
	 var validateResult =$("#T_YBQLC_YRDWCB_FORM").validationEngine("validate");	//获取推送标志位
	 var pushFlag = $("input[name='PUSH_FLAG']").val();//获取推送平潭医保标志位
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				 if(!getXzxxJson(flowSubmitObj))//获取险种信息
					 return null;
			 }
			 //审查环节判断是否推送成功
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查" && pushFlag!='1'){
				 if(!pushYrdwCb()){
					 return null;
				 }else{
					 alert("业务数据推送至平潭医保系统成功！请完成流程办结！");
				 }		
			 }
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWCB_FORM");
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
	 endEditing();
	 var rows = $("#paramConfGrid").datagrid("getSelections");
	 if(rows.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(rows));
	 }
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_YRDWCB_FORM");
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

//退件
function FLOW_NotAccept(flowSubmitObj){
	return flowSubmitObj;
}

/**
 * 获取险种信息
 */
function getXzxxJson(flowSubmitObj) {
	var datas = [];
	var flag = true;
	if(endEditing()){
		var rows = $("#paramConfGrid").datagrid("getSelections");
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].aab033 == null || rows[i].aab033 == "" || rows[i].aab033 == 'undefined'){
					alert("所勾选参保险种信息中，征收方式不可为空，请确认！");
					flag = false;
					break;
				}
				var data = {};
				data.aae140 = rows[i].aae140;//险种类型
				data.aab033 = rows[i].aab033;//征收方式
				data.aab050 = rows[i].aab050;//参保日期
				datas.push(data);//去除选中的字段值ck
			}
		} else {
			alert("至少勾选一条险种信息！");
			flag = false;
		}
		
	}else{
		alert("险种信息存在未结束编辑行，请结束编辑！");
		flag = false;		
	}	
	//alert("险种信息："+ JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//审核提交流程-判断是否推送医保成功
function isPushDwcb(){
	var result =  true;
	var dwbxh = $('input[name="DWBXH"]').val();//单位保险号
	var dwbh = $('input[name="DWBH"]').val();//单位编号
	if(dwbxh==null || dwbxh=="" || dwbxh=="undefined" 
		||dwbh==null || dwbh=="" || dwbh=="undefined" ){
		alert("单位保险号、单位编号存在空值，请确认！");
		result = false;
	}
	return result;
}

//单位参保信息推送医保
function pushYrdwCb(){
	var result = true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
	  type: "POST",
      url: "ybYrdwcbController.do?pushYrdwCb&ywId="+ywId,
      async: false, //采用同步方式进行数据判断
      success: function (responseText) {
        var data=$.parseJSON(responseText);
      	if(data.success){
      		result = true;
	        $('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
	        /*result = true;
	        var row = JSON.parse(data.jsonString);
	        $('input[name="DWBXH"]').val(row.DWBXH);//单位保险号
	        $('input[name="DWBH"]').val(row.DWBH);//单位编号
*/      	}else if(!data.success){
      		art.dialog({
				content: "推送医保系统异常："+data.msg,
				icon:"warning",
				time:5,
			    ok: true
			});
      		result = false;
      	}	
      }
	});
	return result;
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


function beginformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
}

function parseDate(s) { 
	if (!s) return new Date();
	var year= s.substr(0,4); 
	var month= s.substr(4,2); 
	var date = s.substr(6,2); 
	var y = parseInt(year,10); 
	var m = parseInt(month,10); 
	var d = parseInt(date,10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d); 
	} else { 
		return new Date(); 
	} 
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
   //获取征收方式信息集合
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

