<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;
	request.setAttribute("webRoot", basePath);
	request.setAttribute("path", path);
%>
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
</script>
<!--底部开始-->
<div class="eui-footer">
	<div class="eui-footer-main"> 
		<div class="eui-footer-ul">
			<ul>
				<li><a href="http://pi12345.fujian.gov.cn/" target="_blank">我要建议</a></li>
				<li><a href="http://pi12345.fujian.gov.cn/" target="_blank">我要投诉</a></li>
				<li><a href="<%=path%>/contentController/view.do?contentId=94" target="_blank">联系我们</a></li>
				<li><a href="<%=path%>/contentController/view.do?contentId=95" target="_blank">网站地图</a></li>
				<li><a href="<%=path%>/contentController/view.do?contentId=96" target="_blank">使用帮助</a></li>
				<li><a href="<%=path%>/contentController/view.do?contentId=97" target="_blank">关于我们</a></li>
				<li><a href="javascript:void(0)" onClick="SetHome(this,window.location);">设为首页</a></li>
				<li><a href="javascript:AddFavorite(window.location,document.title);">收藏本站</a></li>
			</ul>
		</div>
		<div class="eui-footer-p">
			<p>主办单位：福建省平潭综合实验区项目投资处 承办：福建省平潭综合实验区行政服务中心</p>
			<p>CopyRight © 2019 Pingtan China ， All Rights Reserved 闽ICP备09032658号 </p>
			<p>网站邮箱：admin@pingtan.gov.cn<span style="margin-left: 20px;">技术支持:0591-23162985</span></p>
		</div>
	</div>
</div>
<!--底部结束-->