<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
    <title>用户中心--平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
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
	$("#wdpjlist").html("");
	var newhtml = "";
	$.each( itemList, function(index, node){
		newhtml +="<tr>";
		newhtml += "<td width=\"50px\" valign=\"top\">"+(index+1)+"</td>";
		newhtml += "<td width=\"140px\" valign=\"top\">"+node.EXE_ID+"</td>";
		newhtml += "<td  valign=\"top\" style=\"text-align:left;\">"+node.ITEM_NAME+"</td>";
		if(node.PJNR){
		newhtml += "<td width=\"200px\" valign=\"top\">"+node.PJNR+"</td>";
		}else{
			newhtml += "<td width=\"200px\"></td>";
		}
		if(node.SFMY==1){
			newhtml += "<td width=\"100px\" valign=\"top\"><font color='green'>满意</font></td>";
        }else{
            newhtml += "<td width=\"100px\" valign=\"top\"><font color='red'>不满意</font></td>";
        }
		newhtml += "<td width=\"100px\" valign=\"top\">"+node.CREATE_TIME.substr(0,10)+"</td>";
		newhtml +="</tr>";
	});
	$("#wdpjlist").html(newhtml);
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
        	    <jsp:param value="wdpj" name="menuKey"/>
        	</jsp:include>
        	<%--结束引入用户中心的左侧菜单 --%>
        	
            <div class="nmainR">
            	<div class="nmainRtitle" style="margin-top:0px;"><h3>我的评价</h3></div>
				<!--列表DIV-->
                <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
                    <tr>
                        <th width="50px">序号</th>
                        <th width="140px">申报号</th>
                        <th>事项名称</th>
                        <th width="200px">评价内容</th>
                        <th width="100px">满意度</th>
                        <th width="100px">提交时间</th>
                    </tr>
                 </table>
                 <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10" id="wdpjlist">
                 </table>
                <div>
					<%--开始引入分页JSP --%>
					<jsp:include page="../common/page.jsp" >
					<jsp:param value="bspjController.do?pagelist" name="pageURL"/>
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
