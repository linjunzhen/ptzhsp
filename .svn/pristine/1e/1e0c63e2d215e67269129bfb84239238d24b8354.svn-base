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
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
 <script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script> 
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript">
	
</script>
</head>

<body>
   <form id="T_FLOW_RESULT_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <table cellpadding='0' cellspacing='1' class='tab_tk_pro'  >
    	<tr>
    		<td class='tab_width1' width='80px' align='center'>操作人员</td>
    		<td class='tab_width1' width='80px' align='center'>操作时间</td>
    		<td class='tab_width1' width='480px' align='center'>维护内容</td>
    	</tr>
    	<c:forEach items="${busLogList}" var="busLogList" varStatus="s">
    	<c:if  test="${fn:substring(OPERATETIME,0,17)==fn:substring(busLogList.OPERATE_TIME,0,17)}">
    		<tr>
    			<td class='tab_width1' width='80px' align='center'>${busLogList.FULLNAME}</td>
    			<td class='tab_width1' width='80px' align='center'>${busLogList.OPERATE_TIME}</td>
    			<td class='tab_width1' width='480px' >${busLogList.OPERATE_CONTENT}</td>
    		</tr>
       </c:if>
    	</c:forEach>
    </table>
    
	</form>
</body>
</html>
