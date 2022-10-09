<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>用户注册</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
 <script type="text/javascript">
 	function clkType(type){
 		$("#userType").val(type);
 		$("#typeForm").submit();
 	}
 </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
            <form id="typeForm" method="post" action="wregisterController.do?registerSecond">
            	<input type="hidden" id="userType" name="userType" >
              <h3 class="eveTitle">选择注册类型</h3>
              <div class="card nomargin">
                  <ul class="listitem">
                    <li class="noborder">
                      <div class="grid evebtn" data-col="2">
                        <div class="grid-cell"><a href="javascript:void(0);" class="bgcolor2" onclick="clkType('1');"><img src="<%=path%>/webpage/weixin/images/icon.png"/> 个人用户</a></div>
                        <div class="grid-cell"><a href="javascript:void(0);" class="bgcolor3" onclick="clkType('2');"><img src="<%=path%>/webpage/weixin/images/icon1.png"/> 法人用户</a></div>
                      </div>
                    </li>
                  </ul>
                </div>
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