<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	//获取当前定位菜单  
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
	//获取当前定位菜单  
	String currentNav = request.getParameter("currentNav");
	request.setAttribute("currentNav", StringUtils.isEmpty(currentNav)?"wssb":currentNav);
%>
<script type="text/javascript" src="<%=path%>/webpage/website/common/js/language.js"></script>
<script>
function mcxy(){
	/* art.dialog.confirm("当您在名称自主申报过程中，碰到部分禁限用文字出现“终止”状态导致无法申报通过，<br/>请先拨打业务咨询电话0591-86169725进一步确认。", function() {
		window.open("http://61.154.11.191/usermana/login.do?method=index", "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
	}); */
	
	art.dialog({
		content: "当您在名称自主申报过程中，碰到部分禁限用文字出现“终止”状态导致无法申报通过，请先拨打业务咨询<br/>电话0591-86169725进一步确认。<br/>一、如确不符名称相关规定的，请根据窗口人员指导意见修改名称；<br/>二、如经确认，符合名称相关规定情形的，可到窗口办理名称预先登记，也可在办理设立登记时一并办理。 <br/><span style='float: right;'>企业注册及运营服务科<br/>2021年4月</span>",
		icon:"succeed",
		cancel:false,
		lock: true,
		ok: function(){
			window.open("http://61.154.11.191/usermana/login.do?method=index", "_blank");
		}
	});
}
$ (function (){
	
});
function mztLogin(){
	var locat = document.location+"";
	var basePath = "<%=basePath%>";
	basePath = basePath.replace(":80/","/");
	locat = locat.replace(basePath,"");
	var url = "<%=path%>/userInfoController/mztLogin.do?returnUrl="+locat;
	window.location.href=url;
}
</script>
<!-- 新头部 -->
<script src="https://zwfw.fujian.gov.cn/thirdSys/header.js?unid=CDFF203D00F3BE60FD220C30B8C02EC9"></script>
<div class="eui-headNew">
	<%-- <div class="eui-headCenter">
		<a href="https://zwfw.fujian.gov.cn/"><img src="<%=path%>/webpage/website/newzzhy/images/new/logo-main.png"></a>
		<span class="del">平潭综合实验区工程建设项目管理平台</span>
		<div class="divItem">
			<c:if test="${sessionScope.curLoginMember==null}">
			<span class="workHeight user-logo">
				<img src="<%=path%>/webpage/website/newzzhy/images/new/user.png">
				<a href="<%=path%>/webSiteController/mztRegist.do" style="color:#FFFFFF">注册</a>
			</span>
			<span>&nbsp;|&nbsp;</span>
			<span class="workHeight">
				<a href="javascript:mztLogin();" style="color:#FFFFFF">登录</a>
			</span>
			</c:if>
			<c:if test="${sessionScope.curLoginMember!=null}">
			<span class="workHeight user-logo">	
			<c:if test="${sessionScope.curLoginMember!=null}">
		    	您好!&nbsp;
		    	<c:choose>  
	                <c:when test="${fn:length(sessionScope.curLoginMember.YHMC) > 40}">  
	                    <c:out value="${fn:substring(sessionScope.curLoginMember.YHMC, 0, 36)}..." />
	                </c:when>  
	                <c:otherwise>  
	                    <c:out value="${sessionScope.curLoginMember.YHMC}" />  
	                </c:otherwise>  
	           </c:choose> &nbsp;&nbsp;
			</c:if>
				<img src="<%=path%>/webpage/website/newzzhy/images/new/user.png">
				<a href="<%=userCenter%>" target="_blank"  style="color:#FFFFFF">用户中心</a>
			</span>
			</c:if>
		</div>
	</div> --%>
	
	<div class="menuDiv">
		<ul class="menu-wrap">
				<li <c:if test="${currentNav=='sy'}">class="on"</c:if>><a href="<%=path%>/webpage/tzxm/index.jsp" style="color:#FFFFFF;display:block;">首页</a></li>
				<li <c:if test="${currentNav=='sbzy'}">class="on"</c:if>><a href="<%=path%>/govIvestController/sbzylctList.do" style="color:#FFFFFF;display:block;">申报指引</a></li>
				<li <c:if test="${currentNav=='bszn'}">class="on"</c:if>><a
					href="<%=path%>/govIvestController/govIvestlList.do?type=1" style="color:#FFFFFF;display:block;">办事指南</a></li>
				<li <c:if test="${currentNav=='wssb'}">class="on"</c:if>><a href="<%=userCenter%>" style="color:#FFFFFF;display:block;">我的项目</a></li>
				<%-- <li><a href="${path}/webpage/tzxm/promulgateInfo/handleResult.jsp">公示信息</a></li> --%>
				<li <c:if test="${currentNav=='zcfg'}">class="on"</c:if>><a
					href="<%=path%>/govIvestController/policiesRegulations.do" style="color:#FFFFFF;display:block;">政策法规</a></li>
				<li <c:if test="${currentNav=='zjfw'}">class="on"</c:if>><a
					href="http://ptggzy.pingtan.gov.cn/G2/gbmp/progress/home!index.do" style="color:#FFFFFF;display:block;">中介超市</a></li>
				<li><a
					href="<%=userCenter%>" target="_blank" style="color:#FFFFFF;display:block;">用户中心</a></li>	
		</ul>
		<!---->
	</div>
	
</div>
<!-- 新头部 -->
