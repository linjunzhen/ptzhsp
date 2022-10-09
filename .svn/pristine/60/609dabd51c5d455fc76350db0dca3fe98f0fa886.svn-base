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
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/sbqlc/qyzgxxbg/js/qyzgxxbg.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/sbqlc/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/main/css/fonticon.css" />

<script type="text/javascript">
	$(function(){		
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if(flowSubmitObj){
			if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES =='开始' && flowSubmitObj.EFLOW_EXERUNSTATUS!='2'){
			//除开始环节外均需开放受理信息
		    	$("#qyzgxxbg").attr("style","display:none;");
		    }
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			 //初始化表单字段权限控制
			//FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_SBQLC_QYZGBGDJ");
			//初始化表单字段值
			if(flowSubmitObj.busRecord){
				//初始化表单字段值
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_SBQLC_QYZGXXBG_FORM");
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES =='审核' || 
					flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES =='审批' ||
					flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES =='办结' ||
					flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
				 	$("#T_SBQLC_QYZGXXBG_FORM").find("input,select,.Wdate").attr("disabled","disabled");
				 	//a标签按钮禁用
				 	$("#qyzgxxbg").find(".easyui-linkbutton").attr("disabled",true).css("pointer-events","none");		 	
				 	//隐藏表格的复选框
					$("#xzxxGrid").datagrid("hideColumn", "ck");	
				 }		
		
	    		if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始'){
					if($("input[name='SBMC']").val()){
					}else{
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}			
			}else{
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}
    	}
   	});	
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_SBQLC_QYZGXXBG_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/> 
		<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}"/>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}"/>
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}"/>
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}"/>
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}"/>
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}"/>
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON"/>
		<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}"/>
		<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}"/>
		<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}"/>
		<input type="hidden" name="SXLX" value="${serviceItem.SXLX}"/>
		<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}"/>
		<%--已参加险种信息 --%>
		<input type="hidden" name="CBXZXX_JSON" />
		
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="baseinfo">
			<tr>
				<th colspan="4">基本信息</th>
			</tr>
			<tr>
				<td>审批事项：</td>
				<td>${serviceItem.ITEM_NAME}</td>
				<td class="tab_width">承诺时间(工作日)：</td>
				<td>${serviceItem.CNQXGZR}</td>
			</tr>
			<tr>
				<td>所属部门：</td>
				<td>${serviceItem.SSBMMC}</td>
				<td class="tab_width">审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>

			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3">
				<input type="text"
					class="tab_text validate[required]" style="width: 70%"
					maxlength="220" name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span>
				</td>
			</tr>
			<tr>
				<td class="tab_width">申报号：</td>
				<td id="EXEID"></td>
				<td></td>
				<td></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入公用办理结果领取方式界面 --%>
		<jsp:include page="./sbqlc/T_SBQLC_BJJGLQ.jsp" />
		<%--结束引入公用办理结果领取方式界面 --%>
		
		<%--企业职工基本养老保险变更登记信息--%>
		<div id="qyzgxxbg" >
			<jsp:include page="./sbqlc/qyzgxxbg/qyzgxxbg.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="SB_YWID" />
				<jsp:param value="${exeId}" name="exeId" />
			</jsp:include>	
		</div>
		<%--企业职工基本养老保险变更登记信息 --%>		
	</form>
</body>
</html>