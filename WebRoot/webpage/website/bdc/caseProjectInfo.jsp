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
	<title>${xmxxMap.ReturnData.ProjectInfo.ProjectName}-平潭综合实验区行政服务中心</title>
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
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > 项目预售信息 > <span>${xmxxMap.ReturnData.ProjectInfo.ProjectName}</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
	
		<div class="rfloat in-r"  style="width:100%">
			
			<div class="in-table-box">
				<h3>${xmxxMap.ReturnData.ProjectInfo.ProjectName}</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 9%;">公司名称</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.DeveloperName}</td>
						<th colspan="2">项目名称</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.ProjectName}</td>
					</tr>
					<tr>
						<th>预售许可证</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.LicenceID}</td>
						<th colspan="2">售楼电话</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Tel}</td>
					</tr>
					<tr>
						<th>项目坐落</th>
						<td colspan="10">${xmxxMap.ReturnData.ProjectInfo.Location}</td>
					</tr>
					<tr>
						<th>规划用途</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Purpose}</td>
						<th colspan="2">土地来源</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.LandSource}</td>
					</tr>
					<tr>
						<th>土地面积</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.LandArea}</td>
						<th colspan="2">土地级别</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.LandLevel}</td>
					</tr>
					<tr>
						<th>占地面积</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.ImpropriateArea}</td>
						<th colspan="2">建筑面积</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.BuildArea}</td>
					</tr>
					<tr>
						<th>容积率</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Cubage}</td>
						<th colspan="2">绿地率</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Greenbelt}</td>
					</tr>
					<tr>
						<th>建筑密度</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.BuildDensity}</td>
						<th colspan="2">工程投资</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Investment}</td>
					</tr>
					<tr>
						<th>开工日期</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.BeginDate}</td>
						<th colspan="2">竣工日期</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.EndDate}</td>
					</tr>
					<tr>
						<th>施工单位</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Builder}</td>
						<th colspan="2">测绘单位</th>
						<td colspan="4">${xmxxMap.ReturnData.ProjectInfo.Mappinger}</td>
					</tr>
					<tr>
						<th rowspan="2">房屋用途</th>
						<th colspan="2">住宅</th>
						<th colspan="2">商业</th>
						<th colspan="2">写字楼</th>
						<th colspan="2">车库车位</th>
						<th colspan="2">其他</th>
					</tr>
					<tr>
						<th style="width: 9%;">套数</th>
						<th style="width: 9%;">面积</th>
						<th style="width: 9%;">间数</th>
						<th style="width: 9%;">面积</th>
						<th style="width: 9%;">间数</th>
						<th style="width: 9%;">面积</th>
						<th style="width: 9%;">位数</th>
						<th style="width: 9%;">面积</th>
						<th style="width: 9%;">套数</th>
						<th style="width: 9%;">面积</th>
					</tr>
					<tr>
						<th>批准销售</th>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseCanSellCount + xmxxMap.ReturnData.PurposeStat.HouseSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseCanSellArea + xmxxMap.ReturnData.PurposeStat.HouseSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceCanSellCount + xmxxMap.ReturnData.PurposeStat.MarketplaceSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceCanSellArea + xmxxMap.ReturnData.PurposeStat.MarketplaceSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeCanSellCount  + xmxxMap.ReturnData.PurposeStat.OfficeSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeCanSellArea  + xmxxMap.ReturnData.PurposeStat.OfficeSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnCanSellCount  + xmxxMap.ReturnData.PurposeStat.CarbarnSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnCanSellArea  + xmxxMap.ReturnData.PurposeStat.CarbarnSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherCanSellCount  + xmxxMap.ReturnData.PurposeStat.OtherSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherCanSellArea  + xmxxMap.ReturnData.PurposeStat.OtherSellArea}</td>
					</tr>
					<tr>
						<th style="text-align: center;">可售统计</th>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseCanSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseCanSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceCanSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceCanSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeCanSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeCanSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnCanSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnCanSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherCanSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherCanSellArea}</td>
					</tr>
					<tr>
						<th>已售统计</th>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.HouseSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.MarketplaceSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OfficeSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.CarbarnSellArea}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherSellCount}</td>
						<td style="text-align: center;">${xmxxMap.ReturnData.PurposeStat.OtherSellArea}</td>
					</tr>
					<tr>
						<th colspan="2">点击<span>幢号</span>查看详细信息</th>
						<td colspan="9">
							<c:forEach items="${xmxxMap.ReturnData.Buildings}" var="lpxx" varStatus="s">
                        	<a href="<%=path%>/bdcQueryController/buildings.do?BuildingInfoID=${lpxx.BuildingInfoID}&CaseID=${xmxxMap.ReturnData.ProjectInfo.CaseId}" ><span>${lpxx.BuildingID}</span></a>
							</c:forEach>
                        </td>
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