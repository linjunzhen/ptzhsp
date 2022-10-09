<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<title>福建省人大代表履职服务管理平台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="webpage/main/css/mainframe.css" />
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript">
   $(function(){
	   var curUserResKeys = $("#curUserResKeys").val();
	   if(curUserResKeys!="__ALL"){
		   if(curUserResKeys.indexOf("CmsManager")==-1){
			   $("#CmsManager").attr("href","javascript:void(0);");
			   $("#CmsManagerImg").attr("src","webpage/main/images/pt_system2_on.png")
			   $("#CmsManager").attr("onclick","showNoAuthoy();");
		   }
		   if(curUserResKeys.indexOf("SystemManager")==-1){
			   $("#SystemManager").attr("href","javascript:void(0);");
			   $("#SystemManagerImg").attr("src","webpage/main/images/pt_system5_on.png")
			   $("#SystemManager").attr("onclick","showNoAuthoy();");
		   }
		   if(curUserResKeys.indexOf("BillproposeManager")==-1){
			   $("#BillproposeManager").attr("href","javascript:void(0);");
			   $("#BillproposeManagerImg").attr("src","webpage/main/images/pt_system1_on.png")
			   $("#BillproposeManager").attr("onclick","showNoAuthoy();");
		   }
		   if(curUserResKeys.indexOf("PersonnelSubManager")==-1){
			   $("#PersonnelSubManager").attr("href","javascript:void(0);");
			   $("#PersonnelSubManagerImg").attr("src","webpage/main/images/pt_system_on.png")
			   $("#PersonnelSubManager").attr("onclick","showNoAuthoy();");
		   }
		   if(curUserResKeys.indexOf("MeetingSubManager")==-1){
			   $("#MeetingSubManager").attr("href","javascript:void(0);");
			   $("#MeetingSubManagerImg").attr("src","webpage/main/images/pt_system3_on.png")
			   $("#MeetingSubManager").attr("onclick","showNoAuthoy();");
		   }
		   if(curUserResKeys.indexOf("PerformManager")==-1){
			   $("#PerformManager").attr("href","javascript:void(0);");
			   $("#PerformManagerImg").attr("src","webpage/main/images/pt_system4_on.png")
			   $("#PerformManager").attr("onclick","showNoAuthoy();");
		   }
	   }
   });
   
   function showNoAuthoy(){
	   alert("抱歉,您没有访问该子系统的权限!");
   }
</script>
</head>

<body style="background:#e7e7e7;">
   <input type="hidden" id="curUserResKeys" name="curUserResKeys" value="${sessionScope.curLoginUser.resKeys}">
	<div class="pt_main">
		<div class="pt_logo">
			<img src="webpage/main/images/pt_logo.png" />
		</div>
		<div class="pt_content">
			<ul>
				<li>
					<a href="loginController.do?index&resKey=PersonnelSubManager" id="PersonnelSubManager">
					   <img src="webpage/main/images/pt_system.png" id="PersonnelSubManagerImg"/>
				    </a>
			    </li>
				<li class="pt_margin">
				    <a href="loginController.do?index&resKey=BillproposeManager" id="BillproposeManager">
				       <img src="webpage/main/images/pt_system1.png" id="BillproposeManagerImg"/>
				    </a>
				</li>
				<li>
				    <a href="loginController.do?index&resKey=CmsManager" id="CmsManager">
				     <img src="webpage/main/images/pt_system2.png" id="CmsManagerImg"/>
				    </a>
				</li>
				<li>
				    <a href="loginController.do?index&resKey=MeetingSubManager" id="MeetingSubManager">
				      <img src="webpage/main/images/pt_system3.png" id="MeetingSubManagerImg"/>
				     </a>
				</li>
				<li class="pt_margin"><a href="loginController.do?index&resKey=PerformManager" id="PerformManager">
				   <img src="webpage/main/images/pt_system4.png" id="PerformManagerImg" />
				   </a>
				</li>
				<li><a href="loginController.do?index&resKey=SystemManager" id="SystemManager"><img
						src="webpage/main/images/pt_system5.png" id="SystemManagerImg"/></a></li>
			</ul>
		</div>
		<br class="clearFloat" />
		<div class="pt_copyright">
			<p>版权所有：福建省人大常委会办公厅 新闻信箱：fjrd666@126.com</p>
			<p>技术支持：福建省人大常委会办公厅信息中心</p>
			<p>闽ICP备 05022555号</p>
		</div>
	</div>
</body>
</html>
