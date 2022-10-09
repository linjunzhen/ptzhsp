

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj) {
	//先判断表单是否验证通过
	var validateResult = $("#T_YBQLC_ZGTB_FORM").validationEngine("validate");
	if (validateResult) {
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
		if (submitMaterFileJson || submitMaterFileJson == "") {
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理") {
				if (!getCbrJson(flowSubmitObj)) //获取参保人信息
					return null;
			}
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查") {
				if (!isPushYb(flowSubmitObj)) //停保信息推送医保
					return null;
			}
			//先获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_YBQLC_ZGTB_FORM");
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
	var rows = $("#cbrxxGrid").datagrid("getSelections");
	if(rows.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(rows));
	}
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_YBQLC_ZGTB_FORM");
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

//获取参保人信息
function getCbrJson(){
	var datas = [];
	var flag = true;
	var rows = $("#cbrxxGrid").datagrid("getSelections");
	if (rows.length > 0) {
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].isPush != "处理成功"){
				datas.push(rows[i]);
			}else{
				alert("所勾选参保人信息列表存在已推送成功的数据，请确认！");
				flag = false;
				break;
			}
		}
	}else{
		alert("至少勾选一条参保人信息！");
		flag = false;
	}			
	//alert("提交数据："+ JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//是否推送成功
function isPushYb(flowSubmitObj){
	var flag = true;
	var datas = [];
	var rows = $("#cbrxxGrid").datagrid("getRows");
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].isPush != "处理成功"){
			alert("存在未推送成功的停保人信息，无法提交流程，请确认！");
			flag = false;
			break;
		}else{
			datas.push(rows[i]);
		}
	}
	//alert("提交数据："+ JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}


//查询参保人
function cbrQuery(){
	$.dialog.open("zgjbylbxController.do?cbrQuery", {
	    title : "参保人信息查询",
	    width: "100%",
	    height: "100%",
	    fixed: true,
	    lock : true,
	    resize : false,
	    close:function(){
	    	var cbrInfos = art.dialog.data("cbrInfos");
	    	if(cbrInfos){
	    		//重新加载主页面-参保人信息
	        	//$("#cbrxxGrid").datagrid("loadData",{"total":cbrInfos.length,"rows":cbrInfos});
	        	//追加数据
	        	for(var i = 0;i< cbrInfos.length;i++){
	        		$("#cbrxxGrid").datagrid("appendRow",cbrInfos[i]);
	        	}
	    	}
	    	art.dialog.removeData("cbrInfos");
	    }
	}, false);
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

//职工停保信息推送医保
function pushZgTb(){
	var date=new Date();
	var year = date.getFullYear(); 
	var month = date.getMonth()+1; 
	var day = date.getDay()+1; 
	if (month < 10) { 
		month = "0" + month; 
	}
	if (day < 10) { 
		day = "0" + day; 
	}
	var currentTime = year.toString()+month.toString()+day.toString();
	var rows = $('#cbrxxGrid').datagrid('getRows');
	var pushIndex = [];//推送数据行号
	var pushDatas = [];//推送数据
	var passDatas = [];//推送成功，返回的校验信息
	var passRows = [];//推送成功数据对应的行号
	var failDatas = [];//推送失败,返回的错误信息
	var failRows = [];//推送失败的行号 
	for(var i=0; i<rows.length;i++){
		if(rows[i].isPush!="处理成功"){//推送成功不二次推送
			pushIndex[i] = $('#cbrxxGrid').datagrid('getRowIndex', rows[i]);//行号
			rows[i].aae030=currentTime;//参保日期
			rows[i].zhjzny=currentTime;//最后建账年月
			rows[i].bae619="1";//校验标志
			rows[i].bae627=currentTime;//离职时间
			pushDatas.push(rows[i]);
		}			
	}
	if(pushDatas.length>0){
		//停保保存
		saveZgTb(pushDatas,0,pushIndex,passDatas,passRows,failDatas,failRows);
		//更新参保人信息列表
		updateZgTb(passDatas,passRows,failDatas,failRows);
		//后台数据更新参保人信息
		saveTbCbrXx();
	}	
}

//停保信息保存
function saveZgTb(pushDatas,i,pushIndex,passDatas,passRows,failDatas,failRows){
	var index = pushIndex[i];//行号
	var zdyy = $('input[name="ZDYY"]').val();//中断原因
	var bgyy = $('select[name="BGYY"]').val();//变更原因
	var ifupdate = $('input[name="IFUPDATE"]').val();//是否更新
	var bz = $('input[name="BZ"]').val();//备注
	$.ajax({
		type: "POST",
	    url: "zgjbylbxController.do?pushZgTb",
	    data:{"zdyy":zdyy,"bgyy":bgyy,"ifupdate":ifupdate,"bz":bz,"data":JSON.stringify(pushDatas[i])},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);;
	    	if(data.success){
	    		passDatas.push(data.msg); 
		        passRows.push(index);    
	    	}else if(!data.success){
	    		failRows.push(index);
	    		failDatas.push("推送医保系统异常："+data.msg);
	    	}
	        if((i+1)<pushDatas.length){
	        	saveZgTb(pushDatas,i+1,pushIndex,passDatas,passRows,failDatas,failRows);
	        }	
	    }
	});
}

//更新停保信息(推送接口)
function updateZgTb(passDatas,passRows,failDatas,failRows){
/*	alert("推送成功数据："+JSON.stringify(passDatas));
	alert("推送成功数据行号："+JSON.stringify(passRows));
	alert("推送失败数据："+JSON.stringify(failDatas));
	alert("推送失败数据行号："+JSON.stringify(failRows));*/
	if(passDatas.length>0){
		for(var i=0;i<passDatas.length;i++){
		    $("#cbrxxGrid").datagrid("updateRow",{
		    	index:passRows[i],
		    	row:{
		    		isPush:passDatas[i],//是否推送
		    	}
		    });
		}
	}
	if(failDatas.length>0){
		for(var i=0;i<failDatas.length;i++){
		  $("#cbrxxGrid").datagrid("updateRow",{
		    	index:failRows[i],
		    	row:{
		    		isPush:failDatas[i],//是否推送
		    	}
		    });
		}
	}
}

//审核-后台保存参保人信息
function saveTbCbrXx(){
	var rows = $('#cbrxxGrid').datagrid('getRows');
	$.ajax({
		type: "POST",
	    url: "zgjbylbxController.do?saveTbCbrXx",
	    data:{"ywId":ywId,"data":JSON.stringify(rows)},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);;
	    	if(!data.success){
	    		console.log("后台保存参保人信息出错");   
	    	}	
	    }
	});	
};
