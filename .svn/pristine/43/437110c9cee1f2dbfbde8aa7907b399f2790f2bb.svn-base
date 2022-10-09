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
	<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<style>
	.eflowbutton{
		  background: #3a81d0;
		  border: none;
		  padding: 0 10px;
		  line-height: 26px;
		  cursor: pointer;
		  height:26px;
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
		//初始化表单个性化字段权限
	    initForm(flowSubmitObj);
		if(flowSubmitObj){
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"FC_PROJECT_BACK_FORM");
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			 //初始化表单字段权限控制
			//FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"FC_PROJECT_BACK_FORM");
			if(flowSubmitObj.busRecord){
				//初始化表单字段值
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"FC_PROJECT_BACK_FORM");
				var busRecord = flowSubmitObj.busRecord;
    			var sgtscInfo = busRecord.SGTSCHGZHBH;
    			var zygcxfInfo = busRecord.ZYGCXFSCHGSBH;
    			//回填施工图审查合格证书编号信息
    			if(sgtscInfo!=null && sgtscInfo!=""){
    				var sgtscArr = sgtscInfo.split(",");
    				for(var i=0;i<sgtscArr.length;i++){
    					var sgtscValue = sgtscArr[i];
    					var idValue = "SGTSCID_" + i;
    					var inputText = '<div id="sgdiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="SGTSC_NO" value="'+(sgtscValue)+'"/>';
    					var asign='<a href="javascript:void(0);" onclick="delSgtsNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
    					$('#sgtsc').append(inputText+asign);
    				}
    			}
    			//回填专业工程消防审查合格书编号信息
    			if(zygcxfInfo!=null && zygcxfInfo!=""){
    				var zygcxfArr = zygcxfInfo.split(",");
    				for(var i=0;i<zygcxfArr.length;i++){
    					var zygcxfValue = zygcxfArr[i];
    					var idValue = "ZYGCXFID_" + i;
    					var inputText = '<div id="zydiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="ZYGCXF_NO" value="'+(zygcxfValue)+'"/>';
    					var asign='<a href="javascript:void(0);" onclick="delZygcxfNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
    					$('#zygcxf').append(inputText+asign);
    				}
    			}
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始'){
				}
					if($("input[name='SBMC']").val()){
					}else{
							$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
			}else{
				var prjCode = document.getElementById("PRJ_CODE").value;
				var prjNum = document.getElementById("PRJ_NUM").value;
				$.ajax({
					type: "POST",
			        url: "xfDesignController.do?xfPrjectInfo",
			        data : {
			        	prjCode : prjCode,
			        	prjNum : prjNum,
			        },
			        async: false, 
			        success: function (result) {
			        	var dataObj = JSON2.parse(result);
			        	var data = dataObj.data;
			        	var sgtscInfo = data.SGTSCHGZHBH;
			        	//回填施工图审查合格证书编号信息
						if(sgtscInfo!=null && sgtscInfo!=""){
							var sgtscArr = sgtscInfo.split(",");
							for(var i=0;i<sgtscArr.length;i++){
								var sgtscValue = sgtscArr[i];
								var idValue = "SGTSCID_" + i;
								var inputText = '<div id="sgdiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="SGTSC_NO" value="'+(sgtscValue)+'"/>';
								var asign='<a href="javascript:void(0);" onclick="delSgtsNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
								$('#sgtsc').append(inputText+asign);
							}
						}
						//回填专业工程消防审查合格书编号信息
						var zygcxfInfo = data.ZYGCXFSCHGSBH;
						if(zygcxfInfo!=null && zygcxfInfo!=""){
							var zygcxfArr = zygcxfInfo.split(",");
							for(var i=0;i<zygcxfArr.length;i++){
								var zygcxfValue = zygcxfArr[i];
								var idValue = "ZYGCXFID_" + i;
								var inputText = '<div id="zydiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="ZYGCXF_NO" value="'+(zygcxfValue)+'"/>';
								var asign='<a href="javascript:void(0);" onclick="delZygcxfNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
								$('#zygcxf').append(inputText+asign);
							}
						}
			        	FlowUtil.initFormFieldValue(data,"FC_PROJECT_BACK_FORM");
			        	$("input[name='PRJ_NUM']").val(data.PRJ_NUM);
			        }
				});
    			$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
    		}
    	}
		$('input[name=FC_CHARACTER]').on('click',function(){
			var isCheck =  $('#ifSpecialBuilding input[name=FC_CHARACTER]').is(':checked');
			if(isCheck){
				$("input[name='IFSPECIALBUILDING']").val(1);
			}else{
				$("input[name='IFSPECIALBUILDING']").val(0);
			}
		});
   	});
	function initForm(flowSubmitObj){
		$("select[name='ADMIN_DIVISION']").append("<option value='350128' selected='true'>平潭综合实验区</option>");
	}
	function FLOW_SubmitFun(flowSubmitObj){
		var validateResult =$("#FC_PROJECT_BACK_FORM").validationEngine("validate");
		if(validateResult){
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
			if(submitMaterFileJson||submitMaterFileJson==""){
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				//责任主体
				var zrztRows = $("#zrztxxBackGrid").datagrid("getData");
	    		var zrztJson = JSON.stringify(zrztRows.rows);
	    		$("input[name='ZRZTXX_JSON']").val(zrztJson);
	    		//单体建筑
	    		var dtjzRows = $("#dtjzwxxBackGrid").datagrid("getData");
	    		var dtjzJson = JSON.stringify(dtjzRows.rows);
	    		$("input[name='DTXX_JSON']").val(dtjzJson);
	    		//获取施工图审查合格证书编号值
	    		var sgtscText = $("[name='SGTSC_NO']");
	    		var sgtscValue = "";
	    		$.each(sgtscText,function () {
	    			var input = $(this);
	    			var id = input.attr("id");
	    			var idValue = document.getElementById(id).value;
	    			if(idValue!=""){
	    				sgtscValue += idValue+",";
	    			}
	    		});
	    		if(sgtscValue!=""){
	    			sgtscValue = sgtscValue.substring(0,sgtscValue.length-1);
	    			$("input[name='SGTSCHGZHBH']").val(sgtscValue);
	    		}
	    		//获取专业工程消防审查合格书编号值
	    		var zygcxfText = $("[name='ZYGCXF_NO']");
	    		var zygcxfValue = "";
	    		$.each(zygcxfText,function () {
	    			var input = $(this);
	    			var id = input.attr("id");
	    			var idValue = document.getElementById(id).value;
	    			if(idValue!=""){
	    				zygcxfValue += idValue+",";
	    			}
	    		});
	    		if(zygcxfValue!=""){
	    			zygcxfValue = zygcxfValue.substring(0,zygcxfValue.length-1);
	    			$("input[name='ZYGCXFSCHGSBH']").val(zygcxfValue);
	    		}
	    		//获取表单上的所有值
				var formData = FlowUtil.getFormEleData("FC_PROJECT_BACK_FORM");
				for(var index in formData){
					flowSubmitObj[eval("index")] = formData[index];
				}
				return flowSubmitObj;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 暂存流程
	 */
	function FLOW_TempSaveFun(flowSubmitObj) {
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
		$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		//先获取表单上的所有值
		var formData = FlowUtil.getFormEleData("FC_PROJECT_BACK_FORM");
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
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="FC_PROJECT_BACK_FORM" method="post">
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
		
		<input type="hidden" name="PRJ_NUM"/>
		<%--是否是特殊工程--%>
		<input type="hidden" name="IFSPECIALBUILDING"/>
		<input type="hidden" name="SGTSCHGZHBH"/>
		<input type="hidden" name="ZYGCXFSCHGSBH"/>
		<input type="hidden" name="ZRZTXX_JSON"/>
		<input type="hidden" name="DTXX_JSON"/>
		<input type="hidden" id="PRJ_NUM" name="PRJ_NUM" value="${busRecord.PRJ_NUM}">
		<input type="hidden" id="PRJ_CODE" name="PRJ_CODE" value="${busRecord.PROJECT_CODE}">
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
				<td id="EXEID" colspan="3"></td>
			</tr>
		</table>
		<%--开始引入消防设计基本信息部分 --%>
			<jsp:include page="./xfsj/xfsjjbxx.jsp" />
		<%--结束引入消防设计基本信息部分 --%>
		
		<%--开始引入使用性质单元部分 --%>
			<jsp:include page="./xfsj/syxzdy.jsp" />
		<%--结束引入使用性质单元部分 --%>
		
		<%--开始引入其他信息单元部分 --%>
			<jsp:include page="./xfsj/qtxxdy.jsp" />
		<%--结束引入其他信息单元部分 --%>
		
		<%--开始引入责任主体信息部分 --%>
			<jsp:include page="./xfsj/zrztxx.jsp" />
		<%--结束引入责任主体信息部分 --%>
		
		<%--开始引入单体建筑物信息部分 --%>
			<jsp:include page="./xfsj/dtjzwxx.jsp" />
		<%--结束引入单体建筑物信息部分 --%>


        <%--开始引入公用申报对象--%>
        <jsp:include page="./applyuserinfo.jsp" />
        <%--结束引入公用申报对象 --%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp" >
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

	</form>
</body>
</html>