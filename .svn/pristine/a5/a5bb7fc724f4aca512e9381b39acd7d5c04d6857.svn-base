<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	//获取当前定位菜单  
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 新底部 -->
<div class="footerDiv">
	<div class="container" style="width:1300px;">
		<div class="flex" style="justify-content: center;">
			<div class="md-hide footer-left">
				<div class="lg-hide flex" style="padding-top: 20px; white-space: nowrap; justify-content: center;">
					<!--<a href="https://bszs.conac.cn/sitename?method=show&id=51FD16DFA76E03D4E053012819AC0E84" target="_blank">-->
						<img src="<%=path%>/webpage/website/newzzhy/images/new/sydw.png" class="pago" style="margin-top: -5px; margin-right: 20px;">
					<!--</a>-->
					<!--<img src="<%=path%>/webpage/website/newzzhy/images/new/dzjg.png" class="pago">-->
					<span id="_span_jiucuo">
						<img onclick="Link('3590000004')"  style="margin:0;border:0;cursor: pointer;" src="<%=path%>/webpage/website/newzzhy/images/new/jiucuo.png">
					</span>
					<div style="padding-top: 15px; padding-left: 20px;">
						<p>Copyright 2001-2021 All Rights Reserved.</p>
						<a href="https://beian.miit.gov.cn/">闽ICP备13016306号</a>
					</div>
				</div>
			</div>
			<div class="footer-sec">
				<p>技术支持电话：0591-23162985</p>
				<p>工作时间：工作日上午8点30到下午5点30</p>
				<p>健康码、账户登录问题请联系：0591-62623959，4001033673</p>
			</div>
		</div>
	</div>
	<div class="bottom-tip">
		<p>主办单位：平潭综合实验区行政审批局&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			承办单位：平潭综合实验区行政服务中心</p>
		<p class="md-hide">为确保最佳浏览效果，建议您使用以下浏览器版本：IE浏览器9.0版本及以上； Google Chrome浏览器 63版本及以上；
			360浏览器9.1版本及以上，且IE内核9.0及以上。</p>
	</div>
</div>
<!-- 新底部 -->
<script type="text/javascript" language="javascript">
    function AddFavorite(sURL, sTitle)
    {
        try
        {
            window.external.addFavorite(sURL, sTitle);
        }
        catch (e)
        {
            try
            {
                window.sidebar.addPanel(sTitle, sURL, "");
            }
            catch (e)
            {
                alert("加入收藏失败，请使用Ctrl+D进行添加");
            }
        }
    }
    function SetHome(obj,vrl){
        try {
			obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl);
        }catch(e){
			if(window.netscape) {
					try {
							netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
					} 
					catch (e) { 
							alert("此操作被浏览器拒绝！\n您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!"); 
					}
					var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
					prefs.setCharPref('browser.startup.homepage',vrl);
			}else{
				alert("抱歉，您所使用的浏览器无法完成此操作。\n您需要使用浏览器菜单手动设置。");
			}
        }
    }
	//点击图标
	function Link(site_code) {
		//获取该站点需要纠错页面的url地址
		var url = getCurrUrl();
		//跳转至纠错系统填写页面 
		window.open("https://zfwzgl.www.gov.cn/exposure/jiucuo.html?site_code=" + site_code + "&url=" + encodeURIComponent(url));
	}
	//获取该站点需要纠错页面的url地址
	function getCurrUrl() {
		var url = "";
		if (parent !== window) {
			try {
				url = window.top.location.href;
			} catch (e) {
				url = window.top.document.referrer;
			}
		}
		if (url.length == 0)
			url = document.location.href;

		return url;
	}
</script>