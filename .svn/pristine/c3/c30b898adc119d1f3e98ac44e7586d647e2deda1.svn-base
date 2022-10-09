
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("GlobalUrlForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#GlobalUrlForm").serialize();
				var url = $("#GlobalUrlForm").attr("action");
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
							parent.$("#GlobalUrlGrid").datagrid("reload");
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
		}, null);

	});
	
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="GlobalUrlForm" method="post"
		action="globalUrlController.do?saveOrUpdate">
		<div id="GlobalUrlFormDiv" data-options="region:'center',split:true,border:false">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="URL_ID" value="${globalUrl.URL_ID}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">URL：</span>
						<input type="text" style="width:400px;float:left;" maxlength="256"
						class="eve-input validate[required]" value="${globalUrl.URL_ADDRESS}"
						name="URL_ADDRESS" /><font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">过滤类型：</span>
						<div class="eve-chcekbox" style="width:200px;">
							<ul>
								<li>
								<input class="validate[required]" type="radio" name="URL_FILTERTYPE" value="1" <c:if test="${globalUrl.URL_FILTERTYPE!=2}">checked="checked"</c:if>>匿名类型
								</li>
								<li>
								<input class="validate[required]" type="radio" name="URL_FILTERTYPE" value="2" <c:if test="${globalUrl.URL_FILTERTYPE==2}">checked="checked"</c:if>>会话类型
								</li>
							</ul>
						</div>
						<font class="dddl_platform_html_requiredFlag">*</font>
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

