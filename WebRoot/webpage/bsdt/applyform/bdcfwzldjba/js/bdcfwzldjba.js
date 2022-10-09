$(function() {
	
	//$("select[name='DYQRXX_IDTYPE']").attr("disabled","disabled");
	$("select[name='DYQDJ_DYFS']").attr("disabled","disabled");
	
	//点击类型触发事件
	$("input[name='TAKECARD_TYPE']").on("click", function(event) {
		setTakecardType(this.value);
	});
	//加载自动补全数据
    /* loadAutoCompleteDatas();
	 $("#AutoCompleteInput").result(function(event, data, formatted) {	
		$("input[name='DYQRXX_IDNO']").val(data.DIC_CODE);
		$("input[name='DYQRXX_LEGAL_NAME']").val(data.DIC_DESC);
	}); */
	
	
	
});

function setRuleContent(){
       var selectValue = $('#_select option:selected').text();//选中select的内容
       //alert("selectValue" + selectValue);

var inputValue = $("#_input").val(selectValue);//input获得select的内容并显示在输入框中
//alert(inputValue);
}
function FLOW_SubmitFun(flowSubmitObj){	
	 //先判断表单是否验证通过
	try {
	 var validateResult =$("#T_BDC_FWZLDJBA_FORM").validationEngine("validate");
	 if(validateResult){
		 flowSubmitObj.ryTab=getRyTabjson();
		 flowSubmitObj.CzrxxTab=getCzrxxTabJson();
		 flowSubmitObj.CzfxxTab=getCzFxxTabJson();
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			//先获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_BDC_FWZLDJBA_FORM");
			for(var index in formData){
				flowSubmitObj[eval("index")] = formData[index];
			}
			
			console.dir(flowSubmitObj);	
		
		  
			return flowSubmitObj;
			}
			
       }catch(err){
	 flowSubmitObj.ryTab=getRyTabjson();
	 flowSubmitObj.CzrxxTab=getCzrxxTabJson();
	 flowSubmitObj.CzfxxTab=getCzFxxTabJson();
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BDC_FWZLDJBA_FORM");
		console.log("数据："+formData)
		for(var index in formData){
			flowSubmitObj[eval("index")] = formData[index];
		}
		console.dir(flowSubmitObj);	
		return flowSubmitObj;
}
	
	

}
 function setJJRXXShow(){
	$("select[name='CJFS']").on("click", function(event) {
		if(this.value=='房地产经纪机构居间成交' || this.value=='房地产经纪构代理成交' ){
			$("span[name='JJJG_NAME']").attr("style","");
			$("span[name='JJR_NAME']").attr("style","");
			$("span[name='JJRLX_PHONE']").attr("style","");
			$("tr[name='tr1']").attr("style","");
			$("input[name='JJJG_NAME']").attr("type","text");
			$("input[name='JJR_NAME']").attr("type","text");
			$("input[name='JJRLX_PHONE']").attr("type","text");
		}else{
			$("span[name='JJJG_NAME']").attr("style","display:none");
			$("span[name='JJR_NAME']").attr("style","display:none");
			$("span[name='JJRLX_PHONE']").attr("style","display:none");
			$("tr[name='tr1']").attr("style","display:none");
			$("input[name='JJJG_NAME']").attr("type","hiden");
			$("input[name='JJR_NAME']").attr("type","hiden");
			$("input[name='JJRLX_PHONE']").attr("type","hiden");
			
		}
	});
	
	
}
//保存人员信息
function addPersons(){
	var result = []; // 数组
	var czrxx_tab = document.getElementById("czrxx_tab");//获取表格
  var rows = czrxx_tab.rows;//获取所有行
  for(var i=0; i < rows.length; i++){
    var row = rows[i];//获取每一行
    var PERSON_NAME = row.cells[1].innerHTML;//获取具体单元格
    //var QLBL =row.cells[2].innerHTML;
    var LASSEE_TYPE=row.cells[2].innerHTML;
	var CARD_TYPE=row.cells[3].innerHTML;
	var CARD_NO=row.cells[4].innerHTML;
	var SEX=row.cells[5].innerHTML;
    var PHONE=row.cells[6].innerHTML;
    var ADDRESS=row.cells[7].innerHTML;
    var EMAIL=row.cells[8].innerHTML;
    result.push({
    	"person_type":"2",
    	"lassee_type" :  LASSEE_TYPE,
    	"person_name" :PERSON_NAME,
    	"card_type" :  CARD_TYPE ,
    	"card_no" :   CARD_NO ,  
    	"sex":      SEX ,   
    	"phone":    PHONE,   
    	"ADDRESS":ADDRESS,
    	"EMAIL":EMAIL
    })
 }
  
	 $.ajax({
		    url : "bdcFwzldjbaController/savePersons.do",
			type : "post",
			
			data : {'data':JSON.stringify(result)},
			dataType:"json",
			success : function(data) {
	        console.log(data);
			}

		});
	
}
//保存人员信息
function addxgPersons(){
	var result = []; // 数组
	var czrxx_tab = document.getElementById("ry_tab");//获取表格
var rows = czrxx_tab.rows;//获取所有行
for(var i=1; i < rows.length; i++){
  var row = rows[i];//获取每一行
  var PERSON_TYPE=row.cells[1].innerHTML
  var PERSON_NAME = row.cells[2].innerHTML;//获取具体单元格
  var QLBL ="";
  var LASSEE_TYPE="";
  var CARD_TYPE="";
  if(PERSON_TYPE=='5'){
	  //居(同)住人默认身份证
	  CARD_TYPE="身份证";
  }else{
	  CARD_TYPE=row.cells[3].innerHTML;
  }
	var CARD_NO=row.cells[4].innerHTML;
	var SEX="";
  var PHONE="";
  var ADDRESS="";
  var EMAIL="";
  result.push({
  	"person_type": PERSON_TYPE,
  	"lassee_type" :  LASSEE_TYPE,
  	"person_name" :PERSON_NAME,
  	"card_type" :  CARD_TYPE ,
  	"card_no" :   CARD_NO ,  
  	"sex":      SEX ,   
  	"phone":    PHONE,   
  	"ADDRESS":ADDRESS,
  	"EMAIL":EMAIL
  })
}

	 $.ajax({
		    url : "bdcFwzldjbaController/savePersons.do",
			type : "post",
			
			data : {'data':JSON.stringify(result)},
			dataType:"json",
			success : function(data) {
	        console.log(data);
			}

		});
	
}
function getRyTabjson(){
	
	var result = []; // 数组
	var czrxx_tab = document.getElementById("ry_tab");//获取表格
var rows = czrxx_tab.rows;//获取所有行
for(var i=1; i < rows.length; i++){
  var row = rows[i];//获取每一行
  var PERSON_TYPE=row.cells[1].innerHTML
  var PERSON_NAME = row.cells[2].innerHTML;//获取具体单元格
  var QLBL ="";
  var LASSEE_TYPE="";
  var CARD_TYPE="";
  if(PERSON_TYPE=='5'){
	  //居(同)住人默认身份证
	  CARD_TYPE="身份证";
  }else{
	  CARD_TYPE=row.cells[3].innerHTML;
  }
	var CARD_NO=row.cells[4].innerHTML;
	var SEX="";
  var PHONE="";
  var ADDRESS="";
  var EMAIL="";
  result.push({
  	"person_type": PERSON_TYPE,
  	"lassee_type" :  LASSEE_TYPE,
  	"person_name" :PERSON_NAME,
  	"card_type" :  CARD_TYPE ,
  	"card_no" :   CARD_NO ,  
  	"sex":      SEX ,   
  	"phone":    PHONE,   
  	"ADDRESS":ADDRESS,
  	"EMAIL":EMAIL
  })
}
	if(result.length>0){
		return JSON.stringify(result);
	}else{
		return "";
	}
}

function getCzrxxTabJson(){
	var result = []; // 数组
	var czrxx_tab = document.getElementById("czrxx_tab");//获取表格
  var rows = czrxx_tab.rows;//获取所有行
  for(var i=1; i < rows.length; i++){
    var row = rows[i];//获取每一行
    var PERSON_NAME = row.cells[1].innerHTML;//获取具体单元格
    var LASSEE_TYPE=row.cells[2].innerHTML;
	var CARD_TYPE=row.cells[3].innerHTML;
	var CARD_NO=row.cells[4].innerHTML;
	var SEX=row.cells[5].innerHTML;
    var PHONE=row.cells[6].innerHTML;
    var ADDRESS=row.cells[7].innerHTML;
    var EMAIL=row.cells[8].innerHTML;
    result.push({
    	"person_type":"2",
    	"lassee_type" :  LASSEE_TYPE,
    	"person_name" :PERSON_NAME,
    	"card_type" :  CARD_TYPE ,
    	"card_no" :   CARD_NO ,  
    	"sex":      SEX ,   
    	"phone":    PHONE,   
    	"ADDRESS":ADDRESS,
    	"EMAIL":EMAIL
    })
 }
  if(result.length>0){
		return JSON.stringify(result);
	}else{
		return "";
	}
}
function getCzFxxTabJson(){
	var result = []; // 数组
	var czf_xxtab = document.getElementById("czf_xxtab");//获取表格
  var rows = czf_xxtab.rows;//获取所有行
  for(var i=1; i < rows.length; i++){
    var row = rows[i];//获取每一行
    var PERSON_NAME = row.cells[1].innerHTML;//获取具体单元格
    var LASSEE_TYPE=row.cells[2].innerHTML;
	var CARD_TYPE=row.cells[3].innerHTML;
	var CARD_NO=row.cells[4].innerHTML;
	var SEX=row.cells[5].innerHTML;
    var PHONE=row.cells[6].innerHTML;
    var ADDRESS=row.cells[7].innerHTML;
    var EMAIL=row.cells[8].innerHTML;
    result.push({
    	"person_type":"6",
    	"lassee_type" :  LASSEE_TYPE,
    	"person_name" :PERSON_NAME,
    	"card_type" :  CARD_TYPE ,
    	"card_no" :   CARD_NO ,  
    	"sex":      SEX ,   
    	"phone":    PHONE,   
    	"ADDRESS":ADDRESS,
    	"EMAIL":EMAIL
    })
 }
  if(result.length>0){
		return JSON.stringify(result);
	}else{
		return "";
	}
  }
//消息提示
function setMyMsg(msg) {
	art.dialog({
		content : msg,
		icon : "succeed",
		ok : true
	});
}
//添加出租方信息
function addCzfryxx(){
//str：'人员类型:0-法定代表人（负责人）,1-代理人,2-承租人,3-负责人,4-代理人,5-居(同)住人,6出租人';
var  CZF_PERSON_TYPE = '6';
var  CZF_PERSON_NAME = $("#CZR_NAME").combobox("getText");
var  CZF_LASSEE_TYPE = $("#CZRXZ").val();
var  CZF_CARD_TYPE = $("select[name='CZR_CARD_TYPE']").val();
var  CZF_CARD_NO = $("#CZR_CARD_NO").combobox("getText");
if(CZF_CARD_TYPE==''){
	  setMyMsg("出租人证件类型为空，请重新输入！");
	  return
}
if(CZF_CARD_NO==''){
	  setMyMsg("出租人证件号码为空，请重新输入！");
	return  
}
var  CZF_SEX = $("select[name='CZR_SEX']").val();
if(CZF_SEX=="请选择性别"){
	  setMyMsg("请选择性别！");
	  return	
}
var CZF_PHONE = $("#CZR_PHONE").val();
var CZF_ADDRESS =$("#CZR_ADDRESS").val();
var CZF_EMAIL =$("#CZR_EMAIL").val();
var CZF_tables = $('#czf_xxtab');
var CZF_table = document.getElementById("czf_xxtab");
var CZF_rows = CZF_table.rows;
var CZF_counttr=CZF_rows.length;
	var html = "";
		html +='<tr>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_counttr+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_PERSON_NAME+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_LASSEE_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_CARD_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_CARD_NO+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_SEX+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_PHONE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_ADDRESS+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CZF_EMAIL+'</td>';
		html +='<td>';
		html +='<a href="javascript:void(0);" onclick="delCzrxx(this);" class="syj-closebtn"></a></td>';
		html +='</tr>';
var addHtml=$(html);		
addHtml.appendTo(CZF_table);   
$('#CZR_NAME').combobox("setValue","");
$("#CZRXZ").val("");
$("select[name='CZR_CARD_TYPE']").val("");
$('#CZR_CARD_NO').combobox("setValue","");
$("select[name='CZR_SEX']").val("");
$("#CZR_PHONE").val("");
$("#CZR_ADDRESS").val("");
$("#CZR_EMAIL").val("");
	}
//添加承租方信息
function addCzfxx(){
	
//str：'人员类型:0-法定代表人（负责人）,1-代理人,2-承租人,3-负责人,4-代理人,5-居(同)住人';
  var  PERSON_TYPE = '2';
  var  PERSON_NAME = $('#LESSEE_NAME').val();
  var  LASSEE_TYPE = $("#LESSEE_TYPE").val();
  var  CARD_TYPE =  $("select[name='LESSEE_CARD_TYPE']").val();
  var  CARD_NO = $("#LESSEE_CARD_NO").val();
  if(CARD_TYPE==''){
	  setMyMsg("承租人证件类型为空，请重新输入！");
	  return
  }
  if(CARD_NO==''){
	  setMyMsg("承租人证件号码为空，请重新输入！");
	return  
  }
  
  var  SEX = $("select[name='LESSEE_SEX']").val();
  if(SEX=="请选择性别"){
	  setMyMsg("请选择性别！");
	  return	
  }
  var  PHONE = $("#LESSEE_PHONE").val();
  var ADDRESS =$("#LESSEE_ADDRESS").val();
  var EMAIL =$("#LESSEE_EMAIL").val();
 
  var tables = $('#czrxx_tab');
  var table = document.getElementById("czrxx_tab");
  var rows = table.rows;
  var counttr=rows.length;
	var html = "";
		html +='<tr>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+counttr+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+PERSON_NAME+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+LASSEE_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CARD_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CARD_NO+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+SEX+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+PHONE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+ADDRESS+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+EMAIL+'</td>';
		html +='<td>';
		html +='<a href="javascript:void(0);" onclick="delCzrxx(this);" class="syj-closebtn"></a></td>';
		html +='</tr>';
 var addHtml=$(html);		
 addHtml.appendTo(tables);  
 $('#LESSEE_NAME').val("");
 $("#LESSEE_TYPE").val("");
 $("select[name='LESSEE_CARD_TYPE']").val("");
 $("#LESSEE_CARD_NO").val("");
 $("select[name='LESSEE_SEX']").val("");
 $("#LESSEE_PHONE").val("");
 $("#LESSEE_ADDRESS").val("");
 $("#LESSEE_EMAIL").val("");
	}
function addCzffddbrxx(){
	
	//str：'人员类型:0-法定代表人（负责人）,1-代理人,2-承租人,3-负责人,4-代理人,5-居(同)住人';
	  var  LESSEE_FDDBR_NMAE = $('#LESSEE_FDDBR_NMAE').val();
	  var  LESSEE_FDDBR_CARD_TYPE =  $("select[name='LESSEE_FDDBR_CARD_TYPE']").val();
	  var  LESSEE_FDDBR_CARD_NO = $("#LESSEE_FDDBR_CARD_NO").val();
	 if(LESSEE_FDDBR_NMAE!=''&&LESSEE_FDDBR_CARD_TYPE!=''&&LESSEE_FDDBR_CARD_NO!=''){
		 addhtml('法定代表人（负责人）',LESSEE_FDDBR_NMAE,LESSEE_FDDBR_CARD_TYPE,LESSEE_FDDBR_CARD_NO);
		 
	 }	 
	 
	  var  LESSEE_DLR_NAME = $('#LESSEE_DLR_NAME').val();
	  var  LESSEE_DLR_CARD_TYPE =  $("select[name='LESSEE_DLR_CARD_TYPE']").val();
	  var  LESSEE_DLR_CARD_NO = $("#LESSEE_DLR_CARD_NO").val();
	  
	  if(LESSEE_DLR_NAME!=''&&LESSEE_DLR_CARD_TYPE!=''&&LESSEE_DLR_CARD_NO!=''){
			 addhtml('代理人',LESSEE_DLR_NAME,LESSEE_DLR_CARD_TYPE,LESSEE_DLR_CARD_NO);
			 
		 }
	  
	  var  JTZR = $('#JTZR').val();
	  var  JTZR_CARD_NO=$("input[name='JTZR_CARD_NO']").val();	  
	  if(JTZR!=''&&JTZR_CARD_NO!=''){
			 addhtml('居(同)住人',JTZR,'身份证',JTZR_CARD_NO);
			 
		 }
	  $('#LESSEE_FDDBR_NMAE').val("");
	  $("select[name='LESSEE_FDDBR_CARD_TYPE']").val("");
	  $("#LESSEE_FDDBR_CARD_NO").val("");
	  $('#LESSEE_DLR_NAME').val("");
	  $("select[name='LESSEE_DLR_CARD_TYPE']").val("");
	  $("#LESSEE_DLR_CARD_NO").val("");
	  $('#JTZR').val("");
	  $("input[name='JTZR_CARD_NO']").val("");
		}



//打印申请表、备案证明
function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板
	//dataStr +="&TemplatePath="+TemplatePath;
	dataStr +="&TemplateName="+TemplateName;
	//dataStr +="&isSignButton="+isSignButton;
	var url=encodeURI("printAttachController.do?printTemplate"+dataStr);
	//打印模版
	$.dialog.open(url, {
		title : "打印模版",
		width: "100%",
		height: "100%",
		left: "0%",
		top: "0%",
		fixed: true,
		lock : true,
		resize : false
	}, false);
}
function addhtml(PERSON_TYPE,PERSON_NAME,CARD_TYPE,CARD_NO){  
	var tables = $('#ry_tab');

var table = document.getElementById("ry_tab");
var rows = table.rows;
var counttr=rows.length;
	var html = "";
		html +='<tr>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+counttr+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+PERSON_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+PERSON_NAME+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CARD_TYPE+'</td>';
		html +='<td style="width: 10%;color: #000; text-align: center;">'+CARD_NO+'</td>';
		html +='<td >';
		html +='<a href="javascript:void(0);" onclick="delCzrxx(this);" class="syj-closebtn"></a></td>';
		html +='</tr>';
var addHtml=$(html);		
addHtml.appendTo(tables); 
}
//用于删除列表中的数据
function delCzrxx(id) {
  var o = $(id).parents('tr');
  o.remove();

};

/**抵押权人性质*/
function dyqrChangeFun(c,flag){
    //var c = $("select[name='DYQRXX_NATURE']").val();
    if(c == '1'){
    	if(flag){
    		$("select[name='DYQRXX_IDTYPE'] option[value='身份证']").prop("selected", true);
    	}
        $("select[name='DYQRXX_IDTYPE']").removeAttr("disabled");
        $("#dyr_gr").attr("style","");
        $("#dyr_qy").attr("style","display:none;");
        $("#DYQRXX_LEGAL_NAME_font").attr("style","display:none;");
        $("#DYQRXX_AGENT_NAME_font").attr("style","display:none;");
        $("input[name='DYQRXX_LEGAL_NAME']").removeClass("validate[required]");
        $("input[name='DYQRXX_AGENT_NAME']").removeClass("validate[required]");
    }else{
        $("select[name='DYQRXX_IDTYPE'] option[value='营业执照']").prop("selected", true);
        $("select[name='DYQRXX_IDTYPE']").attr("disabled","disabled");
        $("#dyr_qy").attr("style","");
        $("#dyr_gr").attr("style","display:none;");
        $("#DYQRXX_LEGAL_NAME_font").attr("style","");
        $("#DYQRXX_AGENT_NAME_font").attr("style","");
        $("input[name='DYQRXX_LEGAL_NAME']").addClass("validate[required]");
        $("input[name='DYQRXX_AGENT_NAME']").addClass("validate[required]");
    }
}

//社会限制人员校验
function checkLimitPerson(){
	var data = [];
	var flag = true;
	//获取抵押人
	var dyrmc=$("input[name='DYQDJ_NAME']").val();
	var idno=$("input[name='DYQDJ_IDNO']").val();
	if(dyrmc!='' && idno!=''){
		var dyrmcs = dyrmc.split('、');
		var idnos = idno.split('、');
		if(dyrmcs.length==idnos.length){
			for(var i=0;i<idnos.length;i++){
				var dyqData={};
				dyqData["xm"]=dyrmcs[i];
				dyqData["zjhm"]=idnos[i];
				data.push(dyqData);
			}
		}else{
			parent.art.dialog({
				content: "抵押权人姓名与证件号需一一对应！",
				icon:"warning",
				ok: true
			});
			flag = false;
		}
	}
	if(data.length<1){
		flag = false ;
	}
	var cxlist = JSON.stringify(data);
	$.ajaxSettings.async = false;
	$.post("bdcApplyController.do?checkLimitPerson",{cxlist:cxlist},
		function(responseText, status, xhr) {
			var obj =responseText.rows;
			if(responseText.total>0){
			    var name="";
			    for(var i=0;i<obj.length;i++){
			    	name+=obj[i].XM+"("+obj[i].ZJHM+")、";
			    }
			    name=name.substring(0,name.length-1);
				parent.art.dialog({
					content: "存在涉会/涉黑人员【"+name+"】,不可受理此登记！",
					icon:"warning",
					ok: true
				});
				flag = false;
			}
   	});
   	return flag;
}
function isPowerPeople(){
     var powerPeopleInfoSn=$("input[name='POWERPEOPLEINFO_JSON']").val();
     var sqrmc=$("input[name='SQRMC']").val();
     if(powerPeopleInfoSn.indexOf(sqrmc)>0){
         return true;
	 }else{
         return false;
	 }
}
function FLOW_TempSaveFun(flowSubmitObj){
	//先判断表单是否验证通过

	 flowSubmitObj.ryTab=getRyTabjson();
	 flowSubmitObj.CzrxxTab=getCzrxxTabJson();
	 flowSubmitObj.CzfxxTab=getCzFxxTabJson();
	 console.log( flowSubmitObj.CzfxxTab);
	var type = $("input[name='TAKECARD_TYPE']:checked").val();
	if(type==2){
		getQslyInfoJson();
	} else{
		$("input[name='QSLY_JSON']").val('');
	}
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_FWZLDJBA_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}
	
	return flowSubmitObj;

}

function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


function setFileNumber(serialNum){
	/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
	var enterprise = '${execution.SQJG_CODE}';
	var idCard = '${execution.SQRSFZ}';
	var fileNum;
	if (enterprise != ""){
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
	} else {
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
	}
	$("#fileNumber").val(fileNum);
}
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}

var bdcdydacxTr = 1;
function initAutoTable(flowSubmitObj){
	var dymxJson = flowSubmitObj.busRecord.DYMX_JSON;
	if(null != dymxJson && '' != dymxJson){
		var dymxInfos = eval(dymxJson);
		var html = "";
		for(var i=0;i<dymxInfos.length;i++){
			html +='<tr class="bdcdydacxTr" id="bdcdydacxTr_'+bdcdydacxTr+'">';
			html +='<input type="hidden" name="bdcdyxx"/>';
			html +='<td style="text-align: center;">'+bdcdydacxTr+'</td>';
			html +='<td style="text-align: center;">'+dymxInfos[i].ZH+'</td>';
			html +='<td style="text-align: center;">'+dymxInfos[i].HH+'</td>';
			html +='<td style="text-align: center;">'+dymxInfos[i].BDCQZH+'</td>';
			html +='<td style="text-align: center;">'+dymxInfos[i].BDCDYH+'</td>';
			html +='<td style="text-align: center;">'+dymxInfos[i].QSZT+'</td>';
			html +='<td style="text-align: center;">';
			html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);" id="dymxAdd">查 看</a>';
			html +='<a href="javascript:void(0);" onclick="delDymxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
			html +='</tr>';
			$("#dymxTable").append(html);
			$("#bdcdydacxTr_"+bdcdydacxTr+" input[name='bdcdyxx']").val(JSON.stringify(dymxInfos[i]));
			bdcdydacxTr ++;
			html = "";
		}
		loadBdcdaxxcxToId();
	}

	var qslyJson = flowSubmitObj.busRecord.QSLY_JSON;
	if(null != qslyJson && '' != qslyJson){
		var qslyInfos = eval(qslyJson);
		for(var i=0;i<qslyInfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(qslyInfos[i],"qslyInfo_1");
				$("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
			}else{
				addQslyInfo();
				FlowUtil.initFormFieldValue(qslyInfos[i],"qslyInfo_"+(i+1));
				$("#qslyInfo_"+(i+1)+" input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
			}
		}
	}
}

function setTakecardType(value){

	if (value === "1") {
		$("#qsly").hide();
		$("input[name='BDCDYH']").attr("disabled",true);
		$("input[name='BDCDYH']").removeClass(" validate[required]");
	} else {
		$("#qsly").show();
		$("input[name='BDCDYH']").attr("disabled",false);
		$("input[name='BDCDYH']").addClass(" validate[required]");
	}
}

function setSfzgedy(value){
	if (value === "1") {	
		$("input[name='DYQDJ_ZGZQE']").attr("disabled",false);
		$("input[name='DYQDJ_BDBZZQSE']").attr("disabled",true);
		$("select[name='DYQDJ_DYFS']").val("2");
	}else{		
		$("input[name='DYQDJ_ZGZQE']").attr("disabled",true); 
		$("input[name='DYQDJ_BDBZZQSE']").attr("disabled",false);
		$("select[name='DYQDJ_DYFS']").val("1");
	}
}
function setDyqrxxName(){
	var name = $("select[name='DYQRXX_NAME_SELECT']").find("option:selected").text();
	var value = $("select[name='DYQRXX_NAME_SELECT']").val();
	var desc = $("select[name='DYQRXX_NAME_SELECT']").find("option:selected").attr("dicdesc");
	if(''==value){
		name = "";
	}
	$("input[name='DYQRXX_NAME']").val(name);
	$("input[name='DYQRXX_IDNO']").val(value);
	$("input[name='DYQRXX_LEGAL_NAME']").val(desc);
}
/**=================权属来源信息开始================================*/

function showSelectBdcygdacx(){	
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=0", {
		title : "不动产预告档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcygdacxInfo){
				/*var table = document.getElementById("qslyInfo");
				 if(table.rows.length>1){
					for ( var i = 1; i <= table.rows.length; i++) {
						deleteQslyInfo(i+1);
					}			
				}
				qslyInfoSn = 1;	 */	
				
				//$("#qslyInfo_1 input[name='deleteQslyInfoInput']").show();
				
				var bdcdyh = $("#qslyInfo").find('table').eq(0).find("input[name='BDCDYH']").val();
				for(var i = 0;i<bdcygdacxInfo.length;i++){
					
					if(i==0 && qslyInfoSn==1 && !bdcdyh){
						FlowUtil.initFormFieldValue(bdcygdacxInfo[i],"qslyInfo_1");
						$("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
					}else{
						addQslyInfo();
						FlowUtil.initFormFieldValue(bdcygdacxInfo[i],"qslyInfo_"+(qslyInfoSn));
						$("#qslyInfo_"+(qslyInfoSn)+" input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
					}
				}	
				//$("#qslyInfo_1 input[name='deleteQslyInfoInput']").hide();
				
				$("#qslyInfo").find("input,select").attr("disabled","disabled");
				$("#qslyInfo").find("input,select").attr("readonly","readonly");
				//去除权属来源中按钮的不可编辑性
				$("#qslyInfo").find("input[type='button']").attr("disabled",false);
				$("#qslyInfo").find("input[type='button']").attr("readonly",false);
				art.dialog.removeData("bdcygdacxInfo");
				
			}
		}
	}, false);
};
var qslyInfoSn = 1;
function addQslyInfo(){
	qslyInfoSn = qslyInfoSn+1;
	var table = document.getElementById("qslyInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteQslyInfo('");
	if(qslyInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteQslyInfo('"+qslyInfoSn+"')");
	trHtml = "<tr id=\"qslyInfo_"+qslyInfoSn+"\">"+trHtml+"</tr>";
	$("#qslyInfo").append(trHtml);
}


function deleteQslyInfo(idSn){
	var table = document.getElementById("qslyInfo");
	if(table.rows.length==1){
		parent.art.dialog({
			content: "最少一个权属来源信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#qslyInfo_"+idSn).remove();
}

function getQslyInfoJson(){
	var datas = [];
	var table = document.getElementById("qslyInfo");
	for ( var i = 0; i < table.rows.length; i++) {
		var bdcqsly = $("#qslyInfo").find('table').eq(i).find("input[name='bdcqsly']").val();
		var trData = {};
		if(""!=bdcqsly && null!= bdcqsly && undefined!=bdcqsly){		
			trData = JSON.parse(bdcqsly);
		}
		$("#qslyInfo").find('table').eq(i).find("*[name]").each(function(){
			  var inputName= $(this).attr("name");
			  if(inputName!="bdcqsly"){					 
				  var inputValue = $(this).val();
				  //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
					  var isChecked = $(this).is(':checked');
					  if(isChecked){
						  trData[inputName] = inputValue;
					  }
				  }else if(fieldType=="checkbox"){
					  var inputValues = FlowUtil.getCheckBoxValues(inputName);
					  trData[inputName] = inputValues;
				  }else if(fieldType!="button"){
					  trData[inputName] = inputValue;
				  }           
			  }
		});
		datas.push(trData);
		
	}
	$("input[type='hidden'][name='QSLY_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/


function onlyNumber(obj){       
	//得到第一个字符是否为负号  
	var t = obj.value.charAt(0);    
	//先把非数字的都替换掉，除了数字和.   
	obj.value = obj.value.replace(/[^\d\.]/g,'');     
	//必须保证第一个为数字而不是.     
	obj.value = obj.value.replace(/^\./g,'');     
	//保证只有出现一个.而没有多个.     
	obj.value = obj.value.replace(/\.{2,}/g,'.');     
	//保证.只出现一次，而不能出现两次以上     
	obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
	//只能输入小数点后两位
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加  
	if(t == '-'){  
		obj.value = '-'+obj.value;  
	}   
}  

/**=================抵押明细信息开始================================*/

function showSelectBdcdydacx(){
	var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();
	
	$.dialog.open("bdcDyqscdjController.do?selector&allowCount=0&negativeListCodes="+negativeListCodes, {
		title : "不动产抵押档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
			if(bdcdydacxInfo){
				var html = "";
				for(var i = 0;i<bdcdydacxInfo.length;i++){
					html +='<tr class="bdcdydacxTr">';
					html +='<td>'+(i+1)+'</td>';
					html +='<td>'+bdcdydacxInfo[i].ZH+'</td>';
					html +='<td>'+bdcdydacxInfo[i].HH+'</td>';
					html +='<td>'+bdcdydacxInfo[i].BDCQZH+'</td>';
					html +='<td>'+bdcdydacxInfo[i].BDCDJZMH+'</td>';
					html +='<td>'+bdcdydacxInfo[i].QSZT+'</td>';
					html +='<td>';
					html +='<a href="javascript:void(0);" onclick="delCycjDiv(this);" class="syj-closebtn"></a></td>';
					html +='</tr>';
				}	
				$(".bdcdydacxTr").remove();
				$("#dymxTable").append(html);
				art.dialog.removeData("bdcdydacxInfo");
				
			}
		}
	}, false);
};

function showSelectBdcdaxxcx(){	
	$.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if(bdcdaxxcxInfo){
				var html = "";
				for(var i = 0;i<bdcdaxxcxInfo.length;i++){
					
					html +='<tr class="bdcdydacxTr" id="bdcdydacxTr_'+bdcdydacxTr+'">';
					html +='<input type="hidden" name="bdcdyxx"/>';
					
					html +='</tr>';
					$("#dymxTable").append(html);
					//抵押不动产类型回填						
									
					$("#bdcdydacxTr_"+bdcdydacxTr+" input[name='bdcdyxx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
					bdcdydacxTr ++;
					html = "";
				}	
			
				setSqlandZl();////回填申请人以及坐落   回填不动产权证号
			
				art.dialog.removeData("bdcdaxxcxInfo");
				//loadBdcdaxxcxToId();
			}
		}
	}, false);
};


var dymxInfoSn = 1;
function addDymxInfo(){
	dymxInfoSn = dymxInfoSn+1;
	var table = document.getElementById("dymxInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	
	var findex = trHtml.indexOf("deleteDymxInfo('");
	var replacestr = "";
	if(dymxInfoSn>10){
		replacestr = trHtml.substring(findex,findex+20);
	}else{
		replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteDymxInfo('"+dymxInfoSn+"')");
	trHtml = "<tr id=\"dymxInfo_"+dymxInfoSn+"\">"+trHtml+"</tr>";
	$("#dymxInfo").append(trHtml);
}

function deleteDymxInfo(idSn){
	var table = document.getElementById("dymxInfo");
	if(table.rows.length==1){
		parent.art.dialog({
			content: "最少一个抵押明细信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#dymxInfo_"+idSn).remove();
	setTotlaJzmj();
	setTotlaZdmj();
	setTotlaDytdmj();
	setTotlaFttdmj();
}


/**
 * 获取抵押明细JSON
 */
function getDymxInfoJson(){
	var datas = [];
	 $("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			datas.push(JSON.parse(bdcdyxx));
		}
	 });
	var dymxJson = JSON.stringify(datas);
	$("input[type='hidden'][name='DYMX_JSON']").val(dymxJson);
}
function setTotlaJzmj(){	
	var totla = 0;
	 $("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			var bdcdyxxInfos = JSON.parse(bdcdyxx);	
			totla+=Number(bdcdyxxInfos.JZMJ);			
		}
	}) 
	$("input[name='DYQDJ_DYJZZMJ']").val(totla.toFixed(2));//抵押建筑总面积
	$("input[name='DYQDJ_DYZS']").val($(".bdcdydacxTr").length);//抵押宗数
}
function setTotlaDytdmj(){
	
	var totla = 0;
	 $("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			var bdcdyxxInfos = JSON.parse(bdcdyxx);	
			totla+=Number(bdcdyxxInfos.DYTDMJ);			
		}
	}) 
	$("input[name='DYQDJ_DYTDMJ']").val(totla.toFixed(2));//抵押独用土地面积
}
function setTotlaZdmj(){
	
	var totla = 0;
	$("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			var bdcdyxxInfos = JSON.parse(bdcdyxx);	
			totla+=Number(bdcdyxxInfos.ZDMJ);			
		}
	}) 
	$("input[name='DYQDJ_DYZSZMJ']").val(totla.toFixed(2));//抵押宗地总面积s
}
function setTotlaFttdmj(){
	
	var totla = 0;
	$("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			var bdcdyxxInfos = JSON.parse(bdcdyxx);	
			totla+=Number(bdcdyxxInfos.FTTDMJ);			
		}
	}) 
	$("input[name='DYQDJ_DYFTTDZMJ']").val(totla.toFixed(2));//抵押分摊土地总面积
}


/**=================抵押明细信息结束================================*/
function setSqlandZl(){
	$("#dymxTable tr").each(function(i){
		var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
		if(''!=bdcdyxx && null!=bdcdyxx){
			var bdcdyxxInfos = JSON.parse(bdcdyxx);	
			
			if((i==1) && ($(".bdcdydacxTr").length==1)){
				$("input[name='APPLICANT_UNIT']").val(bdcdyxxInfos.QLRMC);
				//回填不动产权证号
				$("input[name='BDCQZH']").val(bdcdyxxInfos.BDCQZH);
				//出租地址
				$("input[name='CZ_ADDR']").val(bdcdyxxInfos.FDZL);//房屋坐落
				//出租人名称：权利人名称
				var qlrmc=bdcdyxxInfos.QLRMC;
				var czrnamelist=qlrmc.split(",");
                var datas=[];
				for(var i =0;i<czrnamelist.length;i++){
					datas.push({label:czrnamelist[i],value:czrnamelist[i]});
                    }
				
			    $("#CZR_NAME").combobox("loadData",datas);

				//出租人证件类型
				$("#CZR_CARD_TYPE").val(bdcdyxxInfos.ZJLX);
				//出租人证件号码
				var czrzjhm=bdcdyxxInfos.ZJHM;
				var czrzjhmlist=czrzjhm.split(",");
                var zjhmdatas=[];
				for(var i =0;i<czrzjhmlist.length;i++){
					zjhmdatas.push({label:czrzjhmlist[i],value:czrzjhmlist[i]});
                    }
			    $("#CZR_CARD_NO").combobox("loadData",zjhmdatas);

				//权利比例
				$("input[name='QLBL']").val(bdcdyxxInfos.QLBL);
				//出租人性质：权利人性质
				$("input[name='CZRXZ']").val(bdcdyxxInfos.QLRXZ);
				
				//出租人地址
				$("input[name='ADDRESS']").val(bdcdyxxInfos.FDZL);
				//回填房屋信息
				$("input[name='FW_ZL']").val(bdcdyxxInfos.FDZL);
				$("input[name='ZH']").val(bdcdyxxInfos.ZH);
				$("input[name='FW_SZC']").val(bdcdyxxInfos.SZC);
				$("input[name='FW_HH']").val(bdcdyxxInfos.HH);
				$("input[name='FW_ZCS']").val(bdcdyxxInfos.ZCS);
				$("input[name='FW_GHYT']").val(bdcdyxxInfos.FW_GHYT);
				$("input[name='JGSJ']").val(bdcdyxxInfos.JGSJ);
				$("input[name='YTSM']").val(bdcdyxxInfos.GHYTSM);
				$("input[name='FWXZ']").val(bdcdyxxInfos.FWXZ);
				$("input[name='FWJG']").val(bdcdyxxInfos.FWJG);
				$("input[name='JYJG']").val(bdcdyxxInfos.JYJG);
				$("input[name='DYTDMJ']").val(bdcdyxxInfos.DYTDMJ);
				$("input[name='FTTDMJ']").val(bdcdyxxInfos.FTTDMJ);
				$("input[name='JZMJ']").val(bdcdyxxInfos.JZMJ);
				$("input[name='FWGYQK']").val(bdcdyxxInfos.GYFS);
				$("input[name='ZYJZMJ']").val(bdcdyxxInfos.ZYJZMJ);
		
				$("input[name='FTJZMJ']").val(bdcdyxxInfos.FTJZMJ);
				$("input[name='QLLX']").val(bdcdyxxInfos.FW_QLLX);
				//土地使用权人
				$("input[name='TDSYQR']").val(bdcdyxxInfos.QLRMC);
				$("input[name='QZH']").val(bdcdyxxInfos.BDCQZH);
				
				if(bdcdyxxInfos.BDCLX == "土地"){
					$("input[name='LOCATED']").val(bdcdyxxInfos.TDZL);//土地坐落
				}else{
					$("input[name='LOCATED']").val(bdcdyxxInfos.FDZL);//房屋坐落
				}	
			}else{
				$("input[name='APPLICANT_UNIT']").val(bdcdyxxInfos.QLRMC+"等");
				//回填不动产权证号
				$("input[name='BDCQZH']").val(bdcdyxxInfos.BDCQZH);
				$("input[name='CZ_ADDR']").val(bdcdyxxInfos.FDZL);//房屋坐落
				
				
				//出租人名称：权利人名称
				var qlrmc=bdcdyxxInfos.QLRMC;
				var czrnamelist=qlrmc.split(",");
                var datas=[];
				
				for(var i =0;i<czrnamelist.length;i++){
					datas.push({label:czrnamelist[i],value:czrnamelist[i]});
                    }
				
			    $("#CZR_NAME").combobox("loadData",datas);

				//出租人证件类型
				$("#CZR_CARD_TYPE").val(bdcdyxxInfos.ZJLX);
				//出租人证件号码
				
				var czrzjhm=bdcdyxxInfos.ZJHM;

				var czrzjhmlist=czrzjhm.split(",");
                var zjhmdatas=[];
				for(var i =0;i<czrzjhmlist.length;i++){
					zjhmdatas.push({label:czrzjhmlist[i],value:czrzjhmlist[i]});
                    }
			    $("#CZR_CARD_NO").combobox("loadData",zjhmdatas);

				//权利比例
				$("input[name='QLBL']").val(bdcdyxxInfos.QLBL);
				//出租人性质：权利人性质
				$("input[name='CZRXZ']").val(bdcdyxxInfos.QLRXZ);
				//出租人地址
				$("input[name='ADDRESS']").val(bdcdyxxInfos.FDZL);
				//回填房屋信息
				$("input[name='FW_ZL']").val(bdcdyxxInfos.FDZL);
				$("input[name='ZH']").val(bdcdyxxInfos.ZH);
				$("input[name='FW_SZC']").val(bdcdyxxInfos.SZC);
				$("input[name='FW_HH']").val(bdcdyxxInfos.HH);
				$("input[name='FW_ZCS']").val(bdcdyxxInfos.ZCS);
				$("input[name='FW_GHYT']").val(bdcdyxxInfos.FW_GHYT);
				$("input[name='JGSJ']").val(bdcdyxxInfos.JGSJ);
				$("input[name='YTSM']").val(bdcdyxxInfos.GHYTSM);
				$("input[name='FWXZ']").val(bdcdyxxInfos.FWXZ);
				$("input[name='FWJG']").val(bdcdyxxInfos.FWJG);
				$("input[name='JYJG']").val(bdcdyxxInfos.JYJG);
				$("input[name='DYTDMJ']").val(bdcdyxxInfos.DYTDMJ);
				$("input[name='FTTDMJ']").val(bdcdyxxInfos.FTTDMJ);
				$("input[name='JZMJ']").val(bdcdyxxInfos.JZMJ);
				$("input[name='FWGYQK']").val(bdcdyxxInfos.GYFS);
				$("input[name='ZYJZMJ']").val(bdcdyxxInfos.ZYJZMJ);
		
				$("input[name='FTJZMJ']").val(bdcdyxxInfos.FTJZMJ);
				$("input[name='QLLX']").val(bdcdyxxInfos.FW_QLLX);
				//土地使用权人
				$("input[name='TDSYQR']").val(bdcdyxxInfos.QLRMC);
				$("input[name='QZH']").val(bdcdyxxInfos.BDCQZH);
				
				if(bdcdyxxInfos.BDCLX == "土地"){
					$("input[name='LOCATED']").val(bdcdyxxInfos.TDZL+"等");//土地坐落
				}else{
					$("input[name='LOCATED']").val(bdcdyxxInfos.FDZL+"等");//房屋坐落
				}	
			}
			
			return false;
		}	
	});
}

 function loadAutoCompleteDatas() {
	var layload = layer.load('正在提交请求中…');
	$.post("bdcDyqscdjController.do?loadDicSearch", {
	typeCode:"CYYHHMC"
	}, function(responseText, status, xhr) {
		layer.close(layload);
		var resultJson = $.parseJSON(responseText);
		$("#AutoCompleteInput").autocomplete(
			resultJson,
		{
			matchContains : true,
			formatItem : function(row, i, max) {
				//下拉框显示
				return "<div>" + row.DIC_NAME+"</div>";
			},
			formatMatch : function(row) {
				//查询条件
				return row.DIC_NAME+row.PINYINSZM+row.PINYIN;
			},
			formatResult : function(row, i, max) {
				//显示在文本框中
				return row.DIC_NAME;
			}
		});
	});
}

		
function newDicInfoWin(typeCode,combId){
	$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
		title : "新增",
        width:"600px",
        lock: true,
        resize:false,
        height:"300px",
        close: function(){
			$("#"+combId).combobox("reload");
		}
	}, false);
}

function removeDicInfo(combId){
	var datas = $("#"+combId).combobox("getData");
	var val = $("#"+combId).combobox("getValue");
	var id = "";
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME==val){
			id = datas[i].DIC_ID;
			break;
		}
	}
	art.dialog.confirm("您确定要删除该记录吗?", function() {
		var layload = layer.load('正在提交请求中…');
		$.post("dictionaryController.do?multiDel",{
			   selectColNames:id
		    }, function(responseText, status, xhr) {
		    	layer.close(layload);
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					$("#"+combId).combobox("reload");
					$("#"+combId).combobox("setValue","");
				}else{
					$("#"+combId).combobox("reload");
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
		});
	});
}



/**
 * 删除
 */
function delDymxTr(o){
	$(o).closest("tr").remove();	
	setTotlaJzmj();
	setTotlaZdmj();
	setTotlaDytdmj();
	setTotlaFttdmj();
	setSqlandZl();////回填申请人以及坐落
	loadBdcdaxxcxToId();
	if($(".bdcdydacxTr").length<1){
		//抵押不动产类型默认为'土地和房屋'			
		$("select[name='DYBDCLX']").val("2");
	}
} 

function loadBdcdaxxcx(o){
	$("#dymxInfo_1 input[type='text']").val('');
	$("#dymxInfo_1 select").val('');
	var bdcdyxx = $(o).closest("tr").find("[name='bdcdyxx']").val();
	initFormFieldValue(JSON.parse(bdcdyxx),"dymxInfo_1");
	//抵押不动产类型为土地，则坐落取土地坐落，为其他则使用房地坐落
	var bdcdyxxInfos = JSON.parse(bdcdyxx);
	if(bdcdyxxInfos.BDCLX == "土地"){
		$('input[name="FDZL"]').val(bdcdyxxInfos.TDZL);
	}else{
		$('input[name="FDZL"]').val(bdcdyxxInfos.FDZL);
	}
	//抵押不动产类型值回填
	   $('select[name="DYBDCLX"]').find("option").each(function() {
	        if ($(this).text() == bdcdyxxInfos.BDCLX) {
	            $(this).prop("selected", true);
	        }
	   });
	var trId = $(o).closest("tr").attr("id");
	$("#dymxInfo_1 input[name='trid']").val(trId);
	$(".bdcdydacxTr").removeClass("bdcdydacxTrRed");
	$(o).closest("tr").addClass("bdcdydacxTrRed");
}
function loadBdcdaxxcxToId(){
	$("#dymxInfo_1 input[type='text']").val('');
	$("#dymxInfo_1 select").val('');
	if($(".bdcdydacxTr").length>=1){		
		var bdcdyxx = $("#dymxTable").find("tr").eq(1).find("[name='bdcdyxx']").val();
		initFormFieldValue(JSON.parse(bdcdyxx),"dymxInfo_1");
		//抵押不动产类型为土地，则坐落取土地坐落，为其他则使用房地坐落
		var bdcdyxxInfos = JSON.parse(bdcdyxx);
		if(bdcdyxxInfos.BDCLX == "土地"){
			$('input[name="FDZL"]').val(bdcdyxxInfos.TDZL);
		}else{
			$('input[name="FDZL"]').val(bdcdyxxInfos.FDZL);
		}
		//抵押不动产类型值回填
		   $('select[name="DYBDCLX"]').find("option").each(function() {
		        if ($(this).text() == bdcdyxxInfos.BDCLX) {
		            $(this).prop("selected", true);
		        }
		   });
		var trId = $("#dymxTable").find("tr").eq(1).attr("id");
		$("#dymxInfo_1 input[name='trid']").val(trId);
		$(".bdcdydacxTr").removeClass("bdcdydacxTrRed");
		$("#dymxTable").find("tr").eq(1).addClass("bdcdydacxTrRed");
	}
}
function updateDymxInfo(){
	var trid = $("#dymxInfo_1 input[name='trid']").val();	
	var bdcdyxx = $("#"+trid+" input[name='bdcdyxx']").val();
	if(""!=bdcdyxx && null!= bdcdyxx && undefined!=bdcdyxx){		
		var bdcdyxxInfos = JSON.parse(bdcdyxx);
		$("#dymxInfo").find('table').eq(0).find("*[name]").each(function(){
			  var inputName= $(this).attr("name");
			  var inputValue = $(this).val();
			  //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
				  var isChecked = $(this).is(':checked');
				  if(isChecked){
					  bdcdyxxInfos[inputName] = inputValue;
				  }
			  }else if(fieldType=="checkbox"){
				  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  bdcdyxxInfos[inputName] = inputValues;
			  }else if(fieldType!="button"){
				  bdcdyxxInfos[inputName] = inputValue;
			  }          
		});
		$("#"+trid+" input[name='bdcdyxx']").val(JSON.stringify(bdcdyxxInfos));
		art.dialog({
			content: "保存成功",
			icon:"succeed",
			time:3,
			ok: true
		});
		setTotlaJzmj();
		setTotlaZdmj();
		setTotlaDytdmj();
		setTotlaFttdmj();
	} else {
		art.dialog({
			content: "请先选择抵押明细信息！",
			icon:"warning",
			ok: true
		});
	}
}

/**
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementId
 */
function initFormFieldValue(fieldValueObj,elementId){
	for(var fieldName in fieldValueObj){
		//获取目标控件对象
		var targetFields = $("#"+elementId).find("[name='"+fieldName+"']");
		targetFields.each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = fieldValueObj[fieldName];
			if(type=="radio"){
				var radioValue = targetField.val();
				if(radioValue==fieldValue){
					$(this).attr("checked","checked");
				}
			}else if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}else{
				targetField.val(fieldValueObj[fieldName]);
			}
	   });
	}
}
