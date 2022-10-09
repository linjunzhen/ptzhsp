<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>平潭综合实验区行政服务中心-网上办事大厅</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!-- 引入验证库 -->
<eve:resources loadres="apputil,artdialog,json2,layer,validationegine"></eve:resources>
<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("USER_INFO_FORM", function(form, valid) {
			if (valid) {
				if($("#grdl").attr("class")=="on"){
	                $('#USER_TYPE').val(1);
	            }else if($("#qydl").attr("class")=="on"){
	                $('#USER_TYPE').val(2);
	            }
				var formData = $("#USER_INFO_FORM").serialize();
				var url = $("#USER_INFO_FORM").attr("action");
				AppUtil.ajaxProgress({
					url : "userInfoController/login.do",
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							if("${returnUrl}"!=""){
								window.top.location.href="${pageContext.request.contextPath}/${returnUrl}";
							}else{
								window.top.location.href=__newUserCenter;
								//window.top.location.href="${pageContext.request.contextPath}/webSiteController.do?usercenter";
							}
						} else {
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
							changeRandPic();
						}
					}
				});
			}
		}, "T_BSFW_USERINFO"); 
	});
	function changeRandPic(){
        $("#randpic").attr({
              "src": "<%=path %>/rand.jpg?"+Math.random()
         });
    }
	document.onkeydown=function(event)
	{
		  e = event ? event : (window.event ? window.event : null);
	   if(e.keyCode==13){
		   //执行的方法
		$('#USER_INFO_FORM').submit(); 
		   }
	}
	function loginyhzx(){
		var yhzh = $("#YHZH").val().Trim();
        if(yhzh==""||yhzh=="请输入用户名"||yhzh==null){
        	$("#YHZH").val("");
            $("#YHZH").focus();
           // return ;
        }
		var dlmm = $("input[name='DLMM']").val();
		if(dlmm!=""&&dlmm!=null&&dlmm.length>20){
			$("input[name='DLMM']").val("");
			alert("输入密码过长!");
			return;
		}
		$('#USER_INFO_FORM').submit();
	}
	String.prototype.Trim = function() 
    { 
        return this.replace(/(^\s*)|(\s*$)/g, ""); 
    };
	function mztLogin(){
		window.top.location.href="${pageContext.request.contextPath}/userInfoController/mztLogin.do";
	};
	function swbUserRegister(){
		window.top.location.href="${pageContext.request.contextPath}/userInfoController/swbRegister.do?user_type=user";
	};
	function swbeUserRegister(){
		window.top.location.href="${pageContext.request.contextPath}/userInfoController/swbRegister.do?user_type=euser";
	};
</script>
</head>

<body class="userbody">
	<jsp:include page="./head.jsp" />
	<form id="USER_INFO_FORM" action="#" method="post">
		<div class="container">
			<div class="login clearfix">
				<div class="loginL">
					<img src="webpage/website/common/images/loginImg.png" />
				</div>
				<div class="loginR">
					<div class="login_T">
						<ul>
							<li id="grdl">个人登录</li>
							<li id="qydl" style="border-right:none;">法人登录</li>
							<li id="swbdl" style="border-right:none;" onclick="mztLogin();" >闽政通登录</li>
						</ul>
					</div>
					<div class="login_C">
						<div>
							<div class="login_Ctop">
							    <input type="hidden" name="USER_TYPE" id="USER_TYPE" value="1" />
								<p class="login_inpN">
									<input type="text" maxlength="20"
										class="textinput validate[required]"
										name="YHZH" id="YHZH"  
										onfocus="if(this.value=='请输入用户名'){this.value='';}this.style.color='#999';"
                            value="请输入用户名" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/>
								</p>
								<p class="login_inpP">
									<input type="password" maxlength="20"
										class="validate[required]"
										name="DLMM"  autocomplete="off"
										placeholder="请输入密码"
                                        value=""/>
								</p>
								<p class="login_inpY">
									<input type="text" class="validate[required]" 
										name="validateCode" id="validateCode" style="width:146px" 
										placeholder="验证码" value=""><img
										id="randpic" src="<%=path%>/rand.jpg" width="120" height="45"
										style="float:right;cursor: pointer;" alt="换一张" onclick="changeRandPic();">
								</p>
								<a href="javascript:void(0);"
									onclick="loginyhzx();" class="login_dl"><img
									src="webpage/website/common/images/login_dl.png"></a>
							</div>
						</div>
					</div>
<%--					<div class="">--%>
<%--			            <a href="javascript:;" onclick="swbUserRegister();" >省网用户注册</a>--%>
<%--			            <a href="javascript:;" onclick="swbeUserRegister();" >省网企业用户注册</a>--%>
<%--					</div>--%>
					<div class="login_Bconn">
						<p>
							<a href="webSiteController/view.do?navTarget=website/yhzx/zhszmm" class="login_fgpsd">忘记密码？</a> 
							<%--没有账号？ --%>
<%--							<a href="webSiteController/view.do?navTarget=website/yhzx/regist">快速注册</a>--%>
<%--							|--%>
							<%--<a href="javascript:;" onclick="swbUserRegister();" >省网用户注册</a>--%>
						</p>
					</div>
					<span class="main_b"></span>
					<script type="text/javascript">
						jQuery(".loginR").slide({
							trigger:"click",
							titCell : ".login_T ul li",
							mainCell : ".login_C",
                            startFun:function(i,c){ 
                                if(i==0){
                                	$('#USER_TYPE').val(1);
                                }else if(i==1){
                                	$('#USER_TYPE').val(2);
                                }
                            }
						});
					</script>
				</div>
			</div>
		</div>
	</form>
	<jsp:include page="../index/foot.jsp" />
</body>
</html>
