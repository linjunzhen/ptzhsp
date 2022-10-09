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
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
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
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_DZZZJBXX_FORM");
    		}
		}
	});
	
	function submitFlow() {
		AppUtil.submitWebSiteFlowForm('T_BSFW_DZZZJBXX_FORM');
	}

	function tempSaveFlow() {
		AppUtil.tempSaveWebSiteFlowForm('T_BSFW_DZZZJBXX_FORM');
	}
	
</script>
</head>


<body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp" />
    <%--结束编写头部文件 --%>
    <div class="container">
    	<jsp:include page="./formtitle.jsp" />
        
        <form id="T_BSFW_DZZZJBXX_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
		    <%--开始引入公共隐藏域部分 --%>
			<jsp:include page="./commonhidden.jsp" />
		    <%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx.jsp" />
		<%--结束引入基本信息部分 --%>
		
		<%--开始编写证照信息 --%>
        <div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" ><span>证照信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
				<table cellpadding="0" cellspacing="0" class="bstable1">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>持证人:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CZR"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>持证主体:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="CZZT" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>证照编号:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="ZZBH"/>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>标题:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="BT"/>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>发证时间:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="ISSUEDDATE" value="${busRecord.ISSUEDDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
						</td>
			 			<td class="tab_width"><font class="tab_color">*</font>有效时间：</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="EFFECTIVEDATE" value="${busRecord.EFFECTIVEDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>过期时间:</td>
						<td>
						  <input type="text" class="tab_text validate[required]" name="EXPIRINGDATE" value="${busRecord.EXPIRINGDATE}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" />
						</td>
					</tr>
				</table>
            </div>
        </div>
		
		<div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_DZZZJBXX_FORM" name="formName"/>
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
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>