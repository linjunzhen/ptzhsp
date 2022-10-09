<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("projectForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#projectForm").serialize();
				var url = $("#projectForm").attr("action");
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
							parent.reloadProjectList(resultJson.msg);
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
		}, "T_WSBS_PROJECT");
	});
	
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="projectForm" method="post"
		action="projectController.do?saveOrUpdate">
		<div region="center" style="min-height:210px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="PROJECT_ID" value="${project.PROJECT_ID}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:35px;">
					<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">项目名称：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="30" type="text" name="PROJECT_NAME" value="${project.PROJECT_NAME}" style="width:480px;"/>
					</td>
				</tr>
				
				<tr style="height:100px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;height:100px;">项目说明：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
						  name="PROJECT_EXPLAIN">${project.PROJECT_EXPLAIN}</textarea>
					</td>
				</tr>
				<tr style="height:35px;">
					<td style="width:80px;">
						<span style="width: 80px;float:left;text-align:right;">立项时间：</span>
					</td>
					<td colspan="3">
						<input type="text"
                                style="width:197px;float:left; height: 24px;"  readonly="ture" class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" name="PROJECT_TIME" value="${project.PROJECT_TIME}"  />
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

