<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ZXFJForm", function(form, valid) {
			if (valid) {			
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#ZXFJForm").serialize();
				var url = $("#ZXFJForm").attr("action");
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
							//parent.$("#ZXFJItemsGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},null);
	});

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ZXFJForm" method="post" action="bdcDyqzxdjController.do?saveOrUpdateZXFJ">
	    <div  id="ZXFJFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		     <input type="hidden" name="BUSINESS_NAME" value="${businessName}"/>
		     <input type="hidden" name="BUSINESS_TYPE" value="常用意见"/>
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>常用意见</a></div></td>
				</tr>
				<tr>
					<td ><span
						style="width: 100px;float:left;text-align:right;">常用意见：</span> </td>
					<td><input
						type="text" style="width:250px;float:left;" value="" maxlength="528"
						class="eve-input validate[required]" name="OPINION_CONTENT" /> <font
						class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
