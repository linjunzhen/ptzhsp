<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>账号绑定</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	function sexClick(sex){
    		if(sex=='1'){
    			$("#sexMale").removeClass("sex").addClass("sex sexOn");
    			$("#sexFemale").removeClass("sex sexOn").addClass("sex");
    			$("#YHXB").val(1);
    		}else{
    			$("#sexFemale").removeClass("sex").addClass("sex sexOn");
    			$("#sexMale").removeClass("sex sexOn").addClass("sex");
    			$("#YHXB").val(2);
    		}
    	}
    	
    $(function() {
	AppUtil.initWindowForm("changeForm", function(form, valid) {
		if (valid) {
			
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#changeForm").serialize();
			var url = $("#changeForm").attr("action");
			$("#changeForm").submit();
		}
	}, "T_LCJC_BUS_DEPLOY");
});	
function cancelBD(){
	art.dialog.confirm("您确定要取消绑定吗?", function() {			
		window.location.href = "<%=path%>/userBindController.do?cancelBD";
	})
 }
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
            <form class="form-common" method="post" id="changeForm" action="userBindController.do?toChangeBind">
              <h4 class="eveTitle1">已绑定账号：</h4>
              <h4 class="eveTitle1">${userName}</h4>
<%--              <button class="block tmargin20 btnbgcolor1" type="submit">更换绑定账号</button>--%>
<%--              <span class="right btncolor">--%>
<%--              	<a href="javascript:void(0);" class="fontcolor1" style="font-size: 15px;color: blue;" onclick="cancelBD();">取消绑定</a></span>--%>
              <button class="block tmargin20 btnbgcolor1"  type="button" onclick="cancelBD();">取消绑定</button>
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