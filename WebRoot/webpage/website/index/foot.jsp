<%--<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>--%>
<%--<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<%--<div class="footer" style="height:140px;">--%>
<%--	<div class="footnav">--%>
<%--		<a href="contentController/view.do?contentId=94" target="_blank">联系我们</a>|--%>
<%--		<a href="contentController/view.do?contentId=95" target="_blank">网站地图</a>|--%>
<%--		<a href="contentController/view.do?contentId=96" target="_blank">使用帮助</a>|--%>
<%--		<a href="contentController/view.do?contentId=97" target="_blank">关于我们</a>|--%>
<%--		<a href="javascript:void(0)" onClick="SetHome(this,window.location);">设为首页</a>|--%>
<%--		<a href="javascript:AddFavorite(window.location,document.title);">收藏本站</a>--%>
<%--	</div>--%>
<%--	<div style="float:left;margin: -28px 0 0 300px;">--%>
<%--		<script id="_jiucuo_" sitecode='3590000004' src='http://pucha.kaipuyun.cn/exposure/jiucuo.js'></script>--%>
<%--		<script type="text/javascript">document.write(unescape("%3Cspan id='_ideConac' %3E%3C/span%3E%3Cscript src='http://dcs.conac.cn/js/14/223/0000/60500245/CA142230000605002450003.js' type='text/javascript'%3E%3C/script%3E"));</script>--%>
<%--	</div>--%>
<%--	<div style="float:left;">--%>
<%--	<p style="padding-right:58px;">主办单位：平潭综合实验区行政审批局 --%>
<%--		承办单位：--%>
<%--&lt;%&ndash;		<a href="http://www.ptxzfwzx.gov.cn/loginController.do?login" target="_blank;">&ndash;%&gt;--%>
<%--		平潭综合实验区行政服务中心--%>
<%--		</a>--%>
<%--	</p>--%>
<%--	<p>--%>
<%--		网站标识码：3590000004 &emsp;--%>
<%--		<span>--%>
<%--		闽ICP备13016306号--%>
<%--		</span>--%>
<%--		&emsp;<img src="<%=request.getContextPath()%>/webpage/website/index/images/2020060301.png"/>闽公网安备 35012802000176号--%>
<%--	</p>--%>
<%--	<!--<p>CopyRight © 2015 Pingtan China ， All Rights Reserved--%>
<%--		闽ICP备09032658号</p>-->--%>
<%--	<p>地址：平潭综合实验区金井湾大道 邮编：350400 电话：0591-23120111 0591-23120222 技术支持:0591-23162985</p>--%>
<%--	<p>--%>
<%--		 <e:obj filterid="1" var="eobj" dsid="135">--%>
<%--		您是第 <span style="color:red;">${e:visitstat(1,eobj.site_clickall,0)}</span> 位访客--%>
<%--		</e:obj>--%>
<%--		，今日访问量 <span style="color:red;">${e:todatecount(1,0)}</span>--%>
<%--	</p>--%>
<%--	</div>--%>
<%--	--%>
<%--</div>--%>
<%--<script type="text/javascript" language="javascript">--%>
<%--    function AddFavorite(sURL, sTitle)--%>
<%--    {--%>
<%--        try--%>
<%--        {--%>
<%--            window.external.addFavorite(sURL, sTitle);--%>
<%--        }--%>
<%--        catch (e)--%>
<%--        {--%>
<%--            try--%>
<%--            {--%>
<%--                window.sidebar.addPanel(sTitle, sURL, "");--%>
<%--            }--%>
<%--            catch (e)--%>
<%--            {--%>
<%--                alert("加入收藏失败，请使用Ctrl+D进行添加");--%>
<%--            }--%>
<%--        }--%>
<%--    }--%>
<%--    function SetHome(obj,vrl){--%>
<%--        try {--%>
<%--			obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl);--%>
<%--        }catch(e){--%>
<%--			if(window.netscape) {--%>
<%--					try {--%>
<%--							netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); --%>
<%--					} --%>
<%--					catch (e) { --%>
<%--							alert("此操作被浏览器拒绝！\n您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!"); --%>
<%--					}--%>
<%--					var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);--%>
<%--					prefs.setCharPref('browser.startup.homepage',vrl);--%>
<%--			}else{--%>
<%--				alert("抱歉，您所使用的浏览器无法完成此操作。\n您需要使用浏览器菜单手动设置。");--%>
<%--			}--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>