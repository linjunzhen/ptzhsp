<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<title>不动产登记查询-平潭综合实验区行政服务中心</title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/aos.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.selectlist.js"></script>

</head>

<body>
	
	<!--头部-->
	<div class="eui-header"><iframe frameBorder="0" width="100%" height="100%"
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/head.jsp"></iframe></div>
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > <span>不动产登记查询</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="rfloat in-r" style="width:100%">
			
			<div class="in-table-box">
				<c:if test="${empty bdcMap.ReturnData}">
				</br></br></br><h3>该业务不存在！</h3></br></br></br>
				</c:if>
				<c:if test="${!empty bdcMap.ReturnData}">
				<h3>${bdcMap.ReturnData.FlowName}</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 15%;">业务宗号</th>
						<td style="width: 35%;">${bdcMap.ReturnData.CaseId}</td>
						<th style="width: 15%;">登记类别</th>
						<td style="width: 35%;">${bdcMap.ReturnData.RegTypeName}</td>
					</tr>
					<tr>
						<th>申请人</th>
						<td>${bdcMap.ReturnData.Applicant}</td>
						<th>房屋坐落</th>
						<td>${bdcMap.ReturnData.Location}</td>
					</tr>
					<tr>
						<th>当前环节</th>
						<td>${bdcMap.ReturnData.FlowStepName}</td>
						<th>收件人</th>
						<td>${bdcMap.ReturnData.MisUsername}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">${bdcMap.ReturnData.Remark}</td>
					</tr>
				</table>
				</c:if>
			</div>
		</div>
	</div>	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" marginHeight="0" 
	marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>