<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>账号绑定</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
   
    $(function() {
	AppUtil.initWindowForm("bindForm", function(form, valid) {
		if (valid) {
			if(!$("#user_name").val() || $("#user_name").val() == ''){
				$.messager.alert('警告',"用户名称不能为空！");
				return false;
			}
			
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#bindForm").serialize();
			var url = $("#bindForm").attr("action");
			$("#bindForm").submit();
			//return true;
			}
	}, "T_LCJC_BUS_DEPLOY");
});	  

function checkSubmit(){
	if(!$("#user_name").val() || $("#user_name").val() == ''){
				$.messager.alert('警告',"用户名称不能为空！");
				return false;
	}else{
		var userName=$("#user_name").val();
		var userpwd=$("#user_pwd").val();
		$.post("userBindController.do?checkUser", {
					userName:userName,
					userPwd:userpwd},
			 	function(responseText, status, xhr) {
		        	var resultJson = $.parseJSON(responseText);
		        	if(resultJson.success){
		        		$("#bindForm").submit();
		        	}else{
		        		art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
		        	}
		        }	
		    );
	}
}
</script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <h4 class="eveTitle1">用户名或密码错误，请重新输入!</h4>
              <form class="form-common tmargin20" method="post" id="bindForm" action="userBindController.do?bindSave">
                <div class="card">
                  <ul class="listitem">
                      <li>
                        <input type="text" placeholder="请输入用户名" id="user_name" name="user_name" autocomplete="on" required class="noborder" style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="password" placeholder="请输入密码" id="user_pwd" name="user_pwd"  autocomplete="on" required class="noborder" style="width:100%;">
                      </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor" type="submit" >立即绑定</button>
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