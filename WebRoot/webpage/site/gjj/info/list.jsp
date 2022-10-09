<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.DataAbutmentService"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${module.MODULE_NAME}_平潭综合实验区住房公积金</title>
    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/base64-1.0/jquery.base64.js"></script>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/gjj/info/css/eui.css">
	<!--[if lte IE 6]> 
	<script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript"> 
		function contentCallpage(itemList){
			$("#contentList").html("");
			var newhtml = "<ul>";
			$.each( itemList, function(index, node){
				newhtml +='<li>';
				newhtml +='<a target="_blank" href="contentController/view.do?contentId='+node.TID+'">'+node.ITEMTITLE+'</a>';
				newhtml +='<span>'+node.ITEMRELDATE+'</span>';
				newhtml +='</li>';
			});
			newhtml += "</ul>";
			$("#contentList").html(newhtml);
		}
	</script>
</head>

<body>
    <%--开始编写头部文件 --%>
    <jsp:include page="head.jsp?currentNav=${currentNav}" />
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
		<e:mcnav filterid="${module.MODULE_ID}"></e:mcnav>
    	<div class="listMain clearfix">
        	<div class="listL">
				<e:obj filterid="${module.PARENT_ID}" var="eobj" dsid="1">
					<div class="listTitle">${eobj.headName}</div>
					<div class="sublist">
						<ul>
						<e:for dsid="7" filterid="${module.PARENT_ID}" end="200" var="efor">
							<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
								<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
									<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
								</a>
							</li>
						</e:for> 
						</ul>
					</div>					
					<img src="<%=path%>/webpage/website/common/images/listbgL.png" style="margin-top:30px" />
				</e:obj>
            </div>
            <div class="listR">
            	<div class="list2" id="contentList">
                </div>
                <div class="page">
                	<%--开始引入分页JSP --%>
					<jsp:include page="/webpage/website/common/page.jsp" >
					<jsp:param value="contentController/pagelist.do?moduleId=${module.MODULE_ID}&endindex=42&timeout=2" name="pageURL"/>
					<jsp:param value="contentCallpage" name="callpage"/>
					<jsp:param value="15" name="limitNum"/>
					<jsp:param value="content" name="morename"/>
					</jsp:include>
					<%--结束引入分页JSP--%>
                </div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
