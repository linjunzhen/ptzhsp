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
	<title>合同签约情况查询-平潭综合实验区行政服务中心</title>
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
	
	<div class="pub-con eui-crumb" data-aos="fade-up">当前位置：首页 > <span>合同签约情况查询</span></div>
		
	<div class="pub-con in-box clearfix" data-aos="fade-up">
		<div class="rfloat in-r" style="width:100%">
			
			<div class="in-table-box">
				<c:if test="${empty bdcMap.ReturnData}">
				</br></br></br><h3>该业务不存在！</h3></br></br></br>
				</c:if>
				<c:if test="${!empty bdcMap.ReturnData}">
				<h3>合同基本信息</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 15%;">合同号</th>
						<td style="width: 35%;">${bdcMap.ReturnData.ContractBaseInfo.LicenceID}</td>
						<th style="width: 15%;">开发企业</th>
						<td style="width: 35%;">${bdcMap.ReturnData.ContractBaseInfo.DeveloperName}</td>
					</tr>
					<tr>
						<th>合同类别</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.ContractType}</td>
						<th>项目名称</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.ProjectName}</td>
					</tr>
					<tr>
						<th>合同日期</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.ContractDate}</td>
						<th>房屋坐落</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.Location}</td>
					</tr>
					<tr>
						<th>备案日期</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.RegisterDate}</td>
						<th>购买种类</th>
						<td>${bdcMap.ReturnData.ContractBaseInfo.HouseType}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">${bdcMap.ReturnData.ContractBaseInfo.Remark}</td>
					</tr>
				</table>
				<h3>房屋信息</h3>
				
			<c:forEach items="${bdcMap.ReturnData.HouseInfo}" var="houseInfo" varStatus="s">
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 15%;">幢号</th>
						<td style="width: 35%;">${houseInfo.BuildingID}</td>
						<th style="width: 15%;">房号</th>
						<td style="width: 35%;">${houseInfo.HouseID}</td>
					</tr>
					<tr>
						<th>结构</th>
						<td>${houseInfo.Structure}</td>
						<th>总层数</th>
						<td>${houseInfo.TotalFloor}</td>
					</tr>
					<tr>
						<th>所在层</th>
						<td>${houseInfo.OnFloor}</td>
						<th>建筑面积</th>
						<td>${houseInfo.BuildArea}㎡</td>
					</tr>
					<tr>
						<th>套内面积</th>
						<td>${houseInfo.UseArea}㎡</td>
						<th>分摊面积</th>
						<td>${houseInfo.ApportionArea}㎡</td>
					</tr>
					<tr>
						<th>用途</th>
						<td>${houseInfo.Purpose}</td>
						<th>单价</th>
						<td>${houseInfo.UnitPrice}元</td>
					</tr>
					<tr>
						<th>房产价值</th>
						<td>${houseInfo.Price}元</td>
						<th>房屋性质</th>
						<td>${houseInfo.HouseProperty}</td>
					</tr>
				</table>
				</c:forEach>
				<h3>购房者信息</h3>
				<table class="index-table td-left" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th style="width: 15%;">购房者姓名</th>
						<td style="width: 35%;">${bdcMap.ReturnData.Buyers.Name}</td>
						<th style="width: 15%;">身份证明</th>
						<td style="width: 35%;">${bdcMap.ReturnData.Buyers.IDType}</td>
					</tr>
					<tr>
						<th>身份证明号码</th>
						<td>${bdcMap.ReturnData.Buyers.IDCard}</td>
						<th>户籍所在地</th>
						<td>${bdcMap.ReturnData.Buyers.Address}</td>
					</tr>
					<tr>
						<th>联系电话</th>
						<td>${bdcMap.ReturnData.Buyers.Tel}</td>
						<th>受托人</th>
						<td>${bdcMap.ReturnData.Buyers.Broker}</td>
					</tr>
					<tr>
						<th>受托人身份证</th>
						<td colspan="3">${bdcMap.ReturnData.Buyers.BrokerIDCard}</td>
					</tr>
				</table>
				</c:if>
			</div>
		</div>
	</div>	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>