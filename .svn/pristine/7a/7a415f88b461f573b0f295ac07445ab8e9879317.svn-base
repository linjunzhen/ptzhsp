<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>业务咨询</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
		
		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='hidden']").val("");
			$("input[type='password']").val("");
			$("textarea[name='CONSULT_CONTENT']").val("");
		}
$(function() {
});	
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> <!-- 
              <div class="form-common">
                <div class="card">
                  <ul class="listitem">
                      <li class="noborder">
                        <div class="text tbpadding10">区行政服务中心<span class="right fonts">2015-11-23</span></div>
                        <div class="eveDwen tbborder"><span class="bgcolor3">问</span><p>我想咨询下平潭有大学生等创业补助或者贷款吗？</p></div>
                        <div class="eveDwen" style="padding-bottom:0px;"><span class="bgcolor">答</span><p>很抱歉，目前大学生创业补助和贷款方面的工作没有在行政服务中心服务范围内，如果想了解具体的相关政策，建议你可以去区财政金融局咨询，谢谢！</p></div>
                      </li>
                  </ul>
                </div>
              </div>   -->
              
              <c:if test="${empty zxList}">
              	<div class="form-common tmargin20">
                <div class="card" align="center" style="font-size: large;">
                	暂无数据
                </div>
               </div>
              </c:if>
              <c:forEach items="${zxList}" var="zxInfo" varStatus="status">
              	<div class="form-common tmargin20">
                <div class="card">
                  <ul class="listitem">
                      <li class="noborder">
                        <div class="text tbpadding10">${zxInfo.CONSULT_DEPT}<span class="right fonts">${zxInfo.CREATE_TIME}</span></div>
                        <div class="eveDwen tbborder"><span class="bgcolor3">问</span><p>${zxInfo.CONSULT_CONTENT}</p></div>
                        <div class="eveDwen" style="padding-bottom:0px;"><span class="bgcolor">答</span><p>${zxInfo.REPLY_CONTENT}</p></div>
                      </li>
                  </ul>
                </div>
              </div>
              </c:forEach>
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