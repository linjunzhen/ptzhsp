<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>进度查询</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
		function viewZX(){
  			window.location.href = "<%=path%>/busInteractController.do?toZXList";
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
              <table id="cxjgtab" cellpadding="0" cellspacing="0" class="bstable2" style="border-collapse:separate ;display: none;" >
                    <tr>
                        <th>事项名称</th>
                        <th width="100px">提交时间</th>
                        <th width="200px">当前节点</th>
                        <th width="80px">状态</th>
                        <th width="150px">操作</th>
                    </tr>
                    <tr id="bscxList" >
                    </tr>
                </table>
            <div>
             <div id="bslc" style="margin-bottom: 20px;">
        	  </div>
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