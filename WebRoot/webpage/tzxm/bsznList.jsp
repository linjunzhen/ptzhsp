<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;
	request.setAttribute("webRoot", basePath);
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
		<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/approveItem.css">
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.min.js" ></script>
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
	</head>
	<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=bszn" />
	<%--结束编写头部文件 --%>
		<div class="eui-main">
			<div class="eui-content">
				<div class="eui-crumbs">
					<ul>  
						<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
						<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a> > </li>
						<li><a href="${webRoot}/govIvestController/govIvestlList.do?type=1"> 办事指南</a> > </li>
						<li><a>事项列表</a> </li>
					</ul>
				</div>
				<div class="eui-Approval">
					<div class="eui-Approval-head">
						<p>${titleName}</p>
						<a class="eui-back"  href="javascript:history.go(-1)">返回上级</a>
					</div>
					<div class="eui-Approval-main">
						<c:forEach var="category" items="${categoryList}">
							<span>${category.NAME}</span>
							<div class="eui-publicity-table">
								<table>
									<tr>
										<th>序号</th>
										<th>项目名称</th>
										<th>部门</th>
										<th>操作</th>
									</tr>
									<c:forEach var="item" items="${category.itemList}">
										<tr <c:if test='${item.RN %2 == 0}'> bgcolor="#c7e7ff"</c:if>>
											<td width="8%">${item.RN}</td>
											<td width="41%">${item.ITEM_NAME}</td>
											<td width="41%">${item.SSZTMC}</td>
											<td width="10%" align="center">
												<a class="td1" href="${webRoot}/serviceItemController/bsznDetail.do?itemCode=${item.ITEM_CODE}&projectType=1">办事指南</a>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<%--开始编写尾部文件 --%>
			<jsp:include page="/webpage/website/newproject/foot.jsp" />
			<%--结束编写尾部文件 --%>
		</div>
		<!--内容结束-->
	</body>	
	<script>
		$(".eui-head li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".eui-nav li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
</html>
