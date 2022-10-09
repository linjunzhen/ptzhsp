<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>办件评价</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	
		function viewZX(){
  			window.location.href = "<%=path%>/busInteractController.do?toZXList";
  		}
  		function myclick(obj){
  			//var txt=$(obj).find("option:selected").text();
  			$("input[name='SFMY']").val(obj);
  			$("a[id^='manyi']").removeClass("grid-cell evePjon").addClass("grid-cell");
  			$("#manyi"+obj).removeClass("grid-cell").addClass("grid-cell evePjon");
  		}
    $(function() {
	AppUtil.initWindowForm("bsreviewForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#bsreviewForm").serialize();
			var url ="busInteractController.do?savePjxx";// $("#bsreviewForm").attr("action");
			//$("#consultForm").submit();
			AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "评价成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								//resetForm();
								window.location.href = "<%=path%>/busInteractController.do?toReview";
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
            <form class="form-group form-common" method="post" id="bsreviewForm" >
            	<input type="hidden" name="PJ_ID" value="${bspj.PJ_ID}"> <input
				type="hidden" name="EXE_ID" value="${bspj.EXE_ID}"> <input
				type="hidden" name="FWSXBM" value="${bspj.FWSXBM}"> 
				<input type="hidden" name="SFMY" id="SFMY" value="1">
              <h3 class="eveTitle">评价事项</h3>
              <div class="card nomargin">
                <ul class="listitem">
                  <li class="noborder">
                    <div class="text">
                      	${bspj.ITEM_NAME}
                      <small>提交时间：${bspj.CREATE_TIME}<span class="right">完结时间：${bspj.END_TIME}</span></small>
                    </div>
                  </li>
                </ul>
              </div>
              <h3 class="eveTitle tmargin20">评价内容</h3>
              <div class="card nomargin">
                <ul class="listitem">
                  <li>
                    <div class="text" data-col="4">
                      <a href="#" id="manyi1" onclick="myclick('1')" class="grid-cell evePjon">满意</a>
                      <a href="#" id="manyi2" class="grid-cell" onclick="myclick('2')">基本满意</a>
                      <a href="#" id="manyi0" class="grid-cell" onclick="myclick('0')">不满意</a>
                      <a href="#" id="manyi3" class="grid-cell" onclick="myclick('3')">不了解</a>
                    </div>
                  </li>
                  <li class="noborder" style="">
			
                  </li>
                </ul>
				<div class="card nomargin">
                  	<textarea rows="5" cols="10" placeholder="其他评价内容" class="eve-textarea validate[maxSize[200]]"
							style="width: 100%;" name="PJNR"></textarea>
				</div>
			</div>
              <button class="block tmargin20 btnbgcolor" type="submit">提 交</button>
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