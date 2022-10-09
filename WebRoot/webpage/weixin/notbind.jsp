<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>尚未绑定账号</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <header>
            <div class="titlebar">
              <a data-toggle="back" href="#"><i class="iconfont iconline-arrow-left"></i></a>
              <h1>尚未绑定账号</h1>
            </div>
        </header>
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:44px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <h4 class="eveTitle1">您尚未绑定账号</h4>
              <form class="form-common tmargin20">
                <div class="card">
                  <ul class="listitem">
                      <li>
                        <input type="text" placeholder="请输入用户名" class="noborder" style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="text" placeholder="请输入密码" class="noborder" style="width:100%;">
                      </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor">立即绑定</button>
              </form>
            <div>
          </div>  
        </article>
      </section>
    </div>
    
    <!--- third -->
    <script src="<%=path%>/webpage/weixin/agile-lite/third/zepto/zepto.min.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/iscroll/iscroll-probe.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/arttemplate/template-native.js"></script>
    <!-- agile -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/agile/js/agile.js"></script>   
    <!--- bridge -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/exmobi.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/agile.exmobi.js"></script>
    <!-- app -->
    <script src="<%=path%>/webpage/weixin/agile-lite/component/timepicker/agile.timepicker.js"></script> 
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/component/extend.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/app/js/app.js"></script>
    
  </body>
</html>