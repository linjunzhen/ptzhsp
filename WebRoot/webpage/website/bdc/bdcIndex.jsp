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
	<title></title>
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

<body style="background: none;">

		<div class="index-box index-r" data-aos="fade-up" data-aos-delay="200">
			<div class="clearfix">
				<div class="lfloat index-jrtj">
					<div class="index-title">
						<i class="jrtj"></i>今日统计信息
					</div>
					<table class="index-table" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th>上月签约</th>
							<th>住宅</th>
							<th>住宅面积</th>
							<th>其他面积</th>
						</tr>
						<tr>
							<td>${todayStat.ReturnData.UltimoCount+0}套</td>
							<td>${todayStat.ReturnData.UltimoHouseCount+0}套</td>
							<td>${todayStat.ReturnData.UltimoHouseArea+0}万㎡</td>
							<td>${todayStat.ReturnData.UltimoOtherArea+0}万㎡</td>
						</tr>
					</table>
					<table class="index-table" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th>本月签约</th>
							<th>住宅</th>
							<th>住宅面积</th>
							<th>其他面积</th>
						</tr>
						<tr>
							<td>${todayStat.ReturnData.MonthCount+0}套</td>
							<td>${todayStat.ReturnData.MonthHouseCount+0}套</td>
							<td>${todayStat.ReturnData.MonthHouseArea+0}万㎡</td>
							<td>${todayStat.ReturnData.MonthOtherArea+0}万㎡</td>
						</tr>
					</table>
					<table class="index-table" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th>昨日签约</th>
							<td>${todayStat.ReturnData.YesterdayCount+0}套</td>
							<th>面积</th>
							<td>${todayStat.ReturnData.YesterdayArea+0}万㎡</td>
						</tr>
						<tr>
							<th>今日签约</th>
							<td>${todayStat.ReturnData.TodayCount+0}套</td>
							<th>面积</th>
							<td>${todayStat.ReturnData.TodayArea+0}万㎡</td>
						</tr>
						<tr>
							<th>住宅</th>
							<td>${todayStat.ReturnData.TodayHouseCount+0}套</td>
							<th>面积</th>
							<td>${todayStat.ReturnData.TodayHouseArea+0}万㎡</td>
						</tr>
					</table>
				</div>
				<div class="rfloat index-kslp">
					<div class="index-title">
						<i class="search"></i>可售楼盘查询
					</div>
					<div class="kslp-search hover-opacity">
						<form id="kslpcxForm" method="post" target="_blank" action="bdcQueryController/kslpcx.do">
							<input class="kslp-input num" type="hidden" name="PageSize" value="20" >
							<input class="kslp-input num" type="hidden" name="PageNumber" value="1" >
							<p><input class="kslp-input info" type="text" placeholder="请输入项目名称" name="ProjectName" maxlength="32"></p>
							<p><input class="kslp-input info" type="text" placeholder="请输入项目坐标点"  name="Location" maxlength="32"></p>
							<p>
								项目用途
								<select class="kslp-select" id="Purpose" name="Purpose">
									<option value="">所有用途</option>
									<option value="住宅">住宅</option>
									<option value="商业用房">商业用房</option>
									<option value="办公用房">办公用房</option>
									<option value="车库车位">车库车位</option>
								</select>
							</p>
							<p>
								面积
								<input class="kslp-input num" type="text" name="AreaForm"  maxlength="16"  >
								-
								<input class="kslp-input num" type="text" name="AreaTO"  maxlength="16" >
								参考均价
								<input class="kslp-input num" type="text" name="PriceForm"  maxlength="16">
								-
								<input class="kslp-input num" type="text" name="PriceTo"  maxlength="16">
							</p>
							<p><a class="index-btn" href="javascript:void(0);" onclick="$('#kslpcxForm').submit();">查 询</a></p>
						</form>
					</div>
				</div>
			</div>
			<div class="index-title">
				<span><a href="bdcQueryController/kslptjList.do" target="_blank">更多</a></span>
				<i class="lptj"></i><a href="bdcQueryController/kslptjList.do" target="_blank">可售楼盘统计</a>
			</div>
			<table class="index-table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th rowspan="2">地区</th>
					<th colspan="2">楼盘数</th>
					<th colspan="2">可售套数</th>
					<th colspan="2">可售面积（万㎡）</th>
					<th colspan="2">当日成交</th>
					<th colspan="2">成交面积（万㎡）</th>
				</tr>
				<tr>
					<th>总数</th>
					<th>住宅</th>
					<th>总数</th>
					<th>住宅</th>
					<th>总数</th>
					<th>住宅</th>
					<th>总数</th>
					<th>住宅</th>
					<th>总数</th>
					<th>住宅</th>
				</tr>
				<tr>
					<td><a href="bdcQueryController/kslptjList.do" target="_blank">全区</a></td>
					<td>${CanSaleStat.ReturnData.CanSellProjectCount}</td>
					<td>${CanSaleStat.ReturnData.CanSellHouseProjectCount}</td>
					<td>${CanSaleStat.ReturnData.CanSellCount}</td>
					<td>${CanSaleStat.ReturnData.CanSellHouseCount}</td>
					<td>${CanSaleStat.ReturnData.CanSellArea}</td>
					<td>${CanSaleStat.ReturnData.CanSellHouseArea}</td>
					<td>${CanSaleStat.ReturnData.CaseCount}</td>
					<td>${CanSaleStat.ReturnData.HouseCaseCount}</td>
					<td>${CanSaleStat.ReturnData.BuildArea}</td>
					<td>${CanSaleStat.ReturnData.HouseBuildArea}</td>
				</tr>
			</table>
			<div class="index-title">
				<span><a href="bdcQueryController/ysggxx.do" target="_blank">更多</a></span>
				<i class="gsxx"></i><a href="bdcQueryController/ysggxx.do" target="_blank">预售公示信息</a>
			</div>
			<table class="index-table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>预售证号</th>
					<th>项目名称</th>
					<th>批准日期</th>
				</tr>
				<c:forEach items="${PreSalePublicInfo.Items}" var="item" varStatus="s">
				<tr>
					<td width="200">
						<a href="<%=path%>/bdcQueryController/getCaseProject.do?CaseID=${item.CaseID}"  target="_blank" title="${item.LicenceId}">
						<e:sub  str="${item.LicenceID}" endindex="20" ></e:sub>
						</a>
					</td>
					<td>
						<a href="<%=path%>/bdcQueryController/getCaseProject.do?CaseID=${item.CaseID}"  target="_blank" title="${item.ProjectName}">
						<e:sub  str="${item.ProjectName}" endindex="14" ></e:sub>
						</a>
					</td>
					<td width="120">					
						<c:if test="${fn:length(item.BulletinDate)>10}">${fn:substring(item.BulletinDate,0,10)}</c:if>
						<c:if test="${fn:length(item.BulletinDate)<10}">${item.BulletinDate}</c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
			
						
			<div class="index-title">
				<span><a href="<%=path%>/bdcQueryController/spzzyxsphb.do" target="_blank">更多</a></span>
				<i class="xsph"></i><a href="<%=path%>/bdcQueryController/spzzyxsphb.do" target="_blank">商品住宅月销售排行</a>
			</div>
			<table class="index-table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>名次</th>
					<th>项目名称</th>
					<th>成交套数</th>
					<th>成交面积（万㎡）</th>
					<th>销售率</th>
				</tr>
				<c:forEach items="${goodsTopMonthSell.Items}" var="item" varStatus="s">
				<tr>
					<td>${s.index+1}</td>
					<td>
						<a href="<%=path%>/bdcQueryController/getProject.do?ProjectId=${item.ProjectID}"  target="_blank" title="${item.ProjectName}">
						<e:sub  str="${item.ProjectName}" endindex="14" ></e:sub>
						</a>
					</td>
					<td>${item.CaseCount}</td>
					<td>${item.BuildArea}万㎡</td>
					<td>${item.SellRatio}%</td>
				</tr>
				</c:forEach>
			</table>
			
		</div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>