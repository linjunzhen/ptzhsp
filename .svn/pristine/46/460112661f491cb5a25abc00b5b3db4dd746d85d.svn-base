<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>用户注册-账户信息</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
  	<link type="text/css"
	href="plug-in/easyui-1.4/themes/default/easyui.css" rel="stylesheet">
	<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
	<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
  	<script type="text/javascript">
  	function sendCheckCode(){
  		var tel=$("#telephone").val();//document.getElementById("telephone").value;
  		var myreg =/^1\d{10}$/;// /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
  		//alert(myreg.test(te))
		if(!myreg.test(tel)){
			alert("请输入有效的手机号码！");
		}else{
			$.ajax({
				type : "post",
				url :'<%=path%>'+"/wregisterController.do?createCode",
				dataType : "json",
				data : {
					telephone : tel
				},
				async : false,
				success : function(data) {alert(data.sendCode);
					$("#checkCode").val(data.sendCode);
					$.messager.alert("系统提示", "发送成功", "info");
				},
				error:function(msg){
					//alert(JSON.stringify(msg));
					$.messager.alert("系统提示", "发送失败", "info");
				}
				});
		}
	}

function nextSecond(){
	$("input[type='submit']").attr("disabled", "disabled");
	var validCode=$("#validCode").val();
	var checkCode=$("#checkCode").val();/**
	if(!$("#userName").val() || $("#userName").val() == ''){
		$.messager.alert('警告',"用户名不能为空！");
		return false;
	}
	if(!$("#userPwd").val() || $("#userPwd").val() == ''){
		$.messager.alert('警告',"密码不能为空！");
		return false;
	}  **/
	if(validCode!=null&&validCode!=''){
		if(validCode==checkCode){
			$("#secondForm").submit();
		}else{
			$.messager.alert("系统提示", "验证码不正确，请重新输入", "info");
			return false;
		}
	}else{
		$.messager.alert("系统提示", "验证码不能为空", "info");
		return false;
	}
}
var rootPath='<%=path%>';
$(function() {
	AppUtil.initWindowForm("secondForm", function(form, valid) {
		if (valid) {
			if(!$("#userName").val() || $("#userName").val() == ''){
				$.messager.alert('警告',"用户名不能为空！");
				return false;
			}
			if(!$("#userPwd").val() || $("#userPwd").val() ==''){
				$.messager.alert('警告',"密码不能为空！");
				return false;
			}else if($("#userPwd").val()!=$("#rePwd").val()){
				$.messager.alert('警告',"两次输入密码不同,请重新输入！");
				return false;
			}
			if(!$("#telephone").val() || $("#telephone").val() == ''){
				$.messager.alert('警告',"手机号码不能为空！");
				return false;
			}
			if(!$("#validCode").val() || $("#validCode").val() == ''){
				$.messager.alert('警告',"验证码不能为空！");
				return false;
			}
			if($("#validCode").val()!=$("#checkCode").val()){
				$.messager.alert('警告',"验证码错误！");
				return false;
			}
			
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#secondForm").serialize();
			var url = $("#secondForm").attr("action");
			$("#secondForm").submit();
		}
	}, "T_LCJC_BUS_DEPLOY");
});	


</script>
</head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <header>
            <div class="titlebar">
              <a data-toggle="back" href="#"><i class="iconfont iconline-arrow-left"></i></a>
              <h1>用户注册-账户信息</h1>
            </div>
        </header>
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:44px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <form class="form-common" method="post" id="secondForm" action="wregisterController.do?registerThird">
              	<input type="hidden" id="userType" name="userType" value="${userType}">
              	<input type="hidden" id="checkCode" name="checkCode" >
                <div class="card">
                  <ul class="listitem">
                      <li>
                        <input type="text" placeholder="请输入用户名" id="userName" name="userName" class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="password" placeholder="请输入密码" id="userPwd" name="userPwd" class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="password" placeholder="请再次输入密码" id="rePwd" name="rePwd" class="noborder" style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="text" placeholder="请输入手机号码" id="telephone" name="telephone" class="noborder" style="width:100%;">
                      </li>
                  </ul>
                </div>
                <div class="tmargin20 clearfix">
                  <label class="label-right" style="border:1px solid #ddd; border-radius: 4px; padding:0 16px;">
                    <input type="text" placeholder="请输入验证码" id="validCode" name="validCode" style="width:100%; font-size:13.3333px;"/>
                  </label>
                  
                  	<input type="button" value="发送验证码" class="eve-button" onclick="sendCheckCode();" />
                  
                </div>
                <button class="block tmargin20 btnbgcolor" type="submit">下一步</button>
              </form>
            <div>
          </div>  
        </article>
      </section>
    </div>
    
    
  </body>
</html>