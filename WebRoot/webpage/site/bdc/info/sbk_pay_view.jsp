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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>平潭综合实验区不动产登记与交易</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
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

    <script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwplayer.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jwplayer/jwpsrv.js"></script>
    <!--[if lte IE 6]>
    <script src="<%=path%>/plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
    </script>
    <![endif]-->
    <style>
        .display_box{
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>

</head>
<body style="background: #f0f0f0;">

	<!--头部-->	
	<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" > 
		<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
	</jsp:include>

	<div class="eui-main">	
		<div style="width:1200px;margin:auto;height:630px;background-color:white;" class="display_box">
			<div>
				<div class="display_box" style="margin-bottom: 10px"><span>请扫描以下二维码进行缴交登记费，支付前请确认收款账号为: 【<span style="color: #990000;">平潭综合实验区行政服务中心</span>】</span></div>
				<div class="display_box" style="font-size: 30px;font-weight: 700;">扫一扫付款</div>
				<div class="display_box"><img src="<%=path%>/webpage/site/bdc/info/images/payImg.png" style="width: 250px;height: auto;"></div>
				<div class="display_box" style="font-weight: 500;font-size: 25px;margin-bottom: 20px;">平潭综合实验区行政服务中心</div>
				<div class="display_box" style="justify-content: center;">
					<div>
						<img src="<%=path%>/webpage/site/bdc/info/images/weixin.png" style="width: 50px;height: auto;margin-right: 30px;">
						<div>微信支付</div>
					</div>
					<div>
						<img src="<%=path%>/webpage/site/bdc/info/images/zhifubao.png" style="width: 50px;height: auto;">
						<div>支付宝</div>
					</div>
				</div>
				<div>付款须知：</div>
				<div>①社保卡缴费标准：首次办理免费,补、换卡25元/张</div>
				<div>②支付时请务必添加备注：您的缴费类型及个人信息(不限姓名、身份证等)</div>
				<div>③业务咨询：12345</div>
				<div>④退款及申诉：15659416227，联系人：池女士</div>
			</div>
		</div>
    </div>


	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>