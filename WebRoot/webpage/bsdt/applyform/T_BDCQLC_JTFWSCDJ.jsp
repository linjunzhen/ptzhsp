<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<!-- <script type="text/javascript" -->
<!-- <%--	src="<%=basePath%>/webpage/bsdt/applyform/estate/js/commercial.js"></script> --%> -->

<script type="text/javascript">

$(function(){
	
	 //默认企业法人
	 //qyclick();
	 //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
	 $("input[name='JBR_NAME']").removeAttr('readonly');
	 $("input[name='JBR_ZJHM']").removeAttr('readonly');
	 
	 //设置权利类型默认为'宅基地使用权/房屋所有权'
	 $('#HF_QLLX').prop("disabled", "disabled");
	 $('#HF_QLLX').val('6');

	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
	dealItems = "," + dealItems + ",";
	if(flowSubmitObj){
		//获取表单字段权限控制信息
		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		var exeid = flowSubmitObj.EFLOW_EXEID;
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		 //初始化表单字段权限控制
		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_ZJFWSYSCDJ_FORM");
		//初始化表单字段值
		if(flowSubmitObj.busRecord){
			initAutoTable(flowSubmitObj);//初始化权利信息
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_ZJFWSYSCDJ_FORM");
			if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
				$("#T_BDC_ZJFWSYSCDJ_FORM input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#T_BDC_ZJFWSYSCDJ_FORM select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				if($("input[name='SBMC']").val()){
				}else{
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
				}
			}else if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
				$("#userinfo_div input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#userinfo_div select").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#baseinfo input").each(function(index){
					$(this).attr("disabled","disabled");
				});
			}
			var isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
			//设置文件是否合规
			if(isAuditPass == "-1"){
				$("input:radio[name='isAuditPass'][value='-1']").attr("checked",true); 
			}
		}else{
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
		}

		var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
			$("#ptcyjb").attr("style","");
			if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
				$("input[name='JBR_NAME']").removeAttr('readonly');
			}
		}
	}
	
	//初始化材料列表
	//AppUtil.initNetUploadMaters({
	//	busTableName:"T_BDC_ZJFWSYSCDJ"
	//});
	
});


function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_ZJFWSYSCDJ_FORM").validationEngine("validate");
	 if(validateResult){
		 getPowerInfoJson();
         getPowerPeopleInfoJson();
         getPowerSourceInfoJson();
         if(!isPowerPeople()){
             parent.art.dialog({
                 content : "申请人的名字必须出现在权利人信息中",
                 icon : "warning",
                 ok : true
             });
             return;
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
				 var formData = FlowUtil.getFormEleData("T_BDC_ZJFWSYSCDJ_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
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
	getPowerInfoJson();
	getPowerPeopleInfoJson();
	getPowerSourceInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_ZJFWSYSCDJ_FORM");
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
// 	alert(idCard + "," + enterprise);
	var fileNum;
	if (enterprise != ""){
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
	} else {
		fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
	}
	$("#fileNumber").val(fileNum);
}


function initAutoTable(flowSubmitObj){
	var powerinfoJson = flowSubmitObj.busRecord.POWERINFO_JSON;
	if(null != powerinfoJson && '' != powerinfoJson){
		var powerinfos = eval(powerinfoJson);
		for(var i=0;i<powerinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_1");
			}else{
				addPowerInfo();
				FlowUtil.initFormFieldValue(powerinfos[i],"powerInfo_"+(i+1));
			}
		}
	}
	var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
	if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson){
		var powerpeopleinfos = eval(powerpeopleinfoJson);
		for(var i=0;i<powerpeopleinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_1");
			}else{
				addPowerPeopleInfo();
				FlowUtil.initFormFieldValue(powerpeopleinfos[i],"powerPeopleInfo_"+(i+1));
			}
		}
	}
	var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if(null != powersourceinfoJson && '' != powersourceinfoJson){
		var powersourceinfos = eval(powersourceinfoJson);
		for(var i=0;i<powersourceinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_1");
			}else{
				addPowerSourceInfo();
				FlowUtil.initFormFieldValue(powersourceinfos[i],"powerSourceInfo_"+(i+1));
			}
		}
	}
}

/**=================权利信息开始================================*/
    function changePower(val){
        /*  if(val=="2"){
             $("input[name='POWERPEOPLEPRO']").removeAttr("disabled");
		 }else{
             $("input[name='POWERPEOPLEPRO']").attr("disabled","disabled");
             $("input[name='POWERPEOPLEPRO']").val("");
		 } */
		 if(val=="0"){
             $("input[name='POWERPEOPLEPRO']").val("100");
		 }else{
              $("input[name='POWERPEOPLEPRO']").val("");
		 }
    }
var powerInfoSn = 1;
function addPowerInfo(){
	powerInfoSn = powerInfoSn+1;
	var table = document.getElementById("powerInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerInfo('");
	if(powerInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+21);
	}else{
		var replacestr = trHtml.substring(findex,findex+20);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerInfo('"+powerInfoSn+"')");
	trHtml = "<tr id=\"powerInfo_"+powerInfoSn+"\">"+trHtml+"</tr>";
	$("#powerInfo").append(trHtml);
}

function deletePowerInfo(idSn){
	var table = document.getElementById("powerInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerInfo_"+idSn).remove();
}

function getPowerInfoJson(){
	var datas = [];
	var table = document.getElementById("powerInfo");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#powerInfo_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
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
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权利信息结束================================*/
/**=================权利人信息开始================================*/
var powerPeopleInfoSn = 1;
function addPowerPeopleInfo(){
	powerPeopleInfoSn = powerPeopleInfoSn+1;
	var table = document.getElementById("powerPeopleInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerPeopleInfo('");
	if(powerPeopleInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerPeopleInfo('"+powerPeopleInfoSn+"')");
	trHtml = "<tr id=\"powerPeopleInfo_"+powerPeopleInfoSn+"\">"+trHtml+"</tr>";
	$("#powerPeopleInfo").append(trHtml);
}

function deletePowerPeopleInfo(idSn){
	var table = document.getElementById("powerPeopleInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权利人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerPeopleInfo_"+idSn).remove();
}

function getPowerPeopleInfoJson(){
	var datas = [];
	var table = document.getElementById("powerPeopleInfo");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#powerPeopleInfo_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
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
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权利人信息开始================================*/
/**=================权属来源信息开始================================*/
var powerSourceInfoSn = 1;
function addPowerSourceInfo(){
	powerSourceInfoSn = powerSourceInfoSn+1;
	var table = document.getElementById("powerSourceInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deletePowerSourceInfo('");
	if(powerSourceInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deletePowerSourceInfo('"+powerSourceInfoSn+"')");
	trHtml = "<tr id=\"powerSourceInfo_"+powerSourceInfoSn+"\">"+trHtml+"</tr>";
	$("#powerSourceInfo").append(trHtml);
}

function deletePowerSourceInfo(idSn){
	var table = document.getElementById("powerSourceInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "最少一个权属来源信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#powerSourceInfo_"+idSn).remove();
}

function getPowerSourceInfoJson(){
	var datas = [];
	var table = document.getElementById("powerSourceInfo");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#powerSourceInfo_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
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
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/


//------------------------------------身份身份证读取开始---------------------------------------------------
		function MyGetData()//OCX读卡成功后的回调函数
		{	
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>		
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
		}

		function MyClearData()//OCX读卡失败后的回调函数
		{
	        alert("未能有效识别身份证，请重新读卡！");
			$("input[name='POWERPEOPLENAME']").val("");   
			$("input[name='POWERPEOPLEIDNUM']").val("");  
		}

		function MyGetErrMsg()//OCX读卡消息回调函数
		{
// 			Status.value = GT2ICROCX.ErrMsg;	   
		}

		function PowerPeopleRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
				$("#"+powerPeopleInfoID+" [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo)
			}
		} 
		function PowLegalRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWLEGALNAME']").val(GT2ICROCX.Name)
				$("#"+powerPeopleInfoID+" [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo)
			}
		} 
		function PowAgentRead(o)//开始读卡
		{  		
// 			alert($(o).parent().parent().parent().parent().parent().parent().attr('id'));
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //循环读卡
			//单次读卡(点击一次读一次)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#"+powerPeopleInfoID+" [name='POWAGENTNAME']").val(GT2ICROCX.Name)
				$("#"+powerPeopleInfoID+" [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo)
			}
		} 

		function print()//打印
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
		} 
//------------------------------------身份身份证读取结束---------------------------------------------------


</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData()
</SCRIPT>
    <style>
		.sel{
			border:solid 1px red;
		}
    </style>
</head>

<body>
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDC_ZJFWSYSCDJ_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId"
			value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
			name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
			type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
			type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME"
			value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
			value="${serviceItem.SXLX}" /> <input type="hidden"
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
			
			
		<input type="hidden" name="POWERINFO_JSON" />
		<input type="hidden" name="POWERPEOPLEINFO_JSON" />
		<input type="hidden" name="POWERSOURCEINFO_JSON" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td > 所属部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		<%--开始引入受理信息--%>
		<jsp:include page="./estate/T_ESTATE_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始引入权利信息--%>
		<jsp:include page="./bdcqlc/jtjsyddj/rightInfo.jsp" />
		<%--结束引入权利信息--%>
		
		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/jtjsyddj/obligeeInfo.jsp" />
		<%--结束引入权利人信息--%>

	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>