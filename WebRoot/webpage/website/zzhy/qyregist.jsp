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
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
 <script type="text/javascript" src="plug-in/passJs/des.js" charset="UTF-8"></script> 
<!-- 引入验证库 -->
<eve:resources loadres="apputil,artdialog,json2,layer,validationegine,swfupload"></eve:resources>
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
    width: 147px;
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
				if($("#grzc").attr("class")=="on"){
					$('#USER_TYPE').val(1);
                }else if($("#qyzc").attr("class")=="on"){
                	$('#USER_TYPE').val(2);
                }
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
					params : param,
					callback : function(resultJson) {
						if (resultJson.success) {
							  alert( resultJson.msg);
							  window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index";
						} else {
							layer.alert( resultJson.msg,8);
						}
					}
				});
			}
		}, "T_BSFW_USERINFO");
		
		var zzjgdmFileUploadSwf = AppUtil.bindSwfUpload({
	            file_types : "*.jpg;*.jpeg;*.png;*.gif;",
	            file_size_limit :"2 MB",
	            post_params : {
	                   "uploadPath":"userInfozzjgdm"
	            },
	            file_upload_limit :100,
	            button_placeholder_id:"uploadZzjgdmFileImg",
	            successful_uploads:0,
	            upload_success_handler:function(resultJson){
	                if(resultJson.success==true){
	                    //获取文件ID
	                    var fileId = resultJson.fileId;
	                    $("#FILE_ID").val(fileId);
	                    //获取文件名称
	                    var fileName = resultJson.fileName;
	                    var divHtml = "<span style=\"width:30px;text-align:left;\" id=\""+fileId+"\"><a href=\"javascript:void(0);\" onclick=\"AppUtil.downLoadFile('";
	                    divHtml+=(fileId+"');\">");
	                    divHtml+=("<font color=\"blue\">查看</font></a>");
	                    divHtml+="</span>";
	                    $("#UploadZzjgdmFileTd").html(divHtml);
	                }
	            }
	        });
	});
	function changeRandPic(){
        $("#randpic").attr({
              "src": "<%=path %>/rand.jpg?"+Math.random()
         });
    }
	function onclickHref(){
        location.href = "<%=path %>/webSiteController/view.do?navTarget=website/zzhy/regist";
    }
	function deleteZzjgdm(fileId){
		AppUtil.delUploadMater(fileId,'uploadZzjgdmFileImg','UploadZzjgdmFileTd');
		$("#FILE_ID").val('');
	}
</script>
</head>

<body class="userbody">
	<jsp:include page="../yhzx/head.jsp" />
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
							<li id="grzc" onclick="onclickHref();">个人注册</li>
							<li id="qyzc" class="on">企业注册</li>
						</ul>
					</div>
					<div>
					   <div class="reg_con2">
					       <input type="hidden" name="USER_TYPE" id="USER_TYPE" value="1" />
					       <input type="hidden" name="FILE_ID" id="FILE_ID" value="" />
					       <div class="reg_menu">帐户信息</div>
					       <p>
                                <span><i>*</i> 用户名：</span><input type="text" maxlength="20"
                                    class=" validate[required],custom[onlyLetterNumberUnderLine],ajax[ajaxVerifyValueExist]"
                                    name="YHZH" id="YHZH" /><label>不能为空，作为登录账号使用</label>
                            </p>   
                            <p>
                                <span><i>*</i> 密码：</span><input type="password" id="dlmm"
                                    maxlength="20"
                                    class="eve-input validate[required,maxSize[20]],minSize[6],custom[onlyLetterNumberUnderLine]"
                                    name="DLMM" /><label>密码由6-20个字符组成，且字母有大小写之分</label>
                            </p>
                            <p>
                                <span><i>*</i> 确认密码：</span><input type="password" maxlength="20"
                                    class="eve-input validate[equals[dlmm]]" name="QRDLMM" />
                            </p>
                            <div class="reg_menu">法人信息</div>
                            <p>
                                <span id="yhmc"><i>*</i> 机构名称：</span><input type="text" maxlength="50"
                                    class=" validate[required],custom[onlychineseLetter]" name="YHMC" />
                            </p>
                            <p>
                                <span><i>*</i> 机构类型：</span>
                                <eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="OrgType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择机构类型====" name="JGLX"></eve:eveselect>
                            </p>
                            <p>
                                <span><i>*</i> 单位机构代码：</span><input type="text" maxlength="30"
                                    class=" validate[required,maxSize[30]]" name="ZZJGDM" />
                            </p>
                            <p>
                                <span>组织机构代码证图片：</span>
                                <span id="UploadZzjgdmFileTd" style="width:30px;"></span>
                                <img id="uploadZzjgdmFileImg" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
                            </p>
                            <p>
                                <span><i>*</i> 法人代表姓名：</span><input type="text" maxlength="10"
                                    class=" validate[required,maxSize[10]],custom[onlychineseLetter]" name="FRDB" />
                            </p>
                            <p>
                                <span><i>*</i> 法人代表性别：</span>
                                <eve:eveselect clazz="eve-input sel validate[required]"
                                    dataParams="sex"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="======选择性别=====" name="YHXB"></eve:eveselect>
                            </p>
                            <p>
                                <span><i>*</i> 法人代表身份证：</span><input type="text" maxlength="20" 
                                    class=" validate[required,maxSize[20]],custom[vidcard]" name="ZJHM" />
                                    <input type="hidden" name="ZJLX" value="SF"/>
                            </p>
                            <p>
                                <span><i>*</i> 法人代表手机号码：</span><input type="text" maxlength="11"
                                    class="eve-input validate[required,maxSize[11]],custom[mobilePhone]"
                                    name="SJHM" /><label>用于接收系统的办理事项进度等消息</label>
                            </p>
                            <p>
                                <span> 电话号码：</span><input type="text" 
                                    class="eve-input validate[maxSize[13]],custom[fixPhoneWithAreaCode]"
                                    name="DHHM" />
                            </p>
                            <p>
                                <span> 邮政编码：</span><input type="text" maxlength="6"
                                    class="eve-input validate[maxSize[6]],custom[chinaZip]" name="YZBM" />
                            </p>
                            <p style="height: 90px;">
                                <span> 单位住址：</span>
                                <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[50]]"
                                    style="width: 370px;border: 1px solid #c9deef;" name="DWDZ"></textarea>
                            </p>
                            <div class="reg_menu">经办人信息</div>
                            <p>
                                <span> 经办人邮箱地址：</span><input type="text" 
                                    class="eve-input validate[maxSize[30]],custom[email]"
                                    name="JBRYXDZ" />
                            </p>
                            <p>
                                <span><i>*</i> 经办人姓名：</span><input type="text" maxlength="10"
                                    class=" validate[required,maxSize[10]],custom[onlychineseLetter]" name="JBRXM" />
                            </p>
                            <p>
                                <span><i>*</i> 经办人身份证：</span><input type="text" maxlength="20" 
                                    class=" validate[required,maxSize[20]],custom[vidcard]" name="JBRSFZ" />
                            </p>
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
			</div>
		</div>
	</form>
	<jsp:include page="../index/foot.jsp" />
</body>
</html>
