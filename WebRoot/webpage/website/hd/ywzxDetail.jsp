<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>业务咨询详情_平潭综合实验区行政服务中心</title>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete"></eve:resources>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>
	<!--[if lte IE 6]> 
	<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style>			
	.faq_detail{width:970px; margin:20px 0 5px 0;}
	.faq_detail table{width:100%; background:#a2a2a2;table-layout: fixed;}
	.faq_detail table tr th{width:110px; height:40px; padding:0 10px; text-align:right; background:#e2eff9;}
	.faq_detail table tr td{line-height:26px; background:#f6f9fc; padding:0 20px;word-break: break-all;}
	.word_break{margin:0px;padding:0px; white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;}
	</style>
</head>
<body>
	
    <%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
    <div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <a href="webSiteController/view.do?navTarget=website/hd/ywzxList">业务咨询列表</a> > <i>业务咨询详情</i></div>
        
        <div class="main clearfix" style="background: #fff;min-height: 424px;">
            <div class="detail">
                <div class="faq_detail">
					<table cellspacing="1" cellpadding="0">
						<tbody>
							<tr>
								<th>标题：</th>
								<td>${busConsult.CONSULT_TITLE} </td>
							</tr>
							<tr>
								<th>咨询内容：</th>
								<td><pre class="word_break">${busConsult.CONSULT_CONTENT} </pre></td>
							</tr>
							<tr>
								<th>咨询时间：</th>
								<td>${busConsult.CREATE_TIME} </td>
							</tr>
							<tr>
								<th>回复内容：</th>
								<td><pre class="word_break">${busConsult.REPLY_CONTENT} </pre></td>
							</tr>
							<tr>
								<th>回复时间：</th>
								<td>${busConsult.REPLY_TIME} </td>
							</tr>                    
						</tbody>
					</table>
					
				</div>     
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>