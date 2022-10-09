<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>用户注册-账户信息</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
  	<script type="text/javascript">
  	
  	/**
$(function() {
	AppUtil.initWindowForm("secondForm", function(form, valid) {
		if (valid) {
			var myreg=/^[0-9a-zA-Z-_]+$/;
			if(!myreg.test($("#userName").val())){
				$.messager.alert('警告',"用户名只能填写英文字母、数字、下划线！");
				return false;
			}
			
			if(!$("#userPwd").val() || $("#userPwd").val() ==''){
				$.messager.alert('警告',"密码不能为空！");
				return false;
			}else if($("#userPwd").val()!=$("#rePwd").val()){
				$.messager.alert('警告',"两次输入密码不同,请重新输入！");
				return false;
			}
			
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#secondForm").serialize();
			var url ="wregisterController.do?registerThird";// $("#secondForm").attr("action");
			//$("#secondForm").attr("action")=url;
			//$("#secondForm").submit();
			var userName=$("#userName").val();
			$.post("wregisterController.do?checkUser", {
					userName:userName},
			 	function(responseText, status, xhr) {
		        	var resultJson = $.parseJSON(responseText);
		        	if(resultJson.success){
		        		$("#secondForm").submit();
		        	}else{
		        		art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
		        	}
		        }	
		    );
		    
			AppUtil.ajaxProgress({
					url : "wregisterController.do?checkUser",
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							//$("#secondForm").attr("action",url).submit();
							$("#secondForm").submit();
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
});	  **/
function checkSubmit(){
	var userName=$("#userName").val();
	var myreg=/^[0-9a-zA-Z-_]+$/;
			if(!myreg.test($("#userName").val())){
				$.messager.alert('警告',"用户名只能填写英文字母、数字、下划线！");
				return false;
			}
			
			if(!$("#userPwd").val() || $("#userPwd").val() ==''){
				$.messager.alert('警告',"密码不能为空！");
				return false;
			}else if($("#userPwd").val()!=$("#rePwd").val()){
				$.messager.alert('警告',"两次输入密码不同,请重新输入！");
				return false;
			}
	
			$.post("wregisterController.do?checkUser", {
					userName:userName},
			 	function(responseText, status, xhr) {
		        	var resultJson = $.parseJSON(responseText);
		        	if(resultJson.success){
		        		$("#secondForm").submit();
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


</script>
</head>
  
  <body>
    <div id="section_container" >
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <form class="form-common" method="post" id="secondForm" action="wregisterController.do?registerThird">
              	<input type="hidden" id="userType" name="userType" value="${userType}">
              	<input type="hidden" id="checkCode" name="checkCode" >
                <div class="card">
                  <ul class="listitem" style="top:50px;">
                      <li>
                        <input type="text" placeholder="请输入用户名" id="userName" maxlength="20" autocomplete="on" required name="userName" class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="password" placeholder="请输入密码" id="userPwd" maxlength="20" autocomplete="on" required name="userPwd" class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="password" placeholder="请再次输入密码" id="rePwd" maxlength="20" autocomplete="on" required name="rePwd" class="noborder" style="width:100%;">
                      </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor" type="button" onclick="checkSubmit();">下一步</button>
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