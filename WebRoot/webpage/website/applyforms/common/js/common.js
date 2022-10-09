$(function() {		
	//粮食仓储企业备案
	setLsccqyba();	
	$("input[name='LSCCQYBA_IN']").click(function(){ 		
		setLsccqyba();
	});	
	
	//资产评估机构及其分支机构备案
	setZcpgjg();	
	$("input[name='ZCPGJG_IN']").click(function(){ 		
		setZcpgjg();
	});	
	
	//物业服务企业及其分支机构备案
	setFyfwqy();	
	$("input[name='FYFWQY_IN']").click(function(){ 		
		setFyfwqy();
	});	
	
	//从事出版物出租业务备案
	setCscbsczyw();	
	$("input[name='CSCBSCZYW_IN']").click(function(){ 		
		setCscbsczyw();
	});
	
	//气象信息服务企业备案
	setQxxxfwqy();	
	$("input[name='QXXXFWQY_IN']").click(function(){ 		
		setQxxxfwqy();
	});	
	
	//文化艺术品经营单位备案
	setWhyspjydw();	
	$("input[name='WHYSPJYDW_IN']").click(function(){ 		
		setWhyspjydw();
	});	
	//气象信息服务企业备案服务提供方式
	setQxxxfwqyFwtgfs();
	$("input[name='QXXXFWQY_FWTGFS']").click(function(){ 		
		setQxxxfwqyFwtgfs();
	});	
	//房地产经纪机构及其分支机构备案
	setFdcjjjg();
	$("input[name='FDCJJJG_IN']").click(function(){ 		
		setFdcjjjg();
	});	
	//广告发布单位 
	setGgfbdw();
	$("input[name='GGFBDW_IN']").click(function(){ 		
		setGgfbdw();
	});	
});

/*************房地产经纪机构及其分支机构备案JS开始****************/

function setFdcjjjg(){
	var arr = new Array();
	//获取值
	$("input[name='FDCJJJG_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){		
		$("#addFdcjjjg").show();
		$("#fdcjjjg").find("input,select").attr("disabled",false);
		$("#fdcjjjg").find("select").addClass(" validate[required]");
		$("#fdcjjjg").find("input[name='FDCJJJG_JYFW']").addClass(" validate[required]");
		$("#fdcjjjgDiv").find("input[name$='FDCJJJG_NAME']").addClass(" validate[required]");
		$("#fdcjjjgDiv").find("input[name$='FDCJJJG_IDCARD']").addClass(" validate[required],custom[vidcard]");
	} else{
		$("#addFdcjjjg").hide();
		$("#fdcjjjg").find("input,select").attr("disabled",true);
		$("#fdcjjjg").find("select").removeClass(" validate[required]");
		$("#fdcjjjg").find("input[name='FDCJJJG_JYFW']").removeClass(" validate[required]");
		$("#fdcjjjg").find("input[type='text']").val("");
		$("#fdcjjjg").find("input,select").removeAttr("checked");	
		$("#fdcjjjgDiv").find("input[name$='FDCJJJG_NAME']").removeClass(" validate[required]");
		$("#fdcjjjgDiv").find("input[name$='FDCJJJG_IDCARD']").removeClass(" validate[required],custom[vidcard]");
	}
}
/**
 * 添加房地产经纪机构及其分支机构备案
 */
function addFdcjjjgDiv(){
	$.post("multipleController/refreshFdcjjjgDiv.do",{},
	function(responseText, status, xhr) {
		$("#fdcjjjgDiv").append(responseText);	
		setFdcjjjg();
	});
}
/**
 * 删除房地产经纪机构及其分支机构备案
 */
function delFdcjjjgDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取房地产经纪机构及其分支机构备案
 */
function getFdcjjjgJson(){
	var fdcjjjgArray = [];
	$("#fdcjjjgDiv").children("div").each(function(i){
		var FDCJJJG_NAME = $(this).find("[name$='FDCJJJG_NAME']").val();//房地产经纪专业人员姓名
		var FDCJJJG_IDCARD = $(this).find("[name$='FDCJJJG_IDCARD']").val();//身份证号码
		var FDCJJJG_MANAGE_NUMBER = $(this).find("[name$='FDCJJJG_MANAGE_NUMBER']").val();//房地产经纪专业人员职业资格证书管理号
		var FDCJJJG_REGISTRATION_NUMBER = $(this).find("[name$='FDCJJJG_REGISTRATION_NUMBER']").val();//房地产经纪专业人员登记证书登记号	
	   
		var fdcjjjg = {};
		fdcjjjg.FDCJJJG_NAME = FDCJJJG_NAME;
		fdcjjjg.FDCJJJG_IDCARD = FDCJJJG_IDCARD;
		fdcjjjg.FDCJJJG_MANAGE_NUMBER = FDCJJJG_MANAGE_NUMBER;
		fdcjjjg.FDCJJJG_REGISTRATION_NUMBER = FDCJJJG_REGISTRATION_NUMBER;
		fdcjjjgArray.push(fdcjjjg);		
		
	});
	if(fdcjjjgArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(fdcjjjgArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}

/*************房地产经纪机构及其分支机构备案JS结束****************/

/*************广告发布单位JS开始****************/
function setGgfbdw(){
	var arr = new Array();
	//获取值
	$("input[name='GGFBDW_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){		
		$("#addGgfbdw").show();
		$("#ggfbdw").find("input,select").attr("disabled",false);
		$("#ggfbdw").find("input,select").addClass(" validate[required]");
		$("#ggfbdw").find("input[name$='GGFBDW_TIME']").addClass("Wdate");
	} else{
		$("#addGgfbdw").hide();
		$("#ggfbdw").find("input,select").attr("disabled",true);
		$("#ggfbdw").find("input,select").removeClass(" validate[required]");
		$("#ggfbdw").find("input[name$='GGFBDW_TIME']").removeClass("Wdate");
		$("#ggfbdw").find("input,select").removeAttr("checked");	
		$("#ggfbdw").find("input[type='text']").val("");
	}
}
/**
 * 添加广告发布单位
 */
function addGgfbdwDiv(){
	$.post("multipleController/refreshGgfbdwDiv.do",{},
	function(responseText, status, xhr) {
		$("#ggfbdwDiv").append(responseText);	
		setGgfbdw();
	});
}
/**
 * 删除广告发布单位
 */
function delGgfbdwDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取广告发布单位
 */
function getGgfbdwJson(){
	var ggfbdwArray = [];
	$("#ggfbdwDiv").children("div").each(function(i){
		var GGFBDW_NAME = $(this).find("[name$='GGFBDW_NAME']").val();//姓名
		var GGFBDW_TYPE = $(this).find("[name$='GGFBDW_TYPE']").val();//人员类型
		
		var ggfbdw = {};
		ggfbdw.GGFBDW_NAME = GGFBDW_NAME;
		ggfbdw.GGFBDW_TYPE = GGFBDW_TYPE;
		ggfbdwArray.push(ggfbdw);		
		
	});
	if(ggfbdwArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(ggfbdwArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}

/*************房地产经纪机构及其分支机构备案JS结束****************/

//粮食仓储企业备案
function setLsccqyba(){
	var arr = new Array();
	//获取值
	$("input[name='LSCCQYBA_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='LSCCQYBA_JYFW']").attr("disabled",false);
		$("input[name='LSCCQYBA_JYFW']").addClass(" validate[required]");
	} else{
		$("input[name='LSCCQYBA_JYFW']").attr("disabled",true);		
		$("input[name='LSCCQYBA_JYFW']").removeClass(" validate[required]");
		$("input[name='LSCCQYBA_JYFW']").removeAttr("checked");	
	}
}

//资产评估机构及其分支机构备案
function setZcpgjg(){
	var arr = new Array();
	//获取值
	$("input[name='ZCPGJG_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='ZCPGJG_JYFW']").attr("disabled",false);
		$("input[name='ZCPGJG_JYFW']").addClass(" validate[required]");
	} else{
		$("input[name='ZCPGJG_JYFW']").attr("disabled",true);		
		$("input[name='ZCPGJG_JYFW']").removeClass(" validate[required]");
		$("input[name='ZCPGJG_JYFW']").removeAttr("checked");	
	}
}

//物业服务企业及其分支机构备案
function setFyfwqy(){
	var arr = new Array();
	//获取值
	$("input[name='FYFWQY_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='FYFWQY_JYFW']").attr("disabled",false);
		$("input[name='FYFWQY_JYFW']").addClass(" validate[required]");
	} else {
		$("input[name='FYFWQY_JYFW']").attr("disabled",true);		
		$("input[name='FYFWQY_JYFW']").removeClass(" validate[required]");
		$("input[name='FYFWQY_JYFW']").removeAttr("checked");	
	}
}
//从事出版物出租业务备案
function setCscbsczyw(){	
	var arr = new Array();
	//获取值
	$("input[name='CSCBSCZYW_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='CSCBSCZYW_JYFW']").attr("disabled",false);
		$("input[name='CSCBSCZYW_JYFW']").addClass(" validate[required]");
	} else{
		$("input[name='CSCBSCZYW_JYFW']").attr("disabled",true);		
		$("input[name='CSCBSCZYW_JYFW']").removeClass(" validate[required]");
		$("input[name='CSCBSCZYW_JYFW']").removeAttr("checked");	
	}
}
//气象信息服务企业备案
function setQxxxfwqy(){
	var arr = new Array();
	//获取值
	$("input[name='QXXXFWQY_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='QXXXFWQY_JYFW']").attr("disabled",false);
		$("input[name='QXXXFWQY_JYFW']").addClass(" validate[required]");
		
		$("input[name='QXXXFWQY_FWTGFS']").attr("disabled",false);
		$("input[name='QXXXFWQY_FWTGFS']").addClass(" validate[required]");
		
		$("input[name='QXXXFWQY_FWFWSM']").attr("disabled",false);
		$("input[name='QXXXFWQY_FWFWSM']").addClass(" validate[required]");
	} else{
		$("input[name='QXXXFWQY_JYFW']").attr("disabled",true);		
		$("input[name='QXXXFWQY_JYFW']").removeClass(" validate[required]");
		$("input[name='QXXXFWQY_JYFW']").removeAttr("checked");	
		
		$("input[name='QXXXFWQY_FWTGFS']").attr("disabled",true);		
		$("input[name='QXXXFWQY_FWTGFS']").removeClass(" validate[required]");
		$("input[name='QXXXFWQY_FWTGFS']").removeAttr("checked");
		
		$("input[name='QXXXFWQY_FWTGFS_QT']").attr("disabled",true);		
		$("input[name='QXXXFWQY_FWTGFS_QT']").removeClass(" validate[required]");
		$("input[name='QXXXFWQY_FWTGFS_QT']").val('');	
		
		$("input[name='QXXXFWQY_FWFWSM']").attr("disabled",true);		
		$("input[name='QXXXFWQY_FWFWSM']").removeClass(" validate[required]");
		$("input[name='QXXXFWQY_FWFWSM']").val('');	
	}
}
function setQxxxfwqyFwtgfs(){
	var arr = new Array();
	//获取值
	$("input[name='QXXXFWQY_FWTGFS']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	if($.inArray("14", arr)>-1){//其他
		$("input[name='QXXXFWQY_FWTGFS_QT']").attr("disabled",false);
		$("input[name='QXXXFWQY_FWTGFS_QT']").addClass(" validate[required]");
	} else{
		$("input[name='QXXXFWQY_FWTGFS_QT']").attr("disabled",true);		
		$("input[name='QXXXFWQY_FWTGFS_QT']").removeClass(" validate[required]");
		$("input[name='QXXXFWQY_FWTGFS_QT']").val('');			
	}
}
//文化艺术品经营单位备案
function setWhyspjydw(){
	var arr = new Array();
	//获取值
	$("input[name='WHYSPJYDW_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='WHYSPJYDW_JYFW']").attr("disabled",false);
		$("input[name='WHYSPJYDW_JYFW']").addClass(" validate[required]");
		
		$("input[name='WHYSPJYDW_JYHD']").attr("disabled",false);
		$("input[name='WHYSPJYDW_JYHD']").addClass(" validate[required]");
		
		$("input[name='WHYSPJYDW_ISLYXXWL']").attr("disabled",false);
		$("input[name='WHYSPJYDW_ISLYXXWL']").addClass(" validate[required]");
	} else{
		$("input[name='WHYSPJYDW_JYFW']").attr("disabled",true);		
		$("input[name='WHYSPJYDW_JYFW']").removeClass(" validate[required]");
		$("input[name='WHYSPJYDW_JYFW']").removeAttr("checked");
		
		$("input[name='WHYSPJYDW_JYHD']").attr("disabled",true);		
		$("input[name='WHYSPJYDW_JYHD']").removeClass(" validate[required]");
		$("input[name='WHYSPJYDW_JYHD']").removeAttr("checked");
		
		$("input[name='WHYSPJYDW_ISLYXXWL']").attr("disabled",true);		
		$("input[name='WHYSPJYDW_ISLYXXWL']").removeClass(" validate[required]");
		$("input[name='WHYSPJYDW_ISLYXXWL']").removeAttr("checked");	
	}
	
}