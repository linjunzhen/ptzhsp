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
<title>查看结果</title>
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


<script type="text/javascript">
	function previewFile(filePath){
        //预览
        var previewPhotoStr =  "<img class='testImg' style='float:left;'  src='${_file_Server}"+filePath+"' width='214' height='318'/>";
		$("#previewPhoto").html(previewPhotoStr);
	}
</script>

</head>

<body style="background: none; min-width:800px;">
	<table cellpadding="0" cellspacing="0" class="zxtable2">
		<tr>
			<th width="50px">序号</th>
			<th >材料名称</th>
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
             <a href="javascript:void(0);"  onclick="previewFile('${file.FILE_PATH}');" ><font color="red">预览</font></a>
             </td>
         </tr>
         </c:forEach>
	</table>
	<center>
        <div id="previewPhoto" style="overflow-y:auto; width:1090px; height:319px;">
        </div>
	</center>

</body>
</html>
