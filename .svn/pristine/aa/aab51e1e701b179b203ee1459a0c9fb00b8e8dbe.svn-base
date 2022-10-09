var start = "";
var end = "";

//表单个性字段初始化
function initForm(flowSubmitObj){
	$("select[name='INSURED_IDENTITY']").val("01");//参保身份默认在职人员
	$("select[name='DOCUMENT_TYPE']").val(1);
	$("select[name='NATION']").val('01');
	$("select[name='ADMIN_DIVISION']").append("<option value='350128' selected='true'>平潭县</option>");
	$("select[name='ENJOY_LEVEL']").val('0');
	$("select[name='EMPLOYMENT_STATUS']").val('2');
	$("select[name='SURVIVAL_STATE']").val('1');
	$("select[name='POPULATE_NATURE']").val('10');	
	$("select[name='EFFECTIVE_SIGN']").attr("disabled","disabled");//有效标志不可编辑
	if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "受理"){//受理环节初始化参保日期(默认为次月1号)
		var date=new Date();
		var year = date.getFullYear(); 
		var month = date.getMonth()+1; 
		var day = "01"; 
		if (month < 10) { 
			month = "0" + (month+1); 
		} 
		if(month == 12){
			year+=1;
			month = "01";
		}
		$("input[name='INSURED_START_TIME']").val(year.toString()+month.toString()+day.toString());
		start = year.toString()+month.toString()+day.toString();
	}

}

//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	//验证证件类型
	setZjValidate($("select[name='DOCUMENT_TYPE']").val(),'DOCUMENT_NUM');
	//获取推送标志位
	var pushFlag = $("input[name='PUSH_FLAG']").val();
	//先判断表单是否验证通过
	 var validateResult =$("#T_YBQLC_ZGCB_FORM").validationEngine("validate");
	 if(validateResult){		 
		 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
		 if(submitMaterFileJson||submitMaterFileJson==""){
			 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
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
				 if(!getXzxxJson(flowSubmitObj))//获取险种信息
					 return null;
			 }
			 //审查环节推送数据
			 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "审查" && pushFlag!='1'){
				 if(!pushZgCb(flowSubmitObj)){//推送数据至医保系统
					 return null;
				 }else{
					 alert("业务数据推送至平潭医保系统成功！请完成流程办结！");
				 }		 
			 }
			 //获取表单上的所有值
			 var formData = FlowUtil.getFormEleData("T_YBQLC_ZGCB_FORM");
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


//暂存流程
function FLOW_TempSaveFun(flowSubmitObj) {
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	endEditing();
	var rows = $("#xzxxGrid").datagrid("getSelections");
	if(rows.length>0){
		$("input[type='hidden'][name='XZXXINFO_JSON']").val(JSON.stringify(rows));
	}
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_YBQLC_ZGCB_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}


//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}

/**
 * 获取险种信息
 */
function getXzxxJson(flowSubmitObj) {
	var datas = [];
	var flag = true;
	if(endEditing()){
		var rows = $("#xzxxGrid").datagrid("getSelections");
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				var data = {};
				if("" == rows[i].aac031 || null == rows[i].aac031 || "undefined"== rows[i].aac031){
					rows[i].aac031 = "1";//参保状态默认'参保缴费'
				}
				data.aae140 = rows[i].aae140;
				data.aae030 = rows[i].aae030;
				data.aae031 = rows[i].aae031;
				data.aac031 = rows[i].aac031;
				datas.push(data);//去除选中的字段值isCheck
			}
		} else {
			alert("至少勾选一条险种信息！");
			flag = false;
		}
		
	}else{
		alert("险种信息存在未结束编辑行，请结束编辑！");
		flag = false;
		
	}		
	if(datas.length>0){
		$("input[type='hidden'][name='XZXXINFO_JSON']").val(JSON.stringify(datas));
	}
	return flag;
}

//审查环节-数据推送至医保系统
function pushZgCb(flowSubmitObj){
	var result = false;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	 $.ajax({
	  type: "POST",
      url: "zgjbylbxController.do?pushZgCb&ywId="+ywId,
      async: false, //采用同步方式进行数据判断
      success: function (responseText) {
    	//layer.close(layload);
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
//校验-人员是否已有档案（查基本信息接口）
function checkIsHaveDd(){
	var aac002 = $('input[name="DOCUMENT_NUM"]').val();//公民身份证号码
	var aac003 = $('input[name="INSURED_NAME"]').val();//姓名
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
			icon:"warning",
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
	var aac002 = $('input[name="DOCUMENT_NUM"]').val();//公民身份证号码
	var aac003 = $('input[name="INSURED_NAME"]').val();//姓名
	var isExist = false;
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

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="1"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}


/*单位信息查询*/
function dwxxcx(type){
	parent.$.dialog.open("zgjbylbxController.do?dwxxcxSelector&type="+type, {
		title : "单位信息查询",
		width:"100%",
		height:"100%",
	    lock : true,
	    resize : false,
	    close: function () {
	        var selectInfo = art.dialog.data("selectInfo");
	        if(selectInfo){
	        	$("input[name='UNIT_FILE']").val(selectInfo.unitFile);//单位档案号
	        	$("input[name='UNIT_INSURANCE']").val(selectInfo.unitInsurance);//单位保险号
	        	$("input[name='UNIT_NAME']").val(selectInfo.unitName);//单位名称
	        	$("input[name='UNIT_NUM']").val(selectInfo.unitNum);//单位编号        	
	        }
	    }
	}, false);
};



/*单位花名册查询*/
function dwhmccx(){
	var rows = $("#dwxxcxSelectorGrid").datagrid("getChecked");
	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
		art.dialog({
			content: "请选择需要被操作的记录!",
			icon:"warning",
		    ok: true
		});
		return null;
	}else if(rows.length>1){
		art.dialog({
			content: "只能选择一条记录进行操作!",
			icon:"warning",
		    ok: true
		});
		return null;
	}else{
		var unitNo = rows[0].aab001;//单位编号
		$.dialog.open("zgjbylbxController.do?dwhmccxSelector&unitNo="+unitNo, {
	        title : "单位花名册查询",
	        width: "90%",
	        height: "90%",
	        fixed: true,
	        lock : true,
	        resize : false
	    }, false);
	}
};



/**
 * 可编辑险种信息（easyui）操作方法
 */
var editIndex = undefined;
//结束编辑模式
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#xzxxGrid").datagrid("validateRow", editIndex)){
		$("#xzxxGrid").datagrid("endEdit", editIndex);
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
	if (editIndex != index) {
		if (endEditing()) {
			$("#xzxxGrid").datagrid("selectRow", index)
				.datagrid("beginEdit", index);
			editIndex = index;
		} else {
			$("#xzxxGrid").datagrid("selectRow", editIndex);
			var rows = $('#xzxxGrid').datagrid('getRows');
            var row = rows[editIndex];
            start = row.aae030;
            end = row.aae031;
		}
	}
}
//添加行操作
function addParam() {
	if (endEditing()) {
		$("#xzxxGrid").datagrid("appendRow", {});
		editIndex = $("#xzxxGrid").datagrid("getRows").length - 1;
		$("#xzxxGrid").datagrid("selectRow", editIndex)
			.datagrid("beginEdit", editIndex);
	}
}
//删除行操作
function delParam() {
	if (editIndex == undefined) {
		alert("进入行编辑状态的时候才可以删除!");
		return;
	}
	$("#xzxxGrid").datagrid("cancelEdit", editIndex)
		.datagrid("deleteRow", editIndex);
	editIndex = undefined;
}
//开始日期
function beginformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	start = y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
	if(!checkDate(start,end)){
		start = "";
	}
	return start;
}
//结束日期
function endformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	end = y.toString()+(m<10?('0'+m):m).toString()+(d<10?('0'+d):d).toString();
	if(!checkDate(start,end)){
		end = "";
	}
	return end;
}
//日期检验
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
 * 初始化加载险种信息-下拉框值
 */
var xzData = ''; //险种类型
var cbData = ''; //参保状态
function getComboboxData() {
	//获取险种类型集合
	$.ajax({
		url : 'dictionaryController/loadData.do?typeCode=TypeOfInsurance&orderType=asc',
		type : 'get',
		async : false, //此处必须是同步
		dataType : 'json',
		success : function(data) {
			xzData = data;
		}
	});
	//获取参保状态信息集合
	$.ajax({
		url : 'dictionaryController/loadData.do?typeCode=insuredState&orderType=asc',
		type : 'get',
		async : false, //此处必须是同步
		dataType : 'json',
		success : function(data) {
			cbData = data;
		}
	});
}

//动态变更参保开始日期
function setGridDate(val){
	start = val;
	var rows = $("#xzxxGrid").datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		rows[i].aae030=val;
	}
	$("#xzxxGrid").datagrid('loadData',rows);
}
