<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<title>平潭综合实验区不动产登记与交易</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>
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

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>
	<div class="eui-main">	
	<!--头部-->
		<e:obj filterid="${content.ctid}" var="eobj" dsid="4">
		<div class="pub-con eui-crumb" data-aos="fade-up" style="width: 1260px;">
			<e:mcnav filterid="${eobj.module_id}"></e:mcnav>
		</div>
	<div class="pub-con in-box clearfix"  data-aos="fade-up" style="width: 1300px;">
	
	        <div class="eui-detail">	        
	            <div class="detail-title">
	                <h1>${eobj.content_title}</h1>
	                <div class="detail-title-sub">发布时间：<e:sub str="${eobj.release_time}" endindex="5" ellipsis="false"/>　来源：${eobj.comefrom }</div>
	            </div>
	            
	            <div class="detail-con" id="detail-con">
	            	<!-- 文章内容 开始 -->
	                <div align="" style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体;" id="zcontent">
	                    <c:if test="${eobj.contentType==3}">
		                <div align="center" >
		                    <object id="flv_id_1387197553849" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="/common/swflash.cab#version=11,9,900,170" height="600" width="800">
		                        <param name="movie" value="<%=path %>/common/swf/flvplayer.swf">
		                        <param name="quality" value="high">
		                        <param name="allowfullscreen" value="true">
		                        <param name="wmode" value="transparent">
		                        <param name="flashvars" value="file=<%=path %>/${eobj.linkurl}&image=<%=path %>/${eobj.titleimg}&autostart=false&provider=video">
		                        <embed type="application/x-shockwave-flash" src="<%=path %>/common/swf/flvplayer.swf" wmode="transparent" flashvars="file=<%=path %>/${eobj.linkurl}&autostart=&image=<%=path %>/${eobj.titleimg}&provider=video" quality="high" allowfullscreen="true" pluginspage="http://www.macromedia.com/go/getflashplayer" height="600" width="800">
		                    </object>
		                 </div>
	                </c:if>
                ${eobj.content_text}
	                </div>
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
	            	<!-- 文章内容 结束 -->
	            </div>
	            
	        </div>
        </div>
	
	        </e:obj>
	</div>	
	</div>	
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
	
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>
</body>
</html>