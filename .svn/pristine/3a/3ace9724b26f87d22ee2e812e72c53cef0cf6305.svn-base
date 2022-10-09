<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;
	request.setAttribute("webRoot", basePath);
	request.setAttribute("path", path);
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/policyRule.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/webpage/tzxm/css/public.css" />
<script type="text/javascript"
	src="<%=path%>/webpage/tzxm/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
<style type="text/css">
.eui-yhmc {
	width: 120px;
	position: absolute;
	left: 50%;
	margin-left: 520px;
	top: 16px;
}

.eui-yhmc a {
	display: inline-block;
	width: 135%;
	text-align: center;
	font-size: 16px;
	color: #fff;
	float: left;
	height: 20px;
	line-height: 20px;
}
</style>
<script type="text/javascript">
	function hideCode(str, frontLen, endLen) {
		var len = str.length - frontLen - endLen;
		var xing = '';
		for (var i = 0; i < len; i++) {
			xing += '*';
		}
		return str.substring(0, frontLen) + xing + str.substring(str.length - endLen);
	}
	;

	$(function() {
		var yhmc = "${sessionScope.curLoginMember.YHMC}";
		if (yhmc) {
			yhmc = hideCode(yhmc, 2, 1);
			var text = "您好！" + yhmc;
			$("#yhmc").text(text);
		}
	});
	
	function policiesRegulationsPage(itemList) {
		$("#policiesRegulationsList").html("");
		var newhtml = "<table>";
		$.each(itemList, function(index, node) {
			newhtml += '<tr>';
			newhtml += '<td><a title="' + node.ITEMTITLE + '" href="<%=path%>/contentController/view.do?contentId=' + node.TID + '">' + node.ITEMTITLE + '</a></td>';
			newhtml += '<td>' + node.ITEMRELDATE + '</td>';
			newhtml += '</tr>';
		});
		newhtml += "</table>";
		$("#policiesRegulationsList").html(newhtml);
	}
</script>
</head>
<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=zcfg" />
	<%--结束编写头部文件 --%>
	<div class="eui-main">
		<div class="eui-content">
			<div class="eui-crumbs">
				<ul>
					<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
					<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a></li>
					<li><a> > 政策法规 </a></li>
				</ul>
			</div>
			<div class="eui-publicity">
				<div class="eui-publicity-left">
					<p>政策法规</p>
					<ul>
						<c:forEach var="menu" items="${menuList}">
							<c:if test="${menu.classon == true}">
								<a
									href="${webRoot}/govIvestController/policiesRegulations.do?moduleId=${menu.MODULE_ID}">
									<li class="on eui-i1">${menu.MODULE_NAME}</li>
								</a>
							</c:if>
							<c:if test="${menu.classon != true}">
								<a
									href="${webRoot}/govIvestController/policiesRegulations.do?moduleId=${menu.MODULE_ID}">
									<li class="eui-i1">${menu.MODULE_NAME}</li>
								</a>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="eui-publicity-right">
					<div class="eui-publicity-head">
						<span>${pName }</span>
					</div>
					<div class="eui-policy-table" id="policiesRegulationsList"></div>
					<div class="page">
						<jsp:include page="../website/common/page.jsp">
							<jsp:param
								value="${path}/govIvestController/policiesRegulationsInfo.do?moduleId=${moduleId }"
								name="pageURL" />
							<jsp:param value="policiesRegulationsPage" name="callpage" />
							<jsp:param value="5" name="limitNum" />
							<jsp:param value="content" name="morename" />
						</jsp:include>
					</div>
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
		$(".eui-publicity-left li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
</html>
