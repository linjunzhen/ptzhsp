<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>查看回执</title>
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

</head>

<body style="background: none; min-width:800px;">
	<table cellpadding="0" cellspacing="0" class="zxtable2">
		<tr>
			<th width="50px">序号</th>
			<th >材料名称</th>
			<th width="200px" style="background: none;">回执部门</th>
		</tr>
		<c:forEach items="${fileList}" var="file" varStatus="varStatus">
		<tr>
             <td>${varStatus.index+1}</td>
             <td>
             <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${file.FILE_ID}');">
             <font color="blue">
             ${file.FILE_NAME}
             </font>
             </a>
             </td>
             <td>${file.UPLOADER_DEPNAME}</td>
         </tr>
         
         </c:forEach>
              <c:forEach var="xfsj" items="${xfsjMap}" varStatus="varStatus">
        <tr>
             <td>${varStatus.index+1}</td>
             <td>
             <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${xfsj.FILE_ID}');">
             <font color="blue">
             ${xfsj.FILE_NAME}
             </font>
             </a>
             </td>
             <td>${xfsj.UPLOADER_DEPNAME}</td>
         </tr>
            </c:forEach>        
	</table>

</body>
</html>
