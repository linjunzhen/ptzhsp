<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag"%>
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
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://static.runoob.com/assets/qrcode/qrcode.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery3.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery.qrcode.min.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete,layer"></eve:resources>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.bslogo img,.rightNav,.rightNav a,.BlcT,.BlcT1,.BlcT2');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style>
		p{
			margin-top: 0px;
			margin-bottom: 0px;
		}
        .aLink{
            color:#426fa4;
            text-decoration: underline;
        }
        .on span {
            border-bottom: 4px solid #426fa4;
            display: inline-block;
            padding: 0 425px !important;
            color: #426fa4;
            font-size: 16px;
        }
	</style>

	<jsp:include page="../visitlog/visitlogJs.jsp"></jsp:include>

</head>

<body class="bsbody">

    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp?currentNav=sy" />
    <%--结束编写头部文件 --%>

    <div class="container">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do?navTarget=website/bsdt/index">首页</a> >
            <i><a href="webSiteController/view.do?navTarget=website/qlqd/qzqd">权责清单</a></i></div>

        <div class="bsbox clearfix" id="jbxx">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none;text-align: center"><span>${rightItem.RIGHT_NAME}【
                        <c:if test="${empty rightItem.subItem_name}">
                            ${rightItem.RIGHT_NAME}
                        </c:if>
                        <c:if test="${!empty rightItem.subItem_name}">
                            ${rightItem.subItem_name}
                        </c:if>
                        】</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                	<tr>
                    	<th>类别</th>
                        <td><c:if test="${rightItem.DICTYPECODE==null||rightItem.DICTYPECODE==''}">无</c:if>${rightItem.DICTYPECODE}</td>
                    	<th>权责编码</th>
                        <td><c:if test="${rightItem.PROVINCE_CODE==null||rightItem.PROVINCE_CODE==''}">无</c:if>${rightItem.PROVINCE_CODE}</td>
                    </tr>
					<tr>
						<th>关联服务事项名称</th>
						<td COLSPAN="3"><c:if test="${rightItem.ITEM_CODE==null||rightItem.ITEM_CODE==''}">无</c:if>
                            <a href="serviceItemController/bsznDetail.do?itemCode=${rightItem.ITEM_CODE}" class="aLink">
                            ${rightItem.ITEM_NAME}</a></td>
					</tr>
                    <tr>
                        <th>行使主体</th>
                        <td colspan="3"><c:if test="${rightItem.IMPL_DEPART_NAME==null||rightItem.IMPL_DEPART_NAME==''}">无</c:if>${rightItem.IMPL_DEPART_NAME}</td>
                    </tr>
                    <tr>
                        <th>权责层级</th>
                        <td colspan="3"><c:if test="${rightItem.DICLEVEL==null||rightItem.DICLEVEL==''}">无</c:if>${rightItem.DICLEVEL}</td>
                    </tr>
                    <tr>
                        <th>实施依据</th>
                        <td colspan="3">${rightItem.IMPL_BASIS}</td>
                    </tr>
                    <tr>
                        <th>备注</th>
                        <td colspan="3">${rightItem.REMARK}</td>
                    </tr>
                </table>
            </div>
        </div>

		<%--开始编写尾部文件 --%>
		<jsp:include page="./foot.jsp" />
		<%--结束编写尾部文件 --%>

</body>
</html>
