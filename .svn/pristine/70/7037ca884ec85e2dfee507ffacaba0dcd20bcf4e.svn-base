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
		AppUtil.initWindowForm("auditForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#auditForm").serialize();
				var url = $("#auditForm").attr("action");
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
							parent.auditListSearch();
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
		}, "T_CMS_ARTICLE_AUDIT");
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
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="auditForm" method="post"
		action="auditContentController.do?saveOrUpdate">
		<div region="center" style="min-height:460px;">
			<div class="easyui-tabs eve-tabs" fit="true" >
			<div title="审核信息" >
			    <!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="CONTENT_ID" value="${contentId}">
				<!--==========隐藏域部分结束 ===========-->
				<table style="width:100%;border-collapse:collapse;"
					class="dddl-contentBorder dddl_table" >
					<tr style="height:35px;">
						<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
					</tr>
					<tr style="height:35px;">
						<td>
							<span style="width: 80px;float:left;text-align:right;">状态：</span>
						</td>
						<td>
						<select defaultemptytext="请选择状态" class="eve-input validate[required]" name="state">
							<option value="">请选择状态</option>	
							<option value="1">审核通过</option>		
							<option value="0">审核不通过</option>			
							<!--<option value="-1">回退上一步</option>-->
						</select><font class="dddl_platform_html_requiredFlag">*</font>
						</td>
					</tr>
					<tr style="height:100px;">
						<td>
							<span style="width: 80px;float:left;text-align:right;height:100px;">审核意见：</span>
						</td>
						<td>
							<textarea id="auditOPinion" class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:480px;height:100px;" 
							  name="auditOPinion"></textarea><font class="dddl_platform_html_requiredFlag">*</font>
						</td>
					</tr>
					
					<tr style="height:195px;">
						<td colspan="2"></td>
					</tr>
				</table>
				<div data-options="region:'south',split:true,border:false"  >
					<div class="eve_buttons">
						<input value="确定" type="submit"
							class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
						<input
							value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
							onclick="AppUtil.closeLayer();" />
					</div>
				</div>
			</div>
			<!--<div title="审核过程明细" href="auditContentController.do?view&contentId=${contentId}">
			</div>-->
		</div>
			
		</div>
	</form>

</body>

