

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj) {
	//先判断表单是否验证通过
	var validateResult = $("#T_YBQLC_LHJYXBDJ_FORM").validationEngine("validate");
	if (validateResult) {
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
		if (submitMaterFileJson || submitMaterFileJson == "") {
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			//先获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_YBQLC_LHJYXBDJ_FORM");
			for (var index in formData) {
				flowSubmitObj[eval("index")] = formData[index];
			}
			return flowSubmitObj;
		} else {
			return null;
		}
	} else {
		return null;
	}
}

/**
 * 暂存流程
 */
function FLOW_TempSaveFun(flowSubmitObj) {
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_YBQLC_LHJYXBDJ_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}

/**
 * 退回流程
 */
function FLOW_BackFun(flowSubmitObj) {
	return flowSubmitObj;
}

//查询参保人
function cbrQuery(){
	var DWBXH=$("input[name='DWBXH']").val();
	var DWMC=$("input[name='DWMC']").val();
	var DWBH=$("input[name='DWBH']").val();
	if(DWBXH==""||DWBXH==undefined||DWBXH==null){
		art.dialog({
			content: "请先进行单位信息查询!",
			icon:"warning",
		    ok: true
		});
		return;
	}
	$.dialog.open("ybLhjyController.do?cbrQueryForSelect&DWBXH="+DWBXH+"&DWMC="+DWMC+"&DWBH="+DWBH, {
      title : "参保人信息查询",
      width: "100%",
      height: "100%",
      fixed: true,
      lock : true,
      resize : false,
      close:function(){
      	var cbrInfos = art.dialog.data("cbrInfos");
      	//重新加载主页面-参保人信息
      	$("#cbrxxGrid").datagrid("loadData",{"total":cbrInfos.length,"rows":cbrInfos});
      }
  }, false);
}

//单位信息查询
function departQuery(){
	$.dialog.open("ybLhjyController.do?xbDepartQueryForSelect", {
      title : "单位信息查询",
      width: "100%",
      height: "100%",
      fixed: true,
      lock : true,
      resize : false,
		close : function() {
			var selectDepart = art.dialog.data("selectDepart");
			if(selectDepart){
				$("input[name='DWDAH']").val(selectDepart.DWDAH);
				$("input[name='DWBXH']").val(selectDepart.DWBXH);
				$("input[name='DWMC']").val(selectDepart.DWMC);
				$("input[name='DWBH']").val(selectDepart.DWBH);
				$("input[name='DWRS']").val(selectDepart.DWRS);
			}
		}
  }, false);
}



/**
 * 可编辑险种信息（easyui）操作方法
 */
var editIndex = undefined;
//结束编辑模式
function endEditing() {
	if (editIndex == undefined) {
		return true
	}
	if ($("#cbrxxGrid").datagrid("validateRow", editIndex)) {
		$("#cbrxxGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//点击行触发操作
function onClickRow(index) {
	if (editIndex != index) {
		if (endEditing()) {
			$("#cbrxxGrid").datagrid("selectRow", index)
				.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#cbrxxGrid").datagrid("selectRow", editIndex);
		}
	}
}
//添加行操作
function addParam() {
	if (endEditing()) {
		$("#cbrxxGrid").datagrid("appendRow", {});
		editIndex = $("#cbrxxGrid").datagrid("getRows").length - 1;
		$("#cbrxxGrid").datagrid("selectRow", editIndex)
			.datagrid("beginEdit", editIndex);
	}
}
//删除行操作
function delParam() {
	if (editIndex == undefined) {
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#cbrxxGrid").datagrid("cancelEdit", editIndex)
		.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

/**
 * 初始化加载参保人信息-下拉框值
 */
var cbrData = ''; //参保状态
var sfbjData = '';//是否补缴
function getComboboxData() {
	//获取参保身份集合
	$.ajax({
		url : 'dictionaryController/loadData.do?typeCode=insuredIdentity&orderType=asc',
		type : 'get',
		async : false, //此处必须是同步
		dataType : 'json',
		success : function(data) {
			cbrData = data;
		}
	});
	//获取是否补缴集合
	$.ajax({
		url : 'dictionaryController/loadData.do?typeCode=YESNO&orderType=asc',
		type : 'get',
		async : false, //此处必须是同步
		dataType : 'json',
		success : function(data) {
			sfbjData = data;
		}
	});
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

//动态变更参保开始日期
function change(){
	var cbrq = $("input[name='INSURED_START_TIME']").val();//参保开始日期
	var cbsf = $("select[name='INSURED_IDENTITY']").find("option:selected").val();//参保身份
	var gz = $("input[name='WAGES']").val();//工资
	var rows = $("#cbrxxGrid").datagrid("getRows");
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
			if(cbrq != null && cbrq != "" && cbrq!="undefined" ){
				rows[i].aae030 = cbrq;
			}
			if(cbsf != null && cbsf != "" && cbsf!="undefined" ){
				rows[i].aac066 = cbsf;
			}
			if(gz != null && gz != "" && gz!="undefined" && !isNaN(gz)){
				rows[i].aac040 = gz;
			}
		}
		$('#cbrxxGrid').datagrid('loadData',{"total":rows.length,"rows":rows});
	}
}


//勾选可处理（校验通过且未进行推送的）
function dealSelect(){
	var rows = $("#cbrxxGrid").datagrid("getRows");
	$("#cbrxxGrid").datagrid('clearSelections');//先清空所有选中行
	if(rows.length > 0){
		 for(var i=0; i<rows.length;i++){
			 if(rows[i].bae619=="校验通过"&& rows[i].isPush != "是"){
					$('#cbrxxGrid').datagrid('selectRow',i);
			 } 
		 }		
	}
}

//删除指定的行数据
function cbrDel(){
	var rows = $('#cbrxxGrid').datagrid('getSelections');
    if (rows.length > 0) {
        for(var i=0; i<rows.length;i++){
            var rowIndex = $('#cbrxxGrid').datagrid('getRowIndex', rows[i]);
            $('#cbrxxGrid').datagrid('deleteRow', rowIndex);  
        }
     }else{
    	 alert("请选择需要被删除的数据！");
     }
}

//清空参保人信息列表
function  clean(){
	$('#cbrxxGrid').datagrid('loadData',{total: 0, rows: [] });
}

//校验
function cbrCheck(){
	var rows = $('#cbrxxGrid').datagrid('getSelections');
	var pushDatas = [];//校验数据
	var passDatas = [];//校验处理成功，返回的校验信息
	var passRows = [];//校验处理成功数据对应的行号
	var failRows = [];//校验处理失败的行号
	var failDatas = [];//校验处理失败,返回的错误信息
	var msg = "";
	var flag = true;
	if (rows.length > 0) {
		 for(var i=0; i<rows.length;i++){
			 var data ={};
			 //参保开始日期、工资、参保身份不能为空
			 if(rows[i].aae030 == null || rows[i].aae030=="" || rows[i].aae030 == "undefined"){
				 msg += "所勾选数据中参保开始日期存在空值，请确认！\n";
				 flag = false;
				 break;
			 }
			 if(rows[i].aac040 == null || rows[i].aac040=="" || rows[i].aac040 == "undefined"){
				 msg += "所勾选数据中工资存在空值，请确认！\n";
				 flag = false;
				 break;
			 }
			 if(rows[i].aac066 == null || rows[i].aac066=="" || rows[i].aac066 == "undefined"){
				 msg += "所勾选数据中参保身份存在空值，请确认！\n";
				 flag = false;
				 break;
			 }
			 data.aac002 = rows[i].aac002;//证件号码
			 data.Aac001 = rows[i].aac058;//证件类型
			 data.aac003 = rows[i].aac003;//姓名
			 data.Aae030 = rows[i].aae030;//参保开始日期
			 data.Aac040 = rows[i].aac040;//工资
			 data.Aac066 = rows[i].aac066;//参保身份
			 data.Aab001 = rows[i].oldaab001;//单位编号
			 data.rowIndex = $('#cbrxxGrid').datagrid('getRowIndex', rows[i]);//行号
			 pushDatas.push(data);
		 }
		 if(flag){
			checkXb(pushDatas,0,passDatas,passRows,failDatas,failRows);//续保数据校验
			updateXb(passDatas,passRows,failDatas,failRows);//更新续保信息			
		 }else{
			 alert(msg);
		 }	 
	 }else{
    	 alert("请选择需要进行校验的数据！");
     }
}

//续保数据校验
function checkXb(pushDatas,i,passDatas,passRows,failDatas,failRows){
	var index = pushDatas[i].rowIndex;
	$.ajax({
		type: "POST",
	    url: "ybLhjyController.do?cbrCheck&data="+encodeURI(JSON.stringify(pushDatas[i])),
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);
	    	if(data.success){
	    		passDatas.push(JSON.parse(data.jsonString)); 
		        passRows.push(index);    
	    	}else if(!data.success){
	    		failRows.push(index);
	    		failDatas.push(data.msg);
	    	}
	        if((i+1)<pushDatas.length){
	        	checkXb(pushDatas,i+1,passDatas,passRows,failDatas,failRows);
	        }	
	    }
	});
}


//续保信息更新
function updateXb(passDatas,passRows,failDatas,failRows){
/*	alert("校验成功数据："+JSON.stringify(passDatas));
	alert("校验成功数据行号："+JSON.stringify(passRows));
	alert("校验失败数据："+JSON.stringify(failDatas));
	alert("校验失败数据行号："+JSON.stringify(failRows));*/
	var errorMsg="";//校验失败提示信息
	if(passDatas.length>0){
		for(var i=0;i<passDatas.length;i++){
			var data = JSON.stringify(passDatas[i]);
		    $("#cbrxxGrid").datagrid("updateRow",{
		    	index:passRows[i],
		    	row:{
		    		bae619:passDatas[i].bae619,//校验标志
		    		bae620:passDatas[i].bae620//
		    	}
		    });
		}
	}
	if(failDatas.length>0){
		for(var i=0;i<failDatas.length;i++){
			errorMsg += "行号："+ (failRows[i]+1)+"："+failDatas[i]+"\n";
		}
		alert(errorMsg);
	}
	
}

