<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>投诉监督_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,validationegine"></eve:resources>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">

		$(function() {
			AppUtil.initWindowForm("complainForm", function(form, valid) {
				if (valid) {
					var formData = $("#complainForm").serialize();
					var url = $("#complainForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "提交成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								window.location.href = 	window.location.href;	
							} else {
								art.dialog({
									content: resultJson.msg,
									icon:"error",
									time:3,
									ok: true
								});	
								window.location.href = 	"<%=path%>/complainController.do?tsjd";	
							}
						}
					});
				}
			}, "T_HD_COMPLAIN");
		});
		
		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='password']").val("");
			$("textarea[name='COMPLAIN_CONTENT']").val("");	
			$("input[name='CONSULT_DEPTID']").val("");					
		};
		
		function showSelectDeparts(){
			var departId = $("input[name='CONSULT_DEPTID']").val();
			$.dialog.open("departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
					+"checkCascadeN=", {
				title : "部门选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if(selectDepInfo){
						$("input[name='COMPLAIN_DEPTID']").val(selectDepInfo.departIds);
						$("input[name='COMPLAIN_DEPT']").val(selectDepInfo.departNames);
					}
				}
			}, false);
		};
	</script>
</head>

<body>
	<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>投诉监督</i></div>
    	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
                    	<li><a href="webSiteController/view.do?navTarget=website/hd/zxhd">业务咨询</a></li>
                        <li><a href="commentController.do?wspy">网上评议</a></li>
                        <li class="subOn"><a href="complainController.do?tsjd">投诉监督</a></li>
                        <li><a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
            	<div class="title3">提交者信息</div>
				<form id="complainForm" method="post" action="complainController/saveComplain.do">
					<!--==========隐藏域部分开始 ===========-->
					<input type="hidden" name="CREATE_USERNAME"  value="${sessionScope.curLoginMember.YHMC}">
					<input type="hidden" name="CREATE_USERID" value="${sessionScope.curLoginMember.USER_ID}">
					<input type="hidden" name="COMPLAIN_DEPTID">
					<!--==========隐藏域部分结束 ===========-->
					<table cellpadding="0" cellpadding="0" class="tableno">
						<tr>
							<th style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;姓名：</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="10"   class="validate[required],maxSize[10]" name="COMPLAIN_NAME" value="${sessionScope.curLoginMember.YHMC}"/></p></td> 
							<th style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;手机号码：</th>
							<td><p><input  style="width:220px;color: #000;" type="text"  name="COMPLAIN_PHONE" class="validate[required],custom[mobilePhone]" value="${sessionScope.curLoginMember.SJHM}"/></p></td> 
						</tr>
						<tr>
							<th style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;电子邮箱：</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="30" name="COMPLAIN_EMAIL" class="validate[required],custom[email],maxSize[30]"  value="${sessionScope.curLoginMember.DZYX}"/></p></td> 
							
							<th style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;投诉单位：</th>
							<td><p><input style="width:155px;color: #000;" type="text" maxlength="30" name="COMPLAIN_DEPT" class="validate[required],maxSize[30]"/><a href="javascript:showSelectDeparts();" style="margin-left: 5px;">选 择</a></p></td> 
						</tr>
						<tr>
							<th style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;投诉标题：</th>
							<td colspan="3"><p><input type="text"  maxlength="120" style="width:588px;color: #000;" class="validate[required],maxSize[120]" name="COMPLAIN_TITLE"/></p></td> 
						</tr>
						<tr>
							<th valign="top" style="padding-left: 0px; padding-right: 0px;"><span>*</span>&nbsp;投诉内容：</th>
							<td colspan="3"><textarea name="COMPLAIN_CONTENT" class="validate[required],maxSize[500]"></textarea></td>
						</tr>
					</table>
					<div class="sbtbtn"><a href="javascript:void(0);" onclick="$('#complainForm').submit();" class="btn2">提  交</a></div>
				</form>
            </div>
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
