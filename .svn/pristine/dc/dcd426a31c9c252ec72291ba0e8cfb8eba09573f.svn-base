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
<title>找回密码--平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->

<!-- 引入验证库 -->
<eve:resources
	loadres="apputil,artdialog,json2,layer,validationegine,swfupload"></eve:resources>
<style type="text/css">
.badbtn {
	background: #ccc none repeat scroll 0 0;
	color: #fff;
	display: inline-block;
	font-weight: bold;
	height: 27px;
	line-height: 27px;
	margin-left: 10px;
	text-align: center;
	width: 84px;
}

a#hqyzm:hover {
	color: #fff;
}
</style>
<script type="text/javascript"> 
function one() {
    /* if (confirm("请确认信息是否正确")) {
        $("#psd").hide();
        $("#psd1").show();
    } */
    if($("#YHZH").val()==""){
    	alert("请输入用户名");
    	return;
    }
    if($("#validateCode").val()==""){
        alert("请输入验证码");
        return;
    }
    AppUtil.ajaxProgress({
        url : "userInfoController/zhsfcz.do",
        params : {yhzh:$("#YHZH").val(),validateCode:$("#validateCode").val()},
        callback : function(resultJson) {
          if(resultJson.success){
        	  $("#sjhm").html(resultJson.msg);
        	  $("#psd").hide();
              $("#psd1").show();  
          }else{
        	  alert(resultJson.msg);
        	  changeRandPic();
          }
        }
    });
}
function two() {
    /* if (confirm("请确认信息是否正确")) {
        $("#psd1").hide();
        $("#psd2").show();
    } */
	if($("#dxCode").val()==""){
        alert("请输入短信验证码");
        return;
    }
	AppUtil.ajaxProgress({
        url : "userInfoController/yzdxyzm.do",
        params : {yhzh:$("#YHZH").val(),yzm:$("#dxCode").val()},
        callback : function(resultJson) {
          if(resultJson.success){
        	  $("#psd1").hide();
              $("#psd2").show();
          }else{
              alert(resultJson.msg);
          }
        }
    });
}
function changeRandPic(){
    $("#randpic").attr({
          "src": "<%=path%>/rand.jpg?" + Math.random()
		});
	}
	var wait = 60;
	function time() {
		if (wait == 0) {
			$("#hqyzm").attr("class", "btn1");
			$("#hqyzm").html("获取验证码");
			$("#hqyzm").click(function() {
				time();
			});
			wait = 60;
		} else {
			if(wait==60){
				AppUtil.ajaxProgress({
			        url : "userInfoController/fsdx.do",
			        params : {yhzh:$("#YHZH").val()},
			        callback : function(resultJson) {
			          if(resultJson.success){
			              $("#tips").html(resultJson.msg); 
			          }else{
			              alert(resultJson.msg);
			          }
			        }
			    });
			}
			$("#hqyzm").attr("class", "badbtn");
			$("#hqyzm").removeAttr('onclick');
			$("#hqyzm").html("重新发送(" + wait + "s)");
			wait--;
			setTimeout(function() {
				time()
			}, 1000)
		}
	}
	$(function() {
        $("#czmm").validationEngine("attach", { 
            promptPosition:"topLeft",
            autoPositionUpdate:true,
            autoHidePrompt: true,            //自动隐藏提示信息
            autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
            fadeDuration: "0.5",             //隐藏提示信息淡出的时间
            maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
            showOneMessage: false,
            onValidationComplete:czmm
        }); 
    });
	function czmm(form, valid){
		if(valid){
            var formData = {};
            formData["DLMM"]=$("#dlmm").val();
            formData["yhzh"]=$("#YHZH").val();
            var url = $("#czmm").attr("action");
            AppUtil.ajaxProgress({
                url : url,
                params : formData,
                callback : function(resultJson) {
                  if(resultJson.success){
                	  alert(resultJson.msg);
                	  location.href = "<%=path %>/webSiteController/view.do?navTarget=website/yhzx/login";
                  }else{
                	  alert(resultJson.msg);
                  }
                }
            });
        }
	}
</script>
</head>

<body class="userbody">
	<%--开始编写头部文件 --%>
	<jsp:include page="../yhzx/head.jsp?currentNav=zhmm" />
	<%--结束编写头部文件 --%>
	<div class="container lbpadding20">
		<div class="main_t"></div>
		<div class="main clearfix">
			<div class="padding20">
				<div class="reg_tab clearfix">
					<ul>
						<li class="on">找回密码</li>
					</ul>
				</div>
				<div class="regcont">
					<div class="reg_con" id="psd" style="height:260px;">
						<div class="reg_menu">请确认您的用户名</div>
						<p>
							<span style="width:176px;">请输入您的用户名：</span><input type="text"
								maxlength="20" name="YHZH" id="YHZH">
						</p>
						<p>
							<span style="width:176px;">验证码：</span> <input type="text"
								class="validate[required]" name="validateCode" id="validateCode"
								style="width: 100px;" maxlength="5" /><label><img
								id="randpic" src="<%=path%>/rand.jpg" width="70" height="33" />
								<span onclick="changeRandPic();" style="cursor: pointer;">看不清?换一张</span></label>

						</p>
						<div class="find_btn">
							<a href="javascript:void(0)" onclick="one()">下一步</a>
						</div>
					</div>
					<div class="reg_con" id="psd1" style="height:260px; display:none;">
						<div class="reg_menu">找回密码的方式</div>
						<p>
							<span style="width:176px;">手机号：</span><span style="width:80px;" id="sjhm"></span>
							<label><a href="javascript:void(0)" class="btn1"
								onclick="time()" id="hqyzm">获取验证码</a></label>
						</p>
						<p>
							<span style="width:176px;">短信验证码：</span><input type="text" id="dxCode"
								style="width:100px"><label id="tips"></label>
						</p>
						<div class="find_btn">
							<a href="javascript:void(0)" onclick="two()">下一步</a>
						</div>
					</div>
					<div class="reg_con" id="psd2" style="height:260px; display:none;">
						<div class="reg_menu">重置密码</div>
						<form action="userInfoController/czmm.do" id="czmm" >
						<p>
							<span style="width:176px;">输入新的密码：</span><input type="password"
								id="dlmm" maxlength="20"
								class="eve-input validate[required,maxSize[20]],minSize[6],custom[Enpassword]"
								name="DLMM" />
						</p>
						<p>
							<span style="width:176px;">重复密码：</span><input type="password" maxlength="20"
                                    class="eve-input validate[equals[dlmm]]" name="QRDLMM" />
						</p>
						<div class="find_btn">
							<a href="javascript:void(0);" onclick="$('#czmm').submit();">确定</a>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<script type="text/javascript">
		jQuery(".nmainR").slide({
			titCell : ".nmainRtitle1 li",
			mainCell : ".nuserC"
		})
	</script>
	<%--结束编写尾部文件 --%>
</body>
</html>
