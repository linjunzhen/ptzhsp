<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
			request.setAttribute("nowDate", nowDate);
			String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			request.setAttribute("nowTime", nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<base href="<%=basePath%>">
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />

<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcDjxx.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcQlrxx.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcQslyNew.js"></script>
<script type="text/javascript">
	$(function() {

		/*审批表在开始环节无法打印*/
		$("#SPBDF").attr("onclick","notPrint();")
		$("#SPBSF").attr("onclick","notPrint();")
		//设置权利类型默认为'宅基地与房屋使用权'
		$('#HF_QLLX').prop("disabled", "disabled");
		$('#HF_QLLX').val('6');
		//非缴费发证页隐藏缴费发证信息
		$("#djjfxx").attr("style","display:none;");
		$("#djfzxx").attr("style","display:none;");
		$("#djgdxx").attr("style","display:none;");
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if (flowSubmitObj) {
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_ZJFWSYSCDJ_FORM");
			//初始化表单字段值
			if (flowSubmitObj.busRecord) {
				initDjxxTable(flowSubmitObj); //初始化登记信息
				initPowerPeopleTable(flowSubmitObj);
				initPowerSourceTable(flowSubmitObj);
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_ZJFWSYSCDJ_FORM");
				if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
					/*其它环节审批表可以进行打印*/
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '受理'){
						$("#printBtn").show();
					}
					$("#SPBDF").attr("onclick","goPrintTemplate('ZJDSYQSCDJ','3');");
					$("#SPBSF").attr("onclick","goPrintTemplate('ZJDSYQSCDJ','3');");
					/*$("#T_BDCQLC_ZJFWSYSCDJ_FORM input").each(function(index) {
					    $(this).attr("disabled", "disabled");
					});
					$("#T_BDCQLC_ZJFWSYSCDJ_FORM select").each(function(index) {
					    $(this).attr("disabled", "disabled");
					});*/
					if ($("input[name='SBMC']").val()) {
					} else {
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					}
				} else if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
					$("#userinfo_div input").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
					$("#userinfo_div select").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
					$("#baseinfo input").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
				}
			} else {
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
			}
			


			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
				$("#ptcyjb").attr("style", "");
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
		}
	});

	function FLOW_SubmitFun(flowSubmitObj) {
		//先判断表单是否验证通过
		var validateResult = $("#T_BDCQLC_ZJFWSYSCDJ_FORM").validationEngine("validate");
		if (validateResult) {
			getPowerPeopleInfoJson();
			getPowerSourceInfoJson();
			getDjfzxxJson();
			getDjjfxxJson();
			if (!isPowerPeople()) {
				parent.art.dialog({
					content : "申请人的名字必须出现在权利人信息中",
					icon : "warning",
					ok : true
				});
				return;
			}
			var isAuditPass = $('input[name="isAuditPass"]:checked').val();
			if (isAuditPass == "-1") {
				parent.art.dialog({
					content : "检查上传的审批表扫描件不合规，请先退回补件。",
					icon : "warning",
					ok : true
				});
				return null;
			} else {
				setGrBsjbr(); //个人不显示经办人设置经办人的值
				var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
				if (submitMaterFileJson || submitMaterFileJson == "") {
					$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
					//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("T_BDCQLC_ZJFWSYSCDJ_FORM");
					for (var index in formData) {
						flowSubmitObj[eval("index")] = formData[index];
					}
					return flowSubmitObj;
				} else {
					return null;
				}
			}

			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
				var isdbflag = $("input[name='BDC_DBZT']").val();
				if(isdbflag =="-1"){
					parent.art.dialog({
						content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
						icon : "warning",
						ok : true
					});
					return;
				}else if(isdbflag =="0"){
					var dbjg = $("input[name='BDC_DBJG']").val();
					parent.art.dialog({
						content : "登簿异常！"+dbjg,
						icon : "warning",
						ok : true
					});
					return;
				}
			}
		} else {
			return null;
		}
	}

	function isPowerPeople() {
		var powerPeopleInfoSn = $("input[name='POWERPEOPLEINFO_JSON']").val();
		var sqrmc = $("input[name='SQRMC']").val();
		var flag = false;
		$("input[name='POWERPEOPLENAME']").each(function(j, item) {
			if (item.value != "") {
				if (sqrmc.indexOf(item.value) > -1) {
					flag = true;
					return true;
				}
			}
		});
		$("input[name='POWLEGALNAME']").each(function(j, item) {
			if (item.value != "") {
				if (sqrmc.indexOf(item.value) > -1) {
					flag = true;
					return true;
				}
			}
		});
		$("input[name='POWAGENTNAME']").each(function(j, item) {
			if (item.value != "") {
				if (sqrmc.indexOf(item.value) > -1) {
					flag = true;
					return true;
				}
			}
		});
		if (flag) {
			return true;
		} else {
			if (powerPeopleInfoSn.indexOf(sqrmc) > -1) {
				return true;
			} else {
				return false;
			}
		}
	}

	function FLOW_TempSaveFun(flowSubmitObj) {
		getPowerPeopleInfoJson();
		getPowerSourceInfoJson();
		getDjfzxxJson();
		getDjjfxxJson();
		//先判断表单是否验证通过
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("T_BDCQLC_ZJFWSYSCDJ_FORM");
		for (var index in formData) {
			flowSubmitObj[eval("index")] = formData[index];
		}
		return flowSubmitObj;
	}

	function FLOW_BackFun(flowSubmitObj) {
		return flowSubmitObj;
	}

	function setFileNumber(serialNum) {
		var enterprise = '${execution.SQJG_CODE}';
		var idCard = '${execution.SQRSFZ}';
		var fileNum;
		if (enterprise != "") {
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
		} else {
			fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
		}
		$("#fileNumber").val(fileNum);
	}

	/**=================权利人信息开始================================*/

	function getPowerPeopleInfoJson() {
		var datas = [];
		$("#PowerPeopleTable .bdcdydacxTr").each(function() {
			var iteminfo = $(this).find("input[name='PowerPeopleTableTrMx']").val();
			datas.push(JSON.parse(iteminfo));
		});
		$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
	}

	/**=================权利人信息开始================================*/

	// 确认登簿后赋值登簿人，审核人，登簿时间，审核日期
	function doBooking() {
		$("#POWER_DBR").val('${sessionScope.curLoginUser.fullname}');
		$("#POWER_DBSJ").val('${nowTime}');
		BdcUtil.doBooking();
	}

	// 证明预览
	function provePreview() {
		alert("未开发");
	}

	/**=================权属来源信息开始================================*/
	function getPowerSourceInfoJson() {
		var datas = [];
		var table = document.getElementById("PowerSourceInfo");
		for (var i = 1; i <= table.rows.length; i++) {
			var trData = {};
			$("#PowerSourceInfo_" + i).find("*[name]").each(function() {
				var inputName = $(this).attr("name");
				var inputValue = $(this).val();
				//获取元素的类型
				var fieldType = $(this).attr("type");
				if (fieldType == "radio") {
					var isChecked = $(this).is(':checked');
					if (isChecked) {
						trData[inputName] = inputValue;
					}
				} else if (fieldType == "checkbox") {
					var inputValues = FlowUtil.getCheckBoxValues(inputName);
					trData[inputName] = inputValues;
				} else {
					trData[inputName] = inputValue;
				}
			});
			datas.push(trData);
		}
		$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
	}
	/**=================权属来源信息开始================================*/

	//------------------------------------身份身份证读取开始---------------------------------------------------
	function MyGetData() //OCX读卡成功后的回调函数
	{
		//          POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>     
		//          POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
	}

	function MyClearData() //OCX读卡失败后的回调函数
	{
		alert("未能有效识别身份证，请重新读卡！");
		$("input[name='POWERPEOPLENAME']").val("");
		$("input[name='POWERPEOPLEIDNUM']").val("");
	}

	function MyGetErrMsg() //OCX读卡消息回调函数
	{
		//          Status.value = GT2ICROCX.ErrMsg;       
	}

	function PowerPeopleRead(o) //开始读卡
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo)
		}
	}
	function PowLegalRead(o) //开始读卡
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name)
			$("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo)
		}
	}
	function PowAgentRead(o) //开始读卡
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name)
			$("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo)
		}
	}

	function print() //打印
	{
		GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
	}
	//------------------------------------身份身份证读取结束---------------------------------------------------
</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//设置回调函数
	MyClearData()
</SCRIPT>
<style>
.sel {
	border: solid 1px red;
}
</style>
</head>

<body>
	<input type="hidden" id="sxtsx" name="sxtsx" value="0" />
	<input id="AutoExposure" type="hidden" onclick="autoexposure()" />
	<input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
	<input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
	<input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_ZJFWSYSCDJ_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
		<input type="hidden" name="POWERINFO_JSON" />
		<input type="hidden" name="POWERPEOPLEINFO_JSON" />
		<input type="hidden" name="POWERSOURCEINFO_JSON" />
		<%--登记发证信息明细--%>
		<input type="hidden" name="DJFZXX_JSON" />
		<%--登记缴费信息明细--%>
		<input type="hidden" name="DJJFXX_JSON" />

		<%--===================重要的隐藏域内容=========== --%>
		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/zjfwsyscdj/bdcslxx.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>

		<%--开始引入权属来源信息--%>
		<jsp:include page="./bdcqlc/bdcQslyNew.jsp" />
		<%--开始引入权属来源信息--%>

		<%--开始引入宗地信息-国有权力人信息--%>
		<jsp:include page="./bdcqlc/zjfwsyscdj/bdcZdqlxx.jsp" />
		<%--开始引入宗地信息-国有权力人信息--%>

		<%--开始登簿审核信息--%>
		<%-- <jsp:include page="./bdcqlc/bdcDjshxx.jsp" /> --%>
		<%--开始登簿审核信息--%>
		
		<%--开始审批表打印按钮--%>
		<div id="printBtn" name="printBtn" style="display:none;">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="SPBDF"
							>审批表（单方）</a>
						<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="SPBSF"
							>审批表（双方）</a>
					</th>
				</tr>
		    </table>
	    </div>
	    <%--结束审批表打印按钮--%>
		
		<%--开始登记审核意见信息（不动产通用）--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" /> 
		<%--结束登记审核意见信息（不动产通用）--%>

		<%--开始引入登记信息--%>
		<jsp:include page="./bdcqlc/bdcDjxx.jsp" />
		<%--结束引入登记信息--%>
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
