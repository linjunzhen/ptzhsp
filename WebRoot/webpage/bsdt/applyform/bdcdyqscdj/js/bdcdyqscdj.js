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
	$("#dymxInfo").find("input,select").attr("disabled","disabled");
	$("#qslyInfo").find("input,select").attr("disabled","disabled");
	//去除权属来源中按钮的不可编辑性
	$("#qslyInfo").find("input[type='button']").attr("disabled",false);
	$("#qslyInfo").find("input[type='button']").attr("readonly",false);
	//去除抵押明细中的抵押顺位、按钮的不可编辑性
	$("#dymxInfo").find("input[name='DYSW']").attr("disabled",false);
	$("#dymxInfo").find("input[type='button']").attr("disabled",false);

	
});
function setRuleContent(){
       var selectValue = $('#_select option:selected').text();//选中select的内容
       //alert("selectValue" + selectValue);

var inputValue = $("#_input").val(selectValue);//input获得select的内容并显示在输入框中
//alert(inputValue);
};
function FLOW_SubmitFun(flowSubmitObj){	
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_DYQSCDJ_FORM").validationEngine("validate");
	 if(validateResult){
		 var flag = checkLimitPerson();
		 if(!flag){
			 return;
		 }
		getDymxInfoJson();
		if($(".bdcdydacxTr").length<1){
			parent.art.dialog({
				content: "最少一个抵押明细信息！",
				icon:"warning",
				ok: true
			});
			return;
		}
		//判断抵押明细中不动产权利人与证件号是否与抵押权登记中的抵押人与证件号一致
		var dyrmc=$("input[name='DYQDJ_NAME']").val();
		var idno=$("input[name='DYQDJ_IDNO']").val();
		var flag = true;
		$("#dymxTable").find(".bdcdydacxTr").find("input[name='bdcdyxx']").each(function(){
			var obj = JSON.parse(this.value);
			var qlrmc = obj.QLRMC.split(',');
			var zjhm = obj.ZJHM.split(',');
			for(var i=0;i<zjhm.length;i++){
				if(dyrmc.indexOf(qlrmc[i])!='-1'&&idno.indexOf(zjhm[i])!='-1'){
					continue;
				}else{
					flag = false;
					break;
				}
			}
		});
		if(!flag){
			alert("请注意，抵押明细中，不动产权利人与抵押登记中的信息不一致！");
		}
		var type = $("input[name='TAKECARD_TYPE']:checked").val();
		if(type==2){
			getQslyInfoJson();
		} else{
			$("input[name='QSLY_JSON']").val('');
		}

         var isAuditPass = $('input[name="isAuditPass"]:checked').val();
	     if(isAuditPass == "-1"){
	     	 parent.art.dialog({
				content : "检查上传的审批表扫描件不合规，请先退回补件。",
				icon : "warning",
				ok : true
			 });
			 return null;
	     }else{
	     	 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BDC_DYQSCDJ_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 var c = $("select[name='DYQRXX_NATURE']").val();
				 if(c=='1'){
					flowSubmitObj['DYQRXX_NAME'] = $("#DYQRXX_NAME2").val();
				 }
				 //console.dir(flowSubmitObj);
				return flowSubmitObj;
			 }else{
				 return null;
			 }
	     }
	 }else{
		 return null;
	 }
}

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
//		flowSubmitObj.CyjbJson = getCyjbJson();
	getDymxInfoJson();
	var type = $("input[name='TAKECARD_TYPE']:checked").val();
	if(type==2){
		getQslyInfoJson();
	} else{
		$("input[name='QSLY_JSON']").val('');
	}
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_DYQSCDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}
	var c = $("select[name='DYQRXX_NATURE']").val();
	if(c=='1'){
		flowSubmitObj['DYQRXX_NAME'] = $("#DYQRXX_NAME2").val();
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
// 	alert(idCard + "," + enterprise);
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
					html +='<td style="text-align: center;">'+bdcdydacxTr+'</td>';
					html +='<td style="text-align: center;">'+bdcdaxxcxInfo[i].ZH+'</td>';
					html +='<td style="text-align: center;">'+bdcdaxxcxInfo[i].HH+'</td>';
					html +='<td style="text-align: center;">'+bdcdaxxcxInfo[i].BDCQZH+'</td>';
					html +='<td style="text-align: center;">'+bdcdaxxcxInfo[i].BDCDYH+'</td>';
					html +='<td style="text-align: center;">'+bdcdaxxcxInfo[i].QSZT+'</td>';
					html +='<td style="text-align: center;">';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);" id="dymxAdd">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delDymxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html +='</tr>';
					$("#dymxTable").append(html);
					//抵押不动产类型回填						
					$('select[name="DYBDCLX"]').find("option").each(function() {
						if ($(this).text() == bdcdaxxcxInfo[i].BDCLX) {
							bdcdaxxcxInfo[i].DYBDCLX=$(this).val();	
						}
				   });					
					$("#bdcdydacxTr_"+bdcdydacxTr+" input[name='bdcdyxx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
					bdcdydacxTr ++;
					html = "";
				}	
				setTotlaJzmj();
				setTotlaZdmj();
				setTotlaDytdmj();
				setTotlaFttdmj();
				setSqlandZl();////回填申请人以及坐落
				art.dialog.removeData("bdcdaxxcxInfo");
				loadBdcdaxxcxToId();
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
				if(bdcdyxxInfos.BDCLX == "土地"){
					$("input[name='LOCATED']").val(bdcdyxxInfos.TDZL);//土地坐落
				}else{
					$("input[name='LOCATED']").val(bdcdyxxInfos.FDZL);//房屋坐落
				}	
			}else{
				$("input[name='APPLICANT_UNIT']").val(bdcdyxxInfos.QLRMC+"等");
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