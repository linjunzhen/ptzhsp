<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>自助取号系统</title>
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
	<script type="text/javascript" src="plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
    <link rel="stylesheet" href="plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
    <link rel="stylesheet" type="text/css" href="webpage/call/takeNo/cssnew/style.css">

	<script type="text/javascript">
		function realSysTime(clock) {
			var now = new Date(); //创建Date对象
			var year = now.getFullYear(); //获取年份
			var month = now.getMonth(); //获取月份
			var date = now.getDate(); //获取日期
			var day = now.getDay(); //获取星期
			var hour = now.getHours(); //获取小时
			var minu = now.getMinutes(); //获取分钟
			var sec = now.getSeconds(); //获取秒钟
			hour = checkTime(hour);
			minu = checkTime(minu);
			sec = checkTime(sec);
			month = month + 1;
			month = checkTime(month);
			date = checkTime(date);
			var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
					"星期六");
			var week = arr_week[day]; //获取中文的星期
			var day = year + "/" + month + "/" + date ;
			document.getElementById("day").innerHTML = day;
			var time = hour + ":" + minu; //组合系统时间
			clock.innerHTML = time; //显示系统时间
		}
		function checkTime(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		window.onload = function() {
			window.setInterval("realSysTime(clock)", 1000); //实时获取并显示系统时间
		}
		function requestFullScreen(element) { 
			var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen; 
			if (requestMethod) { 
				requestMethod.call(element); 
			} else if (typeof window.ActiveXObject !== "undefined") { 
				var wscript = new ActiveXObject("WScript.Shell"); 
				if (wscript !== null) { 
					wscript.SendKeys("{F11}"); 
				} 
			} 
		} 
		function screenFull(){ 
			var elem = document.documentElement; 
			requestFullScreen(elem); 
		}; 
		function exitFullscreen() {
				  if(document.exitFullscreen) {
				    document.exitFullscreen();
				  } else if(document.mozCancelFullScreen) {
				    document.mozCancelFullScreen();
				  } else if(document.webkitExitFullscreen) {
				    document.webkitExitFullscreen();
				  } else if(typeof window.ActiveXObject !== "undefined") { 
						var wscript = new ActiveXObject("WScript.Shell"); 
						if (wscript !== null) { 
							wscript.SendKeys("{F11}"); 
						} 
				  } 
		}
		var count = 0, timer;  
		$(function(){
			$("#day").bind("click",function(){
				count ++;
				if(count < 2){
					if(timer){
						clearTimeout(timer); 
					}
					timer = setTimeout(function(){count = 0;}, 600);
				}else if(count >= 2){
					count = 0;
					clearTimeout(timer);
					screenFull();
				}
			});
		});
	</script>
</head>
<body ondragstart="return false" oncontextmenu="return false" onselectstart="return false">
	<!-- 头部开始 -->
    <div class="eui-header" >
    	<div class="weather">
        	<!-- <div style="width:195px;height:96px;z-index:100;position:absolute;left:100;top:0;filter:alpha(opacity=0);-moz-opacity:0;"></div>
			<iframe allowtransparency="true" frameborder="0" width="195px" height="72px" scrolling="no" src="http://tianqi.2345.com/plugin/widget/index.htm?s=2&z=3&t=0&v=0&d=1&bd=0&k=&f=ffffff&q=0&e=0&a=0&c=60302&w=195&h=72&align=center"></iframe> -->
        </div>
        <div class="title">平潭综合实验区行政服务中心-自助取号系统</div>

        <div class="time">
        	<p class="clock" id="clock"></p>
        	<p class="day" id="day"></p>
        </div>
    </div>
	<!-- 头部结束 -->
    	
	<!-- 主页面开始 -->
    <div class="eui-content" style="height: 535px;">
	    <iframe id="takeFrame" frameBorder="0" width="100%" height="100%" 
	    marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" 
	    src="callController.do?toYctbTypeChoose&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&macType=${macType}&departId=${departId}"></iframe>
	    </iframe>
    </div>
	<!-- 主页面结束 -->
	<!-- 主页面开始 -->
<!--     <div class="eui-content"> -->
<!-- 	    <iframe id="takeFrame" frameBorder="0" width="100%" height="100%"  -->
<!-- 	    marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true"  -->
<!-- 	    src="newCallController/toYctbBusinessItemChoose.do?departId=${departId}&roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}"></iframe> -->
<!-- 	    </iframe> -->
<!--     </div> -->
	<!-- 主页面结束 -->
    
	<!-- 底部开始 -->
    <div class="eui-footer">   	
		<div style="text-align: center; color: #fff; width: 960px; padding-bottom: 5px;margin: auto;font-size:22px;font-weight: bold;">对窗口工作人员拒收材料或不岀具一次性告知函等服务不到位的行为，</div>
		<div style="text-align: center; color: #fff; width: 960px; margin: auto;font-size:22px;font-weight: bold;">请拨打12345热线投诉，我们将给您满意的答复。</div>
    	<div class="tips"><span class="fs16">温馨提示：</span>请在取号前，先准备好您的身份证，系统将识别您得身份信息并打印出排队单据。<span>(如需帮助，请询问前台向导。)</span></div>
        <div class="img"><img src="webpage/call/takeNo/imagesnew/foot-img.jpg"></div>
    </div>
	<!-- 底部结束 -->
</body>
</html>
