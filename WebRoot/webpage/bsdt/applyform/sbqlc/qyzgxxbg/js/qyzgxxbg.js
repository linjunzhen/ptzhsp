//初始化表单字段值
function initForm(){
	//初始化表单字段值
	//$("#qyzgxxbg").find("select").attr("disabled","disabled");		
	$("input[name='RYYBXX_DACSRQ']").attr("disabled","disabled");	
	$("input[name='RYYBXX_CJGZRQ']").attr("disabled","disabled");
}

//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	//获取推送标志位
	var pushFlag = $("input[name='PUSH_FLAG']").val();
	//先判断表单是否验证通过
	 var validateResult =$("#T_SBQLC_QYZGXXBG_FORM").validationEngine("validate");
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				 getXzxxJson(flowSubmitObj);//获取已参保险种信息
			 }
			 //审查环节推送数据
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "办结" && pushFlag!='1'){
				 if(!pushQyZgXxbg(flowSubmitObj)){//推送数据至平潭社保系统
					 return null;
				 }else{
					 alert("业务数据推送至平潭社保险系统成功！");
				 }		 
			 }
		 }
		 //获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGXXBG_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 } 
		return flowSubmitObj;
	 }else{
		 return null;
	 }			 
}


//暂存流程
function FLOW_TempSaveFun(flowSubmitObj) {
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	var rows = $("#xzxxGrid").datagrid("getRows");
	if(rows.length>0){
		$("input[type='hidden'][name='CBXZXX_JSON']").val(JSON.stringify(rows));
	}
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGXXBG_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}


//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//业务数据推送社保系统
function pushQyZgXxbg(flowSubmitObj){
	var result = true;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
	  type: "POST",
      url: "sbQyzgxxbgController.do?peopleInfoRenew&ywId="+ywId,
      async: false, //采用同步方式进行数据判断
      success: function (responseText) {
        var data=$.parseJSON(responseText);
      	if(data.success){
	        result = true;
	        $('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
      	}else if(!data.success){
      		art.dialog({
				content: "推送社保系统异常："+data.msg,
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


//获取已参保险种信息
function getXzxxJson(flowSubmitObj) {
	var rows = $("#xzxxGrid").datagrid("getRows");	
	//alert("已参加险种信息："+rows.length);
	if(rows.length>0){
		$("input[type='hidden'][name='CBXZXX_JSON']").val(JSON.stringify(rows));
	}
}


//企业职工基本养老保险在职人员个人信息变更
function commonFormat(val,row){
	var typeCode="";
	if("性别"==this.title){
		typeCode="sex";
	}else if("单位类型"==this.title){
		typeCode="SBDWLX";
	}else if("险种类型"==this.title){
		typeCode="SBXZLX";
	}else if("个人缴费状态"==this.title){
		typeCode="SBJBZT";
	}else if("人员参保状态"==this.title){
		typeCode="SBCBZT";
	}else{
		typeCode=this.field;
	}
	var map = getDicNameByTypeCode(typeCode);
	return dataFormatByMap(val,map);
}
function getDicNameByTypeCode(typeCode){
	var datas;
	$.ajax({
		url: "dictionaryController.do?load",
		type: "POST",
		data:{typeCode:typeCode},
		dataType: "json",
		async: false,
		success: function(data){
			datas= data;
		},
		error:function(data){
			datas= data;
		}
	});
	return datas;
}
//数据格式化
function dataFormatByMap(value,map){
	var rtn = "";
	$.each(map, function(idx, dic) {
		if(value==dic.DIC_CODE){
			rtn = dic.DIC_NAME;
			return false;
		}
	});
	return rtn;
}



//联级下拉框改变城市
function  changeCity(id) {
    var val =$("#JZDZ_JZDSSS  option:selected").attr("type_code");
    $.ajax({
            url:"exeDataController/dicTypeByParentDatagrid.do",
            data:{"parentId":val},
            dataType:"json",
            success:function(datas){
                    var data=datas.rows;
                    $("#JZDZ_JZDSSCS").html("<option value=''>-- 请选择市 --</option>");
                    $("#JZDZ_JZDSSQX").html("<option value=''>-- 请选择区/县 --</option>");
                    for(var i=0;i<data.length;i++){
                            var $option = $("<option value='"+data[i].VALUE+"' type_code='"+data[i].TYPE_CODE+"'>"+data[i].TEXT+"</option>");
                            $("#JZDZ_JZDSSCS").append($option);
                    }
            }
    });
}

//联级下拉框改变区县
function changeDictinct(id) {
    var val =$("#JZDZ_JZDSSCS  option:selected").attr("type_code");
    $.ajax({
            url:"exeDataController/dicTypeByParentDatagrid.do",
            data:{"parentId":val},
            dataType:"json",
            success:function(datas){
                    var data=datas.rows;
                    $("#JZDZ_JZDSSQX").html("<option value=''>-- 请选择区/县 --</option>");
                    for(var i=0;i<data.length;i++){
                            var $option = $("<option value='"+data[i].VALUE+"' type_code='"+data[i].TYPE_CODE+"'>"+data[i].TEXT+"</option>");
                            $("#JZDZ_JZDSSQX").append($option);
                    }
            }
    });
}

//个人参考信息查询（弹框）
function personInsureSelector(type,inputName){
	var inputValue=$("input[name='"+inputName+"']").val();
	var queryParam=type+"="+inputValue;
	$.dialog.open("sbQyzgxxbgController.do?personInsureSelector&"+queryParam, {
		title : "人员信息变更查询",
		width:"100%",
		height:"100%",
		lock : true,
		resize : false,
		close: function () {
			var ryjbxx = art.dialog.data("ryjbxxInfo");
			var ryjbxxInfos = art.dialog.data("ryjbxxInfos");
			if(ryjbxx){
				FlowUtil.initFormFieldValue(ryjbxx[0],"T_SBQLC_QYZGXXBG_FORM");
				//加载已参加险种信息
			}
			if(ryjbxxInfos){
				$("#xzxxGrid").datagrid('loadData',ryjbxxInfos);
			}
			art.dialog.removeData("ryjbxxInfo");
			art.dialog.removeData("ryjbxxInfos");
		}
	}, false);
}

