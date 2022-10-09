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
<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>

<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
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
        //获取表单字段权限控制信息
        var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        var exeid = flowSubmitObj.EFLOW_EXEID;
        //初始化表单字段权限控制
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_CZWSPRPSGWXKZ_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
        	//初始化信息
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_CZWSPRPSGWXKZ_FORM");
        }
    }
});

function FLOW_SubmitFun(flowSubmitObj){
    var flowSubmitObj = FlowUtil.getFlowObj();
    //先判断表单是否验证通过
    var validateResult =$("#T_BSFW_CZWSPRPSGWXKZ_FORM").validationEngine("validate");
    if(validateResult){
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
        }else{
            return null;
        }   	
        //先获取表单上的所有值
        var formData = FlowUtil.getFormEleData("T_BSFW_CZWSPRPSGWXKZ_FORM");
        for(var index in formData){
            flowSubmitObj[eval("index")] = formData[index];
        }
        flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
        return flowSubmitObj;
    }else{
        return null;
    }
}

function FLOW_TempSaveFun(flowSubmitObj){
    //先判断表单是否验证通过
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BSFW_CZWSPRPSGWXKZ_FORM");
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;
}
</script>


</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
    
   <form id="T_BSFW_CZWSPRPSGWXKZ_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <%--===================重要的隐藏域内容=========== --%>
	<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
	<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
	<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
	<input type="hidden" name="OBLIGEE_NAME" value="${busRecord.OBLIGEE_NAME}" />
	<input type="hidden" name="OBLIGEE_IDNUM" value="${busRecord.OBLIGEE_IDNUM}" />
	<input type="hidden" name="OB_ID" value="${busRecord.OB_ID}" />
	<input type="hidden" name="runstatus1" value="${execution.RUN_STATUS}" />
	<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
	<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
	<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
	<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
	<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
    <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" /> <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
	<input type="hidden" name="WT_JSON" />
	<input type="hidden" name="ST_JSON" />
	<input type="hidden" name="BD_JSON" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="YW_ID" value="${busRecord.YW_ID}"/>
    <input type="hidden" name="CERTIFICATEIDENTIFIERFILEID" value="${busRecord.CERTIFICATEIDENTIFIERFILEID}"/>
    <input type="hidden" name="CERTIFICATEIDENTIFIERFILEPATH" value="${busRecord.CERTIFICATEIDENTIFIERFILEPATH}"/>
    <%--===================重要的隐藏域内容=========== --%>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
		<tr>
			<th colspan="4">证照信息</th>
		</tr>
		<tr>
			<td class="tab_width">排水户名称:</td>
			<td>
			  <input type="text" class="tab_text" name="PSHMC" value="${busRecord.PSHMC}"/>
			</td>
			<td class="tab_width">排水户代码:</td>
			<td>
			  <input type="text" class="tab_text" name="PSHDM" value="${busRecord.PSHDM}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">有效期自：</td>
			<td>
			<input type="text" class="tab_text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="YXQ_BEGIN" value="${busRecord.YXQ_BEGIN}"/>
			</td>
			<td class="tab_width">有效期至：</td>
			<td>
			<input type="text" class="tab_text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="YXQ_END" value="${busRecord.YXQ_END}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font>许可证编号:<br>
				<span style="color: red">(格式：XXXXXXXXXX字第XXXX号</span>
			</td>
			<td>
			  <input type="text" name="XKZBH" value="${busRecord.XKZBH}" class="tab_text validate[required,custom[czpwZzbm]]"/>
			</td>
			<td class="tab_width">发证时间：</td>
			<td>
			<input type="text" class="tab_text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" name="FZSJ" value="${busRecord.FZSJ}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">排水户名称或排水项目名称:</td>
			<td colspan="3">
			  <input type="text" class="tab_text" name="PSHMCHPSXMMC" value="${busRecord.PSHMCHPSXMMC}"/>
			</td>
		</tr>
		
		<tr>
			<td class="tab_width">法定代表人:</td>
			<td>
			  <input type="text" class="tab_text" name="FDDBR" value="${busRecord.FDDBR}"/>
			</td>
			<td class="tab_width">营业执照注册号:</td>
			<td>
			  <input type="text" class="tab_text" name="YYZZZCH" value="${busRecord.YYZZZCH}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">详细地址:</td>
			<td>
			  <input type="text" class="tab_text" name="XXDZ" value="${busRecord.XXDZ}"/>
			</td>
			<td class="tab_width">排水户类型:</td>
			<td>
			  <input type="text" class="tab_text" name="PSHLX" value="${busRecord.PSHLX}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">列入重点排污单位名录（是/否）：</td>
				<td> <select id="projectType" name="LRZDPWDWML" style="width:186px;" dataparams="PROJECTTYPE" value="${busRecord.LRZDPWDWML}">
				<option value="1">是</option>
				<option value="0">否</option>
			</td>
			<td class="tab_width">主要污染物项目及排放标准:</td>
			<td>
			  <input type="text" class="tab_text" name="ZYWRWXMJPFBZ" value="${busRecord.ZYWRWXMJPFBZ}"/>（mg/L）
			</td>
		</tr>
		<tr>
			<td class="tab_width">排污水口编号:</td>
			<td>
			  <input type="text" class="tab_text" name="PWSKBH" value="${busRecord.PWSKBH}"/>
			</td>
			<td class="tab_width">连接管位置:</td>
			<td>
			  <input type="text" class="tab_text" name="LJGWZ" value="${busRecord.LJGWZ}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">排水去向（路名）:</td>
			<td>
			  <input type="text" class="tab_text" name="PSQX" value="${busRecord.PSQX}"/>
			</td>
			<td class="tab_width">排水量:</td>
			<td>
			  <input type="text" class="tab_text" name="PSL" value="${busRecord.PSL}"/>（m3/日）
			</td>
		</tr>
		<tr>
			<td class="tab_width" >污水最终去向:</td>
			<td colspan="3">
			  <input type="text" class="tab_text" name="WSZZQX" value="${busRecord.WSZZQX}"/>
			</td>
		</tr>
	</table>
	
  	<%--开始引入公用上传材料界面 --%>
 	<jsp:include page="./matterListZF.jsp" >
        <jsp:param value="T_BSFW_CZWSPRPSGWXKZ_FORM" name="formName"/>
     </jsp:include>
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	
	<div class="tab_height"></div>
	</form>
</body>
</html>


