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
		var COMPANY_TYPECODE="${COMPANY_TYPECODE}";
		if(COMPANY_TYPECODE==null||COMPANY_TYPECODE==''){
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
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></p>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></div>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		<div id="neizi" style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: center; line-height: 1.8; font-family: 宋体; font-size: 12pt;"><span style="font-weight: bold;">内资企业申请书目录</span></p>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		1.《企业“一照一码”设立登记申请书》。(1份)
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">2.全体股东签署的公司章程。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">3.股东的主体资格证明或者自然人身份证件复印件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">(股东为企业的，提交加盖此企业公章的营业执照复印件、组织机构代码证复印件、税务登记证复印件：股东为事业法人的，提交事</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">业法人登记证书复印件；股东为社团法人的，提交社团法人登记证复印件；股东为民办非企业单位的，提交民办非企业单位证书复</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">印件； 股东为自然人的，提交身份证件复印件；其他股东提交有关法律法规规定的资格证明）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">4.法定代表人、董事、监事和经理的任职文件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">(股东会决议由股东签署，董事会决议由公司董事签字)</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">5.董事、监事和经理的身份证明复印件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">6.福建省市场主体住所（经营场所）申报承诺表（原件1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">{联络地址可免于提交；集群注册需提交住所（经营场所托管证明函）（原件1份）；</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">无法提供详细地址的需填写《方位示意图》（原件1份）}</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">7.《企业名称预先核准通知书》原件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">（经自主选用的名称，可免于提交，经核准的由商事登记机关出具）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">8.福建省人民政府公布的《企业登记前置许可项目目录》规定设立有限责任公司必须报经批准的，提交有关的批准文件或者许可证件</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">复印件。（比如：交易中心）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">9.公司申请登记的经营范围中有福建省人民政府公布的《平潭综合实验区商事登记前置行政许可审批事项目录》规定必须在登记前报经</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">批准的项目，提交有关批准文件或者许可证件的复印件。（仅涉及六类15项）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">10.公司法定代表人身份证件复印件及委托代理人身份证件复印件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">（法定代表人无法亲自到场办理注册登记需提供委托代理人身份证件复印件）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">11.平潭综合实验区商事主体登记委托书。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">（法定代表人无法亲自到场办理注册登记出具此委托书）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">12.平潭综合实验区商事主体登记确认书。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">（法定代表人亲自到场办理注册登记出具此委托书）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">13.平潭综合实验区商事登记综合服务申请书。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">14.财务负责人身份证件复印件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">15.《平潭综合实验区商事主体登记信息采集表》。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">注：法定代表人以及经办人身份证件原件需带至商事综合服务窗口进行核对。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">规范要求：</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">1、提交的申请书与其他申请材料应当使用A4型纸。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">2、提交材料未注明提交复印件的，应当提交原件。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">3、提交材料涉及签署的，未注明签署人的，自然人由本人签字；法人和其他组织由法定代表人或负责人签字，并加盖公章。</p>
		</div>
		</div>
		</div>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></p>
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
