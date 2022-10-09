<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<link href="webpage/main/css/jquery-accordion-menu.css" rel="stylesheet" type="text/css" />
<link href="webpage/main/css/fonticon.css" rel="stylesheet" type="text/css" />
<link href="webpage/main/css/eui-icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="webpage/main/css/scrollstyle.min.css"/>
<script src="webpage/main/js/jquery-accordion-menu.js" type="text/javascript"></script>
<script src="webpage/main/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<script type="text/javascript" src="webpage/main//js/mousewheel.min.js"></script>
<script type="text/javascript">
$(function(){	
	//changeCss();
	//$(".sc").perfectScrollbar();
	
})

function changeCss(){
	$("#jquery-accordion-menu").jqueryAccordionMenu();
	//顶部导航切换	
	$(".jquery-accordion-menu .jquery-accordion-submenu li").click(function(){
		$(".jquery-accordion-submenu li.eui-active").removeClass("eui-active")
		$(this).addClass("eui-active");
	})	
	$(".jquery-accordion-menu  li").click(function(){
		$(".jquery-accordion-menu li.eui-active1").removeClass("eui-active1")
		$(this).addClass("eui-active1");
	})	
	
}
</script>

<body style="background:#fdfeff;">
	<div class="eui-content clearfix">
	</div>
</body>
