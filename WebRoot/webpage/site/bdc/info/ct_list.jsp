 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib
    prefix="e" uri="/WEB-INF/tld/e-tags.tld"%><%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ page import="net.evecom.core.util.FileUtil" %>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>

 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<base href="<%=basePath%>">
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

	<script type="text/javascript">
        function contentCallpage(itemList){
            $("#eui-list").html("");
            var newhtml = "<ul>";
            $.each( itemList, function(index, node){
                newhtml +='<li>';
                newhtml +='<a target="_blank" href="contentController/view.do?contentId='+node.TID+'">'+node.ITEMTITLE+'</a>';
                newhtml +='<span>'+node.ITEMRELDATE+'</span>';
                newhtml +='</li>';
            });
            newhtml += "</ul>";
            $("#eui-list").html(newhtml);
        }

		function searchView() {
			var searchVal = $("#seachVal").val();
			var moduleId = '${module.MODULE_ID}';
			window.open("<%=path%>/webSiteController/moduleSearch.do?moduleId=" + moduleId + "&key=" + encodeURIComponent(searchVal), "_blank");
		}
	</script>
</head>

<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>
	
	<div class="eui-main">	
		<div class="pub-con in-box clearfix"  data-aos="fade-up" style="width: 1300px;">
			<div class="lfloat in-l">
				<div class="nav-left">
					<ul>
						<li>
							<e:obj filterid="${module.PARENT_ID}" var="eobj" dsid="1">
							<a href="javascript:void(0);"><i><img src="<%=path%>/webpage/site/bdc/info/images/nav6.png"/></i>
								${eobj.headName}
						</a>
							<ul class="sub">
								<e:for dsid="7" filterid="${module.PARENT_ID}" end="200" var="efor">
								<li>
									<a href="<%=path%>/contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
										<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
									</a>
									</li>
							 </e:for>
							</ul>
							</e:obj> 
						</li>
					</ul>
				</div>
			</div>
			<div class="rfloat in-r">
				<c:if test="${module.MODULE_ID == '503'}">
					<div class="info eui-input">
						<input type="text" id="seachVal" style="height: 32px;width: 30%;"/>
						<a href="javascript:searchView()" style="padding: 4px 12px;background-color: #f3b52e;line-height: 28px;">搜索</a>
					</div>
				</c:if>
				<div class="in-title"><e:obj filterid="${param.cnid}" var="eobj" dsid="1">${eobj.headName}</e:obj></div>
				<div class="eui-list" id="eui-list">

				</div>
				<%--开始引入分页JSP --%>
				<jsp:include page="/webpage/website/common/page.jsp" >
					<jsp:param value="contentController/pagelist.do?moduleId=${module.MODULE_ID}&endindex=42&timeout=2" name="pageURL"/>
					<jsp:param value="contentCallpage" name="callpage"/>
					<jsp:param value="10" name="limitNum"/>
					<jsp:param value="content" name="morename"/>
				</jsp:include>
				<%--结束引入分页JSP--%>
			</div>
		</div>	
	</div>	
	
	
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>