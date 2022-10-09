<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("PackageForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#PackageForm").serialize();
				var url = $("#PackageForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$("#PackageServiceGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							$("input[type='submit']").attr("disabled",false);
						}
					}
				});
			}
		},"T_CKBS_WIN_SCREEN");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="PackageForm" method="post" action="emsController.do?savePackageInfo">
	    <div  id="PackageFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${packageInfo.RECORD_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>					
					<td><span style="width: 100px;float:left;text-align:right;">邮件号：</span>
						<input type="text" style="width:180px;float:left;"
						value="${packageInfo.MAIL_NO}" maxlength="30"
						class="eve-input validate[required]"
						name="MAIL_NO" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">收件人：</span>
						<input type="text" style="width:180px;float:left;"
						value="${packageInfo.ADDRESSEE_NAME}" maxlength="16" 
						class="eve-input validate[required]"
						name="ADDRESSEE_NAME"/> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">电话号码：</span>
						<input type="text" style="width:180px;float:left;"
						value="${packageInfo.ADDRESSEE_PHONE}" maxlength="16"
						class="eve-input validate[required],custom[mobilePhoneLoose]"
						name="ADDRESSEE_PHONE" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width:100px;float:left;text-align:right;">详细地址：</span>
						<input type="text" style="width:180px;float:left;"
						value="${packageInfo.ADDRESSEE_ADDR}" maxlength="128"
						class="eve-input validate[required]"
						name="ADDRESSEE_ADDR" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width:100px;float:left;text-align:right;">邮政编码：</span>
						<input type="text" style="width:180px;float:left;"
						value="${packageInfo.ADDRESSEE_ZIPCODE}" maxlength="8"
						class="eve-input validate[required],custom[chinaZip]"
						name="ADDRESSEE_ZIPCODE" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
