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
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/gwybzsh/js/gwybzsh.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/common/js/ybCommon.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/common/js/ybEmsSend.js"></script>	
<script type="text/javascript">
	$(function() {
		//初始化表单字段控制
		$("#TSRYLB").val("008");
		$("#TSRYLB").attr("disabled","disabled");
		$("#GZZT").attr("disabled","disabled");
		$("#FZX").attr("disabled","disabled");
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
			//FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_YBQLC_GWYBZSH_FORM");			
			if (flowSubmitObj.busRecord) {
				//初始化表单字段值
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_YBQLC_GWYBZSH_FORM");
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "page_1");
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "other");
				if(flowSubmitObj.busRecord.BGXX_JSON != null){
					var BGXX_JSON = JSON.parse(flowSubmitObj.busRecord.BGXX_JSON);
					FlowUtil.initFormFieldValue(BGXX_JSON, "page_2");
				}
				if(flowSubmitObj.busRecord.ZXXX_JSON != null){
					var ZXXX_JSON = JSON.parse(flowSubmitObj.busRecord.ZXXX_JSON);
					FlowUtil.initFormFieldValue(ZXXX_JSON, "page_3");
				}
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '开始' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除退回至开始环节外开放受理信息
					$("#slxx").attr("style","");      	  
		      	}		      			      	
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES=='审查'  
					|| flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){	
					$('#T_YBQLC_GWYBZSH_FORM').find(":radio,:checkbox,input,select,.eflowbutton").attr('disabled',"disabled");
		            //操作类型值	
					var bzshValue = $("input[name='BZSH_TYPE']:checked").val();	
					$("#page_"+bzshValue).find("input,select,.Wdate,.eflowbutton").attr('disabled',"disabled");	
				}
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始'){
					if($("input[name='SBMC']").val()){
					}else{
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}				
			} else {
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
				//类型默认选中-居民新参保登记
				$("input[name='BZSH_TYPE']:first").attr('checked', 'checked');
			}
			if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '开始' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除退回至开始环节外开放受理信息
				//类型选择操作
				bzshtypeclick();      	  
	    	}			
			
		}
	});
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_YBQLC_GWYBZSH_FORM" method="post">
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
		
		<%-- 变更信息--%>
		<input type="hidden" name="BGXX_JSON" />
		
		<%-- 注销信息--%>
		<input type="hidden" name="ZXXX_JSON" />
		
		<%--推送标志 --%>
		<input type="hidden" name="PUSH_FLAG" />
		
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
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
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
		
		<%--医保通用查询页面--%>
		<div id="ybCommonSearch" style="">
			<jsp:include page="./ybqlc/common/ybCommonSearch.jsp" />			
		</div>
		<%--医保通用查询页面 --%>
		
		<%--开始受理信息--%>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx" style="display:none">
			<tr>
				<th colspan="2">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>类型：</td>
				<td>
				   <input type="radio" name="BZSH_TYPE" onclick="bzshtypeclick();" value="1" <c:if test="${busRecord.BZSH_TYPE=='1'}">checked="checked"</c:if> >公务员危重病登记
			       <input type="radio" name="BZSH_TYPE" onclick="bzshtypeclick();" value="2" <c:if test="${busRecord.BZSH_TYPE=='2'}">checked="checked"</c:if> >公务员危重病变更
				   <input type="radio" name="BZSH_TYPE" onclick="bzshtypeclick();" value="3" <c:if test="${busRecord.BZSH_TYPE=='3'}">checked="checked"</c:if> >公务员危重病注销	       
				</td>
			</tr>
		</table>
		<%--结束受理信息--%>
		
		</form>
		
		

		<!-- 开始引入公务员危重病登记页面 -->
		<form id="page_1" style="display:none;">
			<jsp:include page="./ybqlc/gwybzsh/djxx.jsp" />
		</form>
		<!-- 结束引入公务员危重病登记页面 -->
		
		<!-- 开始引入公务员危重病变更页面 -->
		<form id="page_2" style="display:none;">
			<jsp:include page="./ybqlc/gwybzsh/bgxx.jsp" />			
		</form>
		<!-- 结束引入公务员危重病变更页面 -->
		
		<!-- 开始引入公务员危重病注销页面 -->
		<form id="page_3" style="display:none;">	
			<jsp:include page="./ybqlc/gwybzsh/zxxx.jsp" />				
		</form>
		<!-- 结束引入公务员危重病注销页面 -->
		
		<%--医保办结结果领取方式&其他信息页面--%>
		<form id="other" style="">
			<jsp:include page="./ybqlc/common/T_YBQLC_BJJGLQ.jsp" />	
		</form>		
		<%--医保办结结果领取方式&其他信息页面 --%>
				
</body>
</html>
