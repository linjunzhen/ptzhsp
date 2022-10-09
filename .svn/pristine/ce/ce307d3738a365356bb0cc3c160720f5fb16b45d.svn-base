<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取登录用户部门编码
	String depCode =  sysUser.getDepCode();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否业务咨询受理员
    boolean ywzxsly = roleCodes.contains("ywzxsly");
	//判断是否超级管理员或业务咨询受理员或部门为行政服务中心部门编码88888
	if("__ALL".equals(resKey)||ywzxsly||depCode.equals("88888")){
	    request.setAttribute("isAll",true);
	}else{
	    request.setAttribute("isAll",false);
	}
%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("wyjcInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#wyjcInfoForm").serialize();
				var url = $("#wyjcInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: "保存成功",
								icon:"succeed",
								time:3,
							    ok: true
							});
							parent.reloadWyjcList(resultJson.msg);
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
		}, "T_HD_LETTER");

	});
</script>

</head>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
	<form id="wyjcInfoForm" method="post"
		action="wyjcController.do?saveOrUpdate">
		<div region="center" style="min-height:456px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ERROR_ID" value="${wyjc.ERROR_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">姓名：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.USER_NAME}</p>
					</td>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">联系方式：</span>
					</td>
					<td>
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.USER_CONTACT}</p>
					</td>
				</tr>
				<tr>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">错误页面地址：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.ERROR_URL}</p>
					</td>
				</tr>
				<tr>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">内容标题：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.ERROR_TITLE}</p>
					</td>
				</tr>
				<tr>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">问题描述：</span>
					</td>
					<td colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.ERROR_CONTENT}</p>
					</td>
				</tr>			
				<tr>
					<td style="width:85px;">
						<span style="width: 120px;float:left;text-align:right;">处理时间：</span>
					</td>
					<td  colspan="3">
						<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">${wyjc.REPLY_TIME}</p>
					</td>
				</tr>	
				<tr style="height:100px;" id="tr_reply">
					<td>
						<span style="width: 120px;float:left;text-align:right;height:100px;">处理情况：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="5" cols="200" style="width:95%;height:200px;" 
						  name="REPLY_CONTENT">${wyjc.REPLY_CONTENT}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

