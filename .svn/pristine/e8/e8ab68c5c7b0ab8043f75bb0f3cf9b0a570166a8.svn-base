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
<title>平潭综合实验区行政服务中心-网上办事大厅</title>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<style>
.eflowbutton {
	background: #3a81d0;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
}
</style>
<script type="text/javascript">
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
            $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
             //初始化表单字段权限控制
            //FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"FINISH_MANAGE_FORM");
            if(flowSubmitObj.busRecord){
                //初始化表单字段值
                FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"FINISH_MANAGE_FORM");
                if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '开始' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除退回至开始环节外开放受理信息
                    $("#xfsjinfo").attr("style","");
                }
                if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始'){
                    /* if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '受理'){
                        $("#xfsjinfo").find("input,select,.Wdate").attr("disabled","disabled");
                    } */
                    if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='决定'){
                        $("input[name='CHECKDEPARTNAME']").val('${sessionScope.curLoginUser.depName}');
                        $("input[name='CHECKPERSONNAME']").val('${sessionScope.curLoginUser.fullname}');
                    }
                }
                
                    if($("input[name='SBMC']").val()){
                    }else{
                        $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');
                    }
            }else{
                var projectCode = document.getElementById("PROJECTCODE").value;
                $.ajax({
                    type: "POST",
                    url: "projectFinishManageController.do?projectFinishItemData",
                    data : {
                        projectCode : projectCode
                    },
                    async: false, 
                    success: function (result) {
                        var dataObj = JSON2.parse(result);
                        var data = dataObj.data;
                        FlowUtil.initFormFieldValue(data,"FINISH_MANAGE_FORM");
                        $("input[name='PRJNUM']").val(data.PRJNUM);
                    }
                });
                $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
            }
            
		}
		//判断有无竣工验收备案编号
		var PRJFINISHNUM = $("[name='PRJFINISHNUM']").val();
		if (PRJFINISHNUM) { //不为空
		} else {
			loadPRJFINISHNUM(); //获取竣工验收备案编号;
		}
		//判断有无竣工验收编号
		var PRJFINISHCHECKNUM = $("[name='PRJFINISHCHECKNUM']").val();
		if (PRJFINISHCHECKNUM) { //不为空
		} else {
			loadPRJFINISHCHECKNUM(); //获取竣工验收编号;
		}
		//判断有无质量监督记录编号
		var QUALITYNUM = $("[name='QUALITYNUM']").val();
		if (QUALITYNUM) { //不为空
		} else {
			loadQUALITYNUM(); //获取质量监督记录编号;
		}
		//判断有无质量报监编号
		var ZLJDNUM = $("[name='ZLJDNUM']").val();
		if (ZLJDNUM) { //不为空
		} else {
			loadZLJDNUM(); //获取质量报监编号;
		}

	});
    function FLOW_SubmitFun(flowSubmitObj){
        var validateResult =$("#FINISH_MANAGE_FORM").validationEngine("validate");
        if(validateResult){
            var cjgfzrztDatas = [];
            if(endEditing()){
                var returnData = $("#cjgfzrztGrid").datagrid("getData");
                var rows = returnData.rows;
                if (rows.length > 0) {
                    for (var i = 0; i < rows.length; i++) {
                        cjgfzrztDatas.push(rows[i]);
                    }
                }
                $("input[type='hidden'][name='CJGFZRZT_JSON']").val(JSON.stringify(cjgfzrztDatas));
            }else{
                alert("险种信息存在未结束编辑行，请结束编辑！");
                return false;
            }
            var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
            if(submitMaterFileJson||submitMaterFileJson==""){
                $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
                //获取表单上的所有值
                var formData = FlowUtil.getFormEleData("FINISH_MANAGE_FORM");
                for(var index in formData){
                    flowSubmitObj[eval("index")] = formData[index];
                }
                return flowSubmitObj;
            }
            return null;
        }
        return null;
    }
    /**
     * 暂存流程
     */
    function FLOW_TempSaveFun(flowSubmitObj) {
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
        $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
        //先获取表单上的所有值
        var formData = FlowUtil.getFormEleData("FINISH_MANAGE_FORM");
        for (var index in formData) {
            flowSubmitObj[eval("index")] = formData[index];
        }
        return flowSubmitObj;
    }
    /**
     * 退回流程
     */
    function FLOW_BackFun(flowSubmitObj){
        return flowSubmitObj;
    }
    
    
     /**
     * 获取竣工验收备案编号
     */ 
    function loadPRJFINISHNUM(){
        var PRJNUM = $("input[name='PRJNUM']").val();
        $.post("projectFinishManageController/getPrjLinkCode.do",{proNum:PRJNUM,codetype:'JX'},
            function(responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.status) {
                    $("[name='PRJFINISHNUM']").val(resultJson.code);            
                } else {
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
            }
        );
    }

    /**
     * 获取竣工验收编号
     */ 
    function loadPRJFINISHCHECKNUM(){
            var PRJNUM = $("input[name='PRJNUM']").val();
        $.post("projectFinishManageController/getPrjLinkCode.do",{proNum:PRJNUM,codetype:'JY'},
            function(responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.status) {
                    $("[name='PRJFINISHCHECKNUM']").val(resultJson.code);           
                } else {
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
            }
        );
    }       
    /**
     * 获取质量监督记录编号
     */ 
    function loadQUALITYNUM(){
            var PRJNUM = $("input[name='PRJNUM']").val();
        $.post("projectFinishManageController/getPrjLinkCode.do",{proNum:PRJNUM,codetype:'ZL'},
            function(responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.status) {
                    $("[name='QUALITYNUM']").val(resultJson.code);          
                } else {
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
            }
        );
    }
    /**
     * 获取质量报监编号
     */ 
    function loadZLJDNUM(){
            var PRJNUM = $("input[name='PRJNUM']").val();
        $.post("projectFinishManageController/getPrjLinkCode.do",{proNum:PRJNUM,codetype:'ZB'},
            function(responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.status) {
                    $("[name='ZLJDNUM']").val(resultJson.code);         
                } else {
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
            }
        );
    }    
    
    
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="FINISH_MANAGE_FORM" method="post">
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
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" /> <input
			type="hidden" name="PRJFINISHNUM" /> <input type="hidden"
			name="PRJFINISHCHECKNUM" /> <input type="hidden" name="QUALITYNUM" />
		<input type="hidden" name="ZLJDNUM" />
		<c:if test="${projectCode != null }">
			<input type="hidden" id="PROJECTCODE" name="PROJECTCODE"
				value="${projectCode}" />
			<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE"
				value="${projectCode}" />
		</c:if>
		<c:if test="${projectCode == null }">
			<input type="hidden" id="PROJECTCODE" name="PROJECTCODE"
				value="${busRecord.PROJECTCODE}" />
			<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE"
				value="${busRecord.PROJECTCODE}" />
		</c:if>
		<!-- <input type="hidden" name="PRJNUM" />  -->
		<input type="hidden"
			name="CJGFZRZT_JSON" /> <input type="hidden" name="CHECKDEPARTNAME" />
		<input type="hidden" name="CHECKPERSONNAME" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro"
			id="baseinfo">
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
				<td colspan="3"><input type="text"
					class="tab_text validate[required]" style="width: 70%"
					maxlength="220" name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span></td>
			</tr>
			<tr>
				<td class="tab_width">申报号：</td>
				<td id="EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入消防设计基本信息部分 --%>
		<div id="xfsjinfo" style="display:none">
			<jsp:include page="./jgxk/jbxx.jsp" />
		</div>
		<%--结束引入消防设计基本信息部分 --%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
	</form>
</body>
</html>