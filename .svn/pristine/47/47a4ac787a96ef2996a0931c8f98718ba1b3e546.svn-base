

//医保通用查询-个人信息综合查询
function grxxzhcx(){
	parent.$.dialog.open("ybCommonController.do?grxxzhcxSelector", {
		title : "个人信息综合查询",
		width:"90%",
		height:"90%",
	    lock : true,
	    resize : false,
	    close: function () {
	    }
	}, false);
	//window.top.location.href="ybCommonController.do?grxxzhcxSelector";//在同当前窗口中打开窗口
	//window.open("ybCommonController.do?grxxzhcxSelector");//在另外新建窗口中打开窗口
}

//医保通用查询-单位信息综合查询
function dwxxzhcx(){
	parent.$.dialog.open("ybCommonController.do?dwxxzhcxSelector", {
		title : "单位信息综合查询",
		width:"90%",
		height:"90%",
	    lock : true,
	    resize : false,
	    close: function () {
	    }
	}, false);
}




///输入身份证号码自动回填出生日期和性别
function getBirthAndSex(idCard,birth,xb){
	var idCard = $("input[name='"+idCard+"']").val();
	var birthday = getBirth(idCard);
	var sex= getSex(idCard) ;
	//alert(birthday+"-"+sex);
	$("input[name='"+birth+"']").val(birthday);
	$("select[name='"+xb+"']").val(sex);
}


function getBirth(idCard) {
  	var birthday = "";
	if(idCard != null && idCard != ""){
		if(idCard.length == 15){
			birthday = "19"+idCard.slice(6,12);
		} else if(idCard.length == 18){
			birthday = idCard.slice(6,14);
		}	
		birthday = birthday.replace(/(.{4})(.{2})/,"$1$2");
		//通过正则表达式来指定输出格式为:1990-01-01
	}	
	return birthday;
}


function getSex(idCard) {
	var sexStr = '';
	if (parseInt(idCard.slice(-2, -1)) % 2 == 1) {
		sexStr = '1';//奇数为男生
	}
	else {
		sexStr = '2';//偶数为女生
	}
	return sexStr;
}


//数据格式化
function dataFormat(value,json){
	var data = JSON.parse(json);
	var rtn = "";
	$.each(data, function(idx, dic) {
		if(value==dic.DIC_CODE){
			rtn = dic.DIC_NAME;
			return false;
		}
	});
	return rtn;
}
//分中心格式化
var subCenterObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'subCenter'}
});
function formatSubCenter(value){
	var json = subCenterObj.responseText;
	return dataFormat(value,json);
}
//参保身份格式化
var cbsfObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'insuredIdentity'}
});
function formatCbsf(value){
	var json = cbsfObj.responseText;
	return dataFormat(value,json);
}
//参保状态格式化
var cbztObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'insuredState'}
});
function formatCbzt(value){
	var json = cbztObj.responseText;
	return dataFormat(value,json);
}
//险种类型格式化
var xzlxObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'TypeOfInsurance'}
});
function formatXzlx(value){
	var json = xzlxObj.responseText;
	return dataFormat(value,json);
}
//险种类别格式化
var xzlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'insuranceSign'}
});
function formatXzlb(value){
	var json = xzlbObj.responseText;
	return dataFormat(value,json);
}
//性别格式化
var sexObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'ybSex'}
});
function formatSex(value){
	var json = sexObj.responseText;
	return dataFormat(value,json);
}
//民族格式化
var mzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'ybNation'}
});
function formatMz(value){
	var json = mzObj.responseText;
	return dataFormat(value,json);
}
//户口性质格式化
var hkxzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'populateNature'}
});
function formatHkxz(value){
	var json = hkxzObj.responseText;
	return dataFormat(value,json);
}
//单位类别格式化
var dwlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'DWLB'}
});
function formatDwlb(value){
	var json = dwlbObj.responseText;
	return dataFormat(value,json);
}
//隶属关系格式化
var lsgxObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'Affiliation'}
});
function formatLsgx(value){
	var json = lsgxObj.responseText;
	return dataFormat(value,json);
}
//征收方式格式化
var zsfsObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'collectionMethod'}
});
function formatZsfs(value){
	var json = zsfsObj.responseText;
	return dataFormat(value,json);
}
//有效标志格式化
var yxbzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'effectiveSign'}
});
function formatYxbz(value){
	var json = yxbzObj.responseText;
	return dataFormat(value,json);
}
//文化程度
var whcdObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'eduLevel'}
});
function formatWhcd(value){
	var json = whcdObj.responseText;
	return dataFormat(value,json);
}
//个人身份格式化
var grsfObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'GRSF'}
});
function formatGrsf(value){
	var json = grsfObj.responseText;
	return dataFormat(value,json);
}
//用工形式格式化
var ygxsObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'YGXS'}
});
function formatYgxs(value){
	var json = ygxsObj.responseText;
	return dataFormat(value,json);
}
//专业技术格式化
var zyjsObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'ZYJS'}
});
function formatZyjs(value){
	var json = zyjsObj.responseText;
	return dataFormat(value,json);
}
//技术等级
var jsdjObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'JSDJ'}
});
function formatJsdj(value){
	var json = jsdjObj.responseText;
	return dataFormat(value,json);
}
//就业状况
var jyzkObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'employmentStatus'}
});
function formatJyzk(value){
	var json = jyzkObj.responseText;
	return dataFormat(value,json);
}
//婚姻状况
var hyzkObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'maritalStatus'}
});
function formatHyzk(value){
	var json = hyzkObj.responseText;
	return dataFormat(value,json);
}
//职务
var zwObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'post'}
});
function formatZw(value){
	var json = zwObj.responseText;
	return dataFormat(value,json);
}
//农民工标识
var nmgbsObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'NMGBS'}
});
function formatNmgbs(value){
	var json = nmgbsObj.responseText;
	return dataFormat(value,json);
}
//个人缴费状态
var grjfztObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'GRJFZT'}
});
function formatGrjfzt(value){
	var json = grjfztObj.responseText;
	return dataFormat(value,json);
}
//证件类型
var zjlxObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'idCardType'}
});
function formatZjlx(value){
	var json = zjlxObj.responseText;
	return dataFormat(value,json);
}
//生存状态
var scztObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'survivalState'}
});
function formatSczt(value){
	var json = scztObj.responseText;
	return dataFormat(value,json);
}
//离退休标志
var ltxbzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'LTXBZ'}
});
function formatLtxbz(value){
	var json = ltxbzObj.responseText;
	return dataFormat(value,json);
}
//单位类型
var dwlxObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'TypeOfUnit'}
});
function formatDwlx(value){
	var json = dwlxObj.responseText;
	return dataFormat(value,json);
}

//军转干部标志
var jzgbbzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'cadreSign'}
});
function formatJzgbbz(value){
	var json = jzgbbzObj.responseText;
	return dataFormat(value,json);
}
//是否新生儿
var xseObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'SXE'}
});
function formatXse(value){
	var json = xseObj.responseText;
	return dataFormat(value,json);
}
//险种主体类别
var xzztlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'XZZTLB'}
});
function formatXzztlb(value){
	var json = xzztlbObj.responseText;
	return dataFormat(value,json);
}

//有效标志
var yxbzObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'effectiveSign'}
});
function formatYxbz(value){
	var json = yxbzObj.responseText;
	return dataFormat(value,json);
}

//银行账号用途类别
var yhzhytlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'YHZHYTLB'}
});
function formatYhzhytlb(value){
	var json = yhzhytlbObj.responseText;
	return dataFormat(value,json);
}

//行业风险类别
var hyfxlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'HYFXLB'}
});
function formatHyfxlb(value){
	var json = hyfxlbObj.responseText;
	return dataFormat(value,json);
}

//工商登记执照种类
var gsdjzzzlObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'GSZZZL'}
});
function formatGsdjzzzl(value){
	var json = gsdjzzzlObj.responseText;
	return dataFormat(value,json);
}

//经济类型
var jjlxObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'GSZZZL'}
});
function formatJjlx(value){
	var json = jjlxObj.responseText;
	return dataFormat(value,json);
}

//行业代码
var hydmObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'HYDM'}
});
function formatHydm(value){
	var json = hydmObj.responseText;
	return dataFormat(value,json);
}

//特殊单位类别
var tsdwlbObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'UnitCategory'}
});
function formatTsdwlb(value){
	var json = tsdwlbObj.responseText;
	return dataFormat(value,json);
}

//卡状态
var kztObj = $.ajax({
    method:'post',
    url:'dictionaryController.do?load',
    async:false,
    dataType:'json',
    data:{typeCode:'KZT'}
});
function formatKzt(value){
	var json = kztObj.responseText;
	return dataFormat(value,json);
}

//地税比对结果
var dsbdjgObj = $.ajax({
  method:'post',
  url:'dictionaryController.do?load',
  async:false,
  dataType:'json',
  data:{typeCode:'DSBDJG'}
});
function formatDsbdjg(value){
	var json = dsbdjgObj.responseText;
	return dataFormat(value,json);
}

//核对标记
var hdbjObj = $.ajax({
	  method:'post',
	  url:'dictionaryController.do?load',
	  async:false,
	  dataType:'json',
	  data:{typeCode:'YESNO'}
	});
function formatHdbj(value){
	var json = hdbjObj.responseText;
	return dataFormat(value,json);
}



