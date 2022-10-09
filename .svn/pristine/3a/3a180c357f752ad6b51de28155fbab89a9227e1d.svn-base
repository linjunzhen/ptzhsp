﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib
    prefix="e" uri="/WEB-INF/tld/e-tags.tld"%><%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	Properties properties = FileUtil.readProperties("conf/config.properties");
	String serviceUrl = properties.getProperty("serviceUrl");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<title>平潭综合实验区不动产登记与交易</title>
	<!-- CSS -->
	<base href="<%=basePath%>">
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/site/bdc/info/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/aos.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/site/bdc/info/js/jquery.selectlist.js"></script>
	<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>
	<style>
		.current-button{
			border: 1px solid #990000;
			font-size: 15px;
			font-weight: 400;
			border-radius: 4px;
			padding: 3px 5px;
			color: #990000;
			cursor: pointer;
		}
	</style>

<script type="text/javascript">
		function bdccx(){
			var CaseId = $("input[name='CaseId']").val();
			if(null==CaseId||''==CaseId){
				alert("业务宗号不能为空");
				$("input[name='CaseId']").focus();
			}else{
				$('#bdcdjcxForm').submit();
			}
		}
		function hyqyqkcx(){
			var LicenceID = $("input[name='LicenceID']").val();
			var SignPwd = $("input[name='SignPwd']").val();
			var IDCard = $("input[name='IDCard']").val();
			if(null==LicenceID||''==LicenceID){
				alert("合同号不能为空");
				$("input[name='LicenceID']").focus();
			}else if(null==SignPwd||''==SignPwd){
				alert("签约密码不能为空");
				$("input[name='SignPwd']").focus();
			}else if(null==IDCard||''==IDCard){
				alert("身份证号码不能为空");
				$("input[name='IDCard']").focus();
			}else{
				$('#hyqyqkcxForm').submit();
			}
		}

		function toBdcPayView() {
			window.open("<%=path%>/webpage/site/bdc/info/bdc_pay_view.jsp");
		}

		function lhgxrsfcx(){
			window.open("<%=path%>/webSiteController/view.do?navTarget=site/bdc/info/bdcdjzlcx_br");

		}
		function brsfcx(){
			window.open("<%=path%>/webSiteController/view.do?navTarget=site/bdc/info/bdcdjzlcx_lhr");
		}
		function toBdcLsyljfjsView() {
			window.open("<%=path%>/bdcApplyController.do?bdcLsyljfjsView");
		}
	</script>
</head>

<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newui/head.jsp?currentNav=sy" >
<jsp:param value="平潭综合实验区不动产登记与交易" name="sname" />
</jsp:include>

	<!--不动产登记公告-->
	<div class="eui-main">
	<div style="width:1300px;margin:auto;height:280px;background-color:white;">
		<div class="lfloat" style="width:1015px;">
			<h2 style="margin:10px 10px 0px 10px;padding:10px;">不动产登记全程网办 <span class="current-button" onclick="toBdcPayView()">不动产缴交登记费</span>
				<span class="current-button" onclick="toBdcLsyljfjsView()">不动产历史遗留缴费计算</span>
			</h2>
			<div class="" >
				<a href="<%=serviceUrl%>/contentController/list.do?moduleId=605" target="_blank">
                <img src="<%=path%>/webpage/site/bdc/info/images/2020082101.jpg" style="height: 223px;width: 1015px;">
                </a>
			</div>
			<script type="text/javascript">jQuery(".slideTxtBox").slide();</script>
		</div>
		<div class="rfloat">
			<div class="eui-bgrn2" style="height: 260px;margin-right:0;">
				<div class="eui-bgrntitle" style="cursor: default">不动产登记资料查询</div>
				<div class="eui-bgrncon">
					<br/>
					<%--                        <a href="javascript:lhgxrsfcx();"></a>--%>
					<div style="width: 234px;height: 45px;background-color: #0075c0;display: flex;align-items: center;justify-content: center;color: #ffffff;font-size: 17px;border-radius: 7px;cursor: pointer;" onclick="lhgxrsfcx();">按利害关系人身份查询</div>
				</div>
				<div class="eui-bgrncon2">
					<br/>
					<%--                        <a href="javascript:brsfcx();"></a>--%>
					<div style="width: 234px;height: 45px;background-color: #0075c0;display: flex;align-items: center;justify-content: center;color: #ffffff;font-size: 17px;border-radius: 7px;cursor: pointer;" onclick="brsfcx();">按本人身份证查询</div>

				</div>
			</div>
		</div>
	</div>
			<!--不动产登记公告-->


	<div class="pub-con"  style="width:1300px;">
	<div style="margin:8px 0;">
	<a target="_blank" href="https://www.fjsbdcdj.com:9000/fjwwsb/"><img   style="width:1300px;" src="<%=path%>/webpage/site/bdc/info/images/linktofj.png">
	</a>
	</div>
	</div>
	<div class="pub-con clearfix"  style="width:1300px;">
		<div class="lfloat index-l">
		<div style="margin-bottom:10px"><a href="http://27.151.80.22:8088/pt_web/" target="_blank"><img src="<%=path%>/webpage/site/bdc/info/images/wqxt.png" /></a></div>
			<!--权利运行网上公开（业务查询）-->
			<div class="index-box" data-aos="fade-up" style="height:171px;">
			<iframe frameBorder="0" width="100%" height="100%" marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://27.151.80.22:8088/pt_interface/src/html/bargain.html"></iframe>

			</div>
		</div>
	<!---右边接口-->
		<div class="rfloat index-r" data-aos="fade-up" data-aos-delay="200"   style="width:820px;">
			<iframe frameBorder="0" width="100%" height="100%" marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://27.151.80.22:8088/pt_interface/src/html/publicity.html"></iframe>
		</div>
	</div>

	<!--友情链接-->
	<div class="pub-con index-box index-link" data-aos="fade-up"   style="width:1270px;">
		<div class="index-title">
			<i class="link"></i>友情链接
		</div>

		<ul class="clearfix">
			<li class="item"><a href="http://www.mlr.gov.cn/" target="_blank">中华人民共和国国土资源部</a></li>
			<li class="item"><a href="http://www.mohurd.gov.cn" target="_blank">中国住房和城乡建设部</a></li>
			<li class="item"><a href="http://www.fjgtzy.gov.cn/" target="_blank">福建省国土资源厅</a></li>
			<li class="item"><a href="http://www.fjjs.gov.cn" target="_blank">福建建设信息网</a></li>
			<li class="item"><a href="http://www.pingtan.gov.cn/" target="_blank">平潭综合实验区政府门户网站</a></li>
			<li class="item">
				<span>省内房地产信息网</span>
				<ul class="sub">
					<li><a href="http://www.ptfg.gov.cn/" target="_blank">莆田房地产信息网</a></li>
					<li><a href="http://www.jofwdj.com/" target="_blank">建瓯房地产信息网</a></li>
					<li><a href="http://www.wysfgc.com/" target="_blank">武夷山房地产信息网</a></li>
					<li><a href="http://jyfdc.com/" target="_blank">建阳房地产信息网</a></li>
					<li><a href="http://www.zpsfdc.com/" target="_blank">漳平房地产信息网</a></li>
					<li><a href="http://www.smfdc.com.cn/" target="_blank">三明房地产信息网</a></li>
					<li><a href="http://www.fjnpfdc.com/" target="_blank">南平房地产信息网</a></li>
					<li><a href="http://www.fdc.cn/" target="_blank">漳州房地产信息网</a></li>
					<li><a href="http://www.fjxyfdc.com/" target="_blank">仙游房地产信息网</a></li>
					<li><a href="http://www.fzhouse.com.cn/" target="_blank">福州房地产信息网</a></li>
					<li><a href="http://www.xmjydj.com/" target="_blank">厦门房地产信息网</a></li>
				</ul>
			</li>
		</ul>
	</div>
	</div>
	<script src="<%=path%>/webpage/site/bdc/info/js/bdc.js"></script>
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>

</body>
</html>