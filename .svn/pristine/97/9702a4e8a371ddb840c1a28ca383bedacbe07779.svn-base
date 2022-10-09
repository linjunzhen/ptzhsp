<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String matterCode = request.getParameter("matterCode");
  	request.setAttribute("matterCode", matterCode);
  	String formjson = request.getParameter("formjson");
  	request.setAttribute("formjson", formjson);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog,json2"></eve:resources>
    
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/applyforms/annexStyle.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		AppUtil.initWindowForm("tabletForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#tabletForm").serialize();
				var jsonData=getFormJson();
				art.dialog.data("annexFormInfo",{
    				formData:jsonData
				});
				AppUtil.closeLayer();
			}
		},null);
		var jsonstr='<%=formjson%>';
		if(jsonstr!="undefined"){
			var jsonObj=JSON.parse(jsonstr);
			FlowUtil.initFormFieldValue(jsonObj,"tabletForm");
		}
	});
	//将form中的值转换为键值对
	function getFormJson() {
		var o = {};
		var a =$("#tabletForm").serializeArray();
		$.each(a, function () {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}
</script>
</head>
<body class="bsbodynew">
    <div class="container" style="width: 900px;padding-left: 0px;margin-left: 0px;">
        <form  method="post" id="tabletForm">
		<c:choose> 
			<%--1.开始引入政府投资项目规划选址初步对接表 --%>
			<c:when test="${matterCode=='345071904QT00201_03'}">
				<jsp:include page="./annexFirstJoint.jsp" />
			</c:when>
			<%--2.开始引入规划选址及用地对接表信息部分 --%>
			<c:when test="${matterCode=='345071904QT00202_02'}">
				<jsp:include page="./annexUseland.jsp" />
			</c:when>
			<%--3、4.《工程可行性研究报告+》（送审版）综合（专家）评审申请表、（修编版）联合审查申请表；对接表信息部分 --%>
			<c:when test="${matterCode=='345071904QT00204_03'||matterCode=='350128XK00111_01'}">
				<jsp:include page="./annexExpertSp.jsp" />
			</c:when>
			<c:when test="${matterCode=='34507190400204'}">
				<jsp:include page="./annexJointCheck.jsp" />
			</c:when>
			<%--5、6.项目《一阶段施工图》（送审版）审查申请表，项目《一阶段施工图》（修编版）联合审查申请表信息部分 --%>
			<c:when test="${matterCode=='345071904QT00205_02'}">
				<jsp:include page="./annexBuildPlanSS.jsp" />
			</c:when>
			<c:when test="${matterCode=='345071904QT02302_01'}">
				<jsp:include page="./annexBuildPlanJointCheck.jsp" />
			</c:when>
			<%--7、8.项目统一竣工验收申请表、项目统一竣工财务决算申请表 --%>
			<c:when test="${matterCode=='345071904QT00206_12'}">
				<jsp:include page="./annexCompleteAccept.jsp" />
			</c:when>
			<c:when test="${matterCode=='345071904QT01801_12'}">
				<jsp:include page="./annexFinanceFinalCost.jsp" />
			</c:when>
			
			<%--1.开始引入社会投资项目规划选址初步对接表 --%>
			<c:when test="${matterCode=='345071904QT02101_03'}">
				<jsp:include page="./shtzAnnexFirstJoint.jsp" />
			</c:when>
			<%--2.开始引入规划选址及用地对接表信息部分 --%>
			<c:when test="${matterCode=='345071904QT01401_02'}">
				<jsp:include page="./annexUseland.jsp" />
			</c:when>
			<%--3、4.《项目备案请示+》（送审版）专家综合评审申请表、《项目备案请示+》（修编版）联合审查申请表 --%>
			<c:when test="${matterCode=='350128XK00111_01'}">
				<jsp:include page="./annexExpertSp.jsp" />
			</c:when>
			<c:when test="${matterCode=='350128XK00111_13'}">
				<jsp:include page="./annexJointCheck.jsp" />
			</c:when>
			<%--5、6.《项目申请报告+》（送审版）综合（专家）评审申请表，《项目申请报告+》（修编版）联合审查申请表 --%>
			<c:when test="${matterCode=='350128XK00110_01'}">
				<jsp:include page="./annexExpertSp.jsp" />
			</c:when>
			<c:when test="${matterCode=='350128XK00110_13'}">
				<jsp:include page="./annexJointCheck.jsp" />
			</c:when>			
			<%--7、8.项目《施工图》（送审版）审查申请表，项目《施工图》（修编版）联合审查申请表信息部分 --%>
			<c:when test="${matterCode=='345071904QT01901_02'}">
				<jsp:include page="./annexBuildPlanSS.jsp" />
			</c:when>
			<c:when test="${matterCode=='345071904QT01901_06'}">
				<jsp:include page="./annexBuildPlanJointCheck.jsp" />
			</c:when>
			<%--9.项目统一竣工验收申请表 --%>
			<c:when test="${matterCode=='345071904QT02201_10'}">
				<jsp:include page="./annexCompleteAccept.jsp" />
			</c:when>
			 <c:otherwise>   
				${matterCode}
  			</c:otherwise> 
		</c:choose>
		
		
		<%--开始引入规划选址及用地对接表信息部分 
				<c:if test="${matterCode=='345071904QT00202_02'}">
						<jsp:include page="./annexUseland.jsp" />
	            </c:if> --%>
		<%--结束引入规划选址及用地对接表信息部分 --%>
		
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
        </form>   
    </div>
</body>
</html>
