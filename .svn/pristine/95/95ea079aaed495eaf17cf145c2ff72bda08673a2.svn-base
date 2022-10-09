<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil"%>
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
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/webpage/tzxm/css/public.css" />
<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/handbook.css">
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
        };
        
        $(function() {
        var yhmc = "${sessionScope.curLoginMember.YHMC}";
        if(yhmc){
                 yhmc = hideCode(yhmc,2,1);
              var text = "您好！"+yhmc;
              $("#yhmc").text(text);
        }
        });
</script>
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
					<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a> ></li>
					<li><a>办事指南</a></li>
				</ul>
			</div>
			
			<div class="eui-instruction">
				<div class="slideTxtBox">
					<div class="hd">
						<ul>
							<c:forEach var="top" items="${topList}">
								<a
									href="${webRoot}/govIvestController/govIvestlList.do?id=${top.ID}">
									<c:if test="${categoryId == top.ID}">
										<li class="on" style="margin: 0;padding-top: 10px;">${top.NAME}</li>
									</c:if> <c:if test="${categoryId != top.ID}">
										<li style="margin: 0;padding-top: 20px;">${top.NAME}</li>
									</c:if>
								</a>
							</c:forEach>
						</ul>
					</div>
					<div class="bd">
						<ul>
							<c:forEach var="title" items="${resultlist}">
								<div class="eui-table">
									<p>${title.NAME }</p>
									<table>
										<c:forEach var="center" items="${centerList}">
											<c:if test="${ title.ID == center.id}">
												<c:forEach var="result" items="${center.resultList}">
													<tr
														<c:if test='${result.bg == true}'> bgcolor="#c7e7ff"</c:if>>
														<td>${result.NAME}</td>
														<td class="td1 on"><a
															href="${webRoot}/govIvestController/bsznList.do?categoryId=${result.ID}">事项列表</a></td>
														<td class="td2 on"><a
															href="${webRoot}/userInfoController/mztLogin.do?type=1&returnUrl=govIvestController/xmdjDetail.do?projectId=${result.ID}">项目登记</a></td>
													</tr>
												</c:forEach>
											</c:if>
										</c:forEach>
									</table>
								</div>
							</c:forEach>
						</ul>
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
		$(".td1").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".td2").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
<script type="text/javascript">
		jQuery(".slideTxtBox").slide({trigger:"click"});
	</script>
</html>
