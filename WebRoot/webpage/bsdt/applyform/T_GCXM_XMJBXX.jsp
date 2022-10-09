<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/main/css/style1.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/syj-style.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/gcjsxm/xmjbxx/js/xmjbxx.js"></script>

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
					$('#T_GCXM_XMJBXX_FORM').find('input,textarea').prop("readonly", true);
					$('#T_GCXM_XMJBXX_FORM').find('input,textarea').prop("disabled", "disabled");
					$('#T_GCXM_XMJBXX_FORM').find('select').prop("disabled", "disabled");
					$('#T_GCXM_XMJBXX_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
					$('#T_GCXM_XMJBXX_FORM').find(".laydate-icon").attr('disabled',"disabled");	
					$("#loadData").remove();
				}
				
				var typeCode = flowSubmitObj.busRecord.INDUSTRY;
				$.post( "dicTypeController/info.do",{
					typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
				function(responseText1, status, xhr) {									
					var resultJson1 = $.parseJSON(responseText1);
					if(null!=resultJson1){	
						$("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
					}else{											
						$("#industry").html('<option value="'+typeCode+'" selected="selected">'+typeCode+'</option>')
					}
				});
				var typeCode2=flowSubmitObj.busRecord.PLACE_CODE;
				$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
					function(responseText2,status,xhr){
						var  resultJson2=$.parseJSON(responseText2);
						if(null!=resultJson2){
							$("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
						}else{											
							$("#placeCode").html('<option value="'+typeCode2+'" selected="selected">'+typeCode2+'</option>')
						}
				});			
				var typeCode3 = flowSubmitObj.busRecord.INDUSTRY_STRUCTURE;
				$.post( "dicTypeController/info.do",{
					typeCode:typeCode3},
					function(responseText3, status, xhr) {									
						var resultJson3 = $.parseJSON(responseText3);
						if(null!=resultJson3){	
							$("#industryStructure").html('<option value="'+resultJson3.TYPE_CODE+'" selected="selected">'+resultJson3.TYPE_NAME+'</option>')
						} else{											
							$("#industryStructure").html('<option value="'+typeCode3+'" selected="selected">'+typeCode3+'</option>')
						}
					});
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
				} else{
					$("[name='PROJECT_CODE']").prop("readonly", false);
					$("[name='PROJECT_CODE']").prop("disabled", "");	
					//loadTZXMXXData();
				}
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_GCXM_XMJBXX_FORM");
				//获取表单字段权限控制信息
				var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
				 //初始化表单字段权限控制
				FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_GCXM_XMJBXX_FORM");
				
			}else{
				$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
			}
		}

	});
</script>
</head>

<body class="eui-diabody" >
    <div class="detail_title"  style="width:90%;margin: auto;">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
<div class="eui-slidebox" style="width:90%">
   <form id="T_GCXM_XMJBXX_FORM" method="post">
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
	<input type="hidden" id="STAGE_ID" name="STAGE_ID" value="${STAGE_ID}"/>
	<c:if test="${!empty STAGE_ID}">
		<e:obj filterid="${STAGE_ID}" var="eobj" dsid="1003">
		<input type="hidden" id="TYPE_ID" name="TYPE_ID" value="${eobj.TYPE_ID}"/>
		</e:obj>
	</c:if>
	<c:if test="${!empty busRecord.STAGE_ID}">
		<e:obj filterid="${busRecord.STAGE_ID}" var="eobj" dsid="1003">
		<input type="hidden" id="TYPE_ID" name="TYPE_ID" value="${eobj.TYPE_ID}"/>
		</e:obj>
	</c:if>
    <%--===================重要的隐藏域内容=========== --%>
	
	<%--开始引入事项基本信息--%>
	<jsp:include page="./gcjsxm/itemInfo.jsp" />
	<%--结束引入事项基本信息 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./gcjsxm/applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>	
	
	<%--开始引入项目基本信息页面 --%>
	<jsp:include page="./gcjsxm/xmjbxx/info.jsp" />
	<%--结束引入项目基本信息页面 --%>
	
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./gcjsxm/applyMaterList.jsp" >
		<jsp:param value="T_GCXM_XMJBXX_FORM" name="formName"/>
		<jsp:param value="2" name="isWebsite" />
    </jsp:include>	
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用事项列表界面 --%>
	<jsp:include page="./gcjsxm/applyItemList.jsp" >
		<jsp:param value="2" name="isWebsite" />
    </jsp:include>	
	<%--结束引入公用事项列表界面 --%>
	
	
	</form>
</div>
</body>
</html>
