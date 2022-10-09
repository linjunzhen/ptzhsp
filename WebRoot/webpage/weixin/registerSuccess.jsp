<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>

  <head>
    <title>注册成功</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta http-equiv="Expires" CONTENT="0">
	<meta http-equiv="Cache-Control" CONTENT="no-cache">
	<meta http-equiv="Pragma" CONTENT="no-cache">
	<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
  	<script type="text/javascript">
  			$(document).ready(function(e) {	
		if (window.history && window.history.pushState) {
			$(window).on('popstate', function () {
				window.history.pushState('forward', null, '#');
				window.history.forward(1);
			});
		}
		window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
		window.history.forward(1);
	});
  		function userBind(){
  			window.location.href = "<%=path%>/userBindController.do?toBind";
  		}
  	</script>
  	<%
 response.setHeader("Pragma","No-cache");
 response.setHeader("Cache-Control","No-cache");
 response.setDateHeader("Expires", -1);
 response.setHeader("Cache-Control", "No-store");
%>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <div class="succeed">恭喜！注册成功！</div>
              <div class="tmargin20 clearfix form-common">
                  <label class="label-right" style="background:#ebebeb; padding-left:0px;">
                    <button class="block btnbgcolor" onclick="userBind();">立即绑定账号</button>
                  </label>
                  <label class="label-left btnsend" style="background:#ebebeb;"><button class="disable" style="width:90%; float:right;">暂不绑定</button></label>
                </div>
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