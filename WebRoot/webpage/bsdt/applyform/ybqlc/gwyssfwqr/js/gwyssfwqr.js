

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_GWYSSFWQR_FORM").validationEngine("validate");
	 //获取推送标志位
	 var pushFlag = $("input[name='PUSH_FLAG']").val();
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				 if(!getXzxxJson(flowSubmitObj))//获取险种信息
					 return null;
			 }
			 //审查环节推送数据
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查" && pushFlag!='1'){
				 if(!pushYb(flowSubmitObj)){//推送数据至医保系统
					 return null;
				 }else{
					 alert("业务数据推送至平潭医保系统成功！请完成流程办结！");
				 }		 
			 }
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYSSFWQR_FORM");
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
	//alert("险种信息为："+JSON.stringify(rows));
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_GWYSSFWQR_FORM");
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
 * 获取险种信息(需要勾选公务员险种)
 */
function getXzxxJson(flowSubmitObj){
	var datas = [];
	var flag = true;
	var isGwy = false;//是否选择公务员险种
	if(endEditing()){
		var rows = $("#paramConfGrid").datagrid("getSelections");
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].aab033==null || rows[i].aab033=='undefined' || rows[i].aab033==''){
					//rows[i].aab033 = "";//征收方式设为""
					alert("征收方式不能为空，请选择！");
					return false;
				}
				datas.push(rows[i]);
			}
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].aae140 == '320'){//公务员补助险种
					isGwy = true;
					$('input[name="YWLX"]').val("01");//设置业务类型为添加公务员险种补助	
					break;
				}	
			}	
		} else {
			flag = false;
		}
		if(flag && isGwy){
			if(datas.length>0){
				$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(datas));
			}
			return true;
		}else if(!flag){
			alert("至少勾选一条险种信息！");
			return false;
		}else if(!isGwy){
			alert("请勾选险种-[公务员医疗补助]！");
			return false;
		}	
	}else{
		alert("单位险种信息存在未结束编辑行，请结束编辑！");
		return false;
	}	
}

//审查环节-数据推送至医保系统
function pushYb(flowSubmitObj){
	var result = false;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
		type: "POST",
        url: "ybGwyssfwqrController.do?pushYb&ywId="+ywId,
        async: false, //采用同步方式进行数据判断
        success: function (responseText) {
            var data=$.parseJSON(responseText);
        	if(data.success){
                result = true;
                $('input[name="PUSH_FLAG"]').val("1");
        	}else if(!data.success){
        		art.dialog({
					content: "推送医保系统异常："+data.msg,
					icon:"warning",
					time:3,
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
	$.dialog.open("ybGwyssfwqrController.do?showSelectUnitInfos&allowCount=1", {
		title : "单位信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			 var dwInfos = art.dialog.data("dwInfos");//单位基本信息
			 var xzxxInfos = art.dialog.data("xzxxInfos");//单位险种信息
			 if(dwInfos!=undefined&&dwInfos!=null&&dwInfos!=""){
				 var info = {
							"DWBXH":dwInfos[0].aab999,//单位保险号
							"DWBH":dwInfos[0].aab001,//单位编号
							"SBBM":dwInfos[0].aab002,//社保编码
							"DWDAH":dwInfos[0].aab511,//单位档案号
							"DWMC":dwInfos[0].aab004//单位名称
						};	
				 FlowUtil.initFormFieldValue(info,"jbxx"); 
			 }
			 //设置单位险种的选中信息
			 if(xzxxInfos!=undefined&&xzxxInfos!=null&&xzxxInfos!=""){
				  $('#paramConfGrid').datagrid('loadData',xzxxInfos);
				  var xzxxRows = $("#paramConfGrid").datagrid("getData");				 
				  for(var i=0;i<xzxxRows.rows.length;i++){
					if(xzxxRows.rows[i].isCheck==true){
						$('#paramConfGrid').datagrid('selectRow',i);
					}
				  }
				  //alert(JSON.stringify(xzxxRows.rows));
			 } 
			 art.dialog.removeData("dwInfos");	
			 art.dialog.removeData("xzxxInfos");
		}
	},false);
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
 * 是否审核
 */
function setValue(value,row,index){
	if(value == '1')
		return "是";
	else if(value == '0')
		return "否";
}

