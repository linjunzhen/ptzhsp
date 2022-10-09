<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String noticePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<script type="text/javascript">
	var system ={};  
	var p = navigator.platform;       
	system.win = p.indexOf("Win") == 0;  
	system.mac = p.indexOf("Mac") == 0;  
	system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);     
	if(system.win||system.mac||system.xll){//电脑端
	
	}else{//如果是手机,跳转到
		window.location.href="<%=noticePath%>/mobilenotice.jsp";
	}
</script>

<input type="hidden" name="uploadUserId" value="${sessionScope.curLoginMember.USER_ID}" />
<input type="hidden" name="uploadUserName" value="${sessionScope.curLoginMember.YHMC}" /> 
<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" /> 
<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" /> 
<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
<input type="hidden" name="EFLOW_FLOWOBJ" value="${EFLOW_FLOWOBJ}" id="EFLOW_FLOWOBJ"/>
<input type="hidden" name="EFLOW_DEFKEY" value="${EFLOWOBJ.EFLOW_DEFKEY}" />
<input type="hidden" name="EFLOW_BUSTABLENAME" value="${EFLOWOBJ.EFLOW_BUSTABLENAME}" /> 
<input type="hidden" name="EFLOW_CUREXERUNNINGNODENAMES" value="${EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES}" /> 
<input type="hidden" name="EFLOW_CURUSEROPERNODENAME" value="${EFLOWOBJ.EFLOW_CURUSEROPERNODENAME}" /> 
<input type="hidden" name="EFLOW_DEFID" value="${EFLOWOBJ.EFLOW_DEFID}" />
<input type="hidden" name="EFLOW_DEFVERSION" value="${EFLOWOBJ.EFLOW_DEFVERSION}" /> 
<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
<!--面签字段-->
<c:if test="${requestParams.ISNEEDSIGN=='0'||requestParams.ISNEEDSIGN=='1'}">
<input type="hidden" name="ISNEEDSIGN"  value="${requestParams.ISNEEDSIGN}"/>
<input type="hidden" name="IS_ACCOUNT_OPEN_ITEM"  value="${requestParams.IS_ACCOUNT_OPEN}"/>
<input type="hidden" name="IS_PREAPPROVAL_PASS_ITEM"  value="${requestParams.IS_PREAPPROVAL_PASS}"/>
    <input type="hidden" name="ISSOCIALREGISTER"  value="${requestParams.ISSOCIALREGISTER}"/>
    <input type="hidden" name="ISMEDICALREGISTER"  value="${requestParams.ISMEDICALREGISTER}"/>
    <input type="hidden" name="ISFUNDSREGISTER"  value="${requestParams.ISFUNDSREGISTER}"/>
    <input type="hidden" name="ISGETBILL"  value="${requestParams.ISGETBILL}"/>
    <input type="hidden" name="ISFIRSTAUDIT"  value="${requestParams.ISFIRSTAUDIT}"/>
    <input type="hidden" name="ISEMAIL_ITEM"  value="${requestParams.ISEMAIL}"/>
    <input type="hidden" name="ISJHYYZZ"  value="${requestParams.ISJHYYZZ}"/>
    <input type="hidden" name="IS_ENGRAVE_SEAL"  value="${requestParams.IS_ENGRAVE_SEAL}"/>
    <input type="hidden" name="IS_FREE_ENGRAVE_SEAL"  value="${requestParams.IS_FREE_ENGRAVE_SEAL}"/>
    <!--秒批字段-->
    <input type="hidden" name="SSSBLX"  value="${requestParams.SSSBLX}"/>
</c:if>
<!--秒批字段-->
<c:if test="${!empty busRecord.SSSBLX}">
    <input type="hidden" name="SSSBLX"  value="${busRecord.SSSBLX}"/>
</c:if>
<c:if test="${!empty busRecord.ISGETBILL}">
    <input type="hidden" name="ISGETBILL"  value="${busRecord.ISGETBILL}"/>
</c:if>