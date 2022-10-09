<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">
    function callpage(itemList){
    $("#listContainer").html("");
    var newhtml = "<ul>";
    for(var i=0; i<itemList.length; i++){
    newhtml += "<li><a target=\"_blank\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" >"+itemList[i].ITEM_NAME+"</a></li>"
    } 
     newhtml += "</ul>";
    $("#listContainer").html(newhtml);
    }
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a>  > <i>热门办事</i></div>
    	<div class="bsMain clearfix">
            <div class="bslist" id="listContainer"></div>
            <%--开始引入分页JSP --%>
		    <jsp:include page="../common/page.jsp" >
		    <jsp:param value="executionController/rdbsPagelist.do" name="pageURL"/>
		    <jsp:param value="callpage" name="callpage"/>
		    <jsp:param value="10" name="limitNum"/>
		    </jsp:include>
		    <%--结束引入分页JSP--%>
		    
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
