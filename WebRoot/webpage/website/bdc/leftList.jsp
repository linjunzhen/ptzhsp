<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
  //获取当前定位菜单
  String currentNav = request.getParameter("currentNav");
  request.setAttribute("currentNav", currentNav);
  
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="nav-left">
	<ul>
		<li <c:if test="${currentNav=='1'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/ysggxx.do"><i class="i1"></i>预售公示信息</a>
		</li>
		<li <c:if test="${currentNav=='2'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/kslptjList.do"><i class="i2"></i>可售楼盘统计信息列表</a>
		</li>
		<li <c:if test="${currentNav=='3'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/spzzyxsphb.do"><i class="i3"></i>商品住宅月销售排行榜</a>
		</li>
		<li <c:if test="${currentNav=='4'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/drqyxxlb.do"><i class="i4"></i>当日签约信息列表</a>
		</li>
		<li <c:if test="${currentNav=='5'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/spzzxmjgphb.do"><i class="i5"></i>商品住宅项目价格排行榜</a>
		</li>
		<li <c:if test="${currentNav=='6'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/kslpcx.do"><i class="i6"></i>可售楼盘查询</a>
		</li>
		<li <c:if test="${currentNav=='7'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/spzzddph.do"><i class="i7"></i>商品住宅单幢价格排行</a>
		</li>
		<li <c:if test="${currentNav=='8'}">class="on"</c:if>>
			<a href="<%=path%>/bdcQueryController/xsmyhqdb.do"><i class="i8"></i>商品住宅销售每月行情对比</a>
		</li>
	</ul>
</div>
