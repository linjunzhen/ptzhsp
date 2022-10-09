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
		var COMPANY_SETTYPE="${COMPANY_SETTYPE}";
		if(COMPANY_SETTYPE==null||COMPANY_SETTYPE==''){
			window.top.location.href="${pageContext.request.contextPath}/webSiteController.do?zzhywssb";
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
		var input5="<input name='ITEMCODES' type='hidden' value='${itemCodes}' />";
		var input6="<input name='ITEMNAMES' type='hidden' value='${itemNames}' />";
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


<body>
<jsp:include page="/webpage/website/zzhy/head.jsp?currentNav=wssb" />
<div  style="float:left; width:100%;">
  <div class="container">
    <div class="syj-crumb"> <span>所在位置：</span><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > <i>网上申报</i> </div>
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
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: center; line-height: 1.8; font-family: 宋体; font-size: 12pt;"><span style="font-weight: bold;">个人独资企业申请书目录</span></p>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		1、《个人独资企业登记（备案）申请书》。（1份）
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">2、《投资人信息表》。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">3、《财务负责人信息表》。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">4、《联络员信息表》。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">5、《办税人信息表》。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">6、《指定代表或者共同委托代理人授权委托书》。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">7、投资人身份证明。（1份）（自行提供）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">8、福建省市场主体住所（经营场所）申报承诺表（原件1份）
{联络地址可免于提交；集群注册需提交住所（经营场所托管证明函）（原件1份）；无法提供详细地址的需填写《方位示意图》（原件1份）}</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">9、福建省人民政府公布的《企业登记前置许可项目目录》规定须报经审批的，提交“目录”规定的批准文件或者许可证件复印件。（1份）（自行提供）</p>
		</div>
		</div>
		</div>
	</div>
		
		<form id="TBSQB_FORM" action="#" method="post">
			<div class="order-footer">
			<div class="tap-btn">
			<a class="tap_back" href="<%=path%>/webSiteController.do?zzhywssb">上一步</a>
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
<jsp:include page="/webpage/website/zzhy/foot.jsp" />
</body>
</html>
