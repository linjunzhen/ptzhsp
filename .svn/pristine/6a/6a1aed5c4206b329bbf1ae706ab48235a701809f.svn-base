<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>平潭综合实验区商事主体登记申报系统</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
	
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("TBSQB_FORM", function(form, valid) {
			if (valid) {
				var formData = $("#TBSQB_FORM").serialize();
				var url = $("#TBSQB_FORM").attr("action");
				AppUtil.ajaxProgress({
					url : "domesticControllerController/yzmsfzq.do",
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							toFormUrl('<%=path%>/webSiteController.do?applyItem&itemCode=${itemCode}');
						} else {
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							$("#validateCode").val("");
							$("#validateCode").focus();
							changeRandPic();
						}
					}
				});
			}
		}, "T_COMMERCIAL_DOMESTIC"); 
		var COMPANY_TYPE="${COMPANY_TYPE}";
		if(COMPANY_TYPE==null||COMPANY_TYPE==''){
			window.top.location.href="${pageContext.request.contextPath}/webSiteController.do?wssbmp";
		}
	});
	function changeRandPic(){
		$("#randpic").attr({
			  "src": "<%=path %>/rand.jpg?"+Math.random()
		 });
	}
	function yjsb(){
		var validateCode = $("#validateCode").val().Trim();
		if(validateCode==""||validateCode=="请输入验证码"||validateCode==null){
			$("#validateCode").val("");
			$("#validateCode").focus();
		   // return ;
		}
		$('#TBSQB_FORM').submit();
	}
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	};
	
	function toFormUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_top'></form>");	
		
		var input1="<input name='COMPANY_TYPE' type='hidden' value='${COMPANY_TYPE}' />";
		var input2="<input name='COMPANY_TYPECODE' type='hidden' value='${COMPANY_TYPECODE}' />";
		var input3="<input name='COMPANY_SETNATURE' type='hidden' value='${COMPANY_SETNATURE}' />";
		var input4="<input name='COMPANY_SETTYPE' type='hidden' value='${COMPANY_SETTYPE}' />";
		var input5="<input name='PT_ID' type='hidden' value='${PT_ID}' />";
		var input6="<input name='sssblx' type='hidden' value='1' />";
		$("body").append(ssoForm);
		ssoForm.append(input1);
		ssoForm.append(input2);
		ssoForm.append(input3);
		ssoForm.append(input4);
		ssoForm.append(input5);
		ssoForm.append(input6);
		ssoForm.submit();
	}
	</script>
</head>


<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />
<div   class="eui-main">
	<div class="eui-crumbs">
		<ul>
			<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
			<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
			<li style="font-size:16px">网上申报</li>
		</ul>
	</div>
  <div class="container" style=" overflow:hidden; margin-bottom:20px;background:url(<%=path%>/webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
  <div class="order-detail">
    <div class="syj-tyys">
      <div class="hd syj-tabtitle">
        <ul>
          <li><a href="javasrcipt:void(0)">填写申请表</a></li>
        </ul>
      </div>
    </div>
    </div>
    <div class="order-content" style="margin-left: 10px; margin-right: 10px;">
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></div>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		<div id="neizi" style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: center; line-height: 1.8; font-family: 宋体; font-size: 12pt;"><span style="font-weight: bold;">商事主体准入确认制（平台个体工商户）“智能审批”申报须知</span></p>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		您好！欢迎您使用平潭综合实验区商事主体准入确认制“智能审批”系统，因此，请您认真阅读并确认：
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		一、已阅读《平潭综合实验区商事主体准入确认制改革试点方案》及商事主体登记管理有关法律法规，确知享有的权利和应尽的义务。
		</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">二、商事主体准入确认制“智能审批”端口，仅限已认定为平台企业申请注册个体工商户。新引进的平台企业未认定前，请通过其他方式申报。进入个体工商智能审批端口后，需录入相应平台的验证码，作为平台企业确认其注册信息的依据。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">三、为方便申报以及管理平台企业批量申请个体工商户，平台个体工商户的名称、经营范围、经营场所、联络员、委托代理人等信息均默认统一为登记机关与各平台企业确认的信息。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">四、经营者需为持居民身份证的中国公民，登记人员涉及未成年人的，港澳台人员的，家庭经营的暂不支持使用该“智能审批”系统。
		</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">五、如实填写申报信息且通过实名认证后无法撤销申请，即视为确认所有申报材料和信息的真实性和有效性。
		</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">六、发现申报虚假信息、商事主体名称不适宜等情况的，登记机关有权责令限期予限期与以改正，逾期未改正的，登记机关将予以撤销登记。
		</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">七、申请人对申请信息的真实性、合法性、有效性负责,保证所填报信息真实、完整、准确，未侵犯他人合法权益。
		</p>
		</div>
		</div>
		</div>
	</div>
		
		<form id="TBSQB_FORM" action="#" method="post">
			<div class="order-footer">
			<div class="tap-btn">
			<a class="tap_back" href="<%=path%>/webSiteController.do?wssbmp">上一步</a>
			<span>验证码：</span>
			<input type="text" class="validate[required]" name="validateCode" id="validateCode" style="width:146px" placeholder="请输入验证码" value="">
			<img id="randpic" src="<%=path%>/rand.jpg" width="120" height="45" style="cursor: pointer;" alt="换一张" onclick="changeRandPic();">
			<a class="onekey" href="javascript:void(0);" onclick="yjsb();">一键申报</a> 
			<p>如不能打开填写表单页面，请将本网站设置为默认站点，或关闭拦截软件</p></div>			
		</form>
    </div>
  </div>
</div>
<script type="text/javascript">
	jQuery(".eui-menu").slide({ 
		type:"menu", //效果类型
		titCell:".syj-location", // 鼠标触发对象
		targetCell:".syj-city", // 效果对象，必须被titCell包含
		delayTime:0, // 效果时间
		defaultPlay:false,  //默认不执行
		returnDefault:true // 返回默认
	});
	jQuery(".syj-tyys").slide({trigger:"click"});
</script>
<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
