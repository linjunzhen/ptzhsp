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
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/hfcqdj/js/qlchfcqdj.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript">

$(function(){
	/*经办人姓名证件号码只读*/
	$("input[name='JBR_NAME']").removeAttr('readonly');
	$("input[name='JBR_ZJHM']").removeAttr('readonly');

	/*所有审批表不可打印*/
	$("a[id='spbdf']").each(function (i) {
		$(this).attr("onclick", "notPrint();");
	});
	$("a[id='spbsf']").each(function (i) {
		$(this).attr("onclick", "notPrint();");
	});

	/*隐藏四个业务缴费发证信息*/
	$("div[id^=djshxx_]").hide();
	$("div[id^=djjfxx_]").hide();
	$("div[id^=djfzxx_]").hide();
	$("div[id^=djgdxx_]").hide();

	//初始化验证引擎的配置
	$.validationEngine.defaults.autoHidePrompt = true;
	$.validationEngine.defaults.promptPosition = "topRight";
	$.validationEngine.defaults.autoHideDelay = "2000";
	$.validationEngine.defaults.fadeDuration = "0.5";
	$.validationEngine.defaults.autoPositionUpdate =true;
	var flowSubmitObj = FlowUtil.getFlowObj();
	var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
	dealItems = "," + dealItems + ",";
	console.log(flowSubmitObj)
	if (flowSubmitObj) {
		//获取表单字段权限控制信息
		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
		var exeid = flowSubmitObj.EFLOW_EXEID;
		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
		if (flowSubmitObj.busRecord) {
			/*回填数据*/
			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_HFCQDJ_FORM");
			if (flowSubmitObj.busRecord.RUN_STATUS) {
				if (flowSubmitObj.busRecord.RUN_STATUS != 0) {
					/*其它环节审批表可以进行打印*/
					$("a[id='spbsf']").each(function (i) {
						$(this).attr("onclick", "goPrintTemplate('HFCQDJSPB','3');");
					});
					$("a[id='spbdf']").each(function (i) {
						$(this).attr("onclick", "goPrintTemplate('HFCQDJSPB','3');");
					});
					/*打印审批表表单出现*/
					$("div[id^=djshxx_]").show();
					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
						/*所有字段不可写*/
						$("#T_BDC_HFCQDJ_FORM input").each(function(index){
							$(this).attr("disabled","disabled");
						});
						$("#T_BDC_HFCQDJ_FORM select").each(function(index){
							$(this).attr("disabled","disabled");
						});
						var hfvalue = $("input[name='HF_TYPE']:checked").val();
						$("#page_"+hfvalue+" input").each(function(index){
							$(this).attr("disabled","disabled");
						});
						$("#page_"+hfvalue+" select").each(function(index){
							$(this).attr("disabled","disabled");
						});
						if($("input[name='SBMC']").val()){
						}else{
							$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');
						}
						$("#userinfo_div input").each(function(index){
							$(this).attr("disabled","disabled");
						});
						$("#userinfo_div select").each(function(index){
							$(this).attr("disabled","disabled");
						});
						$("#baseinfo input").each(function(index){
							$(this).attr("disabled","disabled");
						});
						$("#slxx input , #slxx select").each(function (index) {
							$(this).attr("disabled","disabled");
						})
					}
					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
						/*出现登簿按钮*/
						$("tr[name='bdcqzh_tr']").attr("style","");
						$("tr[name='bdcczr_tr']").attr("style","");
					}
				}
			}
			//回填权力人信息和权属信息
			initAutoTable();
		} else {
			/*申报名称*/
			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
			$("input[name='HF_TYPE']:first").attr('checked', 'checked');
		}
		/*类型切换操作*/
		hftypeclick();
	}



	//------------------权证打印字段控制-------------------------
	$(function () {
		$("input[name='BDC_QZBSM']").removeAttr("disabled");
		var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
		if (BDC_OPTYPE) {
			$("#ydb1").hide();
			$("#qrdb1").hide();
			$("#qrdb2").hide();
			$("#qrdb3").hide();
			$("#qrdb4").hide();
			/*显示打印信息*/
			if (BDC_OPTYPE == '1' || BDC_OPTYPE == 'flag1') {
				$("tr[name='bdcqzh_tr']").show();
				$("tr[name='bdcczr_tr']").show();
				/*控制按钮不被disabled*/
				$("#zsyl1").removeAttr("disabled");
				$("#zsdy1").removeAttr("disabled");
				$("#zsyl2").removeAttr("disabled");
				$("#zsdy2").removeAttr("disabled");
				$("#zsyl3").removeAttr("disabled");
				$("#zsdy3").removeAttr("disabled");
				$("#zsyl4").removeAttr("disabled");
				$("#zsdy4").removeAttr("disabled");
			}
			/*显示缴费发证信息*/
			if (BDC_OPTYPE == '2' || BDC_OPTYPE == 'flag2') {
				$("div[id^=djjfxx_]").show();
				$("div[id^=djfzxx_]").show();
				$("div[id^=djgdxx_]").show();
				var hfvalue = $("input[name='HF_TYPE']:checked").val();
				$("#djxx_Page" + hfvalue + " input").each(function (index) {
					$(this).attr("disabled", false);
				});
				$("#djxx_Page" + hfvalue + " select").each(function (index) {
					$(this).attr("disabled", false);
				});
			}
		} else {
			$("#zsdy1").hide();
			$("#zsdy2").hide();
			$("#zsdy3").hide();
			$("#zsdy4").hide();
		}
	})
//------------------权证打印字段控制-------------------------

/******************************换发产权证书 不可编辑项初始化开始******************************/

    $("#powerSourceInfo input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#powerSourceInfo select").each(function(index){
		$(this).attr("disabled","disabled");
	});
	$('#powerSourceInfo').find(".Wdate").attr('disabled',"disabled");

	$("#zdjbxx input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#zdjbxx select").each(function(index){
		$(this).attr("disabled","disabled");
	});
	$("#gyqlxx input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#gyqlxx select").each(function(index){
		$(this).attr("disabled","disabled");
	});
	$('#gyqlxx').find(".Wdate").attr('disabled',"disabled");

	$("#fwjbxx input").each(function(index){
		$(this).attr("readonly","readonly");
	});
	$("#fwjbxx select").each(function(index){
		$(this).attr("disabled","disabled");
	});
	$('#fwjbxx').find(".Wdate").attr('disabled',"disabled");
/******************************换发产权证书 不可编辑项初始化结束******************************/

/***************所有登簿按钮不可被disabled****************/
$(".disabledButton").each(function (index) {
	$(this).attr("disabled", false);
})
/***************所有登簿按钮不可被disabled****************/
});

</script>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData();
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg();
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData();
</SCRIPT>
    <style>
		.sel{
			border:solid 1px red;
		}
		.tab_text {
		    width: 250px;
		    height: 24px;
		    line-height: 24px;
		    /* padding: 0 5px; */
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
		.tab_text1 {
		    width: 50%;
		    height: 25px;
		    line-height: 25px;
		    padding: 0 5px;
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
    </style>
</head>

<body>
	<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
    <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_HFCQDJ_FORM" method="post">
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

		<input type="hidden" name="DAXX_JSON" value="${busRecord.DAXX_JSON}">
		<input type="hidden" name="FWXX_JSON" value="${busRecord.FWXX_JSON}">
		<input type="hidden" name="DYDA_JSON" value="${busRecord.DYDA_JSON}">
		<input type="hidden" name="YGXX_JSON" value="${busRecord.YGXX_JSON}">

		<!-- <input type="hidden" name="ISAUDITPASS" id="isAuditPass" value="0"/> -->
		<%--===================重要的隐藏域内容=========== --%>

		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

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
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		<!-- 受理信息 -->
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx">
			<tr>
				<th colspan="4">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
				<td><input type="text" class="tab_text " disabled="disabled"
					name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
				<td><eve:eveselect clazz="tab_text validate[required]" dataParams="ZDQLLX"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.HF_QLLX}"
					defaultEmptyText="选择权利类型" name="HF_QLLX" id="HF_QLLX">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>类型：</td>
				<td colspan="3">
				   <input type="radio" name="HF_TYPE" onclick="hftypeclick();" value="1" <c:if test="${busRecord.HF_TYPE=='1'}">checked="checked"</c:if> >换发产权证书
			       <input type="radio" name="HF_TYPE" onclick="hftypeclick();" value="2" <c:if test="${busRecord.HF_TYPE=='2'}">checked="checked"</c:if> >换发抵押权登记证明
				   <input type="radio" name="HF_TYPE" onclick="hftypeclick();" value="3" <c:if test="${busRecord.HF_TYPE=='3'}">checked="checked"</c:if> >换发预告登记证明
			       <input type="radio" name="HF_TYPE" onclick="hftypeclick();" value="4" <c:if test="${busRecord.HF_TYPE=='4'}">checked="checked"</c:if> >换发抵押权预告登记证明
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
				<td><input type="text" class="tab_text validate[required]"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
				<td style="width:430px">
					<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="1">
					</eve:eveselect>
				</td>
			</tr>
			<tr style="display:none;" id="DYH_ISSHOW">
				<td class="tab_width"><font class="tab_color ">*</font>不动产单元号：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required,custom[estateNum]]" style="width: 450px"
						   name="ESTATE_NUM" id="ESTATE_NUM" value="${busRecord.ESTATE_NUM }" />&nbsp;&nbsp;
						   <input type="button" id="DYH_BTN" value="不动产预告登记查询" class="eve-button"/> </td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>坐落：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="LOCATED" value="${busRecord.LOCATED}" style="width: 72%;"  />
				</td>
			</tr>
			<tr>
				<td class="tab_width">通知人姓名：</td>
				<td><input type="text" class="tab_text "
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<td class="tab_width">通知人电话：</td>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
			    <td class="tab_width">受理人员：</td>
                <td><input type="text" class="tab_text "
                           name="ACCEPT_USER" value="${busRecord.ACCEPT_USER }" readonly="readonly"/></td>
				<td class="tab_width">备注：</td>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" />
				</td>
			</tr>
		</table>

		<%--开始引入领证人信息--%>
		<jsp:include page="./bdcqlc/lzrInfo.jsp" />
		<%--结束引入领证人信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
	</form>

	<!-- 开始引入换发产权证书页面 -->
	<form id="page_1" style="display:none">
		<jsp:include page="./bdcqlc/hfcqdj/bdczfhdj.jsp">
			<jsp:param value="${busRecord.YW_ID}" name="BDC_SUB_YWID" />
			<jsp:param value="1" name="hftype" />
			<jsp:param value="${serviceItem.ITEM_CODE}" name="ITEM_CODE" />
		</jsp:include>
	</form>
	<!-- 结束引入换发产权证书页面 -->

	<!-- 开始引入换发抵押权预告登记证明页面-->
	<form id="page_4"  style="display:none">
		<jsp:include page="./bdcqlc/hfcqdj/dyqyghfdj.jsp">
			<jsp:param value="${busRecord.YW_ID}" name="BDC_SUB_YWID" />
			<jsp:param value="4" name="hftype" />
			<jsp:param value="${serviceItem.ITEM_CODE}" name="ITEM_CODE" />
		</jsp:include>
	</form>
	<!-- 结束引入换发抵押权预告登记证明页面 -->

	<!-- 开始引入换发抵押权登记证明页面 -->
	<form id="page_2"  style="display:none">
		<jsp:include page="./bdcqlc/hfcqdj/hfdyqzmdj.jsp">
			<jsp:param value="${busRecord.YW_ID}" name="BDC_SUB_YWID" />
			<jsp:param value="2" name="hftype" />
			<jsp:param value="${serviceItem.ITEM_CODE}" name="ITEM_CODE" />
		</jsp:include>
	</form>
	<!-- 结束引入换发抵押权登记证明页面 -->

	<!-- 开始引入换发预告登记证明页面 -->
	<form id="page_3" style="display:none">
		<jsp:include page="./bdcqlc/hfcqdj/hfygdjzm.jsp">
			<jsp:param value="${busRecord.YW_ID}" name="BDC_SUB_YWID" />
			<jsp:param value="3" name="hftype" />
			<jsp:param value="${serviceItem.ITEM_CODE}" name="ITEM_CODE" />
		</jsp:include>
	</form>
	<!-- 结束引入换发预告登记证明页面 -->

</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">
			</OBJECT>
</html>
