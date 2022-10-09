<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>

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

<div class="header">
	<div class="header1">
		<div class="container">
			
		<jsp:include page="../common/tophead.jsp"></jsp:include>
		</div>
	</div>
	<div class="indswf">
		<object width="1000" height="228" name="top2016" id="top2016" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
			<param name="movie" value="<%=path%>/webpage/website/common/swf/top2016.swf"> 
			<param name="quality" value="High"> 
			<param name="wmode" value="transparent"> 
			<param name="allowScriptAccess" value="sameDomain"> 
			<embed src="<%=path%>/webpage/website/common/swf/top2016.swf" quality="High" pluginspage="http://www.macromedia.com/go/getflashplayer" width="1000" height="228" name="top2016" id="top2016" wmode="transparent" allowscriptaccess="sameDomain" type="application/x-shockwave-flash"></object>
		</embed>
	 </div>
	 <div class="headerNav">
		<ul>
			<li <c:if test="${currentNav=='sy'}">class="navOn"</c:if> >
				<a href="webSiteController/view.do">首页</a>
			</li>
			<li <c:if test="${currentNav=='jgsz'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=23">机构设置</a>
			</li>
			<li <c:if test="${currentNav=='zcfg'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=41">政策法规</a>
			</li>
			<li <c:if test="${currentNav=='wsbs'}">class="navOn"</c:if>>
				<a href="webSiteController/view.do?navTarget=website/bsdt/index" target="_blank">网上办事</a>
			</li>
			<li <c:if test="${currentNav=='zxhd'}">class="navOn"</c:if> >
			   <a href="webSiteController/view.do?navTarget=website/hd/xsqx">咨询互动</a>
			</li>
			<li <c:if test="${currentNav=='wjzl'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=49">文件资料</a>
			</li>
			<li <c:if test="${currentNav=='yhzn'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=53">用户指南</a>
			</li>
<!--
			<li <c:if test="${currentNav=='qnwmh'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=44">青年文明号</a>
			</li>
-->		
			<li <c:if test="${currentNav=='xxgk'}">class="navOn"</c:if> >
			   <a href="contentController/list.do?moduleId=142">信息公开</a>
			</li>
<!--
			<li  style="background:none;" <c:if test="${currentNav=='djgz'}">class="navOn"</c:if> >					
			   <a href="contentController/list.do?moduleId=46">党建工作</a>
			</li>
-->		
		</ul>
	 </div>
</div>

