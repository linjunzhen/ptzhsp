<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<title>预约${param.departName}</title>
		<%@include file="common.jsp"%>
		<link rel="stylesheet"
			href="${webRoot }/plug-in/assets/css/amazeui.min.css">
		<link rel="stylesheet" href="${webRoot }/plug-in/assets/css/app.css">
		<link rel="stylesheet"
			href="${webRoot }/plug-in/evecom/css/evecom.css">
		<script src="${webRoot}/plug-in/assets/js/jquery.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/handlebars.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/amazeui.min.js"></script>
		<script src="${webRoot}/plug-in/layer_mobile/layer.js"></script>
		<script src="${webRoot}/plug-in/evecom/mobilUtil.js"></script>
		<script src="${webRoot}/plug-in/evecom/evevalidate.js"></script>
		<script src="${webRoot}/plug-in/evecom/json2.js"></script>
		<script src="http://101.com/JsBridge.js"></script>
		<script>
		//初始化时获取星榕基用户信息
		$(function(){
			var uc= Bridge.require("sdp.uc");
			uc.getCurrentUser({"force":true},{
				success:function(args){
					var userCode = args.org_exinfo.org_user_code;
					mobilUtil.doPost({
						url:"${webRoot}/bespeakOnlineController/getUser.do",
						params:"userCode=" + userCode,
						callback : function(resultJson) {
							if(resultJson.success){
								mobilUtil.layerAlert(resultJson.msg);
							}
						}
					});
				},
				fail:function(args){
					mobilUtil.layerAlert("获取用户信息失败，错误信息："+args);
				}
			});
		});
		function submitApply(){
			var validateResultMsg = mobilUtil.validateForm("myform");
			if (validateResultMsg || validateResultMsg=="") {
				mobilUtil.layerAlert(validateResultMsg);
				return;
			}
			var chinaIdCard = mobilUtil.chinaIdCard($("#CARD").val());
			if(!chinaIdCard){
				mobilUtil.layerAlert("身份证号码格式不正确");
				return;
			}
			var formData = $("#myform").serialize();
			var url = "${webRoot}/bespeakOnlineController/doPortalBespeakApply.do";
			mobilUtil.doPost({
				url:url,
				params:formData,
				callback : function(resultJson) {
					$("#submitbutton").hide();
					mobilUtil.layerAlert(resultJson.msg);
				}
			});
		}
		</script>
	</head>
	<body>


		<div class="govmain clearfix govtm10">
			<form action="" class="am-form govForm clearfix" id="myform">
			<input type="hidden" value="${param.dateTime}" name="DATE_TIME"/>
			<input type="hidden" value="${param.beginTime}" name="BEGIN_TIME"/>
			<input type="hidden" value="${param.endTime}" name="END_TIME"/>
			<input type="hidden" value="${param.departId}" name="DEPART_ID"/>
			<input type="hidden" value="${param.allowBespeakNumber}" name="allowBespeakNumber"/>
			
				<div class="am-form-group clearfix">
					<label class="label-left lable-title">
						预约信息
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						预约部门
					</label>
					<label class="label-right govcolor am-text-left">
						${param.departName}
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						预约日期
					</label>
					<label class="label-right govcolor am-text-left">
						${param.dateTime}
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						预约时段
					</label>
					<label class="label-right govcolor am-text-left">
						${param.beginTime}-${param.endTime}
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left lable-title">
						预约人信息
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						姓名
					</label>
					<label class="label-right govcolor am-text-left">
						<input type="text" validateRules="required" fieldName="预约人姓名"
							maxlength="60"
							value=""
							name="NAME" />
					</label>
					<label class="lable-must">
						*
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						身份证
					</label>
					<label class="label-right govcolor am-text-left">
						<input type="text" validateRules="required" fieldName="预约人身份证"
							maxlength="60"
							value=""
							name="CARD" id="CARD"/>
					</label>
					<label class="lable-must">
						*
					</label>
				</div>
				<div class="am-form-group clearfix">
					<label class="label-left">
						联系电话
					</label>
					<label class="label-right govcolor am-text-left">
						<input type="text" validateRules="required,mobilePhoneLoose" fieldName="预约人联系电话"
							maxlength="60"
							value=""
							name="PHONE" />
					</label>
					<label class="lable-must">
						*
					</label>
				</div>
				
				<div class="am-form-group clearfix">
					<a id="submitbutton" class="mbutton" onclick="submitApply()">提交</a>
				</div>
		</div>
		</form>
		</div>
	</body>
</html>