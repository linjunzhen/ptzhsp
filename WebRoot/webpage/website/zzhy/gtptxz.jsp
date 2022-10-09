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
					url : "gtptxxglController/ptyzmsfzq.do",
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							toFormUrl('<%=path%>/domesticControllerController/txsqbmp.do');
						} else {
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							if(resultJson.msg=='平台验证码错误!'){								
								$("#validateCode").val("");
								$("input[name='PT_PWD']").val("");
								$("input[name='PT_PWD']").focus();
								changeRandPic();
							} else{
								$("#validateCode").val("");
								$("#validateCode").focus();
								changeRandPic();
							}
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
		var input4="<input name='itemCode' type='hidden' value='${itemCode}' />";
		var input5="<input name='formKey' type='hidden' value='${formKey}' />";
		var input6="<input name='itemCodes' type='hidden' value='${itemCodes}' />";
		var input7="<input name='itemNames' type='hidden' value='${itemNames}' />";
		var input8="<input name='PT_ID' type='hidden' value='"+$("input[name='PT_ID']").val()+"' />";
		$("body").append(ssoForm);
		ssoForm.append(input1);
		ssoForm.append(input2);
		ssoForm.append(input3);
		ssoForm.append(input4);
		ssoForm.append(input5);
		ssoForm.append(input6);
		ssoForm.append(input7);
		ssoForm.append(input8);
		ssoForm.submit();
	}
	
	
//平台信息查询
function showGtptxxSelector(name,id){
    art.dialog.open("gtptxxglController.do?gtptxxSelector&allowCount=1", {
        title : "平台信息查询",
        width:"1000px",
        lock: true,
        resize:false,
        height:"500px",
        close: function () {
            var gtptxxInfo = art.dialog.data("gtptxxInfo");
			if(gtptxxInfo){
				$("input[name='"+name+"']").val(gtptxxInfo.PT_NAME);
				$("input[name='"+id+"']").val(gtptxxInfo.PT_ID);
			}
            art.dialog.removeData("gtptxxInfo");
        }
    });
}

	</script>
</head>


<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />
<div  class="eui-main">
		
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
          <li><a href="javasrcipt:void(0)">仅限平台办理</a></li>
        </ul>
      </div>
    </div>
    </div>
	<form id="TBSQB_FORM" action="#" method="post">
    <div class="order-content " style="margin-left: 10px; margin-right: 20px;">
		<table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top: 5px;">
			<tr>
				<th style="border-bottom-width: 1px;width:180px;text-align:right;">平台名称：</th>
				<td  style="width:378px;">	
					<div class="info eui-input">
						<input type="text" style="width:50%;" placeholder="请选择平台" readonly maxlength="32" class="eve-input validate[required]" name="PT_NAME" />
					<input type="hidden" name="PT_ID">
					<a href="javascript:showGtptxxSelector('PT_NAME','PT_ID');" class="btn1">选 择</a>
					</div>
				</td>
			</tr>
			<tr>
				<th style="border-bottom-width: 1px;width:180px;text-align:right;">平台验证码：</th>
				<td  style="width:378px;">
					<div class="info eui-input">
						<input type="text" style="width:50%;" placeholder="请输入平台验证码" maxlength="32" class="eve-input validate[required]" name="PT_PWD" />
					</div>
				</td>
			</tr>		
			<tr>
				<th style="border-bottom-width: 1px;width:180px;text-align:right;">验证码：</th>
				<td  style="width:378px;">
					<div class="info eui-input">
						<input type="text" class="eve-input validate[required]" maxlength="8" name="validateCode" id="validateCode" style="width:146px;" placeholder="请输入验证码" value="">
						<img id="randpic" src="<%=path%>/rand.jpg" width="65" height="27" style="cursor: pointer;" alt="换一张" onclick="changeRandPic();">
					</div>
				</td>
			</tr>	
		</table>
	</div>
		
	<div class="order-footer">
			<div class="tap-btn">
			<a class="tap_back" href="<%=path%>/webSiteController.do?wssbmp">上一步</a>
			
			<a class="onekey" href="javascript:void(0);" onclick="yjsb();">一键申报</a> 
			<p>如不能打开填写表单页面，请将本网站设置为默认站点，或关闭拦截软件</p></div>			
		
    </div>
	</form>
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
