<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<eve:resources
	loadres="jquery,apputil,layer,artdialog"></eve:resources>
<style type="text/css">
/* 电子证照验证 start */
body{
	margin: 0px;
}
.editPop-cont {
    bottom: 46px;
    left: 30px;
    position: absolute;
    right: 30px;
    top: 12px;
    white-space: nowrap;
}
.editPop-btn3 {
	display: inline-block;
	margin: 0 auto;
	width: 100px;
	height: 32px;
	line-height: 32px;
	font-size: 14px;
	color: #fff;
	background: #1983da;
	border-radius: 2px;
	text-align: center;
	text-decoration: none;
	behavior:url(css/PIE.htc);
}
.picAlert.editPop-cont {
	top: 24px;
	left: 52px;
	right: 52px;
}
.picAlert .eui-ipta {
	display: inline-block;
	position: relative;
	padding: 0 10px;
	width: 320px;
	height: 40px;
	font-size: 14px;
	color: #333;
	border: 1px solid #cce3e8;
	background: #fff;
	vertical-align: middle;
	float: left;
}
.picAlert .eui-ipta input {
	position: relative;	
	display: block;
	padding: 0;
	width: 100%;
	height: 40px;
	line-height: 40px;
	border: 0;
	background: none;
	outline: none;
}
.picAlert .eui-ipta .ipt-placeholder {
	display: inline-block;
	position: absolute;
	left: 10px;
	top: 10px;
	height: 18px;
	line-height: 18px;
	color: #323232;
	z-index: 5;
}
.editPop-button {
	display: inline-block;
	position: relative;
	margin: 2px 0 0 8px;
	width: 100px;
	height: 38px;
	line-height: 38px;
	font-size: 14px;
	color: #fff;
	background: #1983da;
	border-radius: 2px;
	text-align: center;
	behavior:url(css/PIE.htc);
	text-decoration: none;
	float: right;
}
.editPop-btns {
    bottom: 0;
    font-size: 0;
    left: 0;
    padding: 7px 0;
    position: absolute;
    right: 0;
    width:100%;
    text-align: center;
}
.editPop-button.dis {
	background: #d3d3d3;
	cursor: default;
}
.picAlert-success,
.picAlert-fail {
	height: 20px;
	line-height: 20px;
	padding:10px 0;
	font-family: '宋体';
	font-size: 16px;
}
.picAlert-success img,
.picAlert-fail img {
	margin-top: -2px;
	margin-right: 10px;
	vertical-align: middle;
}
.picAlert-success {
	color: #a2d344;
}
.picAlert-fail {
	color: #fa4848;
}
.picAlert-text {
	position: absolute;
	left: -26px;
	right: -26px;
	font-family: '宋体';
	line-height: 24px;
	font-size: 12px;
	color: #149ae1;
	margin-top: 40px;
	_margin-top: 25px;
	*margin-top: 25px;
}
.picAlert-text p {
	margin: 0;
}
.clearfix:after {
	content: '';
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}
.clearfix {
	*zoom: 1;
	_zoom: 1;
}
/* 电子证照验证 end */
</style>
</head>

<body>
	<div class="editPop-body">
		<%-- <input type="hidden" name="licenseCode" value="${LICENSE_CODE}"> --%>
		<div class="editPop-cont picAlert" >
			<div class="clearfix">
				<div>
					<p>请选择认证的证照类型：</p>
					<fda:fda-exradiogroup name="licenseCode" width="150px"
					puremode="true" isstartp="true" selectfirst="true"
					radiolables="${LICENSE_NAME}" radiovalues="${LICENSE_CODE}"
					></fda:fda-exradiogroup>
				</div>
				<div class="eui-ipta">
					<input type="text" autocomplete="off" name="license">
					<span class="ipt-placeholder">请输入证件号</span>
				</div>
				<a  href="javascript:void(0);" onclick="validData();" id="validDataBtn" class="editPop-button">验证</a>
			</div>
			<div class="clearfix picAlert-success" style="display: none;"><img src="webpage/business/images/icon_success.png" alt="">验证成功</div>
			<div class="clearfix picAlert-fail"  style="display: none;"><img src="webpage/business/images/icon_fail.png" alt="">验证失败</div>
			<div class="picAlert-text">
				<p>认证说明：</p>
				<p>1、请严格按照大小写输入完整证件号，如食品生产许可证，证件号为SC开头的许可证号；</p>
				<p>2、若认证失败，请按照其他方式提交证件信息。</p>
			</div>
		</div>
		<div class="editPop-btns">
			<a href="javascript:void(0);" onclick="AppUtil.closeLayer();" class="editPop-btn3">取消</a>
		</div>
	</div>
</body>
<script type="text/javascript">
		//placeholder脚本
        if ($(".eui-ipta input").val() != "") {
            $(this).next(".ipt-placeholder").hide();
        };
        $(".eui-ipta .ipt-placeholder").click(function() {
        	$(this).prev().focus();
        });
        $(".eui-ipta input").focusin(function() {
            $(this).next(".ipt-placeholder").hide();
        });
        $(".eui-ipta input").focusout(function() {
            if ($(this).val() == "") {
                $(this).next(".ipt-placeholder").show();
            };
        });
        function validData(){
        	var code = $("input[name='licenseCode']:checked").val();
        	var license = $("input[name='license']").val();
        	AppUtil.ajaxProgress({
    			url : "webSiteItemController/getLicenseFile.do",
    			timeout:180000,
    			params : {
    				licenseType:code,
    				licenseID:license
    			},
    			callback : function(resultJson) {
    				if(resultJson.status){
    					$("#validDataBtn").removeAttr("onclick");
    					$("#validDataBtn").addClass("dis");
    					$(".picAlert-success").attr("style","");
    					$(".picAlert-fail").attr("style","display: none;");
    					art.dialog.data("certificationInfo", {
    						filepath : resultJson.message,
    						filename : license
    					});
    					setTimeout("AppUtil.closeLayer()",3000);
    				}else{
    					$(".picAlert-success").attr("style","display: none;");
    					$(".picAlert-fail").attr("style","");
    				}
    			}
    		});
        	
        }
</script>

