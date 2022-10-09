<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.DataAbutmentService"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <base href="<%=basePath%>">
	<title>高级搜索-平潭综合实验区行政服务中心</title>
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/base64-1.0/jquery.base64.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	
	<script type="text/javascript"> 
		function searchCallpage(itemList){
			$("#searchList").html("");
			var newhtml = "<ul>";
			$.each( itemList, function(index, node){
				newhtml +='<li>';
				newhtml +='<span>'+node.releaseTime+'</span>';
				newhtml +='<a href="contentController/view.do?contentId='+node.tid+'" target="_blank">'+node.contentTitle+'</a>';
				newhtml +='<p><a href="contentController/view.do?contentId='+node.tid+'" target="_blank">'+node.contentTextdelHTML+'</a></p>'
				newhtml +='</li>';
			});
			if(null==itemList||itemList.length<=0){
				newhtml += "<li style=\"text-align:center;background: rgba(0, 0, 0, 0) none repeat scroll 0 0;\"><font style=\"font-size:14px;\">暂无内容</font></li>";
				$("#searchPage").hide();
			}else{				
				$("#searchPage").show();
			}
			newhtml += "</ul>";
			$("#searchList").html(newhtml);
		}
		$(document).ready(function(){
			var key = '${key}';
			if(null!=key&&key!=''){
				search();
			}
		});
		function search(){
			$("#searchList").html("<ul><li style=\"text-align:center;background: rgba(0, 0, 0, 0) none repeat scroll 0 0;\"><font style=\"font-size:14px;\">数据加载中...</font></li></ul>");
			$("#searchPage").hide();
			reloadsearch();
		}
	</script>
</head>

<body>
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp?currentNav=sy" /> 
    <%--结束编写头部文件 --%>
	
	<div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do?navTarget=website/index/index">首页</a> > <i>高级搜索</i></div>
    	<div class="listMain1 clearfix">
        <div class="" style="padding:10px">
        	<div class="bsSearch">
			<form id="searchForm" method="post">
                <input type="text" name="key" value="${key}"/><a href="javascript:search();">搜 索</a>
			</form>
            </div>
            <div class="title5"><span>搜索结果</span></div>
			<div class="list5" id="searchList">                    	
			</div>
			<div class="page" id="searchPage">
				<%--开始引入分页JSP --%>
				<jsp:include page="../common/page.jsp" >
				<jsp:param value="luceneConfigController/searchlist.do?luceneName=Content" name="pageURL"/>
				<jsp:param value="searchCallpage" name="callpage"/>
				<jsp:param value="15" name="limitNum"/>
				<jsp:param value="search" name="morename"/>				
				<jsp:param value="searchForm" name="pageFormId"/>
				</jsp:include>
				<%--结束引入分页JSP--%>
			</div>
          
        </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="./foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
