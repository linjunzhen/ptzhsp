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
	<script type="text/javascript">
		var userType = '${sessionScope.curLoginMember.USER_TYPE}';
		$(function(){
			if(userType=='1'){
				alert("个人账号无法办理此事项，请登录法人账号!");
				location.href = "<%=path%>/userInfoController/logout.do?type=project";
			}
		});
	</script>
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
		window.location.href = "<%=path%>/userInfoController/mztLogin.do";
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
	$("#wdxmlist").html("");
	var newhtml = "";
	$.each( itemList, function(index, node){
		newhtml +="<tr>";
		newhtml += "<td width=\"50px\" valign=\"top\">"+(index+1)+"</td>";
		newhtml += "<td  valign=\"top\" style=\"text-align:left;\">"+node.PROJECT_NAME+"</td>";
		newhtml += "<td width=\"160px\" valign=\"top\">"+node.CREATE_TIME.substr(0,10)+"</td>";
		newhtml += "<td width=\"160px\" valign=\"top\"><a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" class=\"userbtn3\" onclick=\"xmbj('"
		        	+node.ID+"','"+node.PROJECT_CODE+"');\">编辑</a>";
        newhtml += "<a href=\"userProjectController.do?websiteItemList&Q_T.FWSXMXLB_NEQ=2&Q_C.ID_EQ="
        			+node.FLOW_CATE_ID+"&PROJECT_CODE="+node.PROJECT_CODE+"\""
        			+" class=\"userbtn3\" target=\"_blank\">事项列表</a> </td>";
		newhtml +="</tr>";
	});
	$("#wdxmlist").html(newhtml);
}
function xmbj(id,projectCode){
	window.location.href = "<%=path%>/projectWebsiteApplyController.do?websiteInfo&type=edit&entityId="+id+"&projectCode="+projectCode;
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
        	    <jsp:param value="wdxm" name="menuKey"/>
        	</jsp:include>
        	<%--结束引入用户中心的左侧菜单 --%>
        	
            <div class="nmainR">
            	<div class="nmainRtitle" style="margin-top:0px;"><h3>我的项目</h3></div>
				<!--列表DIV-->
                <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
                    <tr>
                        <th width="50px">序号</th>
                        <th>项目名称</th>
                        <th width="160px">创建时间</th>
                        <th width="160px">操作</th>
                    </tr>
                 </table>
                 <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10" id="wdxmlist">
                 </table>
                <div>
					<%--开始引入分页JSP --%>
					<jsp:include page="../common/page.jsp" >
					<jsp:param value="userProjectController.do?pagelist" name="pageURL"/>
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
