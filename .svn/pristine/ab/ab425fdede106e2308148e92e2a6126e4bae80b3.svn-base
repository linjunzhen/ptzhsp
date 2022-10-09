<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.DateTimeUtil"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

    request.setAttribute("MONTH",DateTimeUtil.getCurrentMonth());
	request.setAttribute("DAY",DateTimeUtil.getStrOfDate(new Date(), "dd"));
    request.setAttribute("WEEK",DateTimeUtil.getWeek(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")));
    String ip = request.getHeader("x-forwarded-for");   
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
        ip = request.getHeader("Proxy-Client-IP");          
    }   
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
        ip = request.getHeader("WL-Proxy-Client-IP");   
    }   
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
    } 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
        ip = request.getHeader("http_client_ip");   
    }   
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
        ip = request.getRemoteAddr();   
    }   
  // 如果是多级代理，那么取第一个ip为客户ip    
    if (ip != null && ip.indexOf(",") != -1) {    
        ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();   
    }  
	System.out.println(ip);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<title>欢迎登录平潭综合审批平台</title>
	<link rel="stylesheet" href="<%=basePath%>/webpage/system/login/css/facestyle.css" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery2/jquery.min.js"></script>
	<eve:resources loadres="layer,artdialog"></eve:resources>
	<script type="text/javascript">
		var InitFace = 1;
		$(function() {
			$(".rememberAcc").click(function() {
				if($(this).hasClass("on")) {
					$(this).removeClass("on");
					$(".facialAccount input").removeAttr("readonly");
				} else {
					$(this).addClass("on");
					$(".facialAccount input").attr("readonly","readonly");
				}
			});
			
			setTimeout("Start720P_onclick();",500);
		});
				
		function Start720P_onclick(){
			var str=captrue.bStopPlay();
			str=captrue.vSetResolution(2);//设置两个摄像头分辨率
			str=captrue.bStartPlay6(0);
	
			captrue.bStopPlay4();//
			captrue.bStartPlay4(1);//
			captrue.bSetMode(5);
			
			StartLR720_onclick();
		}
		function StartLR720_onclick(){
	        var str=captrue.bLivingBodyDetect(0.5,0.4);
			if(str){
				$("#action").html("识别成功,正在比对中……");
				captrue.bSaveJPG("D:\\","face");
				$.post("<%=basePath%>/sysUserController.do?allPhotoUser",{
					
			    },function(responseText, status, xhr) {
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							var obj =  JSON.parse(resultJson.jsonString);
							var username = "";
							for(var i=0;i<obj.length;i++){
								var url = "<%=basePath%>/"+obj[i].PHOTO_PATH;
								var str=captrue.lFaceMatchFromHttp(url,"D:\\face.jpg");
								if(str>8000){
									username = obj[i].USERNAME;
									break;
								}
							}
							if(username!=""){
								$("input[name='username']").val(username);
								$("#loginForm").attr("action","<%=basePath%>/"+resultJson.msg);
								$("#loginForm").submit();
							}else{
								$("#action").html("<font color='red'>未查询到匹配人员</font>");
							}
						}
				});
			}else{
				$("#action").html("<font color='red'>未识别到活体，请平视摄像头</font>");
				StartLR720_onclick();
			}
		}
	</script>
</head>
<body>
<object classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" id="locator" style="display:none;visibility:hidden"></object>
<object classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223" id="foo" style="display:none;visibility:hidden"></object> 
	<div class="loginPage">
		<form action="" name="loginForm" id="loginForm" method="post">
			<input name="username" type="hidden" value=""/>
 			<input type="hidden" id="userip" name="userip" value=""/>
			<input type="hidden" id="returnurl" name="returnurl" value="${returnurl}"/>
			<input type="hidden" id="token" name="token" value="${token}"/>
		</form>
		<h1 class="loginLogo"></h1>
		<div class="loginDate">
			<span class="text1">${MONTH }月${DAY }日</span>
			<span class="text2">${WEEK }</span>
		</div>
		<div class="loginBox">
			<div class="facialReco">
    			<object classid="clsid:454C18E2-8B7D-43C6-8C17-B1825B49D7DE" id="captrue"  width="380" height="285" ></object>
				<span class="cor cor1"></span>
				<span class="cor cor2"></span>
				<span class="cor cor3"></span>
				<span class="cor cor4"></span>
			</div>
			<div class="facialAccount" id="action">
				<!-- 账号：<input type="text" value="" readonly="readonly" style="width: 160px;" /> -->
				请保持头部居中，平视摄像头
			</div>
			<div class="loginLinks">
				<a href="<%=basePath%>/loginController.do?login" class="link rfloat">切换登录</a>
				<!-- <span class="lfloat rememberAcc on">
					<i class="checkbox"></i>记住账号
				</span> -->
			</div>
		</div>
	</div>
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