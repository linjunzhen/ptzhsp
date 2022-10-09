<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib
    prefix="e" uri="/WEB-INF/tld/e-tags.tld"%><%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<title>平潭综合实验区不动产登记与交易</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>

</head>

<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>
	
	<div class="eui-main">	
		<e:obj filterid="${module.MODULE_ID}" var="eobj" dsid="1">
		<div class="pub-con in-box clearfix"  data-aos="fade-up">
			<div class="lfloat in-l">
				<div class="nav-left">
					<ul>
						<li>
							<a href="javascript:void(0);"><i><img src="<%=path%>/webpage/site/bdc/info/images/nav6.png"/></i><e:obj filterid="${param.cnid}" var="eobj" dsid="1">
							${eobj.headName}
						</e:obj></a>
							<ul class="sub">
								<e:for dsid="7" filterid="${module.MODULE_ID}" end="200" var="efor1">
								<li><a title="${efor1.module_name}" href="<%=path%>/contentController/list.do?moduleId=${efor1.module_id}"> ${efor1.module_name}</a></li>
								 </e:for> 
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<div class="rfloat in-r">
				<e:for filterid="${module.MODULE_ID}" end="100" var="efor1" dsid="7">
				<div class="in-title"><a href="<%=path%>/contentController/list.do?moduleId=${efor1.module_id}" target="_top"><span>更多</span>${efor1.module_name}</a></div>
				<div class="eui-list">
					<ul>
						<c:set var="flag" value="0"></c:set>
							<e:for filterid="${efor1.module_id}" end="5" var="efor2" dsid="2">
								<c:set var="flag" value="1"></c:set>
								<li><span class="date">[<fmt:formatDate value="${efor2.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate>]</span>
								<c:if test="${efor2.contenttype==1}">
										<e:for filterid="${efor2.itemId}" end="10" var="efor" dsid="21">
											<a href='${pageContext.request.contextPath}/jhtml/download/${efor.fileid}' title="${epage.itemTitle}" target="_blank">
										</e:for>
								</c:if>
								<c:if test="${efor2.contenttype!=1}">
									<a title="${efor2.itemTitle}" href="<%=path%>/contentController/view.do?contentId=${efor2.tid}" target="_blank">
								</c:if>
								<e:sub objdate="${efor2.itemReldate}" timeout="2"  str="${efor2.itemTitle}" endindex="38" ></e:sub></a></li>
							</e:for>
							<c:if test="${flag==0}">
								<e:for filterid="${efor1.module_id}" end="5" var="efor2" dsid="7">
								   <li><a title="${efor2.module_name}" href="<%=path%>/contentController/list.do?moduleId=${efor2.module_id}" target="_top">${efor2.module_name}</a></li>
								</e:for>
							</c:if>
					</ul>
				</div>
				
				  </e:for>
			</div>
		</div>
		</e:obj>
	</div>
	
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>