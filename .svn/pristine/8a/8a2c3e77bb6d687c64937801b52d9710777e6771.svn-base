<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/aos.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/bdc/css/style.css">
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/jquery.min.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/jquery.SuperSlide.2.1.2.js"></script>
	<script src="<%=path%>/webpage/website/bdc/js/aos.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.placeholder.js"></script>
    <script src="<%=path%>/webpage/website/bdc/js/jquery.selectlist.js"></script>
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
	</script>
</head>

<body>
	
	<!--头部-->
	<div class="eui-header"><iframe frameBorder="0" width="100%" height="100%" 
	marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/head.jsp"></iframe></div>
	
	<div class="pub-con index-box slide-tzgg clearfix" data-aos="fade" data-aos-delay="200">
		<!--幻灯片-->
		<div class="lfloat eui-slide">
            <ul class="pic">
                <li><a href="#" target="_blank"><img src="<%=path%>/webpage/website/bdc/images/1.png"><span>平潭综合实验区</span></a></li>
                <li><a href="#" target="_blank"><img src="<%=path%>/webpage/website/bdc/images/1.png"><span>平潭综合实验区2</span></a></li>
            </ul>
            <i class="prev">&lt;</i>
            <i class="next">&gt;</i>
			<ul class="point"></ul>
		</div>
		<!--通知公告-->
		<div class="rfloat index-tzgg">
			<div class="index-title">
				<span><a href="###">更多</a></span>
				<i class="tzgg"></i>通知公告
			</div>
			<b><a href="###">北京全面围剿商住房 商用房全部暂停抵押</a></b>
			<p>商用房正遭遇全面围剿。北京商报记者了解到，目前，北京各银行商用房
全部暂停抵押。此外，据悉，广州多家银行也不能...<a href="###">[详情]</a></p>
			<div class="eui-list">
				<ul>
					<li><a href="###"><span>04-18</span>房产买卖这些事项都得要注意</a></li>
					<li><a href="###"><span>04-18</span>北京首尝试限价房转自住房 两项目5月…</a></li>
					<li><a href="###"><span>04-18</span>顺义区发布义务教育入学政策 房产属父…</a></li>
					<li><a href="###"><span>04-18</span>胡乃军:政治局会议透调控信号 房产税呼…</a></li>
					<li><a href="###"><span>04-18</span>大学生缴纳公积金 谁来买单？</a></li>
					<li><a href="###"><span>04-18</span>楼市“号头费” 流进了谁的口袋</a></li>
					<li><a href="###"><span>04-18</span>多城全面叫停商改住项目“商住房”或将…</a></li>
					<li><a href="###"><span>04-18</span>房产买卖这些事项都得要注意</a></li>
					<li><a href="###"><span>04-18</span>顺义区发布义务教育入学政策 房产属父…</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="pub-con clearfix">
		<div class="lfloat index-l">
			<!--权利运行网上公开（业务查询）-->
			<div class="index-box" data-aos="fade-up">
				<div class="index-title">
					<i class="search"></i>权利运行网上公开（业务查询）
				</div>
				
				<div class="index-tab">
					<div class="hd">
						<ul class="clearfix">
							<li>不动产登记查询</li>
							<li>合同签约情况查询</li>
						</ul>
					</div>
					<div class="bd index-ywcx hover-opacity">
						<div class="dj">
							
							<form id="bdcdjcxForm" method="post" target="_blank" action="bdcQueryController/bdccx.do">
								<div class="display-table">
									<p><input type="text" name="CaseId" maxLength="32" placeholder="请输入业务宗号"></p>
									<p class="btn"><a class="index-btn" href="javascript:void(0);" onclick="bdccx();" >查 询</a></p>
								</div>
							</form>
						</div>
						<div class="qy">
							<form id="hyqyqkcxForm" method="post" target="_blank" action="bdcQueryController/hyqyqkcx.do">
							<div class="display-table">
								<p><input type="text" placeholder="请输入合同号" name="LicenceID" maxlength="32"></p>
								<p><input type="password" placeholder="请输入签约密码" name="SignPwd" maxlength="32"></p>
							</div>
							<div class="display-table">
								<p><input type="text" placeholder="请输入身份证号" name="IDCard" maxlength="18"></p>
								<p class="btn"><a class="index-btn"  href="javascript:void(0);" onclick="hyqyqkcx();" >查 询</a></p>
							</div>
							</form>
						</div>
					</div>
				</div>		

				
			</div>
			
			<!--政务公开-->
			<div class="index-box" data-aos="fade-up">
				<div class="index-title">
					<span><a href="###">更多</a></span>
					<i class="zwgk"></i>政务公开
				</div>
				<div class="eui-list">
					<ul>
						<li><a href="###"><span>04-18</span>房产买卖这些事项都得要注意</a></li>
						<li><a href="###"><span>04-18</span>北京首尝试限价房转自住房 两项目5月…</a></li>
						<li><a href="###"><span>04-18</span>顺义区发布义务教育入学政策 房产属父…</a></li>
						<li><a href="###"><span>04-18</span>胡乃军:政治局会议透调控信号 房产税呼…</a></li>
						<li><a href="###"><span>04-18</span>大学生缴纳公积金 谁来买单？</a></li>
					</ul>
				</div>				
			</div>
			
			<!--办事指南-->
			<div class="index-box" data-aos="fade-up">
				<div class="index-title">
					<span><a href="###">更多</a></span>
					<i class="bszn"></i>办事指南
				</div>
				<div class="eui-list">
					<ul>
						<li><a href="###"><span>04-18</span>房产买卖这些事项都得要注意</a></li>
						<li><a href="###"><span>04-18</span>北京首尝试限价房转自住房 两项目5月…</a></li>
						<li><a href="###"><span>04-18</span>顺义区发布义务教育入学政策 房产属父…</a></li>
						<li><a href="###"><span>04-18</span>胡乃军:政治局会议透调控信号 房产税呼…</a></li>
						<li><a href="###"><span>04-18</span>大学生缴纳公积金 谁来买单？</a></li>
					</ul>
				</div>				
			</div>
			
			<!--常见问题-->
			<div class="index-box" data-aos="fade-up">
				<div class="index-title">
					<span><a href="###">更多</a></span>
					<i class="cjwt"></i>常见问题
				</div>
				<div class="eui-list">
					<ul>
						<li><a href="###"><span>04-18</span>房产买卖这些事项都得要注意</a></li>
						<li><a href="###"><span>04-18</span>北京首尝试限价房转自住房 两项目5月…</a></li>
						<li><a href="###"><span>04-18</span>顺义区发布义务教育入学政策 房产属父…</a></li>
						<li><a href="###"><span>04-18</span>胡乃军:政治局会议透调控信号 房产税呼…</a></li>
						<li><a href="###"><span>04-18</span>大学生缴纳公积金 谁来买单？</a></li>
					</ul>
				</div>				
			</div>
		</div>
		<!---右边接口-->
		<div class="rfloat index-r" data-aos="fade-up" data-aos-delay="200">
			<iframe frameBorder="0" width="100%" height="100%" marginHeight="0" marginWidth="0" scrolling="no" allowtransparency="true" src="bdcQueryController/bdcIndex.do"></iframe>
		</div>
	</div>
	
	<!--友情链接-->
	<div class="pub-con index-box index-link" data-aos="fade-up">
		<div class="index-title">
			<i class="link"></i>友情链接
		</div>
		
		<ul class="clearfix">
			<li class="item"><a href="http://www.mlr.gov.cn/" target="_blank">中华人民共和国国土资源部</a></li>
			<li class="item"><a href="http://www.mohurd.gov.cn" target="_blank">中国住房和城乡建设部</a></li>
			<li class="item"><a href="http://www.fjjs.gov.cn" target="_blank">福建建设信息网</a></li>
			<li class="item"><a href="http://www.pingtan.gov.cn/" target="_blank">平潭综合实验区政府门户网站</a></li>
			<li class="item">
				<span>省内房地产信息网</span>
				<ul class="sub">
					<li><a href="http://www.ptfg.gov.cn/" target="_blank">莆田房地产信息网</a></li>
					<li><a href="http://www.ndfdc.com.cn/" target="_blank">宁德房地产信息网</a></li>
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
	
	
	<div class="eui-footer"><iframe frameBorder="0" width="100%" height="100%" marginHeight="0" 
	marginWidth="0" scrolling="no" allowtransparency="true" src="http://www.pingtan.gov.cn/site/bdc/info/foot.jsp"></iframe></div>
	<!-- JS -->
	<script src="<%=path%>/webpage/website/bdc/js/bdc.js"></script>
</body>
</html>