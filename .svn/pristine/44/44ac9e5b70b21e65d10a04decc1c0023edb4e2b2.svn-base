<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
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
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<script type="text/javascript" src="webpage/website/common/js/language.js"></script>
<script type="text/javascript">
function nowtime(ctimes){
	var now = new Date(ctimes);  
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日       
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getSeconds();
	 var clock = year + "年";
	 if(month < 10)
		 clock += "0";
	 clock += month + "月";
	 if(day < 10)
		 clock += "0";
	 clock += day + "日 ";
	 clock +=" 星期"+"日一二三四五六".charAt(now.getDay());
	//等待一秒钟后调用time方法，由于settimeout在time方法内，所以可以无限调用
	 $('#shownowtime').html(clock);
	 setTimeout('nowtime('+(ctimes+1000)+')',1000);
}
nowtime(new Date().getTime());

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
</script>
<!-- 头部开始-->
<div class="header">
  <div class="header_content">
    <div class="header_left"> <span class="header_span1" >欢迎光临！</span>
	<span  class="header_span2">今天是：</span>
	<span class="header_span1" id="shownowtime"></span>
	<!--<span class="header_span2"  style="margin-top: -3px;">
		<iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=99&color=%23FFFFFF&py=pingtan" width="160" height="30"
		frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	</span>-->
	</div>
    <div class="header_right">
<!-- 		<a href="#">手机门户</a> -->
		<span></span>
		<a href="javascript:st();" id="st">繁体中文</a>
		<c:if test="${sessionScope.curLoginMember!=null}">
			<span></span>
		    	您好!&nbsp;
		    	<c:choose>  
	                <c:when test="${fn:length(sessionScope.curLoginMember.YHMC) > 40}">  
	                    <c:out value="${fn:substring(sessionScope.curLoginMember.YHMC, 0, 36)}..." />
	                </c:when>  
	                <c:otherwise>  
	                    <c:out value="${sessionScope.curLoginMember.YHMC}" />  
	                </c:otherwise>  
	           </c:choose> 
<%--	        <a style="color: #e60012;" href="userInfoController/logout.do">[安全退出]</a>--%>
	    </c:if>
	</div>
  </div>
</div>
</div>
<div class="title">
  <div class="title_content">
  <a><img src="<%=path%>/webpage/website/zzhy/images/logo.png" /></a>
    <div class="login_box"><img src="<%=path%>/webpage/website/zzhy/images/login_ico.png" />
	<c:if test="${sessionScope.curLoginMember==null}">
		<a href="<%=path%>/userInfoController/mztLogin.do?returnUrl=webSiteController/view.do?navTarget=website/zzhy/index">登录</a>
		<span></span>
		<%-- <a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/regist">注册</a> --%>
		<!-- 注册跳转到闽政通注册 -->
		<a href="<%=path%>/webSiteController/mztRegist.do">注册</a>
	</c:if>
	<c:if test="${sessionScope.curLoginMember!=null}">
		<a href="<%=userCenter%>" target="_blank">用户中心</a>
	</c:if>
	</div>
  </div>
</div>
<div class="banner" <c:if test="${currentNav=='sy'}">style="height:400px;"</c:if>>
  <div class="slideBox">
    <div class="centerhd">
      <div class="hd">
        <ul>
          <li></li>
          <li></li>
          <li></li>
        </ul>
      </div>
    </div>
    <div class="bd">
      <ul>
        <li <c:if test="${currentNav=='sy'}">style="height:400px;"</c:if>>
			<a >
				<img src="<%=path%>/webpage/website/zzhy/images/banner.png" <c:if test="${currentNav=='sy'}">style="height:400px;"</c:if>/>
			</a>
		</li>
        <li >
			<a >
				<img src="<%=path%>/webpage/website/zzhy/images/top1.jpg" <c:if test="${currentNav=='sy'}">style="height:400px;"</c:if>/>
			</a>
		</li>
        <li class="on">
			<a >
				<img src="<%=path%>/webpage/website/zzhy/images/top2.jpg" <c:if test="${currentNav=='sy'}">style="height:400px;"</c:if>/>
			</a>
		</li>
      </ul>
    </div>
    <script type="text/javascript">jQuery(".slideBox").slide({mainCell:".bd ul",effect:"left",autoPlay:true,interTime:15000});</script> 
  </div>
  <div class="menu1">
    <div class="menu_content">
      <ul>
        <li <c:if test="${currentNav=='sy'}">class="cur_li"</c:if>>
			<a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">
				<img src="<%=path%>/webpage/website/zzhy/images/menu_home.png" style="width:25px; height:24px; margin-top:12px;" />
				<span >首页</span>
			</a>
		</li>
        <li >
			<a href="javascript:mcxy();" >
				<img src="<%=path%>/webpage/website/zzhy/images/menu_name.png" style=" margin-top:12px;" />
				<span >名称选用</span>
			</a>
		</li>
        <li <c:if test="${currentNav=='wssb'}">class="cur_li"</c:if>>
			<a  href="<%=path%>/webSiteController.do?wssbSelect">
			<%-- <a  href="<%=path%>/webSiteController.do?zzhywssb"> --%>
				<img src="<%=path%>/webpage/website/zzhy/images/menu_net.png" style=" margin-top:12px;" />
				<span >网上申报</span>
			</a>
		</li>
<%--        <li <c:if test="${currentNav=='zcfg'}">class="cur_li"</c:if>>--%>
<%--			<a href="<%=path%>/contentController/list.do?moduleId=302">--%>
<%--				<img src="<%=path%>/webpage/website/zzhy/images/menu_law.png" style=" margin-top:12px;" />--%>
<%--				<span >政策法规</span>--%>
<%--			</a>--%>
<%--		</li>--%>
        <li <c:if test="${currentNav=='tyfk'}">class="cur_li"</c:if> >
			<a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/tyfk">
				<img src="<%=path%>/webpage/website/zzhy/images/menu_message.png" style="margin-top:12px;" />
				<span >统一反馈</span>
			</a>
		</li>
      </ul>
    </div>
  </div>
</div>
<!-- 头部结束-->
