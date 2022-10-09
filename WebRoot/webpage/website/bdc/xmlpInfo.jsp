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
	<title>${lpxxInfoMap.BuildingID}-${lpxxInfoMap.ProjectName}-平潭综合实验区行政服务中心</title>
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
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > 项目楼盘信息  > <span>${lpxxInfoMap.ProjectName}-${lpxxInfoMap.BuildingID}</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="rfloat in-r" style="width:100%">
			
			<div class="in-table-box">
				<h3>${lpxxInfoMap.ProjectName}-${lpxxInfoMap.BuildingID}</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 9%;">开 发 商</th>
						<td colspan="4">${lpxxInfoMap.DeveloperName}</td>
						<th colspan="2" >项目名称</th>
						<td colspan="4">${lpxxInfoMap.ProjectName}</td>
					</tr>
					<tr>
						<th>项目坐落</th>
						<td colspan="4">${lpxxInfoMap.Location}</td>
						<th colspan="2">幢号</th>
						<td colspan="4">${lpxxInfoMap.BuildingID}</td>
					</tr>
					<tr>
						<th>建筑结构</th>
						<td colspan="4">${lpxxInfoMap.Structure}</td>
						<th colspan="2">设计用途</th>
						<td colspan="4">${lpxxInfoMap.Purpose}</td>
					</tr>
					<tr>
						<th>总层数</th>
						<td colspan="4">${lpxxInfoMap.TotalFloor}</td>
						<th colspan="2">总面积</th>
						<td colspan="4">${lpxxInfoMap.BuildArea}</td>
					</tr>
					<tr>
						<th>开工日期</th>
						<td colspan="4">${lpxxInfoMap.BuildDate}</td>
						<th colspan="2">竣工日期</th>
						<td colspan="4">${lpxxInfoMap.FinishDate}</td>
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
						<td style="text-align: center;">${lpxxInfoMap.HouseCanSellCount + lpxxInfoMap.HouseSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.HouseCanSellArea + lpxxInfoMap.HouseSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceCanSellCount + lpxxInfoMap.MarketplaceSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceCanSellArea + lpxxInfoMap.MarketplaceSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeCanSellCount  + lpxxInfoMap.OfficeSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeCanSellArea  + lpxxInfoMap.OfficeSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnCanSellCount  + lpxxInfoMap.CarbarnSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnCanSellArea  + lpxxInfoMap.CarbarnSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherCanSellCount  + lpxxInfoMap.OtherSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherCanSellArea  + lpxxInfoMap.OtherSellArea}</td>
					</tr>
					<tr>
						<th style="text-align: center;">可售统计</th>
						<td style="text-align: center;">${lpxxInfoMap.HouseCanSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.HouseCanSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceCanSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceCanSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeCanSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeCanSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnCanSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnCanSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherCanSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherCanSellArea}</td>
					</tr>
					<tr>
						<th>已售统计</th>
						<td style="text-align: center;">${lpxxInfoMap.HouseSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.HouseSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.MarketplaceSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OfficeSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.CarbarnSellArea}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherSellCount}</td>
						<td style="text-align: center;">${lpxxInfoMap.OtherSellArea}</td>
					</tr>
					<tr>
						<th>特别声明</th>
						<td colspan="10" class="tbsm">由于我中心对于开发项目进行楼盘管理的时间为2014年，故此日期之前获得预售许可的项目信息与真实的销售情况存在误差，望谅解。</td>
					</tr>
					<tr>
						<th colspan="2">点击<span>幢号</span>查看详细信息</th>
						<td colspan="9">
							<c:forEach items="${xmxxMap.ReturnData.Buildings}" var="lpxx" varStatus="s">
								<c:if test="${lpxx.BuildingInfoID!=variables.BuildingInfoID}">
								<a href="<%=path%>/bdcQueryController/buildings.do?BuildingInfoID=${lpxx.BuildingInfoID}&CaseID=${CaseID}&ProjectID=${ProjectID}">
									<span>${lpxx.BuildingID}</span>
								</a>
								</c:if>
								<c:if test="${lpxx.BuildingInfoID==variables.BuildingInfoID}">
									${lpxx.BuildingID}
								</c:if>
							</c:forEach>
							<c:forEach items="${xmxxMap.ReturnData.ProjectChange}" var="lpxx" varStatus="s">
								<c:if test="${lpxx.BuildingInfoID!=variables.BuildingInfoID}">
								<a href="<%=path%>/bdcQueryController/buildings.do?BuildingInfoID=${lpxx.BuildingInfoID}&CaseID=${CaseID}&ProjectID=${ProjectID}">
									<span>${lpxx.BuildingID}</span>
								</a>
								</c:if>
								<c:if test="${lpxx.BuildingInfoID==variables.BuildingInfoID}">
									${lpxx.BuildingID}
								</c:if>
							</c:forEach>
                        </td>
					</tr>
				</table>
				
				<table class="sty-table" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="sty-1"><b>已限制</b>开发商被查封的房产</td>
						<td class="sty-2"><b>已抵押</b>被开发商抵押的房产</td>
						<td class="sty-3"><b>已签约</b>已网签且未备案的房产</td>
						<td class="sty-4"><b>已备案</b>合同已备案的房产</td>
						<td class="sty-5"><b>可销售</b>正常可销售的房产</td>
						<td class="sty-6"><b>不可售</b>拆迁、集资、配套用房</td>
					</tr>
				</table>
				
				<table class="index-table" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="150" rowspan="2">楼层/单元</th>
						<c:forEach items="${unitList}" var="item" varStatus="s">
						<th colspan="${item.UnitID}">${item.Unit}（单元）</th>
						</c:forEach>
					</tr>
					<tr>
						<c:forEach items="${unitList}" var="item" varStatus="vs">
						<c:forEach var="s"  begin="1" end="${item.UnitID}">	
							<th>${s}</th>
						</c:forEach>
						</c:forEach>
					</tr>
					<c:forEach items="${houseList}" var="item" varStatus="s">
						<c:forEach items="${item}" var="item1" varStatus="s1">	
					<tr>	
							<c:forEach items="${item1.list}" var="house" varStatus="s2">
								<c:if test="${house.HouseID==null||house.HouseID==''}">
									<td>${house.OnFloor}</td>
								</c:if>
								<c:if test="${house.HouseID!=null&&house.HouseID!=''}">	
								<c:if test="${house.isShow!=0}">
										<td <c:if test="${house.floorNum!=null && house.floorNum!=''}">	rowspan="${house.floorNum}"</c:if> 
											<c:choose>
											   <c:when test="${house.HouseProperty == '商品房' && house.IsCongeal == true && house.IsReg == false}">  
													 class="sty-1"      
											   </c:when>
											   <c:when test="${house.HouseProperty == '商品房' && house.IsReg == false &&  house.IsMortgage == true}">  
													 class="sty-2"      
											   </c:when>
											   <c:when test="${house.HouseProperty == '商品房' && house.IsReg == true && house.IsApprove == false}">  
													 class="sty-3"      
											   </c:when>
											   <c:when test="${house.HouseProperty == '商品房' && house.IsReg == true}">  
													 class="sty-4"      
											   </c:when>
											   <c:when test="${house.HouseProperty == '商品房' && house.IsReg == false && house.IsMortgage == false 
														&& house.IsPreSell == true && house.IsCongeal == false}">  
													 class="sty-5"      
											   </c:when>
											   <c:otherwise> 
												class="sty-6"
											   </c:otherwise>
											</c:choose> >
<!-- 											<a href="bdcQueryController/houseInfo.do?HouseCenterID=${house.HouseCenterID}&ProjectID=${lpxxInfoMap.ProjectID}">${house.HouseID}</a> -->
										</td>							
									</c:if>							
								</c:if>
							</c:forEach>
							<c:forEach var="s"  begin="${fn:length(item1.list)}" end="${totalUnit}">	
								<td ></td>
							</c:forEach>
					</tr>
						</c:forEach>
					</c:forEach>
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