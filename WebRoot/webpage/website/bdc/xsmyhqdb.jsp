<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<title>预售公告信息-平潭综合实验区行政服务中心</title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/aos.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.selectlist.js"></script>	
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
</head>

<body>
	
	<!--头部-->
	<div class="eui-header"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/head.jsp"></iframe></div>
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > <span>预售公示信息</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="lfloat in-l">			
			<jsp:include page="/webpage/website/bdc/leftList.jsp?currentNav=8" />
		</div>
		<div class="rfloat in-r">
			<form id="spzzddphForm" method="post" action="bdcQueryController/spzzddph.do">
				<input class="kslp-input num" type="hidden" name="PageSize" value="${variables.PageSize}" >
				<input class="kslp-input num" type="hidden" name="PageNumber" value="${variables.PageNumber}" >
			</form>	
			<div class="in-table-box">
				<table class="index-table" border="0" cellspacing="0" cellpadding="0">
					<tr>						
						<th>序号</th>
						<th>月份</th>
						<th>销售套数</th>
						<th>销售面积(㎡)</th>
						<th>销售单价</th>
						<th>比增(%)</th>
						<th>态势</th>
					</tr>
					<c:forEach items="${resultMap.Items}" var="item" varStatus="s">
					<tr>
						<td>${(resultMap.PagedData.PageNumber-1)*resultMap.PagedData.PageSize+s.index+1}</td>
						<td>${item.Months}</td>
						<td>${item.CaseCount}</td>
						<td>${item.BuildArea}</td>
						<td>${item.UnitPrice}</td>
						<td>${item.HBZJ}</td>
						<td>
							<c:if test="${item.HBZJ>=0}"><img src="<%=path%>/webpage/website/bdc/images/up.gif" style="margin: auto;"/></c:if>
							<c:if test="${item.HBZJ<0}"><img src="<%=path%>/webpage/website/bdc/images/down.gif" style="margin: auto;"/></c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
						
			</div>
		</div>
	</div>	
	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" marginHeight="0" 
	marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>