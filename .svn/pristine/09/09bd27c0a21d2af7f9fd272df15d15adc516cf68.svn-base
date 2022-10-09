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
    <table cellpadding='0' cellspacing='1' class='tab_tk_pro'  id='asdfasdfasdfasdfasf'>
    	<tr>
    		<td class='tab_width1' width='80px' align='center'>挂起时间</td>
    		<td class='tab_width1' width='80px' align='center'>启动时间</td>
    		<td class='tab_width1' width='80px' align='center'>挂起说明</td>
    		<td class='tab_width1' width='80px' align='center'>附件</td>
    		<td class='tab_width1' width='80px' align='center'>挂起环节</td>
    		<td class='tab_width1' width='120px' align='center'>挂起截止时间</td>
    		<td class='tab_width1' width='80px' align='center'>是否超时</td>
    	</tr>
    	<c:forEach items="${flowResult}" var="flowResult" varStatus="s">
    		<tr>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.BEGIN_TIME}</td>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.END_TIME}</td>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.EXPLAIN}</td>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.FILES}</td>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.LINK_NAME}</td>
    			<td class='tab_width1' width='120px' align='center'>${flowResult.LINK_END_TIME}</td>
    			<td class='tab_width1' width='80px' align='center'>${flowResult.OVERTIME}</td>
    		</tr>
    	</c:forEach>
    </table>
    
	</form>
</body>
</html>
