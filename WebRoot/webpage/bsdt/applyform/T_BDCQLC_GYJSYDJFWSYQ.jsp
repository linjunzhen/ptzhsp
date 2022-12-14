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
		//??????????????????????????????
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //???DB????????????????????????????????????,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if (flowSubmitObj) {
			//????????????????????????????????????
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//?????????????????????????????????
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_GYJSYDJFWSYQ_FORM");
			//????????????????????????
			if (flowSubmitObj.busRecord) {
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_GYJSYDJFWSYQ_FORM");
				//????????????????????????
				queryTypeFn();
				initAutoTable(flowSubmitObj);//?????????????????????
				/*???????????????????????????*/
				disabledDbBtn("BDC_DBBTN");
				/*??????????????????????????????????????????????????????????????????????????????????????????*/
				fillInEasyUiForm(flowSubmitObj)

				if (flowSubmitObj.busRecord.RUN_STATUS) {
					if (flowSubmitObj.busRecord.RUN_STATUS != 0) {

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '??????') {
							isDisabledForm("T_BDCQLC_GYJSYDJFWSYQ_FORM", true);
						} else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '????????????') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '?????????') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
							isDisabledForm("zdjbxx", false);
						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {

						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
							$("#bdcdcz_btn").show();
							$(".bdcdbc_tr").show();
							$("#bdcqzh_tr").attr("style", "");
							$("#bdcczr_tr").attr("style", "");
							$("#bdcqzbsm_tr").show();
							$("#BDC_QZVIEW").show();
						}

						if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {

						}

						var isEndFlow = false;
						if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
							isEndFlow = true;
						}
						if (isEndFlow) {
							//???????????????
							$("#bdcqzh_tr").attr("style","");
							$("#bdcdbc_tr").attr("style","");
							$("#bdcqzbsm_tr").attr("style","");
							$("#bdcczr_tr").attr("style","");
							$("#qzbsmsavebtn").remove();
							$("#BDC_QZVIEW").attr("style","");
							$("#BDC_QZVIEW").removeAttr("disabled");
							//???????????????????????????
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
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "??????") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
		}
	});

</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//??????????????????
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//??????????????????
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//??????????????????
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
		<%--===================????????????????????????=========== --%>
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
		
		<%--===================????????????????????????=========== --%>
		<input type="hidden" name="POWERINFO_JSON" />
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
		<!-- ???????????????????????????????????????????????????????????????????????????????????? -->
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		
		<%--????????????????????????--%>
		<input type="hidden" name="DJFZXX_JSON" id="DJFZXX_JSON"/>
		<%--????????????????????????--%>
		<input type="hidden" name="DJJFMX_JSON" id="DJJFMX_JSON"/>
		<%--???????????????????????????--%>
		<input type="hidden" name="JZWQF_JSON" id="JZWQF_JSON"/>
		
		<%--===================????????????????????????=========== --%>
		
		<%--?????????????????????????????????--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--????????????????????????????????? --%>

		<%--??????????????????????????????--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--?????????????????????????????? --%>

		<%--????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcSlxx.jsp" />
		<%--????????????????????????--%>
		
		<%--???????????????????????????--%>
		<jsp:include page="./bdcqlc/lzrInfo.jsp" />
		<%--???????????????????????????--%>

		<%--???????????????????????????????????? --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--???????????????????????????????????? --%>

		<%--????????????????????????--%>
		<%-- <jsp:include page="./bdcqlc/bdcQlrxx.jsp" /> --%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcQlrxx.jsp" />
		<%--????????????????????????--%>

		<%--??????????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcQsly.jsp" />
		<%--??????????????????????????????--%>
		
		<%--???????????????????????????--%>
 		<jsp:include page="./bdcqlc/bdcZqqxx.jsp" />
		<%--???????????????????????????--%>

		<%--??????????????????????????????--%>
 	    <%-- <jsp:include page="./bdcqlc/bdcTjmx.jsp" />  --%>
 	    <jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcTjmx.jsp" />
 	    
 		<%--??????????????????????????????--%>

		<%--?????????????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydjfwsyq/bdcJzwqf.jsp" />
		<%--?????????????????????????????????--%>
		
		<%--????????????????????????-?????????????????????--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--????????????????????????-?????????????????????--%>

		<%--??????????????????????????????--%>
		<jsp:include page="./bdcqlc/bdcFwjbxx.jsp" />
		<%--??????????????????????????????--%>

		<%-- ????????????????????????????????????????????????????????? --%>
		<!-- djshxx:??????????????????,djjfxx:??????????????????,djfzxx:??????????????????,djdaxx:?????????????????? -->
		<!-- optype:??????0??????????????????1????????????????????????????????? -->
		<jsp:include page="./bdcqlc/common/djauditinfo.jsp">
			<jsp:param value="scdj" name="domId" />
			<jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
		</jsp:include>
		<%-- ????????????????????????????????????????????????????????? --%>

		<%--?????????EMS??????--%>
		<jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>

		<jsp:include page="./bdcqlc/bdcRemark.jsp" />
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
