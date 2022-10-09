<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>办件评价</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
		function pjclick(id,code){
  			window.location.href = "<%=path%>/busInteractController.do?reviewInfo&exeId="+id+"&itemCode="+code;
  		}
  		function getTextv(obj){
  			var txt=$(obj).find("option:selected").text();
  			$("input[name='CONSULT_DEPT']").val(txt);
  		}
    $(function() {
	AppUtil.initWindowForm("consultForm", function(form, valid) {
		if (valid) {
			
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#consultForm").serialize();
			var url = $("#consultForm").attr("action");
			//$("#consultForm").submit();
			AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "提交成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								resetForm();
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
              <h3 class="eveTitle">我的办件</h3>
              <div class="card nomargin" style="font-size: 18px;">
              	<c:if test="${empty reviewList}">对不起，您尚未提交办理任何事项</c:if>
                  <ul class="listitem">
                  <c:forEach items="${reviewList}" var="reviewInfo" varStatus="status">
                  	<li>
                      <div class="text">
                        ${status.index+1}、${reviewInfo.ITEM_NAME}
                        <small class="lheight25">
                        	${reviewInfo.ZSTATE}
                        	<c:choose>
								<c:when test="${reviewInfo.isPj=='1'}">
									<a href="#" class="evePj bgcolor1">已评价</a>
								</c:when>
								<c:when test="${reviewInfo.RUN_STATUS=='0'||reviewInfo.RUN_STATUS=='1'}">
									<a href="#" class="evePj bgcolor1">评价</a>
								</c:when>
								<c:otherwise>
									<a href="#" class="evePj bgcolor" onclick="pjclick('${reviewInfo.EXE_ID}','${reviewInfo.ITEM_CODE}');">评价</a>
								</c:otherwise>
							</c:choose>
                        </small>
                      </div>
                    </li>
                  </c:forEach>
                  <!-- 
                    <li>
                      <div class="text">
                        1、外籍高层次人才和投资者签证、居留许可申请
                        <small class="lheight25">已办结<a href="#" class="evePj bgcolor">评价</a></small>
                      </div>
                    </li>
                    <li>
                      <div class="text">
                        2、民办职业资格培训、职业技能培训学校设立审批
                        <small class="lheight25"><span class="fontcolor1">办理中</span><a href="#" class="evePj bgcolor">评价</a></small>
                      </div>
                    </li>
                    <li class="noborder">
                      <div class="text">
                        3、开办外籍人员子女学校审批
                        <small class="lheight25">已办结<a href="#" class="evePj bgcolor1">已评价</a></small>
                      </div>
                    </li>   -->
                  </ul>
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