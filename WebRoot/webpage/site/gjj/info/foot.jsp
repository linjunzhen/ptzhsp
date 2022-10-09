

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!-- 底部 -->
<div class="foot">
    <div class="foot_center center">
        <div class="foot_icon">
            <img src="<%=path%>/webpage/site/gjj/info/images/foot_icon.jpg" />
        </div>
        <div class="foot_copy">
            <p>主办单位：<span>平潭综合实验区住房公积金</span></p>
            <p>地址：<span>平潭县北厝镇金井湾大道区管委会行政服务中心一楼b区</span></p>
            <p>ICP备案号：<span>闽ICP备13015711号-1</span> 技术支持：<a href="http://www.vision-soft.cn/" style="color: #999;" target="_blank">远见信息</a> </p>
        </div>
    </div>
</div>
<div id="login_bg">
    <div>
        <p>
            Copyright 2010-2017<strong> <a href="http://www.vision-soft.cn">漳州市远见信息技术有限公司</a>
        </strong>版权所有 推荐IE8浏览器 技术支持：0596-2096781
        </p>

    </div>
</div>
<!-- 头部结束 -->