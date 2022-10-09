<%--<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>--%>
<%--<%@ page language="java" import="net.evecom.core.util.AppUtil"%>--%>
<%--<%@ page language="java" import="net.evecom.core.util.JsonUtil"%>--%>
<%--<%@ page language="java" import="net.evecom.platform.bsfw.util.MztSample"%>--%>
<%--<%@ page language="java" import="net.evecom.platform.bsfw.service.UserInfoService"%>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>--%>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String loginflag = request.getParameter("loginflag");--%>
<%--String trustTicket = request.getParameter("trustticket");--%>
<%--Map<String, Object> userInfo = AppUtil.getLoginMember();--%>
<%--if(loginflag!=null&&"true".equals(loginflag)){--%>
<%--	request.setAttribute("loginflag", "true");--%>
<%--	UserInfoService userInfoService = (UserInfoService)AppUtil.getBean("userInfoService");--%>
<%--	userInfoService.getMztUserInfoToSession(trustTicket);--%>
<%--}else if(loginflag!=null&&"false".equals(loginflag)){--%>
<%--	request.setAttribute("loginflag", "false");--%>
<%--}else{--%>
<%--	if (userInfo!=null) {--%>
<%--		String MZT_USER_ID = (String)userInfo.get("MZT_USER_ID");--%>
<%--		String MZT_USER_TOKEN = (String)userInfo.get("MZT_USER_TOKEN");--%>
<%--		String resultData = MztSample.getUserInfoByToken(MZT_USER_TOKEN, MZT_USER_ID);--%>
<%--		if (resultData == null || "".equals(resultData)) {--%>
<%--			request.setAttribute("loginflag", "localfalse");--%>
<%--		}else{--%>
<%--			Map<String, Object> m = JsonUtil.parseJSON2Map(resultData);--%>
<%--	        boolean result = (Boolean) m.get("success");--%>
<%--	        if(result){--%>
<%--				request.setAttribute("loginflag", "localtrue");--%>
<%--	        }else{--%>
<%--	        	request.setAttribute("loginflag", "localfalse");--%>
<%--	        }--%>
<%--		}--%>
<%--	}else{--%>
<%--		request.setAttribute("loginflag", "uncheck");--%>
<%--	}--%>
<%--}--%>
<%--%>--%>

<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
<%--<script type="text/javascript" src="webpage/website/common/js/language.js"></script>--%>
<%--<script type="text/javascript" src="webpage/website/common/js/scrolltopcontrol.js"></script>--%>

<%--<script type="text/javascript" src="webpage/website/common/js/quick_common.js"></script>--%>
<%--&lt;%&ndash;<script type="text/javascript" src="webpage/website/common/js/quick_links.js"></script>&ndash;%&gt;--%>
<%--<link href="webpage/website/common/css/quick.css" rel="stylesheet" type="text/css" />--%>
<%--<script type="text/javascript">--%>
<%--	$(function(){--%>
<%--		var loginflag = '${loginflag}';--%>
<%--		var user = '${sessionScope.curLoginMember}'--%>
<%--		if(loginflag=='uncheck'&&(user==null||user=='')){--%>
<%--			var url = window.location.href;--%>
<%--			location.href="<%=path%>/userInfoController/checkLogin.do?backurl="+url;			--%>
<%--		}else if(loginflag=='true'){--%>
<%--			var newurl = window.location.href;--%>
<%--			var end = newurl.indexOf("loginflag");--%>
<%--			if(end!=-1){--%>
<%--				newurl = newurl.substring(0, end-1);				--%>
<%--				end = newurl.indexOf("trustticket");--%>
<%--				if(end!=-1){--%>
<%--					newurl = newurl.substring(0, end-1);--%>
<%--				}--%>
<%--				location.href="<%=path%>/userInfoController/checkLogin.do?backurl="+newurl+"&notmzt="+loginflag;--%>
<%--			}	--%>
<%--		}else if(loginflag=='localfalse'){--%>
<%--			location.href="<%=path%>/userInfoController/logout.do";--%>
<%--		}--%>
<%--	});--%>
<%--	--%>
<%--	function GetQueryString(name)--%>
<%--	{--%>
<%--	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");--%>
<%--	     var r = window.location.search.substr(1).match(reg);--%>
<%--	     if(r!=null)return  unescape(r[2]); return null;--%>
<%--	}--%>
<%--	function login(){--%>
<%--		var myurl=GetQueryString("returnUrl");--%>
<%--		if(myurl !=null && myurl.toString().length>1)--%>
<%--		{ --%>
<%--			window.open("<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login&returnUrl="+myurl,"_self");--%>
<%--		}else{--%>
<%--			var returnurl =  window.location.pathname.replace("<%=path%>"+"/", "");--%>
<%--			returnurl = encodeURI(returnurl+window.location.search);--%>
<%--			if(returnurl.length>0){--%>
<%--			 window.open("<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login&returnUrl="+returnurl,"_self");--%>
<%--			}else{--%>
<%--				returnurl = encodeURI("webSiteController/view.do");--%>
<%--				window.open("<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login&returnUrl="+returnurl,"_self");--%>
<%--			}--%>
<%--		}--%>
<%--		--%>
<%--	}--%>
<%--	function mztLogin(){--%>
<%--		window.top.location.href="<%=path%>/userInfoController/mztLogin.do?returnUrl=webpage/site/bdc/info/index.jsp";--%>
<%--	};--%>
<%--	function swbUserRegister(){--%>
<%--		window.top.location.href="${pageContext.request.contextPath}/userInfoController/swbRegister.do?user_type=user";--%>
<%--	}--%>
<%--	var webpath = "<%=path%>";--%>
<%--</script>--%>
<%--<script>--%>
<%--var _hmt = _hmt || [];--%>
<%--(function() {--%>
<%--  var hm = document.createElement("script");--%>
<%--  hm.src = "https://hm.baidu.com/hm.js?61b8c7ad5a82c87e12ae38d3849afd7c";--%>
<%--  var s = document.getElementsByTagName("script")[0]; --%>
<%--  s.parentNode.insertBefore(hm, s);--%>
<%--})();--%>
<%--</script>--%>

<%--<div class="head">--%>
<%--	<ul class="lfloat">--%>
<%--		<c:if test="${sessionScope.curLoginMember!=null}">--%>
<%--	    	<li>您好!&nbsp;--%>
<%--	    	<c:choose>  --%>
<%--                <c:when test="${fn:length(sessionScope.curLoginMember.YHMC) > 40}">  --%>
<%--                    <c:out value="${fn:substring(sessionScope.curLoginMember.YHMC, 0, 36)}..." />  --%>
<%--                </c:when>  --%>
<%--                <c:otherwise>  --%>
<%--                    <c:out value="${sessionScope.curLoginMember.YHMC}" />  --%>
<%--                </c:otherwise>  --%>
<%--            </c:choose> --%>
<%--&lt;%&ndash;            <a style="color: #e60012;" href="userInfoController/logout.do">[安全退出]</a></li>&ndash;%&gt;--%>
<%--	    </c:if>--%>
<%--	    <c:if test="${sessionScope.curLoginMember==null}">--%>
<%--		<li>--%>
<%--			<!-- <a href="javascript:void(0);" onclick="login();">登录</a> -->--%>
<%--			<!-- 改为闽政通登录入口 -->--%>
<%--			<a href="javascript:void(0);" onclick="mztLogin();">登录</a>--%>
<%--		</li>--%>
<%--&lt;%&ndash;		<li>|</li>&ndash;%&gt;--%>
<%--&lt;%&ndash;		<li>&ndash;%&gt;--%>
<%--&lt;%&ndash;			<a href="webSiteController/view.do?navTarget=website/yhzx/regist">注册</a>&ndash;%&gt;--%>
<%--&lt;%&ndash;		</li>&ndash;%&gt;--%>
<%--		<li>|</li>--%>
<%--		&lt;%&ndash;<li>&ndash;%&gt;--%>
<%--			&lt;%&ndash;<a href="javascript:;" onclick="swbUserRegister();" >省网用户注册</a>&ndash;%&gt;--%>
<%--		&lt;%&ndash;</li>&ndash;%&gt;--%>
<%--		</c:if>--%>
<%--	</ul>--%>
<%--	<ul class="rfloat">--%>
<%--		<li><a href="http://xzfwzx.pingtan.gov.cn/#/userCenter/index">用户中心</a></li>--%>
<%--		<li>|</li>--%>
<%--		<li><a  href="javascript:st();" id="st">繁體中文</a></li>--%>
<%--		<li>|</li>--%>
<%--<!-- 		<li><a href="<%=path%>/webpage/website/common/app.jsp">手机门户</a></li> -->--%>
<%--	</ul>--%>
<%--</div>--%>