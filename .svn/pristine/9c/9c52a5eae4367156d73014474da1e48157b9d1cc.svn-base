<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;
			request.setAttribute("webRoot", basePath);
			String loginflag = request.getParameter("loginflag");
			String checkbackurl = basePath + "/webpage/tzxm/index.jsp";
			String uitype = request.getParameter("uitype");
			if (loginflag != null && "true".equals(loginflag)) {
				String trustticket = request.getParameter("trustticket");
				String url = path + "/userInfoController.do?mztLoginResult&type=tzxm&trustticket=" + trustticket;
				response.sendRedirect(url);
			}
			if ("false".equals(loginflag)) {
				if (uitype != null && "1".equals(uitype)) {
					String url = path + "/userInfoController/checkLogin.do?backurl=" + checkbackurl;
					response.sendRedirect(url);
				}
			}
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
<link rel="stylesheet" type="text/css"
	href="<%=path%>/webpage/tzxm/css/public.css" />
<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/index.css">
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=path%>/webpage/tzxm/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/webpage/website/index/js/common.js"></script>
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
		};
		
		$(function() {
		var yhmc = "${sessionScope.curLoginMember.YHMC}";
		if(yhmc){
		         yhmc = hideCode(yhmc,2,1);
	          var text = "您好！"+yhmc;
	          $("#yhmc").text(text);
		}
		});

		function progressQuery(){
			var queryValue = document.getElementById("projectQueryId").value;
			window.location.href = "<%=path%>/projectWebsiteApplyController/search.do?textValue="+encodeURIComponent(queryValue);
		}
		</script>
</head>
<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=sy" />
	
	<div class="eui-main">
		<div class="eui-logo">
			<img src="<%=path%>/webpage/tzxm/images/logo1.png" />
		</div>
		<div class="eui-input" style="z-index:2;">
			<input type="text" id="projectQueryId" placeholder="请输入项目名称、项目编码">
			<a class="cx-btn" href="javascript:void(0);"
				onclick="progressQuery()">进度查询</a>
			<p>
				<a href="https://fj.tzxm.gov.cn/" referrerpolicy="no-referrer">福建省投资项目在线审批监督平台>>></a>
			</p>

		</div>
		<div class="clearfix"></div>
		<div class="eui-nav">
			<ul>
				<a href="${webRoot}/govIvestController/govIvestlList.do?type=1"
					class="mui-navigate-right">
					<li>
						<div class="eui-nav-main">
							<div class="eui-nav-img">
								<i class="eui-i1"></i>
							</div>
							<div class="eui-nav-p">
								<p>政府投资项目审批</p>
							</div>
						</div>
				</li>
				</a>
				<a href="${webRoot}/govIvestController/govIvestlList.do?type=2"
					class="mui-navigate-right">
					<li style="margin:0 50px">
						<div class="eui-nav-main">
							<div class="eui-nav-img">
								<i class="eui-i2"></i>
							</div>
							<div class="eui-nav-p">
								<p>企业投资项目审核</p>
							</div>
						</div>
				</li>
				</a>
				<a href="${webRoot}/govIvestController/govIvestlList.do?type=3"
					class="mui-navigate-right">
					<li>
						<div class="eui-nav-main">
							<div class="eui-nav-img">
								<i class="eui-i3"></i>
							</div>
							<div class="eui-nav-p">
								<p>企业投资项目备案</p>
							</div>
						</div>
				</li>
				</a>
			</ul>
		</div>
	</div>
	<!--内容结束-->
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newproject/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
<script>
	$(".eui-head li").click(function() {
		$(this).addClass("on").siblings().removeClass("on");
	});
	$(".eui-nav li").click(function() {
		$(this).addClass("on").siblings().removeClass("on");
		$(this).attr("src", "")
	});
</script>
</html>
