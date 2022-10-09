<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="footer"  style="background:#2f90e5;">
  <div class="footer_content">
<%--    <p>主办单位：福建省平潭综合实验区行政服务中心<br />--%>
<%--      Copyright 2015 PingTan China All Rights Reserved  闽ICP备xxxx号</p>--%>
	<p>主办单位：平潭综合实验区行政审批局 &emsp;
		承办单位：平潭综合实验区行政服务中心&emsp;
	</p>
	<p>
		网站标识码：3590000004 &emsp;
		<span>
		闽ICP备13016306号
		</span>
		&emsp;<img src="<%=request.getContextPath()%>/webpage/website/index/images/2020060301.png"/>闽公网安备 35012802000176号
		<br>
		地址：平潭综合实验区金井湾大道 邮编：350400&emsp; 技术支持电话：0591-23162985
	</p>
  </div>
</div>
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