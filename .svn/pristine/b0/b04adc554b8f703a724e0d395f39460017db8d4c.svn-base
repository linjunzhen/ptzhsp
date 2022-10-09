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
	<title>${resultMap.ReturnData.ProjectName}-${resultMap.ReturnData.BuildingID}-${resultMap.ReturnData.HouseID}-平潭综合实验区行政服务中心</title>
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
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > 户室信息 > <span>${xmxxMap.ReturnData.BaseInfo[0].ProjectName}</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
	
		<div class="rfloat in-r" style="width:100%">
			
			<div class="in-table-box">
				<h3>${xmxxMap.ReturnData.BaseInfo[0].ProjectName}-${resultMap.ReturnData.BuildingID}-${resultMap.ReturnData.HouseID}</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 15%;">开 发 商</th>
						<td style="width: 35%;">${xmxxMap.ReturnData.BaseInfo[0].DeveloperName}</td>
						<th style="width: 15%;">房屋坐落</th>
						<td style="width: 35%;">${resultMap.ReturnData.Location}</td>
					</tr>
					<tr>
						<th>项目名称</th>
						<td>${xmxxMap.ReturnData.BaseInfo[0].ProjectName}</td>
						<th>幢　　号</th>
						<td>${resultMap.ReturnData.BuildingID}</td>
					</tr>
					<tr>
						<th>房　　号</th>
						<td>${resultMap.ReturnData.HouseID}</td>
						<th>结　　构</th>
						<td>${resultMap.ReturnData.Structure}</td>
					</tr>
					<tr>
						<th>建筑面积</th>
						<td>${resultMap.ReturnData.BuildArea}</td>
						<th>套内面积</th>
						<td>${resultMap.ReturnData.UseArea}</td>
					</tr>
					<tr>
						<th>分摊面积</th>
						<td>${resultMap.ReturnData.ApportionArea}</td>
						<th>房屋用途</th>
						<td>${resultMap.ReturnData.Purpose}</td>
					</tr>
					<tr>
						<th>所 在 层</th>
						<td>${resultMap.ReturnData.OnFloor}</td>
						<th>总 层 数</th>
						<td>${resultMap.ReturnData.TotalFloor}</td>
					</tr>
					<tr>
						<th>销售单价</th>
						<td>${resultMap.ReturnData.ConsultUnitPrice}</td>
						<th>房屋户型</th>
						<td>${resultMap.ReturnData.HouseStyle}</td>
					</tr>
					<tr>
						<th>合 同 号</th>
						<td colspan="3">${resultMap.ReturnData.LicenceID}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>