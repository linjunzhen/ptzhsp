$(function() {		
	//保安服务公司分公司备案
	setBafwgsfgs();	
	$("input[name='BAFWGSFGS_IN']").click(function(){ 		
		setBafwgsfgs();
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
	
	//农作物种子生产经营分支机构备案
	setNzwzzscjy();	
	$("input[name='NZWZZSCJY_IN']").click(function(){ 		
		setNzwzzscjy();
	});
	
	//劳务派遣单位设立分公司备案
	setLwpqdwslfgs();	
	$("input[name='LWPQDWSLFGS_IN']").click(function(){ 		
		setLwpqdwslfgs();
	});	
	//旅行社企业设立服务网点备案
	setLxsqyslfwwd();
	$("input[name='LXSQYSLFWWD_IN']").click(function(){ 		
		setLxsqyslfwwd();
	});	
	//旅行社企业设立分社备案
	setLxsqyslfs();
	$("input[name='LXSQYSLFS_IN']").click(function(){ 		
		setLxsqyslfs();
	});	
	
	//文化艺术品经营单位备案
	setWhyspjydw();	
	$("input[name='WHYSPJYDW_IN']").click(function(){ 		
		setWhyspjydw();
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
	//工程造价咨询企业设立分支机构备案 
	setGczjzxqysl();
	$("input[name='GCZJZXQYSL_IN']").click(function(){ 		
		setGczjzxqysl();
	});	
});

/*************工程造价咨询企业设立分支机构备案JS开始****************/

function setGczjzxqysl(){
	var arr = new Array();
	//获取值
	$("input[name='GCZJZXQYSL_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){		
		$("#addGczjzxqysl").show();
		$("#gczjzxqysl").find("input,select").attr("disabled",false);
		$("#gczjzxqysl").find("input,select").addClass(" validate[required]");
		$("#gczjzxqysl").find("input[name$='GCZJZXQYSL_TIME']").addClass("Wdate");
		$("#gczjzxqyslDiv").find("input[name$='GCZJZXQYSL_IDCARD']").addClass(" validate[required],custom[vidcard]");
	} else{
		$("#addGczjzxqysl").hide();
		$("#gczjzxqysl").find("input,select").attr("disabled",true);
		$("#gczjzxqysl").find("input,select").removeClass(" validate[required]");
		$("#gczjzxqysl").find("input[type='text']").val("");
		$("#gczjzxqysl").find("input,select").removeAttr("checked");	
		$("#gczjzxqysl").find("input[name$='GCZJZXQYSL_TIME']").removeClass("Wdate");
		$("#gczjzxqyslDiv").find("input[name$='GCZJZXQYSL_IDCARD']").removeClass(" validate[required],custom[vidcard]");
	}
}
/**
 * 添加工程造价咨询企业设立分支机构备案
 */
function addGczjzxqyslDiv(){
	$.post("multipleController/refreshGczjzxqyslDiv.do",{},
	function(responseText, status, xhr) {
		$("#gczjzxqyslDiv").append(responseText);	
		setGczjzxqysl();
	});
}
/**
 * 删除工程造价咨询企业设立分支机构备案
 */
function delGczjzxqyslDiv(o){
	$(o).closest("div").remove();
} 
/**
 * 获取工程造价咨询企业设立分支机构备案
 */
function getGczjzxqyslJson(){
	var gczjzxqyslArray = [];
	$("#gczjzxqyslDiv").children("div").each(function(i){
		var GCZJZXQYSL_NAME = $(this).find("[name$='GCZJZXQYSL_NAME']").val();//姓名
		var GCZJZXQYSL_IDCARD = $(this).find("[name$='GCZJZXQYSL_IDCARD']").val();//身份证号码
		var GCZJZXQYSL_CODE = $(this).find("[name$='GCZJZXQYSL_CODE']").val();//造价工程师注册证书编号
		var GCZJZXQYSL_TIME = $(this).find("[name$='GCZJZXQYSL_TIME']").val();//造价工程师注册证书有效期	
	   
		var gczjzxqysl = {};
		gczjzxqysl.GCZJZXQYSL_NAME = GCZJZXQYSL_NAME;
		gczjzxqysl.GCZJZXQYSL_IDCARD = GCZJZXQYSL_IDCARD;
		gczjzxqysl.GCZJZXQYSL_CODE = GCZJZXQYSL_CODE;
		gczjzxqysl.GCZJZXQYSL_TIME = GCZJZXQYSL_TIME;
		gczjzxqyslArray.push(gczjzxqysl);		
		
	});
	if(gczjzxqyslArray.length>0){
		var reg = /[\(]/g,reg2 = /[\)]/g;
		return JSON.stringify(gczjzxqyslArray).replace(reg,"（").replace(reg2,"）");
	}else{
		return "";
	}
}

/*************工程造价咨询企业设立分支机构备案JS结束****************/


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

//保安服务公司分公司备案
function setBafwgsfgs(){
	var arr = new Array();
	//获取值
	$("input[name='BAFWGSFGS_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='BAFWGSFGS_JYFW']").attr("disabled",false);
		$("input[name='BAFWGSFGS_JYFW']").addClass(" validate[required]");
	} else{
		$("input[name='BAFWGSFGS_JYFW']").attr("disabled",true);		
		$("input[name='BAFWGSFGS_JYFW']").removeClass(" validate[required]");
		$("input[name='BAFWGSFGS_JYFW']").removeAttr("checked");	
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
//农作物种子生产经营分支机构备案
function setNzwzzscjy(){	
	var arr = new Array();
	//获取值
	$("input[name='NZWZZSCJY_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='NZWZZSCJY_JYFW']").attr("disabled",false);
		$("input[name='NZWZZSCJY_JYFW']").addClass(" validate[required]");
	} else{
		$("input[name='NZWZZSCJY_JYFW']").attr("disabled",true);		
		$("input[name='NZWZZSCJY_JYFW']").removeClass(" validate[required]");
		$("input[name='NZWZZSCJY_JYFW']").removeAttr("checked");	
	}
}
//劳务派遣单位设立分公司备案
function setLwpqdwslfgs(){
	var arr = new Array();
	//获取值
	$("input[name='LWPQDWSLFGS_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='LWPQDWSLFGS_JYXKZH']").attr("disabled",false);
		$("input[name='LWPQDWSLFGS_JYXKZH']").addClass(" validate[required]");
		
		$("input[name='LWPQDWSLFGS_JYXKJG']").attr("disabled",false);
		$("input[name='LWPQDWSLFGS_JYXKJG']").addClass(" validate[required]");
		
		$("input[name='LWPQDWSLFGS_JYFW']").attr("disabled",false);
		$("input[name='LWPQDWSLFGS_JYFW']").addClass(" validate[required]");
	} else{
		
		$("input[name='LWPQDWSLFGS_JYXKZH']").attr("disabled",true);		
		$("input[name='LWPQDWSLFGS_JYXKZH']").removeClass(" validate[required]");
		$("input[name='LWPQDWSLFGS_JYXKZH']").val('');	
		
		$("input[name='LWPQDWSLFGS_JYXKJG']").attr("disabled",true);		
		$("input[name='LWPQDWSLFGS_JYXKJG']").removeClass(" validate[required]");
		$("input[name='LWPQDWSLFGS_JYXKJG']").val('');	
		
		$("input[name='LWPQDWSLFGS_JYFW']").attr("disabled",true);		
		$("input[name='LWPQDWSLFGS_JYFW']").removeClass(" validate[required]");
		$("input[name='LWPQDWSLFGS_JYFW']").removeAttr("checked");	
	}
}
//旅行社企业设立服务网点备案
function setLxsqyslfwwd(){
	var arr = new Array();
	//获取值
	$("input[name='LXSQYSLFWWD_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='LXSQYSLFWWD_JYXKZH']").attr("disabled",false);
		$("input[name='LXSQYSLFWWD_JYXKZH']").addClass(" validate[required]");
		
		$("input[name='LXSQYSLFWWD_JYXKJG']").attr("disabled",false);
		$("input[name='LXSQYSLFWWD_JYXKJG']").addClass(" validate[required]");
		
		$("input[name='LXSQYSLFWWD_JYFW']").attr("disabled",false);
		$("input[name='LXSQYSLFWWD_JYFW']").addClass(" validate[required]");
	} else{
		
		$("input[name='LXSQYSLFWWD_JYXKZH']").attr("disabled",true);		
		$("input[name='LXSQYSLFWWD_JYXKZH']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFWWD_JYXKZH']").val('');	
		
		$("input[name='LXSQYSLFWWD_JYXKJG']").attr("disabled",true);		
		$("input[name='LXSQYSLFWWD_JYXKJG']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFWWD_JYXKJG']").val('');	
		
		$("input[name='LXSQYSLFWWD_JYFW']").attr("disabled",true);		
		$("input[name='LXSQYSLFWWD_JYFW']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFWWD_JYFW']").removeAttr("checked");	
	}
}

//旅行社企业设立分社备案
function setLxsqyslfs(){
	var arr = new Array();
	//获取值
	$("input[name='LXSQYSLFS_IN']:checked").each(function(i){
		arr[i] = $(this).val();
	});
	var Value = arr.join(",");
	if(Value == 1){			
		$("input[name='LXSQYSLFS_JYXKZH']").attr("disabled",false);
		$("input[name='LXSQYSLFS_JYXKZH']").addClass(" validate[required]");
		
		$("input[name='LXSQYSLFS_JYXKJG']").attr("disabled",false);
		$("input[name='LXSQYSLFS_JYXKJG']").addClass(" validate[required]");
		
		$("input[name='LXSQYSLFS_JYFW']").attr("disabled",false);
		$("input[name='LXSQYSLFS_JYFW']").addClass(" validate[required]");
	} else{
		
		$("input[name='LXSQYSLFS_JYXKZH']").attr("disabled",true);		
		$("input[name='LXSQYSLFS_JYXKZH']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFS_JYXKZH']").val('');	
		
		$("input[name='LXSQYSLFS_JYXKJG']").attr("disabled",true);		
		$("input[name='LXSQYSLFS_JYXKJG']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFS_JYXKJG']").val('');	
		
		$("input[name='LXSQYSLFS_JYFW']").attr("disabled",true);		
		$("input[name='LXSQYSLFS_JYFW']").removeClass(" validate[required]");
		$("input[name='LXSQYSLFS_JYFW']").removeAttr("checked");	
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