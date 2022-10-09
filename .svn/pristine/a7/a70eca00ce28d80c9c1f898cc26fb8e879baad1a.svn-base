<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript">
	
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
		//获取流程信息对象JSON
		var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		if(EFLOW_FLOWOBJ){
			//将其转换成JSON对象
			var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
			//初始化表单字段值
    		if(eflowObj.busRecord){
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_ZFTZYDYX_FORM");
    		}
			if (eflowObj.busRecord) {
				changeHTIndustryS(eflowObj.busRecord)
				changeHTProjectDetailS(eflowObj.busRecord)
			}
		}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_ZFTZYDYX"
		//});
		document.getElementById("fjbdDiv").style.display="none";
	});

	function annexFormInit(){

	}

	function changeHTIndustryS(eobj) {
		var value = eobj.HTINDUSTRY;
		$.post("webSiteController/getHTProject.do",{industry:value},function (data) {
			var data = JSON.parse(data);
			var str = '';
			for (var i = 0; i < data.length; i++) {
				if	(data[i].PROJECT_NAME == eobj.HTPROJECT) {
					str += '<option selected="selected" value="'+data[i].PROJECT_NAME+'">'+data[i].PROJECT_NAME+'</option>'
				} else {
					str += '<option value="'+data[i].PROJECT_NAME+'">'+data[i].PROJECT_NAME+'</option>'
				}
			}
			$("#HTPROJECT").html(str);
		})
	}

	function changeHTProjectDetailS(eobj){
		var value = eobj.HTPROJECT;
		var HTPROJECT_DETAIL = eobj.HTPROJECT_DETAIL
		$.post("webSiteController/getHTProjectDetail.do",{project:value},function (data) {
			var data = JSON.parse(data);
			var str = "";
			if(data.REPORT != null) {
				if (HTPROJECT_DETAIL.indexOf(data.REPORT) != -1) {
					str += '<option selected="selected" value="'+data.REPORT+'">'+data.REPORT+'</option>';
				} else {
					str += '<option value="'+data.REPORT+'">'+data.REPORT+'</option>';
				}
			}
			if(data.REPORT_FORM != null) {
				if (HTPROJECT_DETAIL.indexOf(data.REPORT_FORM) != -1) {
					str += '<option selected="selected" value="'+data.REPORT_FORM+'">'+data.REPORT_FORM+'</option>';
				} else {
					str += '<option value="'+data.REPORT_FORM+'">'+data.REPORT_FORM+'</option>';
				}
			}
			if(data.REGISTRAT_FORM != null) {
				if (HTPROJECT_DETAIL.indexOf(data.REGISTRAT_FORM) != -1) {
					str += '<option selected="selected" value="'+data.REGISTRAT_FORM+'">'+data.REGISTRAT_FORM+'</option>';
				} else {
					str += '<option value="'+data.REGISTRAT_FORM+'">'+data.REGISTRAT_FORM+'</option>';
				}
			}
			$("#HTPROJECT_DETAILS").html(str)
		})
	}

	function submitFlow() {
		var i='${itemCode}'
		if (i == '569262478QS00203') {
			var HTPROJECT_DETAILS = $("#HTPROJECT_DETAILS").val();
			if (HTPROJECT_DETAILS) {
				$("#HTPROJECT_DETAIL").val(HTPROJECT_DETAILS.join('|'));
			} else {
				alert("请选择行业类别和项目详情");
			}
		}
		AppUtil.submitWebSiteFlowForm('T_BSFW_ZFTZYDYX_FORM');
	}

	function tempSaveFlow() {
		var i='${itemCode}'
		if (i == '569262478QS00203') {
			var HTPROJECT_DETAILS = $("#HTPROJECT_DETAILS").val();
			if (HTPROJECT_DETAILS) {
				$("#HTPROJECT_DETAIL").val(HTPROJECT_DETAILS.join('|'));
			}
		}
		AppUtil.tempSaveWebSiteFlowForm('T_BSFW_ZFTZYDYX_FORM');
	}
	
</script>
</head>


<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=wssb" />

	<div class="eui-main">
    	<jsp:include page="./formtitle.jsp" />
        
        <form id="T_BSFW_ZFTZYDYX_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
		    <%--开始引入公共隐藏域部分 --%>
			<jsp:include page="./commonhidden.jsp" />
		    <%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx.jsp" />
		<%--结束引入基本信息部分 --%>
		
		<%--开始引入规划选址及用地对接表信息部分 --%>
			<!--<c:forEach items="${applyMaters}" var="appmatter" varStatus="varStatus">
				<c:if test="${appmatter.MATER_CODE=='345071904QT00202_02'&&appmatter.SUPPORT_WORD=='1'}">
						<jsp:include page="./annexUseland.jsp" />
	            </c:if>
			</c:forEach> -->
			<jsp:include page="./annexUseland.jsp" />
		<%--结束引入规划选址及用地对接表信息部分 --%>
		
		<div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_ZFTZYDYX_FORM" name="formName"/>
            </jsp:include>
        </div>
        
        <jsp:include page="./applyuserinfo.jsp" />
        
        </form>
        
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="tempSaveFlow();" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
        <c:if test="${EFLOWOBJ.HJMC!=null}">
        <jsp:include page="xmlct.jsp" >
           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
        </jsp:include>
        </c:if>
        <%-- 结束引入阶段流程图 并且当前节点不在开始或结束节点--%>
        
        <%--开始引入回执界面 --%>
		<jsp:include page="./hzxx.jsp">
			<jsp:param value="${EFLOWOBJ.EFLOW_EXEID}" name="exeId" />
		</jsp:include>
		<%--结束引入回执界面 --%>
        
    </div>
    
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>