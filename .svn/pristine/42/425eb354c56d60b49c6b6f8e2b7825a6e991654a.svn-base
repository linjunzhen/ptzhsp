<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
    <title>用户中心_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
	<eve:resources loadres="apputil,artdialog,json2,layer"></eve:resources>
<style>
.usernowinfo1 a {
    color: #ffa103;
}
</style>
<script type="text/javascript"> 
$(function(){
     //顶部导航切换
	$(".subnav li").click(function(){
		$(".subnav ul li").removeClass("subnavOn")
		$(this).addClass("subnavOn");
	})
	
	var username = '${sessionScope.curLoginMember.YHMC}';
	if(null==username||""==username){
		window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login";
	}
})
function TabHeads(c,a,b){
	$(c).click(function(){
		$(c).each(function(){
			$(this).removeClass(a)
		});
		$(b).each(function(){
			$(this).hide()
		});
		$(this).addClass(a);
		var d=$(c).index(this);
		$(b).eq(d).each(function(){
			$(this).show();
		});
	})
}
$(document).ready(function(){
	//TabHeads(".userClist li","usernowinfo1",".hideinfo");
});
function callpage(itemList){
	$("#wdzxlist").html("");
	var newhtml = "<ul>";
	$.each( itemList, function(index, node){
		newhtml +="<li>";
		newhtml +="<span>"+node.CREATE_TIME+"</span>";
		newhtml +="<a href=\"javascript:zxxx('"+node.CONSULT_ID+"')\">";
		newhtml +=node.CONSULT_TITLE
		if(node.REPLY_FLAG==1){
			newhtml += "<font color='green'>(已答复)</font>";
		}else{
			newhtml += "<font color='red'>(未答复)</font>";
		}
		newhtml +="</a>";
		// newhtml +="<div class=\"hideinfo clearfix\"><div class=\"useranswer\" style=\"min-height:42px\">";
		// if(node.REPLY_FLAG==1){
			// newhtml += node.REPLY_CONTENT;
		// }else{
			// newhtml += "<font color='red'>暂未答复</font>";
		// }
		// newhtml +="</div></div>";
		newhtml +="</li>";
	});
	newhtml += "</ul>";
	$("#wdzxlist").html(newhtml);
//	TabHeads(".userClist li","usernowinfo1",".hideinfo");
}

	function zxxx(entityId){
		$.dialog.open("consultController/yhzxInfo.do?entityId="+entityId, {
            title : "咨询信息",
            width : "800px",
            height : "510px",
            lock : true,
            resize : false,
            close: function () {
                reload();
            }
        }, false);
	}
</script> 
</head>

<body class="userbody">
<!-- header 0 -->
<script src="https://zwfw.fujian.gov.cn/thirdSys/header.js?unid=CDFF203D00F3BE60FD220C30B8C02EC9"></script>
<!-- header 1 -->

    <div class="container lbpadding20">
    	<div class="main_t"></div>
    	<div class="nmain clearfix">
        	<%--开始引入用户中心的左侧菜单 --%>
        	<jsp:include page="./yhmenu.jsp" >
        	    <jsp:param value="wdzx" name="menuKey"/>
        	</jsp:include>
        	<%--结束引入用户中心的左侧菜单 --%>
        	
            <div class="nmainR">
            	<div class="nmainRtitle" style="margin-top:0px;"><h3>我的咨询</h3></div>
				<!--列表DIV-->
                <div class="userClist" id="wdzxlist">                   
                </div>
                <div>
					<%--开始引入分页JSP --%>
					<jsp:include page="../common/page.jsp" >
					<jsp:param value="consultController.do?pagelist" name="pageURL"/>
					<jsp:param value="callpage" name="callpage"/>
					<jsp:param value="10" name="limitNum"/>
					</jsp:include>
					<%--结束引入分页JSP--%>
				</div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
