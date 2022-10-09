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
<script type="text/javascript" 
	src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcsdqyw/js/blsdqyw.js"></script>
<script type="text/javascript">
$(function(){
    //初始化验证引擎的配置
    $.validationEngine.defaults.autoHidePrompt = true;
    $.validationEngine.defaults.promptPosition = "topRight";
    $.validationEngine.defaults.autoHideDelay = "2000";
    $.validationEngine.defaults.fadeDuration = "0.5";
    $.validationEngine.defaults.autoPositionUpdate =true;
    var flowSubmitObj = FlowUtil.getFlowObj();
    var BSYHLX = $("input[name='BSYHLX']:checked").val();
    initAutoTable(flowSubmitObj);//初始化权利信息
    if(BSYHLX!=null && BSYHLX=="1"){
    	grclick()
    }else{
    	qyclick();
    }
    $("#slxx_div input , #slxx_div radio, #slxx_div select").each(function () {
		$(this).attr("disabled", true);
	});
    $("#sbdxxx input , #sbdxxx radio, #sbdxxx select").each(function () {
		$(this).attr("disabled", true);
	});
    $("#sqr input , #sqr radio, #sqr select").each(function () {
		$(this).attr("disabled", true);
	});
    $("#sqjg1 input , #sqjg1 radio, #sqjg1 select").each(function () {
		$(this).attr("disabled", true);
	});
    $("#SFXSJBRXX input").each(function () {
		$(this).attr("disabled", true);
	});
    $("#JBRXX input , #JBRXX radio, #JBRXX select").each(function () {
		$(this).attr("disabled", true);
	});
    $("#cksjry input").each(function () {
		$(this).attr("disabled", true);
	});
    
    $("#powerPeopleInfo input, #powerPeopleInfo select").each(function () {
		$(this).attr("disabled", true);
	});
    if(flowSubmitObj){
        //获取表单字段权限控制信息
        var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        var exeid = flowSubmitObj.EFLOW_EXEID;
        $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
         //初始化表单字段权限控制
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDCQLC_BLSDQYW_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
        
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_BLSDQYW_FORM");
            
			checkIsPowTransfer(flowSubmitObj.busRecord.IS_POWTRANSFER);
            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
				if ($("input[name='SBMC']").val()) {
				} else {
					$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
				}
			}
			
			var isShowLx = $('input[name="isShowLx"]').val();
			if(isShowLx=="1"){
			   $("#lx").attr("style","display:none");
			}
		}else{
            $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
            
        }
    }
    
    var itemName = '${serviceItem.ITEM_NAME}';
	if(itemName=="不动产燃气业务流程"){
		document.getElementById("powTr").style.display="none";
		document.getElementById("waterTr").style.display="none";
		document.getElementById("svaTr").style.display="none";
	}else if(itemName=="不动产电力业务流程"){
		document.getElementById("gasTr").style.display="none";
		document.getElementById("waterTr").style.display="none";
		document.getElementById("svaTr").style.display="none";
	}else if(itemName=="不动产水力业务流程"){
		document.getElementById("gasTr").style.display="none";
		document.getElementById("powTr").style.display="none";
		document.getElementById("svaTr").style.display="none";
	}else if(itemName=="不动产广电业务流程"){
		document.getElementById("gasTr").style.display="none";
		document.getElementById("powTr").style.display="none";
		document.getElementById("waterTr").style.display="none";
	}
	
	  
});

</script>
    <style>
		.sel{
			border:solid 1px red;
		}
		.tab_text {
		    width: 350px;
		    height: 24px;
		    line-height: 24px;
		    /* padding: 0 5px; */
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
    </style>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_BLSDQYW_FORM" method="post">
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
	    <input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
	    <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />   		
		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->	
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		
		<%-- 权利人信息明细 --%>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<%-- 权属来源信息明细 --%>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<%-- 权属来源限制明细 --%>
		<input type="hidden" name="POWERLIMITINFO_JSON" id="POWERLIMITINFO_JSON"/>
		<%-- 当前可打印的阅办模板列表 --%>
		<input type="hidden" name="TEMPLATE_LIST" id="TEMPLATE_LIST"/>
		<%--是否开发商业务办理--%>
		<input type="hidden" name="IS_KFSYWSL" id="IS_KFSYWSL"/>
		<%--是否开发商业务办理（url）--%>
		<input type="hidden" name="isKfsywsl" id="isKfsywsl" value="${param.isKfsywsl}"/>
		
		<input type="hidden" name="isShowLx" id="isShowLx" value="${busRecord.ISSHOWLX}"/>
				
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
					style="width: 70%" maxlength="60" disabled="disabled"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./bdcsdqyw/applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		
		<%--开始引入受理信息--%>
		<jsp:include page="./bdcsdqyw/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>
		
		<%--开始引入公用上传材料界面x --%>
		<jsp:include page="./bdcsdqyw/sdqywMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>
	</form>
</body>
</html>
