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
    <c:forEach items="${flowResult}" var="flowResult" varStatus="s">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">挂起说明</th>
		</tr>
		<tr>
				<td><span style="width: 110px;float:right;text-align:right;">挂起时间
				<font class="dddl_platform_html_requiredFlag">*</font>：</span>
				</td><td>
					<p type="text" id="begin_time" name="BEGIN_TIME" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
					readonly="true" maxlength="60" style="width:160px;" >${flowResult.BEGIN_TIME}</p>
				</td>
				<td><span id="closespan1" style="width: 110px;float:right;text-align:right;" >启动时间
				<font class="dddl_platform_html_requiredFlag">*</font>：</span>
				</td><td><span id="closespan2">
					<p type="text" id="end_time" name="END_TIME" 
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
					readonly="true" style="width:160px;" >${flowResult.END_TIME}</p></span>
				</td>
		</tr>
		<tr> 
			<td ><span style="width: 110px;float:right;text-align:right;">挂起说明
			<font class="dddl_platform_html_requiredFlag">*</font>：
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"
					class="eve-textarea validate[required,maxSize[500]]" readonly="true" 
					maxlength="500" style="width: 500px" name="EXPLAIN" id="EXPLAIN">${flowResult.EXPLAIN}</textarea>
			</td>
		</tr>
		<tr>
			<td ><span style="width: 110px;float:right;text-align:right;">附件
			<font class="dddl_platform_html_requiredFlag">*</font>：
			     </span>
			</td>
			<td colspan="3">
				<div style="width:100%;" >${flowResult.FILES}</div>
			</td>
		</tr>
	</table>
	</c:forEach>
	</form>
</body>
</html>
