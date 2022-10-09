<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,swfupload"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
 <script type="text/javascript" src="<%=path%>/plug-in/jqueryUpload/AppUtilNew.js"></script> 
<script type="text/javascript">
var __ctxPath="<%=request.getContextPath() %>";
	$(function() {
		AppUtil.initWindowForm("msgTemplateInfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#msgTemplateInfoForm").serialize();
				var url = $("#msgTemplateInfoForm").attr("action");
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
							//parent.reloadNoticeList(resultJson.msg);
							parent.$("#msgTemplateViewGrid").datagrid("reload");
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
		}, "JBPM6_SENDMSG_TEMPLATE");
	
});

	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="msgTemplateInfoForm" method="post"
		action="msgTemplateController.do?saveOrUpdate">
		<div region="center" style="min-height:360px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="TEMPLATE_ID" value="${templateInfo.TEMPLATE_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
			  <tr style="height:29px;">
				<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			   </tr>  
				<tr style="height:35px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">模板名称：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="100" type="text" name="TEMPLATE_NAME" value="${templateInfo.TEMPLATE_NAME}"
						 style="width:500px;"/><font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:100px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;height:100px;">模板内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="100" style="width:500px;height:100px;" 
						  name="TEMPLATE_CONTENT">${templateInfo.TEMPLATE_CONTENT}</textarea><font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:100px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;height:100px;">备注：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea maxSize[500]" rows="3" cols="100" style="width:500px;height:100px;" 
						  name="REMARK">${templateInfo.REMARK}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>

</body>

