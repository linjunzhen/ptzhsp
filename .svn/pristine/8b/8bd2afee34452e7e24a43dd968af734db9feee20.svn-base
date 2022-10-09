<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>账号绑定</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    $(function() {
	AppUtil.initWindowForm("changeBindForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#changeBindForm").serialize();
			var url = $("#changeBindForm").attr("action");
			//$("#changeBindForm").submit();
			AppUtil.ajaxProgress({
					url : "userBindController.do?saveChangeInfo",
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							window.location.href="${pageContext.request.contextPath}/userBindController.do?toBind";
						} else {
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
	}, "T_LCJC_BUS_DEPLOY");
});	
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <form class="form-common tmargin20" method="post" id="changeBindForm" action="#">
                <div class="card">
                  <ul class="listitem">
                      <li>
                        <input type="password" placeholder="请输入原账号的密码" id="oldPwd" name="oldPwd" autocomplete="on" required class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="text" placeholder="请输入用户名" id="user_name" name="user_name" autocomplete="on" required class="noborder"  style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="password" placeholder="请输入密码" id="user_pwd" name="user_pwd" autocomplete="on" required class="noborder" style="width:100%;">
                      </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor" type="submit">立即绑定</button>
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