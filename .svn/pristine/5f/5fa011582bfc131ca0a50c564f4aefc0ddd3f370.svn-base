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
<html>
<head>
<title>${content.CONTENT_TITLE}</title>
<script type="text/javascript">
	var path = '<%=path%>';
</script>
    <base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete"></eve:resources>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/cms/js/indexinfo.js"></script>

	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwplayer.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwpsrv.js"></script>
	<!--[if lte IE 6]> 
	<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
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
<body style="background: #f0f0f0;">
	
    <%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" />
    <%--结束编写头部文件 --%>
    <div class="eui-main" style="margin-top:-20px;">
        <e:obj filterid="${content.ctid}" var="eobj" dsid="4">
        <input type="hidden" name="ctid" id="ctid" value="${eobj.tid}" />
    	<e:mcnav filterid="${eobj.module_id}"></e:mcnav>
        
        <div class="detailMain clearfix" style="min-height: 345px;">
            <div class="detail">
                <!--startprint1-->
            	<h1 style="text-align:center;">${eobj.content_title}</h1>
                <div class="detailTime">发布时间：<e:sub str="${eobj.release_time}" endindex="5" ellipsis="false"/>&nbsp;&nbsp;&nbsp;信息来源：${eobj.comefrom}&nbsp;&nbsp;&nbsp;点击数：<span style="color:#F00;">${content.HITS}</span>&nbsp;&nbsp;&nbsp;字号：<a id="font-small" href="javascript:void(0)" class="f14px">T</a>&nbsp;|&nbsp;<a id="font-big" href="javascript:void(0)" class="f16px">T</a>
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
					        image: "${_file_Server}${eobj.titleimg}",
					        file: "<%=path%>/${eobj.linkurl}",
							width : 800,
							height : 560
							});
						</script>
					</div>
				</c:if>
                <div id="content" class="clearfix">
                ${eobj.content_text}
                </div>
                <!--endprint1-->
                <br/><br/>
                <ul>
					<e:for filterid="${eobj.tid}" end="100" var="efor" dsid="13">
						<li style="margin-top: 5px;">
							附件${efor.rownum_}:<a style="color: blue;" href="${_file_Server}${efor.filepath}" title="${efor.filename}">
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
        </div>
         </e:obj>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>