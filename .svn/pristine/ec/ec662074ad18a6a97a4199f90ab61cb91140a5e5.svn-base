<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.core.util.DateTimeUtil"%>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java"
	import="net.evecom.platform.system.model.SysUser"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    SysUser currentUser = (SysUser)request.getSession().getAttribute("curLoginUser");
    if(currentUser!=null){
        response.sendRedirect("loginController.do?checkUser");
    }
    String username = request.getParameter("username");
    if(StringUtils.isNotEmpty(username)){
        request.setAttribute("username", username);
    }else{
        request.setAttribute("username","");
    }
    request.setAttribute("HOUR",DateTimeUtil.getStrOfDate(new Date(), "HH"));
    request.setAttribute("MINUE",DateTimeUtil.getStrOfDate(new Date(), "mm"));
    request.setAttribute("MONTH",DateTimeUtil.getCurrentMonth());
    request.setAttribute("DAY",DateTimeUtil.getStrOfDate(new Date(), "dd"));
    request.setAttribute("WEEK",DateTimeUtil.getWeek(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>欢迎登录平潭综合审批平台</title>
<link href="webpage/system/login/css/login.css" rel="stylesheet"
	type="text/css" />
	<link rel="stylesheet" href="webpage/system/login/css/global.css">
	<link rel="stylesheet" href="webpage/system/login/css/animate.css">
	<link rel="stylesheet" href="webpage/system/login/css/toast.css">	
	<link rel="stylesheet" href="webpage/system/login/css/centermenu.css">
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
 <script type="text/javascript" src="plug-in/passJs/des.js" charset="UTF-8"></script> 

<script src="webpage/system/login/js/toast.js"></script>
<script src="webpage/system/login/js/centermenu.js"></script>
<script src="webpage/system/login/js/qrcode.js"></script>
<script type="text/javascript">
var vIp;
    $(function(){
    	AppUtil.checkBrowserVersion();
    	$("#username").focus();
    });
    function changeRandPic(){
    	$("#randpic").attr({
	          "src": "<%=path %>/rand.jpg?"+Math.random()
	     });
    }
  /**
	 * 提交登录表单
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	function submitLogin(loginFormId,isNeedValidCode){
		var username = $("#username").val();
		var password = $("#password").val();
		if(!(username!=null&&username!="")){
			alert("请输入用户帐号!");
			return;
		}
		if(!(password!=null&&password!="")){
			alert("请输入密码!");
			return;
		}else if(password.length>32){
			alert("输入密码过长!");
			return;
		}
		if(isNeedValidCode){
			var validateCode = $("#validateCode").val();
			if(!(validateCode!=null&&validateCode!="")){
				alert("请输入验证码!");
				return;
			}
		}
		$("#password").val(strEnc(password,"1","2","3"));
		$("#"+loginFormId).submit();
	}
     function changesImg(obj){
         var timestamp = (new Date()).valueOf();
         obj.src = "<%=path%>/servlet/regCode?timestamp="+timestamp;
		  /*	obj.src = "<%=path%>/Kaptcha.jpg?" + Math.floor(Math.random() * 100); */
     }
	/**
	 * 进入系统
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	function enterSystemNew(loginFormId,isNeedValidCode){
		//判断浏览器是否为ie6,7,8
	    if (!$.support.leadingWhitespace) {
	       if(event.keyCode == 13){
		    	submitLogin(loginFormId,isNeedValidCode);
		    }
	    }else{
	    	event=arguments.callee.caller.arguments[0] || window.event; 
		    if(event.keyCode == 13){
		    	submitLogin(loginFormId,isNeedValidCode);
		    }
	    }
	}
	
	function toFaceLogin(){
		var token = $("[name='token']").val();
		var returnurl = $("[name='returnurl']").val();
		var url = "loginController.do?facelogin";
		if(token){
			url+="&token="+token;
		}
		if(returnurl){
			url+="&returnurl="+returnurl;
		}
		location.href=url;
	}
	var sl;
	var num = 60;
	function toScanLogin(){
		num = 60;
		$("#smdl_Text").html('');
		$.post("scanCodeLoginController/getLoginQRCode.do",{},
		function(responseText, status, xhr) {
            var resultJson = $.parseJSON(responseText);
			$("#zhdl").hide();
			$("#zhdl_div").show();
			$("#smdl").show();
			$("#smdl_div").hide();
			//$("#scanImg").attr("src",resultJson.path);
			
			$("#smdl_qrcode").html("");
			var qrcode = new QRCode(document.getElementById("smdl_qrcode"), {
				text: resultJson.scanStr,
				width: 200, //生成的二维码的宽度
				height: 200, //生成的二维码的高度
				colorDark : "#000000", // 生成的二维码的深色部分
				colorLight : "#ffffff", //生成二维码的浅色部分
				correctLevel : QRCode.CorrectLevel.H
			  });
			$("#smdl_qrcode").attr("title","点击二维码换一张");
			$("#scanToken").val(resultJson.token);
			if(sl){
				clearInterval(sl);
			}
			sl = setInterval("isSacnCode()",1000);//每秒轮询
		});
	}
	function toUserLogin(){
		$("#zhdl").show();
		$("#smdl").hide();
		$("#zhdl_div").hide();
		$("#smdl_div").show();
		$("#scanToken").val('');
		if(sl){
			clearInterval(sl);
		}
	}
	function isSacnCode(){
		if(num==0){		
			$("#smdl_Text").html('二维码已过期，请点击<a href="javascript:;" onclick="toScanLogin();" class="btn1"><font color="red">刷新</font></a>');
			if(sl){
				clearInterval(sl);
			}
		} else{
			//$("#smdl_Text").html('登录二维码将在<font color="red">'+num+'</font>秒后过期');
			num--;
			$.post("scanCodeLoginController/isSacnCode.do",{
				scanToken:$("#scanToken").val()
			},
			function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					if(sl){
						clearInterval(sl);
					}
					if(resultJson.type==1){		
						$("#smdl_Text").html('')			
						if(resultJson.list.length==1){	
							scanLogin(resultJson.list[0].USERNAME);
						} else if(resultJson.list.length>1){	
							centermenu(resultJson.list);
						} else{
							alert("未查询到匹配用户");
							toScanLogin();
						}
					} else{
						alert(resultJson.msg);
						toScanLogin();
					}
				}
			});
		}
	}
	
	function scanLogin(uname){
		$("[name='uname']").val(uname);
		$("#scanLoginForm").submit();
	}
	
	function centermenu(obj){
		var array=[];
		 for (var i = 0; i < obj.length; i++) {
			 array.push(obj[i].USERNAME);
		 }
		$('body').centermenu({
			animateIn:'fadeInDownBig-hastrans',
			animateOut:'fadeOutDownBig-hastrans',
			hasLineBorder:false,
			duration:600,
			source:array,
			click:function(ret){
				scanLogin(ret.text)
			}
		});
	}
</script>
<style>

</style>
</head>

<body class="loginBg">
<form action="loginController.do?checkUser" name="scanLoginForm"
		id="scanLoginForm" method="post">
	<input type="hidden" id="scanToken" name="scanToken"/>
	<input type="hidden" id="uname" name="uname"/>
</form>
<object classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" id="locator" style="display:none;visibility:hidden"></object>
 <object classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223" id="foo" style="display:none;visibility:hidden"></object> 
	<form action="loginController.do?checkUser" name="loginForm"
		id="loginForm" method="post">
 		<input type="hidden" id="userip" name="userip" value=""/>
 		<input type="hidden" id="returnurl" name="returnurl" value="${returnurl}"/>
 		<input type="hidden" id="token" name="token" value="${token}"/>
		<div class="loginCon">
    	<div class="login-time">
        	<span id="login_hour">${HOUR}</span><span id="login_minute" style="margin-left:12px;">${MINUE}</span>
        </div>
        <div class="login-year">
        	<p id="login_date">${MONTH}月${DAY}日</p>
	    	<p id="login_weekDay">${WEEK}</p>
        </div>
        <div class="login-logo"><img src="webpage/system/login/images/logo.png" /></div>
        <div class="logininfo">
			<div id="zhdl">
				<p class="input-name"><input type="text" name="username" id="username" value="${username}" maxlength="32" 
				   onkeypress="enterSystemNew('loginForm',true);"
				style="width:246px" /></p>
				<p class="input-psd"><input type="password" id="password" name="password"  autocomplete="off"
				 onkeypress="enterSystemNew('loginForm',true);" maxlength="32" 
				 style="width:246px"/></p>
				<p class="input-yzm"><input type="text" name="validateCode" id="validateCode" onkeypress="enterSystemNew('loginForm',true);"  maxlength="5" style="width:116px"/>
				<img title="点击图片换一张" onclick="changesImg(this);" style="cursor: pointer;" id="randpic" src="<%=basePath%>/servlet/regCode" /></p>
				<!-- <a href="javascript:;" onclick="submitLogin('loginForm',true);" class="login-btn"></a> -->
				<div class="eui-btn">
					<a href="javascript:;" onclick="submitLogin('loginForm',true);" class="btn">登录</a>
					<a href="javascript:;" onclick="toFaceLogin();" class="btn1">刷脸登录</a>
				</div>
			</div>
			<div id="smdl" style="display:none; margin-bottom: 20px;">
				<div id="smdl_Text" style="text-align:right;margin-bottom:15px;color:#fff"></div>
				<div id="smdl_qrcode" style="margin: auto;width: 45px;" title="点击二维码换一张" onclick="toScanLogin();"></div>
			</div>
			<div class="eui-btn"  id="smdl_div">
	            <a href="javascript:;" onclick="toScanLogin();" class="btn1">扫码登录</a>
            </div>
			<div class="eui-btn" style="display:none;" id="zhdl_div">
	            <a href="javascript:;" onclick="toUserLogin();" class="btn1">账号登录</a>
            </div>
        </div>
    </div>	
	</form>
<script language="javascript">
 var sMacAddr="11";
 var sIPAddr="";
 var sDNSName="";
 var service = locator.ConnectServer();
 service.Security_.ImpersonationLevel=3;
 service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
 </script>
 <script FOR="foo" EVENT="OnObjectReady(objObject,objAsyncContext)" LANGUAGE="JScript">
	if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true){
    	if(objObject.IPEnabled && objObject.IPAddress(0) !=null && objObject.IPAddress(0) != "undefined"){
    		if(sIPAddr==null||sIPAddr==""){
            	sIPAddr = objObject.IPAddress(0);
    		}
        }
    }
	$("#userip").val(sIPAddr);
 </script>
</body>
</html>
