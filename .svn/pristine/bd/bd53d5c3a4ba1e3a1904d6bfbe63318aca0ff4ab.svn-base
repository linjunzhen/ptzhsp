<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String type = request.getParameter("type");
request.setAttribute("type", type);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我要纠错_平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet" id="easyuiTheme">
<link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
<script src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" type="text/javascript"></script>
<script src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link type="text/css" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" rel="stylesheet">
<link type="text/css" href="<%=path%>/plug-in/validationegine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet">
<script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/json-2.0/json2.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/layer-1.8.5/layer.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/plug-in/swfupload-2.2/swfupload.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/upload/jquery.picupload.js"></script>
<link type="text/css" href="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.css" rel="stylesheet">
<script src="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.js" type="text/javascript"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
<script type="text/javascript">
	var __ctxPath="<%=request.getContextPath() %>";
	$(function() {
		AppUtil.initWindowForm("wyjc_form", function(form, valid) {
			if (valid) {
				var ERROR_URL = $("input[name='ERROR_URL']").val();
				if(ERROR_URL.indexOf("xzfwzx.pingtan.gov.cn")<0){
					alert("错误页面地址非本网站地址，不在纠错范围内！");
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#wyjc_form").serialize();
				var url = $("#wyjc_form").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								ok: true
							});
							resetForm();
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, "T_HD_ERROR");
		
		changeRandPic();
		
	});
	
	function changeRandPic(){
    	$("#randpic").attr({
	          "src": "<%=path %>/rand.jpg?"+Math.random()
	     });
    }
	function checkFormContent(){
		var str = $("textarea[name='ERROR_CONTENT']").val();;
		if (str.length<=1000) {
			document.getElementById("contentSpan").innerHTML = "(还剩"+(1000-str.length)+"字可输入)";
		} else {
			document.getElementById("contentSpan").innerHTML = "<font color='red'>(您输入的内容超过一千字)</font>";
		}
	}
	
	function resetForm(){
		$('#wyjc_form')[0].reset();
		checkFormContent();
		changeRandPic();
	}
	
</script>
</head>

<body>
	<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
<%--    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>我要纠错</i></div>--%>
	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>咨询互动</i> > <i>我要纠错</i></div>
	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
<%--                    	<li><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>--%>
                        <li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/wyjc">我要纠错</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
				<form class="eui-form" id="wyjc_form" method="post" action="<%=path%>/wyjcController/saveError.do">
					<div class="title3" style="margin-bottom:0px;">纠错须知</div>
					<div class="eui-xsqx">                	
						<div>1.无法显示的网页，图片或者附件等；</div>              	
						<div>2.网页内容、格式上的错误，特别是办事服务存在内容不准确、信息不完整、更新不及时的地方；</div>              	
						<div>3.任何关于网站内容和功能方面的意见和想法，您都可以告诉我们；</div>
						<div>4.您提交的问题将在10个工作日内处理。</div>
						<div class="eui-xsyqts">若您在填写信息时所用时间过长，将会造成验证码失效，需刷新后再次提交。</div>
					</div>
					<div class="title3" style="margin-bottom:0px;">信息填写</div>
					<table cellpadding="0" cellpadding="0" class="eui-table">
						<tr>
							<th style="width: 120px;"><font color="#FF0000">* </font>错误类型：</th>
							<td colspan="3">		
								<select defaultemptytext="请选择错误类型" class="validate[required],eve-input" name="ERROR_TYPE">
									<option value="">请选择错误类型</option>
									<option value="网页错误">网页错误</option>
									<option value="其他">其他</option>
								</select>	
							</td>
						</tr>
						<tr>
							<th><font color="#FF0000">* </font>错误页面地址：</th>
							<td colspan="3">
								<input type="text" style="width:500px;" name="ERROR_URL" class="validate[required],maxSize[256]"/>
							</td>
						</tr>
						<tr>
							<th><font color="#FF0000">* </font>内容标题：</th>
							<td colspan="3"><input type="text" style="width:500px;" name="ERROR_TITLE" class="validate[required],maxSize[100]"/></td>
						</tr>
						<tr>
							<th valign="top"><font color="#FF0000">* </font>问题描述：</th>
							<td colspan="3"><textarea onblur="checkFormContent()" onkeyup="checkFormContent()" name="ERROR_CONTENT" class="validate[required],maxSize[1000]"></textarea><br />
							<span id="contentSpan">（还剩1000字可输入）</span></td>
						</tr>
						<tr>
							<th><font color="#FF0000">* </font>您的姓名：</th>
							<td colspan="3"><input type="text" class="validate[required],maxSize[30]" style="width:380px;" name="USER_NAME"/><span>（可匿名填写）</span></td>
						</tr>
						<tr>
							<th><font color="#FF0000">* </font>联系方式：</th>
							<td colspan="3"><input type="text" style="width:380px;" class="validate[required],maxSize[128]" name="USER_CONTACT"/><span>（可填写联系电话、电子邮箱）</span></td>
						</tr>
						<tr>
							<th><font color="#FF0000">* </font>验证码：</th>
							<td colspan="3">
								<input name="vcode" id="vcode" type="text" class="validate[required],maxSize[4]" style="width:105px; height: 24px;" />					
								<img name="vc" id="randpic" style="height: 26px;margin-top: -5px;width:80px;"
								title="点击切换验证码" alt="验证码" src="<%=path %>/rand.jpg" align="middle"
								onclick="changeRandPic();" style="cursor:pointer"/>
							</td>
						</tr>
					</table>
					<div class="sbtbtn">
						<a href="javascript:void(0);" id="smxzBtn" class="btn2" onclick="$('#wyjc_form').submit();">提  交</a> 
						<a href="javascript:resetForm();" class="btn2">重  置</a>
					</div>
				</form>
            </div>
			
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
