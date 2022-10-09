<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>平潭综合实验区行政服务中心-网上办事大厅</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="plug-in/passJs/tripledes.js" ></script>
    <script type="text/javascript" src="plug-in/passJs/mode-ecb.js" ></script>
 <script type="text/javascript" src="plug-in/passJs/des.js" charset="UTF-8"></script>    	
<!-- 引入验证库 -->
<eve:resources loadres="apputil,artdialog,json2,layer,validationegine"></eve:resources>
<style type="text/css">
.sel {
	/*width: 170px;
	height: 28px;
	line-height: 28px;*/
	margin-top: 5px;
}

.reg_con2 {
    background: #fff none repeat scroll 0 0;
    padding: 30px 0 0 103px;
}

.reg_con2 p {
    height: 30px;
    line-height: 30px;
    margin-bottom: 10px;
}

.reg_con2 p input {
    border: 1px solid #c9deef;
    float: left;
    height: 28px;
    line-height: 28px;
    padding: 0 5px;
    width: 360px;
}
.reg_con2 p label, .reg_con2 p label img {
    color: #999;
    float: left;
    font-family: "微软雅黑";
    font-size: 12px;
    margin-left: 10px;
}

.reg_con2 p span {
    float: left;
    font-size: 14px;
    text-align: right;
    width: 117px;
}
.reg_con2 p span i {
    color: #ed1c24;
    font-style: normal;
}
</style>
<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("USER_INFO_FORM", function(form, valid) {
			if (valid) {
				//var formData = $("#USER_INFO_FORM").serialize();
				var x = $("#USER_INFO_FORM").serializeArray();
				var param="";
  				$.each(x, function(i, field){
  					if(field.name=="DLMM"){
  						param+=field.name+"="+strEnc(field.value,"1","2","3")+"&";//encryptByDES(field.value,"12345678")+"&";
  					}else if(field.name=="QRDLMM"){
  						param+=field.name+"="+strEnc(field.value,"1","2","3")+"&";
  					}else{
  						param+=field.name+"="+field.value+"&";
  					}
  				});
  				param=param.substr(0,param.length-1);
				
				var url = $("#USER_INFO_FORM").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : param,//formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							  alert( resultJson.msg);
							  window.location.href = "<%=path%>/webSiteController/view.do";
						} else {
							layer.alert( resultJson.msg,8);
						}
					}
				});
			}
		}, "T_BSFW_USERINFO");
	});
	function changeRandPic(){
        $("#randpic").attr({
              "src": "<%=path %>/rand.jpg?"+Math.random()
         });
    }
	function addzjhmyz(v){
		if(v=='SF'){
		$("#ZJHM").addClass(",custom[vidcard]");
		}else{
			$("#ZJHM").removeClass(",custom[vidcard]");
		}
	}
	function onclickHref(){
		location.href = "<%=path %>/webSiteController/view.do?navTarget=website/yhzx/qyregist";
	}
	 // DES加密
function encryptByDES(message, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}
function hiddenPass(e){
     e = e ? e : window.event; 
     var kcode = e.which ? e.which : e.keyCode;
     var pass = document.getElementByIdx_x("dlmm");
     var j_pass = document.getElementByIdx_x("password");
     if(kcode!=8)
     {
      var keychar=String.fromCharCode(kcode);
      j_pass.value=j_pass.value+keychar;
      j_pass.value=j_pass.value.substring(0,pass.length);
     }
 }
</script>
</head>

<body class="userbody">
	<jsp:include page="./head.jsp" />
	<form id="USER_INFO_FORM" method="post" action="userInfoController.do?saveOrUpdate">
		<div class="container lbpadding20">
			<div class="main_t"></div>
			<div class="main clearfix">
				<div class="padding20">
					<div class="find_Bname">
						<div class="find_Bt">
							<img src="webpage/website/common/images/reg_icon.png">
						</div>
						<div class="find_Bback">
							如已经注册，请点击<a href="webSiteController/view.do?navTarget=website/yhzx/login">登录</a>
						</div>
					</div>
					<div class="reg_tab clearfix">
						<ul>
							<li id="grzc" class="on">个人注册</li>
							<li id="qyzc" onclick="onclickHref();">企业注册</li>
						</ul>
					</div>
					<div>
					   <div class="reg_con2">
					       <input type="hidden" name="USER_TYPE" id="USER_TYPE" value="1" />
					       <p>
                                <span><i>*</i> 用户名：</span><input type="text" maxlength="20"
                                    class=" validate[required],custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]"
                                    name="YHZH" id="YHZH" /><label>不能为空，作为登录账号使用</label>
                            </p>
					       <p>
                                <span id="yhmc"><i>*</i> 姓名：</span><input type="text" maxlength="50"
                                    class=" validate[required],custom[onlychineseLetter]" name="YHMC" />
                            </p>
                            <p>
                                <span><i>*</i> 证件类型：</span>
                                <eve:eveselect clazz="eve-input sel validate[required]" onchange="addzjhmyz(this.value);"
                                    dataParams="DocumentType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择证件类型====" name="ZJLX"></eve:eveselect>
                            </p>
                            <p>
                                <span><i>*</i> 证件号码：</span><input type="text" maxlength="20" id="ZJHM"
                                    class=" validate[required,maxSize[20]],custom[onlyLetterNumber]" name="ZJHM" />
                            </p>
                            <p>
                                <span><i>*</i> 密码：</span><input type="password" id="dlmm" 
                                    maxlength="20" autocomplete="off"
                                    class="eve-input validate[required,maxSize[20]],minSize[6],custom[onlyLetterNumberUnderLine]"
                                    name="DLMM" /><label>密码由6-20个字符组成，且字母有大小写之分</label>
                                     <!--   onkeypress="javascript:hiddenPass(event)" onkeyup="this.value=this.value.replace(/./g,'*');" -->
                            </p>
                            <p>
                                <span><i>*</i> 确认密码：</span><input type="password" maxlength="20"
                                    class="eve-input validate[equals[dlmm]]" name="QRDLMM" />
                            </p>
                            <p>
                                <span><i>*</i> 手机：</span><input type="text" maxlength="11"
                                    class="eve-input validate[required,maxSize[11]],custom[mobilePhone]"
                                    name="SJHM" /><label>用于接收系统的办理事项进度等消息</label>
                            </p>
                            <p>
                                <span> 电话号码：</span><input type="text" 
                                    class="eve-input validate[maxSize[13]],custom[fixPhoneWithAreaCode]"
                                    name="DHHM" />
                            </p>
					   </div>
					</div>
					<div class="regcont">
						<div class="reg_con" style=" padding: 0 0 0 103px;">
							<p>
								<span><i>*</i> 性别：</span>
								<eve:eveselect clazz="eve-input sel validate[required]"
									dataParams="sex"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="======选择性别=====" name="YHXB"></eve:eveselect>
							</p>
							<p>
								<span> 邮箱：</span><input type="text" maxlength="30"
									class="eve-input validate[maxSize[30]],custom[email]" name="DZYX" /><label>可通过些邮箱登录找回账户信息</label>
							</p>			
						</div>
					</div>
					<div class="reg_con" style="padding-top: 0px;">
						<p>
							<span><i>*</i> 验证码：</span> <input type="text"
								class="validate[required]" name="validateCode" id="validateCode"
								style="font-size: 18px;width: 100px;" maxlength="5" /><label><img
								id="randpic" src="<%=path%>/rand.jpg" width="70" height="33" />
								<span onclick="changeRandPic();" style="cursor: pointer;">看不清?换一张</span></label>

						</p>
						<div class="find_btn">
							<a href="javascript:void(0);" onclick="$('#USER_INFO_FORM').submit();">注册</a>
						</div>
					</div>

				</div>
			</div>
		</div>
	</form>
	<jsp:include page="../index/foot.jsp" />
</body>
</html>
