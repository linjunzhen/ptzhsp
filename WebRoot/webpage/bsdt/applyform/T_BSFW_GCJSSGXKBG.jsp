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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/gcjsxm/gcjssgxkbg/js/gcjssgxkbg.js"></script>
<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>
<script  type="text/javascript">
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		if(flowSubmitObj){
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段值
			if(flowSubmitObj.busRecord){
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'||flowSubmitObj.busRecord.RUN_STATUS==2){
					$('#T_BSFW_GCJSSGXKBG_FORM').find('input,textarea').prop("readonly", true);
					$('#T_BSFW_GCJSSGXKBG_FORM').find('select').prop("disabled", "disabled");
					$('#T_BSFW_GCJSSGXKBG_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
					$('#T_BSFW_GCJSSGXKBG_FORM').find(".laydate-icon").attr('disabled',"disabled");	
					$("#loadData").remove();
				}
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_GCJSSGXKBG_FORM");
				//获取表单字段权限控制信息
				var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
				 //初始化表单字段权限控制
				FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_GCJSSGXKBG_FORM");
				changeChangeItem(flowSubmitObj.busRecord.CHANGEITEM);
				initPerson(flowSubmitObj.busRecord.INDEXNUM);
				$("#PERSONNAME").val(flowSubmitObj.busRecord.PERSONNAME);
			}else{
				$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
				changeChangeItem(1);
			}
		}

	});
</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_BSFW_GCJSSGXKBG_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
	<input type="hidden" name="REGISTER_TYPE" value="0"/>
    <input type="hidden" name="flowStage" />
    <input type="hidden" name="SGDW_JSON" />
    <input type="hidden" name="JLDW_JSON" />
    <%--===================重要的隐藏域内容=========== --%>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td class="tab_width"> 审批事项：</td>
				<td width="35%">${serviceItem.ITEM_NAME}</td>
	            <td class="tab_width"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td class="tab_width"> 牵头部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td class="tab_width"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" style="width: 70%" maxlength="60" name="SBMC" value="${busRecord.SBMC }"/>
					<span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>
	
	<%--开始引入项目基本信息页面 --%>
	<jsp:include page="./gcjsxm/gcjssgxkbg/info.jsp" />
	<%--结束引入项目基本信息页面 --%>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./matterListTZXM_ZBTB.jsp" >
    <jsp:param value="T_BSFW_GCJSSGXKBG_FORM" name="formName"/>
    </jsp:include>
	
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	
    <div class="tab_height"></div>
    <div class="tab_height" ></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" style="display: none;">
        <tr>
            <th colspan="4">业务操作</th>
        </tr>
        <tr id="SFXYXT_TR" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>是否需要协调：</td>
            <td><eve:radiogroup typecode="SHSFXYXT" width="130px"
                    fieldname="SFXYXT" defaultvalue="-1"></eve:radiogroup></td>
        </tr>
        <tr id="SFXTYZ_TR2" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>协调是否一致：</td>
            <td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
                    fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
        </tr>
        
		<tr id="GSSFTG_TR2" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>公示结果：</td>
			<td><eve:radiogroup typecode="GSJG" width="130px"
					fieldname="GSSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
        <tr id="GSJG_TR" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>公示情况：</td>
            <td><textarea rows="5" cols="140" maxlength="1998" class="tab_textarea validate[required]]" name="GSJG"></textarea> </td>
        </tr>
		<tr id="GSXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>是否驳回异议：</td>
			<td><eve:radiogroup typecode="YesOrNo" width="130px"
					fieldname="XTSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
    </table>
	</form>
</body>
</html>
