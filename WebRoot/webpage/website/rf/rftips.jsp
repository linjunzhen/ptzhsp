<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String projectCode = request.getParameter("projectCode");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>请先完善人防项目信息表</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<!--[if lte IE 6]> 
	<script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
<eve:resources loadres="artdialog,apputil"></eve:resources>
<style type="text/css">
.nmainRtitle1{height:45px; line-height:45px; margin-top:0px; color:#656565; font-size:14px; padding:0 20px; background:#f7fbfd;}
.nmainRtitle1 h3{margin:0px; padding:0px; color:#426fa4;}
</style>
<script type="text/javascript">
  $(function(){
	  $("a[class='btn2']").click(function(){
		  var url = "<%=basePath%>/webpage/website/rf/rfbaseinfo.jsp?projectCode=${projectCode}" ;
	        window.open(url,"_blank");
		  $.dialog.close();
	  });
  });
</script>
</head>

<body style="background: none; min-width:500px;">
     <div class="nmainRtitle1" >
        <h3>系统未检测到人防项目信息，填写本表格前，请先完善人防项目信息表！</h3>
    </div>
	<div class="Ctext clearfix lbpadding32">
                                    <a href="javascript:void(0)"  target="_blank"
                                        class="btn2">立 即 完 善</a>
                                </div>

</body>
</html>
