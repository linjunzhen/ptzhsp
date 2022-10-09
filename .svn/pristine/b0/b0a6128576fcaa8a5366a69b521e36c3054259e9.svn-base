<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>监督投诉</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	function getTextv(obj){
  			var txt=$(obj).find("option:selected").text();
  			$("input[name='COMPLAIN_DEPT']").val(txt);
  		}
  		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='hidden']").val("");
			$("textarea[name='COMPLAIN_CONTENT']").val("");			
		};
    $(function() {
	AppUtil.initWindowForm("complainForm", function(form, valid) {
				if (valid) {
					var formData = $("#complainForm").serialize();
					var url = $("#complainForm").attr("action");
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
			}, "T_HD_COMPLAIN");
});	
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <form class="form-group form-common" method="post" id="complainForm" action="busInteractController.do?tousuSave">
                <div data-role="select" class="card" style="background:#fff;">
                <input type="hidden" name="COMPLAIN_DEPT">
                  <eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="OrgType" onchange="getTextv(this);"
                                    dataInterface="departmentService.findDatasForSelect"
                                    defaultEmptyText="=====请选择投诉单位====" name="COMPLAIN_DEPTID" id="COMPLAIN_DEPTID"></eve:eveselect>
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="请输入投诉标题" id="COMPLAIN_TITLE" name="COMPLAIN_TITLE" 
                  			class="validate[required],maxSize[120]" maxlength="120" style="width:100%;">
                </div>
                <div class="card tmargin20" style="background:#fff;">
                  <textarea rows="10" name="COMPLAIN_CONTENT" class="validate[required],maxSize[500]" style="padding:8px;" placeholder="请输入投诉内容"></textarea>
                </div>
                <button class="block tmargin20 btnbgcolor" style="font-size: 15px;" type="submit">提交投诉</button>
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