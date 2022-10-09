
var start = "";//开始日期
var end = "";//截止日期

$(function(){
	//城乡居民新参保登记-字段初始化
	$("select[name='XZQH']").append("<option value='350128'>平潭县</option>");//行政区划
	$("select[name='XZQH']").val("350128");//行政区划
	$("select[name='ZJLX']").val("1");//证件类型
	$("select[name='MZ']").val("01");//民族
	$("select[name='RYSCZT']").val("1");//人员生存状态
	$("select[name='CBSF']").on('blur',changeCbsf);//为参保身份添加onblur事件
	$("select[name='XSE']").on('change',updateCbsf);//为是否新生儿添加onblur事件
	var date=new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate(); 
	if (month < 10) { 
	month = "0" + month; 
	} 
	var day = '01'; 
	$("input[name='CB_BEGIN_TIME']").val(year.toString()+month.toString()+day.toString());
	start = year.toString()+month.toString()+day.toString();
	
	//城乡居民续保登记-字段初始化
	$("select[name='XB_DWLB']").attr("disabled","disabled");
	//城乡居民停保登记初始化	
	$("select[name='ZDYY']").val("63");//中断原因	
	$("select[name='SF']").on('blur',change);//为参保身份添加onblur事件
});

/**
 * 提交流程
 */
function FLOW_SubmitFun(flowSubmitObj){
	 //验证证件类型(参保登记)
	 setZjValidate($("select[name='ZJLX']").val(),'ZJHM');
	 //获取推送标志位
	 var pushFlag = $("input[name='PUSH_FLAG']").val();
	 //先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_CXJMDJ_FORM").validationEngine("validate");
	 if(validateResult){		
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 //获取子项的业务数据
			 var cxjmValue = $("input[name='CXJM_TYPE']:checked").val();
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				 if(cxjmValue == "1"){//城乡居民新参保登记
					 //判断人员是否已经参保
					 if(isExistCb()){
						 art.dialog({
							content: "该人员已存在参保信息，不可受理此项登记！",
							icon:"warning",
							time:5,
						    ok: true
						 });
						 return null;
					 }
					 if (!getXzxxJson_Page1(flowSubmitObj))
						return null; 
				 }
				 if(cxjmValue == "2"){//城乡居民续保登记
					 if (!getCbrxxJson_Page2(flowSubmitObj))
						return null;
				 }
				 if(cxjmValue == "3"){//城乡居民停保减员登记
					 if (!getTbxxJson_Page3(flowSubmitObj))
						return null; 
				 } 
			 }
		 	if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查"){
		 		var flag=true;
		 		if(cxjmValue == "1" && pushFlag!='1'){//城乡居民新参保登记
					flag = isPushYbCb(flowSubmitObj);
					if(flag){
						alert("业务数据推送至平潭医保系统成功！请完成流程办结！");//推送数据至平潭医保系统
					}
				}else if(cxjmValue == "2"){
					flag = isPushYbXb(flowSubmitObj);
				}else if(cxjmValue == "3"){
					flag = isPushYbTb(flowSubmitObj);
				}
		 		if(!flag){
					return null;
				}
		 	}
			/* if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "查询建账") {
			if (!getJzxxJson(flowSubmitObj,cxjmValue))
				return null;
		 	}*/
			 //先获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_CXJMDJ_FORM");
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
	 var cxjmValue = $("input[name='CXJM_TYPE']:checked").val();//子项类型
	 //参保-险种信息
	 endEditing();
	 var xzxx_json = $("#xzConfGrid").datagrid("getSelections");
	 if(xzxx_json.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(xzxx_json));
	 }
	/* //建账信息
	 var jzxx_json = $("#cxjzGrid").datagrid("getSelections");
	 if(jzxx_json.length>0){
		if(cxjmValue == '1')
			$("input[type='hidden'][name='CBJZXX_JSON']").val(JSON.stringify(jzxx_json));
		else if(cxjmValue == '2')
			$("input[type='hidden'][name='XBJZXX_JSON']").val(JSON.stringify(jzxx_json));
	 }*/
	 //续保-参保人信息
	 endEditing1();
	 var cbrxx_json = $("#xbConfGrid").datagrid("getSelections");
	 if(cbrxx_json.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(cbrxx_json));
	 }
	 //停保-居民信息
	 var tbxx_json = $("#selectPersonInfosGrid").datagrid("getSelections");
	 if(tbxx_json.length>0){
		$("input[type='hidden'][name='TBXX_JSON']").val(JSON.stringify(tbxx_json));
	 }
	 //先获取表单上的所有值
	 var formData = FlowUtil.getFormEleData("T_YBQLC_CXJMDJ_FORM");
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

//校验-人员是否已有档案（查基本信息接口）
function checkIsHaveDd(){
	var aac002 = $('input[name="ZJHM"]').val();//公民身份证号码
	var aac003 = $('input[name="NAME"]').val();//姓名
	if((aac002==""||aac002==undefined||aac002==null) || 
			(aac003==""||aac003==undefined||aac003==null)){
		art.dialog({
			content: "请先输入证件号码和姓名!",
			icon:"warning",
		    ok: true
		});
		return;
	}
	//校验身份证号码格式（18位，参考验证规则chinaId）
	var regex = /^[1-9]\d{5}[1-9]\d{3}(((0[13578]|1[02])(0[1-9]|[12]\d|3[0-1]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[12]\d)))(\d{4}|\d{3}[xX])$/;
	var re = new RegExp(regex);
	if(!re.test(aac002)){
		art.dialog({
			content: "证件号码格式有误，请输入正确的身份证号码",
			icon:"error",
			time:5,
		    ok: true
		 });
		return ;
	}
	//校验是否已有档案
	var isExist = isExistCb();
	if(isExist){
		art.dialog({
			content: "该人员已存在参保信息，不可受理此项登记！",
			icon:"error",
			time:5,
		    ok: true
		 });
	}else{
		art.dialog({
			content: "该人员未存在参保信息，可受理此项登记！",
			icon:"warning",
			time:5,
		    ok: true
		 });
	}
}
//查询个人综合查询-人员基本信息接口是否已存在参保信息
function isExistCb(){
	var isExist = false;
	var aac002 = $('input[name="ZJHM"]').val();//公民身份证号码
	var aac003 = $('input[name="NAME"]').val();//姓名
	$.ajax({
	     type: "POST",
	     url: "ybCommonController.do?psnBasicGrid",
	     data:{"aac002":aac002,"aac003":aac003},
	     async: false, //采用同步方式进行数据判断
	     success: function (responseText) {
	    	var resultJson = responseText;
			if(resultJson.total>0){	
				//var rows = resultJson.rows;
				isExist = true;
			}
	     }
	});
  return isExist;
}


/**
 * 获取险种信息
 */
function getXzxxJson_Page1(flowSubmitObj){
	var datas = [];
	var flag = true;
	if(endEditing()){
		var rows = $("#xzConfGrid").datagrid("getSelections");
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				rows[i].bae543="1";//默认字段传1
				datas.push(rows[i]);
			}
		}else{
			alert("至少勾选一条险种信息！");
			flag = false;
		}		
	}else{
		alert("险种信息存在未结束编辑行，请结束编辑！");
		flag = false;		
	}	
    //alert("险种信息："+JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='XZXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//城乡居民参保登记信息推送医保
function isPushYbCb(flowSubmitObj){
	var result = false;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
	  type: "POST",
      url: "ybCxjmcbxbController.do?pushCxjmCb&ywId="+ywId,
      async: false, //采用同步方式进行数据判断
      success: function (responseText) {
        var data=$.parseJSON(responseText);
      	if(data.success){
	        result = true; 
	        $('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
      	}else if(!data.success){
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
 * 获取建账信息
 */
function getJzxxJson(flowSubmitObj,value){
	var datas = [];
	var flag = true;
	var rows = $("#cxjzGrid").datagrid("getSelections");
	if(rows.length > 0){		
		for(var i=0;i<rows.length;i++){
			datas.push(rows[i]);
		}
	}else{
		alert("至少勾选一条建账信息！");
		flag = false;
	}
	if(datas.length>0){
		if(value == '1')
			$("input[type='hidden'][name='CBJZXX_JSON']").val(JSON.stringify(datas));
		else if(value=='2')
			$("input[type='hidden'][name='XBJZXX_JSON']").val(JSON.stringify(datas));
	}		
	return flag;
}

/**
 * 获取参保人信息
 */
function getCbrxxJson_Page2(flowSubmitObj){
	var datas = [];
	var flag = true;
	if(endEditing1()){
		var rows = $("#xbConfGrid").datagrid("getSelections");
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				datas.push(rows[i]);
				/*if(rows[i].JY_FLAG=="校验通过"&& rows[i].isPush != "处理成功"){
					datas.push(rows[i]);
				}else{
					alert("所勾选参保人信息列表存在未校验/检验不通过/已推送成功的数据，请确认！");
					flag = false;
					break;
				}*/
			}
		}else{
			alert("至少勾选一条参保人信息！");
			flag = false;
		}		
	}else{
		alert("参保人信息存在未结束编辑行，请结束编辑！");
		flag = false;		
	}
	//alert("提交数据："+ JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//续保登记（判断推送医保是否成功）
function isPushYbXb(flowSubmitObj){
	var flag = true;
	var datas = [];
	var rows = $("#xbConfGrid").datagrid("getRows");
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].isPush != "处理成功"){
			alert("存在未推送成功的续保人信息，无法提交流程，请确认是否推送！");
			flag = false;
			break;
		}else{
			datas.push(rows[i]);
		}
	}
	if(datas.length>0){
		$("input[type='hidden'][name='CBRXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

/**
 * 获取停保信息
 */
function getTbxxJson_Page3(){
	var datas = [];
	var flag = true;
	var rows = $("#selectPersonInfosGrid").datagrid("getSelections");
	if (rows.length > 0) {
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].isPush != "处理成功"){
				datas.push(rows[i]);
			}else{
				alert("所勾选停保人信息列表存在已推送成功的数据，请确认！");
				flag = false;
				break;
			}
		}
	}else{
		alert("至少勾选一条停保信息！");
		flag = false;
	}			
	//alert("提交数据："+ JSON.stringify(datas));
	if(datas.length>0){
		$("input[type='hidden'][name='TBXX_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//停保是否推送成功（判断推送医保是否成功）
function isPushYbTb(flowSubmitObj){
	var flag = true;
	var datas = [];
	var rows = $("#selectPersonInfosGrid").datagrid("getRows");
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



/*类型切换操作*/
function cxjmtypeclick(){
	var cxjmvalue = $("input[name='CXJM_TYPE']:checked").val();
	if(cxjmvalue == 1){
		$("#page_1").attr("style","");
	    $("#xzConfGrid").datagrid("resize");//重新加载可编辑表格
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","display:none;");
	}else if(cxjmvalue == 2){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","");
		//空数据横向滚动条
		$('#xbConfGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
		$("#page_3").attr("style","display:none;");
	}else if(cxjmvalue == 3){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","display:none;");
		$("#page_3").attr("style","");
		//空数据横向滚动条
		$('#selectPersonInfosGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
		
	}
}


/*********************************城乡居民参保登记开始***************************/

//是否新生儿-改变参保身份
function updateCbsf(){
	var xse = $("select[name='XSE']").val();
	var rows = $("#xzConfGrid").datagrid("getRows");
	var cbsf = "";
	if(xse==001){//是-参保身份对应"未成年居民"
		$("select[name='CBSF']").val("a0");
		cbsf ="a0";
	}else if (xse==000){
		$("select[name='CBSF']").val("b0");
		cbsf = "b0";
	}
	if(rows.length > 0 && cbsf!=""){
		for(var i=0;i<rows.length;i++){			
			if(cbsf != null && cbsf != "" && cbsf!="undefined" ){
				rows[i].aac066 = cbsf;
			}
		}
		$('#xzConfGrid').datagrid('loadData',{"total":rows.length,"rows":rows});
	}
}

/**
 * 可编辑险种信息（easyui）操作方法
 */
var editIndex = undefined;
//结束编辑模式
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#xzConfGrid").datagrid("validateRow", editIndex)){
		$("#xzConfGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		start = "";
		end = "";
		return true;
	} else {
		return false;
	}
}
//点击行触发操作
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$("#xzConfGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#xzConfGrid").datagrid("selectRow", editIndex);
			var rows = $('#xzConfGrid').datagrid('getRows');
          var row = rows[editIndex];
          start = row.aae030;//开始日期
          end = row.aae031;//截止日期
		}
	}
}
//添加行操作
function addParam(){
	if (endEditing()){
		$("#xzConfGrid").datagrid("appendRow",{});
		editIndex = $("#xzConfGrid").datagrid("getRows").length-1;
		$("#xzConfGrid").datagrid("selectRow", editIndex)
				.datagrid("beginEdit", editIndex);
	}
}
//删除行操作
function delParam() {
	if (editIndex == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#xzConfGrid").datagrid("cancelEdit", editIndex)
			.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}

function beginformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	start = y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
	if(!checkDate(start,end)){
		start = "";
		return false;
	}
	return y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
}
function endformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	end = y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
	if(!checkDate(start,end)){
		end = "";
		return false;
	}
	return y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
}
function parseDate(s) {	
	if (!s) return new Date();
	var datestr = String(s);	
	var year= datestr.substr(0,4); 
	var month= datestr.substr(4,2); 
	var date = datestr.substr(6,2); 
	var y = parseInt(year,10); 
	var m = parseInt(month,10); 
	var d = parseInt(date,10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d); 
	} else { 
		return new Date(); 
	} 
}
function checkDate(start,end){
    if(start!=""&&end!=""&start!=undefined&&end!=undefined){
    	var startDate = new Date(start.replace(/^(\d{4})(\d{2})(\d{2})$/,"$1/$2/$3"));
        var endDate = new Date(end.replace(/^(\d{4})(\d{2})(\d{2})$/,"$1/$2/$3"));
        if(startDate.getTime() > endDate.getTime()){
        	art.dialog({
    			content: "开始日期不能大于截止日期!",
    			icon:"warning",
    		    ok: true
    		});
        	return false;
        }
    }
    return true;
}


/**
 * 初始化加载险种信息-下拉框值
 */
var xzData='';//险种类型
var cbData='';//参保状态
var cbsfData='';//参保身份
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
	    url: 'dictionaryController/loadData.do?typeCode=insuredState&orderType=asc',
	    type: 'get',
	    async: false,//此处必须是同步
	    dataType: 'json',
	    success: function (data) {
	    	cbData = data;
	    }
    });  
	 //获取参保身份信息集合
	$.ajax({
	    url: 'dictionaryController/loadData.do?typeCode=insuredIdentity&orderType=asc',
	    type: 'get',
	    async: false,//此处必须是同步
	    dataType: 'json',
	    success: function (data) {
	    	cbsfData = data;
	    }
    });
}


/**
 * 居民单位信息查询
 */
function showSelectUnitInfos(){
	$.dialog.open("ybCxjmcbxbController.do?ybSelectUnitInfos&allowCount=1", {
		title : "居民单位信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var dwInfos = art.dialog.data("dwInfos");
			if(dwInfos!=undefined&&dwInfos!=null&&dwInfos!=""){
				var info ={
					"DWBH":dwInfos[0].bab505,//居民单位编号
					"DWMC":dwInfos[0].bab506,//居民单位名称
					"DWID":dwInfos[0].bab507//居民单位ID
				}
				FlowUtil.initFormFieldValue(info,"T_YBQLC_CXJMDJ_FORM");
			}
			art.dialog.removeData("dwInfos");
		}
	},false);
}

/**
 * 居民单位人员清册查询（全屏显示）
 */
function showSelectPersonInfos(){
	$.dialog.open("ybCxjmcbxbController.do?ybSelectPersonInfos", {
		title : "居民单位人员清册查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
	},false);
}

//动态改变险种信息-参保开始日期
function setGridDate(val){
	start = val;
	var rows = $("#xzConfGrid").datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		rows[i].aae030=val;
	}
	$("#xzConfGrid").datagrid('loadData',rows);
}

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="1"){
		//$("input[name='"+name+"']").addClass(",custom[vidcard]");(排除新生儿，且校验格式)
		$("input[name='"+name+"']").addClass(",custom[chinaId]");//不排除新生儿，只校验格式
	}else{
		//$("input[name='"+name+"']").removeClass(",custom[vidcard]");
		$("input[name='"+name+"']").addClass(",custom[chinaId]");
	}
}

//动态变更参保身份
function changeCbsf(){
	var cbsf = $("select[name='CBSF']").find("option:selected").val();//参保身份
	var rows = $("#xzConfGrid").datagrid("getRows");
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){			
			if(cbsf != null && cbsf != "" && cbsf!="undefined" ){
				rows[i].aac066 = cbsf;
			}
		}
		$('#xzConfGrid').datagrid('loadData',{"total":rows.length,"rows":rows});
	}
}

/***********************************城乡居民参保登记结束************************/

/***********************************城乡居民续保登记开始************************/

//可编辑参保人信息（easyui）操作方法
var editIndex1 = undefined;

//结束编辑模式
function endEditing1(){
	if (editIndex1 == undefined){return true}
	if ($("#xbConfGrid").datagrid("validateRow", editIndex1)){
		$("#xbConfGrid").datagrid("endEdit", editIndex1);
		editIndex1 = undefined;
		return true;
	} else {
		return false;
	}
}
//点击行触发操作
function onClickRow1(index){
	if (editIndex1 != index){
		if (endEditing1()){
			$("#xbConfGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndex1 = index;
		} else {
			$("#xbConfGrid").datagrid("selectRow", editIndex1);
		}
	}
}
//添加行操作
function addParam1(){
	if (endEditing1()){
		$("#xbConfGrid").datagrid("appendRow",{});
		editIndex1 = $("#paramConfGrid1").datagrid("getRows").length-1;
		$("#xbConfGrid").datagrid("selectRow", editIndex1)
				.datagrid("beginEdit", editIndex1);
	}
}
//删除行操作
function delParam1() {
	if (editIndex1 == undefined){
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#xbConfGrid").datagrid("cancelEdit", editIndex1)
			.datagrid("deleteRow", editIndex1);
	editIndex1 = undefined;
}

//查询社区或学校
function showSelectSqOrSchool(){
	$.dialog.open("ybCxjmcbxbController.do?ybSelectUnitInfos&allowCount=1", {
		title : "查询社区或学校",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var dwInfos = art.dialog.data("dwInfos");
			if(dwInfos!=undefined&&dwInfos!=null&&dwInfos!=""){
				var info ={
					"XB_DWBH":dwInfos[0].bab505,//居民单位编号
					"XB_DWMC":dwInfos[0].bab506,//居民单位名称
					"XB_DWLB":dwInfos[0].bab508//居民单位类别
				}
				FlowUtil.initFormFieldValue(info,"T_YBQLC_CXJMDJ_FORM");
			}
			art.dialog.removeData("dwInfos");
		}
	},false);
}

//查询调动人员
function showSelectDdPersons(){
	/*var DWBH=$("input[name='XB_DWBH']").val();//居民单位编号
	var DWMC=$("input[name='XB_DWMC']").val();//居民单位名称
	var DWLB=$("input[name='XB_DWLB']").val();//居民单位类别
	if(DWBH==""||DWBH==undefined||DWBH==null){
		art.dialog({
			content: "请先进行社区或学校查询!",
			icon:"warning",
		    ok: true
		});
		return;
	}
	var url ="aab001="+DWBH+"&aab004="+DWMC;*/
	$.dialog.open("ybCxjmcbxbController.do?ybSelectDdPersons", {
		title : "查询调动人员",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var ddPersons = art.dialog.data("ddPersons");
			if(ddPersons){
				editIndex1 = undefined;
				for(var i = 0;i< ddPersons.length;i++){
					ddPersons[i].oldaab001 = ddPersons[i].aab001;//原单位编号
					ddPersons[i].oldaab004 = ddPersons[i].aab004;//原参保单位
					ddPersons[i].oldaac066 = ddPersons[i].aac066;//原参保身份
					ddPersons[i].bae619 = 1;//校验标志默认为1
				}
				//重新加载主页面-参保人信息
				$("#xbConfGrid").datagrid("loadData", {"total":ddPersons.length,"rows":ddPersons});
			}
			art.dialog.removeData("ddPersons");
		}
	},false);
}

//日期格式化
function timeformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
}

//续保校验
/*function cbrCheck(){
	var rows = $('#xbConfGrid').datagrid('getSelections');
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
				 msg += "所勾选数据中参保开始日期存在空值，请确认！";
				 flag = false;
				 break;
			 }
			 if(rows[i].aac040 == null || rows[i].aac040=="" || rows[i].aac040 == "undefined"){
				 msg += "所勾选数据中工资存在空值，请确认！";
				 flag = false;
				 break;
			 }
			 if(rows[i].aac066 == null || rows[i].aac066=="" || rows[i].aac066 == "undefined"){
				 msg += "所勾选数据中参保身份存在空值，请确认！";
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
			 data.rowIndex = $('#xbConfGrid').datagrid('getRowIndex', rows[i]);//行号
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
}*/

//续保数据校验
function checkXb(pushDatas,i,passDatas,passRows,failDatas,failRows){
	var index = pushDatas[i].rowIndex;
	$.ajax({
		type: "POST",
	    url: "ybCxjmcbxbController.do?cbrCheck&data="+encodeURI(JSON.stringify(pushDatas[i])),
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


//续保信息更新（校验接口）
function updateXb(passDatas,passRows,failDatas,failRows){
/*	alert("校验成功数据："+JSON.stringify(passDatas));
	alert("校验成功数据行号："+JSON.stringify(passRows));
	alert("校验失败数据："+JSON.stringify(failDatas));
	alert("校验失败数据行号："+JSON.stringify(failRows));*/
	var errorMsg="";//校验失败提示信息
	if(passDatas.length>0){
		for(var i=0;i<passDatas.length;i++){
		    $("#xbConfGrid").datagrid("updateRow",{
		    	index:passRows[i],
		    	row:{
		    		bae619:passDatas[i].bae619,//校验标志
		    		bae620:passDatas[i].bae620//不通过原因
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

//勾选可处理（校验通过且未进行推送的）
function dealSelect(){
	var rows = $("#xbConfGrid").datagrid("getRows");
	$("#xbConfGrid").datagrid('clearSelections');//先清空所有选中行
	if(rows.length > 0){
		 for(var i=0; i<rows.length;i++){
			 if(rows[i].isPush!= "处理成功"){
					$('#xbConfGrid').datagrid('selectRow',i);
			 } 
		 }		
	}
}

//删除指定的行数据
function cbrDel(){
	var rows = $('#xbConfGrid').datagrid('getSelections');
	if (rows.length > 0) {
	    for(var i=0; i<rows.length;i++){
	        var rowIndex = $('#xbConfGrid').datagrid('getRowIndex', rows[i]);
	        $('#xbConfGrid').datagrid('deleteRow', rowIndex);  
	    }
	 }else{
		 alert("请选择需要被删除的数据！");
	 }
}

//城乡居民续保信息推送医保
function pushCxjmXb(){	
	var XB_DWBH = $("input[name='XB_DWBH']").val();
	//alert("续保新转入单位保险号（编号）："+XB_DWBH);
	var rows = $('#xbConfGrid').datagrid('getRows');
	var pushIndex = [];//推送数据行号
	var pushDatas = [];//推送数据
	var passDatas = [];//推送成功，返回的校验信息
	var passRows = [];//推送成功数据对应的行号
	var failDatas = [];//推送失败,返回的错误信息
	var failRows = [];//推送失败的行号 
	for(var i=0; i<rows.length;i++){
		if(rows[i].isPush!="处理成功"){//推送成功不二次推送
			pushIndex[i] = $('#xbConfGrid').datagrid('getRowIndex', rows[i]);//行号
			rows[i].aab999 = XB_DWBH;//新转入单位的编号（保险号）
			pushDatas.push(rows[i]);
		}	
	}
	if(pushDatas.length>0){
		var layload = layer.load("正在提交请求中…");
		//续保保存
		saveCxjmXb(pushDatas,0,pushIndex,passDatas,passRows,failDatas,failRows);
		//更新参保人信息列表
		updateCxjmXb(passDatas,passRows,failDatas,failRows);
		//后台数据更新参保人信息
		saveXbCbrXx();
		layer.close(layload);
	}
}

//续保信息保存
function saveCxjmXb(pushDatas,i,pushIndex,passDatas,passRows,failDatas,failRows){
	var index = pushIndex[i];
	$.ajax({
		type: "POST",
	    url: "ybCxjmcbxbController.do?pushCxjmXb",
	    data:{"data":JSON.stringify(pushDatas[i])},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);
	    	if(data.success){
	    		passDatas.push(data.msg); 
		        passRows.push(index);    
	    	}else if(!data.success){
	    		failRows.push(index);
	    		failDatas.push(data.msg);
	    	}
	        if((i+1)<pushDatas.length){
	        	saveCxjmXb(pushDatas,i+1,pushIndex,passDatas,passRows,failDatas,failRows);
	        }	
	    }
	});
}

//更新续保信息(推送接口)
function updateCxjmXb(passDatas,passRows,failDatas,failRows){
/*	alert("推送成功数据："+JSON.stringify(passDatas));
	alert("推送成功数据行号："+JSON.stringify(passRows));
	alert("推送失败数据："+JSON.stringify(failDatas));
	alert("推送失败数据行号："+JSON.stringify(failRows));*/
	if(passDatas.length>0){
		for(var i=0;i<passDatas.length;i++){
		    $("#xbConfGrid").datagrid("updateRow",{
		    	index:passRows[i],
		    	row:{
		    		isPush:passDatas[i],//是否推送
		    	}
		    });
		}
	}
	if(failDatas.length>0){
		for(var i=0;i<failDatas.length;i++){
		  $("#xbConfGrid").datagrid("updateRow",{
		    	index:failRows[i],
		    	row:{
		    		isPush:failDatas[i],//是否推送
		    	}
		    });
		}
	}
}

//审核-后台保存参保人信息
function saveXbCbrXx(){
	var rows = $('#xbConfGrid').datagrid('getRows');
	$.ajax({
		type: "POST",
	    url: "ybCxjmcbxbController.do?saveXbCbrXx",
	    data:{"ywId":ywId,"data":JSON.stringify(rows)},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);
	    	if(!data.success){
	    		console.log("后台保存参保人信息出错");   
	    	}	
	    }
	});	
};

/***********************************城乡居民续保登记结束************************/

/***********************************城乡居民停保减员登记开始************************/

/**
 * 查询居民信息
 */
function showSelectJmInfos(){
	$.dialog.open("ybCxjmcbxbController.do?ybSelectJmInfos", {
		title : "查询居民信息",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var jmInfos = art.dialog.data("jmInfos");
			if(jmInfos){
				//重新加载主页面-参保人信息
				$("#selectPersonInfosGrid").datagrid("loadData", {"total":jmInfos.length,"rows":jmInfos});
			}
			art.dialog.removeData("jmInfos");
		}
	},false);	
}

//动态变更参保身份
function change(){
	var cbsf = $("select[name='SF']").find("option:selected").val();//参保身份
	var rows = $("#selectPersonInfosGrid").datagrid("getRows");
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){			
			if(cbsf != null && cbsf != "" && cbsf!="undefined" ){
				rows[i].aac066 = cbsf;
			}
		}
		$('#selectPersonInfosGrid').datagrid('loadData',{"total":rows.length,"rows":rows});
	}
}
//删除指定的行数据
function deleteJmInfos(){
	var rows = $('#selectPersonInfosGrid').datagrid('getSelections');
	if(rows.length > 0) {
	    for(var i=0; i<rows.length;i++){
	        var rowIndex = $('#selectPersonInfosGrid').datagrid('getRowIndex', rows[i]);
	        $('#selectPersonInfosGrid').datagrid('deleteRow', rowIndex);  
	    }
	 }else{
		 alert("请选择需要被删除的数据！");
	 }
}

//清空参保人信息列表
function  clearJmInfos(){
	$('#selectPersonInfosGrid').datagrid('loadData',{total: 0, rows: [] });
}

//城乡居民停保减员信息推送医保
function pushCxjmTb(){	
	var rows = $('#selectPersonInfosGrid').datagrid('getRows');
	var pushIndex = [];//推送数据行号
	var pushDatas = [];//推送数据
	var passDatas = [];//推送成功，返回的校验信息
	var passRows = [];//推送成功数据对应的行号
	var failDatas = [];//推送失败,返回的错误信息
	var failRows = [];//推送失败的行号 
	for(var i=0; i<rows.length;i++){
		if(rows[i].isPush!="处理成功"){//推送成功不二次推送
			pushIndex[i] = $('#selectPersonInfosGrid').datagrid('getRowIndex', rows[i]);//行号
			pushDatas.push(rows[i]);
		}	
	}
	if(pushDatas.length>0){
		var layload = layer.load("正在提交请求中…");
		//停保保存
		saveCxjmTb(pushDatas,0,pushIndex,passDatas,passRows,failDatas,failRows);
		//更新参保人信息列表
		updateCxjmTb(passDatas,passRows,failDatas,failRows);
		//后台数据更新参保人信息
		saveTbCbrXx();
		layer.close(layload);
	}
	
}

//停保信息保存
function saveCxjmTb(pushDatas,i,pushIndex,passDatas,passRows,failDatas,failRows){
	var index = pushIndex[i];
	var zdyy = $('select[name="ZDYY"]').val();
	//alert("中断原因："+zdyy);
	$.ajax({
		type: "POST",
	    url: "ybCxjmcbxbController.do?pushCxjmTb",
	    data:{"zdyy":zdyy,"data":JSON.stringify(pushDatas[i])},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);;
	    	if(data.success){
	    		passDatas.push(data.msg); 
		        passRows.push(index);    
	    	}else if(!data.success){
	    		failRows.push(index);
	    		failDatas.push(data.msg);
	    	}
	        if((i+1)<pushDatas.length){
	        	saveCxjmTb(pushDatas,i+1,pushIndex,passDatas,passRows,failDatas,failRows);
	        }	
	    }
	});
}

//更新停保信息(推送接口)
function updateCxjmTb(passDatas,passRows,failDatas,failRows){
/*	alert("推送成功数据："+JSON.stringify(passDatas));
	alert("推送成功数据行号："+JSON.stringify(passRows));
	alert("推送失败数据："+JSON.stringify(failDatas));
	alert("推送失败数据行号："+JSON.stringify(failRows));*/
	if(passDatas.length>0){
		for(var i=0;i<passDatas.length;i++){
		    $("#selectPersonInfosGrid").datagrid("updateRow",{
		    	index:passRows[i],
		    	row:{
		    		isPush:passDatas[i],//是否推送
		    	}
		    });
		}
	}
	if(failDatas.length>0){
		for(var i=0;i<failDatas.length;i++){
		  $("#selectPersonInfosGrid").datagrid("updateRow",{
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
	var rows = $('#selectPersonInfosGrid').datagrid('getRows');
	$.ajax({
		type: "POST",
	    url: "ybCxjmcbxbController.do?saveTbCbrXx",
	    data:{"ywId":ywId,"data":JSON.stringify(rows)},
	    async: false, //采用同步方式进行数据判断
	    success: function (responseText) {
	        var data=$.parseJSON(responseText);;
	    	if(!data.success){
	    		console.log("后台保存居民信息出错");   
	    	}	
	    }
	});	
};

/***********************************城乡居民停保减员登记结束************************/


		