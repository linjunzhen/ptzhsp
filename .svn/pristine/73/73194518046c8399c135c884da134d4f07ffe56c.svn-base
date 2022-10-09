<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils" %>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ page language="java"
	import="net.evecom.platform.system.model.SysUser"%>
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>平潭审批平台行政机关人员办公门户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="msthemecompatible" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>

<link rel="stylesheet" type="text/css" href="webpage/portal/css/style.css"/>
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript" src="webpage/portal/js/portal.js"></script>
<style type="">
.Mout1{float:left; height:44px; line-height:44px;}
.Mout1 a{display:block; padding:0 0 0 0px;color:#fff;font-weight:900}
</style>
<script type="text/javascript">
   $(function(){
		//窗口改变大小事件
		$(window).resize(function() {
			var height = $(this).height();
			windowHeight(height);
		});
		//初始化右边滚动位置
		var paddingTop = $(window).height() - 511;
		$('.sysMain').css('padding-top',paddingTop/2+'px'); 
	});
	function windowHeight(height){
		var paddingTop = height - 511;
		$('.sysMain').css('padding-top',paddingTop/2+'px'); 
	}
</script>

</head>

<body class="sysMainbg" >
	<div class="header">
    	<div class="logo"><img src="webpage/portal/images/logo.png" /></div>
        <div class="headerR">
        	<div style="float:left; height:44px; line-height:44px;" ><a  class="mainCbox4" href="otherSystemController.do?ssxtLogin" target="_blank">
        	<span style="padding-top:98px;color:#fff;cursor:pointer;font-weight:900;">商事系统</span></a></div>
        	<div class="sysName"><img src="webpage/portal/images/sys_name.png" /> ${sessionScope.curLoginUser.fullname}</div>
            <div class="Mout"><a href="javascript:void(0);" onclick="showExitWin();" style="cursor:pointer;">退出系统</a></div>
        </div>
    </div>
    <div class="sysMain clearfix">
    	<div class="MainL">
        	<div class="mainLcon">
            	<div class="mainLtitle"><a href="#">更多>></a><span>预受理</span></div>
                <div class="mainLi">
                	<ul>
                    	<li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">开教育实践活动专题民生生活会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                    </ul>
                </div>
            </div>
            <div class="mainLcon tmaring">
            	<div class="mainLtitle"><a href="#">更多>></a><span class="wzcolor">待办业务</span></div>
                <div class="mainLi">
                	<ul>
                    	<li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">开教育实践活动专题民生生活会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                        <li>[2015/10/13] <a href="#">市政府常务委员会</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="MainCimg"><img src="webpage/portal/images/img.png" /></div>
        <div class="MainC">
        	<div class="mainCone">
            	<a href="loginController.do?portalToIndex&reskey=wsbsgzfwpt" target="_blank" class="mainCbox"><span style="padding-top:98px;">网上办事<br />公众服务平台</span></a>
            </div>
            <div class="mainCone tmaring">
            	<a  class="mainCbox1" href="loginController.do?portalToIndex&reskey=zhspxt" target="_blank"><span>综合审批系统</span></a>
            </div>
            <div class="mainCone tmaring1">
            	<a href="loginController.do?portalToIndex&reskey=ywzcpt" target="_blank" class="mainCbox2"><span>业务支撑平台</span></a>
            </div>
        </div>
        <div class="MainR">
        	<div class="mainRone">
            	<a  class="mainCbox3" href="loginController.do?portalToIndex&reskey=spzhfwglfbxt" target="_blank"><span>审批综合服务管理分发系统</span></a>
            </div>
            <div class="mainRone tmaring">
            	<a  class="mainCbox4" href="loginController.do?portalToIndex&reskey=qlyxdzjcxt" target="_blank"><span>权利运行电子监察系统</span></a>
            </div>
        </div>
    </div>
</body>
</html>
