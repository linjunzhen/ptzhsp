<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;
	request.setAttribute("webRoot", basePath);
	request.setAttribute("path", path);
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
		<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/selectSchedule.css">
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.min.js" ></script>
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
		<script type="text/javascript">
			function progressPage(itemList){
				$("#progressList").html("");
				var newhtml = "<table><tr>";
				newhtml +='<th width="35%">项目名称</th>';
				newhtml +='<th width="35%">项目编码</th>';
				newhtml +='<th width="15%">申报时间</th>';
				newhtml +='<th width="15%">项目类型</th></tr>';
				$.each( itemList, function(index, node){
					newhtml +='<tr>';
					newhtml +='<td><a target="_blank" href="<%=path%>/projectWebsiteApplyController.do?projectInfo&entityId='+node.ID+'">'+node.PROJECT_NAME+'</a></td>';
					newhtml +='<td>'+node.PROJECT_CODE +'</td>';
					newhtml +='<td>'+node.CREATE_TIME +'</td>';
					newhtml +='<td>'+node.TYPE_NAME +'</td>';
					newhtml +='</tr>';
				});
				newhtml += "</table>";
				$("#progressList").html(newhtml);
			}
			
			function progressQuery(){
				var projectName = document.getElementById("projectName").value;
				var projectCode = document.getElementById("projectCode").value;
				window.location.href = 
					"<%=path%>/projectWebsiteApplyController/search.do?projectName="+encodeURIComponent(projectName)+"&projectCode="+projectCode;
				
			}
			
			function projectInfo(id){
				window.location.href = 
					"<%=path%>/projectWebsiteApplyController.do?projectInfo&entityId="+id;
			}
		</script>
	</head>
	<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=sy" />
	<%--结束编写头部文件 --%>
		<div class="eui-main">
			<div class="eui-content">
				<div class="eui-crumbs">
					<ul>
						<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
						<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a> > </li>
						<li><a>项目查询</a> </li>
					</ul>
				</div>
				<div class="eui-9-progress">
					<div class="eui-9-progress-head">
						项目信息查询
					</div>
					<div class="eui-9-progress-input">
						<table>
							<tr>
								<td>项目名称</td>
								<td><input type="text" name="projectName" id="projectName" value="${projectName }" /></td>
								<td>项目编码</td>
								<td><input type="text" name="projectCode" id="projectCode" value="${projectCode }" /></td>
								<td><a class="eui-query-btn" onclick="progressQuery()">查询</a></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="eui-9-progress-table-1" id="progressList">
						
                </div>
				<div class="page">
					<jsp:include page="../website/common/page.jsp" >
						<jsp:param value="${path}/projectWebsiteApplyController.do?progressQuery&projectName=${encodeName }&projectCode=${encodeCode }&textValue=${encodeValue }" 
							name="pageURL"/>
						<jsp:param value="progressPage" name="callpage"/>
						<jsp:param value="10" name="limitNum"/>
						<jsp:param value="content" name="morename"/>
					</jsp:include>
                </div>
			</div>
			<%--开始编写尾部文件 --%>
			<jsp:include page="/webpage/website/newproject/foot.jsp" />
			<%--结束编写尾部文件 --%>
		</div>
		<!--内容结束-->
	</body>	
	<script>
		$(".eui-head li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".eui-nav li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".td1").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".td2").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
	<script type="text/javascript">
		jQuery(".slideTxtBox").slide({trigger:"click"});
	</script>
</html>
