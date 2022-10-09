<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		
		
		AppUtil.initWindowForm("singleSignOnForm", function(form, valid) {
			if (valid) {
				$('#singleSignOnForm').find('input,textarea').prop("disabled", false);
				$('#singleSignOnForm').find('select').prop("disabled", false);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#singleSignOnForm").serialize();
				var url = $("#singleSignOnForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#singleSignOnGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_SYSTEM_SINGLESIGNON");
		
		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	.eflowbutton {
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height: 26px;
		color: #fff;
		border-radius: 5px;
	}
	.whf_input{width:97%!important;;height:25px;float:left;}
	.btn1 {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 50px;
	}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="singleSignOnForm" method="post"
		action="singleSignOnController.do?saveOrUpdate">
		<div region="center" style="min-height:260px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="SSO_ID" value="${singleSignOn.SSO_ID}">
				<!--==========隐藏域部分结束 ===========-->
				
				<div class="syj-title1" style="height:30px;">
					<span>基本信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>单位名称：</th>
						<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="32" id="SSO_DW"
								class="eve-input validate[required]" placeholder="请输入单位名称"  value="${singleSignOn.SSO_DW}" name="SSO_DW" />
						</td>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>单位联系人：</th>
						<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="32" id="SSO_DWLXR"
								class="eve-input validate[required]" placeholder="请输入单位联系人" value="${singleSignOn.SSO_DWLXR}" name="SSO_DWLXR" />
						</td>
					</tr>
					<tr>
						<th style="border-bottom-width: 1px;"><font class="syj-color2">*</font>联系手机：</th>
						<td colspan="3">
							<input type="text" class="eve-input validate[required],custom[mobilePhoneLoose]" maxlength="11" style="width:36.5%;height:25px;float:left;"
								   name="SSO_DWLXRLXDH" placeholder="请输入联系手机" value="${singleSignOn.SSO_DWLXRLXDH}"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>用户账号：</td>
						<td>
							<c:if test="${singleSignOn.SSO_ID!=null}">
								${singleSignOn.SSO_USERNAME}
							</c:if> 
							<c:if test="${singleSignOn.SSO_ID==null}">
							<input  type="text" class="eve-input  validate[required],ajax[ajaxVerifyValueExist]" style="width:97%;height:25px;float:left;"
									name="SSO_USERNAME" id="SSO_USERNAME"  placeholder="请输入用户账号"  maxlength="32"  value="${singleSignOn.SSO_USERNAME}"/>
							</c:if>
						</td>
						<th><font class="syj-color2">*</font>用户授权码：</td>
						<td><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;"
								   name="SSO_PWD"  placeholder="请输入用户授权码"  maxlength="32"  value="${singleSignOn.SSO_PWD}"/></td>

					</tr>
				</table>
				<div>
					<div class="syj-title1" style="height:30px;">
						<span>获取TOKEN信息</span>
					</div>
					<table cellpadding="0" cellspacing="0" class="syj-table2">
						<tr>									
							<th style="border-bottom-width: 1px;width:180px;">TOKEN</th>
							<td>
								${singleSignOn.SSO_TOKEN}
							</td>
							<th style="border-bottom-width: 1px;width:180px;">获取TOKEN时间</th>
							<td>
								${singleSignOn.SSO_TOKEN_TIME}
							</td>
						</tr>
						<tr>									
							<th style="border-bottom-width: 1px;width:180px;">获取TOKEN IP地址</th>
							<td colspan="3">
								${singleSignOn.SSO_TOKEN_IP}
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<c:if test="${projectDetail != true}">
					<input value="确定" type="submit"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				</c:if>
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

