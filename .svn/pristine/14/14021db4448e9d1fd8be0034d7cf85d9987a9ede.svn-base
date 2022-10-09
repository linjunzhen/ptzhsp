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
	<title>堵点难点问题征集_平潭综合实验区行政服务中心</title>
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
        $(function() {
            AppUtil.initWindowForm("wyjc_form", function(form, valid) {
                if (valid) {
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
                                    content: "你堵点难点问题征集的查询码是："+ resultJson.msg+",请复制保存",
                                    icon:"succeed",
                                    ok: true
                                });
                                resetForm();
                            }else{
                                art.dialog({
                                    content:resultJson.msg,
                                    icon:"error",
                                    ok: true
                                });
                            }
                        }
                    });
                }
            }, "T_HD_QUESTION");

            changeRandPic();

        });

        function changeRandPic(){
            $("#randpic").attr({
                "src": "<%=path %>/rand.jpg?"+Math.random()
            });
        }
        function checkFormContent(){
            var str = $("textarea[name='QUESTION_CONTENT']").val();;
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
	<%--    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>堵点难点问题征集</i></div>--%>
	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>咨询互动</i> > <i>堵点难点问题列表</i></div>
	<div class="listMain clearfix">
		<div class="listL">
			<div class="listTitle">咨询互动</div>
			<div class="sublist">
				<ul>
					<%--                    	<li><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>--%>
					<li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/wtzj">堵点难点问题征集</a></li>
						<li><a href="webSiteController/view.do?navTarget=website/hd/wtzjList">堵点难点问题列表</a></li>
				</ul>
			</div>
		</div>
		<div class="listR">
			<div><h1 style="text-align: center;">2019年群众办事堵点征集疏解行动</h1></div>
			<p style="text-align: justify;line-height: 1.5;text-indent: 2em;font-size: 12pt;margin-bottom: 0px;">
				为落实“放管服”改革，优化政府办事流程，确保改革成效惠及广大群众，8月1日起，区行政服务中心开展“群众办事堵点难点问题征集”活动，
				欢迎您反映办事过程中存在的办事材料多、反复提交证明、手续环节多、耗时长、来回跑路等问题，并提出意见和建议。</p>
			<p style="text-align: justify;line-height: 1.5;text-indent: 2em;font-size: 12pt;margin-bottom: 0px;">
				此次活动重点征集重点围绕商事服务、教育就业、创业创新、社保医疗、便民服务等堵点难点问题。区行政服务中心将对收到的问题和提出的意见建议进行汇总整理，并组织各有关部门研究改进。</p>
			<form class="eui-form" id="wyjc_form" method="post" action="<%=path%>/wyjcController/saveQuestion.do">
				<div class="title3" style="margin-bottom:0px;">信息填写</div>
				<table cellpadding="0" cellpadding="0" class="eui-table">
					<tr>
						<th><font color="#FF0000">* </font>堵点单位：</th>
						<td colspan="3"><input type="text" style="width:500px;" name="QUESTION_ADDRESS" class="validate[required],maxSize[100]"/></td>
					</tr>
					<tr>
						<th><font color="#FF0000">* </font>内容标题：</th>
						<td colspan="3"><input type="text" style="width:500px;" name="QUESTION_TITLE" class="validate[required],maxSize[100]"/></td>
					</tr>
					<tr>
						<th valign="top"><font color="#FF0000">* </font>问题描述：</th>
						<td colspan="3"><textarea onblur="checkFormContent()" onkeyup="checkFormContent()" name="QUESTION_CONTENT" class="validate[required],maxSize[1000]"></textarea><br />
							<span id="contentSpan">（还剩1000字可输入）</span></td>
					</tr>
					<tr>
						<th><font color="#FF0000">* </font>您的姓名：</th>
						<td colspan="3"><input type="text" class="validate[required],maxSize[30]" style="width:380px;" name="QUESTION_NAME"/><span>（可匿名填写）</span></td>
					</tr>
					<tr>
						<th><font color="#FF0000">* </font>手机号码：</th>
						<td colspan="3"><input type="text" style="width:380px;" class=" validate[required,maxSize[11]],custom[mobilePhone]" name="QUESTION_PHONE"/><span></span></td>
					</tr>
					<tr>
						<th><font color="#FF0000">* </font>是否公开：</th>
						<td colspan="3">
							<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">
								可以在网上公开<input type="radio" value="1" name="QUESTION_ISPUBLIC" <c:if test="${question.QUESTION_ISPUBLIC != 0}">checked="checked"</c:if> />
								不在网上公开<input type="radio" value="0" name="QUESTION_ISPUBLIC" <c:if test="${question.QUESTION_ISPUBLIC == 0}">checked="checked"</c:if>/>
							</p>
						</td>
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
