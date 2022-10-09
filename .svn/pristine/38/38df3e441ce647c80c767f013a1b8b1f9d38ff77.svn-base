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
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>   
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/gyjsydjfwsyq/js/gyjsydjfwsyqscdj.js"></script>
<script type="text/javascript">
	$(function() {
		initBeforForm();
		initEasyUiForm();
		delChildItem();
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
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_GYJSYDJFWSYQ_FORM");
			//初始化表单字段值
			if (flowSubmitObj.busRecord) {
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_GYJSYDJFWSYQ_FORM");
				//动态切换抵押信息
				queryTypeFn();
				initAutoTable(flowSubmitObj);//初始化权利信息
				/*登簿后按钮无法点击*/
				disabledDbBtn("BDC_DBBTN");
				/*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
				fillInEasyUiForm(flowSubmitObj)

				if (flowSubmitObj.busRecord.RUN_STATUS) {
					if (flowSubmitObj.busRecord.RUN_STATUS != 0) {

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
							isDisabledForm("T_BDCQLC_GYJSYDJFWSYQ_FORM", true);
						} else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '网上预审') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '待受理') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
							isDisabledForm("zdjbxx", false);
						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
							$("#bdcdcz_btn").show();
							$(".bdcdbc_tr").show();
							$("#bdcqzh_tr").attr("style", "");
							$("#bdcczr_tr").attr("style", "");
							$("#bdcqzbsm_tr").show();
							$("#BDC_QZVIEW").show();
						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {

						}

						var isEndFlow = false;
						if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
							isEndFlow = true;
						}
						if (isEndFlow) {
							//当流程结束
							$("#bdcqzh_tr").attr("style","");
							$("#bdcdbc_tr").attr("style","");
							$("#bdcqzbsm_tr").attr("style","");
							$("#bdcczr_tr").attr("style","");
							$("#qzbsmsavebtn").remove();
							$("#BDC_QZVIEW").attr("style","");
							$("#BDC_QZVIEW").removeAttr("disabled");
							//登记缴费和发证信息
							$("#djshxx").attr("style","");
							$("#djjfxx").attr("style","");
							$("#djfzxx").attr("style","");
							$("#djgdxx").attr("style","");
						}

						initDjshForm();
					}
				}
			} else {
				queryTypeFn();
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
			}

			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
				$("#ptcyjb").attr("style", "");
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
		}
	});

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
	<form id="T_BDCQLC_GYJSYDJFWSYQ_FORM" method="post">
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
		
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="POWERINFO_JSON" />
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		
		<%--登记发证信息明细--%>
		<input type="hidden" name="DJFZXX_JSON" id="DJFZXX_JSON"/>
		<%--登记缴费信息明细--%>
		<input type="hidden" name="DJJFMX_JSON" id="DJJFMX_JSON"/>
		<%--建筑物区分信息明细--%>
		<input type="hidden" name="JZWQF_JSON" id="JZWQF_JSON"/>
		
		<%--===================重要的隐藏域内容=========== --%>
		
		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcSlxx.jsp" />
		<%--结束引入受理信息--%>
		
		<%--开始引入领证人信息--%>
		<jsp:include page="./bdcqlc/lzrInfo.jsp" />
		<%--结束引入领证人信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入权利信息--%>
		<%-- <jsp:include page="./bdcqlc/bdcQlrxx.jsp" /> --%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcQlrxx.jsp" />
		<%--结束引入权利信息--%>

		<%--开始引入权属来源信息--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcQsly.jsp" />
		<%--开始引入权属来源信息--%>
		
		<%--开始引入总确权信息--%>
 		<jsp:include page="./bdcqlc/bdcZqqxx.jsp" />
		<%--结束引入总确权信息--%>

		<%--开始引入套间明细信息--%>
 	    <%-- <jsp:include page="./bdcqlc/bdcTjmx.jsp" />  --%>
 	    <jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcTjmx.jsp" />
 	    
 		<%--结束引入套间明细信息--%>

		<%--开始引入建筑物区分信息--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcJzwqf.jsp" />
		<%--结束引入建筑物区分信息--%>
		
		<%--开始引入宗地信息-国有权力人信息--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--开始引入宗地信息-国有权力人信息--%>

		<%--开始引入房屋基本信息--%>
		<jsp:include page="./bdcqlc/bdcFwjbxx.jsp" />
		<%--开始引入房屋基本信息--%>

		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
		<!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
		<!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
		<jsp:include page="./bdcqlc/common/djauditinfo.jsp">
			<jsp:param value="scdj" name="domId" />
			<jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
		</jsp:include>
		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>

		<%--不动产EMS模块--%>
		<jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>

		<jsp:include page="./bdcqlc/bdcRemark.jsp" />
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
