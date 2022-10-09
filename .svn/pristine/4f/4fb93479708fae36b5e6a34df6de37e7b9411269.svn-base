<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
<base href="<%=basePath%>">
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${content.CONTENT_TITLE}</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwplayer.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwpsrv.js"></script>
<style>
	.ind_body{background: rgba(0, 0, 0, 0) none repeat scroll 0 0;}
</style>
<script>  
    //文章字体变化
    $(function() {
			$("#font-small").on("click", function() {
				$("#font-big").removeClass('sel');	
				$("#font-small").attr('class','f14px sel');			
				setFont('14');
				//$("#content").css("font-size", "14px");
			});
			$("#font-big").on("click", function() {
				$("#font-small").removeClass('sel');	
				$("#font-big").attr('class','f16px sel');			
				setFont('16');
			});
		});
		
		function setFont(size){
			var divBody = document.getElementById("content");
			if(!divBody){
				Return;
			}
		　　divBody.style.fontSize = size+"px";
		　　var divChildBody = divBody.childNodes;
		　　childBody(divChildBody,size);
		}
		function childBody(child,size){
			for (var i = 0; i<child.length;i++){
		　　    if(child[i].nodeType==1){
		　　        child[i].style.fontSize=size+"px";
					childBody(child[i].childNodes,size);
		　　	}
	　	　	}
		}
		/**
		*弹出页面
		*/
		function showOpen(key){
			window.open ('<%=path%>/front/search?key='+encodeURI(key), "newwindow");
		}
</script>  
</head>

<body class="ind_body" style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=${currentNav}" />
<div class="eui-main">

	<div class="eui-crumbs">
		<ul>
			<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
			<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
			<li style="font-size:16px">${module.MODULE_NAME}</li>
		</ul>
	</div>
	<e:obj filterid="${content.ctid}" var="eobj" dsid="4">	
	<div class="container" style="background:#fff; overflow:hidden; margin-bottom:20px;">
		<!--startprint1-->
		<div class="detail-title">
			<div class="detail-title-p1">${eobj.content_title} </div>
			<div class="detail-title-p3"><span>[${eobj.comefrom}]</span>
			<span><e:sub str="${eobj.release_time}" endindex="5" ellipsis="false"/></span>
			<span>字号：<a id="font-small" href="javascript:void(0)" style="font-size:12px;">T</a>
			&nbsp;|&nbsp;<a id="font-big" href="javascript:void(0)" style="font-size:16px;">T</a></span></div>
		</div>
		<c:if test="${eobj.contentType==3}">
			<div style="text-align: center; margin: 20px auto auto; width: 800px;">
				<div id="myElement1" align="center">
					<img src="<%=path%>/webpage/common/images/nopic.jpg" />
				</div>
				<script type="text/javascript">
				jwplayer("myElement1").setup({
					primary: "flash",
					skin: "<%=path%>/plug-in/jwplayer/skins/white.xml",
					image: "<%=path%>/${eobj.titleimg}",
					file: "<%=path%>/${eobj.linkurl}",
					width : 800,
					height : 560
					});
				</script>
			</div>
		</c:if>
		<div class="detail-content">
			<div class="detail-content-p" id="content">
				${eobj.content_text}
			</div>
		</div>
		<!--endprint1-->
		<ul style="padding: 0 20px 20px;">
			<e:for filterid="${eobj.tid}" end="100" var="efor" dsid="13">
				<li style="margin-top: 5px;">
					附件${efor.rownum_}:<a style="color: blue;" href="javascript:AppUtil.downLoadFile('${efor.fileid}');" title="${efor.filename}">
						${efor.filename}
					</a>
				</li>
			</e:for>
		</ul>
		<div class="detailShare" >
		   
			<div class="rfloat" style="width:420px;">
				
				<!-- Baidu Button END -->
				<div>
					<a href="javascript:preview(1);" class="dy">打印</a>
				</div>
			</div>
		</div>  
	</div>
	</e:obj>
</div>

<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
