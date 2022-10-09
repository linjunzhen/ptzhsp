<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
   String menuKey = request.getParameter("menuKey");
   request.setAttribute("menuKey", menuKey);
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>

<div class="nmainL">
	<div class="nmainLname">
		<img src="webpage/website/common/images/userphoto.png" />
		<p>${sessionScope.curLoginMember.YHMC}</p>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
		<p>个人用户</p>
		</c:if>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
        <p>企业用户</p>
        </c:if>
		<p>
			<a href="userInfoController/logout.do">[退出]</a>
		</p>
	</div>
	<div class="subnav">
		<ul>
		    <!-- 
			<li <c:if test="${menuKey=='grzx'}">class="subnavOn"</c:if> >
			     <a href="webSiteController.do?usercenter">
			     <img src="webpage/website/common/images/subicon.png" />个人中心
			     </a>
			</li>  -->
			<li <c:if test="${menuKey=='wdbj'}">class="subnavOn"</c:if>  >
			   <a href="<%=userCenter%>">
			   <img src="webpage/website/common/images/subicon1.png" />我的办件</a>
			</li>
<!-- 			<li <c:if test="${menuKey=='zhsz'}">class="subnavOn"</c:if>> -->
<!-- 				<a href="webSiteController/view.do?navTarget=website/yhzx/wdcl"><img -->
<!-- 					src="webpage/website/common/images/subicon1.png" />我的材料</a></li> -->
			<li <c:if test="${menuKey=='wdzx'}">class="subnavOn"</c:if> ><a
				href="webSiteController/view.do?navTarget=website/yhzx/wdzx"><img
					src="webpage/website/common/images/subicon2.png" />我的咨询</a></li>
			<li <c:if test="${menuKey=='wdts'}">class="subnavOn"</c:if> ><a
				href="webSiteController/view.do?navTarget=website/yhzx/wdts"><img
					src="webpage/website/common/images/wdts.png" />我的投诉</a></li>
		    <%-- 				
			<li><a href="#"><img
					src="webpage/website/common/images/subicon3.png" />常用办件人</a></li>
			--%>	
			<li <c:if test="${menuKey=='wdsc'}">class="subnavOn"</c:if>><a 
			href="webSiteController/view.do?navTarget=website/yhzx/wdsc"><img
					src="webpage/website/common/images/subicon4.png" />我的收藏</a></li>
			<li <c:if test="${menuKey=='wdpj'}">class="subnavOn"</c:if>><a
			 href="webSiteController/view.do?navTarget=website/yhzx/wdpj"><img
					src="webpage/website/common/images/subicon5.png" />我的评价</a></li>
			<li <c:if test="${menuKey=='sjdz'}">class="subnavOn"</c:if>>
			<a href="webSiteController/view.do?navTarget=website/yhzx/sjdz"><img
					src="webpage/website/common/images/subicon1.png" />收件地址</a></li>
			<li <c:if test="${menuKey=='zhsz'}">class="subnavOn"</c:if>>
			<a href="https://mztapp.fujian.gov.cn:8304/dataset/UnifiedController/goUserCenter.do" target="_blank"><img
					src="webpage/website/common/images/subicon6.png" />账户设置</a></li>
			<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
	        	<li <c:if test="${menuKey=='wdxm'}">class="subnavOn"</c:if>>
	        		<a href="webSiteController/view.do?navTarget=website/yhzx/wdxm">
	        		<img src="webpage/website/common/images/subicon3.png" />我的项目</a>
	        	</li>
	        </c:if>
		</ul>
	</div>
</div>

