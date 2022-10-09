<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
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
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<title>平潭综合实验区工程建设项目管理平台</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
		<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/publicInfo.css">
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.min.js" ></script>
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
		<script type="text/javascript">
			function handleResultPage(itemList){
				$("#handleResultList").html("");
				var newhtml = "<table><tr>";
				newhtml +='<th width="31%">项目名称</th>';
				newhtml +='<th width="15%">项目编码</th>';
				newhtml +='<th width="20%">事项名称</th>';
				newhtml +='<th width="6%">办理结果</th>';
				newhtml +='<th width="8%">办理时间</th></tr>';
				$.each( itemList, function(index, node){
					newhtml +='<tr>';
					newhtml +='<td style="padding-left: 14px;text-align:left;">'+node.PROJECT_NAME +'</td>';
					newhtml +='<td>'+node.PROJECT_CODE +'</td>';
					newhtml +='<td>'+node.ITEM_NAME +'</td>';
					newhtml +='<td>'+node.FLOW_STATUS +'</td>';
					newhtml +='<td>'+node.END_TIME +'</td>';
					newhtml +='</tr>';
				});
				newhtml += "</table>";
				$("#handleResultList").html(newhtml);
			}
			
			function handleResultSearch(){
				var textValue = document.getElementById("textValue").value;
				window.location.href = 
					"<%=path%>/govIvestController/search.do?textValue="+encodeURIComponent(textValue);
			}
			
		</script>
	</head>
	<body>
		<!--头部开始-->
		<div class="eui-head">
			<ul>
				<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a></li>
				<li><a href="${webRoot}/govIvestController/sbzylctList.do">申报指引</a></li>
				<li><a href="${webRoot}/govIvestController/govIvestlList.do?type=1">办事指南</a></li>
				<li><a href="<%=userCenter%>">我的项目</a></li>
				<%-- <li class="on" ><a href="${webRoot}/webpage/tzxm/promulgateInfo/handleResult.jsp">公示信息</a></li> --%>
				<li><a href="${webRoot}/govIvestController/policiesRegulations.do">政策法规</a></li>
				<li><a href="http://ptggzy.pingtan.gov.cn/G2/gbmp/progress/home!index.do">中介超市</a></li>
			</ul>
			<%-- <c:if test="${sessionScope.curLoginMember!=null}">
				<div>
					<a >您好！${sessionScope.curLoginMember.YHMC}</a>
					<a style="color: #e60012;" href="<%=path%>/userInfoController/logout.do">[安全退出]</a>
				</div>
				<div class="eui-login">
					<a href="<%=path%>/userInfoController/mztLogin.do">用户中心</a>
				</div>
			</c:if> --%>
			<c:if test="${sessionScope.curLoginMember==null}">
				<div class="eui-login">
					<a href="<%=path%>/userInfoController/mztLogin.do?type=tzxm">登录</a>
					<a href="<%=path%>/webSiteController/mztRegist.do">注册</a>
				</div>
			</c:if>
		</div>
		<!--头部结束-->
		<!--内容开始-->
		<div class="eui-main">
			<div class="eui-logo">
				<img src="<%=path%>/webpage/tzxm/images/logo.png"/>
			</div>
			<div class="eui-content">
				<div class="eui-crumbs">
					<ul>
						<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a></li>
						<li><a> > 公示信息</a> </li>
						<!-- <li><a> > 向民间资本推介项目</a> </li> -->
					</ul>
				</div>
				<div class="eui-publicity">
					<div class="eui-publicity-left">
						<p>公示信息</p>
						<ul>
							<!-- <li class="eui-i1">向民间资本推介项目</li>
							<li class="eui-i2">已完成推介项目</li> -->
							<a href="${webRoot}/webpage/tzxm/promulgateInfo/handleResult.jsp"><li class="on eui-i3">办理结果公示</li></a>
							<!-- <li class="eui-i4">项目登记情况</li>
							<li class="eui-i5">部门办件情况</li>
							<li class="eui-i6">异常名录</li>
							<li class="eui-i7">黑名单</li> -->
						</ul>
					</div>
					<div class="eui-publicity-right">
						<div class="eui-publicity-head">
							<span>办理结果公示</span>
							<div class="eui-publicity-input">
								<input type="text" name="textValue" id="textValue" value="${textValue }" placeholder="项目编码/项目名称">
								<a href="javascript:void(0);" onclick="handleResultSearch()">搜索</a>
							</div>
						</div>
						<div class="eui-publicity-table" id="handleResultList">
						
                		</div>
                		<div class="page">
							<jsp:include page="../../website/common/page.jsp" >
								<jsp:param value="${path}/govIvestController/promulgateInfo.do?type=handleResult&textValue=${encodeValue }" 
									name="pageURL"/>
								<jsp:param value="handleResultPage" name="callpage"/>
								<jsp:param value="8" name="limitNum"/>
								<jsp:param value="content" name="morename"/>
							</jsp:include>
		                </div>
					</div>
				</div>
			</div>
		</div>
		<!--内容结束-->
		<jsp:include page="../../tzxm/foot.jsp" />
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
