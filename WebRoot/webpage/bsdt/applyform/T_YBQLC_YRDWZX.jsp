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
	String today = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd");
	request.setAttribute("today", today);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/yrdwzx/js/yrdwzx.js"></script>
<script type="text/javascript">
	$(function() {
			
		$("input[name='JBR_NAME']").removeAttr('readonly');
		$("input[name='JBR_ZJHM']").removeAttr('readonly');
		
		$('#CB_HDBJ').prop("disabled","disabled");//核对标记不可编辑
		
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
			var exeid = flowSubmitObj.EFLOW_EXEID;//流程申报号
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_YBQLC_YRDWZX_FORM");
			//初始化表单字段值
			if (flowSubmitObj.busRecord) {
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_YBQLC_YRDWZX_FORM");
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES =='审查' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
					 $("#T_YBQLC_YRDWZX_FORM").find("input,select,.Wdate").attr("disabled","disabled");		
			 	 	 $("#CBXX").attr("style","");//开放参保信息核对标记查询
			 	 	 $("#ZXXX").attr("style","");//开放注销单位信息发送地税	 
			 	 	 $("input[name='ZX_FSRQ']").val('${today}');
			 	 	 $("input[name='ZX_FSRQ']").removeAttr('disabled');//发送日期可编辑
				}
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '开始' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除退回至开始环节外开放受理信息
					$("#CBXX").attr("style","");    
		      	}	
			} else {
				//流程初始化操作
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');				
			}
			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
		}
	});


	//------------------------------------身份证读取开始---------------------------------------------------  

	function print() //打印
	{
		GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
	}
	//------------------------------------身份证读取结束---------------------------------------------------
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
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_YBQLC_YRDWZX_FORM" method="post">
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
		
		<%--===================事项基本信息开始=========== --%>
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
				<td >所属部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC}"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>
		<%--===================事项基本信息结束=========== --%>
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始受理信息--%>
		<!-- <div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="2">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width" style="text-align:center">操作：</td>
				<td>
				   <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectUnitInfos();">单位信息综合查询（待建设中）</a>
				</td>
			</tr>
		</table> -->
		<%--结束受理信息--%>

		<%--开始引入用人单位核对标记页面--%>
		<jsp:include page="./ybqlc/yrdwzx/yrdwzx.jsp" />		
		<%--结束引入用人单位核对标记页面--%>

	</form>
</body>
</html>
