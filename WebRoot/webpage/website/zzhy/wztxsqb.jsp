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
		<div id="waizibeian" style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: center; line-height: 1.8; font-family: 宋体; font-size: 12pt;"><span style="font-weight: bold;">外资备案制企业申请书目录</span></p>
		<div style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">
		1.《企业“一照一码”设立登记申请书》。(1份)
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align=""></p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">2.公司章程。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">3.《外商投资企业名称预先核准通知书》原件及复印件。（1份） （经自主选用的名称，可免于提交，经核准的由商事登记机关出具）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">4.投资者的主体资格证明。（1份） （凡属境外的均需公证和认证、凡外文字体的均需翻译）或自然人身份证明。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">5.法定代表人、董事、监事和经理的任职文件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">6.董事、监事和经理的身份证明复印件。(1份)</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">7.福建省市场主体住所（经营场所）申报承诺表（原件1份）
		{联络地址可免于提交；集群注册需提交住所（经营场所托管证明函）（原件1份）；无法提供详细地址的需填写《方位示意图》（原件1份）}</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">8.前置审批文件或证件。（仅涉及六类15项提交）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">9.公司法定代表人及委托代理人身份证件复印件（1份）；法律文件送达授权书的被授权人身份证件复印件（1份）。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">（法定代表人无法亲自到场办理注册登记需提供委托代理人身份证件复印件）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">10.平潭综合实验区商事主体登记委托书。（1份）
		（法定代表人无法亲自到场办理注册登记出具此委托书）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">11.平潭综合实验区商事主体登记确认书。（1份） （法定代表人亲自到场办理注册登记出具此委托书）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">12.平潭综合实验区商事登记综合服务申请书。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">13.财务负责人身份证件复印件。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">14.自贸区设立外资企业备案申报表和承诺书。（1份）</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">15.《平潭综合实验区商事主体登记信息采集表》。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">注：法定代表人以及经办人身份证件原件需带至商事综合服务窗口进行核对。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">规范要求：</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">1、本申请书应用黑色或蓝黑色钢笔、签字笔填写，字迹应清楚。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">2、以上文件除标明复印件外，应提交原件。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">3、以上所提交的文件若用外文书写，需提交中文译本，并加盖翻译单位印章。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">4、第3项公司章程需投资各方法定代表人或其授权人签字、各投资方盖章的原件，投资者为自然人的由本人签字。提交的公司章程应与审批部门批准的相一致。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">5、第4项《名称预先核准通知书》应在有效期内，且内容与拟设立公司申请的相关事项吻合。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">6、第5项中方投资者应提交由本单位加盖公章的营业执照（事业单位法人登记证书、社会团体法人登记证、民办非企业单位证书复印件）作为主体资格证明；外国投资者的主体资格证明或身份证明应经其本国主管机关公证后送我国驻该国使（领）馆认证。如其本国与我国没有外交关系，则应当经与我国有外交关系的第三国驻该国使（领）馆认证，再由我国驻该第三国使（领）馆认证。某些国家的海外属地出具的文书，应先在该属地办妥公证，再经该国外交机构认证，最后由我国驻该国使（领）馆认证。香港、澳门和台湾地区投资者的主体资格证明或身份证明应当按照专项规定或协议依法提供当地公证机构的公证文件。台湾地区投资者的主体资格或身份证明提供原件核对的，可以免公证。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">7、第6项法定代表人、董事、监事和经理的产生应符合公司章程的规定，且上述人员的任职文件中应包括股东会、董事会或投资者对其任职资格的审查意见。</p>
		<p style="margin-top: 6px; margin-bottom: 0px; text-indent: 2em; text-align: justify; line-height: 1.8; font-family: 宋体; font-size: 12pt;" align="">8、第9项指有关前置许可的批准文件或者许可证书复印件或许可证明，适用于经营范围中含《平潭综合实验区商事登记前置行政许可审批事项目录（试行）》规定必须在登记前报经批准的项目的外商投资的公司。
		</p>
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
