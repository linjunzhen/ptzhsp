<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
  </head>
  
  <body>
    <%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=qnwmh" /> 
    <%--结束编写头部文件 --%>
    
    <div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="#">首页</a> > <i>青年文明号</i></div>
    	<div class="listMain1 clearfix">
        	<div class="listL">
            	<div class="listicon"><img src="webpage/website/common/images/bsIcon12.png" /><p>青年文明号</p></div>
            </div>
            <div class="listR">
            	<div class="bslist">
                    <ul>
                        <li><a href="#">民办职业资格培训、职业技能培训学校设立审批</a></li>
                        <li><a href="#">开办外籍人员子女学校审批</a></li>
                        <li><a href="#">民办职业资格培训、职业技能培训学校设立审批</a></li>
                        <li><a href="#">省属中等职业学校和普通高中学生的学籍管理</a></li>
                        <li><a href="#">施工单位的主要负责人、项目负责人、专职安全生产管理人员安全及建筑施工特种操作资</a> </li>
                        <li><a href="#">会计师事务所及分支机构设立审批</a></li>
                        <li><a href="#">民办职业资格培训、职业技能培训学校设立审批</a></li>
                        <li><a href="#">施工单位的主要负责人、项目负责人、专职安全生产管理人员安全及建筑施工特种操作资</a> </li>
                    </ul>
                    <div class="page">
	                	<span>共 2770 条</span>
	                    <span>当前第 1/150 页</span>
	                    <a href="#">首页</a>
	                    <a href="#">上一页</a>
	                    <a href="#">下一页</a>
	                    <a href="#">末页</a>
	                </div>
                </div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
